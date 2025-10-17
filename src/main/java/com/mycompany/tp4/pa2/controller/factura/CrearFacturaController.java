/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.factura;

import com.mycompany.tp4.pa2.DAOImpl.ArticuloDAOImpl;
import com.mycompany.tp4.pa2.DAOImpl.FacturaDAOImpl;
import com.mycompany.tp4.pa2.controller.cliente.SeleccionarClienteController;
import com.mycompany.tp4.pa2.entidades.Articulo;
import com.mycompany.tp4.pa2.entidades.Cliente;
import com.mycompany.tp4.pa2.entidades.Domiciliaria;
import com.mycompany.tp4.pa2.entidades.Factura;
import com.mycompany.tp4.pa2.entidades.Herramienta;
import com.mycompany.tp4.pa2.entidades.Industrial;
import com.mycompany.tp4.pa2.entidades.Item;
import com.mycompany.tp4.pa2.view.clientes.SeleccionarClienteView;
import com.mycompany.tp4.pa2.view.facturas.CrearFacturaView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aquin
 */
public class CrearFacturaController {
    private CrearFacturaView crearF;
    private final FacturaDAOImpl facturaDao = FacturaDAOImpl.getInstancia();
    private final ArticuloDAOImpl articuloDao = ArticuloDAOImpl.getInstancia();
    private Cliente cli=null;
    private Factura factura=null;

    public CrearFacturaController(CrearFacturaView crearF) {
        this.crearF = crearF;
        reloadTableArticulos();
        crearF.getAtrasBtn().addActionListener(e -> cerrarVentana());
        crearF.getBuscarBtn().addActionListener(e -> buscarArticulo());
        crearF.getTablaDatos().getSelectionModel().addListSelectionListener(e -> filaSeleccionada());
        
        crearF.getSeleccionClienteBtn().addActionListener(e -> seleccionarCliente());
        crearF.getAgregarArticuloBtn().addActionListener(e -> agregarArticulo());
        crearF.getRemoverArticuloBtn().addActionListener(e -> removerArticulo());
        crearF.getCrearFacturaBtn().addActionListener(e -> crearFactura());
    }
    
    private void reloadTableArticulos(){
        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
        DefaultTableModel model = (DefaultTableModel) crearF.getTablaDatos().getModel();
        //se quitan las filas presentes en la tabla para cargar las nuevas filas
        model.setRowCount(0);
        // se obtiene la lista de articulos registrados
        List<Articulo> articulos = articuloDao.findAllArticulos();
        //por cada cliente, guarda cada uno de sus datos en un array y lo carga en el modelo como una fila.
        for(Articulo a: articulos){
            Object[] o = new Object[1];
            o[0] = a.getNombre();
            model.addRow(o);
        }
        // se hace el set del modelo en la tabla
        crearF.setTablaDatos(model);
    }
    
    private void cerrarVentana() {
        crearF.dispose();
    }
    
    private void buscarArticulo() {
        copiarDatosTextArea(crearF.getNombreArtBuscarTf().getText());
    }

    private void filaSeleccionada() {
        int fila = crearF.getTablaDatos().getSelectedRow();
        if (fila != -1) {
            String nombre = crearF.getTablaDatos().getValueAt(fila, 0).toString();
            copiarDatosTextArea(nombre);
        }
    }
    
    //no es keysensitive porque se le hace un lower case
    private void copiarDatosTextArea(String nombreArticulo){
        List<Articulo> articulos = articuloDao.findAllArticulos();
        // busca en la lista un cliente que coincida con el dni, si no encuentra devuelve null y luego se introducte el resultado en indexOf para obtener el indice
        int indice = articulos.indexOf(articulos.stream().filter(v -> v.getNombre().toLowerCase().equals(nombreArticulo.toLowerCase())).findFirst().orElse(null));
        // si encontró un cliente con el mismo dni
        
        if(indice != -1){
            crearF.resaltarFila(indice); //aunque aquí ya se haga la verificación del -1 se mantiene en el view por si se utiliza en otro momento.
            
            Articulo a = articulos.get(indice);
            crearF.setNombreArtBuscarTf(a.getNombre());
            String descripcion = "Nombre: " + a.getNombre() + "\nPrecio: $" + a.getPrecio() + "\nTipo de Articulo: " + a.getTipoArticulo();
            switch (a) {
                case Herramienta h -> descripcion += "\nDescripcion:\n" + h.getDescripcion();
                case Industrial i -> descripcion += "\nPotencia máxima: "+i.getPotenciaMaxima()+" V"+"\nTemperatura miníma: " + i.getTemperaturaMinima() + " °C\nTemperatura máxima: " + i.getTemperaturaMaxima()+" °C";
                case Domiciliaria d -> descripcion += "\nPotencia máxima: "+d.getPotenciaMaxima()+" V";
                default -> {
                }
            }
            crearF.setDatosArticuloTA(descripcion);
        }else
            JOptionPane.showMessageDialog(crearF,"No existe el artículo \""+nombreArticulo+"\" en el sistema.","Error",JOptionPane.ERROR_MESSAGE);            
    }

    private void seleccionarCliente() {
        SeleccionarClienteView seleccionarV = new SeleccionarClienteView();
        SeleccionarClienteController seleccionarC = new SeleccionarClienteController(seleccionarV,this);
        seleccionarV.setVisible(true);
        seleccionarV.setLocationRelativeTo(null);
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
        
        crearF.setNombreClienteTf(cli.getNombre()+" "+cli.getApellido());
        crearF.setDniClienteTf(cli.getDni());
        crearF.habilitarContenido(true);
        
        resetTableSeleccionados();
        crearF.setPrecioTotalTf("");
        generarNuevaFactura(cli.getDni());
    }
    
    public void crearFactura(){
        
        factura.setDniCliente(cli.getDni());
        factura.setFecha();
        factura.setLetra(((String) crearF.getTipoFacturaCb().getSelectedItem()).charAt(0));
        factura.setNroFactura(facturaDao.findAllFacturas().size()+1);
        try {
            if(factura.getItems().size()==0) throw new Exception("No se puede generar una factura sin items.");
            facturaDao.addFactura(factura);
            generarNuevaFactura(cli.getDni());
            resetTableSeleccionados();
            JOptionPane.showMessageDialog(crearF, "Factura registrada.","Factura",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(crearF, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarArticulo(){
        //prefiero que busque antes que estar guardando todas las veces que en la busqueda se selecciona un articulo
        List<Articulo> articulos = articuloDao.findAllArticulos();
        int indice = articulos.indexOf(articulos.stream().filter(v -> v.getNombre().toLowerCase().equals(crearF.getNombreArtBuscarTf().getText().toLowerCase())).findFirst().orElse(null));
        // si encontró un articulo con el mismo nombre
        if(indice != -1){
            Articulo articuloEncontrado = articulos.get(indice);
            //busca si el item que se quiere aniadir ya existe, si existe lo asigna a itemQuitar para obtener la cantidad y luego sumarla a la cantidad del nuevo item.
            Item itemQuitar = factura.getItems().stream().filter(v -> v.getArticulo().getNombre().equals(articuloEncontrado.getNombre())).findFirst().orElse(null);
            if(itemQuitar != null){
                int cantidadItem = itemQuitar.getCantidad();
                factura.removeItem(articuloEncontrado.getNombre());
                factura.addItem(articuloEncontrado, ((Integer) crearF.getCantidadArticuloSp().getValue())+cantidadItem);
            }else
                factura.addItem(articulos.get(indice), (Integer) crearF.getCantidadArticuloSp().getValue());
            reloadTableSeleccionados();
            reloadTotalPagar();
        }else
            JOptionPane.showMessageDialog(crearF,"No existe el artículo \""+crearF.getNombreArtBuscarTf().getText()+"\" en el sistema.","Error",JOptionPane.ERROR_MESSAGE);
    }
    
    public void removerArticulo(){
        int fila = crearF.getTablaArticulosSeleccionados().getSelectedRow();
        if (fila != -1) {
            String nombre = crearF.getTablaArticulosSeleccionados().getValueAt(fila, 0).toString();
            try {
                factura.removeItem(nombre);
                reloadTableSeleccionados();
                reloadTotalPagar();
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(crearF, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    private void reloadTableSeleccionados(){
        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
        DefaultTableModel model = (DefaultTableModel) crearF.getTablaArticulosSeleccionados().getModel();
        //se quitan las filas presentes en la tabla para cargar las nuevas filas
        model.setRowCount(0);
        // se obtiene la lista de articulos registrados
        //List<Articulo> articulos = articuloDao.findAllArticulos();
        //por cada cliente, guarda cada uno de sus datos en un array y lo carga en el modelo como una fila.
        for(Item i: factura.getItems()){
            Object[] o = new Object[2];
            o[0] = i.getArticulo().getNombre();
            o[1] = i.getCantidad();
            model.addRow(o);
        }
        // se hace el set del modelo en la tabla
        crearF.setTablaArticulosSeleccionados(model);
    }
    
    private void generarNuevaFactura(String dniCliente){
        factura = new Factura(dniCliente);
    }
    
    private void resetTableSeleccionados(){
        DefaultTableModel model = (DefaultTableModel) crearF.getTablaArticulosSeleccionados().getModel();
        model.setRowCount(0);
        crearF.setTablaArticulosSeleccionados(model);
    }

    private void reloadTotalPagar() {
        crearF.setPrecioTotalTf(String.valueOf(factura.informarTotalPagar()));
    }
    
}

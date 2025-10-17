/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.articulo;

import com.mycompany.tp4.pa2.DAOImpl.ArticuloDAOImpl;
import com.mycompany.tp4.pa2.entidades.Articulo;
import com.mycompany.tp4.pa2.entidades.Domiciliaria;
import com.mycompany.tp4.pa2.entidades.Herramienta;
import com.mycompany.tp4.pa2.entidades.Industrial;
import com.mycompany.tp4.pa2.view.articulos.BuscarArticuloView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aquin
 */
public class BuscarArticuloController {
    private final ArticuloDAOImpl articuloDao = ArticuloDAOImpl.getInstancia();
    private BuscarArticuloView buscarA;

    public BuscarArticuloController(BuscarArticuloView buscarA) {
        this.buscarA = buscarA;
        
        reloadTable();
        buscarA.getAtrasBtn().addActionListener(e -> cerrarVentana());
        buscarA.getBuscarBtn().addActionListener(e -> buscarArticulo());
        buscarA.getTablaDatos().getSelectionModel().addListSelectionListener(e -> filaSeleccionada());
    }
    
    private void reloadTable(){
        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
        DefaultTableModel model = (DefaultTableModel) buscarA.getTablaDatos().getModel();
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
        buscarA.setTablaDatos(model);
    }
    private void cerrarVentana() {
        buscarA.dispose();
    }
    
    private void buscarArticulo() {
        copiarDatosTextArea(buscarA.getNombreArtBuscarTf().getText());
    }

    private void filaSeleccionada() {
        int fila = buscarA.getTablaDatos().getSelectedRow();
        if (fila != -1) {
            String nombre = buscarA.getTablaDatos().getValueAt(fila, 0).toString();
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
            buscarA.resaltarFila(indice); //aunque aquí ya se haga la verificación del -1 se mantiene en el view por si se utiliza en otro momento.
            
            Articulo a = articulos.get(indice);
            buscarA.setNombreArtBuscarTf(a.getNombre());
            String descripcion = "Nombre: " + a.getNombre() + "\nPrecio: $" + a.getPrecio() + "\nTipo de Articulo: " + a.getTipoArticulo();
            switch (a) {
                case Herramienta h -> descripcion += "\nDescripcion:\n" + h.getDescripcion();
                case Industrial i -> descripcion += "\nPotencia máxima: "+i.getPotenciaMaxima()+" V"+"\nTemperatura miníma: " + i.getTemperaturaMinima() + " °C\nTemperatura máxima: " + i.getTemperaturaMaxima()+" °C";
                case Domiciliaria d -> descripcion += "\nPotencia máxima: "+d.getPotenciaMaxima()+" V";
                default -> {
                }
            }
            buscarA.setDatosArticuloTA(descripcion);
        }else
            JOptionPane.showMessageDialog(buscarA,"No existe el artículo \""+nombreArticulo+"\" en el sistema.","Error",JOptionPane.ERROR_MESSAGE);            
    }
}

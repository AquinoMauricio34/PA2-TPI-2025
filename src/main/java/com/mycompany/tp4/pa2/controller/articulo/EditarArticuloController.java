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
import com.mycompany.tp4.pa2.entidades.TipoArticulo;
import com.mycompany.tp4.pa2.view.articulos.EditarArticuloView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aquin
 */
public class EditarArticuloController {
    private EditarArticuloView editarA;
    private final ArticuloDAOImpl articuloDao = ArticuloDAOImpl.getInstancia();

    public EditarArticuloController(EditarArticuloView editarA) {
        this.editarA = editarA;
        reloadTable();
        editarA.getAtrasBtn().addActionListener(e -> cerrarVentana());
        editarA.getBuscarBtn().addActionListener(e -> buscarArticulo());
        editarA.getTablaDatos().getSelectionModel().addListSelectionListener(e -> filaSeleccionada());
        editarA.getGuardarArticuloBtn().addActionListener(e -> editarArticulo());
    }
    
    private void reloadTable(){
        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
        DefaultTableModel model = (DefaultTableModel) editarA.getTablaDatos().getModel();
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
        editarA.setTablaDatos(model);
    }
    private void cerrarVentana() {
        editarA.dispose();
    }
    
    private void buscarArticulo() {
        copiarDatosEnVentana(editarA.getNombreArtBuscarTf().getText());
    }

    private void filaSeleccionada() {
        int fila = editarA.getTablaDatos().getSelectedRow();
        if (fila != -1) {
            String nombre = editarA.getTablaDatos().getValueAt(fila, 0).toString();
            copiarDatosEnVentana(nombre);
        }
    }
    
    //no es keysensitive porque se le hace un lower case
    private void copiarDatosEnVentana(String nombreArticulo){
        List<Articulo> articulos = articuloDao.findAllArticulos();
        // busca en la lista un cliente que coincida con el dni, si no encuentra devuelve null y luego se introducte el resultado en indexOf para obtener el indice
        int indice = articulos.indexOf(articulos.stream().filter(v -> v.getNombre().toLowerCase().equals(nombreArticulo.toLowerCase())).findFirst().orElse(null));
        // si encontró un cliente con el mismo dni
        
        if(indice != -1){
            editarA.resaltarFila(indice); //aunque aquí ya se haga la verificación del -1 se mantiene en el view por si se utiliza en otro momento.
            
            Articulo a = articulos.get(indice);
            editarA.setNombreArtBuscarTf(a.getNombre());
            editarA.setNombreArticuloTf(a.getNombre());
            editarA.setPrecioSp(a.getPrecio());
            String tipo = "";
            switch (a) {
                case Herramienta h -> {
                    tipo = seleccionarPanel(TipoArticulo.HERRAMIENTA);
                    editarA.getHerra().setDescripcionHerramienteTextA(h.getDescripcion());
                }
                case Industrial i -> {
                    tipo = seleccionarPanel(TipoArticulo.ELECTRICIDAD_INDUSTRIAL);
                    editarA.getIndu().setPotenciaMaximaSp(i.getPotenciaMaxima());
                    editarA.getIndu().setTemperaturaMinSp(i.getTemperaturaMinima());
                    editarA.getIndu().setTemperaturaMaxSp(i.getTemperaturaMaxima());
                }
                case Domiciliaria d -> {
                    tipo = seleccionarPanel(TipoArticulo.ELECTRICIDAD_DOMICILIARIA);
                    editarA.getDomi().setPotenciaMaximaSp(d.getPotenciaMaxima());
                }
                default ->{}
            }
            editarA.setTipoArticuloTf(tipo);
        }else
            JOptionPane.showMessageDialog(editarA,"No existe el artículo \""+nombreArticulo+"\" en el sistema.","Error",JOptionPane.ERROR_MESSAGE);            
    }
    
    private String seleccionarPanel(TipoArticulo tipo) {
        String seleccionado = "";
        switch (tipo) {
            case HERRAMIENTA -> seleccionado = "Herramienta";
            case ELECTRICIDAD_DOMICILIARIA -> seleccionado = "Electricidad Domiciliaria";
            case ELECTRICIDAD_INDUSTRIAL -> seleccionado =  "Electricidad Industrial";
        }
        editarA.setPanel(seleccionado);
        return seleccionado;
    }
    
    private void editarArticulo(){
        String seleccionado = (String) editarA.getTipoArticuloTf().getText();
        try {
            String nombre = editarA.getNombreArtBuscarTf().getText();
            double precio = (Double) editarA.getPrecioSp().getValue();
            if(nombre.equals("")) throw new Exception("Asignar nombre al artículo.");
            if(precio <= 0) throw new Exception("El precio del articulo debe ser mayor a cero.");
            switch (seleccionado) {
                case "Electricidad Industrial" -> {
                    double potencia = (Double) editarA.getIndu().getPotenciaMaximaSp().getValue();
                    double tempMin = (Double) editarA.getIndu().getTemperaturaMinSp().getValue();
                    double tempMax = (Double) editarA.getIndu().getTemperaturaMaxSp().getValue();
                    if(potencia <= 0) throw new Exception("El valor de la potencia debe ser mayor a 0.");
                    if(tempMin >= tempMax) throw new Exception("La temperaturas mínima y máxima deben ser distintas y la mínima menor que la máxima.");
                    articuloDao.updateArticulo(new Industrial(tempMin,tempMax,potencia,nombre,precio,TipoArticulo.ELECTRICIDAD_INDUSTRIAL));
                }
                case "Electricidad Domiciliaria" -> {
                    double potencia = (Double) editarA.getDomi().getPotenciaMaximaSp().getValue();
                    if(potencia <= 0) throw new Exception("El valor de la potencia debe ser mayor a 0.");
                    articuloDao.updateArticulo(new Domiciliaria(potencia,nombre,precio,TipoArticulo.ELECTRICIDAD_DOMICILIARIA));
                }
                case "Herramienta" -> {
                    String descripcion = editarA.getHerra().getDescripcionHerramienteTextA().getText();
                    if(descripcion.equals("")) throw new Exception("La descripción de la herramienta no puede estar vacía.");
                    articuloDao.updateArticulo(new Herramienta(nombre,precio,TipoArticulo.HERRAMIENTA,descripcion));
                }
            }
            reloadTable();
            JOptionPane.showMessageDialog(editarA, "Articulo editado.","Actualización",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(editarA, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
}


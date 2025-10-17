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
import com.mycompany.tp4.pa2.view.articulos.BorrarArticuloView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aquin
 */
public class BorrarArticuloController {
    private BorrarArticuloView borrarA;
    private final ArticuloDAOImpl articuloDao = ArticuloDAOImpl.getInstancia();

    public BorrarArticuloController(BorrarArticuloView borrarA) {
        this.borrarA = borrarA;
    
        reloadTable();
        borrarA.getAtrasBtn().addActionListener(e -> cerrarVentana());
        borrarA.getBuscarBtn().addActionListener(e -> buscarArticulo());
        borrarA.getTablaDatos().getSelectionModel().addListSelectionListener(e -> filaSeleccionada());
        borrarA.getBorrarBtn().addActionListener(e -> borrarArticulo());
    }

    private void reloadTable(){
        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
        DefaultTableModel model = (DefaultTableModel) borrarA.getTablaDatos().getModel();
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
        borrarA.setTablaDatos(model);
    }
    private void cerrarVentana() {
        borrarA.dispose();
    }
    
    private void buscarArticulo() {
        copiarDatosTextArea(borrarA.getNombreArtBuscarTf().getText());
    }

    private void filaSeleccionada() {
        int fila = borrarA.getTablaDatos().getSelectedRow();
        if (fila != -1) {
            String nombre = borrarA.getTablaDatos().getValueAt(fila, 0).toString();
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
            borrarA.resaltarFila(indice); //aunque aquí ya se haga la verificación del -1 se mantiene en el view por si se utiliza en otro momento.
            
            Articulo a = articulos.get(indice);
            borrarA.setNombreArtBuscarTf(a.getNombre());
            String descripcion = "Nombre: " + a.getNombre() + "\nPrecio: $" + a.getPrecio() + "\nTipo de Articulo: " + a.getTipoArticulo();
            switch (a) {// cada case hace como un "a instanceof Herramienta h" por ejemplo
                case Herramienta h -> descripcion += "\nDescripcion:\n" + h.getDescripcion();
                case Industrial i -> descripcion += "\nPotencia máxima: "+i.getPotenciaMaxima()+" V"+"\nTemperatura miníma: " + i.getTemperaturaMinima() + " °C\nTemperatura máxima: " + i.getTemperaturaMaxima()+" °C";
                case Domiciliaria d -> descripcion += "\nPotencia máxima: "+d.getPotenciaMaxima()+" V";
                default -> {
                }
            }
            borrarA.setDatosArticuloTA(descripcion);
        }else
            JOptionPane.showMessageDialog(borrarA,"No existe el artículo \""+nombreArticulo+"\" en el sistema.","Error",JOptionPane.ERROR_MESSAGE);            
    }

    private void borrarArticulo() {
            String nombre=borrarA.getNombreArtBuscarTf().getText();
        try {
            articuloDao.removeArticulo(nombre);
            borrarA.setNombreArtBuscarTf("");
            borrarA.setDatosArticuloTA("");
            reloadTable();
            JOptionPane.showMessageDialog(borrarA, "Articulo \""+ nombre +"\" eliminado.","Información",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(borrarA, "No existe el articulo \""+nombre+"\" en los registros.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}


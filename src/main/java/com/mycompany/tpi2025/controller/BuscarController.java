/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.view.BuscarView;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aquin
 */
public class BuscarController {
    private BuscarView view;

    public BuscarController(BuscarView view) {
        this.view = view;
    }
    
//    private void reloadTable(){
//        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
//        DefaultTableModel model = (DefaultTableModel) buscarA.getTablaDatos().getModel();
//        //se quitan las filas presentes en la tabla para cargar las nuevas filas
//        model.setRowCount(0);
//        // se obtiene la lista de articulos registrados
//        List<Articulo> articulos = articuloDao.findAllArticulos();
//        //por cada cliente, guarda cada uno de sus datos en un array y lo carga en el modelo como una fila.
//        for(Articulo a: articulos){
//            Object[] o = new Object[1];
//            o[0] = a.getNombre();
//            model.addRow(o);
//        }
//        // se hace el set del modelo en la tabla
//        buscarA.setTablaDatos(model);
//    }
    
    
    
}

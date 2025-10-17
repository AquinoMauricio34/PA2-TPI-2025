/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.factura;

import com.mycompany.tp4.pa2.DAOImpl.FacturaDAOImpl;
import com.mycompany.tp4.pa2.entidades.Factura;
import com.mycompany.tp4.pa2.view.facturas.DetalleFacturaView;
import com.mycompany.tp4.pa2.view.facturas.ListarFacturaView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aquin
 */
public class ListarFacturaController {
    private final ListarFacturaView listarF;
    private final FacturaDAOImpl facturaDao = FacturaDAOImpl.getInstancia();
    private Factura factura;

    public ListarFacturaController(ListarFacturaView listarF) {
        this.listarF = listarF;
        
        reloadTable();
        this.listarF.getBuscarBtn().addActionListener(e -> buscarFactura());
        this.listarF.getAtrasBtn().addActionListener(e -> cerrarVentana());
        this.listarF.getTablaDatos().getSelectionModel().addListSelectionListener(e -> filaSeleccionada());
        this.listarF.getDetalleBtn().addActionListener(e -> mostrarDetalle());
    }
    
    private void reloadTable(){
        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
        DefaultTableModel model = (DefaultTableModel) listarF.getTablaDatos().getModel();
        //se quitan las filas presentes en la tabla para cargar las nuevas filas
        model.setRowCount(0);
        // se obtiene la lista de facturas registrados
        List<Factura> facturas = facturaDao.findAllFacturas();
        //por cada cliente, guarda cada uno de sus datos en un array y lo carga en el modelo como una fila.
        for(Factura c: facturas){
            Object[] o = new Object[2];
            o[0] = c.getNroFactura();
            o[1] = c.getFecha();
            model.addRow(o);
        }
        // se hace el set del modelo en la tabla
        listarF.setTablaDatos(model);
    }

    private void cerrarVentana() {
        listarF.dispose();
    }

    
    private void buscarFactura() {
        obtenerFactura(Long.parseLong(listarF.getNroBuscarTf().getText()));
    }

    private void filaSeleccionada() {
        int fila = listarF.getTablaDatos().getSelectedRow();
        if (fila != -1) {
            long nro = Long.parseLong(listarF.getTablaDatos().getValueAt(fila, 0).toString());
            obtenerFactura(nro);
        }
    }
    
    //no es keysensitive porque se le hace un lower case
    private void obtenerFactura(long nroFactura){
        List<Factura> facturas = facturaDao.findAllFacturas();
        // busca en la lista un cliente que coincida con el dni, si no encuentra devuelve null y luego se introducte el resultado en indexOf para obtener el indice
        int indice = facturas.indexOf(facturas.stream().filter(v -> v.getNroFactura() == nroFactura).findFirst().orElse(null));
        // si encontró un cliente con el mismo dni
        
        if(indice != -1){
            listarF.resaltarFila(indice); //aunque aquí ya se haga la verificación del -1 se mantiene en el view por si se utiliza en otro momento.
            this.factura = facturas.get(indice);
            listarF.habilitarContenido(true);
        }else
            JOptionPane.showMessageDialog(listarF,"No existe una factura con el nro: \""+nroFactura+"\" en el sistema.","Error",JOptionPane.ERROR_MESSAGE);            
    }
    
    private void mostrarDetalle() {
        DetalleFacturaView facturaV = new DetalleFacturaView();
        DetalleFacturaController facturaC = new DetalleFacturaController(facturaV, factura);
        facturaV.setVisible(true);
        facturaV.setLocationRelativeTo(null);
    }
    
}

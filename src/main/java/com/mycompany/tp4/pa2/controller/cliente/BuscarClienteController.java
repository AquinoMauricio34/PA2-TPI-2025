/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.cliente;

import com.mycompany.tp4.pa2.DAOImpl.ClienteDAOImpl;
import com.mycompany.tp4.pa2.entidades.Cliente;
import com.mycompany.tp4.pa2.view.clientes.BuscarClienteView;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aquin
 */
public class BuscarClienteController {
    private ClienteDAOImpl clienteDao = ClienteDAOImpl.getInstancia();
    private BuscarClienteView buscarC;

    public BuscarClienteController(BuscarClienteView buscarC) {
        this.buscarC = buscarC;
        
        reloadTable();
        this.buscarC.getBuscarBtn().addActionListener(e -> buscarCliente());
        this.buscarC.getAtrasBtn().addActionListener(e -> cerrarVentana());
    }
    
    private void reloadTable(){
        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
        DefaultTableModel model = (DefaultTableModel) buscarC.getTablaDatos().getModel();
        //se quitan las filas presentes en la tabla para cargar las nuevas filas
        model.setRowCount(0);
        // se obtiene la lista de clientes registrados
        List<Cliente> clientes = clienteDao.findAllClientes();
        //por cada cliente, guarda cada uno de sus datos en un array y lo carga en el modelo como una fila.
        for(Cliente c: clientes){
            Object[] o = new Object[5];
            o[0] = c.getNombre();
            o[1] = c.getApellido();
            o[2] = c.getDni();
            o[3] = c.getDireccion();
            o[4] = c.getTelefono();
            model.addRow(o);
        }
        // se hace el set del modelo en la tabla
        buscarC.setTablaDatos(model);
    }

    private void buscarCliente() {
        List<Cliente> clientes = clienteDao.findAllClientes();
        // busca en la lista un cliente que coincida con el dni, si no encuentra devuelve null y luego se introducte el resultado en indexOf para obtener el indice
        int indice = clientes.indexOf(clientes.stream().filter(v -> v.getDni().equals(buscarC.getDniBuscarTf().getText())).findFirst().orElse(null));
        // si encontró un cliente con el mismo dni
        buscarC.resaltarFila(indice);
    }

    private void cerrarVentana() {
        buscarC.dispose();
    }
}

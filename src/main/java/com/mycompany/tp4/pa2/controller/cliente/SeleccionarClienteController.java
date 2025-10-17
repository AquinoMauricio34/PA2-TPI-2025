/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.cliente;

import com.mycompany.tp4.pa2.DAOImpl.ClienteDAOImpl;
import com.mycompany.tp4.pa2.controller.factura.CrearFacturaController;
import com.mycompany.tp4.pa2.entidades.Cliente;
import com.mycompany.tp4.pa2.view.clientes.SeleccionarClienteView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aquin
 */
public class SeleccionarClienteController {
    private ClienteDAOImpl clienteDao = ClienteDAOImpl.getInstancia();
    private SeleccionarClienteView seleccionarC;
    private Cliente cli;
    private CrearFacturaController crearF;

    public SeleccionarClienteController(SeleccionarClienteView seleccionarC,CrearFacturaController crearF) {
        this.seleccionarC = seleccionarC;
        this.crearF = crearF;
        
        reloadTable();
        this.seleccionarC.getBuscarBtn().addActionListener(e -> buscarCliente());
        this.seleccionarC.getAtrasBtn().addActionListener(e -> cerrarVentana());
        this.seleccionarC.getTablaDatos().getSelectionModel().addListSelectionListener(e -> filaSeleccionada());
        this.seleccionarC.getSeleccionarBtn().addActionListener(e -> seleccionarCliente());
    }
    
    private void reloadTable(){
        // se obtiene el modelo de la tabla y se hace un cast para tener el metodo addRow, que de lo contrario con TableModel no tiene.
        DefaultTableModel model = (DefaultTableModel) seleccionarC.getTablaDatos().getModel();
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
        seleccionarC.setTablaDatos(model);
    }

    private void cerrarVentana() {
        seleccionarC.dispose();
    }

    
    private void buscarCliente() {
        obtenerCliente(seleccionarC.getDniBuscarTf().getText());
    }

    private void filaSeleccionada() {
        int fila = seleccionarC.getTablaDatos().getSelectedRow();
        if (fila != -1) {
            String dni = seleccionarC.getTablaDatos().getValueAt(fila, 2).toString();
            obtenerCliente(dni);
        }
    }
    
    //no es keysensitive porque se le hace un lower case
    private void obtenerCliente(String dniCliente){
        List<Cliente> clientes = clienteDao.findAllClientes();
        // busca en la lista un cliente que coincida con el dni, si no encuentra devuelve null y luego se introducte el resultado en indexOf para obtener el indice
        int indice = clientes.indexOf(clientes.stream().filter(v -> v.getDni().equals(dniCliente)).findFirst().orElse(null));
        // si encontró un cliente con el mismo dni
        
        if(indice != -1){
            seleccionarC.resaltarFila(indice); //aunque aquí ya se haga la verificación del -1 se mantiene en el view por si se utiliza en otro momento.
            
            this.cli = clientes.get(indice);
        }else
            JOptionPane.showMessageDialog(seleccionarC,"No existe un cliente con DNI: \""+dniCliente+"\" en el sistema.","Error",JOptionPane.ERROR_MESSAGE);            
    }
    
    private void seleccionarCliente() {
        crearF.setCli(cli);
        cerrarVentana();
    }
    
}

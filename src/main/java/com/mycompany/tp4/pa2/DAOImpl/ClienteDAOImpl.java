/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.DAOImpl;

import com.mycompany.tp4.pa2.DAO.ClienteDAO;
import com.mycompany.tp4.pa2.entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 *
 * @author aquin
 */
public class ClienteDAOImpl implements ClienteDAO {
    
    private static ClienteDAOImpl instancia; // se crea la instancia para que siempre se mantenga la lista.
    private final List<Cliente> clientes;

    public ClienteDAOImpl() {
        this.clientes = new ArrayList<>();
    }
    
    // obtiene la instancia global
    public static ClienteDAOImpl getInstancia() {
        if (instancia == null) {
            instancia = new ClienteDAOImpl();
        }
        return instancia;
    }
    
    @Override
    public void addCliente(Cliente cliente) throws Exception {
        //busca si ya existe un cliente con el mismo dni
        if(!clientes.stream().anyMatch(v -> v.getDni().equals(cliente.getDni()))){
            // si no existe lo agrega
            clientes.add(cliente);
        }else
            throw new Exception("Ya existe un cliente con el dni "+cliente.getDni());
    }

    @Override
    public void removeCliente(String dni) throws Exception {
        clientes.removeIf(v -> v.getDni().equals(dni));
    }

    @Override
    public Optional<Cliente> findCliente(String dni) {
        return clientes.stream().filter(v -> v.getDni().equals(dni)).findFirst();
    }

    @Override
    public List<Cliente> findAllClientes() {
        // no se hace return clientes porque se devolvería la referencia directa.
        return new ArrayList<>(clientes);
    }

    @Override
    public void updateCliente(Cliente cliente) throws Exception {
        int indice = IntStream
                .range(0, clientes.size())
                .filter(i -> clientes.get(i).getDni().equals(cliente.getDni()))
                .findFirst()
                .orElseThrow(() -> new Exception("Cliente con dni "+cliente.getDni()+" no se encuentra registrado."));
        clientes.set(indice, cliente);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tp4.pa2.DAO;

import com.mycompany.tp4.pa2.entidades.Cliente;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author aquin
 */
public interface ClienteDAO {
    void addCliente(Cliente cliente) throws Exception;
    void removeCliente(String dni) throws Exception;
    void updateCliente(Cliente cliente) throws Exception;
    Optional<Cliente> findCliente(String dni) throws Exception;
    List<Cliente> findAllClientes();
}

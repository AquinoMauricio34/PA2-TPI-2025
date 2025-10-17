/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tp4.pa2.DAO;

import com.mycompany.tp4.pa2.entidades.Factura;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author aquin
 */
public interface FacturaDAO {
    void addFactura(Factura articulo) throws Exception;
    void removeFactura(long nroFactura) throws Exception;
    Optional<Factura> findFactura(long nroFactura) throws Exception;
    List<Factura> findAllFacturas();
    void updateFactura(Factura factura) throws Exception;
}

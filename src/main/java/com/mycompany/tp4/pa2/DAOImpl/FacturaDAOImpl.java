/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.DAOImpl;

import com.mycompany.tp4.pa2.DAO.FacturaDAO;
import com.mycompany.tp4.pa2.entidades.Factura;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 *
 * @author aquin
 */
public class FacturaDAOImpl implements FacturaDAO{

    private static FacturaDAOImpl instancia; // se crea la instancia para que siempre se mantenga la lista.
    private final List<Factura> facturas;

    public FacturaDAOImpl() {
        this.facturas = new ArrayList<>();
    }
    
    // obtiene la instancia global
    public static FacturaDAOImpl getInstancia() {
        if (instancia == null) {
            instancia = new FacturaDAOImpl();
        }
        return instancia;
    }
    
    @Override
    public void addFactura(Factura factura) throws Exception {
        //busca si ya existe un factura con el mismo dni
        if(!facturas.stream().anyMatch(v -> v.getNroFactura()==factura.getNroFactura())){
            // si no existe lo agrega
            facturas.add(factura);
        }else
            throw new Exception("Ya existe una factura con el numero "+factura.getNroFactura()+" asignado.");
    }

    @Override
    public void removeFactura(long nroFactura) throws Exception {
        facturas.removeIf(v -> v.getNroFactura()==nroFactura);
    }

    @Override
    public Optional<Factura> findFactura(long nroFactura) throws Exception {
            return facturas.stream().filter(v -> v.getNroFactura()==nroFactura).findFirst();
    }

    @Override
    public List<Factura> findAllFacturas() {
        // no se hace return facturas porque se devolvería la referencia directa.
        return new ArrayList<>(facturas);
    }
    
    public void updateFactura(Factura factura) throws Exception {
        int indice = IntStream
                .range(0, facturas.size())
                .filter(i -> facturas.get(i).getNroFactura()==factura.getNroFactura())
                .findFirst()
                .orElseThrow(() -> new Exception("Factura con nro de factura \""+factura.getNroFactura()+"\" no se encuentra registrado."));
        facturas.set(indice, factura);
    }
    
}

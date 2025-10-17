package com.mycompany.tp4.pa2.entidades;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author aquin
 */
public class Factura {

    private LocalDate fecha;
    private long nroFactura;
    private char letra;
    private String dniCliente;
    private Set<Item> items;

    public Factura(String dniCliente) {
        this.dniCliente = dniCliente;
        //this.fecha = LocalDate.now();// le asignamos siempre la fecha y hora del momento
        //this.nroFactura = nroFactura;
        //this.letra = letra;
        this.items = new LinkedHashSet<>();
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }
    
    public void addItem(Articulo articulo, int cantidad){
        //se permite agregar más de una vez el mismo articulo.
        items.add(new Item(articulo,cantidad));
    }
    
    public void removeItem(String nombreArticulo) throws NullPointerException{
        // se utiliza un stream con un filtro para encontrar el item con el articulo a eliminar
        items.removeIf(e -> e.getArticulo().getNombre().equals(nombreArticulo));
    }
    
    public double informarTotalPagar() {
        // se utiliza stream para hacer la sumatoria del producto entre el precio del articulo y la cantidad, para cada item.
        return items.stream().mapToDouble(item -> item.getArticulo().getPrecio() * item.getCantidad()).sum();
    }

    public Set<Item> getItems() {
        return items;
    }

    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha() {
        this.fecha = LocalDate.now();
    }

    /**
     * @return the nroFactura
     */
    public long getNroFactura() {
        return nroFactura;
    }

    /**
     * @param nroFactura the nroFactura to set
     */
    public void setNroFactura(long nroFactura) {
        this.nroFactura = nroFactura;
    }

    /**
     * @return the letra
     */
    public char getLetra() {
        return letra;
    }

    /**
     * @param letra the letra to set
     */
    public void setLetra(char letra) {
        this.letra = letra;
    }

    @Override
    public String toString() {
        return "Factura{" + "fecha=" + fecha + ", nroFactura=" + nroFactura + ", letra=" + letra + ", dniCliente=" + dniCliente + ", items=" + items + '}';
    }
}

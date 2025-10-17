/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.DAOImpl;

import com.mycompany.tp4.pa2.DAO.ArticuloDAO;
import com.mycompany.tp4.pa2.entidades.Articulo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 *
 * @author aquin
 */
public class ArticuloDAOImpl implements ArticuloDAO{
    
    private static ArticuloDAOImpl instancia; // se crea la instancia para que siempre se mantenga la lista.
    private final List<Articulo> articulos;

    public ArticuloDAOImpl() {
        this.articulos = new ArrayList<>();
    }
    
    // obtiene la instancia global
    public static ArticuloDAOImpl getInstancia() {
        if (instancia == null) {
            instancia = new ArticuloDAOImpl();
        }
        return instancia;
    }
    
    @Override
    public void addArticulo(Articulo articulo) throws Exception {
        //busca si ya existe un articulo con el mismo dni
        if(!articulos.stream().anyMatch(v -> v.getNombre().equals(articulo.getNombre()))){
            // si no existe lo agrega
            articulos.add(articulo);
        }else
            throw new Exception("Ya existe un articulo con el nombre "+articulo.getNombre());
    }

    @Override
    public void removeArticulo(String nombreArticulo) throws Exception {
        articulos.removeIf(v -> v.getNombre().equals(nombreArticulo));
    }

    @Override
    public void updateArticulo(Articulo articulo) throws Exception {
        int indice = IntStream
                .range(0, articulos.size())
                .filter(i -> articulos.get(i).getNombre().equals(articulo.getNombre()))
                .findFirst()
                .orElseThrow(() -> new Exception("El articulo con nombre "+articulo.getNombre()+" no se encuentra registrado."));
        articulos.set(indice, articulo);
    }

    @Override
    public Optional<Articulo> findArticulo(String nombreArticulo) throws Exception {
            return articulos.stream().filter(v -> v.getNombre().equals(nombreArticulo)).findFirst();
    }

    @Override
    public List<Articulo> findAllArticulos() {
        // no se hace return articulos porque se devolvería la referencia directa.
        return new ArrayList<>(articulos);
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tp4.pa2.DAO;

import com.mycompany.tp4.pa2.entidades.Articulo;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author aquin
 */
public interface ArticuloDAO {
    void addArticulo(Articulo articulo) throws Exception;
    void removeArticulo(String nombreArticulo) throws Exception;
    void updateArticulo(Articulo articulo) throws Exception;
    Optional<Articulo> findArticulo(String nombreArticulo) throws Exception;
    List<Articulo> findAllArticulos();
}

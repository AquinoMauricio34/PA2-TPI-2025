/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Zona;
import com.mycompany.tpi2025.model.enums.EstadoSalud;
import com.mycompany.tpi2025.view.JPanels.ReporteView;
import jakarta.persistence.EntityManagerFactory;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aquin
 */
public class ReporteController {
    private ReporteView view;
    private GatoJpaController daoG;
    private ZonaJpaController daoZ;

    public ReporteController(ReporteView view, EntityManagerFactory emf) {
        this.view = view;
        this.daoG = new GatoJpaController(emf);
        this.daoZ = new ZonaJpaController(emf);
        cargarDatos();
    }

    public void cargarDatos() {
        List<Gato> gatos = daoG.findGatoEntities();
        List<Zona> zonas = daoZ.findZonaEntities();
        List<SimpleEntry<Long, Integer>> lista = contarGatosPorZona(gatos);
        view.reloadTable(lista);
        view.setGatosAdoptados("Gatos adoptados: "+contarGatosConUsuario(gatos));
        view.setGatosEsterilizados("Gatos esterilizados: "+contarGatosEsterilizados(gatos));
    }
    
    public List<SimpleEntry<Long, Integer>> contarGatosPorZona(List<Gato> gatos) {
        return gatos.stream()
            .filter(g -> g.getZona() != null)
            .collect(Collectors.groupingBy(
                g -> g.getZona().getId(),
                Collectors.summingInt(g -> 1)
            ))
            .entrySet()
            .stream()
            .map(e -> new SimpleEntry<>(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }

    public int contarGatosEsterilizados(List<Gato> gatos) {
        return (int) gatos.stream()
            .filter(g -> g.getEstadoSalud() == EstadoSalud.ESTERILIZADO)
            .count();
    }

        public int contarGatosConUsuario(List<Gato> gatos) {
        return (int) gatos.stream()
            .filter(g -> g.getUsuario() != null)
            .count();
    }

    
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025.Utils;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Zona;
import com.mycompany.tpi2025.model.enums.EstadoSalud;
import com.mycompany.tpi2025.view.JPanels.CrearGatoView;
import com.mycompany.tpi2025.view.VerGatoView;
import com.mycompany.tpi2025.view.ZonaView;
import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author aquin
 */
public class CrearGatoController {

    private CrearGatoView view;
    private GatoJpaController dao;
    private ZonaJpaController daoZ;
    private Zona zonaGato = null;
    private EntityManagerFactory emf;
    private Gato gato = null;

    public CrearGatoController(CrearGatoView view, String tipoAccion, EntityManagerFactory emf) {
        this(view, null, tipoAccion, emf);
    }

    public CrearGatoController(CrearGatoView view, Gato gato, String tipoAccion, EntityManagerFactory emf) {
        this.view = view;
        this.emf = emf;
        this.gato = gato;
        dao = new GatoJpaController(emf);
        daoZ = new ZonaJpaController(emf);
        view.setRegistrarText("Guardar");
        iniciarView();
        view.activarModificar(false);
        view.setSeleccionarZonaListener(l -> seleccion());
        view.setRegistrarListener(l -> accion(tipoAccion));
        view.setModificarListener(l -> {
            view.activarComponentes(true);
            view.activarRegistrar(true);
            view.activarModificar(false);
        });
        if ("MODIFICAR".equals(tipoAccion)) {
            view.activarComponentes(false);
            view.activarRegistrar(false);
            view.activarModificar(true);
        }
    }

    public void iniciarView() {
        view.setVisible(true);

    }

    private void accion(String tipoAccion) {
        switch (tipoAccion) {
            case "GUARDAR" ->
                guardar();
            case "MODIFICAR" ->
                modificar();
            default ->
                throw new AssertionError();
        }
    }

    public void setZonaGato(Zona zonaGato) {
        this.zonaGato = zonaGato;
    }

    private void seleccion() {
        ZonaView zview = new ZonaView();
        ZonaController controller = new ZonaController(zview, this, emf);
    }

    public void setZonaElegidaTexto(String texto) {
        view.setZonaElegida(texto);
    }

    private void guardar() {
        String nombre = view.getNombreGato().trim();
        String color = view.getColorGato().trim();
        String caracteristicas = view.getCaracteristicasGato().trim();
        String estadoSalud = view.getEstadoSalud();

        try {
            // Validar campos vacíos
            if (Utils.hayVacios(nombre, color, caracteristicas)) {
                throw new Exception("Todos los campos deben ser rellenados.");
            }

            // Validar zona seleccionada
            if (zonaGato == null) {
                throw new Exception("Debe seleccionar una zona.");
            }

            // Convertir estado de salud
            EstadoSalud estado = EstadoSalud.valueOf(estadoSalud);

            // Obtener zona completa
            Zona zona = daoZ.findZona(zonaGato.getId());
            if (zona == null) {
                throw new Exception("La zona seleccionada no existe.");
            }

            // Crear y guardar gato
            Gato gato = new Gato("", nombre, color, zona, caracteristicas, estado);
            gato.setHistorial();
            dao.create(gato);

            // Actualizar con QR
            gato.setQr("QR " + gato.getId());
            dao.edit(gato);

            // Mensaje de éxito
            view.mostrarInfoMensaje("Gato registrado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    private void modificar() {
        if (gato == null) {
            view.mostrarErrorMensaje("No hay gato seleccionado para modificar.");
            return;
        }

        String nombre = view.getNombreGato().trim();
        String color = view.getColorGato().trim();
        String caracteristicas = view.getCaracteristicasGato().trim();
        String estadoSalud = view.getEstadoSalud();

        try {
            // Validar campos vacíos
            if (Utils.hayVacios(nombre, color, caracteristicas, estadoSalud)) {
                throw new Exception("Todos los campos deben ser rellenados.");
            }

            // Validar zona seleccionada
            if (zonaGato == null) {
                throw new Exception("Debe seleccionar una zona.");
            }

            // Convertir y validar estado de salud
            EstadoSalud estado;
            try {
                estado = EstadoSalud.valueOf(estadoSalud);
            } catch (Exception e) {
                throw new Exception("Estado de salud inválido");
            }

            // Obtener zona completa
            Zona zona = daoZ.findZona(zonaGato.getId());
            if (zona == null) {
                throw new Exception("La zona seleccionada no existe.");
            }

            // Actualizar gato
            gato.setNombre(nombre);
            gato.setCaracteristicas(caracteristicas);
            gato.setColor(color);
            gato.setEstadoSalud(estado);
            gato.setZona(zona);

            dao.edit(gato);

            view.mostrarInfoMensaje("Gato modificado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    public void abrirSeleccion() {
        VerGatoView vview = new VerGatoView();
        VerGatoController controller = new VerGatoController(vview, emf);
        vview.habilitarEliminar(true);
        vview.setSeleccionListener(l -> {
            gato = controller.getGato();
            vview.dispose();
            cargarDatos();
            view.activarModificar(true);
        });
    }

    public void cargarDatos() {
        view.setNombreGato(gato.getNombre());
        view.setColorGato(gato.getColor());
        view.setCaracteristicasGato(gato.getCaracteristicas());
        view.setEstadoSalud(gato.getEstadoSalud());
        zonaGato = gato.getZona();
        try {
            view.setZonaElegida("Zona N° " + gato.getZona().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

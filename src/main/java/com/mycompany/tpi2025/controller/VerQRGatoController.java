/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.google.zxing.WriterException;
import com.mycompany.tpi2025.Utils;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.view.VerGatoView;
import com.mycompany.tpi2025.view.VerQRGatoView;
import jakarta.persistence.EntityManagerFactory;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author aquin
 */
public class VerQRGatoController {

    private VerQRGatoView view;

    public VerQRGatoController(EntityManagerFactory emf) {
        abrirSeleccion(emf);
    }

    private void abrirSeleccion(EntityManagerFactory emf) {
        VerGatoView vview = new VerGatoView();
        VerGatoController controller = new VerGatoController(vview, emf);
        vview.setSeleccionListener(l -> {
            mostrarQR(controller.getGato());
            vview.dispose();
        });
    }

    private void mostrarQR(Gato gato) {
        BufferedImage qrImage = null;
        try {
            qrImage = Utils.base64ToImage(Utils.generarQRBase64(gato.toString()));
        } catch (WriterException | IOException ex) {
            ex.printStackTrace();
        }

        view = new VerQRGatoView();
        view.setQRImage(qrImage);
        view.setCerrarListener(l -> cerrarView());
        view.setVisible(true);
        

    }

    private void cerrarView() {
        view.dispose();
        view = null;
    }

}

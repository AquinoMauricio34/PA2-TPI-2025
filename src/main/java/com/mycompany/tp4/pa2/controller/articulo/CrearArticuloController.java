/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.articulo;

import com.mycompany.tp4.pa2.DAOImpl.ArticuloDAOImpl;
import com.mycompany.tp4.pa2.entidades.Articulo;
import com.mycompany.tp4.pa2.entidades.Domiciliaria;
import com.mycompany.tp4.pa2.entidades.Herramienta;
import com.mycompany.tp4.pa2.entidades.Industrial;
import com.mycompany.tp4.pa2.entidades.TipoArticulo;
import com.mycompany.tp4.pa2.view.articulos.CrearArticuloView;
import java.awt.CardLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class CrearArticuloController {
    private CrearArticuloView crearA;
    private CardLayout cardLayout;
    private final ArticuloDAOImpl articuloDao = ArticuloDAOImpl.getInstancia();

    public CrearArticuloController(CrearArticuloView crearA) {
        this.crearA = crearA;
        cardLayout = new CardLayout();
        cargarItems();
        
        this.crearA.getTipoArticuloCombo().addActionListener(e -> seleccionarPanel());
        this.crearA.getAtrasBtn().addActionListener(e -> cerrarVentana());
        this.crearA.getGuardarArticuloBtn().addActionListener(e -> guardarArticulo());
    }

    private void seleccionarPanel() {
        String seleccionado = (String) crearA.getTipoArticuloCombo().getSelectedItem();
        crearA.setPanel(seleccionado);
    }

    private void cargarItems(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) crearA.getTipoArticuloCombo().getModel();
        // removing old data
        model.removeAllElements();
        
        String[] items = {"Electricidad Industrial","Electricidad Domiciliaria","Herramienta"};

        for (String item : items) {
            model.addElement(item);
        }

        // setting model with new data
        crearA.setTipoArticuloCombo(model);
    }

    private void cerrarVentana() {
        crearA.dispose();
    }

    private void guardarArticulo() {
        String seleccionado = (String) crearA.getTipoArticuloCombo().getSelectedItem();
        try {
            String nombre = crearA.getNombreArticuloTf().getText();
            double precio = (Double) crearA.getPrecioSp().getValue();
            if(nombre.equals("")) throw new Exception("Asignar nombre al artículo.");
            if(precio <= 0) throw new Exception("El precio del articulo debe ser mayor a cero.");
            switch (seleccionado) {
                case "Electricidad Industrial" -> {
                    double potencia = (Double) crearA.getIndu().getPotenciaMaximaSp().getValue();
                    double tempMin = (Double) crearA.getIndu().getTemperaturaMinSp().getValue();
                    double tempMax = (Double) crearA.getIndu().getTemperaturaMaxSp().getValue();
                    if(potencia <= 0) throw new Exception("El valor de la potencia debe ser mayor a 0.");
                    if(tempMin >= tempMax) throw new Exception("La temperaturas mínima y máxima deben ser distintas y la mínima menor que la máxima.");
                    articuloDao.addArticulo(new Industrial(tempMin,tempMax,potencia,nombre,precio,TipoArticulo.ELECTRICIDAD_INDUSTRIAL));
                }
                case "Electricidad Domiciliaria" -> {
                    double potencia = (Double) crearA.getDomi().getPotenciaMaximaSp().getValue();
                    if(potencia <= 0) throw new Exception("El valor de la potencia debe ser mayor a 0.");
                    articuloDao.addArticulo(new Domiciliaria(potencia,nombre,precio,TipoArticulo.ELECTRICIDAD_DOMICILIARIA));
                }
                case "Herramienta" -> {
                    String descripcion = crearA.getHerra().getDescripcionHerramienteTextA().getText();
                    if(descripcion.equals("")) throw new Exception("La descripción de la herramienta no puede estar vacía.");
                    articuloDao.addArticulo(new Herramienta(nombre,precio,TipoArticulo.HERRAMIENTA,descripcion));
                }
            }
            JOptionPane.showMessageDialog(crearA, "Articulo añadido.","Información",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(crearA, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
}

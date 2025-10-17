/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tp4.pa2;

import com.mycompany.tp4.pa2.DAOImpl.ArticuloDAOImpl;
import com.mycompany.tp4.pa2.DAOImpl.ClienteDAOImpl;
import com.mycompany.tp4.pa2.controller.MenuController;
import com.mycompany.tp4.pa2.entidades.Cliente;
import com.mycompany.tp4.pa2.entidades.Domiciliaria;
import com.mycompany.tp4.pa2.entidades.Herramienta;
import com.mycompany.tp4.pa2.entidades.Industrial;
import com.mycompany.tp4.pa2.entidades.TipoArticulo;
import com.mycompany.tp4.pa2.view.MenuView;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class Tp4Pa2 {

    public static void main(String[] args) {
        
        ClienteDAOImpl clienteDao = ClienteDAOImpl.getInstancia();
        try {
            clienteDao.addCliente(new Cliente("Mauricio","Aquino","44772888","Calle 135 Nº 2450","3764632667"));
            clienteDao.addCliente(new Cliente("Lucía","Fernández","40123877","Av. Roca Nº 1520","3764123456"));
            clienteDao.addCliente(new Cliente("Carlos","Gómez","38945211","Calle Mitre Nº 890","3764789654"));
            clienteDao.addCliente(new Cliente("Ana","Martínez","37256984","Av. Uruguay Nº 2500","3764951234"));
            clienteDao.addCliente(new Cliente("Sofía","Ramírez","42856932","Calle Córdoba Nº 1200","3764223344"));
            clienteDao.addCliente(new Cliente("Jorge","Pereyra","35478965","Av. López y Planes Nº 3320","3764887766"));
            clienteDao.addCliente(new Cliente("Martina","López","41589632","Calle San Martín Nº 765","3764998877"));
            clienteDao.addCliente(new Cliente("Federico","Silva","39658741","Av. Quaranta Nº 4300","3764665544"));
            clienteDao.addCliente(new Cliente("Valentina","Sosa","42123589","Calle Bolívar Nº 310","3764332211"));
            clienteDao.addCliente(new Cliente("Ricardo","Castro","36895412","Av. Tambor de Tacuarí Nº 980","3764556677"));
            clienteDao.addCliente(new Cliente("Camila","Méndez","40784563","Calle Ituzaingó Nº 145","3764225566"));
            clienteDao.addCliente(new Cliente("Diego","Romero","38214785","Av. Francisco de Haro Nº 3700","3764778899"));
            clienteDao.addCliente(new Cliente("Mariana","Ortiz","41896523","Calle Colón Nº 670","3764443322"));
            clienteDao.addCliente(new Cliente("Hernán","Díaz","36547895","Av. Lavalle Nº 520","3764334455"));
            clienteDao.addCliente(new Cliente("Paula","Acosta","42589631","Calle Entre Ríos Nº 890","3764667788"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR CARGA DE CLIENTES INICIALES MENU.");
        }
        
        ArticuloDAOImpl articuloDao = ArticuloDAOImpl.getInstancia();
        try {
            articuloDao.addArticulo(new Industrial(-2, 80, 50, "Batería", 3000.0, TipoArticulo.ELECTRICIDAD_INDUSTRIAL));
            articuloDao.addArticulo(new Industrial(0, 100, 75, "Transformador trifásico", 12500.0, TipoArticulo.ELECTRICIDAD_INDUSTRIAL));
            articuloDao.addArticulo(new Industrial(-10, 60, 40, "Generador diésel", 45000.0, TipoArticulo.ELECTRICIDAD_INDUSTRIAL));
            articuloDao.addArticulo(new Industrial(-5, 90, 55, "Motor eléctrico", 9800.0, TipoArticulo.ELECTRICIDAD_INDUSTRIAL));
            articuloDao.addArticulo(new Industrial(-15, 70, 65, "Compresor industrial", 18700.0, TipoArticulo.ELECTRICIDAD_INDUSTRIAL));

            articuloDao.addArticulo(new Domiciliaria(10, "Lámpara LED", 1500.0, TipoArticulo.ELECTRICIDAD_DOMICILIARIA));
            articuloDao.addArticulo(new Domiciliaria(5, "Enchufe doble", 800.0, TipoArticulo.ELECTRICIDAD_DOMICILIARIA));
            articuloDao.addArticulo(new Domiciliaria(3, "Interruptor de pared", 600.0, TipoArticulo.ELECTRICIDAD_DOMICILIARIA));
            articuloDao.addArticulo(new Domiciliaria(8, "Ventilador de techo", 7200.0, TipoArticulo.ELECTRICIDAD_DOMICILIARIA));
            articuloDao.addArticulo(new Domiciliaria(4, "Cargador universal", 2500.0, TipoArticulo.ELECTRICIDAD_DOMICILIARIA));

            articuloDao.addArticulo(new Herramienta("Taladro percutor", 8500.0, TipoArticulo.HERRAMIENTA, "Taladro con función de percusión para concreto y madera"));
            articuloDao.addArticulo(new Herramienta("Destornillador eléctrico", 4300.0, TipoArticulo.HERRAMIENTA, "Recargable, con luz LED incorporada"));
            articuloDao.addArticulo(new Herramienta("Sierra circular", 12500.0, TipoArticulo.HERRAMIENTA, "Hoja de 7 pulgadas, ideal para cortes de precisión"));
            articuloDao.addArticulo(new Herramienta("Llave inglesa", 2200.0, TipoArticulo.HERRAMIENTA, "Ajustable, fabricada en acero al cromo-vanadio"));
            articuloDao.addArticulo(new Herramienta("Martillo carpintero", 1800.0, TipoArticulo.HERRAMIENTA, "Cabeza de acero forjado con mango de fibra de vidrio"));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR CARGA DE ARTICULOS INICIALES MENU.");
        }

        
        //se crea la vista
        MenuView menuv = new MenuView();
        //se crea el controlador
        MenuController menuc = new MenuController(menuv);
        menuv.setLocationRelativeTo(null);
        menuv.setVisible(true);
    }
}

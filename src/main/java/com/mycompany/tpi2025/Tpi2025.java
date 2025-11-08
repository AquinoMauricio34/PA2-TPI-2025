/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tpi2025;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.controller.CrearUsuarioController;
import com.mycompany.tpi2025.controller.LoginController;
import com.mycompany.tpi2025.model.Administrador;
import com.mycompany.tpi2025.model.Diagnostico;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Tratamiento;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.model.Veterinario;
import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.model.enums.EstadoSalud;
import com.mycompany.tpi2025.view.CrearUsuarioView;
import com.mycompany.tpi2025.view.LoginView;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author aquin
 */
public class Tpi2025 {

    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tpi25PU");
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println("error con el look and feel");
        }
        
        
        
        
        
        
        
        
//        UsuarioJpaController dao = new UsuarioJpaController(JPAUtil.getEmf());
//        GatoJpaController daoG = new GatoJpaController(JPAUtil.getEmf());
//        
//
//        Usuario usu = new Administrador("Mauricio Aquino", "calculonumerico", "376632667", "mauaquinooo");
//        Usuario usu4 = new Administrador("Carina Pastori", "contra123", "3764150925", "elusuario");
//        Usuario usu1 = new Veterinario("Mateo Veteri", "vetericontra", "3764112233", "veterinario1");
//        Usuario usu2 = new Voluntario("Tomas Voll", "volcontra", "3764223344", "volluntario2");
//        Usuario usu3 = new Familia("Aquino Fami", "famicontra", "3764334455", "familia3");
//        
//        
//        Tratamiento t = new Tratamiento();
//        t.setDescripcion("Desparasitación interna");
//        t.setFecha_inicio(new Date());
//        t.setFecha_fin(Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        t.setAbandono_tratamiento(false);
//        
//
//        Diagnostico d = new Diagnostico();
//        d.setDescripcion("Control rutinario, sin anomalías graves");
//        d.setDiagnostico("Salud óptima");
//        d.setFecha_diagnostico(LocalDate.now());
//        d.setTratamiento(t);
//
//        Gato g1 = new Gato("QR1", "michi1", "morado", "primerazona", "morado, manchas blancas y negras", EstadoSalud.SANO);
//        g1.setHistorial();
//        //se le agrega el historial al que pertenece (JPA)
//        d.setHistorial(g1.getHistorial());
//        g1.getHistorial().addDiagnostico(d);
//        
//
//        try {
//            dao.create(usu);
//            dao.create(usu1);
//            dao.create(usu2);
//            dao.create(usu3);
//            dao.create(usu4);
//            daoG.create(g1);
//        } catch (Exception ex) {
//            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//            ex.printStackTrace();
//        }


        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        Gato g = null;
//        
//        try {
//            g = daoG.findGato(1);
//        } catch (Exception ex) {
//            System.out.println("OBTENER GATO ERRORRRRRRRR");
//            ex.printStackTrace();
//        }
//        
//        System.out.println("BANDERA 1");
//        
//        Tratamiento t = new Tratamiento();
//        t.setDescripcion("nnn Desparasitación interna");
//        t.setFecha_inicio(new Date());
//        t.setFecha_fin(Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        t.setAbandono_tratamiento(false);
//
//        Diagnostico d = new Diagnostico();
//        d.setDescripcion("nnn Control rutinario, sin anomalías graves");
//        d.setDiagnostico("Salud óptima");
//        d.setFecha_diagnostico(LocalDate.now());
//        d.setTratamiento(t);
//        d.setHistorial(g.getHistorial());
//        
//        
//        // Agregar el nuevo diagnóstico al historial del gato
//        g.getHistorial().getDiagnosticos().add(d);
//        
//        try {
//            daoG.edit(g);
//        } catch (Exception ex) {
//            System.out.println("EDITAR GATO ERRORRRRRRRR");
//            ex.printStackTrace();
//        }

        login(emf);
    }

    public static void login(EntityManagerFactory emf) {
        LoginView lView = new LoginView();
        new LoginController(lView, emf);
    }
    
    
}

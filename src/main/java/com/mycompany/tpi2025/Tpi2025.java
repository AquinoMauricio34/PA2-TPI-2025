/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tpi2025;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025.DAOImpl.TratamientoJpaController;
import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025.controller.LoginController;
import com.mycompany.tpi2025.model.Administrador;
import com.mycompany.tpi2025.model.Diagnostico;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.model.Postulacion;
import com.mycompany.tpi2025.model.Tratamiento;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.model.Veterinario;
import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.model.Zona;
import com.mycompany.tpi2025.model.enums.EstadoSalud;
import com.mycompany.tpi2025.view.LoginView;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;

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
        
        
        
        
        
        
        
        UsuarioJpaController dao = new UsuarioJpaController(emf);
        GatoJpaController daoG = new GatoJpaController(emf);
        ZonaJpaController daoZ = new ZonaJpaController(emf);
        TratamientoJpaController daoT = new TratamientoJpaController(emf);
        
        Zona z1 = new Zona("se encuentra el la localizacion 1");
        Zona z2 = new Zona("se encuentra el la localizacion 22");
        Zona z3 = new Zona("se encuentra el la localizacion 333");
        
        try {
            
            daoZ.create(z1);
            daoZ.create(z2);
            daoZ.create(z3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Usuario admin1 = new Administrador("Mauricio Aquino", "a", "3764632667", "a");
        Usuario admin2 = new Administrador("Carlos Ruiz", "securepass", "3764876543", "carlosr");
        Usuario admin3 = new Administrador("Ana Martinez", "anaadmin", "3764765432", "anamtz");
        Usuario admin4 = new Administrador("Pedro Lopez", "pedro2024", "3764654321", "pedrolpz");
        Usuario vet1 = new Veterinario("Dra. Sofia Ramirez", "vetanimal", "3764123456", "sofiveter");
        Usuario vet2 = new Veterinario("Dr. Jorge Silva", "jorgevet", "3764234567", "jorgesilva");
        Usuario vet3 = new Veterinario("Dra. Maria Torres", "mvetcare", "3764345678", "mariatorres");
        Usuario vet4 = new Veterinario("Dr. Luis Fernandez", "luisvet", "3764456789", "luisfern");
        Usuario vol1 = new Voluntario("Juan Perez", "juanvol", "3764567890", "juanperezv");
        Usuario vol2 = new Voluntario("Maria Garcia", "mvolunteer", "3764678901", "mariagarc");
        Usuario vol3 = new Voluntario("Roberto Diaz", "robvol", "3764789012", "robertodz");
        Usuario vol4 = new Voluntario("Elena Castro", "elenavol", "3764890123", "elenacast");
        Usuario fam1 = new Familia("Familia Rodriguez", "famrodri", "3764901234", "famrodriguez");
        Usuario fam2 = new Familia("Familia Herrera", "famherrera", "3764012345", "famherrera");
        Usuario fam3 = new Familia("Familia Morales", "fammorales", "3764123450", "fammorales");
        Usuario fam4 = new Familia("Familia Rios", "famrios", "3764234501", "famrios");
        Hogar hogar1 = new Hogar("Hogar San José", "hogsanjose", "3764123456", "hogsanjose",false);
        Hogar hogar2 = new Hogar("Hogar Santa María", "hogstmaria", "3764234567", "hogstmaria",false);
        Hogar hogar3 = new Hogar("Hogar Esperanza", "hogesperanza", "3764345678", "hogesperanza",false);
        Hogar hogar4 = new Hogar("Hogar Nuevo Amanecer", "hogamanecer", "3764456789", "hogamanecer",false);
        
        Tratamiento t = new Tratamiento();
        t.setDescripcion("Desparasitación interna");
        t.setFecha_inicio(LocalDate.now().toString());
        t.setFecha_fin(LocalDate.now().plusDays(3).toString());
        t.setAbandono_tratamiento(false);
        
        try {
            dao.create(admin1);
            dao.create(admin2);
            dao.create(admin3);
            dao.create(admin4);
            dao.create(vet1);
            dao.create(vet2);
            dao.create(vet3);
            dao.create(vet4);
            dao.create(vol1);
            dao.create(vol2);
            dao.create(vol3);
            dao.create(vol4);
            dao.create(fam1);
            dao.create(fam2);
            dao.create(fam3);
            dao.create(fam4);
            dao.create(hogar1);
            dao.create(hogar2);
            dao.create(hogar3);
            dao.create(hogar4);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        

        Diagnostico d = new Diagnostico();
        d.setDescripcion("Control rutinario, sin anomalías graves");
        d.setDiagnostico("Salud óptima");
        d.setFecha_diagnostico(LocalDate.now());
        d.addTratamiento(t);
        
        Diagnostico d2 = new Diagnostico();
        d2.setDescripcion("Sangrado de nariz orificio derecho");
        d2.setDiagnostico("Sangre");
        d2.setFecha_diagnostico(LocalDate.now());
        
        Zona zz1 = daoZ.findZona(2);
        Gato g = new Gato("QR1", "michi1", "morado", null, "morado, manchas blancas y negras", EstadoSalud.SANO);
        g.setHistorial();
        g.getHistorial().addDiagnostico(d);
        g.getHistorial().addDiagnostico(d2);
        g.setZona(zz1);
        
        Gato g2 = new Gato("QR2", "luna", "blanco", null, "blanco puro", EstadoSalud.ESTERILIZADO);
        Gato g3 = new Gato("QR3", "simba", "naranja", null, "atigrado naranja", EstadoSalud.ENFERMO);
        Gato g4 = new Gato("QR4", "shadow", "negro", null, "negro azabache", EstadoSalud.SANO);
        Gato g5 = new Gato("QR5", "whiskers", "gris", null, "gris con rayas", EstadoSalud.EN_TRATAMIENTO);
        Gato g6 = new Gato("QR6", "mittens", "blanco", null, "blanco con patas negras", EstadoSalud.ESTERILIZADO);
        g6.setHistorial();
        Gato g7 = new Gato("QR7", "oreo", "negro", null, "negro y blanco", EstadoSalud.SANO);
        Gato g8 = new Gato("QR8", "ginger", "naranja", null, "naranja claro", EstadoSalud.ENFERMO);
        Gato g9 = new Gato("QR9", "smokey", "gris", null, "gris oscuro", EstadoSalud.EN_TRATAMIENTO);
        Gato g10 = new Gato("QR10", "coco", "marrón", null, "marrón chocolate", EstadoSalud.SANO);
        Gato g11 = new Gato("QR11", "snowball", "blanco", null, "blanco esponjoso", EstadoSalud.ESTERILIZADO);
        Gato g12 = new Gato("QR12", "midnight", "negro", null, "negro intenso", EstadoSalud.SANO);
        Gato g13 = new Gato("QR13", "calico", "tricolor", null, "blanco, negro y naranja", EstadoSalud.ENFERMO);
        Gato g14 = new Gato("QR14", "tiger", "atigrado", null, "rayas grises y negras", EstadoSalud.EN_TRATAMIENTO);
        Gato g15 = new Gato("QR15", "peaches", "crema", null, "color crema suave", EstadoSalud.SANO);
        Gato g16 = new Gato("QR16", "salem", "negro", null, "negro brillante", EstadoSalud.ESTERILIZADO);
        Gato g17 = new Gato("QR17", "butterscotch", "naranja", null, "naranja intenso", EstadoSalud.SANO);
        Gato g18 = new Gato("QR18", "misty", "gris", null, "gris claro", EstadoSalud.ENFERMO);
        Gato g19 = new Gato("QR19", "patches", "tricolor", null, "manchas blancas, negras y grises", EstadoSalud.EN_TRATAMIENTO);
        Gato g20 = new Gato("QR20", "rascal", "blanco", null, "blanco con orejas negras", EstadoSalud.SANO);
        
        
        

        try {
            daoG.create(g);
            daoG.create(g2);
            daoG.create(g3);
            daoG.create(g4);
            daoG.create(g5);
            daoG.create(g6);
            daoG.create(g7);
            daoG.create(g8);
            daoG.create(g9);
            daoG.create(g10);
            daoG.create(g11);
            daoG.create(g12);
            daoG.create(g13);
            daoG.create(g14);
            daoG.create(g15);
            daoG.create(g16);
            daoG.create(g17);
            daoG.create(g18);
            daoG.create(g19);
            daoG.create(g20);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        PostulacionJpaController daoP = new PostulacionJpaController(emf);
        
        Postulacion p = new Postulacion("b", 8);
        Postulacion p1 = new Postulacion("c", 8);
        Postulacion p2 = new Postulacion("d", 8);
        Postulacion p3 = new Postulacion("hogsanjose", 8);
        Postulacion p4 = new Postulacion("f", 8);
        Postulacion p5 = new Postulacion("g", 3);
        Postulacion p6 = new Postulacion("famrodriguez", 3);
        Postulacion p7 = new Postulacion("i", 13);
        Postulacion p8 = new Postulacion("j", 9);
        
        
        try {
            daoP.create(p);
            daoP.create(p1);
            daoP.create(p2);
            daoP.create(p3);
            daoP.create(p4);
            daoP.create(p5);
            daoP.create(p6);
            daoP.create(p7);
            daoP.create(p8);
        } catch (Exception e) {
        }
        
        
        
        
        
        
        
        
        
        
        
        
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

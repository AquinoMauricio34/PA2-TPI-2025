/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025;

import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author aquin
 */
public class JPAUtil {

    // se cierra el emf (IMPORTANTE)
    public static void close(EntityManagerFactory emf) {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

}

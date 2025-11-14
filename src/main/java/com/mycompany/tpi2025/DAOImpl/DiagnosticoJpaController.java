/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.DAOImpl;

import com.mycompany.tpi2025.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025.model.Diagnostico;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.mycompany.tpi2025.model.HistorialGato;
import com.mycompany.tpi2025.model.Tratamiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aquin
 */
public class DiagnosticoJpaController implements Serializable {

    public DiagnosticoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Diagnostico diagnostico) {
        if (diagnostico.getTratamientos() == null) {
            diagnostico.setTratamientos(new ArrayList<Tratamiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialGato historial = diagnostico.getHistorial();
            if (historial != null) {
                historial = em.getReference(historial.getClass(), historial.getId());
                diagnostico.setHistorial(historial);
            }
            List<Tratamiento> attachedTratamientos = new ArrayList<Tratamiento>();
            for (Tratamiento tratamientosTratamientoToAttach : diagnostico.getTratamientos()) {
                tratamientosTratamientoToAttach = em.getReference(tratamientosTratamientoToAttach.getClass(), tratamientosTratamientoToAttach.getId());
                attachedTratamientos.add(tratamientosTratamientoToAttach);
            }
            diagnostico.setTratamientos(attachedTratamientos);
            em.persist(diagnostico);
            if (historial != null) {
                historial.getDiagnosticos().add(diagnostico);
                historial = em.merge(historial);
            }
            for (Tratamiento tratamientosTratamiento : diagnostico.getTratamientos()) {
                Diagnostico oldDiagnosticoOfTratamientosTratamiento = tratamientosTratamiento.getDiagnostico();
                tratamientosTratamiento.setDiagnostico(diagnostico);
                tratamientosTratamiento = em.merge(tratamientosTratamiento);
                if (oldDiagnosticoOfTratamientosTratamiento != null) {
                    oldDiagnosticoOfTratamientosTratamiento.getTratamientos().remove(tratamientosTratamiento);
                    oldDiagnosticoOfTratamientosTratamiento = em.merge(oldDiagnosticoOfTratamientosTratamiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Diagnostico diagnostico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Diagnostico persistentDiagnostico = em.find(Diagnostico.class, diagnostico.getId());
            HistorialGato historialOld = persistentDiagnostico.getHistorial();
            HistorialGato historialNew = diagnostico.getHistorial();
            List<Tratamiento> tratamientosOld = persistentDiagnostico.getTratamientos();
            List<Tratamiento> tratamientosNew = diagnostico.getTratamientos();
            if (historialNew != null) {
                historialNew = em.getReference(historialNew.getClass(), historialNew.getId());
                diagnostico.setHistorial(historialNew);
            }
            List<Tratamiento> attachedTratamientosNew = new ArrayList<Tratamiento>();
            for (Tratamiento tratamientosNewTratamientoToAttach : tratamientosNew) {
                tratamientosNewTratamientoToAttach = em.getReference(tratamientosNewTratamientoToAttach.getClass(), tratamientosNewTratamientoToAttach.getId());
                attachedTratamientosNew.add(tratamientosNewTratamientoToAttach);
            }
            tratamientosNew = attachedTratamientosNew;
            diagnostico.setTratamientos(tratamientosNew);
            diagnostico = em.merge(diagnostico);
            if (historialOld != null && !historialOld.equals(historialNew)) {
                historialOld.getDiagnosticos().remove(diagnostico);
                historialOld = em.merge(historialOld);
            }
            if (historialNew != null && !historialNew.equals(historialOld)) {
                historialNew.getDiagnosticos().add(diagnostico);
                historialNew = em.merge(historialNew);
            }
            for (Tratamiento tratamientosOldTratamiento : tratamientosOld) {
                if (!tratamientosNew.contains(tratamientosOldTratamiento)) {
                    tratamientosOldTratamiento.setDiagnostico(null);
                    tratamientosOldTratamiento = em.merge(tratamientosOldTratamiento);
                }
            }
            for (Tratamiento tratamientosNewTratamiento : tratamientosNew) {
                if (!tratamientosOld.contains(tratamientosNewTratamiento)) {
                    Diagnostico oldDiagnosticoOfTratamientosNewTratamiento = tratamientosNewTratamiento.getDiagnostico();
                    tratamientosNewTratamiento.setDiagnostico(diagnostico);
                    tratamientosNewTratamiento = em.merge(tratamientosNewTratamiento);
                    if (oldDiagnosticoOfTratamientosNewTratamiento != null && !oldDiagnosticoOfTratamientosNewTratamiento.equals(diagnostico)) {
                        oldDiagnosticoOfTratamientosNewTratamiento.getTratamientos().remove(tratamientosNewTratamiento);
                        oldDiagnosticoOfTratamientosNewTratamiento = em.merge(oldDiagnosticoOfTratamientosNewTratamiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = diagnostico.getId();
                if (findDiagnostico(id) == null) {
                    throw new NonexistentEntityException("The diagnostico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Diagnostico diagnostico;
            try {
                diagnostico = em.getReference(Diagnostico.class, id);
                diagnostico.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The diagnostico with id " + id + " no longer exists.", enfe);
            }
            HistorialGato historial = diagnostico.getHistorial();
            if (historial != null) {
                historial.getDiagnosticos().remove(diagnostico);
                historial = em.merge(historial);
            }
            List<Tratamiento> tratamientos = diagnostico.getTratamientos();
            for (Tratamiento tratamientosTratamiento : tratamientos) {
                tratamientosTratamiento.setDiagnostico(null);
                tratamientosTratamiento = em.merge(tratamientosTratamiento);
            }
            em.remove(diagnostico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Diagnostico> findDiagnosticoEntities() {
        return findDiagnosticoEntities(true, -1, -1);
    }

    public List<Diagnostico> findDiagnosticoEntities(int maxResults, int firstResult) {
        return findDiagnosticoEntities(false, maxResults, firstResult);
    }

    private List<Diagnostico> findDiagnosticoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Diagnostico.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Diagnostico findDiagnostico(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Diagnostico.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiagnosticoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Diagnostico> rt = cq.from(Diagnostico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

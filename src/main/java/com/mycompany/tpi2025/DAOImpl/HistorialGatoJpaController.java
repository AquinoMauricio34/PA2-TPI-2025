/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.DAOImpl;

import com.mycompany.tpi2025.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025.model.Diagnostico;
import com.mycompany.tpi2025.model.HistorialGato;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aquin
 */
public class HistorialGatoJpaController implements Serializable {

    public HistorialGatoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialGato historialGato) {
        if (historialGato.getDiagnosticos() == null) {
            historialGato.setDiagnosticos(new ArrayList<Diagnostico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Diagnostico> attachedDiagnosticos = new ArrayList<Diagnostico>();
            for (Diagnostico diagnosticosDiagnosticoToAttach : historialGato.getDiagnosticos()) {
                diagnosticosDiagnosticoToAttach = em.getReference(diagnosticosDiagnosticoToAttach.getClass(), diagnosticosDiagnosticoToAttach.getId());
                attachedDiagnosticos.add(diagnosticosDiagnosticoToAttach);
            }
            historialGato.setDiagnosticos(attachedDiagnosticos);
            em.persist(historialGato);
            for (Diagnostico diagnosticosDiagnostico : historialGato.getDiagnosticos()) {
                HistorialGato oldHistorialOfDiagnosticosDiagnostico = diagnosticosDiagnostico.getHistorial();
                diagnosticosDiagnostico.setHistorial(historialGato);
                diagnosticosDiagnostico = em.merge(diagnosticosDiagnostico);
                if (oldHistorialOfDiagnosticosDiagnostico != null) {
                    oldHistorialOfDiagnosticosDiagnostico.getDiagnosticos().remove(diagnosticosDiagnostico);
                    oldHistorialOfDiagnosticosDiagnostico = em.merge(oldHistorialOfDiagnosticosDiagnostico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistorialGato historialGato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialGato persistentHistorialGato = em.find(HistorialGato.class, historialGato.getId());
            List<Diagnostico> diagnosticosOld = persistentHistorialGato.getDiagnosticos();
            List<Diagnostico> diagnosticosNew = historialGato.getDiagnosticos();
            List<Diagnostico> attachedDiagnosticosNew = new ArrayList<Diagnostico>();
            for (Diagnostico diagnosticosNewDiagnosticoToAttach : diagnosticosNew) {
                diagnosticosNewDiagnosticoToAttach = em.getReference(diagnosticosNewDiagnosticoToAttach.getClass(), diagnosticosNewDiagnosticoToAttach.getId());
                attachedDiagnosticosNew.add(diagnosticosNewDiagnosticoToAttach);
            }
            diagnosticosNew = attachedDiagnosticosNew;
            historialGato.setDiagnosticos(diagnosticosNew);
            historialGato = em.merge(historialGato);
            for (Diagnostico diagnosticosOldDiagnostico : diagnosticosOld) {
                if (!diagnosticosNew.contains(diagnosticosOldDiagnostico)) {
                    diagnosticosOldDiagnostico.setHistorial(null);
                    diagnosticosOldDiagnostico = em.merge(diagnosticosOldDiagnostico);
                }
            }
            for (Diagnostico diagnosticosNewDiagnostico : diagnosticosNew) {
                if (!diagnosticosOld.contains(diagnosticosNewDiagnostico)) {
                    HistorialGato oldHistorialOfDiagnosticosNewDiagnostico = diagnosticosNewDiagnostico.getHistorial();
                    diagnosticosNewDiagnostico.setHistorial(historialGato);
                    diagnosticosNewDiagnostico = em.merge(diagnosticosNewDiagnostico);
                    if (oldHistorialOfDiagnosticosNewDiagnostico != null && !oldHistorialOfDiagnosticosNewDiagnostico.equals(historialGato)) {
                        oldHistorialOfDiagnosticosNewDiagnostico.getDiagnosticos().remove(diagnosticosNewDiagnostico);
                        oldHistorialOfDiagnosticosNewDiagnostico = em.merge(oldHistorialOfDiagnosticosNewDiagnostico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = historialGato.getId();
                if (findHistorialGato(id) == null) {
                    throw new NonexistentEntityException("The historialGato with id " + id + " no longer exists.");
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
            HistorialGato historialGato;
            try {
                historialGato = em.getReference(HistorialGato.class, id);
                historialGato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialGato with id " + id + " no longer exists.", enfe);
            }
            List<Diagnostico> diagnosticos = historialGato.getDiagnosticos();
            for (Diagnostico diagnosticosDiagnostico : diagnosticos) {
                diagnosticosDiagnostico.setHistorial(null);
                diagnosticosDiagnostico = em.merge(diagnosticosDiagnostico);
            }
            em.remove(historialGato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistorialGato> findHistorialGatoEntities() {
        return findHistorialGatoEntities(true, -1, -1);
    }

    public List<HistorialGato> findHistorialGatoEntities(int maxResults, int firstResult) {
        return findHistorialGatoEntities(false, maxResults, firstResult);
    }

    private List<HistorialGato> findHistorialGatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialGato.class));
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

    public HistorialGato findHistorialGato(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialGato.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialGatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialGato> rt = cq.from(HistorialGato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

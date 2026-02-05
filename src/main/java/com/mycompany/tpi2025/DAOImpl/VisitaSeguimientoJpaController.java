/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.DAOImpl;

import com.mycompany.tpi2025.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025.model.VisitaSeguimiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VisitaSeguimientoJpaController implements Serializable {

    public VisitaSeguimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VisitaSeguimiento visitaSeguimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(visitaSeguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VisitaSeguimiento visitaSeguimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            visitaSeguimiento = em.merge(visitaSeguimiento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = visitaSeguimiento.getId();
                if (findVisitaSeguimiento(id) == null) {
                    throw new NonexistentEntityException("The visitaSeguimiento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VisitaSeguimiento visitaSeguimiento;
            try {
                visitaSeguimiento = em.getReference(VisitaSeguimiento.class, id);
                visitaSeguimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visitaSeguimiento with id " + id + " no longer exists.", enfe);
            }
            em.remove(visitaSeguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VisitaSeguimiento> findVisitaSeguimientoEntities() {
        return findVisitaSeguimientoEntities(true, -1, -1);
    }

    public List<VisitaSeguimiento> findVisitaSeguimientoEntities(int maxResults, int firstResult) {
        return findVisitaSeguimientoEntities(false, maxResults, firstResult);
    }

    private List<VisitaSeguimiento> findVisitaSeguimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VisitaSeguimiento.class));
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

    public VisitaSeguimiento findVisitaSeguimiento(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VisitaSeguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisitaSeguimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VisitaSeguimiento> rt = cq.from(VisitaSeguimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

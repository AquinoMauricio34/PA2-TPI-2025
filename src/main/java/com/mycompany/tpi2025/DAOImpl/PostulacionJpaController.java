/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.DAOImpl;

import com.mycompany.tpi2025.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025.model.Postulacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author aquin
 */
public class PostulacionJpaController implements Serializable {

    public PostulacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Postulacion postulacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(postulacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Postulacion postulacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            postulacion = em.merge(postulacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = postulacion.getId();
                if (findPostulacion(id) == null) {
                    throw new NonexistentEntityException("The postulacion with id " + id + " no longer exists.");
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
            Postulacion postulacion;
            try {
                postulacion = em.getReference(Postulacion.class, id);
                postulacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The postulacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(postulacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Postulacion> findPostulacionEntities() {
        return findPostulacionEntities(true, -1, -1);
    }

    public List<Postulacion> findPostulacionEntities(int maxResults, int firstResult) {
        return findPostulacionEntities(false, maxResults, firstResult);
    }

    private List<Postulacion> findPostulacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Postulacion.class));
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

    public Postulacion findPostulacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Postulacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPostulacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Postulacion> rt = cq.from(Postulacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Postulacion> findPostulacionesByPostulante(String postulante) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Postulacion> query = em.createQuery(
                    "SELECT p FROM Postulacion p WHERE p.postulante = :postulante",
                    Postulacion.class
            );
            query.setParameter("postulante", postulante);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Postulacion> findPostulacionesByIdGato(long idGato) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Postulacion> query = em.createQuery(
                    "SELECT p FROM Postulacion p WHERE p.idGato = :idGato",
                    Postulacion.class
            );
            query.setParameter("idGato", idGato);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}

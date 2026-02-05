/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.DAOImpl;

import com.mycompany.tpi2025.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025.DAOImpl.exceptions.PreexistingEntityException;
import com.mycompany.tpi2025.model.Hogar;
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
public class HogarJpaController implements Serializable {

    public HogarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hogar hogar) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(hogar);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHogar(hogar.getNombreUsuario()) != null) {
                throw new PreexistingEntityException("Hogar " + hogar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hogar hogar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            hogar = em.merge(hogar);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = hogar.getNombreUsuario();
                if (findHogar(id) == null) {
                    throw new NonexistentEntityException("The hogar with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hogar hogar;
            try {
                hogar = em.getReference(Hogar.class, id);
                hogar.getNombreUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hogar with id " + id + " no longer exists.", enfe);
            }
            em.remove(hogar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hogar> findHogarEntities() {
        return findHogarEntities(true, -1, -1);
    }

    public List<Hogar> findHogarEntities(int maxResults, int firstResult) {
        return findHogarEntities(false, maxResults, firstResult);
    }

    private List<Hogar> findHogarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hogar.class));
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

    public Hogar findHogar(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hogar.class, id);
        } finally {
            em.close();
        }
    }

    public int getHogarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hogar> rt = cq.from(Hogar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

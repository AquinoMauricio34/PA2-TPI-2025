/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.DAOImpl;

import com.mycompany.tpi2025.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025.DAOImpl.exceptions.PreexistingEntityException;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Gato;
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
public class FamiliaJpaController implements Serializable {

    public FamiliaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Familia familia) throws PreexistingEntityException, Exception {
        if (familia.getGatos() == null) {
            familia.setGatos(new ArrayList<Gato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Gato> attachedGatos = new ArrayList<Gato>();
            for (Gato gatosGatoToAttach : familia.getGatos()) {
                gatosGatoToAttach = em.getReference(gatosGatoToAttach.getClass(), gatosGatoToAttach.getId());
                attachedGatos.add(gatosGatoToAttach);
            }
            familia.setGatos(attachedGatos);
            em.persist(familia);
            for (Gato gatosGato : familia.getGatos()) {
                com.mycompany.tpi2025.model.Familia oldUsuarioOfGatosGato = (Familia) gatosGato.getUsuario();
                gatosGato.setUsuario(familia);
                gatosGato = em.merge(gatosGato);
                if (oldUsuarioOfGatosGato != null) {
                    oldUsuarioOfGatosGato.getGatos().remove(gatosGato);
                    oldUsuarioOfGatosGato = em.merge(oldUsuarioOfGatosGato);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFamilia(familia.getNombreUsuario()) != null) {
                throw new PreexistingEntityException("Familia " + familia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Familia familia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familia persistentFamilia = em.find(Familia.class, familia.getNombreUsuario());
            List<Gato> gatosOld = persistentFamilia.getGatos();
            List<Gato> gatosNew = familia.getGatos();
            List<Gato> attachedGatosNew = new ArrayList<Gato>();
            for (Gato gatosNewGatoToAttach : gatosNew) {
                gatosNewGatoToAttach = em.getReference(gatosNewGatoToAttach.getClass(), gatosNewGatoToAttach.getId());
                attachedGatosNew.add(gatosNewGatoToAttach);
            }
            gatosNew = attachedGatosNew;
            familia.setGatos(gatosNew);
            familia = em.merge(familia);
            for (Gato gatosOldGato : gatosOld) {
                if (!gatosNew.contains(gatosOldGato)) {
                    gatosOldGato.setUsuario(null);
                    gatosOldGato = em.merge(gatosOldGato);
                }
            }
            for (Gato gatosNewGato : gatosNew) {
                if (!gatosOld.contains(gatosNewGato)) {
                    Familia oldUsuarioOfGatosNewGato = (Familia) gatosNewGato.getUsuario();
                    gatosNewGato.setUsuario(familia);
                    gatosNewGato = em.merge(gatosNewGato);
                    if (oldUsuarioOfGatosNewGato != null && !oldUsuarioOfGatosNewGato.equals(familia)) {
                        oldUsuarioOfGatosNewGato.getGatos().remove(gatosNewGato);
                        oldUsuarioOfGatosNewGato = em.merge(oldUsuarioOfGatosNewGato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = familia.getNombreUsuario();
                if (findFamilia(id) == null) {
                    throw new NonexistentEntityException("The familia with id " + id + " no longer exists.");
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
            Familia familia;
            try {
                familia = em.getReference(Familia.class, id);
                familia.getNombreUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The familia with id " + id + " no longer exists.", enfe);
            }
            List<Gato> gatos = familia.getGatos();
            for (Gato gatosGato : gatos) {
                gatosGato.setUsuario(null);
                gatosGato = em.merge(gatosGato);
            }
            em.remove(familia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Familia> findFamiliaEntities() {
        return findFamiliaEntities(true, -1, -1);
    }

    public List<Familia> findFamiliaEntities(int maxResults, int firstResult) {
        return findFamiliaEntities(false, maxResults, firstResult);
    }

    private List<Familia> findFamiliaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Familia.class));
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

    public Familia findFamilia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Familia.class, id);
        } finally {
            em.close();
        }
    }

    public int getFamiliaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Familia> rt = cq.from(Familia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

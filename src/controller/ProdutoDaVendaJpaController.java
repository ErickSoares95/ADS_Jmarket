/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.ProdutoDaVenda;
import relatorios.DAORelatorios;

/**
 *
 * @author Br
 */
public class ProdutoDaVendaJpaController implements Serializable {

    private DAORelatorios dAORelatorios = new DAORelatorios();

    public ProdutoDaVendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProdutoDaVenda produtoDaVenda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(produtoDaVenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProdutoDaVenda produtoDaVenda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            produtoDaVenda = em.merge(produtoDaVenda);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = produtoDaVenda.getId();
                if (findProdutoDaVenda(id) == null) {
                    throw new NonexistentEntityException("The produtoDaVenda with id " + id + " no longer exists.");
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
            ProdutoDaVenda produtoDaVenda;
            try {
                produtoDaVenda = em.getReference(ProdutoDaVenda.class, id);
                produtoDaVenda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produtoDaVenda with id " + id + " no longer exists.", enfe);
            }
            em.remove(produtoDaVenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProdutoDaVenda> findProdutoDaVendaEntities() {
        return findProdutoDaVendaEntities(true, -1, -1);
    }

    public List<ProdutoDaVenda> findProdutoDaVendaEntities(int maxResults, int firstResult) {
        return findProdutoDaVendaEntities(false, maxResults, firstResult);
    }

    private List<ProdutoDaVenda> findProdutoDaVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProdutoDaVenda.class));
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

    public ProdutoDaVenda findProdutoDaVenda(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProdutoDaVenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoDaVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProdutoDaVenda> rt = cq.from(ProdutoDaVenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

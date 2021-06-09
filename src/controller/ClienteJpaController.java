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
import model.Cliente;
import relatorios.DAORelatorios;

/**
 * Controller de cliente - operações gerais e CRUD
 * @author Br
 */
public class ClienteJpaController implements Serializable {

    //Instancia um objeto para relatorios
    private DAORelatorios dAORelatorios = new DAORelatorios();
    
    /**
     * Contrutor
     * @param emf 
     */
    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    //gerencia os metodos de operações do banco de dados
    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Gera os relatorios de clientes
     * @return boolean
     */
    public boolean gerarRelatorioCliente() {
        return this.dAORelatorios.gerarRelatorioCliente();
    }
    
    /**
     * Cadastra um cliente no banco de dados
     * @param cliente 
     */
    public void create(Cliente cliente) {
        EntityManager em = null;
        try {
            //recupera a entidade gerenciadora
            em = getEntityManager();
            
            //inicia a transaction
            em.getTransaction().begin();
            
            //salva o cliente no banco 
            em.persist(cliente);
            
            //finaliza a transaction
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Altera os dados de um cliente no banco de dados
     * @param cliente
     * @throws NonexistentEntityException
     * @throws Exception 
     */
    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            //recupera a entidade gerenciadora
            em = getEntityManager();
            
             //inicia a transaction
            em.getTransaction().begin();
            
            //executa o update no banco - tabela cliente
            cliente = em.merge(cliente);
            
            //finaliza a transaction
            em.getTransaction().commit();
        } catch (Exception ex) {
            
            //caso ocorra algum erro...
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                
                //verifico se o cliente ainda existe
                Long id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Remove um cliente do banco de dados
     * @param id
     * @throws NonexistentEntityException 
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            
            //instancio o gerenciador
            em = getEntityManager();
            
            //inicia a transaction
            em.getTransaction().begin();
            
            //instancio o cliente de acordo com o id do parametro
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            
            //removo um cliente do banco de dados
            em.remove(cliente);
            
            //finalizo a transaction
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Recupera a lista completa de clientes
     * @return List de Cliente
     */
    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    /**
     * Recupera a lista de clientes com limite mínimo e máximo de registros (entre os intervalos dos parâmetros)
     * @param maxResults
     * @param firstResult
     * @return List de Cliente
     */
    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    /**
     * Junção dos dois metodos anteriores, busca completa e por limites minimos e maximo de resultados
     * @param all
     * @param maxResults
     * @param firstResult
     * @return List de Cliente
     */
    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            
            //cria uma query com filtros
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            
            //verifica se quer todos os resultados
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca o cliente com id do parametro
     * @param id
     * @return Cliente 
     */
    public Cliente findCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Retorna a quantidade de clientes cadastrados
     * @return int
     */
    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

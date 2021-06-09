/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmarketmysql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Br
 */
public class FabricaDeEntidades {
    
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JMarket");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    private static FabricaDeEntidades instancia;

    private FabricaDeEntidades() {
        //iniciar o driver
        //nome, usuario senha ip...
    }
    
    public static synchronized FabricaDeEntidades getInstance(){
        if(instancia == null){
            instancia = new FabricaDeEntidades();
        }
        return instancia;
    }

    /**
     * @return the entityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    
    
}

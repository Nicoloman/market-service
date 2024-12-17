package com.nqlo.ch.mkt.service.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class DaoFactory {

    @PersistenceContext
    private EntityManager em;

    // Guardar un producto
    @Transactional
    public void persistirProducto(Product product) {
        em.persist(product);
    }

    // Actualizar un producto
    @Transactional
    public void actualizarProducto(Product product) {
        em.merge(product);
    }

    // Eliminar un producto
    @Transactional
    public void eliminarProducto(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
    }

        // Guardar un usuario
        @Transactional
        public void persistirUsuario(User user) {
            em.persist(user);
        }
    
        // Actualizar un usuario
        @Transactional
        public void actualizarUsuario(User user) {
            em.merge(user);
        }
    
        // Eliminar un usuario
        @Transactional
        public void eliminarUsuario(Long id) {
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
        }
}
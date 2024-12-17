package com.nqlo.ch.mkt.service.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nqlo.ch.mkt.service.entities.Category;
import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.entities.Sale;
import com.nqlo.ch.mkt.service.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


// Clase persiste o elimina las entidades en la base de datos.
@Service
public class DaoFactory {

    @PersistenceContext
    private EntityManager em;

    // Guardar un producto
    @Transactional
    public void persistProduct(Product product) {

        try {
            em.persist(product);
            System.out.println("Product: " + product.getName() + " created succesfully!");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Eliminar un producto
    @Transactional
    public void deleteProduct(Long id) {
        try {
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product);
                System.out.println("Product: " + product.getName() + "deleted");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Guardar un usuario
    @Transactional
    public void persistUser(User user) {
        
        try {
            em.persist(user);
            System.out.println("User: " + user.getName() + " created succesfully!");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Eliminar un usuario
    @Transactional
    public void deleteUser(Long id) {
        try {
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
                System.out.println("User: " + user.getName() + "deleted");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Guardar un Categoria
    @Transactional
    public void persistCategory(Category category) {
        try {
            em.persist(category);
            System.out.println("Category: " + category.getName() + " created succesfully!");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
    }

    // Eliminar un Categoria
    @Transactional
    public void deleteCategory(Long id) {

        try {
            Category category = em.find(Category.class, id);
            if (category != null) {
                em.remove(category);
                System.out.println("Category: " + category.getName() + "deleted");
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Guardar una Venta
@Transactional
public void persistSale(Sale sale) {
    try {
        em.persist(sale);
        System.out.println("Sale with ID: " + sale.getId() + " created successfully!");
    } catch (Exception e) {
        e.printStackTrace(System.err);
    }
}

// Eliminar una Venta
@Transactional
public void deleteSale(Long id) {
    try {
        Sale sale = em.find(Sale.class, id);
        if (sale != null) {
            em.remove(sale);
            System.out.println("Sale with ID: " + sale.getId() + " deleted");
        }
    } catch (Exception e) {
        e.printStackTrace(System.err);
    }
}
}

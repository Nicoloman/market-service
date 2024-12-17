package com.nqlo.ch.mkt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nqlo.ch.mkt.service.dao.DaoFactory;
import com.nqlo.ch.mkt.service.entities.Category;
import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.entities.User;

@SpringBootApplication
public class MarketServiceApplication implements CommandLineRunner {

    @Autowired
    private DaoFactory dao;

    public static void main(String[] args) {
        SpringApplication.run(MarketServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            
            //Creacion y persistencia de las entidades
            this.createUsers();
            this.createProductsAndCategories();

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /* Funcion que intenta crear usuarios */
    public void createUsers() {
        try {
            User user00 = new User("Nicolas", "nlomanto@coderhouse.com", "Nicolas123", "USER");
            User user01 = new User("Alejandro", "adistefano@coderhouse.com", "Nicolas123", "USER");
            User user02 = new User("Gary", "gary@coderhouse.com", "Pass123", "USER");

            dao.persistUser(user00);
            dao.persistUser(user01);
            dao.persistUser(user02);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Función para crear categorías, recibiendo una lista de categorías.
    public void createCategories(List<Category> categories) {
        try {
            // Recorre el array y persiste las categorías.
            for (Category category : categories) {
                dao.persistCategory(category);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Función que crea tanto categorías como productos
    public void createProductsAndCategories() {
        try {
			
			//Creamos las categorias.
            Category cocina = new Category("Cocina", "Cosas que encontras en una cocina.");
            Category accesorios = new Category("Accesorios", "Accesorios");
            Category verano = new Category("Verano", "Cosas relacionadas con el verano, agua, calor");

			//Las guardamos un array para persistirlas dinamicamente.
            List<Category> categories = List.of(cocina, accesorios, verano);

            // Creando las categorías
            this.createCategories(categories);
			
			//Creamos los productos
            Product product00 = new Product("Mate", "El mejor mate de vas a ver en tu vida", cocina, 15000L, 10);
            Product product01 = new Product("Taza", "Una taza bonita", accesorios, 5000L, 20);
            Product product02 = new Product("Pelota de playa", "Pelota para el verano", verano, 2000L, 50);

			//persisistimos
            dao.persistProduct(product00);
            dao.persistProduct(product01);
            dao.persistProduct(product02);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}

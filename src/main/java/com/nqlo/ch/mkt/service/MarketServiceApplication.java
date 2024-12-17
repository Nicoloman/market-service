package com.nqlo.ch.mkt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nqlo.ch.mkt.service.dao.DaoFactory;
import com.nqlo.ch.mkt.service.entities.Category;
import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.entities.Sale;
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
        // Creación de usuarios
        User user00 = new User("Nicolas", "nlomanto@coderhouse.com", "Nicolas123", "USER");
        User user01 = new User("Alejandro", "adistefano@coderhouse.com", "Nicolas123", "USER");
        User user02 = new User("Gary", "gary@coderhouse.com", "Pass123", "USER");

        // Persistir los usuarios
        dao.persistUser(user00);
        dao.persistUser(user01);
        dao.persistUser(user02);

        // Creación de categorías
        Category cocina = new Category("Cocina", "Cosas que encontras en una cocina.");
        Category accesorios = new Category("Accesorios", "Accesorios");
        Category verano = new Category("Verano", "Cosas relacionadas con el verano, agua, calor");

        // Persistir categorías
        dao.persistCategory(cocina);
        dao.persistCategory(accesorios);
        dao.persistCategory(verano);

        // Creación de productos
        Product product00 = new Product("Mate", "El mejor mate de vas a ver en tu vida", cocina, 15000L, 10);
        Product product01 = new Product("Taza", "Una taza bonita", accesorios, 5000L, 20);
        Product product02 = new Product("Pelota de playa", "Pelota para el verano", verano, 2000L, 50);

        // Persistir productos
        dao.persistProduct(product00);
        dao.persistProduct(product01);
        dao.persistProduct(product02);

        // Obtener un usuario (por ejemplo, el primero)
        User user = user00; // Puedes cambiarlo a cualquier usuario que hayas creado

        // Crear una lista de productos
        List<Product> products = List.of(product00, product01, product02);

        // Definir las cantidades que el usuario desea comprar
        List<Long> quantities = List.of(2L, 1L, 3L); // Ejemplo: 2 Mates, 1 Taza, 3 Pelotas de playa

        // Calcular el total de la venta
        Long total = 0L;
        for (int i = 0; i < products.size(); i++) {
            total += products.get(i).getPrice() * quantities.get(i); // Multiplicar por la cantidad comprada
        }

        // Crear la venta y persistirla
        Sale sale = new Sale(product00, 10 ,user);
        dao.persistSale(sale); // Persistir la venta
        System.out.println("Venta realizada con éxito!");

    } catch (Exception e) {
        e.printStackTrace(System.err);
    }
}

//     /* Funcion que intenta crear usuarios */
//     public void createUsers() {
//         try {
//             User user00 = new User("Nicolas", "nlomanto@coderhouse.com", "Nicolas123", "USER");
//             User user01 = new User("Alejandro", "adistefano@coderhouse.com", "Nicolas123", "USER");
//             User user02 = new User("Gary", "gary@coderhouse.com", "Pass123", "USER");

//             dao.persistUser(user00);
//             dao.persistUser(user01);
//             dao.persistUser(user02);
//         } catch (Exception e) {
//             e.printStackTrace(System.err);
//         }
//     }

//     // Función para crear categorías, recibiendo una lista de categorías.
//     public void createCategories(List<Category> categories) {
//         try {
//             // Recorre el array y persiste las categorías.
//             for (Category category : categories) {
//                 dao.persistCategory(category);
//             }
//         } catch (Exception e) {
//             e.printStackTrace(System.err);
//         }
//     }

//     // Función que crea tanto categorías como productos
//     public void createProductsAndCategories() {
//         try {
			
// 			//Creamos las categorias.
//             Category cocina = new Category("Cocina", "Cosas que encontras en una cocina.");
//             Category accesorios = new Category("Accesorios", "Accesorios");
//             Category verano = new Category("Verano", "Cosas relacionadas con el verano, agua, calor");

// 			//Las guardamos un array para persistirlas dinamicamente.
//             List<Category> categories = List.of(cocina, accesorios, verano);

//             // Creando las categorías
//             this.createCategories(categories);
			
// 			//Creamos los productos
//             Product product00 = new Product("Mate", "El mejor mate de vas a ver en tu vida", cocina, 15000L, 10);
//             Product product01 = new Product("Taza", "Una taza bonita", accesorios, 5000L, 20);
//             Product product02 = new Product("Pelota de playa", "Pelota para el verano", verano, 2000L, 50);

// 			//persisistimos
//             dao.persistProduct(product00);
//             dao.persistProduct(product01);
//             dao.persistProduct(product02);
//         } catch (Exception e) {
//             e.printStackTrace(System.err);
//         }
//     }


//     public void createSale(User user, List<Product> products) {
//         try {
//             // Calcular el total de la venta
//             Long total = 0L;
//             for (int i = 0; i < products.size(); i++) {
//                 total += products.get(i).getPrice() * products.get(i).getStock();
//             }

//             // Crear la venta y persistirla
//             Sale sale = new Sale(total, user);
//             dao.persistSale(sale); // Suponiendo que tienes un método para persistir la venta

//             // Crear y persistir los detalles de la venta
//             for (int i = 0; i < products.size(); i++) {
//                 SaleDetail saleDetail = new SaleDetail(sale, products.get(i), products.get(i).getStock(), products.get(i).getPrice());
//                 dao.persistSaleDetail(saleDetail); // Persistir cada detalle
//             }

//             System.out.println("Venta realizada con éxito!");

//         } catch (Exception e) {
//             System.err.println("Ocurrio un error al realizar una venta. ");
//             e.printStackTrace(System.err);
//         }
//     }
// }
}
package com.nqlo.ch.mkt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nqlo.ch.mkt.service.dao.DaoFactory;
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
		System.out.println("------------------------------------------------");
		System.out.println("------------------------------------------------");
		System.out.println("------------------------------------------------");
		System.out.println("------------------------------------------------");
		try {

			User user00 = new User("Nicolas", "nlomanto@coderhouse.com", "Nicolas123", "USER");
			User user01 = new User("Alejandro", "adistefano@coderhouse.com", "Nicolas123", "USER");
			User user02 = new User("Gary", "gary@coderhouse.com", "Pass123", "USER");
			this.createUser(user00);
			this.createUser(user01);
			this.createUser(user02);


			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}


	public void createUser(User user){
		try {
			dao.persistirUsuario(user);
			System.out.println("Usuario creado exitosamente !" + user.toString() );
		} catch (Exception e) {
			System.err.println("Error al intentar crear el usuario.");
			e.printStackTrace(System.err);
		}
	}

	public void createProduct(Product product){
		try {
			dao.persistirProducto(product);
			System.out.println("Producto creado exitosamente !" + product.toString() );
		} catch (Exception e) {
			System.err.println("Error al intentar crear el producto.");
			e.printStackTrace(System.err);
		}
	}
}

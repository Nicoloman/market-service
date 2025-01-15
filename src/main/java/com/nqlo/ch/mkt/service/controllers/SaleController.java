package com.nqlo.ch.mkt.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nqlo.ch.mkt.service.dto.SaleDTO;
import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.entities.Sale;
import com.nqlo.ch.mkt.service.entities.User;
import com.nqlo.ch.mkt.service.services.ProductService;
import com.nqlo.ch.mkt.service.services.SaleService;
import com.nqlo.ch.mkt.service.services.UserService;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;
    
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<Sale>> getAllSales() {
    try {
        List<Sale> sales = saleService.getSales();
        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build(); // No hay ventas
        }
        return ResponseEntity.ok(sales);
    } catch (Exception e) {
        e.printStackTrace();  // Imprime la excepción en los logs
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        try {
            Sale sale = saleService.findById(id);
            return sale != null ? ResponseEntity.ok(sale) : ResponseEntity.notFound().build();          //Utilizamos ternario para simplificar.
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Sale> createSale(@RequestBody SaleDTO saleDTO) {
    try {
        // Buscar el producto y el usuario por sus respectivos IDs
        Product product = productService.findById(saleDTO.getProduct_id());
        
        User user = userService.findById(saleDTO.getUser_id());
        // Crear la venta y asignar los valores
        Sale sale = new Sale();
        sale.setQuantity(saleDTO.getQuantity());
        sale.setTotal(saleDTO.getTotal());
        sale.setProduct(product); // Asignar el producto encontrado
        sale.setUser(user); // Asignar el usuario encontrado

        // Guardar la venta
        Sale newSale = saleService.save(sale);

        // Devolver la venta creada
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(null); // Manejo de error si no se encuentra el producto o el usuario
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Error interno
    }
}

@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Sale> updateSale(@PathVariable Long id, @RequestBody SaleDTO saleDTO) {
    try {
        // Buscar la venta por ID
        Sale existingSale = saleService.findById(id); // Asegúrate de tener un método para buscar por ID en el servicio
        if (existingSale == null) {
            return ResponseEntity.notFound().build(); // Si la venta no existe, devolver 404
        }

        // Buscar el producto y el usuario por sus respectivos IDs
        Product product = productService.findById(saleDTO.getProduct_id());
        
        User user = userService.findById(saleDTO.getUser_id());

        // Actualizar la venta con los nuevos valores del DTO
        existingSale.setQuantity(saleDTO.getQuantity());
        existingSale.setTotal(saleDTO.getTotal());
        existingSale.setProduct(product); // Asignar el nuevo producto
        existingSale.setUser(user); // Asignar el nuevo usuario

        // Guardar la venta actualizada
        Sale updatedSale = saleService.save(existingSale); // Guarda la venta actualizada

        return ResponseEntity.ok(updatedSale); // Devuelve la venta actualizada
    } catch (IllegalArgumentException e) {
        return ResponseEntity.notFound().build(); // Manejo de error si no se encuentra el producto o el usuario
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Error interno
    }
}


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id){
        try {
            saleService.deleteById(id);
            return ResponseEntity.noContent().build(); //204
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.nqlo.ch.mkt.service.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nqlo.ch.mkt.service.dto.SaleDTO;
import com.nqlo.ch.mkt.service.dto.SaleItemDTO;
import com.nqlo.ch.mkt.service.dto.SaleStatusUpdateDTO;
import com.nqlo.ch.mkt.service.entities.ErrorResponse;
import com.nqlo.ch.mkt.service.entities.Product;
import com.nqlo.ch.mkt.service.entities.Sale;
import com.nqlo.ch.mkt.service.entities.SaleItem;
import com.nqlo.ch.mkt.service.entities.SaleStatus;
import com.nqlo.ch.mkt.service.entities.User;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.services.ProductService;
import com.nqlo.ch.mkt.service.services.SaleService;
import com.nqlo.ch.mkt.service.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SaleController {


    //TODO Revisar el manejo de excepciones.
    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = saleService.getSales();
        return ResponseEntity.ok(sales);

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        return ResponseEntity.ok(sale);
    }
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createSale(@Valid @RequestBody SaleDTO saleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append(". ");
            });
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessages.toString(), "Un ERROR", 0));
        }
        User user = userService.findById(saleDTO.getUser_id());
    
        List<SaleItem> saleItems = new ArrayList<>();
        Sale sale = new Sale(user, saleItems); // Create the Sale object first
    
        Long totalSaleAmount = 0L;
    
        for (SaleItemDTO itemDTO : saleDTO.getItems()) {
            Product product = productService.findById(itemDTO.getProduct_id());
    
            // Check if the product has enough stock
            if (product.getStock() < itemDTO.getQuantity()) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Not enough stock for product: " + product.getName(), "Stock Error", 0));
            }
    
            // Create SaleItem and calculate total
            SaleItem saleItem = new SaleItem(sale, product, itemDTO.getQuantity());
            saleItems.add(saleItem);
    
            // Update product stock
            product.setStock(product.getStock() - itemDTO.getQuantity());
            productService.save(product);
    
            // Calculate total sale amount
            totalSaleAmount += saleItem.getTotal();
        }
    
        // Set the total amount for the sale
        sale.setTotal(totalSaleAmount);
    
        Sale newSale = saleService.save(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSale);
    }

   @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> updateSaleStatus(@PathVariable Long id, @RequestBody SaleStatusUpdateDTO newStatus) {
    try {
        // Find the sale by ID
        Sale sale = saleService.findById(id);
        if (sale == null) {
            return ResponseEntity.notFound().build(); // Return 404 if the sale does not exist
        }

        // Check if the sale is already in the desired status
        if (sale.getStatus() == newStatus.getStatus()) {
            throw new IllegalStateException("Sale is already in the desired status" + sale.getStatus() + ".");
        }

        // Handle status change logic
        if (newStatus.getStatus() == SaleStatus.CANCELLED) {
            // Restock items
            for (SaleItem item : sale.getItems()) {
                Product product = productService.findById(item.getProductId());
                product.setStock(product.getStock() + item.getQuantity());
                productService.updateById(product.getId(), product);
            }
        } else if (newStatus.getStatus() == SaleStatus.SOLD) {
            // Deduct stock
            for (SaleItem item : sale.getItems()) {
                Product product = productService.findById(item.getProductId());
                if (product.getStock() < item.getQuantity()) {
                    return ResponseEntity.badRequest().body(new ErrorResponse("Not enough stock for product: " + product.getName(), "Stock Error", 0));
                }
                product.setStock(product.getStock() - item.getQuantity());
                productService.updateById(product.getId(), product);
            }
        }

        // Update the sale status
        sale.setStatus(newStatus.getStatus());
        Sale updatedSale = saleService.updateStatusById(sale.getId(), sale.getStatus());

        return ResponseEntity.ok(updatedSale); // Return the updated sale
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.notFound().build(); // Handle error if the sale is not found
    } catch (IllegalStateException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "Status Update Error", 0)); // Handle illegal state error
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle internal server error
    }
}

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Long id) {
        try {
            SaleStatusUpdateDTO newStatus = new SaleStatusUpdateDTO(SaleStatus.CANCELLED);
            updateSaleStatus(id, newStatus);
            saleService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle internal server error
        }
    }
}

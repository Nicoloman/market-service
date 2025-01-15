package com.nqlo.ch.mkt.service.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqlo.ch.mkt.service.entities.Sale;
import com.nqlo.ch.mkt.service.repositories.CategoryRepository;
import com.nqlo.ch.mkt.service.repositories.SaleRepository;

import jakarta.transaction.Transactional;


@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private CategoryRepository categoryRepository;


	public List<Sale> getSales(){
		return saleRepository.findAll();
	}

	public Sale findById(Long id){
		return saleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Sale with id:"+ id + "couldnt be found"));
	}

	@Transactional
	public Sale createSale(Sale sale){
		return saleRepository.save(sale);
	}

	@Transactional
	public Sale updateSale(Long id, Sale updatedSale){
		Sale sale = saleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Sale with id:"+ id + "couldnt be found"));

		//Actualizamos el registro
		sale.setProduct(updatedSale.getProduct());
		sale.setQuantity(updatedSale.getQuantity());
		sale.setTotal(updatedSale.getTotal());
		sale.setUser(updatedSale.getUser());

		//Lo impactamos en la base
		return saleRepository.save(sale);
	}

	@Transactional
	public void deleteSale(Long id){
		if(!saleRepository.existsById(id)){
			throw new IllegalArgumentException("Sale with id:"+ id + "couldnt be found");
		} 
		saleRepository.deleteById(id);
	}

	//Assign category to Sale

}


package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.Product;
import com.prs.db.ProductRepository;




@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductRepository productRepo;
	
	//list - return all stuffies
	@GetMapping("/")
	public JsonResponse listProducts() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productRepo.findAll());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	//demo use of a Path Variable
	@GetMapping("/{id}")
	public JsonResponse getProduct(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productRepo.findById(id));
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
//  demo of Request Parameters
//	@GetMapping("")
//	public Stuffy creatAStuffy(@RequestParam int id, @RequestParam String type, @RequestParam String color, @RequestParam String size, @RequestParam int limbs) {
//		Stuffy s = new Stuffy(id, type, color, size, limbs);
//		return s;
//	}
	
	//add - adds a new Stuffy
	@PostMapping("/")
	public JsonResponse addAProduct(@RequestBody Product p) {
		//add a new stuffy
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productRepo.save(p));
		}
		catch (DataIntegrityViolationException dive){
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	//update - update a Stuffy
	@PutMapping("/")
	public JsonResponse updateProduct(@RequestBody Product p) {
		// update a stuffy
		JsonResponse jr = null;
		try {
			if (productRepo.existsById(p.getId())) {
			jr = JsonResponse.getInstance(productRepo.save(p));
		}
		else {
			jr = JsonResponse.getInstance("Error updating Actor. id:  "+p.getId()+"dosent exist!");
		}
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse deleteProduct(@PathVariable int id) {
		// delete a stuffy
		JsonResponse jr = null;
		
		try {
			if (productRepo.existsById(id)) {
				productRepo.deleteById(id);
			jr = JsonResponse.getInstance("Delete successful!");
		}
		else {
			//record dosent exist
			jr = JsonResponse.getInstance("Error deleting Actor. id:  "+id+"dosent exist!");
		}
		}
		catch (DataIntegrityViolationException dive){
			jr = JsonResponse.getInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	

}

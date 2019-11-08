package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.LineItem;
import com.prs.db.LineItemRepository;




@CrossOrigin
@RestController
@RequestMapping("/lineitems")
public class LineItemController {
	@Autowired
	private LineItemRepository lineItemRepo;
	
	//list - return all stuffies
	@GetMapping("/")
	public JsonResponse listLineItems() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(lineItemRepo.findAll());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	//demo use of a Path Variable
	@GetMapping("/{id}")
	public JsonResponse getLineItem(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(lineItemRepo.findById(id));
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
	public JsonResponse addALineItem(@RequestBody LineItem l) {
		//add a new stuffy
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(lineItemRepo.save(l));
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
	public JsonResponse updateLineItem(@RequestBody LineItem l) {
		// update a stuffy
		JsonResponse jr = null;
		try {
			if (lineItemRepo.existsById(l.getId())) {
			jr = JsonResponse.getInstance(lineItemRepo.save(l));
		}
		else {
			jr = JsonResponse.getInstance("Error updating Actor. id:  "+l.getId()+"dosent exist!");
		}
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse deleteLineItem(@PathVariable int id) {
		// delete a stuffy
		JsonResponse jr = null;
		
		try {
			if (lineItemRepo.existsById(id)) {
				lineItemRepo.deleteById(id);
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

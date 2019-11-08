package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.Request;
import com.prs.db.RequestRepository;




@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {
	@Autowired
	private RequestRepository requestRepo;
	
	//list - return all stuffies
	@GetMapping("/")
	public JsonResponse listRequests() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(requestRepo.findAll());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	//demo use of a Path Variable
	@GetMapping("/{id}")
	public JsonResponse getRequest(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(requestRepo.findById(id));
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
	public JsonResponse addARequest(@RequestBody Request r) {
		//add a new stuffy
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(requestRepo.save(r));
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
	public JsonResponse updateRequest(@RequestBody Request r) {
		// update a stuffy
		JsonResponse jr = null;
		try {
			if (requestRepo.existsById(r.getId())) {
			jr = JsonResponse.getInstance(requestRepo.save(r));
		}
		else {
			jr = JsonResponse.getInstance("Error updating Actor. id:  "+r.getId()+"dosent exist!");
		}
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse deleteRequest(@PathVariable int id) {
		// delete a stuffy
		JsonResponse jr = null;
		
		try {
			if (requestRepo.existsById(id)) {
				requestRepo.deleteById(id);
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

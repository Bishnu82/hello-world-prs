package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.User;
import com.prs.db.UserRepository;




@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepo;
	
	//list - return all stuffies
	@GetMapping("/")
	public JsonResponse listUsers() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepo.findAll());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	//demo use of a Path Variable
	@GetMapping("/{id}")
	public JsonResponse getUser(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepo.findById(id));
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
	public JsonResponse addAUser(@RequestBody User u) {
		//add a new stuffy
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(userRepo.save(u));
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
	public JsonResponse updateActor(@RequestBody User u) {
		// update a stuffy
		JsonResponse jr = null;
		try {
			if (userRepo.existsById(u.getId())) {
			jr = JsonResponse.getInstance(userRepo.save(u));
		}
		else {
			jr = JsonResponse.getInstance("Error updating Actor. id:  "+u.getId()+"dosent exist!");
		}
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse deleteUser(@PathVariable int id) {
		// delete a stuffy
		JsonResponse jr = null;
		
		try {
			if (userRepo.existsById(id)) {
				userRepo.deleteById(id);
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

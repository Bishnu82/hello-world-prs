package com.prs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.Vendor;
import com.prs.db.VendorRepository;




@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController {
	@Autowired
	private VendorRepository vendorRepo;
	
	//list - return all stuffies
	@GetMapping("/")
	public JsonResponse listVendors() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(vendorRepo.findAll());
		}
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	//demo use of a Path Variable
	@GetMapping("/{id}")
	public JsonResponse getVendor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(vendorRepo.findById(id));
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
	public JsonResponse addAVendor(@RequestBody Vendor v) {
		//add a new stuffy
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(vendorRepo.save(v));
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
	public JsonResponse updateVendor(@RequestBody Vendor v) {
		// update a stuffy
		JsonResponse jr = null;
		try {
			if (vendorRepo.existsById(v.getId())) {
			jr = JsonResponse.getInstance(vendorRepo.save(v));
		}
		else {
			jr = JsonResponse.getInstance("Error updating Actor. id:  "+v.getId()+"dosent exist!");
		}
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse deleteVendor(@PathVariable int id) {
		// delete a stuffy
		JsonResponse jr = null;
		
		try {
			if (vendorRepo.existsById(id)) {
				vendorRepo.deleteById(id);
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

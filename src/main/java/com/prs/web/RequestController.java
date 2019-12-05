package com.prs.web;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.web.JsonResponse;
import com.prs.business.Request;
import com.prs.db.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {
	@Autowired
	private RequestRepository requestRepo;
	
	//list - return all requests
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
	
	@GetMapping("/list-review/{id}")
	public JsonResponse findByStatus(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(requestRepo.findByUserIdNotLikeAndStatus(id, "pending"));
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
		
	//add - adds a new requests
	@PostMapping("/")
	public JsonResponse addARequest(@RequestBody Request r) {
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
	
	@PutMapping("/approve")
	public JsonResponse approveRequest(@RequestBody Request r) {
		JsonResponse jr = null;
		try {
			r.setStatus("approved");
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
	
	@PutMapping("/reject")
	public JsonResponse rejectRequest(@RequestBody Request r) {
		JsonResponse jr = null;
		try {
			r.setStatus("reject");
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
	
	//update - update a requests
	@PutMapping("/")
	public JsonResponse updateRequest(@RequestBody Request r) {
		JsonResponse jr = null;
		try {
			if (requestRepo.existsById(r.getId())) {
			jr = JsonResponse.getInstance(requestRepo.save(r));
		}
		else {
			jr = JsonResponse.getInstance("Error updating Request. id:  "+r.getId()+"dosent exist!");
		}
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	@PutMapping("/submit-review")
	public JsonResponse findByStatus(@RequestBody Request r) {
		JsonResponse jr = null;
		try {
			if (r.getTotal()<=50) {
				r.setStatus("approved");
				r.setSubmittedDate(LocalDateTime.now());
			jr = JsonResponse.getInstance(requestRepo.save(r));
			}
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@DeleteMapping("/{id}")
	public JsonResponse deleteRequest(@PathVariable int id) {
		// delete a requests
		JsonResponse jr = null;
		
		try {
			if (requestRepo.existsById(id)) {
				requestRepo.deleteById(id);
			jr = JsonResponse.getInstance("Delete successful!");
		}
		else {
			//record dosent exist
			jr = JsonResponse.getInstance("Error deleting Request. id:  "+id+"dosent exist!");
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
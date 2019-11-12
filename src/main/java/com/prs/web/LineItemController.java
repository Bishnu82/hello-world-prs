package com.prs.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.web.JsonResponse;
import com.prs.business.LineItem;
import com.prs.business.Product;
import com.prs.business.Request;
import com.prs.db.LineItemRepository;
import com.prs.db.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/line-items")
public class LineItemController {
	@Autowired
	private LineItemRepository lineItemRepo;
	@Autowired
	private RequestRepository requestRepo;
		
	//list - return all line-items
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
	
	@GetMapping("/lines-for-pr/{id}")
	public JsonResponse listRLines(@PathVariable Request id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(lineItemRepo.findByRequest(id));
		}
		catch (Exception e){
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}
	
	//add - adds a new line-items
	@PostMapping("/")
	public JsonResponse addALineItem(@RequestBody LineItem l) {
		// add a new line-item
		JsonResponse jr = null;
		// for all maintenance methods recalculate the collection value
		try {
			// 1 do maintenance
			jr = JsonResponse.getInstance(lineItemRepo.save(l));
			// 2 recalcCollectionValue
			recalcTotal(l.getRequest());
			// what user are we looking for
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
	
		//update - update a line-items
	@PutMapping("/")
	public JsonResponse updateLineItem(@RequestBody LineItem l) {
		JsonResponse jr = null;
		try {
			if (lineItemRepo.existsById(l.getId())) {
			jr = JsonResponse.getInstance(lineItemRepo.save(l));
			recalcTotal(l.getRequest());
		}
		else {
			jr = JsonResponse.getInstance("Error updating LineItem. id:  "+l.getId()+"dosent exist!");
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
		// delete a line-items
		JsonResponse jr = null;
		
		try {
			if (lineItemRepo.existsById(id)) {
				lineItemRepo.deleteById(id);
				jr = JsonResponse.getInstance("Delete successful!");
				LineItem l = new LineItem();
				recalcTotal(l.getRequest());
		}
		else {
			//record dosent exist
			jr = JsonResponse.getInstance("Error deleting LineItem:  "+id+"dosent exist!");
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
	
	//method will recalculate the collectionValuse and save it in the user instance

	private void recalcTotal(Request r) {
		try {
		// get a list of lineitems
		// all line items for user
		List<LineItem> lineItemList = lineItemRepo.findByRequest(r);
		// loop through list to sum a total
		double total = 0.0;
		for (LineItem li:lineItemList) {
			Product p = li.getProduct();
			double lITotal = p.getPrice() * li.getQuantity();
			total += lITotal;
		}
		// save total in request instance
			r.setTotal(total);
			requestRepo.save(r);
		}
		catch (Exception e) {
			throw e;
		}
	}
}
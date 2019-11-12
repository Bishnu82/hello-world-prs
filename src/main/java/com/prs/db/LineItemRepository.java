package com.prs.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.LineItem;
import com.prs.business.Request;


public interface LineItemRepository extends CrudRepository<LineItem, Integer> {
	List<LineItem> findByRequest(Request request);


}

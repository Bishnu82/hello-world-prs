package com.prs.business;

import javax.persistence.*;

@Entity
public class LineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int requestID;
	private int productID;
	private int quantity;
	
	public LineItem() {
		super();
	}

	public LineItem(int id, int requestID, int productID, int quantity) {
		super();
		this.id = id;
		this.requestID = requestID;
		this.productID = productID;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "lineItem [id=" + id + ", requestID=" + requestID + ", productID=" + productID + ", quantity=" + quantity
				+ "]";
	}
}
//  foreign key (productID) references product(id),
//  foreign key (requestID) references request(id),
//	constraint req_pdt unique (requestID, productID)
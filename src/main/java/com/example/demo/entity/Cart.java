package com.example.demo.entity;

import java.util.HashMap;
import java.util.Map;


public class Cart {
private Map<Integer, Items> items = new HashMap<>();
	
	private int total;

	public Map<Integer, Items> getItems() {
		return items;
	}

	public int getTotal() {
		return total;
	}
	public Cart() {
		
	}
	public void addCart(Items item, int quantity) {
		Items existedItem = items.get(item.getId());
		
		if(existedItem == null) {
			item.setQuantity(quantity);
			items.put(item.getId(), item);
		} else {
			existedItem.setQuantity(existedItem.getQuantity() + quantity);
		}
		recalcTotal();
		
	}
	
	
	public void deleteCart(int itemCode) {
		items.remove(itemCode);
		recalcTotal();
	}
	
	
	public void recalcTotal() {
		total = 0;
		for(Items item : items.values()) {
			total += item.getPrice() * item.getQuantity();
		}
	
	}

}

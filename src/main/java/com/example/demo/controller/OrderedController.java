package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.entity.Cart;
import com.example.demo.repository.ItemsRepository;

@Controller
public class OrderedController {
	@Autowired
	ItemsRepository itemsRepository;

	@Autowired
	HttpSession session;
	
	
	
	public Cart getCart() {

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

}

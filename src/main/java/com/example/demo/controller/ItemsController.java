package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Items;
import com.example.demo.repository.ItemsRepository;

@Controller
public class ItemsController {

	@Autowired
	HttpSession session;

	@Autowired
	ItemsRepository itemsRepository;

	@RequestMapping(value="/items")
	public ModelAndView items(ModelAndView mv) {
			mv.addObject("items", itemsRepository.findAll());
	List<Items> itemList = itemsRepository.findAll();
	mv.addObject("items", itemList);
	mv.setViewName("items");
	return mv;

	}
	@RequestMapping("serch")
	public ModelAndView serch(ModelAndView mv) {
			mv.addObject("itemList", itemsRepository.findAll());
			
		return mv;
	}
	@RequestMapping("/addCart")
	public ModelAndView addCart(ModelAndView mv) {
		Cart cart = getCart();
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.setViewName("itemsAdd");
		return mv;
	}
	public Cart getCart() {

		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	
	
	

}

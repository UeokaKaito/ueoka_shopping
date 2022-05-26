package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Items;
import com.example.demo.repository.ItemsRepository;

@Controller
public class CartController {
	@Autowired
	ItemsRepository itemsRepository;

	@Autowired
	HttpSession session;

	@RequestMapping("/cart")
	public ModelAndView addCart(ModelAndView mv) {
		Cart cart = getCart();
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		return mv;
	}

	@RequestMapping("/cart/add/{id}")
	public ModelAndView addCart(@PathVariable("id") int id,
			@RequestParam(name = "quantity", defaultValue = "1") int quantity, ModelAndView mv) {
		Cart cart = getCart();
		Items items = itemsRepository.getById(id);
		cart.addCart(items, 1);
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.addObject("itemss", itemsRepository.findAll());
		mv.setViewName("itemsAdd");
		return mv;
	}

	@RequestMapping("/allItem")
	public ModelAndView allItem(ModelAndView mv) {
		mv.addObject("items", itemsRepository.findAll());
		mv.setViewName("items");
		return mv;
	}

	@RequestMapping("/cart/delete/{id}")
	public ModelAndView deleteItem(@PathVariable(name = "id") int id, ModelAndView mv) {
		Cart cart = getCart();
		cart.deleteCart(id);
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.setViewName("itemsAdd");

		return mv;
	}
	@RequestMapping("/orderItem")
	public ModelAndView ordered(ModelAndView mv) {
		mv.setViewName("ordered");
		Cart cart = getCart();
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		return mv;
	}

	public Cart getCart() {

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

}

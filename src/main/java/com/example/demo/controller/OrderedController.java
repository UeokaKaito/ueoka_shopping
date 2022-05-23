package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Cart;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class OrderedController {
	@Autowired
	ItemsRepository itemsRepository;
	
	@Autowired
	UsersRepository usersRepository;

	@Autowired
	HttpSession session;

	@RequestMapping("/buy")
	public ModelAndView buy(ModelAndView mv) {

		mv.addObject("userinfo", session.getAttribute("userinfo"));
		mv.setViewName("info");
		return mv;
	}
	
	@RequestMapping("/procedure")
	public ModelAndView procedure(ModelAndView mv) {
		mv.setViewName("confirmation");
		return mv;
	}
	@RequestMapping("/yes")
	public ModelAndView yes(ModelAndView mv) {
		mv.setViewName("complete");
		return mv;
	}
	@RequestMapping("/no")
	public ModelAndView no(ModelAndView mv) {
		Cart cart = getCart();
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.setViewName("itemsAdd");
		return mv;
	}
	@RequestMapping("myPage") 
	public ModelAndView myPage(ModelAndView mv) {
//		mv.addObject("userinfo",(Users) session.getAttribute("userinfo"));
		mv.setViewName("myPage");
		return mv;
	}
//	@RequestMapping("/edit")
//	public ModelAndView edit(ModelAndView mv) {
//		mv.setViewName("edit");
//		return mv;
//	}
//	
	

	public Cart getCart() {

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

}

package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/items")
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
	@RequestMapping(value="/search" ,method=RequestMethod.GET)
	public ModelAndView search(
			ModelAndView mv,
			@RequestParam("searchWord") String searchWord) {
		List<Items> itemList=itemsRepository.findAllByNameContaining(searchWord);
		mv.addObject("items",itemList);
		mv.setViewName("items");
		return mv;
	}
	
	//商品一覧の表示
	@RequestMapping("/items/{id}") //items
	public ModelAndView detail(
			@PathVariable("id") Integer id,
			ModelAndView mv) {      //画像が出力されない
//		Items item = itemsRepository.findById(id).get();
//		List<Items> itemList = itemsRepository.findByName("name");
		
		mv.addObject("item", itemsRepository.findById(id).get());
//		List<Items> itemDetail = itemsRepository.findByName("name");
//		mv.addObject("items", itemDetail);
//		mv.addObject("items", itemsRepository.findByName("name"));
	    mv.setViewName("itemDetail");
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

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repository.ItemsRepository;

@Controller
public class ItemDetailController {
	
	@Autowired 
	ItemsRepository itemsRepository;
	
	@RequestMapping("/items/name") //items
	public ModelAndView detail(
			ModelAndView mv) {//画像が出力されない
		
		mv.addObject("items", itemsRepository.findAll());
		mv.setViewName("itemDetail");
		return mv;
	}

}

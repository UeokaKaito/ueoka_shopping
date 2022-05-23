package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Items;
import com.example.demo.repository.ItemsRepository;

@Controller
public class ItemDetailController {
	
	@Autowired 
	ItemsRepository itemsRepository;
	
	@RequestMapping("/{name}") //items
	public ModelAndView detail(ModelAndView mv) {//画像が出力されない
		List<Items> itemList = itemsRepository.findAll();
		mv.addObject("items", itemList);
		mv.setViewName("itemDetail");
		return mv;
	}

}

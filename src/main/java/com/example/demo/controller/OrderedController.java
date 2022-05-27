package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Items;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Ordered;
import com.example.demo.entity.Pay;
import com.example.demo.entity.Users;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderedRepository;
import com.example.demo.repository.PayRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class OrderedController {
	@Autowired
	ItemsRepository itemsRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	OrderedRepository orderedRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	PayRepository payRepository;

	@Autowired
	HttpSession session;

	@RequestMapping("/buy")
	public ModelAndView buy(ModelAndView mv) {
		Cart cart = getCart();
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.addObject("userinfo", session.getAttribute("userinfo"));
		mv.setViewName("info");
		return mv;
	}
//支払情報の入力
	@RequestMapping(value="/procedure", method=RequestMethod.POST)
	public ModelAndView procedure(
			@RequestParam("userName") String userName, 
			@RequestParam("email") String email,
			@RequestParam("address") String address,
			@RequestParam("creditNo") String creditNo,
			@RequestParam(name="creditSecurity", defaultValue = "0") Integer creditSecurity,
			ModelAndView mv) {
		//未入力チェック
		if(creditNo == null || creditNo.length() == 0 || creditSecurity == null || creditSecurity.equals(0)) {
			mv.addObject("message", "支払情報を入力してください");
			mv.setViewName("info");
			return mv;
		} else {
			if (userName == null || userName.length() == 0 || email == null || email.length() == 0 ||
					address == null || address.length() == 0) {
				mv.addObject("message", "未入力の項目があります");
				mv.setViewName("info");
				return mv;
			}
		}
		//支払い情報の登録
		Pay pay = new Pay(creditNo, creditSecurity);
		payRepository.saveAndFlush(pay);
		mv.setViewName("confirmation");
		return mv;
	}

	// 購入確認＆購入履歴の登録
	@RequestMapping("/orderYes")
	public ModelAndView yes(
//			@RequestParam("id") Integer id,
//		    @RequestParam("userId") Integer userId,
//			@RequestParam("orderedDate") Date orderedDate,
//			@RequestParam("totalPrice") Integer totalPrice,

			ModelAndView mv) {
		Cart cart = (Cart) session.getAttribute("cart");
		Users userinfo = (Users) session.getAttribute("userinfo");
		Integer id = userinfo.getId();

		Ordered ordered = new Ordered(userinfo.getId(),  new Date(), cart.getTotal());
		int order_id = orderedRepository.saveAndFlush(ordered).getId();

		for (Items item : cart.getItems().values()) {
			OrderDetail orderDetail = new OrderDetail(order_id, item.getId(), item.getQuantity());
			orderDetailRepository.saveAndFlush(orderDetail);
		}

		// Ordered ordered = new Ordered(id, userId, orderedDate, cart.getTotal());
		// orderedRepository.saveAndFlush(ordered);
		mv.setViewName("complete");
		return mv;

//		Ordered ordered = new Ordered(id, new Date(), cart.getTotal());
//		int order_code = orderedRepository.saveAndFlush(ordered).getId();
//	
//		for(Item item : cart.getItems().values()) {
//			OrderDetail orderDetail = new OrderDetail(orderedId, item.getId(), item.getQuantity());
//			orderDetailRepository.saveAndFlush(orderDetail);
//	mv.setViewName("complete");
//		return mv;
//		}
	}

	@RequestMapping("/cancel")
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
	
	
//	@RequestMapping("/allaItem")
//	public ModelAndView allaItem(ModelAndView mv) {
//		
//		List<Items> itemList = itemsRepository.findAll(Sort.by(Direction.ASC, "id"));
//		Cart cart = getCart();
//		cart.deleteCart(id);
//		mv.addObject("items", itemList);
//		
//		mv.setViewName("items");
//		return mv;
//	}

	@RequestMapping("/history")
	public ModelAndView history(ModelAndView mv) {
		Users userinfo = (Users) session.getAttribute("userinfo");
		Integer id = userinfo.getId();
		List<Ordered> orderList = orderedRepository.findByUserId(id);
		mv.addObject("ordered", orderList);
		mv.setViewName("history");

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

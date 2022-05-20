package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Items;
import com.example.demo.entity.Users;
import com.example.demo.repository.ItemsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class AccountController {
	@Autowired
	HttpSession session;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	ItemsRepository itemsRepository;

	@RequestMapping("/")
	public String login() {
		// session情報の削除
		session.invalidate();
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelAndView mv) {
		if (email == null || email.length() == 0 || password == null || password.length() == 0) {
			mv.addObject("message", "情報が未入力です");
			mv.setViewName("index");
			return mv;
		}
		List<Users> userlist = usersRepository.findByEmailAndPassword(email, password);
		if (userlist.size() == 0) {
			mv.addObject("message", "登録情報がありません");
			mv.setViewName("index");
			return mv;
		} else {
			//mv.addObject("items", itemsRepository.findAll());
			List<Items> itemList = itemsRepository.findAll();
			mv.addObject("items", itemList);
			mv.setViewName("items");
			return mv;
		}
	}

	@RequestMapping("/signup")
	public ModelAndView signup(ModelAndView mv) {

		mv.addObject("prefecture", getPrefecture());
		mv.setViewName("signup");
		return mv;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signup(@RequestParam("userName") String userName, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("prefecture") String prefecture,
			@RequestParam("address") String address, @RequestParam("tell") String tell,
			@RequestParam("name") String name, ModelAndView mv) {
		if (userName == null || userName.length() == 0 || email == null || email.length() == 0 || password == null
				|| password.length() == 0 || address == null || address.length() == 0 || tell == null
				|| tell.length() == 0 || name == null || name.length() == 0) {
			mv.addObject("message", "未入力の項目があります");
			mv.setViewName("signup");
			return mv;
		}
		Users user = new Users(userName, prefecture + address, email, tell, name, password);
		usersRepository.saveAndFlush(user);
		mv.setViewName("index");
		return mv;
	}

	private String[] getPrefecture() {
		String[] result = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都",
				"神奈川県", "新潟県", "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県", "三重県", "滋賀県", "京都府", "大阪府",
				"兵庫県", "奈良県", "和歌山県", "鳥取県", "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県", "愛媛県", "高知県", "福岡県", "佐賀県",
				"長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県" };
		return result;
	}
	@RequestMapping("/logout")
	public String logout() {
		return login();
	}
}
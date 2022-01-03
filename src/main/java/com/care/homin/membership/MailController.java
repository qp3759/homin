package com.care.homin.membership;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.care.homin.membership.service.IMemberService;

@RestController
public class MailController {
	final static Logger logger = LoggerFactory.getLogger(MailController.class);
	@Autowired IMemberService service;
	
	@RequestMapping(value = "sendAuth")
	public Map<String, String> sendAuth(@RequestBody Map<String, String> map, HttpSession session) {
		logger.warn(map.get("email"));
		Boolean check = (Boolean)session.getAttribute("authState");
		if(check != null && check == true) {
			map.put("msg", "인증 완료됨.");
			return map;
		}
		
		service.sendAuth((String)map.get("email"));
		map.put("msg", "이메일을 확인하세요.");
		return map;
	}
	
	@RequestMapping(value = "authConfirm")
	public Map<String, String> authConfirm(@RequestBody Map<String, String> map, HttpSession session) {
		Boolean check = (Boolean)session.getAttribute("authState");
		if(check != null && check == true) {
			map.put("msg", "인증 완료.");
			return map;
		}
		map.put("msg", service.authConfirm(map.get("inputAuthNum")));
		return map;
	}
}

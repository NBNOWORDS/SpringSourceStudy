package com.dch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dch.service.DemoService;

@Controller
public class DemoController {
	@Autowired
	private DemoService demoService;
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "sucess";
	}
	@RequestMapping("/img")
	public String sucess() {
		return "img";
	}
}

package com.capgemini.controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rebootProyect")
public class ProjectController {
    
    // TRUNCATE + INSERT INTO de datos por defecto del proyecto
	// @PostConstruct
	// public void initialize() {
	//    System.out.println(">>>>>>>>>>>> LALALALALAL <<<<<<<<<<<<<");
	// }

	
    // @GetMapping()
	// public String getIndex(){
	// 	System.out.println("HolaMundo");
	// 	// ModelAndView mav = new ModelAndView("projectInfo");
	// 	// return mav;
	// }
}

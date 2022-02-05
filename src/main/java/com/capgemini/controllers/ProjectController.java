package com.capgemini.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rebootProyect")
public class ProjectController {
    
    // TRUNCATE + INSERT INTO de datos por defecto del proyecto
	
    @GetMapping()
	public ModelAndView getIndex(){
		ModelAndView mav = new ModelAndView("projectInfo");
		return mav;
	}
}

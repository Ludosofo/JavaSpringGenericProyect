package com.capgemini.controllers;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.capgemini.entities.Usuario;
import com.capgemini.servicies.IOfertaServ;
import com.capgemini.servicies.IUsuarioServ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController implements Serializable{

	/*
		- index
		- getUsuario 		( obtener perfil )
		- saveUsuario 		( creación/edición )
		- getListOfertas  	( Hacer un filtro entre las ofertas )
		- saveUsuarioImagen ( Podría ir aparte y ser llamado por saveUsuario )
		- saveOferta 		( creación/edición )
		- saveContrato 		( creación/edición )
		- deleteOferta  	( )
		- deleteUsuario 	( darse de baja ? )
		- getContrato
		- deleteContrato 	( borrar oferta SOLO si no está cerrado )
		- saveValoracion 	( creación/valoracion )

		WARNING!!! Tenemmos que manejar un usuario conectado desde el cliente
		// Esto requiere documentarse sobre seguridad y creación de cookies
	*/

	private static final Log LOG = LogFactory.getLog(MainController.class);
	
	@Autowired
	private IUsuarioServ usuarioService;

	@Autowired
	private IOfertaServ ofertaService;
	
	private Path imagesURL = Paths.get("src//main//resources//static/images");

	@GetMapping()
	public ModelAndView getIndex(){
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("listaOfertas", ofertaService.findAll());
		mav.addObject("absPath", imagesURL.toFile().getAbsolutePath());
		return mav;
	}
	
	@GetMapping("/register")
	public String crearPersona(Model model){
		model.addAttribute("usuario", new Usuario());
		return "createPersona";
	}

	@GetMapping("/login")
	public String setSession(){
		return null;
	}
}
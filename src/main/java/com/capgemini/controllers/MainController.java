package com.capgemini.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lemonapp")
public class MainController {
	
	//añadir métodos, con sus respectivas conexiones a la capa de servicios
	// Implementar navegación a diferentes vistas/obtención de datos/procesamiento/etc...


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

		- 
	*/
}

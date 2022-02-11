package com.capgemini.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Oferta;
import com.capgemini.servicies.IOfertaServ;



@RestController
@RequestMapping(value = "/recursos")
public class RestApiControlador {
	@Autowired
private IOfertaServ ofertaServ;

@GetMapping
public ResponseEntity<List<Oferta>> findall(){
	ResponseEntity<List<Oferta>> res = null;
	List<Oferta> ofertas = null;
	ofertas = ofertaServ.findAll();
	if (ofertas.size() > 0) {
		res = new ResponseEntity<List<Oferta>>(ofertas, HttpStatus.OK);
	} else {
		res = new ResponseEntity<List<Oferta>>(HttpStatus.NO_CONTENT);
	}
	return res;
}
	
}



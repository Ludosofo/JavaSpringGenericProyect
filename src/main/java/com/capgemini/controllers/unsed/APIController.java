package com.capgemini.controllers.unsed;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.controllers.MainController;
import com.capgemini.entities.Usuario;
import com.capgemini.servicies.IOfertaServ;
import com.capgemini.servicies.IUsuarioServ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// Esta es la API RESTful del proyecto LemonApp
// Requisitos de RESTFul
// 0. Se le llamada desde http
// 1. La semantica de las llamadas debe ser /users/all
// 2. Debe devolver enlaces de la llamada
// 3. Controles de hipermedia HATEOAS devuelve un mapa con todos los enlaces que el cliente puede utilizar
// 4. Debe de poder recibir peticiones POST/PUT/DELETE

@RestController
@RequestMapping("/api/")
public class APIController implements Serializable{
    /*
    private static final Log LOG = LogFactory.getLog(MainController.class);
	private static final String defaultUserURL = ""; // TODO
	private Path imagesURL = Paths.get("src//main//resources//static/images");

	@Autowired
	private IUsuarioServ usuarioService;
	@Autowired
	private IOfertaServ ofertaService;

    
    @GetMapping("/users/")
	public List<Usuario> getUsuarios() {
		List<Usuario> listaUsuarios = usuarioService.findAll();
        return listaUsuarios;
	}

    @GetMapping("/users/{id}")
    public Usuario getUserById(String id){

    }

    @DeleteMapping("/{id}")
	public void deleteUsuario(@PathVariable("id") long id) {
		usuarioService.delete(id);
	}
	*/
    
}
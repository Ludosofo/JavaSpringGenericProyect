package com.capgemini.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

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
public class MainController implements Serializable {

	/*
	 * - index - getUsuario ( obtener perfil ) - saveUsuario ( creación/edición ) -
	 * getListOfertas ( Hacer un filtro entre las ofertas ) - saveUsuarioImagen (
	 * Podría ir aparte y ser llamado por saveUsuario ) - saveOferta (
	 * creación/edición ) - saveContrato ( creación/edición ) - deleteOferta ( ) -
	 * deleteUsuario ( darse de baja ? ) - getContrato - deleteContrato ( borrar
	 * oferta SOLO si no está cerrado ) - saveValoracion ( creación/valoracion )
	 * 
	 * WARNING!!! Tenemmos que manejar un usuario conectado desde el cliente // Esto
	 * requiere documentarse sobre seguridad y creación de cookies
	 */

	private static final Log LOG = LogFactory.getLog(MainController.class);
	private static final String defaultUserURL = ""; // TODO
	private Path imagesURL = Paths.get("src//main//resources//static/images");

	@Autowired
	private IUsuarioServ usuarioService;
	@Autowired
	private IOfertaServ ofertaService;

	// Cambio para testear
	// Estás conectado ? Pues debería redireccionarnos a otra pagina
	@GetMapping()
	public ModelAndView getIndex() {
		System.out.println("getIndex()");
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("usuario", new Usuario()); // <---- Necesario para que Thymeleaf sepa los datos que recoge
		mav.addObject("listaUsuarios", usuarioService.findAll());
		// mav.addObject("absPath", imagesURL.toFile().getAbsolutePath());
		return mav;
	}

	// Procesamiento del registro
	@GetMapping("/register")
	public String saveUsuario(@ModelAttribute(name = "usuario") Usuario usuario, @RequestParam (name="file") MultipartFile avatar, Model model) {
		model.addAttribute("usuario", new Usuario());
		usuarioService.save(usuario);
		System.out.println(usuario);
		return "index";
	}


	// Intentar logearte
	@GetMapping("/login")
	public String verifyCredentials() {
		// TODO: Verificar credenciales
		return "redirect:/landingPage";
	}
	
	
	@PostMapping("/formularioLogin")
	public String formularioLogin(@ModelAttribute(name = "usuario") Usuario usuario) {		
		return "redirect:/landingPage";
	}

	@GetMapping("/getImgByUser/{id}")
	public String getImgByUser(@PathVariable(name = "id") Long id, Model model) {
		String userImgURL = defaultUserURL;
		String userImgCandidate = usuarioService.getImgByUser(id);
		if (userImgCandidate != "") {
			userImgURL = userImgCandidate;
		}
		return userImgURL;
	}

}


/*

	@PostMapping("/formularioRegistro")
	public String formularioRegistro() {

		if (!avatar.isEmpty()) {

			// Ruta absoluta
			

			try {
				byte[] bytesImages = avatar.getBytes();

				// Ruta completa, que incluye el nombre original de la imagen

				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + avatar.getOriginalFilename());

				LOG.info("ruta completa la imagen" + rutaCompleta);

				Files.write(rutaCompleta, bytesImages);

				usuario.setAvatar(avatar.getOriginalFilename());

				usuarioService.guardaUsuario(usuario);

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		
		return "redirect:/lemonApp";

	}
	 */
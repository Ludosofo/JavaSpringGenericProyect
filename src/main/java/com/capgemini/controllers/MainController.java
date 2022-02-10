package com.capgemini.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.capgemini.entities.Oferta;
import com.capgemini.entities.Usuario;
import com.capgemini.servicies.IOfertaServ;
import com.capgemini.servicies.IUsuarioServ;

import org.apache.catalina.util.URLEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController implements Serializable {


	// SET VARIABLES
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(MainController.class);
	private static final String defaultUserURL = "";
	@Autowired private IUsuarioServ usuarioService;
	@Autowired private IOfertaServ ofertaService;

	@GetMapping()
	public ModelAndView getIndex(HttpServletResponse response, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("index");
		Usuario usuarioDefault = new Usuario();

		mav.addObject("usuario", usuarioDefault);

		if(request.getSession().getAttribute("user")!=null){
			// Tenemos usuario
			mav.addObject("MY_USER", request.getSession().getAttribute("MY_USER"));
			mav.addObject("PUBLIC_KEY", request.getSession().getAttribute("PUBLIC_KEY"));
		}
		// mav.addObject("absPath", imagesURL.toFile().getAbsolutePath());
		return mav;
	}

	// Procesamiento del registro y lanzamiento a pagina de redirección
	@PostMapping("/register")
	public ModelAndView saveUsuario(@ModelAttribute(name = "usuario") Usuario usuario, HttpServletResponse response, Model model) {
		System.out.println(usuario);
		ModelAndView mav = new ModelAndView("basic-msg");
		mav.addObject("redirect", "http://localhost:8080");
		try {
			usuarioService.save(usuario);
			mav.addObject("mensaje", "Usuario registrado correctamente");
			mav.addObject("miliseconds", "2000");
		} catch (Exception e) {
			System.out.println(usuario.toString());
			mav.addObject("mensaje", e.getMessage());
			mav.addObject("miliseconds", "9000");
		}
		return mav;
	}

	@PostMapping("/checkUsuario")
	public ModelAndView verifyCredentials(HttpServletResponse response, HttpServletRequest request, @ModelAttribute(name = "usuario") Usuario usuario) {
		ModelAndView mav = new ModelAndView();

		// 1. ¿Existe el usuario?
		Usuario cuenta = usuarioService.findUsuarioByAliasAndPass(usuario.getAlias(), usuario.getPass());

		if (cuenta != null) {

			// Tenemos usuario, creamos y pasamos session
			// HttpSession session = getSessionWithAliasAndPublicKey(request, cuenta.getAlias(), cuenta.getPass());
			
			// Solo con esto la session debería estar establecida
			mav.setViewName("welcome");

			// HttpSession session = request.getSession(true);
			// session.setAttribute("user", cuenta.getAlias());

			// Como JAVA se ejecuta desde el lado del backend no puede establecer sessión en el controller por lo que usaremos sessionStorage
			String jscript = "sessionStorage.setItem('user','"+cuenta.getAlias()+"');"+"sessionStorage.setItem('pass','"+cuenta.getPass()+"');";
			mav.addObject("jscript", jscript);
			mav.addObject("user", cuenta.toString());
		} else {
			// No tenemos usuario, mostramos mensaje y redirigimos
			mav.addObject("redirect", "/landingPage");
			mav.addObject("mensaje", "Las claves enviadas no son validas");
			mav.addObject("miliseconds", "3000");
			mav.addObject("jscript", "");
			mav.setViewName("basic-msg");
		}
		return mav;
	}

	// TO_REVIEW
	@GetMapping("/getImgByUser/{id}")
	public String getImgByUser(@PathVariable(name = "id") Long id, Model model) {
		String userImgURL = defaultUserURL;
		String userImgCandidate = usuarioService.getImgByUser(id);
		if (userImgCandidate != "") {
			userImgURL = userImgCandidate;
		}
		return userImgURL;
	}

	// TODO
	@GetMapping("saveAvatar")
	public void saveImg(@RequestParam(name = "file") MultipartFile avatar) {
	}

	// MUESTRA PRODUCTOS
	@GetMapping("/listaProductos")
	public ModelAndView listaProductos(@ModelAttribute(name = "oferta") Oferta oferta) {
		ModelAndView mav = new ModelAndView("listaProductos");
		mav.addObject("listaProductos", ofertaService.findAll());
		mav.addObject("oferta", new Oferta());
		return mav;
	}

	// SUBE PRODUCTOS
	@GetMapping("/subeProducto")
	public ModelAndView subirProducto(@ModelAttribute(name = "oferta") Oferta oferta) {
		ModelAndView mav = new ModelAndView("subeProducto");
		mav.addObject("oferta", new Oferta());
		return mav;
	}

	// CREA PRODUCTOS
	@PostMapping("/crearProducto")
	public String formCreacionProducto(@ModelAttribute(name = "oferta") Oferta oferta,
			@RequestParam(name = "file") MultipartFile imagen) {
		if (!imagen.isEmpty()) {
			String rutaAbsoluta = "//home//curso//FotosOfertas//RecursosBack";
			try {
				byte[] bytesImages = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				LOG.info("ruta completa:" + rutaCompleta);
				Files.write(rutaCompleta, bytesImages);
				oferta.setImagenes(imagen.getOriginalFilename());

				System.out.println(" >> Antes del error"); // TODO: Tenemos un error aquí
				ofertaService.save(oferta);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return "redirect:/listaProductos";

	}

	@GetMapping("/modificaProducto/{id}")
	public String formModificaProducto(@PathVariable(name = "id") Long id, Model model) {

		Oferta updateOferta = ofertaService.findById(id);
		model.addAttribute("listaProductos", ofertaService.findAll());
		model.addAttribute("updateOferta", updateOferta);
		return "modificarProducto";
	}

	@PostMapping("/modificaProducto")
	public String modificaProducto(@ModelAttribute(name = "oferta") Oferta oferta,
			@RequestParam(name = "file") MultipartFile imagen) {
		if (!imagen.isEmpty()) {
			String rutaAbsoluta = "//home//curso//FotosOfertas//RecursosBack";
			try {
				byte[] bytesImages = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				LOG.info("ruta completa la imgen" + rutaCompleta);
				Files.write(rutaCompleta, bytesImages);
				oferta.setImagenes(imagen.getOriginalFilename());
				ofertaService.save(oferta);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/listaProductos";
	}

	public HttpSession getSessionWithAliasAndPublicKey(HttpServletRequest request, String alias, String public_key) {
		request.getSession(true);
		request.getSession().setAttribute("MY_USER", alias);
		request.getSession().setAttribute("PUBLIC_KEY", public_key);
		return request.getSession();
	}

	public static String getMd5(String input) {
		try {
			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());
			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);
			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}
		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}

	/*
	 * - index - getUsuario ( obtener perfil ) - saveUsuario ( creación/edición ) -
	 * getListOfertas ( Hacer un filtro entre las ofertas ) - saveUsuarioImagen (
	 * Podría ir aparte y ser llamado por saveUsuario ) - saveOferta (
	 * creación/edición ) - saveContrato ( creación/edición ) - deleteOferta ( ) -
	 * deleteUsuario ( darse de baja ? ) - getContrato - deleteContrato ( borrar
	 * oferta SOLO si no está cerrado ) - saveValoracion ( creación/valoracion )
	 * 
	 * WARNING!!! Tenemmos que manejar un usuario conectado desde el cliente
	 * Esto requiere documentarse sobre seguridad y creación de cookies
	 */
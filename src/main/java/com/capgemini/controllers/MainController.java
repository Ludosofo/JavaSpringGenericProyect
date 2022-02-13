package com.capgemini.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.capgemini.entities.Oferta;
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

	// SET VARIABLES
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(MainController.class);
	private static final String defaultUserURL = "";
	@Autowired
	private IUsuarioServ usuarioService;
	@Autowired
	private IOfertaServ ofertaService;
	private AuxiliarFunctions auxFunctions;

	// Cambio para testear
	// Estás conectado ? Pues debería redireccionarnos a otra pagina

	public boolean confirmSession(HttpServletRequest request){
		String key = (String) request.getSession().getAttribute("PUBLIC_KEY");
		String user = (String) request.getSession().getAttribute("MY_USER");

		if(key!=null && user!=null){
			System.out.println(">>>>>>> SESSION VACIA");
			return false;
		}
		if(usuarioService.getUserByKey(key)!=null){
			System.out.println(">>>>> confirmamos el usuario");
			return true;
		}
		return false;
	}

	// NO REGISTRADOS van a la landingPage
	public ModelAndView landingPage(){
		return new ModelAndView("landingPage").addObject("usuario", new Usuario());
	}
	
	@GetMapping()
	public ModelAndView getIndex(HttpServletResponse response, HttpServletRequest request) {
		// Estructura que poner en todas las funciones de seguridad
		if(!confirmSession(request)){ return this.landingPage(); }
		return this.listaProductos(request);
	}

	// Procesamiento del registro y lanzamiento a pagina de redirección
	@PostMapping("/register")
	public ModelAndView saveUsuario(
		@ModelAttribute(name = "usuario") Usuario usuario,
		HttpServletResponse response,
		Model model){

		ModelAndView mav = new ModelAndView("basic-msg");
		mav.addObject("redirect", "http://localhost:8080");
		try {
			usuario.setPass(auxFunctions.getMd5(usuario.getPass()));
			usuario.setMail(usuario.getMail().toLowerCase()); // Nos aseguramos de que el correo este en minusculas
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
		Usuario cuenta = usuarioService.findUsuarioByAliasAndPass(usuario.getAlias(), usuario.getPass());

		if (cuenta != null) {	
			// Establecemos la session
			HttpSession session = request.getSession(true);

			System.out.println(cuenta.toString());
			session.setAttribute("MY_USER", cuenta.getAlias());
			session.setAttribute("PUBLIC_KEY", cuenta.getPass());

			// Pasamos JS que se guarda en cliente
			String jscript = "sessionStorage.setItem('user','" + cuenta.getAlias() + "');"
					+ "sessionStorage.setItem('pass','" + cuenta.getPass() + "');";
			mav.addObject("jscript", jscript);
			mav.addObject("user", cuenta.toString());
			System.out.println(">>>>>>>>>> Llamamos a Welcome");
			mav.setViewName("welcome");

			// Return to getIndex se carga la session
			//// return this.getIndex(response, request); // Retornamos el model and view de otro metodo
		} else {
			// No tenemos usuario, mostramos mensaje y redirigimos
			mav.addObject("redirect", "/");
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

	// MUESTRA PRODUCTOS
	// Antes esto tenia @ModelAttribute(name = "oferta") Oferta oferta
	// ¿POR QUÉ LISTA PRODUCTOS RECIBE OFERTA?
	@GetMapping("/listaProductos")
	public ModelAndView listaProductos( HttpServletRequest request ) {
		// confirmSession(request);
		ModelAndView mav = new ModelAndView("template");
		mav.addObject("content", "listaProductos");
		mav.addObject("listaProductos", ofertaService.findAll());
		mav.addObject("oferta", new Oferta());
		mav.addObject("MY_USER", request.getSession().getAttribute("MY_USER") );
		return mav;
	}

	// SUBE PRODUCTOS
	@GetMapping("/subeProducto")
	public ModelAndView subirProducto(@ModelAttribute(name = "oferta") Oferta oferta) {
		ModelAndView mav = new ModelAndView("subeProducto");
		mav.addObject("oferta", new Oferta());
		return mav;
	}

	@GetMapping("/modificaProducto/{id}")
	public String formModificaProducto(@PathVariable(name = "id") Long id, Model model) {

		Oferta updateOferta = ofertaService.findById(id);
		model.addAttribute("listaProductos", ofertaService.findAll());
		model.addAttribute("updateOferta", updateOferta);
		return "modificarProducto";
	}

	// CREA PRODUCTOS
	@PostMapping("/crearProducto")
	public String formCreacionProducto(
			@ModelAttribute(name = "oferta") Oferta oferta,
			@RequestParam(name = "file") MultipartFile imagen,
			HttpServletRequest request) {

		System.out.println(">> Mi usuario es " + request.getSession().getAttribute("MY_USER"));
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

	@GetMapping("/test")
	public ModelAndView test(HttpServletResponse response, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("test");

		// 0. Obtención de datos de getSession()
		// mav.addObject("MY_USER", request.getSession().getAttribute("MY_USER"));
		// mav.addObject("PUBLIC_KEY", request.getSession().getAttribute("PUBLIC_KEY"));
		// mav.addObject("MSG", request.getSession().getAttribute("MSG"));

		Usuario usuario = usuarioService.findById(1);
		mav.addObject("title", "Tratamos de obtener usuario 1");
		mav.addObject("data", usuario.toString());
		return mav;
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
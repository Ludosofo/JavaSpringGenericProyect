package com.capgemini.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.Cookie;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(MainController.class);
	private static final String defaultUserURL = "";
	private Path imagesURL = Paths.get("src//main//resources//static/images");

	@Autowired
	private IUsuarioServ usuarioService;
	@Autowired
	private IOfertaServ ofertaService;

	// Cambio para testear
	// Estás conectado ? Pues debería redireccionarnos a otra pagina

	@GetMapping()
	public ModelAndView getIndex(HttpServletResponse response, HttpServletRequest request) {
		System.out.println("getIndex()");
		ModelAndView mav = new ModelAndView("index");

		// Usuario precargado
		Usuario usuarioDefault = new Usuario();
		// usuarioDefault.setAlias("Nick");
		// usuarioDefault.setNombre("Nombre");
		// usuarioDefault.setPass("password");
		// usuarioDefault.setMail("correo@gmail.com");
		// usuarioDefault.setApellidos("Apellidos");

		mav.addObject("usuario", usuarioDefault); // <---- Necesario para que Thymeleaf sepa los datos que recoge
		mav.addObject("listaUsuarios", usuarioService.findAllByOrderByIdAsc());

		
		var attributeValue = request.getSession().getAttribute("MY_SESSION_MESSAGES");
		mav.addObject("parametro_session", attributeValue);
		// mav.addObject("absPath", imagesURL.toFile().getAbsolutePath());
		return mav;
	}

	// Procesamiento del registro y lanzamiento a pagina de redirección
	@PostMapping("/register")
	public ModelAndView saveUsuario(HttpServletResponse response, @ModelAttribute(name = "usuario") Usuario usuario,
			Model model) {
		ModelAndView mav = new ModelAndView("basic-msg");
		mav.addObject("redirect", "http://localhost:8080");
		try {
			usuarioService.save(usuario);
			mav.addObject("mensaje", "Usuario registrado correctamente");
			mav.addObject("miliseconds", "2000");
		} catch (Exception e) {
			mav.addObject("mensaje", e.getMessage());
			mav.addObject("miliseconds", "9000");
		}
		return mav;
	}

	@PostMapping("/checkUsuario")
	public ModelAndView verifyCredentials(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute(name = "usuario") Usuario usuario) {
		Usuario cuenta = usuarioService.findUsuarioByAliasAndPass(usuario.getAlias(), usuario.getPass());
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		
		
//		List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
//		messages.add("Mensaje 1");
//		messages.add("Mensaje 2");
//		request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);

		if (cuenta != null) {
			// response.addCookie(new Cookie("user", usuario.getAlias()));
			// response.addCookie(new Cookie("publicKey", "ASSSDSSDFSDFSFSDFSDFSDFSDFSSGGFDGDFSGDF"));
			session.setAttribute("JSESSIONID", "Session creada");
			mav.addObject("user", cuenta.toString() );
			mav.setViewName("welcome");
		} else {

			// Reset cookies
			response.addCookie(new Cookie("user", null));
			response.addCookie(new Cookie("publicKey", null));

			// Enviamos a la redirección
			mav.addObject("redirect", "http://localhost:8080");
			mav.addObject("mensaje", "Usuario erroneo");
			mav.addObject("miliseconds", "2000");
			mav.setViewName("basic-msg");
		}

		// mav.addObject( "datos-session", request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);)
		return mav;
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

	@GetMapping("saveAvatar")
	public void saveImg(@RequestParam(name = "file") MultipartFile avatar) {
	}
	

	// PARA MOSTRAR LA LISTA DE PRODUCTOS
	
	
	@GetMapping("/listaProductos")
	public ModelAndView listaProductos(@ModelAttribute(name = "oferta") Oferta oferta) {
		
//		oferta.toString();
		ModelAndView mav = new ModelAndView("listaProductos");

		mav.addObject("listaProductos", ofertaService.findAll());
		mav.addObject("oferta", new Oferta());
			
		
		return mav;
	}
	
	// PARA CREAR/MODIFICAR UN PRODUCTO
	
	
	@GetMapping("/subeProducto")
	public ModelAndView subirProducto(@ModelAttribute( name ="oferta") Oferta oferta) {
		
		ModelAndView mav = new ModelAndView("subeProducto");
		mav.addObject("oferta", new Oferta());
		
		
		return mav;
	}

	
	@PostMapping("/crearProducto")
	public String formCreacionProducto(@ModelAttribute(name = "oferta") Oferta oferta, @RequestParam(name = "file") MultipartFile imagen) {
			if(!imagen.isEmpty()) {
				String rutaAbsoluta = "//home//curso//FotosOfertas//RecursosBack"; 
				try {
					byte[] bytesImages = imagen.getBytes();					
					Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
					LOG.info("ruta completa:" + rutaCompleta);
					Files.write(rutaCompleta, bytesImages);
					oferta.setImagenes(imagen.getOriginalFilename());

					System.out.println(">> Antes del error");
					ofertaService.save(oferta);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		
		return "redirect:/listaProductos";

	}
	
	
	@GetMapping("/modificaProducto/{id}")
	public String formModificaProducto(@PathVariable (name = "id") Long id, Model model) {
		
		Oferta updateOferta = ofertaService.findById(id);
		model.addAttribute("listaProductos", ofertaService.findAll());
		model.addAttribute("updateOferta", updateOferta);
		return "modificarProducto";
	
		
	}
	
	@PostMapping("/modificaProducto")
	public String modificaProducto(@ModelAttribute(name = "oferta") Oferta oferta, @RequestParam(name = "file") MultipartFile imagen) {
		if(! imagen.isEmpty()) {
			
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

}


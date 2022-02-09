package com.capgemini.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	@PostConstruct
	public void initialize() {
		System.out.println(">>>>>> POST CONSTRUCT");
	}

	public void preHandle( HttpServletRequest request, HttpServletResponse response,  Object handler) throws Exception {
		System.out.println(">>>> PRE HANDLE");
	}
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
		System.out.println(">>>>>> GET INDEX");
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

		var attributeValue = request.getSession().getAttribute("MY_USER");
		mav.addObject("parametro_session", attributeValue);
		// mav.addObject("absPath", imagesURL.toFile().getAbsolutePath());
		return mav;
	}

	// Procesamiento del registro y lanzamiento a pagina de redirección
	@PostMapping("/register")
	public ModelAndView saveUsuario(HttpServletResponse response, @ModelAttribute(name = "usuario") Usuario usuario,
			Model model) {

				System.out.println(">>>>>> SAVE USUARIO");
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
		request.getSession().setAttribute("MY_USER", cuenta.getAlias());

		if (cuenta.getAlias() != null) {
			// response.addCookie(new Cookie("user", usuario.getAlias()));
			// response.addCookie(new Cookie("publicKey",
			// "ASSSDSSDFSDFSFSDFSDFSDFSDFSSGGFDGDFSGDF"));
			session.setAttribute("JSESSIONID", "Session creada");
			mav.addObject("user", cuenta.toString());
			mav.addObject("cookie", request.getSession().getAttribute("MY_USER"));
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

		// mav.addObject( "datos-session",
		// request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);)
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

	// Limpieza de cookies
	private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				resp.addCookie(cookie);
			}
	}

}

/*
 * // SUBIDA DE IMAGEN
 * 
 * @PostMapping("/formularioRegistro")
 * public String formularioRegistro() {
 * if (!avatar.isEmpty()) {
 * // Ruta absoluta
 * try {
 * byte[] bytesImages = avatar.getBytes();
 * // Ruta completa, que incluye el nombre original de la imagen
 * Path rutaCompleta = Paths.get(rutaAbsoluta + "//" +
 * avatar.getOriginalFilename());
 * LOG.info("ruta completa la imagen" + rutaCompleta);
 * Files.write(rutaCompleta, bytesImages);
 * usuario.setAvatar(avatar.getOriginalFilename());
 * usuarioService.guardaUsuario(usuario);
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * }
 * return "redirect:/lemonApp";
 * }
 */
package com.capgemini.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.capgemini.dao.IOfertaStatusDao;
import com.capgemini.entities.OfertaStatus;
import com.capgemini.servicies.IOfertaServ;
import com.capgemini.servicies.IOfertaStatusServ;
import com.capgemini.servicies.IUsuarioServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rebootProyect")
public class ProjectController {

	// TRUNCATE + INSERT INTO de datos por defecto del proyecto

	@Autowired
	private IUsuarioServ usuarioService;
	@Autowired
	private IOfertaServ ofertaService;
	@Autowired
	private IOfertaStatusServ ofertaStatusServ;

	@GetMapping()
	public ModelAndView getIndex() {

		// Dropeamos y creamos de nuevo la base de datos
		// String sql = "DROP TABLE the_final_project;";
		// entityManager.createNativeQuery(sql).executeUpdate();

		// Creamos los datos de oferta status automaticamente para testeo
		ofertaStatusServ.deleteAll();
		ofertaStatusServ.save(new OfertaStatus(0, "Activado"));
		ofertaStatusServ.save(new OfertaStatus(0, "Desactivado"));

		ModelAndView mav = new ModelAndView("projectInfo");
		return mav;
	}

	private void connAndExecuteQuery(String sql) {

		Environment.getProperty("spring.datasource.url");
		public final String db_url;

		@ConfigurationProperties("${spring.datasource.username}")
		private final String user;

		@lombok.Value("${spring.datasource.password}")
		private String pass;

		try (Connection conn = DriverManager.getConnection(db_url, user,);
				Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
			System.out.println("Database dropped successfully...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package com.capgemini.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="usuarios")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty(message = "ERROR su alias no puede estar vacio")
	@Size( min = 4, max = 20, message = "El nombre tiene que estar entre 4 y 20 caracteres")
	private String alias;
	
	@NotNull
	@NotEmpty(message = "ERROR su nombre no puede estar vacio")
	@Size( min = 4, max = 20, message = "El nombre tiene que estar entre 4 y 20 caracteres")
	private String nombre;
	
	@NotNull
	@NotEmpty(message = "ERROR su apellidos no puede estar vacio")
	@Size( min = 4, max = 20, message = "El nombre tiene que estar entre 4 y 20 caracteres")
	private String apellidos;
	
	@NotNull
	@NotEmpty(message = "ERROR su password no puede estar vacio")
	@Size( min = 8, message = "El password tiene que tener minimo 8 caracteres")
	private String pass; // TODO: Investigar sobre seguridad
	
	@NotNull
	private String mail;

	// Estos atributos pueden ser null
	private String telefono;
	private String geo;
	
	private String avatar; // Esto sería un dato obtenido de una imagen
	
	@OneToOne(cascade = CascadeType.ALL)
	private UsuarioImagen usuarioImage;
}

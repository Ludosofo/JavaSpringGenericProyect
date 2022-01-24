package com.capgemini.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Oferta implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private long id_usuario;
	
	@NotNull
	private  String status;
	
	@NotNull
	@NotEmpty(message = "Inserte un título para su oferta")
	@Size(min = 3, max = 50, message = "El título debe contener entre 3 y 50 caracteres")
	private String titulo;
	
	@NotNull
	@NotEmpty(message = "Inserte una descripción para su oferta")
	@Size(min = 3, max = 400, message = "La descripción del producto debe contenter entre  3 y 400 caracteres")
	private String descripcion;
	
	@NotNull
	@NotEmpty(message = "Inserte una descripción para su oferta")
	@Size(min = 3, max = 400, message = "La descripción del producto debe contenter entre  3 y 400 caracteres")
	private Double precio;
	
	@ManyToOne
	public Usuario usuario;
	
	
} 
	
	



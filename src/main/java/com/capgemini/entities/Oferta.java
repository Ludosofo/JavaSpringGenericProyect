package com.capgemini.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="ofertas")
public class Oferta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@NotEmpty(message = "Inserte un título para su oferta")
	@Size(min = 3, max = 50, message = "El título debe contener entre 3 y 50 caracteres")
	private String titulo;
	
	@NotNull
	@NotEmpty(message = "Inserte una descripción para su oferta")
	@Size(min = 3, max = 300, message = "La descripción debe contenter entre 12 y 300 caracteres")
	private String descripcion = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vitae pharetra est. Vivamus sollicitudin fringilla ullamcorper. tristique id dui in, dictum mattis risus. Donec rhoncus tellus vitae ligula sagittis aliquet.";

	private double precio = 0.99;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	public Usuario usuario;

	public String imagenes;
} 
	
	



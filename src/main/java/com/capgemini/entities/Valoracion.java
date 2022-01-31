package com.capgemini.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="valoraciones")
public class Valoracion implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long id_vendedor;
	
	@NotNull
	private Long id_usuario;

	@NotNull
	private float stars;
	
	@NotNull
	@ManyToOne
	public Usuario usuario; 

}


/** @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) **/
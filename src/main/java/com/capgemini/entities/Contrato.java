package com.capgemini.entities;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name="contratos")
public class Contrato implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private long id;
	@NotNull
	private long usuarioOfertante;
	@NotNull
	private long usuarioReceptor;
	@NotNull
	@NotEmpty(message = "Inserte precio")
	private Double precio;
	private String status;
	private LocalDate fecha_creacion;
	
	@ManyToOne 
	private Usuario usuario;
	
	
	
}

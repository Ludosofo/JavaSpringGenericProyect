package com.capgemini.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
<<<<<<< Upstream, based on branch 'main' of https://github.com/Ludosofo/BackendProyectoFinal.git

	
	@NotNull
	@ManyToOne
	@JoinColumn(name="nuestrasValoraciones")
	private Usuario usuario; 
=======
>>>>>>> b149c66 Base de datos ya creada y relacioada correctamente
	
	@NotNull
	private Long id_vendedor;
	
<<<<<<< Upstream, based on branch 'main' of https://github.com/Ludosofo/BackendProyectoFinal.git

=======
	@NotNull
>>>>>>> b149c66 Base de datos ya creada y relacioada correctamente
	private Long id_usuario;

<<<<<<< Upstream, based on branch 'main' of https://github.com/Ludosofo/BackendProyectoFinal.git
=======
	@NotNull
>>>>>>> b149c66 Base de datos ya creada y relacioada correctamente
	private float stars;
	
	@NotNull
	@ManyToOne
	public Usuario usuario; 

}


/** @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) **/
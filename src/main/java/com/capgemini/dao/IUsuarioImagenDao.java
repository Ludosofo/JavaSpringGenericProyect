package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.UsuarioImagen;

@Repository
public interface IUsuarioImagenDao extends JpaRepository<UsuarioImagen, Long> {

}

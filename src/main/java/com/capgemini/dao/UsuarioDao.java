package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{

}

package com.capgemini.servicies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.capgemini.entities.Usuario;

public interface IUsuarioServicies {
    public List<Usuario> findAll();
	public Usuario findById(long id);
    public void delete(long id);
    public void save(Usuario oferta);
}

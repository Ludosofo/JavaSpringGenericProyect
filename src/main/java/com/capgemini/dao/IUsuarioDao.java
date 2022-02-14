package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Usuario;
@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, Long>{

    // Hacer una comprobación
    @Query("SELECT u FROM Usuario u WHERE u.alias = ?1 and u.pass = ?2")
	public Usuario findUsuarioByAliasAndPass(String alias, String pass);

    @Query("SELECT u FROM Usuario u WHERE u.pass = ?1")
	public Usuario getUsuarioByKey(String public_key);

    @Query("SELECT u FROM Usuario u WHERE u.alias = ?1")
	public Usuario getUsuarioByName(String name);
    
}
	

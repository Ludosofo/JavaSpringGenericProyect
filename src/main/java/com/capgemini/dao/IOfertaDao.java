package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.capgemini.entities.Oferta;
import com.capgemini.entities.Usuario;

@Repository
public interface IOfertaDao extends JpaRepository<Oferta, Long> {
    // @Query("SELECT o FROM Oferta o WHERE o.usuario_id = ?1")
	// public List<Oferta> findAllByUser(Long usuario_id);
}

package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.OfertaImagenes;

@Repository
public interface IOfertaImagenesDao extends JpaRepository<OfertaImagenes, Long> {

}

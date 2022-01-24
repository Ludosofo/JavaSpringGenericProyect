package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Oferta;

@Repository
public interface IOfertaDao extends JpaRepository<Oferta, Long> {

}

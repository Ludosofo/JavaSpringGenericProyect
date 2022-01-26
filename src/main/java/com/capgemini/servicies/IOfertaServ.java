package com.capgemini.servicies;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.entities.Oferta;

public interface IOfertaServ extends JpaRepository<Oferta, Long> {

}

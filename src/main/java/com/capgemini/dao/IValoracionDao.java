package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Valoracion;

@Repository
public interface IValoracionDao extends JpaRepository<Valoracion, Long> {

}

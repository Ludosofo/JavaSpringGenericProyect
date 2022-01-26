package com.capgemini.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.capgemini.entities.ContratoStatus;

@Repository
public interface IContratoStatusDao extends JpaRepository<ContratoStatus, Long> {

}

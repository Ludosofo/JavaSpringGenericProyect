package com.capgemini.servicies.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IOfertaDao;

import com.capgemini.entities.Oferta;
import com.capgemini.servicies.IOfertaServ;

@Service
public class OfertaServImpl implements IOfertaServ{

	// No sé porque esto se borro o_O!!
	@Autowired
	private IOfertaDao daoOferta;

	@Override
	public List<Oferta> findAll() {
		// TODO Auto-generated method stub
		return daoOferta.findAll();
	}

	@Override
	public Oferta findById(long id) {
		// TODO Auto-generated method stub
		return daoOferta.getById(id);
	}

	@Override
	public void delete(long id) {
		daoOferta.deleteById(id);
		
	}

	@Override
	public void save(Oferta oferta) {
		// TODO Auto-generated method stub
		daoOferta.save(oferta);
		
	}
}
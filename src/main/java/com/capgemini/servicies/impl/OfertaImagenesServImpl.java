package com.capgemini.servicies.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IOfertaImagenesDao;
import com.capgemini.entities.OfertaImagenes;
import com.capgemini.servicies.IOfertaImagenesServ;
@Service
public class OfertaImagenesServImpl implements IOfertaImagenesServ {
	
	
	@Autowired
	private IOfertaImagenesDao daoOfertaImagenes;

	
	
	
	@Override
	public List<OfertaImagenes> imagenesOferta() {
		// TODO Auto-generated method stub
		return null;
	}

}

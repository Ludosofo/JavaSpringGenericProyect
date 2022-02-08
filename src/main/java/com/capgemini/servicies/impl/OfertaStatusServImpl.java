package com.capgemini.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.capgemini.dao.IOfertaStatusDao;
import com.capgemini.entities.OfertaStatus;
import com.capgemini.servicies.IOfertaStatusServ;

@Service
public class OfertaStatusServImpl implements IOfertaStatusServ {

	@Autowired
	private IOfertaStatusDao daoOfertaStatus;

	public List<OfertaStatus> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfertaStatus getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save( OfertaStatus ofertaStatus) {
		daoOfertaStatus.save( ofertaStatus ); 
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		daoOfertaStatus.deleteAll();
		
	}

}

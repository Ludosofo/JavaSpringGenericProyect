package com.capgemini.servicies.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IContratoDao;
import com.capgemini.entities.Contrato;
import com.capgemini.servicies.IContratoServ;

@Service
public class ContratoServImpl implements IContratoServ {
	
	@Autowired
	private IContratoDao daoContrato;

	@Override
	public List<Contrato> findAll() {
		// TODO Auto-generated method stub
		return daoContrato.findAll();
	}

	@Override
	public Contrato findById(long id) {
		// TODO Auto-generated method stub
		return daoContrato.getById(id);
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		daoContrato.deleteById(id);

	}

	@Override
	public void save(Contrato contrato) {
		// TODO Auto-generated method stub
		daoContrato.save(contrato);

	}

}

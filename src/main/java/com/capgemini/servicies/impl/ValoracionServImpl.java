package com.capgemini.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IValoracionDao;
import com.capgemini.servicies.IValoracionServ;

@Service
public class ValoracionServImpl implements IValoracionServ {
	
	@Autowired
	private IValoracionDao daoValoracion;

	@Override
	public String getValoracion() {
		// TODO Auto-generated method stub
		return null;
	}

}

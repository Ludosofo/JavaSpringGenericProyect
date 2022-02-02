package com.capgemini.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IContratoStatusDao;
import com.capgemini.servicies.IContratoStatusServ;


@Service
public class ContratoStatusServImpl implements IContratoStatusServ {
	
	
	@Autowired
	private IContratoStatusDao daoContratoStatus;

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}

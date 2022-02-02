package com.capgemini.servicies.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IUsuarioImagenDao;
import com.capgemini.entities.UsuarioImagen;
import com.capgemini.servicies.IUsuarioImagenServ;

@Service
public class UsuarioImagenServImpl implements IUsuarioImagenServ {
	
	@Autowired
	private IUsuarioImagenDao daoUsuarioImagen;

	@Override
	public List<UsuarioImagen> imagenes() {
		// TODO Auto-generated method stub
		return null;
	}

}

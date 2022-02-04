package com.capgemini.servicies.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IUsuarioDao;
import com.capgemini.entities.Usuario;
import com.capgemini.servicies.IUsuarioServ;

@Service
public class UsuarioServImpl implements IUsuarioServ{

	@Autowired
	private IUsuarioDao daoUsuario;	// ATENTION: No funcionaba daoUsuario.findAll(); Porque no tenia Autowired!!!

	@Override
	public List<Usuario> findAll() {
		return daoUsuario.findAll();
	}

	@Override
	public Usuario findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Usuario oferta) {
		// TODO Auto-generated method stub
		
	}

	// TODO: Buscar URL respecto al usuario, aunque podriamos usar UsuarioImagenServImpl no sé
	@Override
	public String getImgByUser(long id) {
		// TODO Auto-generated method stub
		return "TODO: UsuarioServImpl.getImgByUser(${id})";
	}

}

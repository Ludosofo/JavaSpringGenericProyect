package com.capgemini.servicies.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IUsuarioDao;
import com.capgemini.entities.Usuario;
import com.capgemini.servicies.IUsuarioServ;



// TODO
@Service
public class UsuarioServImpl implements IUsuarioServ{
	private IUsuarioDao daoUsuario;
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

}

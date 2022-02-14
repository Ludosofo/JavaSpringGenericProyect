package com.capgemini.servicies.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.controllers.AuxiliarFunctions;
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
		
		return daoUsuario.getById(id);
	}

	@Override
	public void delete(long id) {
		
		daoUsuario.deleteById(id);
		
	}

	@Override
	public void save(Usuario oferta) {
		
		daoUsuario.save(oferta);

	}

	//  Creo que habia la anotation @query y con ? se le podian enviar las variables
	@Override
	public String getImgByUser(long id) {
		
		return "TODO: UsuarioServImpl.getImgByUser(${id})";
	}

	@Override
	public void guardaUsuario(Usuario usuario) {
		daoUsuario.save(usuario);	
	}
	
//	@Override
//	// Pone ASC pero es DESCENTE JAJSAJSJAJSJASJAJSJASAjaj! SOY SUBNORMAL!!!
//	public List<Usuario> findAllByOrderByIdAsc() {
//		return daoUsuario.findAll().stream().sorted(Comparator.comparing(Usuario::getId).reversed()).collect(Collectors.toList());
//	}

	@Override
	public Usuario findUsuarioByAliasAndPass(String alias, String pass) {
		pass = AuxiliarFunctions.getMd5( pass );
		System.out.println(">>>findUsuarioByAliasAndPass("+alias+" "+pass+")");
		return daoUsuario.findUsuarioByAliasAndPass(alias, pass);
	}

	@Override
	public List<Usuario> findAllByOrderByIdAsc() {
		
		return null;
	}

	@Override
	public Usuario getUserByKey(String key) {
		
		return null;
	}

}

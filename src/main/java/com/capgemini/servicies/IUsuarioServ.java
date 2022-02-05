package com.capgemini.servicies;



import java.util.List;

import com.capgemini.entities.Usuario;

public interface IUsuarioServ {
    public List<Usuario> findAll();
	public Usuario findById(long id);
    public void delete(long id);
    public void save(Usuario oferta);
    public String getImgByUser(long id);
    public void guardaUsuario(Usuario usuario);
}

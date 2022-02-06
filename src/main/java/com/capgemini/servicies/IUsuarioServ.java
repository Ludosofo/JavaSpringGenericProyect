package com.capgemini.servicies;



import java.util.List;

import com.capgemini.entities.Usuario;

import org.springframework.data.domain.Sort;

public interface IUsuarioServ {
    public List<Usuario> findAll();
    // public List<Usuario> findAll(String column, Sort sort);
    public List<Usuario> findAllByOrderByIdAsc(); // Experimento
	public Usuario findById(long id);
    public void delete(long id);
    public void save(Usuario oferta);
    public String getImgByUser(long id);
    public void guardaUsuario(Usuario usuario);
}

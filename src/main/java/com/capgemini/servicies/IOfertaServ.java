package com.capgemini.servicies;



import java.util.List;
import com.capgemini.entities.Oferta;
import com.capgemini.entities.Usuario;

public interface IOfertaServ {
    public List<Oferta> findAll();
    public List<Oferta> findAllByUser(Usuario usuario);
	public Oferta findById(long id);
    public void delete(long id);
    public void save(Oferta oferta);
}
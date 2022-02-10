package com.capgemini.servicies;



import java.util.List;
import com.capgemini.entities.Oferta;

public interface IOfertaServ {
    public List<Oferta> findAll();
	public Oferta findById(long id);
    public void delete(long id);
    public void save(Oferta oferta);
}
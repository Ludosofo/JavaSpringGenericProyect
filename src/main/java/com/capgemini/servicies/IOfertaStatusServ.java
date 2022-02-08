package com.capgemini.servicies;

import java.util.List;

import com.capgemini.entities.OfertaStatus;

public interface IOfertaStatusServ {
	
	public List<OfertaStatus> findAll();
	public void save( OfertaStatus ofertaStatus);
	public OfertaStatus getById(Long id);
	public void deleteAll();

}

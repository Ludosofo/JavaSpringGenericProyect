package com.capgemini.servicies;

import java.util.List;

import com.capgemini.entities.Contrato;

public interface IContratoServ {
    public List<Contrato> findAll();
	public Contrato findById(long id);
    public void delete(long id);
    public void save(Contrato contrato);
}

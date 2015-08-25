package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CodigoFonte {
	private int id;

	private String codigoFonte;
	private String nomeClasse;
	private Date dataColecao;
	
	List<Metrica> metricas = new ArrayList<Metrica>();

	public String getCodigoFonte() {
		return codigoFonte;
	}

	public void setCodigoFonte(String codigoFonte) {
		this.codigoFonte = codigoFonte;
	}

	public Date getDataColecao() {
		return dataColecao;
	}

	public void setDataColecao(Date dataColecao) {
		this.dataColecao = dataColecao;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public List<Metrica> getMetricas() {
		return metricas;
	}

	public void setMetricas(List<Metrica> metricas) {
		this.metricas = metricas;
	}
}

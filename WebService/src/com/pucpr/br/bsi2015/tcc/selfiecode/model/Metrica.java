package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.ArrayList;
import java.util.List;

public class Metrica {
	
	private float valorMetrica;
	private String nomeMetrica;
	private String sigla;
	private List<Intervalo> intervalos;
	private List<Dica> dicas = new ArrayList<Dica>();
	
	public Metrica()
	{
		setIntervalos(new ArrayList<Intervalo>());
	}
	
	public float getValorMetrica() {
		return valorMetrica;
	}
	public void setValorMetrica(float valorMetrica) {
		this.valorMetrica = valorMetrica;
	}
	public String getNomeMetrica() {
		return nomeMetrica;
	}
	public void setNomeMetrica(String nomeMetrica) {
		this.nomeMetrica = nomeMetrica;
	}

	public List<Intervalo> getIntervalos() {
		return intervalos;
	}

	public void setIntervalos(List<Intervalo> intervalos) {
		this.intervalos = intervalos;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public List<Dica> getDicas() {
		return dicas;
	}

	public void setDicas(List<Dica> dicas) {
		this.dicas = dicas;
	}
	
	
}

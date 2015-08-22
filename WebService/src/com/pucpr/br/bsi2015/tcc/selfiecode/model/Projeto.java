package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.Date;

public class Projeto {
	private int id;
	private String nome;
	private String descricao;
	private String status;
	private Date inicio;
	private Date fim;
	private int tempoParaColeta;
	
	public Projeto()
	{
		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date dataInicio) {
		this.inicio = dataInicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date dataFim) {
		this.fim = dataFim;
	}
	public int getTempoParaColeta() {
		return tempoParaColeta;
	}
	public void setTempoParaColeta(int tempoParaColeta) {
		this.tempoParaColeta = tempoParaColeta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

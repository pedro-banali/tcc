package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.Date;

public class Projeto {
	private String nome;
	private String descricao;
	private String status;
	private Date dataInicio;
	private Date dataFim;
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
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
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
}

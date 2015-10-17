package com.pucpr.br.bsi2015.tcc.selfiecode.model;

public class Treino {
	private int idTreino;
	private String descricaoTreino;
	private int duracaoTreino;
	private String assunto;
	private String professor;
	
	public void cadastrarTreino()
	{
		
	}


	public String getDescricaoTreino() {
		return descricaoTreino;
	}


	public void setDescricaoTreino(String descricaoTreino) {
		this.descricaoTreino = descricaoTreino;
	}


	public int getDuracaoTreino() {
		return duracaoTreino;
	}


	public void setDuracaoTreino(int duracaoTreino) {
		this.duracaoTreino = duracaoTreino;
	}


	public String getAssunto() {
		return assunto;
	}


	public void setAssunto(String string) {
		this.assunto = string;
	}


	public int getIdTreino() {
		return idTreino;
	}


	public void setIdTreino(int idTreino) {
		this.idTreino = idTreino;
	}


	public String getProfessor() {
		return professor;
	}


	public void setProfessor(String professor) {
		this.professor = professor;
	}
	
}

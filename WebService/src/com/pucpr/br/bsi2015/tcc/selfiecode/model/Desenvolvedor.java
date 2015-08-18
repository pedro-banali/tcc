package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.List;

public class Desenvolvedor extends Usuario {
	private Usuario gerente;
	private String nivelProgramador;
	private List<Treino> treinos;
	private List<Projeto> projetos;

	public String getNivelProgramador() {
		return nivelProgramador;
	}

	public void setNivelProgramador(String nivelProgramador) {
		this.nivelProgramador = nivelProgramador;
	}

	public List<Treino> getTreinos() {
		return treinos;
	}

	public void setTreinos(List<Treino> treinos) {
		this.treinos = treinos;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public Usuario getGerente() {
		return gerente;
	}

	public void setGerente(Usuario g) {
		this.gerente = g;
	}
	
}

package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.List;

public class Desenvolvedor {
	private String nivelProgramador;
	private List<Treino> treinos;

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
	
}

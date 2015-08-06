package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.List;

public class Gerente extends Usuario {
	private List<Projeto> projetos;

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}
	
}

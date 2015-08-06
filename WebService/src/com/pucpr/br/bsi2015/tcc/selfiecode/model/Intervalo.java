package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.Date;

public class Intervalo {
	private float valorMinimo;
	private float valorMax;
	private Date dataIntervalo;
	
	public void verificarValorIntervalo()
	{
		
	}

	public float getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(float valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public float getValorMax() {
		return valorMax;
	}

	public void setValorMax(float valorMax) {
		this.valorMax = valorMax;
	}

	public Date getDataIntervalo() {
		return dataIntervalo;
	}

	public void setDataIntervalo(Date dataIntervalo) {
		this.dataIntervalo = dataIntervalo;
	}
	
}

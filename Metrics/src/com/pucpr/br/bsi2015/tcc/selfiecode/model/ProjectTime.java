package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.Timer;
import java.util.TimerTask;

import com.pucpr.br.bsi2015.tcc.selfiecode.controller.SessionController;

public class ProjectTime {
	private String nomeProjeto;
	static int interval, intervaloN;

	static Timer timer;

	public ProjectTime(String nome, int min) {
		this.nomeProjeto = nome;
		intervaloN = min;
		timer = new Timer(this.nomeProjeto);
		reset();

	}

	public void reset() {

		int delay = 1000;
		int period = 1000;
		timer = new Timer(this.nomeProjeto);
		interval = (intervaloN * 60);
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				setInterval();

			}
		}, delay, period);

	}

	private final int setInterval() {
		if (interval < 0)
		{
			timer.cancel();
		}
		return --interval;
	}

	public static int getInterval() {
		return interval;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

}

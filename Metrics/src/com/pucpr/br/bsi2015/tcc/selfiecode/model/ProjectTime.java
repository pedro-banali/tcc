package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.Timer;
import java.util.TimerTask;

import com.pucpr.br.bsi2015.tcc.selfiecode.controller.SessionController;

public class ProjectTime {
	private String nomeProjeto;
	private int interval, intervaloN;

	private Timer timer;
	private TimerTask tt;
	public ProjectTime(String nome, int min) {
		this.nomeProjeto = nome;
		intervaloN = min;
		timer = new Timer(this.nomeProjeto);
		reset();

	}

	public void reset() {

		int delay = 1000;
		int period = 1000;
		interval = (intervaloN * 60);
		timer = new Timer(this.nomeProjeto); 
		tt = new TimerTask() {

			public void run() {
				setInterval();

			};
		};
		timer.schedule(tt, delay, period);

	}
	public void restart()
	{
		if (interval < 0)
		{
			tt.cancel();
			timer.cancel();
			timer.purge();
			this.reset();
		}
	}
	private final int setInterval() {
		return --interval;
	}

	public int getInterval() {
		return interval;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

}

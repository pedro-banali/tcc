package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class ThreadMetricsControl extends Thread {
	
	private Object o = null;
	public ThreadMetricsControl() {
		super("Controle de Projetos");
	}
	
	public void run() {
		System.out.println("Thread startou");
		
		
			if(o == null)
			{
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				//System.out.println("tem o");
				AbstractMetricSource c = (AbstractMetricSource) o;

				MetricsController mc = MetricsController.getInstance();

				try {
					String result = mc.dicas(c);
					System.out.println(result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	
	
	public void setMetricsList(Object o)
	{
		this.o = o;
	}
}

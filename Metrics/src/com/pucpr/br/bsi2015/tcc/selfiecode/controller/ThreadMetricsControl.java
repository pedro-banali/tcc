package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.json.JSONObject;

import com.pucpr.br.bsi2015.tcc.selfiecode.filecontroller.FileController;

import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class ThreadMetricsControl extends Thread {
	IJavaElement currentElm;
	private Object o = null;

	public ThreadMetricsControl(IJavaElement currentElm) {
		super("Controle de Projetos");
		this.currentElm = currentElm;
	}

	// public ThreadMetricsControl() {
	// super("Controle de Projetos");
	//
	// }
	//
	public void run() {
		System.out.println("Thread startou");

		if (o == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// System.out.println("tem o");
			AbstractMetricSource c = (AbstractMetricSource) o;
			FileController fc = new FileController();
			// workspace.getRoot().getFullPath().toString();
			fc.saveZip(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()
					+ currentElm.getJavaProject().getPath().toOSString() + "/src/");

			MetricsController mc = MetricsController.getInstance();
			// FileController.getInstance().saveZip(currentElm.getResource().getLocation().toOSString());
			try {
				String result = mc.dicas(c);
				//mc.uploadFile(fc.getFilePath());

				JSONObject json = new JSONObject(result);
				Iterator valor = json.keys();
				
		        //based on you key types

		        String keyvalue =  json.getString("WMC");

		        //Print key and value
		     
		        Object firstElement = currentElm.getJavaProject();
				if (firstElement instanceof IJavaProject) {
					IJavaProject type = (IJavaProject) firstElement;
					writeMarkers(type, keyvalue);

					
			        //for nested objects iteration if required
			       
			           
			    }		

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void writeMarkers(IJavaProject type, String message) {
		try {
			IResource resource = type.getUnderlyingResource();
			IMarker marker = resource.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.MESSAGE, message);
			// marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMetricsList(Object o) {
		this.o = o;
	}
}

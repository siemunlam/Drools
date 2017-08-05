package com.siem.unlam;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
//import java.io.FileWriter;

//import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;


public class Prueba {
	private static String url;
	private static String puerto;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
		factoryBean.setResourceClasses(Service.class);
		
		factoryBean.setResourceProvider(
				  new SingletonResourceProvider(new Service()));
		
		cargarConfiguracion();
		factoryBean.setAddress(url + ":" + puerto +"/");
		System.out.println(url + ":" + puerto +"/");
		Server server = factoryBean.create();
		
		System.out.println("Server ready...");
        Thread.sleep(60 * 1000 * 60);
        System.out.println("Server exiting");
        server.destroy();
        System.exit(0);
	}
	
	 public static void cargarConfiguracion() {
			try{
				String ruta = "configuracionMotor.txt";
		        File archivo = new File(ruta);
		        
		        BufferedReader br = new BufferedReader(new FileReader(archivo));
				url = br.readLine().trim();
		        puerto = br.readLine().trim();
		        br.close();
			}catch(Exception e){
				e.printStackTrace();
			}
	    }
	    
	
}

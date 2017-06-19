package com.siem.unlam;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;


public class Prueba {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
		factoryBean.setResourceClasses(Service.class);
		
		factoryBean.setResourceProvider(
				  new SingletonResourceProvider(new Service()));
		
		factoryBean.setAddress("http://localhost:8081/");
		
		Server server = factoryBean.create();
		
		System.out.println("Server ready...");
        Thread.sleep(60 * 1000 * 60);
        System.out.println("Server exiting");
        server.destroy();
        System.exit(0);
	}
	
}

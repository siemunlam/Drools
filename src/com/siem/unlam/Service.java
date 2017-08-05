package com.siem.unlam;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.codehaus.jettison.json.JSONObject;

@Path("serviciosSoporte")
public class Service {
 
	@POST
    @Path("obtenerCategoria/")
	public String obtenerCategoria(String inputjson){
		System.out.println("1. Solicitud de categorizacion.");
		Procesamiento procesamiento = new Procesamiento();
		HashMap<String, String> valores = new HashMap<>();
		try{
			System.out.println("2. Inputjson: " + inputjson);
			inputjson = URLDecoder.decode(inputjson, "utf-8");
			//System.out.println("Entro inputjson: " + inputjson);
			String[] parameters = inputjson.split("&");
			//System.out.println("Entro inputjson: " + parameters);
			String[] values = parameters[0].split("=");
			//System.out.println("Entro inputjson: " + values);
			JSONObject obj = new JSONObject(values[1]);
			Iterator<String> keys = obj.keys();
			while(keys.hasNext()){
				String key = keys.next();
				valores.put(key, obj.getString(key));
				System.out.println("3. Categorizar (datos): KEY: " + key + " VALUE: " + obj.getString(key));
			}
			Persona persona = new Persona(valores);
			
			return procesamiento.devolverCategoria(persona);
		}catch(Exception e){
			System.out.println("error (obtenerCategoria) " + e);
			return "error (obtenerCategoria) " + e;
		}
	}
	
	@POST
    @Path("actualizarReglas/")
	public boolean actualizarReglas(String reglas){
		System.out.println("1. Solicitud de actualizacion archivo de reglas.");
		Procesamiento procesamiento = new Procesamiento();
		try{
			reglas = URLDecoder.decode(reglas, "utf-8");
			String[] parameters = reglas.split("&");
			String[] values = parameters[0].split("=");
			return procesamiento.saveText(values[1]);
		}catch(Exception e){
			System.out.println("error (actualizarReglas) " + e);
			return false;
		}
	}
}
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
		HashMap<String, String> valores = new HashMap<>();
		try{
			inputjson = URLDecoder.decode(inputjson, "utf-8");
			String[] parameters = inputjson.split("&");
			String[] values = parameters[0].split("=");
			JSONObject obj = new JSONObject(values[1]);
			Iterator<String> keys = obj.keys();
			while(keys.hasNext()){
				String key = keys.next();
				valores.put(key, obj.getString(key));
			}
			
			Persona persona = new Persona(valores);
			
			return Procesamiento.devolverCategoria(persona);
		}catch(Exception e){
			return "";
		}
	}
}
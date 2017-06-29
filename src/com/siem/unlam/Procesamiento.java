package com.siem.unlam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.cxf.helpers.FileUtils;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.io.impl.ClassPathResource;
import org.drools.rule.Package;

public class Procesamiento {

    public static final void main(String[] args) throws Exception {
        //Cargamos la base de reglas
    	Procesamiento procesamiento = new Procesamiento();
        RuleBase ruleBase = procesamiento.leerReglas();
        WorkingMemory workingMemory = ruleBase.newStatefulSession();

        //Obtenemos los empleados
        Collection<Persona> personas = procesamiento.buscarPersonas();

        for (Persona persona : personas) {
            workingMemory.insert(persona);
        }

        //Disparamos las reglas de Drools
        workingMemory.fireAllRules();

        for (Persona persona : personas) {
            System.out.println(persona.toString());
        }
    }
    
    public Procesamiento(){
    	
    }
    
    public boolean saveText(String reglas) {
		try{
			String ruta = "src/com/siem/unlam/Rules.drl";
	        File archivo = new File(ruta);
	        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
			String reglas_mod = reglas.replaceAll(":::", "=");
	        bw.write(reglas_mod);
	        bw.close();
	        return true;
		}catch(Exception e){
			return false;
		}
    }
    
    private Collection<Persona> buscarPersonas() {
        ArrayList<Persona> personas = new ArrayList<Persona>();

        //Creamos algunos empleados para el ejemplo
        Persona persona = new Persona();
        persona.setDato("Sangrado", "Masivo");
        //persona.setDato("Edad", "Mayor de 65 años");
        personas.add(persona);

        return personas;
    }
    
    public String devolverCategoria(Persona persona) {
    	try{
    		RuleBase ruleBase = leerReglas();
            WorkingMemory workingMemory = ruleBase.newStatefulSession();
            
            workingMemory.insert(persona);
            
            workingMemory.fireAllRules();
            
            return persona.getCategoria();
    	}catch(Exception e){
    		return "";
    	}   
    }


    private RuleBase leerReglas() throws Exception {
        //Leemos el archivo de reglas (DRL)
    	System.out.println("1");
    	FileInputStream fis = new FileInputStream("src/com/siem/unlam/Rules.drl");
		Reader source = new InputStreamReader(fis);
        System.out.println("3");
        //Construimos un paquete de reglas
        PackageBuilder builder = new PackageBuilder();

        //Parseamos y compilamos las reglas en un Ãºnico paso
        builder.addPackageFromDrl(source);

        // Verificamos el builder para ver si hubo errores
        if (builder.hasErrors()) {
            System.out.println(builder.getErrors().toString());
            throw new RuntimeException("No se pudo compilar el archivo de reglas.");
        }

        //Obtenemos el package de reglas compilado
        Package pkg = builder.getPackage();

        //Agregamos el paquete a la base de reglas (desplegamos el paquete de reglas).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(pkg);
        return ruleBase;
    }
}

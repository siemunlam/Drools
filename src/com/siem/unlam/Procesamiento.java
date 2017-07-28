package com.siem.unlam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;


public class Procesamiento {

    public static final void main(String[] args) throws Exception {
        //Cargamos la base de reglas
    	
    	Procesamiento procesamiento = new Procesamiento();
    	KnowledgeBase kbase = readKnowledgeBase();
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

        //Obtenemos los empleados
        Collection<Persona> personas = procesamiento.buscarPersonas();

        for (Persona persona : personas) {
        	ksession.insert(persona);
        }

        //Disparamos las reglas de Drools
        ksession.fireAllRules();

        for (Persona persona : personas) {
            System.out.println(persona.toString());
        }
    }
    
    public Procesamiento(){
    	
    }
    
    public boolean saveText(String reglas) {
		try{
			String ruta = "Rules.drl";
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
        	KnowledgeBase kbase = readKnowledgeBase();
    		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();	
            
    		ksession.insert(persona);
            
    		ksession.fireAllRules();
            System.out.println("categoria determinada::"+ persona.getCategoria());
            return persona.getCategoria();
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    		return "";
    	}   
    }

    private static KnowledgeBase readKnowledgeBase() {
    	System.out.println("1");
		final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		System.out.println("2");
		//kbuilder.add(ResourceFactory.newClassPathResource("Rules.drl"), ResourceType.DRL);
		kbuilder.add(ResourceFactory.newFileResource(new File("Rules.drl")), ResourceType.DRL);
		
		System.out.println("3");
		if (kbuilder.hasErrors()) {
			for (KnowledgeBuilderError error : kbuilder.getErrors()) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Imposible crear knowledge con Reglas.drl");
		}
		final KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
}
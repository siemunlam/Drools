package com.siem.unlam;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

public class Procesamiento {

    public static final void main(String[] args) throws Exception {
        //Cargamos la base de reglas
        RuleBase ruleBase = leerReglas();
        WorkingMemory workingMemory = ruleBase.newStatefulSession();

        //Obtenemos los empleados
        Collection<Persona> personas = buscarPersonas();

        for (Persona persona : personas) {
            workingMemory.insert(persona);
        }

        //Disparamos las reglas de Drools
        workingMemory.fireAllRules();

        for (Persona persona : personas) {
            System.out.println(persona.toString());
        }
    }

    
    private static Collection<Persona> buscarPersonas() {
        ArrayList<Persona> personas = new ArrayList<Persona>();

        //Creamos algunos empleados para el ejemplo
        Persona persona1 = new Persona("20 - 80", "0");
        persona1.addListCategoria("verde");
        persona1.addListCategoria("amarillo");
        persona1.addListCategoria("rojo");
        persona1.addListCategoria("negro");
        
        Persona persona2 = new Persona("0 - 20", "1");
        persona2.addListCategoria("verde");
        persona2.addListCategoria("amarillo");
        persona2.addListCategoria("rojo");
        persona2.addListCategoria("negro");
        
        Persona persona3 = new Persona("20 - 80", "2");
        persona3.addListCategoria("verde");
        persona3.addListCategoria("amarillo");
        persona3.addListCategoria("rojo");
        persona3.addListCategoria("negro");

        personas.add(persona1);
        personas.add(persona2);
        personas.add(persona3);

        return personas;
    }


    private static RuleBase leerReglas() throws Exception {
        //Leemos el archivo de reglas (DRL)
        Reader source = new InputStreamReader(Procesamiento.class.getResourceAsStream("Rules.drl"));

        //Construimos un paquete de reglas
        PackageBuilder builder = new PackageBuilder();

        //Parseamos y compilamos las reglas en un único paso
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

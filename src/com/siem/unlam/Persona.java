package com.siem.unlam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Persona {

	private String mPreCategoria;
	private Integer mAjuste;
	private String mCategoria;
    private Map<String, String> mDatos;
    private ArrayList<String> mListCategorias;
    
    public Persona(String edad, String peso) {
    	mDatos = new HashMap<>();
    	mListCategorias = new ArrayList<>();
    	setDato("edad", edad);
    	setDato("peso", peso);
    	setPreCategoria("verde");
    	setAjuste(0);
    }

    @Override
    public String toString() {
        return "Categoria: " + getPreCategoria() + " - Ajuste: " + getAjuste() + " - Cat: " + getCategoria();
    }

    public String getDato(String key) {
        return (mDatos.containsKey(key) ? mDatos.get(key) : "");
    }

    public void setDato(String key, String value) {
    	mDatos.put(key, value);
    }
    
    public String getPreCategoria(){
    	return mPreCategoria;
    }
    
    public void setPreCategoria(String categoria){
    	mPreCategoria = categoria;
    }
    
    public Integer getAjuste(){
    	return mAjuste;
    }
    
    public void setAjuste(Integer ajuste){
    	this.mAjuste = ajuste;
    }
    
    public String getCategoria(){
    	return mCategoria;
    }
    
    public void setCategoria(String categoria){
    	this.mCategoria = categoria;
    }
    
    public void addListCategoria(String categoria){
    	mListCategorias.add(categoria);
    }
    
    public void processCategoria(){
    	int i = 0;
    	for(i = 0; i < mListCategorias.size(); i++){
    		if(mListCategorias.get(i).equals(getPreCategoria())){
    			break;
    		}
    	}
    	i += getAjuste();
    	if(i < 0)
    		i = 0;
    	if(i >= mListCategorias.size())
    		i = mListCategorias.size() - 1;
    	
    	setCategoria(mListCategorias.get(i));
    }

}

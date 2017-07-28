package com.siem.unlam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Persona {

	private String mPreCategoria;
	private int mAjuste;
	private String mCategoria;
    private Map<String, String> mDatos;

    public Persona() {
    	this(new HashMap<String, String>());
    }
    
    public Persona(HashMap<String, String> datos) {
    	mDatos = datos;
    	mPreCategoria = "";
    	mAjuste = 0;
    	mCategoria = "";
    }

    @Override
    public String toString() {
        return "Precategoria: " + getPreCategoria() + " - Ajuste: " + getAjuste() + " - Cat: " + getCategoria();
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
    
    public int getAjuste(){
    	return mAjuste;
    }
    
    public void setAjuste(int ajuste){
    	this.mAjuste = Integer.valueOf(ajuste);
    }
    
    public String getCategoria(){
    	return mCategoria;
    }
    
    public void setCategoria(String categoria){
    	this.mCategoria = categoria;
    }
    
    public void procesarCategoria(String menorCategoria, ArrayList<String> mListCategorias){
    	if(mPreCategoria.equals(""))
    		mPreCategoria = menorCategoria;
    	int i = 0;
    	for(i = 0; i < mListCategorias.size(); i++){
    		if(mListCategorias.get(i).equals(getPreCategoria())){
    			break;
    		}
    	}
    	i += Integer.valueOf(getAjuste());
    	if(i < 0)
    		i = 0;
    	if(i >= mListCategorias.size())
    		i = mListCategorias.size() - 1;
    	
    	setCategoria(mListCategorias.get(i));
    }

}
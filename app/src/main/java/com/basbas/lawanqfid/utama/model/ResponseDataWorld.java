package com.basbas.lawanqfid.utama.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDataWorld{

	@SerializedName("attributes")
	private Attributes attributes;

	public void setAttributes(Attributes attributes){
		this.attributes = attributes;
	}

	public Attributes getAttributes(){
		return attributes;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDataWorld{" + 
			"attributes = '" + attributes + '\'' + 
			"}";
		}
}
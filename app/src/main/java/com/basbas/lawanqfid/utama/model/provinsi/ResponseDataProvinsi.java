package com.basbas.lawanqfid.utama.model.provinsi;

import com.google.gson.annotations.SerializedName;

public class ResponseDataProvinsi{

	@SerializedName("attributes")
	private AttributesProvinsi attributesProvinsi;

	public void setAttributesProvinsi(AttributesProvinsi attributesProvinsi){
		this.attributesProvinsi = attributesProvinsi;
	}

	public AttributesProvinsi getAttributesProvinsi(){
		return attributesProvinsi;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDataProvinsi{" + 
			"attributes = '" + attributesProvinsi + '\'' +
			"}";
		}
}
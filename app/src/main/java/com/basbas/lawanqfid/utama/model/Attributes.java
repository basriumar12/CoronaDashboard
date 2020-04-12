package com.basbas.lawanqfid.utama.model;

import com.google.gson.annotations.SerializedName;

public class Attributes{

	@SerializedName("OBJECTID")
	private int oBJECTID;


	@SerializedName("Recovered")
	private int recovered;

	@SerializedName("Country_Region")
	private String countryRegion;

	@SerializedName("Active")
	private int active;

	@SerializedName("Last_Update")
	private long lastUpdate;

	@SerializedName("Deaths")
	private int deaths;

	@SerializedName("Confirmed")
	private int confirmed;

	@SerializedName("Lat")
	private double lat;

	public void setOBJECTID(int oBJECTID){
		this.oBJECTID = oBJECTID;
	}

	public int getOBJECTID(){
		return oBJECTID;
	}



	public void setRecovered(int recovered){
		this.recovered = recovered;
	}

	public int getRecovered(){
		return recovered;
	}

	public void setCountryRegion(String countryRegion){
		this.countryRegion = countryRegion;
	}

	public String getCountryRegion(){
		return countryRegion;
	}

	public void setActive(int active){
		this.active = active;
	}

	public int getActive(){
		return active;
	}

	public void setLastUpdate(long lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public long getLastUpdate(){
		return lastUpdate;
	}

	public void setDeaths(int deaths){
		this.deaths = deaths;
	}

	public int getDeaths(){
		return deaths;
	}

	public void setConfirmed(int confirmed){
		this.confirmed = confirmed;
	}

	public int getConfirmed(){
		return confirmed;
	}

	public void setLat(double lat){
		this.lat = lat;
	}

	public double getLat(){
		return lat;
	}


}
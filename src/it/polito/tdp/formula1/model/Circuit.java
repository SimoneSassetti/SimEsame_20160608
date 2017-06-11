package it.polito.tdp.formula1.model;

import com.javadocmd.simplelatlng.LatLng;

public class Circuit {
	
	private int circuitId ;
	private String circuitRef ;
	private String name ;
	private String location ;
	private String country ;
	private LatLng coord;
	
	public Circuit(int circuitId, String circuitRef, String name, String location, String country, LatLng coord) {
		super();
		this.circuitId = circuitId;
		this.circuitRef = circuitRef;
		this.name = name;
		this.location = location;
		this.country = country;
		this.coord = coord;
	}
	
	public int getCircuitId() {
		return circuitId;
	}
	public void setCircuitId(int circuitId) {
		this.circuitId = circuitId;
	}
	public String getCircuitRef() {
		return circuitRef;
	}
	public void setCircuitRef(String circuitRef) {
		this.circuitRef = circuitRef;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public LatLng getCoord() {
		return coord;
	}
	public void setCoord(LatLng coord) {
		this.coord = coord;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + circuitId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Circuit other = (Circuit) obj;
		if (circuitId != other.circuitId)
			return false;
		return true;
	}
}

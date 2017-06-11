package it.polito.tdp.formula1.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;

public class Race {
	
	private int raceId ;
	private Year year ;
	private int round ;
	private Circuit circuit;
	private String name ;
	private LocalDate date ;
	private LocalTime time ;
	private String url ;
	
	public Race(int raceId, Year year, int round, Circuit circuit, String name, LocalDate date, LocalTime time,
			String url) {
		super();
		this.raceId = raceId;
		this.year = year;
		this.round = round;
		this.circuit = circuit;
		this.name = name;
		this.date = date;
		this.time = time;
		this.url = url;
	}
	//DUE COSTRUTTORI XK ALCUNI CAMPI NEL DB SONO NULL IN TIME E DAREBBE ERRORE
	public Race(int raceId, Year year, int round, Circuit circuit, String name, LocalDate date, String url) {
		super();
		this.raceId = raceId;
		this.year = year;
		this.round = round;
		this.circuit = circuit;
		this.name = name;
		this.date = date;
		this.url = url;
	}

	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public Circuit getCircuit() {
		return circuit;
	}
	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}

package it.polito.tdp.formula1.model;

import java.time.*;

public class DriverAndTempi{
	
	private Year anno;
	private LapTime tempo;
	
	public DriverAndTempi(Year anno, LapTime tempo) {
		super();
		this.anno = anno;
		this.tempo = tempo;
	}
	public Year getAnno() {
		return anno;
	}
	public void setAnno(Year anno) {
		this.anno = anno;
	}
	public LapTime getTempo() {
		return tempo;
	}
	public void setTempo(LapTime tempo) {
		this.tempo = tempo;
	}
	
	@Override
	public String toString() {
		return anno.toString();
	}
	
}

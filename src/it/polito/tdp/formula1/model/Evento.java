package it.polito.tdp.formula1.model;

import java.time.Year;

public class Evento implements Comparable<Evento>{
	
	private Year anno;
	private int lap;
	private int tempo;//in millisecondi
	
	public Evento(Year anno, int lap, int tempo) {
		super();
		this.anno = anno;
		this.lap = lap;
		this.tempo = tempo;
	}
	public Year getAnno() {
		return anno;
	}
	public void setAnno(Year anno) {
		this.anno = anno;
	}
	public int getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	@Override
	public int compareTo(Evento e) {
		return this.tempo-e.tempo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anno == null) ? 0 : anno.hashCode());
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
		Evento other = (Evento) obj;
		if (anno == null) {
			if (other.anno != null)
				return false;
		} else if (!anno.equals(other.anno))
			return false;
		return true;
	}
	
}

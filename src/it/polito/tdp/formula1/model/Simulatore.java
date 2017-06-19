package it.polito.tdp.formula1.model;

import java.time.*;
import java.util.*;

public class Simulatore {
	
	private Map<Year,Integer> classifica;//fanta-punti
	private PriorityQueue<Evento> coda;
	private List<DriverAndTempi> tempi;
	private Map<Integer, List<Year>> posizione;//tiene conto di come varia la posizione durante i giri
	private int numPiloti=0;
	private int lastLap;
	
	public Simulatore(List<DriverAndTempi> tempi, int lastLap){
		this.tempi=tempi;
		this.lastLap=lastLap;
		
		posizione=new HashMap<Integer,List<Year>>();
		for(int i=1; i<=lastLap;i++){
			posizione.put(i, new ArrayList<Year>());
		}
		classifica=new HashMap<Year,Integer>();
		
		for(DriverAndTempi t: tempi){
			if(!classifica.containsKey(t.getAnno())){
				classifica.put(t.getAnno(), 0);
			}
		}
		coda=new PriorityQueue<Evento>();
	}
	
	public void inserisci(){
		List<DriverAndTempi> temp=new ArrayList<DriverAndTempi>();
		
		for(DriverAndTempi d: tempi){
			if(d.getTempo().getLap()==1){
				coda.add(new Evento(d.getAnno(),d.getTempo().getLap(),d.getTempo().getMiliseconds()));
				temp.add(d);
			}
		}
		class Comparatore implements Comparator<DriverAndTempi>{
			@Override
			public int compare(DriverAndTempi d1, DriverAndTempi d2) {
				return d1.getTempo().getMiliseconds()-d2.getTempo().getMiliseconds();
			}
		}
		Collections.sort(temp,new Comparatore());
		numPiloti=temp.size();
		
		List<Year> listaAnni=new ArrayList<Year>();
		for(DriverAndTempi d: temp){
			listaAnni.add(d.getAnno());
		}
		posizione.put(1, listaAnni);
	}
	
	public void run(){
		int bestLap=0;//il primo a che giro si trova?
		
		while(!coda.isEmpty()){
			
			Evento e=coda.poll();
			
			Year y=e.getAnno();
			int lap=e.getLap();
			List<Year> temp=posizione.get(lap);
			if(temp.isEmpty()){
				bestLap=e.getLap();
			}
			temp.add(y);
			int pos=0;
			for(Year pilota: temp){
				pos++;
				if(pilota.compareTo(y)==0){
					break;
				}
			}
			int trovato=0;
			if(e.getLap()>1){
				List<Year> prec=posizione.get(e.getLap()-1);
				trovato=0;
				for(Year pilota: prec){
					trovato++;
					if(pilota.compareTo(y)==0){
						break;
					}
				}
			}
			if(pos<trovato){
				int punti=classifica.get(e.getAnno());
				punti+=1;
				classifica.put(e.getAnno(), punti);
			}
			
			if(e.getLap()<=bestLap-2){
				classifica.remove(e.getAnno());
				
			}else{
				for(DriverAndTempi d: tempi){
					if(d.getAnno().equals(e.getAnno()) && d.getTempo().getLap()==e.getLap()+1){
						coda.add(new Evento(e.getAnno(),e.getLap()+1,e.getTempo()+d.getTempo().getMiliseconds()));
						break;
					}
				}
			}	
		}
	}
	
	public Map<Year,Integer> getClassifica(){
		return classifica;
	}
}

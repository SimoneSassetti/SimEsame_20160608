package it.polito.tdp.formula1.model;

import java.time.*;
import java.util.*;

public class Simulatore {
	
	private Map<Year,Integer> classifica;//fanta-punti
	private PriorityQueue<Evento> coda;
	private List<DriverAndTempi> tempi;
	private Map<Year,Integer> posizione;//tiene conto di come varia la posizione durante i giri
	private int numPiloti=0;;
	
	public Simulatore(List<DriverAndTempi> tempi){
		this.tempi=tempi;
		
		posizione=new HashMap<Year,Integer>();
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
		int i=1;
		for(DriverAndTempi d: temp){
			posizione.put(d.getAnno(), i);
			i++;
		}
	}
	
	public void run(){
		int contaPassaggi=0;
		int bestLap=0;//il primo a che giro si trova?
		
		while(!coda.isEmpty()){
			
			if(contaPassaggi>=numPiloti)
				contaPassaggi=0;
			
			Evento e=coda.poll();
			contaPassaggi++;
			if(contaPassaggi==1){
				bestLap=e.getLap();
			}
			
			if(contaPassaggi<posizione.get(e.getAnno())){
				int punti=classifica.get(e.getAnno());
				punti+=1;
				classifica.put(e.getAnno(), punti);
				posizione.put(e.getAnno(), contaPassaggi);
			}
			
			if(e.getLap()<=bestLap-2){
				classifica.remove(e.getAnno());
				posizione.remove(e.getAnno());
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

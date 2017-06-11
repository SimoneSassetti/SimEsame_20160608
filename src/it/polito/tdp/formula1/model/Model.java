package it.polito.tdp.formula1.model;

import java.time.Year;
import java.util.*;

import it.polito.tdp.formula1.db.F1DAO;

public class Model {
	
	F1DAO dao;
	private List<Season> stagioni;
	private List<Circuit> circuitiPerStagione;
	private Map<Integer,Circuit> mappaCircuiti;
	private List<Driver> piloti;
	private List<DriverAndTempi> tempiPilota;
	
	public Model(){
		dao=new F1DAO();
		mappaCircuiti=new HashMap<>();
	}
	
	public List<Season> getStagioni() {
		if(stagioni==null){
			stagioni=dao.getAllSeasons();
		}
		return stagioni;
	}
	
	public List<Circuit> getCircuiti(Year y){
		piloti=new ArrayList<Driver>();
		circuitiPerStagione=dao.getCircuitiPerAnno(y,mappaCircuiti);
		
		return circuitiPerStagione;
	}

	public Race getGara(Season s, Circuit c) {
		return dao.getGara(s,c,mappaCircuiti);
	}

	public List<Driver> getPiloti(Race r) {
		piloti=dao.getPiloti(r);
		return piloti;
	}

	public Map<Year, Integer> simula(Driver d, Race r) {
		tempiPilota=dao.getTempiPilota(d,r);
		
		Simulatore sim=new Simulatore(tempiPilota);
		
		sim.inserisci();
		sim.run();
		
		return sim.getClassifica();
	}

	
}

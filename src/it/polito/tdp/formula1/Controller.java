package it.polito.tdp.formula1;

import java.net.URL;
import java.time.Year;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.formula1.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class Controller {
	Model model;
	public void setModel(Model model){
		this.model=model;
		
		boxSeason.getItems().addAll(model.getStagioni());
	}
	private Race r;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Circuit> boxCircuit;

    @FXML
    private ComboBox<Driver> boxDriver;

    @FXML
    private ComboBox<Season> boxSeason;

    @FXML
    private TextArea txtResult;
    
    @FXML
    void doTendina(ActionEvent event) {
    	Season s=boxSeason.getValue();
    	if(s==null){
    		txtResult.appendText("Errore! Selezionare una stagione dal menu.\n");
    		return;
    	}
    	boxCircuit.getItems().addAll(model.getCircuiti(s.getYear()));
    }
    
    @FXML
    void doInfoGara(ActionEvent event) {
    	txtResult.clear();
    	Season s=boxSeason.getValue();
    	if(s==null){
    		txtResult.appendText("Errore! Selezionare una stagione dal menu.\n");
    		return;
    	}
    	Circuit c=boxCircuit.getValue();
    	if(c==null){
    		txtResult.appendText("Errore! Selezionare un circuito.\n");
    		return;
    	}
    	
    	r=model.getGara(s,c);
    	List<Driver> piloti=model.getPiloti(r);
    	txtResult.appendText("Gara selezionata:\n"+r.getName()+" "+r.getUrl());
    	txtResult.appendText("\nPiloti partecipanti:\n");
    	for(Driver d: piloti){
    		txtResult.appendText(d.getForename()+" "+d.getSurname()+" - "+d.getDob().toString()+", "+d.getNationality()+"\n"); 
    	}
    	boxDriver.getItems().addAll(piloti);
    }
    
    @FXML
    void doFantaGara(ActionEvent event) {
    	txtResult.clear();
    	
    	Driver d=boxDriver.getValue();
    	if(d==null){
    		txtResult.appendText("Errore! Selezionare una pilota dal menu.\n");
    		return;
    	}
    	txtResult.appendText("Classifica per "+d.getSurname()+":\n");
    	Map<Year,Integer> mappa=model.simula(d,r);
    	
    	for(Year y: mappa.keySet()){
    		txtResult.appendText(y.toString()+" --> punti: "+mappa.get(y)+"\n");
    	}
    	
    }
    
    @FXML
    void initialize() {
        assert boxCircuit != null : "fx:id=\"boxCircuit\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert boxDriver != null : "fx:id=\"boxDriver\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert boxSeason != null : "fx:id=\"boxSeason\" was not injected: check your FXML file 'Formula1.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Formula1.fxml'.";
    }
}

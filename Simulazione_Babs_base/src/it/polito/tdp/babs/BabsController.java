package it.polito.tdp.babs;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.babs.model.Model;
import it.polito.tdp.babs.model.SimulationResult;
import it.polito.tdp.babs.model.Simulazione;
import it.polito.tdp.babs.model.Statistics;
import it.polito.tdp.babs.model.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class BabsController {

	private Model model;

	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private DatePicker pickData;

	@FXML
	private Slider sliderK;

	@FXML
	private TextArea txtResult;

	@FXML
	void doContaTrip(ActionEvent event) {

		txtResult.clear();
		LocalDate ld = pickData.getValue();
		//non abbiamo il problema che si nullo perchè seleziona sempre una data
		//facciamo comunque il controllo
		if(ld == null){
			txtResult.appendText("Selezionare una data.\n");
			return;
		}
		List<Statistics> stats = model.getStats(ld);
		Collections.sort(stats);
		//l'ordinamento funziona perchè in statistics ho un compare to
		for(Statistics s : stats){
			if(s.getPick()==0){
				txtResult.appendText(String.format("ERRORE! Stazione %s con 0 picks\n", s.getStation().getName()));
			}else{
				if(s.getDrop()==0){
					txtResult.appendText(String.format("ERRORE! Stazione %s con 0 drops\n", s.getStation().getName()));
				}else
					txtResult.appendText(String.format("%s %d %d\n", s.getStation().getName(), s.getPick(), s.getDrop()));
			}
		}
	}

	@FXML
	void doSimula(ActionEvent event) {

		txtResult.clear();
		LocalDate ld = pickData.getValue();
		//non abbiamo il problema che si nullo perchè seleziona sempre una data
		//facciamo comunque il controllo
		if(ld == null || ld.getDayOfWeek()== DayOfWeek.SATURDAY ||ld.getDayOfWeek()== DayOfWeek.SUNDAY){
			txtResult.appendText("Selezionare un giorno feriale.\n");
			return;
		}
		Double k = (Double)sliderK.getValue()/100.00;
		
		SimulationResult stats = model.avviaSimulazione(k, ld);
		txtResult.appendText("Numero Trips totali: "+stats.getNumeroTripsTot()+"\nNumero di pick falliti: "+stats.getNumberOfPickMiss()+"\nNumero di drop falliti: "+stats.getNumberOfDropMiss()+"\n");
		
		
		
		
	}

	@FXML
	void initialize() {
		assert pickData != null : "fx:id=\"pickData\" was not injected: check your FXML file 'Babs.fxml'.";
		assert sliderK != null : "fx:id=\"sliderK\" was not injected: check your FXML file 'Babs.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Babs.fxml'.";

		pickData.setValue(LocalDate.of(2013, 9, 1));
	}
}

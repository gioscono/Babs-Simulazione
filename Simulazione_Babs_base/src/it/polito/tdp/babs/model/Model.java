package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.babs.db.BabsDAO;

public class Model {
	
	List<Station> stazioni ;
	BabsDAO dao;
	public Model(){
		dao = new BabsDAO();
	}
	
	public List<Station> getStazioni(){
		if(stazioni == null){
			stazioni = dao.getAllStations();
		}
		return stazioni;
		
	}
	
	public List<Statistics> getStats(LocalDate ld){
		
		List<Statistics> stats = new ArrayList<Statistics>();
		for(Station s: getStazioni()){
			int picks = dao.getPickNumber(s, ld);
			int drops = dao.getDropNumber(s, ld);
			Statistics stat = new Statistics(s, picks, drops);
			stats.add(stat);
		}
		
		return stats;
		
	}

	public List<Trip> getTripsForDayPick(LocalDate ld) {
		return dao.getTripsForDayPick(ld);
	}
	
	public List<Trip> getTripsForDayDrop(LocalDate ld) {
		return dao.getTripsForDayDrop(ld);
	}

	public SimulationResult avviaSimulazione(Double k, LocalDate ld) {
		
		
		List<Trip> tripsPick = this.getTripsForDayPick(ld);
		List<Trip> tripsDrop = this.getTripsForDayDrop(ld);
		
		Simulazione simulazione = new Simulazione();
		simulazione.loadPicks(tripsPick);
		simulazione.loadDrops(tripsDrop);
		simulazione.loadStations(k, this.getStazioni());
		simulazione.run();
		
		
		
		
		
		return simulazione.collectResults();
	}

}

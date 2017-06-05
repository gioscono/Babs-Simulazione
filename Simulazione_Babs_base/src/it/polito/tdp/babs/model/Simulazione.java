package it.polito.tdp.babs.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.mchange.util.Queue;

public class Simulazione{
	
	
	List<Station> stazioni;
	SimulationResult simulationResult;
	Map<Integer, Integer> mappa;
	Map<Integer, Station> mappaStazioni;
	int numeroPickPersi;
	int numeroDropPersi;
	
	private enum EventType{
		PICK, DROP;
	}
	PriorityQueue<Event> pt;
	
	public Simulazione(){
		pt = new PriorityQueue<Event>();
		simulationResult = new SimulationResult();
		mappa = new HashMap<Integer, Integer>();
		mappaStazioni = new HashMap< Integer, Station>();
		numeroPickPersi = 0;
		numeroDropPersi = 0;
	}
	
	public void run(){
		
		while(!pt.isEmpty()){
			Event e = pt.poll();
			
			switch(e.type){
				
			case PICK:
				if(mappa.get(e.trip.getStartStationID())>0){
					mappa.replace(e.trip.getStartStationID(), mappa.get(e.trip.getStartStationID()), mappa.get(e.trip.getStartStationID())-1);
				}else{
					this.numeroPickPersi++;
				}
			
			case DROP:
				if(mappaStazioni.get(e.trip.getEndStationID()).getDockCount()-mappa.get(e.trip.getEndStationID())>0){
					mappa.replace(e.trip.getEndStationID(), mappa.get(e.trip.getEndStationID()), mappa.get(e.trip.getEndStationID())+1);
				}else{
					this.numeroDropPersi++;
				}
			}
		}
		this.simulationResult.setNumberOfDropMiss(numeroDropPersi);
		this.simulationResult.setNumberOfPickMiss(numeroPickPersi);
		
	}
	
	public SimulationResult collectResults(){
		return simulationResult;
		
	}
	
	//creiamo la classe simulazione come ANNIDATA A SIMULAZIONE perchè viene usata solo da questa
	//si poteva benissimo metterla come classe esterna
	private class Event implements Comparable<Event>{
		EventType type;
		LocalDateTime ldt;
		Trip trip;
				
		public Event(EventType type, LocalDateTime startDate, Trip trip) {
			this.type = type;
			this.ldt = startDate;
			this.trip = trip;
		}

		@Override
		public int compareTo(Event altro) {
			if(this.ldt.isAfter(altro.ldt))
				return +1;
			return -1;
		}
	}


	public void loadPicks(List<Trip> tripsPick) {
		for(Trip trip: tripsPick){
			pt.add(new Event(EventType.PICK, trip.getStartDate(), trip));
		}
		
	}
	public void loadDrops(List<Trip> tripsDrop) {
		for(Trip trip: tripsDrop){
			pt.add(new Event(EventType.DROP, trip.getEndDate(), trip));
		}
		this.simulationResult.setNumeroTripsTot(tripsDrop.size());
		
	}

	public void loadStations(Double k, List<Station> list) {
		
		this.stazioni = list;
		for(Station s: list){
			int numeroBici = (int) (k*s.getDockCount());
			mappa.put(s.getStationID(), numeroBici);
			mappaStazioni.put(s.getStationID(), s);
		}
	}
}

package it.polito.tdp.babs.model;

public class Statistics implements Comparable<Statistics>{
	
	private Station station;
	private int pick;
	private int drop;
	
	
	public Statistics(Station station, int pick, int drop) {
		this.station = station;
		this.pick = pick;
		this.drop = drop;
	}
	
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public int getPick() {
		return pick;
	}
	public void setPick(int pick) {
		this.pick = pick;
	}
	public int getDrop() {
		return drop;
	}
	public void setDrop(int drop) {
		this.drop = drop;
	}



	@Override
	public int compareTo(Statistics o) {
		// TODO Auto-generated method stub
		return Double.compare(this.station.getLat(),o.getStation().getLat());
	}

	
	
	

}

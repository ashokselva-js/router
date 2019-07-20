package com.example.demo.payload;

import org.springframework.stereotype.Service;

@Service
public class StartEndCoOrdinates {
	
	public StartEndCoOrdinates() {
	}
	public StartEndCoOrdinates(double startCoOdrinate, double endCoOrdinate) {
		this.startCoOdrinate = startCoOdrinate;
		this.endCoOrdinate = endCoOrdinate;
	}
	public double getStartCoOdrinate() {
		return startCoOdrinate;
	}
	public void setStartCoOdrinate(double startCoOdrinate) {
		this.startCoOdrinate = startCoOdrinate;
	}
	public double getEndCoOrdinate() {
		return endCoOrdinate;
	}
	public void setEndCoOrdinate(double endCoOrdinate) {
		this.endCoOrdinate = endCoOrdinate;
	}
	public double startCoOdrinate;
	public double endCoOrdinate;
	
	
}

package com.example.demo.payload;

import org.springframework.stereotype.Service;

@Service
public class Geopositions {
	
//	public Geopositions(double latitudes, double longitudes) {
//		this.latitudes = latitudes;
//		this.longitudes = longitudes;
//	}

	public Geopositions() {
		super();
	}

	public Geopositions(String tag, String date, String time, double latitudes, double longitudes, String height,
			String speed, String heading, String vox) {
		this.tag = tag;
		this.date = date;
		this.time = time;
		this.latitudes = latitudes;
		this.longitudes = longitudes;
		this.height = height;
		this.speed = speed;
		this.heading = heading;
		this.vox = vox;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getLatitudes() {
		return latitudes;
	}
	public void setLatitudes(double latitudes) {
		this.latitudes = latitudes;
	}
	public double getLongitudes() {
		return longitudes;
	}
	public void setLongitudes(double longitudes) {
		this.longitudes = longitudes;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getVox() {
		return vox;
	}
	public void setVox(String vox) {
		this.vox = vox;
	}

	public String tag;
	public String date;
	public String time;
	public double latitudes;
	public double longitudes;
	public String height;
	public String speed;
	public String heading;
	public String vox;
	
}

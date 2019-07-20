package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.service.LatLon;
import com.example.demo.service.LoadCSV;

@RestController
public class CoOrdinateController {
@Autowired
public LoadCSV load;
@Autowired
public LatLon latlon;


	@GetMapping("/test")
	public String test() {
		return "hi";
	}
	@GetMapping("/calculate")
	public double calculateDistance(@RequestParam double latitude,@RequestParam double longitude) {
//	latitude and longitude of point 13603
		double lt1= 40.7256;
		double ln1=-73.895934;
//		radius of earth in meter
		double R= 6371e3; 
		
		double l1Rad = Math.toRadians(lt1);
		double l2Rad= Math.toRadians(latitude);
		double dlt=Math.toRadians((latitude-lt1));
		double dln=Math.toRadians((longitude-ln1));
		
		double haversine = (Math.sin(dlt/2) * Math.sin(dlt/2)) + (Math.cos(l1Rad)*Math.cos(l2Rad)*Math.sin(dln/2)*Math.sin(dln/2)) ;
		
		double c = 2* Math.atan2(Math.sqrt(haversine),Math.sqrt(1-haversine));
		
		double distance = R * c;
		
		return distance;
	}
	
	
	@PostMapping("/test-csv/{axis}")
	public ResponseEntity<InputStreamResource>  csvParser(@PathVariable String axis,@RequestParam double x1 ,@RequestParam double x2,@RequestParam("file") MultipartFile file) throws Exception {
		File f = null;
		try {
//			load.test();
			f=latlon.controll(axis,x1,x2,file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 InputStreamResource resource = new InputStreamResource(new FileInputStream(f));
		 return ResponseEntity.ok()
	                // Content-Disposition
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
	                // Content-Type
	                .contentType(MediaType.parseMediaType("text/csv"))
//	                // Contet-Length
	                .contentLength(f.length())
	                .body(resource);
	}
}

package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.payload.Geopositions;
import com.opencsv.CSVWriter;

@Service
public class LatLon  {
	
	public File controll(String axis,double x1,double x2,MultipartFile dataFile) throws Exception {
		
		int i=0,j=0,k=0,l=0;
		double y1 = 0,y2 = 0;
		File newcsv = new File("src//main//resources//output_csv//Processed"+dataFile.getOriginalFilename()); 
	    FileWriter outputfile = new FileWriter(newcsv); 
	    
	    if(x1>x2) {
	    	y1=x1;
	    	y2=x2;
	    }else {y1=x2;y2=x1;}
	    
	    CSVWriter writer = new CSVWriter(outputfile, ',', 
                CSVWriter.NO_QUOTE_CHARACTER, 
                CSVWriter.NO_ESCAPE_CHARACTER, 
                CSVWriter.DEFAULT_LINE_END); 

	    String[] header = { "INDEX","TAG","DATE","TIME","LATITUDE N/S","LONGITUDE E/W","HEIGHT","SPEED","HEADING","VOX" }; 
	    writer.writeNext(header); 
    
	    File file = convert_MultiPartToFile(dataFile);
	    CSVParser parser = CSVParser.parse(file, Charset.forName("UTF-8"),CSVFormat.DEFAULT.withSkipHeaderRecord().withFirstRecordAsHeader());

	   List<CSVRecord> records = parser.getRecords();
	   	
	   List<Geopositions> coordinate= new ArrayList<>();
	   List<Geopositions> finalCoordinate= new ArrayList<>();
	   	
	   for (CSVRecord record : records) {
			String latSTR=record.get(4).replace("N", "");
			double latitude= Double.parseDouble(latSTR);
			String lonSTR =record.get(5).replace("W","");
			StringBuilder sb = new StringBuilder(lonSTR);
			sb.insert(0, "-");
			double longitude =Double.parseDouble(sb.toString());
			Geopositions geo = new Geopositions(record.get(1),record.get(2),record.get(3),latitude, longitude,record.get(6),record.get(7),record.get(8),record.get(9));
			coordinate.add(geo);
	   }
	   
	   if(axis.toLowerCase().equals("lon")) {
		  for(Geopositions geoCo: coordinate) {
		   		double ltc= coordinate.get(i).latitudes;
		   		double lnc=coordinate.get(i).longitudes;
//		   		if(-73.954336<lnc && -73.944393>lnc)
		   			if(-y1<lnc && -y2>lnc) {
		   				System.out.println("test----------------------------");
		   				Geopositions geoFinal =new Geopositions(coordinate.get(i).tag,coordinate.get(i).date,coordinate.get(i).time,ltc,lnc,coordinate.get(i).height,coordinate.get(i).speed,coordinate.get(i).heading,coordinate.get(i).vox);
			   			finalCoordinate.add(geoFinal);
						try {
						  String[] data1 = {Integer.toString(j),coordinate.get(i).tag,coordinate.get(i).date,coordinate.get(i).time,Double.toString(ltc).concat("N"),Double.toString(lnc).replace("-", "").concat("W"),coordinate.get(i).height,coordinate.get(i).speed,coordinate.get(i).heading,coordinate.get(i).vox}; 
						  writer.writeNext(data1); 
						  j++;
						}
						catch (Exception e) {
					  }
		   			}
		   	   i++;
		  }
	   }
	   else if (axis.toLowerCase().equals("lat")) {
		   for(Geopositions geopositions:finalCoordinate) {
			   double ltc= finalCoordinate.get(k).latitudes;
		   	   double lnc=finalCoordinate.get(k).longitudes;
		   		if (y2< ltc && y1>ltc ) {
		   			try {
		   			    String[] data1 = {Integer.toString(l),finalCoordinate.get(k).tag,finalCoordinate.get(k).date,finalCoordinate.get(k).time,Double.toString(ltc).concat("N"),Double.toString(lnc).replace("-", "").concat("W"),finalCoordinate.get(k).height,finalCoordinate.get(k).speed,finalCoordinate.get(k).heading,finalCoordinate.get(k).vox}; 
		   			    writer.writeNext(data1); 
		   			    l++;
		   				}
		   			catch (Exception e) {
	   				}
		   		}
		     k++;
	      }   
	   }
	   else throw new Exception();
	   
	   return newcsv;
//	   for(Geopositions geopositions:finalCoordinate) {
//		   double ltc= finalCoordinate.get(k).latitudes;
//	   		System.out.println("test:----------------------"+ltc);
//	   		double lnc=finalCoordinate.get(k).longitudes;
//	   		if (40.720464< ltc && 40.725559 >ltc ) {
//	   			try {
//	   			    String[] data1 = {Integer.toString(l),finalCoordinate.get(k).tag,finalCoordinate.get(k).date,finalCoordinate.get(k).time,Double.toString(ltc).concat("N"),Double.toString(lnc).replace("-", "").concat("W"),finalCoordinate.get(k).height,finalCoordinate.get(k).speed,finalCoordinate.get(k).heading,finalCoordinate.get(k).vox}; 
//	   			    
//	   			    writer.writeNext(data1); 
//	   			   
//	   			    l++;
//	   				}catch (Exception e) {
//	   				}
//	   		} k++;
//	   }
}
	
	public File convert_MultiPartToFile(MultipartFile File) throws IOException {
		File convFile = new File(File.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(File.getBytes());
		fos.close();
		return convFile;
	}
	
	
	
}
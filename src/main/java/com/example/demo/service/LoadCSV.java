package com.example.demo.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.example.demo.payload.Geopositions;
import com.example.demo.payload.StartEndCoOrdinates;
import com.opencsv.CSVWriter;
@Service
public class LoadCSV  {
		
	int i=0,j=0,f=0;
	double lt1= 40.720553;
	double ln1=-73.904795;
	int startIndex;
	int endIndex;
	List<StartEndCoOrdinates> ssc= new ArrayList<>();
	
	
	
	public void test() throws IOException {
		
		
		File newcsv = new File("src//main//resources//test.csv"); 
	    FileWriter outputfile = new FileWriter(newcsv); 
	    
	    CSVWriter writer = new CSVWriter(outputfile, ',', 
                CSVWriter.NO_QUOTE_CHARACTER, 
                CSVWriter.NO_ESCAPE_CHARACTER, 
                CSVWriter.DEFAULT_LINE_END); 

	    String[] header = { "INDEX","TAG","DATE","TIME","LATITUDE N/S","LONGITUDE E/W","HEIGHT","SPEED","HEADING","VOX" }; 
	    writer.writeNext(header); 
	    
		File rmco = new File("src//main//resources//removedCo.csv"); 
	    FileWriter rmcoOutputfile = new FileWriter(rmco); 
	    
	    CSVWriter rmWriter = new CSVWriter(rmcoOutputfile, ',', 
                CSVWriter.NO_QUOTE_CHARACTER, 
                CSVWriter.NO_ESCAPE_CHARACTER, 
                CSVWriter.DEFAULT_LINE_END); 

	    rmWriter.writeNext(header); 
	    
	    
	    
	 File file = new File("src//main//resources//19041001.txt");
	 CSVParser parser = CSVParser.parse(file, Charset.forName("UTF-8"),CSVFormat.DEFAULT.withSkipHeaderRecord().withFirstRecordAsHeader());

	   List<CSVRecord> records = parser.getRecords();
	   	
	   List<Geopositions> coordinates= new ArrayList<>();
	   	
	   for (CSVRecord record : records) {
		
			String latSTR=record.get(4).replace("N", "");
			double latitude= Double.parseDouble(latSTR);
			String lonSTR =record.get(5).replace("W","");
			StringBuilder sb = new StringBuilder(lonSTR);
			sb.insert(0, "-");
			double longitude =Double.parseDouble(sb.toString());
			Geopositions geo = new Geopositions(record.get(1),record.get(2),record.get(3),latitude, longitude,record.get(6),record.get(7),record.get(8),record.get(9));
			coordinates.add(geo);
	   }	
	   
	  do { 
	   for(Geopositions geo: coordinates) {
		   	
		   		double ltc= coordinates.get(i).latitudes;
		   		double lnc=coordinates.get(i).longitudes;
		   		double travelTarget= calculateDistance(lt1,ln1,ltc, lnc);
			 
				if(travelTarget>=933.2) {
					startIndex=i;
					System.out.println("Destination Arrived.."+"\nstart Index:"+startIndex);
					break;
				}
				try {
			    String[] data1 = {Integer.toString(j),coordinates.get(i).tag,coordinates.get(i).date,coordinates.get(i).time,Double.toString(ltc).concat("N"),Double.toString(lnc).replace("-", "").concat("W"),coordinates.get(i).height,coordinates.get(i).speed,coordinates.get(i).heading,coordinates.get(i).vox}; 
			    writer.writeNext(data1); 
			    j++;
			    
				}catch (Exception e) {
				}
				 i++;
				 
				 
	   }
	   
	   lt1=coordinates.get(i).latitudes;
	   ln1=coordinates.get(i).longitudes;
	   double dist=-1;
	   
	   System.out.println("latitude co-odrinate:"+lt1+"longitude co-ordinate:"+ln1+"index no:"+i);
	   
	   
	   for(int k=i;i<=coordinates.size()-1;i++) {
		   
		   
		   
		double dist2=   calculateDistance(lt1, ln1, coordinates.get(i).latitudes, coordinates.get(i).longitudes);
	
				if(dist<dist2){
					dist=dist2;
				}else{
					if(dist>dist2) {
						if(dist2>14) {
							dist=dist2;
						}else if(dist2<=14){
							endIndex=i;
							StartEndCoOrdinates data= new StartEndCoOrdinates(startIndex,endIndex);
							ssc.add(data);
							System.out.println("distance: "+dist2+"\nEnd Index :"+endIndex);
							break;
						}
					}
				}
				

				try {
			    String[] data1 = {Integer.toString(f),coordinates.get(i).tag,coordinates.get(i).date,coordinates.get(i).time,Double.toString(coordinates.get(i).latitudes).concat("N"),Double.toString(coordinates.get(i).longitudes).replace("-", "").concat("W"),coordinates.get(i).height,coordinates.get(i).speed,coordinates.get(i).heading,coordinates.get(i).vox}; 
			    rmWriter.writeNext(data1); 
			    f++;
			    
				}catch (Exception e) {
				}
		i++;
 }
	}while(i<=coordinates.size()-1);
	}
	
	public double calculateDistance( double lt1,double ln1,double latitude, double longitude) {
			
//			radius of earth in meter
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
	
	
}

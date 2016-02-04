package fr.weavers.yogamat.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class VelocityModel {

	public Integer nb_total = 0;
	public Integer nb_completed = 0;
	public Integer work_days_number = 0;
	public Integer days_number = 0;
	public Integer days_elapsed = 0;
	
	private TreeMap<String, Integer> sprint_calendar = new TreeMap<String, Integer>();
	
	public void addToCalendar(Date date, Integer score){
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date_str = format1.format(date);
		if(!sprint_calendar.containsKey(date_str)){
			sprint_calendar.put(date_str, score);
		}else{
			sprint_calendar.put(date_str, sprint_calendar.get(date_str) + score);
		}
	}
	
	public TreeMap<String, Integer> getSprint_calendar(){
		return sprint_calendar;
	}
	
		
	public Double getRequired_points_by_day(){
		if(days_number != 0){
			return (double) nb_total/days_number;
		}
		return (double) -1;
	}
	
	public Double getCurrent_points_by_day(){
		if(days_number != 0){
			return (double) (nb_completed)/(days_elapsed);
		}
		return (double) -1;
	}
	
}

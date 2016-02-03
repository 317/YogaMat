package fr.weavers.yogamat.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class VelocityModel {

	public Integer nb_total = 0;
	public Integer nb_completed = 0;
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
	
}

package fr.weavers.yogamat.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;

import fr.weavers.yogamat.AsanaName;

@AsanaName("tasks")
public class Task extends JsonToObject {

	public static Task Invoke(Long ID){
		try {
			return JsonToObject.Invoke(ID, Task.class);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Long id;
	private String name;
	private String notes;
	private Boolean completed;
	private Date created_at;
	private Date completed_at;
	private ArrayList<String> tags;

	private Integer points;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public Date getCompleted_at() {
		return completed_at;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public Integer getPoints() {
		if(this.points == null){
			for(String tag : tags){
				if(tag.contains(" pts")){
					String pts_string = tag.replaceAll("[^0-9]", "");
					this.points = Integer.parseInt(pts_string);
				}
			}
		}
		if(this.points == null){
			this.points = 0;
		}
		return points;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setCompleted(BooleanNode completed) {
		this.completed = completed.asBoolean();
	}

	public void setCreated_at(TextNode created_at) {
		DateTimeFormatter parser = ISODateTimeFormat.dateTime();
		DateTime dt = parser.parseDateTime(created_at.asText());
		this.created_at = dt.toDate();
	}

	public void setCompleted_at(TextNode completed_at) {
		DateTimeFormatter parser = ISODateTimeFormat.dateTime();
		DateTime dt = parser.parseDateTime(completed_at.asText());
		this.completed_at = dt.toDate();
	}

	public void setId(LongNode id) {
		this.id = id.asLong();
	}

	public void setName(TextNode name) {
		this.name = name.asText();
	}

	public void setNotes(TextNode notes) {
		this.notes = notes.asText();
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setCompleted_at(Date completed_at) {
		this.completed_at = completed_at;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
	public void setTags(ArrayNode tags) {
		this.tags = new ArrayList<String>();
		for (Iterator<JsonNode> tag = tags.elements(); tag.hasNext();) {
			this.tags.add(tag.next().get("name").asText());
		}
	}

	

}

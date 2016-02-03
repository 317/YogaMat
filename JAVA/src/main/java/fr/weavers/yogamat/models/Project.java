package fr.weavers.yogamat.models;

import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;

import fr.weavers.yogamat.AsanaName;

@AsanaName("projects")
public class Project extends JsonToObject{
	
	public static Project Invoke(Long ID){
		try {
			return JsonToObject.Invoke(ID, Project.class);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Long id;
	private String name;
	private String notes;
	
	public Project(){
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(LongNode id) {
		this.id = id.asLong();
	}
	public String getName() {
		return name;
	}
	public void setName(TextNode name) {
		this.name = name.asText();
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void setNotes(TextNode notes) {
		this.notes = notes.asText();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}

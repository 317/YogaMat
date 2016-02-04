package fr.weavers.yogamat.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	private HashMap<String, JsonNode> details;
	
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

	public HashMap<String, JsonNode> getDetails() {
		if(details == null){
			details = new HashMap<String, JsonNode>();
			String[] notes_array = this.notes.split("=DO NOT EDIT=");
			if(notes_array.length > 1){
				notes_array = notes_array[1].split("=END DO NOT EDIT=");
				ObjectMapper mapper = new ObjectMapper();
				try {
					JsonNode node = mapper.readValue(notes_array[0], JsonNode.class);
					Iterator<Entry<String, JsonNode>> node_fields = node.fields();
					while(node_fields.hasNext()){
						Entry<String, JsonNode> elt = node_fields.next();
						this.details.put(elt.getKey(), elt.getValue());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return details;
	}

	public void setDetails(HashMap<String, JsonNode> details) {
		this.details = details;
	}
	
	
	
	
	
}

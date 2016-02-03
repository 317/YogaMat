package fr.weavers.yogamat.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.weavers.yogamat.models.Project;
import fr.weavers.yogamat.models.Task;
import fr.weavers.yogamat.models.VelocityModel;

@Controller
public class TaskController {

	private String key = "0/efb8f1a901f0e05d15f3723817ea94d0";

	@RequestMapping("/project/{project_id}")
	@ResponseBody
	public Object getProject(@PathVariable Long project_id) throws InstantiationException, IllegalAccessException {
		Project project = Project.Invoke(project_id);
		return project;
	}

	@RequestMapping("/project/{project_id}/velocity")
	@ResponseBody
	public Object velocity(@PathVariable Long project_id) throws JsonParseException, JsonMappingException, IOException {
		VelocityModel velocityModel = new VelocityModel();
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(
				"https://app.asana.com/api/1.0/projects/{project_id}/tasks?access_token={access_token}", String.class,
				project_id, key);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		node = mapper.readValue(response, JsonNode.class);
		node = node.get("data");
		Task task;
		Iterator<JsonNode> node_elts = node.iterator();
		while (node_elts.hasNext()) {
			JsonNode elt = node_elts.next();
			task = Task.Invoke(elt.get("id").asLong());
			velocityModel.nb_total += task.getPoints();
			if (task.getCompleted() == true) {
				velocityModel.nb_completed += task.getPoints();
				velocityModel.addToCalendar(task.getCompleted_at(), task.getPoints());
			}
		}

		return velocityModel;
	}

	@RequestMapping("/test")
	@ResponseBody
	public Object test() throws JsonParseException, JsonMappingException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(
				"https://app.asana.com/api/1.0/tasks/{task_id}?access_token={access_token}", String.class,
				"85313001043012", key);
		Task task = new Task();
		task.hydrate(response);
		return task;
	}

}

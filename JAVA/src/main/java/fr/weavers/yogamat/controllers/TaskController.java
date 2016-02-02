package fr.weavers.yogamat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaskController {

	@RequestMapping("/project/{project_id}")
	@ResponseBody
	public Object getProject(@PathVariable String project_id) {
		return project_id;
		/*
		 * RestTemplate restTemplate = new RestTemplate(); Project project =
		 * restTemplate.getForObject(
		 * "https://app.asana.com/api/1.0/projects/{project_id}?access_token={access_token}",
		 * Project.class, project_id, "0/efb8f1a901f0e05d15f3723817ea94d0");
		 * return project;
		 */
	}
}

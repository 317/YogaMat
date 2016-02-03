package fr.weavers.yogamat.models;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.weavers.yogamat.AsanaName;

public class JsonToObject {
	
	private static RestTemplate restTemplate = new RestTemplate();
	private static String key = "0/efb8f1a901f0e05d15f3723817ea94d0";
	
	public static <T extends JsonToObject> T Invoke(Long ID, Class current_class) throws InstantiationException, IllegalAccessException{
		AsanaName asana_name_annotation = (AsanaName) current_class.getAnnotation(AsanaName.class);
		
		T entity = (T) current_class.newInstance();
		String query = "https://app.asana.com/api/1.0/{asana_name}/{object_id}?access_token={access_token}";
		String response = restTemplate.getForObject(
				query,
				String.class,
				asana_name_annotation.value(), 
				ID, 
				key);
		
	
		((T) entity).hydrate(response);
		return ((T) entity);
	}
	
	public void hydrate(String response){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node;
		try {
			node = mapper.readValue(response, JsonNode.class);
			node = node.get("data");
			Iterator<Entry<String, JsonNode>> node_fields = node.fields();
			while(node_fields.hasNext()){
				Entry<String, JsonNode> elt = node_fields.next();
				String method_name = "set" + StringUtils.capitalize(elt.getKey());
				System.out.println(elt.getKey()+" : "+elt.getValue().getClass());
				
				try {
					Method method = this.getClass().getMethod(method_name, elt.getValue().getClass());
					method.invoke(this, elt.getValue());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						|  SecurityException e) {
					e.printStackTrace();
				} catch(NoSuchMethodException e){
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

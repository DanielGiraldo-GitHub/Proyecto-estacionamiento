package co.com.ceiba.CeibaEstacionamiento.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.CeibaEstacionamiento.model.Parqueadero;
import co.com.ceiba.CeibaEstacionamiento.service.ParqueaderoService;
import co.com.ceiba.CeibaEstacionamiento.util.RestResponse;

@RestController
public class ParqueaderoController {

	@Autowired
	protected ParqueaderoService parqueaderoService;

	protected ObjectMapper mapper;

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String parqueaderoJson) throws JsonParseException, JsonMappingException, IOException  {
		
		this.mapper = new ObjectMapper();
		Parqueadero parqueadero = this.mapper.readValue(parqueaderoJson,Parqueadero.class); 
		
		return null; 

	}
}

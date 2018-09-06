package co.com.ceiba.CeibaEstacionamiento.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.CeibaEstacionamiento.model.Vehiculo;
import co.com.ceiba.CeibaEstacionamiento.service.VehiculoService;
import co.com.ceiba.CeibaEstacionamiento.util.RestResponse;

@RestController
@RequestMapping(value = "/")
@CrossOrigin("http://localhost:8080")
public class VehiculoController {

	@Autowired
	protected VehiculoService vehiculoService;

	protected ObjectMapper mapper;

	@RequestMapping(value = "/registerVehicle", method = RequestMethod.POST)
	public RestResponse registerVehicle(@RequestBody String vehiculoJson) 
			throws JsonParseException, JsonMappingException, IOException {
		
		this.mapper = new ObjectMapper();
		Vehiculo vehiculo = this.mapper.readValue(vehiculoJson,Vehiculo.class);
		
		this.vehiculoService.save(vehiculo);
        return new RestResponse(HttpStatus.OK.value(),"Exito");
	}
}

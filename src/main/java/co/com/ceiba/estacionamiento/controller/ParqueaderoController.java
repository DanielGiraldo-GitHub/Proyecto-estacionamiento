package co.com.ceiba.estacionamiento.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.service.ParqueaderoService;
import co.com.ceiba.estacionamiento.service.VehiculoService;
import co.com.ceiba.estacionamiento.util.RestResponse;

@RestController
public class ParqueaderoController {

	@Autowired
	protected ParqueaderoService parqueaderoService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	protected ObjectMapper mapper;

	public ParqueaderoController() {
		this.mapper = new ObjectMapper();
	}

	@RequestMapping(value = "/ingresarVehiculo", method = RequestMethod.POST)
	public RestResponse ingresarVehiculo(@RequestBody String parqueaderoJson)
			throws IOException {

		Parqueadero parqueadero = mapper.readValue(parqueaderoJson, Parqueadero.class);
		// vehiculoService.b(parqueadero.g)
		return null;

	}

	@GetMapping("/consultarDisponibilidad")
	public String consultarDisponibilidad() throws JsonProcessingException{
		
		return mapper.writeValueAsString(parqueaderoService.consultarDisponibilidad());
	}
	
	@RequestMapping("/registerVehicle")
	@PostMapping
	public RestResponse registerVehicle(@RequestBody String vehiculoJson) throws IOException {

		Vehiculo vehiculo = this.mapper.readValue(vehiculoJson, Vehiculo.class);
		return vehiculoService.save(vehiculo);
	}

	@GetMapping("/buscarVehiculo")
	public String buscarVehiculo(@RequestBody String placa) throws IOException {

		Vehiculo vehiculo = mapper.readValue(placa, Vehiculo.class);
		return mapper.writeValueAsString((Vehiculo)vehiculoService.buscarVehiculo(vehiculo.getPlaca()));
	}
}

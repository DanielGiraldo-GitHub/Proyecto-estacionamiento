package co.com.ceiba.estacionamiento.controller;

import java.io.IOException;
import java.text.ParseException;
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
	public RestResponse ingresarVehiculo(@RequestBody String vehiculoJson) throws IOException, ParseException {

		Vehiculo vehiculo = mapper.readValue(vehiculoJson, Vehiculo.class);
		
		if (vehiculoService.buscarVehiculoPorId(vehiculo.getId()) != null) {
			parqueaderoService.ingresarVehiculoParqueadero(vehiculo.getId());
			
		} else {

			vehiculoService.save(vehiculo);
			Vehiculo busquedaVehiculo = (Vehiculo) vehiculoService.buscarVehiculo(vehiculo.getPlaca());
			parqueaderoService.ingresarVehiculoParqueadero(busquedaVehiculo.getId());
		}
		return vehiculoService.save(vehiculo);
	}

	@GetMapping("/consultarDisponibilidad")
	public String consultarDisponibilidad() throws JsonProcessingException {

		return mapper.writeValueAsString(parqueaderoService.consultarDisponibilidad());
	}

	@RequestMapping("/registerVehicle")
	@PostMapping
	public RestResponse registerVehicle(@RequestBody String vehiculoJson) throws IOException {

		Vehiculo vehiculo = this.mapper.readValue(vehiculoJson, Vehiculo.class);
		return vehiculoService.save(vehiculo);
	}

	@GetMapping("/buscarVehiculo")
	public String buscarVehiculo(@RequestBody String placa)
			throws JsonParseException, JsonMappingException, IOException {

		Vehiculo vehiculo = mapper.readValue(placa, Vehiculo.class);
		return (String) vehiculoService.buscarVehiculo(vehiculo.getPlaca());
	}

	@GetMapping("/listarMotosParqueadas")
	public String listarMotosParqueadas() {
		return parqueaderoService.listarMotosParqueadas().toString();
	}

	@GetMapping("/listarCarrosParqueados")
	public String listarCarrosParqueados() {
		return parqueaderoService.listarCarrosParqueados().toString();
	}

	@PostMapping("/salidaVehiculo")
	public String salidaVehiculo(@RequestBody String parqueaderoJson) throws IOException, ParseException {

		Parqueadero parqueadero = mapper.readValue(parqueaderoJson, Parqueadero.class);
		Vehiculo vehiculo = (Vehiculo) vehiculoService.buscarVehiculoPorId(parqueadero.getIdVehiculo());
		return parqueaderoService.salidaVehiculo(parqueadero, vehiculo).toString();
	}
}

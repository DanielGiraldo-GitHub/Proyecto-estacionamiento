package co.com.ceiba.estacionamiento.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.service.VehiculoService;
import co.com.ceiba.estacionamiento.util.RestResponse;

@RestController
public class VehiculoController {

	@Autowired
	protected VehiculoService vehiculoService;

	protected ObjectMapper mapper;

	public VehiculoController() {
		this.mapper = new ObjectMapper();
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

	@GetMapping("/hola")
	public String hola() {
		return "HOLA";
	}

}

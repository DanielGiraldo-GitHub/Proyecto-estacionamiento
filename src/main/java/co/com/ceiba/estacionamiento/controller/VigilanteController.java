package co.com.ceiba.estacionamiento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.service.VigilanteService;

@RestController
public class VigilanteController {

	@Autowired
	protected VigilanteService vigilanteService;

	
	public VigilanteController(VigilanteService vigilanteService) {
		super();
		 this.vigilanteService = vigilanteService;
	}

	@RequestMapping(value = "/ingresarVehiculo", method = RequestMethod.POST)
	public void ingresarVehiculo(@RequestBody Vehiculo vehiculo) {
		vigilanteService.ingresarVehiculoParqueadero(vehiculo);
	}

	@GetMapping("/consultarDisponibilidad")
	public int[] consultarDisponibilidad() {
		return vigilanteService.consultarDisponibilidad();
	}

	@GetMapping("/buscarVehiculo")
	public Vehiculo buscarVehiculo(@RequestBody Vehiculo vehiculo) {
		return vigilanteService.buscarVehiculo(vehiculo);
	}
	
	@GetMapping("/buscarVehiculoParqueado")
	public Vehiculo buscarVehiculoParqueado(@RequestBody Vehiculo vehiculo) {	
		return vigilanteService.buscarVehiculoParqueado(vehiculo.getPlaca());
	}

	@GetMapping("/listarMotosParqueadas")
	public String listarMotosParqueadas() {
		return vigilanteService.listarMotosParqueadas().toString();
	}

	@GetMapping("/listarCarrosParqueados")
	public String listarCarrosParqueados() {
		return vigilanteService.listarCarrosParqueados().toString();
	}

	@PostMapping("/salidaVehiculo")
	public Parqueadero salidaVehiculo(@RequestBody Vehiculo vehiculo) {
		Parqueadero parqueadero = vigilanteService.buscarParqueaderoVehiculo(vehiculo.getId());
		return vigilanteService.salidaVehiculo(parqueadero, vehiculo);
	}
}

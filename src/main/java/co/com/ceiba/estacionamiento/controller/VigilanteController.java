package co.com.ceiba.estacionamiento.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = { "http://localhost:4200" })
public class VigilanteController {

	@Autowired
	protected VigilanteService vigilanteService;

	
	public VigilanteController(VigilanteService vigilanteService) {
		super();
		 this.vigilanteService = vigilanteService;
	}

	
	@RequestMapping(value = "/ingresarVehiculo", method = RequestMethod.POST)
	public ResponseEntity<String> ingresarVehiculo(@RequestBody Vehiculo vehiculo) {
		
		 vigilanteService.ingresarVehiculoParqueadero(vehiculo);
		 return new ResponseEntity <String> ("Ok", HttpStatus.CREATED);
	}

	@GetMapping("/consultarDisponibilidad")
	public int[] consultarDisponibilidad() {
		return vigilanteService.consultarDisponibilidad();
	}

	@RequestMapping("/registerVehicle")
	@PostMapping
	public int registerVehicle(@RequestBody Vehiculo vehiculo) throws ParseException {
		  
		int result = vigilanteService.save(vehiculo);
		if(result > 0)return result;
		return -1;
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
	public List<Vehiculo> listarMotosParqueadas() {
		return vigilanteService.listarMotosParqueadas();
	}

	@GetMapping("/listarCarrosParqueados")
	public List<Vehiculo> listarCarrosParqueados() {
		return vigilanteService.listarCarrosParqueados();
	}

	@PostMapping("/salidaVehiculo")
	public Parqueadero salidaVehiculo(@RequestBody Vehiculo vehiculo) {
		Parqueadero parqueadero = vigilanteService.buscarParqueaderoVehiculo(vehiculo.getId());
		return vigilanteService.salidaVehiculo(parqueadero, vehiculo);
	}
}

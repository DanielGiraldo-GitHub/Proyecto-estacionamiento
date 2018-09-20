package co.com.ceiba.estacionamiento.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.ceiba.estacionamiento.model.Disponibilidad;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.service.IVigilanteService;
import dominio.excepcion.ParqueaderoException;

@RestController
@ControllerAdvice
@CrossOrigin(origins = { "http://localhost:4200" })
public class VigilanteController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ParqueaderoException.class })
	public ResponseEntity<String> handleAccessDeniedException(ParqueaderoException ex, WebRequest request) {
		return new ResponseEntity<String>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@Autowired
	protected IVigilanteService iVigilanteService;

	public VigilanteController(IVigilanteService iVigilanteService) {
		this.iVigilanteService = iVigilanteService; 
	}

	@RequestMapping(value = "/ingresarVehiculo", method = RequestMethod.POST)
	public void ingresarVehiculo(@RequestBody Vehiculo vehiculo) {
		iVigilanteService.ingresarVehiculo(vehiculo);
	}

	@GetMapping("/consultarDisponibilidad")
	public Disponibilidad consultarDisponibilidad() {
		return iVigilanteService.consultarDisponibilidad();
	}

	@GetMapping("/buscarVehiculo/{placa}")
	public Vehiculo buscarVehiculo(@PathVariable("placa") String placa) {
		return iVigilanteService.buscarVehiculo(placa);
	}

	@GetMapping("/listarMotosParqueadas")
	public List<Vehiculo> listarMotosParqueadas() {
		return iVigilanteService.listarMotosParqueadas();
	}

	@GetMapping("/listarCarrosParqueados")
	public List<Vehiculo> listarCarrosParqueados() {
		return iVigilanteService.listarCarrosParqueados();
	}

	@PostMapping("/salidaVehiculo")
	public Parqueadero salidaVehiculo(@RequestBody Vehiculo vehiculo) {
		return iVigilanteService.salidaVehiculo(vehiculo);
	}
}

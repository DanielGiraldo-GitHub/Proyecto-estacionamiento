package co.com.ceiba.estacionamiento.service;

import java.text.ParseException;
import org.json.JSONArray;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;

public interface ParqueaderoService {

	int[] consultarDisponibilidad();

	JSONArray listarMotosParqueadas();

	JSONArray listarCarrosParqueados();

	Parqueadero salidaVehiculo(Parqueadero parqueadero, Vehiculo vehiculo);

	void ingresarVehiculoParqueadero(int id)throws ParseException;
}

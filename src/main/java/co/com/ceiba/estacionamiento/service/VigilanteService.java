package co.com.ceiba.estacionamiento.service;

import java.text.ParseException;
import java.util.List;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;

public interface VigilanteService {

	int[] consultarDisponibilidad();

	List<Vehiculo> listarMotosParqueadas();

	List<Vehiculo> listarCarrosParqueados();

	Parqueadero salidaVehiculo(Parqueadero parqueadero, Vehiculo vehiculo);

	boolean ingresarVehiculoParqueadero(Vehiculo vehiculo);

	boolean save(Vehiculo vehiculo)throws ParseException;

	Vehiculo buscarVehiculo(Vehiculo vehiculo);

	boolean validate(Vehiculo vehiculo);

	boolean validarPlaca(String placa)throws ParseException;

	Vehiculo buscarVehiculoPorId(int idVehiculo);
	
	Vehiculo buscarVehiculoParqueado(String placa);
	
	Parqueadero buscarParqueaderoVehiculo(int idVehiculo);
}

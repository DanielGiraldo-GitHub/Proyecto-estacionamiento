package co.com.ceiba.estacionamiento.service;

import java.util.List;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;

public interface VigilanteService {

	int[] consultarDisponibilidad();

	List<Vehiculo> listarMotosParqueadas();

	List<Vehiculo> listarCarrosParqueados();

	Parqueadero salidaVehiculo(Parqueadero parqueadero, Vehiculo vehiculo);

	void ingresarVehiculoParqueadero(Vehiculo vehiculo);

	void save(Vehiculo vehiculo);

	Vehiculo buscarVehiculo(Vehiculo vehiculo);

	boolean validate(Vehiculo vehiculo);

	boolean validarPlaca(String placa);

	Vehiculo buscarVehiculoPorId(int idVehiculo);
	
	Vehiculo buscarVehiculoParqueado(String placa);
	
	Parqueadero buscarParqueaderoVehiculo(int idVehiculo);
}

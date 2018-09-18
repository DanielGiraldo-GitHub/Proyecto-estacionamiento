package co.com.ceiba.estacionamiento.service;

import java.text.ParseException;
import java.util.List;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;

public interface IVigilanteService {

	int[] consultarDisponibilidad();

	List<Vehiculo> listarMotosParqueadas();

	List<Vehiculo> listarCarrosParqueados();

	Parqueadero salidaVehiculo(Vehiculo vehiculo) ;

	boolean ingresarVehiculoParqueadero(Vehiculo vehiculo);

	int save(Vehiculo vehiculo);

	Vehiculo buscarVehiculo(Vehiculo vehiculo);

	boolean validate(Vehiculo vehiculo);

	boolean validarPlaca(String placa)throws ParseException;

	Vehiculo buscarVehiculoPorId(int idVehiculo);
	
	Vehiculo buscarVehiculoParqueado(String placa);
	
	Parqueadero buscarParqueaderoVehiculo(int idVehiculo);
}

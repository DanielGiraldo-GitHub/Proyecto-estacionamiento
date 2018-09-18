package co.com.ceiba.estacionamiento.dao;

import java.util.List;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;

public interface IVigilanteRepository {

	Integer contarCarrosParqueados();

	Integer contarMotosParqueados();

	List<Vehiculo> listarCarrosParqueados();

	List<Vehiculo> listarMotosParqueadas();

	void salidaVehiculoParqueado(Parqueadero parqueadero);

	void ingresarVehiculoParqueadero(Parqueadero parqueadero);

	int guardarVehiculo(Vehiculo vehiculo);

	Vehiculo buscarVehiculo(String placa);

	Vehiculo buscarVehiculoPorId(int idVehiculo);

	Vehiculo buscarVehiculoParqueado(String placa);

	Parqueadero buscarParqueaderoVehiculo(int idVehiculo);


}

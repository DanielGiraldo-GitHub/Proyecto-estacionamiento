package co.com.ceiba.estacionamiento.dao;

import java.util.List;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;

public interface IVigilanteRepository {

	Integer contarCarrosParqueados();

	Integer contarMotosParqueados();

	List<Vehiculo> listarCarrosParqueados();

	List<Vehiculo> listarMotosParqueadas();

	void salidaVehiculo(Parqueadero parqueadero);

	void ingresarVehiculoParqueadero(Parqueadero parqueadero);

	void guardarVehiculo(Vehiculo vehiculo);

	Vehiculo buscarVehiculo(String placa);

	Parqueadero buscarParqueadero(int idVehiculo);


}

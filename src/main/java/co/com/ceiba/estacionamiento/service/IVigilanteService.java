package co.com.ceiba.estacionamiento.service;

import java.util.List;
import co.com.ceiba.estacionamiento.model.Disponibilidad;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;

public interface IVigilanteService {

	Disponibilidad consultarDisponibilidad();

	List<Vehiculo> listarMotosParqueadas();

	List<Vehiculo> listarCarrosParqueados();

	Parqueadero salidaVehiculo(Vehiculo vehiculo) ;

	boolean ingresarVehiculo(Vehiculo vehiculo);

	void guardarVehiculo(Vehiculo vehiculo);

	Vehiculo buscarVehiculo(String placa);

	void validarCampos(Vehiculo vehiculo);

	void validarPlaca(String placa);
	
	Parqueadero buscarParqueadero(int idVehiculo);
}

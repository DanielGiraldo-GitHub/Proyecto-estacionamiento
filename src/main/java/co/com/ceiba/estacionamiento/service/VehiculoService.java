package co.com.ceiba.estacionamiento.service;

import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.RestResponse;

public interface VehiculoService {

	RestResponse save(Vehiculo vehiculo);
	Object buscarVehiculo(String placa);
	boolean validate(Vehiculo vehiculo);
	boolean validarPlaca(String placa);
}

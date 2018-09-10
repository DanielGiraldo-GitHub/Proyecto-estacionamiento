package co.com.ceiba.estacionamiento.dao;

import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.RestResponse;

public interface VehiculoRepository {

	RestResponse guardarVehiculo(Vehiculo vehiculo);

	Object buscarVehiculo(String placa);

	Object buscarVehiculoPorId(int idVehiculo);

}

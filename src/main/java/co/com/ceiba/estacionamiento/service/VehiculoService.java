package co.com.ceiba.estacionamiento.service;

import java.text.ParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.RestResponse;

public interface VehiculoService {

	RestResponse save(Vehiculo vehiculo);
	Object buscarVehiculo(String placa)throws JsonProcessingException ;
	boolean validate(Vehiculo vehiculo);
	boolean validarPlaca(String placa);
	Object buscarVehiculoPorId(int idVehiculo)throws ParseException;
}

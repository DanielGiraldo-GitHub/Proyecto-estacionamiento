package co.com.ceiba.estacionamiento.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.dao.VehiculoRepository;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.ControlFecha;
import co.com.ceiba.estacionamiento.util.RestResponse;

@Service
public class VehiculoServiceImpl implements VehiculoService {

	ControlFecha controFechas = new ControlFecha();
	static final char PLACA_CON_RESTRICCION = 'A';
	
	@Autowired
	protected VehiculoRepository vehiculoRepository;

	@Override
	public RestResponse save(Vehiculo vehiculo) {
		
		if (!validate(vehiculo))
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"los campos obligatorios no estan diligenciados");

		if (!validarPlaca(vehiculo.getPlaca()))
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"El vehiculo no puede ser parqueado los dias " + "domingo y lunes");
		
		return vehiculoRepository.guardarVehiculo(vehiculo);
	}

	@Override
	public Object buscarVehiculo(String  placa) {
		return vehiculoRepository.buscarVehiculo(placa);
	}
	
	@Override
	public boolean validate(Vehiculo vehiculo) {

		boolean expression = true;
		if (vehiculo.getPlaca() != null && vehiculo.getTipoVehiculo() != null && vehiculo.getCilindraje() > 0)
			return expression;
		return !expression;
	}

	@Override
	public boolean validarPlaca(String placa) {

		if (placa.toUpperCase().charAt(0) == PLACA_CON_RESTRICCION)
			try {
				return controFechas.velidarDia();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return true;
	}

	

}

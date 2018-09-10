package co.com.ceiba.estacionamiento.service;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.com.ceiba.estacionamiento.dao.VehiculoRepository;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.ControlFecha;
import co.com.ceiba.estacionamiento.util.RestResponse;

@Service
public class VehiculoServiceImpl implements VehiculoService {

	ControlFecha controFechas = new ControlFecha();
	static final char PLACA_CON_RESTRICCION = 'A';
	static final String EXCEPCION_VEHICULO_NO_ENCONTRADO = "No se ha encontrado ningun vehiculo";

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
	public String buscarVehiculo(String placa) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString((Vehiculo) vehiculoRepository.buscarVehiculo(placa));
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

			}
		return true;
	}

	@Override
	public Object buscarVehiculoPorId(int idVehiculo) {

		return vehiculoRepository.buscarVehiculoPorId(idVehiculo);
	}

}

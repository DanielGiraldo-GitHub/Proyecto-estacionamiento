package co.com.ceiba.estacionamiento.service;

import java.text.ParseException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ceiba.estacionamiento.dao.ParqueaderoRepository;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.ControlFecha;
import co.com.ceiba.estacionamiento.util.ControlTarifas;
import dominio.excepcion.ParqueaderoException;

@Service
public class ParqueaderoServiceImpl implements ParqueaderoService {

	@Autowired
	protected ParqueaderoRepository parqueaderoRepository;
	
	protected ControlTarifas controlTarifas = new ControlTarifas();
	protected ControlFecha controlFechas = new ControlFecha();

	public static final String PARQUEADERO_SIN_CUPO_DE_CARRO = "El parqueadero no tiene cupos disponibles para carros";
	public static final String PARQUEADERO_SIN_CUPO_DE_MOTO = "El parqueadero no tiene cupos disponibles para moto";
	public static final String ERROR_AL_CALCULAR_PRECIO = "Se ha precentado un error al calcular el precio del vehiculo";
	public static final int CUPOS_CARRO = 20;
	public static final int CUPOS_MOTO = 10;

	@Override
	public int[] consultarDisponibilidad() {

		int[] cuposDisponibles = new int[2];
		cuposDisponibles[0] = CUPOS_CARRO - parqueaderoRepository.contarCarrosParqueados();
		cuposDisponibles[1] = CUPOS_MOTO - parqueaderoRepository.contarMotosParqueados();

		for (int i = 0; i < cuposDisponibles.length; i++) {

			if (i == 0 && cuposDisponibles[i] <= 0)
				throw new ParqueaderoException(PARQUEADERO_SIN_CUPO_DE_CARRO);
			if (i == 1 && cuposDisponibles[1] <= 0)
				throw new ParqueaderoException(PARQUEADERO_SIN_CUPO_DE_MOTO);
		}
		return cuposDisponibles;
	}

	@Override
	public JSONArray listarMotosParqueadas() {
		return parqueaderoRepository.listarMotosParqueadas();
	}

	@Override
	public JSONArray listarCarrosParqueados() {
		return parqueaderoRepository.listarCarrosParqueados();
	}

	@Override
	public Parqueadero salidaVehiculo(Parqueadero parqueadero, Vehiculo vehiculo) {

		int[] tiempoPermanencia;
		try {
			tiempoPermanencia = controlFechas.calcularTiempo(parqueadero.getFehcaIngreso());
			if (vehiculo.getTipoVehiculo().equals("C"))
				parqueadero.setPrecio(controlTarifas.calcularPrecioCarro(tiempoPermanencia));
			else
			parqueadero.setPrecio(controlTarifas.calcularPrecioMoto(vehiculo.getCilindraje(), tiempoPermanencia));
			parqueadero.setFechaSalida(controlFechas.fechaAcutalSistema().getTime());
			parqueadero.setEstado(false);
			parqueaderoRepository.salidaVehiculoParqueado(parqueadero);
		} catch (ParseException e) {
			throw new ParqueaderoException(ERROR_AL_CALCULAR_PRECIO);
		}
		return parqueadero;
	}

	@Override
	public void ingresarVehiculoParqueadero(int idVehiculo) throws ParseException {
		Parqueadero parqueadero = new Parqueadero(controlFechas.fechaAcutalSistema().getTime(), idVehiculo, true);
		parqueaderoRepository.ingresarVehiculoParqueadero(parqueadero);
	}
}

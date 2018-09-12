package co.com.ceiba.estacionamiento.service;

import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ceiba.estacionamiento.dao.VigilanteRepository;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.ControlFecha;
import co.com.ceiba.estacionamiento.util.ControlTarifas;
import dominio.excepcion.ParqueaderoException;

@Service
public class VigilanteServiceImpl implements VigilanteService {

	protected ControlTarifas controlTarifas = new ControlTarifas();
	protected ControlFecha controlFechas = new ControlFecha();

	static final char PLACA_CON_RESTRICCION = 'A';
	static final String EXCEPCION_VEHICULO_NO_ENCONTRADO = "No se ha encontrado ningun vehiculo";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";
	static final String PARQUEADERO_SIN_CUPO_DE_CARRO = "El parqueadero no tiene cupos disponibles para carros";
	static final String PARQUEADERO_SIN_CUPO_DE_MOTO = "El parqueadero no tiene cupos disponibles para moto";
	static final String ERROR_AL_CALCULAR_PRECIO = "Se ha precentado un error al calcular el precio del vehiculo";
	static final String ERROR_CONVERSION_FECHAS = "Error al realizar conversion de fechas";
	static final String PLACA_DUPLICADA = "Este vehiculo ya se encuentra registrado";
	static final String VEHICULO_NO_ENCONTRADO = "Este vehiculo no se encuentra registrado";
	static final int CUPOS_CARRO = 20;
	static final int CUPOS_MOTO = 10;

	@Autowired
	protected VigilanteRepository vigilanteRepository;

	public VigilanteServiceImpl(VigilanteRepository vigilanteRepository) {
		super();
		this.vigilanteRepository = vigilanteRepository;
	}

	@Override
	public void save(Vehiculo vehiculo) {

		if (!validate(vehiculo))

			throw new ParqueaderoException(CAMPOS_SIN_DILIGENCIAR);

		if (!validarPlaca(vehiculo.getPlaca()))
			throw new ParqueaderoException(RESTRICCION_DE_PLACA);

		try {
			vigilanteRepository.guardarVehiculo(vehiculo);
		} catch (RuntimeException e) {
			throw new ParqueaderoException(PLACA_DUPLICADA);
		}
	}

	@Override
	public Vehiculo buscarVehiculo(Vehiculo vehiculo) {

		return vigilanteRepository.buscarVehiculo(vehiculo.getPlaca());
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
				return controlFechas.velidarDia();
			} catch (ParseException e) {

			}
		return true;
	}

	@Override
	public Vehiculo buscarVehiculoPorId(int idVehiculo) {
		return vigilanteRepository.buscarVehiculoPorId(idVehiculo);
	}

	@Override
	public int[] consultarDisponibilidad() {

		int[] cuposDisponibles = new int[2];
		cuposDisponibles[0] = CUPOS_CARRO - vigilanteRepository.contarCarrosParqueados();
		cuposDisponibles[1] = CUPOS_MOTO - vigilanteRepository.contarMotosParqueados();

		for (int i = 0; i < cuposDisponibles.length; i++) {

			if (i == 0 && cuposDisponibles[i] <= 0)
				throw new ParqueaderoException(PARQUEADERO_SIN_CUPO_DE_CARRO);
			if (i == 1 && cuposDisponibles[1] <= 0)
				throw new ParqueaderoException(PARQUEADERO_SIN_CUPO_DE_MOTO);
		}
		return cuposDisponibles;
	}

	@Override
	public List<Vehiculo> listarMotosParqueadas() {
		return vigilanteRepository.listarMotosParqueadas();
	}

	@Override
	public List<Vehiculo> listarCarrosParqueados() {
		return vigilanteRepository.listarCarrosParqueados();
	}

	@Override
	public Parqueadero salidaVehiculo(Parqueadero parqueadero, Vehiculo vehiculo) {

		int[] tiempoPermanencia;
		try {
			if (parqueadero == null) {
				throw new ParqueaderoException("Este vehiculo no se encuentra en el parqueadero");
			}
			else {
				tiempoPermanencia = controlFechas.calcularTiempo(parqueadero.getFehcaIngreso());
				if (vehiculo.getTipoVehiculo().equals("C")) {
					parqueadero.setPrecio(controlTarifas.calcularPrecioCarro(tiempoPermanencia));
				} else {

					parqueadero.setPrecio(controlTarifas.calcularPrecioMoto(vehiculo.getCilindraje(), tiempoPermanencia));
				}
				parqueadero.setEstado(false);
				parqueadero.setFechaSalida(controlFechas.fechaAcutalSistema().getTime());
				vigilanteRepository.salidaVehiculoParqueado(parqueadero);
			}
			return parqueadero;
		} catch (ParseException e) {
			throw new ParqueaderoException(ERROR_AL_CALCULAR_PRECIO);
		}
	}

	@Override
	public void ingresarVehiculoParqueadero(Vehiculo vehiculo) {

		Parqueadero parqueadero = null;
		Vehiculo busquedaVehiculo = buscarVehiculo(vehiculo);
		try {
			if (busquedaVehiculo == null) {
				save(vehiculo);
				busquedaVehiculo = buscarVehiculo(vehiculo);
			}
			if (buscarParqueaderoVehiculo(busquedaVehiculo.getId()) != null)
				throw new ParqueaderoException("este vehiculo ya se encuentra en el parqueadero");

			parqueadero = new Parqueadero(controlFechas.fechaAcutalSistema().getTime(), busquedaVehiculo.getId(), true);
			vigilanteRepository.ingresarVehiculoParqueadero(parqueadero);

		} catch (ParseException e) {
			throw new ParqueaderoException(ERROR_CONVERSION_FECHAS);
		}

	}

	@Override
	public Vehiculo buscarVehiculoParqueado(String placa) {
		try {
			return vigilanteRepository.buscarVehiculo(placa);
		} catch (NullPointerException e) {
			return null;
		}

	}

	@Override
	public Parqueadero buscarParqueaderoVehiculo(int idVehiculo) {
		try {
			return vigilanteRepository.buscarParqueaderoVehiculo(idVehiculo);
		} catch (Exception e) {
			return null;
		}

	}
}

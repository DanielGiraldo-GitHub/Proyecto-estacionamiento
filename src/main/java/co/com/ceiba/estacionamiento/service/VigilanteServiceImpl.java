package co.com.ceiba.estacionamiento.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ceiba.estacionamiento.dao.IVigilanteRepository;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.ControlFecha;
import co.com.ceiba.estacionamiento.util.ControlTarifas;
import dominio.excepcion.ParqueaderoException;

@Service
public class VigilanteServiceImpl implements IVigilanteService {

	protected ControlTarifas controlTarifas = new ControlTarifas();
	protected ControlFecha controlFechas = new ControlFecha();
	static final char PLACA_CON_RESTRICCION = 'A';
	static final String EXCEPCION_VEHICULO_NO_ENCONTRADO = "No se ha encontrado ningun vehiculo";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";
	static final String PARQUEADERO_SIN_CUPO_DE_CARRO = "El parqueadero no tiene cupos disponibles para carros";
	static final String PARQUEADERO_SIN_CUPO_DE_MOTO = "El parqueadero no tiene cupos disponibles para moto";
	static final String ERROR_AL_CALCULAR_PRECIO = "Se ha precentado un error al calcular el precio del vehiculo";
	static final String PLACA_DUPLICADA = "Este vehiculo ya se encuentra registrado";
	static final String VEHICULO_NO_ENCONTRADO = "Este vehiculo no se encuentra registrado";
	static final int CUPOS_CARRO = 20;
	static final int CUPOS_MOTO = 10;

	@Autowired
	protected IVigilanteRepository repositorio;

	public VigilanteServiceImpl(IVigilanteRepository iVigilanteRepository) {
		super();
		this.repositorio = iVigilanteRepository;
	}

	@Override
	public int save(Vehiculo vehiculo) {

		if (!validate(vehiculo))
			throw new ParqueaderoException(CAMPOS_SIN_DILIGENCIAR);

		if (!validarPlaca(vehiculo.getPlaca()))
			throw new ParqueaderoException(RESTRICCION_DE_PLACA);

		return repositorio.guardarVehiculo(vehiculo);
	}

	@Override
	public Vehiculo buscarVehiculo(Vehiculo vehiculo) {
		Vehiculo resul = repositorio.buscarVehiculo(vehiculo.getPlaca());
		if (resul != null) {
			return resul;
		} else {
			return null;
		}
	}

	@Override
	public boolean validate(Vehiculo vehiculo) {
		boolean expression = true;
		if (vehiculo.getPlaca() != null && vehiculo.getTipoVehiculo() != null && vehiculo.getCilindraje() > 0) {
			return expression;
		}
		return !expression;
	}

	@Override
	public boolean validarPlaca(String placa) {

		if (placa.toUpperCase().charAt(0) == PLACA_CON_RESTRICCION)
			return controlFechas.velidarDia();
		return true;
	}

	@Override
	public Vehiculo buscarVehiculoPorId(int idVehiculo) {
		return repositorio.buscarVehiculoPorId(idVehiculo);
	}

	@Override
	public int[] consultarDisponibilidad() {

		int[] cuposDisponibles = new int[2];
		cuposDisponibles[0] = CUPOS_CARRO - repositorio.contarCarrosParqueados();
		cuposDisponibles[1] = CUPOS_MOTO - repositorio.contarMotosParqueados();

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
		return repositorio.listarMotosParqueadas();
	}

	@Override
	public List<Vehiculo> listarCarrosParqueados() {
		return repositorio.listarCarrosParqueados();
	}
 
	@Override
	public Parqueadero salidaVehiculo(Vehiculo vehiculo) {

		int[] tiempoPermanencia;
		Parqueadero parqueadero = buscarParqueaderoVehiculo(vehiculo.getId());
		if (parqueadero == null) {
			throw new ParqueaderoException("Este vehiculo no se encuentra en el parqueadero");
		} else {
			tiempoPermanencia = controlFechas.calcularTiempo(parqueadero.getFehcaIngreso());
			if (vehiculo.getTipoVehiculo().equals("C")) {
				parqueadero.setPrecio(controlTarifas.calcularPrecioCarro(tiempoPermanencia));
			} else {

				parqueadero.setPrecio(controlTarifas.calcularPrecioMoto(vehiculo.getCilindraje(), tiempoPermanencia));
			}
			parqueadero.setEstado(false);
			parqueadero.setFechaSalida(controlFechas.fechaAcutalSistema().getTime());
			repositorio.salidaVehiculoParqueado(parqueadero);
		}
		return parqueadero;
	}

	@Override
	public boolean ingresarVehiculoParqueadero(Vehiculo vehiculo) {
 
		Parqueadero parqueadero = null;
		Vehiculo busquedaVehiculo = buscarVehiculo(vehiculo);
		int id = -1;
		if (busquedaVehiculo == null) {
			id = save(vehiculo);
			vehiculo.setId(id);
		} else {
			
			if (buscarParqueaderoVehiculo(vehiculo.getId()) != null) {
				throw new ParqueaderoException("Este vehiculo ya se encuentra en el parqueadero");
			}
			busquedaVehiculo = buscarVehiculo(vehiculo);
			parqueadero = new Parqueadero(controlFechas.fechaAcutalSistema().getTime(),busquedaVehiculo.getId(), true);
			repositorio.ingresarVehiculoParqueadero(parqueadero);	
		}
		return true;
	}

	@Override
	public Vehiculo buscarVehiculoParqueado(String placa) {
		return repositorio.buscarVehiculo(placa);
	}

	@Override
	public Parqueadero buscarParqueaderoVehiculo(int idVehiculo) {

		return repositorio.buscarParqueaderoVehiculo(idVehiculo);
	}
}

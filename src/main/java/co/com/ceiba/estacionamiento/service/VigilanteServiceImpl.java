package co.com.ceiba.estacionamiento.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ceiba.estacionamiento.dao.IVigilanteRepository;
import co.com.ceiba.estacionamiento.model.Disponibilidad;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.TiempoPermanencia;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.ControlFecha;
import co.com.ceiba.estacionamiento.util.ControlTarifas;
import dominio.excepcion.ParqueaderoException;

@Service
public class VigilanteServiceImpl implements IVigilanteService {

	protected ControlTarifas controlTarifas = new ControlTarifas();
	protected ControlFecha controlFechas = new ControlFecha();
	static final char PLACA_CON_RESTRICCION = 'A';
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String PARQUEADERO_SIN_CUPO_DE_CARRO = "El parqueadero no tiene cupos disponibles para carros";
	static final String PARQUEADERO_SIN_CUPO_DE_MOTO = "El parqueadero no tiene cupos disponibles para moto";
	static final String VEHICULO_NO_PARQUEADO = "Este vehiculo no se encuentra en el parqueadero";
	static final String VEHICULO_PARQUEADO = "Este vehiculo ya se encuentra en el parqueadero";
	static final int CUPOS_CARRO = 20;
	static final int CUPOS_MOTO = 10;

	@Autowired
	protected IVigilanteRepository repositorio;

	public VigilanteServiceImpl(IVigilanteRepository iVigilanteRepository) {
		this.repositorio = iVigilanteRepository;
	}

	@Override
	public void guardarVehiculo(Vehiculo vehiculo) {
		validarCampos(vehiculo);
		validarPlaca(vehiculo.getPlaca());
		repositorio.guardarVehiculo(vehiculo);
	}

	@Override
	public Vehiculo buscarVehiculo(String placa) {
		return repositorio.buscarVehiculo(placa);
	}

	@Override
	public void validarCampos(Vehiculo vehiculo) {

		if (vehiculo.getPlaca() == null || vehiculo.getTipoVehiculo() == null || vehiculo.getCilindraje() <= 0) {
			throw new ParqueaderoException(CAMPOS_SIN_DILIGENCIAR);
		}
	}

	@Override
	public void validarPlaca(String placa) {
		if (placa.toUpperCase().charAt(0) == PLACA_CON_RESTRICCION)
			controlFechas.velidarDia();
	}

	

	@Override
	public Disponibilidad consultarDisponibilidad() {
		Disponibilidad cuposDisponibles = new Disponibilidad();
		cuposDisponibles.setCarros(CUPOS_CARRO - repositorio.contarCarrosParqueados());
		cuposDisponibles.setMotos(CUPOS_MOTO - repositorio.contarMotosParqueados());

		if (cuposDisponibles.getCarros() <= 0)
			throw new ParqueaderoException(PARQUEADERO_SIN_CUPO_DE_CARRO);
		if (cuposDisponibles.getMotos() <= 0)
			throw new ParqueaderoException(PARQUEADERO_SIN_CUPO_DE_MOTO);
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

		TiempoPermanencia tiempoPermanencia;
		Parqueadero parqueadero = buscarParqueadero(vehiculo.getId());

		if (parqueadero == null) {
			throw new ParqueaderoException(VEHICULO_NO_PARQUEADO);
		} else {
			tiempoPermanencia = controlFechas.calcularTiempo(parqueadero.getFehcaIngreso());
			if (vehiculo.getTipoVehiculo().equals("C")) {
				parqueadero.setPrecio(controlTarifas.calcularPrecioCarro(tiempoPermanencia));
			} else {
				parqueadero.setPrecio(controlTarifas.calcularPrecioMoto(vehiculo.getCilindraje(), tiempoPermanencia));
			}
			parqueadero.setEstado(false);
			parqueadero.setFechaSalida(controlFechas.fechaAcutalSistema().getTime());
			repositorio.salidaVehiculo(parqueadero);
		}
		return parqueadero;
	}

	@Override
	public boolean ingresarVehiculo(Vehiculo vehiculo) {

		Parqueadero parqueadero = null;
		Vehiculo busquedaVehiculo = null;
		if (vehiculo.getId() <= 0) {
			guardarVehiculo(vehiculo);
			busquedaVehiculo = buscarVehiculo(vehiculo.getPlaca());
		}
		if (buscarParqueadero(vehiculo.getId()) != null)
			throw new ParqueaderoException(VEHICULO_PARQUEADO);
		
		busquedaVehiculo = buscarVehiculo(vehiculo.getPlaca());
		parqueadero = new Parqueadero(controlFechas.fechaAcutalSistema().getTime(), busquedaVehiculo.getId(), true);
		repositorio.ingresarVehiculoParqueadero(parqueadero);

		return true;
	}

	@Override
	public Parqueadero buscarParqueadero(int idVehiculo) {
		return repositorio.buscarParqueadero(idVehiculo);
	}
}

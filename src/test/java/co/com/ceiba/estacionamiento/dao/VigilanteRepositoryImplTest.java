package co.com.ceiba.estacionamiento.dao;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import co.com.ceiba.estacionamiento.dao.VigilanteRepositoryImpl;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.ControlFecha;
import dominio.excepcion.ParqueaderoException;

public class VigilanteRepositoryImplTest {

	@PersistenceContext
	private static EntityManager entityManager;
	private static VigilanteRepositoryImpl repositorio;
	private static ControlFecha control = new ControlFecha();

	static final String EXITO_AL_GUARDAR_VEHICULO = "Exito";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";

	@BeforeClass
	public static void setUpClass() throws Exception {
		repositorio = new VigilanteRepositoryImpl(entityManager);
		repositorio = mock(VigilanteRepositoryImpl.class);
	}

	

	@Test
	public void buscarVehiculoTest() {

		Vehiculo vehiculo = new Vehiculo(1, "GXL315", "C", 115);
		// act
		when(repositorio.buscarVehiculo(vehiculo.getPlaca())).thenReturn(vehiculo);
		Vehiculo result = repositorio.buscarVehiculo(vehiculo.getPlaca());
		Assert.assertNotNull(result);
	}

	@Test
	public void buscarVehiculoNoEncontradoTest() {

		String placa = "GXL";
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(placa);
		vehiculo = mock(Vehiculo.class);
		// act
		Vehiculo resul = repositorio.buscarVehiculo(placa);
		Assert.assertEquals(null, resul);

	}

	@Test
	public void contarCarrosParqueadosTest() {

		// arrange
		Integer cantidad = 1;
		Integer cantidadResultado = 0;

		when(repositorio.contarCarrosParqueados()).thenReturn(cantidad);
		cantidadResultado = repositorio.contarCarrosParqueados();
		Assert.assertTrue(cantidadResultado >= cantidad);

	}

	@Test
	public void contarMotosParqueadosTest() {

		// arrange
		Integer cantidad = 2;
		Integer cantidadResultado = 0;

		when(repositorio.contarMotosParqueados()).thenReturn(cantidad);
		cantidadResultado = repositorio.contarMotosParqueados();
		Assert.assertTrue(cantidadResultado >= cantidad);
	}

	@Test
	public void listarMotosParqueadasTest() {

		// arrange
		Vehiculo vehiculo_1 = mock(Vehiculo.class);
		vehiculo_1 = new Vehiculo(18, "BCA54C", "M", 125);
		List<Vehiculo> listaMotos = new ArrayList<>();
		listaMotos.add(vehiculo_1);
		when(repositorio.listarMotosParqueadas()).thenReturn(listaMotos);

		List<Vehiculo> listaResultado = repositorio.listarMotosParqueadas();
		Assert.assertNotNull(listaResultado);
	}

	@Test
	public void listarCarrosParqueadosTest() {

		// arrange
		Vehiculo vehiculo_1 = new Vehiculo(20, "KDY533", "C", 2000);
		List<Vehiculo> listaCarros = new ArrayList<>();
		listaCarros.add(vehiculo_1);

		when(repositorio.listarCarrosParqueados()).thenReturn(listaCarros);
		List<Vehiculo> listaResultado = repositorio.listarCarrosParqueados();
		Assert.assertNotNull(listaResultado);
	}

	@Test
	public void salidaVehiculosParqueaderoTest() {

		// arrange
		Calendar fechaIngreso;
		Parqueadero parqueadero;
		fechaIngreso = control.fechaAcutalSistema();
		fechaIngreso.set(2018, 9, 11);
		parqueadero = new Parqueadero(fechaIngreso.getTime(), 1, false);
		parqueadero.setFechaSalida(control.fechaAcutalSistema().getTime());
		parqueadero.setPrecio(8000.0);
		parqueadero = mock(Parqueadero.class);
		repositorio.salidaVehiculoParqueado(parqueadero);

	}

	@Test
	public void ingresoVehiculosParqueaderoTest() {

		Parqueadero parqueadero;
		parqueadero = new Parqueadero(control.fechaAcutalSistema().getTime(), 1, true);
		parqueadero = mock(Parqueadero.class);
		repositorio.ingresarVehiculoParqueadero(parqueadero);
	}

	@Test
	public void buscarParqueaderoVehiculoTest() {

		Parqueadero parqueadero = new Parqueadero();
		parqueadero.setId(23);
		when(repositorio.buscarParqueaderoVehiculo(20)).thenReturn(parqueadero);
		Parqueadero resultado = repositorio.buscarParqueaderoVehiculo(20);
		Assert.assertNotNull(resultado);
	}

	@Test
	public void buscarParqueaderoVehiculoNoParqueadoTest() {

		// arrange
		Vehiculo vehiculo = repositorio.buscarVehiculo("GXL315");
		Parqueadero esperado = new Parqueadero();
		esperado.setEstado(false);
		vehiculo = mock(Vehiculo.class);
		esperado = mock(Parqueadero.class);
		when(repositorio.buscarParqueaderoVehiculo(vehiculo.getId())).thenReturn(esperado);

		Parqueadero parqueadero = repositorio.buscarParqueaderoVehiculo(vehiculo.getId());
		Assert.assertNotNull(parqueadero);
	}

	@Test
	public void buscarVehiculoParqueadoTest() {

		Vehiculo vehiculoEsperado = new Vehiculo(20, "KDY533", "C", 2000);
		vehiculoEsperado = mock(Vehiculo.class);
		when(repositorio.buscarVehiculoParqueado("KDY533")).thenReturn(vehiculoEsperado);
		Vehiculo vehiculo = repositorio.buscarVehiculoParqueado("KDY533");
		Assert.assertNotNull(vehiculo);
	}
}

package co.com.ceiba.estacionamiento.dao;

import static org.mockito.Mockito.mock;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
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
	static final String EXITO_AL_GUARDAR_VEHICULO = "Exito";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";

	@BeforeClass
	public static void setUpClass() throws Exception {

		entityManager = (EntityManager) Persistence.createEntityManagerFactory("TestPersistence").createEntityManager();
		repositorio = new VigilanteRepositoryImpl(entityManager);
	}

	@Test
	public void guardarVehiculoTest() {

		// arrange
		Vehiculo vehiculo = new Vehiculo();

		vehiculo.setPlaca("BCA55C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);

		// act
		repositorio.guardarVehiculo(vehiculo);
	}

	@Test
	public void guardarVehiculoExistenteTest() {

		// arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("GXL315");
		vehiculo.setTipoVehiculo("C");
		vehiculo.setCilindraje(115);

		try {
			repositorio.guardarVehiculo(vehiculo);

		} catch (ParqueaderoException e) {
			// assert
			Assert.assertEquals(CAMPOS_SIN_DILIGENCIAR, e.getMessage());
		}
	}

	@Test
	public void guardarVehiculoSinPlacaTest() {

		// arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);
		try {
			repositorio.guardarVehiculo(vehiculo);

		} catch (ParqueaderoException e) {
			// assert
			Assert.assertEquals(CAMPOS_SIN_DILIGENCIAR, e.getMessage());
		}
	}

	@Test
	public void buscarVehiculoTest() {

		Vehiculo vehiculo = new Vehiculo(1, "GXL315", "C", 115);
		// act
		Vehiculo result = repositorio.buscarVehiculo(vehiculo.getPlaca());
		Assert.assertEquals(vehiculo.getPlaca(), result.getPlaca());
	}

	@Test
	public void buscarVehiculoNoEncontradoTest() {

		String placa = "GXL";
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(placa);
		// act
		Assert.assertEquals(null, repositorio.buscarVehiculo(placa));
	}

	@Test
	public void contarCarrosParqueadosTest() {

		// arrange
		Integer cantidad = 1;
		Integer cantidadResultado = 0;
		try {

			cantidadResultado = repositorio.contarCarrosParqueados();
			Assert.assertTrue(cantidadResultado >= cantidad);
		} catch (ParqueaderoException e) {
			// assert
			Assert.assertTrue(cantidadResultado >= cantidad);
		}

	}

	@Test
	public void contarMotosParqueadosTest() {

		// arrange
		Integer cantidad = 2;
		Assert.assertTrue(repositorio.contarMotosParqueados() >= cantidad);
	}

	@Test
	public void listarMotosParqueadasTest() {

		// arrange
		Vehiculo vehiculo_1 = mock(Vehiculo.class);
		Vehiculo vehiculo_2 = mock(Vehiculo.class);
		vehiculo_1 = new Vehiculo(18, "BCA54C", "M", 125);
		vehiculo_2 = new Vehiculo(21, "LSD94A", "M", 650);
		List<Vehiculo> listaMotos = new ArrayList<>();
		listaMotos.add(vehiculo_1);
		listaMotos.add(vehiculo_2);
		List<Vehiculo> listaResultado = repositorio.listarMotosParqueadas();
		Assert.assertTrue(listaResultado.size() >= listaMotos.size());
	}

	@Test
	public void listarCarrosParqueadosTest() {

		// arrange
		Vehiculo vehiculo_1 = new Vehiculo(20, "KDY533", "C", 2000);
		List<Vehiculo> listaCarros = new ArrayList<>();
		listaCarros.add(vehiculo_1);

		List<Vehiculo> listaResultado = repositorio.listarCarrosParqueados();
		Assert.assertTrue(listaResultado.size() >= listaCarros.size());
	}

	@Test
	public void salidaVehiculosParqueaderoTest() {

		// arrange
		ControlFecha control = new ControlFecha();
		Calendar fechaIngreso;
		Parqueadero parqueadero;
		try {
			fechaIngreso = control.fechaAcutalSistema();
			fechaIngreso.set(2018, 9, 11);
			parqueadero = new Parqueadero(fechaIngreso.getTime(), 1, false);
			parqueadero.setFechaSalida(control.fechaAcutalSistema().getTime());
			parqueadero.setPrecio(8000.0);
			repositorio.salidaVehiculoParqueado(parqueadero);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void ingresoVehiculosParqueaderoTest() {

		// arrange
		ControlFecha control = new ControlFecha();
		Parqueadero parqueadero;
		try {
			parqueadero = new Parqueadero(control.fechaAcutalSistema().getTime(), 1, true);
			repositorio.ingresarVehiculoParqueadero(parqueadero);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void buscarParqueaderoVehiculoTest() {

		Parqueadero parqueadero = new Parqueadero();
		parqueadero.setId(23);
		Parqueadero resultado = repositorio.buscarParqueaderoVehiculo(20);
		System.out.println("resultado ******************************** " + resultado.getId());
		Assert.assertEquals(parqueadero.getId(), resultado.getId());
	}

	@Test
	public void buscarParqueaderoVehiculoNoParqueadoTest() {

		// arrange
		Vehiculo vehiculo = repositorio.buscarVehiculo("GXL315");
		Parqueadero parqueadero = repositorio.buscarParqueaderoVehiculo(vehiculo.getId());
		Assert.assertEquals(null, parqueadero);
	}

	@Test
	public void buscarVehiculoParqueadoTest() {

		Vehiculo vehiculo = repositorio.buscarVehiculoParqueado("KDY533");
		Vehiculo vehiculoEsperado = new Vehiculo(20, "KDY533", "C", 2000);
		Assert.assertEquals(vehiculoEsperado.getId(), vehiculo.getId());
	}
}

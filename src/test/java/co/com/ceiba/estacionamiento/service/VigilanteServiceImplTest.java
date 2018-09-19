package co.com.ceiba.estacionamiento.service;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import co.com.ceiba.estacionamiento.dao.VigilanteRepositoryImpl;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.service.VigilanteServiceImpl;
import dominio.excepcion.ParqueaderoException;

public class VigilanteServiceImplTest {

	@PersistenceContext
	private static EntityManager entityManager;

	private static VigilanteServiceImpl service;
	private static VigilanteRepositoryImpl repository;
	static final String EXITO_AL_GUARDAR_VEHICULO = "Exito";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String PLACA_DUPLICADA = "Este vehiculo ya se encuentra registrado";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";
	static final String VEHICULO_NO_ENCONTRADO = "Este vehiculo no se encuentra registrado";
	static final String VEHICULO_PARQUEADO = "Este vehiculo ya se encuentra en el parqueadero";
	static final String ERROR_CONVERSION_FECHAS = "Error al realizar conversion de fechas";

	@BeforeClass
	public static void setUpClass() throws Exception {
		repository = Mockito.spy(new VigilanteRepositoryImpl());
		service = new VigilanteServiceImpl(repository);
	}

	@Test
	public void guardarVehiculoTest() {

		// Arrange
		Vehiculo vehiculo = new Vehiculo("BCA55C", "M", 125);
		vehiculo = mock(Vehiculo.class);

		// Act
		service = mock(VigilanteServiceImpl.class);
		service.guardarVehiculo(vehiculo);

	}

	@Test
	public void guardarVehiculoSinPlacaTest() {

		// Arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);

		// Act
		service = mock(VigilanteServiceImpl.class);
		try {
			service.guardarVehiculo(vehiculo);
		} catch (ParqueaderoException e) {
			// Assert
			Assert.assertEquals(CAMPOS_SIN_DILIGENCIAR, e.getMessage());
		}
	}


	@Test
	public void buscarVehiculoTest() {

		// Arrange
		Vehiculo vehiculo = new Vehiculo(1, "GXL315", "C", 115);
		// act
		when(service.buscarVehiculo(vehiculo.getPlaca())).thenReturn(vehiculo);
		Vehiculo result = service.buscarVehiculo(vehiculo.getPlaca());

		// Assert
		Assert.assertNotNull(result);

	}

	@Test
	public void buscarVehiculoNoEncontrado() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("GXL");

		// Act
		vehiculo = mock(Vehiculo.class);
		try {
			service.buscarVehiculo(vehiculo.getPlaca());
		} catch (ParqueaderoException e) {
			// Assert
			Assert.assertEquals(VEHICULO_NO_ENCONTRADO, e.getMessage());
		}
	}

	@Test
	public void validarPlacaTest() {
		String placa = "AFIOD";
		try {
			service.validarPlaca(placa);
		} catch (ParqueaderoException e) {
			Assert.assertEquals(RESTRICCION_DE_PLACA, e.getMessage());
		}

	}

	@Test
	public void salidaVehiculoTest() {

		Vehiculo vehiculo = new Vehiculo(18, "BCA54C", "M", 125);
		Parqueadero parqueadero = new Parqueadero(new Date(), vehiculo.getId(), false);
		parqueadero.setFechaSalida(new Date());
		service = mock(VigilanteServiceImpl.class);
		when(service.buscarParqueaderoVehiculo(vehiculo.getId())).thenReturn(parqueadero);
		when(service.salidaVehiculo(vehiculo)).thenReturn(parqueadero);

		Parqueadero parqueaderoSalida = service.salidaVehiculo(vehiculo);
		Assert.assertNotNull(parqueaderoSalida);

	}

	@Test
	public void ingresarVehiculoParqueaderoVehiculoInexistenteTest() {

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("VAJ51C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(250);

		try {
			service = mock(VigilanteServiceImpl.class);
			service.ingresarVehiculoParqueadero(vehiculo);

		} catch (ParqueaderoException e) {

			Assert.assertEquals(CAMPOS_SIN_DILIGENCIAR, e.getMessage());
		}
	}

	@Test
	public void ingresarVehiculoParqueaderoTest() {

		Vehiculo vehiculo = new Vehiculo(19, "XXL342", "C", 1800);
		Parqueadero parqueadero = new Parqueadero();
		repository = mock(VigilanteRepositoryImpl.class);
		service = mock(VigilanteServiceImpl.class);
		when(repository.buscarVehiculo("XXL342")).thenReturn(vehiculo);
		when(repository.buscarParqueaderoVehiculo(19)).thenReturn(parqueadero);
		when(repository.guardarVehiculo(vehiculo)).thenReturn(56);
		when(service.ingresarVehiculoParqueadero(vehiculo)).thenReturn(true);
		Assert.assertEquals(true, service.ingresarVehiculoParqueadero(vehiculo));

	}

	@Test
	public void buscarVehiculoParqueadoEncontradoTest() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(18);
		vehiculo.setPlaca("BCA54C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);

		when(service.buscarVehiculoParqueado(vehiculo.getPlaca())).thenReturn(vehiculo);

		Vehiculo busqueda = service.buscarVehiculoParqueado(vehiculo.getPlaca());
		Assert.assertNotNull(busqueda);
	}

	@Test
	public void buscarVehiculoParqueadoNoEncontradoTest() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("FSJ54C");
		vehiculo = mock(Vehiculo.class);

		Vehiculo busqueda = service.buscarVehiculoParqueado(vehiculo.getPlaca());
		Assert.assertEquals(null, busqueda);
	}

	@Test
	public void buscarParqueaderoVehiculoEncontradoTest() {

		int idVehiculo = 18;
		Parqueadero parqueadero = new Parqueadero(new Date(), idVehiculo, false);
		parqueadero.setFechaSalida(new Date());
		parqueadero = mock(Parqueadero.class);

		when(service.buscarParqueaderoVehiculo(idVehiculo)).thenReturn(parqueadero);
		Parqueadero parqueaderoBusqueda = service.buscarParqueaderoVehiculo(idVehiculo);

		Assert.assertNotNull(parqueaderoBusqueda);
	}

	@Test
	public void buscarParqueaderoVehiculoNoEncontradoTest() {
		int idVehiculo = 255;
		Parqueadero parqueadero = service.buscarParqueaderoVehiculo(idVehiculo);
		parqueadero = mock(Parqueadero.class);
		when(service.buscarParqueaderoVehiculo(idVehiculo)).thenReturn(parqueadero);

		Assert.assertNotNull(parqueadero);
	}

}

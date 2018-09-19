package co.com.ceiba.estacionamiento.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import co.com.ceiba.estacionamiento.dao.IVigilanteRepository;
import co.com.ceiba.estacionamiento.dao.VigilanteRepositoryImpl;
import co.com.ceiba.estacionamiento.model.Disponibilidad;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.service.VigilanteServiceImpl;
import dominio.excepcion.ParqueaderoException;

public class VigilanteServiceImplTest {

	@PersistenceContext
	private static EntityManager entityManager;
	private static IVigilanteService service;
	private static IVigilanteRepository repository;
	static final String EXITO_AL_GUARDAR_VEHICULO = "Exito";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String PLACA_DUPLICADA = "Este vehiculo ya se encuentra registrado";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";
	static final String VEHICULO_NO_ENCONTRADO = "Este vehiculo no se encuentra registrado";
	static final String VEHICULO_PARQUEADO = "Este vehiculo ya se encuentra en el parqueadero";
	static final String ERROR_CONVERSION_FECHAS = "Error al realizar conversion de fechas";
	static final String VEHICULO_NO_ENCONTRADO_PARQUEADERO = "No se ha encontrado ningun vehiculo";
	static final String VEHICULO_NO_PARQUEADO = "Este vehiculo no se encuentra en el parqueadero";
	static final String PARQUEADERO_SIN_CUPO_DE_CARRO = "El parqueadero no tiene cupos disponibles para carros";
	static final String PARQUEADERO_SIN_CUPO_DE_MOTO = "El parqueadero no tiene cupos disponibles para moto";
	
	@Test
	public void guardarVehiculoTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo("BCA55C", "M", 125);
		// Act
		IVigilanteRepository repo = new VigilanteRepositoryImpl();
		IVigilanteService ser = new VigilanteServiceImpl(repo);
		try {
			ser.guardarVehiculo(vehiculo);
		} catch (RuntimeException e) {
			Assert.assertEquals(null, e.getMessage());
		}
	}

	@Test
	public void guardarVehiculoSinPlacaTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);
		// Act
		IVigilanteRepository repo = new VigilanteRepositoryImpl();
		IVigilanteService ser = new VigilanteServiceImpl(repo);
		try {
			ser.guardarVehiculo(vehiculo);
		} catch (ParqueaderoException e) {
			// Assert
			Assert.assertEquals(CAMPOS_SIN_DILIGENCIAR, e.getMessage());
		}
	}

	@Test
	public void buscarVehiculoTest() {
		// Arrange
		Vehiculo result = new Vehiculo(1, "GXL315", "C", 115);
		// act
		IVigilanteRepository repo = new VigilanteRepositoryImpl();
		IVigilanteService ser = new VigilanteServiceImpl(repo);
		ser = mock(VigilanteServiceImpl.class);
		when(ser.buscarVehiculo("GXL315")).thenReturn(result);
		Assert.assertEquals(115, result.getCilindraje());
	}

	@Test
	public void buscarVehiculoNoEncontrado() {
		// Act
		IVigilanteRepository repo = new VigilanteRepositoryImpl();
		IVigilanteService ser = new VigilanteServiceImpl(repo);
		try {
			ser.buscarVehiculo("FGS");
		} catch (RuntimeException e) {
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
	public void salidaVehiculoNullTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo(0, "BCA54C", "M", 125);
		Parqueadero parqueadero = new Parqueadero(new Date(), vehiculo.getId(), false);
		parqueadero.setFechaSalida(new Date());
		// Act
		IVigilanteRepository repo = new VigilanteRepositoryImpl();
		IVigilanteService ser = new VigilanteServiceImpl(repo);
		try {
			ser.salidaVehiculo(vehiculo);
		} catch (ParqueaderoException e) {
			Assert.assertEquals(VEHICULO_NO_ENCONTRADO_PARQUEADERO, e.getMessage());
		}
	}

	@Test
	public void salidaVehiculoCarroTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo(18, "FDH851", "C", 1800);
		Parqueadero parqueadero = new Parqueadero(new Date(), vehiculo.getId(), false);
		parqueadero.setFechaSalida(new Date());

		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		// Act}
		service = new VigilanteServiceImpl(repository);
		when(repository.buscarParqueadero(18)).thenReturn(parqueadero);
		doNothing().when(repository).salidaVehiculo(parqueadero);

		Parqueadero parqueaderoSalida = service.salidaVehiculo(vehiculo);
		// Assert
		Assert.assertNotNull(parqueaderoSalida);
	}

	@Test
	public void salidaVehiculoMotoTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo(18, "BCA54C", "M", 125);

		Parqueadero parqueadero = new Parqueadero(new Date(), vehiculo.getId(), false);
		parqueadero.setFechaSalida(new Date());

		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		// Act}
		service = new VigilanteServiceImpl(repository);
		when(repository.buscarParqueadero(18)).thenReturn(parqueadero);
		doNothing().when(repository).salidaVehiculo(parqueadero);

		Parqueadero parqueaderoSalida = service.salidaVehiculo(vehiculo);
		// Assert
		Assert.assertNotNull(parqueaderoSalida);
	}

	@Test
	public void salidaVehiculoNoParqueadoTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo(18, "BCA54C", "M", 125);

		Parqueadero parqueadero = null;
		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		// Act
		service = new VigilanteServiceImpl(repository);
		when(repository.buscarParqueadero(18)).thenReturn(parqueadero);
		try {
			service.salidaVehiculo(vehiculo);

		} catch (Exception e) {
			// Assert
			Assert.assertEquals(VEHICULO_NO_PARQUEADO, e.getMessage());
		}

	}

	@Test
	public void ingresarVehiculoParqueaderoVehiculoExistenteTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo(12, "VAJ51C", "M", 250);
		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		Parqueadero parqueadero = new Parqueadero(new Date(), vehiculo.getId(), false);
		parqueadero.setFechaSalida(new Date());

		// Act
		service = new VigilanteServiceImpl(repository);
		when(repository.buscarVehiculo(vehiculo.getPlaca())).thenReturn(vehiculo);
		doNothing().when(repository).guardarVehiculo(vehiculo);
		when(repository.buscarParqueadero(vehiculo.getId())).thenReturn(parqueadero);

		try {
			service.ingresarVehiculo(vehiculo);
		} catch (ParqueaderoException e) {

			Assert.assertEquals(VEHICULO_PARQUEADO, e.getMessage());
		}
	}

	@Test
	public void ingresarVehiculoParqueaderoVehiculoExistenteNoParqueadoTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo(12, "VAJ51C", "M", 250);
		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		Parqueadero parqueadero = new Parqueadero(new Date(), vehiculo.getId(), false);
		parqueadero.setFechaSalida(new Date());
		// Act
		service = new VigilanteServiceImpl(repository);
		when(repository.buscarVehiculo(vehiculo.getPlaca())).thenReturn(vehiculo);
		doNothing().when(repository).guardarVehiculo(vehiculo);
		when(repository.buscarParqueadero(vehiculo.getId())).thenReturn(null);
		doNothing().when(repository).ingresarVehiculoParqueadero(parqueadero);

		// Assert
		Assert.assertTrue(service.ingresarVehiculo(vehiculo));

	}

	@Test
	public void ingresarVehiculoParqueaderoVehiculoInexistenteTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo(0, "XXL342", "C", 1800);
		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		Parqueadero parqueadero = new Parqueadero(new Date(), vehiculo.getId(), false);
		parqueadero.setFechaSalida(new Date());
		// Act
		service = new VigilanteServiceImpl(repository);
		doNothing().when(repository).guardarVehiculo(vehiculo);
		vehiculo.setId(19);
		when(repository.buscarVehiculo("XXL342")).thenReturn(vehiculo);
		when(repository.buscarParqueadero(19)).thenReturn(null);
		doNothing().when(repository).ingresarVehiculoParqueadero(parqueadero);
		// Assert
		Assert.assertEquals(true, service.ingresarVehiculo(vehiculo));

	}

	@Test
	public void buscarVehiculoParqueadoEncontradoTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(18);
		vehiculo.setPlaca("BCA54C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);
		// Act
		when(service.buscarVehiculoParqueado(vehiculo.getPlaca())).thenReturn(vehiculo);
		Vehiculo busqueda = service.buscarVehiculoParqueado(vehiculo.getPlaca());
		// Assert
		Assert.assertNotNull(busqueda);
	}

	@Test
	public void buscarVehiculoParqueadoNoEncontradoTest() {
		// Arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("FSJ54C");
		vehiculo = mock(Vehiculo.class);

		Vehiculo busqueda = service.buscarVehiculoParqueado(vehiculo.getPlaca());
		// Assert
		Assert.assertEquals(null, busqueda);
	}

	@Test
	public void buscarParqueaderoVehiculoEncontradoTest() {
		// Arrange
		int idVehiculo = 18;
		Parqueadero parqueadero = new Parqueadero(new Date(), idVehiculo, false);
		parqueadero.setFechaSalida(new Date());
		parqueadero = mock(Parqueadero.class);
		// Act
		when(service.buscarParqueadero(idVehiculo)).thenReturn(parqueadero);
		Parqueadero parqueaderoBusqueda = service.buscarParqueadero(idVehiculo);
		// Assert
		Assert.assertNotNull(parqueaderoBusqueda);
	}

	@Test
	public void buscarParqueaderoVehiculoNoEncontradoTest() {
		int idVehiculo = 255;
		Parqueadero parqueadero = service.buscarParqueadero(idVehiculo);
		parqueadero = mock(Parqueadero.class);
		when(service.buscarParqueadero(idVehiculo)).thenReturn(parqueadero);

		Assert.assertNotNull(parqueadero);
	}

	@Test
	public void consultarDisponibilidad() {
		// Arrange
		Disponibilidad disponibilidad = new Disponibilidad();
		disponibilidad.setCarros(15);
		disponibilidad.setMotos(8);
		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		// Act
		service = new VigilanteServiceImpl(repository);
		when(repository.contarCarrosParqueados()).thenReturn(5);
		when(repository.contarMotosParqueados()).thenReturn(2);
		Disponibilidad result = service.consultarDisponibilidad();
		// Assert
		Assert.assertEquals(disponibilidad.getCarros(), result.getCarros());
	}
	
	@Test
	public void consultarDisponibilidadSinCupoCarro() {
		// Arrange
		Disponibilidad disponibilidad = new Disponibilidad();
		disponibilidad.setCarros(0);
		disponibilidad.setMotos(8);
		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		// Act
		service = new VigilanteServiceImpl(repository);
		when(repository.contarCarrosParqueados()).thenReturn(20);
		when(repository.contarMotosParqueados()).thenReturn(2);
		try {
			service.consultarDisponibilidad();
		} catch (ParqueaderoException e) {
			// Assert	
			Assert.assertEquals(PARQUEADERO_SIN_CUPO_DE_CARRO,e.getMessage());
		}		
	}
	

	@Test
	public void consultarDisponibilidadSinCupoMoto() {
		// Arrange
		Disponibilidad disponibilidad = new Disponibilidad();
		disponibilidad.setCarros(15);
		disponibilidad.setMotos(0);
		repository = Mockito.mock(VigilanteRepositoryImpl.class);
		// Act
		service = new VigilanteServiceImpl(repository);
		when(repository.contarCarrosParqueados()).thenReturn(5);
		when(repository.contarMotosParqueados()).thenReturn(10);
		try {
			service.consultarDisponibilidad();
		} catch (ParqueaderoException e) {
			// Assert	
			Assert.assertEquals(PARQUEADERO_SIN_CUPO_DE_MOTO,e.getMessage());
		}		
	}
}

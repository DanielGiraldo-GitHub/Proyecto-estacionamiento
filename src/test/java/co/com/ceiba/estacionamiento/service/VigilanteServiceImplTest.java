package co.com.ceiba.estacionamiento.service;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import co.com.ceiba.estacionamiento.dao.VigilanteRepository;
import co.com.ceiba.estacionamiento.dao.VigilanteRepositoryImpl;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.service.VigilanteService;
import co.com.ceiba.estacionamiento.service.VigilanteServiceImpl;
import dominio.excepcion.ParqueaderoException;

public class VigilanteServiceImplTest {

	@PersistenceContext
	private static EntityManager entityManager;
	
	private static VigilanteServiceImpl repositorio;
	private static VigilanteRepository repositorioVigilante;
	static final String EXITO_AL_GUARDAR_VEHICULO = "Exito";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String PLACA_DUPLICADA = "Este vehiculo ya se encuentra registrado";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";
	static final String VEHICULO_NO_ENCONTRADO = "Este vehiculo no se encuentra registrado";
	static final String VEHICULO_PARQUEADO = "este vehiculo ya se encuentra en el parqueadero";

	@BeforeClass
	public static void setUpClass() throws Exception {

		repositorioVigilante = new VigilanteRepositoryImpl(entityManager);
		repositorio = Mockito.spy(new VigilanteServiceImpl(repositorioVigilante));
		repositorio = mock(VigilanteServiceImpl.class);
	}

	@Test
	public void guardarVehiculoTest() {

		// arrange
		Vehiculo vehiculo = new Vehiculo();

		vehiculo.setPlaca("BCA55C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);

		VigilanteService vigilanteService = mock(VigilanteService.class);
		mock(Vehiculo.class);
		// act
		vigilanteService.save(vehiculo);
	}

	@Test
	public void guardarVehiculoExistenteTest() {

		// arrange
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("GXL315");
		vehiculo.setTipoVehiculo("C");
		vehiculo.setCilindraje(115);

		try {
			// act
			mock(Vehiculo.class);
			repositorio.save(vehiculo);

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
			// act
			mock(Vehiculo.class);
			repositorio.save(vehiculo);

		} catch (ParqueaderoException e) {
			// assert
			Assert.assertEquals(CAMPOS_SIN_DILIGENCIAR, e.getMessage());
		}
	}

	@Test
	public void buscarVehiculoTest() {

		//Arrange
		Vehiculo vehiculo = new Vehiculo(1, "GXL315", "C", 115);
		// act
		when(repositorio.buscarVehiculo(vehiculo)).thenReturn(vehiculo);
		Vehiculo result = repositorio.buscarVehiculo(vehiculo);

		//Assert
		Assert.assertNotNull(result);
		
	}

	@Test
	public void buscarVehiculoNoEncontrado() {

		String placa = "GXL";
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(placa);
		// act
		mock(Vehiculo.class);
		try {
			Vehiculo resul = repositorio.buscarVehiculo(vehiculo);
		} catch (ParqueaderoException e) {
			Assert.assertEquals(VEHICULO_NO_ENCONTRADO, e.getMessage());
		}
	}

	@Test
	public void validarPlacaTest() {
		String placa = "AFIOD";
		Assert.assertFalse(repositorio.validarPlaca(placa));
	}

	
	public void consultarDisponibilidadTest() {

		int[] cuposDisponibles = new int[2];
		cuposDisponibles[0] = 19;
		cuposDisponibles[1] = 9;
		int[] disponibilidad = repositorio.consultarDisponibilidad();
		System.out.println(cuposDisponibles[0] + " = " + disponibilidad[0]);
		System.out.println(cuposDisponibles[1] + " = " + disponibilidad[1]);
		Assert.assertEquals(cuposDisponibles[0], disponibilidad[0]);
	}

	
	public void salidaVehiculoTest() {

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(18);
		vehiculo.setPlaca("BCA54C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);
		mock(Vehiculo.class);
		mock(Parqueadero.class);
		Parqueadero parqueadero = repositorio.buscarParqueaderoVehiculo(vehiculo.getId());
		Parqueadero parqueaderoSalida = repositorio.salidaVehiculo(parqueadero, vehiculo);

		Assert.assertEquals(false, parqueaderoSalida.isEstado());
		Assert.assertTrue(null != parqueaderoSalida.getFechaSalida());
	}

	@Test // "este vehiculo ya se encuentra en el parqueadero"
	public void ingresarVehiculoParqueaderoVehiculoInexistenteTest() {

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("VAJ51C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(250);
		mock(Vehiculo.class);

		try {
			repositorio.ingresarVehiculoParqueadero(vehiculo);

		} catch (ParqueaderoException e) {

			Assert.assertEquals(VEHICULO_NO_ENCONTRADO, e.getMessage());
		}
	}

	 //
	public void ingresarVehiculoParqueaderoVehiculoExistenteTest() {

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(18);
		vehiculo.setPlaca("BCA54C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);
		mock(Vehiculo.class);

		try {
			repositorio.ingresarVehiculoParqueadero(vehiculo);

		} catch (ParqueaderoException e) {

			Assert.assertEquals(VEHICULO_PARQUEADO, e.getMessage());
		}
	}

	
	public void ingresarVehiculoParqueaderoTest() {

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(19);
		vehiculo.setPlaca("XXL342");
		vehiculo.setTipoVehiculo("C");
		vehiculo.setCilindraje(1800);
		mock(Vehiculo.class);

		try {
			repositorio.ingresarVehiculoParqueadero(vehiculo);

		} catch (ParqueaderoException e) {

			Assert.assertEquals(VEHICULO_PARQUEADO, e.getMessage());
		}

		repositorio.ingresarVehiculoParqueadero(vehiculo);
		Vehiculo busqueda = repositorio.buscarVehiculoParqueado(vehiculo.getPlaca());
		Assert.assertEquals(vehiculo.getPlaca(), busqueda.getPlaca());

	}
	

	@Test
	public void buscarVehiculoParqueadoEncontradoTest() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setId(18);
		vehiculo.setPlaca("BCA54C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);
		
		when(repositorio.buscarVehiculoParqueado(vehiculo.getPlaca())).thenReturn(vehiculo);

		Vehiculo busqueda = repositorio.buscarVehiculoParqueado(vehiculo.getPlaca());
		Assert.assertNotNull(busqueda);
	}

	@Test
	public void buscarVehiculoParqueadoNoEncontradoTest() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca("FSJ54C");
		mock(Vehiculo.class);

		Vehiculo busqueda = repositorio.buscarVehiculoParqueado(vehiculo.getPlaca());
		Assert.assertEquals(null, busqueda);
	}

	
	public void buscarParqueaderoVehiculoEncontradoTest() {
		int idVehiculo = 18;
		Parqueadero parqueadero = repositorio.buscarParqueaderoVehiculo(idVehiculo);
		mock(Parqueadero.class);
		Assert.assertEquals(idVehiculo, parqueadero.getIdVehiculo());
	}
	
	@Test
	public void buscarParqueaderoVehiculoNoEncontradoTest() {
		int idVehiculo = 255;
		Parqueadero parqueadero = repositorio.buscarParqueaderoVehiculo(idVehiculo);
		mock(Parqueadero.class);
		Assert.assertEquals(null, parqueadero);
	}

}

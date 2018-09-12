package co.com.ceiba.estacionamiento.service;

import static org.mockito.Mockito.mock;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import co.com.ceiba.estacionamiento.dao.VigilanteRepository;
import co.com.ceiba.estacionamiento.dao.VigilanteRepositoryImpl;
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
	
	@BeforeClass
	public static void setUpClass() throws Exception {

		entityManager = (EntityManager) Persistence.createEntityManagerFactory("TestPersistence").createEntityManager();
		repositorioVigilante = new VigilanteRepositoryImpl(entityManager);
		repositorio = new VigilanteServiceImpl(repositorioVigilante);
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
	public void buscarVehiculo() {

		mock(Vehiculo.class);
		Vehiculo vehiculo = new Vehiculo(1, "GXL315", "C", 115);
		// act
		Vehiculo result = repositorio.buscarVehiculo(vehiculo);

		Assert.assertEquals(vehiculo.getPlaca(), result.getPlaca());
	}

	@Test
	public void buscarVehiculoNoEncontrado() {

		String placa = "GXL";
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(placa);
		// act
		mock(Vehiculo.class);
		try {
			Vehiculo resul= repositorio.buscarVehiculo(vehiculo);
		} catch (ParqueaderoException e) {
			Assert.assertEquals(VEHICULO_NO_ENCONTRADO,e.getMessage());
		}
		
	}

}

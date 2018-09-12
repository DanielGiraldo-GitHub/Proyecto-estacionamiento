package co.com.ceiba.estacionamiento.service.integracion;

import static org.mockito.Mockito.mock;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import co.com.ceiba.estacionamiento.dao.VigilanteRepositoryImpl;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.service.VigilanteService;
import co.com.ceiba.estacionamiento.service.VigilanteServiceImpl;
import dominio.excepcion.ParqueaderoException;

public class VigilanteServiceImplTest {

	@PersistenceContext
	private static EntityManager entityManager;
	private static VigilanteRepositoryImpl repositorio ;
	static final String EXITO_AL_GUARDAR_VEHICULO = "Exito";
	static final String CAMPOS_SIN_DILIGENCIAR = "los campos obligatorios no estan diligenciados";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";

	@BeforeClass
	public static void setUpClass() throws Exception {
		
			entityManager = (EntityManager) Persistence.createEntityManagerFactory("TestPersistence")
					.createEntityManager();	
			 repositorio = new VigilanteRepositoryImpl(entityManager);
	}

	@Test
	public void guardarVehiculoTest() {

		// arrange
		Vehiculo vehiculo = new Vehiculo();

		vehiculo.setPlaca("BCA55C");
		vehiculo.setTipoVehiculo("M");
		vehiculo.setCilindraje(125);

		VigilanteService vigilanteService = mock(VigilanteService.class);
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

		VigilanteService vigilanteService = mock(VigilanteService.class);
		try {
			// act
			vigilanteService.save(vehiculo);

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

		VigilanteService vigilanteService = mock(VigilanteService.class);
		try {
			// act
			vigilanteService.save(vehiculo);

		} catch (ParqueaderoException e) {
			// assert
			Assert.assertEquals(CAMPOS_SIN_DILIGENCIAR, e.getMessage());
		}
	}

	@Test
	public void buscarVehiculo() {

		VigilanteServiceImpl vigilanteService = mock(VigilanteServiceImpl.class);
		vigilanteService = new VigilanteServiceImpl(repositorio);
		Vehiculo vehiculo = new Vehiculo(1, "GXL315", "C", 115);
		// act
		System.out.println(" vehiculo id " + vehiculo.getId() + " placa " + vehiculo.getPlaca()+" cilindraje "+vehiculo.getCilindraje() + " tipoVehiculo "+vehiculo.getTipoVehiculo());
		Vehiculo result = vigilanteService.buscarVehiculo(vehiculo);
		System.out.println("result vehiculo id " + result.getId() + " placa " + result.getPlaca()+" cilindraje "+result.getCilindraje() + " tipoVehiculo "+result.getTipoVehiculo());
		Assert.assertEquals(vehiculo.getPlaca(), result.getPlaca());
	}

	@Test
	public void buscarVehiculoNoEncontrado() {

		String placa = "GXL";
		VigilanteServiceImpl vigilanteService = mock(VigilanteServiceImpl.class);
		vigilanteService = new VigilanteServiceImpl(repositorio);
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(placa);
		// act
		Assert.assertEquals(null, vigilanteService.buscarVehiculo(vehiculo));
	}

}
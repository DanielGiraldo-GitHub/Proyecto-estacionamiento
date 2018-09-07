package co.com.ceiba.estacionamiento.daoTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import co.com.ceiba.estacionamiento.dao.VehiculoRepositoryImpl;


public class TestVehiculoRepositoryImpl{

	private static EntityManagerFactory entityManagerFactory;

	private static EntityManager entityManager;

	@Autowired
	private VehiculoRepositoryImpl vehiculoRepositoryImpl;

	@BeforeClass
	public static void beforeClass() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceTest");
			entityManager = entityManagerFactory.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
/*
	@Test
	public void guardarVehiculoTest() {

		Vehiculo vehiculo = new Vehiculo("GXL315", "C", 1600);
		     
		
		if (!validate(vehiculo))
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"los campos obligatorios no estan diligenciados");

		entityManager.persist(vehiculo);
		return new RestResponse(HttpStatus.OK.value(), "Exito");
	}

	@Test
	public Object buscarVehiculo() {

		String sentencia = "SELECT v FROM Vehiculo v WHERE v.placa ='" + placa + "'";
		Query query = entityManager.createQuery(sentencia);
		return (query.getSingleResult());
	}

	@Test
	public boolean validate() {

		boolean expression = true;
		if (vehiculo.getPlaca() != null && vehiculo.getTipoVehiculo() != null && vehiculo.getCilindraje() > 0)
			return expression;
		return !expression;
	}*/
}

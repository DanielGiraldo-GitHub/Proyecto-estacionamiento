package co.com.ceiba.estacionamiento.dao;

import java.sql.ResultSet;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.RestResponse;

@Repository
@Transactional
public class VehiculoRepositoryImpl implements VehiculoRepository {

	@PersistenceContext
	public EntityManager entityManager;
	
	ResultSet result;

	@Override
	public RestResponse guardarVehiculo(Vehiculo vehiculo) {

		entityManager.persist(vehiculo);
		return new RestResponse(HttpStatus.OK.value(), "Exito");
	}

	@Override
	public Object buscarVehiculo(String placa) {

		Query query = entityManager.createQuery("SELECT v FROM Vehiculo v WHERE v.placa =?");
		query.setParameter(0, placa);
		return (query.getSingleResult());	
	}

	@Override
	public Object buscarVehiculoPorId(int idVehiculo) {
		return 	entityManager.find(Vehiculo.class, idVehiculo);
	}

}

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
		
		if (!validate(vehiculo))
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"los campos obligatorios no estan diligenciados");
		
		entityManager.persist(vehiculo);
		return new RestResponse(HttpStatus.OK.value(), "Exito");
	}

	@Override
	public Object buscarVehiculo(String placa) {

		String sentencia = "SELECT v FROM Vehiculo v WHERE v.placa ='" + placa + "'";
		Query query = entityManager.createQuery(sentencia);
		return (query.getSingleResult());
	}

	public boolean validate(Vehiculo vehiculo) {

		boolean expression = true;
		if (vehiculo.getPlaca() != null && vehiculo.getTipoVehiculo() != null && vehiculo.getCilindraje() > 0)
			return expression;
		return !expression;
	}
}

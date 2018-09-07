package co.com.ceiba.estacionamiento.dao;

import java.sql.ResultSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ParqueaderoRepositoryImpl implements ParqueaderoRepository {

	@PersistenceContext
	public EntityManager entityManager;

	ResultSet result;
	
	@Override
	public Integer contarCarrosParqueados() {

		String sentencia = "SELECT COUNT(p.id) "
				         + "FROM   Parqueadero p JOIN Vehiculo v "
				         + "ON     p.idVehiculo   = v.id WHERE p.estado <> 0 "
				         + "AND    v.tipoVehiculo = 'C'";
		Query query = entityManager.createQuery(sentencia);
		
		return Integer.parseInt(query.getSingleResult().toString());			
	}
	
	@Override
	public Integer contarMotosParqueados() {

		String sentencia = "SELECT COUNT(p.id) "
				         + "FROM   Parqueadero p JOIN Vehiculo v "
				         + "ON     p.idVehiculo   = v.id WHERE p.estado <> 0 "
				         + "AND    v.tipoVehiculo = 'M'";
		Query query = entityManager.createQuery(sentencia);
		
		return  Integer.parseInt(query.getSingleResult().toString());			
	}

}

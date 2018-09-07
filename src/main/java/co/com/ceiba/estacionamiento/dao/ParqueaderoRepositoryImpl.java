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
	public int contarCarrosParqueados() {

		String sentencia = "SELECT COUNT(p.ID) "
				         + "FROM   parqueadero p JOIN vehiculo v "
				         + "ON     p.ID_VEHICULO   = v.ID WHERE p.ESTADO <> 0 "
				         + "AND    v.TIPO_VEHICULO = 'C';";
		Query query = entityManager.createQuery(sentencia);
		
		return (int) query.getSingleResult();			
	}
	
	@Override
	public int contarMotosParqueados() {

		String sentencia = "SELECT COUNT(p.ID) "
				         + "FROM   parqueadero p JOIN vehiculo v "
				         + "ON     p.ID_VEHICULO   = v.ID WHERE p.ESTADO <> 0 "
				         + "AND    v.TIPO_VEHICULO = 'M';";
		Query query = entityManager.createQuery(sentencia);
		
		return (int) query.getSingleResult();			
	}

}

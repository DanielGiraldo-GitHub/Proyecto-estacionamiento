package co.com.ceiba.estacionamiento.dao;

import java.sql.ResultSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;

@Repository
@Transactional
public class VigilanteRepositoryImpl implements VigilanteRepository {
	
	@PersistenceContext
	public EntityManager entityManager;

	ResultSet result;

	@Override
	public Integer contarCarrosParqueados() {

		String sentencia = "SELECT COUNT(p.id) " + "FROM   Parqueadero p JOIN Vehiculo v "
				+ "ON     p.idVehiculo   = v.id WHERE p.estado <> 0 " + "AND    v.tipoVehiculo = 'C'";
		Query query = entityManager.createQuery(sentencia);

		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public Integer contarMotosParqueados() {

		String sentencia = "SELECT COUNT(p.id) " + "FROM   Parqueadero p JOIN Vehiculo v "
				+ "ON     p.idVehiculo   = v.id WHERE p.estado <> 0 " + "AND    v.tipoVehiculo = 'M'";
		Query query = entityManager.createQuery(sentencia);

		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public List<Vehiculo> listarCarrosParqueados() {
		try {
			Query query = entityManager.createQuery("SELECT  v.placa,v.tipoVehiculo,v.cilindraje, p.fehcaIngreso "
					+ "FROM Parqueadero p join Vehiculo v on p.idVehiculo = v.id WHERE   v.tipoVehiculo = 'C' and p.estado <> 0");
			return  query.getResultList();
			
		} catch (Exception e) {
			throw new PersistenceException("Error al cargar los carro que se encuentran estacionados en el parqueadero");
		}
		
		}

	@Override
	public List<Vehiculo> listarMotosParqueadas() {
		try {
			Query query = entityManager.createQuery("SELECT  v.placa,v.tipoVehiculo,v.cilindraje, p.fehcaIngreso "
					+ "                               FROM    Parqueadero p join Vehiculo v on p.idVehiculo = v.id "
					+ "                               WHERE   v.tipoVehiculo = 'M' and p.estado <> 0");
			return  query.getResultList();
			
		} catch (Exception e) {
			throw new PersistenceException("Error al cargar las motos que se encuentran estacionadas en el parqueadero");
		}
	}
	
	@Override
	public void salidaVehiculoParqueado(Parqueadero parqueadero) {
		entityManager.merge(parqueadero);	
	}

	@Override
	public void ingresarVehiculoParqueadero(Parqueadero parqueadero) {
		entityManager.persist(parqueadero);	
	}
	
	@Override
	public void guardarVehiculo(Vehiculo vehiculo) throws RuntimeException {
		entityManager.persist(vehiculo);
		
	}

	@Override
	public Vehiculo buscarVehiculo(String placa) {

		Query query = entityManager.createQuery("SELECT v FROM Vehiculo v WHERE v.placa =?");
		query.setParameter(0, placa);
		return (Vehiculo) (query.getSingleResult());	
	}

	@Override
	public Vehiculo buscarVehiculoParqueado(String placa) {
		try {
			Query query = entityManager.createQuery("SELECT   v "
					+ "                               FROM    Parqueadero p join Vehiculo v on p.idVehiculo = v.id "
					+ "                               WHERE   p.estado <> 0 AND v.placa =?");
			query.setParameter(0, placa);
			return (Vehiculo) (query.getSingleResult());	
		} catch (Exception e) {
			return null;
		}
			
	}
	@Override
	public Vehiculo buscarVehiculoPorId(int idVehiculo) {
		return 	entityManager.find(Vehiculo.class, idVehiculo);
	}

	@Override
	public Parqueadero buscarParqueaderoVehiculo(int idVehiculo) {
		try {
			Query query = entityManager.createQuery("SELECT p FROM Parqueadero p WHERE p.estado <> 0 AND  p.idVehiculo =?");
			query.setParameter(0, idVehiculo);
			return (Parqueadero) (query.getSingleResult());	
		} catch (Exception e) {
			return null;
		}
	}

	
}

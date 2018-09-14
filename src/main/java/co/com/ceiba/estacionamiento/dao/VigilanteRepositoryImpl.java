package co.com.ceiba.estacionamiento.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	
	public VigilanteRepositoryImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

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
			Query query = entityManager.createQuery("SELECT  v.placa,v.tipoVehiculo,v.cilindraje, p.fehcaIngreso "
					+ "FROM Parqueadero p join Vehiculo v on p.idVehiculo = v.id WHERE   v.tipoVehiculo = 'C' and p.estado <> 0");	
			@SuppressWarnings("unchecked")
			List<Vehiculo> lista = query.getResultList();
			return lista;
	}

	@Override
	public List<Vehiculo> listarMotosParqueadas() {
			Query query = entityManager.createQuery("SELECT  v.placa,v.tipoVehiculo,v.cilindraje, p.fehcaIngreso "
					+ "                               FROM    Parqueadero p join Vehiculo v on p.idVehiculo = v.id "
					+ "                               WHERE   v.tipoVehiculo = 'M' and p.estado <> 0");
			@SuppressWarnings("unchecked")
			List<Vehiculo> lista = query.getResultList();
			return lista;
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
	public void guardarVehiculo(Vehiculo vehiculo) {
		entityManager.persist(vehiculo);
	}

	@Override
	public Vehiculo buscarVehiculo(String placa) {
		try {
			Query query = entityManager.createQuery("SELECT v FROM Vehiculo v WHERE v.placa =?");
			query.setParameter(0, placa);
			return (Vehiculo) (query.getSingleResult());
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public Vehiculo buscarVehiculoParqueado(String placa) {
		try {
			Query query = entityManager.createQuery("SELECT   v "
					+ "                               FROM    Parqueadero p join Vehiculo v on p.idVehiculo = v.id "
					+ "                               WHERE   p.estado <> 0 AND v.placa =?");
			query.setParameter(0, placa);
			return (Vehiculo) (query.getSingleResult());
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public Vehiculo buscarVehiculoPorId(int idVehiculo) {
		return entityManager.find(Vehiculo.class, idVehiculo);
	}

	@Override
	public Parqueadero buscarParqueaderoVehiculo(int idVehiculo) {
		try {
			Query query = entityManager
					.createQuery("SELECT p FROM Parqueadero p WHERE p.estado <> 0 AND  p.idVehiculo =?");
			query.setParameter(0, idVehiculo);
			return (Parqueadero) (query.getSingleResult());
		} catch (RuntimeException e) {
			return null;
		}
	}
}

package co.com.ceiba.estacionamiento.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import co.com.ceiba.estacionamiento.model.Parqueadero;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import dominio.excepcion.ParqueaderoException;

@Repository
@Transactional
public class VigilanteRepositoryImpl implements IVigilanteRepository {

	@PersistenceContext
	public EntityManager entityManager;
	static final String VEHICULO_NO_ENCONTRADO = "Este vehiculo no se encuentra registrado";
	static final String VEHICULO_NO_ENCONTRADO_PARQUEADERO = "No se ha encontrado ningun vehiculo";

	public VigilanteRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public VigilanteRepositoryImpl() {
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
		Query query = entityManager.createQuery("SELECT  v "
				+ "FROM Parqueadero p join Vehiculo v on p.idVehiculo = v.id WHERE   v.tipoVehiculo = 'C' and p.estado <> 0");
		@SuppressWarnings("unchecked")
		List<Vehiculo> lista = query.getResultList();
		return lista;
	}

	@Override
	public List<Vehiculo> listarMotosParqueadas() {
		Query query = entityManager.createQuery("SELECT  v "
				+ "                               FROM    Parqueadero p join Vehiculo v on p.idVehiculo = v.id "
				+ "                               WHERE   v.tipoVehiculo = 'M' and p.estado <> 0");
		@SuppressWarnings("unchecked")
		List<Vehiculo> lista = query.getResultList();
		return lista;
	}

	@Override
	public void salidaVehiculo(Parqueadero parqueadero) {
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
			Query query = entityManager.createQuery("SELECT v FROM Vehiculo v WHERE v.placa =:placa");
			query.setParameter("placa", placa);
			return (Vehiculo) (query.getSingleResult());
		} catch (RuntimeException e) {
			throw new ParqueaderoException(VEHICULO_NO_ENCONTRADO);
		}
	}

	@Override
	public Vehiculo buscarVehiculoParqueado(String placa) {
		try {
			Query query = entityManager.createQuery("SELECT   v "
					+ "                               FROM    Parqueadero p join Vehiculo v on p.idVehiculo = v.id "
					+ "                               WHERE   p.estado <> 0 AND v.placa =:placa");
			query.setParameter("placa", placa);
			return (Vehiculo) (query.getSingleResult());
		} catch (RuntimeException e) {
			throw new ParqueaderoException(VEHICULO_NO_ENCONTRADO_PARQUEADERO);
		}
	}

	@Override
	public Parqueadero buscarParqueadero(int idVehiculo) {
		try {
			Query query = entityManager
					.createQuery("SELECT p FROM Parqueadero p WHERE p.estado <> 0 AND  p.idVehiculo =:idVehiculo");
			query.setParameter("idVehiculo", idVehiculo);
			return (Parqueadero) (query.getSingleResult());
		} catch (RuntimeException e) {
			throw new ParqueaderoException(VEHICULO_NO_ENCONTRADO_PARQUEADERO);
		}
	}
}

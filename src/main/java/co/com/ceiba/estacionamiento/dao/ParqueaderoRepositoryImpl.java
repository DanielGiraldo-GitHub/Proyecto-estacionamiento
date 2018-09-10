package co.com.ceiba.estacionamiento.dao;

import java.sql.ResultSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import co.com.ceiba.estacionamiento.model.Parqueadero;

@Repository
@Transactional
public class ParqueaderoRepositoryImpl implements ParqueaderoRepository {

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
	public JSONArray listarCarrosParqueados() {
		JSONArray array = new JSONArray();
		try {
			Query query = entityManager.createQuery("SELECT  v.placa,v.tipoVehiculo,v.cilindraje, p.fehcaIngreso "
					+ "                               FROM    Parqueadero p join Vehiculo v on p.idVehiculo = v.id "
					+ "                               WHERE   v.tipoVehiculo = 'C' and p.estado <> 0");
			List lista = query.getResultList();
			for (int i = 0; i < lista.size(); i++) {
				JSONObject json = new JSONObject();
				Object[] object = (Object[]) lista.get(i);
				json.put("placa", object[0] != null ? object[0].toString() : "");
				json.put("tipoVehiculo", object[1] != null ? object[1].toString() : "");
				json.put("cilindraje", object[2] != null ? object[2].toString() : "");
				json.put("fehcaIngreso", object[3] != null ? object[3].toString() : "");
				array.put(json);
			}
		} catch (Exception e) {
			throw new PersistenceException("ERRO AL LISTAR LAS MOTOS PARQUEADAS");
		}
		return array;
		}

	@Override
	public JSONArray listarMotosParqueadas() {
		JSONArray array = new JSONArray();
		try {
			Query query = entityManager.createQuery("SELECT  v.placa,v.tipoVehiculo,v.cilindraje, p.fehcaIngreso "
					+ "                               FROM    Parqueadero p join Vehiculo v on p.idVehiculo = v.id "
					+ "                               WHERE   v.tipoVehiculo = 'M' and p.estado <> 0");
			List lista = query.getResultList();
			for (int i = 0; i < lista.size(); i++) {
				JSONObject json = new JSONObject();
				Object[] object = (Object[]) lista.get(i);
				json.put("placa", object[0] != null ? object[0].toString() : "");
				json.put("tipoVehiculo", object[1] != null ? object[1].toString() : "");
				json.put("cilindraje", object[2] != null ? object[2].toString() : "");
				json.put("fehcaIngreso", object[3] != null ? object[3].toString() : "");
				array.put(json);
			}
		} catch (Exception e) {
			throw new PersistenceException("ERRO AL LISTAR LAS MOTOS PARQUEADAS");
		}
		return array;
	}
	
	@Override
	public void salidaVehiculoParqueado(Parqueadero parqueadero) {
		entityManager.merge(parqueadero);	
	}

	@Override
	public void ingresarVehiculoParqueadero(Parqueadero parqueadero) {
		entityManager.persist(parqueadero);
		
	}
}

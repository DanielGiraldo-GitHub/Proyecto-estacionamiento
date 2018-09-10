package co.com.ceiba.estacionamiento.dao;

import org.json.JSONArray;
import co.com.ceiba.estacionamiento.model.Parqueadero;

public interface ParqueaderoRepository {

	Integer contarCarrosParqueados();

	Integer contarMotosParqueados();

	JSONArray listarCarrosParqueados();
	
	JSONArray listarMotosParqueadas();
	
	void salidaVehiculoParqueado(Parqueadero parqueadero);

	void ingresarVehiculoParqueadero(Parqueadero parqueadero);
}

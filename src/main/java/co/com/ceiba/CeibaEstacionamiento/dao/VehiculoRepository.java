package co.com.ceiba.CeibaEstacionamiento.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import co.com.ceiba.CeibaEstacionamiento.model.Vehiculo;

public interface VehiculoRepository extends  JpaRepository<Vehiculo, Long>{

	@SuppressWarnings("unchecked")
	Vehiculo save(Vehiculo vehiculo);
	
}

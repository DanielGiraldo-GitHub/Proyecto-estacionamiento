package co.com.ceiba.CeibaEstacionamiento.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import co.com.ceiba.CeibaEstacionamiento.model.Parqueadero;


public interface ParqueaderoRepository extends  JpaRepository<Parqueadero, Long> {

}

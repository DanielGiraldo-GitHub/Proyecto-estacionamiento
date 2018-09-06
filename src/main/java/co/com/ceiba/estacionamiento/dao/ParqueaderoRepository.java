package co.com.ceiba.estacionamiento.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import co.com.ceiba.estacionamiento.model.Parqueadero;


public interface ParqueaderoRepository extends  JpaRepository<Parqueadero, Long> {

}

package co.com.ceiba.CeibaEstacionamiento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ceiba.CeibaEstacionamiento.dao.VehiculoRepository;
import co.com.ceiba.CeibaEstacionamiento.model.Vehiculo;

@Service
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	protected VehiculoRepository vehiculoRepository;

	@Override
	public Vehiculo save(Vehiculo vehiculo) {
		return this.vehiculoRepository.save(vehiculo);
	}

}

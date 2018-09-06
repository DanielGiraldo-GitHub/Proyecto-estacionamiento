package co.com.ceiba.estacionamiento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.dao.VehiculoRepository;
import co.com.ceiba.estacionamiento.model.Vehiculo;
import co.com.ceiba.estacionamiento.util.RestResponse;

@Service
public class VehiculoServiceImpl implements VehiculoService {

	@Autowired
	protected VehiculoRepository vehiculoRepository;

	@Override
	public RestResponse save(Vehiculo vehiculo) {
		return vehiculoRepository.guardarVehiculo(vehiculo);
	}

	@Override
	public Object buscarVehiculo(String  placa) {
		return vehiculoRepository.buscarVehiculo(placa);
	}

	

}

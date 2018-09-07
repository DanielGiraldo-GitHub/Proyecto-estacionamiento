package co.com.ceiba.estacionamiento.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import co.com.ceiba.estacionamiento.dao.ParqueaderoRepository;
@Service
public class ParqueaderoServiceImpl implements ParqueaderoService {
    
	@Autowired 
	protected ParqueaderoRepository parqueaderoRepository;

	@Override
	public int[] consultarDisponibilidad() {
		
		int [] cuposDisponibles = new int[2];
		cuposDisponibles [0] = parqueaderoRepository.contarCarrosParqueados();
		cuposDisponibles [1] = parqueaderoRepository.contarMotosParqueados();
        return cuposDisponibles;
	}
	
}

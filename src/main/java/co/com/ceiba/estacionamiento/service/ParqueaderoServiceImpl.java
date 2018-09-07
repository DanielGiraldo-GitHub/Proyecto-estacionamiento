package co.com.ceiba.estacionamiento.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import co.com.ceiba.estacionamiento.dao.ParqueaderoRepository;
import dominio.excepcion.ParqueaderoException;
@Service
public class ParqueaderoServiceImpl implements ParqueaderoService {
    
	@Autowired 
	protected ParqueaderoRepository parqueaderoRepository;

	public static final String PARQUEADERO_SIN_CUPO_DE_CARRO = "El parqueadero no tiene cupos disponibles para carros";
	public static final String PARQUEADERO_SIN_CUPO_DE_MOTO = "El parqueadero no tiene cupos disponibles para moto";
	
	@Override
	public int[] consultarDisponibilidad() {
		
		int [] cuposDisponibles = new int[2];
		cuposDisponibles [0] = parqueaderoRepository.contarCarrosParqueados();
		cuposDisponibles [1] = parqueaderoRepository.contarMotosParqueados();
       
		for (int i = 0; i < cuposDisponibles.length; i++) {
		   
   		if(i== 0 && cuposDisponibles [i] > 20 )
   			throw new  ParqueaderoException(PARQUEADERO_SIN_CUPO_DE_CARRO);
   		if(i== 1 && cuposDisponibles [1] > 10)
   			throw new  ParqueaderoException(PARQUEADERO_SIN_CUPO_DE_MOTO);
       }
		return cuposDisponibles;
	}
	
}

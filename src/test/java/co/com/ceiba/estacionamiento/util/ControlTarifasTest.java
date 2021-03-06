package co.com.ceiba.estacionamiento.util;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import co.com.ceiba.estacionamiento.model.TiempoPermanencia;

public class ControlTarifasTest {
	
	static final double PRECIO_HORA_MOTOS = 500;
	static final double PRECIO_DIA_MOTOS = 4000;
	static final double COBRO_ADICIONAL_MOTOS = 2000;
	static final double PRECIO_HORA_CARROS = 1000;
	static final double PRECIO_DIA_CARROS = 8000;
	
	@Test
	public void calcularPrecioCarroTest() {

		ControlTarifas control = new ControlTarifas();	
		TiempoPermanencia tiempo = new TiempoPermanencia();
		tiempo.setDias(0); 
		tiempo.setHoras(6); 
        double total = (6 *  PRECIO_HORA_CARROS); 
		assertEquals((Double)total, (Double)control.calcularPrecioCarro(tiempo) );
	
	}
	
	@Test
	public void calcularPrecioMotoConCilindrajeSuperadoTest() {

		ControlTarifas control = new ControlTarifas();	
		int cilindraje = 700;
		TiempoPermanencia tiempo = new TiempoPermanencia();
		tiempo.setDias(0); 
		tiempo.setHoras(10); 
        double total = ((0 + 1 ) *  PRECIO_DIA_MOTOS) + COBRO_ADICIONAL_MOTOS; 
			
		Assert.assertEquals((Double)total, (Double)control.calcularPrecioMoto(cilindraje, tiempo));
	}
	
	@Test
	public void calcularPrecioMotoSinCilindrajeSuperadoTest() {

		ControlTarifas control = new ControlTarifas();	
		int cilindraje = 400;
		TiempoPermanencia tiempo = new TiempoPermanencia();
		tiempo.setDias(0); 
		tiempo.setHoras(10); 
        double total = ((1) *  PRECIO_DIA_MOTOS) ; 
			
		Assert.assertEquals((Double)total, (Double)control.calcularPrecioMoto(cilindraje, tiempo));
	}
}

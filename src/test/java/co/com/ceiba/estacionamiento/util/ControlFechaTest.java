package co.com.ceiba.estacionamiento.util;
import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

import co.com.ceiba.estacionamiento.model.TiempoPermanencia;
import dominio.excepcion.ParqueaderoException;

public class ControlFechaTest {

	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";
	
	@SuppressWarnings("deprecation")
	@Test
	public void velidarDiaLunesTest() {

		Date fecha = new Date();
		fecha.getTime();
		fecha.setDate(18);
		ControlFecha control = new ControlFecha();
		try {
			control.velidarDia();
		} catch (ParqueaderoException e) {
		Assert.assertEquals(RESTRICCION_DE_PLACA, e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void fechaAcutalSistema(){

		Date fecha = new Date();
		fecha.getTime();
		ControlFecha control = new ControlFecha();

		Calendar fechaActual = control.fechaAcutalSistema();
		Assert.assertEquals(fecha.getDay(), fechaActual.getTime().getDay());
	}

	@Test
	public void calcularTiempoTest() {

		ControlFecha control = new ControlFecha();
		Date fecha = new Date();
		fecha.getTime();
		TiempoPermanencia tiempo = new TiempoPermanencia();
		tiempo.setDias(0); 
		tiempo.setHoras(0); 
		Assert.assertEquals(tiempo.getDias(), control.calcularTiempo(fecha).getDias());
	}

}

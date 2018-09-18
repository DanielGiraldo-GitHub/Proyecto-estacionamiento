package co.com.ceiba.estacionamiento.util;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

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
	public void fechaAcutalSistema() throws ParseException {

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
		int[] tiempo = new int[2];
		tiempo[0] = 0;
		tiempo[1] = 0;
		Assert.assertArrayEquals(tiempo, control.calcularTiempo(fecha));
	}

}

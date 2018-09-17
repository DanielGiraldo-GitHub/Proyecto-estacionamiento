package co.com.ceiba.estacionamiento.util;

import static org.junit.Assert.assertFalse;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class ControlFechaTest {

	@SuppressWarnings("deprecation")
	@Test
	public void velidarDiaLunesTest() {

		Date fecha = new Date();
		fecha.getTime();
		fecha.setDate(10);
		ControlFecha control = new ControlFecha();

		try {
			assertFalse(control.velidarDia());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void fechaAcutalSistema() throws ParseException {

		Date fecha = new Date();
		fecha.getTime();
		ControlFecha control = new ControlFecha();

		try {
			Calendar fechaActual = control.fechaAcutalSistema();
			Assert.assertEquals(fecha.getDay(), fechaActual.getTime().getDay());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void calcularTiempoTest(){

		ControlFecha control = new ControlFecha();
		Date fecha = new Date();
		fecha.getTime();
		int[] tiempo= new int[2];
		tiempo[0] = 0 ;
		tiempo[1] = 0;
		
		try {
			Assert.assertArrayEquals(tiempo,control.calcularTiempo(fecha));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

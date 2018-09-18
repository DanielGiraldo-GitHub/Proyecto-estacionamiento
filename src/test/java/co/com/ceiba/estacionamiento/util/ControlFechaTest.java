package co.com.ceiba.estacionamiento.util;
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
		Assert.assertTrue(control.velidarDia());
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

package co.com.ceiba.estacionamiento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ControlFecha {

	static final int MAXIMO_DIA_EN_SEGUNDOS = 86400;
	static final int MAXIMO_HORA_EN_SEGUNDOS = 3600;
	
	public ControlFecha() {
		super();
	}

	public boolean velidarDia() throws ParseException {

		Calendar fechaSistema = fechaAcutalSistema();
		boolean retorno = true;
		if (fechaSistema.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| fechaSistema.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
			return !retorno;
		return retorno;
	}

	public Calendar fechaAcutalSistema() throws ParseException {

		Date fecha = new Date();
	
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
		Calendar fechaActual = Calendar.getInstance();
		fechaActual.setTime(formateador.parse(formateador.format(fecha)));
		fechaActual.add(Calendar.DAY_OF_YEAR, 4);
		return fechaActual;
	}

	public int[] calcularTiempo(Date fechaIngreso) throws ParseException {

		// la posicion [0] equivale a los dias y la posicion [1] equivale a las
		// horas
		int[] tiempo = new int[2];
		Calendar fechaSalida = fechaAcutalSistema();
		int tiempoTotal = (int) ((fechaSalida.getTime().getTime() - fechaIngreso.getTime()) / 1000);

		if (tiempoTotal > MAXIMO_DIA_EN_SEGUNDOS) {
			tiempo[0] = (int) Math.floor(tiempoTotal / MAXIMO_DIA_EN_SEGUNDOS);
			tiempoTotal = tiempoTotal - (tiempo[0] * MAXIMO_DIA_EN_SEGUNDOS);
		}
		if (tiempoTotal > MAXIMO_HORA_EN_SEGUNDOS) {
			tiempo[1] = (int) Math.floor(tiempoTotal / MAXIMO_HORA_EN_SEGUNDOS);
		}
		return tiempo;
	}
}

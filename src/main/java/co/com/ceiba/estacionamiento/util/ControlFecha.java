package co.com.ceiba.estacionamiento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.ParqueaderoException;

public class ControlFecha {

	static final int MAXIMO_DIA_EN_SEGUNDOS = 86400;
	static final int MAXIMO_HORA_EN_SEGUNDOS = 3600;
	static final String ERROR_CONVERSION_FECHAS = "Error al realizar conversion de fechas";
	
	public ControlFecha() {
		super();
	}

	public boolean velidarDia() {

		Calendar fechaSistema = fechaAcutalSistema();
		boolean retorno = true;
		if (fechaSistema.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| fechaSistema.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
			return !retorno;
		return retorno;
	}

	public Calendar fechaAcutalSistema(){

		Date fecha = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
		Calendar fechaActual = Calendar.getInstance();
		try {
			fechaActual.setTime(formateador.parse(formateador.format(fecha)));
			fechaActual.add(Calendar.DAY_OF_YEAR,0);
		} catch (ParseException e) {
			throw new ParqueaderoException(ERROR_CONVERSION_FECHAS);
		}
		
		return fechaActual;
	}

	public int[] calcularTiempo(Date fechaIngreso) {

		// la posicion [0] equivale a los dias y la posicion [1] equivale a las
		// horas
		int[] tiempo = new int[2];
		Calendar fechaSalida;
		fechaSalida = fechaAcutalSistema();
		int tiempoTotal = (int) ((fechaSalida.getTime().getTime() - fechaIngreso.getTime()) / 1000);
		
		if (tiempoTotal > MAXIMO_DIA_EN_SEGUNDOS) {
			tiempo[0] = (int) Math.floor(computeMaximoDias(tiempoTotal));
			tiempoTotal = tiempoTotal - (tiempo[0] * MAXIMO_DIA_EN_SEGUNDOS);
		}
		if (tiempoTotal > MAXIMO_HORA_EN_SEGUNDOS) {
			tiempo[1] = (int) Math.floor(computeMaximoHoras(tiempoTotal));
		}
		return tiempo;
	}

	public int computeMaximoDias(int factor) {
		return factor / 86400;
	}

	public int computeMaximoHoras(int factor) {
		return factor / 3600;
	}
}

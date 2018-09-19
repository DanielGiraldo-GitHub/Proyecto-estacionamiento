package co.com.ceiba.estacionamiento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.com.ceiba.estacionamiento.model.TiempoPermanencia;
import dominio.excepcion.ParqueaderoException;

public class ControlFecha {

	static final int MAXIMO_DIA_EN_SEGUNDOS = 86400;
	static final int MAXIMO_HORA_EN_SEGUNDOS = 3600;
	static final String ERROR_CONVERSION_FECHAS = "Error al realizar conversion de fechas";
	static final String RESTRICCION_DE_PLACA = "El vehiculo no puede ser parqueado los dias domingo y lunes";
	
	public ControlFecha() {
		super();
	}

	public void velidarDia() {

		Calendar fechaSistema = fechaAcutalSistema();
		if (fechaSistema.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| fechaSistema.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
			throw new ParqueaderoException(RESTRICCION_DE_PLACA);
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

	public TiempoPermanencia calcularTiempo(Date fechaIngreso) {

		TiempoPermanencia tiempo = new TiempoPermanencia();
		Calendar fechaSalida;
		fechaSalida = fechaAcutalSistema();
		int tiempoTotal = (int) ((fechaSalida.getTime().getTime() - fechaIngreso.getTime()) / 1000);
		
		if (tiempoTotal > MAXIMO_DIA_EN_SEGUNDOS) {
			tiempo.setDias((int) Math.floor(computeMaximoDias(tiempoTotal)));
			tiempoTotal = tiempoTotal - (tiempo.getDias() * MAXIMO_DIA_EN_SEGUNDOS);
		}
		if (tiempoTotal > MAXIMO_HORA_EN_SEGUNDOS) {
			tiempo.setHoras((int) Math.floor(computeMaximoHoras(tiempoTotal)));
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

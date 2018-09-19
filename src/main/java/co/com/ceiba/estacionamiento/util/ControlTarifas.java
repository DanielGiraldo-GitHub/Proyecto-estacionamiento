package co.com.ceiba.estacionamiento.util;
import co.com.ceiba.estacionamiento.model.TiempoPermanencia;

public class ControlTarifas {

	static final double PRECIO_HORA_MOTOS = 500;
	static final double PRECIO_DIA_MOTOS = 4000;
	static final double COBRO_ADICIONAL_MOTOS = 2000;
	static final double PRECIO_HORA_CARROS = 1000;
	static final double PRECIO_DIA_CARROS = 8000;
	static final double HORAS_PERMANECIA = 9;
	static final double MAXIMO_CILINDRAJE_MOTO = 500;

	public double calcularPrecioCarro( TiempoPermanencia tiempoPermanencia) {

		if (tiempoPermanencia.getHoras() > HORAS_PERMANECIA)
			return ((tiempoPermanencia.getDias() + 1) * PRECIO_DIA_CARROS);
		else
			return (tiempoPermanencia.getDias() * PRECIO_DIA_CARROS + tiempoPermanencia.getHoras() * PRECIO_HORA_CARROS);
	}

	public double calcularPrecioMoto(int cilindraje, TiempoPermanencia tiempoPermanencia) {

		double precio = 0;
		if (cilindraje > MAXIMO_CILINDRAJE_MOTO)
			precio += COBRO_ADICIONAL_MOTOS;

		 if (tiempoPermanencia.getHoras() > HORAS_PERMANECIA) {
			precio += ((tiempoPermanencia.getDias() + 1) * PRECIO_DIA_MOTOS);
			return precio;
		} else {
			precio += (tiempoPermanencia.getDias() * PRECIO_DIA_MOTOS + tiempoPermanencia.getHoras() * PRECIO_HORA_MOTOS);

		}
		return precio;
	}
}

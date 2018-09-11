package co.com.ceiba.estacionamiento.util;

public class ControlTarifas {

	static final double PRECIO_HORA_MOTOS = 500;
	static final double PRECIO_DIA_MOTOS = 4000;
	static final double COBRO_ADICIONAL_MOTOS = 2000;
	static final double PRECIO_HORA_CARROS = 1000;
	static final double PRECIO_DIA_CARROS = 8000;
	static final double HORAS_PERMANECIA = 9;
	static final double MAXIMO_CILINDRAJE_MOTO = 500;

	public double calcularPrecioCarro( int[] tiempoPermanencia) {

		int dias = tiempoPermanencia[0];
		int horas = tiempoPermanencia[1];

		if (horas > HORAS_PERMANECIA)
			return ((dias + 1) * PRECIO_DIA_CARROS);
		else
			return (dias * PRECIO_DIA_CARROS + horas * PRECIO_HORA_CARROS);
	}

	public double calcularPrecioMoto(int cilindraje, int[] tiempoPermanencia) {

		int dias = tiempoPermanencia[0];
		int horas = tiempoPermanencia[1];
		double precio = 0;

		if (cilindraje > MAXIMO_CILINDRAJE_MOTO)
			precio += COBRO_ADICIONAL_MOTOS;

		 if (horas > HORAS_PERMANECIA) {
			precio += ((dias + 1) * PRECIO_DIA_MOTOS);
			return precio;
		} else {
			precio += (dias * PRECIO_DIA_MOTOS + horas * PRECIO_HORA_MOTOS);

		}
		return precio;
	}
}

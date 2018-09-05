package co.com.ceiba.CeibaEstacionamiento.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculo")
@Access(AccessType.FIELD)
public class Vehiculo extends ParentEntity {

	private static final long serialVersionUID = 5029334849102944738L;
   
	@Column(name = "PLACA", nullable = false, length = 25)
	private String placa;
	
	@Column(name = "TIPO_VEHICULO", nullable = false, length = 1)
	private String tipoVehiculo;
	
	@Column(name = "CILINDRAJE", nullable = false)
	private int cilindraje;
	
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public int getCilindraje() {
		return cilindraje;
	}
	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}

	
}

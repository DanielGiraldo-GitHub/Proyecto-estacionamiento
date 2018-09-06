package co.com.ceiba.estacionamiento.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parqueadero")
@Access(AccessType.FIELD)
public class Parqueadero extends ParentEntity {

	private static final long serialVersionUID = -967609858153698764L;

	@Column(name = "FECHA_INGRESO", nullable = false)
	private Date fehcaIngreso;

	@Column(name = "FECHA_SALIDA", nullable = true)
	private Date fechaSalida;

	@Column(name = "PRECIO", nullable = true)
	private Double precio;

	@Column(name = "ID_VEHICULO", nullable = false)
	private int idVehiculo;

	@Column(name = "ESTADO", nullable = false)
	private boolean estado;

	
	public Date getFehcaIngreso() {
		return fehcaIngreso;
	}

	public void setFehcaIngreso(Date fehcaIngreso) {
		this.fehcaIngreso = fehcaIngreso;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}

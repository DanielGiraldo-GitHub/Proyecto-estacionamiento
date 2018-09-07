package co.com.ceiba.estacionamiento.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parqueadero")
@Access(AccessType.FIELD)
public class Parqueadero {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private int id;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

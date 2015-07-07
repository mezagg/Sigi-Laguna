package mx.gob.segob.nsjp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ConclusionOrdenAprension")
public class ConclusionOrdenAprension {

	private Long conclusionOrdenAprensionId;
	private NumeroExpediente numeroExpediente;
	private Long corporacionPoliciaca;
	private String noFichaPago;
	private Date fechaPago;
	private Double monto;
	private Boolean esConReparacion;

	public ConclusionOrdenAprension() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ConclusionOrdenAprension_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getConclusionOrdenAprensionId() {
		return conclusionOrdenAprensionId;
	}

	public void setConclusionOrdenAprensionId(Long conclusionOrdenAprensionId) {
		this.conclusionOrdenAprensionId = conclusionOrdenAprensionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NumeroExpediente_id", nullable = false)
	public NumeroExpediente getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(NumeroExpediente numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	@Column(name = "CorporacionPoliciaca_val", nullable = true, precision = 18, scale = 0)
	public Long getCorporacionPoliciaca() {
		return corporacionPoliciaca;
	}

	public void setCorporacionPoliciaca(Long corporacionPoliciaca) {
		this.corporacionPoliciaca = corporacionPoliciaca;
	}

	@Column(name = "cNoFichaPago", nullable = true, length = 50)
	public String getNoFichaPago() {
		return noFichaPago;
	}

	public void setNoFichaPago(String noFichaPago) {
		this.noFichaPago = noFichaPago;
	}

	@Column(name = "dFechaPago", length = 23, nullable= true)
	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	@Column(name = "dcMonto", precision = 5, nullable= true)
	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double dcMonto) {
		this.monto = dcMonto;
	}

	@Column(name = "bConReparacion", precision = 1, scale = 0, nullable= true)
	public Boolean getEsConReparacion() {
		return esConReparacion;
	}

	public void setEsConReparacion(Boolean esConReparacion) {
		this.esConReparacion = esConReparacion;
	}

}
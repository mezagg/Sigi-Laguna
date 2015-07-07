package mx.gob.segob.nsjp.dto.conclusion;

import java.util.Date;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

public class ConclusionOrdenAprensionDTO {

	private Long conclusionOrdenAprensionId;
	private ExpedienteDTO numeroExpediente;
	private Long corporacionPoliciaca;
	private String noFichaPago;
	private Date fechaPago;
	private Double monto;
	private Boolean esConReparacion;

	public Long getConclusionOrdenAprensionId() {
		return conclusionOrdenAprensionId;
	}

	public void setConclusionOrdenAprensionId(Long conclusionOrdenAprensionId) {
		this.conclusionOrdenAprensionId = conclusionOrdenAprensionId;
	}

	public ExpedienteDTO getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(ExpedienteDTO numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public Long getCorporacionPoliciaca() {
		return corporacionPoliciaca;
	}

	public void setCorporacionPoliciaca(Long corporacionPoliciaca) {
		this.corporacionPoliciaca = corporacionPoliciaca;
	}

	public String getNoFichaPago() {
		return noFichaPago;
	}

	public void setNoFichaPago(String noFichaPago) {
		this.noFichaPago = noFichaPago;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Boolean getEsConReparacion() {
		return esConReparacion;
	}

	public void setEsConReparacion(Boolean esConReparacion) {
		this.esConReparacion = esConReparacion;
	}

}

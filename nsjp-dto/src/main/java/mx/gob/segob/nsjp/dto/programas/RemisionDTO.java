package mx.gob.segob.nsjp.dto.programas;

import java.util.Date;

import mx.gob.segob.nsjp.dto.base.GenericDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;

public class RemisionDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -535042547836630927L;
	// Fields

	private Long remisionId;
	private CatTipoRemisionDTO catTipoRemisionDTO;
	private SentenciaDTO sentencia;
	private Float imontoDanioReparado;
	private Long idiasAcreditados;
	private Date fechaAutorizacion;
	private Boolean cumplida;

	public Long getRemisionId() {
		return this.remisionId;
	}

	public void setRemisionId(Long remisionId) {
		this.remisionId = remisionId;
	}

	public CatTipoRemisionDTO getCatTipoRemisionDTO() {
		return this.catTipoRemisionDTO;
	}

	public void setCatTipoRemisionDTO(CatTipoRemisionDTO catTipoRemisionDTO) {
		this.catTipoRemisionDTO = catTipoRemisionDTO;
	}

	public SentenciaDTO getSentencia() {
		return this.sentencia;
	}

	public void setSentencia(SentenciaDTO sentencia) {
		this.sentencia = sentencia;
	}

	public Float getImontoDanioReparado() {
		return this.imontoDanioReparado;
	}

	public void setImontoDanioReparado(Float imontoDanioReparado) {
		this.imontoDanioReparado = imontoDanioReparado;
	}

	public Long getIdiasAcreditados() {
		return this.idiasAcreditados;
	}

	public void setIdiasAcreditados(Long idiasAcreditados) {
		this.idiasAcreditados = idiasAcreditados;
	}

	/**
	 * M&eacute;todo de acceso al campo fechaAutorizacion.
	 * @return El valor del campo fechaAutorizacion
	 */
	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	/**
	 * Asigna el valor al campo fechaAutorizacion.
	 * @param fechaAutorizacion el valor fechaAutorizacion a asignar
	 */
	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	/**
	 * Método de acceso al campo cumplida.
	 * @return El valor del campo cumplida
	 */
	public Boolean getCumplida() {
		return cumplida;
	}

	/**
	 * Asigna el valor al campo cumplida.
	 * @param cumplida el valor cumplida a asignar
	 */
	public void setCumplida(Boolean cumplida) {
		this.cumplida = cumplida;
	}

}
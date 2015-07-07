package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SentenciaId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class SentenciaId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6256925002658210415L;
	private Long numeroExpedienteId;
	private Long involucradoId;

	// Constructors

	/** default constructor */
	public SentenciaId() {
	}

	/** full constructor */
	public SentenciaId(Long numeroExpedienteId, Long involucradoId) {
		this.numeroExpedienteId = numeroExpedienteId;
		this.involucradoId = involucradoId;
	}

	// Property accessors

	@Column(name = "NumeroExpediente_id", nullable = false, precision = 18, scale = 0)
	public Long getNumeroExpedienteId() {
		return this.numeroExpedienteId;
	}

	public void setNumeroExpedienteId(Long numeroExpedienteId) {
		this.numeroExpedienteId = numeroExpedienteId;
	}

	@Column(name = "Involucrado_id", nullable = false, precision = 18, scale = 0)
	public Long getInvolucradoId() {
		return this.involucradoId;
	}

	public void setInvolucradoId(Long involucradoId) {
		this.involucradoId = involucradoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SentenciaId))
			return false;
		SentenciaId castOther = (SentenciaId) other;

		return ((this.getNumeroExpedienteId() == castOther
				.getNumeroExpedienteId()) || (this.getNumeroExpedienteId() != null
				&& castOther.getNumeroExpedienteId() != null && this
				.getNumeroExpedienteId().equals(
						castOther.getNumeroExpedienteId())))
				&& ((this.getInvolucradoId() == castOther.getInvolucradoId()) || (this
						.getInvolucradoId() != null
						&& castOther.getInvolucradoId() != null && this
						.getInvolucradoId()
						.equals(castOther.getInvolucradoId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getNumeroExpedienteId() == null ? 0 : this
						.getNumeroExpedienteId().hashCode());
		result = 37
				* result
				+ (getInvolucradoId() == null ? 0 : this.getInvolucradoId()
						.hashCode());
		return result;
	}

}
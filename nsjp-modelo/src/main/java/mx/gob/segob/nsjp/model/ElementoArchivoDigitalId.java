package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MedidaAdjuntosId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class ElementoArchivoDigitalId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6922512993539333539L;

	 private Long elementoId;
	 private Long archivoDigitalId;

	// Constructors

	/** default constructor */
	public ElementoArchivoDigitalId() {
	}

	/** full constructor */
	public ElementoArchivoDigitalId(Long elementoId, Long archivoDigitalId) {
		this.elementoId = elementoId;
		this.archivoDigitalId = archivoDigitalId;
	}

	// Property accessors

	@Column(name = "Elemento_id", nullable = false, precision = 18, scale = 0)
	public Long getElementoId() {
		return this.elementoId;
	}

	public void setElementoId(Long elementoId) {
		this.elementoId = elementoId;
	}

	@Column(name = "ArchivoDigital_id", nullable = false, precision = 18, scale = 0)
	public Long getArchivoDigitalId() {
		return this.archivoDigitalId;
	}

	public void setArchivoDigitalId(Long archivoDigitalId) {
		this.archivoDigitalId = archivoDigitalId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ElementoArchivoDigitalId))
			return false;
		ElementoArchivoDigitalId castOther = (ElementoArchivoDigitalId) other;

		return ((this.getElementoId() == castOther.getElementoId()) || (this
				.getElementoId() != null
				&& castOther.getElementoId() != null && this.getElementoId()
				.equals(castOther.getElementoId())))
				&& ((this.getArchivoDigitalId() == castOther
						.getArchivoDigitalId()) || (this.getArchivoDigitalId() != null
						&& castOther.getArchivoDigitalId() != null && this
						.getArchivoDigitalId().equals(
								castOther.getArchivoDigitalId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getElementoId() == null ? 0 : this.getElementoId().hashCode());
		result = 37
				* result
				+ (getArchivoDigitalId() == null ? 0 : this
						.getArchivoDigitalId().hashCode());
		return result;
	}

}
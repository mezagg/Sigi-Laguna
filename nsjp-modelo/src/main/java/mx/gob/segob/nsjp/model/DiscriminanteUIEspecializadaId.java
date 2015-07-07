package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UsuarioRolId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class DiscriminanteUIEspecializadaId implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -294767051262990935L;
	private Long catDiscriminanteId;
	private Long catUIEId;

	// Constructors

	/** default constructor */
	public DiscriminanteUIEspecializadaId() {
	}

	/** full constructor */
	public DiscriminanteUIEspecializadaId(Long catDiscriminanteId, Long catUIEId) {
		this.catDiscriminanteId = catDiscriminanteId;
		this.catUIEId = catUIEId;
	}

	// Property accessors

	@Column(name = "catDiscriminante_id", nullable = false, precision = 18, scale = 0)
	public Long getCatDiscriminanteId() {
		return catDiscriminanteId;
	}

	/**
	 * @param catDiscriminanteId the catDiscriminanteId to set
	 */
	public void setCatDiscriminanteId(Long catDiscriminanteId) {
		this.catDiscriminanteId = catDiscriminanteId;
	}

	@Column(name = "catUIE_id", nullable = false, precision = 18, scale = 0)
	public Long getCatUIEId() {
		return catUIEId;
	}

	/**
	 * @param catUIEId the catUIEId to set
	 */
	public void setCatUIEId(Long catUIEId) {
		this.catUIEId = catUIEId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DiscriminanteUIEspecializadaId))
			return false;
		DiscriminanteUIEspecializadaId castOther = (DiscriminanteUIEspecializadaId) other;

		return ((this.getCatDiscriminanteId() == castOther.getCatDiscriminanteId()) || (this
				.getCatDiscriminanteId() != null
				&& castOther.getCatDiscriminanteId() != null && this.getCatDiscriminanteId()
				.equals(castOther.getCatDiscriminanteId())))
				&& ((this.getCatUIEId() == castOther.getCatUIEId()) || (this
						.getCatUIEId() != null
						&& castOther.getCatUIEId() != null && this.getCatUIEId()
						.equals(castOther.getCatUIEId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCatDiscriminanteId() == null ? 0 : this.getCatDiscriminanteId().hashCode());
		result = 37 * result
				+ (getCatUIEId() == null ? 0 : this.getCatUIEId().hashCode());
		return result;
	}

}
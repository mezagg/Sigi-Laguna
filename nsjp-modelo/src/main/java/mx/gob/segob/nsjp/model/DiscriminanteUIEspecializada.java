package mx.gob.segob.nsjp.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UsuarioRol entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DiscriminanteUIEspecializada")
public class DiscriminanteUIEspecializada implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4771959900061282220L;
	private DiscriminanteUIEspecializadaId id;
	private CatDiscriminante catDiscriminante;
	private CatUIEspecializada catUIEspecializada;

	// Constructors

	/** default constructor */
	public DiscriminanteUIEspecializada() {
	}


	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "catDiscriminanteId", column = @Column(name = "catDiscriminante_id", nullable = false, precision = 18, scale = 0)),
			@AttributeOverride(name = "catUIEId", column = @Column(name = "catUIE_id", nullable = false, precision = 18, scale = 0)) })
	public DiscriminanteUIEspecializadaId getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(DiscriminanteUIEspecializadaId id) {
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catDiscriminante_id", nullable = false, insertable = false, updatable = false)
	public CatDiscriminante getCatDiscriminante() {
		return catDiscriminante;
	}


	/**
	 * @param catDiscriminante the catDiscriminante to set
	 */
	public void setCatDiscriminante(CatDiscriminante catDiscriminante) {
		this.catDiscriminante = catDiscriminante;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catUIE_id", nullable = false, insertable = false, updatable = false)
	public CatUIEspecializada getCatUIEspecializada() {
		return catUIEspecializada;
	}


	/**
	 * @param catUIEspecializada the catUIEspecializada to set
	 */
	public void setCatUIEspecializada(CatUIEspecializada catUIEspecializada) {
		this.catUIEspecializada = catUIEspecializada;
	}

}
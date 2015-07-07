package mx.gob.segob.nsjp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ConfTipoActividadOrigenDestino entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ConfTipoActividadOrigenDestino")
public class ConfTipoActividadOrigenDestino implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5118965597892595761L;
	private Long confTipoActividadOrigenDestinoId;
	private Valor tipoActividadOrigenVal;
	private Valor tipoActividadDestinoVal;

	// Constructors

	/** default constructor */
	public ConfTipoActividadOrigenDestino() {
	}

	/** full constructor */
	public ConfTipoActividadOrigenDestino(
			Long confTipoActividadOrigenDestinoId,
			Valor tipoActividadOrigenVal,
			Valor tipoActividadDestinoVal) {
		this.confTipoActividadOrigenDestinoId = confTipoActividadOrigenDestinoId;
		this.tipoActividadOrigenVal = tipoActividadOrigenVal;
		this.tipoActividadDestinoVal = tipoActividadDestinoVal;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ConfTipoActividadOrigenDestino_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getConfTipoActividadOrigenDestinoId() {
		return this.confTipoActividadOrigenDestinoId;
	}

	public void setConfTipoActividadOrigenDestinoId(
			Long confTipoActividadOrigenDestinoId) {
		this.confTipoActividadOrigenDestinoId = confTipoActividadOrigenDestinoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoActividadOrigen_val", nullable = false)
	public Valor getTipoActividadOrigenVal() {
		return this.tipoActividadOrigenVal;
	}

	public void setTipoActividadOrigenVal(
			Valor tipoActividadOrigenVal) {
		this.tipoActividadOrigenVal = tipoActividadOrigenVal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoActividadDestino_val", nullable = false)
	public Valor getTipoActividadDestinoVal() {
		return this.tipoActividadDestinoVal;
	}

	public void setTipoActividadDestinoVal(
			Valor tipoActividadDestinoVal) {
		this.tipoActividadDestinoVal = tipoActividadDestinoVal;
	}

}
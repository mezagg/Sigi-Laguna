package mx.gob.segob.nsjp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ConclusionHecho")
@Inheritance(strategy = InheritanceType.JOINED)
public class ConclusionHecho implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -600879400080882356L;
	
	
	private Hecho hecho;
	private Date fechaConclusion;
	private Valor tipoConclusion;
	private Valor tipoSubConclusion;
	
	@Id
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Hecho_id", nullable = false)
	public Hecho getHecho() {
		return hecho;
	}
	public void setHecho(Hecho hecho) {
		this.hecho = hecho;
	}
	
	@Column(name = "dFechaConclusion", length = 23)
	public Date getFechaConclusion() {
		return fechaConclusion;
	}
	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoConclusion_val")
	public Valor getTipoConclusion() {
		return tipoConclusion;
	}
	public void setTipoConclusion(Valor tipoConclusion) {
		this.tipoConclusion = tipoConclusion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoSubConclusion_val")
	public Valor getTipoSubConclusion() {
		return tipoSubConclusion;
	}
	public void setTipoSubConclusion(Valor tipoSubConclusion) {
		this.tipoSubConclusion = tipoSubConclusion;
	}
	
	
}

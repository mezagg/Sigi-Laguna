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
@Table(name = "ConclusionNumeroExpediente")
@Inheritance(strategy = InheritanceType.JOINED)
public class ConclusionNumeroExpediente implements Serializable {

	
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NumeroExpediente numeroExpediente;
	private Date fechaConclusion;
	private Valor tipoConclusion;
	private Long tipoSubConclusion;
	
	public ConclusionNumeroExpediente() {
		super();
		
	}
	
	public ConclusionNumeroExpediente(NumeroExpediente numeroExpediente,
			Date fechaConclusion, Valor tipoConclusion, Long tipoSubConclusion) {
		super();
		this.numeroExpediente = numeroExpediente;
		this.fechaConclusion = fechaConclusion;
		this.tipoConclusion = tipoConclusion;
		this.tipoSubConclusion = tipoSubConclusion;
	}
	@Id
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NumeroExpediente_id", nullable = false)
	public NumeroExpediente getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(NumeroExpediente numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
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
	
	@Column(name = "TipoSubConclusion_val")
	public Long getTipoSubConclusion() {
		return tipoSubConclusion;
	}
	public void setTipoSubConclusion(Long tipoSubConclusion) {
		this.tipoSubConclusion = tipoSubConclusion;
	}
	
	
}

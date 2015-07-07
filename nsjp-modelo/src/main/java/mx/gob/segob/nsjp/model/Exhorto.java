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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@SuppressWarnings("serial")
@Entity
@Table(name = "Exhortos")
public class Exhorto implements java.io.Serializable {

	private Long exhortoId;
	private String expediente;
	private Funcionario funcionario;
	private Valor valorEstatus;
	private Documento documento;
	private Long folio;
	private	Date fechaVencida;
	private	Date fechaDiligencia;
	private	Date fechaEnvio;
	private String diligencia;
	private Boolean esGuardadoParcial;
	
	public Exhorto() {
	}




	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Exhorto_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getExhortoId() {
		return exhortoId;
	}

	public void setExhortoId(Long exhortoId) {
		this.exhortoId = exhortoId;
	}

	@Column(name = "Expediente_id", nullable = false)
	public String getExpediente() {
		return expediente;
	}



	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iClaveFuncionario", nullable = false)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Estatus", nullable = false)
	public Valor getValorEstatus() {
		return valorEstatus;
	}

	public void setValorEstatus(Valor valorEstatus) {
		this.valorEstatus = valorEstatus;
	}

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Documento_id", nullable = true)
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	@Column(name = "Folio", nullable = false, precision = 18, scale = 0)
	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
	}

	@Column(name = "dFechaVencida", length = 23)
	public Date getFechaVencida() {
		return fechaVencida;
	}

	public void setFechaVencida(Date fechaVencida) {
		this.fechaVencida = fechaVencida;
	}

	@Column(name = "dFechaDiligencia", length = 23)
	public Date getFechaDiligencia() {
		return fechaDiligencia;
	}

	public void setFechaDiligencia(Date fechaDiligencia) {
		this.fechaDiligencia = fechaDiligencia;
	}

	@Column(name = "dFechaEnvio", length = 23)
	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	@Column(name="Diligencia", length=300, nullable=true)
	public String getDiligencia() {
		return diligencia;
	}

	public void setDiligencia(String diligencia) {
		this.diligencia = diligencia;
	}
	
	
	@Transient
    public Boolean getEsGuardadoParcial() {
        return esGuardadoParcial;
    }
    
    public void setEsGuardadoParcial(Boolean esGuardadoParcial) {
        if (esGuardadoParcial == null)
            this.esGuardadoParcial = true;
        else
            this.esGuardadoParcial = esGuardadoParcial;
    }
	
	
}

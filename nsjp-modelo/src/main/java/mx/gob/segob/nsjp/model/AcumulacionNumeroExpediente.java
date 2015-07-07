package mx.gob.segob.nsjp.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UsuarioRol entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AcumulacionNumeroExpediente")
public class AcumulacionNumeroExpediente implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4771959900061282221L;
	private Long acumulacionId;
	private NumeroExpediente numeroExpedienteId;
	private NumeroExpediente numeroExpedienteAcumuladoId;
	private Date fecha;
	private Long claveFuncionario;
	 

	// Constructors

	/** default constructor */
	public AcumulacionNumeroExpediente() {
	}


	

	/**
	 * @param acumulacionId
	 * @param numeroExpedienteId
	 * @param numeroExpedienteAcumuladoId
	 * @param fecha
	 * @param claveFuncionario
	 */
	public AcumulacionNumeroExpediente(Long acumulacionId,
			NumeroExpediente numeroExpedienteId,
			NumeroExpediente numeroExpedienteAcumuladoId, Date fecha,
			Long claveFuncionario) {
		super();
		this.acumulacionId = acumulacionId;
		this.numeroExpedienteId = numeroExpedienteId;
		this.numeroExpedienteAcumuladoId = numeroExpedienteAcumuladoId;
		this.fecha = fecha;
		this.claveFuncionario = claveFuncionario;
	}

	
	/**
	 * @return the acumulacionId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Acumulacion_id", unique = true, nullable = false, precision = 18, scale = 0)    	
	public Long getAcumulacionId() {
		return acumulacionId;
	}

	/**
	 * @param acumulacionId the acumulacionId to set
	 */
	public void setAcumulacionId(Long acumulacionId) {
		this.acumulacionId = acumulacionId;
	}

	/**
	 * @return the numeroExpedienteId
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NumeroExpediente_id", nullable = false)
	public NumeroExpediente getNumeroExpedienteId() {
		return numeroExpedienteId;
	}

	/**
	 * @param numeroExpedienteId the numeroExpedienteId to set
	 */
	public void setNumeroExpedienteId(NumeroExpediente numeroExpedienteId) {
		this.numeroExpedienteId = numeroExpedienteId;
	}

	/**
	 * @return the numeroExpedienteAcumuladoId
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NumeroExpediente_Acumulado_id", nullable = false)
	public NumeroExpediente getNumeroExpedienteAcumuladoId() {
		return numeroExpedienteAcumuladoId;
	}

	/**
	 * @param numeroExpedienteAcumuladoId the numeroExpedienteAcumuladoId to set
	 */
	public void setNumeroExpedienteAcumuladoId(
			NumeroExpediente numeroExpedienteAcumuladoId) {
		this.numeroExpedienteAcumuladoId = numeroExpedienteAcumuladoId;
	}
	 
	/**
	 * @return the fecha
	 */
	@Column(name = "dFecha", nullable = false, length = 23)
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the claveFuncionario
	 */
	@Column(name = "claveFuncionario", nullable = false, length = 10)
	public Long getClaveFuncionario() {
		return claveFuncionario;
	}

	/**
	 * @param claveFuncionario the claveFuncionario to set
	 */
	public void setClaveFuncionario(Long claveFuncionario) {
		this.claveFuncionario = claveFuncionario;
	}
}
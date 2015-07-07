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
import javax.persistence.Table;

/**
 * PermisoExpediente entity. @author MyEclipse Persistence Tools <br>
 * Entity para asignar pemisos a un funcioario para el permiso de lectura o
 * escritura del expediente.
 */
@Entity
@Table(name = "BitacoraEstatusNumExpediente")
public class BitacoraEstatusNumExpediente implements java.io.Serializable {

	private static final long serialVersionUID = 3946591018979114695L;

	private Long bitacoraEstatusNumExpedienteId;
	
	private Date fechaCreacion;
	private JerarquiaOrganizacional jerarquiaOrganizacional;
	private NumeroExpediente numeroExpediente;
    private Funcionario funcionario;
    private Valor estatus;    
    private Valor tipoActividad;
    private Valor tipoDocumento;
    private Long tipoMovimiento;

    // Constructors

    /** default constructor */
    public BitacoraEstatusNumExpediente() {
    }

    /**
	 * @return the bitacoraEstatusNumExpedienteId
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bitacoraEstatusNumExpediente_Id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getBitacoraEstatusNumExpedienteId() {
		return bitacoraEstatusNumExpedienteId;
	}


	/**
	 * @param bitacoraEstatusNumExpedienteId the bitacoraEstatusNumExpedienteId to set
	 */
	public void setBitacoraEstatusNumExpedienteId(
			Long bitacoraEstatusNumExpedienteId) {
		this.bitacoraEstatusNumExpedienteId = bitacoraEstatusNumExpedienteId;
	}

	/**
	 * @return the fechaCreacion
	 */
    @Column(name = "dFechaCreacion", nullable = false, length = 23)
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the estatus
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Estatus_val")
	public Valor getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(Valor estatus) {
		this.estatus = estatus;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iClaveFuncionario", nullable = false)
    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NumeroExpediente_id", nullable = false)
    public NumeroExpediente getNumeroExpediente() {
        return this.numeroExpediente;
    }

    public void setNumeroExpediente(NumeroExpediente numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JerarquiaOrganizacional_id", nullable = false)
    public JerarquiaOrganizacional getJerarquiaOrganizacional() {
        return this.jerarquiaOrganizacional;
    }

    public void setJerarquiaOrganizacional(
            JerarquiaOrganizacional jerarquiaOrganizacional) {
        this.jerarquiaOrganizacional = jerarquiaOrganizacional;
    }

	/**
	 * @return the tipoActuacion
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoActividad_val")
	public Valor getTipoActividad() {
		return tipoActividad;
	}

	/**
	 * @param tipoActuacion the tipoActuacion to set
	 */
	public void setTipoActividad(Valor tipoActuacion) {
		this.tipoActividad = tipoActuacion;
	}

	/**
	 * @return the tipoDocumento
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoDocumento_val")
	public Valor getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(Valor tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the tipoMovimiento
	 */
    @Column(name = "TipoMovimiento_id", precision = 18, scale = 0)
	public Long getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento the tipoMovimiento to set
	 */
	public void setTipoMovimiento(Long tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
    
    

}
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
@Table(name = "BitacoraPermisoExpediente")
public class BitacoraPermisoExpediente implements java.io.Serializable {

    // Fields

    /**
	 * 
	 */
	private static final long serialVersionUID = -4096224374843194395L;
    private Funcionario funcionario;
    private Long bitacoraPermisoExpediente;
    private NumeroExpediente numeroExpediente;
    private JerarquiaOrganizacional jerarquiaOrganizacional;
    private Date fechaLimite;
    private Date fechaMovimiento;
    private Boolean esEscritura;
    private Boolean esActivo;

    // Constructors

    /** default constructor */
    public BitacoraPermisoExpediente() {
    }
    

    /**
	 * @return the bitacoraPermisoExpediente
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bitacoraPermisoExpediente_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getBitacoraPermisoExpediente() {
		return bitacoraPermisoExpediente;
	}

	/**
	 * @param bitacoraPermisoExpediente the bitacoraPermisoExpediente to set
	 */
	public void setBitacoraPermisoExpediente(Long bitacoraPermisoExpediente) {
		this.bitacoraPermisoExpediente = bitacoraPermisoExpediente;
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

    @Column(name = "dFechaLimite", nullable = true, length = 23)
    public Date getFechaLimite() {
        return this.fechaLimite;
    }

    public void setFechaLimite(Date dfechaLimite) {
        this.fechaLimite = dfechaLimite;
    }

    /**
     * Método de acceso al campo esEscritura.
     * 
     * @return El valor del campo esEscritura
     */
    @Column(name = "bEsEscritura", precision = 1, scale = 0)
    public Boolean getEsEscritura() {
        return esEscritura;
    }

    /**
     * Asigna el valor al campo esEscritura.
     * 
     * @param esEscritura
     *            el valor esEscritura a asignar
     */
    public void setEsEscritura(Boolean esEscritura) {
        this.esEscritura = esEscritura;
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
	 * @return the fechaMovimiento
	 */
    @Column(name = "dFechaMovimiento", nullable = false, length = 23)
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	
	/**
	 * @param fechaMovimiento the fechaMovimiento to set
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}


	/**
	 * @return the esActivo
	 */
    @Column(name = "bEsActivo", precision = 1, scale = 0)
	public Boolean getEsActivo() {
		return esActivo;
	}


	/**
	 * @param esActivo the esActivo to set
	 */
	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}
	
	
    
}
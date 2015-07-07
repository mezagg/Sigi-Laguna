package mx.gob.segob.nsjp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Clase que contiene las operaciones y los atributos para realizar la funci�n
 * asociada a la generaci�n de notificaciones para los elementos (personas)
 * asociados a un expediente.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Notificacion")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Notificacion_id")
public class Notificacion extends Documento {

    private String lugar;
    private String motivo;
    private String domicilio;
    private String lugarCitado;
    private Date fechaCitado;
    private String penalidades;
    private String personaAutoriza;
    private String puestoAutoriza;
    private String consecutivoNotificacion;
    private String folioNotificacion;
    private Valor estatus;
    
    private Boolean esFisica;
    
    private Involucrado involucrado;
    private Funcionario funcionario;
    private FuncionarioExterno funcionarioExterno;
    private Audiencia audiencia;
    private CatFormaNotificacion catFormaNotificacion;
    
    /**
     * Usado en solicitudes externas, cuando el sistema necesita saber el numero
     * de caso asociado, nunca se muestra al sistema.
     */
    private String numeroCasoAsociado;

    // Constructors

    /** default constructor */
    public Notificacion() {
    }

    /**
     * 
     * @param documentoId
     */
    public Notificacion(Long documentoId) {
    	super(documentoId);
	}

	// Property accessors
    @Column(name = "cLugar", length = 100)
    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Column(name = "cMotivo")
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Column(name = "cDomicilio", length = 200)
    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Column(name = "cLugarCitado", length = 100)
    public String getLugarCitado() {
        return lugarCitado;
    }

    public void setLugarCitado(String lugarCitado) {
        this.lugarCitado = lugarCitado;
    }

    @Column(name = "dFechaCitado", length = 23, nullable = true)
    public Date getFechaCitado() {
        return fechaCitado;
    }

    public void setFechaCitado(Date fechaCitado) {
        this.fechaCitado = fechaCitado;
    }

    @Column(name = "cPenalidades")
    public String getPenalidades() {
        return penalidades;
    }

    public void setPenalidades(String penalidades) {
        this.penalidades = penalidades;
    }

    @Column(name = "cPersonaAutoriza", length = 100)
    public String getPersonaAutoriza() {
        return personaAutoriza;
    }

    public void setPersonaAutoriza(String personaAutoriza) {
        this.personaAutoriza = personaAutoriza;
    }

    @Column(name = "cPuestoAutoriza", length = 60)
    public String getPuestoAutoriza() {
        return puestoAutoriza;
    }

    public void setPuestoAutoriza(String puestoAutoriza) {
        this.puestoAutoriza = puestoAutoriza;
    }

    @Column(name = "cConsecutivoNotificacion", length = 10)
    public String getConsecutivoNotificacion() {
        return consecutivoNotificacion;
    }

    public void setConsecutivoNotificacion(String consecutivoNotificacion) {
        this.consecutivoNotificacion = consecutivoNotificacion;
    }

    /**
     * M�todo de acceso al campo estatus.
     * 
     * @return El valor del campo estatus
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Estatus_val")
    public Valor getEstatus() {
        return estatus;
    }

    /**
     * Asigna el valor al campo estatus.
     * 
     * @param estatus
     *            el valor estatus a asignar
     */
    public void setEstatus(Valor estatus) {
        this.estatus = estatus;
    }
    /**
     * M�todo de acceso al campo folioSolicitud.
     * 
     * @return El valor del campo folioSolicitud
     */
    @Column(name = "cFolioNotificacion", length = 15, nullable = false)
    public String getFolioNotificacion() {
        return folioNotificacion;
    }

    /**
     * Asigna el valor al campo folioSolicitud.
     * 
     * @param folioSolicitud
     *            el valor folioSolicitud a asignar
     */
    public void setFolioNotificacion(String folioSolicitud) {
        this.folioNotificacion = folioSolicitud;
    }
    
    @Column(name = "cNumeroCasoAsociado", length = 28)
    public String getNumeroCasoAsociado() {
        return this.numeroCasoAsociado;
    }

    public void setNumeroCasoAsociado(String cnumeroCasoAsociado) {
        this.numeroCasoAsociado = cnumeroCasoAsociado;
    }   
    /**
     * M�todo de acceso al campo esFisica.
     * @return El valor del campo esFisica
     */
    @Column(name = "bEsFisica", precision = 1, scale = 0)
    public Boolean getEsFisica() {
        return esFisica;
    }

    /**
     * Asigna el valor al campo esFisica.
     * @param esFisica el valor esFisica a asignar
     */
    public void setEsFisica(Boolean esFisica) {
        this.esFisica = esFisica;
    }

	/**
	 * @return the involucrado
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Involucrado_id", nullable = true)
	public Involucrado getInvolucrado() {
		return involucrado;
	}

	/**
	 * @param involucrado the involucrado to set
	 */
	public void setInvolucrado(Involucrado involucrado) {
		this.involucrado = involucrado;
	}

	/**
	 * @return the funcionario
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iClaveFuncionario", nullable= true)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	/**
	 * @param funcionario the funcionario to set
	 */
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	/**
	 * @return El funcionarioExterno
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FuncionarioExterno_id", nullable= true)
	public FuncionarioExterno getFuncionarioExterno() {
		return funcionarioExterno;
	}

	/**
	 * @param funcionarioExterno el funcionarioExterno a agregar
	 */
	public void setFuncionarioExterno(FuncionarioExterno funcionarioExterno) {
		this.funcionarioExterno = funcionarioExterno;
	}
	
	/**
	 * @return the audiencia
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Audiencia_id", nullable= true)
	public Audiencia getAudiencia() {
		return audiencia;
	}

	/**
	 * @param audiencia the audiencia to set
	 */
	public void setAudiencia(Audiencia audiencia) {
		this.audiencia = audiencia;
	}

	/**
	 * M&eacute;todo de acceso al campo catFormaNotificacion.
	 * @return El valor del campo catFormaNotificacion
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CatFormaNotificacion_id", nullable = true)
	public CatFormaNotificacion getCatFormaNotificacion() {
		return catFormaNotificacion;
	}

	/**
	 * Asigna el valor al campo catFormaNotificacion.
	 * @param catFormaNotificacion el valor catFormaNotificacion a asignar
	 */
	public void setCatFormaNotificacion(CatFormaNotificacion catFormaNotificacion) {
		this.catFormaNotificacion = catFormaNotificacion;
	}
    
    
}
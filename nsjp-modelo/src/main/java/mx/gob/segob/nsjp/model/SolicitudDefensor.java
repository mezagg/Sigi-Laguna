package mx.gob.segob.nsjp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * SolicitudDefensor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SolicitudDefensor")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SolicitudDefensor_id")
public class SolicitudDefensor extends Solicitud {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1510139458007436853L;

	// Fields
    /**
     * @deprecated Usar destinatario de solicitud
     */
    private Funcionario defensor;

    /**
     * Campos usado para cuando la solicitud es entre instituciones v�a NSJP. <br>
     * Nombre completo del elemento
     */
    @Deprecated //Se cambia por el caso de uso nuevo. Revisar InvolucradoSolicitudDefensor
    private String detenido;
    /**
     * Campo usado para cuando la solicitud es entre instituciones v�a NSJP.<br>
     * Folio del elemento
     */
    @Deprecated//Se cambia por el caso de uso nuevo
    private String folioElementoDetenido;
    /**
     * Usado para cuando PJ solicita un defensor y ya hay una audiencia
     * programada.
     */
    private Audiencia audiencia;
    @Deprecated //Se cambia por el caso de uso nuevo
    private Date fechaDesignacion;
    @Deprecated//Se cambia por el caso de uso nuevo
    private Set<SolicitudDefensorDelito> solicitudDefensorDelitos = new HashSet<SolicitudDefensorDelito>();
    @Deprecated//Se cambia por el caso de uso nuevo    
    private Boolean esDetenido;
    
    private Valor tipoAsesoria;
    
	private Set<InvolucradoSolicitudDefensor> involucradoSolicitudDefensor;
	private AvisoDesignacion avisoDesignacion;
	

    // Constructors

    /** default constructor */
    public SolicitudDefensor() {
    }

    /**
     * @deprecated Usar destinatario de solicitud
     * @return
     */

    @Transient
    public Funcionario getDefensor() {
        return this.defensor;
    }
    /**
     * @deprecated Usar destinatario de solicitud
     * @param funcionario
     */
    public void setDefensor(Funcionario funcionario) {
        this.defensor = funcionario;
    }

    /**
     * M�todo de acceso al campo detenido.
     * 
     * @return El valor del campo detenido
     */
    @Column(name = "cDetenido", length = 100)
    public String getDetenido() {
        return detenido;
    }

    /**
     * Asigna el valor al campo detenido.
     * 
     * @param detenido
     *            el valor detenido a asignar
     */
    public void setDetenido(String detenido) {
        this.detenido = detenido;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Audiencia_id")
    public Audiencia getAudiencia() {
        return this.audiencia;
    }

    public void setAudiencia(Audiencia audiencia) {
        this.audiencia = audiencia;
    }

    /**
     * M�todo de acceso al campo solicitudDefensorDelitos.
     * 
     * @return El valor del campo solicitudDefensorDelitos
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "solicitudDefensor")
    public Set<SolicitudDefensorDelito> getSolicitudDefensorDelitos() {
        return solicitudDefensorDelitos;
    }

    /**
     * Asigna el valor al campo solicitudDefensorDelitos.
     * 
     * @param solicitudDefensorDelitos
     *            el valor solicitudDefensorDelitos a asignar
     */
    public void setSolicitudDefensorDelitos(
            Set<SolicitudDefensorDelito> solicitudDefensorDelitos) {
        this.solicitudDefensorDelitos = solicitudDefensorDelitos;
    }

    /**
     * M�todo de acceso al campo fechaDesignacion.
     * 
     * @return El valor del campo fechaDesignacion
     */
    @Column(name = "dFechaDesignacion", length = 23)
    public Date getFechaDesignacion() {
        return fechaDesignacion;
    }

    /**
     * Asigna el valor al campo fechaDesignacion.
     * 
     * @param fechaDesignacion
     *            el valor fechaDesignacion a asignar
     */
    public void setFechaDesignacion(Date fechaDesignacion) {
        this.fechaDesignacion = fechaDesignacion;
    }

    @Column(name = "bEsDetenido", precision = 1, scale = 0)
    public Boolean getEsDetenido() {
        return this.esDetenido;
    }

    public void setEsDetenido(Boolean esDetenido) {
        this.esDetenido = esDetenido;
    }

    /**
     * M�todo de acceso al campo folioElementoDetenido.
     * @return El valor del campo folioElementoDetenido
     */
    @Column(name = "cFolioElementoDetenido", length = 20, nullable = true)
    public String getFolioElementoDetenido() {
        return folioElementoDetenido;
    }

    /**
     * Asigna el valor al campo folioElementoDetenido.
     * @param folioElementoDetenido el valor folioElementoDetenido a asignar
     */
    public void setFolioElementoDetenido(String folioElementoDetenido) {
        this.folioElementoDetenido = folioElementoDetenido;
    }

    /**
     * M�todo de acceso al campo tipoAsesoria.
     * @return El valor del campo tipoAsesoria
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoAsesoria_val")
    public Valor getTipoAsesoria() {
        return tipoAsesoria;
    }

    /**
     * Asigna el valor al campo tipoAsesoria.
     * @param tipoAsesoria el valor tipoAsesoria a asignar
     */
    public void setTipoAsesoria(Valor tipoAsesoria) {
        this.tipoAsesoria = tipoAsesoria;
    }

	/**
	 * M&eacute;todo de acceso al campo involucradoSolicitudDefensor.
	 * 
	 * @return El valor del campo involucradoSolicitudDefensor
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "solicitudDefensor")
	public Set<InvolucradoSolicitudDefensor> getInvolucradoSolicitudDefensor() {
		return involucradoSolicitudDefensor;
	}

	/**
	 * Asigna el valor al campo involucradoSolicitudDefensor.
	 * 
	 * @param involucradoSolicitudDefensor
	 *            el valor involucradoSolicitudDefensor a asignar
	 */
	public void setInvolucradoSolicitudDefensor(
			Set<InvolucradoSolicitudDefensor> involucradoSolicitudDefensor) {
		this.involucradoSolicitudDefensor = involucradoSolicitudDefensor;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "solicitudDefensor")
	public AvisoDesignacion getAvisoDesignacion() {
		return avisoDesignacion;
	}

	public void setAvisoDesignacion(AvisoDesignacion avisoDesignacion) {
		this.avisoDesignacion = avisoDesignacion;
	}
}
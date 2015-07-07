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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * SolicitudPericial entity. <br>
 * Soporta:<br>
 * TiposSolicitudes.ASESORIA<br>
 * TiposSolicitudes.DICTAMEN<br>
 * TiposSolicitudes.EVIDENCIA
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SolicitudPericial")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SolicitudPericial_id")
public class SolicitudPericial extends Solicitud {

    // Fields
    private Valor especialidad;
    private Boolean esEntregaNotificacionFisica;
    private Date fechaInicioPrestamo;
    private Date fechaFinPrestamo;
    private Long solicitudExterna;
    private String quienRecibe;
    private String folioIdentificacion;
    private Valor tipoEstudio;

    /**
     * Usado para saber el rol de la persona que elabora la solicitud, es decir:
     * Abogado defensro, Coordinador Defensor o Coordinador Pericial de
     * defensoria
     */
    private String puestoUsuarioSolicitante;

    private SolicitudPericial solicitudPadre;
    private Set<SolicitudPericial> solicitudesHijas = new HashSet<SolicitudPericial>(
            0);
    // Constructors

    /** default constructor */
    public SolicitudPericial() {
    }
    /** default constructor */
    public SolicitudPericial(Long id) {
    	setDocumentoId(id);
    }
    
    /**Constructor para la consulta de solicitudes del agentemp
     * 
     */
    
    public SolicitudPericial(String folioSolicitud,Long documentoId) {
    	setFolioSolicitud(folioSolicitud);
    	setDocumentoId(documentoId);
    }
    
    
    /**Constructor para la consulta de solicitudes del agentemp
     * 
     */
    
    public SolicitudPericial(String folioSolicitud,NumeroExpediente numeroExpediente,Valor valorByEstatusVal,
    		Date dfechaCierre,Boolean esUrgente,Funcionario funcionarioDestinatario,Valor especialidad,Boolean esEntregaNotificacionFisica,
    		Date fechaInicioPrestamo,Date fechaFinPrestamo,Long solicitudExterna,String quienRecibe,String folioIdentificacion) {
    	setFolioSolicitud(folioSolicitud);
    	setNumeroExpediente(numeroExpediente);
    	setEstatus(valorByEstatusVal);
    	setFechaCierre(dfechaCierre);
    	setEsUrgente(esUrgente);
    	setDestinatario(funcionarioDestinatario);
    	this.especialidad=especialidad;
    	this.esEntregaNotificacionFisica=esEntregaNotificacionFisica;
    	this.fechaInicioPrestamo=fechaInicioPrestamo;
    	this.fechaFinPrestamo=fechaFinPrestamo;
    	this.solicitudExterna=solicitudExterna;
    	this.quienRecibe=quienRecibe;
    	this.folioIdentificacion=folioIdentificacion;
    }
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Especialidad_val")
    public Valor getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Valor especialidad) {
        this.especialidad = especialidad;
    }

    @Column(name = "bEsEntregaNotificacionFisica", precision = 1, scale = 0)
    public Boolean getEsEntregaNotificacionFisica() {
        return esEntregaNotificacionFisica;
    }

    public void setEsEntregaNotificacionFisica(
            Boolean esEntregaNotificacionFisica) {
        this.esEntregaNotificacionFisica = esEntregaNotificacionFisica;
    }

    @Column(name = "cPuestoUsuarioSolicitante", nullable = false, length = 100)
    public String getPuestoUsuarioSolicitante() {
        return puestoUsuarioSolicitante;
    }

    public void setPuestoUsuarioSolicitante(String puestoUsuarioSolicitante) {
        this.puestoUsuarioSolicitante = puestoUsuarioSolicitante;
    }

    /**
     * M�todo de acceso al campo fechaInicioPrestamo.
     * 
     * @return El valor del campo fechaInicioPrestamo
     */
    @Column(name = "dFechaInicioPrestamo", length = 23)
    public Date getFechaInicioPrestamo() {
        return fechaInicioPrestamo;
    }

    /**
     * Asigna el valor al campo fechaInicioPrestamo.
     * 
     * @param fechaInicioPrestamo
     *            el valor fechaInicioPrestamo a asignar
     */
    public void setFechaInicioPrestamo(Date fechaInicioPrestamo) {
        this.fechaInicioPrestamo = fechaInicioPrestamo;
    }

    /**
     * M�todo de acceso al campo fechaFinPrestamo.
     * 
     * @return El valor del campo fechaFinPrestamo
     */
    @Column(name = "dFechaFinPrestamo", length = 23)
    public Date getFechaFinPrestamo() {
        return fechaFinPrestamo;
    }

    /**
     * Asigna el valor al campo fechaFinPrestamo.
     * 
     * @param fechaFinPrestamo
     *            el valor fechaFinPrestamo a asignar
     */
    public void setFechaFinPrestamo(Date fechaFinPrestamo) {
        this.fechaFinPrestamo = fechaFinPrestamo;
    }

    /**
     * M�todo de acceso al campo solicitudPadre.
     * 
     * @return El valor del campo solicitudPadre
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SolicitudPadre_id")
    public SolicitudPericial getSolicitudPadre() {
        return solicitudPadre;
    }

    /**
     * Asigna el valor al campo solicitudPadre.
     * 
     * @param solicitudPadre
     *            el valor solicitudPadre a asignar
     */
    public void setSolicitudPadre(SolicitudPericial solicitudPadre) {
        this.solicitudPadre = solicitudPadre;
    }

    /**
     * M�todo de acceso al campo solicitudesHijas.
     * 
     * @return El valor del campo solicitudesHijas
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "solicitudPadre")
    public Set<SolicitudPericial> getSolicitudesHijas() {
        return solicitudesHijas;
    }

    /**
     * Asigna el valor al campo solicitudesHijas.
     * 
     * @param solicitudesHijas
     *            el valor solicitudesHijas a asignar
     */
    public void setSolicitudesHijas(Set<SolicitudPericial> solicitudesHijas) {
        this.solicitudesHijas = solicitudesHijas;
    }

    /**
     * M�todo de acceso al campo solicitudExterna.
     * 
     * @return El valor del campo solicitudExterna
     */
    @Column(name = "iSolicitudExterna", precision = 18, scale = 0)
    public Long getSolicitudExterna() {
        return solicitudExterna;
    }

    /**
     * Asigna el valor al campo solicitudExterna.
     * 
     * @param solicitudExterna
     *            el valor solicitudExterna a asignar
     */
    public void setSolicitudExterna(Long solicitudExterna) {
        this.solicitudExterna = solicitudExterna;
    }
    /**
     * M�todo de acceso al campo quienRecibe.
     * @return El valor del campo quienRecibe
     */
    @Column(name = "cQuienRecibe", length = 150)
    public String getQuienRecibe() {
        return quienRecibe;
    }
    /**
     * Asigna el valor al campo quienRecibe.
     * @param quienRecibe el valor quienRecibe a asignar
     */
    public void setQuienRecibe(String quienRecibe) {
        this.quienRecibe = quienRecibe;
    }
    /**
     * M�todo de acceso al campo folioIdentificacion.
     * @return El valor del campo folioIdentificacion
     */
    @Column(name = "cFolioIdentificacion", length = 30)
    public String getFolioIdentificacion() {
        return folioIdentificacion;
    }
    /**
     * Asigna el valor al campo folioIdentificacion.
     * @param folioIdentificacion el valor folioIdentificacion a asignar
     */
    public void setFolioIdentificacion(String folioIdentificacion) {
        this.folioIdentificacion = folioIdentificacion;
    }
    
    
	public void setTipoEstudio(Valor tipoEstudio) {
		this.tipoEstudio = tipoEstudio;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TipoEstudio_val")
	public Valor getTipoEstudio() {
		return tipoEstudio;
	}

}
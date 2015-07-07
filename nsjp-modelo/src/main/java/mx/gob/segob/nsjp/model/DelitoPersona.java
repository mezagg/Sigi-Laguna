/**
 * Nombre del Programa		: DelitoPersona.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha:27/06/2013 
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase entity para DelitoPersona
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion		: N/A
 * Dias de ejecucion		: N/A                       Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania					: N/A
 * Proyecto                 : N/A						Fecha: N/A
 * Modificacion				: N/A
 *------------------------------------------------------------------------------
 */

package mx.gob.segob.nsjp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * DelitoPersona entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "DelitoPersona")
public class DelitoPersona implements java.io.Serializable {

	// Fields
	private Long delitoPersonaId;
	private Valor bienTutelado;
	private Valor formaParticipacion;
	private Delito delito;
	private Involucrado victima;
	private Involucrado probableResponsable;
    private Boolean esActivo;
    private Long catDelitoClasificacionId;
    private Long catDelitoLugarId;
    private Long catDelitoModalidadId;
    private Long catDelitoModusId;
    private Long catDelitoCausaId;
    private String folioDelitoPersona;
    private Set<Mandamiento> mandamientos = new HashSet<Mandamiento>(0);
    private Long catDelitoEstadisticaId;
    private Long catDelitoTipoId;
    private Long catDelitoCalificacionId;
    private Long catDelitoConcursoId;
    private Long catDelitoOrdenResId;


	// Constructors

	/** default constructor */
	public DelitoPersona() {
	}

	/** minimal constructor */
	public DelitoPersona(Long delitoPersonaId,
			Valor valorByFormaParticipacionVal, Delito delito,
			Involucrado involucradoByProbableResponsableId,
			Long catDelitoClasificacionId,
		    Long catDelitoLugarId,
		    Long catDelitoModalidadId,
		    Long catDelitoModusId,
		    Long catDelitoCausaId) {
		this.delitoPersonaId = delitoPersonaId;
		this.formaParticipacion = valorByFormaParticipacionVal;
		this.delito = delito;
		this.probableResponsable = involucradoByProbableResponsableId;
		this.catDelitoClasificacionId = catDelitoClasificacionId;
		this.catDelitoLugarId = catDelitoLugarId;
		this.catDelitoModalidadId = catDelitoModalidadId;
		this.catDelitoModusId = catDelitoModusId;
		this.catDelitoCausaId = catDelitoCausaId;
	}

	/** full constructor */
	public DelitoPersona(Long delitoPersonaId, Valor bienTutelado,
			Valor formaParticipacion, Delito delito, Involucrado victima,
			Involucrado probableResponsable, Boolean esActivo,
			Long catDelitoClasificacionId, Long catDelitoLugarId,
			Long catDelitoModalidadId, Long catDelitoModusId,
			Long catDelitoCausaId, String folioDelitoPersona,
			Set<Mandamiento> mandamientos, Long catDelitoEstadisticaId,
			Long catDelitoTipoId, Long catDelitoCalificacionId,
			Long catDelitoConcursoId, Long catDelitoOrdenResId) {
		super();
		this.delitoPersonaId = delitoPersonaId;
		this.bienTutelado = bienTutelado;
		this.formaParticipacion = formaParticipacion;
		this.delito = delito;
		this.victima = victima;
		this.probableResponsable = probableResponsable;
		this.esActivo = esActivo;
		this.catDelitoClasificacionId = catDelitoClasificacionId;
		this.catDelitoLugarId = catDelitoLugarId;
		this.catDelitoModalidadId = catDelitoModalidadId;
		this.catDelitoModusId = catDelitoModusId;
		this.catDelitoCausaId = catDelitoCausaId;
		this.folioDelitoPersona = folioDelitoPersona;
		this.mandamientos = mandamientos;
		this.catDelitoEstadisticaId = catDelitoEstadisticaId;
		this.catDelitoTipoId = catDelitoTipoId;
		this.catDelitoCalificacionId = catDelitoCalificacionId;
		this.catDelitoConcursoId = catDelitoConcursoId;
		this.catDelitoOrdenResId = catDelitoOrdenResId;
	}
	
	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "DelitoPersona_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getDelitoPersonaId() {
		return this.delitoPersonaId;
	}

	public void setDelitoPersonaId(Long delitoPersonaId) {
		this.delitoPersonaId = delitoPersonaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BienTutelado_val")
	public Valor getBienTutelado() {
		return this.bienTutelado;
	}

	public void setBienTutelado(Valor valorByBienTuteladoVal) {
		this.bienTutelado = valorByBienTuteladoVal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FormaParticipacion_val")
	public Valor getFormaParticipacion() {
		return this.formaParticipacion;
	}

	public void setFormaParticipacion(
			Valor valorByFormaParticipacionVal) {
		this.formaParticipacion = valorByFormaParticipacionVal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Delito_id", nullable = false)
	public Delito getDelito() {
		return this.delito;
	}

	public void setDelito(Delito delito) {
		this.delito = delito;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Victima_id", nullable = false)
	public Involucrado getVictima() {
		return this.victima;
	}

	public void setVictima(Involucrado involucradoByVictimaId) {
		this.victima = involucradoByVictimaId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ProbableResponsable_id", nullable = false)
	public Involucrado getProbableResponsable() {
		return this.probableResponsable;
	}

	public void setProbableResponsable(
			Involucrado involucradoByProbableResponsableId) {
		this.probableResponsable = involucradoByProbableResponsableId;
	}
	
	/**
	 * Método de acceso al campo esActivo.
	 * @return El valor del campo esActivo
	 */
    @Column(name = "bEsActivo", precision = 1, scale = 0)
	public Boolean getEsActivo() {
		return esActivo;
	}

	/**
	 * Asigna el valor al campo esActivo.
	 * @param esActivo el valor esActivo a asignar
	 */
	public void setEsActivo(Boolean esActivo) {
		this.esActivo = esActivo;
	}

	@Column(name = "catDelitoClasificacion_id")
	public Long getCatDelitoClasificacionId() {
		return catDelitoClasificacionId;
	}

	public void setCatDelitoClasificacionId(Long catDelitoClasificacionId) {
		this.catDelitoClasificacionId = catDelitoClasificacionId;
	}

	@Column(name = "catDelitoLugar_id")
	public Long getCatDelitoLugarId() {
		return catDelitoLugarId;
	}

	public void setCatDelitoLugarId(Long catDelitoLugarId) {
		this.catDelitoLugarId = catDelitoLugarId;
	}

	@Column(name = "catDelitoModalidad_id")
	public Long getCatDelitoModalidadId() {
		return catDelitoModalidadId;
	}

	public void setCatDelitoModalidadId(Long catDelitoModalidadId) {
		this.catDelitoModalidadId = catDelitoModalidadId;
	}

	@Column(name = "catDelitoModus_id")
	public Long getCatDelitoModusId() {
		return catDelitoModusId;
	}

	public void setCatDelitoModusId(Long catDelitoModusId) {
		this.catDelitoModusId = catDelitoModusId;
	}

	@Column(name = "catDelitoCausa_id")
	public Long getCatDelitoCausaId() {
		return catDelitoCausaId;
	}

	public void setCatDelitoCausaId(Long catDelitoCausaId) {
		this.catDelitoCausaId = catDelitoCausaId;
	}

	/**
	 * @return the folioDelitoPersona
	 */
	@Column(name = "cFolioDelitoPersona", length = 20, nullable = false)
	public String getFolioDelitoPersona() {
		return folioDelitoPersona;
	}

	/**
	 * @param folioDelitoPersona the folioDelitoPersona to set
	 */
	public void setFolioDelitoPersona(String folioDelitoPersona) {
		this.folioDelitoPersona = folioDelitoPersona;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamientoDelitoPersona
	 * @return  MandamientoDelitoPersona obtenidos
	 */
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "MandamientoDelitoPersona", joinColumns = { @JoinColumn(name = "DelitoPersona_id") }, inverseJoinColumns = { @JoinColumn(name = "Mandamiento_id") })
	public Set<Mandamiento> getMandamientos() {
		return mandamientos;
	}

	/**
	 * Asigna el valor al campo mandamientos
	 * @param mandamientos, a asignar
	 */
	public void setMandamientos(Set<Mandamiento> mandamientos) {
		this.mandamientos = mandamientos;
	}

	@Column(name = "catDelitoEstadistica_id")
	public Long getCatDelitoEstadisticaId() {
		return catDelitoEstadisticaId;
	}

	public void setCatDelitoEstadisticaId(Long catDelitoEstadisticaId) {
		this.catDelitoEstadisticaId = catDelitoEstadisticaId;
	}

	@Column(name = "catDelitoTipo_id")
	public Long getCatDelitoTipoId() {
		return catDelitoTipoId;
	}

	public void setCatDelitoTipoId(Long catDelitoTipoId) {
		this.catDelitoTipoId = catDelitoTipoId;
	}

	@Column(name = "catDelitoCalificacion_id")
	public Long getCatDelitoCalificacionId() {
		return catDelitoCalificacionId;
	}

	public void setCatDelitoCalificacionId(Long catDelitoCalificacionId) {
		this.catDelitoCalificacionId = catDelitoCalificacionId;
	}

	@Column(name = "catDelitoConcurso_id")
	public Long getCatDelitoConcursoId() {
		return catDelitoConcursoId;
	}

	public void setCatDelitoConcursoId(Long catDelitoConcursoId) {
		this.catDelitoConcursoId = catDelitoConcursoId;
	}

	@Column(name = "catDelitoOrdenRes_id")
	public Long getCatDelitoOrdenResId() {
		return catDelitoOrdenResId;
	}

	public void setCatDelitoOrdenResId(Long catDelitoOrdenResId) {
		this.catDelitoOrdenResId = catDelitoOrdenResId;
	}
	
	
}
/**
 * Nombre del Programa 		: MandamientoPersona.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: NSJP 								Fecha: 14 Mayo 2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase de persistencia para la tabla MandamientoPersona
 * Programa Dependiente 	: N/A
 * Programa Subsecuente 	: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 								Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 				: N/A
 * Proyecto 				: N/A 								Fecha: N/A
 * Modificacion 			: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * @author AlejandroGA
 * @version 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "MandamientoPersona")
public class MandamientoPersona implements java.io.Serializable {

	// Fields
	private Long mandamientoPersonaId;
	private Mandamiento mandamiento;
	private Persona persona;
	private Valor estatus;
	private Date fechaEjecucion;
	private String folioInterInstitucional;
	private Set<MandamientoPersonaDocumento> mandamientosPersonaDocumento = new HashSet<MandamientoPersonaDocumento>(
			0);

	// Constructors

	/** default constructor */
	public MandamientoPersona() {

	}

	/** minimal constructor */
	public MandamientoPersona(Long mandamientoPersonaId) {
		this.mandamientoPersonaId = mandamientoPersonaId;
	}

	// Property accessors
	/**
	 * M&eacute;todo de acceso al campo id.
	 * 
	 * @return El valor del campo id //@GeneratedValue(strategy =
	 *         GenerationType.IDENTITY)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MandamientoPersona_id", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getMandamientoPersonaId() {
		return mandamientoPersonaId;
	}

	/**
	 * Asigna el valor al id.
	 * 
	 * @param id
	 *            el valor id a asignar
	 */
	public void setMandamientoPersonaId(Long mandamientoPersonaId) {
		this.mandamientoPersonaId = mandamientoPersonaId;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamiento.
	 * 
	 * @return El valor del campo mandamiento
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Mandamiento_id", nullable = false)
	public Mandamiento getMandamiento() {
		return this.mandamiento;
	}

	/**
	 * Asigna el valor al campo mandamiento.
	 * 
	 * @param mandamiento
	 *            el valor mandamiento a asignar
	 */
	public void setMandamiento(Mandamiento mandamiento) {
		this.mandamiento = mandamiento;
	}

	/**
	 * M&eacute;todo de acceso al campo persona.
	 * 
	 * @return El valor del campo persona
	 */

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Persona_id", nullable = false)
	public Persona getPersona() {
		return this.persona;
	}

	/**
	 * Asigna el valor al campo persona.
	 * 
	 * @param persona
	 *            el valor persona a asignar
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * M&eacute;todo de acceso al campo estatus.
	 * 
	 * @return El valor del campo estatus
	 */

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Estatus_val", nullable = false)
	public Valor getEstatus() {
		return this.estatus;
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
	 * M&eacute;todo de acceso al campo fechaEjecucion.
	 * 
	 * @return El valor del campo fechaEjecucion
	 */

	@Column(name = "dFechaEjecucion", nullable = true, length = 23)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getFechaEjecucion() {
		return this.fechaEjecucion;
	}

	/**
	 * Asigna el valor al campo fechaEjecucion.
	 * 
	 * @param fechaEjecucion
	 *            el valor fechaEjecucion a asignar
	 */

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	/**
	 * M&eacute;todo de acceso al campo folioInterInstitucional.
	 * 
	 * @return El valor del campo folioInterInstitucional
	 */

	@Column(name = "cFolioInterInstitucional", length = 20, nullable = false)
	public String getFolioInterInstitucional() {
		return this.folioInterInstitucional;
	}

	/**
	 * Asigna el valor al campo folioInterInstitucional.
	 * 
	 * @param folioInterInstitucional
	 *            el valor folioInterInstitucional a asignar
	 */
	public void setFolioInterInstitucional(String folioInterInstitucional) {
		this.folioInterInstitucional = folioInterInstitucional;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "mandamientoPersona")
	public Set<MandamientoPersonaDocumento> getMandamientosPersonaDocumento() {
		return this.mandamientosPersonaDocumento;
	}

	public void setMandamientosPersonaDocumento(
			Set<MandamientoPersonaDocumento> mandamientosPersonaDocumento) {
		this.mandamientosPersonaDocumento = mandamientosPersonaDocumento;
	}
}

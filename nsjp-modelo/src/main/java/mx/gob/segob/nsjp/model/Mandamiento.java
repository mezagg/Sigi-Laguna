/**
 * Nombre del Programa 		: Mandamiento.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: NSJP 								Fecha: 04 Junio 2013
 * Marca de cambio 			: N/A
 * Descripcion General 		: Clase de persistencia para la tabla Mandamiento
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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Mandamiento entity.
 * 
 * @author AlejandroGA
 * @version 2.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Mandamiento")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "Mandamiento_id")
public class Mandamiento extends Documento {

	/**
	 * Atributos
	 */
	private Resolutivo resolutivo;
	private Valor tipoMandamiento;
	private Valor estatus;
	private String numeroCausa;
	private Set<DelitoPersona> delitosPersona  = new HashSet<DelitoPersona>(0);
	private Set<MandamientoPersona> mandamientosPersona = new HashSet<MandamientoPersona>(0);

	// Constructors
	/** default constructor */
	public Mandamiento() {
	}

	/** Minimal constructor */
	public Mandamiento(Long mandamientoId) {
		super(mandamientoId);
	}

	/** Full Constructor */
	public Mandamiento(Long documentoId, Resolutivo resolutivo,
			Valor tipoMandamiento, Valor estatus, String numeroCausa) {
		super(documentoId);
		this.resolutivo = resolutivo;
		this.tipoMandamiento = tipoMandamiento;
		this.estatus = estatus;
		this.numeroCausa = numeroCausa;
	}

	/**
	 * Obtiene el valor del campo resolutivo
	 * 
	 * @return el valor del resolutivo.
	 */	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Resolutivo_id", nullable = true)
	public Resolutivo getResolutivo() {
		return this.resolutivo;
	}

	/**
	 * Asigna el valor del campo resolutivo
	 * 
	 * @param resolutivo
	 *            a asignar
	 */
	public void setResolutivo(Resolutivo resolutivo) {
		this.resolutivo = resolutivo;
	}

	/**
	 * Obtiene el valor del campo TipoMandamiento
	 * 
	 * @return el valor del campo TipoMandamiento
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TipoMandamiento_val", nullable = false)
	public Valor getTipoMandamiento() {
		return this.tipoMandamiento;
	}

	/**
	 * Asigna el valor del campo TipoMandamiento
	 * 
	 * @param el
	 *            valor del campo TipoMandamiento
	 */
	public void setTipoMandamiento(Valor valor) {
		this.tipoMandamiento = valor;
	}

	/**
	 * M&eacute;todo de acceso al campo estatus.
	 * 
	 * @return El valor del campo estatus
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Estatus_val", nullable = false)
	public Valor getEstatus() {
		return estatus;
	}

	/**
	 * Asigna el valor al campo estatus.
	 * 
	 * @param estatus
	 *            , el valor estatus a asignar
	 */
	public void setEstatus(Valor estatus) {
		this.estatus = estatus;
	}

	/**
	 * M&eacute;todo de acceso al campo numeroCausa.
	 * 
	 * @return El valor del campo numeroCausa
	 */
	@Column(name = "cNumeroCausa", nullable = true, length = 34)
	public String getNumeroCausa() {
		return numeroCausa;
	}

	/**
	 * Asigna el valor al campo numeroCausa.
	 * 
	 * @param numeroCausa
	 *            el valor numeroCausa a asignar
	 */
	public void setNumeroCausa(String numeroCausa) {
		this.numeroCausa = numeroCausa;
	}

	/**
	 * M&eacute;todo de acceso al campo delitosPersona.
	 * 
	 * @return El valor del campo delitosPersona
	 */
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "MandamientoDelitoPersona", joinColumns = { @JoinColumn(name = "Mandamiento_id") }, inverseJoinColumns = { @JoinColumn(name = "DelitoPersona_id") })
	public Set<DelitoPersona> getDelitosPersona() {
		return delitosPersona;
	}

	/**
	 * Asigna el valor al campo delitosPersona.
	 * 
	 * @param delitosPersona
	 *            , el valor delitosPersona a asignar
	 */
	public void setDelitosPersona(Set<DelitoPersona> delitosPersona) {
		this.delitosPersona = delitosPersona;
	}

	
	/**
	 * @return the mandamientosPersona
	 */
	@OneToMany(mappedBy = "mandamiento",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	public Set<MandamientoPersona> getMandamientosPersona() {
		return mandamientosPersona;
	}

	/**
	 * @param mandamientosPersona the mandamientosPersona to set
	 */
	public void setMandamientosPersona(Set<MandamientoPersona> mandamientosPersona) {
		this.mandamientosPersona = mandamientosPersona;
	}
}
/**
 * Nombre del Programa 		: MandamientoPersonaDocumento.java
 * Autor 					: AlejandroGA
 * Compania 				: Ultrasist
 * Proyecto 				: nsjp-modelo 					Fecha: 04/06/2013
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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author AlejandroGA
 * @version 1.0
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "MandamientoPersonaDocumento")
public class MandamientoPersonaDocumento implements java.io.Serializable {

	// Fields
	private MandamientoPersonaDocumentoId mandamientoPersonaDocumentoId;
	private MandamientoPersona mandamientoPersona;
	private Documento documento;
	private Valor estatus;

	// Constructors

	public MandamientoPersonaDocumento() {

	}
	
	/**
	 * @param mandamientoPersonaDocumentoId
	 */
	public MandamientoPersonaDocumento(
			MandamientoPersonaDocumentoId mandamientoPersonaDocumentoId) {
		this.mandamientoPersonaDocumentoId = mandamientoPersonaDocumentoId;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamientoPersonaDocumentoId.
	 * @return El valor del campo mandamientoPersonaDocumentoId
	 */
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "mandamientoPersonaId", column = @Column(name = "MandamientoPersona_id", nullable = false, precision = 18, scale = 0)),
			@AttributeOverride(name = "documentoId", column = @Column(name = "Documento_id", nullable = false, precision = 18, scale = 0)) })
	public MandamientoPersonaDocumentoId getMandamientoPersonaDocumentoId() {
		return mandamientoPersonaDocumentoId;
	}

	/**
	 * Asigna el valor al campo mandamientoPersonaDocumentoId.
	 * @param mandamientoPersonaDocumentoId
	 *            el valor mandamientoPersonaDocumentoId a asignar
	 */
	public void setMandamientoPersonaDocumentoId(
			MandamientoPersonaDocumentoId mandamientoPersonaDocumentoId) {
		this.mandamientoPersonaDocumentoId = mandamientoPersonaDocumentoId;
	}			
			
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MandamientoPersona_id", nullable = false, insertable = false, updatable = false)
	public MandamientoPersona getMandamientoPersona() {
		return this.mandamientoPersona;
	}

	public void setMandamientoPersona(MandamientoPersona mandamientoPersona) {
		this.mandamientoPersona = mandamientoPersona;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Documento_id", nullable = false, insertable = false, updatable = false)
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Estatus_val", nullable = false)
	public Valor getEstatus() {
		return estatus;
	}

	public void setEstatus(Valor estatus) {
		this.estatus = estatus;
	}
}

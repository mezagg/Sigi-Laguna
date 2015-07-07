/**
 * Nombre del Programa 		: MandamientoPersonaDocumentoId.java
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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author AlejandroGA
 * @version 1.0
 */

@SuppressWarnings("serial")
@Embeddable
public class MandamientoPersonaDocumentoId implements java.io.Serializable {

	// Fields
	private Long mandamientoPersonaId;
	private Long documentoId;

	// Constructors

	/**
	 * minimal constructor
	 */
	public MandamientoPersonaDocumentoId() {
	}

	/**
	 * full constructor
	 * 
	 * @param mandamientoPersonaId
	 *            , el valor mandamientoPersonaId a asignar
	 * @param documentoId
	 *            , el valor documentoId a asignar
	 */
	public MandamientoPersonaDocumentoId(Long mandamientoPersonaId,
			Long documentoId) {
		this.mandamientoPersonaId = mandamientoPersonaId;
		this.documentoId = documentoId;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamientoPersonaId.
	 * 
	 * @return El valor del campo mandamientoPersonaId
	 */
	@Column(name = "MandamientoPersona_id", nullable = false, precision = 18, scale = 0)
	public Long getMandamientoPersonaId() {
		return mandamientoPersonaId;
	}

	/**
	 * Asigna el valor al campo mandamientoPersonaId.
	 * 
	 * @param mandamientoPersonaId
	 *            el valor mandamientoPersonaId a asignar
	 */
	public void setMandamientoPersonaId(Long mandamientoPersonaId) {
		this.mandamientoPersonaId = mandamientoPersonaId;
	}

	/**
	 * M&eacute;todo de acceso al campo documentoId.
	 * 
	 * @return El valor del campo documentoId
	 */
	@Column(name = "Documento_id", nullable = false, precision = 18, scale = 0)
	public Long getDocumentoId() {
		return documentoId;
	}

	/**
	 * Asigna el valor al campo documentoId.
	 * 
	 * @param documentoId
	 *            el valor documentoId a asignar
	 */
	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}
}

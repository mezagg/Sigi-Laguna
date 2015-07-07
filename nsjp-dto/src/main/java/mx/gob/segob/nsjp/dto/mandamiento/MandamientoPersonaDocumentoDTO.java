/**
 * Nombre del Programa		: MandamientoPersonaDocumentoDTO.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha:07/06/2013 
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase DTO para mandamientosPersonaDocumento
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

package mx.gob.segob.nsjp.dto.mandamiento;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoPersonaDocumentoDTO {
	
	
	// Atributos
	private MandamientoPersonaDocumentoIdDTO mandamientoPersonaDocumentoId;
	private DocumentoDTO documento;
	private MandamientoPersonaDTO mandamientoPersona;
	private ValorDTO estatus;
	
	//Constructores
	/**
	 * Default
	 */
	public MandamientoPersonaDocumentoDTO() {
		
	}
	
	/**
	 * @param mandamientoPersonaDocumentoId
	 */
	public MandamientoPersonaDocumentoDTO(
			MandamientoPersonaDocumentoIdDTO mandamientoPersonaDocumentoId) {
		this.mandamientoPersonaDocumentoId = mandamientoPersonaDocumentoId;
	}
	
	/**
	 * @param mandamientoPersonaDocumentoId
	 * @param estatus
	 */
	public MandamientoPersonaDocumentoDTO(
			MandamientoPersonaDocumentoIdDTO mandamientoPersonaDocumentoId,
			ValorDTO estatus) {
		this.mandamientoPersonaDocumentoId = mandamientoPersonaDocumentoId;
		this.estatus = estatus;
	}

	
	
	/**
	 * M&eacute;todo de acceso al campo mandamientoPersonaDocumentoId.
	 * @return El valor del campo mandamientoPersonaDocumentoId
	 */
	public MandamientoPersonaDocumentoIdDTO getMandamientoPersonaDocumentoId() {
		return mandamientoPersonaDocumentoId;
	}

	/**
	 * Asigna el valor al campo mandamientoPersonaDocumentoId.
	 * @param mandamientoPersonaDocumentoId a asignar
	 */
	public void setMandamientoPersonaDocumentoId(
			MandamientoPersonaDocumentoIdDTO mandamientoPersonaDocumentoId) {
		this.mandamientoPersonaDocumentoId = mandamientoPersonaDocumentoId;
	}

	/**
	 * M&eacute;todo de acceso al campo documento.
	 * @return El valor del campo documento
	 */
	public DocumentoDTO getDocumento() {
		return documento;
	}

	/**
	 * Asigna el valor al campo documento.
	 * @param documento a asignar
	 */
	public void setDocumento(DocumentoDTO documento) {
		this.documento = documento;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamientoPersona.
	 * @return El valor del campo mandamientoPersona
	 */
	public MandamientoPersonaDTO getMandamientoPersona() {
		return mandamientoPersona;
	}

	/**
	 * Asigna el valor al campo mandamientoPersona.
	 * @param mandamientoPersona a asignar
	 */
	public void setMandamientoPersona(MandamientoPersonaDTO mandamientoPersona) {
		this.mandamientoPersona = mandamientoPersona;
	}

	/**
	 * M&eacute;todo de acceso al campo estatus.
	 * @return El valor del campo estatus
	 */
	public ValorDTO getEstatus() {
		return estatus;
	}

	/**
	 * Asigna el valor al campo estatus.
	 * @param estatus a asignar
	 */
	public void setEstatus(ValorDTO estatus) {
		this.estatus = estatus;
	}
}

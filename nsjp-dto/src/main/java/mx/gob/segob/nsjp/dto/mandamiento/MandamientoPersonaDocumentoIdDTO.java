/**
 * Nombre del Programa		: MandamientoPersonaDocumentoIdDTO.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP						Fecha:07/06/2013 
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase DTO para MandamientoPersonaDocumentoId
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

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoPersonaDocumentoIdDTO {

	//Atributos
	private Long documentoId;
	private Long mandamientoPersonaId;
	
	
	//Constructores
	
	/**
	 * Constructor defaul
	 */
	public MandamientoPersonaDocumentoIdDTO() {
		super();
	}
	
	/**
	 * @param documentoId
	 * @param mandamientoPersonaId
	 */
	public MandamientoPersonaDocumentoIdDTO(Long documentoId,
			Long mandamientoPersonaId) {
		this.documentoId = documentoId;
		this.mandamientoPersonaId = mandamientoPersonaId;
	}

	
	/**
	 * M&eacute;todo de acceso al campo documentoId.
	 * @return El valor del campo documentoId
	 */
	public Long getDocumentoId() {
		return documentoId;
	}

	/**
	 * Asigna el valor al campo documentoId.
	 * @param documentoId a asignar
	 */
	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	/**
	 * M&eacute;todo de acceso al campo mandamientoPersonaId.
	 * @return El valor del campo mandamientoPersonaId
	 */
	public Long getMandamientoPersonaId() {
		return mandamientoPersonaId;
	}

	/**
	 * Asigna el valor al campo mandamientoPersonaId.
	 * @param mandamientoPersonaId a asignar
	 */
	public void setMandamientoPersonaId(Long mandamientoPersonaId) {
		this.mandamientoPersonaId = mandamientoPersonaId;
	}
	
}

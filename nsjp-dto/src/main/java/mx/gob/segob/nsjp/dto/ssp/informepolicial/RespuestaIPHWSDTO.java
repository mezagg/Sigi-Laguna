/**
 * Nombre del Programa : RespuestaIPHWSDTO.java
 * Autor                            : RicardoGG                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 25/09/2012 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : DTO de intercambio entre sistemas para transportar los datos básicos de un IPH.  
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */

package mx.gob.segob.nsjp.dto.ssp.informepolicial;

import mx.gob.segob.nsjp.dto.base.GenericWSDTO;


/**
 * DTO de intercambio entre sistemas para transportar los datos básicos de un IPH.
 *  
 * @version 1.0
 * @author RicardoGG
 *
 */
public class RespuestaIPHWSDTO extends GenericWSDTO{

    
	private static final long serialVersionUID = 619316717610941325L;
	private Long idNuevoExpedienteIPH;
    private String mensajeDeError;
    private Long idDocumentoIPH;
	/**
	 * 
	 */
	public RespuestaIPHWSDTO() {
	}
	
	
	
	public RespuestaIPHWSDTO(Long idNuevoExpedienteIPH, String mensajeDeError) {
		super();
		this.idNuevoExpedienteIPH = idNuevoExpedienteIPH;
		this.mensajeDeError = mensajeDeError;
	}



	public RespuestaIPHWSDTO(long idExpediente) {
		this.idNuevoExpedienteIPH = idExpediente;
	}

	/**
	 * @return the idNuevoExpedienteIPH
	 */
	public Long getIdNuevoExpedienteIPH() {
		return idNuevoExpedienteIPH;
	}

	/**
	 * @param idNuevoExpedienteIPH the idNuevoExpedienteIPH to set
	 */
	public void setIdNuevoExpedienteIPH(Long idNuevoExpedienteIPH) {
		this.idNuevoExpedienteIPH = idNuevoExpedienteIPH;
	}

	/**
	 * @return the mensajeDeError
	 */
	public String getMensajeDeError() {
		return mensajeDeError;
	}
	/**
	 * @param mensajeDeError the mensajeDeError to set
	 */
	public void setMensajeDeError(String mensajeDeError) {
		this.mensajeDeError = mensajeDeError;
	}



	/**
	 * @return the idDocumentoIPH
	 */
	public Long getIdDocumentoIPH() {
		return idDocumentoIPH;
	}



	/**
	 * @param idDocumentoIPH the idDocumentoIPH to set
	 */
	public void setIdDocumentoIPH(Long idDocumentoIPH) {
		this.idDocumentoIPH = idDocumentoIPH;
	}

	
	
}

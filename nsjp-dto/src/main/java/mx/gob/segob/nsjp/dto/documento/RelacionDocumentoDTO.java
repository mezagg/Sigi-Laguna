/**
* Nombre del Programa 		: RelacionDocumentoDTO.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 11/01/2013
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.dto.documento;

import mx.gob.segob.nsjp.dto.base.GenericDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class RelacionDocumentoDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8132743337932080496L;
	
	private Long relacionId;
	private DocumentoDTO documentoPrincipal;
	private DocumentoDTO documentoRelacionado;
	private Boolean esActivo;
	
	/**
	 * M&eacute;todo de acceso al campo relacionId.
	 * @return El valor del campo relacionId
	 */
	public Long getRelacionId() {
		return relacionId;
	}
	
	/**
	 * Asigna el valor al campo relacionId.
	 * @param relacionId el valor relacionId a asignar
	 */
	public void setRelacionId(Long relacionId) {
		this.relacionId = relacionId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo documentoPrincipal.
	 * @return El valor del campo documentoPrincipal
	 */
	public DocumentoDTO getDocumentoPrincipal() {
		return documentoPrincipal;
	}
	
	/**
	 * Asigna el valor al campo documentoPrincipal.
	 * @param documentoPrincipal el valor documentoPrincipal a asignar
	 */
	public void setDocumentoPrincipal(DocumentoDTO documentoPrincipal) {
		this.documentoPrincipal = documentoPrincipal;
	}
	
	/**
	 * M&eacute;todo de acceso al campo documentoRelacionado.
	 * @return El valor del campo documentoRelacionado
	 */
	public DocumentoDTO getDocumentoRelacionado() {
		return documentoRelacionado;
	}
	
	/**
	 * Asigna el valor al campo documentoRelacionado.
	 * @param documentoRelacionado el valor documentoRelacionado a asignar
	 */
	public void setDocumentoRelacionado(DocumentoDTO documentoRelacionado) {
		this.documentoRelacionado = documentoRelacionado;
	}
	
	/**
	 * M&eacute;todo de acceso al campo esActivo.
	 * @return El valor del campo esActivo
	 */
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
	
}

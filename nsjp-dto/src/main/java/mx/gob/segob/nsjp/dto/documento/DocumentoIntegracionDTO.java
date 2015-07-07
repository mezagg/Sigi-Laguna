/**
* Nombre del Programa 		: DocumentoIntegracionDTO.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 26/11/2012
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
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class DocumentoIntegracionDTO extends GenericDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3069385793140350962L;

	private Long documentoIntegracionId;
	private ValorDTO tipoDocumento;
	private Boolean obligatorio;
	
	/**
	 * M&eacute;todo de acceso al campo documentoIntegracionId.
	 * @return El valor del campo documentoIntegracionId
	 */
	public Long getDocumentoIntegracionId() {
		return documentoIntegracionId;
	}
	
	/**
	 * Asigna el valor al campo documentoIntegracionId.
	 * @param documentoIntegracionId el valor documentoIntegracionId a asignar
	 */
	public void setDocumentoIntegracionId(Long documentoIntegracionId) {
		this.documentoIntegracionId = documentoIntegracionId;
	}
	
	/**
	 * M&eacute;todo de acceso al campo tipoDocumento.
	 * @return El valor del campo tipoDocumento
	 */
	public ValorDTO getTipoDocumento() {
		return tipoDocumento;
	}
	
	/**
	 * Asigna el valor al campo tipoDocumento.
	 * @param tipoDocumento el valor tipoDocumento a asignar
	 */
	public void setTipoDocumento(ValorDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	/**
	 * M&eacute;todo de acceso al campo obligatorio.
	 * @return El valor del campo obligatorio
	 */
	public Boolean getObligatorio() {
		return obligatorio;
	}
	
	/**
	 * Asigna el valor al campo obligatorio.
	 * @param obligatorio el valor obligatorio a asignar
	 */
	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	
	
}

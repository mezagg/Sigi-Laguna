/**
* Nombre del Programa 		: DocumentoIntegracionTransformer.java
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
package mx.gob.segob.nsjp.service.documento.impl.tranform;

import mx.gob.segob.nsjp.dto.documento.DocumentoIntegracionDTO;
import mx.gob.segob.nsjp.model.DocumentoIntegracion;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class DocumentoIntegracionTransformer {
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de
	 * tipo DocumentoIntegracion a tipo DocumentoIntegracionDTO.
	 * @param documentoIntegracion - El entity que se va a transformar en DTO.
	 * @return dto - El DTO una vez que se ha hecho la transformaci&oacute;n.
	 */
	public static DocumentoIntegracionDTO transformar(DocumentoIntegracion documentoIntegracion){
		DocumentoIntegracionDTO dto = null;
		if (documentoIntegracion != null){
			dto = new DocumentoIntegracionDTO();
			dto.setDocumentoIntegracionId(documentoIntegracion.getDocumentoIntegracionId());
			dto.setObligatorio(documentoIntegracion.getObligatorio());
			dto.setTipoDocumento(ValorTransformer.transformar(documentoIntegracion.getTipoDocumento()));
		}
		return dto;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de
	 * tipo DocumentoIntegracionDTO a tipo DocumentoIntegracion.
	 * @param documentoIntegracionDTO - El DTO a transformar en un entity.
	 * @return entity - El entity una vez que se ha hecho la transformaci&oacute;n
	 */
	public static DocumentoIntegracion transformar(DocumentoIntegracionDTO documentoIntegracionDTO){
		DocumentoIntegracion entity = null;
		if (documentoIntegracionDTO != null){
			entity = new DocumentoIntegracion();
			entity.setDocumentoIntegracionId(documentoIntegracionDTO.getDocumentoIntegracionId());
			entity.setObligatorio(documentoIntegracionDTO.getObligatorio());
			entity.setTipoDocumento(ValorTransformer.transformar(documentoIntegracionDTO.getTipoDocumento()));
		}
		return entity;
	}
}

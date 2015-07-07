/**
* Nombre del Programa 		: RelacionDocumentoTransformer.java
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
package mx.gob.segob.nsjp.service.documento.impl.tranform;

import mx.gob.segob.nsjp.dto.documento.RelacionDocumentoDTO;
import mx.gob.segob.nsjp.model.RelacionDocumento;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class RelacionDocumentoTransformer {
	
	public static RelacionDocumentoDTO transformar (RelacionDocumento entity){
		RelacionDocumentoDTO dto = null;
		if (entity != null){
			dto = new RelacionDocumentoDTO();
			if (entity.getDocumentoPrincipal() != null){
				dto.setDocumentoPrincipal(DocumentoTransformer.transformarDocumento(entity.getDocumentoPrincipal()));				
			}
			if (entity.getDocumentoRelacionado() != null){
				dto.setDocumentoRelacionado(DocumentoTransformer.transformarDocumento(entity.getDocumentoRelacionado()));				
			}
			dto.setEsActivo(entity.getEsActivo());
			dto.setRelacionId(entity.getRelacionId());
		}
		return dto;
	}
	
	public static RelacionDocumento transformar (RelacionDocumentoDTO dto){
		RelacionDocumento entity = null;
		if (dto != null){
			entity = new RelacionDocumento();
			if (dto.getDocumentoPrincipal() != null){
				entity.setDocumentoPrincipal(DocumentoTransformer.transformarDocumentoDTO(dto.getDocumentoPrincipal()));				
			}
			if (dto.getDocumentoRelacionado() != null){
				entity.setDocumentoRelacionado(DocumentoTransformer.transformarDocumentoDTO(dto.getDocumentoRelacionado()));				
			}
			entity.setEsActivo(dto.getEsActivo());
			entity.setRelacionId(dto.getRelacionId());
		}
		return entity;
	}
}

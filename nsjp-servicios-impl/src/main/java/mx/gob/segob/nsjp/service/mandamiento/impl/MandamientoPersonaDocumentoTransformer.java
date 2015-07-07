/**
 * Nombre del Programa		: MandamientoPersonaDocumentoTransformer.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 15/08/2011
 * Marca de cambio			: N/A
 * Descripcion General		: Clase transformer para MandamientoPersonaDocumentoTransformer
 * Programa Dependiente		: N/A
 * Programa Subsecuente		: N/A
 * Cond. de ejecucion       : N/A
 * Dias de ejecucion        : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania               	: N/A
 * Proyecto                 : N/A                           Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.mandamiento.impl;

import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDocumentoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDocumentoIdDTO;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumento;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoPersonaDocumentoTransformer {

	public static MandamientoPersonaDocumento transformar(
			MandamientoPersonaDocumentoDTO mandamientoDocumentoPersonaDTO) {

		if (mandamientoDocumentoPersonaDTO == null) {
			return null;
		}

		MandamientoPersonaDocumento mandamientoPersonaDocumento = new MandamientoPersonaDocumento();
		
		mandamientoPersonaDocumento.setEstatus(ValorTransformer
				.transformar(mandamientoDocumentoPersonaDTO.getEstatus()));

		return mandamientoPersonaDocumento;
	}
	
	
	public static MandamientoPersonaDocumentoDTO transformar(
			MandamientoPersonaDocumento mandamientoPersonaDocumento) {

		if (mandamientoPersonaDocumento == null) {
			return null;
		}

		MandamientoPersonaDocumentoDTO mandamientoPersonaDocumentoDTO = transformarBasico(mandamientoPersonaDocumento);

		//CUIDADO CON RECURSION
		mandamientoPersonaDocumentoDTO
				.setMandamientoPersona(MandamientoPersonaTransformer
						.transformarBasico(mandamientoPersonaDocumento
								.getMandamientoPersona()));

		return mandamientoPersonaDocumentoDTO;
	}
	
	
	public static MandamientoPersonaDocumentoDTO transformarBasico(
			MandamientoPersonaDocumento mandamientoPersonaDocumento) {

		if (mandamientoPersonaDocumento == null) {
			return null;
		}

		MandamientoPersonaDocumentoDTO mandamientoPersonaDocumentoDTO = new MandamientoPersonaDocumentoDTO();

		MandamientoPersonaDocumentoIdDTO MandamientoPersonaDocumentoIdDTO = new MandamientoPersonaDocumentoIdDTO();
		MandamientoPersonaDocumentoIdDTO
				.setDocumentoId(mandamientoPersonaDocumento
						.getMandamientoPersonaDocumentoId().getDocumentoId());
		MandamientoPersonaDocumentoIdDTO
				.setMandamientoPersonaId(mandamientoPersonaDocumento
						.getMandamientoPersonaDocumentoId()
						.getMandamientoPersonaId());

		mandamientoPersonaDocumentoDTO
				.setMandamientoPersonaDocumentoId(MandamientoPersonaDocumentoIdDTO);
		
		mandamientoPersonaDocumentoDTO.setDocumento(DocumentoTransformer
				.transformarDocumento(mandamientoPersonaDocumento
						.getDocumento()));

		mandamientoPersonaDocumentoDTO.setEstatus(ValorTransformer
				.transformar(mandamientoPersonaDocumento.getEstatus()));

		return mandamientoPersonaDocumentoDTO;

	}

}

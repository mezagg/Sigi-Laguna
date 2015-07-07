/**
 * Nombre del Programa		: MandamientoPersonaTransformer.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 15/08/2011
 * Marca de cambio			: N/A
 * Descripcion General		: Clase transformer para MandamientoPersonaTransformer
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDocumentoDTO;
import mx.gob.segob.nsjp.model.MandamientoPersona;
import mx.gob.segob.nsjp.model.MandamientoPersonaDocumento;
import mx.gob.segob.nsjp.service.documento.impl.tranform.MandamientoJudicialTransformer;
import mx.gob.segob.nsjp.service.persona.impl.transform.PersonaTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoPersonaTransformer {

	public static MandamientoPersona transformar(
			MandamientoPersonaDTO mandamientoPersonaDTO) {

		if (mandamientoPersonaDTO == null) {
			return null;
		}

		MandamientoPersona mandamientoPersona = new MandamientoPersona();

		// Estatus
		mandamientoPersona.setEstatus(ValorTransformer
				.transformar(mandamientoPersonaDTO.getEstatus()));
		// Fecha de Ejecucion
		mandamientoPersona.setFechaEjecucion(mandamientoPersonaDTO
				.getFechaEjecucion());
		// Folio InterInstitucional
		mandamientoPersona.setFolioInterInstitucional(mandamientoPersonaDTO
				.getFolioInterInstitucional());
		// Mandamientos Persona Documento
		if (mandamientoPersonaDTO.getMandamientosPersonaDocumento() != null
				&& !mandamientoPersonaDTO.getMandamientosPersonaDocumento()
						.isEmpty()) {

			Set<MandamientoPersonaDocumento> mandamientosPersona = new HashSet<MandamientoPersonaDocumento>(
					0);
			for (MandamientoPersonaDocumentoDTO dto : mandamientoPersonaDTO
					.getMandamientosPersonaDocumento()) {
				mandamientosPersona.add(MandamientoPersonaDocumentoTransformer
						.transformar(dto));
			}

			mandamientoPersona
					.setMandamientosPersonaDocumento(mandamientosPersona);
		}

		mandamientoPersona.setPersona(PersonaTransformer
				.transformarPersona(mandamientoPersonaDTO.getPersona()));

		mandamientoPersona.setMandamiento(MandamientoJudicialTransformer
				.transformarMandamientoBasico(mandamientoPersonaDTO
						.getMandamiento()));

		return mandamientoPersona;
	}
	
	
	public static MandamientoPersonaDTO transformar(
			MandamientoPersona mandamientoPersona) {

		if (mandamientoPersona == null) {
			return null;
		}

		MandamientoPersonaDTO mandamientoPersonaDTO = transformarBasico(mandamientoPersona);

		if (mandamientoPersona.getMandamientosPersonaDocumento() != null
				&& !mandamientoPersona.getMandamientosPersonaDocumento()
						.isEmpty()) {

			List<MandamientoPersonaDocumentoDTO> listaMandamientosPersonaDocumentoDTO = new ArrayList<MandamientoPersonaDocumentoDTO>();

			for (MandamientoPersonaDocumento mandamientoPersonaDocumento : mandamientoPersona
					.getMandamientosPersonaDocumento()) {
				listaMandamientosPersonaDocumentoDTO
						.add(MandamientoPersonaDocumentoTransformer
								.transformarBasico(mandamientoPersonaDocumento));
			}
			mandamientoPersonaDTO
					.setMandamientosPersonaDocumento(listaMandamientosPersonaDocumentoDTO);
		}

		mandamientoPersonaDTO.setPersona(PersonaTransformer
				.transformarPersona(mandamientoPersona.getPersona()));

		mandamientoPersonaDTO.setMandamiento(MandamientoJudicialTransformer
				.transformarMandamientoBasico(mandamientoPersona
						.getMandamiento()));

		return mandamientoPersonaDTO;
	}
	
	
	public static MandamientoPersonaDTO transformarBasico(
			MandamientoPersona mandamientoPersona) {

		if (mandamientoPersona == null) {
			return null;
		}

		MandamientoPersonaDTO mandamientoPersonaDTO = new MandamientoPersonaDTO();

		mandamientoPersonaDTO.setMandamientoPersonaId(mandamientoPersona
				.getMandamientoPersonaId());
		mandamientoPersonaDTO.setEstatus(ValorTransformer
				.transformar(mandamientoPersona.getEstatus()));
		mandamientoPersonaDTO.setFechaEjecucion(mandamientoPersona
				.getFechaEjecucion());
		mandamientoPersonaDTO.setFolioInterInstitucional(mandamientoPersona
				.getFolioInterInstitucional());
		
		return mandamientoPersonaDTO;
	}
	
	
	public static List<MandamientoPersonaDTO> transformar(
			List<MandamientoPersona> listaMandamientosPersona) {

		if (listaMandamientosPersona == null) {
			return null;
		}

		List<MandamientoPersonaDTO> listaMandamientosPersonaDTO = new ArrayList<MandamientoPersonaDTO>();

		for (MandamientoPersona mandamientoPersona : listaMandamientosPersona) {

			listaMandamientosPersonaDTO.add(transformar(mandamientoPersona));

		}

		return listaMandamientosPersonaDTO;

	}
}

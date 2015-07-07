/**
 * Nombre del Programa		: MandamientoPersonaWSDTOTransformer.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 26/06/2013
 * Marca de cambio			: N/A
 * Descripcion General		: MandamientoWSDTOTransformer
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
package mx.gob.segob.nsjp.service.infra.impl.transform.registrarmandamiento;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaWSDTO;
import mx.gob.segob.nsjp.dto.persona.PersonaDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoPersonaWSDTOTransformer {

	public static MandamientoPersonaDTO transformar(
			MandamientoPersonaWSDTO mandamientoPersonaWSDTO) {

		if (mandamientoPersonaWSDTO == null) {
			return null;
		}

		MandamientoPersonaDTO mandamientoPersonaDTO = new MandamientoPersonaDTO();

		if (mandamientoPersonaWSDTO.getEstatus() != null) {
			mandamientoPersonaDTO.setEstatus(new ValorDTO(
					mandamientoPersonaWSDTO.getEstatus()));
		}

		if (mandamientoPersonaWSDTO.getFechaEjecucion() != null) {
			mandamientoPersonaDTO.setFechaEjecucion(mandamientoPersonaWSDTO
					.getFechaEjecucion());
		}

		mandamientoPersonaDTO
				.setFolioInterInstitucional(mandamientoPersonaWSDTO
						.getFolioInterInstitucional());

		if (mandamientoPersonaWSDTO.getPersona() != null
				&& mandamientoPersonaWSDTO.getPersona().getFolioElemento() != null) {
			PersonaDTO personaDTO = new PersonaDTO();
			personaDTO.setFolioElemento(mandamientoPersonaWSDTO.getPersona()
					.getFolioElemento());
			mandamientoPersonaDTO.setPersona(personaDTO);
		}

		return mandamientoPersonaDTO;
	}

	public static List<MandamientoPersonaDTO> transformarListaWSDTOaDTO(
			List<MandamientoPersonaWSDTO> mandamientosPersonaWSDTO) {

		if (mandamientosPersonaWSDTO == null
				|| mandamientosPersonaWSDTO.isEmpty()) {
			return null;
		}

		List<MandamientoPersonaDTO> listaMandamientoPersona = new ArrayList<MandamientoPersonaDTO>();

		for (MandamientoPersonaWSDTO mandamientoPersonaWSDTO : mandamientosPersonaWSDTO) {
			listaMandamientoPersona.add(transformar(mandamientoPersonaWSDTO));
		}
		return listaMandamientoPersona;
	}

	public static mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoPersonaWSDTO transformar(
			MandamientoPersonaDTO mandamientoPersonaDTO) {

		if (mandamientoPersonaDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoPersonaWSDTO mandamientoPersonaWSDTO = new mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoPersonaWSDTO();

		if (mandamientoPersonaDTO.getEstatus() != null) {
			mandamientoPersonaWSDTO.setEstatus(mandamientoPersonaDTO
					.getEstatus().getIdCampo());
		}

		if (mandamientoPersonaDTO.getFechaEjecucion() != null) {
			mandamientoPersonaWSDTO.setFechaEjecucion(WsTransformer
					.transformFecha(mandamientoPersonaDTO.getFechaEjecucion()));
		}

		mandamientoPersonaWSDTO
				.setFolioInterInstitucional(mandamientoPersonaDTO
						.getFolioInterInstitucional());

		if (mandamientoPersonaDTO.getPersona() != null
				&& mandamientoPersonaDTO.getPersona().getFolioElemento() != null) {
			
			mx.gob.segob.nsjp.ws.cliente.mandamiento.PersonaWSDTO personaWSDTO = new mx.gob.segob.nsjp.ws.cliente.mandamiento.PersonaWSDTO();
			
			personaWSDTO.setFolioElemento(mandamientoPersonaDTO.getPersona()
					.getFolioElemento());
			mandamientoPersonaWSDTO.setPersona(personaWSDTO);
		}

		return mandamientoPersonaWSDTO;

	}

	public static List<mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoPersonaWSDTO> transformarListaDTOaWSDTO(
			List<MandamientoPersonaDTO> mandamientosPersonaDTO) {

		if (mandamientosPersonaDTO == null) {
			return null;
		}

		List<mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoPersonaWSDTO> mandamientosPersonaWSDTO = new ArrayList<mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoPersonaWSDTO>();

		for (MandamientoPersonaDTO mandamientoPersonaDTO : mandamientosPersonaDTO) {
			mandamientosPersonaWSDTO.add(transformar(mandamientoPersonaDTO));
		}

		return mandamientosPersonaWSDTO;
	}
}

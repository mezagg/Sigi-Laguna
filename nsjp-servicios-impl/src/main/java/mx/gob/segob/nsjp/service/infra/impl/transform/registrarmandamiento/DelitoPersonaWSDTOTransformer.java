/**
 * Nombre del Programa		: DelitoPersonaWSDTOTransformer.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 26/06/2013
 * Marca de cambio			: N/A
 * Descripcion General		: DelitoPersonaWSDTOTransformer
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

import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.DelitoPersonaWSDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class DelitoPersonaWSDTOTransformer {

	public static DelitoPersonaDTO transformar(
			DelitoPersonaWSDTO delitoPersonaWSDTO) {

		if (delitoPersonaWSDTO == null) {
			return null;
		}

		DelitoPersonaDTO delitoPersonaDTO = new DelitoPersonaDTO();

		delitoPersonaDTO.setFolioDelitoPersona(delitoPersonaWSDTO
				.getFolioInterInstitucionalDelitoPersona());

		delitoPersonaDTO.setEsActivo(delitoPersonaWSDTO.getEsActivo());

		if (delitoPersonaWSDTO.getClaveInterIntitucionalDelito() != null
				&& !delitoPersonaWSDTO.getClaveInterIntitucionalDelito().trim()
						.isEmpty()) {

			DelitoDTO delitoDTO = new DelitoDTO();
			delitoDTO.setClaveInterInstitucional(delitoPersonaWSDTO
					.getClaveInterIntitucionalDelito());

			delitoPersonaDTO.setDelito(delitoDTO);
		}

		if (delitoPersonaWSDTO.getFolioProbableResponsable() != null
				&& !delitoPersonaWSDTO.getFolioProbableResponsable().trim()
						.isEmpty()) {
			InvolucradoDTO involucradoDTO = new InvolucradoDTO();
			involucradoDTO.setFolioElemento(delitoPersonaWSDTO
					.getFolioProbableResponsable());
			delitoPersonaDTO.setProbableResponsable(involucradoDTO);
		}

		if (delitoPersonaWSDTO.getFolioVictima() != null
				&& !delitoPersonaWSDTO.getFolioVictima().trim().isEmpty()) {
			InvolucradoDTO involucradoDTO = new InvolucradoDTO();
			involucradoDTO.setFolioElemento(delitoPersonaWSDTO
					.getFolioVictima());
			delitoPersonaDTO.setVictima(involucradoDTO);
		}

		return delitoPersonaDTO;
	}

	public static List<DelitoPersonaDTO> transformarListaWSDTOaDTO(
			List<mx.gob.segob.nsjp.dto.solicitud.DelitoPersonaWSDTO> delitosPersonaWSDTO) {

		if (delitosPersonaWSDTO == null || delitosPersonaWSDTO.isEmpty()) {
			return null;
		}

		List<DelitoPersonaDTO> delitosPersonaDTO = new ArrayList<DelitoPersonaDTO>();
		for (DelitoPersonaWSDTO delitoPersonaWSDTO : delitosPersonaWSDTO) {
			delitosPersonaDTO.add(transformar(delitoPersonaWSDTO));
		}
		return delitosPersonaDTO;

	}

	public static mx.gob.segob.nsjp.ws.cliente.mandamiento.DelitoPersonaWSDTO transformar(
			DelitoPersonaDTO delitoPersonaDTO) {

		if (delitoPersonaDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.mandamiento.DelitoPersonaWSDTO delitoPersonaWSDTO = new mx.gob.segob.nsjp.ws.cliente.mandamiento.DelitoPersonaWSDTO();

		delitoPersonaWSDTO
				.setFolioInterInstitucionalDelitoPersona(delitoPersonaDTO
						.getFolioDelitoPersona());
		delitoPersonaWSDTO.setEsActivo(delitoPersonaDTO.getEsActivo());

		if (delitoPersonaDTO.getDelito() != null) {
			delitoPersonaWSDTO.setClaveInterIntitucionalDelito(delitoPersonaDTO
					.getDelito().getClaveInterInstitucional());
		}

		if (delitoPersonaDTO.getProbableResponsable() != null) {
			delitoPersonaWSDTO.setFolioProbableResponsable(delitoPersonaDTO
					.getProbableResponsable().getFolioElemento());
		}

		if (delitoPersonaDTO.getVictima() != null) {
			delitoPersonaWSDTO.setFolioVictima(delitoPersonaDTO.getVictima()
					.getFolioElemento());
		}

		return delitoPersonaWSDTO;
	}
	
	public static List<mx.gob.segob.nsjp.ws.cliente.mandamiento.DelitoPersonaWSDTO> transformarListaDTOaWSDTO (List<DelitoPersonaDTO> delitosPersonaDTO){

		if(delitosPersonaDTO == null){
			return null;
		}
		
		List<mx.gob.segob.nsjp.ws.cliente.mandamiento.DelitoPersonaWSDTO> delitosPersonaWSDTO = new  ArrayList<mx.gob.segob.nsjp.ws.cliente.mandamiento.DelitoPersonaWSDTO>();
		
		for(DelitoPersonaDTO delitoPersonaDTO:delitosPersonaDTO){
			delitosPersonaWSDTO.add(transformar(delitoPersonaDTO));
		}
		
		return delitosPersonaWSDTO;
	}

}

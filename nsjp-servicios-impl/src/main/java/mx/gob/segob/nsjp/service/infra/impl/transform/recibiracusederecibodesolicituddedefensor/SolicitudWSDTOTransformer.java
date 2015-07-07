/**
 * Nombre del Programa	: SolicitudWSDTOTransformer.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Nov 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Transformer SolicitudWSDTOT para el paquete de recibiracusederecibodesolicituddedefensor
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                                 Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.DelitoPersonaWSDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.SolicitudWSDTO;

/**
 * @author AlejandroGA
 */
public class SolicitudWSDTOTransformer {

	public static SolicitudWSDTO transformarWSDTO(SolicitudDTO src) {

		SolicitudWSDTO solicitudWSDTO = null;

		if (src != null) {

			solicitudWSDTO = (SolicitudWSDTO) DocumentoWSDTOTransformer
					.transformarWSDTO(src);
			// atributos prpopios de la solicitud

			// Area destino
			solicitudWSDTO.setAreaDestino(src.getAreaDestino());
			// Area origen
			solicitudWSDTO.setAreaOrigen(src.getAreaOrigen());
			// Asunto
			solicitudWSDTO.setAsuntoSolicitud(src.getAsuntoSolicitud());
			// destinatario

			// destinatarioInstExterna

			// Es urgente
			solicitudWSDTO.setEsUrgente(src.getEsUrgente());
			// fechaCierre
			if (src.getFechaCierre() != null) {
				solicitudWSDTO.setFechaCierre(WsTransformer.transformFecha(src
						.getFechaCierre()));
			}
			// fecha limite
			if (src.getFechaLimite() != null) {
				solicitudWSDTO.setFechaLimite(WsTransformer.transformFecha(src
						.getFechaLimite()));
			}
			// fechaModificacion
			if (src.getFechaModificacion() != null) {
				solicitudWSDTO.setFechaModificacion(WsTransformer
						.transformFecha(src.getFechaModificacion()));
			}
			// Folio solicitud
			solicitudWSDTO.setFolioSolicitud(src.getFolioSolicitud());

			// Id Estatus
			if (src.getEstatus() != null
					&& src.getEstatus().getIdCampo() != null) {
				solicitudWSDTO.setIdEstatus(src.getEstatus().getIdCampo());
			}
			// Institucion
			if (src.getConfInstitucion() != null) {
				solicitudWSDTO.setIdInstitucion(src.getConfInstitucion()
						.getConfInstitucionId());
				solicitudWSDTO.setNombreInstitucionSolicitante(src
						.getConfInstitucion().getNombreInst());
			}
			// id Tipo solicitud
			if (src.getTipoSolicitudDTO() != null) {
				solicitudWSDTO.setIdTipoSolicitud(src.getTipoSolicitudDTO()
						.getIdCampo());
			}
			// Motivo
			solicitudWSDTO.setMotivo(src.getMotivo());
			// Nombre institucion solicitante
			solicitudWSDTO.setNombreInstitucionSolicitante(src
					.getNombreInstitucionSolicitante());
			// Nombre solicitante
			solicitudWSDTO.setNombreSolicitante(src.getNombreSolicitante());
			// Nombre solicitante externo interno
			solicitudWSDTO.setNombreSolicitanteExternoInterno(src
					.getNombreSolicitanteExternoInterno());
			// Num carpeta ejecucion
			solicitudWSDTO.setNumCarpetaEjecucion(src.getNumCarpetaEjecucion());
			// Num Causa
			solicitudWSDTO.setNumCausa(src.getNumCausa());
			// Num caso Asociado
			solicitudWSDTO.setNumeroCasoAsociado(src.getNumeroCasoAsociado());
			// Num causa sentencia

			// Num expediente asociado
			solicitudWSDTO.setNumeroExpedienteAsociado(src
					.getNumeroExpedienteAsociado());
			// Num exp Id

			// Observaciones
			solicitudWSDTO.setObservaciones(src.getObservaciones());
			// Solicitante Externo
			solicitudWSDTO.setSolicitanteExterno(src.getSolicitanteExterno());
			// solicitanteInstExterna

			// usuarioSolicitante
		}

		return solicitudWSDTO;
	}

	public static List<mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.DelitoPersonaWSDTO> transforma(
			List<DelitoPersonaDTO> delitosPersonaDTO) {
		List<mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.DelitoPersonaWSDTO> delitosPersonaWSDTO = null;
		if (delitosPersonaDTO != null && !delitosPersonaDTO.isEmpty()) {
			delitosPersonaWSDTO = new ArrayList<mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.DelitoPersonaWSDTO>();
			for (DelitoPersonaDTO delitoPersonaDTO : delitosPersonaDTO) {
				delitosPersonaWSDTO.add(SolicitudWSDTOTransformer
						.transforma(delitoPersonaDTO));
			}
		}
		return delitosPersonaWSDTO;
	}

	public static mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.DelitoPersonaWSDTO transforma(
			DelitoPersonaDTO delitoPersonaDTO) {
		if (delitoPersonaDTO == null)
			return null;

		// Validacion de completes de datos de una relacion
		// Delito-Persona-Victima
		if (delitoPersonaDTO.getProbableResponsable() == null
				|| delitoPersonaDTO.getProbableResponsable().getFolioElemento() == null
				|| delitoPersonaDTO.getVictima() == null
				|| delitoPersonaDTO.getVictima().getFolioElemento() == null
				|| delitoPersonaDTO.getDelito() == null
				|| delitoPersonaDTO.getDelito().getCatDelitoDTO() == null
				|| delitoPersonaDTO.getDelito().getCatDelitoDTO()
						.getClaveInterInstitucional() == null
				|| delitoPersonaDTO.getDelito().getCatDelitoDTO()
						.getClaveInterInstitucional().isEmpty())
			return null;

		mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.DelitoPersonaWSDTO delitoPersonaWSDTO = new mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.DelitoPersonaWSDTO();

		delitoPersonaWSDTO.setFolioProbableResponsable(delitoPersonaDTO
				.getProbableResponsable().getFolioElemento());
		delitoPersonaWSDTO.setFolioVictima(delitoPersonaDTO.getVictima()
				.getFolioElemento());
		delitoPersonaWSDTO.setClaveInterIntitucionalDelito(delitoPersonaDTO
				.getDelito().getClaveInterInstitucional());

		delitoPersonaWSDTO.setEsPincipal(delitoPersonaDTO.getDelito()
				.getEsPrincipal());
		
		delitoPersonaWSDTO.setEsProbable(delitoPersonaDTO.getDelito().getEsProbable());

		if (delitoPersonaDTO.getBienTutelado() != null){
			delitoPersonaWSDTO.setBienTutelado(delitoPersonaDTO
					.getBienTutelado().getIdCampo());			
		}
		
		if (delitoPersonaDTO.getFormaParticipacion() != null){
			delitoPersonaWSDTO.setFormaParticipacion(delitoPersonaDTO
					.getFormaParticipacion().getIdCampo());
		}
		
		delitoPersonaWSDTO.setEsActivo(delitoPersonaDTO.getEsActivo());
		delitoPersonaWSDTO.setCatDelitoClasificacionId(delitoPersonaDTO
				.getCatDelitoClasificacionId());
		delitoPersonaWSDTO.setCatDelitoLugarId(delitoPersonaDTO
				.getCatDelitoLugarId());
		delitoPersonaWSDTO.setCatDelitoModalidadId(delitoPersonaDTO
				.getCatDelitoModalidadId());
		delitoPersonaWSDTO.setCatDelitoModusId(delitoPersonaDTO
				.getCatDelitoModusId());
		delitoPersonaWSDTO.setCatDelitoCausaId(delitoPersonaDTO
				.getCatDelitoCausaId());
		delitoPersonaWSDTO.setFolioInterInstitucionalDelitoPersona(delitoPersonaDTO.getFolioDelitoPersona());
		
		return delitoPersonaWSDTO;
	}

	public static List<DelitoPersonaDTO> transformaWSDTO(
			List<DelitoPersonaWSDTO> delitosPersonaWSDTO) {
		List<DelitoPersonaDTO> delitosPersonaDTO = null;
		if (delitosPersonaWSDTO != null && !delitosPersonaWSDTO.isEmpty()) {
			delitosPersonaDTO = new ArrayList<DelitoPersonaDTO>();
			for (DelitoPersonaWSDTO delitoPersonaWSDTO : delitosPersonaWSDTO) {
				DelitoPersonaDTO delitoPersonaDTO = SolicitudWSDTOTransformer
						.transforma(delitoPersonaWSDTO);
				if (delitoPersonaDTO != null) {
					delitosPersonaDTO.add(delitoPersonaDTO);
				}
			}
		}
		return delitosPersonaDTO;
	}

	public static DelitoPersonaDTO transforma(
			DelitoPersonaWSDTO delitoPersonaWSDTO) {
		
		if (delitoPersonaWSDTO == null) {
			return null;
		}

		// Validacion de completes de datos de una relacion
		// Delito-Persona-Victima
		if (delitoPersonaWSDTO.getFolioProbableResponsable() == null
				|| delitoPersonaWSDTO.getFolioProbableResponsable().isEmpty()
				|| delitoPersonaWSDTO.getFolioVictima() == null
				|| delitoPersonaWSDTO.getFolioVictima().isEmpty()
				|| delitoPersonaWSDTO.getClaveInterIntitucionalDelito() == null
				|| delitoPersonaWSDTO.getClaveInterIntitucionalDelito()
						.isEmpty()
				|| delitoPersonaWSDTO.getFolioInterInstitucionalDelitoPersona() == null
				|| delitoPersonaWSDTO.getFolioInterInstitucionalDelitoPersona()
						.trim().isEmpty()

		) {
			return null;
		}

		DelitoPersonaDTO delitoPersonaDTO = new DelitoPersonaDTO();

		InvolucradoDTO probRespInvolucradoDto = new InvolucradoDTO();
		probRespInvolucradoDto.setFolioElemento(delitoPersonaWSDTO
				.getFolioProbableResponsable());
		delitoPersonaDTO.setProbableResponsable(probRespInvolucradoDto);

		InvolucradoDTO victimaInvolucradoDto = new InvolucradoDTO();
		victimaInvolucradoDto.setFolioElemento(delitoPersonaWSDTO
				.getFolioVictima());
		delitoPersonaDTO.setVictima(victimaInvolucradoDto);

		DelitoDTO delitoDto = new DelitoDTO();
		CatDelitoDTO catDelitoDto = new CatDelitoDTO();
		catDelitoDto.setClaveInterInstitucional(delitoPersonaWSDTO
				.getClaveInterIntitucionalDelito());
		delitoDto.setCatDelitoDTO(catDelitoDto);
		delitoDto.setEsPrincipal(delitoPersonaWSDTO.getEsPincipal());
		delitoDto.setEsProbable(delitoPersonaWSDTO.getEsProbable());
		delitoPersonaDTO.setDelito(delitoDto);

		if (delitoPersonaWSDTO.getBienTutelado() != null)
			delitoPersonaDTO.setBienTutelado(new ValorDTO(delitoPersonaWSDTO
					.getBienTutelado()));
		if (delitoPersonaWSDTO.getFormaParticipacion() != null)
			delitoPersonaDTO.setFormaParticipacion(new ValorDTO(
					delitoPersonaWSDTO.getFormaParticipacion()));

		delitoPersonaDTO.setEsActivo(delitoPersonaWSDTO.getEsActivo());
		delitoPersonaDTO.setCatDelitoClasificacionId(delitoPersonaWSDTO
				.getCatDelitoClasificacionId());
		delitoPersonaDTO.setCatDelitoLugarId(delitoPersonaWSDTO
				.getCatDelitoLugarId());
		delitoPersonaDTO.setCatDelitoModalidadId(delitoPersonaWSDTO
				.getCatDelitoModalidadId());
		delitoPersonaDTO.setCatDelitoModusId(delitoPersonaWSDTO
				.getCatDelitoModusId());
		delitoPersonaDTO.setCatDelitoCausaId(delitoPersonaWSDTO
				.getCatDelitoCausaId());		
		delitoPersonaDTO.setFolioDelitoPersona(delitoPersonaWSDTO
				.getFolioInterInstitucionalDelitoPersona());
		
		return delitoPersonaDTO;
	}
}

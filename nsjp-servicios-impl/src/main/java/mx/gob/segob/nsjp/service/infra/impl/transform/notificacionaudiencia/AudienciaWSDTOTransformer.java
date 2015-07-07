/**
 * Nombre del Programa		: AudienciaWSDTOTransformer.java
 * Autor					: AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP                    Fecha: 29/05/2013
 * Marca de cambio			: N/A
 * Descripcion General		: Clase para transformar de un objeto AudienciaWSDTO 
 * 							  a AudienciaDTO y viceversa
 * Programa Dependiente		: N/A
 * Programa Subsecuente		: N/A
 * Cond. de ejecucion		: N/A
 * Dias de ejecucion		: N/A                      Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor					: N/A
 * Compania					: N/A
 * Proyecto                 : N/A                      Fecha: N/A
 * Modificacion				: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.infra.impl.transform.notificacionaudiencia;

import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaWSDTO;
import mx.gob.segob.nsjp.dto.audiencia.SalaAudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class AudienciaWSDTOTransformer {

	private static final String SALA_TEMPORAL = "Sala Temporal";

	public static mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.AudienciaWSDTO transformarDtoAWsDto(
			AudienciaDTO audienciaDTO) {

		if (audienciaDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.AudienciaWSDTO audienciaWSDTO = new mx.gob.segob.nsjp.ws.cliente.notificacionaudiencia.AudienciaWSDTO();

		// numero causa
		audienciaWSDTO.setNumeroCausa(audienciaDTO.getNumeroCausa());

		// Datos Sala
		if (audienciaDTO.getSala() != null) {
			audienciaWSDTO
					.setNombreSala(audienciaDTO.getSala().getNombreSala());
			audienciaWSDTO.setDomicilioSala(audienciaDTO.getSala()
					.getDomicilioSala());
			audienciaWSDTO.setUbicacionSala(audienciaDTO.getSala()
					.getUbicacionSala());
		} else {
			if (audienciaDTO.getSalaTemporal() != null) {
				audienciaWSDTO.setNombreSala(SALA_TEMPORAL);
				audienciaWSDTO.setDomicilioSala(audienciaDTO.getSalaTemporal()
						.getDomicilioSala());
				audienciaWSDTO.setUbicacionSala(audienciaDTO.getSalaTemporal()
						.getUbicacionSala());
			}
		}

		// duracion estimada
		audienciaWSDTO.setDuracionEstimada(audienciaDTO.getDuracionEstimada());

		// fechaHora Audiencia
		if (audienciaDTO.getFechaEvento() != null) {
			audienciaWSDTO.setFechaHoraAudiencia(WsTransformer
					.transformFecha(audienciaDTO.getFechaEvento()));
			
			audienciaWSDTO.setFechaInicio(WsTransformer
					.transformFecha(audienciaDTO.getFechaEvento()));
		}

		// fechaFin
		if (audienciaDTO.getFechaHoraFin() != null) {
			audienciaWSDTO.setFechaFin(WsTransformer
					.transformFecha(audienciaDTO.getFechaHoraFin()));
		}

		// Folio audiencia
		audienciaWSDTO.setFolioAudiencia(audienciaDTO.getFolioAudiencia());

		// tipo audiencia
		if (audienciaDTO.getTipoAudiencia() != null) {
			audienciaWSDTO.setTipoAudienciaId(audienciaDTO.getTipoAudiencia()
					.getIdCampo());
		}

		// estatus audiencia
		if (audienciaDTO.getEstatusAudiencia() != null) {
			audienciaWSDTO.setEstatusAudienciaId(audienciaDTO
					.getEstatusAudiencia().getIdCampo());
		}

		// fecha Asignacion de sala
		if (audienciaDTO.getFechaAsignacionSala() != null) {
			audienciaWSDTO.setFechaAsignacionSala(WsTransformer
					.transformFecha(audienciaDTO.getFechaAsignacionSala()));
		}

		// numero toca o carpeta
		audienciaWSDTO.setNumeroTocaOrCarpeta(audienciaDTO
				.getNumeroTocaOrCarpeta());

		return audienciaWSDTO;
	}

	public static AudienciaDTO transformarWsDtoADto(
			AudienciaWSDTO audienciaWSDTO) {

		if (audienciaWSDTO == null) {
			return null;
		}

		AudienciaDTO audienciaDTO = new AudienciaDTO();
		// numero causa
		audienciaDTO.setNumeroCausa(audienciaWSDTO.getNumeroCausa());

		// Datos Sala
		if (audienciaWSDTO.getNombreSala() != null) {

			SalaAudienciaDTO salaAudienciaDTO = new SalaAudienciaDTO();

			salaAudienciaDTO.setNombreSala(audienciaWSDTO.getNombreSala());
			salaAudienciaDTO
					.setUbicacionSala(audienciaWSDTO.getUbicacionSala());
			salaAudienciaDTO
					.setDomicilioSala(audienciaWSDTO.getDomicilioSala());
			audienciaDTO.setSala(salaAudienciaDTO);
		}

		// duracion estimada
		audienciaDTO.setDuracionEstimada(audienciaWSDTO.getDuracionEstimada());

		// fechaHora Audiencia
		if (audienciaWSDTO.getFechaHoraAudiencia() != null) {
			audienciaDTO.setFechaEvento(audienciaWSDTO.getFechaHoraAudiencia());
		}

		// fechaFin
		if (audienciaWSDTO.getFechaFin() != null) {
			audienciaDTO.setFechaHoraFin(audienciaWSDTO.getFechaFin());
		}

		// fechaHora Inicio
		if (audienciaWSDTO.getFechaInicio() != null) {
			audienciaDTO.setFechaEvento(audienciaWSDTO.getFechaInicio());
		}

		// Folio audiencia
		audienciaDTO.setFolioAudiencia(audienciaWSDTO.getFolioAudiencia());

		// tipo audiencia
		if (audienciaWSDTO.getTipoAudienciaId() != null) {
			audienciaDTO.setTipoAudiencia(new ValorDTO(audienciaWSDTO
					.getTipoAudienciaId()));
		}

		// estatus audiencia
		if (audienciaWSDTO.getEstatusAudienciaId() != null) {
			audienciaDTO.setEstatusAudiencia(new ValorDTO(audienciaWSDTO
					.getEstatusAudienciaId()));
		}

		// fecha Asignacion de sala
		if (audienciaWSDTO.getFechaAsignacionSala() != null) {
			audienciaDTO.setFechaAsignacionSala(audienciaWSDTO
					.getFechaAsignacionSala());
		}

		// numero toca o carpeta
		audienciaDTO.setNumeroTocaOrCarpeta(audienciaWSDTO
				.getNumeroTocaOrCarpeta());

		return audienciaDTO;
	}

}

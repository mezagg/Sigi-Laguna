/**
 * Nombre del Programa	: AudienciaWSDTOTransformer.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 29 Nov 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Transformer AudienciaWSDTO para el paquete de recibiracusederecibodesolicituddedefensor
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

import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.service.infra.impl.transform.WsTransformer;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.AudienciaWSDTO;

/**
 * @author AlejandroGA
 * 
 */
public class AudienciaWSDTOTransformer {

	public static AudienciaWSDTO transformarWSDTO(AudienciaDTO src) {

		AudienciaWSDTO audienciaWSDTO = null;

		if (src != null) {

			audienciaWSDTO = new AudienciaWSDTO();

			// domicilio sala
			audienciaWSDTO.setDomicilioSala(src.getDomicilioEvento());
			// duracion estimada
			audienciaWSDTO.setDuracionEstimada(src.getDuracionEstimada());
			// estatus audiencia
			if (src.getEstatusAudiencia() != null) {
				audienciaWSDTO.setEstatusAudienciaId(src.getEstatusAudiencia()
						.getIdCampo());
			}
			// fecha Asignacion de sala
			if (src.getFechaAsignacionSala() != null) {
				audienciaWSDTO.setFechaAsignacionSala(WsTransformer
						.transformFecha(src.getFechaAsignacionSala()));
			}
			// fechaFin
			if (src.getFechaFiltroFin() != null) {
				audienciaWSDTO.setFechaFin(WsTransformer.transformFecha(src
						.getFechaFiltroFin()));
			}
			// fechaHora Audiencia
			if (src.getFechaEvento() != null) {
				audienciaWSDTO.setFechaHoraAudiencia(WsTransformer
						.transformFecha(src.getFechaEvento()));
			}
			// fechaHora Inicio
			if (src.getFechaFiltroInicio() != null) {
				audienciaWSDTO.setFechaInicio(WsTransformer.transformFecha(src
						.getFechaFiltroInicio()));
			}
			// Folio audiencia
			audienciaWSDTO.setFolioAudiencia(src.getFolioAudiencia());
			// Involucrados

			
			// Nombre sala
			if (src.getSala() != null) {
				audienciaWSDTO.setNombreSala(src.getSala().getNombreSala());
				// sala temporal
				audienciaWSDTO.setSalaTemporal(src.getSala().isTemporal());
				// ubicacion sala
				audienciaWSDTO.setUbicacionSala(src.getSala()
						.getUbicacionSala());
			}
			// numero causa
			audienciaWSDTO.setNumeroCausa(src.getNumeroCausa());
			// numero toca o carpeta
			audienciaWSDTO.setNumeroTocaOrCarpeta(src.getNumeroTocaOrCarpeta());
			// tipo audiencia
			if (src.getTipoAudiencia() != null) {
				audienciaWSDTO.setTipoAudienciaId(src.getTipoAudiencia()
						.getIdCampo());
			}
		}
		return audienciaWSDTO;
	}
}

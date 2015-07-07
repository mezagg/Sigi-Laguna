/**
 * Nombre del Programa : ExpedienteSesionTransformer.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 11 Mar 2013
 * Marca de cambio        : N/A
 * Descripcion General    : Clase de utileria para la manipulaci&0acute;n de DTO ExpedienteSesionDTO.
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.base.action;

import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteSesionDTO;

/**
 * Clase de utileria para la manipulaci&0acute;n de DTO ExpedienteSesionDTO.
 * 
 * @author GustavoBP
 */
public class ExpedienteSesionTransformer {

	public static ExpedienteDTO transformaExpedienteEnSesion(
			ExpedienteSesionDTO expedienteSesion) {
		if (expedienteSesion == null) {
			return null;
		}
		ExpedienteDTO expedienteDto = new ExpedienteDTO();
		expedienteDto.setExpedienteId(expedienteSesion.getExpedienteId());
		expedienteDto.setNumeroExpediente(expedienteSesion
				.getNumeroExpediente());
		expedienteDto.setNumeroExpedienteId(expedienteSesion
				.getNumeroExpedienteId());

		CasoDTO loCasoDTO = new CasoDTO();
		loCasoDTO.setCasoId(expedienteSesion.getCasoId());
		loCasoDTO.setNumeroGeneralCaso(expedienteSesion.getNumeroGeneralCaso());

		expedienteDto.setCasoDTO(loCasoDTO);
		return expedienteDto;
	}

	public static ExpedienteSesionDTO transformaExpedienteEnSesion(
			ExpedienteDTO expedienteDTO) {
		if (expedienteDTO == null) {
			return null;
		}
		ExpedienteSesionDTO loExpedienteSesionDTO = new ExpedienteSesionDTO();
		loExpedienteSesionDTO.setExpedienteId(expedienteDTO.getExpedienteId());
		loExpedienteSesionDTO.setNumeroExpediente(expedienteDTO
				.getNumeroExpediente());
		loExpedienteSesionDTO.setNumeroExpedienteId(expedienteDTO
				.getNumeroExpedienteId());

		if (expedienteDTO.getCasoDTO() != null) {
			loExpedienteSesionDTO.setCasoId(expedienteDTO.getCasoDTO()
					.getCasoId());
			loExpedienteSesionDTO.setNumeroGeneralCaso(expedienteDTO
					.getCasoDTO().getNumeroGeneralCaso());
		}

		return loExpedienteSesionDTO;
	}

}

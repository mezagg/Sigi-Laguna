/**
 * Nombre del Programa	: SolicitudDefensorWSDTOTransformer.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Nov 2012
 * Marca de cambio      : N/A
 * Descripcion General  : Transformer SolicitudDefensorWSDTO para el paquete de recibiracusederecibodesolicituddedefensor
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

import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.SolicitudDefensorWSDTO;

/**
 * @author AlejandroGA
 */
public class SolicitudDefensorWSDTOTransformer {

	public static SolicitudDefensorWSDTO transformarWSDTO(
			SolicitudDefensorDTO src) {

		SolicitudDefensorWSDTO solicitudDefensorWSDTO = null;

		if (src != null) {

			solicitudDefensorWSDTO = (SolicitudDefensorWSDTO) SolicitudWSDTOTransformer
					.transformarWSDTO(src);
			// atributos prpopios de la solicitud del defensor

			// Audiencia
			solicitudDefensorWSDTO.setAudiencia(AudienciaWSDTOTransformer
					.transformarWSDTO(src.getAudiencia()));

			// involucrados

			// Tipo asesoria
			if (src.getTipoAsesoria() != null) {
				solicitudDefensorWSDTO.setTipoAsesoria(src.getTipoAsesoria()
						.getIdCampo());
			}
			
			if (src.getAvisoDesignacion() != null) {
				solicitudDefensorWSDTO
						.setDefensorAsignado(FuncionarioExternoWSDTOTransformer
								.transformarWSDTO(src.getAvisoDesignacion()
										.getFuncionario()));
			}
		}

		return solicitudDefensorWSDTO;
	}
}

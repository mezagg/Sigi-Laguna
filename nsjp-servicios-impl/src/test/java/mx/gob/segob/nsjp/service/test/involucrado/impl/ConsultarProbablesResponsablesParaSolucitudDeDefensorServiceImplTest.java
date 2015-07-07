/**
* Nombre del Programa		: ConsultarProbablesResponsablesParaSolucitudDeDefensorServiceImplTest.java
* Autor						: AlejandroGA
* Compania					: Ultrasist
* Proyecto                  : NSJP                    Fecha: 31 Oct 2012
* Marca de cambio        	: N/A
* Descripcion General    	: Tests para consultarProbablesResponsablesParaSolicitudDefensor
* Programa Dependiente  	: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion        : N/A
* Dias de ejecucion         : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                     :N/A
* Compania               	:N/A
* Proyecto                 	:N/A                                 Fecha: N/A
* Modificacion           	:N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.test.involucrado.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.service.involucrado.ConsultarProbablesResponsablesParaSolucitudDeDefensorService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Tests para consultarProbablesResponsablesParaSolicitudDefensor
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class ConsultarProbablesResponsablesParaSolucitudDeDefensorServiceImplTest
		extends
		BaseTestServicios<ConsultarProbablesResponsablesParaSolucitudDeDefensorService> {

	
	
	public void testConsultarProbablesResponsablesParaSolucitudDeDefensor() {

		try {
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setExpedienteId(3600L);

			List<InvolucradoDTO> involucrados = service
					.consultarProbablesResponsablesParaSolucitudDeDefensor(expedienteDTO);

			if (involucrados != null && !involucrados.isEmpty()) {

				logger.info("\n\r Existen " + involucrados.size()
						+ " involucrados");

				for (InvolucradoDTO dto : involucrados) {
					logger.info("Involucrado ID: " + dto.getElementoId());
					logger.info("Nombre: " + dto.getNombreCompleto());
					logger.info("Calidad: "
							+ dto.getCalidadDTO().getValorIdCalidad()
									.getValor());
					logger.info("EsDetenido:"+dto.getEsDetenido());
//					InvolucradoWSDTO involucradoWSDTO = InvolucradoWSDTOTransformer
//							.transforma2Send(dto);
//					logger.info("EsDetenido:"+involucradoWSDTO.isEsDetenido());
				}
			} else {
				logger.info("\n\r NO existes probables responsables que cumplan con las condiciones");
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
}

/**
 * Nombre del Programa	: IngresarFuncionarioExternoAudienciaServiceImplTest.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 11 Marzo 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Service Test de EliminarFuncionarioExternoAudienciaServiceImpl
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                             Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.test.audiencia.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
import mx.gob.segob.nsjp.service.audiencia.EliminarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Test para eliminar un funcionario externo de una audiencia
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class EliminarFuncionarioExternoAudienciaServiceImplTest extends
		BaseTestServicios<EliminarFuncionarioExternoAudienciaService> {

	public void testIngresarFuncionarioExternoAudienciaService()
			throws NSJPNegocioException {

		try {

			logger.info("Tests eliminar asociacion funcionario externo-audiencia");
			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO = new FuncionarioExternoAudienciaIdDTO();
			funcionarioExternoAudienciaIdDTO.setAudienciaId(793L);
			funcionarioExternoAudienciaIdDTO.setFuncionarioExternoId(22L);
			service.eliminarFuncionarioExternoDeAudiencia(funcionarioExternoAudienciaIdDTO);

		} catch (Exception e) {
			logger.info("Exception e" + e);
		}
	}

}

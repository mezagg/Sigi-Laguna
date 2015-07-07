/**
 * Nombre del Programa : GenerarFolioDelitoPersonaServiceImplTest.java
 * Autor               : AlejandroGA
 * Compania            : Ultrasist
 * Proyecto            : NSJP                    	  Fecha: 18/04/2013
 * Marca de cambio     : N/A
 * Descripcion General : Test para Generar un folio de la relaci&oacute; delito-persona
 * Programa Dependiente: N/A
 * Programa Subsecuente: N/A
 * Cond. de ejecucion  : N/A
 * Dias de ejecucion   : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor               : N/A
 * Compania            : N/A
 * Proyecto            : N/A                           Fecha: N/A
 * Modificacion        : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.test.delito.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.service.delito.GenerarFolioDelitoPersonaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class GenerarFolioDelitoPersonaServiceImplTest extends
		BaseTestServicios<GenerarFolioDelitoPersonaService> {

	public void testsGenerarFolioDelitoPersona() {
		try {
			String ultimoFolioDp = service.generarFolioDelitoPersona();
			assertEquals("FG2013-1", ultimoFolioDp);
			logger.info("ultimoFolioDp:" + ultimoFolioDp);
		} catch (NSJPNegocioException e) {
			logger.error("No fue posible generar el folio", e);
			logger.error(e.getCause(), e);
		}

	}
}

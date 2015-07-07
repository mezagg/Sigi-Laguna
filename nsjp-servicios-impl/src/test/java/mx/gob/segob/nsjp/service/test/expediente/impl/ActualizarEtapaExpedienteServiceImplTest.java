/**
 * Nombre del Programa : ActualizarEtapaExpedienteServiceImplTest.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 23/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Clase de Prueba Unitaria para los servicios de Actualizar Etapa Expediente
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
package mx.gob.segob.nsjp.service.test.expediente.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.service.expediente.ActualizarEtapaExpedienteService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Clase de Prueba Unitaria para los servicios de Actualizar Etapa Expediente
 * 
 * @author GustavoBP
 */
public class ActualizarEtapaExpedienteServiceImplTest extends
		BaseTestServicios<ActualizarEtapaExpedienteService> {

	public void testActualizarEtapaInvolucradoExpediente() {
		 logger.info("Probando el servicio de: ActualizarEtapaInvolucradoExpediente");
		 
		try {
			Long involucradoId = 3372L;
			Long etapaValorId = 7582L;
			service.actualizarEtapaInvolucradoExpediente(involucradoId , etapaValorId );
		} catch (NSJPNegocioException e) {
			 fail("Ocurrio una excepcion al consultar el test ActualizarEtapaInvolucradoExpediente");
		}
		
	}
}

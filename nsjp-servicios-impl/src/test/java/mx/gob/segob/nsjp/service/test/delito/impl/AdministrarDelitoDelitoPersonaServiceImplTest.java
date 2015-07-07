/**
 * Nombre del Programa : EliminarDelitoServiceImplTest.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Prueba Unitario del servicio para elminar delitos y dependencia con DelitoPersona.
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
package mx.gob.segob.nsjp.service.test.delito.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.service.delito.AdministrarDelitoDelitoPersonaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba Unitario del servicio para elminar delitos y dependencia con
 * DelitoPersona.
 * 
 * @author GustavoBP
 */
public class AdministrarDelitoDelitoPersonaServiceImplTest extends
		BaseTestServicios<AdministrarDelitoDelitoPersonaService> {

	public void tetsEliminarDelitosExpediente() {
		Long expedienteId = 0L;// 793L;
		try {
			service.eliminarDelitosExpediente(expedienteId);
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(), e);
		}
	}
}

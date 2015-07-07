/**
 * Nombre del Programa : actualizarEstatusSolicitudServiceImplTest.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 29 Jul 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Prueba unitaria de ConsultarSolicitudServiceImpl
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
package mx.gob.segob.nsjp.service.test.solicitud.impl;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.service.solicitud.ActualizarEstatusSolicitudService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria de actualizarEstatusSolicitudServiceImplTest.
 * 
 * @version 1.0
 * @author rgama
 * 
 */
//TODO GAMA Corregir el nombre de la clase de acuerdo al estandar, documentar la clase adecuadamente y Documentar Metodos.
public class actualizarEstatusSolicitudServiceImplTest
        extends
            BaseTestServicios<ActualizarEstatusSolicitudService> {
    
    public void testActualizarEstatusSolicitud() {
    	String folioSolicitud = "CD/199900001";   	
    	
        try {
        	
            service.actualizaEstatusSolicitud(folioSolicitud, EstatusSolicitud.CERRADA);
        } catch (NSJPNegocioException e) {
            fail(e.getMessage());
        }
    }    
	
    /**
     * Prueba Unitara para el servicio que actualiza el estatus de la solicitudes
     * asociadas al n&uacute;mero de expediente y, asi mismo, el estatus del n&uacute;mero de 
     * expediente. 
     */
	public void testActualizarEstatusSolicitudes() {
		try {
			Long nuevoEstatusNumExpId = EstatusExpediente.POR_ASIGNAR
					.getValorId();
			Long numeroExpedienteId = 705L;

			Boolean regreso = service.actualizarEstatusSolicitudes(
					nuevoEstatusNumExpId, numeroExpedienteId);
			logger.debug("regreso:" + regreso);
			assertTrue(
					"No se actualizaron los estatus de la solicitudes del numExpedienteID:"
							+ nuevoEstatusNumExpId, regreso);
			logger.debug("Actualizacion exitosa del numExpedienteID:"
					+ nuevoEstatusNumExpId);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
}

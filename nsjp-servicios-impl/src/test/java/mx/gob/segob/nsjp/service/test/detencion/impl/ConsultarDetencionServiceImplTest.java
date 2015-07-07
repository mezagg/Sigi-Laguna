/**
 * Nombre del Programa : ConsultarDetencionServiceImplTest.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 05-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.test.detencion.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.service.detencion.ConsultarDetencionService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
public class ConsultarDetencionServiceImplTest
    extends BaseTestServicios<ConsultarDetencionService> {

    public void testConsultarDetencionService(){
        try {
            logger.info("Probando el servicio de: ConsultarDetencionService");
            assert service != null;
            DetencionDTO detencionDto = service.consultarDetencion(993L, null);
            assertNotNull("detencionDto", detencionDto);
        } catch (NSJPNegocioException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
            fail("Ocurrio una excepcion al ejecutar el test ConsultarDetencionService");
        }
    }
    
    
    public void testConsultarDetencionesPorExpedienteId(){
        try {
        	
        	Long idExpediente = 3140L;
        	
            List<DetencionDTO> detenciones = service.consultarDetencionesPorExpedienteId(idExpediente);
           logger.info("=====================");
           logger.info("Total Detenciones: " + detenciones.size());
           
            for (DetencionDTO detencion: detenciones) {
    			logger.info("Detencion: " + detencion.getDetencionId());
    			logger.info("Detenido: " + detencion.getInvolucradoDTO().getNombreCompleto());
    			logger.info("Fecha de incio: " + detencion.getFechaInicioDetencion());
    			logger.info("Fecha de fin: " + detencion.getFechaFinDetencion());
    		}
            
        } catch (NSJPNegocioException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug(ex);
            }
           fail("Ocurrio una excepcion al ejecutar eltestConsultarDetencionService");
        }
    }
   
}

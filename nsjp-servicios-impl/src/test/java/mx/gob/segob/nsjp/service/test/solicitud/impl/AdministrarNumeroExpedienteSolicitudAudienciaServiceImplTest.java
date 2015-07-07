/**
 * Nombre del Programa 	: AdministrarNumeroExpedienteSolicitudAudienciaServiceImplTest.java                                    
 * Autor               	: AlejandroGA                                              
 * Compania            	: Ultrasist                                                
 * Proyecto            	: NSJP              			Fecha:17/05/2013 
 * Marca de cambio     	: N/A                                                     
 * Descripcion General  : Clase test para AdministrarNumeroExpedienteSolicitudAudienciaService
 * Programa Dependiente : N/A                                                      
 * Programa Subsecuente : N/A                                                      
 * Cond. de ejecucion   : N/A                                                  
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                               MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                 :N/A                                                           
 * Compania              :N/A                                                           
 * Proyecto              :N/A                                   Fecha: N/A       
 * Modificacion          :N/A                                                           
 *------------------------------------------------------------------------------      
 */
package mx.gob.segob.nsjp.service.test.solicitud.impl;

import junit.framework.Assert;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.service.solicitud.AdministrarNumeroExpedienteSolicitudAudienciaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class AdministrarNumeroExpedienteSolicitudAudienciaServiceImplTest  extends
BaseTestServicios<AdministrarNumeroExpedienteSolicitudAudienciaService>{
	
	public void editarNumeroExpedienteSolicitudAudienciaTest(){
		
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setNumeroExpedienteId(679L);
		
		try {
			Boolean sePuedeActualizar = service.editarNumeroExpedienteSolicitudAudiencia(expedienteDTO);
			logger.info("RESPUESTA DE LA PRUEBA="+sePuedeActualizar);
			Assert.assertTrue(sePuedeActualizar);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
			fail();
		}
	}
	
	public void actualizarNumeroExpedienteSolicitudAudienciaTest() {

		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setNumeroExpedienteId(679L);
		expedienteDTO.setNumeroExpediente("00035/CA/2013-PJ-ZAC-002");

		try {

			Boolean seActualizo = service
					.actualizarNumeroExpedienteSolicitudAudiencia(expedienteDTO);
			logger.info("RESPUESTA DE LA PRUEBA=" + seActualizo);
			Assert.assertTrue(seActualizo);
		} catch (NSJPNegocioException e) {
			logger.error(e.getCodigo());
			fail();
		}
	}

}

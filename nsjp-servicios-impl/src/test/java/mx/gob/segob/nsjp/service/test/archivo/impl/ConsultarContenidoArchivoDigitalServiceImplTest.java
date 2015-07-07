/**
 * Nombre del Programa : ConsultarContenidoArchivoDigitalServiceImplTest.java                                    
 * Autor                            : GustavoBP                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 11/04/2013
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Prueba unitaria para el servicio para consultar archivo digital                    
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.service.test.archivo.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.service.archivo.ConsultarContenidoArchivoDigitalService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria para el servicio para consultar archivo digital
 * 
 * @author GustavoBP
 */

//TODO Hacer pruebas unitarias de los servicios de ConsultarContenidoArchivoDigitalService 
public class ConsultarContenidoArchivoDigitalServiceImplTest extends
		BaseTestServicios<ConsultarContenidoArchivoDigitalService> {

	public void testConsultarArchivoDigitalXElementoId() {

		Long idElemento = 17881L;

		try {
			ArchivoDigitalDTO archivoDigitalDTO = service
					.consultarArchivoDigitalXElementoId(idElemento);
			assertNotNull("No se tienen archivo asociado al elemento ("
					+ idElemento+")", archivoDigitalDTO);
			logger.info("ArchivoDigital:" + archivoDigitalDTO);
			logger.info("ArchivoDigital-ArchivoDigitalId:"
					+ archivoDigitalDTO.getArchivoDigitalId());
			logger.info("ArchivoDigital-NombreArchivo:"
					+ archivoDigitalDTO.getNombreArchivo());
			logger.info("ArchivoDigital-TipoArchivo:"
					+ archivoDigitalDTO.getTipoArchivo());

		} catch (NSJPNegocioException e) {
			logger.error(e);
		}

	}

}

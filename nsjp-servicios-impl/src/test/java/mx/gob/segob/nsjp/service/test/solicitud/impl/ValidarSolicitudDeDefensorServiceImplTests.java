/**
* Nombre del Programa		: ValidarSolicitudDeDefensorServiceImplTests.java
* Autor						: AlejandroGA
* Compania					: Ultrasist
* Proyecto                  : NSJP                    Fecha: 01 Nov 2012
* Marca de cambio        	: N/A
* Descripcion General    	: Servicio tests para validar si se puede 
* 							  enviar una solicitud de defensor
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
package mx.gob.segob.nsjp.service.test.solicitud.impl;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.service.solicitud.ValidarSolicitudDeDefensorService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author AlejandroGA
 *
 */
public class ValidarSolicitudDeDefensorServiceImplTests extends BaseTestServicios<ValidarSolicitudDeDefensorService>{
	
	
	public void testValidarSolicitudDeDefensorSevice(){
		
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setExpedienteId(3101L);
	
		try {
			String respuesta = service.validarSolicitudDeDefensor(expedienteDTO);
			logger.debug("Respuesta del servicio:::::"+respuesta);
			
		} catch (NSJPNegocioException e) {
			logger.error("error en la prueba"+e);
			e.printStackTrace();
		}
	}
}

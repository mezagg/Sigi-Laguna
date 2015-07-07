/**
* Nombre del Programa : RegistrarPermisoExpedienteServiceImplTest.java
* Autor                            : cesar
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 4 Oct 2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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

import java.util.Date;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.expediente.RegistrarPermisoExpedienteService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * @author cesar
 *
 */
public class RegistrarPermisoExpedienteServiceImplTest extends
		BaseTestServicios<RegistrarPermisoExpedienteService> {

	public void testRegistrarPermisoExpedienteFuncionario() {
		
		try {
			UsuarioDTO usuarioFirmado = new UsuarioDTO();
			AreaDTO area = new AreaDTO();
			area.setAreaId(Areas.PROCURADOR.parseLong());
			usuarioFirmado.setAreaActual(area);
			
			service.registrarPermisoExpedienteFuncionario(new Long(251), new Long(3786), new Date(), false, usuarioFirmado);
			fail();
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testEliminarPermisoExpedienteFuncionario() {
		try {
			UsuarioDTO usuarioFirmado = new UsuarioDTO();
			AreaDTO area = new AreaDTO();
			area.setAreaId(Areas.PROCURADOR.parseLong());
			usuarioFirmado.setAreaActual(area);
			service.eliminarPermisoExpedienteFuncionario(new Long(250), new Long(3787), usuarioFirmado);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
}

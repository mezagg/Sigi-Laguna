/**
* Nombre del Programa		: AtenderSolicitudDeDefensorServiceImplTest.java
* Autor						: AlejandroGA
* Compania					: Ultrasist
* Proyecto					: NSJP                    Fecha: 14/11/2012
* Marca de cambio			: N/A
* Descripcion General		: Service Tests de atender solicitud de defensor
* Programa Dependiente		: N/A
* Programa Subsecuente		: N/A
* Cond. de ejecucion		: N/A
* Dias de ejecucion			: N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor						: N/A
* Compania					: N/A
* Proyecto					: N/A                                 Fecha: N/A
* Modificacion				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.test.solicitud.impl;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.solicitud.AtenderSolicitudDeDefensorService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Service Tests de atender solicitud de defensor
 * @version 1.0
 * @author AlejandroGA
 */
public class AtenderSolicitudDeDefensorServiceImplTest extends BaseTestServicios<AtenderSolicitudDeDefensorService>{

	/**
	 * TEST para atender una solicitud de defensor
	 */
	public void testAtenderSolicitudDeDefensorServiceImplTest() {
				
		try {
			
			UsuarioDTO usuarioFirmado = new UsuarioDTO();
			usuarioFirmado.setAreaActual(new AreaDTO(Areas.COORDINACION_DEFENSORIA.parseLong()));
			usuarioFirmado.setFuncionario(new FuncionarioDTO(32L));
			
			SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO();
			solicitudDefensorDTO.setDocumentoId(1599L);
			
			String respuesta = service.atenderSolicitudDeDefensor(solicitudDefensorDTO, usuarioFirmado);
			logger.debug("RESPUESTA DEL TESTS ATENDER SOLICITUD DE DEFENSOR"+respuesta);
			
		} catch (NSJPNegocioException e) {
			logger.error("OCURRIO UN ERROR"+e);
			e.printStackTrace();
		}
	}

}

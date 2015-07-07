/**
* Nombre del Programa : ActualizarSituacionJuridicaInvolucradoServiceImplTest.java
* Autor                            : cesar
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 2 Sep 2011
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
package mx.gob.segob.nsjp.service.test.involucrado.impl;

import mx.gob.segob.nsjp.comun.enums.involucrado.SituacionJuridica;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.involucrado.ActualizarSituacionJuridicaInvolucradoService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author cesar
 *
 */
public class ActualizarSituacionJuridicaInvolucradoServiceImplTest extends
		BaseTestServicios<ActualizarSituacionJuridicaInvolucradoService> {

	public void testActualizarSituacionJuridicaInvolucrado() {
		InvolucradoDTO involucradoDTO = new InvolucradoDTO();
		involucradoDTO.setElementoId(new Long(13));
		
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO(new Long(16));
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(new Long(16));
		usuarioDTO.setFuncionario(funcionarioDTO);
		
		involucradoDTO.setUsuario(usuarioDTO);
		
		
		try {
			Long respuesta = service.actualizarSituacionJuridicaInvolucrado(involucradoDTO, SituacionJuridica.INDICIADO.getValorId(), null);
			
			assertNotNull(respuesta);
			logger.info("--------------------------");
			logger.info("Carpeta Ejecucion ID :: "+respuesta);
			logger.info("--------------------------");
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public void testObtenerSituacionJuridicaInvolucrado() {
		InvolucradoDTO involucradoDTO = new InvolucradoDTO();
		involucradoDTO.setElementoId(new Long(59));
		
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO(new Long(16));
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(new Long(16));
		usuarioDTO.setFuncionario(funcionarioDTO);
		
		involucradoDTO.setUsuario(usuarioDTO);
		
		
		try {
			Long situacionJuridica = service.obtenerSituacionJuridicaInvolucrado(involucradoDTO);
			
			assertNotNull(situacionJuridica);
			logger.info("--------------------------");
			logger.info("situacionJuridica:: "+situacionJuridica);
			logger.info("--------------------------");
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
		
	}
	
}

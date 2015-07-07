/**
 * 
 */
package mx.gob.segob.nsjp.service.almacen;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;

/**
 * @author adrian
 *
 */
public interface ConsultarEncargadosAlmacenService {

	/**
	 * Operaci�n que permite consultar los funcionarios asignados como encargados para un almac�n dado
	 * @param almacenDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<FuncionarioDTO> consultarEncargadosDAlmacen(AlmacenDTO almacenDTO)throws NSJPNegocioException;

	/**
	 * Operaci�n que permite consultar los funcionarios internos y externos al Sistema asignados como encargados para un almac�n dado
	 * @param almacenDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<FuncionarioDTO> consultarEncargadosInternosExternosDAlmacen(AlmacenDTO almacenDTO)throws NSJPNegocioException;
}

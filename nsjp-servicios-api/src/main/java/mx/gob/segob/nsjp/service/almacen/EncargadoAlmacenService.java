/**
 * 
 */
package mx.gob.segob.nsjp.service.almacen;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;

/**
 * @author adrian
 *
 */
public interface EncargadoAlmacenService {

	/**
	 * Operaci�n que permite asignar un almac�n a un funcionario 
	 * @param almacenDTO: Id
	 * @param funcionarioDTO: Id
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long asignarEncargadoDAlmacen(AlmacenDTO almacenDTO,
			FuncionarioDTO funcionarioDTO)throws NSJPNegocioException;

	/**
	 * Operaci�n que permite des-asignar un almac�n a un funcionario (Si elimina el cruce, no los objetos)
	 * @param almacenDTO
	 * @param funcionarioDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long removerEncargadoDAlmacen(AlmacenDTO almacenDTO,
			FuncionarioDTO funcionarioDTO)throws NSJPNegocioException;

}

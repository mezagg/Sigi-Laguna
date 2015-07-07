/**
 * 
 */
package mx.gob.segob.nsjp.delegate.almacen;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;

/**
 * @author adrian
 *
 */
public interface EncargadoAlmacenDelegate {
	
	/**
	 * Operaci�n que permite asignar un almac�n a un funcionario 
	 * @param almacenDTO: Id
	 * @param funcionarioDTO: Id
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long asignarEncargadoDAlmacen(AlmacenDTO almacenDTO, FuncionarioDTO funcionarioDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite des-asignar un almac�n a un funcionario (Si elimina el cruce, no los objetos)
	 * @param almacenDTO: Id
	 * @param funcionarioDTO: Id
	 * @return
	 * @throws NSJPNegocioException
	 */
	public Long removerEncargadoDAlmacen(AlmacenDTO almacenDTO, FuncionarioDTO funcionarioDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar los funcionarios asignados como encargados para un almac�n dado
	 * @param almacenDTO: Id
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarEncargadosDAlmacen(AlmacenDTO almacenDTO)throws NSJPNegocioException;
	
	/**
	 * Operaci�n que permite consultar los funcionarios internos y encargados externos al Sistema asignados como encargados para un 
	 * almac�n dado, los encargados externos no cuentan con clave de funcionario
	 * @param almacenDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarEncargadosInternosExternosDAlmacen (AlmacenDTO almacenDTO) 
			throws NSJPNegocioException;
}

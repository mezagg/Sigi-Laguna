/**
* Nombre del Programa 		: FuncionarioExternoService.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 13 Apr 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.funcionarioexterno;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;

/**
 * Clase de servicios que expone la funcionalidad relacionada con los 
 * funcionarios externos.
 * @version 1.0
 * @author EdgarTE
 *
 */
public interface FuncionarioExternoService {
	
	/**
	 * M&eacute;todo que lleva a cabo la persistencia de un funcionario externo dentro
	 * de la base de datos.
	 * @param funcionarioExternoDTO - el funcionario externo a persistir.
	 * @return funcionarioExternoDTO - el mismo funcionario externo con su id asociado.
	 */
	public FuncionarioExternoDTO crearFuncionarioExterno(FuncionarioExternoDTO funcionarioExternoDTO);
	
	/**
	 * M&eacute;todo encargado de llevar a cabo la actualizaci&oacute;n de un funcionario
	 * externo dentro de la base de datos.
	 * @param funcionarioExternoDTO - el funcionario externo a actualizar.
	 */
	public void actualizarFuncionarioExterno(FuncionarioExternoDTO funcionarioExternoDTO);
	
	/**
	 * M&eacute;todo encargado de llevar a cabo la baja f&iacute;sica del
	 * funcionario externo persistido en la base de datos.
	 * @param funcionarioExternoDTO - El funcionario externo a eliminar.
	 */
	public void eliminarFuncionarioExterno(FuncionarioExternoDTO funcionarioExternoDTO);
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de un funcionario externo a trav&eacute;s de
	 * su identificador asociado.
	 * @param funcionarioExternoDTO - DTO que incluye el identificador del funcionario externo a 
	 * 								  consultar.
	 * @return funcionarioExternoDTO - DTO con todos los datos asociados del funcionario externo.
	 */
	public FuncionarioExternoDTO consultarFuncionarioExternoPorId(FuncionarioExternoDTO funcionarioExternoDTO);
	
	/**
	 *  M&eacute;todo para guardar o actualizar un funcionario externo de acuerdo su iClaveFuncionarioInsExt y el confInstitucionId
	 * @param funcionarioExternoDTO -Debe incluir la clave funcionario inst externo y el confInstitucion, como obligatorios
	 * @return funcionarioExternoDTO - el mismo funcionario externo con su id asociado.
	 */
	public FuncionarioExternoDTO guardarActualizarFuncionarioExternoPorIClaveFuncionarioInsExt(
			FuncionarioExternoDTO funcionarioExternoDTO)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para consultar funcionarios externos por filtro
	 * no asociados a la audiencia.
	 * 
	 * @param funcionarioExternoDTO, filtro funcionario externo 
	 * @param audienciaDTO,		audiencia con su respectivo Id con la que no se relacionan
	 * los funcionarios externos que superaron el primer filtro
	 * @return List<FuncionarioExternoDTO>, Lista de funcionarios externos que superaron el primer filtro
	 * que no estan asociados con la audiencia
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioExternoDTO> consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(
			FuncionarioExternoDTO funcionarioExternoDTO,
			AudienciaDTO audienciaDTO) throws NSJPNegocioException;

}

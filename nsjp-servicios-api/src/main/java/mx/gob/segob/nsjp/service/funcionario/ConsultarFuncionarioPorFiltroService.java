/**
* Nombre del Programa : ConsultarPeritosPorFiltroServiceImpl.java
* Autor                            : rgama
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    
* Marca de cambio        : N/A
* Descripcion General    : Servicio para designar un abogado defensor
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
package mx.gob.segob.nsjp.service.funcionario;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.funcionario.CriterioConsultaFuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.model.Funcionario;

/**
 * Servicio para registrar un Perito.
 * @version 1.0
 * @author rgama
 *
 */
public interface ConsultarFuncionarioPorFiltroService {
	/**
	 * Metodo que permite consultar Peritos por filtro
	 * @param filtro: permite configurar el objeto para poder filtrar por
	 * 
	 * - Identificador de personal operativo
	 * - Nombre
	 * - Apellido Materno
	 * - Apellido Paterno
	 * - Especialidad
	 * - CURP
	 * - RFC (con homoclave)
	 * - Correo electr�nico (email))
	 * - Nombre de la organizaci�n a la que pertenece.
	 * - Puesto
	 * - C�dula profesional
	 * - �rea
	 * - Instituci�n
	 * - N�mero de empleado
	 * @return List<PeritoDTO>
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarFuncionarioPorFiltro(FuncionarioDTO filtro,Long rolId, Boolean esCambiarResponsableAExpediente)
		throws NSJPNegocioException;

	/**
	 * Servicio que consultar los funcionarios asociados a un departamento y los
	 * asociados al �rea, a la que pertenece el departamento.
	 * 
	 * @param idDepartamento
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<FuncionarioDTO> consultarFuncionarioPorDepartamentoYArea(Long idDepartamento)
		throws NSJPNegocioException ;

	/**
	 * Servicio que permite consultar a los funcionarios de una agencia en particular
	 * @param idAgencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarFuncionariosXAgencia(Long idAgencia)throws NSJPNegocioException;

	/**
	 * M&eacute;todo utilizado para llevar a cabo la consulta de los funcionarios en base a un 
	 * criterio din&aacute;mico.
	 * @param criterio - El criterio sobre el cual se va a llevar a cabo la consulta.
	 * @return List<FuncionarioDTO> - Lista de funcionarios que cumplen con el criterio 
	 * 		   de b&uacute;squeda. 
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarFuncionariosXCriterio(CriterioConsultaFuncionarioExternoDTO 
			criterioConsultaFuncionarioExternoDTO) throws NSJPNegocioException;
	
    /**
	 * M&eacute;todo utilizado para llevar a cabo la consulta de los funcionarios pertenencientes 
	 * a una instituci&oacute;n en espec&iacute;fico en base a un criterio establecido.
	 * @param criterioConsultaFuncionarioExternoDTO - El criterio sobre el cual se va a llevar a 
	 * 												  cabo la consulta.
	 * @param target - La instituci�n sobre la cual se va a invocar el servicio.
	 * @return List<FuncionarioDTO> - Lista de funcionarios que cumplen con el criterio 
	 * 		   						  de b&uacute;squeda. 
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarFuncionariosExternosXCriterio(CriterioConsultaFuncionarioExternoDTO 
			criterioConsultaFuncionarioExternoDTO, Instituciones target) throws NSJPNegocioException;
	
	/**
	 * Permite consultar funcionarios por filtro y por un conjunto de areas
	 * @param filtro
	 * @param idsJerarquiaOrganizacional
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarFuncionarioXFiltroYAreas(FuncionarioDTO filtro, List<Long> idsJerarquiaOrganizacional)
	throws NSJPNegocioException;

	/**
	 * Permite consultar todos los funcionarios existentes
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioDTO> consultarFuncionarios()
			throws NSJPNegocioException;
}



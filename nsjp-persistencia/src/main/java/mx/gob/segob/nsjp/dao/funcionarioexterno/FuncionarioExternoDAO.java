/**
 * Nombre del Programa 		: FuncionarioExternoDAO.java
 * Autor 					: AAAV
 * Compania 					: Ultrasist
 * Proyecto 					: NSJP 								Fecha: 06 Jun 2012
 * Marca de cambio        	: N/A
 * Descripcion General    	: Objeto de acceso a datos para la entidad FuncionarioExterno
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion 		: N/A
 * Dias de ejecucion 		: N/A 								Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor 					: N/A
 * Compania 					: N/A
 * Proyecto 					: N/A 								Fecha: N/A
 * Modificacion 				: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.funcionarioexterno;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.FuncionarioExterno;

/**
 * Clase de acceso a datos que permite llevar a cabo operaciones sobre la
 * entidad FuncionarioExterno.
 * 
 * @version 1.0
 * @author AAAV
 * 
 */
public interface FuncionarioExternoDAO extends
		GenericDao<FuncionarioExterno, Long> {

	/**
	 * @param claveFuncExt
	 *            , Clave funcionario
	 * @param claveInst
	 *            , ConfInstitucionId del funcionario
	 * @return FuncionarioExterno, Un &uacute;nico funcionario externo con la
	 *         clave funcionario y conf institucion id
	 */
	FuncionarioExterno consultarFuncExternoPorClaveFuncExt(Long claveFuncExt,
			Long claveInst);

	/**
	 * Consulta los funcionario externos por filtro
	 * @param filtro, los atributos propios de la entidad FuncionarioExterno
	 * @return List<FuncionarioExterno>, La lista de funcionarios externos que corresponden al filtro
	 */
	List<FuncionarioExterno> ejecutarQueryConsultarFuncionarioExternoPorFiltro(FuncionarioExterno filtro);
	
	/**
	 * @param funcionarioExterno
	 *            , Funcionario externo filtro
	 * @param audiencia
	 *            , Audiencia con audienciaId para consultar los funcionarios NO
	 *            asociados a la audiencia
	 * @return List<FuncionarioExterno>, Lista de funcionarios externos por
	 *         algun filtro que no esten asociados a la audiencia
	 */
	List<FuncionarioExterno> consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(
			FuncionarioExterno filtro, Audiencia audiencia);
}

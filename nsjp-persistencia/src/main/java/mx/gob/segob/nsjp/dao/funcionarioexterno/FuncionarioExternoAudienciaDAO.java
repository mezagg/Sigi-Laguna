/**
 * Nombre del Programa	: FuncionarioExternoAudienciaDAO.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Interface de la clase FuncionarioExternoAudienciaDAOImpl
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                             Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.funcionarioexterno;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudienciaId;

/**
 * Interface de la clase FuncionarioExternoAudienciaDAOImpl
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public interface FuncionarioExternoAudienciaDAO extends
		GenericDao<FuncionarioExternoAudiencia, FuncionarioExternoAudienciaId> {

	/**
	 * M&eacute;todo que consulta los funcionario externos por determinado rol
	 * asociados a determinada audiencia
	 * @param rolId, Rol del funcionario externo deseado
	 * @param audienciaId, Audiencia en la cual se relaciona al funcionario externo
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<FuncionarioExterno> consultarFuncionarioExternoPorRol(
			Long rolId, Long audienciaId);
}

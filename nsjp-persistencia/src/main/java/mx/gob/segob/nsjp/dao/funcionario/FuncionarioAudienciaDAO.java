/**
* Nombre del Programa : FuncionarioAudienciaDAO.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24/06/2011
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
package mx.gob.segob.nsjp.dao.funcionario;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.FuncionarioAudiencia;
import mx.gob.segob.nsjp.model.FuncionarioAudienciaId;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
public interface FuncionarioAudienciaDAO extends
		GenericDao<FuncionarioAudiencia, FuncionarioAudienciaId> {

	/**
	 * Actualiza el campo que indica si el funcionario est� presente o no en una audiencia
	 * @param funcionarioId ID del funcionario de audiencia
	 * @param audienciaId ID de la audiencia
	 * @param presente Valor del campo que indica si est� presente
	 * @param esTitular indica si el funcionario es titular o suplente
	 * @author Emigdio Hern�ndez
	 */
	void actualizarIndicadorPresenteInvolucrado(Long funcionarioId, Long audienciaId,boolean presente,Boolean esTitular);
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n de los involucrados que 
	 * no son titulares cuando se actualiza uno que s&iacute; lo es.
	 * @param funcionarioId - ID del funcionario de audiencia
	 * @param audienciaId - ID de la audiencia
	 * @author Alejandro Traconis
	 */
	void actualizarInvolucradosNoTitularesPorEspecialidad(Long funcionarioId, Long audienciaId, 
			Long tipoEspecialidadId) throws NSJPNegocioException;
	
	/**
	 * Consulta si el funcionario est� presente o no en una audiencia
	 * @param funcionarioId ID del funcionario de audiencia
	 * @param audienciaId ID de la audiencia
	 * @author Eduardo Alvarado
	 */
	FuncionarioAudiencia consultarIndicadorPresenteInvolucrado(Long funcionarioId, Long audienciaId);
	
	/**
	 * Permite consultar los funcionarios(jueces) asociados a una denuncia
	 * @param audienciaId
	 * @return
	 */
	public List<Funcionario> consultarFuncionariosPorAudiencia(Long audienciaId);
	
	/**
	 * Permite eliminar la relacion de funcionarios con audiencia
	 * @param audienciaId
	 */
	public List<FuncionarioAudiencia> consultaFuncionariosPorAudiencia(Long audienciaId);
	
}


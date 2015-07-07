/**
* Nombre del Programa : TareaDAO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 12 Jul 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato de metodos de acceso a datos para la entidad Tarea
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
package mx.gob.segob.nsjp.dao.tarea;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Tarea;

/**
 * Contrato de metodos de acceso a datos para la entidad Tarea.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public interface TareaDAO extends GenericDao<Tarea, Long> {

	/**
	 * Calcula las horas de carga de trabajo de un Funcionario de un dia
	 * @param fechaInicioEvento
	 * @return
	 */
	Long sumCargaDiaria(Date fechaInicioEvento, Long funcionarioId);

	/**
	 * 
	 * @param iniTarea
	 * @param finTarea
	 * @return
	 */
	List<Tarea> obtenerDisponibilidadHorarioActividad(Date iniTarea, Short duracion, Long idFuncionario);

	/**
	 * 
	 * @param idFuncionario
	 * @return
	 */
	List<Tarea> consultarTareasFuncionario(Long idFuncionario, Date fecha);
	
	/**
	 * 
	 * @param idFuncionario
	 * @param fecha
	 * @return
	 */
	List<Tarea> consultarTareasFuncionarioByEstatus(Long idFuncionario, Date fecha, Long estatus);
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de una tarea en base al identificador del evento asociado.
	 * @param idEventoCita - El identificador del evento a partir del cual se va a recuperar la tarea.
	 * @return tarea - La tarea que se encuentra asociada con el identificador del evento.
	 * @throws NSJPNegocioException - En el caso de que el identificador del evento proporcionado no sea 
	 * 								  v&aacute;lido.
	 */
	Tarea consultarTareaPorIdEvento(Long idEventoCita) throws NSJPNegocioException;

}

/**
* Nombre del Programa : ProgramaService.java
* Autor                            : AntonioBV
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 27/01/2012
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
package mx.gob.segob.nsjp.service.programa;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.programas.ActoBuenaConductaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionMedidaAlternaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CursoDTO;
import mx.gob.segob.nsjp.dto.programas.PeriodoAcumulacionPuntosDTO;
import mx.gob.segob.nsjp.dto.programas.ProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.dto.programas.TrabajoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author AntonioBV
 *
 */
public interface AsignacionProgramaService {

	/**
	 * M&eacute;todo que consulta una AsignacionCentroDetencionDTO por id
	 * @return AsignacionCentroDetencionDTO
	 * @throws NSJPNegocioException
	 */
	public AsignacionCentroDetencionDTO consultarAsignacionCentroDetencionPorId(AsignacionCentroDetencionDTO asignacionCentroDetencionDTO)throws NSJPNegocioException;

	/**
	 * M&eacute;todo que consulta una AsignacionMedidaAlternaDTO por id
	 * @return AsignacionMedidaAlternaDTO
	 * @throws NSJPNegocioException
	 */
	public AsignacionMedidaAlternaDTO consultarAsignacionMedidaAlternaPorId(AsignacionMedidaAlternaDTO asignacionMedidaAlternaDTO)throws NSJPNegocioException;	
	
	/**
	 * M&eacute;todo que consulta una AsignacionProgramaDTO por id
	 * @return AsignacionProgramaDTO
	 * @throws NSJPNegocioException
	 */
	public AsignacionProgramaDTO consultarAsignacionProgramaPorId(AsignacionProgramaDTO asignacionProgramaDTO)throws NSJPNegocioException;

	/**
	 * M&eacute;todo que consulta un CursoDTO por id
	 * @return CursoDTO
	 * @throws NSJPNegocioException
	 */
	public CursoDTO consultarCursoPorId(CursoDTO cursoDTO)throws NSJPNegocioException;

	/**
	 * M&eacute;todo que actualiza un CursoDTO
	 * @throws NSJPNegocioException
	 */
	public void actualizarCurso(CursoDTO cursoDTO) throws NSJPNegocioException;	
	
	/**
	 * M&eacute;todo que consulta un ProgramaDTO por id
	 * @return ProgramaDTO
	 * @throws NSJPNegocioException
	 */
	public ProgramaDTO consultarProgramaPorId(ProgramaDTO programaDTO)throws NSJPNegocioException;

	/**
	 * M&eacute;todo que consulta una remision por id
	 * @return Remision
	 * @throws NSJPNegocioException
	 */
	public RemisionDTO consultarRemisionPorId(RemisionDTO remisionDTO) throws NSJPNegocioException;
			
	/**
	 * M&eacute;todo que consulta una Sentencia por filtro
	 * @return Sentencia
	 * @throws NSJPNegocioException
	 */
	public SentenciaDTO consultarSentencia(SentenciaDTO sentenciaDTO) throws NSJPNegocioException;
			
	/**
	 * M&eacute;todo que consulta un Trabajo por id
	 * @return Trabajo
	 * @throws NSJPNegocioException
	 */
	public TrabajoDTO consultarTrabajoPorId(TrabajoDTO trabajoDTO)throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que actualiza un Trabajo
	 * @throws NSJPNegocioException
	 */
	public void actualizarTrabajo(TrabajoDTO trabajoDTO) throws NSJPNegocioException;	
	
	/**
	 * M&eacute;todo que consulta todos las sentencias
	 * @param filtro filtro para discriminar las sentencias
	 * @return Lista de SentenciasDTO
	 * @throws NSJPNegocioException
	 */
	public List<SentenciaDTO> consultarSentencias(SentenciaDTO filtroDTO) throws NSJPNegocioException;
	
	/**
	 * M&ecute;todo que lleva a cabo la persistencia o actualizaci&oacute;n
	 * de una sentencia cuando se han asignado nuevos programas a la misma.
	 * @param sentenciaDTO La sentencia que se va a persistir.
	 */
	public void registrarSentencia(SentenciaDTO sentenciaDTO);
	
	/**
	 * M&ecute;todo que lleva a cabo la persistencia de un programa.
	 * @param programaDTO El programa que se va a persistir.
	 */
	public ProgramaDTO crearPrograma(ProgramaDTO programaDTO);
	
	/**
	 * M&eacute;todo utilizado para poder llevar a cabo la persistencia 
	 * de un curso. 
	 * @param cursoDTO el curso a persistir
	 * @return cursoDTO el curso persistido con el id asociado.
	 */
	public CursoDTO crearCurso(CursoDTO cursoDTO);
	
	/**
	 * M&eacute;todo utilizado para poder llevar a cabo la persistencia 
	 * de una lista de cursos. 
	 * @param cursosDTO la lista de cursos a persistir.
	 */
	public void crearCursos(List<CursoDTO> cursosDTO);
	
	/**
	 * M&eacute;todo utilizado para poder llevar a cabo la persistencia 
	 * de un trabajo.
	 * @param trabajoDTO el trabajo a persistir.
	 * @return trabajoDTO el trabajo con el id asociado.
	 */
	public TrabajoDTO crearTrabajo(TrabajoDTO trabajoDTO);
	
	/**
	 * M&eacute;todo utilizado para poder llevar a cabo la persistencia 
	 * de una lista de trabajos.
	 * @param trabajosDTO la lista de trabajos a persistir.
	 */
	public void crearTrabajos(List<TrabajoDTO> trabajosDTO);
	
	/**
	 * @param actoBuenaConducta
	 * @return
	 */
	public ActoBuenaConductaDTO consultarActoBuenaConductaPorId(ActoBuenaConductaDTO actoBuenaConductaDTO);
	
	/**
	 * @param sentencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ActoBuenaConductaDTO> consultarActosBuenaConductaPorSentencia (SentenciaDTO sentenciaDTO) throws NSJPNegocioException;
	
	/**
	 * @param actoBuenaConductaDTO
	 * @return
	 */
	public ActoBuenaConductaDTO crearActoBuenaConducta(ActoBuenaConductaDTO actoBuenaConductaDTO);
	
	/**
	 * @param periodoAcumulacionPuntos
	 * @return
	 */
	public PeriodoAcumulacionPuntosDTO consultarPeriodoAcumulacionPuntosPorId(PeriodoAcumulacionPuntosDTO periodoAcumulacionPuntosDTO);
	
	/**
	 * @param sentencia
	 * @return
	 */
	public List<PeriodoAcumulacionPuntosDTO> consultarPeriodosAcumulacionPuntosPorSentencia(SentenciaDTO sentenciaDTO) throws NSJPNegocioException;
	
	/**
	 * @param actoBuenaConductaDTO
	 * @return
	 */
	public PeriodoAcumulacionPuntosDTO crearPeriodoAcumulacionPuntos(PeriodoAcumulacionPuntosDTO periodoAcumulacionPuntosDTO);
	
	/**
	 * @param actoBuenaConductaDTO
	 * @return
	 */
	public void actualizarActoBuenaConducta(ActoBuenaConductaDTO actoBuenaConductaDTO);
	
	/**
	 * M&eacute;todo encargado de llevar a cabo la baja f&iacute;sica de los 
	 * actos de buena conducta persistidos en la base de datos
	 * @param actoBuenaConductaDTO
	 */
	public void eliminarActoBuenaConducta(ActoBuenaConductaDTO actoBuenaConductaDTO);
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de todos los 
	 * actos de buena conducta que no han sido acumulados en 
	 * un per&iacute;odo. 
	 * @param sentencia - La sentencia a partir de la cual se
	 * 			consultan los actos de buena conducta.
	 * @return <List>ActoBuenaConducta - Los actos de buena 
	 * 			conducta de la sentencia que no han sido acumulados.
	 */
	public List<ActoBuenaConductaDTO> consultarActosBuenaConductaSinAcumular (SentenciaDTO sentenciaDTO) throws NSJPNegocioException;

	/**
	 * 
	 * @param asignacionCentroDetencionDTO
	 * @throws NSJPNegocioException
	 */

	AsignacionCentroDetencionDTO asignarCentroDetencionaSentencia(AsignacionCentroDetencionDTO asignacionCentroDetencionDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que consulta todos las sentencias con filtro
	 * @param sentenciaDTO filtro de la busqueda
	 * @return Lista de SentenciasDTO
	 * @throws NSJPNegocioException
	 */
	List<SentenciaDTO> consultarSentenciasXEstado(SentenciaDTO sentenciaDTO)
			throws NSJPNegocioException;

	/**
	 * M&eacute;todo que actualiza un centro de detenci&oacute;n
	 * 
	 * @param asignacionCentroDetencionDTO
	 * @throws NSJPNegocioException
	 */
	void actualizarCentroDetencion(
			AsignacionCentroDetencionDTO asignacionCentroDetencionDTO)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n de una
	 * asignaci&oacute;n de programa previamente existente.
	 * 
	 * @param asignacionProgramaDTO
	 *            - DTO con la informaci&oacute;n de la asignaci&oacute;n a
	 *            actualizar.
	 * @throws NSJPNegocioException
	 *             - En el caso de que la asignaci&oacute;n del programa no
	 *             cuente con todos los datos necesarios para llevar a cabo la
	 *             actualizaci&oacuten.
	 */
	public void actualizarAsignacionPrograma(
			AsignacionProgramaDTO asignacionProgramaDTO)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n de los
	 * per&iacute;odos de acumulaci&oacute;n de actos de buena cuyo resumen ha
	 * sido emitido.
	 * 
	 * @param sentenciaDTO
	 *            - La sentencia de la cual se actualizar&aacute;n los
	 *            per&ioacute;odos.
	 * @throws NSJPNegocioException
	 *             - En el caso de que no se pueda llevar a cabo la
	 *             actualizaci&oacute;n
	 */
	public void actualizarResumenPeriodosAcumulacion(SentenciaDTO sentenciaDTO)
			throws NSJPNegocioException;
}

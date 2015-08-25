/**
*
* Nombre del Programa : InvolucradoDAO.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 05/04/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Interface para el DAO de la entidad Involucrado                      
* Programa Dependiente  :N/A                                                      
* Programa Subsecuente :N/A                                                      
* Cond. de ejecucion        :N/A                                                      
* Dias de ejecucion          :N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                       :N/A                                                           
* Compania               :N/A                                                           
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/

package mx.gob.segob.nsjp.dao.involucrado;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.model.Caso;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Valor;

/**
 * 
 * @author CesarAgustin
 * @version 1.0
 */
public interface InvolucradoDAO extends GenericDao<Involucrado, Long> {

	public List<Involucrado> obtenerInvolucradosAll();

	public Expediente obtenerExpediente(Integer i);

	/**
	 * Consulta que regresa los involucrados que estan en el expediente que es recibido como parametro.
	 * @param expedienteId
	 * @return Lista de involucrado encontrados
	 */
	public List<Involucrado> consultarInvolucradosByExpediente(Long expedienteId);

	
	 /**
	  * Consulta de involucrados por calidades asociados a un expediente.
	  * 
	  * @param expedienteId
	  * @param calidades
	  * @return
	  */
	List<Involucrado> obtenerInvolucradosPorExpedienteYCalidades(Long expedienteId, Calidades[] calidades, Boolean esActivo);
	
	 /**
	 * Consulta los folios Interinstitucionales de involucrados por calidades
	 * asociados a un expediente.
	 * 
	 * @param expedienteId
	 * @param calidades
	 * @return
	 */
	List<String> obtenerFolioInterInstInvolucradosPorExpedienteYCalidades(
			Long expedienteId, Calidades[] calidades, Boolean esActivo);
	
	/**
	 * 
	 * @param aliasInvolucrado
	 * @return
	 */
	public List<Involucrado> consultarExpedientesByAlias (String aliasInvolucrado);
	
	
	/**
	 * 
	 * @param aliasInvolucrado
	 * @return
	 */
	public List<Involucrado> consultarExpedientesByAliasLike(String aliasInvolucrado);

	
	/**
	 * realiza la busqueda de personas por nombre y/o apellido paterno y/o apellido materno 
	 * @version 1.1
	 * @author CesarAgustin
	 * @param apellidoMat
	 * @param apellidoPat
	 * @param nombre
	 * @return
	 */
	public List<Involucrado> consultarExpedientesByNombre(FiltroExpedienteDTO filtroExpedienteDTO);

	/**
	 * 
	 * @param idExpediente
	 * @param calidadId
	 * @return
	 */
	public List<Involucrado> obtenerInvByIdExpAndCalidad(Long idExpediente, Long calidadId, Boolean esDetenido);

	/**
	 * Operaci&oacute;n que realiza la funcionalidad de consultar los involucrados en una audiencia.
	 * 
	 * @param calidad: La calidad del involucrado (si no recibe calidad, consulta todas las calidades).
	 * @param audiencia: El identificador de la audiencia
	 * @return Devuelve un listado de involucrados asociados a la audiencia, en caso contrario null.
	 */
	public List<Involucrado> obtenerInvolucradosByAudiencia(Long audienciaId, Long calidadId);
	/**
	 * Busca los involucrados que sean de cierto tipo de calidad y que est&eacute;n
	 * relacionados a cierto numero general de caso
	 * @param numeroCaso N&uacute;mero general de caso donde se encuentran los involucrados a buscar
	 * @param calidades calidades buscadas en los involucrados a obtener
	 * de resultado
	 * @return Lista de involucrados encontrados
	 */
	List<Involucrado> obtenerInvolucradosByCasoYCalidades(String numeroCaso,Calidades []calidades);

	/**
	 * Consulta los involucrados dado una lista de Identificadores, en caso de 
	 * que sea vacia la lista, se consulta nuevamente.  
	 * 
	 * @param idInvolucrados
	 * @return
	 */
	public List<Involucrado> consultarInvolucradosByIds(List<Long> idInvolucrados);
	
	/**
	 * Obtiene el numero de expedientes por mes dentro de un rango de fechas, en el cual el expediente
	 * cuente con algun involucrado con la condicion detenido, solicitado por el ususario.
	 * @param fechaInicio
	 * @param fechaFin
	 * @param condicionDet
	 * @return
	 */
	public List<Object[]> obtenerExpedientesPorCondicionDetencionInvYMes (Date fechaInicio, Date fechaFin, Boolean condicionDet);

	/**
	 * Consulta un involucrado de acuerdo a su propiedad folio de elemento.
	 * @param folioElemento
	 * @return
	 */
	public Involucrado consultarInvolucradoPorFolioElemento(String folioElemento);

	/**
	 * 
	 * @param folioInvolucrado
	 * @return
	 */
	public Involucrado obtenerInvolucradoByFolioElemento(String folioInvolucrado);

	/**
	 * Permite consultar involucrados asociados a una audiencia por calidad
	 * @param idAudiencia
	 * @param calidades
	 * @return
	 */
	public List<Involucrado> consultarInvolucradosDeAudienciaPorCalidad(Long idAudiencia, List<Long> calidades,Boolean involucradosVivos);
	
	/**
	 * Metodo para obtener victimas y denunciantes que son victimas
	 * @param idAudiencia
	 * @return
	 */
	public List<Involucrado> obtenerDenuncianteVictimaSinPaginado(Long idAudiencia,Long calidadId);
	
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de involucrados a trav&eacute;s del folio del elemento
	 * y el caso al cual est&aacute; asociado el involucrado.
	 * @param involucrado - El objeto del cual se va a obtener el folio del elemento a consultar.
	 * @param caso - el objeto del cual se obtiene el numero de caso del cual se va a filtrar la consulta.
	 * @return Involucrado - El involucrado que cumple con los requisitos de la consulta.
	 * @throws NSJPNegocioException - En el caso de que el involucrado no cuente con un folio del elemento 
	 * 								  asociado o el caso no tenga un n&uacute;mero de caso asociado.
	 */
	public Involucrado obtenerInvolucradoPorCasoYFolioElemento(Involucrado involucrado, Caso caso) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para consultar los probables responsables, candidatos para
	 * enviar una solicitud de defensor (aviso de detencion);
	 * 
	 * @param expedienteId
	 * @param calidadId
	 * @param esDetenido
	 * @param noTieneSolicitudDefensor
	 * @param tieneRelDelitoPersona
	 * @return List<Involucrado> - Lista de involucrados que superaron los filtros
	 * @throws NSJPNegocioException
	 */
	public List<Involucrado> consultarProbablesResponsablesParaSolucitudDeDefensor(
			Long expedienteId, Long calidadId, Boolean esDetenido,
			Boolean noTieneSolicitudDefensor, Boolean tieneRelDelitoPersona)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los involucrados de acuerdo al tipo 
	 * de situaci&oacute;n jur&iacute;dica en la que se encuentran, filtrando solamente 
	 * aquellos que no cuentan con una sentencia dictada. 
	 * @param situacionJuridica - situaci&oacute;n jur&iacute;dica en base a la cual se
	 * 							  se van a filtrar los involucrados.
	 * @return List<Involucrado> - Lista con los involucrados que se encuentran en la
	 * 							   situaci&oacute;n jur&iacute;dica pasada como 
	 * 							   par&aacute;metro y que no tienen una sentencia 
	 * 							   asociada.
	 * @throws NSJPNegocioException - En el caso de que los argumentos pasados no sean
	 * 								  suficientes para poder llevar a cabo la consulta.
	 */
	public List<Involucrado> consultarInvolucradosXSituacionSinSentencia(Valor situacionJuridica) throws NSJPNegocioException;

	/**
	 * M&eacute;todo de consulta a BD, para obtener el valor maximo del order de las etapas
	 * de los involcurados asociados a un expediente.
	 * 
	 * @param expedienteId - Identififcador del expediente para la consulta de lso involucrados.
	 * @return El valor m&acute;ximo del orden de las etapas de los involucrados del expediente.
	 */
	Long consultarEtapaMaximaInvolucradosDelExpediente(Long expedienteId);
	
	 /** Consulta que regresa los involucrados que estan en el expediente que es recibido como parametro.
	 * @param expedienteId
	 * @return Lista de involucrado encontrados
	 */
	public List<Involucrado> consultarInvolucradosByExpedientePaginacion(Long expedienteId);

	
}

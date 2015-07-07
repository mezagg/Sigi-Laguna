/**
* Nombre del Programa : SentenciaDAO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 9 Sep 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato de metodos de acceso a datos de la entidad Sentencia
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
package mx.gob.segob.nsjp.dao.sentencia;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.NombreDemografico;
import mx.gob.segob.nsjp.model.Sentencia;

/**
 * Contrato de metodos de acceso a datos de la entidad Sentencia.
 * @author cesarAgustin
 *
 */
public interface SentenciaDAO extends GenericDao<Sentencia, Long> {

	/**
	 * M&eacute;todo que consulta todos las sentencias
	 * @param filtro filtro para discriminar las sentencias
	 * @return Lista de Sentencias
	 * @throws NSJPNegocioException
	 */

	public List<Sentencia> consultarSentencias(Sentencia filtro) throws NSJPNegocioException;	
	
	/**
	 * M&eacute;todo que consulta una Sentencia por filtro
	 * @return Sentencia
	 * @throws NSJPNegocioException
	 */
	public Sentencia consultarSentencia(Sentencia sentencia)throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo encargado de llevar a cabo la consulta de las sentencias que se encuentran en un estado 
	 * espec&iacute;fico. (Se obtiene a trav&eacute;s del expediente asociado a la sentencia).
	 * @param sentencia - Entidad que obtiene el estado sobre el cual se va a filtrar la consulta.
	 * @return List<Sentencia> - Lista de sentencias que cumplen con el criterio del estado 
	 * @throws NSJPNegocioException - En el caso de que no se proporcionen los argumentos suficientes.
	 */
	public List<Sentencia> consultarSentenciasXEstado(Sentencia sentencia) throws NSJPNegocioException;


	/**
	 * M&eacute;todo que consulta un NUS en base al CURP o al Nombre Completo del Involucrado 
	 * @param nombreDemografico
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<Sentencia> consultarNUS(NombreDemografico nombreDemografico, Boolean esPorCURP)throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo encargado de llevar a cabo la consulta de sentencias en base a al n&uacute;mero
	 * de causa y el nus asociado a un sentenciado. 
	 * @param sentencia - Sentencia de la cual se van a obtener los atributos para llevar a cabo 
	 * 					  la consulta 
	 * @return Sentencia - Sentencia con la información que se encuentra persistida dentro de la base 
	 * 					   de datos.
	 * @throws NSJPNegocioException - En el caso de que no se hayan pasado loa par&aacute;metros 
	 * 								  requeridos.
	 */
	public Sentencia consultarSentenciaPorNusYCausa(Sentencia sentencia) throws NSJPNegocioException;
	
	/**
	 * @param sentencia
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<Sentencia> consultarSentenciasJEJ(Sentencia sentencia) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de sentencias en base al identificador del 
	 * n&uacute;mero de expediente pasado como par&aacute;metro dentro de la lista.
	 * @param idsNumExp - List<Long> con los identificadores del n&uacute;mero de expediente 
	 * 					  sobre los cuales se van a filtrar las sentencias.
	 * @return List<Sentencia> - List<Sentencia> con las sentencias que cumplieron con el 
	 * 							  criterio de la consulta.
	 * @throws NSJPNegocioException - En el caso de que la lista de identificadores sea nula 
	 * 								  o se encuentre vac&iacute;a.
	 */
	public List<Sentencia> consultarSentenciasPorIdsNumExp(List<Long> idsNumExp) throws NSJPNegocioException;

}

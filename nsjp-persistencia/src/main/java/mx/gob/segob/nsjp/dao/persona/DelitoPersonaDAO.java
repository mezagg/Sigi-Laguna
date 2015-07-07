/**
* Nombre del Programa : DelitoPersonaDAO.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 24 May 2011
* Marca de cambio        : N/A
* Descripcion General    : Contrato de metodos de accesos a datos de la entidad Delito Persona
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
package mx.gob.segob.nsjp.dao.persona;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Expediente;

/**
 * Contrato de metodos de accesos a datos de la entidad Delito Persona.
 * @version 1.0
 * @author cesarAgutin
 *
 */
public interface DelitoPersonaDAO extends GenericDao<DelitoPersona, Long> {

	/**
	 * 
	 * @param involucradoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<DelitoPersona> consultarDelitoPerByInvolucrado (Long involucradoId);
	
	/**
	 * Metodo que realiza la funcionalidad de consultar los delitos asociados a un individuo por expediente
	 * @param involucradoId
	 * @return List<DelitoPersona> 
	 */
	public List<DelitoPersona> consultarDelitosPorImputado(Long involucradoId, Long expedienteId);

	/**
	 * Metodo que realiza la funcionalidad de consultar los delitos asociados a un individuo probable responsable por expediente
	 * @param involucradoId
	 * @return List<DelitoPersona> 
	 */
	public List<DelitoPersona> consultarDelitosPorImputadoResponsable(Long idInvolucrado,
			Long idExpediente);

	/**
	 * Metodo que realiza la funcionalidad de consultar los delitos asociados a un individuo probable responsable por expediente
	 * y que la victima no sea nula
	 * @param involucradoId
	 * @return List<DelitoPersona> 
	 */
	public List<DelitoPersona> consultarDelitosPorImputadoResponsableConVictima(Long idInvolucrado,
			Long idExpediente);

	
	/**
	 * Servicio que consulta la relación delito-persona por expediente y delito
	 * @param idDelito
	 * @param idExpediente
	 * @return
	 */
	public List<DelitoPersona> consultarVictimaImputadoPorDelito(Long idDelito,
			Long idExpediente);
	
	/**
	 * Consulta los delitospersona por expediente y los ids de delitos, para saber si existen delitos asociados
	 * a un PR de acuerdo al expediente y si estan activos.
	 * 
	 * En caso de que se omita la lista de id de Delitos, se consultan todos los existentes del expediente que 
	 * esten activos.
	 * 
	 * @param idsDelitos
	 * @param idExpediente
	 * @return
	 */
	List<DelitoPersona> consultarDelitosPersonaPorExpedienteIdsDelito(List<Long> idsDelitos,
			Long idExpediente) ;

	/**
	 * Elimina las relaciones para un delito dado que esta por ser eliminado del expediente
	 * @param delPersist
	 */
	public void eliminarRelacionPorDelito(Delito delPersist);
	
	
	/**
	 * Metodo que realiza la funcionalidad de consultar los delitos asociados a un individuo probable responsable 
	 * @param involucradoId
	 * @author Emigdio Hernández
	 * @return List<DelitoPersona> 
	 */
	public List<DelitoPersona> consultarDelitosPorImputadoResponsable(Long idInvolucrado);
	
	/**
	 * Permite actualizar el campo esActivo de un DelitoPersona, es decir permite 
	 * dejar activo o inactivo un Delito Persona
	 * @param delitoPersonaId: Identificador 
	 */
	public void desactivarDelitoPersona(Long delitoPersonaId);
	
	/**
	 * Obtiene los detenidos por delito y mes, dentro de un rango de fechas.
	 * @author cesarAgustin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param catDelito
	 * @return
	 */
	Long obtenerDetenidosPorMesYDelito(Date fechaInicio, Date fechaFin, Long catDelito);

	/**
	 * Consulta que permite determinar si exite la relacion de un delito, con el probable
	 * responsable, la victima y el mismo modo de participacion.
	 * Con el objetivo que no se registre la misma relacion.
	 * 
	 * @param delitoId
	 * @param idProbableResponsable
	 * @param idVictima
	 * @param idFormaParticipacion
	 * @return
	 */
	Boolean existeRelacionProbableResponsableVictimaDelitoFormaParticipacion(Long delitoId, Long idProbableResponsable, Long idVictima, Long idFormaParticipacion) ;

	/**
	 * Consulta las relaciones delito persona por un lista de imputados y el expedienteId
	 * @param idsImputados
	 * @param idExpediente
	 * @return La lista de relaciones delitos persona asociada a los ids de los imputados
	 * @throws NSJPNegocioException 
	 */
	List<DelitoPersona> consultarRelacionesDelitoPersonaPorIdsImputados(List<Long> idsImputados,
			Long idExpediente) throws NSJPNegocioException ;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las relaciones delito persona 
	 * a trav&eacute;s de una lista de identificadores de delitosPersona. 
	 * @param idsDelitoPersona - List<Long> con los identificadores de la 
	 * 							 relaci&oacute;n delitoPersona.
	 * @return List<DelitoPersona> - List<DelitoPersona> con la informaci&oacute;n 
	 * 								 de los delitosPersona registrados en la base de 
	 * 								 datos.
	 * @throws NSJPNegocioException - En el caso de que no se hayan pasado los 
	 * 								  par&aacute;metros necesarios.
	 */
	public List<DelitoPersona> consultarDelitosPersonaPorIds(List<Long> idsDelitoPersona) throws NSJPNegocioException;
	
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de DelitosPersona a trav&eacute;s 
	 * de ciertos criterios.
	 * @param filtro - DelitoPersona con los criterios a partir de los cuales 
	 * 				   se va a realizar la consulta.
	 * @return DelitoPersona - DelitoPersona registrado en la base de datos que cumple 
	 * 						   con los criterios de b&uacute;squeda.
	 * @throws NSJPNegocioException - En el caso de que no se ingresen los parametros
	 * 								  obligatorios.
	 */
	public DelitoPersona consultarDelitoPersonaPorCriterios(DelitoPersona filtro) throws NSJPNegocioException;
	
	
	/**
	 * Consulta todas las relaciones delito persona del expediente por expedienteId
	 * @param expediente
	 * @return La lista de relaciones delitos persona asociadas al expediente
	 */
	List<DelitoPersona> consultarRelacionesDelitoPersonaDelExpediente(
			Expediente expediente) throws NSJPNegocioException;

	/**
	 * Consultar lista de relaciones delito-persona por sus
	 * idsRelacionDelitoPersona
	 * 
	 * @param expediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DelitoPersona> consultarListaRelacionesDelitoPersona(
			List<Long> idsRelsDelPer) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para consultar el &uacute;ltimo folio de la relaci&oacute;n delito persona
	 * @return String - &uacute;ltimo numero de folio de la relaci&oacute;n  
	 */
	public Long obtenerUltimoFolioDelitoPersona();

	/**
	 * M&eacute;todo para consultar una &uacute;nica relaci&oacute;n delito
	 * persona por folio delito persona.
	 * 
	 * @param filtro
	 *            , DelitoPersona folio por el cual consultar&aacute; la
	 *            relaci&oacute;n
	 * @return DelitoPersona, &uacute;nica relaci&oacute;n delito-persona
	 * @throws NSJPNegocioException
	 */
	public DelitoPersona consultarRelacionDelitoPersonaPorFolio(
			DelitoPersona filtro);
}
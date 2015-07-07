/**
* Nombre del Programa : ConsultarDelitoPersonaService.java
* Autor                            : adrian
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 08/07/2011
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
package mx.gob.segob.nsjp.service.delito;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author adrian
 *
 */
public interface ConsultarDelitoPersonaService {

	/**
	 * Servicio que consulta la relacion delito-persona por expediente y probable responsable
	 * @param idInvolucrado
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DelitoPersonaDTO> consultarDelitosVictimaPorImputado(
			Long idInvolucrado, Long idExpediente)throws NSJPNegocioException;
	
	/**
	 * Consulta las relaciones delito persona del expediente. Este servicio hace
	 * uso del servicio de consultarDelitosVictimaPorImputado PRECONDICION: El
	 * expediente debe de tener los involucrados asociados al expediente.
	 * 
	 * @param expedienteCompleto
	 *            Expediente con la informacion de los involucrados asociados al
	 *            expediente.
	 * @return Lista de las relaciones delito persona del expediente
	 */
	List<DelitoPersonaDTO> consultarDelitosVictimaImputadoPorExpediente(
			ExpedienteDTO expedienteCompleto) throws NSJPNegocioException;

	/**
	 * Servicio que consulta la relación delito-persona por expediente y delito
	 * 
	 * @param idDelito (Opcional)
	 * @param idExpediente (Obligatorio)
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DelitoPersonaDTO> consultarVictimaImputadoPorDelito(Long idDelito,
			Long idExpediente)throws NSJPNegocioException;
	
	/**
	 * Servicio que permite la validar si existe la relación de un delito con una persona, dentro
	 * de la entidad delitoPersona. 
	 * 
	 * Es utilizado para la eliminación del la lista de los delitos asociados al expediente.
	 * 
	 * 
	 * @param idDelito
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean existeRelacionPersonaDelitoExpediente(
			Long idDelito, Long idExpediente) throws NSJPNegocioException;
	
	/**
	 * Consulta los delitospersona por expediente y los ids de delitos, para saber si existen delitos asociados
	 * a un PR de acuerdo al expediente y si dichas relaciones estan activas.
	 * 
	 * En caso de que se omita la lista de id de Delitos, se consultan todos los existentes del expediente que 
	 * esten activos.
	 * 
	 * @param idsDelitos
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DelitoPersonaDTO> consultarDelitosPersonaPorExpedienteIdsDelito(
			List<Long> idsDelitos, Long idExpediente) throws NSJPNegocioException;
	
			
	/**
	 * Consulta los delitos cometidos por cierto involucrado sin importar
	 * el expediente
	 * @param idInvolucrado Identificador del involucrado a buscar
	 * @author Emigdio Hernández
	 * @return Lista de delitos cometidos por el involucrado
	 */
	List<DelitoPersonaDTO> consultarDelitosVictimaPorImputado(Long idInvolucrado);
	
	/**
	 * Consulta los delitos por persona dado un identificador de expediente
	 * @param idExpediente
	 * @return List<DelitoPersonaDTO>
	 * @throws NSJPNegocioException
	 */
	public List<DelitoPersonaDTO> consultarVictimaImputadoPorExpediente(Long idExpediente) throws NSJPNegocioException;
	
	/**
	 * Permite desactivar una relacion Delito Persona
	 * @param delitoPersonaId
	 * @throws NSJPNegocioException
	 */
	public void desactivarDelitoPersona(Long delitoPersonaId) throws NSJPNegocioException;
	
	/**
	 * Servicio que se encarga de la eliminación física del delito.
	 * 
	 * @param delitoPersonaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean eliminarDelitoPersona(Long delitoPersonaId)throws NSJPNegocioException;
	
	/**
	 * Consulta un DelitoPersona en base a su identificador
	 * @param idDelitoPersona
	 * @return
	 * @throws NSJPNegocioException
	 */
	public DelitoPersonaDTO consultarDelitoPersonaPorId(Long idDelitoPersona) throws NSJPNegocioException;
	
	/**
	 * Consulta las relaciones delito persona de los involucrados con calidad de probable responsable (fisica o moral)
	 * y el expedienteId 
	 * @param audienciaDto
	 * @return lista de relaciones delito persona de los ivolucrados con calidad de probable responsable 
	 * @throws NSJPNegocioException 
	 */
	List<DelitoPersonaDTO> consultarRelacionesDelitoPersonaPorAudiencia(AudienciaDTO audienciaDto) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las relaciones delito persona 
	 * a trav&eacute;s de una lista de identificadores de delitosPersona. 
	 * @param idsDelitoPersona - List<Long> con los identificadores de la 
	 * 							 relaci&oacute;n delitoPersona.
	 * @return List<DelitoPersonaDTO> - List<DelitoPersonaDTO> con la informaci&oacute;n 
	 * 									de los delitosPersona registrados en la base de 
	 * 									datos.
	 * @throws NSJPNegocioException - En el caso de que no se hayan pasado los 
	 * 								  par&aacute;metros necesarios.
	 */
	public List<DelitoPersonaDTO> consultarDelitosPersonaPorIds(List<Long> idsDelitoPersona) throws NSJPNegocioException;
	
	
	/**
	 * Consulta las relaciones delitos persona del expediente
	 * @param expedienteDto, expedienteId (requerido)
	 * @return List<DelitoPersonaDTO>, Lista de relaciones delito persona del expediente
	 * @throws NSJPNegocioException
	 */
	List<DelitoPersonaDTO> consultarRelacionesDelitoPersonaDelExpediente(ExpedienteDTO expedienteDto) throws NSJPNegocioException;
	
	
	/**
	 * Consulta una &uacute;nica relaci&oacute;n delito-persona, por el folio de la relaci&oacute;n
	 * @param folioDelitoPersona, folio para buscar(requerido)
	 * @return DelitoPersonaDTO, delitoPersona encontrado, null,s en caso contrario
	 * @throws NSJPNegocioException
	 */
	public DelitoPersonaDTO consultarUnicaRelacionDelitoPersonaPorFolio(DelitoPersonaDTO delitoPersonaDTO) throws NSJPNegocioException;
	
}

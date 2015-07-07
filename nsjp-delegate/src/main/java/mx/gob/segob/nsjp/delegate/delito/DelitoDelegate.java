/**
 * Nombre del Programa : DelitoDelegate.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 31 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del delegate para Delito y conectar sus servicios con la vista
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
package mx.gob.segob.nsjp.delegate.delito;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;

/**
 * Contrato del delegate para Delito y conectar sus servicios con la vista.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
public interface DelitoDelegate {

	/**
	 * Guarda (Registra, Modifica, Elimina)el(los) delitos en el expediente. Hace una asociacion en la base
	 * de datos de cada delito con el expediente.
	 * 
	 * @throws NSJPNegocioException
	 *             En caso que alguno se cumpla alguna de las siguiente
	 *             condiciones:
	 *             <ol>
	 *             <li> {@code delitosDto == null}
	 *             <li> {@code expedienteDto == null}
	 *             </ol>
	 *             Tambien se lanza la esta excepcion si alguno de los elementos
	 *             de los delitosDto cumple alguna de las siguientes
	 *             condiciones:
	 *             <ol>
	 *             <li> {@code delitoDto.esProbable == null}
	 *             <li> {@code delitoDto.esPrincipal == null}
	 *             </ol>
	 */
	void guardarDelito(List<DelitoDTO> delitosDto, ExpedienteDTO expedienteDto)
			throws NSJPNegocioException;

	/**
	 * Valida si existe alg�n delito grave dentro de un listado de delitos.
	 * 
	 * @param delitos
	 *            Delitos a verificar.
	 * @return {@code true} en caso que alguno de los delitos sea grave,
	 *         {@code false} en caso contrario.
	 * @throws NSJPNegocioException
	 *             En caso que la lista de delitos sea nula.
	 */
	boolean validarExisteDelitoGrave(List<DelitoDTO> delitos)
			throws NSJPNegocioException;

	/**
	 * 
	 * @param delitosDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<DelitoDTO> registrarDelitosDenuncia(List<DelitoDTO> delitosDTO)
			throws NSJPNegocioException;

	/**
	 * Consulta los delitos registrados para el expediente enviado con parametro
	 * 
	 * @param expedienteDTO
	 *            identificador del expediente
	 * @return lista de los delitos registrados para el expediente
	 * @throws NSJPNegocioException
	 */
	public List<DelitoDTO> consultarDelitosExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException;

	/**
	 * Realiza la actualizacion de la informacion de los delitos enviados como
	 * parametros.
	 * 
	 * @param delitosDTO
	 * @throws NSJPNegocioException
	 */
	public void modificarDelito(List<DelitoDTO> delitosDTO)
			throws NSJPNegocioException;

	/**
	 * Consulta los delitos de un imputado asociado a un expediente
	 * 
	 * @param idInvolucrado
	 * @param idExpediente
	 * @throws NSJPNegocioException
	 */
	public List<DelitoDTO> consultarDelitosPorImputado(Long idInvolucrado,
			Long idExpediente) throws NSJPNegocioException;

	/**
	 * Servicio que se encarga de verificar si existe, en BD, la relaci�n
	 * -Delito
	 * -Probable Responsable
	 * -V�ctima
	 * @param delitoId
	 * @param idProbableResponsable
	 * @param idVictima
	 * @param idFormaParticipacion
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean existeRelacionProbableResponsableVictimaDelitoFormaParticipacion(Long delitoId,
			Long idProbableResponsable, Long idVictima, Long idFormaParticipacion)
			throws NSJPNegocioException;
	
	/**
	 * Realiza la asociaci�n (creaci�n y modificaci�n) de un delito con el probable responsable y la v�ctima que afecta.
	 * Puede no existir v�ctima a registrar, como se puede no indicar si es "bien Tutelado"
	 * 
	 * CREACION: asociacionID=null
	 * MODIFICACI�N: asociacionID=DelitoPersona_id
	 * 
	 * @param delito: Identificador del delito
	 * @param responsable: Identificador del involucrado como probable responsable
	 * @param victima: Identificador del involucrado como v�ctima
	 * @param formaParticipacion:
	 * @param bienTutelado:
	 * @throws NSJPNegocioException
	 */
	public Long asociarDelitoResponsableVictima(Long asociacionID,DelitoDTO delito,
			InvolucradoDTO responsable, InvolucradoDTO victima,
			Long formaParticipacion, Long bienTutelado, Long idClasificacion, 
			Long idLugar, Long idModalidad, Long idModus, Long idCausa)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para registrar las formas y grados de participaci&oacute;n,
	 * as&iacute; como los modus, modalidad y lugar; de una o varias relaciones delito-persona
	 * @param idsRelsDelPersona
	 * @param delitoPersonaDtoUpdate
	 * @throws NSJPNegocioException
	 */
	public void establecerModosGradosYFormasDeParticipacion(
			List<Long> idsRelsDelPersona, DelitoPersonaDTO delitoPersonaDtoUpdate) throws NSJPNegocioException;
	
	/**
	 * Servicio que consulta la relacion delito-persona por expediente y probable responsable
	 * @param idInvolucrado
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<DelitoPersonaDTO> consultarDelitosVictimaPorImputado(Long idInvolucrado, Long idExpediente)throws NSJPNegocioException;
	
	/**
	 * Servicio que consulta la relaci�n delito-persona por expediente y delito
	 * 
	 * @param idDelito (Opcional)
	 * @param idExpediente (Obligatorio)
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<DelitoPersonaDTO> consultarVictimaImputadoPorDelito(Long idDelito, Long idExpediente)throws NSJPNegocioException;
	
	/**
	 * Servicio que permite la validar si existe la relaci�n de un delito con una persona, dentro
	 * de la entidad delitoPersona. 
	 * 
	 * Es utilizado para la eliminaci�n del la lista de los delitos asociados al expediente.
	 * 
	 * 
	 * @param idDelito
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean existeRelacionPersonaDelitoExpediente(Long idDelito, Long idExpediente) throws NSJPNegocioException;
	
	/**
	 * Consulta los delitos cometidos por cierto involucrado sin importar
	 * el expediente
	 * @param idInvolucrado Identificador del involucrado a buscar
	 * @author Emigdio Hern�ndez
	 * @return Lista de delitos cometidos por el involucrado
	 */
	List<DelitoPersonaDTO> consultarDelitosVictimaPorImputado(Long idInvolucrado);
	
	/**
	 * Servicio que permite consultar la relacion de probables responsables
	 * @author ricardog
	 */
	public List<DelitoPersonaDTO> consultarDelitosImputadoPorExpediente(Long idExpediente) throws NSJPNegocioException;

	/**
	 * Obtiene los delitos de acuerdo al tipo y mes segun el rango de fechas
	 * @author cesarAgustin
	 * @param fechaInicial
	 * @param fechaFin
	 * @param tipo
	 * 			<li>true: grave<li>
	 * 			<li>false: false<li>
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<Object[]> delitosTipoPorMes(Date fechaInicial, Date fechaFin, boolean tipo) throws NSJPNegocioException;
	
	/**
	 * Permite desactivar una relacion Delito Persona
	 * @param delitoPersonaId
	 * @throws NSJPNegocioException
	 */
	public void desactivarDelitoPersona(Long delitoPersonaId) throws NSJPNegocioException;

	/**
	 *  Obtiene el numero de registros de un delito existentes por mes dentro de un rango de fechas.
	 * @author cesarAgustin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param catDelito
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<Object[]> obtenerDelitoPorMes(Date fechaInicio, Date fechaFin,
			Long catDelito) throws NSJPNegocioException;

	/**
	 * Obtiene los detenidos por delito y mes, dentro de un rango de fechas
	 * @author cesarAgustin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param long1
	 * @throws NSJPNegocioException
	 * @return
	 */
	Long obtenerDetenidosPorMesYDelito(Date fechaInicio,
			Date fechaFin, Long catDelito) throws NSJPNegocioException;
	
	/**
	 * Consulta si la media atimetica de los delitos que pertenecen a un expediente
	 * @param inputExpDto
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean consultarMediaAritmeticaDelitosExpediente(ExpedienteDTO inputExpDto) throws NSJPNegocioException;

	/**
	 * Servicio que se encarga de la eliminación física del delito.
	 * 
	 * @param delitoPersonaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean eliminarDelitoPersona(Long delitoPersonaId)throws NSJPNegocioException;
	
	
	DelitoDTO obtenerDelitoPrincipal(List<DelitoDTO> lstDelitos) throws NSJPNegocioException;
	
	/**
	 * Permite consultar un Delito Persona en base a su identificador
	 * @param idDelitoPersona
	 * @return
	 * @throws NSJPNegocioException
	 */
	public DelitoPersonaDTO consultarDelitoPersonaPorId(Long idDelitoPersona) throws NSJPNegocioException;

	/**
	 * Consulta las relaciones delito persona, de los involucrados con calidad
	 * de probable responsable (fisica o moral) asociadas a la audiencia y al
	 * expediente que recibe como parametro
	 * 
	 * @param audienciaDto
	 * @return Lista de relaciones delito persona
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
	 * Realiza la asociaci&oacuten, del los probables responsables con los
	 * delitos y las victimas (sin formas de participacion), si existe una
	 * relacion delito persona repetida, no se guardar&aacute; dos veces
	 * 
	 * @param idsProbResponsables
	 * @param idsVictimas
	 * @param delito
	 * @return
	 * @throws NSJPNegocioException
	 */
	public void asociarDelitosConIvolucrados(List<Long> idsProbResponsables,
			List<Long> idsVictimas, Long delitoId) throws NSJPNegocioException;
	
	/**
	 * Consulta la lista de relaciones delito persona por expedienteId
	 * @param expDtoInput
	 * @return Lista de relaciones delito persona asociadas al expediente
	 * @throws NSJPNegocioException
	 */
	List<DelitoPersonaDTO> consultarRelacionesDelitoPersonaDelExpediente(
			ExpedienteDTO expDtoInput) throws NSJPNegocioException;
}

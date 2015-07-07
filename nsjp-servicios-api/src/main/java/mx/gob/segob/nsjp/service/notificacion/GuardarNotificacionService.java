/**
 * 
 */
package mx.gob.segob.nsjp.service.notificacion;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;

/**
 * @author adrian
 *
 */
public interface GuardarNotificacionService {

	/**
	 * Operaci&oacutre;n que realiza la funcionalidad de guardar una
	 * notifcaci&oacute;n.
	 * 
	 * @param notificacionDTO
	 *            : objeto a guardar. Hereda de Documento
	 * @param audienciaDTO
	 *            : idAudiencia
	 * @param involucradoDTO
	 *            : idInvolucradoDestinatario
	 * @return Notificaci&oacute;n Id
	 * @throws NSJPNegocioException
	 */
	Long guardarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, InvolucradoDTO involucradoDTO)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para registrar una audiencia en la agenda de un funcionario
	 * tanto externo, como interno.
	 * 
	 * @param funcionarioDTO, funcionario al que se le actualizar&aacute; la agenda
	 * @param audienciaDTO, audiencia que ser&aacute; agendada
	 * @return verdadero en caso de que se haya podido regustrar, false en caso
	 *         contrario
	 * @throws NSJPNegocioException
	 */
	Boolean registrarAudienciaEnAgendaDeFuncionario(FuncionarioDTO funcionarioDTO,
			AudienciaDTO audienciaDTO) throws NSJPNegocioException;

	/**
	 * M&eacute;todo para guardar una notificaci&oacute;n a un funcionario
	 * 
	 * @param notificacionDTO
	 *            , notificacion con valores
	 * @param audienciaDTO
	 *            , audiencia a la cual se le generar&aacute; la
	 *            notificaci&oacute;n
	 * @param funcionarioDTO
	 *            , Funcionario al que se le generar&aacute; la
	 *            notificaci&oacute;n
	 * @return Long, Id de la notificaci&oacute;n creada
	 * @throws NSJPNegocioException
	 */
	Long guardarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, FuncionarioDTO funcionario)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para guardar una notificaci&oacute;n a un funcionario
	 * externo
	 * 
	 * @param notificacionDTO
	 *            , notificacion con valores
	 * @param audienciaDTO
	 *            , audiencia a la cual se le generar&aacute; la
	 *            notificaci&oacute;n
	 * @param funcionarioExternoDTO
	 *            , Funcionario eterno al que se le generar&aacute; la
	 *            notificaci&oacute;n
	 * @return Long, Id de la notificaci&oacute;n creada
	 * @throws NSJPNegocioException
	 */
	Long guardarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO,
			FuncionarioExternoDTO funcionarioExternoDTO)
			throws NSJPNegocioException;
	
	
	/**
	 *Permite actualizar los datos de una notificacion(generico) 
	 * @param notificacionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	void actualizarNotificacion(NotificacionDTO notificacionDTO)throws NSJPNegocioException;
	
	
	/**
	 * M&eacute;todo para enviar la notificaci&oacute;n una vez generada
	 * previamente, si el funcionario es local, se guardara en su agenda
	 * internamente, si el funcionario es externo, se usara un WS para buscar al
	 * funcionario por su claveFuncionario y agendarle el evento
	 * 
	 * @param notificacionDTO
	 *            , id de la notificacion generada
	 * @param audienciaDTO
	 *            , id de la audiencia que se esta notificando
	 * @param funcionarioDTO
	 *            , funcionario con su clave funcionario y su institucion
	 * 
	 * @return verdadero si logr&oacute; guardar el evento en la agenda del
	 *         funcionario, falso en otro caso
	 * 
	 * @throws NSJPNegocioException
	 */
	Boolean enviarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, FuncionarioDTO funcionarioDTO)
			throws NSJPNegocioException;

	/**
	 * 
	 * Servicio que permite guardar (enviar) la notificación a la misma institución.
	 * 
	 * @param expedienteDTO
	 * @param documentoDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long guardarYEnviarNotificacionAMismaInstitucion(ExpedienteDTO expedienteDTO, DocumentoDTO documentoDTO) throws NSJPNegocioException ;
}

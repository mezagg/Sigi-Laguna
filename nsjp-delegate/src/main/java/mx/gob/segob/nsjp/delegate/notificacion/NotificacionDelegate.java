/**
 * Nombre del Programa : NotificacionDelegate.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 18-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.delegate.notificacion;


import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.almacen.AlmacenDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoHechoDelictivoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;

/**
 * Contrato del delegate para los metodos de comunicacion de Notificacion
 * entre la vista y los servicios.
 * @version 1.0
 * @author Jacob Lobaco
 */
public interface NotificacionDelegate {

    /**
     * Consulta las notificaciones del funcionario asociado a un usuario que
     * esten en un estatus es especifico.
     * @param usuarioDto El usuario del que se buscaran las notificaciones
     * asociadas al funcionario.
     * @param estatusDto El estatus de las notificaciones que se estan
     * buscando.
     * @return Una lista de las notificaciones asociadas a la busqueda. La
     * lista vacia en caso que no existan notificaciones del usuario en el
     * estatus requerido.
     * @throws NSJPNegocioException En caso que se cumpla cualquiera de las
     * siguientes condiciones:
     * <ol>
     * <li> usuarioDto == null
     * <li> usuarioDto.getFuncionario()
     * <li> usuarioDto.getFuncionario()
     * <li> estatusDto == null
     * </ol>
     */
    List<NotificacionDTO> consultarNotificacionesXUsuario(FuncionarioDTO funcionarioDto,
            ValorDTO estatusDto, int pagina, int numeroDeRegistros,
            String campoOrden, int direccionOrden) throws NSJPNegocioException;

    long consultarNumeroTotalDeNotificacionesXUsuario(FuncionarioDTO funcionarioDto,
            ValorDTO estatusDto) throws NSJPNegocioException;

    NotificacionDTO consultaNotificacion(NotificacionDTO consulta) throws NSJPNegocioException;

    NotificacionDTO consultarUltimaNotificacionPorAnio(FuncionarioDTO funcionario)
            throws NSJPNegocioException;

    /**
     * Consulta una lista de notificaciones asociadas a un almacen, un tipo de
     * movimiento y un estado de las notificaciones. La informacion consultada:
     * <ol>
     * <li> - Folio de Cadena de Custodia
     * <li> - Autorizado por
     * <li> - Autorizado por
     * <li> - Fecha de Inicio de Préstamo
     * <li> - Fecha de Fin de Préstamo
     * <li> - Nombre de persona autorizada
     * <li> - Tipo de Identificación
     * <li> - El Motivo de Notificación ?¿?¿?
     * </ol>
     * @param almacenDto
     * @param tipoMovimiento
     * @param estadoNotificacion
     * @return
     * @throws NSJPNegocioException
     */
    List<NotificacionDTO> consultarNotificacionesAlmacen(AlmacenDTO almacenDto,
            long tipoMovimiento, long estadoNotificacion) throws NSJPNegocioException;

    /**
     * Actualiza el estado  de una notificación.
     * @param notificacionDto La notificación que será actualizada.
     * @param nuevoEstado El estatus de la notificación a actualizar.
     * @throws NSJPNegocioException En caso que alguno de los parametros sea
     * null o que el id de la notificacion sea null.
     */
    void actualizarEstatusNotificacion(NotificacionDTO notificacionDto,
            ValorDTO nuevoEstado) throws NSJPNegocioException;

    /**
     * Consulta la información concerniente a un aviso de designacion de acuerdo al identificador
     * idAviso
     * @param idAviso
     * @return
     * @throws NSJPNegocioException
     */
    public AvisoDesignacionDTO consultarAvisoDesignacion(Long idAviso) throws NSJPNegocioException;
    
    /**
     * Actualiza el estatus del aviso de designacion a Atendido y asigna
     * el expediente al defensor al que fue asignado el aviso 
     * @param idAvisoDesignacion
     */
    public void cerrarAvisoDesignacion(Long idAvisoDesignacion) throws NSJPNegocioException;
    
	/**
	 * Consulta los avisos de Designacion por :
	 * 
	 * @param estadoNotificacion
	 * @param funcionarioDTO - Clave del Funcionario
	 * @param tipoSolicitud
	 * @param distritoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<AvisoDesignacionDTO> consultarAvisosDesignacion(
			EstatusNotificacion estadoNotificacion,
			FuncionarioDTO funcionarioDTO, TiposSolicitudes tipoSolicitud,
			Long distritoId) throws NSJPNegocioException;
	
	/**
	 * Registra un nuevo aviso de designación.
	 * @param designacion
	 * @return
	 * @throws NSJPNegocioException
	 */
	@Deprecated
	public AvisoDesignacionDTO registrarAvisoDesignacion (AvisoDesignacionDTO designacion) throws NSJPNegocioException;

	
	/**
	 * Se crea o actualiza la asignaci&oacute;n de la solicitud de asesr&iacute;a legal. 
	 * 
	 * @param avisoDesignacionDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	AvisoDesignacionDTO designarAbogadoDefensorAsesoria(
			AvisoDesignacionDTO avisoDesignacionDTO)
			throws NSJPNegocioException;
	
	/**
	 * Asocia un abogado defensor al aviso de Designación para que lo atienda.
	 * @param designacion
	 * @throws NSJPNegocioException
	 */
	void designarAbogadoDefensor(AvisoDesignacionDTO designacion,
			Boolean cambiarEstatusExpediente, Boolean esIterativo)
			throws NSJPNegocioException;
	
	/**
	 * Reasigna un abogado defensor a un expediente, teniendo en cuenta las siguientes reglas:
	 * 1) Si el expediente cuenta con una solicitud de carpeta de investigacion, muestra el mensaje
	 * 	indicando que no se puede realizar la reasignacion, hasta que se cierre la solicitud
	 * 
	 * 2) En caso de que no cuente con solicitud de carpeta de investigacion en estatus abierta o en proceso
	 * 	se cambia la clave funcionario, por la que se trae como parametro, ademas de actualizar los
	 *  avisos de designacion.
	 *  
	 * @param avisoDesignacion
	 * @throws NSJPNegocioException
	 */
	void reasignarAbogadoDefensorExpediente(AvisoDesignacionDTO avisoDesignacion) throws NSJPNegocioException;
	
	
	/**
	 * Operación que realiza la funcionalidad de guardar una notifcación.
	 * @param notificacionDTO: objeto a guardar. Hereda de Documento
	 * @param audienciaDTO: idAudiencia
	 * @param funcionario: idFuncionarioDestinatario
	 * @return
	 * @throws NSJPNegocioException
	 */
    Long guardarNotificacion(NotificacionDTO notificacionDTO, AudienciaDTO audienciaDTO, InvolucradoDTO involucradoDTO)throws NSJPNegocioException;

    
    /**
     * Perimite actualizar todos los datos de la notificacion,(generico) 
     * @param notificacionDTO
     * @throws NSJPNegocioException
     */
    void actualizarNotificacion(NotificacionDTO notificacionDTO)throws NSJPNegocioException;
    
    
	/**
	 * Operación que realiza la funcionalidad de guardar una notifcación.
	 * @param notificacionDTO: objeto a guardar. Hereda de Documento
	 * @param audienciaDTO: idAudiencia
	 * @param involucradoDTO: idInvolucradoDestinatario
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long guardarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, FuncionarioDTO funcionario) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para guardar una notificaci&oacute;n a un funcionario externo
	 * @param notificacionDTO, notificacion con valores
	 * @param audienciaDTO,	audiencia a la cual se le generar&aacute; la notificaci&oacute;n
	 * @param funcionarioExternoDTO, Funcionario eterno al que se le generar&aacute; la notificaci&oacute;n
	 * @return Long, Id de la notificaci&oacute;n creada
	 * @throws NSJPNegocioException
	 */
	Long guardarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, FuncionarioExternoDTO funcionarioExternoDTO) throws NSJPNegocioException;
    
    /**
     * Registra un nuevo aviso de designación para una reasignación de defensro para el expediente input
     * @param input
     * @return
     * @throws NSJPNegocioException
     */
	AvisoDesignacionDTO registrarAvisoDesignacionPorReasignacionDefensor(
			ExpedienteDTO input) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para enviar la notificaci&oacute;n una vez generada
	 * previamente, si el funcionario es local, se guardara en su agenda
	 * internamente, si el funcionario es externo, se usara un WS para buscar al
	 * funcionario por su claveFuncionario y agendar el evento
	 * 
	 * @param notificacionDTO
	 *            , id de la notificacion generada
	 * @param audienciaDTO
	 *            , id de la audiencia que se esta notificando
	 * @param funcionarioDTO
	 *            , funcionario con su clave funcionario y su institucion
	 * 
	 * @return verdadero si logro programar la audiencia en la agenda del
	 *         funcionario, falso en caso contrario
	 * 
	 * @throws NSJPNegocioException
	 */
	Boolean enviarNotificacion(NotificacionDTO notificacionDTO,
			AudienciaDTO audienciaDTO, FuncionarioDTO funcionarioDTO)
			throws NSJPNegocioException;
	
	/**
	 * Registra un Aviso Hecho Delictivo
	 * @author cesarAgustin
	 * @param avisoHechoDelictivoDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AvisoHechoDelictivoDTO ingresarAvisoHechoDeictivoSSP(AvisoHechoDelictivoDTO avisoHechoDelictivoDTO) throws NSJPNegocioException;
	
	/**
	 * Operación que permite consultar el detalle de un aviso de hecho delictivo 
	 * @param avisoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public AvisoHechoDelictivoDTO consultarAvisoHechoXId(Long avisoId)throws NSJPNegocioException;

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
	
	/**
	 * 
	 * Servicio que permite cerrar un aviso de hecho delictivo.
	 * 
	 * @param avisoId
	 * @param MotivoRechazo
	 * @return
	 * @throws NSJPNegocioException
	 */
	public void asignarMotivoRechazoHD(Long avisoId, Long motivoRechazo)throws NSJPNegocioException;
	/**
	 * Atiende un aviso de hecho delictivo generando un número de expediente para el expediente al que está relacionado el aviso de hecho delictivo.
	 * @param input Requeridos: documentoId y usuario
	 * @throws NSJPNegocioException
	 */
	public void atenderAvisoHechoDelictivo(AvisoHechoDelictivoDTO input) throws NSJPNegocioException;
	
	/**
	 * Recupera un AvisoHechoDelictivo a partir del id del expediente 
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	AvisoHechoDelictivoDTO obtenerAvisoPorIdExpediente(Long idExpediente) throws NSJPNegocioException;
	
    /**
     * envía el aviso a procuraduria.
     * 
     * @param avisoDTO requerido <b>documentoId</b>
     * @throws NSJPNegocioException
     */
    public void enviarAvisoHDelictivo(AvisoHechoDelictivoDTO avisoDTO)
            throws NSJPNegocioException;
    
	/**
	 * Permite consultar las notificaciones ya sea de un funcionario o de un
	 * Involucrado o de un funcionario externo asociados a una audiencia
	 * 
	 * @param idAudiencia, id de la audiencia que se desea consultar
	 * @param idPersona, id de la persona(funcionario,funcionario externo o involucrado)
	 * @param esFuncionario, indica si es funcionario o involucrado
	 * @param esFuncionarioExterno, indica si es funcionario externo
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<NotificacionDTO> consultaNotificaciones(Long idAudiencia,
			Long idPersona, Boolean esFuncionario, Boolean esFuncionarioExterno)
			throws NSJPNegocioException;
}

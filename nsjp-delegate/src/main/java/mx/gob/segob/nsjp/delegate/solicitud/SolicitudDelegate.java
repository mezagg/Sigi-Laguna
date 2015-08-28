/**
 * Nombre del Programa : SolicitudDelegate.java
 * Autor                            : cesarAgustin
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 17 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del delegate para los metodos de comunicacion de Solicitud entre la vista y los servicios
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
package mx.gob.segob.nsjp.delegate.solicitud;

import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudPericialDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudTranscripcionAudienciaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Contrato del delegate para los metodos de comunicacion de Solicitud entre la
 * vista y los servicios.
 * 
 * @version 1.0
 * @author cesarAgustin
 * 
 */
public interface SolicitudDelegate {
	
    /**
     * Consulta todas las solicitudes de un expediente.
     * 
     * @param filtro
     *            Obligaorio <b>numeroExpedienteId</b>.
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesPorExpediente(ExpedienteDTO filtro)
            throws NSJPNegocioException;

    /**
     * Consultar el detalle de una solicitud por n�mero de documento.
     * 
     * @param sol
     *            Obligaorio <b>identificador de la solicitud</b>.
     * @return
     * @throws NSJPNegocioException
     */
    SolicitudDTO obtenerDetalleSolicitud(SolicitudDTO sol)
            throws NSJPNegocioException;

    /**
     * consultar las solicitudes de defensoria no atendidas
     * 
     * @return List<SolicitudDefensorDTO>
     * @throws NSJPNegocioException
     */
    List<SolicitudDefensorDTO> obtenerSolicitudesDefensoriaPorEstatus(
            EstatusSolicitud estatusSolc, Instituciones institucion)
            throws NSJPNegocioException;

    /**
     * M&eacute;todo para consultar solicitudes por filtro
     * 
     * @param SolicitudDTO solFiltro (seteando los valores que se aplicar&aacute;n en el filtro)
     * @param esAsociadaNumeroExpediente (Si la solicitud se asocia al expediente)
     * @param distritoId identificador del Distrito para aplicar el filtro sobre el numero de expediente
     * @return List<SolicitudDTO> (Lista de solicitudes que superaron el filtro)
     * @throws NSJPNegocioException
     */
	List<SolicitudDTO> consultarSolicitudesPorFiltro(SolicitudDTO solFiltro,
			Boolean esAsociadaNumeroExpediente, Long distritoId)
			throws NSJPNegocioException;
    
    /**
     * consultar las solicitudes de asesoria de defensor no atendidas
     * 
     * @return List<SolicitudDefensorDTO>
     * @throws NSJPNegocioException
     */
    List<SolicitudDefensorDTO> obtenerSolicitudesAsesoriaDefensoriaPorEstatus(
            EstatusSolicitud estatusSolc, Instituciones institucion)
            throws NSJPNegocioException;

    /**
     * Turna la solicitud de defensoria ciudadana creada al coodinador de
     * defensoria
     * 
     * @param solicitudDefensorDTO
     * @throws NSJPNegocioException
     */
    void turnarSolicitudDefensoriaCiudadanaACoordinar(
            SolicitudDefensorDTO solicitudDefensorDTO)
            throws NSJPNegocioException;

    /**
     * Crea la solicitud de defensor a partir de un expediente si es el caso
     * creara uno nuevo.
     */
    @Deprecated
    SolicitudDefensorDTO generarAcuseAtencion(ExpedienteDTO expedienteDto)
            throws NSJPNegocioException;

    /**
	 * Servicio que se encarga de crear:
	 * 1) Expediente con:
	 * 		-Etapa:		Asesoria
	 * 		-Area:		Atenci&oacuete;n temprana penal
	 * 		-Estatus:	Abierto
	 * 		-Caso: 		null
	 * 		-CatEtapa	null
	 * 
	 * 2) Solicitud con:
	 * 		-Estaus:			Abierta
	 * 		-Folio:				Generado
	 * 		-Tipo Solicitud:	ASESORIA_DEFENSORIA
	 * 		-Forma:				SOLICITUD_DE_ASESORIA_LEGAL
	 * 		-Tipo Documento: 	SOLICITUD_DE_ASESORIA_LEGAL
	 * 
	 * Para el registro de una asesoria legal
	 * 
	 * Queda documentado el c&oacute;digo para generar una actividad y asociarse con el
	 * Expediente
	 * 
	 * @param expedienteDTO 	Con los datos de: 
	 * 							-Usuario.
	 * 							-Funcionario
	 * 							-Clave Funcionario
	 * 							-Clave del Distrito (del Discriminate)
	 *  
	 * @param expedienteDTO
	 * @return SolicitudDefensorDTO		Con los datos de: 
	 * 							-Expediente Id
	 * 							-NumeroExpedienteId 
	 * 							-Numero Expediente
	 * 							-Solicitud
     * @return
     * @throws NSJPNegocioException
     */
    //MOD defensorATE
    SolicitudDefensorDTO registrarSolicitudAsesoriaLegal(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException; 
			
    /**
     * Guarda el motivo de la solicitud de defensoria
     * 
     * @param imputado
     * @param solicitante
     * @param expediente
     * @throws NSJPNegocioException
     */
    void guardarMotivoSolicitudDefensoria(
            SolicitudDefensorDTO solicitudDefensorDTO)
            throws NSJPNegocioException;

    /**
     * Guarda la informacion de la solicitud de defensoria asociada al
     * solcitante
     * 
     * @param solicitante
     * @param InvolucradoDTO
     *            .IdSolicitudDefensor
     * @return InvolucradoDTO solicitante
     * @throws NSJPNegocioException
     */
    InvolucradoDTO guardarSolicitanteSolicitudDefensoria(
            InvolucradoDTO solicitante) throws NSJPNegocioException;

    /**
	 * Registra la solicitud de aesoria, crea un nuevo expediente,una actividad
	 * y la liga a la solicitud creada Registra el solicitante y se asocia al
	 * expediente.
	 * 
     * @param solicitudDefensorDTO
     * @return
     * @throws NSJPNegocioException
     */
	SolicitudDefensorDTO actualizarSolicitudAsesoriaLegal(
			SolicitudDefensorDTO solicitudDefensorDTO)
			throws NSJPNegocioException;
	
    /**
     * Actualiza el imputado de la solicitud de defensoria
     * 
     * @param imputado
     * @throws NSJPNegocioException
     */
    void actualizaImputadoSolicitudDefensoria(InvolucradoDTO imputado)
            throws NSJPNegocioException;

    /**
     * Guarda la informacion de la solicitud de defensoria asociada al
     * involucrado
     * 
     * @param imputado solicitante
     * @param tipoAsesoria
     * @param etapa La etapa del expediente
     * @return
     * @throws NSJPNegocioException
     */
    InvolucradoDTO guardarDefendidoSolicitudDefensoria(InvolucradoDTO imputado,
            Long tipoAsesoria, EtapasExpediente etapa)
            throws NSJPNegocioException;

    /**
     * @param solicitud
     * @param estatus
     * @throws NSJPNegocioException
     */
    void actualizaEstatusSolicitud(SolicitudDTO solicitud,
            EstatusSolicitud estatus) throws NSJPNegocioException;

    /**
     * Servicio encargado de enviar una solicitud de Defensor para una lista de imputado
     * previamente seleccionados, los cuales se encuentran en
     * la audiencia representada por <code>audiencia</code>.
     * 
     * @param audiencia
     *            Audiencia a la cual se debe presentar el Defensor una vez
     *            asignado.
     * @param listaProbablesResponsables
     *            Lista de imputados que necesita el Defensor.
     * @return           
     * @throws NSJPNegocioException
     */
    SolicitudDefensorDTO  registrarSolicitudDefensorPJ(AudienciaDTO audienciaDTO,
            List<InvolucradoDTO> listaProbablesResponsables) throws NSJPNegocioException;

    /**
     * Servicio que envia un Aviso de Detencion de un Involucrado a
     * Coordinacic�n de Defensoria
     * 
     * @param imputadoId
     *            identificador del Probable Responsable que se encuentra
     *            Detenido
     * @throws NSJPNegocioException
     */
    public void registrarAvisoDeDetencion(Long imputadoId, CasoDTO noCaso,Long idAgencia, String claveAgencia)
            throws NSJPNegocioException;

    /**
     * Operaci�n que realiza la funcionalidad de consultar las solicitudes
     * asociadas al estatus recibido.
     * 
     * @param - El estatus de la solicitud que se requiere consultar - El
     *        usuario que consulta las solicitudes. - El tipo de solicitud a
     *        consultar (si no se recibe, consulta todas las solicitudes).
     * 
     * @return Devuelve un listado de solicitudes que cumplen con los criterios,
     *         en caso contrario, regresa null.
     * @throws NSJPNegocioException
     */
    public List<? extends SolicitudDTO> consultarSolicitudXEstatus(
            EstatusSolicitud estatusSolicitud, UsuarioDTO usuario,
            TiposSolicitudes tipoSolicitud) throws NSJPNegocioException;

    /**
     * Operaci�n que realiza la funcionalidad de consultar las solicitudes
     * asociadas al estatus recibido.
     * 
     * @param - El estatus de la solicitud que se requiere consultar - El
     *        usuario que consulta las solicitudes. - El tipo de solicitud a
     *        consultar (si no se recibe, consulta todas las solicitudes).
     * 
     * @return Devuelve un listado de solicitudes que cumplen con los criterios,
     *         en caso contrario, regresa null.
     * @throws NSJPNegocioException
     */
    public List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudTranscripcion(
            UsuarioDTO usuario) throws NSJPNegocioException;

    /**
     * Consulta las solicitudes que ordenan la Atencion de un Mandato Judicial
     * (Resolutivo)
     * 
     * @return
     * @throws NSJPNegocioException
     */
    public List<SolicitudMandamientoDTO> consultarSolicitudesMandatoJudicial()
            throws NSJPNegocioException;

    /**
     * Metodo para actualizar la etapa de un expediente asociado a la solicitur
     * 
     * @param solicitud
     * @param etapa
     * @throws NSJPNegocioException
     */
    void actualizarEtapaExpedienteSolicitudDefensoria(
            SolicitudDefensorDTO solicitud, EtapasExpediente etapa)
            throws NSJPNegocioException;

    /**
     * Realiza la asociacion de una solicitud a un NumeroExpediente de un tipo
     * determinado.
     * 
     * @author cesarAgustin
     * @param expedienteDTO
     *            -Identificador del NumeroExpediente al cual se asociara la
     *            solicitud
     * @param solicitudDTO
     *            -Identificador de la solicitud a asociar
     * @throws NSJPNegocioException
     */
    void asociarSolicitudANumeroExpediente(ExpedienteDTO expedienteDTO,
            SolicitudDTO solicitudDTO) throws NSJPNegocioException;

    /**
     * Servicio que recibe un SolicitudDTO y un ArchivoDigitalDTO y guarda este
     * �ltimo como un ArchivoDigital posterioremte crea la relaci�n entre el
     * archivo digital y la solicitud a traves de un objeto SolicitudAdjuntos;
     * 
     * @param solicitud
     *            nueva solicitud que a su vez ser� a la cual se asocie el
     *            documento cargado como Adjunto a la Solicitud
     * @param adjunto
     *            requere traer todas sus propiedades con valores, a excepci�in
     *            del identificador, para de esta manera crear el archivo y
     *            luego asociarlo a la solicitud
     * 
     */
    void adjuntarArchivoASolicitud(SolicitudDTO solicitud,
            ArchivoDigitalDTO adjunto) throws NSJPNegocioException;

    /**
     * Consulta las solicitudes de cierto numero de expediente y cierto tipo
     * 
     * @param numeroExpedienteId
     *            Numero de expediente a filtrar
     * @param tipo
     *            Tipo a filtrar
     * @return Lista de solicitudes<
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesPorNumeroExpedienteYTipo(
            Long numeroExpedienteId, TiposSolicitudes tipo)
            throws NSJPNegocioException;

    /**
     * Registra una solicitud gen�rica
     * 
     * @param solicitud
     *            Datos fuente
     */
    SolicitudDTO registrarSolicitud(SolicitudDTO solicitud)
            throws NSJPNegocioException;

    /**
     * Registara una nueva Orden de Detencion y se la envia al Comandante de
     * Policia Ministerial
     * 
     * @param ordenDetencion
     * @return
     * @throws NSJPNegocioException
     */
    public SolicitudDTO registrarSolicitudOrdenDeDetencion(
            SolicitudDTO ordenDetencion, Long idDocumentoAnexo)
            throws NSJPNegocioException;

    /**
     * Consulta las solicitudes defensoria que fueron asignadas a un funcionario
     * que es enviado como parametro.
     * 
     * @author cesarAgustin
     * @param funcionarioDTO
     * @return Lista de solicitud asignadas al funcionario
     * @throws NSJPNegocioException
     */
    List<SolicitudDefensorDTO> consultarSolDefensorAsignadas(
            FuncionarioDTO funcionarioDTO) throws NSJPNegocioException;

    /**
     * Actuliza la solicitud defensoria, al estatus que se envia como parametro
     * 
     * @author cesarAgustin
     * @param solDefensorDTO
     *            -Identificador de la solicitud defensoria a actualizar <li>
     *            documentoId<li>
     * @param estatusSolicitud
     *            -Estatus al que se actualizara la solicitud defensoria
     * @throws NSJPNegocioException
     */
    public void actualizarEstatusSolicitudDefensoria(
            SolicitudDefensorDTO solDefensorDTO,
            EstatusSolicitud estatusSolicitud) throws NSJPNegocioException;

    /**
     * Consulta un listado de solicitudes de audiencia de cierto tipo y cierto
     * estado
     * 
     * @param tipo
     *            Tipo a buscar
     * @param estado
     *            Estado a buscar
     * @return Solicitudes encontradas
     */
    List<SolicitudAudienciaDTO> consultarSolicitudesAudienciaPorTipoyEstado(
            TiposSolicitudes tipo, EstatusSolicitud estado);

    /**
     * Consulta el n�mero de expediente relacionado a una solicitud
     * 
     * @param solicitudId
     *            Identificador de la solicitud a consultar
     * @return Indentificador del expediente, null si la solicitud no tiene
     *         n�mero de expediente
     */
    Long consultarNumeroExpedienteDeSolicitud(Long solicitudId);

    /**
     * Realiza la consulta de las solicitudes de acuerdo a la variable historico
     * y un estatus determinado.
     * 
     * @author cesarAgustin
     * @param estatusSolicitud
     * @return Lista de solicitudes obtenidas
     * @throws NSJPNegocioException
     */
    List<SolicitudDefensorDTO> consultarSolicitudesDefensoriaByHistoricoYEstatus(
            EstatusSolicitud estatusSolicitud) throws NSJPNegocioException;

    /**
     * Elimina la asignacion del funcionario enviado como parametro del
     * expediente tambien enviado.
     * 
     * @author cesarAgustin
     * @param expedienteDTO
     * @param funcionarioDTO
     * @throws NSJPNegocioException
     */
    public SolicitudDefensorDTO reasignarDefensorAExpediente(
            ExpedienteDTO expedienteDTO, FuncionarioDTO funcionarioDTO)
            throws NSJPNegocioException;

    /**
     * Permite actualizar el estatus de una solicitud
     * 
     * @param folioSolicitud
     * @param estatus
     * @throws NSJPNegocioException
     */
    void actualizaEstatusSolicitud(String folioSolicitud,
            EstatusSolicitud estatus) throws NSJPNegocioException;

    /**
     * Metodo que permite consultar Datos de una solicitud
     * 
     * @param folioSolicitud
     * @return SolicitudDTO
     * @throws NSJPNegocioException
     */
    public SolicitudDTO consultarDatosDeSolicitud(String folioSolicitud)
            throws NSJPNegocioException;

    /**
     * Metodo para registrar una nueva solicitud de Audiencia
     * 
     * @param solicitud
     * @return
     * @throws NSJPNegocioException
     */
    public SolicitudAudienciaDTO registrarSolicitudAudiencia(
            SolicitudAudienciaDTO solicitud) throws NSJPNegocioException;

    /**
     * Metodo para registrar una nueva solicitud de Transcripcion de Audienia o
     * de Audio y Video de Audiencia
     * 
     * @param solicitud
     * @return
     * @throws NSJPNegocioException
     */
    public SolicitudTranscripcionAudienciaDTO registrarSolicitudTranscripcionAudiencia(
            SolicitudTranscripcionAudienciaDTO solicitud)
            throws NSJPNegocioException;

    /**
     * Crea y adjunta un archivo digital a una solicitud que ya existe
     * 
     * @param solicitud
     *            Solicitud ya existente
     * @param adjunto
     *            Archivo adjunto a crear y asociar
     * @throws NSJPNegocioException
     */
    void adjuntarArchivoASolicitudBasico(SolicitudDTO solicitud,
            ArchivoDigitalDTO adjunto) throws NSJPNegocioException;

    /**
     * Registra una solicitud de Carpeta de Invesigaci�n y la envia a
     * Procuradur�a.
     * 
     * @param idNumeroExpediente
     * @param usuario
     * @return
     * @throws NSJPNegocioException
     */
    public SolicitudDTO registrarSolicitudCarpetaInvestigacion(
            Long idExpediente, UsuarioDTO usuario) throws NSJPNegocioException;

    /**
     * Servicio que permite consultar las solicitudes de acuerdo a: -Tipo de
     * Solicitud : Lista con los id de Tipo de solictudes a recuperar -Estatus
     * de Solicitud : Lista con los id de Estaus de las solictudes a recuperar
     * -idAreaOrigen : Identificador de la institucion a las que pertenencen las
     * solicitudes a recuperar. -idFuncionarioSolicitante (Funcionario
     * Remitente): Para las Solicitudes Generadas por el mismo funcionario.
     * 
     * El idAreaOrigen es utilizado para consultar todas las solicitudes
     * independientemente del funcionario. Regresa todo si se pasa la lista
     * vacia de TipoSolicitud y EstatusSolicitud, y los campos idAreaOrigen e
     * idFuncionarioSolicitante, en null.
     * 
     * @param idEstatusSolicitud
     * @param idTipoSolicitud
     * @param idAreaOrigen
     * @param idFuncionarioRemitente
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesGeneradas(
            List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
            Long idAreaOrigen, Long idFuncionarioSolicitante)
            throws NSJPNegocioException;

    /**
     * Servicio que permite consultar las solicitudes de acuerdo a: -Tipo de
     * Solicitud : Lista con los Id de Tipo de solictud a recuperar -Estatus de
     * Solicitud : Lista con los id de Estaus de las solictudes a recuperar
     * -idAreaOrigen : Identificador de la institucion a las que pertenencen las
     * solicitudes a recuperar. -idFuncionarioSolicitante (Funcionario
     * Remitente): Para las Solicitudes Generadas por el mismo funcionario.
     * -numeroExpediente: Cadena del n�mero de expedeinte asociado a la
     * solicitud.
     * 
     * El idAreaOrigen es utilizado para consultar todas las solicitudes
     * independientemente del funcionario. Regresa todo si se pasa la lista
     * vacia de TipoSolicitud y EstatusSolicitud, y los campos idAreaOrigen e
     * idFuncionarioSolicitante y numeroExpediente en null.
     * 
     * @param idEstatusSolicitud
     * @param idTipoSolicitud
     * @param idAreaOrigen
     * @param idFuncionarioRemitente
     * @param numeroExpediente
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesGeneradasPorNumeroExpediente(
            List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
            Long idAreaOrigen, Long idFuncionarioSolicitante,
            String numeroExpediente) throws NSJPNegocioException;

    /**
     * Servicio que permite consultar las solicitudes de acuerdo a: -Tipo de
     * Solicitud : Lista con los id de Tipo de solictudes a recuperar -Estatus
     * de Solicitud : Lista con los id de Estaus de las solictudes a recuperar
     * -idAreaDestino : Identificador de la institucion a las que pertenencen
     * las solicitudes a recuperar. -idFuncionarioDestinatario : Para las
     * Solicitudes a Atender y es la relacion de iFuncionarioDestinatario de
     * Solicitud El idAreaOrigen es utilizado para consultar todas las
     * solicitudes independientemente del funcionario. Regresa todo si se pasa
     * la lista vacia de TipoSolicitud y EstatusSolicitud, y los campos
     * idAreaDestino e idFuncionarioSolicitante, en null.
     * 
     * @param idEstatusSolicitud
     * @param idTipoSolicitud
     * @param idAreaDestino
     * @param idFuncionarioDestinatario
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesParaAtender(
            List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
            Long idAreaDestino, Long idFuncionarioDestinatario, Long catDiscriminanteOrigen)
            throws NSJPNegocioException;

    /**
     * Consulta de los Tipos de solicitudes de acuerdo al ID de la Jerarquia
     * Organizacional. La recuperaci�n de la informaci�n se realiza mediante la
     * tabla de cruce de JerarquiaOrgTipoSolicitud
     * 
     * @param idJerarquiaOrganizacional
     * @return
     * @throws NSJPNegocioException
     */
    List<ValorDTO> consultarTipoSolictudesPorJerarquiaOrganizacional(
            Long idJerarquiaOrganizacional) throws NSJPNegocioException;

    /**
     * Consulta solicitudes de acuerdo a:
     * 
     * @param tipoSolicitudes
     * @param estatusSolicitud
     * @author cesarAgustin
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesPorTipoYEstatus(
            TiposSolicitudes tipoSolicitudes,
            EstatusSolicitud estatusSolicitud, Long claveFuncionario,UsuarioDTO usuario)
            throws NSJPNegocioException;

    /**
     * Consulta las solicitudes de tipo <code>tiposolicitud</code> y que no
     * tengan el estado <code>estatusSolicitud</code>
     * 
     * @param tipoSolicitud
     * @param estatusSolicitud
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesPorTipoYNoEstatus(
            TiposSolicitudes tipoSolicitudes,
            EstatusSolicitud estatusSolicitud, Long claveFuncionario)
            throws NSJPNegocioException;

    // FIXME MGALLARDO Documentar
    public List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(
            Long idAudiencia, Long tipoId, List<Long> estatusId)
            throws NSJPNegocioException;

    // FIXME MGALLARDO Documentar
    public List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudMaster(
            Long idAudiencia, Long idTipo) throws NSJPNegocioException;

    /**
     * Actualiza el estatus de la solicitud beneficios preliberacion a en
     * proceso y obtiene la carpeta de ejecucion correspondiente
     * 
     * @author cesarAgustin
     * @param solicitudDTO
     *            <li>documentoId<li> <li>expedienteDTO.numeroExpedienteId<li>
     * @return Carpeta ejecucion
     * @throws NSJPNegocioException
     */
    public ExpedienteDTO atenderSolicitudPreliberacion(SolicitudDTO solicitudDTO)
            throws NSJPNegocioException;

    /**
     * Consulta las solicitudes de transcripci�n de audiencia que est�n en
     * cierto estatus y que sean de cierto tipo
     * 
     * @param tipoId
     *            Tipo de la solicitud de transcripci�n (transcripci�n o A/V)
     * @param estatusId
     *            Estatus de la solicitud de transcripci�n
	 * @param fechaIni  Fecha inicial, de creaci�n de la solicitud.
	 * @param fechaFin  Fecha final, de creaci�n del a solicitud.
	 * @author Emigdio - GBP
     * @return Lista de solicitudes
     */
    List<SolicitudTranscripcionAudienciaDTO> consultarSolicitudTranscripcionAudienciaPorTipoYEstatus(
            Long tipoId, Long estatusId,UsuarioDTO usuario, Date fechaIni, Date fechaFin);
    /**
     * Consulta el detalle de una solicitud de transcripci�n de audiencia en
     * base a su llave primaria
     * 
     * @param solicitudId
     *            Identificador de la solicitud a buscar
     * @return Solicitud encontrada, null en caso de no encontrarla
     */
    SolicitudTranscripcionAudienciaDTO consultarDetalleSolicitudTranscripcion(
            Long solicitudId);

    /**
     * Consulta el detalle de una solicitud por llave primaria
     * 
     * @param solicitudId
     *            Identificador de la solicitud a buscar
     * @return Solicitud encontrada, null en caso de no encontrarla
     * @throws NSJPNegocioException
     */
    public SolicitudDTO consultarSolicitudXId(Long solicitudId)
            throws NSJPNegocioException;

    public Long autorizarSolicitudDeEvidencia(SolicitudPericialDTO evidencia)
            throws NSJPNegocioException;

    /**
     * Env�a v�a servicio web la actualizaci�n del estatus de una solicitud de
     * transcripci�n de audiencia junto con el archivo digital generado para
     * esta
     * 
     * @param solicitudId
     *            ID de la solicitud a enviar
     * @author Emigdio Hern�ndez
     */
    void enviarActualizacionSolicitudTranscripcionAudiencia(Long solicitudId)
            throws NSJPNegocioException;

    /**
     * Metodo que permite guardar una solicitud de transcripcion, a trav�s de un
     * Web services
     * 
     * @param solicitudTranscripcionDto
     * @return
     * @throws NSJPNegocioException
     */
    public SolicitudTranscripcionAudienciaDTO enviarSolicitudDeTranscripcionAAreaExterna(
            SolicitudTranscripcionAudienciaDTO solicitudTranscripcionDto)
            throws NSJPNegocioException;

    /**
     * Consulta el detalle de una solicitud de defensor�a
     * 
     * @param sol
     * @return
     * @throws NSJPNegocioException
     */
    public SolicitudDefensorDTO obtenerSolicitudDefensor(
            SolicitudDefensorDTO sol) throws NSJPNegocioException;

	/**
	 * Consulta las solicitudes por criterios.
	 * El numero de expediente al que esta relacionada la solicitud.
	 * El idFuncionarioSolicitante debe de ser el remitente, es decir el funcionario que 
	 * genera las solicitudes. Este valor es obligatorio. 
	 * El idAreaOrigen es utilizado para consultar todas las solicitudes 
	 * independientemente del funcionario.
	 * Regresa todo si se pasa la lista vac&iacute;a de TipoSolicitud y EstatusSolicitud, y los 
	 * campos idAreaOrigen e idFuncionarioSolicitante y numeroExpediente en null.
	 * @param solicitante
	 * @param solicitud
	 * @param idEstatusSolicitud
	 * @param idTipoSolicitud
	 * @param tipoConsulta
	 * @return
	 * @throws NSJPNegocioException
	 */
	
	List<SolicitudDTO> consultarSolicitudesConCriterios (
			SolicitudDTO solicitudDTO,
			List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
			String tipoConsulta
			) throws NSJPNegocioException;
	/**
	 * M&eacute;todo que env&iacute;a una solicitud a otra instituci&oacute;n
	 * 
	 * @param solicitudDTO
	 * @param destino
	 * @param lstDocumentoAdjuntos
	 * @param sentenciaDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	SolicitudDTO enviarSolicitud(SolicitudDTO solicitudDTO,
			Instituciones destino, List<DocumentoDTO> lstDocumentoAdjuntos,
			SentenciaDTO sentenciaDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que consulta los imputados que tengan solicitud de defensor
	 * y, opcionalmente, pertenezcan a una audiencia en particular.
	 * 
	 * No requiere manejo de Excepciones ya que es posible mandar los parametros
	 * nulos. En este caso regresa la lista de id de todas las solicitudes.
	 * 
	 * @param audienciaDTO
	 *            - La audiencia a partir de la cual se van a consultar las
	 *            solicitudes de defensor.
	 * @param imputadosId
	 *            - Lista de imputados que se consultara, para saber si ya
	 *            cuentan con almenos una solicitud.
	 * @return List<Long> - Lista de Id de los involucrados que ya cuentan con
	 *         solicitudes de defensor
	 * @throws NSJPNegocioException
	 */
	List<Long> obtenerInvolucradosIdConSolicitudDefensor(
			AudienciaDTO audienciaDTO, List<Long> imputadosId)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para invocar el servicio para validar si 
	 * se puede realizar la solicitud de defensor
	 * 
	 * @param expedienteDTO
	 * @return string que se mostrar&aacute; en vistas con la respuesta
	 * @throws NSJPNegocioException
	 */
	public String validarSolicitudDeDefensor(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para cambiar el estatus de la solicitud de defensor y verificar
	 * si existe un n&uacute;mero de expediente(de defensor&iacute;a)asociado al caso,
	 * al cual pertenece la solicitud; de ser as&iacute;, asocia la solicitud al expediente.
	 * En caso contrario genera un nuevo n&uacute;mero de expediente(de defensor&iacute;a) y
	 * asocia la solicitud a ese nuevo n&uacute;mero
	 * 
	 * @param solicitudDefensorDTO
	 * @return string que se mostrar&aacute; en vistas con la respuesta
	 * @throws NSJPNegocioException
	 */
	public String atenderSolicitudDeDefensor(SolicitudDefensorDTO solicitudDefensorDTO,UsuarioDTO usuarioFirmado)
			throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para consultar los involucrados de una solicitud de defensor
	 * @param solicitudDefensorId
	 * @return Lista de Involucrados asociados a la solicitud
	 * @throws NSJPNegocioException
	 */
	public List<InvolucradoDTO> consultarInvolucradosSolicitudDefensor(
			Long solicitudDefensorId) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las solicitudes asociadas a un expediente
	 * y que corresponden con los tipos pasados dentro de la lista de tipos de documento
	 * ingresada como argumento, asimismo filtra en base al estatus de la solicitud recibido como
	 * par&aacute;metro en el caso de que sea distinto a <code>null</code>.
	 * @param expediente - Expediente del cu&aacute;l se van a obtener las dsolicitudes asociadas.
	 * @param tiposDocumento - List<Valor> con los tipos de documento sobre los cuales se va 
	 * 						   a realizar la consulta.
	 * @param estatusSolicitud - Valor con el estatus de la solicitud sobre el cual se van a filtrar
	 * 							 las solicitudes.
	 * @return List<SolicitudDTO> - List<SolicitudDTO> con los documentos que cumplieron con los filtros
	 * 							 pasados como par&aacute;metros.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en todos los par&aacute;metros
	 * 								  obligatorios.
	 */
	public List<SolicitudDTO> consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(ExpedienteDTO expediente,
			List<ValorDTO> tiposDocumento, ValorDTO estatusSolicitud) throws NSJPNegocioException;
	/**
	 * Permite consultar solicitudes asociadas a numeros de expedientes y tipos de solicitudes 
	 * @param idCaso
	 * @param idTipoSolicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<SolicitudDTO> consultarSolicitudesPorTipoYNumeroExps(Long idCaso, List<Long> idTipoSolicitud) 
		throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que crea una SolicitudMandamiento con un SolicitudId 
	 * y un MandamientoId previamente existentes.
	 * 
	 * @param solicitudMandamiento: Objeto que incluye la informaci&oacute;n
	 * 								del identificador de la solicitud y el 
	 * 								identificador del mandamiento que se encuentran
	 * 								previamente registrados en la base de datos.
	 * 
	 * @return idSolicitudMandamiento: Identificador de la solicitud mandamiento generada.
	 * 
	 * @throws NSJPNegocioException - En el caso de que no se ingresen los par&aacute;metros
	 * 								  necesarios.
	 */
	public SolicitudMandamientoDTO crearSolicitudMandamientoConInfoExistente(SolicitudMandamientoDTO 
			solicitudMandamientoDTO) throws NSJPNegocioException;
	
	
	/**
	 * M&eacute;todo para verificar si se puede editar el numero de expediente
	 * en una solicitud de audiencia, recibe como par&aacute;metro el
	 * numeroExpedienteId, y realiza lo siguiente: 1) Verifica si el
	 * par&aacute;metro de edici&oacute;n de n&uacute;mero de esta encendido (es
	 * decir regresa true o 1).
	 * 
	 * 2) Verifica que el numeroExpedienteId "NO" tiene asociadas audiencia, las
	 * cuales tengan una dFechaAudiencia que no sea NULL.
	 * 
	 * Si ambos servicios devuelven TRUE, entonces la respuesta es TRUE, en
	 * cualquier otro caso FALSE.
	 * 
	 * 
	 * @param expedienteDTO
	 *            , par&aacute;metro del cual obtenemos el numeroExpedienteId
	 * @return Booleano con la respuesta, se puede editar o no
	 * @throws NSJPNegocioException
	 */
	public Boolean editarNumeroExpedienteSolicitudAudiencia(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo para actualizar el N&uacute;mero de expediente (String)
	 * @param expedienteDTO, con el numeroExpedienteId
	 *					   , con el nuevoNumeroExpediente (String) a actualizar
	 * @return Booleano con la respuesta, se pudo actualizar (true) o no (false)
	 * @throws NSJPNegocioException, si NO es posible actualizar.
	 */
	public Boolean actualizarNumeroExpedienteSolicitudAudiencia(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException;
	
	/**
	 * Servicio que se encarga de actualizar el estatus de las solicitudes
	 * asociadas al numero de expediente. El cambio de la solicitud se efectua
	 * de acuerdo al estatus del expediente.
	 * 
	 * Tambien verifica si el estatus del n&uacute;mero de expediente son distintos, 
	 * de ser as&iacute;, se hace la actualizaci&oacute;n del estatus del n&uacute;mero 
	 * de expediente por el proporcionado.
	 * 
	 * @param nuevoEstatusNumExpId
	 * @param numeroExpedienteId
	 * @return
	 * @throws NSJPNegocioException
	 */
	Boolean actualizarEstatusSolicitudes(Long nuevoEstatusNumExpId,
			Long numeroExpedienteId) throws NSJPNegocioException;
	
	/**
	 * Consulta las solicitudes de mandamiento judicial por filtro
	 * @param solicitudMandamientoDTO, objetoDTO con los atributos de b&uacute;squeda
	 * @return Lista de solicitudes que superaron el filtro
	 * @throws NSJPNegocioException, en caso de alguna excepci&oacute;n
	 */
    public List<SolicitudMandamientoDTO> consultarSolicitudesMandatoJudicialPorFiltro(SolicitudMandamientoDTO solicitudMandamientoDTO)
            throws NSJPNegocioException;
    
    /**
	 * Enable JC. Compartir solicitudes UAVD
	 * @param funcionarioId
	 * @param solicitudId
	 * @param fechaVencimiento
	 * @param permiso
	 */
	void asignarPermisoSolicitudFuncionario(Long funcionarioId, Long solicitudId, Date fechaVencimiento, Boolean permiso)
		throws NSJPNegocioException;
        
        void eliminarPermisoSolicitudFuncionario(Long funcionarioId, Long solicitudId)throws NSJPNegocioException;
}

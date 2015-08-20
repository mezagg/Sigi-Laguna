/**
 * Nombre del Programa : ConsultarSolicitudService.java Autor : vaguirre
 * Compania : Ultrasist Proyecto : NSJP Fecha: 16 Jun 2011 Marca de cambio : N/A
 * Descripcion General : Contrato para la consulta de las solicitudes Programa
 * Dependiente :N/A Programa Subsecuente :N/A Cond. de ejecucion :N/A Dias de
 * ejecucion :N/A Horario: N/A MODIFICACIONES
 * ------------------------------------------------------------------------------
 * Autor :N/A Compania :N/A Proyecto :N/A Fecha: N/A Modificacion :N/A
 * ------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.solicitud;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.solicitud.PermisoSolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;

/**
 * Contrato para la consulta de las solicitudes.<br>
 * Clase dise�ada para las consultas de las solicitudes de manera general, es
 * decir, las especializaciones de solicitudes las deben manejar otro servicios
 * (<code>ConsultarSolicitudesAudienciaService</code>...).
 *
 * @version 1.0
 * @author vaguirre
 *
 */
public interface ConsultarSolicitudService {

    /**
     * Consulta las solicitudes por expediente.
     *
     * @param filtro requerido <b>numeroExpedienteId</b>.
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesPorExpediente(ExpedienteDTO filtro)
            throws NSJPNegocioException;

    /**
     * Servicio que genera una nueva solicitud de defensoria - N�mero de
     * expediente - Fecha ultima modificaci�n - Fecha L�mite - Especialidad -
     * Nombre del solicitante - N�mero de caso
     *
     * @param solicitudDefensorDTO
     * @return SolicitudDefensorDTO
     * @throws NSJPNegocioException
     */
    SolicitudDTO obtenerSolicitud(SolicitudDTO solicitudDTO) throws NSJPNegocioException;

    /**
     * Consulta las solicitudes de cierto numero de expediente y cierto tipo
     *
     * @param numeroExpedienteId Numero de expediente a filtrar
     * @param tipo Tipo a filtrar
     * @return Lista de solicitudes
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesPorNumeroExpedienteYTipo(Long numeroExpedienteId, TiposSolicitudes tipo) throws NSJPNegocioException;

    /**
     * Consulta el n�mero de expediente relacionado a una solicitud
     *
     * @param solicitudId Identificador de la solicitud a consultar
     * @return Indentificador del expediente, null si la solicitud no tiene
     * n�mero de expediente
     */
    Long consultarNumeroExpedienteDeSolicitud(Long solicitudId);

    /**
     * Metodo que permite consultar Datos de una solicitud
     *
     * @param folioSolicitud
     * @return SolicitudDTO
     * @throws NSJPNegocioException
     */
    public SolicitudDTO consultarDatosDeSolicitud(String folioSolicitud) throws NSJPNegocioException;

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
    List<SolicitudDTO> consultarSolicitudesGeneradas(List<Long> idEstatusSolicitud,
            List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioSolicitante) throws NSJPNegocioException;

    /**
     * Servicio que permite consultar las solicitudes de acuerdo a: -Tipo de
     * Solicitud : Lista con los id de Tipo de solictudes a recuperar -Estatus
     * de Solicitud : Lista con los id de Estaus de las solictudes a recuperar
     * -idAreaOrigen : Identificador de la institucion a las que pertenencen las
     * solicitudes a recuperar. -idFuncionarioSolicitante (Funcionario
     * Remitente): Para las Solicitudes Generadas por el mismo funcionario.
     * -numeroExpediente : numero de expediente asociado a la solicitud.
     *
     * El idAreaOrigen es utilizado para consultar todas las solicitudes
     * independientemente del funcionario. Regresa todo si se pasa la lista
     * vacia de TipoSolicitud y EstatusSolicitud, y los campos idAreaOrigen e
     * idFuncionarioSolicitante y numeroExpediente en null.
     *
     * @param idEstatusSolicitud
     * @param idTipoSolicitud
     * @param idAreaOrigen
     * @param idFuncionarioSolicitante
     * @param numeroExpediente
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesGeneradasPorNumeroExpediente(List<Long> idEstatusSolicitud,
            List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioSolicitante, String numeroExpediente) throws NSJPNegocioException;

    /**
     * Servicio que permite consultar las solicitudes de acuerdo a: -Tipo de
     * Solicitud : Lista con los id de Tipo de solictudes a recuperar -Estatus
     * de Solicitud : Lista con los id de Estaus de las solictudes a recuperar
     * -idAreaDestino : Identificador de la institucion a las que pertenencen
     * las solicitudes a recuperar. -idFuncionarioDestinatario : Para las
     * Solicitudes a Atender y es la relacion de iFuncionarioDestinatario de
     * Solicitud
     *
     * El idAreaDestino es utilizado para consultar todas las solicitudes
     * independientemente del funcionario. Regresa todo si se pasa la lista
     * vacia de TipoSolicitud y EstatusSolicitud, y los campos idAreaDestino e
     * idFuncionarioSolicitante, en null.
     *
     * @param idEstatusSolicitud
     * @param idTipoSolicitud
     * @param idAreaDestino
     * @param idFuncionarioDestinatario
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesParaAtender(List<Long> idEstatusSolicitud,
            List<Long> idTipoSolicitud, Long idAreaDestino, Long idFuncionarioDestinatario, Long catDiscriminanteOrigen) throws NSJPNegocioException;

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
            TiposSolicitudes tipoSolicitudes, EstatusSolicitud estatusSolicitud, Long claveFuncionario, UsuarioDTO usuario) throws NSJPNegocioException;

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
            TiposSolicitudes tipoSolicitud, EstatusSolicitud estatusSolicitud, Long claveFuncionario)
            throws NSJPNegocioException;

    /**
     * Consulta las solicitudes por su Id
     *
     * @param solicitudId
     * @return
     * @throws NSJPNegocioException
     */
    SolicitudDTO consultarSolicitudXId(Long solicitudId) throws NSJPNegocioException;

    /**
     * Consulta las solicitudes por criterios. El numero de expediente al que
     * esta relacionada la solicitud. El idFuncionarioSolicitante debe de ser el
     * remitente, es decir el funcionario que genera las solicitudes. Este valor
     * es obligatorio. El idAreaOrigen es utilizado para consultar todas las
     * solicitudes independientemente del funcionario. Regresa todo si se pasa
     * la lista vac&iacute;a de TipoSolicitud y EstatusSolicitud, y los campos
     * idAreaOrigen e idFuncionarioSolicitante y numeroExpediente en null.
     *
     * @param solicitante
     * @param solicitud
     * @param idEstatusSolicitud
     * @param idTipoSolicitud
     * @param tipoConsulta
     * @return
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesConCriterios(
            SolicitudDTO solicitudDTO,
            List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
            String tipoConsulta
    ) throws NSJPNegocioException;

    /**
     * M&eacute;todo para consultar solicitudes por filtro. Se agrega nueva
     * funcionalidad para las solcitudes de Defensoria (Solicitud de Defensor y
     * Solicitud de Asesoria Legal), el filtro se encuntra en el atributo de la
     * SolicitudDTO con el nombre de AvisoDesignacion
     *
     * @param SolicitudDTO solFiltro (Setear los valores que se aplicar&aacute;n
     * en el filtro)
     * @param esAsociadaNumeroExpediente (Si la solicitud se asocia al
     * expediente)
     * @param distritoId identificador del Distrito para aplicar el filtro sobre
     * el numero de expediente
     * @return List<SolicitudDTO> (Lista de solicitudes que superaron el filtro)
     * @throws NSJPNegocioException
     */
    List<SolicitudDTO> consultarSolicitudesPorFiltro(SolicitudDTO solFiltro,
            Boolean esAsociadaNumeroExpediente, Long distritoId) throws NSJPNegocioException;

    /**
     * M&eacute;todo que lleva a cabo la consulta de las solicitudes asociadas a
     * un expediente y que corresponden con los tipos pasados dentro de la lista
     * de tipos de documento ingresada como argumento, asimismo filtra en base
     * al estatus de la solicitud recibido como par&aacute;metro en el caso de
     * que sea distinto a <code>null</code>.
     *
     * @param expediente - Expediente del cu&aacute;l se van a obtener los
     * documentos asociados.
     * @param tiposDocumento - List<Valor> con los tipos de documento sobre los
     * cuales se va a realizar la consulta.
     * @param estatusSolicitud - Valor con el estatus de la solicitud sobre el
     * cual se van a filtrar las solicitudes.
     * @return List<Documento> - List<Documento> con los documentos que
     * cumplieron con los filtros pasados como par&aacute;metros.
     * @throws NSJPNegocioException - En el caso de que no se env&iacute;en
     * todos los par&aacute;metros obligatorios.
     */
    public List<SolicitudDTO> consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(ExpedienteDTO expediente,
            List<ValorDTO> tiposDocumento, ValorDTO estatusSolicitud) throws NSJPNegocioException;

    /**
     * Permite consultar solicitudes asociadas a Numeros de expedeintes y tipos
     * de solicitud
     *
     * @param idCaso
     * @param idTipoSolicitud
     * @return
     * @throws NSJPNegocioException
     */
    public List<SolicitudDTO> consultarSolicitudesPorTipoYNumeroExps(Long idCaso, List<Long> idTipoSolicitud)
            throws NSJPNegocioException;

    /**
     * Consulta las solicitudes de mandamiento judicial por filtro
     *
     * @param SolicitudMandamientoDTO, objetoDTO con los atributos de
     * b&uacute;squeda
     * @return Lista de solicitudes que superaron el filtro
     * @throws NSJPNegocioException , en caso de alguna excepci&oacute;n
     */
    public List<SolicitudMandamientoDTO> consultarSolicitudesMandatoJudicialPorFiltro(
            SolicitudMandamientoDTO solicitudMandamientoDTO)
            throws NSJPNegocioException;

    /**
     * Enable JC. Obtiene las solicitudes con permiso UAVD
     *
     * @param funcionarioId
     * @return
     * @throws NSJPNegocioException
     */
    List<PermisoSolicitudDTO> buscarSolicitudesConPermisoFuncionario(Long funcionarioId) throws NSJPNegocioException;

}

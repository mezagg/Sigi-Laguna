/**
 * Nombre del Programa : SolicitudDAO.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 16 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato del objeto de acceso a datos para la entidad Solicitud
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
package mx.gob.segob.nsjp.dao.solicitud;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.AvisoDesignacion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Solicitud;
import mx.gob.segob.nsjp.model.SolicitudAudiencia;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Contrato del objeto de acceso a datos para la entidad Solicitud.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public interface SolicitudDAO extends GenericDao<Solicitud, Long> {
	
    /**
     * 
     * @return
     */
    List<Solicitud> consultarSolicitudesPorExpediente(Long numeroExpedienteId);

    Solicitud consultarSolicitudPorDocumentoId(long solicitudId);
    /**
     * Operación que realiza la funcionalidad de consultar las solicitudes asociadas al estatus recibido.
     * 
     * @param estatusSolicitud
     * @param transformarDTO
     * @param tipoSolicitud
     * @return Devuelve un listado de solicitudes que cumplen con los criterios, en caso contrario, regresa null.
     */
	List<Solicitud> consultarSolicitudXEstatus(Long estatusSolicitud, Long tipoSolicitud);

	List<SolicitudAudiencia> consultarSolicitudAudienciaXEstatus(
			Long valorId, Long valorId2) throws NSJPNegocioException;
	
	/**
	 * Consulta solicitudes de acuerdo al filtro que es enviado.
	 * En caso de que se omita el filtro, se consultan todos las solicitudes
	 * 
	 * @param solicitudFiltro (Setear los valores que se aplicar&aacute;n en el filtro)
	 * @param esAsociadaNumeroExpediente (Si la solicitud se asocia al expediente)
	 * @param distritoId identificador del Distrito para aplicar el filtro sobre el numero de expediente
	 * @param avisoDesignacionFiltro objeto de Aviso para aplicar el filtro, solo Defensoria
	 * @return List<Solicitud> (Lista de solicitudes que superaron el filtro)
	 */
	List<Solicitud> consultarSolicitudPorFiltro(Solicitud solicitudFiltro,
			Boolean esAsociadaNumeroExpediente, Long distritoId,
			AvisoDesignacion avisoDesignacionFiltro);
	
	/**
	 * Consulta las solicitudes de cierto numero de expediente y cierto tipo
	 * @param numeroExpedienteId Numero de expediente a filtrar
	 * @param tipo Tipo a filtrar
	 * @return Lista de solicitudes
	 */
    List<Solicitud> consultarSolicitudesPorNumeroExpedienteYTipo(Long numeroExpedienteId,TiposSolicitudes tipo);
 
    /**
     * Consulta las solicitudes de cierto id de expediente y cierto tipo
     * @param idExpediente Id de expediente a filtrar
     * @param carpetaInvestigacion Tipo de Solicitud a filtrar
     * @return 
     */
	List<Solicitud> consultarSolicitudesPorExpedienteYTipo(Long idExpediente,TiposSolicitudes carpetaInvestigacion); 
    
    /**
     * Consulta el número de expediente relacionado a una solicitud
     * @param solicitudId Identificador de la solicitud a consultar
     * @return Indentificador del expediente, null si la solicitud no tiene número de expediente
     */
    Long consultarNumeroExpedienteDeSolicitud(Long solicitudId);
    

    /**
     * Consultar la solicitud de acuerdo al folio de Solicitud, el folio de solicitud es único, 
     * si existen mas de dos sulicitudes con el mismo folio, se regresa el primero que se encuentra.
     * @param folioSolicitud
     * @return
     */
	public Solicitud consultarSolicitudPorFolio(String folioSolicitud);
	
	/**
	 * Consultar todo el expediente asociados a una solicitud, a través de un número
	 * de expediente.
	 *  
	 * @param solicitudId
	 * @return
	 */
	public Expediente consultarExpedienteDeNumeroExpedienteSolicitud(Long solicitudId);

    /**
     * Consulta el folio de la última solicitud
     * @return
     * @throws NSJPNegocioException
     */
	String obtenerUltimoFolioSolicitud() throws NSJPNegocioException;

	/**
	 * Obtine la información de una solicitud asociada al <code>folio</code>
	 * @param folio
	 * @return
	 */
	Solicitud obtenerSolicitudPorFolio(String folio) throws NSJPNegocioException;
	
	Solicitud consultarSolicitudPorId(Long idSolicitud) throws NSJPNegocioException;
	
	public List<Solicitud> consultarSolicitudXEstatusYPuesto(Long estatusSolicitud,
			Long tipoSolicitud, Long puestoId);
	
	/**
	 * Consulta las solicitudes por Tipo, Estatus, institución (área) y 
	 * idFuncionarioSolicitante.
	 * El idFuncionarioSolicitante debe de ser el remitente, es decir el funcionario que 
	 * genera las solicitudes. Este valor es obligatorio. 
	 * El idAreaOrigen es utilizado para consultar todas las solicitudes 
	 * independientemente del funcionario.
	 * Regresa todo si se pasa la lista vacia de TipoSolicitud y EstatusSolicitud, y los 
	 * campos idAreaOrigen e idFuncionarioSolicitante, en null.
	 * 
	 * @param idEstatusSolicitud
	 * @param idTipoSolicitud
	 * @param idAreaOrigen
	 * @param idFuncionarioSolicitante
	 * @return
	 */
	List<Solicitud> consultarSolicitudesGeneradas(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioSolicitante);

	/**
	 * Consulta las solicitudes por Tipo, Estatus, área,
	 * idFuncionarioSolicitante y numero de Expediente.
	 * El numero de expediente al que esta relacionada la solicitud.
	 * El idFuncionarioSolicitante debe de ser el remitente, es decir el funcionario que 
	 * genera las solicitudes. Este valor es obligatorio. 
	 * El idAreaOrigen es utilizado para consultar todas las solicitudes 
	 * independientemente del funcionario.
	 * Regresa todo si se pasa la lista vacia de TipoSolicitud y EstatusSolicitud, y los 
	 * campos idAreaOrigen e idFuncionarioSolicitante y numeroExpediente en null.
	 * 
	 * @param idEstatusSolicitud
	 * @param idTipoSolicitud
	 * @param idAreaOrigen
	 * @param idFuncionarioSolicitante
	 * @param numeroExpediente
	 * @return
	 */
	List<Solicitud> consultarSolicitudesGeneradasPorNumeroExpediente(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaOrigen, Long idFuncionarioSolicitante, String numeroExpediente );
			
	/**
	 * Consulta las solicitudes por Tipo, Estatus, institución (área) y 
	 * idFuncionarioDestinatario.
	 * El idFuncionarioDestinatario debe de ser funcionario que debe de Atender la solicitudes.
	 * Este valor es obligatorio.
	 * El idAreaDestino es utilizado para consultar todas las solicitudes 
	 * independientemente del funcionario.
	 * Regresa todo si se pasa la lista vacia de TipoSolicitud y EstatusSolicitud, y los 
	 * campos idAreaDestino e idFuncionarioSolicitante, en null.
	 * 
	 * @param idEstatusSolicitud
	 * @param idTipoSolicitud
	 * @param idAreaDestino
	 * @param idFuncionarioDestinatario
	 * @return
	 */
	List<Solicitud> consultarSolicitudesParaAtender(List<Long> idEstatusSolicitud,
			List<Long> idTipoSolicitud, Long idAreaDestino, Long idFuncionarioDestinatario, Long idCatDiscriminante);

	
	 /**
	 * Consulta solicitudes de acuerdo a:
	 * @param tipoSolicitudes
	 * @param estatusSolicitud
	 * @author cesarAgustin
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<Solicitud> consultarSolicitudesPorTipoYEstatus(Long tipo,
			Long estatus, Long claveFuncionario,Long discriminanteId);

	
	/**
	 * Consulta las solicitudes de tipo <code>tiposolicitud</code> y que no tengan el estado
	 * <code>estatusSolicitud</code>
	 * @param tipoSolicitud
	 * @param estatusSolicitud
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<Solicitud> consultarSolicitudesPorTipoYNoEstatus(Long tipoSolicitud,
			Long estatusSolicitud, Long claveFuncionario) throws NSJPNegocioException;

	NumeroExpediente consultarNumeroExpedienteSolicitud(Long solicitudId);

	/**
     * Consultar la solicitud de acuerdo al folio de Solicitud, 
     * si existe mas de una sulicitud con el mismo folio regresa todas las solicitudes encontradas
     * Este método se utiliza para localizar una solicitud origen entre 2 instituciones que convivan en la misma BD
     * @param folioSolicitud
     * @author Emigdio Hernández
     * @return Listado de solicitudes con el mismo folio
     */
	public List<Solicitud> consultarSolicitudesPorFolio(String folioSolicitud);
	
	/**
	 * Metodo que permite recuperar el folio de una solicitud
	 * @param idSolicitud: Identificador del folio de la solicitud
	 * @return String
	 * @throws NSJPNegocioException
	 */
	public String obtenerFolioDeSolicitud(Long idSolicitud) throws NSJPNegocioException;

	/**
	 * M&acutee;todo que crea una solicitud con un DocumentoId ya existente
	 * @param Solicitud: Datos de la solicitud
	 * @return idSolicitud: Identificador de la solicitud
	 * @throws NSJPNegocioException
	 */
	public Long crearSolicitudConDocumentoExistente(Solicitud solicitud) throws NSJPNegocioException;	

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
	
	List<Solicitud> consultarSolicitudesConCriterios (
			Solicitud solicitud,
			List<Long> idEstatusSolicitud, List<Long> idTipoSolicitud,
			String tipoConsulta
			) throws NSJPNegocioException;	
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las solicitudes asociadas a un expediente
	 * y que corresponden con los tipos pasados dentro de la lista de tipos de documento
	 * ingresada como argumento, asimismo filtra en base al estatus de la solicitud recibido como
	 * par&aacute;metro en el caso de que sea distinto a <code>null</code>.
	 * @param expediente - Expediente del cu&aacute;l se van a obtener los documentos asociados.
	 * @param tiposDocumento - List<Valor> con los tipos de documento sobre los cuales se va 
	 * 						   a realizar la consulta.
	 * @param estatusSolicitud - Valor con el estatus de la solicitud sobre el cual se van a filtrar
	 * 							 las solicitudes.
	 * @return List<Documento> - List<Documento> con los documentos que cumplieron con los filtros
	 * 							 pasados como par&aacute;metros.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en todos los par&aacute;metros
	 * 								  obligatorios.
	 */
	public List<Solicitud> consultarSolicitudesPorExpedienteTiposDocumentoYEstatus(Expediente expediente,
			List<Valor> tiposDocumento, Valor estatusSolicitud) throws NSJPNegocioException;
	
	/**
	 * Permite consultar solicitudes asociadas a una lista de numeros de expedientes 
	 * @param idCaso
	 * @param idTipoSolicitud
	 * @return
	 */
	public List<Solicitud> consultarSolicitudesPorTipoYNumeroExps(Long idCaso, List<Long> idTipoSolicitud);

}

/**
 * Nombre del Programa : DocumentoDelegateImpl.java
 * Autor                            : Emigdio Hern�ndez
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 26/05/2011
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci�n del Delegate para manipulaci�n de documentos
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
package mx.gob.segob.nsjp.delegate.documento.impl;

import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoForma;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoHechoDelictivoDTO;
import mx.gob.segob.nsjp.dto.documento.CartaCumplimientoDTO;
import mx.gob.segob.nsjp.dto.documento.CuerpoOficioEstructuradoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoIntegracionDTO;
import mx.gob.segob.nsjp.dto.documento.GuardadoDefinitivoDTO;
import mx.gob.segob.nsjp.dto.documento.IndiceEstructuradoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.documento.RelacionDocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ParametrosDocumentoDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.archivo.ConsultarContenidoArchivoDigitalService;
import mx.gob.segob.nsjp.service.archivo.GuardarArchivoDigitalService;
import mx.gob.segob.nsjp.service.documento.ActualizarControversiaService;
import mx.gob.segob.nsjp.service.documento.ActualizarNotificacionService;
import mx.gob.segob.nsjp.service.documento.AdjuntarArchivoDigitalAExpedienteService;
import mx.gob.segob.nsjp.service.documento.AsociarArchivosDigitalesASolicitudService;
import mx.gob.segob.nsjp.service.documento.AsociarDocumentoAService;
import mx.gob.segob.nsjp.service.documento.CargarDocumentoService;
import mx.gob.segob.nsjp.service.documento.ConsultarArchivosDigitalesService;
import mx.gob.segob.nsjp.service.documento.ConsultarAvisosHDelictivoService;
import mx.gob.segob.nsjp.service.documento.ConsultarControversiasResueltasService;
import mx.gob.segob.nsjp.service.documento.ConsultarCuerpoOficioService;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentoPorUsuarioService;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentoXExpedienteService;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentosXTipoDocumentoService;
import mx.gob.segob.nsjp.service.documento.ConsultarIndicesEstructuradosService;
import mx.gob.segob.nsjp.service.documento.ConsultarPliegoDeConsignacionService;
import mx.gob.segob.nsjp.service.documento.ConsultarResumenExpedienteParaDocumentoService;
import mx.gob.segob.nsjp.service.documento.ConsultarTeoriaDelCasoService;
import mx.gob.segob.nsjp.service.documento.EnviarDocumentoAInstitucionService;
import mx.gob.segob.nsjp.service.documento.GuardarAvisoHDelictivoService;
import mx.gob.segob.nsjp.service.documento.GuardarControversiaResueltaService;
import mx.gob.segob.nsjp.service.documento.GuardarDocumentoService;
import mx.gob.segob.nsjp.service.eslabon.CrearDocumentoEslabonService;
import mx.gob.segob.nsjp.service.forma.BuscarFormaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementaci�n del Delegate para manipulaci�n de documentos
 * 
 * @author Emigdio Hern�ndez
 * 
 */
/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
@Service("documentoDelegate")
public class DocumentoDelegateImpl implements DocumentoDelegate {

	@Autowired
	private CargarDocumentoService cargarDocumentoService;
	@Autowired
	private GuardarArchivoDigitalService guardarArchivoDigitalService;
	@Autowired
	private BuscarFormaService buscarFormaService;
	@Autowired
	private GuardarDocumentoService guardarDocumentoService;
	@Autowired
	private ConsultarDocumentoService consultarDocumentoService;
	@Autowired
	private ConsultarResumenExpedienteParaDocumentoService consultarResumenExpedienteParaDocumentoService;
	@Autowired
	private ConsultarDocumentosXTipoDocumentoService consultarDocumentosXTipoDocumentoService;
	@Autowired
	private ConsultarDocumentoPorUsuarioService consultarDocumentoPorUsuarioService;
	@Autowired
	private ConsultarDocumentoXExpedienteService consultarDocumentoXExpedienteService;
	@Autowired
	private ConsultarContenidoArchivoDigitalService consultarContenidoArchivoDigitalService;
	@Autowired
	private AsociarDocumentoAService asociarDocumentoAService;
	@Autowired
	private ConsultarAvisosHDelictivoService consultarAvisosHDelictivoService;
	@Autowired
	private ActualizarNotificacionService actualizarNotificacionService;
	@Autowired
	private GuardarAvisoHDelictivoService guardarAvisoHDelictivoService;
	@Autowired
	private ConsultarIndicesEstructuradosService consultarIndicesEstructuradosService;
	@Autowired
	private ConsultarTeoriaDelCasoService consultarTeoriaDelCasoService;
	@Autowired
	private ConsultarPliegoDeConsignacionService consultarPliegoDeConsignacionService;
	@Autowired
	private ConsultarCuerpoOficioService consultarCuerpoOficioService;
	@Autowired
	private ConsultarControversiasResueltasService consultarControversiasResueltasService;
	@Autowired
	private ActualizarControversiaService actualizarControversiaService;
	@Autowired
	private GuardarControversiaResueltaService guardarControversiaResueltaService;
	@Autowired
	private AdjuntarArchivoDigitalAExpedienteService adjuntarArchivoDigitalAExpedienteService;
	@Autowired
	private ConsultarArchivosDigitalesService consultarArchivosDigitales;
	
	@Autowired
	AsociarArchivosDigitalesASolicitudService asociarArchivoService;
	@Autowired
	EnviarDocumentoAInstitucionService aInstitucionService;
	
	@Autowired
	CrearDocumentoEslabonService crearDocumentoEslabonService;

	
	
	

	/*-------------------------------------------------------*/
	@Override
	public DocumentoDTO cargarDocumentoPorExpedienteYForma(
			ExpedienteDTO expedienteParam, FormaDTO tipoForma)
			throws NSJPNegocioException {
		return cargarDocumentoService.cargarDocumentoPorExpedienteYForma(
				expedienteParam, tipoForma);
	}

	@Override
	public DocumentoDTO cargarDocumentoPorAudienciaYForma(
			AudienciaDTO audiencia, FormaDTO tipoForma)
			throws NSJPNegocioException {
		return cargarDocumentoService.cargarDocumentoPorAudienciaYForma(
				audiencia, tipoForma);
	}

	@Override
	public DocumentoDTO cargarDocumentoPorResolutivoYForma(
			ResolutivoDTO resolutivo, FormaDTO tipoForma)
			throws NSJPNegocioException {
		return cargarDocumentoService.cargarDocumentoPorResolutivoYForma(
				resolutivo, tipoForma);
	}

	@Override
	public Long guardarArchivoDigital(ArchivoDigitalDTO archivo)
			throws NSJPNegocioException {
		return guardarArchivoDigitalService.guardarArchivoDigital(archivo);
	}

	@Override
	public FormaDTO buscarForma(Long formaId) throws NSJPNegocioException {
		return buscarFormaService.buscarForma(formaId);
	}

	@Transactional
	@Override
	//Se agrega la anotacion de transactional, para asegurar que el guardado del documento es atomico.
	public Long guardarDocumento(DocumentoDTO documento,
			ExpedienteDTO expedienteActual, Long nuevaActividad, Long idActividadExistente) throws NSJPNegocioException {
		return guardarDocumentoService.guardarDocumento(documento,expedienteActual,nuevaActividad,idActividadExistente);
	}

	@Override
	public Long guardarDocumentoTranscripcion(DocumentoDTO documento,
			Long idSolTranAudi) throws NSJPNegocioException {
		return guardarDocumentoService.guardarDocumentoTranscripcion(documento,
				idSolTranAudi);
	}

	public ParametrosDocumentoDTO cargarResumenExpedienteParaDocumento(
			ExpedienteDTO expediente) throws NSJPNegocioException {
		return consultarResumenExpedienteParaDocumentoService
				.consultarResumenExpedienteParaDocumento(expediente);
	}

	public List<DocumentoDTO> consultarDocumentosExpediente(
			ExpedienteDTO expedienteDTO, UsuarioDTO usuario)
			throws NSJPNegocioException {
		return consultarDocumentoService.consultarDocumentosExpediente(
				expedienteDTO, usuario);
	}

	@Override
	public DocumentoDTO cargarDocumentoPorId(Long documentoId)
			throws NSJPNegocioException {
		return cargarDocumentoService.cargarDocumentoPorId(documentoId);
	}

	@Override
	public List<DocumentoDTO> consultarDocumentosXTipoDocumento(
			ExpedienteDTO expedienteDTO, Long tipoDocumento)
			throws NSJPNegocioException {
		return consultarDocumentosXTipoDocumentoService
		.consultarDocumentosXTipoDocumento(expedienteDTO, tipoDocumento);
	}
	
	@Override
	public List<DocumentoDTO> consultarDocumentosSinActividadXExpedienteYTipoDocumento(
			ExpedienteDTO expedienteDTO, Long tipoDocumento)
			throws NSJPNegocioException {
		return consultarDocumentosXTipoDocumentoService
				.consultarDocumentosSinActividadXExpedienteYTipoDocumento(expedienteDTO, tipoDocumento);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DocumentoDTO> consultarDocumentoPorUsuario(
			UsuarioDTO usuarioDto, Long tipoDocumento)
			throws NSJPNegocioException {
		return consultarDocumentoPorUsuarioService
				.consultarDocumentoPorUsuario(usuarioDto, tipoDocumento);
	}

	public Long guardarActaAudiencia(DocumentoDTO documento,
			ExpedienteDTO expTrabajo) throws NSJPNegocioException {
		return guardarDocumentoService.guardarActaAudiencia(documento,
				expTrabajo);

	}

	public List<DocumentoDTO> consultarDocumentosExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return consultarDocumentoService
				.consultarDocumentosExpediente(expedienteDTO);
	}
		

	public ArchivoDigitalDTO consultarArchivoDitalSinContenidoPorActividad(Long idExpediente,
			Actividades actividad) throws NSJPNegocioException {
		return consultarContenidoArchivoDigitalService
				.consultarArchivoDitalSinContenidoPorActividad(idExpediente, actividad);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocumentoDTO consultarDocumentoXExpediente(
			ExpedienteDTO expedienteDto, Long tipoDocumento)
			throws NSJPNegocioException {
		return consultarDocumentoXExpediente(expedienteDto, tipoDocumento);
	}

	@Override
	public byte[] consultarContenidoArchivoDigitalPorArchivoODocumento(
			Long documentoId, Long archivoId) throws NSJPNegocioException {
		return consultarContenidoArchivoDigitalService
				.consultarContenidoArchivoDigitalPorArchivoODocumento(
						documentoId, archivoId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#
	 * consultarDocumentosPorNumeroExpediente
	 * (mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public List<DocumentoDTO> consultarDocumentosPorNumeroExpediente(
			ExpedienteDTO expediente) {
		return consultarDocumentoXExpedienteService
				.consultarDocumentosPorNumeroExpediente(expediente);
	}

	@Override
	public List<DocumentoDTO> consultarDocumentosAudienciaByTipoForma(
			AudienciaDTO audienciaDTO, TipoForma tipoForma)
			throws NSJPNegocioException {
		return this.consultarDocumentoService
				.consultarDocumentosAudienciaByTipoForma(audienciaDTO,
						tipoForma);
	}

	@Override
	public DocumentoDTO cargarDocumentoPorIdDocumentoYForma(Long idDocumento, FormaDTO forma) throws NSJPNegocioException{
		return cargarDocumentoService.cargarDocumentoPorIdDocumentoYForma(idDocumento, forma);
	}
	
	public void asociarDocuementoA(ResolutivoDTO resolutivo,
			DocumentoDTO documento) throws NSJPNegocioException {
		asociarDocumentoAService.asociarDocuementoA(resolutivo, documento);
	}

	@Override
	public DocumentoDTO cargarDocumentoPorId(Long documentoId,
			ExpedienteDTO expediente) throws NSJPNegocioException {
		return cargarDocumentoService.cargarDocumentoPorId(documentoId,
				expediente);
	}

	@Override
	public List<AvisoHechoDelictivoDTO> consultarAvisosHDelictivoPorEstatus(
			Long estatusNotificacion,Long discriminante) throws NSJPNegocioException {
		return consultarAvisosHDelictivoService.consultarAvisosHDelictivoPorEstatus(estatusNotificacion,discriminante);
	}

	@Override
	public NotificacionDTO actualizarEstatusNotificacion(
			NotificacionDTO notificacionDTO, Long estatusNotificacion)
			throws NSJPNegocioException {
		return actualizarNotificacionService.actualizarEstatusNotificacion(notificacionDTO, estatusNotificacion);
	}

	@Override
	public Long guardarAvisoHDelictivo(AvisoHechoDelictivoDTO avisoDTO)
			throws NSJPNegocioException {
		return guardarAvisoHDelictivoService.guardarAvisoHDelictivo(avisoDTO);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarIndicesEstructuradosPorTipoOficio(java.lang.Long)
	 */
	@Override
	public List<IndiceEstructuradoDTO> consultarIndicesEstructuradosPorTipoOficio(Long tipoOficio)
			throws NSJPNegocioException {
		return consultarIndicesEstructuradosService.consultarIndicesEstructuradosXTipoOficio(tipoOficio);
	}
	
	@Override
	public AvisoHechoDelictivoDTO consultarAvisoHDelictivo(
			AvisoHechoDelictivoDTO avisoDTO) throws NSJPNegocioException {
		return consultarAvisosHDelictivoService.consultarAvisoHDelictivo(avisoDTO);
	}

	@Override
	public DocumentoDTO consultarTeoriasDelCasoXExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return consultarTeoriaDelCasoService.consultarTeoriasDelCasoXExpediente(expedienteDTO);
	}
	
	@Override
	public DocumentoDTO consultarPliegoDeConsignacionXExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return consultarPliegoDeConsignacionService.consultarPliegoDeConsignacionXExpediente(expedienteDTO);
	}

	@Override
	public CuerpoOficioEstructuradoDTO consultarCuerpoOficio(Long cuerpoOficioId, Long indiceEstructuradoId)
			throws NSJPNegocioException {
		return consultarCuerpoOficioService.consultarCuerpoOficio(cuerpoOficioId, indiceEstructuradoId);
	}

	@Override
	public String guardarTeoriaDeCaso(DocumentoDTO documentoDTO)
			throws NSJPNegocioException {
		return guardarDocumentoService.guardarTeoriaDeCaso(documentoDTO);
	}

	@Override
	public Long guardarPliegoConsignacion(DocumentoDTO documentoDTO)
			throws NSJPNegocioException {
		return guardarDocumentoService.guardarPliegoConsignacion(documentoDTO);
	}
	
	@Override
	public List<CartaCumplimientoDTO> consultarControversiasResueltas(
			Long idTipoDocumento) throws NSJPNegocioException {
		return consultarControversiasResueltasService.consultarControversiasResueltas(idTipoDocumento);
	}

	@Override
	public void actualizarControversia(CartaCumplimientoDTO cumplimientoDTO)
			throws NSJPNegocioException {
		actualizarControversiaService.actualizarControversia(cumplimientoDTO);
	}

	@Override
	public Long guardarControversiaResuelta(CartaCumplimientoDTO cumplimientoDTO)
			throws NSJPNegocioException {
		return guardarControversiaResueltaService.guardarControversiaResuelta(cumplimientoDTO);
	}

	@Override
	public Long adjuntarArchivoDigitalAExpediente(ExpedienteDTO expedienteDTO,
			ArchivoDigitalDTO archivoDigitalDTO, FuncionarioDTO funcionarioDTO, Actividades actividad) throws NSJPNegocioException {
		return adjuntarArchivoDigitalAExpedienteService.adjuntarArchivoDigitalAExpediente(expedienteDTO, archivoDigitalDTO,funcionarioDTO, actividad);
	}
	
	
	@Override
	public List<ArchivoDigitalDTO> consultarArchivosDeSolicitud(Long IdSolicitud) throws NSJPNegocioException {
		return consultarArchivosDigitales.consultarArchivosDigitalesXSolicitud(IdSolicitud);		
	}
	
	@Override
	public List<DocumentoDTO> consultarArchivosDeSolicitudPericial(Long IdSolicitud,Boolean esPdf) throws NSJPNegocioException {
		return consultarArchivosDigitales.consultarArchivosDigitalesXSolicitudPericial(IdSolicitud,esPdf);		
	}
	
	@Override
	public ArchivoDigitalDTO consultarArchivoDigitalXElementoId(Long idElemento)
			throws NSJPNegocioException {
		return consultarContenidoArchivoDigitalService
				.consultarArchivoDigitalXElementoId(idElemento);
	}

	@Override
	public Boolean asociarArchivosDigitalesASolicitud(Long idSolicitud,
			String cadenaIds) throws Exception {
		return asociarArchivoService.asociarArchivosDigitalesASolicitud(idSolicitud, cadenaIds);		
	}

	@Override
	public List<ArchivoDigitalDTO> consultarArchivoDigitalPorMedida(
			MedidaDTO medidaDTO) throws NSJPNegocioException {
		return consultarContenidoArchivoDigitalService.consultarArchivoDigitalPorMedida(medidaDTO);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#asociarArchivoDigitalADocumento(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void asociarArchivoDigitalADocumento(Long documentoId,
			Long archivoDigitalId) throws NSJPNegocioException {
		asociarArchivoService.asociarArchivoDigitalADocumento(documentoId, archivoDigitalId);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarArchivoDigitalCompleto(java.lang.Long)
	 */
	@Override
	public ArchivoDigitalDTO consultarArchivoDigitalCompleto(Long documentoId)
			throws NSJPNegocioException {
		return consultarArchivosDigitales.consultarArchivoDigitalCompleto(documentoId);
	}

	@Override
	public DocumentoDTO cargarDocumentoPorExpedienteYForma(ExpedienteDTO expediente,
			FormaDTO tipoForma,Map<String,Object> parametrosExtras, String mandaFormaEnConsulta) throws NSJPNegocioException{
		return cargarDocumentoService.cargarDocumentoPorExpedienteYForma(expediente, tipoForma, parametrosExtras, mandaFormaEnConsulta);
	}
	/*
	 * (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarArchivoDigitalCompletoPorArchivoODocumento(java.lang.Long, java.lang.Long)
	 */
	@Override
	public ArchivoDigitalDTO consultarArchivoDigitalCompletoPorArchivoODocumento(
			Long documentoId, Long archivoId) throws NSJPNegocioException {
		return consultarContenidoArchivoDigitalService.consultarArchivoDigitalCompletoPorArchivoODocumento(documentoId, archivoId);
	}
	
	@Override
	public Long adjuntarDocumentoAExpediente(ExpedienteDTO expedienteDTO,
			DocumentoDTO documentoDTO, FuncionarioDTO funcionarioDTO, Actividades actividad, TipoDocumento tipoDocumento) throws NSJPNegocioException {
		return adjuntarArchivoDigitalAExpedienteService.adjuntarDocumentoAExpediente(expedienteDTO, documentoDTO,funcionarioDTO, actividad, tipoDocumento);
	}

	@Override
	public Long enviarDocumentoAInstitucion(List<DocumentoDTO> lstDocumentoDTO,
			String numeroDeCaso, Instituciones target)
			throws NSJPNegocioException {
		return aInstitucionService.enviarDocumentoAInstitucion(lstDocumentoDTO, numeroDeCaso, target);
	}
	
	
    /**
     * Consulta los Documentos que estén asociados a un expediente y  Usuario de Reinsercion Social
     * 
     * @param usuario
     *            El usuario del que se consultan sus documentos
     * @param documento
     *            Los datos del documento solicitado, por default el NumeroExpediente_id.
     * @return El listado de documentos asociados al Usuario. Si el usuario no
     *         existe en la base de datos o si no tiene documentos asociados, se
     *         regresa la lista vacia.
     */    
	public List<DocumentoDTO> consultarDocumentosReinsercionSocial(FuncionarioDTO funcionarioDTO, DocumentoDTO documentoDTO) throws NSJPNegocioException {
		return consultarDocumentoService.consultarDocumentosReinsercionSocial(funcionarioDTO, documentoDTO);
	}

	@Override
	public List<DocumentoDTO> consultarDocumentosAudiencia(
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {
		return consultarDocumentoService.consultarDocumentosAudiencia(audienciaDTO);
	}

	@Override
	public DocumentoDTO relacionarDocumentoAudiencia(AudienciaDTO audienciaDTO,
			DocumentoDTO documentoDTO) throws NSJPNegocioException {
		return asociarDocumentoAService.asociarDocumentoAAudiencia(audienciaDTO, documentoDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarDocumentoXId(java.lang.Long)
	 */
	@Override
	public DocumentoDTO consultarDocumentoXId(Long idDocumento) {
		return consultarDocumentoService.consultarDocumentoXId(idDocumento);
	}
	
	public DocumentoDTO crearDocumentoEslabonService(EslabonDTO eslabon, Boolean esGuardado)throws NSJPNegocioException{
		return crearDocumentoEslabonService.crearDocumentoEslabonService(eslabon, esGuardado);
	}

	@Override
	public void asociarDocumentoAEslabon(EslabonDTO eslabon,
			DocumentoDTO documento) throws NSJPNegocioException {
			asociarDocumentoAService.asociarDocumentoAEslabon(eslabon, documento);
	}	

	@Override
	/**
	 * M&eacute;todo que obtiene los parametros de campo forma para una
	 * plantilla de sentencia
	 * 
	 * @param sentenciaDTO id de la sentencia a consultar
	 * @param parametrosSentencia llaves con los campos forma
	 * @return parametrosSentencia con los valores deseados
	 * @throws NSJPNegocioException
	 */
	public Map<String, Object> getParametrosExtraSentecia (
			SentenciaDTO sentenciaDTO, Map<String, Object> parametrosSentencia)
			throws NSJPNegocioException {
		return cargarDocumentoService.getParametrosExtraSentecia(sentenciaDTO, parametrosSentencia);
	}

	@Override
	public List<DocumentoDTO> consultarDocumentosDeMandamientoJudicialPorExpediente(
			ExpedienteDTO expedienteDto, MandamientoDTO mandamientoDto,
			Long tipoDocumento) throws NSJPNegocioException {
	
		return consultarDocumentoService.consultarDocumentosDeMandamientoJudicialPorExpediente(expedienteDto, mandamientoDto,tipoDocumento);
	}
	
	@Override
	public DocumentoDTO consultarDocumentoFiltro(DocumentoDTO filtroDTO) throws NSJPNegocioException {
		DocumentoDTO documentoDTO = null;
		try {
			documentoDTO = consultarDocumentoService.consultarDocumentoFiltro(filtroDTO);
		}catch(Exception e) {
    		throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);  			 
		}
		return documentoDTO;
	}
	
	@Override
	public List<DocumentoDTO> consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo(
			ExpedienteDTO expedienteDto, MedidaCautelarDTO medidaCautelarDTO,
			Long tipoDocumento) throws NSJPNegocioException {
	
		return consultarDocumentoService.consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo(expedienteDto, medidaCautelarDTO,tipoDocumento);
	}

	@Override
	public DocumentoDTO consultarUltimoDocumentoPorActividadYExpedienteId(
			DocumentoDTO filtroDTO) throws NSJPNegocioException {
		return consultarDocumentoService.consultarUltimoDocumentoPorActividadYExpedienteId(filtroDTO);
	}
	
	
	@Override
	public Long ejecutaGuardadoDefinitivo(GuardadoDefinitivoDTO aoGuardadoDefinitivo) throws NSJPNegocioException {
		return guardarDocumentoService.ejecutaGuardadoDefinitivo(aoGuardadoDefinitivo);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarDocumentosPorExpedienteYTipos(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO, java.util.List)
	 */
	@Override
	public List<DocumentoDTO> consultarDocumentosPorExpedienteYTipos(
			ExpedienteDTO expediente, List<ValorDTO> tiposDocumento)
			throws NSJPNegocioException {
		return consultarDocumentosXTipoDocumentoService.consultarDocumentosPorExpedienteYTipos(expediente, tiposDocumento);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarDocumentosIntegracionXExpediente(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public Map<Long, DocumentoDTO> consultarDocumentosIntegracionXExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		return consultarDocumentoService.consultarDocumentosIntegracionXExpediente(expedienteDTO);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarDocumentosIntegracion(java.lang.String, boolean)
	 */
	@Override
	public List<DocumentoIntegracionDTO> consultarDocumentosIntegracion(
			String campoOrdenamiento, boolean ascendente) {
		return consultarDocumentoService.consultarDocumentosIntegracion(campoOrdenamiento, ascendente);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarDocumentosPorArchivosDigitales(java.util.List)
	 */
	@Override
	public Map<Long, DocumentoDTO> consultarDocumentosPorArchivosDigitales(
			List<Long> lstArchivoDigitalId) throws NSJPNegocioException {
		return consultarArchivosDigitales.consultarDocumentosPorArchivosDigitales(lstArchivoDigitalId);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarRelacionesPorDocPrincipal(mx.gob.segob.nsjp.dto.documento.DocumentoDTO)
	 */
	@Override
	public List<RelacionDocumentoDTO> consultarRelacionesPorDocPrincipal(
			DocumentoDTO doc) throws NSJPNegocioException {
		return consultarDocumentoService.consultarRelacionesPorDocPrincipal(doc);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#guardarRelacionDocumento(mx.gob.segob.nsjp.dto.documento.RelacionDocumentoDTO)
	 */
	@Override
	public RelacionDocumentoDTO guardarRelacionDocumento(
			RelacionDocumentoDTO relacion) {
		return guardarDocumentoService.guardarRelacionDocumento(relacion);
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate#consultarSolicitudesMandamientoPorDestinatario(mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public List<SolicitudMandamientoDTO> consultarSolicitudesMandamientoPorDestinatario(
			FuncionarioDTO destinatario, List<ValorDTO> estatusSolicitud,
			List<ValorDTO> tipoMandamiento, List<ValorDTO> tipoSolicitud)
			throws NSJPNegocioException {
		return consultarDocumentoService.consultarSolicitudesMandamientoPorDestinatario(
				destinatario, estatusSolicitud, tipoMandamiento, tipoSolicitud);
	}
	
	@Override
	public Boolean existeDocumentoPorTipoActividadPorExpedienteId(
			Long expedienteId, Long tipoActividad) throws NSJPNegocioException {
		return consultarDocumentoService.existeDocumentoPorTipoActividadPorExpedienteId(expedienteId, tipoActividad);
	}

	@Override
	public Long adjuntarDocumento(DocumentoDTO documentoDTO,
			FuncionarioDTO funcionarioDTO) throws NSJPNegocioException {
		return adjuntarArchivoDigitalAExpedienteService.adjuntarDocumento( documentoDTO,funcionarioDTO);
	}
	
}
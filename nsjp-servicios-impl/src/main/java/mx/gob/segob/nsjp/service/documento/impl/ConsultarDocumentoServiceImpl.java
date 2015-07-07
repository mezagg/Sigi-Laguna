/**
* Nombre del Programa : ConsultarDocumentoServiceImpl.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 2 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Implementacion del servicio para realizar las consultas de Documento
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
package mx.gob.segob.nsjp.service.documento.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoForma;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.documento.AudienciaDocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoIntegracionDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoAdjuntosDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dao.documento.RelacionDocumentoDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.medida.MedidaAdjuntosDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudMandamientoDAO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoIntegracionDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MedidaCautelarDTO;
import mx.gob.segob.nsjp.dto.documento.RelacionDocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Actividad;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.DocumentoIntegracion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.RelacionDocumento;
import mx.gob.segob.nsjp.model.SolicitudMandamiento;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoIntegracionTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.DocumentoTransformer;
import mx.gob.segob.nsjp.service.documento.impl.tranform.RelacionDocumentoTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ActividadTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.funcionario.impl.transform.FuncionarioTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.SolicitudTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion del servicio para realizar las consultas de Documento.
 * @version 1.0
 * @author cesarAgustin
 *
 */
@Service
@Transactional
public class ConsultarDocumentoServiceImpl implements ConsultarDocumentoService {

	/**
	 * 
	 */
	public final static Logger LOGGER = Logger.getLogger(ConsultarDocumentoServiceImpl.class); 

	@Autowired
	private DocumentoDAO documentoDAO;
	@Autowired
	private NumeroExpedienteDAO numeroExpedienteDAO;
	@Autowired
	private AudienciaDocumentoDAO audienciaDocumentoDAO;
	@Autowired
	private MandamientoAdjuntosDAO mandamientoAdjuntoDAO;
	@Autowired
	private MedidaAdjuntosDAO medidaAdjuntosDAO;
	@Autowired
	private DocumentoIntegracionDAO documentoIntegracionDAO;
	@Autowired
	private RelacionDocumentoDAO relacionDocumentoDAO;
	@Autowired
	private SolicitudMandamientoDAO solicitudMandamientoDAO;
	@Autowired
	private MandamientoDAO mandamientoDAO;
	@Autowired
	private ValorDAO valorDAO;
	
	@Override
	public List<DocumentoDTO> consultarDocumentosExpediente(ExpedienteDTO expedienteDTO, UsuarioDTO usuarioDTO)
			throws NSJPNegocioException {

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE ****/");
		
		if (expedienteDTO==null || usuarioDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		
		Long idAreaActual = expedienteDTO.getIdAreaActual();
		
		List<Documento> documentosExpediente = null;
		
		if(expedienteDTO.getResponsableTramite() == null){
			documentosExpediente = documentoDAO.consultarDocumentoPorExpediente(expedienteDTO.getExpedienteId(),null,
					expedienteDTO.getIdsJerarquiasOrganizacionales(),idAreaActual);
		}else{
			documentosExpediente = documentoDAO.consultarDocumentoPorExpediente(expedienteDTO.getExpedienteId(),expedienteDTO.getResponsableTramite().getClaveFuncionario(),
					expedienteDTO.getIdsJerarquiasOrganizacionales(),idAreaActual);
		}

		
		List<DocumentoDTO> documentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento documento : documentosExpediente) {
			if (usuarioDTO.getArea().getAreaId().equals(new Long(Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal()))) {
				if (documento.getForma().getFormaId().equals(new Long(1)))
					documentosDTO.add(DocumentoTransformer.transformarDocumento(documento));
			}			
		}
		
		return documentosDTO;
	}
	
	@Override
	public List<DocumentoDTO> consultarDocumentosExpediente(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {

		if (LOGGER.isDebugEnabled()){
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE ****/");
		}
		
		Long idAreaActual = expedienteDTO.getIdAreaActual();
		
		if (expedienteDTO.getNumeroExpedienteId() == null
				&& expedienteDTO.getExpedienteId() == null
				&& expedienteDTO.getNumeroExpedienteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}
					
		if (expedienteDTO.getExpedienteId() == null
				|| expedienteDTO.getExpedienteId() <= 0L) {
			NumeroExpediente loNumExp = numeroExpedienteDAO.read(expedienteDTO.getNumeroExpedienteId());    	
			expedienteDTO.setExpedienteId(loNumExp.getExpediente().getExpedienteId());
		}
		
		List<Documento> documentosExpediente = null;
		if(expedienteDTO.getResponsableTramite() == null){
			documentosExpediente = documentoDAO.consultarDocumentoPorExpediente(expedienteDTO.getExpedienteId(),null,
					expedienteDTO.getIdsJerarquiasOrganizacionales(),idAreaActual);
		}else{
			documentosExpediente = documentoDAO.consultarDocumentoPorExpediente(expedienteDTO.getExpedienteId(),expedienteDTO.getResponsableTramite().getClaveFuncionario(), 
					expedienteDTO.getIdsJerarquiasOrganizacionales(), idAreaActual);
		}
		
		Valor loDirigiraUI = valorDAO.read(Actividades.DIRIGIR_A_LA_UNIDAD_DE_FISCALES_INVESTIGADORES.getValorId());
		Valor loDirigiraJAR = valorDAO.read(Actividades.DIRIGIR_A_LA_UNIDAD_DE_SOLUCION_DE_CONTROVERSIAS.getValorId());
		
		List<DocumentoDTO> documentosDTO = new ArrayList<DocumentoDTO>();
		
		for (Documento documento : documentosExpediente) {
			// Se cambia el nombre del tipo de actividad del documento de canalizacion a UI
			if(	documento.getActividad() != null && documento.getActividad().getTipoActividad() != null &&
					documento.getActividad().getTipoActividad().getValorId().longValue() == Actividades.RECIBIR_CANALIZACION_UI.getValorId()){
				
				documento.getActividad().setTipoActividad(loDirigiraUI);
				
			}
			// Se cambia el nombre del tipo de actividad del documento de canalizacion a JAR
			if(documento.getActividad() != null && documento.getActividad().getTipoActividad() != null &&
					documento.getActividad().getTipoActividad().getValorId().longValue() == Actividades.RECIBIR_CANALIZACION_JAR.getValorId()){
				
				documento.getActividad().setTipoActividad(loDirigiraJAR);
				
			}
				documentosDTO.add(DocumentoTransformer.transformarDocumento(documento));
		}		
		return documentosDTO;
	}

	@Override
	public List<DocumentoDTO> consultarDocumentosAudienciaByTipoForma(
			AudienciaDTO audienciaDTO, TipoForma tipoForma) throws NSJPNegocioException {
		List<Documento> documentos = documentoDAO.consultarDocumentosAudienciaByTipoForma(audienciaDTO.getId(), tipoForma.getValorId());
		List<DocumentoDTO> documentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento documento : documentos) {
			documentosDTO.add(DocumentoTransformer.transformarDocumento(documento));
		}
		
		return documentosDTO;
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
		Funcionario funcionario = FuncionarioTransformer.transformarFuncionario(funcionarioDTO);
		if (funcionario != null 
				&& funcionario.getUsuario() == null){
			funcionario.setUsuario(UsuarioTransformer.transformarUsuarioMinimo(funcionarioDTO.getUsuario()));
		}
		Documento documento = DocumentoTransformer.transformarDocumentoDTO(documentoDTO);
		ExpedienteDTO expedienteDTO = documentoDTO.getExpedienteDTO();
		NumeroExpediente numeroExpediente = new NumeroExpediente(
				expedienteDTO.getNumeroExpedienteId(),
				expedienteDTO.getNumeroExpediente(),
				null);
		
		List<Documento> lstDocumentos = documentoDAO.consultarDocumentosReinsercionSocial(funcionario, documento, numeroExpediente);
		List<DocumentoDTO>  lstDocumentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento tmp : lstDocumentos){
			DocumentoDTO tmpDTO = DocumentoTransformer.transformarDocumento(tmp);
			lstDocumentosDTO.add(tmpDTO);
		}
		
		return lstDocumentosDTO;
	}

	@Override
	public List<DocumentoDTO> consultarDocumentosAudiencia(
			AudienciaDTO audienciaDTO) throws NSJPNegocioException {
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE ****/");
		
		if (audienciaDTO==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);		
		if (audienciaDTO.getId()==null)
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);

		List<Documento> lstDocumentos = audienciaDocumentoDAO.consultarDocumentosAudiencia(audienciaDTO.getId());
		List<DocumentoDTO>  lstDocumentosDTO = new ArrayList<DocumentoDTO>();
		for (Documento tmp : lstDocumentos){
			DocumentoDTO tmpDTO = DocumentoTransformer.transformarDocumento(tmp);
			lstDocumentosDTO.add(tmpDTO);
		}
		
		return lstDocumentosDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService#consultarDocumentoXId(java.lang.Long)
	 */
	@Override
	public DocumentoDTO consultarDocumentoXId(Long idDocumento) {
		Documento d = documentoDAO.read(idDocumento);
		return DocumentoTransformer.transformarDocumento(d);
	}

	
	@Override
	public List<DocumentoDTO> consultarDocumentosDeMandamientoJudicialPorExpediente(
			ExpedienteDTO expedienteDto, MandamientoDTO mandamientoDto,
			Long tipoDocumento) throws NSJPNegocioException {

		LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE POR TIPO ****/");

		if (tipoDocumento == null || tipoDocumento <= 0L
				|| mandamientoDto == null
				|| mandamientoDto.getDocumentoId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<DocumentoDTO> lstDocumentosDTO = new ArrayList<DocumentoDTO>();
		List<Documento> lstDocumentos = new ArrayList<Documento>();

		// Si el tipo de documento es archivo adjunto
		if (tipoDocumento.equals(TipoDocumento.ARCHIVO_ADJUNTADO.getValorId())) {
			lstDocumentos = mandamientoAdjuntoDAO
					.consultarDocumentoMandamientoAdjuntoPorMandamientoId(mandamientoDto
							.getDocumentoId());
		} else if (tipoDocumento
				.equals(TipoDocumento.REPORTE_CUMPLIMIENTO_MANDAMIENTO_JUDICIAL
						.getValorId())) {

			lstDocumentos = mandamientoDAO
					.consultarDocumentosRelacionadosMandamientoPorTipo(
							mandamientoDto.getDocumentoId(), tipoDocumento);
			
			// Buscamos tambien los documento de seguimiento a oficio de
			// cumplimiento
			lstDocumentos
					.addAll(mandamientoDAO.consultarDocumentosRelacionadosMandamientoPorTipo(
							mandamientoDto.getDocumentoId(),
							TipoDocumento.SEGUIMIENTO_A_OFICIO_DE_CUMPLIMIENTO_DE_MANDAMIENTO_JUDICIAL
									.getValorId()));

			// Buscamos los documentos de la solicitud mandamiento (Solo deberia
			// existir uno)
			SolicitudMandamientoDTO solicitudMandamientoDTO = new SolicitudMandamientoDTO();
			MandamientoDTO mandamientoDTO = new MandamientoDTO();
			mandamientoDTO.setDocumentoId(mandamientoDto.getDocumentoId());
			solicitudMandamientoDTO.setMandamiento(mandamientoDTO);

			// las agregamos a la lista de documentos
			lstDocumentos
					.addAll(solicitudMandamientoDAO
							.consultarSolicitudesMandatoJudicialPorFiltro(SolicitudTransformer
									.transformarSolicitudMandamientoBasico(solicitudMandamientoDTO)));
		}

		for (Documento tmp : lstDocumentos) {
			DocumentoDTO tmpDTO = DocumentoTransformer
					.transformarDocumento(tmp);
			lstDocumentosDTO.add(tmpDTO);
		}

		return lstDocumentosDTO;
	}

	
	@Override
	public List<DocumentoDTO> consultarDocumentosDeMedidasCautelaresPorExpedienteYTipo(
			ExpedienteDTO expedienteDto, MedidaCautelarDTO medidaCautelarDTO,
			Long tipoDocumento) throws NSJPNegocioException {

		LOGGER.debug("/**** SERVICIO PARA CONSULTAR DOCUMENTOS DE UN EXPEDIENTE POR TIPO ****/");

		if (tipoDocumento == null || tipoDocumento <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		} else {
			if (tipoDocumento.equals(TipoDocumento.ARCHIVO_ADJUNTADO
					.getValorId())) {
				if (medidaCautelarDTO == null
						|| medidaCautelarDTO.getDocumentoId() == null) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
			} else {
				if (expedienteDto == null
						|| expedienteDto.getNumeroExpedienteId() == null
						|| expedienteDto.getNumeroExpedienteId() <= 0L) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
			}
		}

		List<DocumentoDTO> lstDocumentosDTO = new ArrayList<DocumentoDTO>();
		List<Documento> lstDocumentos = null;

		/**
		 * Si el tipo de documento que se busca es diferente a archivo adjuntado
		 * (es decir de tipo medida cautelar o de tipo cambio de estado de
		 * medida cautelar)
		 */
		if (!tipoDocumento.equals(TipoDocumento.ARCHIVO_ADJUNTADO.getValorId())) {
			lstDocumentos = documentoDAO
					.consultarDocumentosXExpedienteYTipoDocumento(
							expedienteDto.getNumeroExpedienteId(),
							tipoDocumento);
		} else { // Si el tipo de documento es Archivo Adjunto a la medida cautelar

			lstDocumentos = medidaAdjuntosDAO
					.consultarDocumentosMedidaCautelarAdjuntosPorMedidaCautelarId(medidaCautelarDTO
							.getDocumentoId());
		}

		for (Documento tmp : lstDocumentos) {
			DocumentoDTO tmpDTO = DocumentoTransformer
					.transformarDocumento(tmp);
			lstDocumentosDTO.add(tmpDTO);
		}

		return lstDocumentosDTO;
	}
	
	
	@Override
	public DocumentoDTO consultarDocumentoFiltro(DocumentoDTO filtroDTO) throws NSJPNegocioException {
		DocumentoDTO documentoDTO = null;
		try {
			Documento filtro = DocumentoTransformer.transformarDocumentoDTO(filtroDTO);
			if (filtro != null  && filtroDTO != null){
				if(filtroDTO.getActividadDTO() != null ) {
					filtro.setActividad(ActividadTransformer.transformarActividadDTO(filtroDTO.getActividadDTO()));
				}
				
				if(filtroDTO.getExpedienteDTO()!= null){
					Actividad actividad = filtro.getActividad() != null ? filtro.getActividad() : new Actividad();
					Expediente expediente = ExpedienteTransformer.transformarExpediente(filtroDTO.getExpedienteDTO());
					expediente.setNumeroExpedienteId(filtroDTO.getExpedienteDTO().getNumeroExpedienteId());
					actividad.setExpediente(expediente);
					filtro.setActividad(actividad);
				}
				Documento  documento = documentoDAO.consultarDocumentoFiltro(filtro);
				documentoDTO =  DocumentoTransformer.transformarDocumento(documento);
			} 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
    		throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA, e);
		}
		
		return documentoDTO;
	
	}
	
	@Override
	public DocumentoDTO consultarUltimoDocumentoPorActividadYExpedienteId(
			DocumentoDTO filtroDTO) throws NSJPNegocioException {

		if (filtroDTO == null
				|| filtroDTO.getActividadDTO() == null
				|| filtroDTO.getActividadDTO().getTipoActividad() == null
				|| filtroDTO.getActividadDTO().getTipoActividad().getValorId() == null
				|| filtroDTO.getExpedienteDTO() == null
				|| filtroDTO.getExpedienteDTO().getNumeroExpedienteId() == null) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<Documento> lstDocumentos = null;

		DocumentoDTO tmpDTO = null;

		lstDocumentos = documentoDAO
				.consultarDocumentosPorTipoActividadYNumExpedienteId(filtroDTO
						.getExpedienteDTO().getNumeroExpedienteId(), filtroDTO
						.getActividadDTO().getTipoActividad().getValorId());

		if (lstDocumentos != null && lstDocumentos.size() > 0) {
			tmpDTO = DocumentoTransformer.transformarDocumento(lstDocumentos
					.get(0));
		}
		
		return tmpDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService#consultarDocumentosIntegracionXExpediente(mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public Map<Long, DocumentoDTO> consultarDocumentosIntegracionXExpediente(
			ExpedienteDTO expedienteDTO) throws NSJPNegocioException {
		Expediente expConsulta = ExpedienteTransformer.transformarExpedienteBasicoDefensoria(expedienteDTO);
		List<Documento> documentosIntegrados = documentoIntegracionDAO.consultarDocumentosIntegracionXExpediente(expConsulta);
		Map<Long, DocumentoDTO> mapaDoctos = new HashMap<Long, DocumentoDTO>();
		if (documentosIntegrados != null
				&& !documentosIntegrados.isEmpty()){
			for (Documento d : documentosIntegrados){
				mapaDoctos.put(d.getTipoDocumento().getValorId(), DocumentoTransformer.transformarDocumento(d));
			}
		}
		return mapaDoctos;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService#consultarDocumentosIntegracion(java.lang.String, boolean)
	 */
	@Override
	public List<DocumentoIntegracionDTO> consultarDocumentosIntegracion(
			String campoOrdenamiento, boolean ascendente) {
		List<DocumentoIntegracionDTO> documentosIntegracionDTO = null;
		List<DocumentoIntegracion> documentosIntegracion = documentoIntegracionDAO.findAll(campoOrdenamiento, ascendente);
		if (documentosIntegracion != null
				&& !documentosIntegracion.isEmpty()){
			documentosIntegracionDTO = new ArrayList<DocumentoIntegracionDTO>();
			for (DocumentoIntegracion di : documentosIntegracion){
				documentosIntegracionDTO.add(DocumentoIntegracionTransformer.transformar(di));
			}
		}
		return documentosIntegracionDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService#consultarRelacionesPorDocPrincipal(mx.gob.segob.nsjp.dto.documento.DocumentoDTO)
	 */
	@Override
	public List<RelacionDocumentoDTO> consultarRelacionesPorDocPrincipal(
			DocumentoDTO doc) throws NSJPNegocioException {
		Documento docFiltro = DocumentoTransformer.transformarDocumentoDTO(doc);
		List<RelacionDocumentoDTO> relacionesDTO = null;
		
		List<RelacionDocumento> relaciones = relacionDocumentoDAO.consultarRelacionesPorDocPrincipal(docFiltro);
		if (relaciones != null
				&& !relaciones.isEmpty()){
			relacionesDTO = new ArrayList<RelacionDocumentoDTO>();
			for (RelacionDocumento rd : relaciones){
				relacionesDTO.add(RelacionDocumentoTransformer.transformar(rd));
			}
		}
		return relacionesDTO;
	}

	/* (non-Javadoc)
	 * @see mx.gob.segob.nsjp.service.documento.ConsultarDocumentoService#consultarSolicitudesMandamientoPorDestinatario(mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public List<SolicitudMandamientoDTO> consultarSolicitudesMandamientoPorDestinatario(
			FuncionarioDTO destinatario, List<ValorDTO> estatusSolicitud,
			List<ValorDTO> tipoMandamiento, List<ValorDTO> tipoSolicitud)
			throws NSJPNegocioException {
		
		List<SolicitudMandamientoDTO> solicitudesDTO = null;
		
		Funcionario funcFilter = FuncionarioTransformer.transformarFuncionario(destinatario);
		List<Valor> estatusSolicitudes = ValorTransformer.transformar(estatusSolicitud);
		List<Valor> tiposSolicitudes = ValorTransformer.transformar(tipoSolicitud);
		List<Valor> tiposMandamientos = ValorTransformer.transformar(tipoMandamiento);
		
		List<SolicitudMandamiento> solicitudesMandamientoBD = 
			solicitudMandamientoDAO.consultarSolicitudesMandamientoPorDestinatario(
					funcFilter, estatusSolicitudes, tiposMandamientos, tiposSolicitudes);
		
		if (solicitudesMandamientoBD != null 
				&& !solicitudesMandamientoBD.isEmpty()){
			solicitudesDTO = new ArrayList<SolicitudMandamientoDTO>();
			for (SolicitudMandamiento sm : solicitudesMandamientoBD){
				solicitudesDTO.add(SolicitudTransformer.solicitudTransformer(sm));
			}
		}
		
		return solicitudesDTO;
	}
	
	@Override
	public Boolean existeDocumentoPorTipoActividadPorExpedienteId(
			Long expedienteId, Long tipoActividad) throws NSJPNegocioException {

		if (expedienteId == null || expedienteId <= 0
				|| tipoActividad == null || tipoActividad <= 0) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		List<Documento> lstDocumentos = documentoDAO.consultarDocumentosPorTipoActividadYExpedienteId(
				expedienteId, tipoActividad);

		if (lstDocumentos != null && lstDocumentos.size() > 0) {
			return true;
		}
		
		return false;
	}
}

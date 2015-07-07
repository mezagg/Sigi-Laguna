/**
 * Nombre del Programa : DocumentosReinsercionAction.java
 * Autor                            : AntonioBV
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 27/01/2012
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
package mx.gob.segob.nsjp.web.reinsercionsocial.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.actividad.ActividadesRS;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.actividad.ConfActividadDocumentoDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.sentencia.SentenciaDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.actividad.ConfTipoActividadOrigenDestinoDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.RelacionDocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.reinsercionsocial.form.DetalleSolicitudRSForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author AntonioBV
 * 
 */
@SuppressWarnings("unused")
public class DocumentosReinsercionAction extends GenericAction {

	/* Log de clase */
	private static final Logger LOG = Logger
			.getLogger(DocumentosReinsercionAction.class);
	
	private static final String VALOR_DEFAULT_ID_ACTUACION = "0";
	private static final String VALOR_DEFAULT_NOMBRE_ACTUACION = "";
	private static final String VALOR_DELIMITADOR_TIPOS_DOCUMENTO=",";
	private static final String ATTR_RECORD_XML_OPEN = ConstantesGenerales.XML_CELL_OPEN+ConstantesGenerales.C_DATA_OPEN+ConstantesGenerales.DIV_TAG_CLASS_CELDA_GRID_OPEN;
	private static final String ATTR_RECORD_XML_CLOSE = ConstantesGenerales.DIV_TAG_CLOSE+ConstantesGenerales.C_DATA_CLOSE+ConstantesGenerales.XML_CELL_CLOSE;
	public static final String ESTATUS_DOCUMENTO_PARCIAL = "Guardado Parcial";
	public static final String ESTATUS_DOCUMENTO_DEFINITIVO = "Guardado Definitivo";
	private static final String PARAM_ID_TIPO_DOCUMENTO = "idTipoDocto";
	private static final String PARAM_ID_NUMERO_EXPEDIENTE = "idNumeroExpediente";
	private static final String PARAM_ID_EXPEDIENTE = "idExpediente";
	private static final String PARAM_TIPO_CONSULTA = "tipoConsulta";
	private static final String PARAM_ID_SENTENCIA = "sentenciaId";
	private static final String PARAM_TIPO_SOLICITUD = "tipoSolicitud";
	private static final String PARAM_ESTATUS = "estatus";
	private static final String PARAM_ID_SOLICITUD = "solicitudId";
	private static final String PARAM_ID_DOC_PRINCIPAL = "idDocPrincipal";
	private static final String PARAM_ID_DOC_RELACIONADO = "idDocRelacionado";
	
	private static final String KEY_JSON_ID_DOCUMENTO = "idDocumento";
	private static final String KEY_JSON_NOMBRE_DOCUMENTO = "nombreDocumento";
	private static final String KEY_JSON_ID_TIPO_DOCUMENTO = "idTipoDocumento";
	private static final String KEY_JSON_NOMBRE_TIPO_DOCUMENTO = "nombreTipoDocumento";
	
	private static final String FWD_DETALLES_SOLICITUD = "detallesSolicitud";
	
	@Autowired
	private SolicitudDelegate solicitudDelegate;
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	private ConfActividadDocumentoDelegate confActividadDocumentoDelegate;
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	@Autowired
	private AsignacionProgramaDelegate asignacionProgramaDelegate;
	@Autowired
	private SentenciaDelegate sentenciaDelegate;
	
	/***
	 * Metodo para mandar consultar las solicitudes por atender con cierto
	 * estatus,tipo y area
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDocumentosConCriterios(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			LOG.info("ejecutando Action DocumentosReinsercionAction en metodo consultarDocumentosConCriterios:#####");

			String tipoSolicitud = request.getParameter("tipoSoliciutd");
			String estatusSolicitud = request.getParameter("estatus");
			String areaSolicitud = request.getParameter("idArea");
			String tipoConsulta = request.getParameter(PARAM_TIPO_CONSULTA);
			String idNumeroExpediente = request
					.getParameter("numeroExpedienteId");
			String cNumeroExpediente = request
					.getParameter("cNumeroExpediente");

			Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId"),0);
			
			// construimos la lista de los tipos de solicitud a consultar
			tipoSolicitud = tipoSolicitud != null ? tipoSolicitud : "";
			String[] idTiposols = tipoSolicitud.split(",");

			LOG.info("idsTipoSols:: " + tipoSolicitud);
			List<Long> idsTipSols = new ArrayList<Long>();

			LOG.info("arrIDsTipoSols:: " + idTiposols);
			
			if (!tipoSolicitud.equals("")) {
				for (String long1 : idTiposols) {
					idsTipSols.add(Long.parseLong(long1));
				}
			}

			// construimos la lista de los estatus a consultar
			estatusSolicitud = estatusSolicitud != null ? estatusSolicitud : "";
			String[] idEstatus = estatusSolicitud.split(",");
			List<Long> idsEstatus = new ArrayList<Long>();
			LOG.info("arrIDsEstatus:: " + idEstatus);
			if (!estatusSolicitud.equals("")) {
				for (String long1 : idEstatus) {
					idsEstatus.add(Long.parseLong(long1));
				}
			}
			// consultamos las solicitudes por atender
			UsuarioDTO loUsuario = super.getUsuarioFirmado(request);

			FuncionarioDTO funcionarioDTO = loUsuario.getFuncionario();

			Long claveAgencia = null;

			if (funcionarioDTO == null) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			if (funcionarioDTO.getDiscriminante() != null
					&& funcionarioDTO.getDiscriminante()
							.getCatDiscriminanteId() != null) {
				claveAgencia = funcionarioDTO.getDiscriminante()
						.getCatDiscriminanteId();
			}

			UsuarioDTO usuarioDTO = new UsuarioDTO();
			UsuarioRolDTO usuarioRolDTO = loUsuario.getRolACtivo();
			usuarioRolDTO.setEsPrincipal(Boolean.TRUE);
			Set<UsuarioRolDTO> lstRoles = new HashSet<UsuarioRolDTO>();
			lstRoles.add(usuarioRolDTO);
			usuarioDTO.setUsuarioRoles(lstRoles);
			funcionarioDTO.setUsuario(usuarioDTO);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			if (idNumeroExpediente != null && idNumeroExpediente != "") {
				expedienteDTO.setNumeroExpedienteId(Long
						.parseLong(idNumeroExpediente));
			}

			expedienteDTO.setNumeroExpediente(cNumeroExpediente);

			if (tipoConsulta.equals(SolicitudDTO.PARCIALES)) {
				DocumentoDTO documentoDTO = new DocumentoDTO();
				documentoDTO.setExpedienteDTO(expedienteDTO);
				List<DocumentoDTO> listaDocumentos = documentoDelegate
						.consultarDocumentosReinsercionSocial(
								funcionarioDTO, documentoDTO);
				final PaginacionDTO pag = PaginacionThreadHolder.get();
				writer = llenarXMLDocumentos(listaDocumentos, writer, pag, tipoConsulta, null);
			
			} else if (tipoConsulta.equals(SolicitudDTO.ADJUNTOS)) {
				List<DocumentoDTO> listaDocumentosDTO = 
						documentoDelegate.consultarArchivosDeSolicitudPericial(solicitudId,null);
				SolicitudDTO solicitudOriginal = solicitudDelegate.consultarSolicitudXId(solicitudId);
				StringBuilder remitente = new StringBuilder("");
				if (solicitudOriginal != null
						&& solicitudOriginal.getSolicitanteInstExterna() != null){
					FuncionarioExternoDTO funRemitente = solicitudOriginal.getSolicitanteInstExterna();
					remitente = remitente.append(funRemitente.getNombre()+" "+
							funRemitente.getApellidoPaterno()+" "+funRemitente.getApellidoMaterno());
				}
				final PaginacionDTO pag = PaginacionThreadHolder.get();
				writer = llenarXMLDocumentos(listaDocumentosDTO, writer, pag, tipoConsulta, remitente.toString());

			} else {
				SolicitudDTO solicitudDTO = new SolicitudDTO();
				solicitudDTO.setExpedienteDTO(expedienteDTO);

				if (areaSolicitud != null && areaSolicitud != "") {
					solicitudDTO.setAreaOrigen(Long.parseLong(areaSolicitud));
				}

				if (tipoConsulta.equals(SolicitudDTO.GENERADAS)) {
					solicitudDTO.setUsuarioSolicitante(loUsuario
							.getFuncionario());
				} else if (tipoConsulta.equals(SolicitudDTO.POR_ATENDER)) {
					solicitudDTO.setDestinatario(funcionarioDTO);
				}

				List<SolicitudDTO> listaSolicitudes = solicitudDelegate
						.consultarSolicitudesConCriterios(solicitudDTO,
								idsEstatus, idsTipSols, tipoConsulta);
				final PaginacionDTO pag = PaginacionThreadHolder.get();
				Map<Long, DocumentoDTO> documentosAdjuntos = obtenerDocumentosAdjuntos(listaSolicitudes);
				writer = llenarXMLSolicitudes(listaSolicitudes, writer, pag, documentosAdjuntos, tipoConsulta);
			}
			// generamos el HTML del grid

			writer.flush();
			writer.close();

		} catch (Exception e) {
			LOG.info(e.getCause(), e);
		}
		return null;
	}

	/**
	 * @param listaSolicitudes
	 * @param writer
	 */
	private PrintWriter llenarXMLSolicitudes(
			List<SolicitudDTO> listaSolicitudes, PrintWriter writer,
			PaginacionDTO pag, Map<Long,DocumentoDTO> documentosAdjuntos, String tipoConsulta) {
		writer.print("<rows>");
		if (pag != null && pag.getTotalRegistros() != null) {
			writer.print("<total>" + pag.getTotalPaginas() + "</total>");
			writer.print("<records>" + pag.getTotalRegistros() + "</records>");
		} else {
			writer.print("<total>0</total>");
			writer.print("<records>0</records>");
		}		
		for (SolicitudDTO solicitudDTO : listaSolicitudes) {
			DocumentoDTO docAdjunto = obtenerDocumentoAdjuntoDeSolicitud(solicitudDTO, documentosAdjuntos);
			writer.print("<row id='"+solicitudDTO.getDocumentoId()+"'>");

			if (solicitudDTO.getExpedienteDTO() == null
					|| solicitudDTO.getExpedienteDTO().getCasoDTO() == null
					|| solicitudDTO.getExpedienteDTO().getCasoDTO()
							.getNumeroGeneralCaso() == null) {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
			} else {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"
						+ solicitudDTO.getExpedienteDTO().getCasoDTO()
								.getNumeroGeneralCaso() + "</div>]]></cell>");
			}

			if (solicitudDTO.getExpedienteDTO() == null) {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
			} else {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"
						+ solicitudDTO.getExpedienteDTO().getNumeroExpediente()
						+ "</div>]]></cell>");
			}

			writer.print("<cell><![CDATA[<div class='celdaGrid'>"
					+ solicitudDTO.getFolioSolicitud() + "</div>]]></cell>");
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"
					+ solicitudDTO.getEstatus().getValor()
					+ "</div>]]></cell>");
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"
					+ DateUtils.formatear(solicitudDTO.getFechaCreacion())
					+ "</div>]]></cell>");
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"
					+ DateUtils.formatear(solicitudDTO.getFechaLimite())
					+ "</div>]]></cell>");
			writer.print(ATTR_RECORD_XML_OPEN);
			if (solicitudDTO.getDestinatarioInstExterna() != null){
				writer.print(solicitudDTO.getDestinatarioInstExterna().getConfInstitucionDTO().getNombreInst());
			}else if (solicitudDTO.getInstitucion() != null){
				writer.print(solicitudDTO.getInstitucion().getNombreInst());
			}else{
				writer.print(ConstantesGenerales.VALOR_OMISION_GRID);
			}
			writer.print(ATTR_RECORD_XML_CLOSE);
			writer.print(ATTR_RECORD_XML_OPEN);
			if (tipoConsulta.equals(SolicitudDTO.POR_ATENDER)){
				if (solicitudDTO.getUsuarioSolicitante() == null) {
					if (solicitudDTO.getSolicitanteInstExterna() != null){
						writer.print(solicitudDTO.getSolicitanteInstExterna().getNombreCompleto());
					}else{
						writer.print(ConstantesGenerales.VALOR_OMISION_GRID);
					}
				} else {
					writer.print(solicitudDTO.getUsuarioSolicitante().getNombreCompleto());
				}
			}else{
				if (solicitudDTO.getDestinatario() == null) {
					if (solicitudDTO.getDestinatarioInstExterna() != null){
						writer.print(solicitudDTO.getDestinatarioInstExterna().getNombreCompleto());
					}else{
						writer.print(ConstantesGenerales.VALOR_OMISION_GRID);
					}
				} else {
					writer.print(solicitudDTO.getDestinatario().getNombreCompleto());
				}
			}
			writer.print(ATTR_RECORD_XML_CLOSE);
			if (solicitudDTO.getExpedienteDTO() == null) {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
			} else {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"
						+ solicitudDTO.getExpedienteDTO().getExpedienteId()
						+ "</div>]]></cell>");
			}
			writer.print(ATTR_RECORD_XML_OPEN);
			if (tipoConsulta.equals(SolicitudDTO.POR_ATENDER)
					&& docAdjunto != null){
				writer.print(docAdjunto.getNombreDocumento());
			}else{
				if (solicitudDTO.getNombreDocumento() == null || solicitudDTO.getNombreDocumento().isEmpty()) {
					writer.print(ConstantesGenerales.VALOR_OMISION_GRID);
				} else {
					writer.print(solicitudDTO.getNombreDocumento());
				}
			}
			writer.print(ATTR_RECORD_XML_CLOSE);
			writer.print(ATTR_RECORD_XML_OPEN);
			if (solicitudDTO.getSolicitanteInstExterna() != null){
				writer.print(solicitudDTO.getSolicitanteInstExterna().getConfInstitucionDTO().getNombreInst());
			}else if (solicitudDTO.getInstitucion() != null){
				writer.print(solicitudDTO.getInstitucion().getNombreInst());
			}else{
				writer.print(ConstantesGenerales.VALOR_OMISION_GRID);
			}
			writer.print(ATTR_RECORD_XML_CLOSE);
			writer.print(ATTR_RECORD_XML_OPEN);
			if (solicitudDTO.getUsuarioSolicitante() == null) {
				if (solicitudDTO.getSolicitanteInstExterna() != null){
					writer.print(solicitudDTO.getSolicitanteInstExterna().getNombreCompleto());
				}else{
					writer.print(ConstantesGenerales.VALOR_OMISION_GRID);
				}
			} else {
				writer.print(solicitudDTO.getUsuarioSolicitante().getNombreCompleto());
			}
			writer.print(ATTR_RECORD_XML_CLOSE);
			writer.print(ATTR_RECORD_XML_OPEN);
			if (docAdjunto != null){
				writer.print(docAdjunto.getNombreDocumento());					
			}else{
				writer.print(ConstantesGenerales.VALOR_OMISION_GRID);
			}
			writer.print(ATTR_RECORD_XML_CLOSE);
			writer.print(ATTR_RECORD_XML_OPEN);
			if (docAdjunto != null){
				writer.print(docAdjunto.getDocumentoId());					
			}else{
				writer.print(ConstantesGenerales.VALOR_OMISION_GRID);
			}
			writer.print(ATTR_RECORD_XML_CLOSE);
			writer.print("</row>");
		}
		writer.print("</rows>");
		return writer;
	}

	/**
	 * @param listaSolicitudes
	 * @param writer
	 */
	private PrintWriter llenarXMLDocumentos(List<DocumentoDTO> listaDocumentos,
			PrintWriter writer, PaginacionDTO pag, String tipoConsulta, String remitente) {
		writer.print("<rows>");
		if (pag != null && pag.getTotalRegistros() != null) {
			writer.print("<total>" + pag.getTotalPaginas() + "</total>");
			writer.print("<records>" + pag.getTotalRegistros() + "</records>");
		} else {
			writer.print("<total>0</total>");
			writer.print("<records>0</records>");
		}

		for (DocumentoDTO documentoDTO : listaDocumentos) {
			writer.print("<row id='" + documentoDTO.getDocumentoId() + "'>");
			// 1 CASO
			if (documentoDTO.getExpedienteDTO() == null
					|| documentoDTO.getExpedienteDTO().getCasoDTO() == null
					|| documentoDTO.getExpedienteDTO().getCasoDTO()
							.getNumeroGeneralCaso() == null) {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
			} else {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"
						+ documentoDTO.getExpedienteDTO().getCasoDTO()
								.getNumeroGeneralCaso() + "</div>]]></cell>");
			}
			// 2 NUMERO EXPEDIENTE
			if (documentoDTO.getExpedienteDTO() == null) {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>---</div>]]></cell>");
			} else {
				writer.print("<cell><![CDATA[<div class='celdaGrid'>"
						+ documentoDTO.getExpedienteDTO().getNumeroExpediente()
						+ "</div>]]></cell>");
			}
			String folioDocumento = (documentoDTO.getFolioDocumento() != null ? 
					documentoDTO.getFolioDocumento() : "---"); 
			// 3 FOLIO DOCUMENTO
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"+ folioDocumento + "</div>]]></cell>");
			// 4 ESTATUS
			if (documentoDTO.getEsGuardadoParcial() != null
					&& !documentoDTO.getEsGuardadoParcial()){
				writer.print(ATTR_RECORD_XML_OPEN+ESTATUS_DOCUMENTO_DEFINITIVO+ATTR_RECORD_XML_CLOSE);
			}else{
				writer.print(ATTR_RECORD_XML_OPEN+ESTATUS_DOCUMENTO_PARCIAL+ATTR_RECORD_XML_CLOSE);
			}
			String fechaCreacion = DateUtils.formatear(documentoDTO.getFechaCreacion());
			// 5 FECHA CREACION
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"+ fechaCreacion + "</div>]]></cell>");
			// 6 FECHA LIMITE
			writer.print("<cell><![CDATA[<div class='celdaGrid'>" + " "+ "</div>]]></cell>");
			// 7 INSTITUCION
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"
					+ (documentoDTO.getConfInstitucion() != null
							&& documentoDTO.getConfInstitucion()
									.getNombreInst() != null ? documentoDTO
							.getConfInstitucion().getNombreInst() : "---")
					+ "</div>]]></cell>");
			
			// 8 REMITENTE
			writer.print(ATTR_RECORD_XML_OPEN);
			if (remitente != null){
				writer.print(remitente);
			}else{
				writer.print("---");
			}
			writer.print(ATTR_RECORD_XML_CLOSE);
						
			if (documentoDTO.getActividadDTO() != null
					&& documentoDTO.getActividadDTO().getTipoActividadRS() != null) {
				ActividadesRS actividadRS = documentoDTO.getActividadDTO()
						.getTipoActividadRS();
			//9 TIPO ACTIVIDAD ID
				writer.print("<cell><![CDATA[" + actividadRS.getValorId()
						+ "]]></cell>");
			} else {
				writer.print("<cell><![CDATA[---]]></cell>");
			}
			// 10 NOMBRE DOCUMENTO
			writer.print("<cell><![CDATA[<div class='celdaGrid'>"
					+ (documentoDTO.getNombreDocumento() != null ? documentoDTO
							.getNombreDocumento() : "---")
					+ "</div>]]></cell>");
			
			if (tipoConsulta.equals(SolicitudDTO.ADJUNTOS)) {
				List<String> infoActRespuesta = obtenerInformacionActuacionesRespuesta(documentoDTO);
				if (infoActRespuesta == null){
					writer.print(ATTR_RECORD_XML_OPEN);
					writer.print(VALOR_DEFAULT_ID_ACTUACION);
					writer.print(ATTR_RECORD_XML_CLOSE);
					writer.print(ATTR_RECORD_XML_OPEN);
					writer.print(VALOR_DEFAULT_NOMBRE_ACTUACION);
					writer.print(ATTR_RECORD_XML_CLOSE);
				}else{
					for (String info : infoActRespuesta){
						writer.print(ATTR_RECORD_XML_OPEN);
						writer.print(info);
						writer.print(ATTR_RECORD_XML_CLOSE);
					}
				}
			}
			
			writer.print("</row>");
		}
		writer.print("</rows>");
		return writer;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la consulta de los ids y nombres de las 
	 * actuaciones que se pueden ejecutar como respuesta a una solicitud enviada desde una 
	 * instituci&oacute;n externa. 
	 * @param documentoDTO - <code>DocumentoDTO</code> con la informaci&oacute;n de la 
	 * 						 actividad de la cual se va a obtener las actuaciones de
	 * 						 respuesta. 
	 * @return List<String> InfoActuacionesRespuesta - Lista de cadenas (con cada valor 
	 * 								separado por comas) que representan los identificadores 
	 * 								y nombres de las actuaciones de respuesta que se pueden 
	 * 								ejecutar. 
	 */
	private List<String> obtenerInformacionActuacionesRespuesta(DocumentoDTO documentoDTO){
		List<String> infoActuacionesRespuesta = null;
		if (documentoDTO.getActividadDTO() != null
				&& (documentoDTO.getActividadDTO().getTipoActividad() != null
						|| documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum() != null)){
			
			Long tipoActividadId = null;
			
			if (documentoDTO.getActividadDTO().getTipoActividad() != null){
				tipoActividadId = documentoDTO.getActividadDTO().getTipoActividad().getValorId();
			} else if (documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum() != null){
				tipoActividadId = documentoDTO.getActividadDTO().getTipoActividadAlternaNoEnum().getIdCampo();
			}					
			
			if(tipoActividadId != null){
				ConfTipoActividadOrigenDestinoDTO confTipoActividadOrigenDestinoDTO = new ConfTipoActividadOrigenDestinoDTO();
				ValorDTO tipoActividadOrigen = new ValorDTO(tipoActividadId);
				confTipoActividadOrigenDestinoDTO.setTipoActividadOrigenVal(tipoActividadOrigen);
				try {
					List<ConfActividadDocumentoDTO> confActividadesDocumentoDTO = confActividadDocumentoDelegate.consultarConfActividadDocumentoPorConfTipoActividadDestino(confTipoActividadOrigenDestinoDTO);
					if (confActividadesDocumentoDTO != null 
							&& !confActividadesDocumentoDTO.isEmpty()){
						infoActuacionesRespuesta = new ArrayList<String>();
						StringBuilder ids = new StringBuilder(""); 
						StringBuilder nombresActividad = new StringBuilder("");
						for (int i=0; i<confActividadesDocumentoDTO.size(); i++){
							ConfActividadDocumentoDTO conf = confActividadesDocumentoDTO.get(i);
							if (i>0){
								ids.append(",");
								nombresActividad.append(",");
							}
							ids.append(conf.getConfActividadDocumentoId().toString());
							nombresActividad.append(conf.getNombreActividad());
						}
						infoActuacionesRespuesta.add(ids.toString());
						infoActuacionesRespuesta.add(nombresActividad.toString());
					}
				} catch (NSJPNegocioException e) {
					LOG.error(e.getMessage(), e);
				}						
			} 
		}
		return infoActuacionesRespuesta;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos asociados al expediente de 
	 * acuerdo al tipo de documento, y regresa la informaci&oacute;n de dicho documento dentro 
	 * de un objeto de JSON para su interpretaci&oacute;n dentro de la vista.
	 * 
	 * @param mapping - mapeos de struts para enlazar las peticiones.
	 * @param form - forma de struts con la informaci&oacute;n pasada dentro de la petici&oacute;n
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML.
	 * @param response - Objeto de java que representa la respuesta en HTML.
	 * @return null - Se escribe el objeto de JSON directamente en el response pasado como argumento.
	 * @throws IOException - en el caso de que ocurra alg&uacute;n error al momento de escribir el 
	 * 						 JSON en la respuesta.
	 */
	public ActionForward consultarDocumentoExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAM_ID_TIPO_DOCUMENTO),0L);
		Long numeroExpedienteId = NumberUtils.toLong(request.getParameter(PARAM_ID_NUMERO_EXPEDIENTE),0L);
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
		expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
		try {
			List<DocumentoDTO> documentos = documentoDelegate.consultarDocumentosXTipoDocumento(expedienteDTO, tipoDocumento);
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_JAVASCRIPT+"; "+ ConstantesGenerales.CHARSET_ISO_8859);
			response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
			PrintWriter writer = response.getWriter();
			if (documentos != null 
					&& !documentos.isEmpty()){
				writer.print(dto2Json(documentos.get(0)));	
			}else{
				writer.print(dto2Json(null));
			}
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos asociados al expediente de 
	 * acuerdo a una serie de tipos de documento. 
	 * @param mapping - mapeos de struts para enlazar las peticiones.
	 * @param form - forma de struts con la informaci&oacute;n pasada dentro de la petici&oacute;n
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML.
	 * @param response - Objeto de java que representa la respuesta en HTML.
	 * @return null - Se escribe la respuesta 
	 * @throws IOException - en el caso de que ocurra alg&uacute;n error al momento de escribir el 
	 * 						 JSON en la respuesta.
	 */
	public ActionForward consultarDocumentosExpedienteXTipos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String cadenaTiposDocumento = request.getParameter(PARAM_ID_TIPO_DOCUMENTO);
		String tipoConsulta = request.getParameter(PARAM_TIPO_CONSULTA);
		Long expedienteId = NumberUtils.toLong(request.getParameter(PARAM_ID_EXPEDIENTE),0L);
		ExpedienteDTO expedienteDTO = new ExpedienteDTO(expedienteId);
		
		List<ValorDTO> tiposFiltro = null;
		if(cadenaTiposDocumento != null
				&& !cadenaTiposDocumento.isEmpty()){
			tiposFiltro = new ArrayList<ValorDTO>();
			String[] tiposDocumento = cadenaTiposDocumento.split(VALOR_DELIMITADOR_TIPOS_DOCUMENTO);
			for (String tipoDocto : tiposDocumento){
				tiposFiltro.add(new ValorDTO(Long.parseLong(tipoDocto)));
			}
		}
		
		try {
			List<DocumentoDTO> documentos = documentoDelegate.consultarDocumentosPorExpedienteYTipos(expedienteDTO, tiposFiltro);
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			PrintWriter writer = response.getWriter();
			writer = llenarXMLDocumentos(documentos, writer, pag, tipoConsulta, null);
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/***
	 * M&eacute;todo para llevar a cabo la consulta de las solicitudes que se encuentran asociadas
	 * con una sentencia y desplegar la informaci&oacute;n relevante de dichas solicitudes dentro 
	 * de un grid.
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return null - Ya que el servicio s&oacute;lo se limita a llevar a cabo la consulta.
	 * @throws IOException - En el caso de que se presente un error al momento de escribir la
	 * 						 respuesta.
	 */
	public ActionForward consultaSolsGeneradasPorSentencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String tipoSolicitud = request.getParameter(PARAM_TIPO_SOLICITUD);
			String estatusSolicitud = request.getParameter(PARAM_ESTATUS);
			//construimos la lista de los tipos de solicitud a consultar
			String[] idTiposols=tipoSolicitud.split(",");
			List<Long> idsTipSols=new ArrayList<Long>();
			for (String long1 : idTiposols) {
				idsTipSols.add(Long.parseLong(long1));
			}

			//construimos la lista de los estatus a consultar
			String[] idEstatus=estatusSolicitud.split(",");
			List<Long> idsEstatus=new ArrayList<Long>();
			for (String long1 : idEstatus) {
				idsEstatus.add(Long.parseLong(long1));
			}

			//consultamos las solicitudes por atender
			List<SolicitudDTO> listaSolicitudes= solicitudDelegate.consultarSolicitudesGeneradas(
					idsEstatus,idsTipSols, 
					super.getUsuarioFirmado(request).getAreaActual().getAreaId(), 
					super.getUsuarioFirmado(request).getFuncionario().getClaveFuncionario());

			List<Long> idsNumExp = new ArrayList<Long>();

			if (listaSolicitudes != null 
					&& !listaSolicitudes.isEmpty()){
				for (SolicitudDTO solicitud : listaSolicitudes){
					if (solicitud.getExpedienteDTO() != null){
						idsNumExp.add(solicitud.getExpedienteDTO().getNumeroExpedienteId());
					}
				}
			}
			Map<Long, SentenciaDTO> mapaSentencias = new HashMap<Long, SentenciaDTO>();
			if (!idsNumExp.isEmpty()){
				mapaSentencias = sentenciaDelegate.consultarSentenciasPorIdsNumExp(idsNumExp);				
			}

			//generamos el HTML del grid
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML+ "; "+ConstantesGenerales.CHARSET_ISO_8859);
			response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL,ConstantesGenerales.ENCABEZADO_NOCACHE);
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();

			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			List<List<String>> tablaValores = new ArrayList<List<String>>();

			for (SolicitudDTO solicitudDTO : listaSolicitudes) {
				SentenciaDTO sentenciaDTO = null;
				if (solicitudDTO.getExpedienteDTO() != null 
						&& solicitudDTO.getExpedienteDTO().getNumeroExpedienteId() != null){
					sentenciaDTO = mapaSentencias.get(solicitudDTO.getExpedienteDTO().getNumeroExpedienteId());
				}
				tablaValores.add(creaFilaParaGrid(solicitudDTO, sentenciaDTO, false));
			}

			String datos = crearDatosGrid(tablaValores);
			writer.print(datos);
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e){
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/***
	 * M&eacute;todo para llevar a cabo la consulta de las solicitudes que se encuentran asociadas
	 * con una sentencia y desplegar la informaci&oacute;n relevante de dichas solicitudes dentro 
	 * de un grid.
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return null - Ya que el servicio s&oacute;lo se limita a llevar a cabo la consulta.
	 * @throws IOException - En el caso de que se presente un error al momento de escribir la
	 * 						 respuesta.
	 */
	public ActionForward consultaSolsRecibidasPorSentencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String tipoSolicitud = request.getParameter(PARAM_TIPO_SOLICITUD);
			String estatusSolicitud = request.getParameter(PARAM_ESTATUS);
			//construimos la lista de los tipos de solicitud a consultar
			String[] idTiposols=tipoSolicitud.split(",");
			List<Long> idsTipSols=new ArrayList<Long>();
			for (String long1 : idTiposols) {
				idsTipSols.add(Long.parseLong(long1));
			}

			//construimos la lista de los estatus a consultar
			String[] idEstatus=estatusSolicitud.split(",");
			List<Long> idsEstatus=new ArrayList<Long>();
			for (String long1 : idEstatus) {
				idsEstatus.add(Long.parseLong(long1));
			}

			//consultamos las solicitudes por atender
			List<SolicitudDTO> listaSolicitudes= solicitudDelegate.consultarSolicitudesParaAtender(
					idsEstatus,idsTipSols, 
					null, 
					super.getUsuarioFirmado(request).getFuncionario().getClaveFuncionario(),
					null);

			List<Long> idsNumExp = new ArrayList<Long>();

			if (listaSolicitudes != null 
					&& !listaSolicitudes.isEmpty()){
				for (SolicitudDTO solicitud : listaSolicitudes){
					if (solicitud.getExpedienteDTO() != null){
						idsNumExp.add(solicitud.getExpedienteDTO().getNumeroExpedienteId());
					}
				}
			}
			Map<Long, SentenciaDTO> mapaSentencias = new HashMap<Long, SentenciaDTO>();
			if (!idsNumExp.isEmpty()){
				mapaSentencias = sentenciaDelegate.consultarSentenciasPorIdsNumExp(idsNumExp);				
			}

			//generamos el HTML del grid
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML+ "; "+ConstantesGenerales.CHARSET_ISO_8859);
			response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL,ConstantesGenerales.ENCABEZADO_NOCACHE);
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();

			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			List<List<String>> tablaValores = new ArrayList<List<String>>();

			for (SolicitudDTO solicitudDTO : listaSolicitudes) {
				SentenciaDTO sentenciaDTO = null;
				if (solicitudDTO.getExpedienteDTO() != null 
						&& solicitudDTO.getExpedienteDTO().getNumeroExpedienteId() != null){
					sentenciaDTO = mapaSentencias.get(solicitudDTO.getExpedienteDTO().getNumeroExpedienteId());
				}
				tablaValores.add(creaFilaParaGrid(solicitudDTO, sentenciaDTO, Boolean.TRUE));
			}

			String datos = crearDatosGrid(tablaValores);
			writer.print(datos);
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e){
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/***
	 * M&eacute;todo que lleva a cabo la consulta del detalle de las solicitudes de 
	 * Reinserci&oacute;n social que han sido respondidas. Muestra la informaci&oacute;n
	 * correspondiente tanto a la solicitud original como a la solicitud de respuesta. 
	 * 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma del JSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return ActionForward - Forward de struts que env&iacute;a la informaci&oacute;n 
	 * 						   consultada al JSP en el que se va a presentar.
	 */
	public ActionForward consultarDetalleSolicitud(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		DetalleSolicitudRSForm forma = (DetalleSolicitudRSForm) form;
		Long solicitudId = NumberUtils.toLong(request.getParameter(PARAM_ID_SOLICITUD));
		Long sentenciaId = NumberUtils.toLong(request.getParameter(PARAM_ID_SENTENCIA));
		
		if (solicitudId > 0L
				&& sentenciaId > 0L){
			try {
				SolicitudDTO solicitud = solicitudDelegate.consultarSolicitudXId(solicitudId);
				SentenciaDTO sentencia = new SentenciaDTO();
				sentencia.setSentenciaId(sentenciaId);
				sentencia = asignacionProgramaDelegate.consultarSentencia(sentencia);
				List<RelacionDocumentoDTO> relaciones = documentoDelegate.consultarRelacionesPorDocPrincipal(solicitud);
				SolicitudDTO solicitudRespuesta = null;
				if (relaciones != null 
						&& !relaciones.isEmpty()){
					//Se obtiene el primer elemento de la lista, ya que de solamente puede haber un documentop de respuesta a cada sol.
					solicitudRespuesta = solicitudDelegate.consultarSolicitudXId(relaciones.get(0).getDocumentoRelacionado().getDocumentoId());					
				}
				if (solicitud != null){
					if (sentencia != null){
						ExpedienteDTO expediente = sentencia.getNumeroExpedienteDTO();
						if (expediente != null){
							forma.setNumeroExpediente(expediente.getNumeroExpediente());
							if (expediente.getCasoDTO() != null){
								forma.setNumeroCaso(expediente.getCasoDTO().getNumeroGeneralCaso());
							}
						}
					}
					forma.setFolioSolicitud(solicitud.getFolioSolicitud());
					if (solicitud.getEstatus() != null){
						forma.setEstatusSolicitud(solicitud.getEstatus().getValor());						
					}
					forma.setFechaSolicitudSTR(DateUtils.formatear(solicitud.getFechaCreacion()));
					forma.setTipoSolicitud(solicitud.getTipoSolicitudDTO().getValor());
					
					if (solicitudRespuesta != null){
						if (solicitudRespuesta.getDestinatarioInstExterna() != null){
							forma.setDestinatario(solicitudRespuesta.getDestinatarioInstExterna().getNombreCompleto());
							forma.setInstitucion(solicitudRespuesta.getDestinatarioInstExterna().getConfInstitucionDTO().getNombreInst());
						}else if (solicitudRespuesta.getDestinatario() != null){
							forma.setDestinatario(solicitudRespuesta.getDestinatario().getNombreCompleto());
							forma.setInstitucion(solicitudRespuesta.getInstitucion().getNombreInst());
						}
						forma.setFolioDocumento(solicitudRespuesta.getFolioDocumento());
						forma.setFechaCreacion(DateUtils.formatear(solicitudRespuesta.getFechaCreacion()));
						forma.setTipoDocumento(solicitudRespuesta.getTipoDocumentoDTO().getValor());
						forma.setIdDocumento(solicitudRespuesta.getDocumentoId());
					}
				}
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return mapping.findForward(FWD_DETALLES_SOLICITUD);
	}
	
	/***
	 * M&eacute;todo que lleva a cabo el registro de la relaci&oacute;n 
	 * que existe entre dos documentos, el de envío y el de respuesta a 
	 * una solicitud. 
	 * 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma del JSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return null - solamente se regresa una bandera estableciendo si la 
	 * 				  actualizaci&oacute;n se llev&oacute; a cabo de manera 
	 * 				  exitosa.
	 */
	public ActionForward registrarRelacionDocumento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		Long docPrincipalId = NumberUtils.toLong(request.getParameter(PARAM_ID_DOC_PRINCIPAL));
		Long docRelacionadoId = NumberUtils.toLong(request.getParameter(PARAM_ID_DOC_RELACIONADO));
		Boolean banderaRegistro = Boolean.FALSE; 
		if (docPrincipalId > 0L
				&& docRelacionadoId > 0L){
			
			RelacionDocumentoDTO relacion = new RelacionDocumentoDTO();
			
			DocumentoDTO docPrincipal = new DocumentoDTO();
			docPrincipal.setDocumentoId(docPrincipalId);
			relacion.setDocumentoPrincipal(docPrincipal);
			
			DocumentoDTO docRelacionado = new DocumentoDTO();
			docRelacionado.setDocumentoId(docRelacionadoId);
			relacion.setDocumentoRelacionado(docRelacionado);
			
			relacion.setEsActivo(Boolean.TRUE);
			
			documentoDelegate.guardarRelacionDocumento(relacion);
			banderaRegistro = Boolean.TRUE;
		}
		converter.alias("banderaRegistro", Boolean.class);
		escribirRespuesta(response, converter.toXML(banderaRegistro));
		
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creaci&oacute;n de un objeto de JSON a partir de un 
	 * DocumentoDTO
	 * @param documentoDTO - El DTO que se va a transformar en un JSON.
	 * @return jsonText - Cadena que representa el objeto de JSON creado.
	 */
	private String dto2Json(DocumentoDTO documentoDTO) {
		JSONObject jsonObject = null;
		StringWriter out = null;
		String jsonText = "";
		jsonObject = convertirJson(documentoDTO);
		out = new StringWriter();
		try {
			jsonObject.writeJSONString(out);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		jsonText = out.toString();
		return jsonText;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de un objeto de JSON con las propiedades
	 * del objeto documentoDTO.
	 * @param documentoDTO - objeto del cual se van a escribir sus propiedades en el objeto de JSON.
	 * @return JSONObject - con la informaci&oacute;n del DTO transcrita.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject convertirJson (DocumentoDTO documentoDTO){
		JSONObject json = new JSONObject();
		if (documentoDTO != null){
			json.put(KEY_JSON_ID_DOCUMENTO, documentoDTO.getDocumentoId());
			json.put(KEY_JSON_NOMBRE_DOCUMENTO, documentoDTO.getNombreDocumento());
			json.put(KEY_JSON_ID_TIPO_DOCUMENTO, documentoDTO.getTipoDocumentoDTO().getIdCampo());
			json.put(KEY_JSON_NOMBRE_TIPO_DOCUMENTO, documentoDTO.getTipoDocumentoDTO().getValor());
		}else{
			json.put(KEY_JSON_ID_DOCUMENTO,0L);
		}
		return json;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de los documentos que se encuentran adjuntos a una
	 * lista de solicitudes y los regresa dentro de un mapa.
	 * @param listaSolicitudes - List<SolicitudDTO> con la lista de solicitudes de las cuales se van
	 * 							 a consultar los documentos adjuntos.
	 * @return documentosAdjuntos - Map<Long,DocumentoDTO> con los documentos asociados a las 
	 * 								solicitudes que se consultaron.
	 */
	private Map<Long, DocumentoDTO> obtenerDocumentosAdjuntos(List<SolicitudDTO> listaSolicitudes){
		Map<Long, DocumentoDTO> documentosAdjuntos = null;
		if (listaSolicitudes != null 
				&& !listaSolicitudes.isEmpty()){
			List<Long> idsArchivoDigital = new ArrayList<Long>();
			for (SolicitudDTO sol : listaSolicitudes){
				if (sol.getArchivosAdjuntos() != null 
						&& !sol.getArchivosAdjuntos().isEmpty()){
					idsArchivoDigital.add(sol.getArchivosAdjuntos().get(0).getArchivoDigital().getArchivoDigitalId());
				}
			}
			try {
				documentosAdjuntos = documentoDelegate.consultarDocumentosPorArchivosDigitales(idsArchivoDigital);
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return documentosAdjuntos;
	}
	
	/**
	 * M&eacute;todo utilitario que regresa el primer documento adjunto a la solicitud en el caso de que dicho documento
	 * exista. En el caso contrario regresa <code>null</code>
	 * @param solicitudDTO - solicitudDTO de la cual se va a obtener el primer documento adjunto.
	 * @param documentosAdjuntos - Mapa en donde se encuentran los documentos adjuntos a la solicitud agrupados de acuerdo 
	 * 							   con el identificador del archivo digital asociado.
	 * @return documentoDTO - documentoDTO asociado a la solicitud.
	 */
	private DocumentoDTO obtenerDocumentoAdjuntoDeSolicitud(SolicitudDTO solicitudDTO, Map<Long,DocumentoDTO> documentosAdjuntos){
		DocumentoDTO documentoDTO = null;
		if (solicitudDTO != null
				&& solicitudDTO.getArchivosAdjuntos() != null
				&& !solicitudDTO.getArchivosAdjuntos().isEmpty()
				&& documentosAdjuntos != null){
			documentoDTO = documentosAdjuntos.get(solicitudDTO.getArchivosAdjuntos().get(0).getArchivoDigital().getArchivoDigitalId());
		}
		return documentoDTO;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de
	 * una lista de cadenas con la informaci&oacute;n que se va a mostrar
	 * dentro de un grid.
	 * @param solicitud - SolicitudDTO de la cual se va a obtener la informaci&oacute;n 
	 * 					  a mostrar.
	 * @param sentencia - SentenciaDTO asociada a la solicitud de la cual se va a obtener
	 * 					  informaci&oacute;n adicional. 
	 * @return List<String> - Lista de cadenas con la informaci&oacute;n a desplegar
	 * 						  dentro del grid.
	 */
	private List<String> creaFilaParaGrid(SolicitudDTO solicitud, SentenciaDTO sentencia, boolean agregarIdSentencia){
		List<String> fila = new ArrayList<String>();
		boolean sentenciaAsociada = (sentencia != null ? true : false);
		fila.add(solicitud.getDocumentoId().toString());
		fila.add(solicitud.getFolioSolicitud());
		fila.add(solicitud.getEstatus() != null ? 
				solicitud.getEstatus().getValor() : 
					ConstantesGenerales.VALOR_OMISION_GRID);
		fila.add(solicitud.getFechaCreacion() != null ? 
				DateUtils.formatear(solicitud.getFechaCreacion()) : 
					ConstantesGenerales.VALOR_OMISION_GRID);
		if (sentenciaAsociada){
			fila.add(1, sentencia.getNumeroExpedienteDTO().getNumeroExpediente());
			fila.add(4, sentencia.getInvolucradoDTO().getNombreCompleto());
			fila.add(5, sentencia.getInvolucradoDTO().getCadenaDelitosCometidos());
			if (agregarIdSentencia){
				fila.add(sentencia.getSentenciaId().toString());
			}
		}else{
			fila.add(1, ConstantesGenerales.VALOR_OMISION_GRID);
			fila.add(4, ConstantesGenerales.VALOR_OMISION_GRID);
			fila.add(5, ConstantesGenerales.VALOR_OMISION_GRID);
		}
		return fila;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de 
	 * una tabla con datos en formato XML para ser interpretados dentro
	 * de un grid.
	 * @param matrizValores - Lista de Listas de cadenas en donde se 
	 * 						  guarda la información a desplegar en el
	 * 						  grid.
	 * @return String - Cadena en formato XML con la informaci&oacute;n 
	 * 					que ser&aacute; presentada dentro del grid.		
	 */
	private String crearDatosGrid(List<List<String>> matrizValores){
		StringBuffer buffer = new StringBuffer();
		if (matrizValores != null && !matrizValores.isEmpty()){
			for (List<String> fila : matrizValores){
				if (fila != null && !fila.isEmpty()){
					for (int i=0; i<fila.size();i++){
						if (i==0){
							buffer.append("<row id='"+ fila.get(i)+"'>");
						}else{
							buffer.append("<cell><![CDATA[<div class='celdaGrid'>");
							if (fila.get(i) != null){
								buffer.append(fila.get(i));
							}else{
								buffer.append(ConstantesGenerales.VALOR_OMISION_GRID);
							}
							buffer.append("</div>]]></cell>");
						}
					}
					buffer.append("</row>");
				}
			}
		}
		return buffer.toString();
	}
}

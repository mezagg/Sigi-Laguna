package mx.gob.segob.nsjp.web.mandamientojudicial.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.enums.solicitud.TipoMandamiento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.mandamiento.EstatusMandamientoPersona;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.delito.DelitoDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.mandamiento.AdministrarMandamientoJudicialDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDTO;
import mx.gob.segob.nsjp.dto.mandamiento.MandamientoPersonaDocumentoDTO;
import mx.gob.segob.nsjp.dto.persona.PersonaDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.documento.form.AdjuntarDocumentoAMandamientoJudicialForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.annotation.Autowired;

public class AdministrarMandamientoJudicialAction extends GenericAction {

	@Autowired
	public InvolucradoDelegate involucradoDelegate;
	@Autowired
	private DelitoDelegate delitoDelegate;
	@Autowired
	private AdministrarMandamientoJudicialDelegate administrarMandamientoJudicialDelegate;
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	private SolicitudDelegate solicitudDelegate;

	private static final String MANDAMIENTO_JUDICIAL_EN_SESION = "MANDAMIENTO_JUDICIAL_EN_SESION";
	private static final String ANONIMO = "An\u00F3nimo";
	private static final String DELITO_SIN_NOMBRE = "Delito sin nombre";
	private static final String UNDEFINED = "undefined";
	private static final String NULO = "null";
	private static final String FALLO = "fail";
	private static final String EXITO = "success";

	/** Log de clase */
	private static final Logger log = Logger
			.getLogger(AdministrarMandamientoJudicialAction.class);

	/**
	 * M&eacute;todo para consultar los Probables Responsables por audiencia
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, Se regresa en formato XML
	 * @throws IOException
	 */
	public ActionForward consultarProbablesResponsablesPorAudiencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			log.info("EJECUTANDO ACTION ---- CONSULTAR PROBABLES RESPONSABLES POR AUDIENCIA)");
			log.debug("audienciaId ... " + request.getParameter("audienciaId"));

			Long idAudiencia = NumberUtils.toLong(
					request.getParameter("audienciaId"), 0L);

			if (idAudiencia <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			AudienciaDTO audienciaDTO = new AudienciaDTO(idAudiencia);

			List<InvolucradoViewDTO> listaInvolucrados = involucradoDelegate
					.obtenerImputadosAudiencia(audienciaDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			PaginadorUtil.obtenerPaginacionManual(listaInvolucrados);
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaInvolucrados != null && !listaInvolucrados.isEmpty()) {
				for (InvolucradoViewDTO involucradoViewDTO : listaInvolucrados) {
					writer.print("<row id='"
							+ involucradoViewDTO.getInvolucradoId() + "'>");
					writer.print("<cell>"
							+ involucradoViewDTO.getNombreCompleto()
							+ "</cell>");
					writer.print("</row>");
				}

			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * M&eacute;todo para consultar las relaciones Delito-Persona por Probable
	 * Responsable seleccionado en el Grid de PR
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, Se regresa en formato XML
	 * @throws IOException
	 */
	public ActionForward consultarRelDelitoPersonaPorProbableResponsableEnMandamientos(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.debug("EJECUTANDO ACTION consultarRelDelitoPersonaPorProbableResponsableEnMandamientos");
			log.debug("VERIFICANDO PARAMETRO..............................");
			log.debug("Prob. resp Id=" + request.getParameter("probRespId"));
			log.debug(" idExpediente=" + request.getParameter("idExpediente"));

			Long probRespId = NumberUtils.toLong(
					request.getParameter("probRespId"), 0L);
			Long idExpediente = NumberUtils.toLong(
					request.getParameter("idExpediente"), 0L);

			if (probRespId <= 0L || idExpediente <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			List<DelitoPersonaDTO> listaRelDelPerXInv = delitoDelegate
					.consultarDelitosVictimaPorImputado(probRespId,
							idExpediente);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaRelDelPerXInv != null && !listaRelDelPerXInv.isEmpty()) {
				for (DelitoPersonaDTO delitoPersonaDTO : listaRelDelPerXInv) {

					writer.print("<row id='"
							+ delitoPersonaDTO.getDelitoPersonaId() + "'>");

					// Nombre del probable responsable
					writer.print("<cell>"
							+ ((delitoPersonaDTO.getProbableResponsable()
									.getNombreCompleto() != null && !delitoPersonaDTO
									.getProbableResponsable()
									.getNombreCompleto().trim().isEmpty()) ? delitoPersonaDTO
									.getProbableResponsable()
									.getNombreCompleto() : ANONIMO) + "</cell>");

					// Nombre del Delito
					writer.print("<cell>"
							+ ((delitoPersonaDTO.getDelito().getCatDelitoDTO() != null
									&& delitoPersonaDTO.getDelito()
											.getCatDelitoDTO().getNombre() != null && !delitoPersonaDTO
									.getDelito().getCatDelitoDTO().getNombre()
									.trim().isEmpty()) ? delitoPersonaDTO
									.getDelito().getCatDelitoDTO().getNombre()
									: DELITO_SIN_NOMBRE) + "</cell>");

					// Nombre de la Victima
					writer.print("<cell>"
							+ ((delitoPersonaDTO.getVictima()
									.getNombreCompleto() != null && !delitoPersonaDTO
									.getVictima().getNombreCompleto().trim()
									.isEmpty()) ? delitoPersonaDTO.getVictima()
									.getNombreCompleto() : ANONIMO) + "</cell>");

					// Id del Probable Responsable
					writer.print("<cell>" + probRespId + "</cell>");

					writer.print("</row>");
				}
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();
			log.debug("FINALIZO LA EJECUCION DEL ACTION consultarRelDelitoPersonaPorProbableResponsableEnMandamientos");

		} catch (NSJPNegocioException ex) {
			log.error(ex.getMessage(), ex);
		}
		return null;
	}

	/**
	 * M&eacute;todo para generar el Mandamiento en base a tipo de mandamiento,
	 * resolutivo, n&uacute;mero de causa
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, Se regresa en formato XML
	 * @throws IOException
	 */
	public ActionForward generarMandamiento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.debug("EJECUTANDO ACTION generarMandamiento");
			log.debug("VERIFICANDO PARAMETROS..............................");
			log.debug("Tipo de Mandamiento Id="
					+ request.getParameter("tipoMandamiento"));
			log.debug("idResolutivo=" + request.getParameter("idResolutivo"));
			log.debug("numeroCausa=" + request.getParameter("numeroCausa"));
			log.debug("listaIdsRelDelPersona="
					+ request.getParameter("listaIdsRelDelPersona"));
			log.debug("listaIdsProbablesResponsables="
					+ request.getParameter("listaIdsProbablesResponsables"));

			Long tipoMandamiento = NumberUtils.toLong(
					request.getParameter("tipoMandamiento"), 0L);
			Long idResolutivo = NumberUtils.toLong(
					request.getParameter("idResolutivo"), 0L);
			String numeroExpediente = request.getParameter("numeroExpediente");
			String listaIdsRelDelPersona = request
					.getParameter("listaIdsRelDelPersona");
			String listaIdsProbablesResponsables = request
					.getParameter("listaIdsProbablesResponsables");

			if (tipoMandamiento <= 0L || idResolutivo <= 0L
					|| numeroExpediente == null || numeroExpediente.isEmpty()
					|| listaIdsRelDelPersona == null
					|| listaIdsRelDelPersona.isEmpty()
					|| listaIdsProbablesResponsables == null
					|| listaIdsProbablesResponsables.isEmpty()) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			// Objeto que sera pasado como parametro al servicio
			MandamientoDTO mandamientoDTO = new MandamientoDTO();
			Long respuestaDocumentoId = 0L;

			/**
			 * VALORES REFERENTES A RELACIONES DELITO PERSONA Y MANDAMIENTOS
			 * PERSONA
			 */
			// Lista de relaciones delito persona
			List<DelitoPersonaDTO> listaDeRelacionesDelitoPersona = new ArrayList<DelitoPersonaDTO>();
			// Hash Map con los id's de los probables responsables para los
			// mandamientos-persona
			Map<Long, Long> hashMapIdsProbresponsables = new HashMap<Long, Long>();
			// Lista de probables responsables sin repeticiones
			List<MandamientoPersonaDTO> listaMandamientoPersonaDTO = new ArrayList<MandamientoPersonaDTO>();

			// se crea un StringTokenizer que separa la cadena de acuerdo al
			// separador dado ","
			StringTokenizer tokenslistaIdsRelDelPersona = new StringTokenizer(
					listaIdsRelDelPersona, ",");

			// LLenamos la lista de relaciones delito persona
			while (tokenslistaIdsRelDelPersona.hasMoreTokens()) {

				String idRelDelitoPersona = tokenslistaIdsRelDelPersona
						.nextToken();// el siguiente token
				Long idRelacionDelitoPersona = NumberUtils.toLong(
						idRelDelitoPersona, 0L);
				if (idRelacionDelitoPersona.equals(0L)) {
					throw new NSJPNegocioException(
							CodigoError.INFORMACION_PARAMETROS_ERRONEA);
				}
				DelitoPersonaDTO delitoPersonaDTO = new DelitoPersonaDTO();
				delitoPersonaDTO.setDelitoPersonaId(idRelacionDelitoPersona);
				listaDeRelacionesDelitoPersona.add(delitoPersonaDTO);
			}

			// se crea un StringTokenizer que separa la cadena de acuerdo al
			// separador dado ","
			StringTokenizer tokenslistaIdsProbablesResponsables = new StringTokenizer(
					listaIdsProbablesResponsables, ",");

			// LLenamos la lista de relaciones delito persona
			while (tokenslistaIdsProbablesResponsables.hasMoreTokens()) {
				String idProbableResponsable = tokenslistaIdsProbablesResponsables
						.nextToken();
				Long elementoId = NumberUtils.toLong(idProbableResponsable, 0L);
				if (elementoId.equals(0L)) {
					throw new NSJPNegocioException(
							CodigoError.INFORMACION_PARAMETROS_ERRONEA);
				}
				log.debug("idProbableResponsable=" + idProbableResponsable);
				hashMapIdsProbresponsables.put(elementoId, elementoId);
			}

			if (!hashMapIdsProbresponsables.isEmpty()) {
				// iterating over keys only
				for (Long idProbResponsable : hashMapIdsProbresponsables
						.keySet()) {
					MandamientoPersonaDTO mandamientoPersonaDTO = new MandamientoPersonaDTO();
					mandamientoPersonaDTO.setPersona(new PersonaDTO(
							idProbResponsable));
					listaMandamientoPersonaDTO.add(mandamientoPersonaDTO);
				}
			}

			// LLenamos todos los parametros obligatorios del mandamiento
			mandamientoDTO.setDelitosPersona(listaDeRelacionesDelitoPersona);
			mandamientoDTO.setMandamientosPersona(listaMandamientoPersonaDTO);

			/**
			 * VALOR REFERENTE AL RESOLUTIVO
			 */
			ResolutivoDTO resolutivoDTO = new ResolutivoDTO();
			resolutivoDTO.setResolutivoId(idResolutivo);
			mandamientoDTO.setResolutivo(resolutivoDTO);

			/**
			 * VALOR REFERENTE A TIPO MANDAMIENTO
			 */
			mandamientoDTO.setTipoMandamiento(new ValorDTO(tipoMandamiento));

			/**
			 * VALOR REFERENTE AL NUMERO DE CAUSA
			 */
			mandamientoDTO.setNumeroCausa(numeroExpediente);

			/**
			 * VALOR REFERENTE A USUARIO
			 */
			mandamientoDTO.setUsuario(super.getUsuarioFirmado(request));

			/**
			 * ID DE LA ACTIVIDAD
			 */
			ActividadDTO actividadDTO = new ActividadDTO();
			actividadDTO
					.setActividadId(Actividades.GENERAR_MANDAMIENTO_JUDICIAL
							.getValorId());
			mandamientoDTO.setActividadDTO(actividadDTO);

			mandamientoDTO = administrarMandamientoJudicialDelegate
					.generarMandamientoJudicial(mandamientoDTO);

			// Devolvemos la respuesta vista
			if (mandamientoDTO.getDocumentoId() != null
					&& mandamientoDTO.getDocumentoId() > 0L) {
				respuestaDocumentoId = mandamientoDTO.getDocumentoId();
			}

			String xml = "<documentoId>" + respuestaDocumentoId
					+ "</documentoId>";
			escribirRespuesta(response, xml);

			log.debug("SE EJECUTO COMPLETO EL ACTION generarMandamiento");
		} catch (NSJPNegocioException ex) {
			log.error(ex.getMessage(), ex);
			escribirError(response, ex);
		}
		return null;
	}

	/**
	 * M&eacute;todo que consulta un mandamiento por su id
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward consultarMandamientoJudicial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// Se le pasa la session false para que si ya no esta activa no cree una
		// nueva sesion
		HttpSession session = request.getSession(false);
		
		try {

			if (session == null) {
				log.error("La session es invalida");
				throw new NSJPNegocioException(CodigoError.SESSION_NULA);
			}
			
			Long mandamientoJudicialId = NumberUtils.toLong(
					request.getParameter("mandamientoJudicialId"), 0L);

			if (mandamientoJudicialId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			MandamientoDTO mandamientoDTO = administrarMandamientoJudicialDelegate
					.consultarMandamientoPorId(mandamientoJudicialId);

			if (mandamientoDTO == null) {
				log.error("MandamientoDTO consultado no existe, o no se encuentra");
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);

			}
			/*
			 * Se sube el objeto mandamientoDTO a session para poder consultar
			 * los delitos-persona en el action
			 * consultarRelacionDelitoPersonaProbableResp()
			 */
			session.setAttribute(MANDAMIENTO_JUDICIAL_EN_SESION, mandamientoDTO);

			log.info("expedienteDTO: " + mandamientoDTO.getExpedienteDTO());
			if (mandamientoDTO.getExpedienteDTO() != null) {
				ExpedienteDTO expedienteDTO = mandamientoDTO.getExpedienteDTO();
				super.setExpedienteTrabajo(request, expedienteDTO);
			}

			/**
			 * CAMPOS NO REQUERIDOS PARA EL DETALLE
			 */
			if (mandamientoDTO.getArchivoDigital() != null) {
				mandamientoDTO.getArchivoDigital().setContenido(null);
			}
			mandamientoDTO.setFormaDTO(null);
			if (mandamientoDTO.getResolutivo() != null
					&& mandamientoDTO.getResolutivo().getAudiencia() != null
					&& mandamientoDTO.getResolutivo().getAudiencia()
							.getExpediente() != null) {
				mandamientoDTO.getResolutivo().getAudiencia()
						.setExpediente(null);
			}

			mandamientoDTO.setMandamientosPersona(null);

			XStream converter=new XStream(); 			converter.alias("mandamientoJudicial", MandamientoDTO.class);
			String xml = converter.toXML(mandamientoDTO);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * M&eacute;todo que permite administrar los mandamientos persona mostrando
	 * el combobox para el cambio de estatus o el calendario para la fecha de
	 * ejecucion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward administrarMandamientosPersona(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			log.info("EJECUTANDO ACTION ----");
			log.debug("mandamientoJudicialId ... "
					+ request.getParameter("mandamientoJudicialId"));

			Long mandamientoJudicialId = NumberUtils.toLong(
					request.getParameter("mandamientoJudicialId"), 0L);

			if (mandamientoJudicialId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO = new FiltroMandamientoPersonaDTO();
			filtroMandamientoPersonaDTO.setMandamientoId(mandamientoJudicialId);

			List<MandamientoPersonaDTO> listaMandamientoPersonaDTO = administrarMandamientoJudicialDelegate
					.consultarMandamientosPersonaPorFiltro(filtroMandamientoPersonaDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaMandamientoPersonaDTO != null
					&& !listaMandamientoPersonaDTO.isEmpty()) {
				for (MandamientoPersonaDTO mandamientoPersonaDTO : listaMandamientoPersonaDTO) {

					// ID del renglon
					writer.print("<row id='"
							+ mandamientoPersonaDTO.getMandamientoPersonaId()
							+ "'>");

					/**
					 * NOMBRE DEL PROBABLE RESPONSABLE
					 */
					if (mandamientoPersonaDTO.getPersona() != null
							&& mandamientoPersonaDTO.getPersona()
									.getNombreCompleto() != null
							&& !mandamientoPersonaDTO.getPersona()
									.getNombreCompleto().isEmpty()) {
						writer.print("<cell>"
								+ mandamientoPersonaDTO.getPersona()
										.getNombreCompleto() + "</cell>");
					} else {
						writer.print("<cell>" + ANONIMO + "</cell>");

					}

					/**
					 * FECHA DEL RESOLUTIVO, SI LA INSTITUCION ES PJ
					 */
					if (mandamientoPersonaDTO.getMandamiento() != null
							&& mandamientoPersonaDTO.getMandamiento()
									.getResolutivo() != null
							&& mandamientoPersonaDTO.getMandamiento()
									.getResolutivo().getTemporizador() != null) {
						writer.print("<cell>"
								+ DateUtils.formatear(mandamientoPersonaDTO
										.getMandamiento().getResolutivo()
										.getTemporizador()) + "</cell>");
					} else {
						// Para PG, no contamos con resolutivo y mostraremos la,
						// fecha de creacion del mandamiento
						writer.print("<cell>"
								+ DateUtils.formatear(mandamientoPersonaDTO
										.getMandamiento().getFechaCreacion())
								+ "</cell>");
					}

					/**
					 * ESTATUS ACTUAL DEL MANDAMIENTO PERSONA
					 */
					if (mandamientoPersonaDTO.getEstatus() != null
							&& mandamientoPersonaDTO.getEstatus().getValor() != null) {
						writer.print("<cell>"
								+ mandamientoPersonaDTO.getEstatus().getValor()
								+ "</cell>");
					} else {
						writer.print("<cell>" + GUION + "</cell>");
					}

					/**
					 * PARA VALIDAR EL ESTATUS ACTUAL Y NO PODER REALIZAR
					 * ACTUALIZACIONES
					 */
					if (mandamientoPersonaDTO.getEstatus() != null
							&& mandamientoPersonaDTO.getEstatus().getIdCampo() != null) {
						writer.print("<cell>"
								+ mandamientoPersonaDTO.getEstatus()
										.getIdCampo() + "</cell>");
					} else {
						writer.print("<cell>" + GUION + "</cell>");
					}

					/**
					 * MUESTRA EL COMBO BOX DE CAMBIO DE ESTATUS
					 */
					if (mandamientoPersonaDTO.getEstatusTransicion() != null
							&& !mandamientoPersonaDTO.getEstatusTransicion()
									.isEmpty()) {

						// cambia estatus a:
						writer.print("<cell><![CDATA["
								+ "<select style='width:180px;' id='cbxCambioEstausMandPer_"
								+ mandamientoPersonaDTO
										.getMandamientoPersonaId() + "'>");

						writer.print("<option value=0> -Seleccione- </option>");

						for (CatalogoDTO catalogoTransicionEstatus : mandamientoPersonaDTO
								.getEstatusTransicion()) {
							writer.print("<option value='"
									+ catalogoTransicionEstatus.getClave()
									+ "'>"
									+ catalogoTransicionEstatus.getValor()
									+ "</option>");
						}
						writer.print("</select>" + "]]></cell>");
					} else {
						// cambia estatus a:
						writer.print("<cell><![CDATA[<select style='width:180px;' readonly='readonly'>");
						writer.print("<option value=0> " + NA + " </option>");
						writer.print("</select>" + "]]></cell>");
					}

					/**
					 * FECHA EJECUCION
					 */
					if (mandamientoPersonaDTO.getFechaEjecucion() != null) {
						String fechaEjecucion = DateUtils
								.formatear(mandamientoPersonaDTO
										.getFechaEjecucion());
						writer.print("<cell>" + fechaEjecucion + "</cell>");
					} else {

						// Solo se muestra el el calendario, cuando los estatus
						// del Mandamiento persona es terminal
						if (mandamientoPersonaDTO.getEstatus() != null
								&& mandamientoPersonaDTO.getEstatus()
										.getIdCampo() != null
								&& mandamientoPersonaDTO
										.getEstatus()
										.getIdCampo()
										.equals(EstatusMandamientoPersona.EN_PROCESO
												.getValorId())) {
							writer.print("<cell><![CDATA[<input type='text'  style='width:70px;' readonly='readonly' id='fechaEjecucion_"
									+ mandamientoPersonaDTO
											.getMandamientoPersonaId()
									+ "'>]]></cell>");
						} else {
							writer.print("<cell>" + GUION + "</cell>");
						}

					}

					/**
					 * DATOS DEL DOCUMENTO DE CAMBIO DE ESTATUS ACTUAL
					 */
					if (mandamientoPersonaDTO.getDocumentoEstatusActual() != null
							&& mandamientoPersonaDTO
									.getDocumentoEstatusActual().getDocumento() != null
							&& mandamientoPersonaDTO
									.getDocumentoEstatusActual().getDocumento()
									.getDocumentoId() != null) {

						// Liga documento
						writer.print("<cell><![CDATA[<a href='#' title='Ver Documento' onclick='abrirEditorDocumentosMandamientos("
								+ mandamientoPersonaDTO
										.getDocumentoEstatusActual()
										.getDocumento().getDocumentoId()
								+ ","
								+ Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL
										.getValorId()
								+ ","
								+ mandamientoPersonaDTO
										.getDocumentoEstatusActual()
										.getDocumento().getEsGuardadoParcial()
								+ ","
								+ Boolean.TRUE // Permite editar cuando es
												// guardado parcial
								+ ")'>"
								+ mandamientoPersonaDTO
										.getDocumentoEstatusActual()
										.getDocumento().getNombreDocumento()
								+ "</a>]]></cell>");

						// Guardado parcial
						writer.print("<cell>"
								+ obtenerValorEsParcial(mandamientoPersonaDTO
										.getDocumentoEstatusActual()
										.getDocumento().getEsGuardadoParcial())
								+ "</cell>");

					} else {
						// Liga documento
						writer.print("<cell>" + GUION + "</cell>");
						// Guardado parcial
						writer.print("<cell>" + GUION + "</cell>");
					}
					
					/**
					 * VER RELACION DELITO-PERSONA
					 */
					writer.print("<cell><![CDATA[<a href='#' title='Ver relaci&oacute;n delito-persona' onclick='abrirVerRelacionDelitoPersona("
					 + mandamientoPersonaDTO.getPersona().getElementoId()
					 + ","
					 + mandamientoPersonaDTO.getMandamientoPersonaId()
					 + ")'> Ver relaci&oacute;n delito-persona </a>]]></cell>");

					writer.print("</row>");
				}
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Obtiene un string, dependiendo del valor Booleano
	 * 
	 * @param esParcial
	 *            , puede ser null, true o false
	 * @return SI en caso de true, NO en caso false o null
	 */
	private String obtenerValorEsParcial(Boolean esParcial) {

		String SI = "Si";
		String NO = "No";

		if (esParcial) {
			return SI;
		} else {
			return NO;
		}
	}

	/**
	 * M&eacute;todo que permite administrar los mandamientos persona mostrando
	 * el combobox para el cambio de estatus o el calendario para la fecha de
	 * ejecucion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarMandamientosPersonaPorPersona(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			log.info("EJECUTANDO ACTION ----");
			log.debug("probaleResponsableId ... "
					+ request.getParameter("probRespId"));

			// variable que distingue cuando la consulta, es por
			// audiencia o por expediente
			Boolean esAudiencia = Boolean.parseBoolean(request
					.getParameter("esAudiencia"));

			// id del probResp
			Long probaleResponsableId = NumberUtils.toLong(
					request.getParameter("probRespId"), 0L);
			// id de la audiencia
			Long audienciaId = NumberUtils.toLong(
					request.getParameter("audienciaId"), 0L);

			if (probaleResponsableId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			// Se llena el objeto para el filtrado de mandamientos persona
			FiltroMandamientoPersonaDTO filtroMandamientoPersonaDTO = new FiltroMandamientoPersonaDTO();

			filtroMandamientoPersonaDTO.setPersonaId(probaleResponsableId);
			if (esAudiencia.equals(true)) {
				filtroMandamientoPersonaDTO.setAudienciaId(audienciaId);
			}

			UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);

			if (usuarioFirmado != null
					&& usuarioFirmado.getRolACtivo() != null
					&& usuarioFirmado.getRolACtivo().getRol() != null
					&& usuarioFirmado.getRolACtivo().getRol().getRolId() != null
					&& (usuarioFirmado.getRolACtivo().getRol().getRolId()
							.equals(Roles.DIRECTOR_APREHENSIONES.getValorId())

					|| usuarioFirmado.getRolACtivo().getRol().getRolId()
							.equals(Roles.POLICIAMINISTER.getValorId()))) {

				List<Long> listaTiposMandamientos = new ArrayList<Long>();

				listaTiposMandamientos.add(TipoMandamiento.ORDEN_DE_APREHENSION
						.getValorId());
				listaTiposMandamientos
						.add(TipoMandamiento.ORDEN_DE_REAPREHENSION
								.getValorId());
				listaTiposMandamientos
						.add(TipoMandamiento.ORDEN_DE_COMPARECENCIA
								.getValorId());

				filtroMandamientoPersonaDTO
						.setTipoMandamiento(listaTiposMandamientos);
			}

			List<MandamientoPersonaDTO> listaMandamientoPersonaDTO = administrarMandamientoJudicialDelegate
					.consultarMandamientosPersonaPorFiltro(filtroMandamientoPersonaDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaMandamientoPersonaDTO != null
					&& !listaMandamientoPersonaDTO.isEmpty()) {
				for (MandamientoPersonaDTO mandamientoPersonaDTO : listaMandamientoPersonaDTO) {

					// ID del renglon
					writer.print("<row id='"
							+ mandamientoPersonaDTO.getMandamientoPersonaId()
							+ "'>");

					/**
					 * TIPO DE MANDAMIENTO
					 */
					if (mandamientoPersonaDTO.getMandamiento() != null
							&& mandamientoPersonaDTO.getMandamiento()
									.getTipoMandamiento() != null
							&& mandamientoPersonaDTO.getMandamiento()
									.getTipoMandamiento().getValor() != null) {
						writer.print("<cell>"
								+ mandamientoPersonaDTO.getMandamiento()
										.getTipoMandamiento().getValor()
								+ "</cell>");
					} else {
						writer.print("<cell>" + GUION + "</cell>");
					}

					/**
					 * FECHA DEL RESOLUTIVO, SI LA INSTITUCION ES PJ
					 */
					if (mandamientoPersonaDTO.getMandamiento() != null
							&& mandamientoPersonaDTO.getMandamiento()
									.getResolutivo() != null
							&& mandamientoPersonaDTO.getMandamiento()
									.getResolutivo().getTemporizador() != null) {
						writer.print("<cell>"
								+ DateUtils.formatear(mandamientoPersonaDTO
										.getMandamiento().getResolutivo()
										.getTemporizador()) + "</cell>");
					} else {
						// Para PG, no contamos con resolutivo y mostraremos la,
						// fecha de creacion del mandamiento
						writer.print("<cell>"
								+ DateUtils.formatear(mandamientoPersonaDTO
										.getMandamiento().getFechaCreacion())
								+ "</cell>");
					}

					/**
					 * ESTATUS ACTUAL DEL MANDAMIENTO PERSONA
					 */
					if (mandamientoPersonaDTO.getEstatus() != null
							&& mandamientoPersonaDTO.getEstatus().getValor() != null) {
						writer.print("<cell>"
								+ mandamientoPersonaDTO.getEstatus().getValor()
								+ "</cell>");
					} else {
						writer.print("<cell>" + GUION + "</cell>");
					}

					/**
					 * FECHA EJECUCION
					 */
					if (mandamientoPersonaDTO.getFechaEjecucion() != null) {
						String fechaEjecucion = DateUtils
								.formatear(mandamientoPersonaDTO
										.getFechaEjecucion());
						writer.print("<cell>" + fechaEjecucion + "</cell>");
					} else {
						writer.print("<cell>" + GUION + "</cell>");
					}

					/**
					 * DATOS DEL DOCUMENTO DE CAMBIO DE ESTATUS ACTUAL
					 */
					if (mandamientoPersonaDTO.getDocumentoEstatusActual() != null
							&& mandamientoPersonaDTO
									.getDocumentoEstatusActual().getDocumento() != null
							&& mandamientoPersonaDTO
									.getDocumentoEstatusActual().getDocumento()
									.getDocumentoId() != null) {

						// Liga documento
						writer.print("<cell><![CDATA[<a href='#' title='Ver Documento' onclick='abrirEditorDocumentosMandamientos("
								+ mandamientoPersonaDTO
										.getDocumentoEstatusActual()
										.getDocumento().getDocumentoId()
								+ ","
								+ Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL
										.getValorId()
								+ ","
								+ mandamientoPersonaDTO
										.getDocumentoEstatusActual()
										.getDocumento().getEsGuardadoParcial()
								+ ","
								+ Boolean.FALSE // Permite editar cuando es
												// guardado parcial
								+ ")'>"
								+ mandamientoPersonaDTO
										.getDocumentoEstatusActual()
										.getDocumento().getNombreDocumento()
								+ "</a>]]></cell>");

					} else {
						// Liga documento
						writer.print("<cell>" + GUION + "</cell>");
					}

					/**
					 * DATOS DEL DOCUMENTO DEL MANDAMIENTO
					 */
					if (mandamientoPersonaDTO.getMandamiento() != null
							&& mandamientoPersonaDTO.getMandamiento()
									.getDocumentoId() != null) {

						// Liga documento
						writer.print("<cell><![CDATA[<a href='#' title='Ver Documento' onclick='abrirEditorDocumentosMandamientos("
								+ mandamientoPersonaDTO.getMandamiento()
										.getDocumentoId()
								+ ","
								+ Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL
										.getValorId()
								+ ","
								+ mandamientoPersonaDTO.getMandamiento()
										.getEsGuardadoParcial()
								+ ","
								+ Boolean.FALSE // Permite editar cuando es
												// guardado parcial
								+ ")'>"
								+ mandamientoPersonaDTO.getMandamiento()
										.getNombreDocumento()
								+ "</a>]]></cell>");

					} else {
						// Liga documento
						writer.print("<cell>" + GUION + "</cell>");
					}

					/**
					 * ID DEL MANDAMIENTO (COLUMNA OCULTA)
					 */
					writer.print("<cell>"
							+ mandamientoPersonaDTO.getMandamiento()
									.getDocumentoId() + "</cell>");

					writer.print("</row>");
				}
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * M&eacute;todo para consultar las relaciones Delito-Persona por Probable
	 * Responsable seleccionado en el Grid de PR
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, Se regresa en formato XML
	 * @throws IOException
	 */
	public ActionForward consultarRelDelitoPersonaPorMandamiento(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.debug("EJECUTANDO ACTION consultarRelDelitoPersonaPorMandamiento");
			log.debug("VERIFICANDO PARAMETRO..............................");
			log.debug("mandamientoJudicialId ... "
					+ request.getParameter("mandamientoJudicialId"));

			Long mandamientoJudicialId = NumberUtils.toLong(
					request.getParameter("mandamientoJudicialId"), 0L);

			if (mandamientoJudicialId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			MandamientoDTO mandamientoDTO = administrarMandamientoJudicialDelegate
					.consultarMandamientoPorId(mandamientoJudicialId);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			PaginadorUtil.obtenerPaginacionManual(mandamientoDTO
					.getDelitosPersona());
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (mandamientoDTO != null
					&& mandamientoDTO.getDelitosPersona() != null
					&& !mandamientoDTO.getDelitosPersona().isEmpty()) {
				for (DelitoPersonaDTO delitoPersonaDTO : mandamientoDTO
						.getDelitosPersona()) {

					writer.print("<row id='"
							+ delitoPersonaDTO.getDelitoPersonaId() + "'>");

					// Nombre del probable responsable
					writer.print("<cell>"
							+ ((delitoPersonaDTO.getProbableResponsable()
									.getNombreCompleto() != null && !delitoPersonaDTO
									.getProbableResponsable()
									.getNombreCompleto().trim().isEmpty()) ? delitoPersonaDTO
									.getProbableResponsable()
									.getNombreCompleto() : ANONIMO) + "</cell>");

					// Nombre del Delito
					writer.print("<cell>"
							+ ((delitoPersonaDTO.getDelito().getCatDelitoDTO() != null
									&& delitoPersonaDTO.getDelito()
											.getCatDelitoDTO().getNombre() != null && !delitoPersonaDTO
									.getDelito().getCatDelitoDTO().getNombre()
									.trim().isEmpty()) ? delitoPersonaDTO
									.getDelito().getCatDelitoDTO().getNombre()
									: DELITO_SIN_NOMBRE) + "</cell>");

					// Nombre de la Victima
					writer.print("<cell>"
							+ ((delitoPersonaDTO.getVictima()
									.getNombreCompleto() != null && !delitoPersonaDTO
									.getVictima().getNombreCompleto().trim()
									.isEmpty()) ? delitoPersonaDTO.getVictima()
									.getNombreCompleto() : ANONIMO) + "</cell>");
					writer.print("</row>");
				}
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();
			log.debug("FINALIZO LA EJECUCION DEL ACTION consultarRelDelitoPersonaPorProbableResponsableEnMandamientos");

		} catch (NSJPNegocioException ex) {
			log.error(ex.getMessage(), ex);
		}
		return null;
	}

	/**
	 * Consulta una lista de mandamientos judiciales en un periodo de tiempo
	 * Prepara el resultado para llenar un grid
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @author Emigdio Hern&aacute;ndez
	 * @return
	 * @throws IOException
	 */

	public ActionForward consultarDocumentosDelMandamiento(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			log.debug("*****EJECUTANDO CONSULTAR DOCUMENTOS DE MANDAMIENTO*********");
			log.debug("mandamientoJudicialId: "
					+ request.getParameter("mandamientoJudicialId"));
			log.debug("tipoDocumento: " + request.getParameter("tipoDocumento"));
			log.debug("numeroExpedienteId: "
					+ request.getParameter("numeroExpedienteId"));

			Long mandamientoJudicialId = NumberUtils.toLong(
					request.getParameter("mandamientoJudicialId"), 0L);

			Long tipoDocumentoId = NumberUtils.toLong(
					request.getParameter("tipoDocumento"), 0L);

			Long estatusDocumentoDeCambio = NumberUtils.toLong(
					request.getParameter("estatusDocumentoDeCambio"), 0L);

			Long probableResponsableId = NumberUtils.toLong(
					request.getParameter("probableResponsableId"), 0L);

			Long numeroExpedienteId = NumberUtils.toLong(
					request.getParameter("numeroExpedienteId"), 0L);

			if (tipoDocumentoId == null || mandamientoJudicialId == null
					|| mandamientoJudicialId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			} else {

				if (tipoDocumentoId
						.equals(TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL
								.getValorId())) {
					if (estatusDocumentoDeCambio == null
							|| estatusDocumentoDeCambio <= 0L
							|| probableResponsableId == null
							|| probableResponsableId <= 0L) {
						throw new NSJPNegocioException(
								CodigoError.PARAMETROS_INSUFICIENTES);
					}
				}
			}

			List<DocumentoDTO> listaDocumentosAsociados = new ArrayList<DocumentoDTO>();
			MandamientoDTO mandamientoDTO = new MandamientoDTO();
			PersonaDTO personaDTO = new PersonaDTO();
			String nombreCompleto = new String();

			if (tipoDocumentoId.equals(TipoDocumento.MANDAMIENTO_JUDICIAL
					.getValorId())
					|| tipoDocumentoId
							.equals(TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL
									.getValorId())) {
				mandamientoDTO = administrarMandamientoJudicialDelegate
						.consultarMandamientoPorId(mandamientoJudicialId);
				if (mandamientoDTO != null) {
					if (tipoDocumentoId
							.equals(TipoDocumento.MANDAMIENTO_JUDICIAL
									.getValorId())) {
						if (mandamientoDTO.getEsGuardadoParcial().equals(false)) {
							listaDocumentosAsociados.add(mandamientoDTO);
						}
					} else {
						obtenerDocumentosDeCambioDeEstatus(mandamientoDTO,
								listaDocumentosAsociados, personaDTO,
								probableResponsableId,
								estatusDocumentoDeCambio, nombreCompleto);
					}
				}
			} else {
				ExpedienteDTO expedienteDto = new ExpedienteDTO();
				expedienteDto.setNumeroExpedienteId(numeroExpedienteId);
				mandamientoDTO.setDocumentoId(mandamientoJudicialId);

				listaDocumentosAsociados = documentoDelegate
						.consultarDocumentosDeMandamientoJudicialPorExpediente(
								expedienteDto, mandamientoDTO, tipoDocumentoId);
			}

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			PaginadorUtil.obtenerPaginacionManual(listaDocumentosAsociados);
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			for (DocumentoDTO documentoDto : listaDocumentosAsociados) {

				writer.print("<row id='" + documentoDto.getDocumentoId() + "'>");

				if (tipoDocumentoId
						.equals(TipoDocumento.CAMBIO_DE_ESTADO_DE_MANDAMIENTO_JUDICIAL
								.getValorId())) {
					// Tipo de mandamiento
					writer.print("<cell>"
							+ mandamientoDTO.getTipoMandamiento().getValor()
							+ "</cell>");
					// Nombre del probable responsable
					writer.print("<cell>" + personaDTO.getNombreCompleto()
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
					writer.print("<cell>" + "---" + "</cell>");
				}

				// Fecha de creacion del documento
				writer.print("<cell>" + documentoDto.getStrFechaCreacion()
						+ "</cell>");
				// Nombre del documento
				if (documentoDto.getNombreDocumento() != null) {
					writer.print("<cell>" + documentoDto.getNombreDocumento()
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}

				writer.print("</row>");

			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			log.error(e.getCause(), e);
		}

		return null;
	}

	private void obtenerDocumentosDeCambioDeEstatus(
			MandamientoDTO mandamientoDTO,
			List<DocumentoDTO> listaDocumentosAsociados, PersonaDTO personaDTO,
			Long probableResponsableId, Long estatusDocumentoDeCambio,
			String nombreCompleto) {

		if (mandamientoDTO.getMandamientosPersona() != null
				&& !mandamientoDTO.getMandamientosPersona().isEmpty()) {
			for (MandamientoPersonaDTO mandamientoPersonaDTO : mandamientoDTO
					.getMandamientosPersona()) {
				if (mandamientoPersonaDTO.getPersona() != null
						&& mandamientoPersonaDTO.getPersona().getElementoId() != null
						&& mandamientoPersonaDTO.getPersona().getElementoId()
								.equals(probableResponsableId)) {

					personaDTO.setNombresDemograficoDTO(mandamientoPersonaDTO
							.getPersona().getNombresDemograficoDTO());
					personaDTO.setElementoId(mandamientoPersonaDTO.getPersona()
							.getElementoId());

					if (mandamientoPersonaDTO.getMandamientosPersonaDocumento() != null
							&& !mandamientoPersonaDTO
									.getMandamientosPersonaDocumento()
									.isEmpty()) {

						for (MandamientoPersonaDocumentoDTO mandPerDocumento : mandamientoPersonaDTO
								.getMandamientosPersonaDocumento()) {
							if (mandPerDocumento.getEstatus().getIdCampo()
									.equals(estatusDocumentoDeCambio)) {

								if (mandPerDocumento.getDocumento() != null
										&& mandPerDocumento.getDocumento()
												.getEsGuardadoParcial()
												.equals((false))) {
									listaDocumentosAsociados
											.add(mandPerDocumento
													.getDocumento());
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * M&eacute;todo para actualizar el estatus del mandamiento persona, ademas
	 * se puede actualizar la fecha de ejecucion. Genera el documento de cambio
	 * de estatus y las relaciones mandamientoPersonaDocumento
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward generarDocumentoCambioEstatusMandamientoPersona(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			String parametros = request.getParameter("parametros");
			Long mandamientoId = NumberUtils.toLong(
					request.getParameter("mandamientoId"), 0L);
			Long expedienteId = NumberUtils.toLong(
					request.getParameter("expedienteId"), 0L);

			if (parametros == null || parametros.isEmpty()
					|| mandamientoId == null || mandamientoId <= 0L
					|| expedienteId == null || expedienteId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			List<MandamientoPersonaDTO> listaMandamientoPersona = new ArrayList<MandamientoPersonaDTO>();
			MandamientoDTO mandamientoDTO = new MandamientoDTO();
			mandamientoDTO.setExpedienteDTO(new ExpedienteDTO(expedienteId));
			mandamientoDTO.setDocumentoId(mandamientoId);

			StringTokenizer mandamientosPersona = new StringTokenizer(
					parametros, "|");

			while (mandamientosPersona.hasMoreTokens()) {

				MandamientoPersonaDTO mandamientoPersonaDTO = new MandamientoPersonaDTO();

				String renglonMandPersona = mandamientosPersona.nextToken();

				StringTokenizer camposMandamientoPersona = new StringTokenizer(
						renglonMandPersona, ",");

				int contadorParametros = 0;
				while (camposMandamientoPersona.hasMoreTokens()) {

					if (contadorParametros == 0) {
						String campoMandamientoPersona = camposMandamientoPersona
								.nextToken();
						if (NumberUtils.toLong(campoMandamientoPersona, 0L) == 0L) {
							throw new NSJPNegocioException(
									CodigoError.INFORMACION_PARAMETROS_ERRONEA);
						}
						Long mandamientoPersonaId = NumberUtils
								.toLong(campoMandamientoPersona);
						mandamientoPersonaDTO
								.setMandamientoPersonaId(mandamientoPersonaId);
					} else if (contadorParametros == 1) {
						String campoMandamientoPersona = camposMandamientoPersona
								.nextToken();
						if (NumberUtils.toLong(campoMandamientoPersona, 0L) == 0L) {
							throw new NSJPNegocioException(
									CodigoError.INFORMACION_PARAMETROS_ERRONEA);
						}
						Long estatusSeleccionado = NumberUtils
								.toLong(campoMandamientoPersona);
						mandamientoPersonaDTO.setEstatus(new ValorDTO(
								estatusSeleccionado));
					} else if (contadorParametros == 2) {
						String campoMandamientoPersona = camposMandamientoPersona
								.nextToken();

						if (campoMandamientoPersona != null
								&& !campoMandamientoPersona.trim().isEmpty()
								&& !campoMandamientoPersona.equals(UNDEFINED)
								&& !campoMandamientoPersona.equals(NULO)) {
							Date fechaEjecucion = null;
							try {
								fechaEjecucion = DateUtils
										.obtener(campoMandamientoPersona);
							} catch (NSJPNegocioException e) {
								log.error("IMPOSIBLE PARSEAR FECHA EJECUCION****");
								throw new NSJPNegocioException(
										CodigoError.INFORMACION_PARAMETROS_ERRONEA);
							}
							mandamientoPersonaDTO
									.setFechaEjecucion(fechaEjecucion);
						}
					}
					contadorParametros++;
				}

				listaMandamientoPersona.add(mandamientoPersonaDTO);
			}

			Long documentoId = administrarMandamientoJudicialDelegate
					.actualizarMandamientoPersona(listaMandamientoPersona,
							super.getUsuarioFirmado(request), mandamientoDTO,
							null);

			String xml = "<respuesta>" + documentoId + "</respuesta>";

			escribirRespuesta(response, xml);

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribirError(response, e);
		}
		return null;
	}

	/**
	 * M&eacute;todo para enviar del mandamiento judicial a otra institucion
	 * (PG)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return respuesta con el id del mandamiento, cuando se env&iacute;a de
	 *         forma correcta
	 * @throws IOException
	 */
	public ActionForward enviarMandamiento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.debug("*****EJECUTANDO ENVIARMANDAMIENTOJUDICIAL********");
			log.debug("mandamientoJudicialId: "
					+ request.getParameter("mandamientoJudicialId"));

			Long mandamientoJudicialId = NumberUtils.toLong(
					request.getParameter("mandamientoJudicialId"), 0L);

			if (mandamientoJudicialId == null || mandamientoJudicialId < 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			MandamientoDTO mandamientoDTO = new MandamientoDTO();
			mandamientoDTO.setDocumentoId(mandamientoJudicialId);

			administrarMandamientoJudicialDelegate
					.enviarMandamientoJudicial(mandamientoDTO);
			String xml = "<documentoId>" + mandamientoDTO.getDocumentoId()
					+ "</documentoId>";
			escribirRespuesta(response, xml);

			log.debug("Mandamiento=" + mandamientoDTO.getDocumentoId()
					+ ",enviado correctamente");
		} catch (NSJPNegocioException ex) {
			log.debug("NO SE PUDO ENVIAR EL MANDAMIENTO***");
			log.error(ex.getMessage(), ex);
			escribirError(response, ex);
		}

		return null;
	}

	/**
	 * M&eacute;todo para enviar un documento de cambio de estatus de un
	 * mandamiento judicial a otra institucion (PG)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward enviarDocumentoCambioEstatusMandamiento(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.debug("*****EJECUTANDO ENVIAR DOCUMENTO DE CAMBIO DE ESTATUS********");
			log.debug("mandamientoJudicialId: "
					+ request.getParameter("mandamientoJudicialId"));
			log.debug("idDocCambioEstatus: "
					+ request.getParameter("idDocCambioEstatus"));

			Long mandamientoJudicialId = NumberUtils.toLong(
					request.getParameter("mandamientoJudicialId"), 0L);

			Long idDocCambioEstatus = NumberUtils.toLong(
					request.getParameter("idDocCambioEstatus"), 0L);

			if (mandamientoJudicialId == null || mandamientoJudicialId < 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			MandamientoDTO mandamientoDTO = new MandamientoDTO();
			mandamientoDTO.setDocumentoId(mandamientoJudicialId);

			DocumentoDTO documentoCambioEstatus = null;

			if (idDocCambioEstatus > 0L) {
				documentoCambioEstatus = new DocumentoDTO();
				documentoCambioEstatus.setDocumentoId(idDocCambioEstatus);
			}

			administrarMandamientoJudicialDelegate
					.enviarDocumentoCambioEstatusMandamiento(mandamientoDTO,
							documentoCambioEstatus);

			String xml = "<documentoId>" + mandamientoDTO.getDocumentoId()
					+ "</documentoId>";
			escribirRespuesta(response, xml);

			log.debug("Mandamiento=" + mandamientoDTO.getDocumentoId()
					+ ",enviado correctamente");
		} catch (NSJPNegocioException ex) {
			log.debug("NO SE PUDO ENVIAR LA ACTUALIZACION DEL MANDAMIENTO***");
			log.error(ex.getMessage(), ex);
			escribirError(response, ex);
		}

		return null;
	}

	/**
	 * Adjunta un documento a un mandamiento judicial, sin crear actividad,
	 * directamente en mandamiento adjuntos y sin adjuntarlo al expediente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return find mapping forwars con suucces o fail si tuvo exito o fallo
	 * @throws IOException
	 */
	public ActionForward adjuntarDocumentoAMandamientoJudicial(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("****EJECUTANDO ADJUNTAR DOCUMENTO A MANDAMIENTO JUDICIAL ACTION*****");

			AdjuntarDocumentoAMandamientoJudicialForm formaAdjuntarDocumento = (AdjuntarDocumentoAMandamientoJudicialForm) form;

			ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
			MandamientoDTO mandamientoJudicialDTO = new MandamientoDTO();

			mandamientoJudicialDTO.setDocumentoId(formaAdjuntarDocumento
					.getMandamientoJudicialId());

			FormFile archivo = formaAdjuntarDocumento.getArchivoAdjunto();
			String fileName = archivo.getFileName();
			byte[] fileData = archivo.getFileData();

			adjunto.setContenido(fileData);

			if (fileName != null) {
				String[] extension = fileName.split("\\.");
				adjunto.setTipoArchivo("." + extension[extension.length - 1]);
				adjunto.setNombreArchivo(extension[0]);
				log.info("El nombre del archivo es: " + extension[0]);
			}

			adjunto.setUsuario(super.getUsuarioFirmado(request));

			DocumentoDTO loDocuemntoDTO = new DocumentoDTO();
			loDocuemntoDTO.setArchivoDigital(adjunto);
			/* Obligatorios de Documento */
			loDocuemntoDTO.setNombreDocumento(adjunto.getNombreArchivo());
			loDocuemntoDTO.setFechaCreacion(new Date());
			// Se asigna el tipo de documento
			loDocuemntoDTO.setTipoDocumentoDTO(new ValorDTO(
					TipoDocumento.ARCHIVO_ADJUNTADO.getValorId()));
			loDocuemntoDTO.setEsGuardadoParcial(false);

			Long idArchivoDig = administrarMandamientoJudicialDelegate
					.adjuntarDocumentoAMandamientoJudicial(loDocuemntoDTO,
							mandamientoJudicialDTO,
							TipoDocumento.ARCHIVO_ADJUNTADO);

			log.info("archivo digital id:" + idArchivoDig);

			if (idArchivoDig != null) {
				return mapping.findForward(EXITO);
			} else {
				return mapping.findForward(FALLO);
			}

		} catch (Exception e) {
			log.info(e.getCause(), e);
			return mapping.findForward(FALLO);
		}

	}

	/**
	 * Metodo para consultar las relaciones Delito-Persona por probable responsable,
	 * del mandamiento persona
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward consultarRelacionDelitoPersonaProbableResp(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		//Se le pasa la session false para que si ya no esta activa no cree una nueva sesion
		HttpSession session = request.getSession(false);
		try {
			log.debug("*****EJECUTANDO CONSULTAR RELACION DELITO PERSONA POR PROBABLE RESPONSABLE DEL MANDAMIENTO PERSONA*********");
			log.debug("probableResponsableId: "
					+ request.getParameter("probableResponsableId"));
			log.debug("mandamientoPersonaId: "
					+ request.getParameter("mandamientoPersonaId"));
			log.debug("mandamientoJudicialId: "
					+ request.getParameter("mandamientoJudicialId"));

			Long probableResponsableId = NumberUtils.toLong(
					request.getParameter("probableResponsableId"), 0L);

			String mandamientoJudicialId = request
					.getParameter("mandamientoJudicialId");

			if (probableResponsableId == null || probableResponsableId < 0L
					|| mandamientoJudicialId == null
					|| mandamientoJudicialId.isEmpty()) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}else if (session == null){
				throw new NSJPNegocioException(
						CodigoError.SESSION_NULA);
			}
			
			/*
			 * Se obtiene el objeto mandamientoDTO de sesion subido en
			 * consultarMandamientoJudicial() para obtener las relaciones
			 * delito-persona del mandamiento
			 */
			MandamientoDTO mandamientoDTO = (MandamientoDTO) session
					.getAttribute(MANDAMIENTO_JUDICIAL_EN_SESION);

			if (mandamientoDTO == null
					|| mandamientoDTO.getDelitosPersona() == null
					|| mandamientoDTO.getDelitosPersona().isEmpty()) {
				log.error("MandamientoDTO es nulo. Y En DelitosPersona es:" + mandamientoDTO.getDelitosPersona());
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}

			//Se obtienen las  relaciones delito-persona del mandamiento judicial
			List<DelitoPersonaDTO> listaRelacionDelitoPersonaDelMandamiento = mandamientoDTO.getDelitosPersona();

			// Lista de Relaciones Delito-Persona obtenidos del mandamiento por el ID Probable Responsable
			List<DelitoPersonaDTO> listRelDelPersonaXProbResposable = new ArrayList<DelitoPersonaDTO>();

			for (DelitoPersonaDTO delitoPersonaDelMandamientoDTO : listaRelacionDelitoPersonaDelMandamiento) {
				if (delitoPersonaDelMandamientoDTO.getProbableResponsable().getElementoId().equals(probableResponsableId)){
					listRelDelPersonaXProbResposable.add(delitoPersonaDelMandamientoDTO);
				}
			}

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			PaginadorUtil.obtenerPaginacionManual(listRelDelPersonaXProbResposable);
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
			
			if (listRelDelPersonaXProbResposable != null
					&& !listRelDelPersonaXProbResposable.isEmpty()) {
				for (DelitoPersonaDTO delitoPersonaDTO : listRelDelPersonaXProbResposable) {

					writer.print("<row id='"
							+ delitoPersonaDTO.getDelitoPersonaId() + "'>");

					/* Nombre del Probable Responsable */
					if (delitoPersonaDTO.getProbableResponsable() != null
							&& delitoPersonaDTO.getProbableResponsable()
									.getNombreCompleto() != null
							&& !delitoPersonaDTO.getProbableResponsable()
									.getNombreCompleto().isEmpty()) {
						writer.print("<cell>"
								+ delitoPersonaDTO.getProbableResponsable()
										.getNombreCompleto() + "</cell>");
					} else {
						writer.print("<cell>" + ANONIMO + "</cell>");
					}

					/* Relacion delito-persona */
					if (delitoPersonaDTO.getDelito() != null
							&& delitoPersonaDTO.getDelito().getCatDelitoDTO() != null
							&& delitoPersonaDTO.getDelito().getCatDelitoDTO()
									.getNombre() != null) {
						writer.print("<cell>"
								+ delitoPersonaDTO.getDelito()
										.getCatDelitoDTO().getNombre()
								+ "</cell>");
					} else {
						writer.print("<cell>" + GUION + "</cell>");
					}

					/* Nombre de la Victima */
					if (delitoPersonaDTO.getVictima() != null
							&& delitoPersonaDTO.getVictima()
									.getNombreCompleto() != null
							&& !delitoPersonaDTO.getVictima()
									.getNombreCompleto().isEmpty()) {
						writer.print("<cell>"
								+ delitoPersonaDTO.getVictima()
										.getNombreCompleto() + "</cell>");
					} else {
						writer.print("<cell>" + ANONIMO + "</cell>");
					}

					writer.print("</row>");

				}
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();

			log.debug("*****TERMINO LA EJECUCION DE CONSULTAR RELACION DELITO PERSONA POR PROBABLE RESPONSABLE DEL MANDAMIENTO PERSONA*********");
		} catch (Exception e) {
			log.debug("NO SE PUDO CONSULTAR RELACION DELITO PERSONA POR PROBABLE RESPONSABLE DEL MANDAMIENTO PERSONA");
			log.error(e.getCause(), e);
			log.error(e.getMessage(), e);

		}
		return null;

	}

	/**
	 * M&eacute;todo que consulta las solicitudes asociadas al mandamiento
	 * judicial
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward consultarSolCumplimientoMandamientoJud(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			Long mandamientoJudicialId = NumberUtils.toLong(
					request.getParameter("mandamientoJudicialId"), 0L);

			if (mandamientoJudicialId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			SolicitudMandamientoDTO solicitudMandamientoDTO = new SolicitudMandamientoDTO();
			MandamientoDTO mandamientoDTO = new MandamientoDTO();

			mandamientoDTO.setDocumentoId(mandamientoJudicialId);
			solicitudMandamientoDTO.setMandamiento(mandamientoDTO);

			List<SolicitudMandamientoDTO> listaSolicitudesDeMandamiento = solicitudDelegate
					.consultarSolicitudesMandatoJudicialPorFiltro(solicitudMandamientoDTO);
			
			//QUitamos los campos que no deseamos se vean en vista
			if(listaSolicitudesDeMandamiento != null && !listaSolicitudesDeMandamiento.isEmpty()){
				for(SolicitudMandamientoDTO solMandamiento:listaSolicitudesDeMandamiento){
					if(solMandamiento.getMandamiento() != null){
						solMandamiento.getMandamiento().setArchivoDigital(null);
					}
					if(solMandamiento.getDestinatario() != null){
						solMandamiento.getDestinatario().setArchivoDigital(null);
					}
					if(solMandamiento.getResponsableDocumento() != null){
						solMandamiento.getResponsableDocumento().setArchivoDigital(null);
					}
				}
			}

			XStream converter=new XStream(); 			converter.alias("listaSolCumplMandamiento", List.class);
			XStream converter=new XStream(); 			converter.alias("solicitudMandamientoDTO",
					SolicitudMandamientoDTO.class);
			String xml = converter.toXML(listaSolicitudesDeMandamiento);
			escribirRespuesta(response, xml);

		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribirError(response, e);
		}
		return null;
	}
}

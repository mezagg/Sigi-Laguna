package mx.gob.segob.nsjp.web.solicitud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.avisodetencion.AvisoDetencionDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDetencionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.domicilio.TipoAsentamientoDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.MediaFiliacionDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDefensorDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.base.action.InvolucradoFormUtil;
import mx.gob.segob.nsjp.web.caso.form.IngresarActaCircunstanciadaForm;
import mx.gob.segob.nsjp.web.caso.form.IngresarIndividuoForm;
import mx.gob.segob.nsjp.web.solicitud.form.SolicitudDefensorDesdeDefensorAteForm;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class SolicitudDefensorAction extends GenericAction {

	@Autowired
	private SolicitudDelegate solicitudDelegate;
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	@Autowired
	private AvisoDetencionDelegate avisoDetencionDelegate;
	@Autowired
	private InvolucradoDelegate involucradoDelegate;
	@Autowired
	private NotificacionDelegate notificacionDelegate;
	@Autowired
	private ExpedienteDelegate expedienteDelegate;

	private static final Logger log = Logger
			.getLogger(SolicitudDefensorAction.class);
	
	private Long TIPO_PERSONA_FISICA = new Long(1L);

	/**
	 * Metodo que permite guardar/actualizar la solicitud de 
	 * Aseosr&iacute;a Legal.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	//MOD defensorATE
	public ActionForward guardaSolicitanteAsesoria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.debug("/**** GUARDAR SOLICITANTE DE ASESORIA ****/");
			
			IngresarIndividuoForm forma = (IngresarIndividuoForm) form;
			
			String motivoAsesoria = request.getParameter("motivoAsesoria");
			log.debug("motivo de asesoria:"+motivoAsesoria);
			Long idIndividuo = NumberUtils.toLong(request.getParameter("idIndividuo"), 0L);
			log.debug("ElemEnto id:"+idIndividuo);
			Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);
			log.debug("idExpediente====" + idExpediente);
			Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId"), 0L);
			log.debug("solicitudId====" + solicitudId);
			
			//EXPEDIENTE
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			if(idExpediente > 0L){
				expedienteDTO.setExpedienteId(idExpediente);
			}
			expedienteDTO.setUsuario(super.getUsuarioFirmado(request));
			
			IngresarIndividuoForm retorno = new IngresarIndividuoForm();
			
			InvolucradoDTO involucradoDTO = InvolucradoFormUtil
					.extraerDatosInvolucradoForm(expedienteDTO, retorno, forma,
							request);
			if (log.isDebugEnabled()) {
				log.debug("::::::::::::::INVOLUCRADO:::::::::Ingresar:::::::::::"+ involucradoDTO);
				log.debug("RETORNO:");
				log.debug("Nombre:"+retorno.getNombre());
			}
			if(idIndividuo > 0L ){
				involucradoDTO.setElementoId(idIndividuo);
				log.debug("involucradoAsesoiaLegal.getElementoId()-" + involucradoDTO.getElementoId());
			}

			//DATOS DE LA SOLICITUD
			SolicitudDefensorDTO solicitudDefensorDTO = new SolicitudDefensorDTO();
			if(solicitudId >0L){
				solicitudDefensorDTO.setDocumentoId(solicitudId);
			}
			solicitudDefensorDTO.setInvolucradoDTO(involucradoDTO);
			solicitudDefensorDTO.setMotivo(motivoAsesoria);
			solicitudDefensorDTO.setExpedienteDTO(expedienteDTO);
			

			SolicitudDefensorDTO solicitudDefensorRespuestaDTO = solicitudDelegate
					.actualizarSolicitudAsesoriaLegal(solicitudDefensorDTO);
			
			//DATOS DE RESPUESTA
			ExpedienteDTO expedienteRespuestaDTO = solicitudDefensorRespuestaDTO
					.getExpedienteDTO();
			if (expedienteRespuestaDTO != null) {
				solicitudDefensorDTO.setExpedienteDTO(new ExpedienteDTO(
						expedienteRespuestaDTO.getExpedienteId()));
			}
			InvolucradoDTO defendido = solicitudDefensorRespuestaDTO
					.getInvolucradoDTO();
			if (defendido != null) {
				solicitudDefensorDTO.setInvolucradoDTO(new InvolucradoDTO(
						defendido.getElementoId()));
			}
			
			XStream converter=new XStream();
			converter.alias("solicitud", SolicitudDefensorDTO.class);
			String xml = converter.toXML(solicitudDefensorRespuestaDTO);
			escribirRespuesta(response, xml);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	/**
	 * Metodo que consulta la solicitud por ID, con la opci&oacute;n de
	 * recortar la informaci�n de la solicitud para que sea una 
	 * consulta b&aacute;sica
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	//MOD defensorATE
	public ActionForward consultarSolicitudBasico(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		try{
			log.debug("Action para consultar la informacion de la solicitud basica.");
			Long idSolicitud = NumberUtils.toLong(request
					.getParameter("idSolicitud"));		
			
			SolicitudDTO solicitudDTO = solicitudDelegate
					.consultarSolicitudXId(idSolicitud);
			
			//Recortar Solicitud
			solicitudDTO.setExpedienteDTO( null);
			solicitudDTO.setUsuarioSolicitante(null);
			solicitudDTO.setInstitucion(null);
			
			 XStream converter=new XStream(); 			converter.alias("solicitudDTO", SolicitudDTO.class);
			 String xml = converter.toXML(solicitudDTO);
			 escribirRespuesta(response, xml);
			 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}

	
	
	
	/**
	 * Metodo utilizado para la consulta de avisos de detencion por Estatus No
	 * atendida.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             en caso de obtener una exception
	 */

	public ActionForward consultarAvisosDeDetencion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("Solicitudes no Atendidas");

			//Se obtiene el usuario firmado
			UsuarioDTO usuarioDTO=super.getUsuarioFirmado(request);
			
			Long idDiscriminante = null;
		
			if(usuarioDTO != null && usuarioDTO.getFuncionario()!= null && usuarioDTO.getFuncionario().getDiscriminante()!= null){
				idDiscriminante = usuarioDTO.getFuncionario().getDiscriminante().getCatDiscriminanteId();
			}
			
			log.info("El discirminante obtenido es: " + idDiscriminante);	

			
			List<AvisoDetencionDTO> avisoDetencionDTOs = avisoDetencionDelegate
					.obtenerAvisosDetencionPorEstatus(EstatusNotificacion.NO_ATENDIDA, idDiscriminante);
			log.info("consultar avisos de detencion" + avisoDetencionDTOs);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			int lTotalRegistros = avisoDetencionDTOs.size();

			writer.print("<records>" + lTotalRegistros + "</records>");

			for (AvisoDetencionDTO avisoDetencionDTO : avisoDetencionDTOs) {

				writer.print("<row id='" + avisoDetencionDTO.getDocumentoId()
						+ "'>");
				writer.print("<cell>" + avisoDetencionDTO.getFolioDocumento()
						+ "</cell>");

				if (avisoDetencionDTO.getExpedienteDTO() != null
						&& avisoDetencionDTO.getExpedienteDTO().getCasoDTO() != null
						&& avisoDetencionDTO.getExpedienteDTO().getCasoDTO()
								.getNumeroGeneralCaso() != null) {

					writer.print("<cell>"
							+ avisoDetencionDTO.getExpedienteDTO().getCasoDTO()
									.getNumeroGeneralCaso() + "</cell>");
				} else {
					writer.print("<cell>" + "-" + "</cell>");
				}
				writer.print("<cell>" + avisoDetencionDTO.getDetenido()
						+ "</cell>");
				writer.print("<cell>" + "-" + "</cell>");
				writer.print("<cell>" + "-" + "</cell>");
				writer.print("<cell>"
						+ DateUtils.formatear(avisoDetencionDTO
								.getFechaDetencion())
						+ "-"
						+ DateUtils.formatearHora(avisoDetencionDTO
								.getFechaDetencion()) + "</cell>");
				writer.print("<cell>"
						+ DateUtils.formatear(avisoDetencionDTO
								.getFechaCreacion())
						+ "-"
						+ DateUtils.formatearHora(avisoDetencionDTO
								.getFechaCreacion()) + "</cell>");
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para la consulta de solicitudes de defensor.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             en caso de obtener una exception
	 */

	@SuppressWarnings("unchecked")
	public ActionForward consultarSolicitudesDeDefensor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("Consultar Solicitudes De Defensor");

			List<SolicitudDTO> solicitudDTOs = (List<SolicitudDTO>) solicitudDelegate
					.consultarSolicitudXEstatus(EstatusSolicitud.ABIERTA,
							super.getUsuarioFirmado(request),
							TiposSolicitudes.DEFENSOR);
			if (log.isDebugEnabled()) {
				log.debug("lista de solicitudes  de defensor" + solicitudDTOs);
			}
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");

			int lTotalRegistros = solicitudDTOs.size();

			writer.print("<records>" + lTotalRegistros + "</records>");

			for (SolicitudDTO solicitudDTO : solicitudDTOs) {

				writer.print("<row id='" + solicitudDTO.getDocumentoId() + "-"
						+ solicitudDTO.getExpedienteDTO() != null ? solicitudDTO
						.getExpedienteDTO().getNumeroExpediente() : "" + "'>");
				writer.print("<cell>" + solicitudDTO.getAreaOrigen()
						+ "</cell>");
				writer.print("<cell>" + solicitudDTO.getNumeroCasoAsociado()
						+ "</cell>");
				writer.print("<cell>"
						+ solicitudDTO.getExpedienteDTO().getNumeroExpediente()
						+ "</cell>");
				writer.print("<cell>"
						+ solicitudDTO.getExpedienteDTO().getEtapa()
						+ "</cell>");

				for (InvolucradoDTO involucradoDTOs : solicitudDTO
						.getExpedienteDTO().getInvolucradoByCalidad(
								Calidades.PROBABLE_RESPONSABLE_PERSONA)) {

					writer.print("<cell>" + involucradoDTOs.getNombreCompleto()
							+ "</cell>");

					for (DetencionDTO detencionDTO : involucradoDTOs
							.getDetenciones()) {

						writer.print("<cell>"
								+ detencionDTO.getStrFechaInicioDetencion()
								+ "-" + detencionDTO.getHoraInicioDetencion()
								+ "</cell>");

					}

					writer.print("<cell>");

					for (DelitoDTO delitoDTO : involucradoDTOs
							.getDelitosCometidos()) {

						writer.print(delitoDTO.getCatDelitoDTO().getNombre()
								+ " ");

					}
					writer.print("</cell>");

				}
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para la consulta de defensor por id.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             en caso de obtener una exception
	 */

	public ActionForward consultarDefensorAsignado(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			XStream converter=new XStream();
			log.info("Consultar Defensor asignado");

			String idExpediente = request.getParameter("idExpediente");
			log.info("llega idExpediente" + idExpediente);

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setExpedienteId(NumberUtils.toLong(idExpediente));

			FuncionarioDTO funcionario = funcionarioDelegate
					.obtenerDefensorAsignadoAExpediente(expedienteDTO);
			log.info("llega funcionario" + funcionario);

			if (funcionario != null) {
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");
				writer.print("<records>1</records>");
				writer.print("<row id='" + funcionario.getClaveFuncionario()+ "'>");
				writer.print("<cell>"+ funcionario.getNombreFuncionario()+ "</cell>");
				writer.print("<cell>"+ funcionario.getApellidoPaternoFuncionario()+ "</cell>");
				writer.print("<cell>"+ funcionario.getApellidoMaternoFuncionario()+ "</cell>");
				writer.print("</row>");
				writer.print("</rows>");
				writer.flush();
				writer.close();

			} else {

				String xml = converter.toXML("El expediente aún no tiene defensor designado, favor de verificar");
				escribir(response, xml, null);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * M&eacute;todo utilizado para el cambio de un defensor asignado.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             en caso de obtener una exception
	 */
	public ActionForward cambiarDefensorAsignado(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("******REASIGNAR DEFENSOR**************");

			Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"),0L);
			log.info("llega idExpediente" + idExpediente);
			XStream converter=new XStream();
			if(idExpediente > 0){
				ExpedienteDTO input = new ExpedienteDTO();
				input.setNumeroExpedienteId(idExpediente);
				input.setUsuario(getUsuarioFirmado(request));
				AvisoDesignacionDTO aviso = notificacionDelegate.registrarAvisoDesignacionPorReasignacionDefensor(input);
				String xml = converter.toXML(aviso);
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
			}else{
				String xml = converter.toXML("Error");
				response.setContentType("text/xml");
				PrintWriter pw = response.getWriter();
				pw.print(xml);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para la consulta de las solicitudes asignadas a un
	 * defensor por id.
	 * SOLO SOLUCITUDES DEFENSOR
	 * --Defensor (esASignada): Se pasa el Funcionario y el estatus de NO_ATENDIDAS. 
	 * --CoordinadorDEF: Se omite el Funcioanrio y el estatus es NO_ATENDIDAS.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             en caso de obtener una exception
	 */
	//MOD Defensor, CoordinadorDEF
	public ActionForward consultarSolicitudesDefensorAsignadas(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.debug("Consultar las solicitudes de defensor Asignadas o no Asignados (CoordinadorDEF)");
			
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			Boolean esAsignada = BooleanUtils.toBoolean(request
					.getParameter("esAsignada"));
			
			//Es asignada, se hace la consulta desde el Defensor
			FuncionarioDTO funcionarioDTO = null;
			if(esAsignada){
				funcionarioDTO = usuarioDTO.getFuncionario();
			}
			
			Long distritoId = null;
			if (usuarioDTO.getFuncionario().getDiscriminante()
					.getDistrito() != null
					&& usuarioDTO.getFuncionario().getDiscriminante()
							.getDistrito().getCatDistritoId() != null) {
				distritoId = usuarioDTO.getFuncionario().getDiscriminante()
						.getDistrito().getCatDistritoId();
				
			}
			
			Long tipoSolicitudId = NumberUtils.toLong(
					request.getParameter("tipoSolicitud"),
					TiposSolicitudes.DEFENSOR.getValorId());
			TiposSolicitudes tipoSolicitud = (tipoSolicitudId > 0L ? TiposSolicitudes
					.getByValor(tipoSolicitudId) : null);
			
			EstatusNotificacion estadoNotificacion = EstatusNotificacion.NO_ATENDIDA;
			
			log.debug("**recibirDesignaciones-estadoNotificacion:"
					+ estadoNotificacion + "-Funcionario:"
					+ (funcionarioDTO!=null?funcionarioDTO.getClaveFuncionario(): null )
					+ " - tipoSolicitud:" + tipoSolicitud + "-distritoId:"
					+ distritoId);
			
			List<AvisoDesignacionDTO> designaciones = notificacionDelegate
					.consultarAvisosDesignacion(estadoNotificacion, funcionarioDTO, tipoSolicitud, distritoId);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			//Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			Long numeroExpedienteId = null;
			String institucion = " - ";
			String folioSolicitud = " - ";
			String numeroGCaso = " - ";
			String numeroExped = " - ";
			String etapaExpedt = " - ";
			String nombreImput = "-";
			String sfuncionari = " Sin Defensor ";
			Long solicitudDefensorId = 0L;
			Long expedienteId = 0L;
			
			for (AvisoDesignacionDTO designacion : designaciones) {
				numeroExpedienteId = null;
				institucion = " - ";
				folioSolicitud = " - ";
				numeroGCaso = " - ";
				numeroExped = " - ";
				etapaExpedt = " - ";
				nombreImput = "-";
				sfuncionari = " Sin Defensor ";
				solicitudDefensorId = 0L;
				expedienteId = 0L;
				
				if (designacion.getSolicitudDefensor() != null) {
					solicitudDefensorId = designacion.getSolicitudDefensor()
							.getDocumentoId();
					institucion = designacion.getSolicitudDefensor()
							.getInstitucion().getNombreInst();
					folioSolicitud = designacion.getSolicitudDefensor()
							.getFolioSolicitud();
					numeroGCaso = designacion.getSolicitudDefensor()
							.getNumeroCasoAsociado();
					
					List<InvolucradoDTO> imputados = solicitudDelegate
							.consultarInvolucradosSolicitudDefensor(designacion
									.getSolicitudDefensor().getDocumentoId());
					// Combo box defendidos
					if (imputados != null && !imputados.isEmpty()) {
						nombreImput = "<cell><![CDATA["
								+ "<select id='cbxDefendidos' style='width: 300px;'>";
						for (InvolucradoDTO involucrado : imputados) {
							nombreImput = nombreImput + "<option>"
									+ involucrado.getNombreCompleto()
									+ "</option>";
						}
						nombreImput = nombreImput + "</select>" + "]]></cell>";
					} else {
						nombreImput = "<cell> - </cell>";
					}
				}
				
				//ETAPA - NUMERO EXPEDIENTE
				if (designacion.getExpediente() != null) {
					if (designacion.getExpediente().getCatEtapaDTO() != null
							&& designacion.getExpediente().getCatEtapaDTO()
									.getCatEtapaValor() != null
							&& designacion.getExpediente().getCatEtapaDTO()
									.getCatEtapaValor().getIdCampo() != null) {
						etapaExpedt = designacion.getExpediente()
								.getCatEtapaDTO().getCatEtapaValor().getValor();
					} else {
						etapaExpedt = " - ";
					}
					
					numeroExped = designacion.getExpediente()
							.getNumeroExpediente();
					numeroExpedienteId = designacion.getExpediente()
							.getNumeroExpedienteId();
				}

				if (designacion.getFuncionario() != null) {
					sfuncionari = designacion.getFuncionario()
							.getNombreCompleto();
				}
				
				String fechaCreacion = DateUtils.formatear(designacion
						.getFechaCreacion());
				String horaCreacion = DateUtils.formatearHora(designacion
						.getFechaCreacion());

				if (designacion.getExpediente() != null
						&& designacion.getExpediente().getExpedienteId() != null
						&& designacion.getExpediente().getExpedienteId() > 0L) {
					expedienteId = designacion.getExpediente()
							.getExpedienteId();
				}

				writer.print("<row id='" + designacion.getDocumentoId() + "'>");
				writer.print("<cell>" + institucion + "</cell>");
				writer.print("<cell>" + folioSolicitud + "</cell>");
				writer.print("<cell>" + numeroGCaso + "</cell>");
				writer.print("<cell>" + numeroExped + "</cell>");
				writer.print(nombreImput);
				writer.print("<cell>" + etapaExpedt + "</cell>");
				writer.print("<cell>" + fechaCreacion + " - " + horaCreacion
						+ "</cell>");
				writer.print("<cell>" + sfuncionari + "</cell>");
				writer.print("<cell>" + numeroExpedienteId + "</cell>");
				writer.print("<cell>" + solicitudDefensorId + "</cell>");
				writer.print("<cell>" + expedienteId + "</cell>");
				writer.print("</row>");
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Metodo utilizado para la consulta de las solicitudes asignadas a un
	 * defensor por id.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             en caso de obtener una exception
	 */
	public ActionForward consultarSolicitudesPorAtender(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("Consultar las solicitudes de carpeta de investigacion por atender");
			String tipo = request.getParameter("tipoSolicitud");
			Long tipoSolicitud =  NumberUtils.toLong(tipo, TiposSolicitudes.CARPETA_DE_INVESTIGACION.getValorId());
			Long claveFuncionario = getUsuarioFirmado(request).getFuncionario().getClaveFuncionario();
			List<SolicitudDTO> solicitudes = solicitudDelegate
					.consultarSolicitudesPorTipoYNoEstatus(TiposSolicitudes.getByValor(tipoSolicitud),
							EstatusSolicitud.CERRADA, claveFuncionario);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
			
			String caso = "", expediente = "", folio = "", estatus = "";
			String fechaCreacion = "", fechaLimite = "", institucion = "";
			String destinatario = "";

			for (SolicitudDTO solicitud : solicitudes) {

				caso = solicitud.getNumeroCasoAsociado();
				if(solicitud.getExpedienteDTO() !=null){
					expediente = solicitud.getExpedienteDTO().getNumeroExpediente();
				}
				folio = solicitud.getFolioSolicitud();
				if(solicitud.getEstatus() != null){
					estatus = solicitud.getEstatus().getValor();
				}
				Date fecha = solicitud.getFechaCreacion();
				fechaCreacion = DateUtils.formatear(fecha) + " - "+ DateUtils.formatearHora(fecha);
				fecha = solicitud.getFechaLimite();
				fechaLimite = DateUtils.formatear(fecha) + " - "+ DateUtils.formatearHora(fecha);
				if (solicitud.getDestinatarioInstExterna() != null
						&& solicitud.getDestinatarioInstExterna()
								.getConfInstitucionDTO() != null
						&& solicitud.getDestinatarioInstExterna()
								.getConfInstitucionDTO().getNombreInst() != null
						&& !solicitud.getDestinatarioInstExterna()
								.getConfInstitucionDTO().getNombreInst()
								.isEmpty()) {
					institucion = solicitud.getDestinatarioInstExterna()
							.getConfInstitucionDTO().getNombreInst();
				}
				
				if (solicitud.getDestinatarioInstExterna() != null
						&& solicitud.getDestinatarioInstExterna().getNombre() != null
						&& !solicitud.getDestinatarioInstExterna().getNombre()
								.isEmpty()
						&& solicitud.getDestinatarioInstExterna()
								.getApellidoPaterno() != null
						&& !solicitud.getDestinatarioInstExterna()
								.getApellidoPaterno().isEmpty()) {

					if (solicitud.getDestinatarioInstExterna()
							.getApellidoMaterno() != null
							&& !solicitud.getDestinatarioInstExterna()
									.getApellidoMaterno().isEmpty()) {
						destinatario = solicitud.getDestinatarioInstExterna()
								.getNombre()
								+ " "
								+ solicitud.getDestinatarioInstExterna()
										.getApellidoPaterno()
								+ " "
								+ solicitud.getDestinatarioInstExterna()
										.getApellidoMaterno();
					} else {
						destinatario = solicitud.getDestinatarioInstExterna()
								.getNombre()
								+ " "
								+ solicitud.getDestinatarioInstExterna()
										.getApellidoPaterno();
					}
				}

				writer.print("<row id='" + solicitud.getDocumentoId() + "'>");
				writer.print("<cell>" + caso + "</cell>");
				writer.print("<cell>" + expediente + "</cell>");
				writer.print("<cell>" + folio + "</cell>");
				writer.print("<cell>" + estatus + "</cell>");
				writer.print("<cell>" + fechaCreacion + "</cell>");
				writer.print("<cell>" + fechaLimite + "</cell>");
				writer.print("<cell>" + institucion + "</cell>");
				writer.print("<cell>" + destinatario + "</cell>");
				writer.print("</row>");
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	
	/**
	 * Metodo utilizado para obtener el detalle de la solicitud del defensor.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 * 
	 */
	public ActionForward obtenerDetalleDefensoria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("Obtiene el detalle de la solicitud del defensor");

			int tipoDocumento = NumberUtils.toInt(
					request.getParameter("tipoDocumento"), 0);
			
			Long idDocumento = NumberUtils.toLong(
					request.getParameter("idDocumento"), 0);
			
			Long numeroExpedienteId = NumberUtils.toLong(
					request.getParameter("numeroExpedienteId"), 0);
			
			String numeroCaso = request.getParameter("numeroCaso");
			
			
			if (log.isDebugEnabled()) {
				log.debug("documento id  ::: " + idDocumento);
				log.debug("tipoDocumento ::: " + tipoDocumento);

			}
			
			if (numeroCaso != null && !numeroCaso.equals("")
					&& numeroExpedienteId != null) {
				
				ExpedienteDTO expedienteDTO = new ExpedienteDTO();
				CasoDTO casoDTO = new CasoDTO();
				
				casoDTO.setNumeroGeneralCaso(numeroCaso);
				expedienteDTO.setCasoDTO(casoDTO);
				expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
				expedienteDelegate
						.asignarNumeroCasoSolicitudDefensor(expedienteDTO);
			}
			

			String xml = null;
			PrintWriter pw = null;
			XStream converter=new XStream();
			switch(tipoDocumento){
				case 3: //AVISO DE DESIGNACION
					AvisoDesignacionDTO designacion = notificacionDelegate.consultarAvisoDesignacion(idDocumento);
					converter.alias("designacion", AvisoDesignacionDTO.class);
					converter.alias("nombreDemograficoDTO", NombreDemograficoDTO.class);
					converter.alias("involucradoDTO", InvolucradoDTO.class);
					converter.alias("delitosAvisoDetencion", LinkedList.class);
					converter.alias("delitoDTO", DelitoDTO.class);
					converter.alias("detencionDTO", DetencionDTO.class);
					if (designacion.getExpedienteDTO() != null){						
						designacion.getExpediente().setDelitos(null);
						super.setExpedienteTrabajo(request, designacion.getExpediente());
					}
					xml = converter.toXML(designacion);
					break;
				case 2: // SOLICITUD DE DEFENSOR DE ATENCION TEMPRANA
				case 4: // SOLICITUD DE DEFENSOR DE PODER JUDICIAL
				case 5: // ASESORIA LEGAL
					SolicitudDefensorDTO solicitud = new SolicitudDefensorDTO();
					solicitud.setDocumentoId(idDocumento);
					solicitud= solicitudDelegate.obtenerSolicitudDefensor(solicitud);
					converter.alias("solicitud", SolicitudDefensorDTO.class);
					converter.alias("involucradoDTO", InvolucradoDTO.class);
					converter.alias("nombreDemograficoDTO", NombreDemograficoDTO.class);
					if (solicitud.getExpedienteDTO() != null){
						solicitud.getExpedienteDTO().setDelitos(null);
						super.setExpedienteTrabajo(request, solicitud.getExpedienteDTO());						
					}
					xml = converter.toXML(solicitud);
					break;
				case 1: // AVISO DE DETENCION
					AvisoDetencionDTO avisoDetencionDTO = new AvisoDetencionDTO();
					avisoDetencionDTO.setDocumentoId(idDocumento);
					avisoDetencionDTO = avisoDetencionDelegate.obtenerAvisoDetencion(avisoDetencionDTO);
					converter.alias("avisoDetencion", AvisoDetencionDTO.class);
					converter.alias("involucradoDTO", InvolucradoDTO.class);
					if (avisoDetencionDTO.getExpedienteDTO() != null){
						avisoDetencionDTO.getExpedienteDTO().setDelitos(null);
						super.setExpedienteTrabajo(request, avisoDetencionDTO.getExpedienteDTO());						
					}
					xml = converter.toXML(avisoDetencionDTO);
					break;
			}			
			log.info("xml ::: "+xml);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	

	/**
	 * funcion para consultar los datos Documentos generados para el visor de elementos
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	//TODO GBP Utiliza rel componente Generico
	public ActionForward consultarDocumentosDefensoria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			log.info("*******CONSULTAR DOCUMENTOS DEFENSORIA**********");
			log.info("VERIFICANDO PARAMETROS**********");
			log.info("numeroExpedienteId......"
					+ request.getParameter("idExpedienteop"));
			log.info("ExpedienteId......"
					+ request.getParameter("expedienteId"));
			log.info("Tipo......" + request.getParameter("tipo"));

			Long numeroExpedienteId = NumberUtils.toLong(
					request.getParameter("idExpedienteop"), 0L);
			Long expedienteId = NumberUtils.toLong(
					request.getParameter("expedienteId"), 0L);
//			String tipo = request.getParameter("tipo");

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();

			//Para algunos roles se restringe la consulta de documentos, el usuario solo podra consultar los ducmentos de su area.
			List<Long> idJerarquias = new ArrayList<Long>();
			idJerarquias.add(Areas.DEFENSORIA.parseLong());
			idJerarquias.add(Areas.COORDINACION_DEFENSORIA.parseLong());
			idJerarquias.add(Areas.ATENCION_TEMPRANA_DEFENSORIA.parseLong());
			
			expedienteDTO.setIdsJerarquiasOrganizacionales(idJerarquias);
			
//			if (tipo.equals("1")) {
//				expedienteDTO.setArea(new AreaDTO(Areas.DEF));
//				log.info("tipo uno");
//			}
//			if (tipo.equals("2")) {
//				expedienteDTO
//						.setArea(new AreaDTO(Areas.COORDINACION_DEFENSORIA));
//				log.info("tipo dos");
//			}
//			if (tipo.equals("3")) {
//				expedienteDTO.setArea(new AreaDTO(Areas.DEFENSORIA));
//				log.info("tipo 3");
//			}

			UsuarioDTO usuarioDTO=super.getUsuarioFirmado(request);
			RolDTO rolActivoDTO=null;
			if(usuarioDTO.getRolACtivo()!=null && usuarioDTO.getRolACtivo().getRol()!=null){
				rolActivoDTO=usuarioDTO.getRolACtivo().getRol();
				rolActivoDTO.getJerarquiaOrganizacionalDTO();
				log.debug("RolActivo:"+ rolActivoDTO);
				log.debug("RolActivo-Jerarquia:"+ rolActivoDTO.getJerarquiaOrganizacionalDTO());
			}
			//Se setea el area acutual del usuario que esta en sesion.
			if(usuarioDTO.getAreaActual() != null && usuarioDTO.getAreaActual().getAreaId() != null){
				expedienteDTO.setIdAreaActual(usuarioDTO.getAreaActual().getAreaId());
				log.debug("AreaActual Usiario:"+usuarioDTO.getAreaActual());
			}
			
			
			expedienteDTO.setExpedienteId(expedienteId);
			expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);

			log.debug("expedienteId:"+expedienteId);
			log.debug("numeroExpedienteId:"+numeroExpedienteId);
			
			List<DocumentoDTO> listaDocumentoDTOs = documentoDelegate
					.consultarDocumentosExpediente(expedienteDTO);

			request.getSession().setAttribute("totalRegistrosDocumentos",
					listaDocumentoDTOs.size());

			request.setAttribute("totalRegistrosDocumentos",
					listaDocumentoDTOs.size());
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaDocumentoDTOs != null && !listaDocumentoDTOs.isEmpty()) {

				for (DocumentoDTO documentoDTO : listaDocumentoDTOs) {

					writer.print("<row id='" + documentoDTO.getDocumentoId()
							+ "'>");
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
							+ documentoDTO.getTipoDocumentoDTO().getValor()
							+ " </div>]]></cell>");
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
							+ (documentoDTO.getActividadDTO() != null
									&& documentoDTO.getActividadDTO()
											.getFechaCreacion() != null ? DateUtils
									.formatear(documentoDTO.getActividadDTO()
											.getFechaCreacion()) : "-")
							+ " </div>]]></cell>");
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
							+ documentoDTO.getNombreDocumento()
							+ " </div>]]></cell>");
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
							+ (documentoDTO.getActividadDTO() != null
									&& documentoDTO.getActividadDTO()
											.getNombre() != null ? documentoDTO
									.getActividadDTO().getNombre() : "-")
							+ " </div>]]></cell>");
					log.info("valor actividad"
							+ documentoDTO.getActividadDTO().getActividadId());
					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
							+ documentoDTO.getStrFechaCreacion()
							+ " </div>]]></cell>");
					// Area de negocio del dueno del documento
					if (documentoDTO.getActividadDTO() != null
							&& documentoDTO.getActividadDTO().getUsuario() != null
							&& documentoDTO.getActividadDTO().getUsuario()
									.getFuncionario() != null
							&& documentoDTO.getActividadDTO().getUsuario()
									.getFuncionario().getCatAreaNegocio() != null
							&& documentoDTO.getActividadDTO().getUsuario()
									.getFuncionario().getCatAreaNegocio()
									.getNombreCatAreaNegocio() != null) {

						writer.print("<cell><![CDATA[<div  style='background-color: #f2f2f2; color:#393939;'>"
								+ documentoDTO.getActividadDTO().getUsuario()
										.getFuncionario().getCatAreaNegocio()
										.getNombreCatAreaNegocio()
								+ " </div>]]></cell>");
					} else {
						writer.print("<cell><![CDATA[<div  style='background-color: #f2f2f2; color:#393939;'>"
								+ "-" + " </div>]]></cell>");
					}
					writer.print("<cell><![CDATA[<div class='celdaGrid'>"+documentoDTO.getStrEsGuardadoParcial()+" </div>]]></cell>");
					
					if(documentoDTO.getEsGuardadoParcial()!=null){
						writer.print("<cell><![CDATA[<div class='celdaGrid'>"+documentoDTO.getEsGuardadoParcial()+" </div>]]></cell>");
					}
					else if(documentoDTO.getArchivoDigital()!=null){
						writer.print("<cell><![CDATA[<div class='celdaGrid'>true</div>]]></cell>");
					}else{
						writer.print("<cell><![CDATA[<div class='celdaGrid'>--</div>]]></cell>");
					}
//					if (documentoDTO.getEsGuardadoParcial() == null
//							|| documentoDTO.getEsGuardadoParcial()
//									.equals(false)) {
//						writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
//								+ "no" + " </div>]]></cell>");
//					} else {
//						writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
//								+ "si" + " </div>]]></cell>");
//					}
					writer.print("</row>");
				}
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo para validar si re puede realizar la solicitud de defensor
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward validarSolicitudDeDefensor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		log.info("*********VALIDAR LA SOLICITUD DE DEFENSOR*************");
		log.info("idNumeroExpediente....."
				+ request.getParameter("idNumeroExpediente"));
		log.info("idExpediente..........."
				+ request.getParameter("idExpediente"));
		log.info("numeroExpediente......."
				+ request.getParameter("numeroExpediente"));

		try {
			
			String respuesta = "";

			Long idNumeroExpediente = NumberUtils.toLong(
					request.getParameter("idNumeroExpediente"), 0L);
			Long idExpediente = NumberUtils.toLong(
					request.getParameter("idExpediente"), 0L);
			String numeroExpediente = request.getParameter("numeroExpediente");

			ExpedienteDTO expedienteDTO = new ExpedienteDTO(); 

			expedienteDTO.setExpedienteId(idExpediente);
			expedienteDTO.setNumeroExpediente(numeroExpediente);
			expedienteDTO.setNumeroExpedienteId(idNumeroExpediente);
			
			respuesta = solicitudDelegate.validarSolicitudDeDefensor(expedienteDTO);

			// Enviamos respuesta a vista la respuesta
			escribirRespuesta(response, respuesta);

		} catch (NSJPNegocioException ex) {
			log.error("Ocurrio un error en enviarSolicitudAudiencia", ex);
			escribirError(response, ex);
		}

		return null;
	}
	
	
	/**
	 * M&eacute;todo para consultar los probables responsables para solicitud de
	 * defensor
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarProbablesResponsablesSolicitudDeDefensor(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		log.info("*********CONSULTAR PROBABLES RESPONSABLES PARA LA SOLICITUD DE DEFENSOR*************");
		log.info("VERIFICANDO PARAMETROS.............");

		log.info("idExpediente..........."
				+ request.getParameter("idExpediente"));

		try {

			Long idExpediente = NumberUtils.toLong(
					request.getParameter("idExpediente"), 0L);

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();

			expedienteDTO.setExpedienteId(idExpediente);

			List<InvolucradoDTO> listaProbablesResponsables = involucradoDelegate
					.consultarProbablesResponsablesParaSolucitudDeDefensor(expedienteDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if(listaProbablesResponsables != null && !listaProbablesResponsables.isEmpty()){
				for (InvolucradoDTO probableResp : listaProbablesResponsables) {
					writer.print("<row id='" + probableResp.getElementoId() + "'>");
					writer.print("<cell>" + probableResp.getNombreCompleto()
							+ "</cell>");
					writer.print("</row>");
				}				
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException ex) {
			log.error("Ocurrio un al consultar los probables responsables", ex);
		}

		return null;
	}
		
	/**
	 * Metodo utilizado para obtener el detalle de la solicitud.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 * 
	 */
	//MOD defensorATE, coordinadorDEF, defensor
	//TODO GBP Cambiar nombre a consultarDetalleSolicitudAsesoria
	public ActionForward consultarDetalleSolicitud(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			XStream converter=new XStream();
			log.info("ACTION PARA OBTENER EL DETALLE DE UNA SOLICITUD");

			Long idDocumento = NumberUtils.toLong(
					request.getParameter("idDocumento"), 0L);
			
			String xml = null;
			PrintWriter pw = null;
			
			SolicitudDefensorDTO solicitudDefensorDto = new SolicitudDefensorDTO();
			solicitudDefensorDto.setDocumentoId(idDocumento);

			solicitudDefensorDto = solicitudDelegate
					.obtenerSolicitudDefensor(solicitudDefensorDto);
					
			converter.alias("delito", DelitoDTO.class);
			converter.alias("solicitud", SolicitudDefensorDTO.class);
			converter.alias("avisoDesignacionDTO", AvisoDesignacionDTO.class);

			converter.alias("involucradoDTO",InvolucradoDTO.class);
			converter.alias("AsentamientoDTO",AsentamientoDTO.class);
			converter.alias("mediaFiliacionDTO",MediaFiliacionDTO.class);
			converter.alias("MunicipioDTO",MunicipioDTO.class);
			converter.alias("domicilioDTO",DomicilioDTO.class);
			converter.alias("EntidadFederativaDTO",EntidadFederativaDTO.class);
			converter.alias("aliasInvolucradoDTO",AliasInvolucradoDTO.class);
			converter.alias("DetencionDTO",DetencionDTO.class);
			//Agregados para la consulta de medios de contacto
			converter.alias("TelefonoDTO",TelefonoDTO.class);
			converter.alias("CorreoElectronicoDTO",CorreoElectronicoDTO.class);
			converter.alias("nombreDemograficoDTO",NombreDemograficoDTO.class);
			converter.alias("ValorDTO",ValorDTO.class);
			
//			if (solicitud.getExpedienteDTO() != null){
//				//solicitud.getExpedienteDTO().setDelitos(null);
//				super.setExpedienteTrabajo(request, solicitud.getExpedienteDTO());						
//			}
			
			xml = converter.toXML(solicitudDefensorDto);

			log.info("xml ::: "+xml);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * M&eacute;todo para consultar los involucrados de una solicitud de defensor
	 * para el detalle del involucrados
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public ActionForward consultarInvolucradosSolicitudDefensor(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("CONSULTAR INVOLUCRADOS SOLICITUD DE DEFENSOR");
			log.info("*************VERIFICANDO PARAMETROS**************");
			log.info("solicitudDefensorId................."
					+ request.getParameter("solicitudId"));
			
			Long solicitudDefensorId = NumberUtils.toLong(
					request.getParameter("solicitudId"), 0L);
			
			List<InvolucradoDTO> listaInvolucradosDto =  solicitudDelegate
					.consultarInvolucradosSolicitudDefensor(solicitudDefensorId);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaInvolucradosDto != null && !listaInvolucradosDto.isEmpty()) {
								
				for (InvolucradoDTO involucradoDTO : listaInvolucradosDto) {
					
					//id del elemento
					writer.print("<row id='" + involucradoDTO.getElementoId()
							+ "'>");
					
					//nombre completo
					writer.print("<cell>" + involucradoDTO.getNombreCompleto()
							+ "</cell>");
					
					//Calidad
					writer.print("<cell>"
							+ ((involucradoDTO.getCalidadDTO() != null
									&& involucradoDTO.getCalidadDTO()
											.getValorIdCalidad() != null && involucradoDTO
									.getCalidadDTO().getValorIdCalidad()
									.getValor() != null) ? involucradoDTO
									.getCalidadDTO().getValorIdCalidad()
									.getValor() : "") + "</cell>");
					
					//Es detenido
					if(involucradoDTO.getEsDetenido() != null && involucradoDTO.getEsDetenido().equals(true)){
						writer.print("<cell>"+"<![CDATA[<input type='checkbox' checked='checked'  disabled='disabled' />]]>"+"</cell>");
					}else{
						writer.print("<cell>"+"<![CDATA[<input type='checkbox' disabled='disabled' />]]>"+"</cell>");
					}
					
					//Fecha Detencion
					if (involucradoDTO.getDetenciones() != null
							&& !involucradoDTO.getDetenciones().isEmpty()
							&& involucradoDTO.getDetenciones().get(0)
									.getStrFechaInicioDetencion() != null
							&& !involucradoDTO.getDetenciones().get(0)
									.getStrFechaInicioDetencion().trim()
									.isEmpty()) {
						
						writer.print("<cell>"
								+ DateUtils.formatear(involucradoDTO.getDetenciones().get(0).getFechaInicioDetencion())
								+ "</cell>");
					}else{
						writer.print("<cell>"+" "+ "</cell>");
					}
					
					//Hora Detencion
					if (involucradoDTO.getDetenciones() != null
							&& !involucradoDTO.getDetenciones().isEmpty()
							&& involucradoDTO.getDetenciones().get(0)
									.getHoraInicioDetencion() != null
							&& !involucradoDTO.getDetenciones().get(0)
									.getHoraInicioDetencion().trim()
									.isEmpty()) {
						
						writer.print("<cell>"
								+ involucradoDTO.getDetenciones().get(0)
										.getHoraInicioDetencion()
								+ "</cell>");
					}else{
						writer.print("<cell>"+" "+ "</cell>");
					}
					writer.print("</row>");
				}
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * M&eacute;todo para realizar una solicitud de defensor desde
	 * defensor&iacute;a, espc&iacute;ficamente desde el usuario defensorAte, es
	 * decir de la instituci&oacute;n defensor&iacute;a a defensor&iacute;a.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward guardarSolicitanteSolDeDefensor(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO ACTION::::guardarSolicitanteSolDeDefensor");
			// Realizar el cast de solicitud de defensor Form para obtener los
			// parametros
			SolicitudDefensorDesdeDefensorAteForm solDefensorForm = (SolicitudDefensorDesdeDefensorAteForm) form;
			
			//RESPUESTA DEL ACTION
			SolicitudDefensorDesdeDefensorAteForm retorno = new SolicitudDefensorDesdeDefensorAteForm();

			/**
			 * DATOS DEL EXPEDIENTE O CASO
			 */
			log.info("Verificando el id del expediente["
					+ solDefensorForm.getExpedienteId() + "]");
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setExpedienteId(solDefensorForm.getExpedienteId());

			/**
			 * DATOS DEL SOLICITANTE
			 */
			InvolucradoDTO solicitante = new InvolucradoDTO();

			solicitante.setFechaCreacionElemento(new Date());
			validarDatosGeneralesSolicitudDefensorForm(solDefensorForm);
			validarDatosDomicilioSolicitudDefensorForm(solDefensorForm);
			
			solicitante.setExpedienteDTO(expedienteDTO);
			

			CalidadDTO calidadDTO = new CalidadDTO();
			calidadDTO.setCalidades(Calidades.DOMICILIO);

			// Encapsulamos la informacion del domicilio
			// PAIS MEXICO
			if (Long.parseLong(solDefensorForm.getPais()) == 10) {

				DomicilioDTO domicilioDTO = new DomicilioDTO();

				// if(idDomicilio > 0){
				// domicilioDTO.setElementoId(idDomicilio);
				// }
				domicilioDTO.setFechaCreacionElemento(new Date());

				domicilioDTO.setLatitud(solDefensorForm.getLatitud());
				domicilioDTO.setLongitud(solDefensorForm.getLongitud());
				domicilioDTO.setEdificio(solDefensorForm.getEdificio());
				domicilioDTO.setAlias(solDefensorForm.getAliasDomicilio());
				domicilioDTO.setEntreCalle2(solDefensorForm.getYcalle());
				domicilioDTO.setEntreCalle1(solDefensorForm.getEntreCalle());
				domicilioDTO.setReferencias(solDefensorForm.getReferencias());
				domicilioDTO
						.setNumeroInterior(solDefensorForm.getNumInterior());
				domicilioDTO
						.setNumeroExterior(solDefensorForm.getNumExterior());
				domicilioDTO.setCalle(solDefensorForm.getCalle());
				if (StringUtils.isNotBlank(solDefensorForm.getTipoCalle())
						&& !solDefensorForm.getTipoCalle().equals("-1")) {
					domicilioDTO.setValorCalleId(new ValorDTO(Long
							.parseLong(solDefensorForm.getTipoCalle())));
				}
				domicilioDTO.setCalidadDTO(calidadDTO);
				domicilioDTO.setExpedienteDTO(expedienteDTO);
				// delcaramos el nuevo asentamiento
				AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
				if (StringUtils.isNotBlank(solDefensorForm
						.getAsentamientoColonia())
						&& !solDefensorForm.getAsentamientoColonia().equals(
								"-1")) {
					asentamientoDTO
							.setAsentamientoId(Long.parseLong(solDefensorForm
									.getAsentamientoColonia()));
				}
				asentamientoDTO.setCodigoPostal(solDefensorForm
						.getCodigoPostal());

				// Declaramos el tipo de asentamiento
				if (StringUtils.isNotBlank(solDefensorForm
						.getTipoAsentamiento())
						&& !solDefensorForm.getTipoAsentamiento().equals("-1")) {
					TipoAsentamientoDTO tipoAsentamientoDTO = new TipoAsentamientoDTO(
							Long.parseLong(solDefensorForm
									.getTipoAsentamiento()), "");
					asentamientoDTO.setTipoAsentamientoDTO(tipoAsentamientoDTO);
				}
				// Declaramos el municipio
				MunicipioDTO municipioDTO = new MunicipioDTO();
				if (StringUtils.isNotBlank(solDefensorForm
						.getDelegacionMunicipio())
						&& !solDefensorForm.getDelegacionMunicipio().equals(
								"-1")) {
					municipioDTO.setMunicipioId(Long.parseLong(solDefensorForm
							.getDelegacionMunicipio()));
				}

				if (!(solDefensorForm.getLatitudN() == null)
						&& !solDefensorForm.getLatitudN().equals("")) {
					String lat = solDefensorForm.getLatitudN()
							+ solDefensorForm.getLatitudGrados() + "°"
							+ solDefensorForm.getLatitudMinutos() + "'"
							+ solDefensorForm.getLatitudSegundos() + "\"";
					domicilioDTO.setLatitud(lat);
				}
				if (!(solDefensorForm.getLongitudE() == null)
						&& !solDefensorForm.getLongitudE().equals("")) {
					String longitud = solDefensorForm.getLongitudE()
							+ solDefensorForm.getLongitudGrados() + "°"
							+ solDefensorForm.getLongitudMinutos() + "'"
							+ solDefensorForm.getLongitudSegundos() + "\"";
					domicilioDTO.setLongitud(longitud);
				}

				asentamientoDTO.setMunicipioDTO(municipioDTO);

				// declaramos la Ciudad
				CiudadDTO ciudadDTO = new CiudadDTO();
				if (StringUtils.isNotBlank(solDefensorForm.getCiudad())
						&& !solDefensorForm.getCiudad().equals("-1")) {
					ciudadDTO.setCiudadId(Long.parseLong(solDefensorForm
							.getCiudad()));
				}
				// declaramos la entidad federativa
				EntidadFederativaDTO entidadFederativaDTO = new EntidadFederativaDTO();
				if (StringUtils.isNotBlank(solDefensorForm
						.getEntidadFederativa())
						&& !solDefensorForm.getEntidadFederativa().equals("-1")) {
					entidadFederativaDTO.setEntidadFederativaId(Long
							.parseLong(solDefensorForm.getEntidadFederativa()));
				}
				if (StringUtils.isNotBlank(solDefensorForm.getPais())
						&& !solDefensorForm.getPais().equals("-1")) {
					entidadFederativaDTO.setValorIdPais(new ValorDTO(Long
							.parseLong(solDefensorForm.getPais())));
				}
				ciudadDTO.setEntidadFederativaDTO(entidadFederativaDTO);
				asentamientoDTO.setCiudadDTO(ciudadDTO);
				domicilioDTO.setCiudadDTO(ciudadDTO);
				domicilioDTO.setEntidadDTO(entidadFederativaDTO);
				domicilioDTO.setAsentamientoDTO(asentamientoDTO);
				domicilioDTO.setMunicipioDTO(municipioDTO);

				/**
				 * SETEAMOS EL DOMICILIO AL SOLICITANTE
				 */
				solicitante.setDomicilio(domicilioDTO);

				// REVISAMOS SI ES LA MISMA DIRECCION PARA EL DOMICILIO DE
				// NOTIFICACIONES
				// if(formaContOrg.getMismoDomicilioNotificaciones().equals("true"))
				// {
				// contactoOrgDTO.setDomicilioNotificacion(domicilioDTO);
				// }
			} else {// Otro pais

				DomicilioExtranjeroDTO domExtranjreoDTO = new DomicilioExtranjeroDTO();

				// if(idDomicilio > 0){
				// domExtranjreoDTO.setElementoId(idDomicilio);
				// }

				domExtranjreoDTO.setLatitud(solDefensorForm.getLatitud());
				domExtranjreoDTO.setLongitud(solDefensorForm.getLongitud());
				domExtranjreoDTO.setEdificio(solDefensorForm.getEdificio());
				domExtranjreoDTO.setAlias(solDefensorForm.getAliasDomicilio());
				domExtranjreoDTO.setEntreCalle2(solDefensorForm.getYcalle());
				domExtranjreoDTO
						.setEntreCalle1(solDefensorForm.getEntreCalle());
				domExtranjreoDTO.setReferencias(solDefensorForm
						.getReferencias());
				domExtranjreoDTO.setNumeroInterior(solDefensorForm
						.getNumInterior());
				domExtranjreoDTO.setNumeroExterior(solDefensorForm
						.getNumExterior());
				domExtranjreoDTO.setCalle(solDefensorForm.getCalle());
				domExtranjreoDTO.setPais(solDefensorForm.getPais());
				domExtranjreoDTO.setPaisValor(new ValorDTO(new Long(
						solDefensorForm.getPais())));
				domExtranjreoDTO.setCodigoPostal(solDefensorForm
						.getCodigoPostal());
				domExtranjreoDTO.setCiudad(solDefensorForm.getCiudad());
				domExtranjreoDTO.setMunicipio(solDefensorForm
						.getDelegacionMunicipio());
				domExtranjreoDTO.setAsentamientoExt(solDefensorForm
						.getAsentamientoColonia());
				domExtranjreoDTO.setEstado(solDefensorForm
						.getEntidadFederativa());
				domExtranjreoDTO.setCalidadDTO(calidadDTO);
				domExtranjreoDTO.setExpedienteDTO(expedienteDTO);
				domExtranjreoDTO.setFechaCreacionElemento(new Date());
				domExtranjreoDTO.setCalidadDTO(calidadDTO);

				/**
				 * SETEAMOS EL DOMICILIO EXTRANJERO EL SOLICITANTE
				 */
				solicitante.setDomicilio(domExtranjreoDTO);

				// REVISAMOS SI ES LA MISMA DIRECCION PARA EL DOMICILIO DE
				// NOTIFICACIONES
				// if(formaContOrg.getMismoDomicilioNotificaciones().equals("true")){
				// contactoOrgDTO.setDomicilioNotificacion(domExtranjreoDTO);
				// }
			}

			NombreDemograficoDTO nombreDemografico = new NombreDemograficoDTO();
			nombreDemografico.setNombre(solDefensorForm.getNombre());
			nombreDemografico.setApellidoPaterno(solDefensorForm
					.getApellidoPaterno());
			nombreDemografico.setApellidoMaterno(solDefensorForm
					.getApellidoMaterno());
			nombreDemografico.setRfc(solDefensorForm.getRfc());
			nombreDemografico.setCurp(solDefensorForm.getCurp());
			nombreDemografico.setSexo(solDefensorForm.getSexo());

			if (StringUtils.isNotBlank(solDefensorForm.getFechaNacimiento())) {
				nombreDemografico.setFechaNacimiento(DateUtils
						.obtener(solDefensorForm.getFechaNacimiento()));
			} else {
				nombreDemografico.setFechaNacimiento(null);
			}

			nombreDemografico.setEdadAproximada(solDefensorForm
					.getEdadAproximada());

			List<NombreDemograficoDTO> nombreL = new ArrayList<NombreDemograficoDTO>();
			nombreL.add(nombreDemografico);
			solicitante.setNombresDemograficoDTO(nombreL);
			solicitante.setFechaCreacionElemento(new Date());

			if (solDefensorForm.getPais() != null
					&& !solDefensorForm.getPais().isEmpty()
					&& !solDefensorForm.getPais().equals("-1")) {
				ValorDTO paisValorDTO = new ValorDTO(
						Long.parseLong(solDefensorForm.getPais()));
				nombreDemografico.setPaisValorDTO(paisValorDTO);
				EntidadFederativaDTO entidadFederativaDTO = new EntidadFederativaDTO();
				if (solDefensorForm.getEntFederativaNacimiento() != null
						&& !solDefensorForm.getEntFederativaNacimiento()
								.isEmpty()
						&& !solDefensorForm.getEntFederativaNacimiento()
								.equals("-1")) {
					entidadFederativaDTO.setEntidadFederativaId(Long
							.parseLong(solDefensorForm
									.getEntFederativaNacimiento()));
					nombreDemografico
							.setEntidadFederativaDTO(entidadFederativaDTO);
				} else {
					nombreDemografico.setEntidadFederativaDTO(null);
				}

				MunicipioDTO municipioDTO = new MunicipioDTO();
				if (solDefensorForm.getMunicipioNacimiento() != null
						&& !solDefensorForm.getMunicipioNacimiento().isEmpty()
						&& !solDefensorForm.getMunicipioNacimiento().equals(
								"-1")) {
					municipioDTO.setMunicipioId((Long.parseLong(solDefensorForm
							.getMunicipioNacimiento())));
					nombreDemografico.setMunicipioDTO(municipioDTO);
				} else {
					nombreDemografico.setMunicipioDTO(null);
				}

			} else {
				nombreDemografico.setPaisValorDTO(null);
			}

			// seteo la calidad
			CalidadDTO calidadInvDTO = new CalidadDTO();

			calidadInvDTO.setCalidades(Calidades.SOLICITANTE);
			solicitante.setCalidadDTO(calidadInvDTO);

			ValorDTO idiomaDTO = new ValorDTO(Long.parseLong(solDefensorForm
					.getIdioma()));

			solicitante.setValorIdIdioma(idiomaDTO);

			if (solDefensorForm.getEscolaridad() != null
					&& !solDefensorForm.getEscolaridad().isEmpty()
					&& !solDefensorForm.getEscolaridad().equals(
							"- Selecciona -")) {
				ValorDTO escolaridadDTO = new ValorDTO(
						Long.parseLong(solDefensorForm.getEscolaridad()));
				solicitante.setValorIdEscolaridad(escolaridadDTO);
			} else {
				solicitante.setValorIdEscolaridad(null);
			}

			if (solDefensorForm.getEstadoCivil() != null
					&& !solDefensorForm.getEstadoCivil().isEmpty()
					&& !solDefensorForm.getEstadoCivil().equals(
							"- Selecciona -")) {
				ValorDTO estadoCivilDTO = new ValorDTO(
						Long.parseLong(solDefensorForm.getEstadoCivil()));
				solicitante.setValorIdEstadoCivil(estadoCivilDTO);
			} else {
				solicitante.setValorIdEstadoCivil(null);
			}

			solicitante
					.setAliasInvolucradoDTO(obtenerAliasInvolucrado(solDefensorForm));

			// seteo las ocupaciones
			if (!solDefensorForm.getOcupacion().equalsIgnoreCase("")
					&& solDefensorForm.getOcupacion() != null
					&& !solDefensorForm.getOcupacion().equalsIgnoreCase(
							"undefined")) {
				// barremos las ocupaciones
				String[] ocupaciones = solDefensorForm.getOcupacion()
						.split(",");
				List<ValorDTO> ocupacionesL = new ArrayList<ValorDTO>();
				for (String ocupacion : ocupaciones) {
					ValorDTO ocupacionV = new ValorDTO(
							Long.parseLong(ocupacion));
					ocupacionesL.add(ocupacionV);
				}
				solicitante.setValorIdOcupacion(ocupacionesL);
			}
			// seteo las nacionalidades
			if (!solDefensorForm.getNacionalidad().equalsIgnoreCase("")) {
				// barremos las ocupaciones
				String[] nacionalidades = solDefensorForm.getNacionalidad()
						.split(",");
				List<ValorDTO> nacionalidadesL = new ArrayList<ValorDTO>();
				for (String nacionalidad : nacionalidades) {
					ValorDTO nacionalidadV = new ValorDTO(
							Long.parseLong(nacionalidad));
					nacionalidadesL.add(nacionalidadV);
				}
				solicitante.setValorIdNacionalidad(nacionalidadesL);
			}

			// seteamos los medio de contacto
			List<TelefonoDTO> lstTelefonos = new ArrayList<TelefonoDTO>();
			String strTelefonos = solDefensorForm.getMedioContactoTelefono();
			StringTokenizer lstStrTelefonos = new StringTokenizer(strTelefonos,
					"|");
			while (lstStrTelefonos.hasMoreElements()) {
				String strTelefono = (String) lstStrTelefonos.nextElement();
				String[] datosTelefono = strTelefono.split(",");

				TelefonoDTO telefono = new TelefonoDTO();

				ValorDTO valorTipoTelefono = new ValorDTO();
				if (datosTelefono.length != 0) {
					valorTipoTelefono.setIdCampo(new Long(datosTelefono[0]));
					log.info("&&&&Telefono:" + datosTelefono[0]);
					telefono.setValorTipoTelefono(valorTipoTelefono);
					telefono.setCodigoPais(datosTelefono[1]);
					log.info("&&&&Telefono:" + datosTelefono[1]);
					telefono.setCodigoArea(datosTelefono[2]);
					log.info("&&&&Telefono:" + datosTelefono[2]);
					telefono.setNumeroTelefonico(datosTelefono[3]);
					log.info("&&&&Telefono:" + datosTelefono[3]);
					lstTelefonos.add(telefono);
				}

			}
			solicitante.setTelefonosDTO(lstTelefonos);

			List<CorreoElectronicoDTO> lstCorreos = new ArrayList<CorreoElectronicoDTO>();
			
			if (!solDefensorForm.getMedioContactoCorreo().trim().isEmpty()) {
				String[] datosCorreo = solDefensorForm.getMedioContactoCorreo()
						.split(",");
				for (int i = 0; i < datosCorreo.length; i++) {
					CorreoElectronicoDTO correo = new CorreoElectronicoDTO();
					correo.setDireccionElectronica(datosCorreo[i]);
					lstCorreos.add(correo);
				}
			}
			solicitante.setCorreosDTO(lstCorreos);
			// fin seteo medios de contacto
			//Es vivo
			solicitante.setEsVivo(true);
			// Tipo de persona
			solicitante.setTipoPersona(TIPO_PERSONA_FISICA);
			solicitante.setCondicion(new Short("0"));
			
			Long involucradoId = involucradoDelegate.guardarInvolucrado(solicitante);
			
			XStream converter=new XStream();
			converter.alias("involucradoSolDefForm",SolicitudDefensorDesdeDefensorAteForm.class);
			retorno.setIdIndividuo(involucradoId);
			String xml = converter.toXML(retorno);
			escribirRespuesta(response,xml);
			log.info("Termina ejecucion Action");
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(), e);
			escribir(response, "", e);
		}
		return null;
	}

	/**
	 * M&eacute;todo para validar los datos generales de la forma
	 * 
	 * @param solDefensorForm
	 */
	private void validarDatosGeneralesSolicitudDefensorForm(
			SolicitudDefensorDesdeDefensorAteForm solDefensorForm) {

		// VERIFICAMOS QUE LOS DATOS GENERALES NO SEAN NULOS
		if (StringUtils.isBlank(solDefensorForm.getNombre())) {
			solDefensorForm.setNombre("");
		}
		if (StringUtils.isBlank(solDefensorForm.getApellidoPaterno())) {
			solDefensorForm.setApellidoPaterno("");
		}
		if (StringUtils.isBlank(solDefensorForm.getApellidoMaterno())) {
			solDefensorForm.setApellidoMaterno("");
		}
		if (StringUtils.isBlank(solDefensorForm.getCurp())) {
			solDefensorForm.setCurp("");
		}
		if (StringUtils.isBlank(solDefensorForm.getRfc())) {
			solDefensorForm.setRfc("");
		}
		if (StringUtils.isBlank(solDefensorForm.getFechaIngreso())) {
			solDefensorForm.setFechaIngreso("");
		}
		if (StringUtils.isBlank(solDefensorForm.getIdioma())) {
			solDefensorForm.setIdioma("");
		}
		if (StringUtils.isBlank(solDefensorForm.getEscolaridad())) {
			solDefensorForm.setEscolaridad("");
		}
		if (StringUtils.isBlank(solDefensorForm.getEstadoCivil())) {
			solDefensorForm.setEstadoCivil("");
		}
		if (StringUtils.isBlank(solDefensorForm.getSexo())) {
			solDefensorForm.setSexo("");
		}
		if (StringUtils.isBlank(solDefensorForm.getFechaNacimiento())) {
			solDefensorForm.setFechaNacimiento("");
		}
		if (solDefensorForm.getEdadAproximada() == null) {
			solDefensorForm.setEdadAproximada(Short.parseShort("0"));
		}
		if (StringUtils.isBlank(solDefensorForm.getAlias())) {
			solDefensorForm.setAlias("");
		}
		if (StringUtils.isBlank(solDefensorForm.getOcupacion())) {
			solDefensorForm.setOcupacion("");
		}
		if (StringUtils.isBlank(solDefensorForm.getNacionalidad())) {
			solDefensorForm.setNacionalidad("");
		}
		if (StringUtils.isBlank(solDefensorForm.getPaisNacimiento())) {
			solDefensorForm.setPaisNacimiento("");
		}
		if (StringUtils.isBlank(solDefensorForm.getEntFederativaNacimiento())) {
			solDefensorForm.setEntFederativaNacimiento("");
		}
		if (StringUtils.isBlank(solDefensorForm.getMunicipioNacimiento())) {
			solDefensorForm.setMunicipioNacimiento("");
		}
		if (solDefensorForm.getEsVivo() == null) {
			solDefensorForm.setEsVivo(true);
		}
	}

	/**
	 * M&eacute;todo para validar los datos del domicilio de la forma
	 * 
	 * @param solDefensorForm
	 */
	private void validarDatosDomicilioSolicitudDefensorForm(
			SolicitudDefensorDesdeDefensorAteForm solDefensorForm) {

		if (StringUtils.isBlank(solDefensorForm.getCodigoPostal())) {
			solDefensorForm.setCodigoPostal("");
		}

		if (StringUtils.isBlank(solDefensorForm.getCalle())) {
			solDefensorForm.setCalle("");
		}

		if (StringUtils.isBlank(solDefensorForm.getNumExterior())) {
			solDefensorForm.setNumExterior("");
		}

		if (StringUtils.isBlank(solDefensorForm.getNumInterior())) {
			solDefensorForm.setNumInterior("");
		}

		if (StringUtils.isBlank(solDefensorForm.getReferencias())) {
			solDefensorForm.setReferencias("");
		}

		if (StringUtils.isBlank(solDefensorForm.getEntreCalle())) {
			solDefensorForm.setEntreCalle("");
		}

		if (StringUtils.isBlank(solDefensorForm.getYcalle())) {
			solDefensorForm.setYcalle("");
		}

		if (StringUtils.isBlank(solDefensorForm.getAliasDomicilio())) {
			solDefensorForm.setAliasDomicilio("");
		}

		if (StringUtils.isBlank(solDefensorForm.getEdificio())) {
			solDefensorForm.setEdificio("");
		}

		if (StringUtils.isBlank(solDefensorForm.getLongitud())) {
			solDefensorForm.setLongitud("");
		}

		if (StringUtils.isBlank(solDefensorForm.getLatitud())) {
			solDefensorForm.setLatitud("");
		}
	}

	/**
	 * M&eacute;todo que recupera los alias asignados al involucrado en el JSP y
	 * los transforma a una lista de AliasInvolucradoDTO
	 * 
	 * @param forma
	 *            - Forma de struts de la cual se obtienen los alias asignados a
	 *            cada uno de los incolucrados.
	 * @return List<AliasInvolucradoDTO> - Lista con los DTOs que representan
	 *         los alias del involucrado.
	 */
	private List<AliasInvolucradoDTO> obtenerAliasInvolucrado(ActionForm forma) {
		List<AliasInvolucradoDTO> listaAliasDTO = new ArrayList<AliasInvolucradoDTO>();
		String aliases = null;
		if (forma instanceof IngresarIndividuoForm) {
			aliases = ((IngresarIndividuoForm) forma).getAlias();
		} else if (forma instanceof IngresarActaCircunstanciadaForm) {
			aliases = ((IngresarActaCircunstanciadaForm) forma).getAlias();
		} else if (forma instanceof SolicitudDefensorDesdeDefensorAteForm) {
			aliases = ((SolicitudDefensorDesdeDefensorAteForm) forma)
					.getAlias();
		}

		if (StringUtils.isNotBlank(aliases)) {
			// obtenemos los alias
			String[] arrAliasInvlocurado = aliases.split(",");
			log.info("alias_del_involucrado:: " + arrAliasInvlocurado);
			for (String alias : arrAliasInvlocurado) {
				AliasInvolucradoDTO aliasDTO = new AliasInvolucradoDTO();
				aliasDTO.setAlias(alias);
				listaAliasDTO.add(aliasDTO);
				aliasDTO = null;
			}
		}
		return listaAliasDTO;
	}
}

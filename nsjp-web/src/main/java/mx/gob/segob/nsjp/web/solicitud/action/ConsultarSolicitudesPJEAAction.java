/**
 * Nombre del Programa 	: SolicitudPJAction.java                                    
 * Autor               	: AlejandroGA                                              
 * Compania            	: Ultrasist                                                
 * Proyecto            	: NSJP              			Fecha:07/06/2011 
 * Marca de cambio     	: N/A                                                     
 * Descripcion General   : Clase para lo relacionado con las solicitudes
 * Programa Dependiente  : N/A                                                      
 * Programa Subsecuente 	: N/A                                                      
 * Cond. de ejecucion    : N/A                                                  
 * Dias de ejecucion     : N/A                             Horario: N/A
 *                               MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                 :N/A                                                           
 * Compania              :N/A                                                           
 * Proyecto              :N/A                                   Fecha: N/A       
 * Modificacion          :N/A                                                           
 *------------------------------------------------------------------------------      
 */
package mx.gob.segob.nsjp.web.solicitud.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.TipoAudiencia;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.comun.Meses;
import mx.gob.segob.nsjp.comun.enums.eventocita.EstatusEventoCita;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.enums.tarea.TipoEvento;
import mx.gob.segob.nsjp.comun.enums.tarea.TipoTarea;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.delegate.tarea.TareaDelegate;
import mx.gob.segob.nsjp.delegate.usuario.UsuarioDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.DiaDisponibilidadDTO;
import mx.gob.segob.nsjp.dto.audiencia.EspacioCalendarioDTO;
import mx.gob.segob.nsjp.dto.audiencia.MesDisponibilidadDTO;
import mx.gob.segob.nsjp.dto.audiencia.SalaAudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.SemanaDisponibilidadDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudAudienciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.tarea.EventoCitaDTO;
import mx.gob.segob.nsjp.dto.tarea.TareaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
//import org.apache.taglibs.standard.lang.jpath.adapter.Convert;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase que implementa las acciones para consultar solicitudes de audiencia o
 * de otro tipo.
 * 
 * @version 1.0
 * @author Alejandro Galaviz
 */
public class ConsultarSolicitudesPJEAAction extends GenericAction {

	/** Log de clase */
	private static final Logger log = Logger
			.getLogger(ConsultarSolicitudesPJEAAction.class);

	@Autowired
	public AudienciaDelegate audienciaDelegate;
	@Autowired
	public SolicitudDelegate solicitudDelegate;
	@Autowired
	public InvolucradoDelegate involucradoDelegate;
	@Autowired
	private TareaDelegate tareaDelegate;
	@Autowired
	private UsuarioDelegate usuarioDelegate;
	
	private final static String KEY_SESSION_AUDIENCIA = "KEY_SESSION_AUDIENCIA_DTO";
	
	private final static String TIPO_RESPUESTA_UNO = "1";
	private final static String TIPO_RESPUESTA_DOS = "2";
	
	private static final String PARAM_ID_TIPO_AUDIENCIA = "tipoAudienciaId";
	private static final String KEY_JSON_ID_CAMPO = "idCampo";
	private static final String KEY_JSON_VALOR = "valor";
	private static final String KEY_JSON_NOMBRE_CAMPO = "nombreCampo";
	private static final String KEY_JSON_CATALOGO_PADRE = "catalogoPadre";
	
	private static final String STR_CONVERTER_GUARDAR_AUDIENCIA = "fechaActual";

	/**
	 * Metodo utilizado para la consulta de solicitudes, de audiencia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarSolicitudesDeAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR SOLICITUDES DE AUDIENCIA");

			UsuarioDTO usuarioDTO = getUsuarioFirmado(request);
			List<SolicitudAudienciaDTO> listaDeAudiencias = audienciaDelegate
					.consultarSolicitudesAudiencia(usuarioDTO);

			log.info("audiencia" + listaDeAudiencias);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			for (SolicitudAudienciaDTO solicitudAudienciaDTO : listaDeAudiencias) {
				if (log.isDebugEnabled()) {
					log.debug("SOLICITUD AUDIENCIA" + solicitudAudienciaDTO);
				}
				/*
				 * Se modifica el id del grid de solicitud de audiencias debido
				 * a que se necesita, el id de la solicitud para verificar, la
				 * asociacion de la solicitud con el expediente
				 */
				writer.print("<row id='"
						+ solicitudAudienciaDTO.getAudiencia().getId() + ","
						+ solicitudAudienciaDTO.getDocumentoId() + "'>");
				// Caso
				writer.print("<cell>"
						+ (solicitudAudienciaDTO.getExpedienteDTO() != null
								&& solicitudAudienciaDTO.getExpedienteDTO()
										.getCasoDTO() != null ? solicitudAudienciaDTO
								.getExpedienteDTO().getCasoDTO()
								.getNumeroGeneralCaso()
								: "") + "</cell>");

				// Expediente
				writer.print("<cell>"
						+ (solicitudAudienciaDTO.getExpedienteDTO() != null ? solicitudAudienciaDTO
								.getExpedienteDTO().getNumeroExpediente() : "")
						+ "</cell>");

				// Caracter
				writer.print("<cell>"
						+ (solicitudAudienciaDTO.getAudiencia().getCaracter() != null ? solicitudAudienciaDTO
								.getAudiencia().getCaracter() : "---")
						+ "</cell>");

				// Tipo de audiencia
				writer.print("<cell>"
						+ solicitudAudienciaDTO.getAudiencia()
								.getTipoAudiencia().getValor() + "</cell>");

				// Fecha de solicitud
				if (solicitudAudienciaDTO.getFechaCreacion() != null) {

					String fechaSolicitud = DateUtils
							.formatear(solicitudAudienciaDTO.getFechaCreacion());

					String horaSolicitud = DateUtils
							.formatearHora(solicitudAudienciaDTO
									.getFechaCreacion());
					writer.print("<cell>" + fechaSolicitud + " "
							+ horaSolicitud + "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}

				// Institucion solicitante
				if (solicitudAudienciaDTO.getNombreInstitucionSolicitante() != null) {

					writer.print("<cell>"
							+ solicitudAudienciaDTO
									.getNombreInstitucionSolicitante()
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}
				// Solicitante
				if (solicitudAudienciaDTO.getNombreSolicitanteExternoInterno() != null) {
					writer.print("<cell>"
							+ solicitudAudienciaDTO
									.getNombreSolicitanteExternoInterno()
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}

				// Fecha hora limite
				if (solicitudAudienciaDTO.getFechaLimite() != null) {
					String fechaLimite = DateUtils
							.formatear(solicitudAudienciaDTO.getFechaLimite());
					String horaLimite = DateUtils
							.formatearHora(solicitudAudienciaDTO
									.getFechaLimite());
					writer.print("<cell>" + fechaLimite + " " + horaLimite
							+ "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");
				}
				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para la consulta de solicitudes, de audiencia.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarDetalleSolicitudAudiencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR DETALLE DE SOLICITUD DE AUDIENCIA");

			String tipoDeRespuesta = request.getParameter("tipoDeRespuesta");
			String idAudiencia = request.getParameter("idAudiencia");
			log.info("___________________________________________________________________________________________________");
			log.info("ID DE LA SOLICITUD DE LA AUDIENCIA******************************=="
					+ idAudiencia);
			log.info("TIPO DE RESPUESTA DE LA SOLICITUD DE LA AUDIENCIA******************************=="
					+ tipoDeRespuesta);
			log.info("___________________________________________________________________________________________________");

			if (tipoDeRespuesta != null && idAudiencia != null) {
				if (tipoDeRespuesta.equals(TIPO_RESPUESTA_UNO)) {

					log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA DETALLE DE AUDIENCIA:::::");
					/**
					 * Se desea enviar el objeto audiencia, y en esta parte de
					 * la respuesta solo llenar los campos que la TAB Detalle
					 * evento de la pantalla
					 * atencionSolicitudAudienciaNotificador.jsp
					 */
					AudienciaDTO detalleSolicitudAudienciaDTO = new AudienciaDTO();
					log.info("antes del delegate:::::");
					detalleSolicitudAudienciaDTO.setId(Long
							.parseLong(idAudiencia));
					detalleSolicitudAudienciaDTO = audienciaDelegate
							.obtenerAudiencia(detalleSolicitudAudienciaDTO);
					log.info("depues del delegate::: detalleSolicitudAudienciaDTO"
							+ detalleSolicitudAudienciaDTO);

					XStream converter=new XStream();
					converter.alias("audienciaDTO", AudienciaDTO.class);
					String xml = converter.toXML(detalleSolicitudAudienciaDTO);

					escribir(response, xml, null);
					// Se sube a sesion el objeto evento con todos sus atributos
					request.getSession().setAttribute(
							KEY_SESSION_AUDIENCIA + idAudiencia,
							detalleSolicitudAudienciaDTO);
				} else {
					if (tipoDeRespuesta.equals(TIPO_RESPUESTA_DOS)) {
						log.info("_________________________________________________________________________________");
						log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA DE TRASLADOS:::::");
						log.info("ID DE LA SOLICITUD DE LA AUDIENCIA=="
								+ idAudiencia);
						log.info("_________________________________________________________________________________");
						/**
						 * En esta parte de la respuesta se desea enviar la
						 * informacion del grid, correspondiente a la TAB de
						 * Traslados en el visorSolicitudAudiencia.jsp
						 */
						List<Long> calidades = new ArrayList<Long>();
						AudienciaDTO audienciaDto = new AudienciaDTO();

						Long audienciaId = NumberUtils.toLong(idAudiencia, 0L);
						audienciaDto.setId(audienciaId);

						calidades.add(Calidades.PROBABLE_RESPONSABLE_PERSONA
								.getValorId());
						calidades.add(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION
								.getValorId());

						List<InvolucradoDTO> listaInvolucrados = involucradoDelegate
								.obtenerInvolucradosDTOAudienciaPorCalidades(
										audienciaDto, calidades);
						escribeXmlInvolucradosAudiencia(listaInvolucrados,response);
						
					} else {
						log.info("********************************************************************************");
						log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA GRID:::::");
						log.info("ID DE LA SOLICITUD DE LA AUDIENCIA=="
								+ idAudiencia);
						log.info("********************************************************************************");

						/**
						 * En esta parte de la respuesta se desea enviar la
						 * informacion del grid, correspondiente a la TAB
						 * Detalle de solicitud de la pantalla
						 * visorSolicitudAudiencia.jsp
						 */
						List<Long> calidades = new ArrayList<Long>();
						AudienciaDTO audienciaDTO = new AudienciaDTO();

						Long audienciaId = NumberUtils.toLong(idAudiencia, 0L);
						audienciaDTO.setId(audienciaId);

						calidades.add(Calidades.VICTIMA_PERSONA.getValorId());
						calidades.add(Calidades.DENUNCIANTE.getValorId());
						calidades.add(Calidades.TESTIGO.getValorId());
						calidades.add(Calidades.PROBABLE_RESPONSABLE_PERSONA
								.getValorId());
						calidades
								.add(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION
										.getValorId());

						List<InvolucradoDTO> listaInvolucrados = involucradoDelegate
								.obtenerInvolucradosDTOAudienciaPorCalidades(
										audienciaDTO, calidades);

						escribeXmlInvolucradosAudiencia(listaInvolucrados,response);
					}
				}
			}
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}


	/**
	 * Metodo utilizado para la consulta de solicitudes, de otro tipo audio,
	 * video, recuso, etc.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarSolicitudesOtro(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR SOLICITUDES DE OTRO TIPO");

			Long[] idTS= 
			{TiposSolicitudes.RECURSO_APELACION.getValorId(), TiposSolicitudes.RECURSO_CASACION.getValorId(), //TiposSolicitudes.AUDIENCIA.getValorId(),
					TiposSolicitudes.AUDIO_VIDEO.getValorId(),TiposSolicitudes.TRANSCRIPCION_DE_AUDIENCIA.getValorId()};
			Long[] idES= {EstatusSolicitud.EN_PROCESO.getValorId(), EstatusSolicitud.ABIERTA.getValorId()};
			List<Long> idEstatusSolicitud= Arrays.asList(idES);
			List<Long> idTipoSolicitud = Arrays.asList(idTS);
			Long idAreaOrigen = null; 
			Long idFuncionarioSolicitante = null;
			// audienciaDelegate.consultarOtrasSolicitudes(usuarioDTO);
			List<SolicitudDTO> listaDeOtrasSolicitudes =solicitudDelegate.consultarSolicitudesGeneradas(idEstatusSolicitud, idTipoSolicitud, idAreaOrigen, idFuncionarioSolicitante);
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros()
						+ "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}
//			int lTotalRegistros = listaDeOtrasSolicitudes.size();
//			writer.print("<records>" + lTotalRegistros + "</records>");
			
			for (SolicitudDTO otraSolicitudDTO : listaDeOtrasSolicitudes) {

				log.info("OTRA SOLICITUD " + otraSolicitudDTO);

				writer.print("<row id='" + otraSolicitudDTO.getDocumentoId()
						+ "'>");
				if(otraSolicitudDTO.getExpedienteDTO()!= null &&  otraSolicitudDTO.getExpedienteDTO().getCasoDTO() != null ){
					writer.print("<cell>"
							+ otraSolicitudDTO.getExpedienteDTO().getCasoDTO()
									.getNumeroGeneralCaso() + "</cell>");
				}
				else{
					writer.print("<cell>"+ "---" + "</cell>");
				}
				
				if(otraSolicitudDTO.getExpedienteDTO() !=null && otraSolicitudDTO.getExpedienteDTO().getNumeroExpediente() != null ){
					writer.print("<cell>"
							+ otraSolicitudDTO.getExpedienteDTO()
									.getNumeroExpediente() + "</cell>");
				}
				else{
					writer.print("<cell>"+ "---" + "</cell>");
				}
				if(otraSolicitudDTO.getTipoSolicitudDTO() != null && otraSolicitudDTO.getTipoSolicitudDTO().getValor() != null){
					writer.print("<cell>"
							+ otraSolicitudDTO.getTipoSolicitudDTO().getValor()
							+ "</cell>");
				}
				else{
					writer.print("<cell>"+ "---" + "</cell>");
				}

				// fecha Hora de solicitud
				if(otraSolicitudDTO.getFechaCreacion() != null){
					String fechaCreacion = DateUtils.formatear(otraSolicitudDTO
							.getFechaCreacion());
					String horaCreacion = DateUtils.formatearHora(otraSolicitudDTO
							.getFechaCreacion());
					writer.print("<cell>" + fechaCreacion + " " + horaCreacion
							+ "</cell>");
				}
				else{
					writer.print("<cell>"+ "---" + "</cell>");
				}

				if(otraSolicitudDTO.getNombreInstitucionSolicitante() != null){
					writer.print("<cell>" + otraSolicitudDTO.getNombreInstitucionSolicitante()+ "</cell>");
				}
				else{
					writer.print("<cell>"+ "---" + "</cell>");
				}

				// Solicitante
				if(otraSolicitudDTO.getNombreSolicitanteExternoInterno() != null ){
					writer.print("<cell>"+ otraSolicitudDTO.getNombreSolicitanteExternoInterno() + "</cell>");
				}
				else{
					writer.print("<cell>"+ "---" + "</cell>");
				}
				// Estado
				if(otraSolicitudDTO.getEstatus() != null){
					writer.print("<cell>" + otraSolicitudDTO.getEstatus().getValor()+ "</cell>");
				}
				else{
					writer.print("<cell>"+ "---" + "</cell>");
				}

				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}

	/**
	 * Metodo utilizado para la consulta de solicitudes, de otro tipo audio,
	 * video, recuso, etc.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward calendarioActual(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO ACTION ---- CALENDARIO ACTUAL");

			MesDisponibilidadDTO mesTrabajo = (MesDisponibilidadDTO) request.getSession().getAttribute("mesDisponibilidadSesion");

			if (mesTrabajo == null) {
				mesTrabajo = new MesDisponibilidadDTO();
			} else {
				String movimiento = request.getParameter("mov");
				if (movimiento != null && !movimiento.equals("")) {
					if (movimiento.equals("atras")) {
						mesTrabajo.retrocederMes();
					} else {
						mesTrabajo.avanzarMes();
					}
				}
			}
			
			// Subimos el objeto Mes a sesion
			request.getSession().setAttribute("mesDisponibilidadSesion",mesTrabajo);

			final MesDisponibilidadDTO mesDisponibilidadDTO = audienciaDelegate.consultarDisponibilidadPorMes(mesTrabajo);

			request.setAttribute("actualDate", mesDisponibilidadDTO.getMes()
					.getNombre());
			log.info("MES ACTUAL=========================="
					+ mesDisponibilidadDTO.getMes().getNombre());

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			writer.print("<records>" + mesDisponibilidadDTO.getSemanas().size()
					+ "</records>");

			for (SemanaDisponibilidadDTO semana : mesDisponibilidadDTO
					.getSemanas()) {
				writer.print("<row id='" + semana.getNoSemana() + "'>");

				for (DiaDisponibilidadDTO diaDTO : semana.getDias()) {

					if (diaDTO.isHabil() == false)
						writer.print("<cell><![CDATA[<div id='inhabil' style='background-color: #CCCCCC; color:white;'>"
								+ diaDTO.getDia() + "</div>]]></cell>");
					else {
						if (diaDTO.isDisponible() == true)
							// verde
							writer.print("<cell><![CDATA[<div style='background-color: #669933; color:white;'>"
									+ diaDTO.getDia() + "</div]]></cell>");
						else
							writer.print("<cell><![CDATA[<div style='background-color: red; color:white;'>"
									+ diaDTO.getDia() + "</div]]></cell>");
					}

				}
				writer.print("</row>");

			}

			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para consultar el numero de salas
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarNumeroDeSalas(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO ACTION ---- CONSULTAR NUMERO DE SALAS");
			
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			
			List<SalaAudienciaDTO> nombreSalas = audienciaDelegate.obtenerNombresSalas(usuarioDTO);
			
			 log.info("NUMERO DE SALAS"+nombreSalas);
			 XStream converter=new XStream();
			 converter.alias("ListaSalas", List.class);
			 converter.alias("Sala", SalaAudienciaDTO.class);
			 String xml = converter.toXML(nombreSalas);
			 escribir(response, xml,null);

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}
	
	/**
	 * Metodo utilizado para la consulta la disponibilidad de salas.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarDisponibilidadDeSalasPorDia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION ---- CONSULTAR DISPONIBILIDAD DE SALAS POR DIA");
			String dia = request.getParameter("diaDisp");
			String mes = request.getParameter("mesDisp");
			String anio = request.getParameter("anioDisp");
			
			log.info("DIA="+dia);
			log.info("MES="+mes);
			log.info("ANIO="+anio);
			

			DiaDisponibilidadDTO diaDisponibilidadDTO= new DiaDisponibilidadDTO();
			diaDisponibilidadDTO.setDia(Integer.parseInt(dia));
			MesDisponibilidadDTO mesDisponibilidadDTO = new MesDisponibilidadDTO();
			
			if(mes.equalsIgnoreCase("enero"))
				mesDisponibilidadDTO.setMes(Meses.ENERO);
			if(mes.equalsIgnoreCase("febrero"))
				mesDisponibilidadDTO.setMes(Meses.FEBRERO);
			if(mes.equalsIgnoreCase("marzo"))
				mesDisponibilidadDTO.setMes(Meses.MARZO);
			if(mes.equalsIgnoreCase("abril"))
				mesDisponibilidadDTO.setMes(Meses.ABRIL);
			if(mes.equalsIgnoreCase("mayo"))
				mesDisponibilidadDTO.setMes(Meses.MAYO);
			if(mes.equalsIgnoreCase("junio"))
				mesDisponibilidadDTO.setMes(Meses.JUNIO);
			if(mes.equalsIgnoreCase("julio"))
				mesDisponibilidadDTO.setMes(Meses.JULIO);
			if(mes.equalsIgnoreCase("agosto"))
				mesDisponibilidadDTO.setMes(Meses.AGOSTO);
			if(mes.equalsIgnoreCase("septiembre"))
				mesDisponibilidadDTO.setMes(Meses.SEPTIEMBRE);
			if(mes.equalsIgnoreCase("octubre"))
				mesDisponibilidadDTO.setMes(Meses.OCTUBRE);
			if(mes.equalsIgnoreCase("noviembre"))
				mesDisponibilidadDTO.setMes(Meses.NOVIEMBRE);
			if(mes.equalsIgnoreCase("diciembre"))
				mesDisponibilidadDTO.setMes(Meses.DICIEMBRE);
			
			mesDisponibilidadDTO.setAnio(Integer.parseInt(anio));
			diaDisponibilidadDTO.setMes(mesDisponibilidadDTO);

			diaDisponibilidadDTO = audienciaDelegate.consultarDisponibilidadPorDia(diaDisponibilidadDTO);
			
			Map<Long, SalaAudienciaDTO> mapSalas = new HashMap<Long, SalaAudienciaDTO>();

			
			for (SalaAudienciaDTO salita : diaDisponibilidadDTO.getSalas()){
			    mapSalas.put(new Long(salita.getSalaAudienciaId()), salita);

			    
			}
			request.getSession().setAttribute("mapSalas", mapSalas);
			log.info(diaDisponibilidadDTO);
					
//			 USADO PARA VER EL XML QUE REGRESA EL DELEGATE
			 XStream converter=new XStream();
			 converter.alias("DiaDisponibilidadDTO", DiaDisponibilidadDTO .class);
			 converter.alias("SalaAudienciaDTO", SalaAudienciaDTO.class);
			 converter.alias("EspacioCalendarioDTO", EspacioCalendarioDTO.class);
			 String xml = converter.toXML(diaDisponibilidadDTO);
			 response.setContentType("text/xml");
			 PrintWriter pw = response.getWriter();
			 pw.print(xml);
			 pw.flush();
			 pw.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}


    /**
     * Metodo utilizado para la consulta la disponibilidad de salas.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return null
     * @throws IOException
     *             En caso de obtener una exception
     */
    @SuppressWarnings("unchecked")
	public ActionForward consultarDisponibilidadDeUnaSalaPorDia(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {

        	log.info("EJECUTANDO ACTION ---- CONSULTAR DISPONIBILIDAD DE UNA SALA POR DIA *****************");
            String posSala = request.getParameter("posSala");
            log.info("POS SALA="+posSala);
            
            Map<Long, SalaAudienciaDTO> mapSalas =  (Map<Long, SalaAudienciaDTO>) request.getSession().getAttribute("mapSalas");
            
            SalaAudienciaDTO sala = mapSalas.get(new Long(posSala));
           
            
            response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			int lTotalRegistros = sala.getTotalEspacios();

			writer.print("<records>" + lTotalRegistros + "</records>");
			String nombreEstilo = null;
			int indiceEspacio = 0;
			
			for (EspacioCalendarioDTO espacioDTO : sala.getEventos()) {
				
				log.info("ESPACIO DTO"+espacioDTO);
				
				for(int i=1;i<=espacioDTO.getTamanio();i++){
					log.info("ESPACIO DTO TAMANIO"+espacioDTO.getTamanio());
					if(espacioDTO.getId()==null){
						log.info("INDICE ESPACIO DTO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+indiceEspacio);
						//id de la sala + la hora inicial + tamanio "disponibilidad = numero de medias hrs."
						writer.print("<row id='"+sala.getSalaAudienciaId()+ "+" +espacioDTO.getHoraInicio()+"/"+(espacioDTO.getTamanio()-i+1)+"*"+
						
						(indiceEspacio<ConstantesGenerales.HORAS_AUDIENCIA.length?ConstantesGenerales.HORAS_AUDIENCIA[indiceEspacio]:"00:00")+"#"+"'>");
						writer.print("<cell></cell>");
						writer.print("</row>");
					}
					else{
						
						if(i==1 && espacioDTO.getTamanio() == 1){
							//un solo espacio de tiempo
							nombreEstilo = "ocupado1";
						}else if(i==1 && espacioDTO.getTamanio()>1){
							//inicia espacio mayor que 1
							nombreEstilo = "ocupadoInicio";
						}else if(i>1 && i<espacioDTO.getTamanio()){
							//espacio intermedio
							nombreEstilo = "ocupadoIntermedio";
						}else if(i == espacioDTO.getTamanio()){
							//ultimo espacio
							nombreEstilo = "ocupadoUltimo";
						}
					
						writer.print("<row id='"+nombreEstilo+"'>");
						writer.print("<cell><![CDATA[<div style='background-color:#09F;'>"+"Audiencia"+espacioDTO.getId()+ "</div>]]></cell>");

						writer.print("</row>");
					}
					indiceEspacio++;
				}			
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
			// USADO PARA VER EL XML QUE REGRESA EL DELEGATE
//			 converter.alias("Salas", SalaAudienciaDTO.class);
//			 String xml = converter.toXML(sala);
//			 response.setContentType("text/xml");
//			 PrintWriter pw = response.getWriter();
//			 pw.print(xml);
//			 pw.flush();
//			 pw.close();

        } catch (Exception e) {
            log.info(e.getCause(), e);

        }
        return null;
    }
    
    
    /**
	 * Metodo utilizado para consultar testigos para audiencia, toma el objeto
	 * audiencia que se encuentra en sesion y obtiene la lista de testigos
	 * y la respuesta se pone en formato para grid
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarTestigosParaAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			log.info("EJECUTANDO ACTION ---- CONSULTAR TESTIGOS PARA AUDIENCIA");
			
			String idAudiencia = request.getParameter("idAudiencia");
			
			if(idAudiencia != null){
				
				AudienciaDTO audienciaDTO = new AudienciaDTO();
				
				audienciaDTO = (AudienciaDTO) request.getSession().getAttribute(KEY_SESSION_AUDIENCIA+idAudiencia);
				log.info("OBTENIENDO EL OBJETO AUDIENCIA DE SESION:::::"+audienciaDTO);
				
				List<InvolucradoViewDTO> listaInvolucrados =involucradoDelegate.obtenerInvolucradosAudiencia(audienciaDTO);
				 //audienciaDTO.getInvolucradoByCalidad(Calidades.TESTIGO);
				log.info("LISTA DE Intervinientes"+listaInvolucrados);
				int lTotalRegistros = listaInvolucrados.size();
				
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");
					writer.print("<records>" + lTotalRegistros + "</records>");
					
					for (InvolucradoViewDTO involucrado : listaInvolucrados) {
						writer.print("<row id='" + involucrado.getInvolucradoId()+"'>");
							writer.print("<cell>"+ involucrado.getNombreCompleto() + "</cell>");
						writer.print("</row>");
					}
				writer.print("</rows>");
				writer.flush();
				writer.close();
				
			}
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}
	
	/**
	 * Metodo utilizado para consultar la lista de jueces, su carga de trabajo
	 * y el valor de la paridad, por juez
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarDisponibilidadJueces(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			log.info("EJECUTANDO ACTION ---- CONSULTAR DISPONIBILIDAD DE JUECES");
			
			log.info("VERIFICANDO PARAMETROS:::::::::::::::::::::::::::::::::::");
			log.info("diaDisp"+request.getParameter("diaDisp"));
			log.info("mesDisp"+request.getParameter("mesDisp"));
			log.info("anioDisp"+request.getParameter("anioDisp"));
			log.info("horaSeleccionada"+request.getParameter("horaSeleccionada"));
			log.info("minutoSeleccionado"+request.getParameter("minutoSeleccionado"));
			log.info("duracionEstimada"+request.getParameter("duracionEstimada"));
			log.info("tipoAudiencia"+request.getParameter("tipoAudiencia"));
			log.info("audienciaId"+request.getParameter("audienciaId"));
			log.info("automatico"+request.getParameter("automatico"));
			log.info("juezSustituto"+request.getParameter("juezSustituto"));
			
			Integer mesDisponibilidad = 0;
			String diaDisp = request.getParameter("diaDisp");
			String mes = request.getParameter("mesDisp");
			String anioDisp = request.getParameter("anioDisp");
			String horaSeleccionada = request.getParameter("horaSeleccionada");
			String minutoSeleccionado = request.getParameter("minutoSeleccionado");
			String duracionEstimada = request.getParameter("duracionEstimada");
			String tipoAudiencia = request.getParameter("tipoAudiencia");
			String audienciaId = request.getParameter("audienciaId");
			String tipoDeAudienciaSolicitada = request.getParameter("tipoDeAudienciaSolicitada");
			
			Long tipo = NumberUtils.toLong(tipoDeAudienciaSolicitada);
			
			if(tipo == 0L && tipoAudiencia != null){
				tipo = NumberUtils.toLong(tipoAudiencia);
			}
			
//			TipoAudiencia tipoAudienciaSolicitada = TipoAudiencia.getByValor(tipo);
			AudienciaDTO audiencia = new AudienciaDTO();
			audiencia.setId(NumberUtils.toLong(audienciaId));
			audiencia.setTipoAudiencia(new ValorDTO(tipo));
			boolean automatico = false;
			boolean juezSustituto = false;
			if(request.getParameter("automatico") != null){
				automatico = Boolean.parseBoolean(request.getParameter("automatico"));
			}
			if(request.getParameter("juezSustituto") != null){
				juezSustituto = Boolean.parseBoolean(request.getParameter("juezSustituto"));
			}
			UsuarioDTO usuario = super.getUsuarioFirmado(request);
			
			if(mes.equalsIgnoreCase("enero"))
				mesDisponibilidad= 1;
			if(mes.equalsIgnoreCase("febrero"))
				mesDisponibilidad= 2;
			if(mes.equalsIgnoreCase("marzo"))
				mesDisponibilidad= 3;
			if(mes.equalsIgnoreCase("abril"))
				mesDisponibilidad= 4;
			if(mes.equalsIgnoreCase("mayo"))
				mesDisponibilidad= 5;
			if(mes.equalsIgnoreCase("junio"))
				mesDisponibilidad= 6;
			if(mes.equalsIgnoreCase("julio"))
				mesDisponibilidad= 7;
			if(mes.equalsIgnoreCase("agosto"))
				mesDisponibilidad= 8;
			if(mes.equalsIgnoreCase("septiembre"))
				mesDisponibilidad= 9;
			if(mes.equalsIgnoreCase("octubre"))
				mesDisponibilidad= 10;
			if(mes.equalsIgnoreCase("noviembre"))
				mesDisponibilidad= 11;
			if(mes.equalsIgnoreCase("diciembre"))
				mesDisponibilidad= 12;
			//String idAudiencia = request.getParameter("idAudiencia");
			
			if(diaDisp != null && anioDisp != null){
				
				log.info("FECHA PARA BUSCAR UN JUEZ="+diaDisp+mes+anioDisp+"HORA="+horaSeleccionada+"MINUTO SELECCIONADO="+minutoSeleccionado);
				Calendar calendario = Calendar.getInstance();
				calendario.set(Integer.parseInt(anioDisp),mesDisponibilidad-1,Integer.parseInt(diaDisp) ,Integer.parseInt(horaSeleccionada) ,Integer.parseInt(minutoSeleccionado));
			
				log.info("antes del delegate");
				List<FuncionarioDTO> listaJueces = new ArrayList<FuncionarioDTO>();
				
				// filtro por discriminante para jueces
				if(usuario != null && usuario.getFuncionario()!=null){
					
					if(automatico) { 
						listaJueces = audienciaDelegate.consultarJuezAutomaticoADesignar(
								calendario.getTime(),
								Integer.parseInt(duracionEstimada),
								audiencia,
								juezSustituto,
								usuario.getFuncionario());
					} else {
						listaJueces = audienciaDelegate.consultarJuecesDisponiblesParaFechaYHoraAudiencia(
								calendario.getTime(),
								Integer.parseInt(duracionEstimada),
								usuario);
					}
				}
				
				log.debug("TAMANIO DE LISTA DE JUECES=" + listaJueces.size());

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				writer.print("<rows>");

				final PaginacionDTO pag = PaginacionThreadHolder.get();
				
				log.debug("pag :: " + pag);
				if (pag != null && pag.getTotalPaginas() != null
						&& pag.getTotalRegistros() != null) {
					writer.print("<total>" + pag.getTotalPaginas() + "</total>");
					writer.print("<records>" + pag.getTotalRegistros()
							+ "</records>");
				} else {
					writer.print("<total>0</total>");
					writer.print("<records>0</records>");
				}

				for (FuncionarioDTO juezDTO : listaJueces) {

					writer.print("<row id='" + juezDTO.getClaveFuncionario() + "'>");
					writer.print("<cell>" + juezDTO.getNombreCompleto() + "</cell>");
					writer.print("<cell>" + juezDTO.getCargaTrabajo() + "</cell>");
					writer.print("<cell>"
							+ (juezDTO.getEsPar() != null
									&& juezDTO.getEsPar().booleanValue() ? "Par"
									: "Impar") + "</cell>");
					writer.print("</row>");
				}
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}
	
	/**
	 * Metodo utilizado para convertir las cadenas anio,mes,dia,hora y minuto a formato date
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	private Date parseaCadenasFechaHora(String dia,String mes, String anio,String hora,String minuto)throws IOException {
		
//		Date fechaHora = new Date();
		Date fechaHora = null;
		try{	
			Integer mesDisponibilidad = 0;
			
			
			if(mes.equalsIgnoreCase("enero"))
				mesDisponibilidad= 1;
			if(mes.equalsIgnoreCase("febrero"))
				mesDisponibilidad= 2;
			if(mes.equalsIgnoreCase("marzo"))
				mesDisponibilidad= 3;
			if(mes.equalsIgnoreCase("abril"))
				mesDisponibilidad= 4;
			if(mes.equalsIgnoreCase("mayo"))
				mesDisponibilidad= 5;
			if(mes.equalsIgnoreCase("junio"))
				mesDisponibilidad= 6;
			if(mes.equalsIgnoreCase("julio"))
				mesDisponibilidad= 7;
			if(mes.equalsIgnoreCase("agosto"))
				mesDisponibilidad= 8;
			if(mes.equalsIgnoreCase("septiembre"))
				mesDisponibilidad= 9;
			if(mes.equalsIgnoreCase("octubre"))
				mesDisponibilidad= 10;
			if(mes.equalsIgnoreCase("noviembre"))
				mesDisponibilidad= 11;
			if(mes.equalsIgnoreCase("diciembre"))
				mesDisponibilidad= 12;
				
			Calendar calendario = Calendar.getInstance();
			
			calendario.set(Integer.parseInt(anio),mesDisponibilidad-1,Integer.parseInt(dia) ,Integer.parseInt(hora) ,Integer.parseInt(minuto),0);
			fechaHora=calendario.getTime();
			
		}catch (Exception e) {
			log.info(e.getCause(), e);

		}	
		return fechaHora;
	}
	
	/**
	 * Metodo utilizado para obtener una lista de jueces apartir de su id
	 * localizado en una cadena
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	private List<FuncionarioDTO> obtenerIdsFuncionario(String idJueces)throws IOException{
		
		List<FuncionarioDTO> listaJueces = new ArrayList<FuncionarioDTO>();
		
		try {
			if(idJueces != null){
				String listaIds[] = idJueces.split(",");
				for(String id : listaIds){
					FuncionarioDTO funcionario = new FuncionarioDTO();
					funcionario.setClaveFuncionario(Long.parseLong(id));
					listaJueces.add(funcionario);
				}
			}
			
			
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return listaJueces;
	}
	
	
	private List<InvolucradoDTO> obtenerIdsInvolucrados(String idInvolucradosPA)throws IOException{
		
		List<InvolucradoDTO> listaInvolucrados = new ArrayList<InvolucradoDTO>();
		
		try {
			if(idInvolucradosPA != null){
				String listaIds[] = idInvolucradosPA.split(",");
				for(String id : listaIds){
					InvolucradoDTO involucrado = new InvolucradoDTO();
					involucrado.setElementoId(Long.parseLong(id));
					listaInvolucrados.add(involucrado);
				}
			}
			
			
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return listaInvolucrados;
	}

	/**
	 * Metodo utilizado el guardado de los datos programando la audiencia
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 * @throws NSJPNegocioException 
	 */
	public ActionForward guardarAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, NSJPNegocioException {
		String idAudiencia = request.getParameter("idAudiencia");
		Long id = NumberUtils.toLong(idAudiencia, 0L);
		try {
			
			log.info("EJECUTANDO ACTION ---- GUARDAR AUDIENCIA");
			log.info("llega id audiencia"+ idAudiencia);
			AudienciaDTO audienciaDTO;
			Long idEvento = (long) ConstantesGenerales.NO_ES_JAVS;
			String Evento="";			
			Boolean esNuevo=true;
			Long estadoJAVS;
			
			audienciaDTO = (AudienciaDTO) request.getSession().getAttribute(KEY_SESSION_AUDIENCIA+idAudiencia);
			
			if(audienciaDTO == null){
				audienciaDTO = new AudienciaDTO();
				audienciaDTO.setId(Long.parseLong(idAudiencia));				
			}
			
			
			String anioEvento = request.getParameter("anioEvento");
			String mesEvento = request.getParameter("mesEvento");
			String diaEvento = request.getParameter("diaEvento");
			String duracionEstimada = request.getParameter("duracionEstimada");
			String idJueces = request.getParameter("idJueces");
			String salaTemporal = request.getParameter("salaTemporal");
			String tipoDeAudienciaSeleccionada = request.getParameter("tipoDeAudienciaSeleccionada");
			String esReprogramacionDeAudiencia = request.getParameter("esReprogramacionDeAudiencia");
			
			if(esReprogramacionDeAudiencia != null && esReprogramacionDeAudiencia.equals("true")){
				audienciaDTO.setEsReprogramacionDeAudiencia(true);
				esNuevo=false;
			}
			
			List<FuncionarioDTO> listaJueces = obtenerIdsFuncionario(idJueces);
		
			if(salaTemporal.equalsIgnoreCase("false")){

				String horaEvento = request.getParameter("horaEvento");
				String minutoEvento = request.getParameter("minutoEvento");
				String idSala = request.getParameter("idSala");
				
				Date fechaHoraAudiencia = parseaCadenasFechaHora(diaEvento, mesEvento, anioEvento, horaEvento, minutoEvento);
				
				audienciaDTO.setFechaEvento(fechaHoraAudiencia);				
				audienciaDTO.setSala(new SalaAudienciaDTO(Long.parseLong(idSala), "Sala"));
			}
			else{
				
				//Para sala temporal
				SalaAudienciaDTO salaAudiencia = new SalaAudienciaDTO();
				
			   String direccionSalaTemp = request.getParameter("direccion");
			   String ubicacionSalaTemp = request.getParameter("ubicacion");
			   String motivosAsignacionTemp = request.getParameter("motivoAsignacion");
			   String horaProgramada = request.getParameter("horaTemp");
			   String minutoProgramado = request.getParameter("minutoTemp");
			   
			   salaAudiencia.setDomicilioSala(direccionSalaTemp);
			   salaAudiencia.setUbicacionSala(ubicacionSalaTemp);
			   salaAudiencia.setMotivo(motivosAsignacionTemp);
			   
			   Date fechaHoraAudiencia = parseaCadenasFechaHora(diaEvento, mesEvento, anioEvento, horaProgramada,minutoProgramado);
			   audienciaDTO.setFechaEvento(fechaHoraAudiencia);
			   audienciaDTO.setSala(salaAudiencia);
			}
			
			audienciaDTO.setTipoAudiencia(new ValorDTO(NumberUtils.toLong(tipoDeAudienciaSeleccionada)));
			audienciaDTO.setFuncionarios(listaJueces);
			audienciaDTO.getSala().setTemporal(Boolean.parseBoolean(salaTemporal));
			audienciaDTO.setFechaAsignacionSala(new Date());
			audienciaDTO.setDuracionEstimada(Integer.parseInt(duracionEstimada));
			audienciaDTO.setEstatusAudiencia(new ValorDTO(EstatusAudiencia.PROGRAMADA.getValorId()));
							
			log.info("antes del delegate::::guardarProgramacionAudiencia");
			try{				

				if(esNuevo==true){
					
					audienciaDelegate.guardarProgramacionAudiencia(audienciaDTO);
					
					if(audienciaDTO.getId()!= null){
						log.info("Utilizando JAVSCliente para AGENDAR AUDIENCIA"+ audienciaDTO.getId());
						idEvento = audienciaDelegate.agendarAudiencia(audienciaDTO.getId());	
						log.info("Despues de AGENDAR AUDIENCIA"+ idEvento);
						
						if(!(idEvento==ConstantesGenerales.EXITO_AGENDAR_JAVS || idEvento==ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_IN 
								   || idEvento==ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_INOUT || idEvento==ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_OUT
								   || idEvento==ConstantesGenerales.NO_ES_JAVS)){
							
							//TODO FLUJO JAVS
									//*audienciaDelegate.cancelarAudienciaSistema(audienciaDTO.getId());
						}else{
							//Todo salio bien
							//Permite generar una nueva solicitud para una audiencia de tipo Juicio Oral
							if(TipoAudiencia.INTERMEDIA.getValorId().equals(tipoDeAudienciaSeleccionada)){
								//GAMA
								
							}
						}

					}																				
				}
				else{
					
					audienciaDelegate.cancelarAudienciaJAVS(id);										
					
					estadoJAVS = audienciaDelegate.consultarEstadoAudiencia(id);
					if(estadoJAVS!=ConstantesGenerales.AUDIENCIA_PROCESO && estadoJAVS!=ConstantesGenerales.AUDIENCIA_TERMINO){
						audienciaDelegate.guardarProgramacionAudiencia(audienciaDTO);
						
						if(audienciaDTO.getId()!= null){
							log.info("Utilizando JAVSCliente para REAGENDAR AUDIENCIA"+ audienciaDTO.getId());
							idEvento = audienciaDelegate.reagendarAudiencia(audienciaDTO.getId());	
							log.info("Despues de REAGENDAR AUDIENCIA"+ idEvento);
						
							if(!(idEvento==ConstantesGenerales.EXITO_AGENDAR_JAVS || idEvento==ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_IN 
									|| idEvento==ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_INOUT || idEvento==ConstantesGenerales.EXITO_AGENDAR_JAVS_SIN_ARCH_OUT
									|| idEvento==ConstantesGenerales.NO_ES_JAVS)){
								audienciaDelegate.cancelarAudienciaSistema(audienciaDTO.getId());
							}
						}
					}
				}
				
				/*
				 * Consulta si existen otras solicitudes de audiencia del mismo caso y del mismo tipo 
				 */
				XStream converter=new XStream();
				String numCasoSolicitudAudienciaDetalle = request.getParameter("numCasoSolicitudAudienciaDetalle");
				
				
				if(numCasoSolicitudAudienciaDetalle != null && !numCasoSolicitudAudienciaDetalle.isEmpty()){
					
					SolicitudAudienciaDTO solicitudAudienciaDTO = new SolicitudAudienciaDTO();
					ExpedienteDTO expedienteDTO = new ExpedienteDTO();
					CasoDTO casoDTO = new CasoDTO();
					casoDTO.setNumeroGeneralCaso(numCasoSolicitudAudienciaDetalle);
					expedienteDTO.setCasoDTO(casoDTO);
					solicitudAudienciaDTO.setExpedienteDTO(expedienteDTO);

					List<Long> lstIdEstatusSolicitud = new ArrayList<Long>();
					List<Long> lstIdEstatusAudiencia = new ArrayList<Long>();
					List<Long> lstIdTipoSolicitud = new ArrayList<Long>();
					List<Long> lstIdTipoAudiencia = new ArrayList<Long>();
					
					String tipoConsulta = SolicitudAudienciaDTO.NUMEROCASOASOCIADO;
					
					lstIdEstatusSolicitud.add(EstatusSolicitud.ABIERTA.getValorId());				
					
					lstIdTipoSolicitud.add(TiposSolicitudes.AUDIENCIA.getValorId());
					
					lstIdEstatusAudiencia.add(EstatusAudiencia.SOLICITADA.getValorId());
					
					lstIdTipoAudiencia.add(audienciaDTO.getTipoAudiencia().getIdCampo());
					
					List<SolicitudAudienciaDTO> listaSolicitudes = 
							audienciaDelegate.consultarSolicitudesAudienciaConCriterios(
												solicitudAudienciaDTO, 
												lstIdEstatusSolicitud, 
												lstIdTipoSolicitud, 
												lstIdEstatusAudiencia,
												lstIdTipoAudiencia, tipoConsulta);
					if (listaSolicitudes != null && !listaSolicitudes.isEmpty()){
						converter.alias(STR_CONVERTER_GUARDAR_AUDIENCIA, String.class);
						escribirRespuesta(response, converter.toXML(Evento.toString() + "," + listaSolicitudes.size()));
					}else{
						Evento = String.valueOf(idEvento);
						converter.alias(STR_CONVERTER_GUARDAR_AUDIENCIA, String.class);
						escribirRespuesta(response, converter.toXML(Evento));
					}
				}else{
					Evento = String.valueOf(idEvento);
					converter.alias(STR_CONVERTER_GUARDAR_AUDIENCIA, String.class);
					escribirRespuesta(response, converter.toXML(Evento));
				}
			}catch (NSJPNegocioException ne) {
				XStream converter=new XStream();
				log.info(ne.getCause(), ne);
				if(ne.getCodigo().equals(CodigoError.RANGO_FECHAS_CRUZADAS)){
					audienciaDelegate.cancelarAudienciaSistema(audienciaDTO.getId());
					escribirRespuesta(response,converter.toXML("fail"));
				}
				else if(ne.getCodigo().equals(CodigoError.SALA_OCUPADA)){
					audienciaDelegate.cancelarAudienciaSistema(audienciaDTO.getId());
					escribirRespuesta(response,converter.toXML("salaOcupada"));
				}
				
			}		
		}
		catch (Exception e) {
			log.info(e.getCause(), e);
			XStream converter=new XStream();
			audienciaDelegate.cancelarAudienciaSistema(id);
			escribirRespuesta(response,converter.toXML("failNoObs"));
		}
		return null;
	}
	
	/**
	 * Metodo utilizado el guardado de los datos programando la audiencia
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward guardarAudienciaProgramaNueva(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		XStream converter=new XStream();
		try {
			
			log.info("EJECUTANDO ACTION ---- GUARDAR AUDIENCIA");
			String idAudiencia = request.getParameter("idAudiencia");
			log.info("llega id audiencia"+ idAudiencia);
			String numeroExpediente = request.getParameter("numeroExpediente");
			log.info("llega numero Expediente"+ numeroExpediente);
			AudienciaDTO audienciaDTO;
			
			if (idAudiencia.equals("0")) {
				SolicitudAudienciaDTO solicitudAudienciaDTO = new SolicitudAudienciaDTO();
				solicitudAudienciaDTO.setNumCarpetaEjecucion(numeroExpediente);
				solicitudAudienciaDTO.setInstitucion(new ConfInstitucionDTO(Instituciones.PJ.getValorId()));
				solicitudAudienciaDTO.setFechaLimite(new Date());
				UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
				if(usuarioFirmado != null){
					if(usuarioFirmado.getFuncionario() != null)
						if(usuarioFirmado.getFuncionario().getNombreCompleto() != null )
							solicitudAudienciaDTO.setNombreSolicitante(usuarioFirmado.getFuncionario().getNombreCompleto());
				}
				SolicitudAudienciaDTO solAudNew =  solicitudDelegate.registrarSolicitudAudiencia(solicitudAudienciaDTO);				
				idAudiencia = solAudNew.getAudiencia().getId().toString(); 
			}
			
			audienciaDTO = (AudienciaDTO) request.getSession().getAttribute(KEY_SESSION_AUDIENCIA+idAudiencia);
			
			if(audienciaDTO == null){
				audienciaDTO = new AudienciaDTO();
				audienciaDTO.setId(Long.parseLong(idAudiencia));
				
			}			

			String anioEvento = request.getParameter("anioEvento");
			String mesEvento = request.getParameter("mesEvento");
			String diaEvento = request.getParameter("diaEvento");
			String duracionEstimada = request.getParameter("duracionEstimada");
			String idJueces = request.getParameter("idJueces");
			String idInvolucradosPA = request.getParameter("idInvolucradosPA");
			
			log.info("LISTA DE INVOLUCRADOS:::___:::::___:::::::////////::::::::______::::::::____"+ idInvolucradosPA);
			
			String salaTemporal = request.getParameter("salaTemporal");
			String tipoDeAudienciaSeleccionada = request.getParameter("tipoDeAudienciaSeleccionada");
			
			List<FuncionarioDTO> listaJueces = obtenerIdsFuncionario(idJueces);
			List<InvolucradoDTO> listaInvolucrados = obtenerIdsInvolucrados(idInvolucradosPA);
			
			if(salaTemporal.equalsIgnoreCase("false")){
				String horaEvento = request.getParameter("horaEvento");
				String minutoEvento = request.getParameter("minutoEvento");
				String idSala = request.getParameter("idSala");
				
				Date fechaHoraAudiencia = parseaCadenasFechaHora(diaEvento, mesEvento, anioEvento, horaEvento, minutoEvento);
				
				audienciaDTO.setFechaEvento(fechaHoraAudiencia);				
				audienciaDTO.setSala(new SalaAudienciaDTO(Long.parseLong(idSala), "Sala"));
			}
			else{
				
				//Para sala temporal
				SalaAudienciaDTO salaAudiencia = new SalaAudienciaDTO();
				
			   String direccionSalaTemp = request.getParameter("direccion");
			   String ubicacionSalaTemp = request.getParameter("ubicacion");
			   String motivosAsignacionTemp = request.getParameter("motivoAsignacion");
			   String horaProgramada = request.getParameter("horaTemp");
			   String minutoProgramado = request.getParameter("minutoTemp");
			   
			   salaAudiencia.setDomicilioSala(direccionSalaTemp);
			   salaAudiencia.setUbicacionSala(ubicacionSalaTemp);
			   salaAudiencia.setMotivo(motivosAsignacionTemp);
			   
			   Date fechaHoraAudiencia = parseaCadenasFechaHora(diaEvento, mesEvento, anioEvento, horaProgramada,minutoProgramado);
			   audienciaDTO.setFechaEvento(fechaHoraAudiencia);
			   audienciaDTO.setSala(salaAudiencia);
			}
			
			audienciaDTO.setTipoAudiencia(new ValorDTO(NumberUtils.toLong(tipoDeAudienciaSeleccionada)));
			audienciaDTO.setFuncionarios(listaJueces);
			audienciaDTO.setInvolucrados(listaInvolucrados);
			audienciaDTO.getSala().setTemporal(Boolean.parseBoolean(salaTemporal));
			audienciaDTO.setFechaAsignacionSala(new Date());
			audienciaDTO.setDuracionEstimada(Integer.parseInt(duracionEstimada));
			audienciaDTO.setEstatusAudiencia(new ValorDTO(EstatusAudiencia.PROGRAMADA.getValorId()));							
			log.info("antes del delegate::::guardarProgramacionAudiencia");
			try{
				
				audienciaDelegate.guardarProgramacionAudiencia(audienciaDTO);
				log.info("despues del delegate::::guardarProgramacionAudiencia");
				escribirRespuesta(response, converter.toXML(audienciaDTO.getId()));
				
			}catch (NSJPNegocioException ne) {
				
				log.info(ne.getCause(), ne);
				if(ne.getCodigo().equals(CodigoError.RANGO_FECHAS_CRUZADAS)){
					escribirRespuesta(response,converter.toXML("fail"));
				}
				else if(ne.getCodigo().equals(CodigoError.SALA_OCUPADA)){
					escribirRespuesta(response,converter.toXML("salaOcupada"));
				}
			}		
		} catch (Exception e) {
			log.info(e.getCause(), e);
		}
		return null;
	}
	/**
	 * Metodo utilizado para realizar la consulta de eventos por expediente (SOLO HIST�RCIO) 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward guardaComplejidadTipoAudiencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando guarda Complejidad TipoAudiencia");
			
			Long complejidad = Long.parseLong(request.getParameter("complejidad"));
			log.info("COMPLEJIDAD = "+complejidad);
			
			Long clave = Long.parseLong(request.getParameter("clave"));
			log.info("CLAVE = "+clave);			
			
//			audienciaDelegate.calcularCargaTrabajo(complejidad, clave);
			audienciaDelegate.actualizarComplejidadAudiencia(clave, complejidad);
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}

	
	/**
	 * Metodo utilizado para la consulta la disponibilidad de salas.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarDetalleSolicitudOtros(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("Action consultaDetalle Solicitud");
			Long idSolicitud = NumberUtils.toLong(request.getParameter("idSolicitud")); 
			
			
			SolicitudDTO solicitudDTO = new SolicitudDTO();
			solicitudDTO=solicitudDelegate.consultarSolicitudXId(idSolicitud);
			
			 converter.alias("solicitudDTO", SolicitudDTO.class);
			 String xml = converter.toXML(solicitudDTO);
			 response.setContentType("text/xml");
			 PrintWriter pw = response.getWriter();
			 pw.print(xml);
			 pw.flush();
			 pw.close();

		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}
	
	/**
	 * Metodo utilizado el guardado de los datos programando la audiencia
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward guardarEventoAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		XStream converter=new XStream();
		try {
			
			log.info("EJECUTANDO ACTION ---- GUARDAR EVENTO AUDIENCIA");
			
			String idJueces = request.getParameter("idJueces");
			log.info("llega idJueces: "+ idJueces);
			String anioEvento = request.getParameter("anioEvento");
			log.info("llega anioEvento: "+ anioEvento);
			String mesEvento = request.getParameter("mesEvento");
			log.info("llega mesEvento: "+ mesEvento);
			String diaEvento = request.getParameter("diaEvento");
			log.info("llega diaEvento: "+ diaEvento);
			String fechaEvento = mesEvento + "/" + diaEvento + "/" + anioEvento;
			log.info("fechaEvento formato US: "+ fechaEvento);
			String duracionEstimada = request.getParameter("duracionEstimada");
			log.info("llega duracionEstimada: "+ duracionEstimada);
			String horaEvento = request.getParameter("horaEvento");
			log.info("llega horaEvento: "+ horaEvento);
			String idSala = request.getParameter("idSala");
			log.info("llega idSala: "+ idSala);
			String tipoDeAudienciaSeleccionada = request.getParameter("tipoDeAudienciaSolicitada");
			log.info("llega tipoDeAudienciaSeleccionada: "+ tipoDeAudienciaSeleccionada);
					
			
			List<FuncionarioDTO> listaJueces = obtenerIdsFuncionario(idJueces);
			if(listaJueces != null){
				log.info("listaJueces size: "+ listaJueces.size());
				
				for(FuncionarioDTO juez: listaJueces){
					log.info("antes del delegate::::asignarTareaFuncionario");
					UsuarioDTO juezUsuario = usuarioDelegate.consultarUsuarioPorClaveFuncionario(juez.getClaveFuncionario());
					
					EventoCitaDTO evento = new EventoCitaDTO();
					evento.setTipoEvento(new ValorDTO(TipoEvento.AUDIENCIA.getValorId()));
					Date dateInicio = DateUtils.obtenerUS(fechaEvento, horaEvento);
					evento.setFechaInicioEvento(dateInicio);
					log.info("fecha y hora - inicial : "+ dateInicio.toString());
					//sumar la duracion estimada, 1 minuto = 60000 milisegundos
					Date aumento = new Date();
					aumento.setTime(dateInicio.getTime() + (Integer.parseInt(duracionEstimada) * 60000));
					Date dateFin = DateUtils.obtenerUS(fechaEvento, String.valueOf(aumento.getHours()+":"+aumento.getMinutes()));
					log.info("fecha y hora - final calculada : "+ dateFin.toString());
					evento.setFechaFinEvento(dateFin);
					evento.setNombreEvento("Audiencia programada");
					evento.setDireccion("Sala ("+idSala+")");
					evento.setDescripcionEvento("Asistir a Audiencia de tipo: "+tipoDeAudienciaSeleccionada);
					evento.setUsuario(juezUsuario);
					evento.setEstatus(new ValorDTO(EstatusEventoCita.NO_ATENDIDO.getValorId()));
					evento.setEsAlertaAlarma(true);
					TareaDTO tarea = new TareaDTO();
					tarea.setValor(new ValorDTO(TipoTarea.ACUDIR_AUDIENCIA.getValorId()));
					tarea.setEventoCita(evento);
					tarea.setUsuario(juezUsuario);
					tarea.setIdFuncionario(juez.getClaveFuncionario());
					tarea.setNtiempoReal(Short.parseShort(duracionEstimada));
					tarea.setDiaTarea(evento.getFechaInicioEvento());
					evento.setTarea(tarea);
					
					tareaDelegate.asignarTareaFuncionario(tarea, juez);
					
					log.info("despues del delegate::::asignarTareaFuncionario");
				}
				//se agendaron las tareas
				escribirRespuesta(response, converter.toXML("ok"));			
			}else{
				//no hay jueces
				escribirRespuesta(response, converter.toXML("fallo"));
			}
		}
		catch (Exception e) {
			log.info(e.getCause(), e);
			escribirRespuesta(response,converter.toXML("fallo"));
		}
		return null;
	}
	
	/**
	 * Metodo utilizado la cancelacion de solicitudes
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward cancelarSolicitudesDeAudiencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		XStream converter=new XStream();
		try {
			
			AudienciaDTO audienciaDTO = new AudienciaDTO();

			String tipoDeAudienciaSeleccionada = request.getParameter("tipoDeAudienciaSeleccionada");
						
			audienciaDTO.setTipoAudiencia(new ValorDTO(NumberUtils.toLong(tipoDeAudienciaSeleccionada)));
			
			String numCasoSolicitudAudienciaDetalle = request.getParameter("numCasoSolicitudAudienciaDetalle");
			
			try{
				
				if(numCasoSolicitudAudienciaDetalle != null && !numCasoSolicitudAudienciaDetalle.isEmpty()){
					
					SolicitudAudienciaDTO solicitudAudienciaDTO = new SolicitudAudienciaDTO();
					ExpedienteDTO expedienteDTO = new ExpedienteDTO();
					CasoDTO casoDTO = new CasoDTO();
					casoDTO.setNumeroGeneralCaso(numCasoSolicitudAudienciaDetalle);
					expedienteDTO.setCasoDTO(casoDTO);
					solicitudAudienciaDTO.setExpedienteDTO(expedienteDTO);
					
					List<Long> lstIdEstatusSolicitud = new ArrayList<Long>();
					List<Long> lstIdEstatusAudiencia = new ArrayList<Long>();
					List<Long> lstIdTipoSolicitud = new ArrayList<Long>();
					List<Long> lstIdTipoAudiencia = new ArrayList<Long>();
					
					String tipoConsulta = SolicitudAudienciaDTO.NUMEROCASOASOCIADO;
					
					lstIdEstatusSolicitud.add(EstatusSolicitud.ABIERTA.getValorId());				
					
					lstIdTipoSolicitud.add(TiposSolicitudes.AUDIENCIA.getValorId());
					
					lstIdEstatusAudiencia.add(EstatusAudiencia.SOLICITADA.getValorId());
					
					lstIdTipoAudiencia.add(audienciaDTO.getTipoAudiencia().getIdCampo());
									
					List<SolicitudAudienciaDTO> listaSolicitudes = 
							audienciaDelegate.consultarSolicitudesAudienciaConCriterios(
												solicitudAudienciaDTO, 
												lstIdEstatusSolicitud, 
												lstIdTipoSolicitud, 
												lstIdEstatusAudiencia,
												lstIdTipoAudiencia, tipoConsulta);
					
					
					for(SolicitudAudienciaDTO tmp : listaSolicitudes){
						solicitudDelegate.actualizaEstatusSolicitud(tmp, EstatusSolicitud.CANCELADA);
					}
					escribirRespuesta(response, converter.toXML("Canceladas"));					
				}
				
			}catch (NSJPNegocioException ne) {
				log.info(ne.getCause(), ne);
					escribirRespuesta(response,converter.toXML("fail"));
			}				
		}
		catch (Exception e) {
			log.info(e.getCause(), e);
			escribirRespuesta(response,converter.toXML("fail"));
		}
		return null;
	}
	
	
	/**
	 * Metodo para actualizar el caracter de una Audiencia
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward actualizarCaracterAudiencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO ACTUALIZACION DE CARACTER DE AUDIENCIA");
			
			//Se obtiene el id de la audiencia a actualizar 
			Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"), 0L);
			String esPublica = request.getParameter("esPublica");
			
			log.info("id de la audiencia::::::::::"+ idAudiencia);		
			log.info("esPublica::::::::::"+ esPublica);		
			
			Boolean esPublicaAudiencia = new Boolean(esPublica.equals("1") ? true: false);
			
			audienciaDelegate.actualizaCaracterAudiencia(idAudiencia, esPublicaAudiencia);
			
			converter.alias("respuesta", String.class);
			String xml = converter.toXML("true");
			log.info("xml de la evidencia respuesta: :::::::::"+ xml);			
			escribir(response, xml,null);												
			
		} catch (Exception e) {		
			log.info("ERROR AL ACTUALIZAR CARACTER AUDIENCIA ----");
			log.info(e.getCause(),e);
			converter.alias("respuesta", String.class);
			String xml = converter.toXML("false");
			log.info("xml de la evidencia respuesta: :::::::::"+ xml);			
			escribir(response, xml,null);		
			
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la complejidad del tipo de audiencia 
	 * y lo regresa a través de un objeto de JSON.
	 * @param mapping -mapeos de struts para enlazar las peticiones.
	 * @param form - forma de struts con la informaci&oacute;n pasada dentro de la petici&oacute;n.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML.
	 * @param response - Objeto de java que representa la respuesta en HTML.
	 * @return null - Se escribe el objeto de JSON directamente en el response pasado como argumento.
	 * @throws IOException - en el caso de que ocurra alg&uacute;n error al momento de escribir el 
	 * 						 JSON en la respuesta.
	 */
	public ActionForward consultarComplejidadTipoAudiencia (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		Long tipoAudienciaId = NumberUtils.toLong(request.getParameter(PARAM_ID_TIPO_AUDIENCIA), 0L);
		ValorDTO tipoAud = new ValorDTO(tipoAudienciaId);

		ValorDTO complejidadAudiencia = null;
		try {
			complejidadAudiencia = audienciaDelegate.consultarComplejidadTipoAudiencia(tipoAud);
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_JAVASCRIPT+"; "+ ConstantesGenerales.CHARSET_ISO_8859);
			response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
			PrintWriter writer = response.getWriter();
			
			writer.print(dto2Json(complejidadAudiencia));
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creaci&oacute;n de un objeto de JSON a partir de un 
	 * AsignacionCentroDetencionDTO
	 * @param asignacionCentroDetencionDTO - El DTO que se va a transformar en un JSON.
	 * @return jsonText - Cadena que representa el objeto de JSON creado.
	 */
	private String dto2Json(ValorDTO valorDTO) {
		JSONObject jsonObject = null;
		StringWriter out = null;
		String jsonText = "";
		jsonObject = convertirJson(valorDTO);
		out = new StringWriter();
		try {
			jsonObject.writeJSONString(out);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		jsonText = out.toString();
		return jsonText;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de un objeto de JSON con las propiedades
	 * del objeto valorDTO.
	 * @param valorDTO - Objeto del cual se van a escribir sus propiedades en el objeto de JSON.
	 * @return JSONObject - Onjeto de JSON con la informaci&oacute;n del DTO transcrita.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject convertirJson (ValorDTO valorDTO){
		JSONObject json = new JSONObject();
		if (valorDTO != null){
			json.put(KEY_JSON_ID_CAMPO,valorDTO.getIdCampo());
			json.put(KEY_JSON_VALOR, valorDTO.getValor());
			if (valorDTO.getNombreCampo() != null){
				json.put(KEY_JSON_NOMBRE_CAMPO,valorDTO.getNombreCampo());				
			}
			if (valorDTO.getCatalogoPadre() != null){
				json.put(KEY_JSON_CATALOGO_PADRE, valorDTO.getCatalogoPadre());				
			}
		}else{
			json.put(KEY_JSON_ID_CAMPO, 0L);
		}
		return json;
	}
	
}

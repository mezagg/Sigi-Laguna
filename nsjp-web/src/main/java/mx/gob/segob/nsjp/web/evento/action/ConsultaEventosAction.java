/**
* Nombre del Programa 			: ConsultaEventosAction.java
* Autor                         : AlejandroGA
* Compania                    	: Ultrasist
* Proyecto                      : NSJP                    Fecha: 01 June 2011
* Marca de cambio        		: N/A
* Descripcion General    		: Clase encargada de consultar los eventos para
* 								  notificaciones
* Programa Dependiente  		:N/A
* Programa Subsecuente 			:N/A
* Cond. de ejecucion        	:N/A
* Dias de ejecucion          	:N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       	:N/A
* Compania               		:N/A
* Proyecto                		:N/A                                 Fecha: N/A
* Modificacion          		:N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.evento.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.audiencia.BandejaNotificador;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.audiencia.Eventos;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.documento.MandamientoJudicialDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.mandamiento.AdministrarMandamientoJudicialDelegate;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.delegate.persona.PersonaDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.audiencia.EventoDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.domicilio.AsentamientoDTO;
import mx.gob.segob.nsjp.dto.domicilio.CiudadDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioExtranjeroDTO;
import mx.gob.segob.nsjp.dto.domicilio.EntidadFederativaDTO;
import mx.gob.segob.nsjp.dto.domicilio.MunicipioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.mandamiento.FiltroMandamientoDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AlejandroGA
 *
 */
public class ConsultaEventosAction extends GenericAction{

	/**Log de clase*/
	private static final Logger log  = Logger.getLogger(ConsultaEventosAction.class);
	
	@Autowired
	public AudienciaDelegate audienciaDelegate;
	
	@Autowired
	public PersonaDelegate personaDelegate;
	
	@Autowired
	public MandamientoJudicialDelegate mandamientoJudicialDelegate;
	
	@Autowired
	public InvolucradoDelegate involucradoDelegate;
	
	@Autowired
	public CatalogoDelegate catalogoDelegate;
	
	@Autowired
	public NotificacionDelegate notificacionDelegate;
	
	@Autowired
	public FuncionarioExternoService funcionarioExternoService; 
	
	@Autowired
	public AdministrarMandamientoJudicialDelegate administrarMandamientoJudicialDelegate;
	
	private final static String KEY_SESSION_EVENTO = "KEY_SESSION_EVENTO_DTO";
	private final static String PARAM_TIPO_MANDAMIENTOS = "tiposMandamientos";
	private final static String PARAM_FILTRO_PROPIOS = "filtrarPropios";
	private final static String INVOLUCRADO = "0";
	private final static String FUNCIONARIO = "1";
	private final static String FUNCIONARIO_EXTERNO = "2";
	private final static String SIN_NOTIFICACIONES = "0";
	private final static String NO_APLICA = "NA";
	
	

	/**
	 * Metodo utilizado para realizar la consulta de nuevos eventos 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultaNuevasNotificaciones(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Consulta Nuevas Notificaciones - Carga eventos");
			
			EventoDTO eventoDTO = new EventoDTO(); 
			eventoDTO.setBandeja(BandejaNotificador.NUEVO);
			
			List<EventoDTO> listaNuevosEventos=audienciaDelegate.consultarEventosParaNotificar(eventoDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");
			
//			int lTotalRegistros=listaNuevosEventos.size();
			
//			writer.print("<records>" + lTotalRegistros + "</records>");
			
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}
			
			log.info("ANTES DE ENTRAR AL FOR");
			for (EventoDTO eventoNuevoDTO : listaNuevosEventos) {
				
				log.info("EVENTO"+eventoNuevoDTO);
				writer.print("<row id='" + eventoNuevoDTO.getId()+ "'>");
					if( eventoNuevoDTO.getExpediente() != null && eventoNuevoDTO.getExpediente().getNumeroExpediente() != null){
						writer.print("<cell>" + eventoNuevoDTO.getExpediente().getNumeroExpediente()+ "</cell>");
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
					if( eventoNuevoDTO.getTipo() != null && eventoNuevoDTO.getTipo().getValor() != null ){
						writer.print("<cell>" + eventoNuevoDTO.getTipo().getValor()+ "</cell>");
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
					if(eventoNuevoDTO.getTipoEvento() != null && eventoNuevoDTO.getTipoEvento().getNombre() != null){
						
						if(eventoNuevoDTO.getEstatusAudiencia().getIdCampo() == EstatusAudiencia.CANCELADA.getValorId().longValue())
							writer.print("<cell>Audiencia Cancelada</cell>");
						else{
							if(eventoNuevoDTO.getEstatusAudiencia().getIdCampo() == EstatusAudiencia.REPROGRAMADA.getValorId().longValue())
								writer.print("<cell>Audiencia Reprogramada</cell>");
							else
								writer.print("<cell>" + eventoNuevoDTO.getTipoEvento().getNombre()+ "</cell>");
						}
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
					if(eventoNuevoDTO.getFechaSolicitud() != null){
						String fechaSolicitud=DateUtils.formatear(eventoNuevoDTO.getFechaSolicitud());
						String horaSolicitud=DateUtils.formatearHora(eventoNuevoDTO.getFechaSolicitud());
						writer.print("<cell>" + fechaSolicitud+" "+horaSolicitud+ "</cell>");
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
					if( eventoNuevoDTO.getFechaEvento() != null){

						String fechaEvento=DateUtils.formatear(eventoNuevoDTO.getFechaEvento());
						String horaEvento=DateUtils.formatearHora(eventoNuevoDTO.getFechaEvento());
						writer.print("<cell>" + fechaEvento+" "+horaEvento+ "</cell>");
					}
					else{
						writer.print("<cell>" + "---" + "</cell>");
					}
				writer.print("</row>");
			}			
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para realizar del detalle de eventos
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultaDetalleNotificaciones(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO CONSULTA DETALLE NOTIFICACIONES - CARGA DETALLE DEL EVENTO");
			
			//Se obtiene el id del evento a consultar a detalle
			String idEvento = request.getParameter("idEvento");
			//Se obtiene el tipo de respuesta que se desea, 
			String tipoDeRespuesta = request.getParameter("tipoDeRespuesta");
			//Permite saber si se desea consultar Funcionarios o Involucrados
			String tipoPersona = request.getParameter("esFuncionario");
			
			log.info("___________________________________________");
			log.info("VERIFICANDO PARAMETROS..");
			log.info("idEvento:::::::::::"+ idEvento);
			log.info("tipoDeRespuesta::::::::::"+tipoDeRespuesta);
			log.info("tipoPersona::::::::::::::"+tipoPersona);
			log.info("___________________________________________");
			
			EventoDTO eventoDTO = new EventoDTO(); 
			eventoDTO.setId(NumberUtils.toLong(idEvento, 0L));
			eventoDTO.setTipoEvento(Eventos.AUDIENCIA);
			
			
			if(Integer.parseInt(tipoDeRespuesta) == 1){
				
				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA DETALLE DE EVENTO:::::");
				/**
				 * Se desea enviar el objeto evento y en esta, parte de la respuesta
				 * solo llenar los campos que la TAB Detalle evento de la pantalla
				 * atencionSolicitudAudienciaNotificador.jsp
				 */
				
				log.info("Obteniendo el evento.....");
				eventoDTO = audienciaDelegate.obtenerEvento(eventoDTO);
				
				if (eventoDTO != null && eventoDTO.getExpediente() != null
						&& eventoDTO.getExpediente().getNumeroExpediente() != null) {
					request.setAttribute("numeroExpediente", eventoDTO
							.getExpediente().getNumeroExpediente());

					setExpedienteTrabajo(request, eventoDTO.getExpediente());
				}
				converter.alias("eventoDTO", EventoDTO.class);
				String xml = converter.toXML(eventoDTO);
				escribir(response, xml,null);
				
				//Se sube a sesion el objeto evento con todos sus atributos
				request.getSession().setAttribute(KEY_SESSION_EVENTO+idEvento, eventoDTO);
			}
			else{
				
				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA GRID:::::");				
				AudienciaDTO audienciaDTO = new AudienciaDTO();		
				audienciaDTO.setId(NumberUtils.toLong(idEvento, 0L));
				
				List<InvolucradoDTO> listaInvolucrados = null;
				List<FuncionarioDTO> listaFuncionarios = null;
				List<FuncionarioExternoDTO> listaFuncionariosExternos = null;
				
				if (tipoPersona.equals(INVOLUCRADO)) {
					//Permite consultar involucrados de una audiencia por calidad
			    	List<Long> calidades = new ArrayList<Long>();
			    	
					// Bandera para que el servicio solo recupere los
					// involucrados VIVOS
					listaInvolucrados = new ArrayList<InvolucradoDTO>();
					InvolucradoDTO invoParametro = new InvolucradoDTO();
					invoParametro.setEsVivo(true);
					listaInvolucrados.add(invoParametro);
					audienciaDTO.setInvolucrados(listaInvolucrados);
					// La bandera se toma del primer involucrado de la audiencia
					listaInvolucrados = involucradoDelegate
							.obtenerInvolucradosDTOAudienciaPorCalidades(
									audienciaDTO, calidades);
					escribeXmlNotificacionesInvolucrado(listaInvolucrados, eventoDTO, response);

				}
				if (tipoPersona.equals(FUNCIONARIO)) {
					//Permite consultar funcionarios de una audiencia por especialidad
					List<Long> especialidades = new ArrayList<Long>();
					
					listaFuncionarios = involucradoDelegate
							.obtenerFuncionariosDTOAudienciaPorTipoEspecialidad(
									audienciaDTO, especialidades);
					escribeXmlNotificacionesFuncionario(listaFuncionarios, eventoDTO, response);
				}
				if (tipoPersona.equals(FUNCIONARIO_EXTERNO)) {
					listaFuncionariosExternos = audienciaDelegate
							.consultarFuncionarioExternoAudienciaNotificaciones(audienciaDTO);
					escribeXmlNotificacionesFuncionarioExterno(listaFuncionariosExternos, eventoDTO, response);
				}	
			}						
			
		} catch (Exception e) {		
			log.info("ERROR AL CONSULTAR EL EVENTO ---- consultaDetalleNotificaciones");
			log.info(e.getCause(),e);
			escribir(response, "consultaDetalleNotificaciones",null);
			
		}
		return null;
	}
	
	
	/**
	 * Metodo que construye el XML para mostrar los involucrados
	 * 
	 * @param listaInvolucrados
	 * @param response
	 * @throws IOException
	 */
	protected void escribeXmlNotificacionesInvolucrado(
			List<InvolucradoDTO> listaInvolucrados, EventoDTO eventoDTO,
			HttpServletResponse response) throws IOException {

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();

		writer.print("<rows>");
		
		PaginadorUtil.obtenerPaginacionManual(listaInvolucrados);
		String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
		writer.print(paginacion);
		
		if (listaInvolucrados != null && !listaInvolucrados.isEmpty()) {
			for (InvolucradoDTO invo : listaInvolucrados) {
				//Id del involucrado
				writer.print("<row id='" + invo.getElementoId() + "'>");
				// Nombre del Involucrado
				writer.print("<cell>" + invo.getNombreCompleto() + "</cell>");

				// Tipo evento
				if (eventoDTO.getTipoEvento() != null
						&& eventoDTO.getTipoEvento().getNombre() != null
						&& !eventoDTO.getTipoEvento().getNombre().isEmpty()) {
					writer.print("<cell>" + eventoDTO.getTipoEvento().getNombre()
							+ "</cell>");
				} else {
					writer.print("<cell> - </cell>");
				}

				// Calidad
				if (invo.getCalidadDTO() != null
						&& invo.getCalidadDTO().getValorIdCalidad() != null
						&& invo.getCalidadDTO().getValorIdCalidad().getValor() != null
						&& !invo.getCalidadDTO().getValorIdCalidad().getValor()
								.isEmpty()) {
					writer.print("<cell>"
							+ invo.getCalidadDTO().getValorIdCalidad()
									.getValor() + "</cell>");
				} else {
					writer.print("<cell> - </cell>");
				}

				// Ultima Notificacion Creada/Recibida
				// Fecha creacion y Hora recepcion
				if (invo.getNotificaciones() != null
						&& !invo.getNotificaciones().isEmpty()) {

					/*
					 * Ultima notificacion, haya sido recibida o no.
					 */
					NotificacionDTO ultimaNotificacion = invo
							.getNotificaciones().get(0);

					String fechaCreacion = DateUtils
							.formatear(ultimaNotificacion.getFechaCreacion());
					String horaCreacion = DateUtils
							.formatearHora(ultimaNotificacion
									.getFechaCreacion());

					/*
					 * Representa la ultima notificacion que tiene una fecha de
					 * recepcion.
					 */
					NotificacionDTO ultimaNotificacionRecibida = this.obtenerUltimaNotificacionRecibida(invo
							.getNotificaciones());

					String fechaRecepcionUltimaNotif = null;
					String horaRecepcionUltimaNotif = null;

					if (ultimaNotificacionRecibida != null) {
						fechaRecepcionUltimaNotif = DateUtils
								.formatear(ultimaNotificacionRecibida
										.getFechaCitado());
						horaRecepcionUltimaNotif = DateUtils
								.formatearHora(ultimaNotificacionRecibida
										.getFechaCitado());
					}

					//fecha creacion
					if (fechaCreacion != null && horaCreacion != null
							&& !fechaCreacion.isEmpty()
							&& !horaCreacion.isEmpty()) {
						writer.print("<cell>" + fechaCreacion + " "
								+ horaCreacion + "</cell>");
					} else {
						writer.print("<cell>" + NO_APLICA + "</cell>");
					}
					
					//fecha recepcion
					if (fechaRecepcionUltimaNotif != null && horaRecepcionUltimaNotif != null
							&& !fechaRecepcionUltimaNotif.isEmpty()
							&& !horaRecepcionUltimaNotif.isEmpty()) {
						writer.print("<cell>" + fechaRecepcionUltimaNotif + " "
								+ horaRecepcionUltimaNotif + "</cell>");
					} else {
						writer.print("<cell>" + NO_APLICA + "</cell>");
					}
					// Numero de notificaciones
					writer.print("<cell>"
							+ ultimaNotificacion.getConsecutivoNotificacion()
							+ "</cell>");

				} else {
					//fechas
					writer.print("<cell>" + NO_APLICA + "</cell>");
					writer.print("<cell>" + NO_APLICA + "</cell>");
					// Numero de notificaciones
					writer.print("<cell>"+SIN_NOTIFICACIONES+"</cell>");
				}
				
				// Es funcionario
				writer.print("<cell>"+INVOLUCRADO+"</cell>");

				writer.print("</row>");
			}
		}
		writer.print("</rows>");
		writer.flush();
		writer.close();
	}
	
	
	/**
	 * Metodo que construye el XML para mostrar los funcionario locales
	 * 
	 * @param listaInvolucrados
	 * @param response
	 * @throws IOException
	 */
	protected void escribeXmlNotificacionesFuncionario(
			List<FuncionarioDTO> listaFuncionarios, EventoDTO eventoDTO,
			HttpServletResponse response) throws IOException {

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();
		
		writer.print("<rows>");
		
		PaginadorUtil.obtenerPaginacionManual(listaFuncionarios);
		String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
		writer.print(paginacion);

		if (listaFuncionarios != null && !listaFuncionarios.isEmpty()) {
			for (FuncionarioDTO funcionario : listaFuncionarios) {

				// Id del funcionario externo
				writer.print("<row id='" + funcionario.getClaveFuncionario()
						+ "'>");
				// Nombre del Involucrado
				writer.print("<cell>" + funcionario.getNombreCompleto()
						+ "</cell>");

				// Institucion Id
				writer.print("<cell>" + Instituciones.PJ.getValorId()
						+ "</cell>");
				// Nombre institucion
				writer.print("<cell>" + Instituciones.PJ.name()
						+ "</cell>");

				// Tipo evento
				if (eventoDTO.getTipoEvento() != null
						&& eventoDTO.getTipoEvento().getNombre() != null
						&& !eventoDTO.getTipoEvento().getNombre().isEmpty()) {
					writer.print("<cell>"
							+ eventoDTO.getTipoEvento().getNombre() + "</cell>");
				} else {
					writer.print("<cell> - </cell>");
				}

				// Fecha creacion y Hora recepcion
				if (funcionario.getNotificaciones() != null
						&& !funcionario.getNotificaciones().isEmpty()) {

					/*
					 * Ultima notificacion, haya sido recibida o no.
					 */
					NotificacionDTO ultimaNotificacion = funcionario
							.getNotificaciones().get(0);

					String fechaCreacion = DateUtils
							.formatear(ultimaNotificacion.getFechaCreacion());
					String horaCreacion = DateUtils
							.formatearHora(ultimaNotificacion
									.getFechaCreacion());

					/*
					 * Representa la ultima notificacion que tiene una fecha de
					 * recepcion.
					 */
					NotificacionDTO ultimaNotificacionRecibida = obtenerUltimaNotificacionRecibida(funcionario
							.getNotificaciones());

					String fechaRecepcionUltimaNotif = null;
					String horaRecepcionUltimaNotif = null;

					if (ultimaNotificacionRecibida != null) {
						fechaRecepcionUltimaNotif = DateUtils
								.formatear(ultimaNotificacionRecibida
										.getFechaCitado());
						horaRecepcionUltimaNotif = DateUtils
								.formatearHora(ultimaNotificacionRecibida
										.getFechaCitado());
					}

					// Fecha de creacion
					if (fechaCreacion != null && horaCreacion != null
							&& !fechaCreacion.isEmpty()
							&& !horaCreacion.isEmpty()) {
						writer.print("<cell>" + fechaCreacion + " "
								+ horaCreacion + "</cell>");
					} else {

						writer.print("<cell>" + NO_APLICA + "</cell>");
					}
					
					// Fecha de recepcion
					if (fechaRecepcionUltimaNotif != null && horaRecepcionUltimaNotif != null
							&& !fechaRecepcionUltimaNotif.isEmpty()
							&& !horaRecepcionUltimaNotif.isEmpty()) {
						writer.print("<cell>" + fechaRecepcionUltimaNotif + " "
								+ horaRecepcionUltimaNotif + "</cell>");
					} else {
						writer.print("<cell>" + NO_APLICA + "</cell>");
					}
					// Numero de notificaciones
					writer.print("<cell>"
							+ ultimaNotificacion.getConsecutivoNotificacion()
							+ "</cell>");
				} else {
					// fechas
					writer.print("<cell>" + NO_APLICA + "</cell>");
					writer.print("<cell>" + NO_APLICA + "</cell>");
					// numero de notificaciones
					writer.print("<cell>" + SIN_NOTIFICACIONES + "</cell>");
				}
				writer.print("<cell>"+FUNCIONARIO+"</cell>");
				writer.print("</row>");
			}
		}
		writer.print("</rows>");
		writer.flush();
		writer.close();
	}
	
	
	/**
	 * Metodo que construye el XML para mostrar los funcionario externos
	 * 
	 * @param listaInvolucrados
	 * @param response
	 * @throws IOException
	 */
	protected void escribeXmlNotificacionesFuncionarioExterno(
			List<FuncionarioExternoDTO> listaFuncionariosExternos,
			EventoDTO eventoDTO, HttpServletResponse response)
			throws IOException {

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();

		writer.print("<rows>");
		
		PaginadorUtil.obtenerPaginacionManual(listaFuncionariosExternos);
		String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
		writer.print(paginacion);

		if (listaFuncionariosExternos != null
				&& !listaFuncionariosExternos.isEmpty()) {
			for (FuncionarioExternoDTO funcExt : listaFuncionariosExternos) {

				// Id del funcionario externo
				writer.print("<row id='" + funcExt.getFuncionarioExternoId()
						+ "'>");
				// Nombre del Involucrado
				writer.print("<cell>" + funcExt.getNombreCompleto() + "</cell>");
				// Institucion Id
				writer.print("<cell>"
						+ funcExt.getConfInstitucionDTO()
								.getConfInstitucionId() + "</cell>");
				// Nombre institucion
				writer.print("<cell>"
						+ funcExt.getConfInstitucionDTO().getNombreInst()
						+ "</cell>");

				// Tipo evento
				if (eventoDTO.getTipoEvento() != null
						&& eventoDTO.getTipoEvento().getNombre() != null
						&& !eventoDTO.getTipoEvento().getNombre().isEmpty()) {
					writer.print("<cell>"
							+ eventoDTO.getTipoEvento().getNombre() + "</cell>");
				} else {
					writer.print("<cell> - </cell>");
				}

				// Fecha creacion y Hora recepcion
				if (funcExt.getNotificaciones() != null
						&& !funcExt.getNotificaciones().isEmpty()) {

					/*
					 *Ultima notificacion, haya sido recibida o no. 
					 */
					NotificacionDTO ultimaNotificacion = funcExt
							.getNotificaciones().get(0);

					String fechaCreacion = DateUtils
					.formatear(ultimaNotificacion.getFechaCreacion());
					String horaCreacion = DateUtils
					.formatearHora(ultimaNotificacion
							.getFechaCreacion());
					
					/*
					 * Representa la ultima notificacion que tiene una fecha de
					 * recepcion.
					 */
					NotificacionDTO ultimaNotificacionRecibida = obtenerUltimaNotificacionRecibida(funcExt
							.getNotificaciones());


					String fechaRecepcionUltimaNotif = null;
					String horaRecepcionUltimaNotif = null;

					if (ultimaNotificacionRecibida != null) {
						fechaRecepcionUltimaNotif = DateUtils
								.formatear(ultimaNotificacionRecibida
										.getFechaCitado());
						horaRecepcionUltimaNotif = DateUtils
								.formatearHora(ultimaNotificacionRecibida
										.getFechaCitado());
					}

					// Fecha de creacion
					if (fechaCreacion != null && horaCreacion != null
							&& !fechaCreacion.isEmpty()
							&& !horaCreacion.isEmpty()) {
						writer.print("<cell>" + fechaCreacion + " "
								+ horaCreacion + "</cell>");
					} else {

						writer.print("<cell>" + NO_APLICA + " </cell>");
					}
					// Fecha de recepcion
					if (fechaRecepcionUltimaNotif != null && horaRecepcionUltimaNotif != null
							&& !fechaRecepcionUltimaNotif.isEmpty()
							&& !horaRecepcionUltimaNotif.isEmpty()) {
						writer.print("<cell>" + fechaRecepcionUltimaNotif + " "
								+ horaRecepcionUltimaNotif + "</cell>");
					} else {
						writer.print("<cell>" + NO_APLICA + "</cell>");
					}
					// numero de notificaciones
					writer.print("<cell>"
							+ ultimaNotificacion.getConsecutivoNotificacion()
							+ "</cell>");
				} else {
					// fechas
					writer.print("<cell>" + NO_APLICA + "</cell>");
					writer.print("<cell>" + NO_APLICA + "</cell>");
					// numero de notificaciones
					writer.print("<cell>" + SIN_NOTIFICACIONES + "</cell>");
				}
				writer.print("<cell>"+ FUNCIONARIO +"</cell>");
				writer.print("</row>");
			}
		}
		writer.print("</rows>");
		writer.flush();
		writer.close();
	}
	
	/**
	 * M&eacute;todo para obtener la &uacute;ltima notificaci&oacute;n que fue
	 * recibida, es decir, que tiene fecha de citado
	 * 
	 * @param listaNotificaciones
	 * @return ultimaNotificacionRecibida
	 */
	private NotificacionDTO obtenerUltimaNotificacionRecibida(
			List<NotificacionDTO> listaNotificaciones) {

		NotificacionDTO ultimaNotificacionRecibida = null;

		if (listaNotificaciones != null && !listaNotificaciones.isEmpty()) {
			for (NotificacionDTO notificacionDto : listaNotificaciones) {
				if (notificacionDto.getFechaCitado() != null) {
					ultimaNotificacionRecibida = notificacionDto;
					break;
				}
			}
		}

		return ultimaNotificacionRecibida;
	}
	
	/**
	 * Metodo utilizado para realizar la consulta de eventos por expediente (SOLO HISTORCIO) 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward consultaEventosPorExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Consulta Eventos por expediente- HISTORICO");
			
			EventoDTO eventoDTO = new EventoDTO(); 
			eventoDTO.setBandeja(BandejaNotificador.HISTORICO);
			
			
			String numeroExpedienteId = request.getParameter("numeroExpedienteId");
			log.info("ID DEL NUMERO DE EXPEDIENTE="+numeroExpedienteId);
			
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();	
			expedienteDTO.setNumeroExpedienteId(Long.parseLong(numeroExpedienteId));
			eventoDTO.setExpediente(expedienteDTO);
	
			
			List<EventoDTO> listaNuevosEventos=audienciaDelegate.consultarEventosParaNotificar(eventoDTO);

			log.info("EVENTOs"+listaNuevosEventos);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");
			
			int lTotalRegistros=listaNuevosEventos.size();
			
			writer.print("<records>" + lTotalRegistros + "</records>");
				for (EventoDTO eventoNuevoDTO : listaNuevosEventos) {
					
					log.info("EVENTO"+eventoNuevoDTO);
					
					writer.print("<row id='" + eventoNuevoDTO.getId()+ "'>");
						if(eventoNuevoDTO.getExpediente() != null && eventoNuevoDTO.getExpediente().getNumeroExpediente() != null){
							writer.print("<cell>" + eventoNuevoDTO.getExpediente().getNumeroExpediente()+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
						if( eventoNuevoDTO.getTipo() != null && eventoNuevoDTO.getTipo().getValor() != null){
							writer.print("<cell>" + eventoNuevoDTO.getTipo().getValor()+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
						if( eventoNuevoDTO.getTipoEvento() != null && eventoNuevoDTO.getTipoEvento().getNombre() != null ){
							writer.print("<cell>" + eventoNuevoDTO.getTipoEvento().getNombre()+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
						if(eventoNuevoDTO.getFechaSolicitud() != null ){
							String fechaSolicitud=DateUtils.formatear(eventoNuevoDTO.getFechaSolicitud());
							String horaSolicitud=DateUtils.formatearHora(eventoNuevoDTO.getFechaSolicitud());
							writer.print("<cell>" + fechaSolicitud+" "+horaSolicitud+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
						if( eventoNuevoDTO.getFechaEvento() != null){
							String fechaEvento=DateUtils.formatear(eventoNuevoDTO.getFechaEvento());
							String horaEvento=DateUtils.formatearHora(eventoNuevoDTO.getFechaEvento());
							writer.print("<cell>" + fechaEvento+" "+horaEvento+ "</cell>");
						}
						else{
							writer.print("<cell>" + "---" + "</cell>");
						}
					writer.print("</row>");
				}			
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para realizar el seguimiento a audiencias
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward darSeguimientoEventos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUNTANDO ACTION CONSULTAR EVENTOS POR ESTATUS NOTIFICACION");


			NotificacionDTO notificacionDto = new NotificacionDTO();
			
			Long estatusNotificacion = NumberUtils.toLong(request.getParameter("estatusNotificacion"),0L);

			//Si el estatus en NULL o CERO consultara las audiecias que no tengan notificaciones asociadas
			if(estatusNotificacion != null && estatusNotificacion > 0L){
				ValorDTO estatus = new ValorDTO();
				estatus.setIdCampo(estatusNotificacion);
				notificacionDto.setEstatus(estatus);
			}
			
//			String tipoEvento = request.getParameter("eventoTipo");

//			if(tipoEvento.equalsIgnoreCase("audiencia")){
//				log.info("SEGUIMENTO AUDIENCIAS");
//				eventoDTO.setTipoEvento(Eventos.AUDENCIA);
//			}
//
//			if(tipoEvento.equalsIgnoreCase("recurso")){
//				log.info("SEGUIMENTO RECURSO");
//				eventoDTO.setTipoEvento(Eventos.RECURSO);
//			}

			List<EventoDTO> listaNuevosEventos=audienciaDelegate.consultarEventosPorEstatusNotificacion(notificacionDto);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			log.debug("pag :: " + pag);
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}
			
			for (EventoDTO eventoNuevoDTO : listaNuevosEventos) {

				log.info("EVENTO"+eventoNuevoDTO);
				writer.print("<row id='" + eventoNuevoDTO.getId()+ "'>");
				
				if(eventoNuevoDTO.getExpediente() != null && eventoNuevoDTO.getExpediente().getNumeroExpediente() != null){
					writer.print("<cell>" + eventoNuevoDTO.getExpediente().getNumeroExpediente()+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				
				if(eventoNuevoDTO.getTipo() != null &&  eventoNuevoDTO.getTipo().getValor() != null){
					writer.print("<cell>" + eventoNuevoDTO.getTipo().getValor()+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				
				if(eventoNuevoDTO.getTipoEvento() != null && eventoNuevoDTO.getTipoEvento().getNombre() != null){
					writer.print("<cell>" + eventoNuevoDTO.getTipoEvento().getNombre()+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				
				if (eventoNuevoDTO.getEstatusAudiencia() != null
						&& eventoNuevoDTO.getEstatusAudiencia().getValor() != null
						&& !eventoNuevoDTO.getEstatusAudiencia().getValor()
								.isEmpty()){
					writer.print("<cell>" + eventoNuevoDTO.getEstatusAudiencia().getValor() + "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				
				if( eventoNuevoDTO.getFechaSolicitud() != null){
					String fechaSolicitud=DateUtils.formatear(eventoNuevoDTO.getFechaSolicitud());
					String horaSolicitud=DateUtils.formatearHora(eventoNuevoDTO.getFechaSolicitud());
					writer.print("<cell>" + fechaSolicitud+" "+horaSolicitud+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				
				if( eventoNuevoDTO.getFechaEvento() != null){
					String fechaEvento=DateUtils.formatear(eventoNuevoDTO.getFechaEvento());
					String horaEvento=DateUtils.formatearHora(eventoNuevoDTO.getFechaEvento());
					writer.print("<cell>" + fechaEvento+" "+horaEvento+ "</cell>");
				}
				else{
					writer.print("<cell>" + "---" + "</cell>");
				}
				
				writer.print("</row>");
			}			
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	
	/**
	 * Metodo utilizado para realizar el paso del parametro id del evento
	 * a la JSP de atencionSolicitudAudienciaNotificador
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward acarrearIdEvento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando action acarrearIdEvento");
			
			String idEvento = request.getParameter("idEvento");
			log.info("ID-DEL EVENTO:::"+ idEvento);
			request.setAttribute("idEvento",idEvento);
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return mapping.findForward("succes");
	}	
	
	/**
	 * Metodo utilizado para realizar la consulta de el detalle de la persona
	 * por notificacion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultaDetalleNotificacionesPersona(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			log.info("EJECUTANDO ACTION CONSULTAR DETALLE NOTIFICACIONES PERSONA......");

			
			String tipoDeRespuesta = request.getParameter("tipoDeRespuesta");
			// Se obtiene el tipo de respuesta que se desea,
			Long idEvento = NumberUtils.toLong(
					request.getParameter("idEvento"), 0L);
			// Se obtiene el tipo de respuesta que se desea
			Long funcionarioId = NumberUtils.toLong(
					request.getParameter("funcionarioId"), 0L);
			Long funcionarioExternoId = NumberUtils.toLong(
					request.getParameter("funcionarioExternoId"), 0L);
			Long involucradoId = NumberUtils.toLong(
					request.getParameter("involucradoId"), 0L);
			
			log.info("_____________________________________________________________");
			log.info("VERIFICANDO PARAMETRROS................");
			log.info("tipo respuesta::::::::::" + tipoDeRespuesta);
			log.info("funcionarioId.........." + funcionarioId);
			log.info("funcionarioExternoId..." + funcionarioExternoId);
			log.info("involucradoId.........." + involucradoId);
			log.info("idEvento.........." + idEvento);
			log.info("_____________________________________________________________");

			

			if (Integer.parseInt(tipoDeRespuesta) == 1) {

				EventoDTO eventoDTO = new EventoDTO();
				eventoDTO.setId(idEvento);
				eventoDTO.setTipoEvento(Eventos.AUDIENCIA);
				eventoDTO = (EventoDTO) audienciaDelegate
						.obtenerEvento(eventoDTO);

				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA DETALLE DE LOS DATOS DE LA PERSONA:::::");
				/**
				 * Se desea enviar el objeto evento y en esta, parte de la
				 * respuesta solo llenar los campos que la TAB Detalle evento de
				 * la pantalla atencionSolicitudAudienciaNotificador.jsp
				 */
				InvolucradoDTO involucrado = null;
				FuncionarioDTO funcionario = null;
				String xml = "";
				
				if (funcionarioId > 0L) {
					//TODO Consultar el funcionario por id De funcionario
					for (FuncionarioDTO func : eventoDTO.getFuncionarios()) {
						if (func.getClaveFuncionario().equals(funcionarioId.longValue())) {
							funcionario = func;
							log.info("FUNCIONARIO_DTO, OBTENIDO:::::" + func);
						}
					}
					converter.alias("funcionario", FuncionarioDTO.class);
					xml = converter.toXML(funcionario);
				}

				if (funcionarioExternoId > 0L) {
					// LISTA DE FUNCIONARIOS EXTERNOS
					FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO(funcionarioExternoId);
					funcionarioExternoDTO = funcionarioExternoService.consultarFuncionarioExternoPorId(funcionarioExternoDTO);
					converter.alias("funcionarioExterno", FuncionarioExternoDTO.class);
					xml = converter.toXML(funcionarioExternoDTO);
				}

				if (involucradoId > 0L) {
					//TODO consultar involucrado por involucrado ID
					for (InvolucradoDTO invo : eventoDTO.getInvolucrados()) {
						if (invo.getElementoId().equals(involucradoId.longValue())) {
							involucrado = invo;
							log.info("INVOLUCRADO_DTO, OBTENIDO:::::" + invo);
						}
					}
					converter.alias("involucrado", InvolucradoDTO.class);
					xml = converter.toXML(involucrado);
				}

				escribir(response, xml, null);
			} else {

				log.info("EL TIPO DE RESPUESTA ES EN FORMATO PARA GRID DETALLE NOTIFICACIONES:::::");

				/**
				 * En esta parte de la respuesta se desea enviar la informacion
				 * del grid, correspondiente al detalle de notificaciones de una
				 * persona en la ventana modal de
				 * atencionSolicitudAudienciaNotificador.jsp
				 */
				List<NotificacionDTO> lista = null;
				if (funcionarioId > 0L) {
					lista = notificacionDelegate.consultaNotificaciones(
							idEvento, funcionarioId, true,null);
				}
				if(funcionarioExternoId > 0L){
					lista = notificacionDelegate.consultaNotificaciones(
							idEvento, funcionarioExternoId, null,true);
				}
				
				if (involucradoId > 0L) {
					lista = notificacionDelegate.consultaNotificaciones(
							idEvento, involucradoId,false,null);
				}

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");

				String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
				writer.print(paginacion);

				for (NotificacionDTO notificacionDTO : lista) {

					log.info("NOTIFICACION:::::::::::::::" + notificacionDTO);
					writer.print("<row id='" + notificacionDTO.getDocumentoId()
							+ "'>");

					// NUMERO DE NOTIFICACION
					if (notificacionDTO.getConsecutivoNotificacion() != null) {
						writer.print("<cell>"
								+ notificacionDTO.getConsecutivoNotificacion()
								+ "</cell>");
					} else {
						writer.print("<cell>" + " - " + "</cell>");
					}

					// FORMA NOTIFICACION
					if (notificacionDTO.getCatFormaNotificacionDTO() != null
							&& notificacionDTO.getCatFormaNotificacionDTO()
									.getFormaNotificacion() != null) {
						writer.print("<cell>"
								+ notificacionDTO.getCatFormaNotificacionDTO()
										.getFormaNotificacion() + "</cell>");
					} else {
						writer.print("<cell>" + " - " + "</cell>");
					}

					// FECHA / HORA DE CREACION DE LA NOTIFICACION
					if (notificacionDTO.getFechaCreacion() != null) {
						String fechaCreacion = DateUtils
								.formatear(notificacionDTO.getFechaCreacion());
						String horaCreacion = DateUtils
								.formatearHora(notificacionDTO
										.getFechaCreacion());
						writer.print("<cell>" + fechaCreacion + " "
								+ horaCreacion + "</cell>");
					} else {
						writer.print("<cell>" + " - " + "</cell>");
					}

					// FECHA DE RECEPCION DE LA NOTIFICACION
					if (notificacionDTO.getFechaCitado() != null) {
						String fechaRecepcion = DateUtils
								.formatear(notificacionDTO.getFechaCitado());
						writer.print("<cell>" + fechaRecepcion + "</cell>");
					} else {

						if (notificacionDTO.getEstatus() != null
								&& notificacionDTO.getEstatus().getIdCampo() != null
								&& notificacionDTO
										.getEstatus()
										.getIdCampo()
										.equals(EstatusNotificacion.NO_ATENDIDA
												.getValorId())) {

							writer.print("<cell>" + "NA" + "</cell>");
						} else {
							writer.print("<cell><![CDATA[<input type='text'  style='width:70px;' readonly='readonly' id='fechaRecepcion_"
									+ notificacionDTO.getDocumentoId()
									+ "'>]]></cell>");
						}
					}

					// HORA DE RECEPCION DE LA NOTIFICACION
					if (notificacionDTO.getFechaCitado() != null) {
						String horaRecepcion = DateUtils
								.formatearHora(notificacionDTO.getFechaCitado());
						writer.print("<cell>" + horaRecepcion + "</cell>");
					} else {

						if (notificacionDTO.getEstatus() != null
								&& notificacionDTO.getEstatus().getIdCampo() != null
								&& notificacionDTO
										.getEstatus()
										.getIdCampo()
										.equals(EstatusNotificacion.NO_ATENDIDA
												.getValorId())) {

							writer.print("<cell>" + "NA" + "</cell>");
						} else {
							writer.print("<cell><![CDATA[<input type='text' readonly='readonly' style='width:40px;'  id='horaRecepcion_"
									+ notificacionDTO.getDocumentoId()
									+ "'>"
									+ "<input type='button' value='Guardar'  style='width:55px;' id='btnConfirmarFechaHora'  onclick='confirmarFechaHoraRecepcion("
									+ notificacionDTO.getDocumentoId()
									+ ")'/>"

									+ "<input type='button' value='NA' style='width:25px;' id='btnNoAplica'  onclick='confirmarNoAplica("
									+ notificacionDTO.getDocumentoId() + ")'/>"

									+ "]]></cell>");
						}
					}

					// OBSERVACIONES
					if (notificacionDTO.getMotivo() != null
							&& !notificacionDTO.getMotivo().isEmpty()) {
						writer.print("<cell><![CDATA[<textarea readonly='readonly' id='textArea_"
								+ notificacionDTO.getDocumentoId()
								+ "'>"
								+ notificacionDTO.getMotivo()
								+ "</textarea>]]></cell>");
					} else {
						writer.print("<cell><![CDATA[<textarea id='textArea_"
								+ notificacionDTO.getDocumentoId()
								+ "'/>]]></cell>");
					}

					// NOMBRE DEL DOCUMENTO
					if (notificacionDTO.getNombreDocumento() != null) {
						writer.print("<cell><![CDATA[<a href='#' title='Ver Documento' onclick='abreDocumento("
								+ notificacionDTO.getDocumentoId()
								+ ","
								+ notificacionDTO.getEsGuardadoParcial()
								+ ")'>"
								+ notificacionDTO.getNombreDocumento()
								+ "</a>]]></cell>");
					} else {
						writer.print("<cell>" + " - " + "</cell>");
					}

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
	 * Metodo utilizado para agregar un destinatario
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward agregarDestinatario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("EJECUTANDO ACTION AGREGAR DESTINATARIO");
			
			String idEventoStr = request.getParameter("idEvento");
			String idExpediente = request.getParameter("idExpediente");
			String nombreDest = request.getParameter("nombreDest");
			String apPatDest = request.getParameter("apPatDest");
			String apMatDest = request.getParameter("apMatDest");
			// String institucionDest = request.getParameter("institucionDest");
			String dirElectDest = request.getParameter("dirElectDest");
			String pais = request.getParameter("pais");
			String codigoPostal = request.getParameter("codigoPostal");
			String entidadFederativa = request.getParameter("entidadFederativa");
			String ciudad = request.getParameter("ciudad");
			String delegacionMunicipio = request.getParameter("delegacionMunicipio");
			String asentamientoColonia = request.getParameter("asentamientoColonia");
			String tipoCalle = request.getParameter("tipoCalle");
			String calle = request.getParameter("calle");
			String numExterior = request.getParameter("numExterior");
			String numInterior = request.getParameter("numInterior");
			String referencias = request.getParameter("referencias");
			String entreCalle = request.getParameter("entreCalle");
			String ycalle = request.getParameter("ycalle");
			String aliasDomicilio = request.getParameter("aliasDomicilio");
			String edificio = request.getParameter("edificio");
			String longitud = request.getParameter("longitud");
			String latitud = request.getParameter("latitud");
		
			//Seteamos el id del expedienta
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setExpedienteId(Long.parseLong(idExpediente));
			
			//SETEAMOS LOS DATOS DE LA PERSONA
				//Para setear la calidad
			CalidadDTO calidadDTO = new CalidadDTO();
				//cambiar la calidad del individuo
			calidadDTO.setCalidades(Calidades.SIN_CALIDAD_JURIDICA);
			
				//Nombre
			NombreDemograficoDTO nombre = new NombreDemograficoDTO();
			
			nombre.setNombre(nombreDest);
			nombre.setApellidoPaterno(apPatDest);
			nombre.setApellidoMaterno(apMatDest);
			
			List<NombreDemograficoDTO> nombresDemograficoDTO = new ArrayList<NombreDemograficoDTO>();
			
			
			nombresDemograficoDTO.add(nombre);
			
			InvolucradoDTO involucradoNuevo = new InvolucradoDTO();
			involucradoNuevo.setCalidadDTO(calidadDTO);
			involucradoNuevo.setNombresDemograficoDTO(nombresDemograficoDTO);
				
				//Fecha de creacion del elemento
			involucradoNuevo.setFechaCreacionElemento(new Date());
			
			//Se agrega la dirección de correo electronico al Involucrado
			List<CorreoElectronicoDTO> llCorreos = new ArrayList<CorreoElectronicoDTO>();
			llCorreos.add(new CorreoElectronicoDTO(dirElectDest));			
			involucradoNuevo.setCorreosDTO(llCorreos);
			
			
			//CUANDO EL PAIS ES MEXICO
			if((Long.parseLong(pais)==10 || pais.equalsIgnoreCase("mexico") || pais.equalsIgnoreCase("méxico")) && (Long.parseLong(pais)!= -1L)){
				
				DomicilioDTO domicilioDTO = new DomicilioDTO();
				
				//parte izquierda de la pantalla ingresar domicilio				
					//entidad federativa
				if(!entidadFederativa.equalsIgnoreCase("")){
					
					if(Long.parseLong(entidadFederativa)!= -1L ){
						EntidadFederativaDTO entidadDTO = new EntidadFederativaDTO();
						entidadDTO.setEntidadFederativaId(Long.parseLong(entidadFederativa));
						domicilioDTO.setEntidadDTO(entidadDTO);
					}
				}
				
					//ciudad
				if(!ciudad.equalsIgnoreCase("")){
					
					if(Long.parseLong(ciudad)!= -1L ){
						CiudadDTO ciudadDTO = new CiudadDTO();
						ciudadDTO.setCiudadId(Long.parseLong(ciudad));
						domicilioDTO.setCiudadDTO(ciudadDTO);
					}
				}
					//delegacion-municipio
				if(!delegacionMunicipio.equalsIgnoreCase("")){
					
					if(Long.parseLong(delegacionMunicipio)!= -1L ){
						MunicipioDTO municipioDTO = new MunicipioDTO();
						municipioDTO.setMunicipioId(Long.parseLong(delegacionMunicipio));
						domicilioDTO.setMunicipioDTO(municipioDTO);
					}
				}
					
					//asentamiento-colonia
				if(!asentamientoColonia.equalsIgnoreCase("")){
					
					if(Long.parseLong(asentamientoColonia)!= -1L ){
						AsentamientoDTO asentamientoDTO = new AsentamientoDTO();
						asentamientoDTO.setAsentamientoId(Long.parseLong(asentamientoColonia));
						domicilioDTO.setAsentamientoDTO(asentamientoDTO);		
					}
				}
					
					//tipo de calle
				if(tipoCalle.equalsIgnoreCase("")){
					
					if(Long.parseLong(tipoCalle) != -1){
						
						ValorDTO valorCalleId = new ValorDTO(Long.parseLong(tipoCalle));
						domicilioDTO.setValorCalleId(valorCalleId);
					}
				}					
				
				//parte derecha de la pantalla ingresar domicilio
				domicilioDTO.setCalle(calle);
				domicilioDTO.setNumeroExterior(numExterior);
				domicilioDTO.setNumeroInterior(numInterior);
				domicilioDTO.setEntreCalle1(entreCalle);
				domicilioDTO.setEntreCalle2(ycalle);
				domicilioDTO.setAlias(aliasDomicilio);
				domicilioDTO.setEdificio(edificio);
				domicilioDTO.setReferencias(referencias);
				
				if(StringUtils.isNotBlank(longitud)){
					domicilioDTO.setLongitud(longitud);
				}
				else{
					domicilioDTO.setLongitud(null);
				}
				if(StringUtils.isNotBlank(latitud)){
					domicilioDTO.setLatitud(latitud);
				}
				else{
					domicilioDTO.setLatitud(null);
				}
				//Seteamos la fecha de creacion del elemento
				domicilioDTO.setFechaCreacionElemento(new Date());
				
				//seteamos el expediente
				domicilioDTO.setExpedienteDTO(expedienteDTO);
				
				//Seteamos la calidad del domicilio
				CalidadDTO calidadDomicilioDTO = new CalidadDTO();
				calidadDomicilioDTO.setCalidades(Calidades.DOMICILIO);
				domicilioDTO.setCalidadDTO(calidadDomicilioDTO);
				
				//Seteamos el id del aexpediente al elemento persona
				involucradoNuevo.setExpedienteDTO(expedienteDTO);
				//Seteamos el domicilio de notificaciones a la persona
				involucradoNuevo.setDomicilioNotificacion(domicilioDTO);				
	
			}
			//CUANDO EL PAIS NO ES MEXICO
			else{
				
				DomicilioExtranjeroDTO domicilioExtranjeroDTO = new DomicilioExtranjeroDTO();
				
				//Parte izq de la pantalla ingresar domicilio
				if(!pais.equalsIgnoreCase("")){
					if(Long.parseLong(pais)!= -1L){
					
							//id del pais
						domicilioExtranjeroDTO.setPais(pais);
					}
				}
				
				domicilioExtranjeroDTO.setCodigoPostal(codigoPostal);
				domicilioExtranjeroDTO.setEstado(entidadFederativa);
				domicilioExtranjeroDTO.setCiudad(ciudad);
				domicilioExtranjeroDTO.setMunicipio(delegacionMunicipio);
				domicilioExtranjeroDTO.setAsentamientoExt(asentamientoColonia);
				
				//parte derecha de la pantalla ingresar domicilio
				domicilioExtranjeroDTO.setCalle(calle);
				domicilioExtranjeroDTO.setNumeroExterior(numExterior);
				domicilioExtranjeroDTO.setNumeroInterior(numInterior);
				domicilioExtranjeroDTO.setEntreCalle1(entreCalle);
				domicilioExtranjeroDTO.setEntreCalle2(ycalle);
				domicilioExtranjeroDTO.setAlias(aliasDomicilio);
				domicilioExtranjeroDTO.setEdificio(edificio);
				domicilioExtranjeroDTO.setReferencias(referencias);
				if(!longitud.equalsIgnoreCase("")){
					domicilioExtranjeroDTO.setLongitud(longitud);
				}
				else{
					domicilioExtranjeroDTO.setLongitud(null);
				}
				if(!latitud.equalsIgnoreCase("")){
					domicilioExtranjeroDTO.setLatitud(latitud);
				}
				else{
					domicilioExtranjeroDTO.setLatitud(null);
				}
				//Seteamos la fecha de creacion del elemento
				domicilioExtranjeroDTO.setFechaCreacionElemento(new Date());
				
				//Seteamos el expediente
				domicilioExtranjeroDTO.setExpedienteDTO(expedienteDTO);
				
				//Seteamos la calidad del domicilio
				CalidadDTO calidadDomicilioExtranjeroDTO = new CalidadDTO();
				calidadDomicilioExtranjeroDTO.setCalidades(Calidades.DOMICILIO);
				domicilioExtranjeroDTO.setCalidadDTO(calidadDomicilioExtranjeroDTO);
				
				involucradoNuevo.setExpedienteDTO(expedienteDTO);
				//Seteamos el domicilio extranjero de notificaciones a la persona
				involucradoNuevo.setDomicilioNotificacion(domicilioExtranjeroDTO);
		}
		
			//Delegate para ingresar
			involucradoNuevo.setElementoId(involucradoDelegate.guardarInvolucrado(involucradoNuevo));
			
			Long idEvento = NumberUtils.toLong(idEventoStr,0L);
			if(idEvento > 0){
				audienciaDelegate.asociarInvolucradoAAudiencia(involucradoNuevo.getElementoId(), idEvento);
			}
			
			String xml = converter.toXML("ok");
			escribir(response, xml,null);
			
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;		
	}
	
	
	public ActionForward consultaMandamientosJudicialesGenerico(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String inicio = request.getParameter("inicio");
		String fin = request.getParameter("fin");

		String numeroExpediente = request.getParameter("numeroExpediente");

		Long estatusMedida = NumberUtils.toLong(
				request.getParameter("estatus"), 0L);

		String cadenaTiposMandamiento = request
				.getParameter(PARAM_TIPO_MANDAMIENTOS);

		Boolean filtroPropios = BooleanUtils.toBoolean(request
				.getParameter(PARAM_FILTRO_PROPIOS));

		if (numeroExpediente != null && numeroExpediente == "") {
			numeroExpediente = null;
		}

		DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		Date fechaInicio = null;
		Date fechaFin = null;

		if (inicio != null && !inicio.equals("")) {
			try {
				fechaInicio = formato.parse(inicio);
			} catch (ParseException e) {
				fechaInicio = null;
			}
		}
		if (fin != null && !fin.equals("")) {
			try {
				fechaFin = formato.parse(fin);
			} catch (ParseException e) {
				fechaFin = null;
			}
		}

		List<Long> idsTipoMandamiento = null;

		if (cadenaTiposMandamiento != null
				&& !cadenaTiposMandamiento.trim().isEmpty()) {
			String[] idsTipos = cadenaTiposMandamiento.split(",");
			if (idsTipos != null && idsTipos.length > 0) {
				idsTipoMandamiento = new ArrayList<Long>();
				for (String idTipo : idsTipos) {
					idsTipoMandamiento.add(NumberUtils.toLong(idTipo,0L));
				}
			}
		}

		try {

			FiltroMandamientoDTO filtroMandamientoDTO = new FiltroMandamientoDTO();

			filtroMandamientoDTO.setFechaInicioBusqueda(fechaInicio);
			filtroMandamientoDTO.setFechaFinBusqueda(fechaFin);

			filtroMandamientoDTO.setNumeroExpediente(numeroExpediente);

			if (filtroPropios) {
				FuncionarioDTO filtroFuncionario = getUsuarioFirmado(request)
						.getFuncionario();
				if (filtroFuncionario != null) {
					filtroMandamientoDTO.setClaveFuncionario(filtroFuncionario
							.getClaveFuncionario());
				}
			}

			if (estatusMedida != 0L) {
				filtroMandamientoDTO.setIdEstatus(estatusMedida);
			}
			
			if(idsTipoMandamiento!=null && !idsTipoMandamiento.isEmpty()){
				filtroMandamientoDTO.setIdsTipoMandamiento(idsTipoMandamiento);
			}

			List<MandamientoDTO> listaMandamientos = administrarMandamientoJudicialDelegate
					.consultarMandamientoPorFiltro(filtroMandamientoDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");

			final PaginacionDTO pag = PaginacionThreadHolder.get();
			if (pag != null && pag.getTotalRegistros() != null) {
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros()
						+ "</records>");
			} else {
				writer.print("<total>0</total>");
				writer.print("<records>0</records>");
			}

			for (MandamientoDTO mandamiento : listaMandamientos) {

				writer.print("<row id='" + mandamiento.getDocumentoId() + "'>");

				writer.print("<cell>"
						+ ((mandamiento != null
								&& mandamiento.getExpedienteDTO() != null
								&& mandamiento.getExpedienteDTO().getCasoDTO() != null && mandamiento
								.getExpedienteDTO().getCasoDTO()
								.getNumeroGeneralCaso() != null) ? mandamiento
								.getExpedienteDTO().getCasoDTO()
								.getNumeroGeneralCaso() : "") + "</cell>");

				writer.print("<cell>"
						+ ((mandamiento != null
								&& mandamiento.getExpedienteDTO() != null && mandamiento
								.getExpedienteDTO().getNumeroExpediente() != null) ? mandamiento
								.getExpedienteDTO().getNumeroExpediente() : "")
						+ "</cell>");

				writer.print("<cell>"
						+ (mandamiento != null ? mandamiento
								.getTipoMandamiento().getValor() : "")
						+ "</cell>");

				writer.print("<cell>"
						+ (mandamiento != null
								&& mandamiento.getFechaCreacion() != null ? DateUtils
								.formatear(mandamiento.getFechaCreacion())
								: "-") + "</cell>");

				writer.print("<cell>"
						+ ((mandamiento != null && mandamiento.getEstatus() != null) ? mandamiento
								.getEstatus().getValor() : "") + "</cell>");

				writer.print("</row>");
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e) {
			log.error(e);
		}

		return null;
	}
	
	@Deprecated
	public ActionForward consultaMandamientoJudicialPJENC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
			try {
				
			Long idMandamientoJudicial = NumberUtils.toLong(request.getParameter("idMandamientoJudicial"),0L);

			if(idMandamientoJudicial != null && idMandamientoJudicial > 0){
				MandamientoDTO loMandamiento= mandamientoJudicialDelegate.consultarMandamientoPorId(idMandamientoJudicial);

				converter.alias("mandamientoJudicial", MandamientoDTO.class);
				converter.alias("involucrado", InvolucradoDTO.class);
				converter.alias("nombresDemograficoDTO", NombreDemograficoDTO.class);
				
				String xml = converter.toXML(loMandamiento);
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
}

package mx.gob.segob.nsjp.web.defensor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.audiencia.EstatusAudiencia;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class AudienciasDefensorAction extends GenericAction {
	
	private static final Logger log = Logger.getLogger(AdministrarDefensaEjecucionAction.class);
	
	@Autowired
	private AudienciaDelegate audienciaDelegate;
	
	@Autowired
	private ConfiguracionDelegate configuracionDelegate;
	
	public ActionForward consultarDetalleAudienciaDefensoria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			Long idAudiencia = NumberUtils.toLong(request.getParameter("idAudiencia"), -1);
			AudienciaDTO audiencia = new AudienciaDTO();
			audiencia.setId(idAudiencia);
			audiencia = audienciaDelegate.consultarAudienciaFromPoderJudicial(audiencia);
			
			String xml = null;
			PrintWriter pw = null;
			XStream converter=new XStream();
			converter.alias("audiencia", AudienciaDTO.class);
			converter.alias("involucrado", InvolucradoViewDTO.class);
			xml = converter.toXML(audiencia);
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
	 * Metodo para consultar las audiencias de la institucion poder judicial de
	 * acuerdo al distrito del usuario firmado y la fecha de inicio de la
	 * audiencia
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarAudienciasDefensor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			Long claveFuncionarioExt = 0L;
			String cadenaEstatus = "";
			String cadenaTipo = "";
			Long confInstId = 0L;

			UsuarioDTO usuarioDTO = (UsuarioDTO) request.getSession()
					.getAttribute(GenericAction.KEY_SESSION_USUARIO_FIRMADO);

			if (usuarioDTO != null
					&& usuarioDTO.getFuncionario() != null
					&& usuarioDTO.getFuncionario().getClaveFuncionario() != null) {

				//Cuando es coordinador no se debe filtrar por clave funcionario
				if (usuarioDTO.getRolACtivo() != null
						&& usuarioDTO.getRolACtivo().getRol() != null
						&& usuarioDTO.getRolACtivo().getRol().getRolId() != null
						&& usuarioDTO.getRolACtivo().getRol().getRolId() > 0L
						&& !usuarioDTO.getRolACtivo().getRol().getRolId()
								.equals(Roles.COORDINADORDEF.getValorId()) 
						&& !usuarioDTO.getRolACtivo().getRol().getRolId()
						.equals(Roles.SUBPROCURADOR.getValorId())
						&& !usuarioDTO.getRolACtivo().getRol().getRolId()
						.equals(Roles.VISITADOR.getValorId())
					) {

					claveFuncionarioExt = usuarioDTO.getFuncionario()
							.getClaveFuncionario();
				}
			}

			cadenaEstatus = EstatusSolicitud.ABIERTA.getValorId().toString()
					+ "," + EstatusSolicitud.CERRADA.getValorId().toString()
					+ "," + EstatusSolicitud.EN_PROCESO.getValorId().toString();

			cadenaTipo = TiposSolicitudes.AUDIO_VIDEO.getValorId().toString();

			ConfInstitucionDTO confInstDto = configuracionDelegate
					.consultarInstitucionActual();

			if (confInstDto != null
					&& confInstDto.getConfInstitucionId() != null) {
				confInstId = confInstDto.getConfInstitucionId();
			}

			String fechaInicio = request.getParameter("inicio");
			String fechaFin = request.getParameter("fin");
			String desarrolloJAVS = request.getParameter("desarrolloJAVS");
			Boolean porSemana = Boolean.parseBoolean(request.getParameter("porSemana"));

			log.info("ejecuntando consultarAudienciasDefensor....");
			Calendar calendario = Calendar.getInstance();
			
			AudienciaDTO audiencia = new AudienciaDTO();
			audiencia.setEstatusAudiencia(new ValorDTO(
					EstatusAudiencia.PROGRAMADA.getValorId()));

			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaCreacionInicio = null;
			Date fechaCreacionFin = null;

			//Filtro para audiencias por semana
			if(porSemana.equals(true)){
				Calendar c1 = Calendar.getInstance(); 
				Calendar c2 = Calendar.getInstance();
				
				c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				c2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
				audiencia.setFechaFiltroInicio(c1.getTime());
				audiencia.setFechaFiltroFin(c2.getTime());
				
			}else{
				if (fechaInicio == null || fechaInicio == "") {
					audiencia.setFechaFiltroInicio(calendario.getTime());
				} else {
					fechaCreacionInicio = formato.parse(fechaInicio);
					audiencia.setFechaFiltroInicio(fechaCreacionInicio);
				}
				
				if (fechaFin == null || fechaInicio == "") {
					audiencia.setFechaFiltroFin(calendario.getTime());
				} else {
					fechaCreacionFin = formato.parse(fechaFin);
					audiencia.setFechaFiltroFin(fechaCreacionFin);
				}
			}
			

			// Se obtiene el distrito del usuario firmado en session
			UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
			
			if (usuarioFirmado.getFuncionario() != null
					&& usuarioFirmado.getFuncionario().getDiscriminante() != null
					&& usuarioFirmado.getFuncionario().getDiscriminante()
							.getDistrito() != null
					&& usuarioFirmado.getFuncionario().getDiscriminante()
							.getDistrito().getCatDistritoId() != null) {
				
				log.info("El Distrito del usuario firmado en sesion es: "
						+ usuarioFirmado.getFuncionario().getDiscriminante()
								.getDistrito().getCatDistritoId());
				
				audiencia.setIdDistritoFiltroAudiencias(usuarioFirmado
						.getFuncionario().getDiscriminante().getDistrito()
						.getCatDistritoId());
			}

			List<AudienciaDTO> audienciaDTOs = audienciaDelegate
					.consultarAudienciasFromPoderJudicial(audiencia,
							claveFuncionarioExt, cadenaEstatus, cadenaTipo,
							confInstId);

			log.info("Audiencias " + audienciaDTOs);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			String caso = " - ", tipo = " - ", caracter = " - ";
			String fecha = " - ", sala = " - ";

			for (AudienciaDTO audienciaDTO : audienciaDTOs) {

				if (audienciaDTO.getExpediente() != null
						&& audienciaDTO.getExpediente().getCasoDTO() != null
						&& audienciaDTO.getExpediente().getCasoDTO()
								.getNumeroGeneralCaso() != null) {

					caso = audienciaDTO.getExpediente().getCasoDTO()
							.getNumeroGeneralCaso();
				}
				
				if (audienciaDTO.getCaracter() != null) {
					caracter = audienciaDTO.getCaracter();
				}
				
				if (audienciaDTO.getTipoAudiencia() != null
						&& audienciaDTO.getTipoAudiencia().getValor() != null) {
					tipo = audienciaDTO.getTipoAudiencia().getValor();
				}

				if (audienciaDTO.getStrHoraEvento() != null
						&& audienciaDTO.getStrFechaEvento() != null) {
					fecha = audienciaDTO.getStrFechaEvento() + " - "
							+ audienciaDTO.getStrHoraEvento();
				}

				if (audienciaDTO.getSala() != null
						&& audienciaDTO.getSala().getNombreSala() != null) {
					sala = audienciaDTO.getSala().getNombreSala();
				}
				
				writer.print("<row id='" + audienciaDTO.getId() + "'>");
				
				if (desarrolloJAVS != null && desarrolloJAVS.equals("TRUE")) {
					writer.print("<cell>" + audienciaDTO.getId() + "</cell>");
				}
				
				writer.print("<cell>" + caso + "</cell>");
				writer.print("<cell>" + caracter + "</cell>");
				writer.print("<cell>" + tipo + "</cell>");
				writer.print("<cell>" + fecha + "</cell>");
				writer.print("<cell>" + sala + "</cell>");
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
	
}

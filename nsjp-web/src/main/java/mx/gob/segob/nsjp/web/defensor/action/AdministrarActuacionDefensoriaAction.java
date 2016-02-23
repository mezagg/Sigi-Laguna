package mx.gob.segob.nsjp.web.defensor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.dto.catalogo.CatEtapaDTO;
import mx.gob.segob.nsjp.dto.documento.AvisoDesignacionDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class AdministrarActuacionDefensoriaAction extends GenericAction {
	
	private static final Logger log = Logger.getLogger(AdministrarActuacionDefensoriaAction.class);
	
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	
	public ActionForward consultarExpedientesUsuarioArea(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			List<ExpedienteDTO> expedienteDTOs = new ArrayList<ExpedienteDTO>();
			expedienteDTOs = expedienteDelegate.consultarExpedientesUsuarioArea(usuarioDTO);			
			XStream converter=new XStream();
			converter.alias("lista", java.util.List.class);
			converter.alias("expedienteDTOs", ExpedienteDTO.class);
			String xml = converter.toXML(expedienteDTOs);
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
	
	
	public ActionForward consultarMenuEtapasExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			log.info("ejecutando AdministrarActuacionDefensoriaAction: -  consultarMenuEtapasExpediente");
			
			
			Boolean esEtapaExpediente = true;
			
			List<CatEtapaDTO> catEtapasDTO = expedienteDelegate.consultarEtapasJerarquiaPorPadre(esEtapaExpediente );

			XStream converter=new XStream();
			converter.alias("listaCatEtapas", java.util.List.class);
			converter.alias("catEtapa", CatEtapaDTO.class);
			
			String xml = converter.toXML(catEtapasDTO);
			escribirRespuesta(response,xml);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			escribirError(response, null);
		}		
		return null;
	}

	public ActionForward consultarExpedientesEtapa(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("Inicia Action actuacion consulta por etapa y usuario");
			List<ExpedienteDTO> expedienteDTOs = new ArrayList<ExpedienteDTO>();
			Long etapa = new Long(request.getParameter("etapa"));
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			expedienteDTOs = expedienteDelegate.consultarExpedientesPorUsuarioYEtapa(usuarioDTO, etapa);
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");		
			
			//Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
									
			for(ExpedienteDTO expedienteDTO : expedienteDTOs){			
				writer.print("<row id='"+expedienteDTO.getNumeroExpedienteId()+"'>");
				
				//TODO GPB Revisar cuando se hace la consulta de asesoria
				if(expedienteDTO.getEtapa().getIdCampo().longValue()!= EtapasExpediente.ASESORIA.getValorId().longValue()){
					if(expedienteDTO.getCasoDTO() != null){//FIXME ELIMINAR ESTE IF, EL EXPEDIENTE SIEMPRE DEBE DE TRAER EL CASO EN ESTE MOMENTO
						writer.print("<cell> "+expedienteDTO.getCasoDTO().getNumeroGeneralCaso()+" </cell>");
					}else{
						writer.print("<cell> - </cell>");
					}
					
					writer.print("<cell> "+expedienteDTO.getNumeroExpediente()+" </cell>");
					
					//Obtener los defendidos
					List<InvolucradoDTO> imputados = expedienteDTO.getInvolucradoByCalidad(Calidades.DEFENDIDO);
					String nombreImput= "";
					// Combo box defendidos
					if (imputados != null && !imputados.isEmpty()) {
						nombreImput = "<![CDATA["
								+ "<select id='cbxDefendidosExpedientes' style='width: 300px;'>";
						for (InvolucradoDTO involucrado : imputados) {
							nombreImput = nombreImput + "<option>"
									+ involucrado.getNombreCompleto()
									+ "</option>";
						}
						nombreImput = nombreImput + "</select>" + "]]>";
					} else {
						nombreImput = "<cell> - </cell>";
					}
					writer.print("<cell> "+nombreImput+" </cell>");
					
					if (expedienteDTO.getEtapa() != null
							&& expedienteDTO.getEtapa().getValor() != null
							&& !expedienteDTO.getEtapa().getValor().trim().isEmpty()) {
						writer.print("<cell> "
								+ expedienteDTO.getEtapa().getValor().trim() + " </cell>");
					}else{
						writer.print("<cell> - </cell>");
					}
					
					if (expedienteDTO.getEstatusNumeroExpediente()!= null
							&& expedienteDTO.getEstatusNumeroExpediente().getValor() != null
							&& !expedienteDTO.getEstatusNumeroExpediente().getValor().trim().isEmpty()) {
						writer.print("<cell> "
								+ expedienteDTO.getEstatusNumeroExpediente().getValor().trim() + " </cell>");
					}else{
						writer.print("<cell> - </cell>");
					}
				}else{
					log.info("entra asesoria");
					writer.print("<cell> "+expedienteDTO.getNumeroExpediente()+" </cell>");
					
					List<InvolucradoDTO> imputados = expedienteDTO.getInvolucradoByCalidad(Calidades.DEFENDIDO);
					InvolucradoDTO imputado = null;
					if(imputados != null && !imputados.isEmpty()){
						imputado = imputados.get(0);
						writer.print("<cell> "+imputado.getNombreCompleto()+" </cell>");
					}
					else{
						writer.print("<cell> - </cell>");

					}
					
					Date fecha = null;
					AvisoDesignacionDTO aviso = null;
					for(AvisoDesignacionDTO avisoD : expedienteDTO.getAvisosDesignacion()){
						aviso = avisoD;
					}
					fecha = aviso.getFechaCreacion();
					writer.print("<cell>"+ DateUtils.formatear(fecha)+" - "+DateUtils.formatearHora(fecha)   + "</cell>");
									
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
	
	
	public ActionForward consultarExpedientesPorEtapaDefensoria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("Inicia Action Consultar Expedientes por etapa y usuario");
			List<ExpedienteDTO> expedienteDTOs = new ArrayList<ExpedienteDTO>();
			Long etapaValorId = new Long(request.getParameter("etapaValorId"));
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			
			expedienteDTOs = expedienteDelegate.consultarExpedientesPorEtapaDefensoria(usuarioDTO, etapaValorId);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");		
			
			//Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
									
			for(ExpedienteDTO expedienteDTO : expedienteDTOs){			
				writer.print("<row id='"+expedienteDTO.getNumeroExpedienteId()+"'>");
				writer.print("<cell> "+expedienteDTO.getCasoDTO().getNumeroGeneralCaso()+" </cell>");
				writer.print("<cell> "+expedienteDTO.getNumeroExpediente()+" </cell>");

				//Obtener los defendidos
				List<InvolucradoDTO> imputados = expedienteDTO.getInvolucradoByCalidad(Calidades.DEFENDIDO);
				String nombreImput= "";
				// Combo box defendidos
				if (imputados != null && !imputados.isEmpty()) {
					nombreImput = "<![CDATA["
							+ "<select id='cbxDefendidosExpedientes' style='width: 300px;'>";
					for (InvolucradoDTO involucrado : imputados) {
						nombreImput = nombreImput + "<option>"
								+ involucrado.getNombreCompleto()
								+ "</option>";
					}
					nombreImput = nombreImput + "</select>" + "]]>";
				} else {
					nombreImput = "<cell> - </cell>";
				}
				writer.print("<cell> "+nombreImput+" </cell>");

				if (expedienteDTO.getCatEtapaDTO()!= null
						&& expedienteDTO.getCatEtapaDTO().getCatEtapaValor() != null
						&& expedienteDTO.getCatEtapaDTO().getCatEtapaValor().getValor()!= null
						&& !expedienteDTO.getCatEtapaDTO().getCatEtapaValor().getValor().trim().isEmpty()) {
					writer.print("<cell> "
							+ expedienteDTO.getCatEtapaDTO().getCatEtapaValor().getValor().trim() + " </cell>");
				}else{
					writer.print("<cell> - </cell>");
				}
				
				if (expedienteDTO.getEstatusNumeroExpediente()!= null
						&& expedienteDTO.getEstatusNumeroExpediente().getValor() != null
						&& !expedienteDTO.getEstatusNumeroExpediente().getValor().trim().isEmpty()) {
					writer.print("<cell> "
							+ expedienteDTO.getEstatusNumeroExpediente().getValor().trim() + " </cell>");
				}else{
					writer.print("<cell> - </cell>");
				}
				
				if (expedienteDTO.getExpedienteId()!= null) {
					writer.print("<cell>"
							+ expedienteDTO.getExpedienteId() + "</cell>");
				}else{
					writer.print("<cell> - </cell>");
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
}

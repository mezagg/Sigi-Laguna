/**
 * 
 */
package mx.gob.segob.nsjp.web.defensor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author EdgarAG
 *
 */
public class DefensaTecnicaAction extends GenericAction {
	
	private static final Logger log = Logger.getLogger(AdministrarActuacionDefensoriaAction.class);
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	@Autowired
	private SolicitudDelegate solicitudDelegate;
	
	public ActionForward consultarDefensaTecnica(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			List<ExpedienteDTO> expedienteDTOs = new ArrayList<ExpedienteDTO>();
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			//usuarioDTO.setIdUsuario(1L);
			expedienteDTOs = expedienteDelegate.consultarExpedientesPorUsuarioYEtapa(usuarioDTO, EtapasExpediente.TECNICA.getValorId());
			
			log.info("DEFENSORES Tareas");
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");		
			int lTotalRegistros = expedienteDTOs.size();
			writer.print("<records>" + lTotalRegistros + "</records>");
			//NSJYUCPROC2011333WT								

			for(ExpedienteDTO expedienteDTO : expedienteDTOs){
				//for(InvolucradoDTO involucradoDTO : expedienteDTO.addInvolucradoDTO(involucradoDTO.getExpedienteDTO().getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA) )){
				
				List<InvolucradoDTO> probablesResponsables = expedienteDTO.getInvolucradoByCalidad(Calidades.PROBABLE_RESPONSABLE_PERSONA);
				 log.info("NumeroExpediente :: " + expedienteDTO.getNumeroExpediente() +" ::,DEFENSORES Tareas res :::"+ probablesResponsables);
								
				 	writer.print("<row id='"+expedienteDTO.getNumeroExpedienteId()+"-"+expedienteDTO.getEtapa().getIdCampo()+ "-" + expedienteDTO.getEtapa().getValor() + "'>"); 
					writer.print("<cell>");
					if(expedienteDTO.getCasoDTO()!=null && expedienteDTO.getCasoDTO().getNumeroGeneralCaso()!= null){
						writer.print(expedienteDTO.getCasoDTO().getNumeroGeneralCaso());
							}else{
								writer.print("-");
							}
					writer.print("</cell>");
														
					writer.print("<cell>");
					if(expedienteDTO.getNumeroExpediente()!=null){
						writer.print(expedienteDTO.getNumeroExpediente());
					}else{
						writer.print("-");
					}
					writer.print("</cell>");
					
					writer.print("<cell>");
					
					for (InvolucradoDTO involucradoDTO : probablesResponsables) {
					if(involucradoDTO.getNombreCompleto()!=null){
						
					writer.print(involucradoDTO.getNombreCompleto());
						}
					}
				    writer.print("</cell>");
				    
				    writer.print("<cell>");					   
					if(expedienteDTO.getDelitoPrincipal()!=null){							
					writer.print(expedienteDTO.getDelitoPrincipal());						
					}
				    writer.print("</cell>");
				    
				    writer.print("<cell>");					   
					//if(caraceter de la audiencia!=null){							
					writer.print("");						
					//}
				    writer.print("</cell>");		
				    
				    writer.print("<cell>");					   
					//if(fecha hora de la audiencia!=null){							
					writer.print("");						
					//}
				    writer.print("</cell>");
				    
				    writer.print("<cell>");					   
					if(expedienteDTO.getStrFechaApertura()!=null && expedienteDTO.getStrHoraApertura()!=null){							
					writer.print(expedienteDTO.getStrFechaApertura()+"-"+ expedienteDTO.getStrHoraApertura());						
					}
				    writer.print("</cell>");					
				   				
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
	 * M�todo para colocar el expediente en sesi�n del expediente de defensa tecnica
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward colocarExpedienteParaDefensaTecnica(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String numeroExpediente = request.getParameter("numExpediente");
		ExpedienteDTO expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
		setExpedienteTrabajo(request, expedienteDTO);
		XStream converter=new XStream();
		escribirRespuesta(response, converter.toXML(expedienteDTO));
		return null;
		
	}

	/**
	 * Realiza una consulta de la carpeta de investigaci�n para el expediente
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward solictarCarpetaInvestigacion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String xml = "";
		try{
			Long idNumeroExpediente = NumberUtils.toLong(
					request.getParameter("numeroExpedienteId"), 0L);
			if(idNumeroExpediente.equals(0L)){
				log.error("No se ha podido recuperar el idNumeroExpediente==="+idNumeroExpediente);
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
			log.info("idNumeroExpediente- "+idNumeroExpediente);
			UsuarioDTO usuario = getUsuarioFirmado(request);
			log.info("usuario- "+usuario);
			SolicitudDTO sol = solicitudDelegate.registrarSolicitudCarpetaInvestigacion(idNumeroExpediente, usuario);
			CasoDTO caso=new CasoDTO();
			caso.setNumeroGeneralCaso(sol.getNumeroCasoAsociado());
			ExpedienteDTO expe=expedienteDelegate.consultarExpedientePorNumeroDeCaso(caso, usuario);
			HttpSession ses = request.getSession();
			ses.setAttribute("ExpedienteJor",expe);
			XStream converter=new XStream(); 			converter.alias("solicitud", SolicitudDTO.class);
			xml = converter.toXML(sol);
			escribir(response, xml, null);
		}catch(NSJPNegocioException nsje){
			String msj = "";
			if (nsje.getCodigo() == CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO) {
				msj = "Ya se ha solicitado la carpeta de investigaci�n para este expediente";
			} else if (nsje.getCodigo() == CodigoError.ERROR_COMUNICACION) {
				msj = "No se pudo enviar la solicitud por problemas de comunicaci�n";
			} else if (nsje.getCodigo() == CodigoError.INFORMACION_PARAMETROS_ERRONEA) {
				msj = "No se encontraron probables responsables para los cuales solicitar la carpeta de investigaci�n";
			}  else if (nsje.getCodigo() == CodigoError.PARAMETROS_INSUFICIENTES) {
				msj = "No se ha podido realizar esta operaci&oacute;n, faltan parametros";
			}
			
			escribir(response, msj, nsje);
		} 
		return null;
	}
	
	/**
	 * Realiza una consulta de las solicitudes asociadas a un expediente
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 */
	public ActionForward solicitudCarpetaInvesticacionAtendida(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			
			Long idNumeroExpediente = new Long(request.getParameter("numeroExpedienteId"));
			List<SolicitudDTO> solicitudDTOs =null;
			Long carpeta = 0L;
			solicitudDTOs = solicitudDelegate.consultarSolicitudesPorNumeroExpedienteYTipo(idNumeroExpediente,TiposSolicitudes.CARPETA_DE_INVESTIGACION);
			if(!solicitudDTOs.isEmpty() &&  solicitudDTOs.size() == 1){
				carpeta = solicitudDTOs.get(0).getEstatus().getIdCampo().longValue();
			}
			XStream converter=new XStream();
			String xml = converter.toXML(carpeta);
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
		
}

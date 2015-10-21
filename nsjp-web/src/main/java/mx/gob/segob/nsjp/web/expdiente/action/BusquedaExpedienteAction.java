package mx.gob.segob.nsjp.web.expdiente.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.caso.CasoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.caso.form.BuscarCasosForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class BusquedaExpedienteAction extends GenericAction{
	/* Log de clase*/
	private static final Logger log  = Logger.getLogger(BusquedaExpedienteAction.class);
	
	@Autowired
	public ExpedienteDelegate expedienteDelegate;

	@Autowired
	public CasoDelegate casoDelegate;

	/**
	 * Metodo utilizado para guardar los datos de un individuo dependiendo su calidad 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward busquedaCasos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		log.info("EJECUTANDO ACTION BUSQUEDA CASOS");
		BuscarCasosForm buscarCasosForm =(BuscarCasosForm) form;
		log.info("buscando caso:" + buscarCasosForm.getIdCaso());
		
		CasoDTO casoDTO = new CasoDTO();
		casoDTO.setCasoId(buscarCasosForm.getIdCaso());
		
		String tipoRespuesta = request.getParameter("tipoRespuesta");
		log.info("TIPO DE RESPUESTA SOLICITADA ACTION BUSQUEDA CASOS::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+tipoRespuesta);
		
		try {
			if(tipoRespuesta != null && Long.parseLong(tipoRespuesta)== 1L){
				
				UsuarioDTO usr = super.getUsuarioFirmado(request);
				usr.setAreaActual(new AreaDTO(Areas.ATENCION_TEMPRANA_PJ));
				casoDTO.setUsuario(usr);
				List<ExpedienteDTO> listaExpediente = expedienteDelegate.consultarExpedientesConEventosHistorico(casoDTO.getCasoId(), usr);
				converter.alias("expedientes", java.util.List.class);
				converter.alias("expediente", ExpedienteDTO.class);
				response.setContentType("text/xml");
				escribirRespuesta(response, converter.toXML(listaExpediente));
			}
			else{
				
				UsuarioDTO usr = super.getUsuarioFirmado(request);
				usr.setAreaActual(new AreaDTO(Areas.ATENCION_TEMPRANA_PJ));
				casoDTO.setUsuario(usr);
				List<ExpedienteDTO> listaExpediente = expedienteDelegate.consultarExpedientesPorCaso(casoDTO);
				converter.alias("expedientes", java.util.List.class);
				converter.alias("expediente", ExpedienteDTO.class);
				response.setContentType("text/xml");
				escribirRespuesta(response, converter.toXML(listaExpediente));
				
			}
		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.error(e);
		}
		return null;
	}
	
	public ActionForward busquedaInicialCasos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		log.info("EJECUTANDO ACTION BUSQUEDA INICIAL CASOS");
		BuscarCasosForm buscarCasosForm =(BuscarCasosForm) form;
		log.info("buscando caso:" + buscarCasosForm.getIdCaso());
		
		UsuarioDTO usuarioDTO =  getUsuarioFirmado(request);
		
		String tipoRespuesta = request.getParameter("tipoRespuesta");
		log.info("TIPO DE RESPUESTA SOLICITADA ACTION BUSQUEDA INICIAL CASOS::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+tipoRespuesta);
		
		try {
			if(tipoRespuesta != null && Long.parseLong(tipoRespuesta)== 1L){
				
				List<CasoDTO> listaCasos = casoDelegate.consultarCasosConEventosHistoricos(usuarioDTO);
				converter.alias("casos", java.util.List.class);
				converter.alias("caso", CasoDTO.class);
				response.setContentType("text/xml");
				escribirRespuesta(response, converter.toXML(listaCasos));
			}
			else{
				List<CasoDTO> listaCasos = casoDelegate.consultarCasosPorUsuario(usuarioDTO);
				converter.alias("casos", java.util.List.class);
				converter.alias("caso", CasoDTO.class);
				response.setContentType("text/xml");
				escribirRespuesta(response, converter.toXML(listaCasos));
			}
			
		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.error(e);
		}
		return null;
	}

	public ActionForward busquedaInicialCasosGrid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			log.info("ejecutando Action BusquedaExpedienteAction en metodo busquedaInicialCasosGrid");
			try {
				UsuarioDTO usuarioDTO =  getUsuarioFirmado(request);
				log.info("ejecutando Action BusquedaExpedienteAction en metodo busquedaInicialCasosGrid:#####"+casoDelegate);
				List<CasoDTO> listaCasos = casoDelegate.consultarCasosPorUsuario(usuarioDTO);
				if (log.isDebugEnabled()) {
					log.debug("##################lista de Casos:::::::::" + listaCasos.size());
				}
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
			
				writer.print("<rows>");
				int lTotalRegistros=listaCasos.size();
				writer.print("<records>" + lTotalRegistros + "</records>");			
				
				for (CasoDTO casoDTO : listaCasos) {
					writer.print("<row id='"+casoDTO.getCasoId()+"'>");
					writer.print("<cell> <![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>" + casoDTO.getNumeroGeneralCaso()+ " </div]]></cell>");
					writer.print("<cell> <![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>" + "13/05/2011"+ " </div]]></cell>");
//					writer.print("<cell> <![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>" + casoDTO.getFechaApertura()+ " </div]]></cell>");
					writer.print("<cell> <![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>" + "Arturo Le�n Galicia"+ " </div]]></cell>");
//					writer.print("<cell> <![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>" + casoDTO.getImputado()+ " </div]]></cell>");
//					writer.print("<cell> <![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>" + casoDTO.getDelito()+ " </div]]></cell>");
					writer.print("<cell> <![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>" + "Violaci�n"+ " </div]]></cell>");
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
	 * Llena el �rbol de cusas del m�dulo de Poder Judicial 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward busquedaInicialCausasPJ(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		//Definir la fecha de inicio: 1 a�o atr�s
		//TODO revisar criterios
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-365);
		try {
				List<ExpedienteDTO> listaCausas = expedienteDelegate.consultarExpedientesPorFiltro
				(cal.getTime(), null, null, TipoExpediente.CAUSA, null);
				converter.alias("expedientes", java.util.List.class);
				converter.alias("expedienteDTO", ExpedienteDTO.class);
				response.setContentType("text/xml");
				escribirRespuesta(response, converter.toXML(listaCausas));
			
			
		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.error(e);
		}
		return null;
	}
	
	
	/**
	 * Llena las sub-ramas del �rbol de causas en base a un numero de causa (expediente padre)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward busquedaNumeroExpedientesPorCausaPJ(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Calendar cal = Calendar.getInstance();
		//TODO revisar criterio de fecha hacia atr�s
		cal.add(Calendar.DATE,-365);
		Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"));
		try {
				if(numeroExpedienteId >0){
					List<ExpedienteDTO> listaCausas = expedienteDelegate.consultarExpedientesPorFiltro
					(cal.getTime(), null, null, TipoExpediente.TOCA, numeroExpedienteId);
					converter.alias("expedientes", java.util.List.class);
					converter.alias("expedienteDTO", ExpedienteDTO.class);
					response.setContentType("text/xml");
					escribirRespuesta(response, converter.toXML(listaCausas));
				}
				
			
			
		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.error(e);
		}
		return null;
		
	}
	
	/**
	 * Valida si existe el numero de causa
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward validarNumeroDeCausa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		String numeroExpediente =request.getParameter("numeroCausa");
		log.info("TIPO DE RESPUESTA SOLICITADA ACTION BUSQUEDA CASOS::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+numeroExpediente);
		try {
			ExpedienteDTO expediente = new ExpedienteDTO();
			expediente.setNumeroExpediente(numeroExpediente);
			UsuarioDTO usuario = getUsuarioFirmado(request);
			expediente = expedienteDelegate.obtenerNumeroExpedienteByNumExp(expediente,usuario);
			converter.alias("causa", ExpedienteDTO.class);
			response.setContentType("text/xml");
			escribirRespuesta(response, converter.toXML(expediente));

		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.error(e);
		}
		return null;
		
	}
	
	/**
	 * M&eacute;todo para consultar un expediente por el numero de caso y el
	 * discriminante del usuario firmado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNumeroDeCaso(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		log.info("buscarExpedientePorNumeroDeCaso::::::::::::::::::::::");

		String numeroDeCaso = request.getParameter("numeroDeCaso");
		log.info("numeroDeCaso=" + numeroDeCaso);
		try {

			CasoDTO casoDto = new CasoDTO();
			casoDto.setNumeroGeneralCaso(numeroDeCaso);

			UsuarioDTO usuarioDto = super.getUsuarioFirmado(request);

			ExpedienteDTO expedienteDto = expedienteDelegate
					.consultarExpedientePorNumeroDeCaso(casoDto, usuarioDto);

			converter.alias("ExpedienteDTO", ExpedienteDTO.class);
			response.setContentType("text/xml");
			escribirRespuesta(response, converter.toXML(expedienteDto));
			
		} catch (NSJPNegocioException e) {
			escribirError(response, e);
			log.error(e);
		}
		return null;
	}
	
	
	public ActionForward actualizarCatUIEDeExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			try {
				Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"),0);
				log.info("idExpediente:"+idExpediente);
				
				Long idCatUIE = NumberUtils.toLong(request.getParameter("idCatUIE"),0);
				log.info("idCatUIE:"+idCatUIE);
				
				ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
				loExpedienteDTO.setExpedienteId(idExpediente);
				loExpedienteDTO.setCatUIE(idCatUIE);
				
				
				expedienteDelegate.actualizarCatUIEDeExpediente(loExpedienteDTO);

				String xml = "<respuesta><bandera>1</bandera></respuesta>";
				if(log.isDebugEnabled())
				{
					log.info(xml);
				}
				escribirRespuesta(response, xml);
			} catch (Exception e) {		
				log.error(e.getMessage(),e);
				String xml = "<respuesta><bandera>0</bandera></respuesta>";
				escribirRespuesta(response, xml);
			}
			return null;
	}
	
	public ActionForward realizarConsultaCiudadana(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
			String expediente = request.getParameter("numeroExpediente").toLowerCase();
			String nombre = request.getParameter("nombre");
			String apaterno = request.getParameter("apellidoPaterno");
			String amaterno = request.getParameter("apellidoMaterno");
			
			try {
					List<ExpedienteViewDTO> listaExpedientes = expedienteDelegate.consultaCiudadana(apaterno, amaterno, nombre, expediente);
					converter.alias("expedientes", java.util.List.class);
					converter.alias("ExpedienteViewDTO", ExpedienteViewDTO.class);
					response.setContentType("text/xml");
					escribirRespuesta(response, converter.toXML(listaExpedientes));
			} catch (NSJPNegocioException e) {
				escribirError(response, e);
				log.error(e);
			}
		return null;
	}
}

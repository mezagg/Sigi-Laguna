/**
* Nombre del Programa 	: BuscarExpedientePorAliasAction.java                                    
* Autor               	: Andres Gomez Godinez                                              
* Compania            	: Ultrasist                                                
* Proyecto            	: NSJP              			Fecha:09/03/2011 
* Marca de cambio     	: N/A                                                     
* Descripcion General   : Clase action que implementa las acciones para el CU Buscar Expediente Por Alias
* Programa Dependiente  : N/A                                                      
* Programa Subsecuente 	: N/A                                                      
* Cond. de ejecucion    : struts-config.xml                                                    
* Dias de ejecucion     : N/A                             Horario: N/A
*                               MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                 :N/A                                                           
* Compania              :N/A                                                           
* Proyecto              :N/A                                   Fecha: N/A       
* Modificacion          :N/A                                                           
*------------------------------------------------------------------------------      
*/
package mx.gob.segob.nsjp.web.expdiente.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.delito.DelitoDelegate;
import mx.gob.segob.nsjp.delegate.detencion.DetencionDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.expediente.HistoricoExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDelitoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.BitacoraEstatusNumExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.HistoricoExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.involucrado.DetencionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.expediente.form.BuscarExpedientePorAliasForm;
import mx.gob.segob.nsjp.web.expediente.form.BuscarExpedientePorNombreDePersonaForm;
import mx.gob.segob.nsjp.web.funcionario.form.FuncionarioForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *Clase que implementa las acciones para el CU Buscar Expediente Por Alias
 * @version 1.0
 * @author AndresGG
 */

public class BuscarExpedienteAction extends GenericAction{
	private static final Logger log  = Logger.getLogger(BuscarExpedienteAction.class);
		
	@Autowired
	ExpedienteDelegate expedienteDelegate;
	@Autowired
	FuncionarioDelegate funcionarioDelegate;
	@Autowired
	DelitoDelegate delitoDelegate;
	@Autowired
	HistoricoExpedienteDelegate historicoExpedienteDelegate;
	@Autowired
	AsignacionProgramaDelegate asignacionProgramaDelegate;
	@Autowired
	DetencionDelegate detencionDelegate;
	@Autowired
	private SolicitudDelegate solicitudDelegate;
	
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNumeroDeExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
 throws IOException {

		try {

			String noExpediente = request.getParameter("noExpediente");
			String reasignarExpediente = request.getParameter("reasignarExpediente");
			String opcionUI = request.getParameter("opcion");
			Long esCordinadorGeneralAMP = NumberUtils.toLong(request.getParameter("esCordinadorGeneralAMP"),0L);
			if(opcionUI!=null && opcionUI.equals("6")){
				opcionUI=Areas.UNIDAD_INVESTIGACION.parseLong().toString();
			}else{
				opcionUI=null;
			}
			log.info("opcion:"+opcionUI);
			Boolean verHistorico = request.getParameter("verHistorico")!= null ? Boolean.parseBoolean(request.getParameter("verHistorico")): Boolean.FALSE;
			if (log.isDebugEnabled()) {
				log.debug("##################llega noExpediente:::::::::"
						+ noExpediente);
			}
			FiltroExpedienteDTO filtrosBusquedaExpediente = new FiltroExpedienteDTO();
			if(opcionUI!=null){
				filtrosBusquedaExpediente.setFiltroEspecificoDeAreaRolActual(Long.parseLong(opcionUI));
			}
			
			
			UsuarioDTO usuario =super.getUsuarioFirmado(request); 
			Long idFuncionario=usuario.getFuncionario().getClaveFuncionario();
			filtrosBusquedaExpediente.setUsuario(usuario);
			
			UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
			Roles rolAsociado = obtenEnumRol(usuarioRolDTO.getRol().getRolId());
			
			switch (rolAsociado) {
				case COORDINADORAMPGENERAL:				
				case COORDINADORAMP:{
					filtrosBusquedaExpediente.setIdActividad(Actividades.RECIBIR_CANALIZACION_UI.getValorId());
					filtrosBusquedaExpediente.setIdArea(Areas.UNIDAD_INVESTIGACION.parseLong());
					break;
				}
				case COORDINADORJAR:{
					filtrosBusquedaExpediente.setIdActividad(Actividades.RECIBIR_CANALIZACION_JAR.getValorId());
					filtrosBusquedaExpediente.setIdArea(Areas.FISCAL_FACILITADOR.parseLong());
					break;
				}
				case ATPENAL:
				case FACILITADOR:
				case AGENTEMP:{
					filtrosBusquedaExpediente.setIdFuncionario(super.getUsuarioFirmado(request).getFuncionario().getClaveFuncionario());
					break;
				}
				case DSE :{
					//si es de DSE de  Reinsercion Social se agrega el area del ASE Como subordinada 
					filtrosBusquedaExpediente.getUsuario().getFuncionario().getDiscriminante().setCatDiscriminanteId(0L);
					filtrosBusquedaExpediente.setIdArea(Areas.DSE_DE_REINSERCION.parseLong());
					filtrosBusquedaExpediente.getUsuario().getAreaActual().setAreaId(Areas.DSE_DE_REINSERCION.parseLong());
					Set<JerarquiaOrganizacionalDTO> jerarquiaOrganizacionalDTOs = new HashSet<JerarquiaOrganizacionalDTO>();
					jerarquiaOrganizacionalDTOs.add(new JerarquiaOrganizacionalDTO(Areas.ASE_DE_REINSERCION.parseLong()));
					filtrosBusquedaExpediente.setJerarquiaOrgSubordinadas(jerarquiaOrganizacionalDTOs);
					//se agregan los estatus al 
					List<Long> estatus = new ArrayList<Long>();
//					estatus.add(EstatusExpediente.POR_ATENDER.getValorId());
					estatus.add(EstatusExpediente.EN_PROCESO.getValorId());
					estatus.add(EstatusExpediente.ABIERTO_EJECUCION.getValorId());
					estatus.add(EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId());
					estatus.add(EstatusExpediente.EN_TRAMITE.getValorId());
					filtrosBusquedaExpediente.setEstatusNumeroExpediente(estatus);
				}
				
			}
			
			if(reasignarExpediente != null) {
				filtrosBusquedaExpediente.setIdActividad(null);
				filtrosBusquedaExpediente.getUsuario().getAreaActual().setBuscarEnJerarquia(Boolean.TRUE);
				filtrosBusquedaExpediente.getUsuario().getFuncionario().setClaveFuncionario(0L);
				filtrosBusquedaExpediente.setEsCordinadorGeneralAMP(esCordinadorGeneralAMP);
			}
			
			filtrosBusquedaExpediente.setNumeroExpediente(noExpediente);
			
			// List<ExpedienteDTO>
			// listExpedienteDTOs=expedienteDelegate.buscarExpedientes(filtrosBusquedaExpediente);
			List<ExpedienteDTO> listExpedienteDTOs = null;
			if (rolAsociado.getValorId().equals(Roles.DSE.getValorId())){
				listExpedienteDTOs = expedienteDelegate.consultarExpedientesRSPorNumeroExpediente(filtrosBusquedaExpediente);
			}else{
				listExpedienteDTOs = expedienteDelegate
				.buscarExpedientesPorNumExpDiscriminanteArea(filtrosBusquedaExpediente);				
			}

			log.debug("TAMAnio LISTA DE EXPEDIENTES="
					+ listExpedienteDTOs.size());

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.print("<rows>");

			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			for (ExpedienteDTO expedienteDTO : listExpedienteDTOs) {
				if(opcionUI!=null && opcionUI.equals(""+Areas.UNIDAD_INVESTIGACION.parseLong())){
					writer.print("<row id='"
							+ expedienteDTO.getIdNumeroExpedienteBusquedaATP() + "'>");
				}else{
					// se cambio por numeroExpedienteId dado que el solo Id no era
					// el correcto
					writer.print("<row id='"
							+ expedienteDTO.getNumeroExpedienteId() + "'>");
				}
				if (expedienteDTO.getCasoDTO() != null) {
					writer.print("<cell>"
							+ expedienteDTO.getCasoDTO().getNumeroGeneralCaso()
							+ "</cell>");
				} else {
					writer.print("<cell> -- </cell>");
				}
				writer.print("<cell>" + ((expedienteDTO.getNumeroExpediente() != null) ? expedienteDTO.getNumeroExpediente() : "---")	+ "</cell>");
				if(reasignarExpediente==null){
					if( expedienteDTO.getResponsableTramite()!=null &&
							 expedienteDTO.getResponsableTramite().getNombreCompleto()!=null){
						writer.print("<cell>"+ expedienteDTO.getResponsableTramite().getNombreCompleto()+ "</cell>");
					}else{
						writer.print("<cell>"+ "---"+ "</cell>");
					}
					if( expedienteDTO.getDiscriminante()!=null &&
							 expedienteDTO.getDiscriminante().getNombre()!=null){
						writer.print("<cell>"+ expedienteDTO.getDiscriminante().getNombre()+ "</cell>");
					}else{
						writer.print("<cell>"+ "---"+ "</cell>");
					}
					
					
				}

				// SOLO PARA CUANDO SE QUIERA REASIGNAR UN EXPEDIENTE
				if(reasignarExpediente != null) {
					
					if (rolAsociado == Roles.DSE) {
						writer.print(datosSentenciaPorNumeroExpediente(expedienteDTO));
					}
					
					ValorDTO estatus = expedienteDTO.getEstatus();
					if (estatus != null) { 
						writer.print("<cell>" + ((estatus.getValor() != null) ? estatus.getValor() : "---")	+ "</cell>");
					} else {
						writer.print("<cell>---</cell>");
					}
					
					try {
						FuncionarioDTO responsable = funcionarioDelegate.consultarFuncionarioXExpediente(expedienteDTO);
						if (responsable != null) {
							writer.print("<cell>" + ((responsable.getClaveFuncionario() != null) ? responsable.getClaveFuncionario() : "---")	+ "</cell>");
							writer.print("<cell>" + ((responsable.getNombreCompleto() != null) ? responsable.getNombreCompleto() : "---")	+ "</cell>");
						} else {
							writer.print("<cell>---</cell>");
							writer.print("<cell>---</cell>");
						}
					}catch(Exception e){
						log.info("Error al consultar Funcionario Responsable: " , e);
						writer.print("<cell>---</cell>");
						writer.print("<cell>---</cell>");
					}
					
					List<DelitoDTO> lstDelitos = delitoDelegate.consultarDelitosExpediente(expedienteDTO); 
					DelitoDTO delitoPrincipal = delitoDelegate.obtenerDelitoPrincipal(lstDelitos);
					
					if (delitoPrincipal != null) {
						CatDelitoDTO catDelitoDTO =(delitoPrincipal.getCatDelitoDTO() != null) ? delitoPrincipal.getCatDelitoDTO() : null;
						if (catDelitoDTO != null) {
							writer.print("<cell>" + ((catDelitoDTO.getNombre() != null) ? catDelitoDTO.getNombre() : "---")	+ "</cell>");
						} else {
							writer.print("<cell>---</cell>");
						}				
					} else {
						writer.print("<cell>---</cell>");
					}				
					if(verHistorico){
						writer.print("<cell>");
						writer.print("<![CDATA[");
						writer.print("<input  type=\"button\" " +
											" name=\"btnVerHistorial" + expedienteDTO.getNumeroExpedienteId() + "\" " +
											" id=\"btnVerHistorial" + expedienteDTO.getNumeroExpedienteId() + "\" " +
											" value=\"Ver Historial\" " +
											" onclick=\"verHistorial(" + expedienteDTO.getNumeroExpedienteId() + ");\" " +
											" class=\"btn_Generico\" />");
						writer.print("]]>");
						writer.print("</cell>");
					}
				}
				writer.print("</row>");
			}

			writer.print("</rows>");
			writer.flush();
			writer.close();
			if(reasignarExpediente != null) {
				usuario.getFuncionario().setClaveFuncionario(idFuncionario);
			}else if (!usuario.getFuncionario().getClaveFuncionario().equals(idFuncionario)){
				usuario.getFuncionario().setClaveFuncionario(idFuncionario);
			}
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		
		
		return null;
	}

	/**
	 * @param usuario
	 * @param writer
	 * @param expedienteDTO
	 * @throws NSJPNegocioException
	 */
	private String datosSentenciaPorNumeroExpediente(ExpedienteDTO expedienteDTO)
			throws NSJPNegocioException {
		
		StringBuilder datosSentencia = new StringBuilder();
		SentenciaDTO sentenciaDTO = new SentenciaDTO();
		sentenciaDTO.setNumeroExpedienteDTO(new ExpedienteDTO(null, expedienteDTO.getNumeroExpedienteId(),expedienteDTO.getNumeroExpediente()));
		List<SentenciaDTO> lstSentencia = asignacionProgramaDelegate.consultarSentencias(sentenciaDTO);
		
		sentenciaDTO = null;
		
		if(lstSentencia != null && !lstSentencia.isEmpty()){
			sentenciaDTO = lstSentencia.get(0);
		}
		
		if (sentenciaDTO != null && sentenciaDTO.getCnus() != null) { 
			datosSentencia.append("<cell>" + ((sentenciaDTO.getCnus() != null) ? sentenciaDTO.getCnus() : "---")	+ "</cell>");
		} else {
			datosSentencia.append("<cell>---</cell>");
		}
		
		if (sentenciaDTO != null && sentenciaDTO.getInvolucradoDTO() != null) {
			
			InvolucradoDTO sentenciado = sentenciaDTO.getInvolucradoDTO();
			if(sentenciado != null) {
				datosSentencia.append("<cell>" + ((sentenciado.getNombreCompleto() != null) ? sentenciado.getNombreCompleto() : "---")	+ "</cell>");
			} else {
				datosSentencia.append("<cell>---</cell>");
			}
		} else {
			datosSentencia.append("<cell>---</cell>");
		}
		
		return datosSentencia.toString();
	
	}
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorEvidencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			
			String evidencia=request.getParameter("evidencia");
			String palabraUno=request.getParameter("palabraUno");
			String palabraDos=request.getParameter("palabraDos");
			String palabraTres=request.getParameter("palabraTres");
			String palabraCuatro=request.getParameter("palabraCuatro");
			String palabraCinco=request.getParameter("palabraCinco");
			
			
			if (palabraUno.equals("")){
				palabraUno=null;
				
			}

			if (palabraDos.equals("")){
				palabraDos=null;
				
			}				
			
			if (palabraTres.equals("")){
				palabraTres=null;
				
			}

			if (palabraCuatro.equals("")){
				palabraCuatro=null;
				
			}		
			
			if (palabraCinco.equals("")){
				palabraCinco=null;
				
			}
			
			if (log.isDebugEnabled()) {
				log.debug("##################llega evidencia id :::::::::" + evidencia);
				log.debug("##################llega evidencia:::::::::" + palabraUno);
				log.debug("##################llega evidencia:::::::::" + palabraDos);
				log.debug("##################llega evidencia:::::::::" + palabraTres);
				log.debug("##################llega evidencia:::::::::" + palabraCuatro);
				log.debug("##################llega evidencia:::::::::" + palabraCinco);
			}			
			
			List<String> lstPalabra = new ArrayList<String>();
			lstPalabra.add(palabraUno);
			lstPalabra.add(palabraDos);
			lstPalabra.add(palabraTres);
			lstPalabra.add(palabraCuatro);
			lstPalabra.add(palabraCinco);			
			
			Objetos tipoObjeto = Objetos.getByValor(new Long(evidencia));
			
			FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
			//Se realiza la siguiente validacion dado que para no afectar el arbol de elementos de los
			//editores de texto para la cnsulta del objeto otro y solucionar la consulta de expedientes por evidencias
			if(new Long(evidencia)== Objetos.OTRO.getValorId().longValue())
			{
				filtrosBusquedaExpediente.setNombreEvidencia("Objeto");
				log.debug("##################Entity_nombre_evidencias OTRO::::::");
			}
			else
			{
				filtrosBusquedaExpediente.setNombreEvidencia(tipoObjeto.getNombreEntity());
				log.debug("##################Entity_nombre_evidencias ::::::" + tipoObjeto.getNombreEntity() +" - ID TipoObjeto:: "+ evidencia +" -  enum:: "+Objetos.OTRO.getValorId());
			}
			
			filtrosBusquedaExpediente.setPalabrasClave(lstPalabra);
			filtrosBusquedaExpediente.setUsuario(super.getUsuarioFirmado(request));
			
			UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
			Roles rolAsociado = obtenEnumRol(usuarioRolDTO.getRol().getRolId());
			switch (rolAsociado) {
				case COORDINADORAMP:
				case COORDINADORJAR:{
					filtrosBusquedaExpediente.setIdArea(usuarioRolDTO.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId());
					break;
				}
				case ATPENAL:
				case FACILITADOR:
				case AGENTEMP:{
					filtrosBusquedaExpediente.setIdFuncionario(super.getUsuarioFirmado(request).getFuncionario().getClaveFuncionario());
					break;
				}
			}
			
			
			List<ExpedienteDTO> listExpedienteDTOs=expedienteDelegate.buscarExpedientes(filtrosBusquedaExpediente);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
//			List<NombreDemograficoDTO> listaNombreDemograficoDto = null;
			
			writer.print("<rows>");
			int lTotalRegistros=listExpedienteDTOs.size();

			writer.print("<records>" + lTotalRegistros + "</records>");

			for (ExpedienteDTO expedienteDTO : listExpedienteDTOs) {
				writer.print("<row id='"+ expedienteDTO.getNumeroExpedienteId()+"'>");//se cambio por numeroExpedienteId dado que el solo Id no era el correcto
				writer.print("<cell>" + expedienteDTO.getCasoDTO().getNumeroGeneralCaso()+ "</cell>");
				writer.print("<cell>" + expedienteDTO.getNumeroExpediente()+ "</cell>");
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
	 * M&eacute;todo utilizado para realizar la consulta de expedientes
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorAlias(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			BuscarExpedientePorAliasForm forma=(BuscarExpedientePorAliasForm) form;
			String alias=forma.getAlias();
			Long tipo=forma.getTipo();
			if (log.isDebugEnabled()) {
				log.debug("llega alias " + alias );
				log.debug("llega tipo " + tipo );
			}
			FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
			filtrosBusquedaExpediente.setAlias(alias);
			filtrosBusquedaExpediente.setTipoBusqueda(tipo);
			List<InvolucradoDTO> listInvolucradoDTOs=expedienteDelegate.consultarExpedientesPorFiltros(filtrosBusquedaExpediente);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			List<NombreDemograficoDTO> listaNombreDemograficoDto = null;
			
			writer.print("<rows>");
			
			int lTotalRegistros=0;
			
			for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
				lTotalRegistros=+involucradoDTO.getNombresDemograficoDTO().size();
			}
				
			writer.print("<records>" + lTotalRegistros + "</records>");
			for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
				listaNombreDemograficoDto = involucradoDTO.getNombresDemograficoDTO();
				for (NombreDemograficoDTO nombreDemograficoDTO : listaNombreDemograficoDto) {
				
				writer.print("<row id='" + involucradoDTO.getExpedienteDTO().getNumeroExpedienteId()+ "'>");//se cambio por numeroExpedienteId dado que el solo Id no era el correcto
				writer.print("<cell>" + involucradoDTO.getExpedienteDTO().getNumeroExpediente()+ "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getNombre()+ "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getApellidoPaterno() + "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getApellidoMaterno() + "</cell>");
				writer.print("<cell>" + involucradoDTO.getCalidadDTO().getValorIdCalidad().getValor() + "</cell>");
				writer.print("</row>");
				}			
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
	 * M&eacute;todo utilizado para realizar la carga de la tabla que se presenta
	 * en la JSP BuscarExpedientePorNombreDePersona
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNombreDePersona(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {		
		try {
			
			BuscarExpedientePorNombreDePersonaForm forma=(BuscarExpedientePorNombreDePersonaForm) form;
			//String nombre=forma.getNombre();
			//String apellido=forma.getApellido();
			
			if (forma.getNombre().equals("")){
				forma.setNombre(null);
				log.info(forma.getNombre());
			}

			if (forma.getApellido().equals("")){
				forma.setApellido(null);
				log.info(forma.getApellido());
			}					
			
			if (log.isDebugEnabled()) {
				log.debug("llega nombre " + forma.getNombre() );
				log.debug("llega apellido " + forma.getApellido() );
				log.debug("llega apellido materno " + forma.getApellidoMat() );
			}
			FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
			filtrosBusquedaExpediente.setNombre(forma.getNombre());
			filtrosBusquedaExpediente.setApellidos(forma.getApellido());
			filtrosBusquedaExpediente.setApellidoMat(forma.getApellidoMat());
			
			UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);
			
			UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
			Roles rolAsociado = obtenEnumRol(usuarioRolDTO.getRol().getRolId());
			switch (rolAsociado) {
				case COORDINADORAMP:
				case COORDINADORJAR:{
					filtrosBusquedaExpediente.setIdArea(usuarioRolDTO.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId());
					filtrosBusquedaExpediente.setUsuario(getUsuarioFirmado(request));
					break;
				}
				case ATPENAL:
				case ADMINAT:{
					filtrosBusquedaExpediente.setIdArea(usuarioRolDTO.getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId());
					break;
				}
				case FACILITADOR:
				case AGENTEMP:{
					filtrosBusquedaExpediente.setIdFuncionario(super.getUsuarioFirmado(request).getFuncionario().getClaveFuncionario());
					filtrosBusquedaExpediente.setUsuario(getUsuarioFirmado(request));
					break;
				}
			}
			
			
			List<InvolucradoDTO> listInvolucradoDTOs =expedienteDelegate.consultarExpedientesPorFiltrosYDiscriminante(filtrosBusquedaExpediente,usuarioFirmado);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			List<NombreDemograficoDTO> listaNombreDemograficoDto = null;			

			writer.print("<rows>");
			
			int lTotalRegistros=0;			
			for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
				lTotalRegistros=+involucradoDTO.getNombresDemograficoDTO().size();
			}
				
			writer.print("<records>" + lTotalRegistros + "</records>");
			for (InvolucradoDTO involucradoDTO : listInvolucradoDTOs) {
				listaNombreDemograficoDto = involucradoDTO.getNombresDemograficoDTO();
				for (NombreDemograficoDTO nombreDemograficoDTO : listaNombreDemograficoDto) {
				
				writer.print("<row id='" + involucradoDTO.getExpedienteDTO().getNumeroExpedienteId() + "'>");//se cambio por numeroExpedienteId dado que el solo Id no era el correcto
				
					if (involucradoDTO.getExpedienteDTO() != null
							&& involucradoDTO.getExpedienteDTO().getCasoDTO() != null
							&& involucradoDTO.getExpedienteDTO().getCasoDTO()
									.getNumeroGeneralCaso() != null) {
						writer.print("<cell>"
								+ involucradoDTO.getExpedienteDTO()
										.getCasoDTO().getNumeroGeneralCaso()
								+ "</cell>");
					}
					else{
						writer.print("<cell>"+" "+"</cell>");
					}
				
				writer.print("<cell>" + involucradoDTO.getExpedienteDTO().getNumeroExpediente()+ "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getNombre()+ "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getApellidoPaterno() + "</cell>");
				writer.print("<cell>" + nombreDemograficoDTO.getApellidoMaterno() + "</cell>");
				writer.print("<cell>" + involucradoDTO.getCalidadDTO().getValorIdCalidad().getValor() + "</cell>");
				writer.print("</row>");
				}			
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
	 * M&eacute;todo utilizado para realizar la carga del combo tipo de evidencia 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException En caso de obtener una exception
	 */
	
	public ActionForward cargarTipoEvidencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Action Buscar Expediente Por Evidencia");
			
			List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.TIPO_OBJETO);

			XStream converter=new XStream();
			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("catEvidencia", CatalogoDTO.class);
			
			String xml = converter.toXML(listaCatalogo);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
			
		} catch (Exception e) {		
			log.info(e);
		}
		return null;
	}	
	
	public ActionForward generarDetalleExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			Long idExpediente=Long.parseLong(request.getParameter("idRow"));
			
			if (log.isDebugEnabled()) {
				
				log.debug("##################llega idExpediente:::::::::" + idExpediente);
			}

			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
			expedienteDTO.setExpedienteId(idExpediente);
			expedienteDTO.setArea(super.getUsuarioFirmado(request).getAreaActual());
			ExpedienteDTO expedienteDTO2=expedienteDelegate.obtenerExpediente(expedienteDTO);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			
			if (log.isDebugEnabled()) {
				
				log.debug("##################llega grid detalle:::::::::" + expedienteDTO2 );
			}

			List<NombreDemograficoDTO> list=new ArrayList<NombreDemograficoDTO>();
			
			for (InvolucradoDTO dos : expedienteDTO2.getInvolucradosDTO()) {
				for (NombreDemograficoDTO nombreDemograficoDTO : dos.getNombresDemograficoDTO()) {
					list.add(nombreDemograficoDTO);
				}
			}
			
			writer.print("<rows>");
			int lTotalRegistros=list.size();

			writer.print("<records>" + lTotalRegistros + "</records>");
			
			for (InvolucradoDTO dos : expedienteDTO2.getInvolucradosDTO()) {
				for (NombreDemograficoDTO demograficoDTO : list) {
					
				writer.print("<row id='1'>");
				writer.print("<cell>" + demograficoDTO.getNombre() + "</cell>");
				writer.print("<cell>" + demograficoDTO.getApellidoPaterno()  + "</cell>");
				writer.print("<cell>" + demograficoDTO.getApellidoMaterno()  + "</cell>");
				writer.print("<cell>" + dos.getCalidadDTO().getValorIdCalidad().getValor() + "</cell>");
				writer.print("<cell>" + dos.getExpedienteDTO().getDelitoPrincipal() + "</cell>");
				writer.print("<cell>" + dos.getExpedienteDTO().getCasoDTO()+ "</cell>");
				writer.print("</row>");
				}	
			}
			
			writer.print("</rows>");
			writer.flush();
			writer.close();
									
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	public ActionForward generarDetalleDeBusquedaDeExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			String idRow=request.getParameter("idRow");
			
			if (log.isDebugEnabled()) {
				
				log.debug("##################llega fila:::::::::" + idRow);
			}

			request.setAttribute("idRow", idRow);

			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return mapping.findForward("generarDetalleExp");
	}
	
	public ActionForward cargarTitulo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Action carga titulo");
			
			Long idExp=Long.parseLong(request.getParameter("idRow"));
			
			if (log.isDebugEnabled()) {
				
				log.debug("##################llega idExpediente  titulo:::::::::" + idExp);
			}
			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
			expedienteDTO.setExpedienteId(idExp);
			ExpedienteDTO expedienteDTO2=expedienteDelegate.obtenerExpediente(expedienteDTO);
			
			XStream converter=new XStream();
			converter.alias("expediente", ExpedienteDTO.class);
			
			String xml = converter.toXML(expedienteDTO2);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
			
		} catch (Exception e) {		
			log.info(e);
		}
		return null;
	}	
	
	
	/**
	 * M&eacute;todo utilizado para pasar parametros y diferenciar de donde se hace el llamado del caso d euso buscar expediente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Action buscar expediente");
			
			String tipo = request.getParameter("tipo");
			log.info("LLEGA TIPO:::"+ tipo);
			UsuarioDTO usuarioDTO=super.getUsuarioFirmado(request);
			if(usuarioDTO.getRolACtivo().getRol().getJerarquiaOrganizacionalDTO().getJerarquiaOrganizacionalId().equals(Areas.UNIDAD_INVESTIGACION.parseLong())){
				request.setAttribute("opcion",Areas.UNIDAD_INVESTIGACION.parseLong());
			}
			
			
			request.setAttribute("tipo",tipo);
			
		} catch (Exception e) {		
			log.info(e);
		}
		return mapping.findForward("succes");
	}
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes en forma de xml
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNumeroDeExpedienteComparaExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
			
			String noExpediente=request.getParameter("noExpediente");
			if (log.isDebugEnabled()) {
				log.debug("##################llega noExpediente:::::::::" + noExpediente);
			}
			FiltroExpedienteDTO filtrosBusquedaExpediente=new FiltroExpedienteDTO();
			filtrosBusquedaExpediente.setNumeroExpediente(noExpediente);
			
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			
			if(usuarioDTO.getAreaActual().getAreaId().longValue() == Areas.COORDINACION_DEFENSORIA.parseLong().longValue()){
				filtrosBusquedaExpediente.setUsuario(new UsuarioDTO());
			}else{
				filtrosBusquedaExpediente.setUsuario(usuarioDTO);
			}
			
			List<ExpedienteDTO> listExpedienteDTOs=expedienteDelegate.buscarExpedientes(filtrosBusquedaExpediente);
			log.debug("##################listExpedienteDTOs::::::::: " + listExpedienteDTOs.size());
			

			XStream converter=new XStream();
			converter.alias("listExpedienteDTOs", java.util.List.class);
			converter.alias("expedienteDTO", ExpedienteDTO.class);
			String xml = converter.toXML(listExpedienteDTOs);

			log.debug("##################listExpedienteDTOs   XML::::::::: " + xml);
			
			response.setContentType("text/xml");
			
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();	
			
			
		} catch (Exception e) {		
			log.info(e.getCause(),e);
			
		}
		return null;
	}
	
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward buscarExpedientePorNumeroDeExpedienteParaDocumentos(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			
			UsuarioDTO usuario = getUsuarioFirmado(request);
			
			 //Para que no busque por area
			
			if (usuario != null && usuario.getAreaActual() != null
					&& usuario.getAreaActual().getAreaId() != null) {
				usuario.getAreaActual().setAreaId(null);
			}
			
			log.info(":::::::::::::VERIFICANDO PARAMETROS:::::::::::::::::::::");			
			log.info("Numero de Expediente=" + request.getParameter("numeroExpedienteId"));
			log.info("FechaIni=" + request.getParameter("fechaIni"));
			log.info("FechaFin=" + request.getParameter("fechaFin"));

			String noExpediente = request.getParameter("numeroExpedienteId");
			
			FiltroExpedienteDTO filtrosBusquedaExpediente = new FiltroExpedienteDTO();
			
			List<ExpedienteDTO> listExpedienteDTOs = new ArrayList<ExpedienteDTO>();
			
			filtrosBusquedaExpediente.setNumeroExpediente(noExpediente);
			filtrosBusquedaExpediente.setUsuario(usuario);
			
			if (noExpediente != null && noExpediente != "") {
				listExpedienteDTOs = expedienteDelegate
						.buscarExpedientes(filtrosBusquedaExpediente);
			}else{
				
				if (usuario.getFuncionario() != null
						&& usuario.getFuncionario().getClaveFuncionario() != null) {
					usuario.getFuncionario().setClaveFuncionario(null);
				}
				
				Date fechaInicio = new Date();
				Date fechaFinal = new Date();
				
				if (request.getParameter("fechaIni") != null
						&& request.getParameter("fechaIni") != ""
						&& request.getParameter("fechaFin") != null
						&& request.getParameter("fechaFin") != "") {
					
					fechaInicio = DateUtils.obtener(request.getParameter("fechaIni"),"00:00");
					fechaFinal = DateUtils.obtener(request.getParameter("fechaFin"),"00:00");
					
					listExpedienteDTOs = expedienteDelegate
							.consultarExpedientesPorFiltro(fechaInicio,
									fechaFinal, usuario.getFuncionario(), null, null);
					
				}else{
					
					fechaInicio = new Date();
					Calendar calTempDec = Calendar.getInstance();
					
					calTempDec.setTime(fechaInicio);
					calTempDec.add(Calendar.DATE, 1);
					log.info(calTempDec + "tiempo despues tiempo antes"	+ fechaInicio);
					listExpedienteDTOs = expedienteDelegate
							.consultarExpedientesPorFiltro(fechaInicio,
									calTempDec.getTime(), usuario.getFuncionario(), null,
									null);
				}
			}
			
			log.info("lista de expedientes else" + listExpedienteDTOs);
			
			
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

			for (ExpedienteDTO expedienteDTO : listExpedienteDTOs) {
				writer.print("<row id='"+ expedienteDTO.getNumeroExpedienteId() + "'>");
				writer.print("<cell>");
					if (expedienteDTO.getNumeroExpediente() != null) {
						writer.print(expedienteDTO.getNumeroExpediente());
					} else {
						writer.print("-");
					}
				writer.print("</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getCasoDTO() != null) {
						writer.print(expedienteDTO.getCasoDTO()
								.getNumeroGeneralCaso());
					} else {
						writer.print("-");
					}
				writer.print("</cell>");

				writer.print("<cell>");
					if (expedienteDTO.getStrFechaApertura() != null) {
						writer.print(expedienteDTO.getStrFechaApertura());
					} else {
						writer.print("-");
					}
				writer.print("</cell>");
				writer.print("<cell>");
					if (expedienteDTO.getDocumentosDTO() != null) {
						for (DocumentoDTO documentoDTO : expedienteDTO
								.getDocumentosDTO()) {
							if (documentoDTO.getArchivoDigital() != null) {
								writer.print("Si");
							} else {
	
								writer.print("no");
							}
						}
					} else {
						writer.print("-");
					}
				writer.print("</cell>");
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
	 * M&eacute;todo utilizado para realizar la consulta de expedientes por area, por
	 * catDiscriminante del Usuario y por fecha de creacion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward busquedaExpedientesXEstatusXFechaXAreaXDiscriminante(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			if (log.isDebugEnabled()) {
				log.debug("::::::EJECUNTANDO ACTION busquedaExpedientesXEstatusXFechaXAreaXDiscriminante:::::");
			}

			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");
						
			String estatusExpediente = request.getParameter("estatusExpediente");
			Long idRol = NumberUtils.toLong(request.getParameter("idRol"),0);
			Long idDistrito = NumberUtils.toLong(request.getParameter("idDistrito"),0);
			Long idFuncionario = NumberUtils.toLong(request.getParameter("idFuncionario"),0);
			Long idDiscriminante = NumberUtils.toLong(request.getParameter("idDiscriminante"),0);
			
			if (log.isDebugEnabled()) {
				log.debug("VERIFICANDO PARAMETROS:::::::::");
				log.debug("fechaInicio=" + fechaInicio);
				log.debug("fechaFin=" + fechaFin);
				log.debug("estatusExpediente=" + estatusExpediente);
				log.debug("idRol=" + idRol);
				log.debug("idDistrito=" + idDistrito);
				log.debug("idFuncionario=" + idFuncionario);
				log.debug("idDiscriminante=" + idDiscriminante);
			}

			Date iniDate = null;
			Date finDate = null;
			
			if (fechaInicio != null && !fechaInicio.equals("")) {
				iniDate = DateUtils.obtener(fechaInicio);
			}
			if (fechaFin != null && !fechaFin.equals("")){
				finDate = DateUtils.obtener(fechaFin);
			}
								
			//Permite recuperar los ids de estatus
			String[] listaEstatus= estatusExpediente.split(",");
			List<Long> listaEstatusLong = new ArrayList<Long>();

			for (String idEstatus : listaEstatus) {
				listaEstatusLong.add(Long.parseLong(idEstatus));
			}
			
			List<ExpedienteDTO> listExpedienteDTOs = expedienteDelegate
					.consultarExpedientesPorFiltroST(iniDate, finDate,
							super.getUsuarioFirmado(request),
							listaEstatusLong,idDiscriminante,idRol,idDistrito,idFuncionario);

			log.debug("TAMANIO LISTA DE EXPEDIENTES="
					+ listExpedienteDTOs.size());

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			//Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
			
			for (ExpedienteDTO expedienteDTO : listExpedienteDTOs) {

				//Id del renglon
				writer.print("<row id='" + expedienteDTO.getNumeroExpedienteId()
						+ "'>");

				//Numero de Expediente
				writer.print("<cell>" + expedienteDTO.getNumeroExpediente()
						+ "</cell>");

				//Fecha de apertura del expediente
				if (expedienteDTO.getStrFechaApertura() != null) {
					writer.print("<cell>" + expedienteDTO.getStrFechaApertura()
							+ "</cell>");
				} else {
					writer.print("<cell>" + " " + "</cell>");
				}
				
				//Columna Denunciante
				log.info("Este es el expediente con calidad de denunciante"+expedienteDTO.getInvolucradoByCalidad(Calidades.DENUNCIANTE));
				log.info("invol tamanio"+expedienteDTO.getInvolucradoByCalidad(Calidades.DENUNCIANTE).size());
				log.info("invol tamanio de"+expedienteDTO.getInvolucradosDTO().size());
				 boolean op=true;
				for (InvolucradoDTO involucradoDTO : expedienteDTO.getInvolucradoByCalidad(Calidades.DENUNCIANTE)) {
					log.info("numero de involucrado nombre completo perdon:"+involucradoDTO.getNombreCompleto());
					for (NombreDemograficoDTO nombreDemograficoDTO : involucradoDTO.getNombresDemograficoDTO()) {
						log.info("Verdadero nombre:"+nombreDemograficoDTO.getEsVerdadero());
						if(nombreDemograficoDTO.getEsVerdadero()!=null && nombreDemograficoDTO.getEsVerdadero()){
							writer.print("<cell>"+nombreDemograficoDTO.getNombre()+" "+nombreDemograficoDTO.getApellidoPaterno()+" "+nombreDemograficoDTO.getApellidoMaterno() +"</cell>");
							op=false;
						}
					}
				}
				if(op){
					writer.print("<cell>"+"An&#243;nimo"+"</cell>");
				}


				
				//Delito principal
				if (expedienteDTO.getDelitoPrincipal() != null
						&& expedienteDTO.getDelitoPrincipal().getCatDelitoDTO() != null
						&& expedienteDTO.getDelitoPrincipal().getCatDelitoDTO()
								.getNombre() != null) {
					writer.print("<cell>"
							+ expedienteDTO.getDelitoPrincipal()
									.getCatDelitoDTO().getNombre().toLowerCase() + "</cell>");
				} else {
					writer.print("<cell>" + "---" + "</cell>");

				}
				
				//Columna Origen 
				if(expedienteDTO.getOrigen()!=null){
					writer.print("<cell>"+expedienteDTO.getOrigen().getValor().toLowerCase()+"</cell>");
				}else{
					writer.print("<cell>"+"Denuncia"+"</cell>");
				}
				
				//Estatus
				if(expedienteDTO.getEstatus()!=null && expedienteDTO.getEstatus().getValor() !=null){
					writer.print("<cell>"+ expedienteDTO.getEstatus().getValor().toLowerCase() +"</cell>");
				}else{
					writer.print("<cell>"+"---"+"</cell>");
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
	
	
	public ActionForward consultarExpedientesSistemaTradicional(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {

			log.info("EJECUTANDO ACTION consultarExpedientesSistemaTradicional");

			String idNumeroExpediente = request
					.getParameter("idNumeroExpediente");

			log.info("VERIFICANDO PARAMETROS ExpedienteId: "
					+ idNumeroExpediente);

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setArea(new AreaDTO(
					Areas.AGENCIA_DEL_MINISTERIO_PUBLICO));

			if (idNumeroExpediente != null) {
				expedienteDTO.setNumeroExpedienteId(Long
						.parseLong(idNumeroExpediente));
			}
			expedienteDTO = expedienteDelegate.obtenerExpediente(expedienteDTO);

			expedienteDTO.setConsulta(true);
			log.info("ACTION- NUMERO EXPEDIENTE ENCONTRADO:"
					+ expedienteDTO.getNumeroExpediente());

			request.getSession().removeAttribute("numExpConsul");
			request.getSession().setAttribute("numExpConsul",
					expedienteDTO.getNumeroExpediente());
			request.getSession().setAttribute("numeroExpediente",
					expedienteDTO.getNumeroExpediente());
			request.getSession().setAttribute("idNumeroExpedienteop",
					expedienteDTO.getNumeroExpedienteId());
			request.getSession().setAttribute("idExpedienteop",
					expedienteDTO.getExpedienteId());
			request.getSession().setAttribute("idExpedienteConsulop",
					expedienteDTO.getExpedienteId());

			// IMPLEMENTACION ANTERIOR
			// request.getSession().removeAttribute("numExpConsul");
			// request.getSession().setAttribute("numExpConsul",
			// expedienteDTO.getNumeroExpediente());
			// request.getSession().setAttribute("idExpedienteConsul",
			// expedienteDTO.getNumeroExpedienteId());
			// request.getSession().setAttribute("idExpedienteConsulop",
			// expedienteDTO.getExpedienteId());
			// request.getSession().setAttribute("numeroCasoConsul",
			// expedienteDTO.getCasoDTO().getNumeroGeneralCaso());

			log.info("area_exp:: " + expedienteDTO.getArea().getAreaId());

			// asignamos la pantalla solicitada
			if (expedienteDTO.getArea().getAreaId().longValue() == Areas.ATENCION_TEMPRANA_PG_PENAL
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 1);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 2);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.UNIDAD_INVESTIGACION
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.UNIDAD_INVESTIGACION.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 3);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.COORDINACION_UNIDAD_INVESTIGACION
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.UNIDAD_INVESTIGACION.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 4);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.FISCAL_FACILITADOR
					.parseLong()) {
				log.info("area_enum:: " + Areas.FISCAL_FACILITADOR.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 5);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.POLICIA_MINISTERIAL
					.parseLong()) {
				log.info("area_enum:: " + Areas.POLICIA_MINISTERIAL.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 6);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.COORDINACION_VISITADURIA
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.COORDINACION_VISITADURIA.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 7L);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.VISITADURIA
					.parseLong()) {
				log.info("area_enum:: " + Areas.VISITADURIA.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 8);
			} else if (expedienteDTO.getArea().getAreaId().longValue() == Areas.AGENCIA_DEL_MINISTERIO_PUBLICO
					.parseLong()) {
				log.info("area_enum:: "
						+ Areas.AGENCIA_DEL_MINISTERIO_PUBLICO.parseLong());
				request.getSession().setAttribute("pantallaSolicitada", 9);
			}
			super.setExpedienteTrabajo(request, expedienteDTO);
			log.info("SUBE A SESION EL EXPEDIENTE DE TRABAJO: " + expedienteDTO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mapping.findForward("succes");
	}

	/**
	 * M&eacute;todo utilizado para realizar la consulta de expedientes en forma de xml
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward actualizarCatDiscriminanteDeExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException {	
					
		try {
        	Long catDiscriminanteId = NumberUtils.toLong(request.getParameter("catDiscriminanteId"),0L);
        	Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpedienteId"),0L);
        	Long catUIEId = NumberUtils.toLong(request.getParameter("catUIE"),0L);
        	
        	String numeroUnicoExpediente = request.getParameter("numeroUnicoExpediente");
        	
        	if (numeroExpedienteId.equals(0L) && numeroUnicoExpediente != null){
        		numeroUnicoExpediente = numeroUnicoExpediente.trim();
        		ExpedienteDTO exp = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroUnicoExpediente);
        		numeroExpedienteId = exp.getNumeroExpedienteId();
        	}
        	
        	ExpedienteDTO expedienteDTO = new ExpedienteDTO();
        	CatDiscriminanteDTO discriminante = new CatDiscriminanteDTO();
        	discriminante.setCatDiscriminanteId(catDiscriminanteId);
        	expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
        	expedienteDTO.setDiscriminante(discriminante);
        	expedienteDTO.setCatUIE(catUIEId);
        	Boolean respuesta = expedienteDelegate.actualizarCatDiscriminanteDeExpediente(expedienteDTO);

        	if(respuesta!=null){
				log.info("respuesta: " + respuesta);
			}else{
				log.info("respuesta: NULO");
}
			XStream converter=new XStream();
			converter.alias("boolean", java.lang.Boolean.class);
			String xml = converter.toXML(respuesta);
			escribir(response, xml,null);
			if(log.isDebugEnabled())
			{
				log.info(xml);
			}	               	
        	
        } catch (Exception e) {
            log.error(e.getMessage(), e);               	        	
        }   
		return null;
	}

	/**
	 * M&eacute;todo que consulta los expedientes de un funcionario
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarExpedientesPorFuncionario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("cargarGridExpedientesFuncionario - ejecuantdo action");
			List<ExpedienteDTO> expedientes = null;
			Boolean verHistorico = request.getParameter("verHistorico")!= null ? Boolean.parseBoolean(request.getParameter("verHistorico")): Boolean.FALSE;
			String reasignarExpediente = request.getParameter("reasignarExpediente");
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			Long idJerarquiaAsociadaAlFuncionario = NumberUtils.toLong(request.getParameter("idJerarquiaAsociadaAlFuncionario"),0);

			FuncionarioForm funcionarioForm = (FuncionarioForm)form;
			
			
			if(funcionarioForm.getFuncionarioDTO() != null){				
				
				FuncionarioDTO funcionarioDTO = funcionarioForm.getFuncionarioDTO();
				
				usuarioDTO.setFuncionario(funcionarioDTO);
				if(reasignarExpediente != null) {
					usuarioDTO.setAreaActual(new AreaDTO());
					usuarioDTO.getAreaActual().setBuscarEnJerarquia(Boolean.TRUE);
				}
				
				List <ValorDTO> estatusExpedientes = null;
				
				UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
				Roles rolAsociado = obtenEnumRol(usuarioRolDTO.getRol().getRolId());
				
				if(rolAsociado == Roles.DSE){
					estatusExpedientes = new ArrayList<ValorDTO>();
//					estatusExpedientes.add(new ValorDTO(EstatusExpediente.POR_ATENDER.getValorId()));
					estatusExpedientes.add(new ValorDTO(EstatusExpediente.EN_PROCESO.getValorId()));
					estatusExpedientes.add(new ValorDTO(EstatusExpediente.ABIERTO_EJECUCION.getValorId()));
					estatusExpedientes.add(new ValorDTO(EstatusExpediente.EN_ESPERA_DE_SENTENCIADO.getValorId()));
					estatusExpedientes.add(new ValorDTO(EstatusExpediente.EN_TRAMITE.getValorId()));
				}
				
				if(rolAsociado == Roles.COORDINADORAMPGENERAL){
					if(idJerarquiaAsociadaAlFuncionario > 0){
						usuarioDTO.getFuncionario().setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(idJerarquiaAsociadaAlFuncionario));
						usuarioDTO.setRolActivo(usuarioRolDTO.getRol().getNombreRol());
						UsuarioRolDTO usuarioRolDTO2=new UsuarioRolDTO();
						usuarioRolDTO2.setRol(usuarioRolDTO.getRol());
						Set<UsuarioRolDTO> setUSEROL=new HashSet<UsuarioRolDTO>();
						setUSEROL.add(usuarioRolDTO2);
						usuarioDTO.setUsuarioRoles(setUSEROL);
					}
				}
				
				expedientes = funcionarioDelegate.consultarExpedientesDelFuncionario(usuarioDTO, estatusExpedientes);
 				
				
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				writer.print("<rows>");		
				String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
				writer.print(paginacion);
	            
				for (ExpedienteDTO expedienteDTO : expedientes) {
					writer.print("<row id='"+expedienteDTO.getNumeroExpedienteId()+"'>");
					if (expedienteDTO.getCasoDTO() != null) {
						writer.print("<cell>"
								+ expedienteDTO.getCasoDTO().getNumeroGeneralCaso()
								+ "</cell>");
					} else {
						writer.print("<cell> -- </cell>");
					}
					
					writer.print("<cell>" + ((expedienteDTO.getNumeroExpediente() != null) ? expedienteDTO.getNumeroExpediente() : "---")	+ "</cell>");
										
					if (rolAsociado == Roles.DSE) {
						writer.print(datosSentenciaPorNumeroExpediente(expedienteDTO));
					}
					
					ValorDTO estatus = expedienteDTO.getEstatus();
					if (rolAsociado == Roles.DSE){
						String estatusNumExp = "---";
						if (expedienteDTO.getEstatusNumeroExpediente()!= null){
							estatusNumExp = expedienteDTO.getEstatusNumeroExpediente().getValor(); 
						}
						writer.print("<cell>"+ estatusNumExp +"</cell>");
					}else if (estatus != null) { 
						writer.print("<cell>" + ((estatus.getNombreCampo() != null) ? estatus.getNombreCampo() : "---")	+ "</cell>");
					} else {
						writer.print("<cell>---</cell>");							
					}					
					
					FuncionarioDTO responsable = expedienteDTO.getResponsableTramite();
					if (responsable != null) {
						writer.print("<cell>" + ((responsable.getClaveFuncionario() != null) ? responsable.getClaveFuncionario() : "---")	+ "</cell>");
						writer.print("<cell>" + ((responsable.getNombreCompleto() != null) ? responsable.getNombreCompleto() : "---")	+ "</cell>");
					} else {
						writer.print("<cell>---</cell>");
						writer.print("<cell>---</cell>");
					}
					
					DelitoDTO delito = expedienteDTO.getDelitoPrincipal();
					if(delito == null && expedienteDTO.getDelitos() != null && !expedienteDTO.getDelitos().isEmpty()){
						delito = expedienteDTO.getDelitos().get(0);
					}
					if(delito != null && delito.getCatDelitoDTO() != null){
						writer.print("<cell>"+delito.getCatDelitoDTO().getNombre()+"</cell>");
					}else{
						writer.print("<cell>-</cell>");
					}
					if(verHistorico){
						writer.print("<cell>");
						writer.print("<![CDATA[");
						writer.print("<input  type=\"button\" " +
											" name=\"btnVerHistorial" + expedienteDTO.getNumeroExpedienteId() + "\" " +
											" id=\"btnVerHistorial" + expedienteDTO.getNumeroExpedienteId() + "\" " +
											" value=\"Ver Historial\" " +
											" onclick=\"verHistorial(" + expedienteDTO.getNumeroExpedienteId() + ");\" " +
											" class=\"btn_Generico\" />");
						writer.print("]]>");						
						writer.print("</cell>");
					}
					writer.print("</row>");
				}
				
				writer.print("</rows>");
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	

	/**
	 * M&eacute;todo que consulta los hist&oacute;ricos de un expediente
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarHistoricoExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("cargarGridExpedientesFuncionario - ejecuantdo action");
			Long numeroExpedienteId = NumberUtils.toLong(request.getParameter("numeroExpediente"), 0L);		
			Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			HistoricoExpedienteDTO historicoExpedienteDTO = new HistoricoExpedienteDTO();
			ExpedienteDTO numeroExpedienteDTO = new ExpedienteDTO();
			numeroExpedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
			numeroExpedienteDTO.setExpedienteId(idExpediente);
			historicoExpedienteDTO.setNumeroExpediente(numeroExpedienteDTO);
			
			UsuarioRolDTO usuarioRolDTO=super.getUsuarioFirmado(request).getRolACtivo();
			Roles rolAsociado = obtenEnumRol(usuarioRolDTO.getRol().getRolId());
			if (rolAsociado.getValorId().equals(Roles.COORDINADOR_DE_POLICIA_MINISTERIAL.getValorId())){
				historicoExpedienteDTO.setConsultarSolicitudes(Boolean.TRUE);
			}
			
			List<HistoricoExpedienteDTO > lstHistoricoExpedienteDTO = historicoExpedienteDelegate.consultarHistoricoExpediente(historicoExpedienteDTO);

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

			for (HistoricoExpedienteDTO temp : lstHistoricoExpedienteDTO) {
			
				writer.print("<row id='" + temp.getHistoricoExpedienteId()  + "'>");
				if (temp.getNumeroExpediente() != null) {
					numeroExpedienteDTO = temp.getNumeroExpediente();
					writer.print("<cell>"
							+ (numeroExpedienteDTO.getNumeroExpediente() != null
									? numeroExpedienteDTO.getNumeroExpediente() 
									: " --- ")
							+ "</cell>");
				} else {
					writer.print("<cell> -- </cell>");
				}
				
				if (rolAsociado.getValorId().equals(Roles.COORDINADOR_DE_POLICIA_MINISTERIAL.getValorId())){
					writer.print("<cell>"+ (temp.getEsSolicitud() == true ? "SI": "NO") +"</cell>");
				}
				

				if (temp.getFuncionarioActual() != null) {
					FuncionarioDTO funcionarioDTO = temp.getFuncionarioActual();
					writer.print("<cell>"
							+ (funcionarioDTO.getClaveFuncionario() != null 
									? funcionarioDTO.getClaveFuncionario()
									: " --- ")
							+ "</cell>");
					writer.print("<cell>"
							+ (funcionarioDTO.getNombreCompleto() != null 
									? funcionarioDTO.getNombreCompleto()
									: " --- ")
							+ "</cell>");					
				} else {
					writer.print("<cell> -- </cell>");
					writer.print("<cell> -- </cell>");
				}
				
				if (temp.getFuncionarioAsigna() != null) {
					FuncionarioDTO funcionarioDTO = temp.getFuncionarioAsigna();
					writer.print("<cell>"
							+ (funcionarioDTO.getClaveFuncionario() != null 
									? funcionarioDTO.getClaveFuncionario()
									: " --- ")
							+ "</cell>");
					writer.print("<cell>"
							+ (funcionarioDTO.getNombreCompleto() != null 
									? funcionarioDTO.getNombreCompleto()
									: " --- ")
							+ "</cell>");					
				} else {
					writer.print("<cell> -- </cell>");
					writer.print("<cell> -- </cell>");
				}

				if (temp.getFuncionarioAnterior() != null) {
					FuncionarioDTO funcionarioDTO = temp.getFuncionarioAnterior();
					writer.print("<cell>"
							+ (funcionarioDTO.getClaveFuncionario() != null 
									? funcionarioDTO.getClaveFuncionario()
									: " --- ")
							+ "</cell>");
					writer.print("<cell>"
							+ (funcionarioDTO.getNombreCompleto() != null 
									? funcionarioDTO.getNombreCompleto()
									: " --- ")
							+ "</cell>");					
				} else {
					writer.print("<cell> -- </cell>");
					writer.print("<cell> -- </cell>");
				}				
				
				if (temp.getdFechaInicio() != null) {
					writer.print("<cell>"
							+ (temp.getdFechaInicio() != null ? DateUtils.formatear(temp.getdFechaInicio()) + " " + DateUtils.formatearHoraAm(temp.getdFechaInicio()) : "-")
							+ "</cell>");
				} else {
					writer.print("<cell> -- </cell>");
				}				

				if (temp.getdFechaFin() != null) {
					writer.print("<cell>"
							+ (temp.getdFechaFin() != null ? DateUtils.formatear(temp.getdFechaFin()) + " " + DateUtils.formatearHoraAm(temp.getdFechaFin()) : "-")
							+ "</cell>");
				} else {
					writer.print("<cell> -- </cell>");
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
	
	
	
	public ActionForward buscarExpedientesPotTipoConSP(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			log.info("EJECUTANDO ACTION BUSCAR EXPEDIENTESPORTIPOCONSP");
			
			Long institucionActual=0L;
			//Parametros para realizar busqueda 
			String catdelitoId = request.getParameter("catdelitId") != null ? request.getParameter("catdelitId"): "";
			String numeroExpediente = request.getParameter("numeroExpediente") != null ? request.getParameter("numeroExpediente"): "";
			String numeroCaso = request.getParameter("numeroCaso") != null ?request.getParameter("numeroCaso"): "";
			String fechaIni = request.getParameter("fechaIni")!= null ? request.getParameter("fechaIni"): "";
			String fechaFin = request.getParameter("fechaFin")!= null ? request.getParameter("fechaFin"): "";
			String nombre = request.getParameter("nombre")!= null ? request.getParameter("nombre"): "";
			String apPaterno = request.getParameter("apPaterno")!= null ? request.getParameter("apPaterno"): "";
			String apMaterno = request.getParameter("apMaterno")!= null ? request.getParameter("apMaterno"): "";
			String nombreOrganizacion = request.getParameter("nombreOrganizacion")!= null ? request.getParameter("nombreOrganizacion"): "";
			int opcionSeleccionada = Integer.parseInt(request.getParameter("tipoBusqueda")) - 1;
			
			String esBusquedaATP = request.getParameter("esBusquedaATP")!= null ? request.getParameter("esBusquedaATP"): "";
			String estatus = request.getParameter("estatus")!= null ? request.getParameter("estatus"): "";
			String anio = request.getParameter("anio")!= null ? request.getParameter("anio"): "";
			String nombreFun = request.getParameter("nombreFun")!= null ? request.getParameter("nombreFun"): "";
			String apPaternoFun = request.getParameter("apPaternoFun")!= null ? request.getParameter("apPaternoFun"): "";
			String apMaternoFun = request.getParameter("apMaternoFun")!= null ? request.getParameter("apMaternoFun"): "";
			
			
			//Se obtien el id rol activo para realizar la busqueda
			UsuarioDTO usuarioDTO = super.getUsuarioFirmado(request);
			String claveFuncionario = usuarioDTO.getFuncionario().getClaveFuncionario().toString();
			String rolId = usuarioDTO.getRolACtivo().getRol().getRolId().toString();
			String numPagina = new Integer(Integer.parseInt(request.getParameter("page"))-1).toString();
			String noRegistrosPorConsulta = request.getParameter("rows");
			String columnaOrd = request.getParameter("sidx") != null ? request.getParameter("sidx") : "";
			String direccionOrd = request.getParameter("sord");
			
			TipoDeBusquedaDeExpediente tipoBusqueda = TipoDeBusquedaDeExpediente.values()[opcionSeleccionada];
			//Configuración de parametros comunes para realizar la busqueda
			HashMap<String, String> valores = new HashMap<String, String>();
			valores.put("ai_iClaveFuncionario", claveFuncionario);
			valores.put("ai_rol_id", rolId);
			valores.put("ai_pagina", numPagina);
			valores.put("ai_registros", noRegistrosPorConsulta);
			valores.put("as_columnaOrd", columnaOrd);
			valores.put("as_direccionOrd", direccionOrd);
			
			switch(tipoBusqueda){
			case POR_DELITO:
				valores.put("ai_catdelito_id", catdelitoId);
				break;
			case POR_NUM_EXP: 
				valores.put("as_cNumeroExpediente", numeroExpediente);
				break;			
			case POR_NUM_CASO:
				valores.put("as_cNumeroCaso", numeroCaso);
				break;
			case POR_FECHAS:
				valores.put("as_fechaIni", fechaIni);	
				valores.put("as_fechaFin", fechaFin);
				break;
			case POR_ATP:
				valores.put("as_cNumeroExpediente", numeroExpediente);
				break;
			case POR_NOMBRE_PERSONA:
				valores.put("as_nombre", nombre);
				valores.put("as_apPaterno", apPaterno);
				valores.put("as_apMaterno", apMaterno);
				break;
			case POR_NOMBRE_ORGANIZACION:
				valores.put("as_nombre", nombreOrganizacion);
				break;
			case BUS_AVANZADA_DE_EXP:
				valores.put("as_fechaIni", fechaIni);	
				valores.put("as_fechaFin", fechaFin);
				valores.put("as_nombreOrg", nombreOrganizacion);
				valores.put("as_nombre", nombre);
				valores.put("as_apPaterno", apPaterno);
				valores.put("as_apMaterno", apMaterno);
				valores.put("as_cNumeroCaso", numeroCaso);
				valores.put("as_cNumeroExpediente", numeroExpediente);
				valores.put("ai_bexpedienteATP", esBusquedaATP);
				valores.put("ai_nanio", anio);
				valores.put("ai_catdelito_id", catdelitoId);
				valores.put("ai_estatus_val", estatus);
				valores.put("as_nombreFun", nombreFun);
				valores.put("as_apPaternoFun", apPaternoFun);
				valores.put("as_apMaternoFun", apMaternoFun);
				break;
			//Empiezan casos para PJ	
			case POR_NUM_EXP_PJ:
				valores.put("as_cNumeroExpediente", numeroExpediente);
				break;
			case POR_NOMBRE_PERSONA_PJ:
				valores.put("as_nombre", nombre);
				valores.put("as_apPaterno", apPaterno);
				valores.put("as_apMaterno", apMaterno);
				break;
			
			}
			
			List<ExpedienteViewDTO> listExpedienteDTOs = expedienteDelegate.consultarExpedientesConSP(tipoBusqueda, valores);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.print("<rows>");
			
			final PaginacionDTO pag = new PaginacionDTO();
			if(!listExpedienteDTOs.isEmpty() && listExpedienteDTOs.size() > 0){
				pag.setRows(Integer.parseInt( noRegistrosPorConsulta));
				pag.setTotalRegistros(Long.parseLong( listExpedienteDTOs.get(0).getTotalRegistros()));
				writer.print("<total>" + pag.getTotalPaginas() + "</total>");
				writer.print("<records>" + pag.getTotalRegistros() + "</records>");
			}else{
				writer.print("<total>" + 0 + "</total>");
				writer.print("<records>" + 0 + "</records>");
			}
			
			//Verificamos cual es la institucion actual traida de session
			institucionActual=usuarioDTO.getInstitucion().getConfInstitucionId();
			Instituciones insAct = Instituciones.getByValor(institucionActual);
			
			//	Se valida la institucion que no sea nula y que sea solo PG o PJ  
			//	ya que solo debe entrar al switch para esos dos casos
			if (insAct == null
					|| insAct.getValorId() == null
					|| (!insAct.getValorId().equals(
							Instituciones.PGJ.getValorId()) && !insAct
							.getValorId().equals(Instituciones.PJ.getValorId()))) {
				log.error("ERROR LA INSTITUCION NO ES PJ, NI PG,");
				throw new NSJPNegocioException(
						CodigoError.INFORMACION_PARAMETROS_ERRONEA);
			}
			//Se maneja el switch ya que las instituciones pueden aumentar
			switch(insAct){
            	case PJ: 
            		for (ExpedienteViewDTO loExp : listExpedienteDTOs) {
        				writer.print("<row id='" + loExp.getNumeroExpedienteId()  + "'>");
        					writer.print("<cell>" + loExp.getCasoId()+"</cell>");
        					writer.print("<cell>" + loExp.getNumeroGeneralCaso()+"</cell>");
        					writer.print("<cell>" + loExp.getExpedienteId()+"</cell>");
        					writer.print("<cell><![CDATA["+loExp.getNumeroExpediente()+"]]></cell>");
        					writer.print("<cell>" + loExp.getNombre()+"</cell>");
        					writer.print("<cell>" + loExp.getApPaterno()+"</cell>");
        					writer.print("<cell>" + loExp.getApMaterno()+"</cell>");
        					writer.print("<cell>" + loExp.getNombreCalidadInvolucrado()+"</cell>");
        					writer.print("<cell>" + loExp.getTribunal()+"</cell>");
        					writer.print("<cell>" + loExp.getEsConsulta()+"</cell>");
        				        					
        				writer.print("</row>");
        			}
            		break;
            	case PGJ:
            		for (ExpedienteViewDTO loExp : listExpedienteDTOs) {
        				writer.print("<row id='" + loExp.getNumeroExpedienteId()  + "'>");
        					writer.print("<cell>" + loExp.getCasoId()+"</cell>");
        					writer.print("<cell>" + loExp.getNumeroGeneralCaso()+"</cell>");
        					writer.print("<cell>" + loExp.getExpedienteId()+"</cell>");
        					writer.print("<cell><![CDATA["+loExp.getNumeroExpediente()+"]]></cell>");
        					writer.print("<cell>" + loExp.getNombreInvolucrado()+"</cell>");
        					writer.print("<cell>" + loExp.getNombreCalidadInvolucrado()+"</cell>");
        					writer.print("<cell>" + loExp.getDelito()+"</cell>");
        					writer.print("<cell>" + (loExp.getEsPrincipal() != null && 
        							loExp.getEsPrincipal().equals("1")? "SI": "NO")+"</cell>");
        					writer.print("<cell>" + loExp.getClaveFuncionario()+"</cell>");
        					writer.print("<cell>" + loExp.getNombreFuncionario()+"</cell>");
        					writer.print("<cell>" + loExp.getAreaFuncionario()+"</cell>");
        					writer.print("<cell>" + loExp.getEdificio()+"</cell>");
        					writer.print("<cell>" + loExp.getEstatus()+"</cell>");
        					writer.print("<cell>" + loExp.getEsConsulta()+"</cell>");
        					writer.print("<cell>" + loExp.getFechaApertura()+"</cell>");
        					writer.print("<cell>" + loExp.getIdArea()+"</cell>");
        					
        				writer.print("</row>");
        			}
            		break;
            }
           			
			writer.print("</rows>");
			writer.flush();
			writer.close();
			log.info("FINALIZO EJECUCION ACTION BUSCAR EXPEDIENTESPORTIPOCONSP");
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	
	public ActionForward consultarGeneralesDeHistorialDeExp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			//Parametros para realizar busqueda 
			String jerarquiaOrganizacional_id = request.getParameter("jerarquiaOrganizacional_id") != null ? request.getParameter("jerarquiaOrganizacional_id"): "";
			Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"), 0L);
			Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);
			
			ExpedienteViewDTO loExp = expedienteDelegate.consultarGeneralesDeHistorialDeExp(idNumeroExpediente);

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.print("<rows>");
				writer.print("<row id='" + loExp.getNumeroExpedienteId()  + "'>");
				//Numero de caso
				writer.print("<cell><![CDATA["+ 
								"<label class='vinculo' onclick='abrirVisorExpediente(\""+ loExp.getNumeroExpedienteId() + "\","+idExpediente+ ",\""+loExp.getNumeroExpediente() + "\"," + jerarquiaOrganizacional_id + ")'>"+ loExp.getNumeroGeneralCaso() +"</label>" +  
							 "]]></cell>");
				//Denunciante
				writer.print("<cell><![CDATA["+ 
						"<label class='vinculo' onclick='abrirVisorExpediente(\""+ loExp.getNumeroExpedienteId() + "\","+idExpediente+ ",\""+loExp.getNumeroExpediente()+ "\"," + jerarquiaOrganizacional_id + ")'>"+ (loExp.getDenunciate() != null && !loExp.getDenunciate().isEmpty() &&
								!loExp.getDenunciate().equals("-")? loExp.getDenunciate(): "An&oacute;nimo") +"</label>" +  
					 "]]></cell>");
				
				//Delito principal
				writer.print("<cell><![CDATA["+ 
						(!loExp.getDelito().equals("-")? "<label class='vinculo' onclick='abrirVisorExpediente(\""+ loExp.getNumeroExpedienteId() + "\","+idExpediente+ ",\""+loExp.getNumeroExpediente()+ "\"," + jerarquiaOrganizacional_id + ")'>"+ loExp.getDelito() +"</label>": "-")+  
					 "]]></cell>");
				
				//Tipo de denuncia
				writer.print("<cell><![CDATA["+
						(!loExp.getTipoDenuncia().equals("-")? "<label class='vinculo' onclick='abrirVisorExpediente(\""+ loExp.getNumeroExpedienteId() + "\","+idExpediente+ ",\""+loExp.getNumeroExpediente() + "\"," + jerarquiaOrganizacional_id + ")'>"+ loExp.getTipoDenuncia() +"</label>": "-")+
					 "]]></cell>");
				
				writer.print("</row>");
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}

	public ActionForward consultarDetalleDeNumerosExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Parametros para realizar busqueda 
		Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);
		String idsAreas= request.getParameter("idsAreas");
		List<ExpedienteDTO> loExpedientes = null;
		try {
			
			//creamos la lista de los IDs seleccionados
			List<Long> idsJerarquiasOrganizacional =new ArrayList<Long>();
			String[] idAreasFiltroExp =null;
			
			if(idsAreas != null && idsAreas.length()>0){
				idAreasFiltroExp = idsAreas.split(",");
				for (String idArea: idAreasFiltroExp)
					idsJerarquiasOrganizacional.add(Long.parseLong(idArea));//agregamos el ID correspondiente			
			}		
			
			
			loExpedientes = expedienteDelegate.consultarNumerosExpedientesPorIdExpediente(idExpediente,idsJerarquiasOrganizacional);		
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
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
			
			for (ExpedienteDTO loExp : loExpedientes) {
					writer.print("<row id='"+loExp.getNumeroExpedienteId()+"'>");
					writer.print("<cell>" + (loExp.getFechaAperturaNumeroExp() != null ? 
							DateUtils.formatear(loExp.getFechaAperturaNumeroExp()) + " " + DateUtils.formatearHoraAm(loExp.getFechaAperturaNumeroExp()): "-")+"</cell>");					
						writer.print("<cell>" + (loExp.getArea().getNombre() != null ? loExp.getArea().getNombre(): "-")+"</cell>");					
						writer.print("<cell>" + (loExp.getNumeroExpediente() != null ? loExp.getNumeroExpediente(): "-")+"</cell>");					
						writer.print("<cell>" + (loExp.getResponsableTramite() != null && 
								loExp.getResponsableTramite().getNombreCompleto() != null ?
								loExp.getResponsableTramite().getNombreCompleto(): "-")+"</cell>");					
						writer.print("<cell>" + (loExp.getEstatusNumeroExpediente() != null &&
								loExp.getEstatusNumeroExpediente().getValor() != null?
										loExp.getEstatusNumeroExpediente().getValor(): "-")+"</cell>");					
					writer.print("</row>");	
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
        return null;
		
	}
	
	
	public ActionForward consultarProcesoDelExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			
			//Parametros para realizar busqueda 
			Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);
			Long idCaso = NumberUtils.toLong(request.getParameter("idCaso"), 0L);

			
			List<DetencionDTO> loDetenidos = detencionDelegate.consultarDetencionesPorExpedienteId(idExpediente);
			
			
			SolicitudDTO solFiltro = new SolicitudDTO();
			solFiltro.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.AUDIENCIA.getValorId()));
			ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
			loExpedienteDTO.setExpedienteId(idExpediente);			
			solFiltro.setExpedienteDTO(loExpedienteDTO);
			
			List<SolicitudDTO> listaSolicitudesAudiencia = solicitudDelegate.consultarSolicitudesPorFiltro(solFiltro,null, null);
			
			List<Long> idsAreaPM = new ArrayList<Long>();
			idsAreaPM.add(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong());
			List<ExpedienteDTO> loExpedientesPM = expedienteDelegate.consultarNumerosExpedientesPorIdExpediente(idExpediente,idsAreaPM);
			
			
			List<Long> idsTiposSolicitud =new ArrayList<Long>();
			List<Long> idNumerosDeExpedientes =new ArrayList<Long>();
			List<ExpedienteDTO> loExpedientes = null;
			
			idsTiposSolicitud.add(TiposSolicitudes.ATENCION_PSICOLOGICA.getValorId());	
			idsTiposSolicitud.add(TiposSolicitudes.TRABAJO_SOCIAL.getValorId());	
			idsTiposSolicitud.add(TiposSolicitudes.ATENCION_JURIDICA.getValorId());	
			
			loExpedientes = expedienteDelegate.consultarNumerosExpedientesPorIdExpediente(idExpediente,null);	
			for (ExpedienteDTO loExpediente : loExpedientes) {
				idNumerosDeExpedientes.add(loExpediente.getNumeroExpedienteId());
			}
			
			List<SolicitudDTO> loExpedientesUAVD = solicitudDelegate.consultarSolicitudesPorTipoYNumeroExps(idCaso, idsTiposSolicitud);


			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.print("<rows>");
				writer.print("<row>");
				
				//Policia Ministerial
				writer.print("<cell><![CDATA["+ 
						(loExpedientesPM != null && loExpedientesPM.size() > 0 ? 
								"<label class='vinculo' onclick='consultaExpedientesPM()'>SI</label>"
								: "NO") +  
					 "]]></cell>");
				
				//Audiencias
				writer.print("<cell><![CDATA["+ 
						(listaSolicitudesAudiencia != null && listaSolicitudesAudiencia.size() > 0 ? 
								"<label class='vinculo' onclick='consultaAudiencias()'>SI</label>"
								: "NO") +  
					 "]]></cell>");
				
				//Detenidos
				writer.print("<cell><![CDATA["+ 
						(loDetenidos != null && loDetenidos.size() > 0 ? 
								"<label class='vinculo' onclick='consultaDetenidos()'>SI</label>"
								: "NO") +  
					 "]]></cell>");
				//Visitaduria
				writer.print("<cell><![CDATA["+ 
						"NO" +  
					 "]]></cell>");
				//UAVD
				writer.print("<cell><![CDATA["+ 
						(loExpedientesUAVD != null && loExpedientesUAVD.size() > 0 ? 
								"<label class='vinculo' onclick='consultaExpedientesUAVD()'>SI</label>"
								: "NO") +  
					 "]]></cell>");
				
				writer.print("</row>");
								
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			log.info(e.getCause(), e);

		}
		return null;
	}
	
	
	public ActionForward consultarDetencionesPorExpediente(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Parametros para realizar busqueda 
		Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);
		try {
			List<DetencionDTO> loDetenidos = detencionDelegate.consultarDetencionesPorExpedienteId(idExpediente);		
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
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
			
			for (DetencionDTO detencion : loDetenidos) {
					writer.print("<row id='"+detencion.getDetencionId()+"'>");
					
					writer.print("<cell>" + (detencion.getInvolucradoDTO() != null && detencion.getInvolucradoDTO().getNombreCompleto() != null ? 
							detencion.getInvolucradoDTO().getNombreCompleto(): "-")+"</cell>");
					
					writer.print("<cell>" + (detencion.getFechaInicioDetencion() != null ? 
							DateUtils.formatear(detencion.getFechaInicioDetencion()): "-")+"</cell>");					
					
					writer.print("<cell>" + (detencion.getFechaInicioDetencion() != null ? 
							DateUtils.formatearHoraAm(detencion.getFechaInicioDetencion()): "-")+"</cell>");
					
					
					writer.print("<cell>" + (detencion.getFechaFinDetencion() != null ? 
							DateUtils.formatear(detencion.getFechaFinDetencion()): "-")+"</cell>");					
					
					writer.print("<cell>" + (detencion.getFechaFinDetencion() != null ? 
							DateUtils.formatearHoraAm(detencion.getFechaFinDetencion()): "-")+"</cell>");
					
				writer.print("</row>");	
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
        return null;
		
	}
	
	
	public ActionForward consultarAudienciasSolicitadasPorIdExpediente(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);

			
			SolicitudDTO solFiltro = new SolicitudDTO();
			solFiltro.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.AUDIENCIA.getValorId()));
			ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
			loExpedienteDTO.setExpedienteId(idExpediente);			
			solFiltro.setExpedienteDTO(loExpedienteDTO);
			
			List<SolicitudDTO> listaSolicitudes = solicitudDelegate.consultarSolicitudesPorFiltro(solFiltro,null, null);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaSolicitudes != null
					&& !listaSolicitudes.isEmpty()) {
				for (SolicitudDTO loSolicitud : listaSolicitudes) {

					//id
					writer.print("<row id='"
							+ loSolicitud.getDocumentoId() + "'>");
					
					//Expediente asociado
					writer.print("<cell>"
							+ (loSolicitud.getExpedienteDTO() != null  &&
									loSolicitud.getExpedienteDTO().getNumeroExpediente() != null ? loSolicitud.getExpedienteDTO().getNumeroExpediente(): "-")
							+ "</cell>");

					//Solicitante
					writer.print("<cell>"
							+ (loSolicitud.getNombreSolicitante() != null ? loSolicitud
									.getNombreSolicitante() : "-") + "</cell>");
					//Area
					writer.print("<cell>"
							+ (loSolicitud.getNombreAreaOrigen() != null ? loSolicitud.getNombreAreaOrigen() : "-") + "</cell>");

					//Fecha de solicitud
					writer.print("<cell>"
							+ DateUtils.formatear(loSolicitud
									.getFechaCreacion())
							+ " "
							+ DateUtils.formatearHoraAm(loSolicitud
									.getFechaCreacion()) + "</cell>");
					
					//En este campo se guarda el Tipo de audiencia
					writer.print("<cell>" + (loSolicitud.getObservaciones() != null ?
							loSolicitud.getObservaciones(): "-") + "</cell>");
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
	
	public ActionForward obtenDetalleDeCanalizacionDeNumeroExpediente(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
			String xml; 
		try {
			Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"), 0L);

			int respuesta = expedienteDelegate.obtenDetalleDeCanalizacionDeNumeroExpediente(idNumeroExpediente);
			xml = "<exito><respuesta>" + respuesta + "</respuesta></exito>";
			log.info(xml);
			escribirRespuesta(response, xml);
		} catch (Exception e) {
			xml = "<error></error>";
			log.info(xml);
		    log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	
	public ActionForward consultarDetalleDeSolicitudesUAVD(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Parametros para realizar busqueda 
		Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);
		String idsAreas= request.getParameter("idsAreas");
		List<ExpedienteDTO> loExpedientes = null;
		try {
			
			//creamos la lista de los IDs seleccionados
			List<Long> idsJerarquiasOrganizacional =new ArrayList<Long>();
			String[] idAreasFiltroExp =null;
			
			if(idsAreas != null && idsAreas.length()>0){
				idAreasFiltroExp = idsAreas.split(",");
				for (String idArea: idAreasFiltroExp)
					idsJerarquiasOrganizacional.add(Long.parseLong(idArea));//agregamos el ID correspondiente			
			}		
			
			
			loExpedientes = expedienteDelegate.consultarNumerosExpedientesPorIdExpediente(idExpediente,idsJerarquiasOrganizacional);		
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
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
			
			for (ExpedienteDTO loExp : loExpedientes) {
					writer.print("<row id='"+loExp.getNumeroExpedienteId()+"'>");
					writer.print("<cell>" + (loExp.getFechaAperturaNumeroExp() != null ? 
							DateUtils.formatear(loExp.getFechaAperturaNumeroExp()) + " " + DateUtils.formatearHoraAm(loExp.getFechaAperturaNumeroExp()): "-")+"</cell>");					
						writer.print("<cell>" + (loExp.getArea().getNombre() != null ? loExp.getArea().getNombre(): "-")+"</cell>");					
						writer.print("<cell>" + (loExp.getNumeroExpediente() != null ? loExp.getNumeroExpediente(): "-")+"</cell>");					
						writer.print("<cell>" + (loExp.getResponsableTramite() != null && 
								loExp.getResponsableTramite().getNombreCompleto() != null ?
								loExp.getResponsableTramite().getNombreCompleto(): "-")+"</cell>");					
						writer.print("<cell>" + (loExp.getEstatusNumeroExpediente() != null &&
								loExp.getEstatusNumeroExpediente().getValor() != null?
										loExp.getEstatusNumeroExpediente().getValor(): "-")+"</cell>");					
					writer.print("</row>");	
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
        return null;
		
	}


	
	public ActionForward consultarSolicitudesPorTipoYNumeroExps(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			//Parametros para realizar busqueda 
			Long idCaso = NumberUtils.toLong(request.getParameter("idCaso"), 0L);

			String idsTipoSolicitud= request.getParameter("idsTiposSolicitud");
			
			//creamos la lista de los IDs 
			List<Long> idsTiposSolicitud =new ArrayList<Long>();
			String[] idAreasFiltroExp =null;
			
			if(idsTipoSolicitud != null && idsTipoSolicitud.length()>0){
				idAreasFiltroExp = idsTipoSolicitud.split(",");
				for (String idTipoSolicitud: idAreasFiltroExp)
					idsTiposSolicitud.add(Long.parseLong(idTipoSolicitud));//agregamos el ID correspondiente	
			}
			
			List<SolicitudDTO> listaSolicitudes = solicitudDelegate.consultarSolicitudesPorTipoYNumeroExps(idCaso, idsTiposSolicitud);
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (listaSolicitudes != null && !listaSolicitudes.isEmpty()) {
				for (SolicitudDTO solicitudDTO : listaSolicitudes) {

					//id
					writer.print("<row id='"
							+ solicitudDTO.getDocumentoId() + "'>");
					
					//Fecha de creacion del numero de expediente
					writer.print("<cell>"
							+ (solicitudDTO.getExpedienteDTO() != null  &&
									solicitudDTO.getExpedienteDTO().getFechaAperturaNumeroExp() != null ? DateUtils.formatear(solicitudDTO.getExpedienteDTO().getFechaAperturaNumeroExp()) + " " + 
											DateUtils.formatearHoraAm(solicitudDTO.getExpedienteDTO().getFechaAperturaNumeroExp()): "-")
									+ "</cell>");
					//Numero de Expediente asociado
					writer.print("<cell>"
							+ (solicitudDTO.getExpedienteDTO() != null  &&
									solicitudDTO.getExpedienteDTO().getNumeroExpediente() != null ? solicitudDTO.getExpedienteDTO().getNumeroExpediente(): "-")
							+ "</cell>");

					//Funcionario
					writer.print("<cell>"
							+ (solicitudDTO.getExpedienteDTO() != null &&
									solicitudDTO.getExpedienteDTO().getResponsableTramite() != null &&
									solicitudDTO.getExpedienteDTO().getResponsableTramite().getNombreCompleto() != null ?
									solicitudDTO.getExpedienteDTO().getResponsableTramite().getNombreCompleto():"-") + "</cell>");
					//Estatus
					writer.print("<cell>"+ (solicitudDTO.getExpedienteDTO() != null &&
							solicitudDTO.getExpedienteDTO().getEstatusNumeroExpediente() != null &&
							solicitudDTO.getExpedienteDTO().getEstatusNumeroExpediente().getValor() != null ?
							solicitudDTO.getExpedienteDTO().getEstatusNumeroExpediente().getValor() : "-"
							) + "</cell>");
					
					
					//Tipo de solicitud
					writer.print("<cell>"
							+ (solicitudDTO.getTipoSolicitudDTO()!= null && solicitudDTO.getTipoSolicitudDTO().getValor() != null ?
									solicitudDTO.getTipoSolicitudDTO().getValor(): "-") + "</cell>");
					
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
	
	
	
	
	public ActionForward consultarBitacoraEstatusNumExpedientePorIdExpediente(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			//Parametros para realizar busqueda 
			Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"), 0L);
			List<BitacoraEstatusNumExpedienteDTO> loRegistrosEnBitacora = null;
						
			loRegistrosEnBitacora = expedienteDelegate.consultarBitacoraEstatusNumExpedientePorIdExpediente(idExpediente);	
						
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");
			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (loRegistrosEnBitacora != null && !loRegistrosEnBitacora.isEmpty()) {
				for (BitacoraEstatusNumExpedienteDTO loRegistroDTO : loRegistrosEnBitacora) {

					//id
					writer.print("<row id='"
							+ loRegistroDTO.getBitacoraEstatusNumExpedienteId() + "'>");
					
					//Fecha de movimiento
					writer.print("<cell>"
							+ (loRegistroDTO.getFechaCreacion() != null  &&
									loRegistroDTO.getFechaCreacion() != null ? DateUtils.formatear(loRegistroDTO.getFechaCreacion()) + " " + 
											DateUtils.formatearHoraAm(loRegistroDTO.getFechaCreacion()): "-")
											
											+ "</cell>");
					//Area en donde se registro el movimiento
					writer.print("<cell>"
							+ (loRegistroDTO.getJerarquiaOrganizacional() != null  &&
									loRegistroDTO.getJerarquiaOrganizacional().getNombre() != null ? loRegistroDTO.getJerarquiaOrganizacional().getNombre(): "-")
											
											+ "</cell>");
					//Numero de Expediente asociado
					writer.print("<cell>"
							+ (loRegistroDTO.getExpediente() != null  &&
									loRegistroDTO.getExpediente().getNumeroExpediente() != null ? loRegistroDTO.getExpediente().getNumeroExpediente(): "-")
							+ "</cell>");

					//Funcionario
					writer.print("<cell>"
							+ (loRegistroDTO.getFuncionario() != null &&
									loRegistroDTO.getFuncionario().getNombreCompleto() != null ?
											loRegistroDTO.getFuncionario().getNombreCompleto():"-") + "</cell>");
					
					//Estatus
					writer.print("<cell>"+ (loRegistroDTO.getEstatus() != null &&
							loRegistroDTO.getEstatus().getValor() != null ?
									loRegistroDTO.getEstatus().getValor(): "-"
					) + "</cell>");
					
					//Tipo de Actividad
					writer.print("<cell>"+ (loRegistroDTO.getTipoActividad() != null &&
							loRegistroDTO.getTipoActividad().getValor() != null ?
									loRegistroDTO.getTipoActividad().getValor(): "-"
					) + "</cell>");
					
					//Tipo de documento
					writer.print("<cell>"+ (loRegistroDTO.getTipoDocumento() != null &&
							loRegistroDTO.getTipoDocumento().getValor() != null ?
							loRegistroDTO.getTipoDocumento().getValor(): "-"
							) + "</cell>");
					
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
	 * Metodo que realiza la consulta de solicitudes periciales relacionadas con
	 * un numero de causa, originalemte usado en el visor de elementos.
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws NSJPNegocioException En caso de obtener una exception
	 */
	public ActionForward consultarSolicitudesPoliciaMinisterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws NSJPNegocioException
	{
		try{
			
			Long idExpedienteop = NumberUtils.toLong(request.getParameter("idExpedienteop"));			
			log.debug("idExpedienteop ... " + idExpedienteop);
			
			SolicitudDTO solFiltro = new SolicitudDTO();
			solFiltro.setTipoSolicitudDTO(new ValorDTO(TiposSolicitudes.POLICIA_MINISTERIAL.getValorId()));
			
			ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
			loExpedienteDTO.setExpedienteId(idExpedienteop);			
			solFiltro.setExpedienteDTO(loExpedienteDTO);
			
			List<SolicitudDTO> solicitudes = null;
			solicitudes = solicitudDelegate.consultarSolicitudesPorFiltro(solFiltro,null, null);
			
			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			//Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
			
			if (solicitudes != null && !solicitudes.isEmpty()) {
				for(SolicitudDTO solicitud : solicitudes){
					
					writer.print("<row id='"+solicitud.getDocumentoId() + "'>");							
					
					writer.print("<cell>" + (solicitud.getUsuarioSolicitante()!=null?
							solicitud.getUsuarioSolicitante().getNombreCompleto():"-")
							+  "</cell>");
					
					writer.print("<cell>" + DateUtils.formatear(solicitud.getFechaCreacion())
							+  "</cell>");
					
					writer.print("<cell>" + (solicitud.getDestinatario()!=null?
							solicitud.getDestinatario().getNombreCompleto():"")
							+  "</cell>");
					
					
					writer.print("</row>");				
				}				
			}
						
			writer.print("</rows>");
			writer.flush();
			writer.close();
						
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
}

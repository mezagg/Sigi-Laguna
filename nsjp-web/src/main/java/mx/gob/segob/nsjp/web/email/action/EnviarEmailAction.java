package mx.gob.segob.nsjp.web.email.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.catalogo.CatDiscriminanteDelegate;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.parametro.ParametroDelegate;
import mx.gob.segob.nsjp.dto.ParametroDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.email.form.EmailForm;
import mx.gob.segob.nsjp.web.solicitud.form.SolicitudAudienciaForm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class EnviarEmailAction extends GenericAction {

	private static final Logger logger = Logger.getLogger(EnviarEmailAction.class);
	

	@Autowired
	private ParametroDelegate parametroDelegate;
	@Autowired
	private AudienciaDelegate audienciaDelegate;
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	@Autowired
	protected CatalogoDelegate catDelegate;
	@Autowired
	private CatDiscriminanteDelegate catDiscriminanteDelegate;
		
	/**
	 * Metodo usado para notificar via correo electronico a los diferentes encargados sobre
	 *  la conclusion de resolucion de audiencia (PJ)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward enviarCorreoAvisoAudienciaConclusion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			logger.info("inicia metodo envio de correo Conclusion resolucion de auduencia");
			String idAudiencia = request.getParameter("idAudiencia");
			
			//Obtenemos datos generales para el envio de la notificacion
			EmailForm emailForm = cargaDatosGeneralesEnvio();
			
			//Obtenemos datos de la audiencia
			AudienciaDTO audiencia = new AudienciaDTO();
			audiencia.setId(Long.parseLong(idAudiencia));
			audiencia = audienciaDelegate.obtenerAudiencia(audiencia);
			
			//Obtenemos datos especificos de la resolucion de la audiencia
			ParametroDTO subject = parametroDelegate.consultarParametro(Parametros.SUBJECT_EMAIL_CON_RES_AUD);
			ParametroDTO content = parametroDelegate.consultarParametro(Parametros.CONTENT_EMAIL_CON_RES_AUD);
			
			//Construimos el cuerpo del la notificacion
			String cuerpo = construyeTextoConclusionAudiencia(content.getValor(), audiencia);
			
			//Obtenemos los destinatarios de la notificacion
			List<String> destinos = obtenerDestinatariosConclusionAudiencia(audiencia, request);
			
			//Seteamos asunto, texto y destinatarios de la notificacion
			emailForm.setSubject(subject != null ? subject.getValor() : "Notificaciones SIGI");
			emailForm .setContext(cuerpo);
			emailForm.setTo(destinos);
			
			//Enviamos la notificacion, solo si existe algun destino, usuario y contraseña 
			if(destinos.size() > 0 && emailForm.getUser() != null && emailForm.getPass() != null){
				EnviarEmail email = new EnviarEmail();
				email.enviarCorreoAvisoConclusionAudiendicia(emailForm);
			}
			

			
		} catch (Exception e) {
			logger.error("Error al tratar de enviar el correo, EnviarEmailAction - enviarCorreoAvisoAudienciaConclusion ", e);
		}
		return mapping.findForward("success");
	}
	
	/**
	 * Metodo usado para notificar via correo electronico a los diferentes encargados sobre la solicitud
	 * de una audiencia (PG) 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward enviarCorreoAvisoAudienciaSolicitud(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			logger.info("inicia metodo envio de correo solicitud de auduencia");
			SolicitudAudienciaForm forma = (SolicitudAudienciaForm) form;
			
			//Obtenemos datos generales para el envio de la notificacion
			EmailForm emailForm = cargaDatosGeneralesEnvio();
			
			//Obtenemos datos de la solicitud
			FuncionarioDTO funSolicitante = funcionarioDelegate.obtenerFuncionarioPorNombreCompleto(forma.getNombreDelSolicitante());

			FuncionarioDTO funDestino = null;
			if(forma.getNombreCompletoDestinatarioInstExterna() != null && forma.getTribunal() != null){
				List<FuncionarioDTO> func = funcionarioDelegate.consultarFuncionariosXTribunal(forma.getTribunal(),Instituciones.PJ);
				for(FuncionarioDTO f : func){
					if(f.getNombreCompleto().equalsIgnoreCase(forma.getNombreCompletoDestinatarioInstExterna().replace(':', ' ')))
						funDestino = f;
				}
			}			
			
			//Obtenemos datos especificos de la solicitud de la audiencia
			ParametroDTO subject = parametroDelegate.consultarParametro(Parametros.SUBJECT_EMAIL_SOLICITUD_AUD);
			ParametroDTO content = parametroDelegate.consultarParametro(Parametros.CONTENT_EMAIL_SOLICITUD_AUD);
			
			//Construimos el cuerpo del la notificacion
			String cuerpo = construyeTextoSolicitudAudiencia(content.getValor(), forma, funDestino, funSolicitante);
			
			//Obtenemos los destinatarios de la notificacion
			List<String> destinos = new ArrayList<String>();
			if(funSolicitante != null && funSolicitante.getEmail() != null)
				destinos.add(funSolicitante.getEmail());
			if(funDestino != null && funDestino.getEmail() != null)
				destinos.add(funDestino.getEmail());
			
			//Seteamos asunto, texto y destinatarios de la notificacion
			emailForm.setSubject(subject != null ? subject.getValor() : "Notificaciones SIGI");
			emailForm .setContext(cuerpo);
			emailForm.setTo(destinos);
			
			//Enviamos la notificacion, solo si existe algun destinatario, usuario y contraseña 
			if(destinos.size() > 0 && emailForm.getUser() != null && emailForm.getPass() != null){
				EnviarEmail email = new EnviarEmail();
				email.enviarCorreoAvisoConclusionAudiendicia(emailForm);	
			}
			

			
		} catch (Exception e) {
			logger.error("Error al tratar de enviar el correo, EnviarEmailAction - enviarCorreoAvisoAudienciaSolicitud ", e);
		}
		return mapping.findForward("success");
	}
	
	
	/**
	  * Metodo que carga los datos generales para el envio de 
	  * notificaciones: usuario, contraseña, servidor, puerto, etc..
	  * @return EmaiForm
	  */
	 public EmailForm cargaDatosGeneralesEnvio (){
		 
		 EmailForm form = new EmailForm();
		 
	  	 try {
	  		 
		 	ParametroDTO user = parametroDelegate.consultarParametro(Parametros.USER_EMAIL_NOTIFICACIONES);
	  	 	ParametroDTO pass = parametroDelegate.consultarParametro(Parametros.PASSWORD_EMAIL_NOTIFICACIONES);
			ParametroDTO host = parametroDelegate.consultarParametro(Parametros.HOST_EMAIL_NOTIFICACIONES);
			ParametroDTO port = parametroDelegate.consultarParametro(Parametros.PORT_EMAIL_NOTIFICACIONES);
			ParametroDTO protocol = parametroDelegate.consultarParametro(Parametros.PROTOCOL_EMAIL_NOTIFICACIONES);
			ParametroDTO auth = parametroDelegate.consultarParametro(Parametros.AUTH_EMAIL_NOTIFICACIONES);
			ParametroDTO starttls = parametroDelegate.consultarParametro(Parametros.STARTTLS_EMAIL_NOTIFICACIONES);
			
			form.setUser(user != null ? user.getValor() : null);
			form.setPass(pass != null ? pass.getValor() : null);
			form.setHost(host != null ? host.getValor() : null);
			form.setPort(port != null ? port.getValor() : null);
			form.setProtocol(protocol != null ? protocol.getValor() : null);
			form.setAuth(auth != null ? auth.getValor() : null);
			form.setStarttls(starttls != null ? starttls.getValor() : null);
			
		} catch (NSJPNegocioException ne) {
			logger.error("Error al tratar de cargar los datos generales del envio de notificaciones EnviarEmailAction - cargaDatosGeneralesEnvio ", ne);
		} catch(Exception e){
			logger.error("Error al tratar de cargar los datos generales del envio de notificaciones EnviarEmailAction - cargaDatosGeneralesEnvio ", e);
		}
	  	 
	  	return form;
	  	
	 }
	 
	
	/**
	 * Metodo que construye el texto a enviar en la conclusion de la resolucion de audiencia
	 * @param texto
	 * @param dto
	 * @return String
	 */
	 public String construyeTextoConclusionAudiencia(String texto, AudienciaDTO dto){
		   
		   //Obtenemos los datos del dto enviado
		   String id = String.valueOf(dto.getId());
		   
		   String tipo = dto.getTipoAudiencia() != null && 
				   dto.getTipoAudiencia().getValor() != null ? 
						   dto.getTipoAudiencia().getValor() : "Tipo no encontrado";
						   
		   String caracter = dto.getCaracter() != null ? dto.getCaracter() : "Caracter no encontrado";
		   
		   String numCaso = dto.getExpediente() != null && 
				   dto.getExpediente().getCasoDTO() != null && 
				   dto.getExpediente().getCasoDTO().getNumeroGeneralCaso() != null ? 
						   dto.getExpediente().getCasoDTO().getNumeroGeneralCaso() : "No. de caso no encontrado";
						   
		   String numCausa = dto.getCausa() != null && 
				   dto.getCausa().getNumeroExpediente() != null ? 
						   dto.getCausa().getNumeroExpediente() : "No. de causa no encontrado";
		  
		   String fchIn = dto.getSolicitud() != null && dto.getSolicitud().getAudiencia() != null &&
				   dto.getSolicitud().getAudiencia().getStrFechaInicio() != null ?
						   dto.getSolicitud().getAudiencia().getStrFechaInicio() : "Fecha no encontrada";
		   String hrIn = dto.getSolicitud() != null && dto.getSolicitud().getAudiencia() != null &&
				   dto.getSolicitud().getAudiencia().getStrHoraInicio() != null ?
						   dto.getSolicitud().getAudiencia().getStrHoraInicio() : "Hora no encontrada";
		   
		   String fchFin = dto.getSolicitud() != null && dto.getSolicitud().getAudiencia() != null &&
				   dto.getSolicitud().getAudiencia().getStrFechaFin() != null ?
						   dto.getSolicitud().getAudiencia().getStrFechaFin() : "Fecha no encontrada";
		   String hrFin = dto.getSolicitud() != null && dto.getSolicitud().getAudiencia() != null &&
				   dto.getSolicitud().getAudiencia().getStrHoraFin() != null ?
						   dto.getSolicitud().getAudiencia().getStrHoraFin() : "Hora no encontrada";
		   
		   String sala;		   
		   if(dto.getSala() != null){
			   sala = dto.getSala().getNombreSala() != null ? "la sala <B>" + dto.getSala().getNombreSala() + "</B>" : "<B>Sala no encontrada</B>";
		   }else  if(dto.getSalaTemporal() != null){
			   sala = dto.getSalaTemporal().getDomicilioSala() != null ? "<B>" + dto.getSalaTemporal().getDomicilioSala() + "</B>" : "<B>Ubicacion no encontrada</B>";
		   }else{
			   sala = "<B>Sala no encontrada</B>";
		   }
		   
		   String edo = dto.getEstatusAudiencia() != null && dto.getEstatusAudiencia().getValor() != null ?
				   dto.getEstatusAudiencia().getValor() : "Estado no encontrado";
			
		   // Remplazamos los datos
		   texto = StringUtils.replace(texto, ConstantesGenerales.ID_AUDIENCIA_TAG_BUSQUEDA, "<B>" + id + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.ID_AUDIENCIA_TAG_BUSQUEDA, "<B>" + id + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.TIPO_AUDIENCIA_TAG_BUSQUEDA, "<B>" + tipo + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.CARACTER_AUDIENCIA_TAG_BUSQUEDA, "<B>" + caracter + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.NUM_CASO_AUDIENCIA_TAG_BUSQUEDA, "<B>" + numCaso + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.NUM_CAUSA_AUDIENCIA_TAG_BUSQUEDA, "<B>" + numCausa + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.FCH_INICIO_AUDIENCIA_TAG_BUSQUEDA, "<B>" + fchIn + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.HR_INICIO_AUDIENCIA_TAG_BUSQUEDA, "<B>" + hrIn + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.SALA_AUDIENCIA_TAG_BUSQUEDA, sala);
		   texto = StringUtils.replace(texto, ConstantesGenerales.FCH_FIN_AUDIENCIA_TAG_BUSQUEDA, "<B>" + fchFin + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.HR_FIN_AUDIENCIA_TAG_BUSQUEDA, "<B>" + hrFin + "</B>");
		   texto = StringUtils.replace(texto, ConstantesGenerales.EDO_AUDIENCIA_TAG_BUSQUEDA, "<B>" + edo + "</B>");
		   
		   
		   
		   return texto;
	   }
	 
	 /**
	  * Metodo que construye el texto a enviar de la solicitud de audiencia
	  * @param texto
	  * @param forma
	  * @return String
	  */
	 public String construyeTextoSolicitudAudiencia(String texto, SolicitudAudienciaForm forma, 
			 FuncionarioDTO funDestino, FuncionarioDTO funSol){
		 
		 try {
			 
			 String destinatario = funDestino.getNombreCompleto() != null ? 
					 funDestino.getNombreCompleto() : "Destinatario no encontrado";
			 
			 List<CatalogoDTO> catalogo;
			 
			 String distrito = "Distrito no encontrado";
			 catalogo = catDelegate.recuperarCatalogo(Catalogos.DISTRITOS);
			 if(catalogo != null && catalogo.size() > 0){
				 for(CatalogoDTO cat : catalogo){
					 if(cat.getClave().compareTo(forma.getDistrito()) == 0){
						 distrito = cat.getValor();
						 break;
					 }
				 } 
			 }
			 
			 String tribunal = "Tribunal no encontrado";
			 List <CatDiscriminanteDTO> catalogoDiscr = catDiscriminanteDelegate.consultarTribunalesPorDistrito(forma.getDistrito(),Instituciones.PJ);
			 if(catalogoDiscr != null && catalogoDiscr.size() > 0){
				 for(CatDiscriminanteDTO cat : catalogoDiscr){
					 if(cat.getCatDiscriminanteId().compareTo(forma.getTribunal()) == 0){
						 tribunal = cat.getNombre();
						 break;
					 }
				 }
			 }
			 
			 String tipoAudiencia = "Tipo de audiencia no encontrada";
			 catalogo = catDelegate.recuperarCatalogo(Catalogos.TIPO_AUDIENCIA);
			 if(catalogo != null && catalogo.size() > 0){
				 for(CatalogoDTO cat : catalogo){
					 if(cat.getClave().compareTo(forma.getTipoDeAudiencia()) == 0){
						 tipoAudiencia = cat.getValor();
						 break;
					 }
				 } 
			 }
				
			 String solicitante = funSol.getNombreCompleto() != null ? funSol.getNombreCompleto() : "Solicitante no encontrado";
			 
			 String institucionSol = funSol.getNombreInstitucion() != null ? funSol.getNombreInstitucion() : "Institucion no encontrada";
					
			 String numCaso = forma.getNumeroDeCaso() != null ? forma.getNumeroDeCaso() : "Numero de caso no encontrado";
			 
			 String fchLimite = forma.getFechaLimiteAudiencia() != null ? forma.getFechaLimiteAudiencia() : "Fecha no encontrada";
			 
			 String hrLimite = forma.getHoraLimiteAudiencia() != null ? forma.getHoraLimiteAudiencia() : "Hora no encontrada";
			 
			 
			// Remplazamos los datos
			   texto = StringUtils.replace(texto, ConstantesGenerales.DESTINATARIO_SOL_AUD_TAG_BUSQUEDA, "<B>" + destinatario + "</B>");
			   texto = StringUtils.replace(texto, ConstantesGenerales.DISTRITO_DES_AUD_TAG_BUSQUEDA, "<B>" + distrito + "</B>");
			   texto = StringUtils.replace(texto, ConstantesGenerales.TRIBUNAL_DES_AUD_TAG_BUSQUEDA, "<B>" + tribunal + "</B>");
			   texto = StringUtils.replace(texto, ConstantesGenerales.TIPO_AUDIENCIA_TAG_BUSQUEDA, "<B>" + tipoAudiencia + "</B>");
			   texto = StringUtils.replace(texto, ConstantesGenerales.SOLICITANTE_SOL_AUD_TAG_BUSQUEDA, "<B>" + solicitante + "</B>");
			   texto = StringUtils.replace(texto, ConstantesGenerales.INSTITUCION_SOLICITANTE_TAG_BUSQUEDA, "<B>" + institucionSol + "</B>");
			   texto = StringUtils.replace(texto, ConstantesGenerales.NUM_CASO_AUDIENCIA_TAG_BUSQUEDA, "<B>" + numCaso + "</B>");
			   texto = StringUtils.replace(texto, ConstantesGenerales.FCH_LIMITE_SOL_AUD_TAG_BUSQUEDA, "<B>" + fchLimite + "</B>");
			   texto = StringUtils.replace(texto, ConstantesGenerales.HR_LIMITE_SOL_AUD_TAG_BUSQUEDA, "<B>" + hrLimite + "</B>");
			   
			 
		 } catch (NSJPNegocioException ne) {
			 logger.error("Error al tratar de construir el cuerpo de la notificacion de la solicitud de audiencia: EnviarEmailAction - construyeTextoSolicitudAudiencia ", ne);
		 } catch (Exception e){
			 logger.error("Error al tratar de construir el cuerpo de la notificacion de la solicitud de audiencia: EnviarEmailAction - construyeTextoSolicitudAudiencia ", e);
		 }
		 return texto;
	 }
	 
	 
	 /**
	  * Metodo que obtiene los destinatarios a los que se les enviara el correo sobre la 
	  * conclusion de la resolucion de audiencia
	  * @param dto
	  * @param request
	  * @return List (String)
	  */
	 public List<String> obtenerDestinatariosConclusionAudiencia(AudienciaDTO dto, HttpServletRequest request){
		 List<String> destinos = new ArrayList<String>();
		 
		 try {
			 
		 
			 //Correo del encargado de sala
			 UsuarioDTO u = getUsuarioFirmado(request);
			 if(u.getFuncionario() != null){
				 //se realiza la consulta porque el metodo getUsuarioFirmado() no trae los datos del funcionario completos
				 List<FuncionarioDTO> fun = funcionarioDelegate.consultarFuncionarioXNumeroEmpleado(u.getFuncionario());
				 if(fun != null && fun.size() > 0){
					 if(fun.get(0) != null && fun.get(0).getEmail() != null )
						 destinos.add(fun.get(0).getEmail());
				 }
			 }
			 
			 //Correo del solicitante (agenteMP)
			 String nombreSolicitante = null;
			 if(dto.getSolicitud().getNombreSolicitante() != null){
				 nombreSolicitante = dto.getSolicitud().getNombreSolicitante();
			 }else if(dto.getSolicitud().getNombreSolicitanteExternoInterno() != null){
				 nombreSolicitante = dto.getSolicitud().getNombreSolicitanteExternoInterno();
			 }
			 if(nombreSolicitante != null){
				 List<FuncionarioExternoDTO> funcionariosExt = audienciaDelegate.consultarFuncionarioExternoAudienciaNotificaciones(dto);
				 for(FuncionarioExternoDTO fun : funcionariosExt){
					 if(fun.getNombreCompleto().equalsIgnoreCase(nombreSolicitante) && fun.getEmail() != null)
						 destinos.add(fun.getEmail());
				 }
			 }
			 
			 //correo del juez asignado
			 List<String> juezEmail = new ArrayList<String>();
			 if(dto.getFuncionarios() != null && dto.getFuncionarios().size() > 0){
				 for(FuncionarioDTO fun : dto.getFuncionarios()){
					 if(fun.getEmail() != null)
					juezEmail.add(fun.getEmail());
					 
				 }
			 }
			 if(juezEmail.size() > 0)
				 destinos.addAll(juezEmail);
			 
			 //correo del coordinador (admonPJ)
			 String coorEmail = dto.getExpediente() != null &&
					 dto.getExpediente().getResponsableTramite() != null &&
					 dto.getExpediente().getResponsableTramite().getEmail() != null ?
							 dto.getExpediente().getResponsableTramite().getEmail() : null;
			 if(coorEmail != null)
				 destinos.add(coorEmail);
			 
			 //correos de encargados de causas
			 List<FuncionarioDTO> funcionarios = funcionarioDelegate.consultarFuncionariosPorDicriminanteYRol(ConstantesGenerales.ID_CATDISCRIMINANTE_CENTRO_JUSTICIA, ConstantesGenerales.ID_ROL_ENCARGADO_CAUSA_PJ, null);
			 for(FuncionarioDTO fun : funcionarios){
				 if(fun.getEmail() != null)
					 destinos.add(fun.getEmail());
			 }
			 
		 } catch (NSJPNegocioException e) {
			 logger.error("Error al tratar de consultar los destinatarios del mensaje a enviar: EnviarEmailAction - obtenerDestinatariosConclusionAudiencia ", e);
		 }
		 
		 return destinos;
	 }
	
	
}

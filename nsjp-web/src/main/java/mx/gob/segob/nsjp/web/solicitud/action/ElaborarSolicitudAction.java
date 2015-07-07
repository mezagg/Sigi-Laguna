package mx.gob.segob.nsjp.web.solicitud.action;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
//import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.delegate.usuario.UsuarioDelegate;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rgama
 */
public class ElaborarSolicitudAction extends GenericAction {

    private static final Logger LOG =
            Logger.getLogger(ElaborarSolicitudAction.class);
    
    private static final String KEY_JSON_SOLICITUD_ORIGINAL_ID = "idSolicitudOriginal";

    @Autowired
	SolicitudDelegate solicitudDelegate;
    @Autowired
	ExpedienteDelegate expedienteDelegate;
    @Autowired
    UsuarioDelegate usuarioDelegate;
    @Autowired
    AsignacionProgramaDelegate asignacionProgramaDelegate;
    @Autowired
    DocumentoDelegate documentoDelegate;
    @Autowired
    ConfiguracionDelegate configuracionDelegate;

    /**
	 * Metodo utilizado para registrar una Solicitud 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward registrarSolicitud(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			LOG.info("ejecutando registrar solicitud");
			
			Long institucionSolicitante = Long.parseLong(request.getParameter("institucionSolicitante"));
			Long numeroExpedienteId = null;
			UsuarioDTO loUsuario = getUsuarioFirmado(request); 
			String solicitante = loUsuario.getFuncionario().getNombreCompleto();
			String numeroExpediente =request.getParameter("numeroExpediente");
			Long idSolicitud = NumberUtils.toLong(request.getParameter("idSolicitud"),0);
			Long idTipoSolicitud = NumberUtils.toLong(request.getParameter("idTipoSolicitud"),0);			
			String motivo =request.getParameter("motivo");
			Long solicitudIdOrigen = NumberUtils.toLong(request.getParameter("solicitudIdOrigen"),0);
			Long areaDestino = NumberUtils.toLong(request.getParameter("areaDestino"),0);
			

			
			if(numeroExpediente != null && numeroExpediente != "") {
				ExpedienteDTO expedienteDTO = super.getExpedienteTrabajo(request,numeroExpediente);
				if(expedienteDTO == null) {
					expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
				}
				numeroExpedienteId=expedienteDTO.getNumeroExpedienteId();
			}else{
				throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			}
			String idFuncionariosSolicitantes = request.getParameter("idsFuncionariosSolicitantes");
			String[] iDsFuncionarios = null;
			if(!idFuncionariosSolicitantes.equals(""))
				iDsFuncionarios = idFuncionariosSolicitantes.split(",");
			
			
			Date fechaHora= new Date();

			LOG.info("VERIFICANDO PARAMETROS::::::::::::::::::::::::::");
			LOG.info("institucionSolicitante:" + institucionSolicitante);
			LOG.info("solicitante           :" + solicitante);			
			LOG.info("numeroExpediente      :" + numeroExpediente);			
			LOG.info("idsFuncionariosSolicitantes: " + idFuncionariosSolicitantes);			
			LOG.info("idSolicitud           : " + idSolicitud);			
			LOG.info("usuarioSolicitante    : " + loUsuario.getFuncionario());			
			LOG.info("idTipoSolicitud       : " + idTipoSolicitud);
			LOG.info("motivo                : " + motivo);
			LOG.info("solicitudIdOrigen     : " + solicitudIdOrigen);
			LOG.info("areaDestino     : " + areaDestino);
			
						
			
			ValorDTO tipoSolicitudDTO = new ValorDTO();
			SolicitudDTO solicitudDTO = new SolicitudDTO();
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			ConfInstitucionDTO confInstitucionDTO = null;
			if(institucionSolicitante != null && institucionSolicitante>0){
				confInstitucionDTO = new ConfInstitucionDTO();
				confInstitucionDTO.setConfInstitucionId(institucionSolicitante);	
			}
								
			expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
			
			if(idTipoSolicitud != 0 && idTipoSolicitud == TiposSolicitudes.POLICIA_MINISTERIAL.getValorId().longValue()){
				tipoSolicitudDTO.setIdCampo(TiposSolicitudes.POLICIA_MINISTERIAL.getValorId());
				solicitudDTO.setAreaDestino((long) Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal());
			}else{
				if(idTipoSolicitud != 0)
					tipoSolicitudDTO.setIdCampo(idTipoSolicitud);
				else
					tipoSolicitudDTO.setIdCampo(TiposSolicitudes.APOYO.getValorId());
			}
			
			solicitudDTO.setTipoSolicitudDTO(tipoSolicitudDTO);
			solicitudDTO.setEstatus(new ValorDTO(EstatusSolicitud.ABIERTA.getValorId()));
			
			solicitudDTO.setNombreDocumento("Solicitud");
			solicitudDTO.setFechaCreacion(new Date());
			
			solicitudDTO.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.SOLICITUD.getValorId()));
			solicitudDTO.setFormaDTO(new FormaDTO(1L));
			
			solicitudDTO.setExpedienteDTO(expedienteDTO);	
			solicitudDTO.setFechaCreacion(fechaHora);
			solicitudDTO.setInstitucion(confInstitucionDTO);
			solicitudDTO.setNombreSolicitante(solicitante);
			solicitudDTO.setUsuarioSolicitante(loUsuario.getFuncionario());
			if(motivo != null)
				solicitudDTO.setMotivo(motivo);
			solicitudDTO.setDocumentoId(idSolicitud);
			
			UsuarioDTO usuarioEnSesionDTO=super.getUsuarioFirmado(request);
			Long areaId = usuarioEnSesionDTO.getAreaActual().getAreaId();
			SolicitudDTO solicitudDTO2 = null;

			for (int i=0; i< iDsFuncionarios.length; i++) {
				FuncionarioDTO loDestinatario = new FuncionarioDTO(Long.parseLong(iDsFuncionarios[i]));
				solicitudDTO.setDestinatario(loDestinatario);
				UsuarioDTO usuarioDTO=usuarioDelegate.consultarUsuarioPorClaveFuncionario(Long.parseLong(iDsFuncionarios[i]));
				//Tomamos el area del usuario destinatario
				if(areaDestino != null && areaDestino>0L){
					solicitudDTO.setAreaDestino(areaDestino);
				}
				// TODO Verificar el funcionamiento de estecodigo ya que no es correcto colocar el area del funcionario logueado si es una solicitud
				//Quitar cuando aseguremos que todos los flujos que usan este metodo funcionan correctamenete
				else{
					solicitudDTO.setAreaDestino(usuarioDTO.getAreaActual().getAreaId());
				}
				solicitudDTO.setAreaOrigen(areaId);
				if(idTipoSolicitud != 0 && idTipoSolicitud == TiposSolicitudes.POLICIA_MINISTERIAL.getValorId().longValue()){
					solicitudDTO.setAreaDestino((long) Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal());
				}
				solicitudDTO2 = solicitudDelegate.registrarSolicitud(solicitudDTO);
			}

			LOG.info("guarda con exito" + solicitudDTO2);
			// revisamos si el guardado fue exitoso para mandar el xml
			// correspondiente
			if (solicitudDTO2 != null
					&& solicitudDTO2.getDocumentoId() != null) {
				converter.alias("SolicitudDTO", SolicitudDTO.class);
				String xml = converter.toXML(solicitudDTO2);
				LOG.info(xml);
				escribirRespuesta(response, xml);
			} else {
				solicitudDTO2.setDocumentoId(0L);
				converter.alias("SolicitudDTO", SolicitudDTO.class);
				String xml = converter.toXML(solicitudDTO2);
				LOG.info(xml);
				escribirRespuesta(response, xml);
			}
		} catch (Exception e) {		
			LOG.info(e.getCause(),e);
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward actualizaEstatusSolicitud(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String mensaje = "";
		try {

			LOG.info("designar cambio estatus");
		    SolicitudDTO solicitudDTO = new SolicitudDTO();
		    
		    Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId"), 0L);

		    Long estatus = NumberUtils.toLong(request.getParameter("estatus"), 0L);
		    
		    EstatusSolicitud estatusSolicitud =  EstatusSolicitud.getByValor(estatus);
		    
//		    Boolean verificarInventarioPertenencia = BooleanUtils.toBoolean(request.getParameter("verificarInventarioPertenencia"));
		    
		    solicitudDTO.setDocumentoId(solicitudId);
		    
		    if(solicitudId > 0 && estatusSolicitud != null){
		    	LOG.info("designar cambio estatus::::: "+ solicitudId);
		    	solicitudDelegate.actualizaEstatusSolicitud(solicitudDTO , estatusSolicitud);			    	
		    	mensaje = "Los datos se han guardado con exito.";
		    	
//		    	if(verificarInventarioPertenencia) {
//		    		solicitudDTO = solicitudDelegate.consultarSolicitudXId(solicitudId);
//		    		ExpedienteDTO expedienteDTO = solicitudDTO.getExpedienteDTO();
//
//		    		SentenciaDTO sentenciaDTO = new SentenciaDTO();
//		    		sentenciaDTO.setNumeroExpedienteDTO(expedienteDTO);
//		    		sentenciaDTO = asignacionProgramaDelegate.consultarSentencia(sentenciaDTO);
//		    		
//		    		if (sentenciaDTO != null
//		    				&& sentenciaDTO.getInventarioPertenenciaDTO() != null
//		    				&& sentenciaDTO.getInventarioPertenenciaDTO().getDefinitivo()){
//		    			expedienteDTO.setEstatus(new ValorDTO(EstatusExpediente.PENDIENTE_DE_INGRESO.getValorId()));
//		    			expedienteDelegate.actualizarEstatusExpediente(expedienteDTO);
//		    		}
//		    	}
		    	
		    } else {
		    	mensaje = "Por favor verifique que los datos sean correctos.";
		    }	   
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			mensaje = "Ha ocurrido un error.";
		} finally {
			try {
				escribirRespuesta(response, mensaje);
			} catch(Exception e){
				LOG.error(e.getMessage(), e);
			}
		}

		return null;
	}	

    /**
	 * Metodo utilizado para registrar una Solicitud 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward enviarSolicitudOtrasInstituciones(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		StringBuilder html = new StringBuilder(); 

		response.setContentType("text/javascript; charset=ISO-8859-1");
		
		try {
			JSONParser parser = new JSONParser();		
			
			StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    }    
		    Object obj = parser.parse(sb.toString());
			
		    JSONObject jsonObject = (JSONObject) obj;
		    
		    List<DocumentoDTO> lstDocumentoAdjuntos = new ArrayList<DocumentoDTO>();

		    if (jsonObject != null && !jsonObject.isEmpty()) {
		    	
		    	Long sentenciaId = NumberUtils.toLong(jsonObject.get("sentenciaId").toString());
		    	SentenciaDTO sentenciaDTO = null;
		    	if(sentenciaId >0L){
		    		sentenciaDTO = new SentenciaDTO();
		    		sentenciaDTO.setSentenciaId(sentenciaId);
		    		sentenciaDTO = asignacionProgramaDelegate.consultarSentencia(sentenciaDTO);	
		    	}			    
			    
				LOG.info("ejecutando registrar solicitud");
				
				Long institucionSolicitante = NumberUtils.toLong(jsonObject.get("institucionSolicitante").toString());
				Long numeroExpedienteId = null;
				UsuarioDTO loUsuario = getUsuarioFirmado(request); 
				String solicitante = loUsuario.getFuncionario().getNombreCompleto();
				String numeroExpediente = jsonObject.get("numeroExpediente").toString();
				Long idSolicitud = NumberUtils.toLong(jsonObject.get("idSolicitud").toString());
				Long idTipoSolicitud = NumberUtils.toLong(jsonObject.get("idTipoSolicitud").toString());			
				String motivo =request.getParameter("motivo");
				Long idSolicitudOriginal = Long.parseLong(jsonObject.get(KEY_JSON_SOLICITUD_ORIGINAL_ID).toString());
//				Long solicitudIdOrigen = NumberUtils.toLong(jsonObject.get("solicitudIdOrigen").toString());
//				Long areaDestino = NumberUtils.toLong(jsonObject.get("areaDestino").toString());
	
				//se utiliza el numero de causa de la sentencia para RS 
				
				if(numeroExpediente != null && numeroExpediente != "") {
					ExpedienteDTO expedienteDTO = super.getExpedienteTrabajo(request,numeroExpediente);
					if(expedienteDTO == null) {
						expedienteDTO = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
					}
					numeroExpedienteId=expedienteDTO.getNumeroExpedienteId();
				}else{
					html.append("{\"error\":\"Parametros insuficientes.\"}");
					throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
				
				
				Date fechaHora= new Date();
	
				LOG.info("VERIFICANDO PARAMETROS::::::::::::::::::::::::::");
				LOG.info("institucionSolicitante:" + institucionSolicitante);
				LOG.info("solicitante           :" + solicitante);			
				LOG.info("numeroExpediente      :" + numeroExpediente);			
				LOG.info("idsFuncionariosSolicitantes: " + jsonObject.get("destinatarios"));			
				LOG.info("idSolicitud           : " + idSolicitud);			
				LOG.info("usuarioSolicitante    : " + loUsuario.getFuncionario());			
				LOG.info("idTipoSolicitud       : " + idTipoSolicitud);
				LOG.info("motivo                : " + motivo);
//				LOG.info("solicitudIdOrigen     : " + solicitudIdOrigen);
//				LOG.info("areaDestino     : " + areaDestino);
				
							
				
				ValorDTO tipoSolicitudDTO = new ValorDTO();
				SolicitudDTO solicitudDTO = new SolicitudDTO();
				ExpedienteDTO expedienteDTO = new ExpedienteDTO();
				ConfInstitucionDTO confInstitucionDTO = null;
				if(institucionSolicitante != null && institucionSolicitante>0){
					confInstitucionDTO = new ConfInstitucionDTO();
					confInstitucionDTO.setConfInstitucionId(institucionSolicitante);	
				}
				
				if(idTipoSolicitud != 0) {
						tipoSolicitudDTO.setIdCampo(idTipoSolicitud);
				} else {
						tipoSolicitudDTO.setIdCampo(TiposSolicitudes.APOYO.getValorId());
				}
				
				solicitudDTO.setTipoSolicitudDTO(tipoSolicitudDTO);
				solicitudDTO.setEstatus(new ValorDTO(EstatusSolicitud.ABIERTA.getValorId()));
				
				solicitudDTO.setNombreDocumento("Solicitud");
				solicitudDTO.setFechaCreacion(new Date());
				
				solicitudDTO.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.SOLICITUD.getValorId()));
				solicitudDTO.setFormaDTO(new FormaDTO(1L));
				
				solicitudDTO.setExpedienteDTO(expedienteDTO);	
				solicitudDTO.setFechaCreacion(fechaHora);
				solicitudDTO.setInstitucion(confInstitucionDTO);
				solicitudDTO.setNombreSolicitante(solicitante);
				if (loUsuario.getFuncionario().getInstitucion() == null
						|| loUsuario.getFuncionario().getInstitucion().getConfInstitucionId() == null
						|| loUsuario.getFuncionario().getInstitucion().getConfInstitucionId() < 1L){
					loUsuario.getFuncionario().setInstitucion(configuracionDelegate.consultarInstitucionActual());
				}
				solicitudDTO.setUsuarioSolicitante(loUsuario.getFuncionario());
				if(motivo != null)
					solicitudDTO.setMotivo(motivo);
				solicitudDTO.setDocumentoId(idSolicitud);
				
				UsuarioDTO usuarioEnSesionDTO=super.getUsuarioFirmado(request);
				Long areaId = usuarioEnSesionDTO.getAreaActual().getAreaId();
				@SuppressWarnings("unused")
				SolicitudDTO solicitudDTO2 = null;
				
				JSONArray jsonArray = (JSONArray)jsonObject.get("documentos");
			    
			    if (jsonArray != null && !jsonArray.isEmpty()){
					@SuppressWarnings("unchecked")
					Iterator<JSONObject> iterator = jsonArray.iterator();
					while (iterator.hasNext()) {
						JSONObject json = iterator.next();
						if (json.get("idDocumento") != null){
							Long documentoId = NumberUtils.toLong(json.get("idDocumento").toString());
							if(documentoId>0L){
								DocumentoDTO documentoDTO = documentoDelegate.consultarDocumentoXId(documentoId);
								/*
								 * FIXME Se mete ciclo en donde se duerme el hilo de ejecuci&oacute;n para que 
								 * siempre que se env&iacute;e un documento como solicitud de datos adjuntos, 
								 * cuente con un PDF asociado, esto para poder relacionar el pdf como dato adjunto 
								 * de la solicitud.
								 */
								while (documentoDTO.getArchivoDigital() == null
										|| documentoDTO.getArchivoDigital().getArchivoDigitalId() == null){
									Thread.sleep(250L);
									documentoDTO = documentoDelegate.consultarDocumentoXId(documentoId);
								}
								lstDocumentoAdjuntos.add(documentoDTO);
							}
						}
					}
				}
				
				if (!lstDocumentoAdjuntos.isEmpty()) {
			    	jsonArray = (JSONArray)jsonObject.get("destinatarios");				    	
				    if (jsonArray != null && !jsonArray.isEmpty()){
						@SuppressWarnings("unchecked")
						Iterator<JSONObject> iterator = jsonArray.iterator();
						while (iterator.hasNext()) {
							JSONObject json = iterator.next();
							if (json.get("idFuncionario") != null
									&& json.get("instId") != null){
								Long idFuncionario = NumberUtils.toLong(json.get("idFuncionario").toString());
								Long idInstitucion = NumberUtils.toLong(json.get("instId").toString());
								Long areaDestinoId = NumberUtils.toLong(json.get("areaDestinoId").toString());
								Boolean esOtraInst = BooleanUtils.toBoolean(json.get("esOtraInst").toString());
								String nombreCompleto = null;
								if (json.get("nombreCompleto") != null){
									nombreCompleto = json.get("nombreCompleto").toString();
								}
								if(idFuncionario>0L && idInstitucion>0L){
									FuncionarioDTO funcionarioDTO = new FuncionarioDTO(idFuncionario);
									if (nombreCompleto != null){
										funcionarioDTO.setNombreCompleto(nombreCompleto);
									}
									Instituciones destino = Instituciones.getByValor(idInstitucion);
									for (DocumentoDTO documentoDTO : lstDocumentoAdjuntos) {
										documentoDTO.setResponsableDocumento(funcionarioDTO);
									}
									solicitudDTO.setDestinatario(funcionarioDTO);
									solicitudDTO.setAreaDestino(areaDestinoId);
									solicitudDTO.setAreaOrigen(areaId);
									if(esOtraInst){
										
										solicitudDTO2 = solicitudDelegate.enviarSolicitud(
												solicitudDTO, 
												destino, 
												lstDocumentoAdjuntos, 
												sentenciaDTO);
										
										//Se agregan las invocaciones a los metodos para registrar la solicitud enviada a otra institucion.
										expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
										asignarDatosSolicitudExterna(solicitudDTO, destino);
										solicitudDTO2 = solicitudDelegate.registrarSolicitud(solicitudDTO);
									} else {
										expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
										solicitudDTO2 = solicitudDelegate.registrarSolicitud(solicitudDTO);
									}
								}
							}
						}	
					}
			    }
				if (idSolicitudOriginal > 0L){
					actualizarEstatusSolicitud(idSolicitudOriginal, EstatusSolicitud.CERRADA);					
				}
				html.append("{\"exito\":\"Los documentos se han enviado con exito.\"}");				    
			} else {
				html.append("{\"error\":\"Parametros insuficientes.\"}");
			}
			
		} catch (Exception e) {		
			LOG.error(e.getCause(),e);
			html.append("{\"error\":\"Ha ocurrido un error, por favor intente mas tarde.\"}");
		} finally {
			try {			
				PrintWriter printWriter = response.getWriter();
				printWriter.print(html.toString());
				printWriter.flush();
				printWriter.close();
			} catch(Exception e) {
				LOG.error(e.getCause(),e);	
			}
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n del estatus de la solicitud
	 * que se va a responder v&iacute;a web service.
	 * @param solicitudId - El identificador de la solicitud de la cual se va a actualizar
	 * 						su estatus.
	 * @param estatus - El estatus con el cual se actualizar&aacute; la solicitud.
	 */
	private void actualizarEstatusSolicitud(Long solicitudId, EstatusSolicitud estatus){
		if (solicitudId != null && solicitudId > 0L){
			SolicitudDTO solicitud = new SolicitudDTO();
			solicitud.setDocumentoId(solicitudId);
			try {
				solicitudDelegate.actualizaEstatusSolicitud(solicitud, estatus);
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(),e);
			}
		}
	}
	
	private void asignarDatosSolicitudExterna (SolicitudDTO solicitudDTO, Instituciones destino){
		FuncionarioExternoDTO funExt = new FuncionarioExternoDTO();
		funExt.setCveFuncionarioInstExt(solicitudDTO.getDestinatario().getClaveFuncionario());
		
		ConfInstitucionDTO confInstitucionDTO = new ConfInstitucionDTO();
		confInstitucionDTO.setConfInstitucionId(destino.getValorId());
		funExt.setConfInstitucionDTO(confInstitucionDTO);
		String nombreCompletoDestinatario = solicitudDTO.getDestinatario().getNombreCompleto();
		if (nombreCompletoDestinatario != null){
			String[] arrNombres = nombreCompletoDestinatario.split(" ");
			if (arrNombres.length > 3){
				funExt.setNombre(arrNombres[0]+" "+arrNombres[1]);
				funExt.setApellidoPaterno(arrNombres[2]);
				StringBuilder sbApellidoMaterno = new StringBuilder("");
				for (int i=3; i< arrNombres.length; i++){
					if (i>3){
						sbApellidoMaterno.append(" ");
					}
					sbApellidoMaterno.append(arrNombres[i]);
				}
				funExt.setApellidoMaterno(sbApellidoMaterno.toString());
			}else{
				funExt.setNombre(arrNombres[0]);
				funExt.setApellidoPaterno(arrNombres[1]);
				funExt.setApellidoMaterno(arrNombres[2]);
			}
		}
		
		solicitudDTO.setDestinatario(null);
		solicitudDTO.setDestinatarioInstExterna(funExt);
	}
}

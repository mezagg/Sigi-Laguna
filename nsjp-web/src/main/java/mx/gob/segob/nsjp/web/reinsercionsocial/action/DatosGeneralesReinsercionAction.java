/**
* Nombre del Programa 	: DatosGeneralesReinsercionAction.java
* Autor              	: EdgarTE
* Compania            	: Ultrasist
* Proyecto           	: NSJP 						Fecha: 21 Feb 2012
* Marca de cambio    	: N/A
* Descripcion General 	: Describir el objetivo de la clase de manera breve
* Programa Dependiente	: N/A
* Programa Subsecuente 	: N/A
* Cond. de ejecucion 	: N/A
* Dias de ejecucion 	: N/A 						Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor  				: N/A
* Compania 				: N/A
* Proyecto           	: N/A 						Fecha: N/A
* Modificacion     		: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.reinsercionsocial.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.remisiones.CatTipoRemision;
import mx.gob.segob.nsjp.comun.enums.sentencia.TipoSentencia;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
//import mx.gob.segob.nsjp.delegate.delito.DelitoDelegate;
import mx.gob.segob.nsjp.delegate.elemento.ElementoDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.sentencia.SentenciaDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.elemento.BitacoraElementoDTO;
//import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.medida.MedidaAlternaDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionMedidaAlternaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatTipoRemisionDTO;
import mx.gob.segob.nsjp.dto.programas.ProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.reinsercionsocial.form.DatosGeneralesReinsercionForm;
import mx.gob.segob.nsjp.web.reinsercionsocial.form.IngresarSentenciaForm;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase que representa el action de struts que consulta la informaci&oacute;n presentada
 * dentro de las pantallas de datos generales para Reinserci&oacute;n Social.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class DatosGeneralesReinsercionAction extends GenericAction {
	
	/* Log de clase*/
	private static final Logger LOG  = Logger.getLogger(ReinsercionSocialAction.class);
	
	private static final String DATOS_GENERALES = "datosGenerales";
	public static final String CERTIFICADO_MEDICO = "certificadoMedicoRS";
	public static final String SENTENCIAS_JUEZ_EJECUCION = "sentenciasJuezEjecucion";
	public static final String CONSTANCIAS_CERERESO = "constanciasCERERESO";
	public static final String ESTUDIOS_CTI = "estudiosCTI";
	private static final String FWD_INGRESAR_SENTENCIA = "ingresarSentencia";
	private static final String KEY_MAP_ANIOS = "Anios";
	private static final String KEY_MAP_MESES = "Meses";
	private static final String KEY_MAP_DIAS = "Dias";
	private static final String PARAM_ID_SENTENCIA = "sentenciaId";
	private static final String PARAM_ID_SITUACION_JURIDICA = "situacionJuridicaId";
	private static final String PARAM_ID_INVOLUCRADO = "involucradoId";
	private static final String PARAM_FECHA_INICIO = "fechaInicio";
	private static final String PARAM_FECHA_FIN = "fechaFin";
	private static final String PARAM_CAMBIO_EXITO = "cambioExitoso";
	
	private static final String KEY_JSON_SENTENCIA_ID="sentenciaId";
	private static final String KEY_JSON_ASIGNACION_CENTRO_DETENCION_ID="asignacionCentroDetencionId";
	private static final String KEY_JSON_CENTRO_DETENCION_ID="centroDetencionId";
	private static final String KEY_JSON_NOMBRE_CENTRO_DETENCION="nombreCentroDetencion";
	
	@Autowired
	private AsignacionProgramaDelegate asignacionProgD;

	@Autowired
	private SentenciaDelegate sentenciaDelegate;

	@Autowired
	private InvolucradoDelegate involucradoDelegate;
	
//	@Autowired
//	private DelitoDelegate delitoDelegate;
	
	@Autowired
	private ElementoDelegate elementoDelegate;
	
	@Autowired
	private SolicitudDelegate solicitudDelegate;
	
	public ActionForward obtenerSentenciasGrid (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			LOG.info("ejecutando Action ReinsercionSocialAction m&eacute;todo llenarGrid");
			try {
				
				Long estatusExpediente = NumberUtils.toLong(request.getParameter("estatusExpediente"), 0);
				Long estatusSolicitud =  NumberUtils.toLong(request.getParameter("estatusSolicitud"), 0);
				 
				
				SentenciaDTO filtroDTO = new SentenciaDTO();
				ExpedienteDTO filtroExpedienteDTO = new ExpedienteDTO();
				filtroExpedienteDTO.setEstatus(new ValorDTO(estatusExpediente));
				if (estatusSolicitud>0L ) {
					SolicitudDTO solicitudDTO = new SolicitudDTO();
					solicitudDTO.setEstatus(new ValorDTO(estatusSolicitud));
					filtroExpedienteDTO.setSolicitudDTO(solicitudDTO);
				}
				filtroDTO.setNumeroExpedienteDTO(filtroExpedienteDTO);
				
				List<SentenciaDTO> lstSentenciasDTO = asignacionProgD.consultarSentenciasXEstado(filtroDTO);	
//				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("################## Resultados :::::::::" + lstSentenciasDTO.size());
				}
				
				response.setContentType("text/xml; charset=ISO-8859-1");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
			
				writer.print("<rows>");
				final PaginacionDTO pag2 = PaginacionThreadHolder.get();
				if (pag2 != null){
					//Caso particular para considerar el total de registros
					//pag2.setTotalRegistros(new Long(listaObjetos.size()));
	                LOG.debug("pag :: " + pag2);
	                if (pag2 != null 
	                		&& pag2.getTotalRegistros() != null 
	                		&& pag2.getTotalRegistros()>0L) {
	                    writer.print("<total>" + pag2.getTotalPaginas() + "</total>");
	                    writer.print("<records>" + pag2.getTotalRegistros() + "</records>");
	                    writer.print("<page>" + pag2.getPage() + "</page>");
	                } else {
	                    writer.print("<total>0</total>");
	                    writer.print("<records>0</records>");
	                    writer.print("<page>0</page>");
	                }
				}		
				
				List<List<String>> tablaValores = new ArrayList<List<String>>();
				for (SentenciaDTO sentenciaDTO : lstSentenciasDTO) {
					tablaValores.add(crearFilaParaGrid(sentenciaDTO));
				}
				
			String datos = crearDatosGrid(tablaValores);
			writer.print(datos);
			writer.print("</rows>");
			writer.flush();
			writer.close();
		
		} catch (Exception e) {		
			LOG.info(e.getCause(),e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las sentencias que han sido dictadas
	 * pero a&uacute;n no han sido cambiadas de estatus a ejecutoriadas y regresa la 
	 * informaci&oacute;n dentro de un grid.
	 * 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return null - Ya que el servicio s&oacute;lo se limita a llevar a cabo la consulta.
	 * @throws IOException - En el caso de que se presente un error al momento de escribir la
	 * 						 respuesta.
	 */
	public ActionForward obtenerGridSentenciasDictadas (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		Long idSituacionJuridica = NumberUtils.toLong(request.getParameter(PARAM_ID_SITUACION_JURIDICA));

		if (idSituacionJuridica > 0L){
			ValorDTO situacionJuridica = new ValorDTO(idSituacionJuridica);
			try {
				List<InvolucradoDTO> involucrados = sentenciaDelegate.consultarInvolucradosXSituacionSinSentencia(situacionJuridica);

				response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML+"; "+ConstantesGenerales.CHARSET_ISO_8859);
				response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
				PrintWriter writer = response.getWriter();
				
				writer.print("<rows>");
				final PaginacionDTO pag2 = PaginacionThreadHolder.get();
				if (pag2 != null){
					//Caso particular para considerar el total de registros
					if (pag2 != null && pag2.getTotalRegistros() != null) {
						writer.print("<total>" + pag2.getTotalPaginas() + "</total>");
						writer.print("<records>" + pag2.getTotalRegistros() + "</records>");
					} else {
						writer.print("<total>0</total>");
						writer.print("<records>0</records>");
					}
				}
				
				if (involucrados != null
						&& !involucrados.isEmpty()){
					List<BitacoraElementoDTO> lstbitacoras = new ArrayList<BitacoraElementoDTO>();
					for (InvolucradoDTO inv : involucrados){
						BitacoraElementoDTO bit = new BitacoraElementoDTO();
						bit.setElemento(inv);
						lstbitacoras.add(bit);
					}

					Map<Long, BitacoraElementoDTO> bitacorasObtenidas = elementoDelegate.consultarBitacoraElementos(lstbitacoras);

					List<List<String>> tablaValores = new ArrayList<List<String>>();
					for (InvolucradoDTO inv : involucrados) {
						tablaValores.add(crearFilaParaGrid(inv, 
								bitacorasObtenidas != null ? 
										bitacorasObtenidas.get(inv.getElementoId()) : null));
					}
					String datos = crearDatosGrid(tablaValores);
					writer.print(datos);
				}
				
				writer.print("</rows>");
				writer.flush();
				writer.close();
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDatosGeneralesRS(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String forward = "";
		
		try {
			DatosGeneralesReinsercionForm datosGralesRF = (DatosGeneralesReinsercionForm)form;
			SentenciaDTO sentenciaDTO = null;
			Long sentenciaId = NumberUtils.toLong(request.getParameter("sentenciaId"), 0);
			String cNumeroExpediente = request.getParameter("cNumeroExpediente");
			
			String enviarA = request.getParameter("enviarA");
			
			
			if(sentenciaId > 0L) {
				SentenciaDTO filtro = new SentenciaDTO();
				filtro.setSentenciaId(sentenciaId);
				sentenciaDTO = asignacionProgD.consultarSentencia(filtro);
				
			} else if (sentenciaId == 0L && cNumeroExpediente != null) {
				SentenciaDTO filtro = new SentenciaDTO();
				filtro.setNumeroExpedienteDTO(new ExpedienteDTO(null, null, cNumeroExpediente));
				sentenciaDTO = asignacionProgD.consultarSentencia(filtro);
			}
			
			if (sentenciaDTO != null) {

				ExpedienteDTO expedienteDTO = sentenciaDTO.getNumeroExpedienteDTO();
				
				if(expedienteDTO.getEstatus() == null) {
					throw new NSJPNegocioException(CodigoError.INFORMACION_PARAMETROS_ERRONEA);
				}
				
				datosGralesRF = obtenerDatosGenerales(datosGralesRF, sentenciaDTO, request);
				if ( sentenciaDTO.getNumeroExpedienteDTO() != null ) {
					ExpedienteDTO tmp = sentenciaDTO.getNumeroExpedienteDTO();
					super.setExpedienteTrabajo(request, tmp);
				}
			}
			
			if(enviarA != null){
				forward = enviarA;
				if (enviarA.equals(SENTENCIAS_JUEZ_EJECUCION)){
					validarBeneficios(datosGralesRF);
				}
			} else {
				forward = DATOS_GENERALES;
			}
			
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return mapping.findForward(forward);
	}

	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward manejarSentencia(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		try {
			DatosGeneralesReinsercionForm datosGralesRF = (DatosGeneralesReinsercionForm)form;
			SentenciaDTO sentenciaDTO = new SentenciaDTO();
			Long sentenciaId = NumberUtils.toLong(request.getParameter("sentenciaId"), 0);
			Boolean estaLesionado = request.getParameter("estaLesionado") != null
									? Boolean.valueOf(request.getParameter("estaLesionado"))
									: null;
			
			String fechaNacimiento = request.getParameter("fechaNacimiento");
			
			Boolean estaArraigado = request.getParameter("estaArraigado") != null
									? Boolean.valueOf(request.getParameter("estaArraigado"))
									 :null;

			String motivo = request.getParameter("motivo");
			
			InvolucradoDTO involucradoDTO = null;
			AsignacionCentroDetencionDTO centroDetencionDTO = null;
			
			
			if(sentenciaId != null && sentenciaId.compareTo(0L) > 0) {
				sentenciaDTO.setSentenciaId(sentenciaId);
				sentenciaDTO = asignacionProgD.consultarSentencia(sentenciaDTO);
				if(sentenciaDTO != null) {
					if(estaLesionado != null) {
						sentenciaDTO.setBlesionado(estaLesionado);
					}
					
					if (estaArraigado != null || motivo != null) {;
						if (sentenciaDTO.getAsignacionCentroDetencions() != null
								&& !sentenciaDTO.getAsignacionCentroDetencions().isEmpty()){
							for (AsignacionCentroDetencionDTO asignacionCentroDetencionDTO : sentenciaDTO.getAsignacionCentroDetencions()) {
								//TODO: validar cual es el CENTRO ACTIVO
								
								centroDetencionDTO = new AsignacionCentroDetencionDTO();
								centroDetencionDTO.setAsignacionCentroDetencionId(asignacionCentroDetencionDTO.getAsignacionCentroDetencionId());

								if(estaArraigado != null) {
									centroDetencionDTO.setBarraigado(estaArraigado);
								}
								
								if(motivo != null){
									centroDetencionDTO.setCmotivo(motivo);
								}
							}
						}
					}
					
					involucradoDTO = sentenciaDTO.getInvolucradoDTO();
				}
				
				if (involucradoDTO != null
						&& fechaNacimiento != null
						&& involucradoDTO.getNombresDemograficoDTO() != null
						&& !involucradoDTO.getNombresDemograficoDTO().isEmpty()){
					NombreDemograficoDTO nombreDemograficoDTO = involucradoDTO.getNombresDemograficoDTO().get(0);
					nombreDemograficoDTO.setFechaNacimiento(DateUtils.obtener(fechaNacimiento));
					
					involucradoDelegate.actualizarIndividuo(involucradoDTO);
				}
				sentenciaDelegate.realizarAltasCambiosASentencia(sentenciaDTO);
				
				if(centroDetencionDTO != null){
					asignacionProgD.actualizarCentroDetencion(centroDetencionDTO);
				}
					
			}
			
			datosGralesRF = obtenerDatosGenerales(datosGralesRF, sentenciaDTO, request);
			XStream converter=new XStream(); 			converter.alias("datosGralesRF", DatosGeneralesReinsercionForm.class);
			String xml = converter.toXML(datosGralesRF);
			//mandamos la respuesta al cliente
			escribir(response, xml,null);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta del centro de detenci&oacute;n actual asociado a
	 * la sentencia, y regresa la informaci&oacute;n de dicho dentro de detenci&oacute;n dentro de 
	 * un objeto de JSON para su interpretaci&oacute;n dentro de la vista.
	 * @param mapping - mapeos de struts para enlazar las peticiones.
	 * @param form - forma de struts con la informaci&oacute;n pasada dentro de la petici&oacute;n
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML.
	 * @param response - Objeto de java que representa la respuesta en HTML.
	 * @return null - Se escribe el objeto de JSON directamente en el response pasado como argumento.
	 * @throws IOException - en el caso de que ocurra alg&uacute;n error al momento de escribir el 
	 * 						 JSON en la respuesta.
	 */
	public ActionForward obtenerCentroDetencionActualDeSentencia (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {

		Long sentenciaId = NumberUtils.toLong(request.getParameter(PARAM_ID_SENTENCIA), 0L);
		SentenciaDTO sentencia = new SentenciaDTO();
		sentencia.setSentenciaId(sentenciaId);

		AsignacionCentroDetencionDTO centroDetencionActual;
		try {
			centroDetencionActual = sentenciaDelegate.consultarAsignacionCentroDetencionActual(sentencia);
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_JAVASCRIPT+"; "+ ConstantesGenerales.CHARSET_ISO_8859);
			response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
			PrintWriter writer = response.getWriter();
			
			writer.print(dto2Json(centroDetencionActual));
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las sentencias que han sido 
	 * dictadas pero a&uacute;n no han sido cambiadas de estatus a ejecutoriadas.
	 * 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return null - Ya que el servicio s&oacute;lo se limita a llevar a cabo la consulta.
	 * @throws IOException - En el caso de que se presente un error al momento de escribir la
	 * 						 respuesta.
	 */
	public ActionForward consultarDatosSentenciaDictada (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		Long idInvolucrado = NumberUtils.toLong(request.getParameter(PARAM_ID_INVOLUCRADO));
		InvolucradoDTO involucradoDTO = new InvolucradoDTO(idInvolucrado);
		
		Boolean ext=BooleanUtils.toBoolean(request.getParameter(PARAM_CAMBIO_EXITO));
				
		try {
			involucradoDTO = involucradoDelegate.obtenerInvolucrado(involucradoDTO);
			if (form instanceof IngresarSentenciaForm
					&& involucradoDTO != null){
				IngresarSentenciaForm forma = (IngresarSentenciaForm) form;
				forma.setAplicaMulta(Boolean.FALSE);
				forma.setMontoMulta(0);
				forma.setMultaPagada(Boolean.FALSE);
				ExpedienteDTO exp = involucradoDTO.getExpedienteDTO();
				if (exp != null){
					forma.setCaso(exp.getCasoDTO() != null ? exp.getCasoDTO().getNumeroGeneralCaso() : "");
					forma.setCausa(exp.getNumeroExpediente());
					forma.setExpedienteId(exp.getExpedienteId());
					forma.setNumeroExpedienteId(exp.getNumeroExpedienteId());
				}else{
					forma.setCaso("");
					forma.setCausa("");
					forma.setExpedienteId(0L);
					forma.setNumeroExpedienteId(0L);
				}
				forma.setDelitos(involucradoDTO.getCadenaDelitosCometidos());
				forma.setDuracionPena("");
				forma.setFechaFinPena("");
				forma.setFechaInicioPena("");
				forma.setLesionado(Boolean.FALSE);
				forma.setNus(involucradoDTO.getElementoId().toString());
				forma.setPuntosTotales(0);
				forma.setReparacionDanio(Boolean.FALSE);
				forma.setMontoDanioReparado(0);
				forma.setReparacionPagada(Boolean.FALSE);
				forma.setSentenciado(involucradoDTO.getNombreCompleto());
				
				List<ValorDTO> estatusSentencia = new ArrayList<ValorDTO>();
				estatusSentencia.add(new ValorDTO (1L, "Sentencia Dictada"));
				estatusSentencia.add(new ValorDTO (2L, "Sentencia Ejecutoriada"));
				
				if(ext){
					forma.setIdEstatus(2L);
				}
				
				forma.setEstatusSentencia(estatusSentencia);
				
				
				List<ValorDTO> tiposDocto = new ArrayList<ValorDTO>();
				tiposDocto.add(new ValorDTO(TipoDocumento.AUTO_INICIO_PROCEDIMIENTO_EJECUCION.getValorId(), 
						"Auto de Inicio de Procedimiento de Ejecucion"));
				tiposDocto.add(new ValorDTO(TipoDocumento.SENTENCIA_EJECUTORIADA.getValorId(), 
						"Sentencia Ejecutoriada"));
				forma.setLstTiposDocumento(tiposDocto);
				
				cargarDatosSentenciasNUS(involucradoDTO, forma);
				
				forma.setInvolucradoId(involucradoDTO.getElementoId());
			}
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		}
		return mapping.findForward(FWD_INGRESAR_SENTENCIA);
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las sentencias que han sido 
	 * dictadas pero a&uacute;n no han sido cambiadas de estatus a ejecutoriadas.
	 * 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return null - Ya que el servicio s&oacute;lo se limita a llevar a cabo la consulta.
	 * @throws IOException - En el caso de que se presente un error al momento de escribir la
	 * 						 respuesta.
	 */
	public ActionForward registrarSentenciaEjecutoriada (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
			IngresarSentenciaForm forma = (IngresarSentenciaForm) form;

			SentenciaDTO sentenciaDTO = new SentenciaDTO();
			ValorDTO valorDTO = new ValorDTO(TipoSentencia.PRIVATIVA_DE_LA_LIBERTAD.getValorId());
			sentenciaDTO.setValorDTO(valorDTO);
			sentenciaDTO.setBlesionado(forma.getLesionado());
			try {
				sentenciaDTO.setDfechaInicioPena(DateUtils.obtener(forma.getFechaInicioPena()));
				sentenciaDTO.setDfechaFinPena(DateUtils.obtener(forma.getFechaFinPena()));
				sentenciaDTO.setDfechaCreacion(new Date());
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(), e);
			}
			sentenciaDTO.setIpuntosPorAcumular(new Long(forma.getPuntosTotales()));
			sentenciaDTO.setBcumplida(Boolean.FALSE);
			sentenciaDTO.setCnus(forma.getNus());
			
			if (forma.isAplicaMulta()){
				List<RemisionDTO> remisiones = new ArrayList<RemisionDTO>();
				CatTipoRemisionDTO tipoMulta = new CatTipoRemisionDTO(CatTipoRemision.MULTA.getId());
				RemisionDTO multa = new RemisionDTO();
				multa.setCatTipoRemisionDTO(tipoMulta);
				multa.setCumplida(forma.isMultaPagada());
				multa.setFechaAutorizacion(new Date());
				multa.setImontoDanioReparado(forma.getMontoMulta());
				remisiones.add(multa);
				sentenciaDTO.setRemisions(remisiones);
			}
			
			if (forma.isReparacionDanio()){
				if (sentenciaDTO.getRemisions() == null){
					sentenciaDTO.setRemisions(new ArrayList<RemisionDTO>());
				}
				CatTipoRemisionDTO tipoReparacion = new CatTipoRemisionDTO(CatTipoRemision.REPARACION_DEL_DANIO.getId());
				RemisionDTO reparacion = new RemisionDTO();
				reparacion.setCatTipoRemisionDTO(tipoReparacion);
				reparacion.setCumplida(forma.isReparacionPagada());
				reparacion.setFechaAutorizacion(new Date());
				reparacion.setImontoDanioReparado(forma.getMontoDanioReparado());
				sentenciaDTO.getRemisions().add(reparacion);
			}
			
			InvolucradoDTO involucradoDTO = new InvolucradoDTO();
			involucradoDTO.setElementoId(forma.getInvolucradoId());
			
			try {
				involucradoDTO = involucradoDelegate.obtenerInvolucrado(involucradoDTO);
				involucradoDTO.setUsuario(super.getUsuarioFirmado(request));
				sentenciaDelegate.registrarSentenciaEjecutoriada(sentenciaDTO, involucradoDTO);
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(), e);
			}
		return new ActionForward(mapping.findForward(FWD_INGRESAR_SENTENCIA).getPath()+
				"?"+PARAM_ID_SENTENCIA+"="+ involucradoDTO.getElementoId() +
				"&"+PARAM_CAMBIO_EXITO+"="+Boolean.TRUE, false);
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de las sentencias que han sido 
	 * dictadas pero a&uacute;n no han sido cambiadas de estatus a ejecutoriadas.
	 * 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return null - Ya que el servicio s&oacute;lo se limita a llevar a cabo la consulta.
	 * @throws IOException - En el caso de que se presente un error al momento de escribir la
	 * 						 respuesta.
	 */
	public ActionForward calcularIntervaloFechas (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		String fechaInicio = request.getParameter(PARAM_FECHA_INICIO);
		String fechaFin = request.getParameter(PARAM_FECHA_FIN);
		//En el caso de que el parametro de fecha final no venga en la peticion, se calcula el intervalo con la fecha actual.
		if (fechaFin == null
				|| fechaFin.trim().isEmpty()){
			fechaFin = DateUtils.formatear(new Date());
		}
		if (fechaInicio != null 
				&& fechaFin != null){
			try {
				Date fechaInicial = DateUtils.obtener(fechaInicio);
				Date fechaFinal = DateUtils.obtener(fechaFin);
				
				Integer dias = DateUtils.calcularIntervaloDias(fechaInicial, fechaFinal);
				Integer meses = DateUtils.calcularIntervaloMeses(fechaInicial, fechaFinal);
				Integer anios = DateUtils.calcularIntervaloAnios(fechaInicial, fechaFinal);
				
				Map<String,String> intervalo = new HashMap<String, String>();
				intervalo.put(KEY_MAP_ANIOS, anios.toString());
				intervalo.put(KEY_MAP_MESES, meses.toString());
				intervalo.put(KEY_MAP_DIAS, dias.toString());
				
				PrintWriter writer = response.getWriter();
				
				writer.print(map2Json(intervalo));
				writer.flush();
				writer.close();
				
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return null;
	}
	
	private DatosGeneralesReinsercionForm obtenerDatosGenerales(
			DatosGeneralesReinsercionForm datosGralesRF,
			SentenciaDTO sentenciaDTO,
			HttpServletRequest request) {
		
		if (datosGralesRF == null){
			datosGralesRF = new DatosGeneralesReinsercionForm();
		}
		
		datosGralesRF.setSentenciaId(sentenciaDTO.getSentenciaId().toString());
		
		if(sentenciaDTO.getInvolucradoDTO() != null) {
			InvolucradoDTO involucradoDTO = sentenciaDTO.getInvolucradoDTO();
			List<NombreDemograficoDTO> lstNombreDemograficoDTO = involucradoDTO.getNombresDemograficoDTO();
			datosGralesRF.setDelitoCometido(involucradoDTO.getCadenaDelitosCometidos());
			if(lstNombreDemograficoDTO != null && !lstNombreDemograficoDTO.isEmpty()){
				NombreDemograficoDTO nombreDemograficoDTO = lstNombreDemograficoDTO.get(0);
				datosGralesRF.setApellidoMaterno(nombreDemograficoDTO.getApellidoMaterno());
				datosGralesRF.setApellidoPaterno(nombreDemograficoDTO.getApellidoPaterno());
				datosGralesRF.setNombre(nombreDemograficoDTO.getNombre());
				nombreDemograficoDTO.getEdadAproximada();
				datosGralesRF.setEdad(""+calcularEdadAproximada(nombreDemograficoDTO.getFechaNacimiento()));
			}
		}
		
		if ( sentenciaDTO.getNumeroExpedienteDTO() != null ) {
		
			ExpedienteDTO expedienteDTO = sentenciaDTO.getNumeroExpedienteDTO();
			if(expedienteDTO.getCasoDTO() != null){
				CasoDTO casoDTO = expedienteDTO.getCasoDTO();
				datosGralesRF.setCaso(casoDTO.getNumeroGeneralCaso());
			}
			
			if (expedienteDTO.getEstatusNumeroExpediente() != null){
				datosGralesRF.setEstatusNumExpId(expedienteDTO.getEstatusNumeroExpediente().getIdCampo());				
			}
		
			datosGralesRF.setCarpeta(expedienteDTO.getNumeroExpediente());
			datosGralesRF.setExpedienteId(""+expedienteDTO.getExpedienteId());
			datosGralesRF.setNumeroExpedienteId(""+expedienteDTO.getNumeroExpedienteId());
		}
		String fechaInicioPena = DateUtils.formatear(sentenciaDTO.getDfechaInicioPena());
		datosGralesRF.setFechaInicioPenaSTR(fechaInicioPena);
		String fechaFinPena = DateUtils.formatear(sentenciaDTO.getDfechaFinPena());
		datosGralesRF.setFechaFinPenaSTR(fechaFinPena);
		datosGralesRF.setFechaSalidaSTR(fechaFinPena);
		Date fechaProbableLC = calcularFechaDosTercios(fechaInicioPena, fechaFinPena);
		datosGralesRF.setFechaProbableLC(DateUtils.formatear(fechaProbableLC));
		Map<String,Integer> camposPena = calcularIntervaloFechas(sentenciaDTO.getDfechaInicioPena(), sentenciaDTO.getDfechaFinPena());
		datosGralesRF.setAniosPena(camposPena.get(KEY_MAP_ANIOS));
		datosGralesRF.setMesesPena(camposPena.get(KEY_MAP_MESES));
		datosGralesRF.setDiasPena(camposPena.get(KEY_MAP_DIAS));
		datosGralesRF.setDuracionPena("");
		Map<String,Integer> camposActuales = calcularIntervaloFechas(sentenciaDTO.getDfechaInicioPena(), new Date());
		datosGralesRF.setAniosComputoActual(camposActuales.get(KEY_MAP_ANIOS));
		datosGralesRF.setMesesComputoActual(camposActuales.get(KEY_MAP_MESES));
		datosGralesRF.setDiasComputoActual(camposActuales.get(KEY_MAP_DIAS));
		if(sentenciaDTO.getBlesionado()){
			datosGralesRF.setLesionado("1");
		}else{
			datosGralesRF.setLesionado("0");
		}
		
		datosGralesRF.setCausa(sentenciaDTO.getCnumeroCausa());
		AsignacionCentroDetencionDTO asignacionCDActual = sentenciaDTO.getAsignacionCentroDetencionActual();
		
		if(sentenciaDTO.getAsignacionCentroDetencions() != null){
			if(asignacionCDActual != null){
				datosGralesRF.setCeresoAsignado(asignacionCDActual.getCentroDetencionDTO().getNombre());
				datosGralesRF.setEncarcelado(asignacionCDActual.getBarraigado());
				datosGralesRF.setMotivo(asignacionCDActual.getCmotivo());
				datosGralesRF.setFechaIngresoCeresoSTR(DateUtils.formatear(asignacionCDActual.getDfechaIngreso()));
			}else{
				datosGralesRF.setCeresoAsignado("");
				datosGralesRF.setEncarcelado(false);
				datosGralesRF.setMotivo("");
				datosGralesRF.setFechaIngresoCeresoSTR("");
			}
		}
		if(sentenciaDTO.getValorDTO() !=null ) {
			datosGralesRF.setTipoSentencia(sentenciaDTO.getValorDTO().getValor());
		}
		
		List<String> lstMedidasAlternativas = new ArrayList<String>();
		if(sentenciaDTO.getAsignacionMedidaAlternas()!= null){
			for (AsignacionMedidaAlternaDTO asigMedidaAlternaDTO : sentenciaDTO.getAsignacionMedidaAlternas()) {
				MedidaAlternaDTO  medidaAlternaDTO = asigMedidaAlternaDTO.getMedidaAlternaDTO();
				lstMedidasAlternativas.add(medidaAlternaDTO.getDescripcionMedida());	
			}
		}
		datosGralesRF.setLstMedidasAlternativas(lstMedidasAlternativas);
		
		
		List<String> lstProgramas = new ArrayList<String>();
		if(sentenciaDTO.getAsignacionProgramas()!= null) {
			for (AsignacionProgramaDTO asigProgramaDTO:sentenciaDTO.getAsignacionProgramas()) {
				ProgramaDTO programaDTO = asigProgramaDTO.getProgramaDTO();
				if(programaDTO.getCatProgramaDTO() != null) {
					CatProgramaDTO catProgramaDTO = programaDTO.getCatProgramaDTO();
					lstProgramas.add(catProgramaDTO.getNombre());					
				}
			}
		}
		
		datosGralesRF.setLstProgramas(lstProgramas);
		
		datosGralesRF.setNus(sentenciaDTO.getCnus());
		
		datosGralesRF.setPorcentajeCubiertoPena(
				calcularPorcentajeCumplimientoPena(datosGralesRF.getFechaInicioPenaSTR(), 
						datosGralesRF.getFechaFinPenaSTR()));
		
		datosGralesRF.setPuntosAcumulados(sentenciaDTO.getTotalPuntosObtenidos().intValue());
		
		if(sentenciaDTO.getIpuntosPorAcumular() != null) {
			datosGralesRF.setPuntosTotales(sentenciaDTO.getIpuntosPorAcumular().intValue());
		}
		datosGralesRF.setPorcentajeCumplido(
				calcularPorcentajeCumplimientoPuntos(datosGralesRF.getPuntosTotales(),
						datosGralesRF.getPuntosAcumulados()));
		
		if(sentenciaDTO.getRemisions() != null){
			datosGralesRF.setBeneficio("0");
			datosGralesRF.setCandidatoBeneficio("0");
			datosGralesRF.setPreliberacion("0");
			datosGralesRF.setRemisionParcial(false);
			datosGralesRF.setReparacionDanio(false);
			datosGralesRF.setLibertadCondicional(false);
			datosGralesRF.setMontoDanioReparado("0");
			datosGralesRF.setMontoMulta("0");

			Map<String,Integer> fechasRemision = null;

			for (RemisionDTO remisionDTO : sentenciaDTO.getRemisions()) {
				CatTipoRemisionDTO catTipoRemisionDTO = remisionDTO.getCatTipoRemisionDTO();
				if(catTipoRemisionDTO != null){
					
					if(catTipoRemisionDTO.getCatTipoRemisionId() == CatTipoRemision.CONDICIONAL.getId()) {
						datosGralesRF.setLibertadCondicional(true);
						datosGralesRF.setFechaLibCondSTR(datosGralesRF.getFechaProbableLC());
					}
							
					if(catTipoRemisionDTO.getCatTipoRemisionId() == CatTipoRemision.REMISION_PARCIAL_DE_LA_PENACONDICIONAL.getId()) {
						datosGralesRF.setRemisionParcial(true);
						datosGralesRF.setFechaRemisionSTR(DateUtils.formatear(remisionDTO.getFechaAutorizacion()));
						fechasRemision = calcularIntervaloFechas(remisionDTO.getFechaAutorizacion(), new Date());
						int aniosRemision = fechasRemision.get(KEY_MAP_ANIOS);
						int mesesRemision = fechasRemision.get(KEY_MAP_MESES);
						int diasRemision = fechasRemision.get(KEY_MAP_DIAS);
						datosGralesRF.setAniosComputoRemision(aniosRemision);
						datosGralesRF.setMesesComputoRemision(mesesRemision);
						datosGralesRF.setDiasComputoRemision(diasRemision);
						if (!datosGralesRF.isLibertadCondicional()){
							datosGralesRF.setFechaSalidaSTR(
									DateUtils.formatear(DateUtils.restarIntervaloFechas(
											sentenciaDTO.getDfechaFinPena(), 
											aniosRemision, mesesRemision, diasRemision)));							
						}
					}
					
					if(catTipoRemisionDTO.getCatTipoRemisionId() == CatTipoRemision.REPARACION_DEL_DANIO.getId()) {
						datosGralesRF.setReparacionDanio(true);
						datosGralesRF.setReparacionPagada(remisionDTO.getCumplida());
						if (remisionDTO.getImontoDanioReparado() != null){
							datosGralesRF.setMontoDanioReparado(remisionDTO.getImontoDanioReparado().toString());							
						}
						datosGralesRF.setIdRD(remisionDTO.getRemisionId());
					}
					
					if (catTipoRemisionDTO.getCatTipoRemisionId() == CatTipoRemision.MULTA.getId()){
						datosGralesRF.setAplicaMulta(true);
						datosGralesRF.setMultaPagada(remisionDTO.getCumplida());
						if (remisionDTO.getImontoDanioReparado() != null){
							datosGralesRF.setMontoMulta(remisionDTO.getImontoDanioReparado().toString());							
						}
						datosGralesRF.setIdMulta(remisionDTO.getRemisionId());
					}
					
					
					if(datosGralesRF.getPorcentajeCubiertoPena() >= catTipoRemisionDTO.getIporcentajeCumplido()){
						datosGralesRF.setCandidatoBeneficio("1");
					}
				}
			}
			if (datosGralesRF.isRemisionParcial()
					&& datosGralesRF.isLibertadCondicional()){
				datosGralesRF.setLibertadCondicionalRemision(true);
				String fechaRemisionLCSTR = DateUtils.formatear(
						DateUtils.restarIntervaloFechas(
								fechaProbableLC, 
								fechasRemision.get(KEY_MAP_ANIOS), 
								fechasRemision.get(KEY_MAP_MESES), 
								fechasRemision.get(KEY_MAP_DIAS)));
				datosGralesRF.setFechaLibCondRemisionSTR(fechaRemisionLCSTR);
				datosGralesRF.setFechaSalidaSTR(fechaRemisionLCSTR);
			}
		}
		
		UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
		if (usuarioFirmado != null 
				&& usuarioFirmado.getRolACtivo() != null
				&& usuarioFirmado.getRolACtivo().getRol() != null
				&& usuarioFirmado.getRolACtivo().getRol().getRolId() != null){
			datosGralesRF.setRolActivoId(usuarioFirmado.getRolACtivo().getRol().getRolId());
		}
						
		return datosGralesRF;
		
	}
	
	private int calcularPorcentajeCumplimientoPena(String fechaInicioPena,String fechaFinPena){
		int porcentajeCumplido = 0;
		try {
			
			Date fechaFin = DateUtils.obtener(fechaFinPena);
			double millisFin = fechaFin.getTime();
			
			Date fechaInicio = DateUtils.obtener(fechaInicioPena);
			double millisInicio = fechaInicio.getTime();
			
			Calendar cal = Calendar.getInstance();
			Date fechaActual = cal.getTime();
			
			double millisActual = fechaActual.getTime();
			if (millisActual > millisFin){
				return 100;
			}else{
				double intervaloSentencia = millisFin - millisInicio;
				double intervaloCumplido = millisActual - millisInicio;
				porcentajeCumplido = (int) ((intervaloCumplido / intervaloSentencia)*100);
			}
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		}
		return porcentajeCumplido;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo el c&aacute;lculo de la fecha que representa el d&iacute;a
	 * en el cual ha transcurrido dos tercios del lapso que existe entre dos fechas.
	 * @param fechaInicioPena - Fecha inicial del lapso que se va a calcular.
	 * @param fechaFinPena - Fecha final del lapso que se va a calcular.
	 * @return fechaDosTercios - Fecha en la cual han transcurrido dos tercios del intervalo que 
	 * 							 existe entre las fechas ingresadas como par&aacute;metros.
	 */
	private Date calcularFechaDosTercios(String fechaInicioPena,String fechaFinPena){
		Date fechaDosTercios = null;
		try {
			Date fechaFin = DateUtils.obtener(fechaFinPena);
			double millisFin = fechaFin.getTime();
			
			Date fechaInicio = DateUtils.obtener(fechaInicioPena);
			double millisInicio = fechaInicio.getTime();
						
			double intervaloSentencia = millisFin - millisInicio;
			double intervaloDosTercios = intervaloSentencia * (0.666666);
			double millisDosTercios = millisInicio + intervaloDosTercios;
			fechaDosTercios = new Date((long) millisDosTercios);
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		}
		return fechaDosTercios;
	}
	
	public int calcularPorcentajeCumplimientoPuntos(int puntosTotales, int puntosAcumulados){
		int porcentaje=0;
		if (puntosAcumulados > puntosTotales){
			porcentaje = 100;
		}else{
			double dTotal = puntosTotales;
			double dAcumulado = puntosAcumulados;
			porcentaje = (int) ((dAcumulado / dTotal)*100);			
		}
		return porcentaje;
	}

	private int calcularEdadAproximada(Date fechaNacimiento){
		int anios = 0;
		Date fechaActual = new Date();
		anios = DateUtils.calcularIntervaloAnios(fechaNacimiento, fechaActual);
		return anios;
	}
	
	
	/**
	 * M&eacute;todo que construye un mapa con los intervalos de a&ntilde;os, meses y d&iacute;as
	 * transcurridos entre las fechas de inicio y fin de pena.
	 * @param fechaInicioPena - Fecha en la cual se inici&oacute; el cumplimiento de la pena.
	 * @param fechaFinPena - Fecha en la cual se cumple la pena asignada al sentenciado.
	 * @return camposPena - Mapa con los atributos para los intervalos de a&ntilde;os, meses y 
	 * 						d&iacute;as de duraci&oacute;n de la pena.
	 */
	private Map<String,Integer> calcularIntervaloFechas(Date fechaInicioPena, Date fechaFinPena){
		Map<String,Integer> camposPena = new HashMap<String,Integer>();
		camposPena.put(KEY_MAP_ANIOS, DateUtils.calcularIntervaloAnios(fechaInicioPena,fechaFinPena));
		camposPena.put(KEY_MAP_MESES, DateUtils.calcularIntervaloMeses(fechaInicioPena, fechaFinPena));
		camposPena.put(KEY_MAP_DIAS, DateUtils.calcularIntervaloDias(fechaInicioPena, fechaFinPena));
		return camposPena;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta del delito principal asociado con el expediente y 
	 * regresa su nombre como una cadena.
	 * @param expedienteDTO - El expediente del cual se va a obtener el nombre del delito principal.
	 * @return strDelito - Cadena que representa el nombre del delito principal asociado con el 
	 * 					   expediente.
	 */
//	private String obtenerDelitoPrincipal (ExpedienteDTO expedienteDTO){
//		String strDelito = "";
//		List<DelitoDTO> delitosCometidos;
//		try {
//			delitosCometidos = delitoDelegate.consultarDelitosExpediente(expedienteDTO);
//			if (delitosCometidos != null && !delitosCometidos.isEmpty()){
//				for(DelitoDTO delito : delitosCometidos){
//					if (delito.getEsPrincipal()){
//						strDelito = delito.getCatDelitoDTO().getNombre();
//						break;
//					}
//				}
//			}
//		} catch (NSJPNegocioException e) {
//			LOG.error(e.getMessage(),e);
//		}
//		return strDelito;
//	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creaci&oacute;n de un objeto de JSON a partir de un 
	 * AsignacionCentroDetencionDTO
	 * @param asignacionCentroDetencionDTO - El DTO que se va a transformar en un JSON.
	 * @return jsonText - Cadena que representa el objeto de JSON creado.
	 */
	private String dto2Json(AsignacionCentroDetencionDTO asignacionCentroDetencionDTO) {
		JSONObject jsonObject = null;
		StringWriter out = null;
		String jsonText = "";
		jsonObject = convertirJson(asignacionCentroDetencionDTO);
		out = new StringWriter();
		try {
			jsonObject.writeJSONString(out);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		jsonText = out.toString();
		return jsonText;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creaci&oacute;n de un objeto de JSON a partir de un 
	 * mapa de llave - valor.
	 * @param mapa - El <code>Map<String,String></code> del cual se obtendr&aacute; su 
	 * 				 representaci&oacute;n en on JSON.
	 * @return jsonText - Cadena que representa el objeto de JSON creado.
	 */
	private String map2Json(Map<String,String> mapa) {
		JSONObject jsonObject = null;
		StringWriter out = null;
		String jsonText = "";
		jsonObject = convertirJson(mapa);
		out = new StringWriter();
		try {
			jsonObject.writeJSONString(out);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		jsonText = out.toString();
		return jsonText;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de un objeto de JSON con las propiedades
	 * del objeto asignacionCentroDetencionDTO.
	 * @param asignacionCentroDetencionDTO - objeto del cual se van a escribir sus propiedades en el
	 * 										 objeto de JSON.
	 * @return JSONObject - con la informaci&oacute;n del DTO transcrita.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject convertirJson (AsignacionCentroDetencionDTO asignacionCentroDetencionDTO){
		JSONObject json = new JSONObject();
		if (asignacionCentroDetencionDTO != null){
			json.put(KEY_JSON_ASIGNACION_CENTRO_DETENCION_ID,asignacionCentroDetencionDTO.getAsignacionCentroDetencionId());
			json.put(KEY_JSON_SENTENCIA_ID, asignacionCentroDetencionDTO.getSentenciaDTO().getSentenciaId());
			json.put(KEY_JSON_CENTRO_DETENCION_ID,asignacionCentroDetencionDTO.getCentroDetencionDTO().getCentroDetencionId());
			json.put(KEY_JSON_NOMBRE_CENTRO_DETENCION,asignacionCentroDetencionDTO.getCentroDetencionDTO().getNombre());
		}else{
			json.put(KEY_JSON_ASIGNACION_CENTRO_DETENCION_ID,0L);
		}
		return json;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la generaci&oacute;n de un objeto de JSON con las propiedades
	 * de un mapa de lllave - valor.
	 * @param mapa - mapa del cual se van a escribir sus propiedades en el objeto de JSON.
	 * @return JSONObject - con la informaci&oacute;n del mapa transcrita.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject convertirJson (Map<String,String> mapa){
		JSONObject json = new JSONObject();
		if (mapa != null){
			for (String key : mapa.keySet()){
				json.put(key, mapa.get(key));				
			}
		}
		return json;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de
	 * una lista de cadenas con la informaci&oacute;n que se va a mostrar
	 * dentro de un grid.
	 * @param inv - InvolucradoDTO del cual se va a obtener la 
	 * 				informaci&oacute;n a mostrar.
	 * @param bitacora - bit&aacute;cora en donde se muestra la informaci&oacute;n
	 * 					 de la &uacute;ltima modificaci&oacute;n que sufri&oacute;
	 * 					 el involucrado. 
	 * @return List<String> - Lista de cadenas con la informaci&oacute;n a desplegar
	 * 						  dentro del grid.
	 */
	private List<String> crearFilaParaGrid(InvolucradoDTO inv, BitacoraElementoDTO bitacora){
		List<String> fila = new ArrayList<String>();
		fila.add(inv.getElementoId().toString());
		ExpedienteDTO expediente = inv.getExpedienteDTO();
		if (expediente != null){
			fila.add(expediente.getCasoDTO() != null ? expediente.getCasoDTO().getNumeroGeneralCaso() : ConstantesGenerales.VALOR_OMISION_GRID );
			fila.add(expediente.getNumeroExpediente());
		}else{
			fila.add(ConstantesGenerales.VALOR_OMISION_GRID);
			fila.add(ConstantesGenerales.VALOR_OMISION_GRID);
		}
		fila.add(inv.getNombreCompleto());
		fila.add(inv.getCadenaDelitosCometidos());
		fila.add(bitacora != null ? 
				DateUtils.formatear(bitacora.getFechaModificacion()) 
				: ConstantesGenerales.VALOR_OMISION_GRID);
		return fila;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de
	 * una lista de cadenas con la informaci&oacute;n que se va a mostrar
	 * dentro de un grid.
	 * @param sentenciaDTO - SentenciaDTO de la cual se va a obtener la 
	 * 						 informaci&oacute;n a mostrar.
	 * @return List<String> - Lista de cadenas con la informaci&oacute;n a 
	 * 						  desplegar dentro del grid.
	 */
	private List<String> crearFilaParaGrid(SentenciaDTO sentenciaDTO){
		List<String> fila = new ArrayList<String>();
		fila.add(sentenciaDTO.getSentenciaId().toString()); 
		if ( sentenciaDTO.getNumeroExpedienteDTO() != null ) {						
			ExpedienteDTO expedienteDTO = sentenciaDTO.getNumeroExpedienteDTO();
			if(expedienteDTO.getCasoDTO() != null){
				fila.add(expedienteDTO.getCasoDTO().getNumeroGeneralCaso());
			}
			fila.add(sentenciaDTO.getCnumeroCausa());
			fila.add(expedienteDTO.getNumeroExpediente());
			if(sentenciaDTO.getInvolucradoDTO() != null) {
				InvolucradoDTO involucradoDTO = sentenciaDTO.getInvolucradoDTO();
				fila.add(involucradoDTO.getNombreCompleto());
				fila.add(involucradoDTO.getCadenaDelitosCometidos());
			}
			fila.add(DateUtils.formatear(sentenciaDTO.getDfechaCreacion()));
			fila.add(expedienteDTO.getNumeroExpedienteId().toString());
		}
		return fila;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de 
	 * una tabla con datos en formato XML para ser interpretados dentro
	 * de un grid.
	 * @param matrizValores - Lista de Listas de cadenas en donde se 
	 * 						  guarda la información a desplegar en el
	 * 						  grid.
	 * @return String - Cadena en formato XML con la informaci&oacute;n 
	 * 					que ser&aacute; presentada dentro del grid.
	 * 					
	 */
	private String crearDatosGrid(List<List<String>> matrizValores){
		StringBuffer buffer = new StringBuffer();
		if (matrizValores != null && !matrizValores.isEmpty()){
			for (List<String> fila : matrizValores){
				if (fila != null && !fila.isEmpty()){
					for (int i=0; i<fila.size();i++){
						if (i==0){
							buffer.append("<row id='"+ fila.get(i)+"'>");
						}else{
							buffer.append("<cell><![CDATA[<div class='celdaGrid'>");
							if (fila.get(i) != null){
								buffer.append(fila.get(i));
							}else{
								buffer.append(ConstantesGenerales.VALOR_OMISION_GRID);
							}
							buffer.append("</div>]]></cell>");
						}
					}
					buffer.append("</row>");
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la carga de los distintos NUS disponibles
	 * para el imputado al cual se le va a dictar sentencia y los asigna a la
	 * forma de struts que presenta los datos en la vista.
	 * @param involucradoDTO - Involucrado del cu&aacute;l se van a consultar 
	 * 						   los NUS que podr&iacute;an ser asignados.
	 * @param forma - Forma de struts en d&oacute;nde se vaciar&aacute;
	 */
	private void cargarDatosSentenciasNUS(InvolucradoDTO involucradoDTO, IngresarSentenciaForm forma){
		try {
			List<SentenciaDTO> sentenciasNUS = sentenciaDelegate.consultarNUS(involucradoDTO);
			if (sentenciasNUS == null ){
				sentenciasNUS = new ArrayList<SentenciaDTO>();
			}		
			
			NombreDemograficoDTO nombreDemografico = new NombreDemograficoDTO();
			nombreDemografico.setNombre("Nuevo");
			nombreDemografico.setApellidoPaterno("");
			nombreDemografico.setApellidoMaterno("");
			List<NombreDemograficoDTO> nombresDemografico = new ArrayList<NombreDemograficoDTO>();
			nombresDemografico.add(nombreDemografico);
			
			InvolucradoDTO invl = new InvolucradoDTO();
			invl.setNombresDemograficoDTO(nombresDemografico);
			
			SentenciaDTO sentencia = new SentenciaDTO();
			sentencia.setCnus(involucradoDTO.getElementoId().toString());
			sentencia.setInvolucradoDTO(invl);
			
			sentenciasNUS.add(0, sentencia);
			forma.setSentenciasNUS(sentenciasNUS);
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la validaci&oacute;n de los documentos que 
	 * otorgan beneficios para una sentencia. En espec&iacute;fico se validan los
	 * documentos para remisi&oacute;n parcial de la pena y libertad condicional.
	 * @param forma - Forma de struts en la cual se regresa la validaci&oacute;n
	 * 				  de los documentos contenidos para el expediente.
	 */
	private void validarBeneficios(DatosGeneralesReinsercionForm forma){
		if (forma != null){
			ExpedienteDTO expediente = new ExpedienteDTO();
			
			expediente.setExpedienteId(NumberUtils.toLong(forma.getExpedienteId(),0L));
			
			List<ValorDTO> tiposDocumento = new ArrayList<ValorDTO>();
			Long idRemisionParcial = TipoDocumento.ACUERDO_AUTORIZACION_REMISION_PARCIAL_PENA.getValorId();
			Long idLibertadCondicional = TipoDocumento.ACUERDO_AUTORIZACION_LIBERTAD_CONDICIONAL.getValorId();
			Long idMulta = TipoDocumento.ACUERDO_AUTORIZACION_EXTINCION_MULTA.getValorId();
			Long idReparacionDanio = TipoDocumento.ACUERDO_AUTORIZACION_EXTINCION_REPARACION_DANIO.getValorId();
			tiposDocumento.add(new ValorDTO(idRemisionParcial));
			tiposDocumento.add(new ValorDTO(idLibertadCondicional));
			tiposDocumento.add(new ValorDTO(idMulta));
			tiposDocumento.add(new ValorDTO(idReparacionDanio));
			
			forma.setIdSolRPP(0L);
			forma.setIdSolLC(0L);
			forma.setIdSolRD(0L);
			forma.setIdSolMulta(0L);
			
			try {
				List<SolicitudDTO> solicitudesBeneficio = solicitudDelegate.consultarSolicitudesPorExpedienteTiposDocumentoYEstatus
					(expediente, tiposDocumento, null);
				if (solicitudesBeneficio != null){
					for (SolicitudDTO sol : solicitudesBeneficio){
						boolean solicitudAbierta = sol.getEstatus().getIdCampo().equals(EstatusSolicitud.ABIERTA.getValorId());
						if (sol.getTipoDocumentoDTO().getIdCampo().equals(idRemisionParcial)){
							forma.setAutorizacionRPP(Boolean.TRUE);
							if (solicitudAbierta){
								forma.setIdSolRPP(sol.getDocumentoId());
							}
						}
						if (sol.getTipoDocumentoDTO().getIdCampo().equals(idLibertadCondicional)){
							forma.setAutorizacionLC(Boolean.TRUE);
							if (solicitudAbierta){
								forma.setIdSolLC(sol.getDocumentoId());
							}
						}
						if (sol.getTipoDocumentoDTO().getIdCampo().equals(idMulta)){
							if (solicitudAbierta){
								forma.setIdSolMulta(sol.getDocumentoId());								
							}
						}
						if (sol.getTipoDocumentoDTO().getIdCampo().equals(idReparacionDanio)){
							if (solicitudAbierta){
								forma.setIdSolRD(sol.getDocumentoId());								
							}
						}
					}
				}
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
}

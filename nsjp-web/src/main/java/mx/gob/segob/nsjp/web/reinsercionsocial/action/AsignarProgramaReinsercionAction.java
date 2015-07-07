/**
* Nombre del Programa 				: AsignarProgramaReinsercionAction.java
* Autor                            	: EdgarTE
* Compania                    		: Ultrasist
* Proyecto                      	: NSJP                    	Fecha: 6 Mar 2012
* Marca de cambio        			: N/A
* Descripcion General    			: Describir el objetivo de la clase de manera breve
* Programa Dependiente  			: N/A
* Programa Subsecuente 				: N/A
* Cond. de ejecucion        		: N/A
* Dias de ejecucion          		: N/A 						Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       		:N/A
* Compania               			:N/A
* Proyecto                 			:N/A                     	Fecha: N/A
* Modificacion           			:N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.reinsercionsocial.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.programa.ProgramaDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionCentroDetencionDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatCursoDTO;
import mx.gob.segob.nsjp.dto.programas.CatProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.CatTrabajoDTO;
import mx.gob.segob.nsjp.dto.programas.CursoDTO;
import mx.gob.segob.nsjp.dto.programas.ProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.TrabajoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.reinsercionsocial.form.AsignarProgramaReinsercionForm;

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
 * Clase que modela el action de struts para solventar la funcionalidad relacionada con la 
 * asignación de programas.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class AsignarProgramaReinsercionAction extends GenericAction {
	
	private static final Logger LOG  = Logger.getLogger(AsignarProgramaReinsercionAction.class);
	
	//Forwards de la forma
	private static final String ASIGNAR_PROGRAMAS = "asignar.programas.page";
	
	//Constantes
	private static final String PROGRAMAS_DISPONIBLES = "programasDisponibles";
	private static final String PROGRAMAS_ASIGNADOS = "programasAsignados";
	private static final String ASIGNACION_DE_PUNTOS = "asignacionDePuntos";
	private static final String PARAM_CATALOGO = "catalogo";
	private static final String PARAM_ID_SENTENCIA = "sentenciaId";
	private static final String PARAM_ID = "id";
	private static final String PARAM_ID_ASIGNACIONPROGRAMA = "asignacionProgramaId";
	private static final String PARAM_ASOC_EXITO = "asociacionExitosa";
	private static final String PARAM_CERTIFICADO_EMITIDO = "bCertificadoEmitido";
	private static final String PARAM_PROGRAMA_CUMPLIDO = "bProgramaCumplido";
	private static final String PARAM_GRID_ID = "gridID";
	private static final String PARAM_ID_ESTATUS_NUM_EXP = "idEstatusNumeroExpediente";
	private static final String PARAM_ESTA_ARRAIGADO = "estaArraigado";
	private static final String PARAM_MOTIVO = "motivo";
	private static final String PARAM_ID_NUMERO_EXPEDIENTE = "idNumeroExpediente";
	private static final String FORMATO_BASICO_FECHA = "dd/MM/yyyy";
	private static final String ATTR_SENTENCIA = "sentenciaConsultada";
	private static final String FWD_ASIGNA_PROGRAMA = "asignar.programas.do";
	private static final String CONFIRMACION = "S&#237;";
	private static final String NEGACION = "No";
	private static final String INGRESO = "Ingreso";
	
	public static final String ES_CURSO = "esCurso";
	public static final String ES_TRABAJO = "esTrabajo";
	
	@Autowired
	public ProgramaDelegate programaDelegate;
	
	@Autowired
	public AsignacionProgramaDelegate asignacionProgramaDelegate;
	
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	
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
	public ActionForward asignarProgramas(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (form instanceof AsignarProgramaReinsercionForm){
			AsignarProgramaReinsercionForm forma = (AsignarProgramaReinsercionForm) form;
			forma.setSentenciaId(Long.parseLong(request.getParameter(PARAM_ID_SENTENCIA)));
			DateFormat sdf = new SimpleDateFormat(FORMATO_BASICO_FECHA);
			SentenciaDTO sentencia = consultarSentencia(request);
			request.getSession().setAttribute(ATTR_SENTENCIA, sentencia);
			forma.setFechaFinPena(sdf.format(sentencia.getDfechaFinPena()));
			forma.setFechaInicioPena(sdf.format(sentencia.getDfechaInicioPena()));
			forma.setNombreSentenciado(sentencia.getInvolucradoDTO().getNombreCompleto());
			forma.setNus(sentencia.getCnus());
			forma.setTipoSentencia(sentencia.getValorDTO().getValor());
			if (sentencia.getCentroDetencionActual() != null){
				forma.setNombreCereso(sentencia.getCentroDetencionActual().getNombre());
			}else{
				forma.setNombreCereso("");
			}
			forma.setPuntosPorAcumular(sentencia.getIpuntosPorAcumular());
			forma.setPuntosAcumulados(sentencia.getTotalPuntosObtenidos());
			forma.setNumeroExpedienteId(sentencia.getNumeroExpedienteDTO().getNumeroExpedienteId());
		}
		return mapping.findForward(ASIGNAR_PROGRAMAS);
	}
	
	public ActionForward llenarGridAsignacionPrograma (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		LOG.debug("ejecutando Action AsignarProgramaReinsercionSocialAction método llenarGrid");
		try {

			String grid = request.getParameter(PARAM_GRID_ID);
			Boolean filtroCert = obtenerParametroBooleano(request, PARAM_CERTIFICADO_EMITIDO);
			Boolean filtroCump = obtenerParametroBooleano(request, PARAM_PROGRAMA_CUMPLIDO);
			
			List<? extends Object> listaObjetos = null;
			
			SentenciaDTO sentencia = consultarSentencia(request);
			List<Long> puntosObtenidos = null;
			List<AsignacionProgramaDTO> lstEsAceptado = null;
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			if (pag != null){
				pag.setCampoOrd("cp.catTipoPrograma.catTipoProgramaId, cp.cnombre");
				PaginacionThreadHolder.set(pag);
				
				List<CatProgramaDTO> programasAsignados = creaListaProgramasAsignados(sentencia, filtroCert, filtroCump);
				
				if(grid.equals(PROGRAMAS_DISPONIBLES)){
					listaObjetos = programaDelegate.consultarProgramasDisponibles(sentencia.getCentroDetencionActual(), 
																				  programasAsignados, 
																				  new Date());
				} else if(grid.equals(PROGRAMAS_ASIGNADOS) || grid.equals(ASIGNACION_DE_PUNTOS)) {
					lstEsAceptado = sentencia.getAsignacionProgramas();
					puntosObtenidos = creaListaPuntosProgramaAsignado(sentencia, filtroCert, filtroCump);
					listaObjetos = programasAsignados;
				}
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
				if (pag2 != null && pag2.getTotalRegistros() != null) {
					writer.print("<total>" + pag2.getTotalPaginas() + "</total>");
					writer.print("<records>" + pag2.getTotalRegistros() + "</records>");
				} else {
					writer.print("<total>0</total>");
					writer.print("<records>0</records>");
				}
			}
			List<List<String>> tablaValores = new ArrayList<List<String>>();
			for (int i=0; i<listaObjetos.size(); i++) {
				Object object = listaObjetos.get(i);
				if (object instanceof CatProgramaDTO){
					CatProgramaDTO catProgramaDTO = (CatProgramaDTO) object;
					List<String> filas = null;
					if (puntosObtenidos != null && !puntosObtenidos.isEmpty()){
						filas = crearFilaParaGrid(catProgramaDTO,puntosObtenidos.get(i));
					}else{
						filas = crearFilaParaGrid(catProgramaDTO, null);
					}
					
					if(grid.equals(PROGRAMAS_ASIGNADOS) 
							&& lstEsAceptado != null 
							&& !lstEsAceptado.isEmpty()) {
						AsignacionProgramaDTO asignacionProgramaDTO = lstEsAceptado.get(i);
						CatProgramaDTO tmp = asignacionProgramaDTO.getProgramaDTO().getCatProgramaDTO();
						if(tmp.getProgramaId().compareTo(catProgramaDTO.getProgramaId()) == 0) {
							if (asignacionProgramaDTO.getBaceptado()){
								filas.add(CONFIRMACION);
							}else{
								filas.add(NEGACION);
							}
						}
					}
					if(grid.equals(PROGRAMAS_DISPONIBLES)){
						filas.add(NEGACION);
					}

					if(grid.equals(ASIGNACION_DE_PUNTOS) 
							&& lstEsAceptado != null 
							&& !lstEsAceptado.isEmpty()) {
						AsignacionProgramaDTO asignacionProgramaDTO = lstEsAceptado.get(i);
						filas.add(asignacionProgramaDTO.getAsignacionProgramaId().toString());						
					}
					
					
					tablaValores.add(filas);
				}
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
	
	public ActionForward obtenerDetallesAsignacionPrograma (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
				
			String catalogo = request.getParameter(PARAM_CATALOGO);
			String id = request.getParameter(PARAM_ID);
			SentenciaDTO sentencia = consultarSentencia(request);
			
			if(id!=null){
				Object objeto = null;
				Long puntos = null;
				if(catalogo.equals(PROGRAMAS_DISPONIBLES)){
					CatProgramaDTO catProgramaDTO = new CatProgramaDTO();
					catProgramaDTO.setProgramaId(Long.parseLong(id));
					objeto = programaDelegate.consultarProgramaPorId(catProgramaDTO);	
				}else if(catalogo.equals(PROGRAMAS_ASIGNADOS)){
					CatProgramaDTO catProgramaDTO = new CatProgramaDTO();
					catProgramaDTO.setProgramaId(Long.parseLong(id));
					objeto = programaDelegate.consultarProgramaPorId(catProgramaDTO);
					puntos = obtenerPuntosObtenidosPrograma(catProgramaDTO, sentencia);
				}
				
				response.setContentType("text/javascript; charset=ISO-8859-1");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
			
				writer.print(dto2Json(objeto,puntos));
				writer.flush();
				writer.close();
			}
		
		} catch (Exception e) {		
			LOG.info(e.getCause(),e);
		}
		return null;
	}
	
	public ActionForward guardarAsignacionesProgramas (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		if (form instanceof AsignarProgramaReinsercionForm){
			AsignarProgramaReinsercionForm forma = (AsignarProgramaReinsercionForm) form;
			String[] idsCatProgramasAgregar = forma.getIdsProgramasConcatenados().split(",");
			
			SentenciaDTO sentencia = consultarSentencia(request);
			
			List<AsignacionProgramaDTO> asignacionesPrograma = 
				(sentencia.getAsignacionProgramas() != null) ? 
						sentencia.getAsignacionProgramas() 
						: new ArrayList<AsignacionProgramaDTO>();
			
			for(String idCatPrograma : idsCatProgramasAgregar){
				CatProgramaDTO catProgramaDTO = new CatProgramaDTO();
				catProgramaDTO.setProgramaId(Long.parseLong(idCatPrograma));
				ProgramaDTO instancia = new ProgramaDTO();
				try {
					catProgramaDTO = programaDelegate.consultarProgramaPorId(catProgramaDTO);
					instancia.setDfechaIngreso(catProgramaDTO.getFechaInicioPrograma());
					instancia.setDfechaTermino(catProgramaDTO.getFechaFinPrograma());
					instancia.setCatProgramaDTO(catProgramaDTO);
					instancia = asignacionProgramaDelegate.crearPrograma(instancia);
					if (catProgramaDTO.getLstCatCursoDTO() != null && !catProgramaDTO.getLstCatCursoDTO().isEmpty()){
						List<CursoDTO> cursos = new ArrayList<CursoDTO>(); 
						for (CatCursoDTO catCurso : catProgramaDTO.getLstCatCursoDTO()){
							CursoDTO c = new CursoDTO();
							c.setCatCursoDTO(catCurso);
							c.setDfechaIngreso(catProgramaDTO.getFechaInicioPrograma());
							c.setBcompletado(false);
							c.setProgramaDTO(instancia);
							c.setIpuntosObtenidos(0L);
							cursos.add(c);
						}
						asignacionProgramaDelegate.crearCursos(cursos);
						instancia.setCursos(cursos);
					}
					if (catProgramaDTO.getLstCatTrabajoDTO() != null && !catProgramaDTO.getLstCatTrabajoDTO().isEmpty()){
						List<TrabajoDTO> trabajos = new ArrayList<TrabajoDTO>(); 
						for (CatTrabajoDTO catTrabajo : catProgramaDTO.getLstCatTrabajoDTO()){
							TrabajoDTO t = new TrabajoDTO();
							t.setCatTrabajoDTO(catTrabajo);
							t.setDfechaIngreso(catProgramaDTO.getFechaInicioPrograma());
							t.setBcompletado(false);
							t.setProgramaDTO(instancia);
							t.setIpuntosObtenidos(0L);
							trabajos.add(t);
						}
						asignacionProgramaDelegate.crearTrabajos(trabajos);
						instancia.setTrabajos(trabajos);
					}
				} catch (NSJPNegocioException e) {
					e.printStackTrace();
				}
				AsignacionProgramaDTO asignacion = new AsignacionProgramaDTO();
				asignacion.setBcertificadoEmitido(Boolean.FALSE);
				asignacion.setBaceptado(Boolean.FALSE);
				asignacion.setProgramaDTO(instancia);
				asignacion.setSentenciaDTO(sentencia);
				asignacionesPrograma.add(asignacion);
			}
			sentencia.setAsignacionProgramas(asignacionesPrograma);
			asignacionProgramaDelegate.registrarSentencia(sentencia);
			return new ActionForward(mapping.findForward(FWD_ASIGNA_PROGRAMA).getPath()+"?"+PARAM_ID_SENTENCIA+"=" + sentencia.getSentenciaId()
					+"&"+PARAM_ASOC_EXITO+"="+Boolean.TRUE, false);
		}
		
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la actualización del campo bCertificadoEmitido para una 
	 * asignaci&oacute;n de programa, asigna el valor del certificado emitido a verdadero. 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return null - Ya que el servicio s&oacute;lo se limita a llevar a cabo la consulta. 
	 */
	public ActionForward persistirCertificadoEmitido (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String strAsignacionPrograma = request.getParameter(PARAM_ID_ASIGNACIONPROGRAMA);
		Long idAsignacionPrograma = 0L;
		if (strAsignacionPrograma != null
				&& !strAsignacionPrograma.trim().isEmpty()){
			idAsignacionPrograma = Long.parseLong(strAsignacionPrograma);
		}
		AsignacionProgramaDTO asigProg = new AsignacionProgramaDTO();
		asigProg.setAsignacionProgramaId(idAsignacionPrograma);
		
		try {
			asigProg = asignacionProgramaDelegate.consultarAsignacionProgramaPorId(asigProg);
			if (asigProg != null){
				asigProg.setBcertificadoEmitido(Boolean.TRUE);
			}
			asignacionProgramaDelegate.actualizarAsignacionPrograma(asigProg);
			
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML+ "; "+ConstantesGenerales.CHARSET_ISO_8859);
			response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
			PrintWriter writer = response.getWriter();
			writer.print("<mensaje>&#201;xito</mensaje>");
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la funcionalidad relacionada con la integraci&oacute;n del 
	 * procedimiento de ejecuci&oacute;n. Se actualiza la asignaci&oacute;n del centro de 
	 * detenci&oacute;n del sentenciado, ya que en este momento es cuando el sentenciado ingresa 
	 * al CERERESO. Asimismo se actualiza el estatus del expediente de Reinserci&oacute;n social,
	 * para reflejar el nuevo estado una vez que ha ingresado el sentenciado. 
	 * 
	 * @param mapping - mapeos de struts para enlazar las peticiones.
	 * @param form - forma de struts con la informaci&oacute;n pasada dentro de la petici&oacute;n
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML.
	 * @param response - Objeto de java que representa la respuesta en HTML.
	 * @return null - Se escribe la respuesta dentro de un xml.
	 */
	public ActionForward integrarProcedimientoEjecucion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		Long numeroExpedienteId = NumberUtils.toLong(request.getParameter(PARAM_ID_NUMERO_EXPEDIENTE), 0L);
		Long estatusId = NumberUtils.toLong(request.getParameter(PARAM_ID_ESTATUS_NUM_EXP),0L);
		Boolean estaArraigado = BooleanUtils.toBooleanObject(request.getParameter(PARAM_ESTA_ARRAIGADO)); 
		String motivo = request.getParameter(PARAM_MOTIVO);
		Long sentenciaId = NumberUtils.toLong(request.getParameter(PARAM_ID_SENTENCIA), 0L);
		try {
			if (sentenciaId > 0L){
				SentenciaDTO sentenciaDTO = new SentenciaDTO();
				sentenciaDTO.setSentenciaId(sentenciaId);
				sentenciaDTO = asignacionProgramaDelegate.consultarSentencia(sentenciaDTO);
				
				AsignacionCentroDetencionDTO asignacionCentroDetencionDTO = sentenciaDTO.getAsignacionCentroDetencionActual();
				if (asignacionCentroDetencionDTO != null){
					if(estaArraigado != null) { 
						asignacionCentroDetencionDTO.setBarraigado(estaArraigado);
					}
					if(motivo != null){
						asignacionCentroDetencionDTO.setCmotivo(motivo);
						if (motivo.equalsIgnoreCase(INGRESO)){
							asignacionCentroDetencionDTO.setDfechaIngreso(new Date());							
						}
					}
					asignacionProgramaDelegate.actualizarCentroDetencion(asignacionCentroDetencionDTO);				
				}
			}
			if (estatusId > 0L
					&& numeroExpedienteId > 0L){				
				ExpedienteDTO expediente = new ExpedienteDTO();
				expediente.setNumeroExpedienteId(numeroExpedienteId);
				expediente.setEstatus(new ValorDTO(estatusId));
				
				expedienteDelegate.actualizarEstatusExpediente(expediente);
			}
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML+ "; "+ConstantesGenerales.CHARSET_ISO_8859);
			response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
			PrintWriter writer = response.getWriter();
			writer.print("<mensaje>&#201;xito</mensaje>");
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @param writer
	 */
	private String dto2Json(Object object, Long puntos) {
		JSONObject jsonObject = null;
		StringWriter out = null;
		String jsonText = "";
		if (object instanceof CatProgramaDTO){
			CatProgramaDTO catProgramaDTO = (CatProgramaDTO) object;
			jsonObject = convertirJson(catProgramaDTO, puntos);
		}
		out = new StringWriter();
		try {
			jsonObject.writeJSONString(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jsonText = out.toString();
		return jsonText;		
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject convertirJson (CatProgramaDTO catProgramaDTO, Long puntos){
		JSONObject json = null;
		DateFormat sdf = null;
		if (catProgramaDTO != null){
			json = new JSONObject();
			sdf = new SimpleDateFormat(FORMATO_BASICO_FECHA);
			json.put("CatProgramaId",catProgramaDTO.getProgramaId());
			json.put("CatProgramaNombre",catProgramaDTO.getNombre());
			
			if(catProgramaDTO.getCatTipoProgramaDTO() != null){
				json.put("CatTipoProgramaId",catProgramaDTO.getCatTipoProgramaDTO().getCatTipoProgramaId());
				json.put("CatTipoProgramaNombre", catProgramaDTO.getCatTipoProgramaDTO().getDescripcion());
			}else{
				json.put("CatTipoProgramaId",-1L);
				json.put("CatTipoProgramaNombre", "");
			}

			json.put("CatProgramaDescripcion", catProgramaDTO.getDescripcion()); 
			if (puntos != null){
				json.put("CatProgramaPuntos", puntos);
			}else{
				json.put("CatProgramaPuntos", catProgramaDTO.getTotalPuntosPrograma());
			}
			json.put("CatProgramaFechaInicio", sdf.format(catProgramaDTO.getFechaInicioPrograma())); 
			json.put("CatProgramaFechaInicio", sdf.format(catProgramaDTO.getFechaFinPrograma()));
			
			if (catProgramaDTO.getLstCatCursoDTO() != null && !catProgramaDTO.getLstCatCursoDTO().isEmpty()){
				JSONArray jsonArrayCursos = new JSONArray();
				for (CatCursoDTO catCursoDTO : catProgramaDTO.getLstCatCursoDTO()){
					jsonArrayCursos.add(convertirJson(catCursoDTO));
				}
				json.put("CatProgramaCursos", jsonArrayCursos);
			}
			
			if (catProgramaDTO.getLstCatTrabajoDTO() != null && !catProgramaDTO.getLstCatTrabajoDTO().isEmpty()){
				JSONArray jsonArrayTrabajos = new JSONArray();
				for (CatTrabajoDTO catTrabajoDTO : catProgramaDTO.getLstCatTrabajoDTO()){
					jsonArrayTrabajos.add(convertirJson(catTrabajoDTO));
				}
				json.put("CatProgramaTrabajos", jsonArrayTrabajos);
			}
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject convertirJson (CatCursoDTO catCursoDTO){
		JSONObject json = null;
		if (catCursoDTO != null){
			json = new JSONObject();
			json.put("CatCursoId",catCursoDTO.getCatCursoId());
			json.put("CatCursoNombre", catCursoDTO.getCnombre());
			json.put("CatCursoPuntos", catCursoDTO.getIpuntos());
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject convertirJson (CatTrabajoDTO catTrabajoDTO){
		JSONObject json = null;
		if (catTrabajoDTO != null){
			json = new JSONObject();
			json.put("CatTrabajoId", catTrabajoDTO.getCatTrabajoId());
			json.put("CatTrabajoNombre", catTrabajoDTO.getCnombre());
			json.put("CatTrabajoPuntos", catTrabajoDTO.getIpuntos());
		}
		return json;
	}
	
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
								buffer.append("&nbsp;");
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
	
	private List<String> crearFilaParaGrid(CatProgramaDTO catProgramaDTO, Long puntos){
		DateFormat sdf = new SimpleDateFormat(FORMATO_BASICO_FECHA);
		List<String> fila = new ArrayList<String>();
		fila.add(catProgramaDTO.getProgramaId().toString());
		fila.add(catProgramaDTO.getCatTipoProgramaDTO().getDescripcion());
		fila.add(catProgramaDTO.getNombre());
		fila.add(sdf.format(catProgramaDTO.getFechaInicioPrograma()));
		fila.add(sdf.format(catProgramaDTO.getFechaFinPrograma()));
		if (puntos == null){
			fila.add(catProgramaDTO.getTotalPuntosPrograma().toString());
		}else{
			fila.add(puntos.toString());
		}
		return fila;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creaci&oacute;n de la lista de programas asignados a una sentencia
	 * tomando en cuenta la bandera que establece si el certificado para el programa ha sido emitido.
	 * @param sentenciaDTO - Sentencia de la cual se va a crear la lista de programas asignados.
	 * @param filtroCert - Filtro en base al cual se van a considerar los programas dependiendo de si el 
	 * 					   certificado de cumplimiento correspondiente ha sido emitido o no.
	 * @param filtroCump - Filtro en base al cual se van a considerar los programas dependiendo de si el 
	 * 					   programa se ha cumplido o no.
	 * @return List<CatProgramaDTO> - Lista con el cat&aacute;logo de programas que han sido asignados a la
	 * 								  sentencia consultada.
	 */
	private List<CatProgramaDTO> creaListaProgramasAsignados(SentenciaDTO sentenciaDTO, Boolean filtroCert, Boolean filtroCump){
		List<CatProgramaDTO> catProgramas = null;
		if (sentenciaDTO != null 
				&& sentenciaDTO.getAsignacionProgramas() != null
				&& !sentenciaDTO.getAsignacionProgramas().isEmpty()){
			catProgramas = new ArrayList<CatProgramaDTO>();
			for (AsignacionProgramaDTO asignacionPrograma : sentenciaDTO.getAsignacionProgramas()){
				if (filtroCert != null){
					if (filtroCert.equals(asignacionPrograma.isBcertificadoEmitido())){
						catProgramas.add(asignacionPrograma.getProgramaDTO().getCatProgramaDTO());
					}
				}else if (filtroCump != null){
					if (filtroCump.equals(asignacionPrograma.getProgramaDTO().isProgramaCumplido())){
						catProgramas.add(asignacionPrograma.getProgramaDTO().getCatProgramaDTO());
					}
				}
				else{
					catProgramas.add(asignacionPrograma.getProgramaDTO().getCatProgramaDTO());					
				}
			}
		}
		return catProgramas;
	}
	
/*	private SentenciaDTO obtenerSentencia(HttpServletRequest request){
		Object attr = request.getSession().getAttribute(ATTR_SENTENCIA);
		SentenciaDTO sentencia = null;
		Long sentenciaId = NumberUtils.toLong(request.getParameter(PARAM_ID_SENTENCIA), 0L);
		
		if (attr != null && attr instanceof SentenciaDTO){
			sentencia = (SentenciaDTO) attr;
		}
		if (sentencia == null || (sentenciaId > 0 && sentencia.getSentenciaId() != sentenciaId )) {
			try {
				if(sentenciaId > 0) {
					sentencia = new SentenciaDTO();
					sentencia.setSentenciaId(sentenciaId);
					sentencia = asignacionProgramaDelegate.consultarSentencia(sentencia);
					request.getSession().setAttribute(ATTR_SENTENCIA, sentencia);
				}
			} catch (NSJPNegocioException e) {
				LOG.error(e.getMessage(), e);
			}			
		}				
		return sentencia;
	}
*/
	
	private List<Long> creaListaPuntosProgramaAsignado(SentenciaDTO sentenciaDTO, Boolean filtroCert, Boolean filtroComp){
		List<Long> puntosObtenidos = null;
		List<AsignacionProgramaDTO> asignacionesPrograma = sentenciaDTO.getAsignacionProgramas();
		if (asignacionesPrograma != null && !asignacionesPrograma.isEmpty() ){
			puntosObtenidos = new ArrayList<Long>();
			for (AsignacionProgramaDTO asignacion : asignacionesPrograma){
				if (filtroCert != null){
					if (filtroCert.equals(asignacion.isBcertificadoEmitido())){
						puntosObtenidos.add(asignacion.getProgramaDTO().getPuntosObtenidosPrograma());
					}
				}else if (filtroComp != null){
					if (filtroComp.equals(asignacion.getProgramaDTO().isProgramaCumplido())){
						puntosObtenidos.add(asignacion.getProgramaDTO().getPuntosObtenidosPrograma());
					}
				}
				else{
					puntosObtenidos.add(asignacion.getProgramaDTO().getPuntosObtenidosPrograma());					
				}
			}
		}
		return puntosObtenidos;
	}
	
	private Long obtenerPuntosObtenidosPrograma(CatProgramaDTO catProgramaDTO, SentenciaDTO sentenciaDTO){
		Long puntosObtenidos = catProgramaDTO.getTotalPuntosPrograma();
		for(AsignacionProgramaDTO asignacionPrograma : sentenciaDTO.getAsignacionProgramas()){
			if (asignacionPrograma.getProgramaDTO().getCatProgramaDTO().getProgramaId() == catProgramaDTO.getProgramaId()){
				puntosObtenidos = asignacionPrograma.getProgramaDTO().getPuntosObtenidosPrograma();
			}
		}
		return puntosObtenidos;
	}
	
//	private String agregarBotonEnviar(AsignacionProgramaDTO asignacionProgramaDTO) {
//		StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("<input type='button' ");
//		if (asignacionProgramaDTO.getBaceptado()) {
//			stringBuffer.append(" value='Reenviar' ");
//		} else {
//			stringBuffer.append(" value='Enviar' ");
//		}
//		stringBuffer.append(" onclick='enviarParaAutorizacion(");
//		stringBuffer.append(asignacionProgramaDTO.getAsignacionProgramaId());
//		stringBuffer.append(")'");
//		stringBuffer.append(" class='btn_Generico' ");
//		stringBuffer.append(" />");
//		return stringBuffer.toString();
//	}

	public ActionForward asignacionDePuntos (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		LOG.debug("ejecutando Action AsignarProgramaReinsercionSocialAction m&eacute;todo asignacionDePuntos");

		StringBuilder html = new StringBuilder(); 
		
		try {

			Long asignacionProgramaId = NumberUtils.toLong(request.getParameter("asignacionProgramaId"), 0L);	
			
			if (asignacionProgramaId == 0){ 
				JSONParser parser = new JSONParser();		
				
				StringBuilder sb = new StringBuilder();
			    BufferedReader br = request.getReader();
			    String str;
			    while( (str = br.readLine()) != null ){
			        sb.append(str);
			    }    
			    Object obj = parser.parse(sb.toString());
				
			    JSONArray jsonArray = (JSONArray) obj;
			    
			    if (jsonArray != null && !jsonArray.isEmpty()){
					response.setContentType("text/javascript; charset=ISO-8859-1");
			    	List<CursoDTO> lstCursosDTO = new ArrayList<CursoDTO>();
			    	List<TrabajoDTO> lstTrabajosDTO = new ArrayList<TrabajoDTO>();
			    	SentenciaDTO sentenciaDTO = new SentenciaDTO();
					@SuppressWarnings("unchecked")
					Iterator<JSONObject> iterator = jsonArray.iterator();
					while (iterator.hasNext()) {
						JSONObject json = iterator.next();
						
						if (json.get("objetoTipo").toString().equals(ES_CURSO)){
							CursoDTO cursoDTO = new CursoDTO();
							cursoDTO.setCursoId(NumberUtils.toLong(json.get("objetoId").toString()));
							cursoDTO.setIpuntosObtenidos(NumberUtils.toLong(json.get("objetoValor").toString()));
							cursoDTO.setBcompletado(BooleanUtils.toBooleanObject(json.get("objetoFinal").toString()));
							lstCursosDTO.add(cursoDTO);
							sentenciaDTO.setSentenciaId(NumberUtils.toLong(json.get("sentenciaId").toString()));
						} else if (json.get("objetoTipo").toString().equals(ES_TRABAJO)){
							TrabajoDTO trabajoDTO = new TrabajoDTO();
							trabajoDTO.setTrabajoId(NumberUtils.toLong(json.get("objetoId").toString()));
							trabajoDTO.setIpuntosObtenidos(NumberUtils.toLong(json.get("objetoValor").toString()));
							trabajoDTO.setBcompletado(BooleanUtils.toBooleanObject(json.get("objetoFinal").toString()));
							lstTrabajosDTO.add(trabajoDTO);
							sentenciaDTO.setSentenciaId(NumberUtils.toLong(json.get("sentenciaId").toString()));
						}
					}	
					
					if(!lstCursosDTO.isEmpty()){
						for (CursoDTO cursoDTO : lstCursosDTO) {
							asignacionProgramaDelegate.actualizarCurso(cursoDTO);
						}
					}
					
					if(!lstTrabajosDTO.isEmpty()){
						for (TrabajoDTO trabajoDTO : lstTrabajosDTO) {
							asignacionProgramaDelegate.actualizarTrabajo(trabajoDTO);
						}
					}
					html.append("{\"exito\":\"Los datos se han guardado con exito.\",");
					
					if (sentenciaDTO.getSentenciaId() > 0 ){
						sentenciaDTO = asignacionProgramaDelegate.consultarSentencia(sentenciaDTO);
					
						DatosGeneralesReinsercionAction action = new DatosGeneralesReinsercionAction();
						if(sentenciaDTO != null
								&& sentenciaDTO.getTotalPuntosObtenidos() != null
								&& sentenciaDTO.getIpuntosPorAcumular() != null) {
						html.append("\"totalPuntosObtenidos\":\"")
						.append(sentenciaDTO.getTotalPuntosObtenidos())
						.append("\",");
						
						html.append("\"porcentajeCumplimiento\":\"")
							.append(action.calcularPorcentajeCumplimientoPuntos(
									sentenciaDTO.getIpuntosPorAcumular().intValue(),
									sentenciaDTO.getTotalPuntosObtenidos().intValue()))
							.append("\"");			
						}
					}
					html.append("}");			
				} else {
					html.append("{\"error\":\"Parametros insuficientes.\"}");
				}
			} else {
				response.setContentType("text/html");
				AsignacionProgramaDTO asignacionProgramaDTO = new AsignacionProgramaDTO();				
				asignacionProgramaDTO.setAsignacionProgramaId(asignacionProgramaId);				
				asignacionProgramaDTO = asignacionProgramaDelegate.consultarAsignacionProgramaPorId(asignacionProgramaDTO);
				html.append(getHTMLAsignacionPuntos(asignacionProgramaDTO));				
			}
				
		} catch (Exception e) {		
			LOG.error(e.getCause(),e);
			html.append("Ha ocurrido un error, por favor intente mas tarde.");
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
	 * @param html
	 * @param asignacionProgramaDTO
	 */
	private String getHTMLAsignacionPuntos(
			AsignacionProgramaDTO asignacionProgramaDTO) {
		StringBuilder html = new StringBuilder(); 
		if(asignacionProgramaDTO != null
				&& asignacionProgramaDTO.getProgramaDTO() != null) {
			ProgramaDTO programaDTO = asignacionProgramaDTO.getProgramaDTO();
			
			if((programaDTO.getCursos() == null 
						|| programaDTO.getCursos().isEmpty())					
					&& (programaDTO.getTrabajos() == null 
						|| programaDTO.getTrabajos().isEmpty())){
				html.append("No hay Cursos y/o Trabajos para asignar puntos.");	
			}
			
			if(programaDTO.getCursos() != null && !programaDTO.getCursos().isEmpty()){					
				html.append("<fieldset>");
				html.append("<legend>Cursos</legend>");
				html.append("<table>");
				for (CursoDTO cursoDTO : programaDTO.getCursos()) {
					html.append("<tr>");
					html.append("<td align='right'>"+cursoDTO.getCatCursoDTO().getCnombre()+"</td>");
					html.append("<td align='left'>");
					html.append("<input   type='text' ");
							html.append(" name='curso"+cursoDTO.getCursoId()+"' ");
							html.append(" id='curso"+cursoDTO.getCursoId()+"'");
							html.append(" objetoId='"+cursoDTO.getCursoId()+"'");
							html.append(" objetoTipo='" + ES_CURSO + "'");
							html.append(" onKeyPress='return numerosDecimales(event);' ");
							html.append(" onBlur='validarMaximoPuntos(this);' ");
							html.append(" maxpuntos='" + cursoDTO.getCatCursoDTO().getIpuntos()+"' ");
							if (cursoDTO.getIpuntosObtenidos()!= null){
								html.append(" value='"+(cursoDTO.getIpuntosObtenidos() > 0L ? cursoDTO.getIpuntosObtenidos() : "") +"'");	
							}
							html.append(" />");
							html.append(" M&aacute;x. " + cursoDTO.getCatCursoDTO().getIpuntos());
					html.append("</td>");
					html.append("</tr>");
				}
				html.append("</table>");
				html.append("</fieldset>");			
			}
			if(programaDTO.getTrabajos() != null && !programaDTO.getTrabajos().isEmpty()){					
				html.append("<fieldset>");
				html.append("<legend>Trabajos</legend>");
				html.append("<table>");					
				for (TrabajoDTO trabajoDTO : programaDTO.getTrabajos()) {
					html.append("<tr>");
					html.append("<td align='right'>"+trabajoDTO.getCatTrabajoDTO().getCnombre()+"</td>");
					html.append("<td align='left'>");
					html.append("<input   type='text' ");
							html.append(" name='trabajo"+trabajoDTO.getTrabajoId()+"' ");
							html.append(" id='trabajo"+trabajoDTO.getTrabajoId()+"'");
							html.append(" objetoId='"+trabajoDTO.getTrabajoId()+"'");
							html.append(" objetoTipo='" + ES_TRABAJO + "'");
							html.append(" onKeyPress='return numerosDecimales(event);' ");
							html.append(" onBlur='validarMaximoPuntos(this);' ");
							html.append("  " + trabajoDTO.getCatTrabajoDTO().getIpuntos());
							html.append(" maxpuntos = '" + trabajoDTO.getCatTrabajoDTO().getIpuntos()+"' ");
							if (trabajoDTO.getIpuntosObtenidos()!= null){
								html.append(" value='"+(trabajoDTO.getIpuntosObtenidos() > 0L  ? trabajoDTO.getIpuntosObtenidos() :"")+"'");	
							}
							html.append(" />");
							html.append(" M&aacute;x. " + trabajoDTO.getCatTrabajoDTO().getIpuntos());
					html.append("</td>");
					html.append("</tr>");								
				}
				html.append("</table>");
				html.append("</fieldset>");			
			}				
		} else {
			html.append("El Programa no existe");
		}
		return html.toString();
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la sentencia desde la base de datos a trav&eacute;s
	 * de unl identificador de la sentencia. Dicho identificador se obtiene como par&aacute;metro anidado
	 * dentro del objeto HttpServletRequest.
	 * @param request - Objeto del cual se obtiene el par&aacute;metro para poder llevar a cabo la consulta
	 * 					de la sentencia.
	 * @return sentencia - SentenciaDTO con la informaci&oacute;n de la sentencia que se encuentra persistida
	 * 					   en la base de datos.
	 */
	private SentenciaDTO consultarSentencia(HttpServletRequest request){
		Long sentenciaId = Long.parseLong(request.getParameter(PARAM_ID_SENTENCIA));
		SentenciaDTO sentencia = new SentenciaDTO();
		sentencia.setSentenciaId(sentenciaId);
		try {
			sentencia = asignacionProgramaDelegate.consultarSentencia(sentencia);
		} catch (NSJPNegocioException e) {
			LOG.error(e.getMessage(), e);
		}
		return sentencia;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la recuperaci&oacute;n de un par&aacute;metro booleano
	 * desde la solicitud de Http a trav&eacute;s del nombre de dicho parametro.
	 * @param request - La solicitud en la cual se encuentra contenido el parametro.
	 * @param nombreParametro - El nombre con el cual se recupera el par&aacute;metro de la 
	 * 							petici&oacute;n.
	 * @return parametro - variable booleana con el valor correspondiente del parseo 
	 * 					   del parametro.
	 */
	private Boolean obtenerParametroBooleano(HttpServletRequest request, String nombreParametro){
		Boolean parametro = null;
		String strParam = request.getParameter(nombreParametro);
		if (strParam != null && !strParam.trim().isEmpty()){
			parametro = Boolean.parseBoolean(strParam);
		}
		return parametro;
	}
}

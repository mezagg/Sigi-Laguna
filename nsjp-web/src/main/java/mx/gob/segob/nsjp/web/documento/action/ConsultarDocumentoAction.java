/**
* Nombre del Programa : ConsultarDocumentoAction.java
* Autor                            : Emigdio
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 06/07/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
* Programa Dependiente  :N/A
* Programa Subsecuente :N/A
* Cond. de ejecucion        :N/A
* Dias de ejecucion          :N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor                       :N/A
* Compania               :N/A
* Proyecto                 :N/A                                 Fecha: N/A
* Modificacion           :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.documento.action;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.CONTENT_TYPE_PDF;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_CACHE_CONTROL;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_CONTENT_DISPOSITION;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_INLINE_FILE_NAME;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_NOCACHE;
import static mx.gob.segob.nsjp.comun.constants.ConstantesGenerales.ENCABEZADO_PRAGMA;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.pdf.PDFPropiedad;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.forma.FormaDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoIntegracionDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.CamposFormaDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudMandamientoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.ReporteBaseAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action encargado de consultar el archivo digital de un documento y enviarlo
 * al navegador
 * @version 1.0
 * @author Emigdio Hern谩ndez
 *
 */
public class ConsultarDocumentoAction extends ReporteBaseAction {
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	private FormaDelegate formaDelegate;
	private static final String PARAM_EXPEDIENTE_ID = "expedienteId";
	private static final String PARAM_IDS_TIPOS_SOLICITUDES = "idsTiposSolicitudes";
	private static final String PARAM_IDS_ESTATUS_SOLICITUDES = "idsEstatusSolicitudes";
	private static final String PARAM_IDS_TIPOS_MANDAMIENTOS = "idsTiposMandamientos";
	private static final String KEY_JSON_DOCTO_INTEG_ID = "documentoIntegracionId";
	private static final String KEY_JSON_NOMBRE_TIPO_DOCUMENTO = "nombreTipoDocumento";
	private static final String KEY_JSON_OBLIGATORIO = "obligatorio";
	private static final String KEY_JSON_ASOCIADO = "asociado";
	
	private static final Logger logger = Logger
    .getLogger(ConsultarDocumentoAction.class);
	/**
     * Realiza la consulta de un documento o archivoDigital en base a su ID enviado como par谩metro del 
     * request.
     * Lo recupera de la base de datos, consulta su archivo digital y el contenido
     * lo escribe al response con el nombre del archivo digital y el tipo de archivo
     * Parametros necesario:
     * documentoId - Identificador del documento o
     * archivoDigitalId - Identificador del archivoDigital
     * 
     * @param mapping Configuraci贸n del mapping
     * @param form Form generado
     * @param request Objeto request
     * @param response Objeto response
     * @return null
     */
    public ActionForward consultarContenidoArchivoDigital(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    	
try {
    		
    		
	    		Long documentoId = request.getParameter("documentoId")!=null?NumberUtils.toLong(request.getParameter("documentoId")):null;
	    		
	    		Long archivoDigitalId = request.getParameter("archivoDigitalId")!=null?NumberUtils.toLong(request.getParameter("archivoDigitalId")):null; 
	    		//Boolean mostrarTipoArchivo = (request.getParameter("mostrarTipoArchivo")==null ? true:false);
                String mostrarTipoArchivoS = request.getParameter("mostrarTipoArchivo");
                Boolean mostrarTipoArchivo = true;
                if(mostrarTipoArchivoS!=null){
                        mostrarTipoArchivo = Boolean.parseBoolean(mostrarTipoArchivoS);
                } 
                logger.info("EJECUTANDO CONSULTAR ARCHIVO DIGITAL................documentoId:"+documentoId);
	    		logger.info("ARCHIVO DIGITAL ID OBTENIDO="+archivoDigitalId);
				ArchivoDigitalDTO archivo = documentoDelegate.consultarArchivoDigitalCompletoPorArchivoODocumento(documentoId, archivoDigitalId);
				
	    		if(archivo != null){
	    			UsuarioDTO user=getUsuarioFirmado(request);
	    			boolean op=user.getAreaActual().getAreaId().equals(Areas.VISITADURIA.parseLong());
	    			if(archivo.getTipoArchivo()!=null && archivo.getTipoArchivo().equals(".pdf") && op){
	    				response.setContentType("application/pdf");	
	    			}else{
	    				response.setContentType("application/octet-stream");	
	    			}
    	            response.setHeader(ENCABEZADO_CACHE_CONTROL, ENCABEZADO_NOCACHE);
    	            response.setHeader(ENCABEZADO_PRAGMA, ENCABEZADO_NOCACHE);
//	    			if(request.getParameter("inFrame") == null){
	    				//Escribirlo como attachment
    	        	if(mostrarTipoArchivo){
	            		response.setHeader(ENCABEZADO_CONTENT_DISPOSITION,
	    						ENCABEZADO_INLINE_FILE_NAME
	    	                    + archivo.getNombreArchivo().trim().replaceAll(" ", "_") + archivo.getTipoArchivo());
	            	}else{
	            		response.setHeader(ENCABEZADO_CONTENT_DISPOSITION,
	    						ENCABEZADO_INLINE_FILE_NAME
	    	                    + archivo.getNombreArchivo().trim().replaceAll(" ", "_"));
	            	}
//	    			}	    			
	    			response.setContentLength(archivo.getContenido().length);
	    			  ServletOutputStream sos = response.getOutputStream();
	    	            sos.write(archivo.getContenido());
	    	            sos.flush();
	    			
	    		}
				
			
			   
		    }catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
	    
    return null;
    }
    
    
    /**
     * Realiza la consulta de todas las plantillas por el flitro de tipo de documento
     * 
     * @param mapping Configuraci贸n del mapping
     * @param form Form generado
     * @param request Objeto request
     * @param response Objeto response
     * @return null
     */
    public ActionForward consultarPlantillasPorTipo(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    	
    	try {
    			//logger.info("_____________________________________________________________");
    			//logger.info("EJECUTANDO CONSULTA PLANTILLAS POR TIPO ACTION");
    			//logger.info("_____________________________________________________________");
    			
	    		//Se obtiene el id del tipo de documento
				String tipoDocumento = request.getParameter("tipoDocumento");
			//	logger.info("TIPO DE DOCUMENTO::::::::::"+tipoDocumento);
				List<FormaDTO> listaPlantillas = formaDelegate.consultarFormasPorTipoForma(NumberUtils.toLong(tipoDocumento));
				
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
				
				for (FormaDTO plantilla : listaPlantillas) {
					writer.print("<row id='" + plantilla.getFormaId()+ "'>");
						//Nombre de la plantilla
						writer.print("<cell>" + plantilla.getNombre()+ "</cell>");
						//Tipo de plantilla
						if(plantilla.getTipoFormaDTO() != null){
							writer.print("<cell>" + plantilla.getTipoFormaDTO().getValor()+ "</cell>");
						}else{
							writer.print("<cell>-</cell>");
						}
						//Fecha de creacion de la plantilla
						if(plantilla.getFechaCreacion() != null){
							writer.print("<cell>" + DateUtils.formatear(plantilla.getFechaCreacion())+ "</cell>");
						}else{
							writer.print("<cell>-</cell>");
						}
					writer.print("</row>");
				}			
				writer.print("</rows>");
				writer.flush();
				writer.close();
		    }catch (Exception e) {
				logger.error(e);
			}
		    
        return null;
    	
    }
    
    
    /**
     * Realiza la consulta de todas las plantillas por el flitro de tipo de documento
     * 
     * @param mapping Configuraci贸n del mapping
     * @param form Form generado
     * @param request Objeto request
     * @param response Objeto response
     * @return null
     */
    public ActionForward administrarPlantilla(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    	
    	try {
    			//logger.info("****************************************************************");
    			//logger.info("EJECUTANDO ADMINISTRAR PLANTILLA ACTION");
    			//logger.info("****************************************************************");
    			
	    		//Se obtiene el id del tipo de plantilla
			//	logger.info("PLANTILLA ID::::::::::"+request.getParameter("plantillaId"));
				Long plantillaId = null; 
				FormaDTO plantilla = null;
				
				if(request.getParameter("plantillaId") != null){
					plantillaId = NumberUtils.toLong(request.getParameter("plantillaId"));
				}
				if(plantillaId != null){
					plantilla	= formaDelegate.consultarDetalleForma(plantillaId);
				}
				if(plantilla == null){
					plantilla = new FormaDTO();
				}
				//logger.info("PLANTILLA"+plantilla);
				request.setAttribute("tipoPlantilla",plantilla.getTipoFormaDTO().getIdCampo());
				request.setAttribute("cuerpoPlantilla",plantilla.getCuerpo());
				request.setAttribute("nombrePlantilla",plantilla.getNombre());
				
		    }catch (Exception e) {
				logger.error(e);
			}
		    
        return mapping.findForward("succes");
    	
    }
    
    /**
     * Crea una nueva plantilla o almacena la plantilla editada
     * 
     * @param mapping Configuraci贸n del mapping
     * @param form Form generado
     * @param request Objeto request
     * @param response Objeto response
     * @return null
     */
    public ActionForward guardarPlantilla(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    
    	
    	
    	
    	FormaDTO forma = new FormaDTO();
    	Long formaId = NumberUtils.toLong(request.getParameter("formaId"));
    	if(formaId<1){
    		formaId = null;
    	}
    	forma.setFormaId(formaId);
    	forma.setCuerpo(request.getParameter("cuerpo"));
    	forma.setNombre(request.getParameter("nombre"));
    	forma.setDescForma(request.getParameter("nombre"));
    	forma.setTipoFormaDTO(new ValorDTO(NumberUtils.toLong(request.getParameter("tipoForma"))));
    	
    	Long respuesta = 0L;
    	
    	try {
			formaDelegate.guardarForma(forma);
		} catch (NSJPNegocioException e) {
			respuesta = 1L;
			logger.error(e);
		}
		
		escribirRespuesta(response,converter.toXML(respuesta));
    	
    	
    	return null;
    	
    }
    
    /**
     * Consulta los campos forma disponibles en el sistema 
     * 
     * @param mapping Configuraci贸n del mapping
     * @param form Form generado
     * @param request Objeto request
     * @param response Objeto response
     * @return null
     */
    public ActionForward consultarCamposForma(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    
    	
    	try {
			List<CamposFormaDTO> resultado = formaDelegate.consultarCamposForma();
			converter.alias("camposFormaDTO", CamposFormaDTO.class);
			escribirRespuesta(response,converter.toXML(resultado));
		} catch (NSJPNegocioException e) {
			logger.error(e);
		}
    	
    	
    	return null;
    }

    
    
    
    /**
     * Realiza la consulta de todas las plantillas por el flitro de tipo de documento
     * 
     * @param mapping Configuraci贸n del mapping
     * @param form Form generado
     * @param request Objeto request
     * @param response Objeto response
     * @return null
     */
    public ActionForward ConsultaExpedientesDocumento(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    	
    	try {
    			//logger.info("_____________________________________________________________");
    			//logger.info("EJECUTANDO CONSULTA Documentos ACTION");
    			//logger.info("_____________________________________________________________");
    			
	    		//Se obtiene el id del tipo de documento
				String numeroExpedienteId = request.getParameter("numeroExpedienteId");
			//	logger.info("EXPEDIENTE ID::::::::::"+numeroExpedienteId);
				
				ExpedienteDTO expedienteDTO = new ExpedienteDTO();
				expedienteDTO.setNumeroExpedienteId(NumberUtils.toLong(numeroExpedienteId));
				List<DocumentoDTO> documentoDTOs = documentoDelegate.consultarDocumentosExpediente(expedienteDTO);
				
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();
				
				writer.print("<rows>");
				
				final PaginacionDTO pag = PaginacionThreadHolder.get();

				//logger.debug("pag :: " + pag);
				if (pag != null && pag.getTotalRegistros() != null
						&& !pag.getTotalRegistros().equals(0L)) {
					writer.print("<page>" + pag.getPage() + "</page>");
					writer.print("<total>" + (pag.getTotalPaginas() > 0 ? pag.getTotalPaginas() : "1") + "</total>");
					writer.print("<records>" + pag.getTotalRegistros()
							+ "</records>");
				} else {
					writer.print("<page>0</page>");
					writer.print("<total>0</total>");
					writer.print("<records>0</records>");
				}
				
				for (DocumentoDTO documentoDTO : documentoDTOs) {
					writer.print("<row id='" + documentoDTO.getDocumentoId()+ "'>");
						//Nombre del documento
						writer.print("<cell>" +  documentoDTO.getNombreDocumento()+ "</cell>");
						
						//Si tiene archivo Digital
						writer.print("<cell>" +  documentoDTO.getArchivoDigital() != null ? "true" : "false" + "</cell>");
						
					writer.print("</row>");
				}			
				writer.print("</rows>");
				writer.flush();
				writer.close();
		    }catch (Exception e) {
				logger.error(e);
			}
		    
        return null;
    	
    }
        		
    public ActionForward consultarArchivoDigitalIframe(ActionMapping mapping,
    		ActionForm form, HttpServletRequest request,
    		HttpServletResponse response)  {    	
    	try {

    		
    		Long documentoId = request.getParameter("documentoId")!=null?NumberUtils.toLong(request.getParameter("documentoId")):null;
    		Long archivoDigitalId = request.getParameter("archivoDigitalId")!=null?NumberUtils.toLong(request.getParameter("archivoDigitalId")):null;
    		String tamanioPapelIdString = request.getParameter("seleccionTamanioPapel");
        	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
        	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
        	logger.info("EJECUTANDO CONSULTAR ARCHIVO DIGITAL................documentoId:"+documentoId+" archivoDigitalId:"+archivoDigitalId);
        	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
        	
			ArchivoDigitalDTO archivoDigitalDTO = documentoDelegate
					.consultarArchivoDigitalCompletoPorArchivoODocumento(
							documentoId, archivoDigitalId);

			if (archivoDigitalDTO != null
					&& archivoDigitalDTO.getContenido() != null) {
				escribirResponsePDF(archivoDigitalDTO.getContenido(), request,
						response);
			} else {
				if (documentoId != null) {
					DocumentoDTO documento = documentoDelegate
							.consultarDocumentoXId(documentoId);
					if (documento != null && documento.getEsGuardadoParcial()
							&& documento.getTextoParcial() != null) {
						byte[] contenido = generarReportePDFDeHTML(
								documento.getTextoParcial(), confPapel)
								.toByteArray();
						escribirResponsePDF(contenido, request, response);
					}
				}
			}
    	}catch (Exception e) {
    		logger.error(e);
    	}
    	return null;
    }
    
    private void escribirResponsePDF(byte[] contenido, 
    		HttpServletRequest request, HttpServletResponse response) throws IOException{
    	ByteArrayOutputStream archivoPDF = null;
    	if (contenido != null){    		
    		archivoPDF = new ByteArrayOutputStream();
    		archivoPDF.write(contenido);
    		if(request.getParameter("inFrame") == null){
    			//Escribirlo como attachment
    			escribirReporte(response, archivoPDF, "documentoDigital");
    		}else{
    			//escribirlo en linea
    			response.setContentType(CONTENT_TYPE_PDF);
    			response.setHeader(ENCABEZADO_CACHE_CONTROL, 
    					ENCABEZADO_NOCACHE);
    			response.setHeader(ENCABEZADO_PRAGMA,
    					ENCABEZADO_NOCACHE);
    			response.setContentLength(archivoPDF.size());
    			ServletOutputStream sos = response.getOutputStream();
    			archivoPDF.writeTo(sos);
    			sos.flush();
    			sos.close();
    		}
    	}
    }
    
    /**
     * M&eacute;todo que lleva a cabo la consulta de los documentos que
     * se encuentran asociados al expediente y que son requeridos para
     * llevar a cabo la integraci&oacute;n del procedimiento de 
     * ejecuci&oacute;n
     * @param mapping - Mapeo de struts sobre el cual se manejan los redirects.
     * @param form - Forma de struts con los datos correspondientes a la petici&oacute;n.
     * @param request - Objeto en donde viaja la petici&oacute;n web.
     * @param response - Objeto en donde viaja la respuesta generada por el servicio.
     * @return null - Se regresa el arreglo de JSON dentro del response.
     */
    @SuppressWarnings("unchecked")
    public ActionForward consultarDocumentosIntegracionExpediente(ActionMapping mapping,
    		ActionForm form, HttpServletRequest request,
    		HttpServletResponse response)  {
        try {
            Long expedienteId = NumberUtils.toLong(request.getParameter(PARAM_EXPEDIENTE_ID), 0L);
            if (expedienteId != null
                    && expedienteId > 0L) {

                ExpedienteDTO expedienteDTO = new ExpedienteDTO();
                expedienteDTO.setExpedienteId(expedienteId);
                List<DocumentoIntegracionDTO> documentosIntegracion = documentoDelegate.consultarDocumentosIntegracion("tipoDocumento.valorId", true);
                Map<Long, DocumentoDTO> documentosExpediente = documentoDelegate.consultarDocumentosIntegracionXExpediente(expedienteDTO);
                if (documentosIntegracion != null
                        && !documentosIntegracion.isEmpty()) {
                    JSONArray jsonArrayDoctos = new JSONArray();
                    for (DocumentoIntegracionDTO di : documentosIntegracion) {
                        boolean asociado = documentosExpediente.containsKey(di.getTipoDocumento().getIdCampo());
                        jsonArrayDoctos.add(convertirJson(di, asociado));
                    }

                    response.setContentType(ConstantesGenerales.CONTENT_TYPE_JAVASCRIPT + "; " + ConstantesGenerales.CHARSET_ISO_8859);
                    response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
                    PrintWriter writer = response.getWriter();

                    StringWriter out = new StringWriter();
                    jsonArrayDoctos.writeJSONString(out);

                    writer.print(out.toString());
                    writer.flush();
                    writer.close();
                }
            }
        } catch (NSJPNegocioException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    	return null;
    }
    
    /**
     * M&eacute;todo que lleva a cabo la consulta de las solicitudes de mandamientos
     * judiciales y regresa la informaci&oacute;n para su interpretaci&oacute;n en un 
     * grid.
     * 
     * @param mapping - Mapeos de struts para enlazar las peticiones.
     * @param form - Forma de struts en donde se encuentran los datos que provienen
     * 				 desde la vista.
     * @param request - Objeto de java que representa la petici&oacute;n de HTML.
     * @param response - Objeto de java que representa la respuesta en HTML.
     * @return null - Ya que el xml se escribe directamente en el response.
     */
    public ActionForward consultarSolicitudesMandamiento(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {

    	//Se obtiene el id del tipo de documento
    	List<ValorDTO> estatusSolicitudes = obtenerListaValores(request.getParameter(PARAM_IDS_ESTATUS_SOLICITUDES));
    	List<ValorDTO> tiposSolicitudes = obtenerListaValores(request.getParameter(PARAM_IDS_TIPOS_SOLICITUDES));
    	List<ValorDTO> tiposMandamientos = obtenerListaValores(request.getParameter(PARAM_IDS_TIPOS_MANDAMIENTOS));

    	UsuarioDTO usuarioFirmado = getUsuarioFirmado(request);
    	
    	List<SolicitudMandamientoDTO> solicitudesMandamiento;
		try {
			solicitudesMandamiento = documentoDelegate.consultarSolicitudesMandamientoPorDestinatario(
    				usuarioFirmado.getFuncionario(), estatusSolicitudes, 
    				tiposMandamientos, tiposSolicitudes);
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML+"; "+ConstantesGenerales.CHARSET_ISO_8859);
			response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL, ConstantesGenerales.ENCABEZADO_NOCACHE);
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");
		
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
			
			if (solicitudesMandamiento != null
					&& !solicitudesMandamiento.isEmpty()){
				List<List<String>> tablaValores = new ArrayList<List<String>>();
				for (SolicitudMandamientoDTO solMan : solicitudesMandamiento) {
					tablaValores.add(crearFilaParaGrid(solMan));
				}			
				String datos = crearDatosGrid(tablaValores);
				writer.print(datos);
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
    	return null;
    }
    
    /**
     * M&eacute;todo utilitario que lleva a cabo la creaci髇 de una lista de 
     * cadenas para que se muestren dentro de un grid.
     * 
     * @param solMan - SolicitudMandamientoDTO del cual se van a obtener los
     * 				   atributos para desplegarlos en el grid.
     * @return List<String> - Lista de cadenas con la informaci&oacute;n a 
     * 						  presentar dentro del grid.
     */
    private List<String> crearFilaParaGrid(SolicitudMandamientoDTO solMan){
		List<String> fila = new ArrayList<String>();
		MandamientoDTO man = solMan.getMandamiento();

		fila.add(solMan.getDocumentoId().toString());
		fila.add(solMan.getExpedienteDTO() != null 
				&& solMan.getExpedienteDTO().getCasoDTO() != null ? 
						solMan.getExpedienteDTO().getCasoDTO().getNumeroGeneralCaso() : 
							ConstantesGenerales.VALOR_OMISION_GRID);
		
		fila.add(solMan.getExpedienteDTO() != null 
				&& solMan.getExpedienteDTO().getNumeroExpediente() != null ?
						solMan.getExpedienteDTO().getNumeroExpediente() :
							ConstantesGenerales.VALOR_OMISION_GRID);		
		if (man != null){
			//fila.add(man.getInvolucrado().getNombreCompleto());
			fila.add(man.getTipoMandamiento().getValor());
			fila.add(man.getFechaCreacion() != null ? 
					DateUtils.formatear(man.getFechaCreacion()) : 
						ConstantesGenerales.VALOR_OMISION_GRID);
			fila.add(man.getEstatus().getValor() != null ? 
					man.getEstatus().getValor() : 
						ConstantesGenerales.VALOR_OMISION_GRID);
			fila.add(man.getDocumentoId().toString());
		}
		return fila;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la creaci&oacute;n del xml
	 * con los valores que van a ser presentados dentro de un grid.
	 * @param matrizValores - List<List<String>> con los registros
	 * 						  y la informaci&oacute;n que se va a 
	 * 						  mostrar dentro del grid.
	 * @return String - Cadena con los datos del xml que se va a 
	 * 					interpretar dentro del grid.
	 */
	private String crearDatosGrid(List<List<String>> matrizValores){
		StringBuffer buffer = new StringBuffer();
		if (matrizValores != null && !matrizValores.isEmpty()){
			for (List<String> fila : matrizValores){
				if (fila != null && !fila.isEmpty()){
					for (int i=0; i<fila.size();i++){
						if (i==0){
							buffer.append("<row id='").append(fila.get(i)).append("'>");
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
	 * M&eacute;todo que lleva a cabo la conversi&oacute;n de un objeto de tipo
	 * <code>DocumentoIntegracionDTO</code> a un objeto de JSON, para que este
	 * sea regresado a la vista y pueda ser desplegado como informaci&oacute;n 
	 * al usuario.
	 * @param documentoIntegracionDTO - El DTO a transformar en un JSON.
	 * @param asociado - bandera que indica si el DTO se encuentra asociado con
	 * 					 el expediente.
	 * @return json - Objeto de json con la informaci&oacute;n asociada del 
	 * 				  DTO Transformado.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject convertirJson (DocumentoIntegracionDTO documentoIntegracionDTO, boolean asociado){
		JSONObject json = null;
		if (documentoIntegracionDTO != null){
			json = new JSONObject();
			json.put(KEY_JSON_DOCTO_INTEG_ID, documentoIntegracionDTO.getDocumentoIntegracionId());
			json.put(KEY_JSON_NOMBRE_TIPO_DOCUMENTO, documentoIntegracionDTO.getTipoDocumento().getValor());
			json.put(KEY_JSON_OBLIGATORIO, documentoIntegracionDTO.getObligatorio());
			json.put(KEY_JSON_ASOCIADO, asociado);
		}
		return json;
	}
	
	/**
	 * M&eacute;todo utilitario que crea una lista de ValorDTO a partir de una 
	 * cadena con Identificadores de Valor que se encuentran separados por comas.
	 * 
	 * @param cadenaIdentificadores - Cadena con identificadores de la tabla Valor
	 * 								  separados por comas.
	 * 
	 * @return valores - List<ValorDTO> con un objeto de tipo ValorDTO asociado 
	 * 					 con cada uno de los identificadores ingresados como
	 * 					 par&aacute;metros.
	 */
	private List<ValorDTO> obtenerListaValores(String cadenaIdentificadores){
		List<ValorDTO> valores = null;
		if (cadenaIdentificadores != null){
			String[] arrIds = cadenaIdentificadores.split(",");
			if (arrIds != null
					&& arrIds.length > 0){
				valores = new ArrayList<ValorDTO>();
				for (String id : arrIds){
					valores.add(new ValorDTO(Long.parseLong(id)));
				}
			}
		}
		return valores;
	}
}

package mx.gob.segob.nsjp.amparo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.configuracion.ConfiguracionDelegate;
import mx.gob.segob.nsjp.delegate.documento.AmparoDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.delegate.relacion.RelacionDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.documento.form.AdjuntarDocumentoAsociadoAExpedienteForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rgama
 */
public class AmparoAction extends GenericAction {

    private static final Logger logger =
            Logger.getLogger(GenericAction.class);

    @Autowired
    private InvolucradoDelegate involucradoDelegate;
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	private RelacionDelegate relacionDelegate;
	@Autowired
	private AmparoDelegate amparoDelegate;
	@Autowired
	private ConfiguracionDelegate configuracionDelegate;
	
	
	
	

    
    public ActionForward consultarInvolucradosPorExpediente(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
        	// Se recuperan parametros            
            Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"),0);
            logger.info("idExpediente:::"+ idExpediente);
            
            // Consultas de negocio
            List<InvolucradoDTO> involucradosDTO = null;
            if(idExpediente > 0)
            	involucradosDTO = involucradoDelegate.consultarInvolucradosPorExpediente(idExpediente);
                              
            //Inicia la iteracion sobre Almacenes
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
		
			writer.print("<rows>");
			//Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);
			
			if(involucradosDTO != null)
				for (InvolucradoDTO involucrado : involucradosDTO) {
						writer.print("<row id='"+involucrado.getElementoId()+"'>");
						
						writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+
								(involucrado.getNombreCompleto() != null ? involucrado.getNombreCompleto():"-")
								+" </div>]]></cell>");
						
						writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"+
								(involucrado.getCalidadDTO()!= null && involucrado.getCalidadDTO().getValorIdCalidad() != null &&
										involucrado.getCalidadDTO().getValorIdCalidad().getValor() != null?
												involucrado.getCalidadDTO().getValorIdCalidad().getValor(): "-")
								+" </div>]]></cell>");
						
						writer.print("</row>");	
					
				}
		writer.print("</rows>");
		writer.flush();
		writer.close();
       
        } catch (NSJPNegocioException ex) {
             java.util.logging.Logger.getLogger(AmparoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

	/**
	 * Metodo utilizado para adjuntar un documento y asociarlo a un expediente 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward adjuntarDocumentoAsociadoAExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			logger.info("EJECUTANDO ADJUNTAR DOCUMENTO Y ASOCIARLO A UN EXPEDIENTE");
			
			//Se reiciben los parametros del amparo
			String folio = request.getParameter("folio");
			String descripcion = request.getParameter("descripcion");
			String idsSeleccionados = request.getParameter("idsSeleccionados");
			String numeroGeneralCaso = request.getParameter("numeroGeneralCaso");
			Long estatusAmparo =NumberUtils.toLong(request.getParameter("estatusAmparo"));
			
			logger.info("folio:::" + folio);
			logger.info("descripcion:::" + descripcion);
			logger.info("idsSeleccionados" + idsSeleccionados);
			logger.info("numeroGeneralCaso" + numeroGeneralCaso);
			
			//creamos la lista de los IDs seleccionados
			List<Long> idsInvolucrados=new ArrayList<Long>();
			String[] idInvolucrados =null;
			
			if(idsSeleccionados != null && idsSeleccionados.length()>0){
				idInvolucrados = idsSeleccionados.split(",");
				for (String idInvolucrado: idInvolucrados)
					idsInvolucrados.add(Long.parseLong(idInvolucrado));//agregamos el ID correspondiente			
			}				
			logger.info("	::::::::::::::::: El total de Id�s recibidos es: ::::::::::::::::::::: " + idsInvolucrados.size());		
				
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			
			AdjuntarDocumentoAsociadoAExpedienteForm adjuntarDocumentoAsociadoAExpedienteForm = (AdjuntarDocumentoAsociadoAExpedienteForm)form;
					
			expedienteDTO.setExpedienteId(adjuntarDocumentoAsociadoAExpedienteForm.getExpedienteId());
			logger.info("expediente Id de la forma" + adjuntarDocumentoAsociadoAExpedienteForm.getExpedienteId());
			
			FormFile archivo = adjuntarDocumentoAsociadoAExpedienteForm.getArchivoAdjunto();
			String fileName    = archivo.getFileName();
			byte[] fileData    = archivo.getFileData();			        
			
			adjunto.setContenido(fileData);
			
			if(fileName != null){
				String[] extension = fileName.split("\\.");
				adjunto.setTipoArchivo("."+extension[extension.length-1]);
				adjunto.setNombreArchivo(extension[0]);
			}
			
			adjunto.setUsuario(super.getUsuarioFirmado(request));
			
			funcionarioDTO = getUsuarioFirmado(request).getFuncionario();
			
			Actividades actividad = Actividades.ADJUNTAR_ACUERDO_RESTAURATIVO;
			
			if(adjuntarDocumentoAsociadoAExpedienteForm.getTipo()!= 0){
				actividad = Actividades.ADJUNTAR_ARCHIVO_DIGITAL;
				
			}
			DocumentoDTO loDocuemntoDTO = new DocumentoDTO();
			loDocuemntoDTO.setArchivoDigital(adjunto);
			loDocuemntoDTO.setFolioDocumento(folio);
			loDocuemntoDTO.setEsGuardadoParcial(false);
			//300 la longitud del campo en BD
			if(descripcion.length() > 300)
				loDocuemntoDTO.setDescripcion(descripcion.substring(0,299));
			else
			loDocuemntoDTO.setDescripcion(descripcion);
			
			Long idDocumento = documentoDelegate.adjuntarDocumentoAExpediente(expedienteDTO, loDocuemntoDTO, funcionarioDTO, actividad, TipoDocumento.AMPARO);
			
			//Si se esta en PJ entonces se envia copia del documento a PG
            int institucionActual = 0;
			ConfInstitucionDTO loInstitucion = configuracionDelegate.consultarInstitucionActual();
            if(loInstitucion != null && loInstitucion.getConfInstitucionId() != null)
				institucionActual = loInstitucion.getConfInstitucionId().intValue();
            if(institucionActual == Instituciones.PJ.getValorId()){
        		/*Obligatorios de Documento*/
    			loDocuemntoDTO.setNombreDocumento(adjunto.getNombreArchivo());
    			loDocuemntoDTO.setFechaCreacion(new Date());
    			//Se asigna el tipo de documento
    			loDocuemntoDTO.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.AMPARO.getValorId()));
    			
    			List<DocumentoDTO> lstDocumentoDTO = new ArrayList<DocumentoDTO>();
    			lstDocumentoDTO.add(loDocuemntoDTO);
    			documentoDelegate.enviarDocumentoAInstitucion(lstDocumentoDTO, numeroGeneralCaso, Instituciones.PGJ);
            }
            
			//Se registran los involucrados asociados al amparo
			for (Long idInv : idsInvolucrados) {
				relacionDelegate.registrarRelacionDocumentoElemento(new Long(Relaciones.AMPARADO.ordinal()),idInv,idDocumento);
			}
			if(estatusAmparo.compareTo(0L) != 0){
				ValorDTO estatus = new ValorDTO(estatusAmparo);
				DocumentoValorDTO documentoValorDTO = new DocumentoValorDTO(estatus , idDocumento);
				amparoDelegate.insertarEstatusAmparo(documentoValorDTO );
			}
			logger.info("regreso"+ idDocumento);
			return mapping.findForward("success");
					
		} catch (Exception e) {		
			logger.info(e.getCause(),e);
			return mapping.findForward("fail");
		}
		
	}
	
	/**
	 * M&eacute;todo para realizar la consulta de involucrados asociados a 
	 * un amparo, es necesario el idAmparo
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
    public ActionForward consultarInvolucradosAsociadosAAmparo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
        	// Se recuperan parametros            
            Long idAmparo = NumberUtils.toLong(request.getParameter("idAmparo"),0L);
            logger.info("idAmparo:::"+ idAmparo);
            
            // Consultas de negocio
            List<InvolucradoDTO> involucradosDTO = null;
            
			if (idAmparo > 0) {
				involucradosDTO = amparoDelegate
						.consultarInvolucradosXAmparo(idAmparo);
			} else {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}
                  
            //Inicia la iteracion sobre Almacenes
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
		
			writer.print("<rows>");
			//Configuracion del paginador
			PaginadorUtil.obtenerPaginacionManual(involucradosDTO);
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);		
			if (involucradosDTO != null && !involucradosDTO.isEmpty()) {

				for (InvolucradoDTO involucrado : involucradosDTO) {
					writer.print("<row id='" + involucrado.getElementoId()
							+ "'>");

					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
							+ ((involucrado.getNombreCompleto() != null) ? involucrado
									.getNombreCompleto() : "-")
							+ " </div>]]></cell>");

					writer.print("<cell><![CDATA[<div style='background-color: #f2f2f2; color:#393939;'>"
							+ ((involucrado.getCalidadDTO() != null
									&& involucrado.getCalidadDTO()
											.getValorIdCalidad() != null && involucrado
									.getCalidadDTO().getValorIdCalidad()
									.getValor() != null) ? involucrado
									.getCalidadDTO().getValorIdCalidad()
									.getValor() : "-") + " </div>]]></cell>");

					writer.print("</row>");

				}
			}
		writer.print("</rows>");
		writer.flush();
		writer.close();
       
        } catch (NSJPNegocioException ex) {
             java.util.logging.Logger.getLogger(AmparoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ActionForward consultarAmparosPorExpediente(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
        	// Se recuperan parametros            
            Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"),0);
            logger.info("idNumeroExpediente:::"+ idNumeroExpediente);
            
            // Consultas de negocio
            List<DocumentoDTO> documentosDTO = null;
          

            if(idNumeroExpediente > 0){
                ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
                loExpedienteDTO.setNumeroExpedienteId(idNumeroExpediente);
                documentosDTO = documentoDelegate.consultarDocumentosXTipoDocumento(loExpedienteDTO, TipoDocumento.AMPARO.getValorId());
            }
            List<Long> longs = new ArrayList<Long>();
            for (DocumentoDTO documentoDto : documentosDTO) {
            	longs.add(documentoDto.getDocumentoId());
            }
            Map<Long, DocumentoValorDTO> mapa = amparoDelegate.consultarEstatusPorIdsDocumentos(longs);
                              
            //Inicia la iteracion sobre Almacenes
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
		
			writer.print("<rows>");
			final PaginacionDTO pag = PaginacionThreadHolder.get();
			if (pag != null && pag.getTotalRegistros() != null && !pag.getTotalRegistros().equals(0L)) {
		    	writer.print("<page>" + pag.getPage() + "</page>");
		        writer.print("<total>" + pag.getTotalPaginas() + "</total>");
		        writer.print("<records>" + pag.getTotalRegistros() + "</records>");
		    } else {
		    	writer.print("<page>0</page>");
		        writer.print("<total>0</total>");
		        writer.print("<records>0</records>");
		    }
			if(documentosDTO != null)
				for (DocumentoDTO documento : documentosDTO) {
						writer.print("<row id='"+documento.getDocumentoId()+"'>");
						
						writer.print("<cell>"+
								(documento.getFolioDocumento() != null ? documento.getFolioDocumento():"-")
								+" </cell>");
						
						writer.print("<cell>"+
								(documento.getDescripcion()!= null ?
										documento.getDescripcion(): "-")
								+" </cell>");		
						writer.print("<cell>"+ "<a href='#' title='Ver Documento' onclick='consultaPDF("+ documento.getDocumentoId() + ")'>Ver documento</a>" +  "</cell>");
						DocumentoValorDTO valorDTO = mapa.get(documento.getDocumentoId());
						writer.print("<cell>"+
								(valorDTO!= null ?
										valorDTO.getEstatus().getValor(): "-")
								+" </cell>");
						writer.print("</row>");	
					
				}
		writer.print("</rows>");
		writer.flush();
		writer.close();
       
        } catch (NSJPNegocioException ex) {
             java.util.logging.Logger.getLogger(AmparoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
	public ActionForward consultarAmparoPorId(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
	
        Long idAmparo = NumberUtils.toLong(request.getParameter("idAmparo"),0);
        if(idAmparo > 0L){
        	try {
	        	DocumentoDTO documentoDTO = documentoDelegate.consultarDocumentoXId(idAmparo);
	        	List<InvolucradoDTO> involucradosDTO = amparoDelegate.consultarInvolucradosXAmparo(idAmparo);
	        	DocumentoValorDTO documentoValorDTO =  amparoDelegate.consultarEstatusPorIdDocumento(idAmparo);
        	
	        	JSONObject json = new JSONObject();
	        	if(documentoDTO != null && documentoDTO.getFolioDocumento() != null 
	        			&& !documentoDTO.getFolioDocumento().isEmpty()){	        		
	        		json.put("folio",documentoDTO.getFolioDocumento());
	        	}
	        	if(documentoValorDTO != null && documentoValorDTO.getEstatus() != null
	        			&& documentoValorDTO.getEstatus().getIdCampo() != null){	        		
	        		json.put("estatusAmparo",documentoValorDTO.getEstatus().getIdCampo());
	        	}
	        	if(documentoDTO != null && documentoDTO.getDescripcion() != null
	        			&& !documentoDTO.getDescripcion().isEmpty()){	        		
	        		json.put("descripcion",documentoDTO.getDescripcion());
	        	}
	        	if(involucradosDTO != null && !involucradosDTO.isEmpty()){
	        		JSONArray jsonArrayDoctos = new JSONArray();
	        		for (InvolucradoDTO involucradoDTO : involucradosDTO) {
	        			jsonArrayDoctos.add(involucradoDTO.getElementoId());
					}	        		
	        		json.put("involucradosPorExpediente",jsonArrayDoctos);
	        	}
	        	
	        	response.setContentType(ConstantesGenerales.CONTENT_TYPE_JAVASCRIPT+ "; "+ConstantesGenerales.CHARSET_ISO_8859);
	        	response.setHeader(ConstantesGenerales.ENCABEZADO_CACHE_CONTROL,ConstantesGenerales.ENCABEZADO_NOCACHE);
	        	PrintWriter writer = response.getWriter();
	        	StringWriter out = new StringWriter();
	        	json.writeJSONString(out);
	        	writer.print(out.toString());
	        	writer.flush();
	        	writer.close();
        	} catch (NSJPNegocioException e) {
        		e.printStackTrace();
        	}
        }
        return null;
    }


	public ActionForward modificarAmparo(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
	
        Long idAmparo = NumberUtils.toLong(request.getParameter("idAmparo"),0);
        String folio = request.getParameter("folio");
		String descripcion = request.getParameter("descripcion");
        Long estatusAmparo =NumberUtils.toLong(request.getParameter("estatusAmparo"));
        String idsSeleccionados = request.getParameter("idsSeleccionados");
        
        if(idAmparo > 0L){
        	try {
	        	DocumentoDTO amparo = new DocumentoDTO();
	        	amparo.setDocumentoId(idAmparo);
	        	amparo.setFolioDocumento(folio);
	        	amparo.setDescripcion(descripcion);
	        	
	        	DocumentoValorDTO estatus = null;
				if(estatusAmparo.compareTo(0L) != 0){
					ValorDTO valor = new ValorDTO(estatusAmparo);
					estatus = new DocumentoValorDTO(valor , idAmparo);
				}
				List<Long> idsInvolucrados=new ArrayList<Long>();
				String[] idInvolucrados =null;
				
				if(idsSeleccionados != null && !idsSeleccionados.isEmpty()){
					idInvolucrados = idsSeleccionados.split(",");
					for (String idInvolucrado: idInvolucrados)
						idsInvolucrados.add(Long.parseLong(idInvolucrado));			
				}
				amparoDelegate.actualizaAmparo(amparo, estatus, idsInvolucrados);
        	} catch (NSJPNegocioException e) {
        		e.printStackTrace();
        	}
        }
        return null;
    }

}

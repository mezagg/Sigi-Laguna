/**
 * Nombre del Programa 		: AdjuntarFotoDeElementoAction.java
 * Autor                     : rgama
 * Compania                  : Ultrasist
 * Proyecto                  : NSJP                    Fecha: 24 08 2011
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase Action que permite adjuntar una fotografia a un elemento
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion        : N/A
 * Dias de ejecucion         : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                     : N/A
 * Compania              	: N/A
 * Proyecto                 	: N/A                                 Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.elemento.action;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.elemento.TipoElemento;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.elemento.ElementoDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.elemento.form.ElementoForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase Action para ingresar objetos.
 * 
 * @version 1.0
 * @author rgama
 * 
 */
public class AdjuntarFotoDeElementoAction extends GenericAction {
	
	@Autowired
	public ElementoDelegate elementoDelegate;
	
	/* Log de clase */
	private static final Logger log = Logger
			.getLogger(AdjuntarFotoDeElementoAction.class);
	
	@Autowired
	private DocumentoDelegate documentoDelegate;

	/**
	 * Metodo utilizado para guardar la imagen asociada a un Elemento
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward ingresarImagenDelElemento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long elementoID = null;
		String numeroExpediente= null;
		Long tipoElementoId = null;
		String calidadInv = null;
		Long detenido = 0L;
		
		try {
			
			log.info("Ejecutando action AdjuntarFotoDelElemeto - ingresarImagenDelElemento  con Id de objeto:: "+request.getParameter("elementoID"));
			ElementoForm forma = (ElementoForm) form;
			elementoID=NumberUtils.toLong(request.getParameter("elementoID"),0);
			tipoElementoId = NumberUtils.toLong(
					request.getParameter("tipoElementoId"), 0);
			String idIndividuo=request.getParameter("idIndividuo");
			calidadInv = request.getParameter("calidadInv");
			log.info("calidadInv::" + calidadInv);
			numeroExpediente = request.getParameter("numeroExpediente");
			log.info("IDINdividuo="+idIndividuo);		
			request.setAttribute("idIndividuoProp", idIndividuo);
			if(forma!=null && forma.getMuestraDetenidoModificar() != null){
				request.setAttribute("detenido", forma.getMuestraDetenidoModificar());
				detenido=forma.getMuestraDetenidoModificar();
				log.info("****** " + forma.getMuestraDetenidoModificar());
			}
			//Permite guardar la foto de un elemento
			if(forma !=null && forma.getArchivo() != null && forma.getArchivo().getFileSize()>0){				
				ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();

				FormFile archivo = forma.getArchivo();
		        String contentType = archivo.getContentType();
		        String fileName    = archivo.getFileName();
		        log.debug(" EL ADJUNTO GAMASOFT: El nombre del archivo es" + archivo.getFileName());
		        byte[] fileData    = archivo.getFileData();
				adjunto.setContenido(fileData);
				adjunto.setNombreArchivo(fileName);
				adjunto.setTipoArchivo(contentType);
				adjunto.setUsuario(super.getUsuarioFirmado(request));
				ElementoDTO elemento = new ElementoDTO();
				elemento.setElementoId(elementoID);				
				elementoDelegate.adjuntarArchivoAElemento(elemento, adjunto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		// Para los objetos no es necesario volver a consultarlo, se cierra la
		// venta. Utilizando en el ingresarMenuIntermedio y
		// asentarRegCadenaCustodia.jsp 
		if(tipoElementoId.equals(TipoElemento.OBJETO.getValorId())){
			return null;
		}
		else{
			if(calidadInv!=null && !calidadInv.trim().isEmpty() ){
				return new ActionForward(mapping.findForward("success").getPath()+"?idProbableResponsable=" + elementoID +"&calidadInv="+calidadInv+"&numeroExpediente=" + numeroExpediente+"&detenido="+detenido, false);
			}else{
				return new ActionForward(mapping.findForward("success").getPath()+"?idProbableResponsable=" + elementoID +"&calidadInv=PROBABLE_RESPONSABLE&numeroExpediente=" + numeroExpediente+"&detenido="+detenido, false);
			}
		}
		
	}
	
	
	/**
	 * Metodo utilizado para obtener la imagen de un elemento 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, debido a la comunicacion Ajax
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward obtenImagenDeElemento(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			String idObjeto = request.getParameter("elementoID");

			log.info("$$$$ ID del Elemento solicitado $$$ : " + idObjeto);
		
			response.setContentType("image/gif");
			ArchivoDigitalDTO archivoDigitalDTO = documentoDelegate
					.consultarArchivoDigitalXElementoId(Long
							.parseLong(idObjeto));
                        ServletOutputStream out = response.getOutputStream();
                                
			if (archivoDigitalDTO != null
					&& archivoDigitalDTO.getContenido() != null) {
				byte[] imag = archivoDigitalDTO.getContenido();
				if(imag.length>0)
                                    out.write(imag);
                                
			}else{
                             BufferedInputStream bis= null;
                                    URL resource= AdjuntarFotoDeElementoAction.class.getResource("/foto.png");
                                    URLConnection urlConnection= resource.openConnection();
                                    //int length=  urlConnection.getContentLength();
                                    //out.setContentLength(length);
                                    bis= new BufferedInputStream(urlConnection.getInputStream());
                                    byte[] imagen = new byte[1024*2];
                                    int read;
                                    while ((read= bis.read(imagen))>0){
                                        out.write(imagen, 0, read);
                                    }
                        }
                            
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	
	public ActionForward ingresarImagenAObjeto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Long elementoID = null;
		String extensionesPermitidas = null;
		String respueta = "0";
		
		try {
			
			log.info("Ejecutando action ingresarImagenAObjeto con Id de objeto:: "+request.getParameter("elementoID"));
			ElementoForm forma = (ElementoForm) form;
			elementoID=NumberUtils.toLong(request.getParameter("elementoID"),0);
			extensionesPermitidas = request.getParameter("extensionesPermitidas");
			
			
			//Permite guardar la foto de un elemento
			if(forma.getArchivo() != null && forma.getArchivo().getFileSize()>0){				
				ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();

				FormFile archivo = forma.getArchivo();
		        String contentType = archivo.getContentType();
		        String fileName    = archivo.getFileName();
		        log.debug("El nombre del archivo es" + archivo.getFileName());
		        byte[] fileData    = archivo.getFileData();
				adjunto.setContenido(fileData);
				adjunto.setNombreArchivo(fileName);
				adjunto.setTipoArchivo(contentType);
				adjunto.setUsuario(super.getUsuarioFirmado(request));
				ElementoDTO elemento = new ElementoDTO();
				elemento.setElementoId(elementoID);				
				elementoDelegate.adjuntarArchivoAElementoTablaCruce(elemento, adjunto);
				respueta = "1";
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return new ActionForward(mapping.findForward("success").getPath()+"?extensionesPermitidas=" + extensionesPermitidas +"&elementoID=" + elementoID + "&respueta=" + respueta, false);
		
	}
	
}

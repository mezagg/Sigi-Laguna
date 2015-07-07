package mx.gob.segob.nsjp.web.defensor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

public class SolicitarDiligenciasAction extends GenericAction{
	
	private static final Logger log  = Logger.getLogger(SolicitarDiligenciasAction.class);
	
	@Autowired
	private SolicitudDelegate solicitudDelegate;
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	public ActionForward consultarTipoDiligencias(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
    	try {
    		log.info("ejecutando Action Cargar Combo Diligencias"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.TIPO_DILIGENCIA);
    		
    		
    		
    	
			converter.alias("listaCatalogo", java.util.List.class);
			converter.alias("catDiligencia", CatalogoDTO.class);
			
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
		return null;
	}
	
	
	/**
	 * Metodo que se utiliza para el registro de una diligencia
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	
	public ActionForward registrarDiligencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		    	try {
		    		log.info("Ejecutando Action registrar diligencia");
		    	
		    		
		    		
		    		
					}
				catch (Exception e) {
					log.info(e);
				}
			
		return null;
			
	}
	
	

}

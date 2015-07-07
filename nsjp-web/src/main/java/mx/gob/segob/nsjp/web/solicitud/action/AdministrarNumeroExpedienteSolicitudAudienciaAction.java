/**
 * Nombre del Programa 	: AdministrarNumeroExpedienteSolicitudAudiencia.java                                    
 * Autor               	: AlejandroGA                                              
 * Compania            	: Ultrasist                                                
 * Proyecto            	: NSJP              			Fecha:16/05/2013 
 * Marca de cambio     	: N/A                                                     
 * Descripcion General  : Clase para lo relacionado con el numero de 
 * 							expediente en las solicitudes de audiencia
 * Programa Dependiente : N/A                                                      
 * Programa Subsecuente : N/A                                                      
 * Cond. de ejecucion   : N/A                                                  
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                               MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                 :N/A                                                           
 * Compania              :N/A                                                           
 * Proyecto              :N/A                                   Fecha: N/A       
 * Modificacion          :N/A                                                           
 *------------------------------------------------------------------------------      
 */
package mx.gob.segob.nsjp.web.solicitud.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class AdministrarNumeroExpedienteSolicitudAudienciaAction extends
		GenericAction {

	/** Log de clase */
	private static final Logger log = Logger
			.getLogger(AdministrarNumeroExpedienteSolicitudAudienciaAction.class);

	@Autowired
	private SolicitudDelegate solicitudDelegate;

	/**
	 * M&eacute;todo para validar si se puede editar el numeroExpediente en una
	 * solicitud de audiencia regresa la respuesta a vista para habilitar, la
	 * edici&oacute;n del n&uacute;mero de expediente, que por default se
	 * encuentra deshabilitada.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, respuesta XML
	 * @throws IOException
	 */
	public ActionForward editarNumeroExpedienteSolicitudAudiencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO ACTION ---- editarNumeroExpedienteSolicitudAudiencia");

			Long numeroExpedienteId = NumberUtils.toLong(
					request.getParameter("numeroExpedienteId"), 0L);

			if (numeroExpedienteId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
			Boolean sePuedeEditar = solicitudDelegate
					.editarNumeroExpedienteSolicitudAudiencia(expedienteDTO);

			converter.alias("sePuedeEditar", boolean.class);		
			String xml = converter.toXML(sePuedeEditar);
			escribirRespuesta(response, xml);

		} catch (NSJPNegocioException ex) {
			escribirError(response, ex);
			log.info(ex.getCause(), ex);

		}
		return null;
	}
	
	
	/**
	 * M&eacute;todo para actualizar el n&uacute;mero de expediente (String)
	 * obtiene de vista el numeroExpedienteId y el string con el nuevo
	 * n&uacute;mero de expediente
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null, respuesta XML
	 * @throws IOException
	 */
	public ActionForward actualizarNumeroExpedienteSolicitudAudiencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("EJECUTANDO ACTION ---- actualizarNumeroExpedienteSolicitudAudiencia");

			Long numeroExpedienteId = NumberUtils.toLong(
					request.getParameter("numeroExpedienteId"), 0L);
			
			String nuevoNumeroExpediente = request
					.getParameter("nuevoNumeroExpediente");

			if (numeroExpedienteId <= 0L || nuevoNumeroExpediente == null
					|| nuevoNumeroExpediente.isEmpty()
					|| nuevoNumeroExpediente.equals("undefined")) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			expedienteDTO.setNumeroExpedienteId(numeroExpedienteId);
			expedienteDTO.setNumeroExpediente(nuevoNumeroExpediente.trim().toUpperCase());
			
			Boolean seActualizo = solicitudDelegate
					.actualizarNumeroExpedienteSolicitudAudiencia(expedienteDTO);

			converter.alias("seActualizo", boolean.class);		
			String xml = converter.toXML(seActualizo);
			escribirRespuesta(response, xml);

		} catch (NSJPNegocioException ex) {
			escribirError(response, ex);
			log.info(ex.getCause(), ex);

		}
		return null;
	}
}

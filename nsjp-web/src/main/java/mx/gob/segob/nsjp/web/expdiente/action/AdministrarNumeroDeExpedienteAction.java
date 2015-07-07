/**
* Nombre del Programa 	: AdministrarNumeroDeExpedienteAction.java                                    
* Autor               	: rgama                                             
* Compania            	: Ultrasist                                                
* Proyecto            	: NSJP              			Fecha:27/05/2013 
* Marca de cambio     	: N/A                                                     
* Descripcion General   : Clase action para adminsitrar un numero de expediente
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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * Clase action para asignar un numero de expediente
 * @version 1.0
 * @author rgama 
 */
public class AdministrarNumeroDeExpedienteAction extends GenericAction{
	/*Log de clase*/
	private static final Logger log  = Logger.getLogger(AdministrarNumeroDeExpedienteAction.class);
	
	@Autowired
	ExpedienteDelegate expedienteDelegate;
	
	/**
	 * Metodo que permite al rol "Coordinador de Policia Ministerial" reasignar expediente.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward resignarNumerosDeExpedientes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		try {
			
			String idsNumerosExpedientes=request.getParameter("idsNumerosExpedientes");
			Long  idNuevoFuncionario = NumberUtils.toLong(request.getParameter("idNuevoFuncionario"),0L);
			Long  idFuncionarioActual = NumberUtils.toLong(request.getParameter("idFuncionarioActual"),0L);
			
			UsuarioDTO loUsuarioDTO = super.getUsuarioFirmado(request);
			List<Long> listNumerosDeExpediente=new ArrayList<Long>();
			String[] idsNumerosExpediente =null;

			
			if(idsNumerosExpedientes != null && idsNumerosExpedientes.length()>0){
				idsNumerosExpediente = idsNumerosExpedientes.split(",");
				for (String idNumExp: idsNumerosExpediente)
					listNumerosDeExpediente.add(Long.parseLong(idNumExp));			
			}
			
			expedienteDelegate.reasignarNumerosDeExpedientePM(listNumerosDeExpediente, idNuevoFuncionario, loUsuarioDTO,idFuncionarioActual);
								
			//escribimos la respuesta
			String xml="<respuesta>1</respuesta>";
			escribirRespuesta(response, xml);
			
		} catch (Exception e) {			
			log.error(e.getMessage(), e);
			String xml="<respuesta>0</respuesta>";
			escribirRespuesta(response, xml);
		}
		return null;
	}

	
}

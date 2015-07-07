/**
 * Nombre del Programa 	 : ConsultarFuncionarioExternoAction                                    
 * Autor               	 : AlejandroGA                                              
 * Compania            	 : Ultrasist                                                
 * Proyecto            	 : NSJP              			Fecha:05/03/2013 
 * Marca de cambio     	 : N/A                                                     
 * Descripcion General   : Clase para consultar los funcionarios externos
 * Programa Dependiente  : N/A                                                      
 * Programa Subsecuente  : N/A                                                      
 * Cond. de ejecucion    : N/A                                                  
 * Dias de ejecucion     : N/A                             Horario: N/A
 *                               MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                 :N/A                                                           
 * Compania              :N/A                                                           
 * Proyecto              :N/A                                   Fecha: N/A       
 * Modificacion          :N/A                                                           
 *------------------------------------------------------------------------------      
 */
package mx.gob.segob.nsjp.web.funcionacioExterno.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AlejandroGA
 * 
 */
public class EliminarFuncionarioExternoAction extends GenericAction {

	@Autowired
	AudienciaDelegate audienciaDelegate;

	private static final Logger log = Logger
			.getLogger(EliminarFuncionarioExternoAction.class);

	public ActionForward eliminarFuncionarioExternoAudiencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("***CONSULTAR FUNCIONARIO EXTERNO AUDIENCIA ACTION***");
			Long audienciaId = NumberUtils.toLong(
					request.getParameter("idEvento"), 0L);

			Long funcExtId = NumberUtils.toLong(
					request.getParameter("funcExtId"), 0L);

			log.info("ID DE LA AUDIENCIA -------- " + audienciaId);
			log.info("FUNCIONARIO EXTERNO ID ---- " + funcExtId);

			if (audienciaId == null || audienciaId <= 0L || funcExtId == null
					|| funcExtId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO = new FuncionarioExternoAudienciaIdDTO();
			funcionarioExternoAudienciaIdDTO.setAudienciaId(audienciaId);
			funcionarioExternoAudienciaIdDTO.setFuncionarioExternoId(funcExtId);

			audienciaDelegate
					.eliminarFuncionarioExternoAudiencia(funcionarioExternoAudienciaIdDTO);

			escribirRespuesta(response, "exito");

		} catch (NSJPNegocioException e) {
			log.error("Ocurrio un error relacionar funcionario audiencia:", e);
			escribirError(response, e);
			log.error(e.getCause(), e);
		}
		return null;
	}

}

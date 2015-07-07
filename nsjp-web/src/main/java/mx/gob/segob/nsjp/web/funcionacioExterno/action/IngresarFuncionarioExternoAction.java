/**
 * Nombre del Programa 	 : IngresarFuncionarioExternoAction                                    
 * Autor               	 : AlejandroGA                                              
 * Compania            	 : Ultrasist                                                
 * Proyecto            	 : NSJP              			Fecha:07/03/2013 
 * Marca de cambio     	 : N/A                                                     
 * Descripcion General   : Clase para ingresar los funcionarios externos
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
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase para ingresar los funcionarios externos
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class IngresarFuncionarioExternoAction extends GenericAction {

	private static final Logger log = Logger
			.getLogger(IngresarFuncionarioExternoAction.class);

	@Autowired
	private AudienciaDelegate audienciaDelegate;

	/**
	 * M&eacute;todo para guardar o actulizar un funcionario externo
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward guardarActualizarFuncionarioExterno(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("***GUARDAR ACTUALIZAR FUNCIONARIO EXTERNO AUDIENCIA ACTION***");

			Long idEvento = NumberUtils.toLong(
					request.getParameter("idEvento"), 0L);
			Long rolId = NumberUtils.toLong(request.getParameter("rolId"), 0L);
			String nombreFuncExterno = request
					.getParameter("nombreFuncExterno");
			String apPatFuncExterno = request.getParameter("apPatFuncExterno");
			String apMatFuncExterno = request.getParameter("apMatFuncExterno");
			Long claveFuncExterno = NumberUtils.toLong(
					request.getParameter("claveFuncExterno"), 0L);
			Long funcExtId = NumberUtils.toLong(
					request.getParameter("funcExtId"), 0L);

			log.info("Verificando parametros::::::::::::::::::::::::::::");
			log.info("idEvento::::::::::::::::::::::" + idEvento);
			log.info("nombreFuncExterno:::::::::::::" + nombreFuncExterno);
			log.info("apPatFuncExterno::::::::::::::" + apPatFuncExterno);
			log.info("apMatFuncExterno::::::::::::::" + apMatFuncExterno);
			log.info("claveFuncExterno::::::::::::::" + claveFuncExterno);
			log.info("funcExtId:::::::::::::::::::::" + funcExtId);

			if (idEvento <= 0L || rolId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO = null;
			FuncionarioExternoDTO funcionarioExternoDTOInput = null;
			AudienciaDTO audienciaDTO = new AudienciaDTO(idEvento);

			if (funcExtId <= 0L) {
				
				/**
				 * Funcionalidad pensada para cuando el funcionario externo, es nuevo
				 * y se desea registrar de forma local(encargadoSala,juezPJ en la inst. PJ)
				 */
				
				if (claveFuncExterno <= 0L || nombreFuncExterno == null
						|| nombreFuncExterno.isEmpty()
						|| apPatFuncExterno == null
						|| apPatFuncExterno.isEmpty()) {
					throw new NSJPNegocioException(
							CodigoError.PARAMETROS_INSUFICIENTES);
				}
				
				funcionarioExternoDTOInput = new FuncionarioExternoDTO();

				funcionarioExternoDTOInput.setNombre(nombreFuncExterno);
				funcionarioExternoDTOInput.setApellidoPaterno(apPatFuncExterno);
				funcionarioExternoDTOInput.setApellidoMaterno(apMatFuncExterno);
				funcionarioExternoDTOInput
						.setCveFuncionarioInstExt(claveFuncExterno);
				funcionarioExternoDTOInput.setRolDTO(new RolDTO(rolId));

				Roles rolInput = obtenEnumRol(rolId);
				Long confInstitucionId = 0L;

				switch (rolInput) {
				case DEFENSOR:
					confInstitucionId = Instituciones.DEF.getValorId();
					break;
				case AGENTEMP:
				case PERITOAMP:
				case POLICIAMINISTER:
					confInstitucionId = Instituciones.PGJ.getValorId();
					break;

				case PERITODEF:
					confInstitucionId = Instituciones.PGJ.getValorId();
					break;

				case SSPPOLICIA:
					confInstitucionId = Instituciones.SSP.getValorId();
					break;

				default:
					confInstitucionId = Instituciones.PJ.getValorId();
					break;
				}
				funcionarioExternoDTOInput
						.setConfInstitucionDTO(new ConfInstitucionDTO(
								confInstitucionId));
				//Invocamos al servicio con el parametro ingresar/actualizar en true
				funcionarioExternoAudienciaDTO = audienciaDelegate
						.ingresarFuncionarioExternoAudiencia(
								funcionarioExternoDTOInput, audienciaDTO, true);
			} else {

				/**
				 * Funcionalidad para cuando el funcionario externo, ya existe y se desea
				 * asociar con la audiencia
				 */
				funcionarioExternoDTOInput = new FuncionarioExternoDTO(
						funcExtId);
				
				//Invocamos al servicio con el parametro ingresar/actualizar en false
				funcionarioExternoAudienciaDTO = audienciaDelegate
						.ingresarFuncionarioExternoAudiencia(
								funcionarioExternoDTOInput, audienciaDTO, false);
			}

			if (funcionarioExternoAudienciaDTO != null) {
				
				escribirRespuesta(response,"exito");
			} else {
				escribirRespuesta(response,"falla");
			}
		} catch (NSJPNegocioException e) {
			log.error("Ocurrio un error relacionar funcionario audiencia:", e);
			escribirError(response, e);
			log.error(e.getCause(), e);
		}
		return null;
	}

}

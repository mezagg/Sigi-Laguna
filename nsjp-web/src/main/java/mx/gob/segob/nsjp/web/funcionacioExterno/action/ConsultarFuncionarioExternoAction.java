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
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
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
 * Clase para consultar los funcionarios externos
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class ConsultarFuncionarioExternoAction extends GenericAction {

	@Autowired
	private AudienciaDelegate audienciaDelegate;

	private static final Logger log = Logger
			.getLogger(ConsultarFuncionarioExternoAction.class);

	public ActionForward consultarFuncionarioExternoAudiencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("***CONSULTAR FUNCIONARIO EXTERNO AUDIENCIA ACTION***");
			Long idEvento = NumberUtils.toLong(
					request.getParameter("idEvento"), 0L);
			Long rolId = NumberUtils.toLong(request.getParameter("rolId"), 0L);
			log.info("ID DE LA AUDIENCIA---- " + idEvento);
			log.info("TIPO DE CONSULTA---- " + rolId);

			if (idEvento == null || idEvento <= 0L | rolId == null
					|| rolId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO = new FuncionarioExternoAudienciaDTO();
			funcionarioExternoAudienciaDTO.setAudienciaDTO(new AudienciaDTO(
					idEvento));
			FuncionarioExternoDTO funcionarioExternoDTOFiltro = new FuncionarioExternoDTO();
			funcionarioExternoDTOFiltro.setRolDTO(new RolDTO(rolId));
			funcionarioExternoAudienciaDTO
					.setFuncionarioExternoDTO(funcionarioExternoDTOFiltro);

			List<FuncionarioExternoDTO> funcionarioExternoDTOList = audienciaDelegate
					.consultarFuncionarioExternoAudienciaPorRol(funcionarioExternoAudienciaDTO);
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");

			// Configuracion del Paginador
			PaginadorUtil.obtenerPaginacionManual(funcionarioExternoDTOList);
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (funcionarioExternoDTOList != null
					&& !funcionarioExternoDTOList.isEmpty()) {

				for (FuncionarioExternoDTO FuncionarioExternoDTO : funcionarioExternoDTOList) {
					String funcExtId = FuncionarioExternoDTO
							.getFuncionarioExternoId().toString();
					String rolFuncionarioExt = obtenEnumRol(rolId).name();

					// Se forma la llave, para consultar el
					// funcionarioExternoAudiencia, no es necesario
					// confIntitucionId
					FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO = new FuncionarioExternoAudienciaIdDTO(
							idEvento,
							FuncionarioExternoDTO.getFuncionarioExternoId());

					FuncionarioExternoAudienciaDTO involucrado = audienciaDelegate
							.consultarFuncionarioExternoAudienciaPorId(funcionarioExternoAudienciaIdDTO);

					writer.print("<row id='"
							+ FuncionarioExternoDTO.getFuncionarioExternoId()
							+ "'>");
					writer.print("<cell>" + FuncionarioExternoDTO.getNombre()
							+ "</cell>");
					writer.print("<cell>"
							+ FuncionarioExternoDTO.getApellidoPaterno()
							+ "</cell>");
					writer.print("<cell>"
							+ FuncionarioExternoDTO.getApellidoMaterno()
							+ "</cell>");
					if (involucrado != null
							&& involucrado.getEsTitular() != null
							&& involucrado.getEsTitular() == true) {
						writer.print("<cell><![CDATA["
								+ "<input type='checkbox' name='titular_"
								+ rolFuncionarioExt
								+ "' onclick='guardarAsistenciaFuncionario("
								+ funcExtId + ",true);'"
								+ " id='chkTitFunExt_" + funcExtId
								+ "' checked='checked'>]]></cell>");
					} else {
						writer.print("<cell><![CDATA["
								+ "<input type='checkbox' name='titular_"
								+ rolFuncionarioExt
								+ "' onclick='guardarAsistenciaFuncionario("
								+ funcExtId + ",true);'"
								+ " id='chkTitFunExt_" + funcExtId
								+ "'>]]></cell>");
					}
					if (involucrado != null
							&& involucrado.getEsPresente() != null
							&& involucrado.getEsPresente() == true) {
						writer.print("<cell><![CDATA["
								+ "<input type='checkbox' onclick='guardarAsistenciaFuncionario("
								+ funcExtId + ",true);'" + " id='chkPreFunExt_"
								+ funcExtId + "' checked='checked'>]]></cell>");
					} else {
						writer.print("<cell><![CDATA["
								+ "<input type='checkbox' onclick='guardarAsistenciaFuncionario("
								+ funcExtId + ",true);'" + " id='chkPreFunExt_"
								+ funcExtId + "'>]]></cell>");
					}
					writer.print("</row>");
				}

			}

			writer.print("</rows>");
			writer.flush();
			writer.close();

		} catch (Exception e) {
		}
		return null;
	}

	public ActionForward consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("***CONSULTAR FUNCIONARIO EXTERNO AUDIENCIA ACTION***");
			Long idEvento = NumberUtils.toLong(
					request.getParameter("idEvento"), 0L);
			Long rolId = NumberUtils.toLong(request.getParameter("rolId"), 0L);
			log.info("ID DE LA AUDIENCIA---- " + idEvento);
			log.info("TIPO DE CONSULTA---- " + rolId);

			if (idEvento == null || idEvento <= 0L | rolId == null
					|| rolId <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();
			funcionarioExternoDTO.setRolDTO(new RolDTO(rolId));

			AudienciaDTO audienciaDTO = new AudienciaDTO();
			audienciaDTO.setId(idEvento);

			List<FuncionarioExternoDTO> funcionarioExternoDTOList = audienciaDelegate
					.consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(
							funcionarioExternoDTO, audienciaDTO);

			XStream converter=new XStream(); 			converter.alias("listaFuncionariExternoDTO", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("funcionarioExternoDTO",
					FuncionarioExternoDTO.class);
			String xml = converter.toXML(funcionarioExternoDTOList);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();

		} catch (Exception e) {
		}
		return null;
	}
}

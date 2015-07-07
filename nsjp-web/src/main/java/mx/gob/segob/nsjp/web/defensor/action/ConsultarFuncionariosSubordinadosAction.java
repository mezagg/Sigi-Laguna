/**
 * 
 */
package mx.gob.segob.nsjp.web.defensor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author EdgarAG
 *
 */
public class ConsultarFuncionariosSubordinadosAction extends GenericAction{
	private static final Logger log = Logger.getLogger(ConsultarFuncionariosSubordinadosAction.class);
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	
	public ActionForward consultarFuncionariosSubordinadosEtapa(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			log.info("ConsultarFuncionariosSubordinados");
			Long id = 1L;
			
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			funcionarioDTO.setClaveFuncionario(id);
			List<FuncionarioDTO> funcionarioDTOs = new ArrayList<FuncionarioDTO>();
			UsuarioDTO usuario = new UsuarioDTO();
			funcionarioDTOs = funcionarioDelegate.consultarFuncionariosSubordinados(usuario);

			
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			int lTotalRegistros = funcionarioDTOs.size();
			writer.print("<rows>");
			writer.print("<records>" + lTotalRegistros + "</records>");			
		
			for (FuncionarioDTO funcionarioDTO2 : funcionarioDTOs) {
			
			
				writer.print("<row id='"+ funcionarioDTO2.getClaveFuncionario() +  "'>");
				
				log.info("ConsultarFuncionariosSubordinados res"+funcionarioDTO2.getNombreCompleto());
				writer.print("<cell>");
				if(funcionarioDTO2.getNombreCompleto()!=null){
				writer.print(funcionarioDTO2.getNombreCompleto());
				}
				writer.print( "</cell>"); 
				writer.print("</row>");
						}
					
			// }
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
		    log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * M&eacute;todo para consultar los expedientes asignados a alg&uacute;n
	 * funcionario en especial, el cual se obtiene mediante par&aacute;metro,
	 * as&iacute; como la etapa del expediente y el par&aacute;metro expTodos
	 * mediante el cual, se consultan los expedientes solo por la clave del
	 * funcionario (Este m&eacute;todo, esta en formato para grid)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return XML 
	 * @throws IOException
	 */
	public ActionForward consultarExpedientesPorUsuarioYEtapa(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		log.debug("/**** OBTENER LOS EXPEDIENTES QUE SE ENCUENTRAN EN ETAPA DE INTEGRACION DE UN FUNCIONARIO ****/");
		try {

			log.info(":::::::::::VERIFICANDO PARAMETROS:::::::::::::::::::");

			log.info("Funcionario.............."
					+ request.getParameter("idFuncionario"));
			log.info("Etapa del Expediente....."
					+ request.getParameter("etapa"));
			log.info("Consultar todos.........."
					+ request.getParameter("expTodos"));

			String etapa = request.getParameter("etapa");
			Long cveFuncionario = NumberUtils.toLong(
					request.getParameter("idFuncionario"), 0L);
			Boolean expTodos = Boolean.parseBoolean(request
					.getParameter("expTodos"));

			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setFuncionario(new FuncionarioDTO(cveFuncionario));

			List<ExpedienteDTO> expedientesRes = null;
			//TODO @Deprecate ETAPAS
			if (expTodos.equals(false) && etapa != null
					&& !etapa.trim().isEmpty()) {

				if (etapa.equals(EtapasExpediente.EJECUCION.getValorId()
						.toString())) {
					expedientesRes = expedienteDelegate
							.consultarExpedientesPorUsuarioYEtapa(usuarioDTO,
									EtapasExpediente.EJECUCION.getValorId());
					log.info("Respeuesta de consulta consultarExpedientesPorUsuarioYEtapa :::"
							+ expedientesRes);
				}
				if (etapa.equals(EtapasExpediente.INTEGRACION.getValorId()
						.toString())) {
					expedientesRes = expedienteDelegate
							.consultarExpedientesPorUsuarioYEtapa(usuarioDTO,
									EtapasExpediente.INTEGRACION.getValorId());
					log.info("Respeuesta de consulta consultarExpedientesPorUsuarioYEtapa :::"
							+ expedientesRes);
				}
				if (etapa.equals(EtapasExpediente.CONCILIACION_Y_MEDIACION
						.getValorId().toString())) {
					expedientesRes = expedienteDelegate
							.consultarExpedientesPorUsuarioYEtapa(usuarioDTO,
									EtapasExpediente.CONCILIACION_Y_MEDIACION
											.getValorId());
					log.info("Respeuesta de consulta consultarExpedientesPorUsuarioYEtapa :::"
							+ expedientesRes);
				}
				if (etapa.equals(EtapasExpediente.TECNICA.getValorId()
						.toString())) {
					expedientesRes = expedienteDelegate
							.consultarExpedientesPorUsuarioYEtapa(usuarioDTO,
									EtapasExpediente.TECNICA.getValorId());
					log.info("Respeuesta de consulta consultarExpedientesPorUsuarioYEtapa :::"
							+ expedientesRes);
				}
			} else {
				expedientesRes = expedienteDelegate
						.consultarExpedientesPorFiltro(null, null,
								new FuncionarioDTO(cveFuncionario), null, null);
				log.info("Respuesta de consulta consultar todos los ExpedientesPorUsuario :::"
						+ expedientesRes);
			}

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.print("<rows>");

			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (expedientesRes != null && !expedientesRes.isEmpty()) {
				
				// Mapa usado para no duplicar los delitos del expediente en
				// vista
				Map<Long, String> listaDelitosMostrados = null;
				
				// List<DelitoDTO> delitoDTOs = new ArrayList<DelitoDTO>();
				for (ExpedienteDTO expedienteDTO : expedientesRes) {

					writer.print("<row id='"
							+ expedienteDTO.getNumeroExpedienteId() + "'>");

					// Numero de Caso
					writer.print("<cell>"
							+ ((expedienteDTO.getCasoDTO() != null && expedienteDTO
									.getCasoDTO().getNumeroGeneralCaso() != null) ? expedienteDTO
									.getCasoDTO().getNumeroGeneralCaso() : "")
							+ "</cell>");

					// Numero de Expediente
					writer.print("<cell>"
							+ (expedienteDTO.getNumeroExpediente() != null ? expedienteDTO
									.getNumeroExpediente() : "") + "</cell>");

					// Etapa del Expediente
					if ((expedienteDTO.getCatEtapaDTO() != null
							&& expedienteDTO.getCatEtapaDTO()
									.getCatEtapaValor() != null
							&& expedienteDTO.getCatEtapaDTO()
									.getCatEtapaValor().getValor() != null && !expedienteDTO
							.getCatEtapaDTO().getCatEtapaValor().getValor()
							.trim().isEmpty())) {
						writer.print("<cell>"
								+ (expedienteDTO.getCatEtapaDTO()
										.getCatEtapaValor().getValor())
								+ "</cell>");
					} //Si es una asesoria, solo se registra en la relacion con valor
					else if (expedienteDTO.getEtapa() != null
							&& expedienteDTO.getEtapa().getValor() != null
							&& !expedienteDTO.getEtapa().getValor().trim().isEmpty()) {

						writer.print("<cell>"
								+ expedienteDTO.getEtapa().getValor()
								+ "</cell>");
					}
					else{
						writer.print("<cell></cell>");
					}

					// Defendido
					// if
					// (expedienteDTO.getInvolucradoByCalidad(Calidades.DEFENDIDO)
					// != null
					// && expedienteDTO.getInvolucradoByCalidad(
					// Calidades.DEFENDIDO).size() > 0
					// && !expedienteDTO.getInvolucradoByCalidad(
					// Calidades.DEFENDIDO).isEmpty()) {
					// InvolucradoDTO involucradoDTO = expedienteDTO
					// .getInvolucradoByCalidad(Calidades.DEFENDIDO)
					// .get(0);
					// writer.print(involucradoDTO.getNombreCompleto());
					// } else {
					// writer.print("-");
					// }
					// writer.print("</cell>");

					
					//Combo box defendidos
					if (expedienteDTO
							.getInvolucradoByCalidad(Calidades.DEFENDIDO) != null
							&& !expedienteDTO.getInvolucradoByCalidad(
									Calidades.DEFENDIDO).isEmpty()) {
						writer.print("<cell><![CDATA["
								+ "<select id='cbxDefendidos' style='width: 300px;'>");
						for (InvolucradoDTO involucrado : expedienteDTO
								.getInvolucradoByCalidad(Calidades.DEFENDIDO)) {
							writer.print("<option>"
									+ involucrado.getNombreCompleto()
									+ "</option>");
						}
						writer.print("</select>" + "]]></cell>");
					} else {
						writer.print("<cell>" + " " + "</cell>");
					}
					
					// ///////////////////////////////////////////////////
					// writer.print("<cell>");
					// for (DelitoDTO delitoDTO : delitoDTOs) {
					// if (delitoDTO.getCatDelitoDTO() != null) {
					// writer.print(" "
					// + delitoDTO.getCatDelitoDTO().getNombre() + " ");
					// }
					// }
					// writer.print("</cell>");

					
					//Combo box delitos
					List<DelitoDTO> listaDelitos = expedienteDTO.getDelitos();

					if (listaDelitos != null && !listaDelitos.isEmpty()) {
						listaDelitosMostrados = new HashMap<Long, String>();
						writer.print("<cell><![CDATA["
								+ "<select id='cbxDelitos' style='width: 300px;'>");
						for (DelitoDTO delito : listaDelitos) {
							if (!listaDelitosMostrados.containsKey(delito
									.getCatDelitoDTO().getCatDelitoId())) {
								writer.print("<option>"
										+ delito.getCatDelitoDTO().getNombre()
										+ "</option>");
								listaDelitosMostrados.put(delito
										.getCatDelitoDTO().getCatDelitoId(),
										delito.getCatDelitoDTO().getNombre());
							}
						}
						writer.print("</select>" + "]]></cell>");
					} else {
						writer.print("<cell>" + " " + "</cell>");
					}
					writer.print("</row>");
				}
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (NSJPNegocioException e) {
			log.error(e.getMessage());
		}
		return null;
	}
}

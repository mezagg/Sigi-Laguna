package mx.gob.segob.nsjp.web.expdiente.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.involucrado.InvolucradoDelegate;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class VisorExpedienteDefensoriaAction extends GenericAction {

	private static final Logger logger = Logger.getLogger(VisorExpedienteDefensoriaAction.class);

	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	@Autowired
	private InvolucradoDelegate involucradoDelegate;
	@Autowired
	private CatalogoDelegate catalogoDelegate;
	
	public ActionForward visorExpedienteDefensoria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			
			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"), 0);
			String numeroCaso = request.getParameter("numeroCaso");
			if(idNumeroExpediente > 0){
				expedienteDTO.setNumeroExpedienteId(idNumeroExpediente);
				expedienteDTO.setInvolucradosSolicitados(true);
				expedienteDTO.setAvisosDesignacionSolicitados(true);
				expedienteDTO = expedienteDelegate.obtenerExpediente(expedienteDTO);
				setExpedienteTrabajo(request, expedienteDTO);
				request.setAttribute("expediente", expedienteDTO);
				request.getSession().setAttribute("expedienteId", expedienteDTO.getExpedienteId());
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mapping.findForward("error");
	}
	
	public ActionForward consultarInvolucradoDefensoria(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try{
			Long idInvolucrado = NumberUtils.toLong(request.getParameter("idInvolucrado"), 0);
			if(idInvolucrado > 0){
				InvolucradoDTO involucrado = new InvolucradoDTO();
				involucrado.setElementoId(idInvolucrado);
				involucrado = involucradoDelegate.obtenerInvolucrado(involucrado);
				request.setAttribute("involucrado", involucrado);
			}
			List<CatalogoDTO> idiomas = catalogoDelegate.recuperarCatalogo(Catalogos.IDIOMA);
			List<CatalogoDTO> escolaridad = catalogoDelegate.recuperarCatalogo(Catalogos.ESCOLARIDAD);
			List<CatalogoDTO> estadoCivil = catalogoDelegate.recuperarCatalogo(Catalogos.ESTADO_CIVIL);
			List<CatalogoDTO> nacionalidad = catalogoDelegate.recuperarCatalogo(Catalogos.NACIONALIDAD);
			List<CatalogoDTO> ocupacion = catalogoDelegate.recuperarCatalogo(Catalogos.OCUPACION);
			request.setAttribute("consultar", true);
			request.setAttribute("idiomas", idiomas);
			request.setAttribute("ocupacion", ocupacion);
			request.setAttribute("escolaridad", escolaridad);
			request.setAttribute("estadoCivil", estadoCivil);
			request.setAttribute("nacionalidad", nacionalidad);
			return mapping.findForward("success");
		}catch(Exception e){
		
			return mapping.findForward("error");
		}
	}
	
	/**
	 * M&eacute;todo para obtener en formato de grid los defendidos de un
	 * expediente
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarDefendidosConEtapaPorExpediente(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			Long expedienteId = NumberUtils.toLong(
					request.getParameter("expedienteId"), 0L);

			List<InvolucradoDTO> listaDeDefendidos = new ArrayList<InvolucradoDTO>();

			if (expedienteId > 0) {
				Calidades[] calidades = new Calidades[] { Calidades.DEFENDIDO };
				listaDeDefendidos = involucradoDelegate
						.obtenerInvolucradosPorExpedienteYCalidades(null,
								calidades, null, expedienteId);
			}

			if (listaDeDefendidos != null && !listaDeDefendidos.isEmpty()) {

				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter writer = response.getWriter();

				writer.print("<rows>");
				// Configuracion del Paginador
				String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
				writer.print(paginacion);

				for (InvolucradoDTO defendido : listaDeDefendidos) {
					writer.print("<row id='" + defendido.getElementoId() + "'>");
					writer.print("<cell>"
							+ ((defendido.getNombreCompleto() != null && !defendido
									.getNombreCompleto().trim().isEmpty()) ? defendido
									.getNombreCompleto() : "-")
							+ "</cell>");

					if (defendido.getEtapaInvolucrado() != null
							&& defendido.getEtapaInvolucrado()
									.getCatEtapaValor() != null) {
						writer.print("<cell>"
								+ defendido.getEtapaInvolucrado()
										.getCatEtapaValor().getValor()
								+ "</cell>");
						
						writer.print("<cell>"
								+ defendido.getEtapaInvolucrado()
										.getCatEtapaValor().getIdCampo()
								+ "</cell>");
					} else {
						writer.print("<cell> - </cell>");
						writer.print("<cell> - </cell>");
					}
					writer.print("</row>");
				}

				writer.print("</rows>");
				writer.flush();
				writer.close();

			}
		} catch (Exception e) {
		}
		return null;
	}
}

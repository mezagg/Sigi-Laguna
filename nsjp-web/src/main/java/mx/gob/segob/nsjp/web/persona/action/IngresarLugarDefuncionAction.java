/**
 * Nombre del Programa 	: IngresarLugarDefuncionAction.java                                           
 * Compania            	: Ultrasist                                                
 * Proyecto            	: NSJP              			Fecha:01/03/2011 
 * Marca de cambio     	: N/A                                                     
 * Descripcion General   : Clase action para ingresar los datos de la defuncion de la victima
 * Programa Dependiente  : N/A                                                      
 * Programa Subsecuente 	: N/A                                                      
 * Cond. de ejecucion    : N/A                                                   
 * Dias de ejecucion     : N/A                             Horario: N/A
 *                               MODIFICACIONES                                       
 *------------------------------------------------------------------------------                                                                    
 * Compania              :Ultrasist                                                             
 * Proyecto              :NSJP                           Fecha:18/04/2011       
 * Modificacion          :N/A                                                           
 *------------------------------------------------------------------------------      
 */
package mx.gob.segob.nsjp.web.persona.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.service.catalogo.CatalogoService;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase action para ingresar los datos de defuncion de la victima
 * 
 * @version 1.0
 * 
 */
public class IngresarLugarDefuncionAction extends GenericAction {
	/** Clase delegadora para Calidad */

	/** Log de clase */
	private static final Logger log = Logger
			.getLogger(IngresarLugarDefuncionAction.class);
	
	@Autowired
	 private CatalogoService service;

	/**
	 * Método utilizado para realizar la carga del combo Tipos de defuncion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoTiposDefuncion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipos defuncion
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.TIPOS_DEFUNCION);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catTiposDefuncion", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo Certificada por
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoCertificadaPor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Certificada por
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.DEFUNCION_CERTIFICADA_POR);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catCertificadaPor", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo Sitio de defuncion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoSitioDefuncion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Sitio de defuncion
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.SITIO_DEFUNCION);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catSitioDefuncion", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo Sitio de lesion
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoSitioLesion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Sitio de lesion
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.DEFUNCION_SITIO_LESION);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catSitioLesion", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo Fue en el trabajo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoFueEnTrabajo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Fue en el trabajo
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.DEFUNCION_FUE_EN_TRABAJO);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catFueEnTrabajo", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo Tipo de evento
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoTipoEvento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo de Evento
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.DEFUNCION_TIPO_EVENTO);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catTipoEvento", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo tipo de victima
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoTipoVictima(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo de victima
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.DEFUNCION_TIPO_VICTIMA);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catTipoVictima", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo tipo de arma
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoTipoArma(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Tipo de arma
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.DEFUNCION_TIPO_ARMA);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catTipoArma", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo condicion de embarazo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoCondicionEmbarazo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Condicion del embarazo
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.DEFUNCION_CONDICION_EMBARAZO);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catCondicionEmbarazo", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * Método utilizado para realizar la carga del combo periodo de posparto
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarCatalogoPeriodoPosparto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			// Carga catalogo Periodo de posparto
			log.debug("ejecutando Action cargarCatalogos");
			List<CatalogoDTO> listaCatalogo = service.recuperarCatalogo(Catalogos.DEFUNCION_PERIODO_POSPARTO);
			
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catPeriodoPosparto", CatalogoDTO.class);
			String xml = converter.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

//	/**
//	 * Método utilizado para realizar la carga del EntidadFederativa
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 */
//	public ActionForward cargarEntFederativas(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//
//		try {
//			IngresarDomicilioForm forma = (IngresarDomicilioForm) form;
//			List<CatalogoDTO> listaCatalogoEntidades = catDelegate
//					.recuperarCatalogoDependiente(Catalogos.ENTIDAD_FEDERATIVA,
//							forma.getGlCatPaisId());
//			XStream converter=new XStream(); 			converter.alias("listaCatalogoEntidades", java.util.List.class);
//			XStream converter=new XStream(); 			converter.alias("catEntidadesFed", CatalogoDTO.class);
//			String xml = converter.toXML(listaCatalogoEntidades);
//			response.setContentType("text/xml");
//			PrintWriter pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * Método utilizado para realizar la carga del combo ciudad
//	 * 
//	 * @param form
//	 * @param request
//	 * @param response
//	 */
//	public ActionForward cargarCiudad(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//
//		try {
//			IngresarDomicilioForm forma = (IngresarDomicilioForm) form;
//			List<CatalogoDTO> listaCatalogoCiudades = catDelegate
//					.recuperarCatalogoDependiente(Catalogos.CIUDAD,
//							forma.getGlCatEntidadFederativaId());
//			XStream converter=new XStream(); 			converter.alias("listaCatalogoCiudades", java.util.List.class);
//			XStream converter=new XStream(); 			converter.alias("catCiudades", CatalogoDTO.class);
//			String xml = converter.toXML(listaCatalogoCiudades);
//			response.setContentType("text/xml");
//			PrintWriter pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * Método utilizado para realizar la carga del combo delegacion o Municipio
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 */
//	public ActionForward cargarDelegacionMunicipio(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//
//		try {
//			IngresarDomicilioForm forma = (IngresarDomicilioForm) form;			
//			List<CatalogoDTO> listaCatalogoDelegMun = catDelegate
//			.recuperarCatalogoDependiente(Catalogos.DELEGACION_MUNICIPIO,
//					forma.getGlCatEntidadFederativaId());		
//			XStream converter=new XStream(); 			converter.alias("listaCatalogoDelegMun", java.util.List.class);
//			XStream converter=new XStream(); 			converter.alias("catDelegMun", CatalogoDTO.class);
//			String xml = converter.toXML(listaCatalogoDelegMun);
//			response.setContentType("text/xml");
//			PrintWriter pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//
//	}
//
//	/**
//	 * Método utilizado para realizar la carga del combo asentamiento o colonia
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 */
//	public ActionForward cargarAsentamientoColonia(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		
//		try {
////			IngresarDomicilioForm forma = (IngresarDomicilioForm) form;			
////			List<CatalogoDTO> listaCatalogoAsentColonia = catDelegate
////					.recuperarCatalogoDependiente(Catalogos.ASENTAMIENTO_COLONIA,
////							forma.getGlDelgMunId());
//			
//			IngresarDomicilioForm forma = (IngresarDomicilioForm) form;
//			log.debug("::::::::::::::::EJECUTANDO CARGA ASENTAMIENTO COLONIA:::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
//			if(forma.getGlDelgMunId()== null || forma.getGlDelgMunId().longValue() == -1){
//				log.debug("<---------DELEGACION MUNICIPIO = -1     ---------------------------------->");
//				forma.setGlDelgMunId(null);
//			}
//			if(forma.getGlCatCiudadId()==null || forma.getGlCatCiudadId().longValue() == -1){
//				log.debug("<---------CIUDAD = -1     ---------------------------------->");
//				forma.setGlCatCiudadId(null);
//			}
//			if(forma.getGlCatTipoAsentamientoId()==null || forma.getGlCatTipoAsentamientoId().longValue() == -1){
//				log.debug("<---------ASENTAMIENTO = -1     ---------------------------------->");
//				forma.setGlCatTipoAsentamientoId(null);
//			}
//			
//			List<CatalogoDTO> listaCatalogoAsentColonia = catDelegate.consultarAsentamiento(forma.getGlDelgMunId(), forma.getGlCatCiudadId(), forma.getGlCatTipoAsentamientoId());
//			
//			XStream converter=new XStream(); 			converter.alias("listaCatalogoAsentColonia", java.util.List.class);
//			XStream converter=new XStream(); 			converter.alias("catAsentColonia", CatalogoDTO.class);
//			String xml = converter.toXML(listaCatalogoAsentColonia);
//			response.setContentType("text/xml");
//			PrintWriter pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//		
//	}
//
//	/**
//	 * Método utilizado para realizar la carga del combo tipo de asentamiento
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 */
//	public ActionForward cargarTipoAsentamiento(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		// Carga catalogo tipo asentamiento
//		try {
//			log.debug("ejecutando Action cargarTipoAsentamiento");
//			List<CatalogoDTO> listaCatalogo = catDelegate
//					.recuperarCatalogo(Catalogos.TIPO_ASENTAMIENTO);
//			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
//			XStream converter=new XStream(); 			converter.alias("catTipoAsentamiento", CatalogoDTO.class);
//			String xml = converter.toXML(listaCatalogo);
//			response.setContentType("text/xml");
//			PrintWriter pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
//
//		}
//
//		catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * Método utilizado para realizar la carga del combo tipo de calle
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 */
//	public ActionForward cargarTipoCalle(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		// Carga catalogo tipo calle
//		try {
//			log.debug("ejecutando Action cargarTipoCalle");
//			List<CatalogoDTO> listaCatalogo = catDelegate
//					.recuperarCatalogo(Catalogos.TIPO_CALLE);
//			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
//			XStream converter=new XStream(); 			converter.alias("catTipoCalle", CatalogoDTO.class);
//			String xml = converter.toXML(listaCatalogo);
//			response.setContentType("text/xml");
//			PrintWriter pw = response.getWriter();
//			pw.print(xml);
//			pw.flush();
//			pw.close();
//
//		}
//
//		catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	/**
//	 * Método utilizado para realizar la consulta de los datos de un
//	 * Asentamiento, ciudad, delegacion o municipio y tipo de asentamiento en
//	 * base al codigo postal introducido por el usuario
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 */
//	public ActionForward consultarDatosAsentamiento(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		 try {
//		
//		 log.debug("Ejecutando Action consultarDatosAsentamiento::::Ingresar Domicilio Action");
//		 IngresarDomicilioForm forma=(IngresarDomicilioForm) form;
//		
//		 if(!forma.getCodigoPostal().isEmpty()){
//		 log.debug("CODIGO POSTAL="+forma.getCodigoPostal()+":::Ingresar Domicilio Action");
//		
//		 }
//		 
//		 List<AsentamientoDTO> asentamientos = lugarDelegate.completarAsentamientoXCodigoPostal(forma.getCodigoPostal());
//			XStream converter=new XStream(); 			converter.alias("asentamientos", java.util.List.class);
//			XStream converter=new XStream(); 			converter.alias("asentamiento", AsentamientoDTO.class);
//		
//		 String xml = converter.toXML(asentamientos);
//		 response.setContentType("text/xml");
//		 
//		 PrintWriter pw = response.getWriter();
//		 pw.print(xml);
//		 pw.flush();
//		 pw.close();
//		 }
//		 catch (Exception e) {
//		 log.debug(e.getCause());
//		 log.error(e.getCause());
//		 }
//		return null;
//	}
//	
//	public ActionForward obtenerCodigoPostalXIdAsentamiento(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		 try {
//		
//		 log.debug("Ejecutando Action obtenerCodigoPostalXIdAsentamiento::::Ingresar Domicilio Action");
//		 
//		 Long idAsentamiento = NumberUtils.toLong(request.getParameter("idAsentamiento"),0);
//		 log.debug("::::idAsentamiento::::: " + idAsentamiento);
//		 
////		 String loCodigoPostaBD = lugarDelegate.obtenerCodigoPostalXIdAsentamiento(idAsentamiento);
//		 AsentamientoDTO asentDTO = lugarDelegate.obtenerAentamientoPrId(idAsentamiento);
//		 log.debug("::::loCodigoPostaBD::::: " + asentDTO.getCodigoPostal());
//
////			XStream converter=new XStream(); 			converter.alias("loCodigoPostaBD", String.class);
//			XStream converter=new XStream(); 			converter.alias("asentamiento", AsentamientoDTO.class);
//		
//		 String xml = converter.toXML(asentDTO);
//		 response.setContentType("text/xml");
//		 
//		 PrintWriter pw = response.getWriter();
//		 pw.print(xml);
//		 pw.flush();
//		 pw.close();
//		 }
//		 catch (Exception e) {
//		 log.debug(e.getCause());
//		 log.error(e.getCause());
//		 }
//		return null;
//	}
}
/**
* Nombre del Programa : IngresarIndividuoAction.java
* Autor               : Erick Arturo
* Compania            : Ultrasist
* Proyecto            : NSJP                    Fecha: 23/02/2011
* Marca de cambio     : N/A
* Descripcion General : Clase Action para Ingresar Individuo
* Programa Dependiente: N/A
* Programa Subsecuente: N/A
* Cond. de ejecucion  : N/A
* Dias de ejecucion   : N/A                             Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor               :N/A
* Compania            :N/A
* Proyecto            :N/A                                   Fecha: N/A
* Modificacion        :N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.persona.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author arturo
 *
 *  Action para ingresar datos generales
 *
 */
public class IngresarDatosGeneralesAction extends GenericAction{

private static final Logger log  = Logger.getLogger(IngresarDatosGeneralesAction.class);
//    private ICatEscolaridadBDelegate  catEscolaridadBDelegate;
//    private ICatEstadoCivilBDelegate  catEstadoCivilBDelegate;
//    private ICatIdiomaBDelegate catIdiomaBDelegate;
//    private ICatNacionalidadBDelegate catNacionalidadBDelegate;
//    private ICatOcupacionBDelegate catOcupacionBDelegate;
//    private ICatReligionBDelegate catReligionBDelegate;

public ActionForward consultarCatalogoIdioma(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
//		
    	try {
    		log.info("ejecutando Action Cargar Combo idioma"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.IDIOMA);
			XStream converterIdioma= new XStream();
			converterIdioma.alias("listaCatalogo", java.util.List.class);
			converterIdioma.alias("catIdioma", CatalogoDTO.class);
			
			String xml = converterIdioma.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}
		return null;

		
		
		
	}
    
    public ActionForward consultarCatalogoEstadoCivil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
    	try {
    		log.info("ejecutando Action Cargar Combo Estado civil"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.ESTADO_CIVIL);
			XStream converterEstCivil= new XStream();

			converterEstCivil.alias("listaCatalogo", java.util.List.class);
			converterEstCivil.alias("catEstadoCivil", CatalogoDTO.class);
			
			String xml = converterEstCivil.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}

		return null;
	}
    
	public ActionForward consultarCatalogoEscolaridad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Cargar Combo Escolaridad"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.ESCOLARIDAD);
			XStream converterEscolaridad= new XStream();

			int situacioId = Catalogos.SITUACION_JURIDICA_DETENIDO.ordinal();

			converterEscolaridad.alias("listaCatalogo", java.util.List.class);
			converterEscolaridad.alias("catEscolaridad", CatalogoDTO.class);
			
			String xml = converterEscolaridad.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}

		return null;
		
		
	}

	public ActionForward consultarCatalogoSituacionJuridicaDetenido(ActionMapping mapping, ActionForm form,
													  HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			log.info("ejecutando Action Cargar Combo Escolaridad");
			List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.SITUACION_JURIDICA);

			XStream converterSituacion= new XStream();
			converterSituacion.alias("listaCatalogo", java.util.List.class);
			converterSituacion.alias("catSituacionJuridicaDetenido", CatalogoDTO.class);

			String xml = converterSituacion.toXML(listaCatalogo);
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
	
	public ActionForward consultarCatalogoNacionalidad (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			XStream converterNacionalidad= new XStream();
    		log.info("ejecutando Action Cargar Combo Nacionalidad"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.NACIONALIDAD);

			converterNacionalidad.alias("listaCatalogo", java.util.List.class);
			converterNacionalidad.alias("catNacionalidad", CatalogoDTO.class);
			
			String xml = converterNacionalidad.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}

		return null;
		
		
	}
	
	
	public ActionForward consultarCatalogoReligion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Cargar Combo Religion"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.RELIGION);
			XStream converterReligion= new XStream();
			converterReligion.alias("listaCatalogo", java.util.List.class);
			converterReligion.alias("catReligion", CatalogoDTO.class);
			
			String xml = converterReligion.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}

		return null;
		
		
	}
	
	public ActionForward consultarCatalogoOcupacion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			XStream converterOp= new XStream();
    		log.info("ejecutando Action Cargar Combo Ocupacion"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.OCUPACION);

			converterOp.alias("listaCatalogo", java.util.List.class);
			converterOp.alias("catOcupacion", CatalogoDTO.class);
			
			String xml = converterOp.toXML(listaCatalogo);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}
		return null;
		
		
	}

	public ActionForward consultarCatalogoServicioPericial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Cargar Combo Servicios Periciales"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.TIPO_SOLICITUD);
			XStream converterServicio= new XStream();
    	
			converterServicio.alias("listaCatalogo", java.util.List.class);
			converterServicio.alias("catServicioPericial", CatalogoDTO.class);
			
			String xml = converterServicio.toXML(listaCatalogo);
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

	public ActionForward consultarCatalogoEspecialidadPericial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Cargar Combo Especialidad Pericial"); 
    		List<CatalogoDTO> listaCatalogo=catDelegate.recuperarCatalogo(Catalogos.TIPO_ESPECIALIDAD_FUNCIONARIO);
			XStream converterEsp= new XStream();
			converterEsp.alias("listaCatalogo", java.util.List.class);
			converterEsp.alias("catEspecialidadPericial", CatalogoDTO.class);
			
			String xml = converterEsp.toXML(listaCatalogo);
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
	
	public ActionForward consultarFechaCaptura(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Fecha actual"); 
    		
			Date fechaCaptura=new Date();
			SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
			String fech=formato.format(fechaCaptura);
			XStream converterFechaCap= new XStream();
			converterFechaCap.alias("fechaActual", java.lang.String.class);
						
			String xml = converterFechaCap.toXML(fech);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}
		return null;
		
		
	}
	
	public ActionForward consultarHoraCaptura(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Hora actual"); 
    		
			Date horaCaptura=new Date();
			SimpleDateFormat formato= new SimpleDateFormat("HH:mm:ss");
			String hora=formato.format(horaCaptura);
			XStream converter= new XStream();
			XStream converter=new XStream(); 			converter.alias("horaActual", java.lang.String.class);
			String xml = converter.toXML(hora);
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			}
		catch (Exception e) {
			log.info(e);
		}
			
			
//			e.printStackTrace();
//		}
		return null;
		
		
	}
	
	public ActionForward consultarEdoFisico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			
			log.info("ejecutando Action Cargar Combo Estado Fisico"); 
			List<CatalogoDTO> listaCatalogo = catDelegate.recuperarCatalogo(Catalogos.DATOS_GRLS_EDO_FISICO);
			XStream converter= new XStream();
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catEdoFisico", CatalogoDTO.class);
			
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
	
	public ActionForward consultarEdoConsciencia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			
			log.info("ejecutando Action Cargar Combo Estado Fisico"); 
			List<CatalogoDTO> listaCatalogo = catDelegate.recuperarCatalogo(Catalogos.DATOS_GRLS_EDO_CONSCIENCIA);
			XStream converter= new XStream();
    		
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catEdoConsciencia", CatalogoDTO.class);
			
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
	
	public ActionForward consultarEdoConscienciaInconsciente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Cargar Combo Estado Conciencia"); 
    		
			List<CatalogoDTO> listaCatalogo = catDelegate.recuperarCatalogo(Catalogos.DATOS_GRLS_EDO_CONSCIENCIA_INCONSCIENTE);
			XStream converter= new XStream();
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catEdoConscienciaInconsciente", CatalogoDTO.class);
			
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
	 * Action que carga el cobo de Edad unidad para victimas muertas
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarCatalogoEdadUnidad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Cargar Combo Edad Unidad"); 
    		    		
			List<CatalogoDTO> listaCatalogo = catDelegate.recuperarCatalogo(Catalogos.EDAD_UNIDAD);
			XStream converter= new XStream();
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catEdadUnidad", CatalogoDTO.class);
			
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
	 * Action que carga el cobo de Condicion Actividad para victimas muertas
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward consultarCatalogoCondicionActividad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
    		log.info("ejecutando Action Cargar Combo Condicion Actividad"); 
    		    		
			List<CatalogoDTO> listaCatalogo = catDelegate.recuperarCatalogo(Catalogos.CONDICION_ACTIVIDAD);
			XStream converter= new XStream();
			XStream converter=new XStream(); 			converter.alias("listaCatalogo", java.util.List.class);
			XStream converter=new XStream(); 			converter.alias("catCondicionActividad", CatalogoDTO.class);
			
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
		
		
}

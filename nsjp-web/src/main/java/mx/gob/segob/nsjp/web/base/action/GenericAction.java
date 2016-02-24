/**
 * Nombre del Programa : GenericAction.java                                    
 * Autor: Vladimir Aguirre Piedragil
 * Compania: Ultrasist                                                
 * Proyecto: NSJP 
 * Fecha: 29 Mar 2011 
 * Marca de cambio: N/A                                                     
 * Descripcion General: Action gen�rico del que deben extender todos los actions de la aplicaci�n y poder agrupar funcionalidad com�n.(DRY).
 * Programa Dependiente: N/A                                                      
 * Programa Subsecuente: N/A                                                      
 * Cond. de ejecucion: N/A                                                      
 * Dias de ejecucion: N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.web.base.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.delegate.catalogo.CatalogoDelegate;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteSesionDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.usuario.RolService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.actions.MappingDispatchAction;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.xstream.XStream;
import java.util.logging.Level;
import javax.naming.Context;
import javax.servlet.ServletContext;
import mx.gob.segob.nsjp.comun.enums.catalogo.Catalogos;
import mx.gob.segob.nsjp.dto.catalogo.CatalogoDTO;



/**
 * @author vaguirre
 * 
 */
public abstract class GenericAction extends MappingDispatchAction {
    private static final Logger log = Logger.getLogger(GenericAction.class);
    /** Clase para la conversion de los objetos a xml */

    private final static String KEY_SESSION_EXPEDIENTE_TRABAJO = "KEY_SESSION_EXPEDIENTE_TRABAJO";
    public final static String KEY_SESSION_USUARIO_FIRMADO = "KEY_SESSION_USUARIO_FIRMADO";
    public final static String KEY_SESSION_CONFIGURACION_GLOBAL = "KEY_SESSION_CONFIGURACION_GLOBAL";
    //URG-005 Turno
    public final static String KEY_SESSION_HABILITAR_TURNO = "KEY_SESSION_HABILITAR_TURNO";

    public final static String KEY_SESSION_ACTION_LOCALE = "org.apache.struts.action.LOCALE";
    // Menu izquierdo, se generara dinamicamente 
    public final static String KEY_SESSION_MENU_DINAMICO_DERECHO = "KEY_SESSION_MENU_DINAMICO_DERECHO";
    public final static String KEY_SESSION_MENU_DINAMICO_SUPERIOR = "KEY_SESSION_MENU_DINAMICO_SUPERIOR";
    public final static String KEY_SESSION_MENU_DINAMICO_IZQUIERDO = "KEY_SESSION_MENU_DINAMICO_IZQUIERDO";
    public final static String EXITO="exito";
    public final static String GUION = "-";
    public final static String NA = "- NA -";

    //TODO: verificar que cada action tenga su propio converter, esto porque se utiliza por todas
    //las llamadas asincronas y entra en conflicto con lo que genera.
    @Autowired
    protected XStream converter;

    @Autowired
    protected CatalogoDelegate catDelegate;
    
    @Autowired
    private RolService rolService;

    
    public void inicializaCatalogos() {
       // if (paises != null) {
       ServletContext sc = this.getServlet().getServletContext();
            try {
                if (sc.getAttribute("nacionalidades") == null) {
                    sc.setAttribute("nacionalidades", catDelegate.recuperarCatalogo(Catalogos.NACIONALIDAD));
                }
                if (sc.getAttribute("paises") == null) {
                    sc.setAttribute("paises", catDelegate.recuperarCatalogo(Catalogos.PAISES));
                }
                if (sc.getAttribute("ocupaciones") == null){
                    sc.setAttribute("ocupaciones", catDelegate.recuperarCatalogo(Catalogos.OCUPACION));
                }
                if (sc.getAttribute("idiomas") == null){
                    sc.setAttribute("idiomas", catDelegate.recuperarCatalogo(Catalogos.IDIOMA));
                }
                if (sc.getAttribute("escolaridades") == null){
                    sc.setAttribute("escolaridades", catDelegate.recuperarCatalogo(Catalogos.ESCOLARIDAD));
                }
                if (sc.getAttribute("tiposAsentamientos") == null){
                    sc.setAttribute("tiposAsentamientos", catDelegate.recuperarCatalogo(Catalogos.TIPO_ASENTAMIENTO));
                }
                if (sc.getAttribute("tiposCalles") == null){
                    sc.setAttribute("tiposCalles", catDelegate.recuperarCatalogo(Catalogos.TIPO_CALLE));
                }
                
            } catch (NSJPNegocioException ex) {
                log.error("Error al consultar las Nacionalidades", ex);
            }
        
        
    }

    
    
    public GenericAction() {
        
    }
    
    
  /*  
    public 
    try {
            //catDelegate.consultarDelito();
            //catDelegate.consultarFunciones();
            Context context= ServletActionContext.getContext();
            getApplication();
            .put(Catalogos.NACIONALIDAD, catDelegate.recuperarCatalogo(Catalogos.NACIONALIDAD));
            
        } catch (NSJPNegocioException ex) {
            java.util.logging.Logger.getLogger(GenericAction.class.getName()).log(Level.SEVERE, null, ex);
        }
*/

    /**
     * Escribe el XML de la respuesta en el response.
     * 
     * @param response
     *            Objeto <code>HttpServletResponse</code> donde se escribir� la
     *            respuesta.
     * @param xml
     *            Respuesta.
     */
    protected void escribirRespuesta(HttpServletResponse response, String xml) {
        this.escribir(response, xml, null);
        
    }

    /**
     * Escribe el XML de con le mesaje de error en el response.
     * 
     * @param response
     *            Objeto <code>HttpServletResponse</code> donde se escribir� la
     *            respuesta.
     * @param ex
     *            Excepci�n ocurrida.
     */
    protected void escribirError(HttpServletResponse response,
            NSJPNegocioException ex) {
        this.escribir(response, null, ex);
    }
    /**
     * 
     * @param response
     *            Objeto <code>HttpServletResponse</code> donde se escribir� la
     *            respuesta.
     * @param xml
     *            Respuesta.
     * @param ex
     *            Excepci�n ocurrida.
     */
    protected void escribir(HttpServletResponse response, String xml,
            NSJPNegocioException ex) {

        final StringBuffer buf = new StringBuffer();
        buf.append("<response>");
        if (ex == null) {
            buf.append("<code>0</code>");
            buf.append("<message></message>");
        } else {
            buf.append("<code>");
            buf.append(ex.getCodigo());
            buf.append("</code>");
            buf.append("<message>");
            buf.append(ex.getMessage());
            buf.append("</message>");
        }
        buf.append("<body>");
        buf.append(xml);
        buf.append("</body>");
        buf.append("</response>");
        PrintWriter pWriter = null;
        try {
            response.setContentType("text/xml");
            pWriter = response.getWriter();
            pWriter.print(buf);
            pWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (pWriter != null) {
                pWriter.close();
            }
        }

    }

    /**
     * Obtiene el usuario firmado en la aplicaci�n.
     * 
     * @return Usuario obtenido de la sesion, <code>null</code> en caso de que
     *         no exista un usuario firmado.
     */
    protected UsuarioDTO getUsuarioFirmado(HttpServletRequest request) {
        HttpSession ses = request.getSession();

        UsuarioDTO usrFromSess = (UsuarioDTO) ses
                .getAttribute(KEY_SESSION_USUARIO_FIRMADO);

        if (usrFromSess == null) {
            return null;
        }
        if (usrFromSess.getAreaActual() == null) {
            usrFromSess.setAreaActual(usrFromSess.getArea());
        }
        return usrFromSess;
    }
    /**
     * Recupera la configuraci�n global
     * @param request
     * @return
     */
    protected ConfiguracionDTO getConfguracionGlobal(HttpServletRequest request) {
        HttpSession ses = request.getSession();
        ConfiguracionDTO cfgFromSess = (ConfiguracionDTO) ses
                .getAttribute(KEY_SESSION_CONFIGURACION_GLOBAL);
        return cfgFromSess;
    }

    /**
     * Obtiene el expediete de trabajo.
     * 
     * @return Expediente obtenido de sesion, <code>null</code> en caso de que
     *         no exista.
     */
    protected ExpedienteDTO getExpedienteTrabajo(HttpServletRequest request,
            String numeroUnicoExpediente) {
        HttpSession ses = request.getSession();
        
        ExpedienteDTO fromSes = ExpedienteSesionTransformer.
        	transformaExpedienteEnSesion((ExpedienteSesionDTO) ses.getAttribute(KEY_SESSION_EXPEDIENTE_TRABAJO + numeroUnicoExpediente));
        
        if (fromSes != null) {
            log.debug("Obteniendo de session :: "
                    + fromSes.getNumeroExpediente() + " con numeroExpedienteId = " + fromSes.getNumeroExpedienteId());
            return fromSes;
        }
        return null;
    }

    /**
     * Remueve el expediente de trabajo.
     * 
     * @return Expediente obtenido de sesion, <code>null</code> en caso de que
     *         no exista.
     */
    protected void eliminarExpedienteTrabajo(HttpServletRequest request,
            String numeroUnicoExpediente) {
        HttpSession ses = request.getSession();
        ses.removeAttribute(KEY_SESSION_EXPEDIENTE_TRABAJO
                + numeroUnicoExpediente);
    }

    /**
     * Sube a sesi�n el expediente de trabajo, usa como llave la propiedad:
     * <code>numeroExpediente</code>.
     * 
     * @param request
     * @param exp
     */
    protected void setExpedienteTrabajo(HttpServletRequest request,
            ExpedienteDTO exp) {
        log.debug("Poniendo en session :: " + exp.getNumeroExpediente());
        
        ExpedienteSesionDTO loExpedienteSesionDTO = ExpedienteSesionTransformer.transformaExpedienteEnSesion(exp);
        
        HttpSession ses = request.getSession();
        ses.setAttribute(
                KEY_SESSION_EXPEDIENTE_TRABAJO + exp.getNumeroExpediente(), loExpedienteSesionDTO);
    }
    /**
     * Conviert eun String a Long.
     * 
     * @param val
     * @return Si es nulo o no es numerico regresa nulo, en caso contrario
     *         regresa el valor numerico representado en la cadena
     */
    protected Long parseLong(String val) {
        if (val == null || !NumberUtils.isNumber(val)) {
            return null;
        }
        return Long.parseLong(val);
    }
    /**
     * Valida que la cadena no apunte a la referencia <code>null</code>, est�
     * vac�a o contenga la palabra null (ignorando may�sculas o min�sculas).<br>
     * isNotBlank(null) = <code>false</code><br>
     * isNotBlank("") = <code>false</code><br>
     * isNotBlank(" ") = <code>false</code><br>
     * isNotBlank("null") = <code>false</code><br>
     * isNotBlank("NULL") = <code>false</code><br>
     * isNotBlank("hola") = <code>true</code><br>
     * isNotBlank("  hola  ") = <code>true</code>
     * 
     * @param val
     * @return
     */
    protected boolean isNotBlank(String val) {
        return (StringUtils.isNotBlank(val) && val.equalsIgnoreCase("null"));
    }
    
    /**
     * Metodo que construye el XML para mostrarse en los girds de PJ admonPj y encargado sala
     * @param listaInvolucrados
     * @param response
     * @throws IOException
     */
	protected void escribeXmlInvolucradosAudiencia(
			List<InvolucradoDTO> listaInvolucrados, HttpServletResponse response)
			throws IOException {

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();

		writer.print("<rows>");

		final PaginacionDTO pag = PaginacionThreadHolder.get();

		if (pag != null && pag.getTotalRegistros() != null) {
			writer.print("<total>" + pag.getTotalPaginas() + "</total>");
			writer.print("<records>" + pag.getTotalRegistros() + "</records>");
		} else {
			writer.print("<total>0</total>");
			writer.print("<records>0</records>");
		}

		for (InvolucradoDTO involucrado : listaInvolucrados) {

			if (involucrado.getCalidadDTO() != null
					&& involucrado.getCalidadDTO().getCalidades() != null
					&& involucrado.getCalidadDTO().getCalidades().getValorId() != null) {

				writer.print("<row id='" + involucrado.getElementoId() + "'>");
				writer.print("<cell>"
						+ (involucrado.getNombreCompleto() != null ? involucrado
								.getNombreCompleto() : "") + "</cell>");

				// Obtenemos el id de la calidad
				Long calidadId = involucrado.getCalidadDTO().getCalidades()
						.getValorId();

				if (calidadId.equals(Calidades.DENUNCIANTE.getValorId())
						&& involucrado.getCondicion() != null
						&& involucrado.getCondicion().equals(new Short("1"))) {
					writer.print("<cell>" + "V&#237;ctima y Denunciante"
							+ "</cell>");

				} else {
					writer.print("<cell>"
							+ ((involucrado.getCalidadDTO().getValorIdCalidad() != null && involucrado
									.getCalidadDTO().getValorIdCalidad()
									.getValor() != null) ? involucrado
									.getCalidadDTO().getValorIdCalidad()
									.getValor() : "") + "</cell>");
				}

				if (calidadId.equals(Calidades.PROBABLE_RESPONSABLE_PERSONA
						.getValorId())
						|| calidadId
								.equals(Calidades.PROBABLE_RESPONSABLE_ORGANIZACION
										.getValorId())) {

					List<DelitoDTO> listaDelitos = involucrado
							.getDelitosCometidos();
					
					//Mapa usado para no duplicar los delitos del involucrado en vista
					Map<Long,String> listaDelitosMostrados = null;

					// Combo box de delitos
					if (listaDelitos == null || listaDelitos.isEmpty()) {
						writer.print("<cell><![CDATA[<select id='cbxDelitos' style='width: 150px;' disabled='disabled'><option>"
								+ "Sin delito</option></select>]]></cell>");
					} else {
						
						listaDelitosMostrados = new HashMap<Long,String>();
						
						writer.print("<cell><![CDATA["
								+ "<select id='cbxDelitos' style='width: 150px;'>");
						for (DelitoDTO delito : listaDelitos) {
							
							if(!listaDelitosMostrados.containsKey(delito.getCatDelitoDTO().getCatDelitoId())){
								writer.print("<option>" + delito.getCatDelitoDTO().getNombre()
										+ "</option>");
								listaDelitosMostrados.put(delito.getCatDelitoDTO().getCatDelitoId(), delito.getCatDelitoDTO().getNombre());
							}
							
						}
						writer.print("</select>" + "]]></cell>");
					}

					// Check box de detenido
					if (involucrado.getEsDetenido() != null
							&& involucrado.getEsDetenido()) {
						writer.print("<cell>"
								+ "<![CDATA[<input type='checkbox' checked='checked' disabled='disabled'>]]>"
								+ "</cell>");
					} else {
						writer.print("<cell>"
								+ "<![CDATA[<input type='checkbox' disabled='disabled'>]]>"
								+ "</cell>");
					}

				} else {
					writer.print("<cell></cell>");
					writer.print("<cell></cell>");
				}
				writer.print("</row>");
			}
		}
		writer.print("</rows>");
		writer.flush();
		writer.close();
	}
	
	
	/**
	 * Metodo que intenta obtener una instancia del enum Roles, intetara obtenerla en el siguiente orden:
	 * 1. En base al rol que se pasa como parametro siempre y cuando sea diferente a ROL_NO_DEFINIDO 
	 * 2. En base al rol padre del rol que se pasa como parametro  
	 * @param idRol
	 * @return
	 * @throws NSJPNegocioException
	 */
	@SuppressWarnings("unused")
	protected Roles obtenEnumRol(long idRol) throws NSJPNegocioException {
		Roles rolEnum = Roles.getByValor(idRol); 
		
		if(rolEnum == Roles.ROL_NO_DEFINIDO){
			RolDTO rolPadre = rolService.consultarRolPadre(idRol);
			if(rolPadre != null && rolPadre.getRolId() > 0){
				rolEnum = Roles.getByValor(rolPadre.getRolId().longValue());
			}
		}
		return rolEnum;
	}
}

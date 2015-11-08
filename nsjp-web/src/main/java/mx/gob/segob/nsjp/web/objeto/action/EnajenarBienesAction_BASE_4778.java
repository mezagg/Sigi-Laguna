/**
 * Nombre del Programa 		: ConsultarBienesPorEnajenarAction.java
 * Autor                     : Adriana S�nchez
 * Compania                  : Draconiantech
 * Proyecto                  : NSJP                    Fecha: 19 Oct 2015
 * Marca de cambio        	: N/A
 * Descripcion General    	: Clase Action para consultar bienes por enajenar
 * Programa Dependiente  	: N/A
 * Programa Subsecuente 		: N/A
 * Cond. de ejecucion        : N/A
 * Dias de ejecucion         : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                     : N/A
 * Compania              	: N/A
 * Proyecto                 	: N/A                                 Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.objeto.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.gob.segob.nsjp.comun.enums.configuracion.Parametros;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.objeto.Objetos;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.objeto.ObjetoDelegate;
import mx.gob.segob.nsjp.delegate.parametro.ParametroDelegate;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.objeto.DocumentoOficialDTO;
import mx.gob.segob.nsjp.dto.objeto.ExplosivoDTO;
import mx.gob.segob.nsjp.dto.objeto.JoyaDTO;
import mx.gob.segob.nsjp.dto.objeto.NumerarioDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoDTO;
import mx.gob.segob.nsjp.dto.objeto.ObraArteDTO;
import mx.gob.segob.nsjp.dto.objeto.SustanciaDTO;
import mx.gob.segob.nsjp.dto.objeto.TelefoniaDTO;
import mx.gob.segob.nsjp.dto.objeto.VegetalDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import org.apache.commons.lang.math.NumberUtils;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase Action para ingresar pertenencias
 * 
 * @version 1.0
 * @author Cuauhtemoc Paredes
 * 
 */
public class EnajenarBienesAction extends GenericAction {

	/* Log de clase */
	private static final Logger log = Logger
			.getLogger(EnajenarBienesAction.class);

	@Autowired
	private ObjetoDelegate objetoDelegate;
	@Autowired
	private ParametroDelegate parametroDelegate; 
	@Autowired
	public DocumentoDelegate documentoDelegate;
	/**
	 * M�todo utilizado para realizar la consulta de bienes por enajenar
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward consultarBienesPorEnajenar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
	
			log.info("ejecutando Action consultar bienes por enajenar");
			SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
			String fecha = request.getParameter("fechaEnajenar");
			Integer diasParaEnajenar=new Integer(parametroDelegate.consultarParametro(Parametros.DIAS_PARA_ENAJENAR).getValor());
			List<ObjetoDTO> objetoDTOs = objetoDelegate.consultarBienesPorEnajenar(DateUtils.obtener(fecha),diasParaEnajenar);
						
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
                       
			PrintWriter writer = response.getWriter();
			
			writer.print("<rows>");
		
			writer.print("<records>" + objetoDTOs.size() + "</records>");
	
			for (ObjetoDTO objetoDTO : objetoDTOs) {
				
				writer.print("<row id='" + objetoDTO.getElementoId()+ "'>");				
                                writer.print("<cell>" + objetoDTO.getTipoObjeto().getNombreEntity()+ "</cell>");
				writer.print("<cell>" + objetoDTO.getDescripcion()+ "</cell>");
                                if(objetoDTO.getTipoObjeto()==Objetos.DOCUMENTO_OFICIAL){
                                    writer.print("<cell>" + ((DocumentoOficialDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.EXPLOSIVO){
                                    writer.print("<cell>" + ((ExplosivoDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.JOYA){
                                    writer.print("<cell>" + ((JoyaDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.NUMERARIO){
                                    writer.print("<cell>" + ((NumerarioDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.OBRA_DE_ARTE){
                                    writer.print("<cell>" + ((ObraArteDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.SUSTANCIA){
                                    writer.print("<cell>" + ((SustanciaDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.EQUIPO_TELEFONICO){
                                    writer.print("<cell>" + ((TelefoniaDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else if(objetoDTO.getTipoObjeto()==Objetos.VEGETAL){
                                    writer.print("<cell>" + ((VegetalDTO)objetoDTO).getCantidad()+ "</cell>");	
                                }else{
                                    writer.print("<cell>-</cell>");	
                                }                                
				writer.print("<cell>" + objetoDTO.getExpedienteDTO().getNumeroExpediente() + "</cell>");
                                Calendar c=Calendar.getInstance();
                                c.setTime(objetoDTO.getFechaCreacionElemento());
                                DateUtils.sumarDias(c, diasParaEnajenar);
                                writer.print("<cell>" + formato.format(c.getTime())+ "</cell>");
                                writer.print("<cell>" + formato.format(objetoDTO.getFechaCreacionElemento())+ "</cell>");                                    
				writer.print("</row>");
			}			
			
			writer.print("</rows>");
			writer.flush();
			writer.close();
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
        
        public ActionForward enajenarBienes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
                        String fecha = request.getParameter("fechaEnajenar");
			log.info("ejecutando Action enajenar");
			
			String ids = request.getParameter("idsBienes");
                        objetoDelegate.enajenarBienes(ids);                       
		    	
		    	DocumentoDTO documento = null;
                        FormaDTO forma =null;
		    	Long documentoId = 0L;
                      
		    	UsuarioDTO usuarioFirmado = getUsuarioFirmado(request); 
	                FuncionarioDTO responsableDocumento = usuarioFirmado.getFuncionario();
				
				ConfInstitucionDTO confInstitucionDTO = null;
				if(usuarioFirmado.getInstitucion() != null) {
					confInstitucionDTO = new ConfInstitucionDTO();
					Long confInstId = usuarioFirmado.getInstitucion().getConfInstitucionId();
					confInstitucionDTO.setConfInstitucionId(confInstId);	
				}
		
		        documento = new DocumentoDTO();
			Long formaId = 980L;
		        forma = documentoDelegate.buscarForma(formaId);
		    	documento.setConfInstitucion(confInstitucionDTO);
		    	String textoParcial="IDS BIENES ENAJENADOS: "+ids;
                        
                         StringTokenizer st=new StringTokenizer(ids, ",");
                         while(st.hasMoreTokens()){
                             ObjetoDTO oDTO=new ObjetoDTO(new Long(st.nextToken()));
                             oDTO.setConsultarArchivoDigital(Boolean.TRUE);
                             ObjetoDTO obDTO=objetoDelegate.obtenerObjeto(oDTO);
                             textoParcial+="\n"+obDTO.getDescripcion();
                         }
                         documento.setTextoParcial(textoParcial);
                             
		    	    documento.setFormaDTO(forma);
			    //documento.setArchivoDigital(archivo);
			    documento.setFechaCreacion(new Date());
			    documento.setNombreDocumento(forma.getNombre());
			    documento.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.OFICIO.getValorId()));
			    documento.setResponsableDocumento(responsableDocumento);
			    //Se setea el area del rol activo.
			    if(usuarioFirmado != null && usuarioFirmado.getAreaActual() != null && usuarioFirmado.getAreaActual().getAreaId() != null){
			    	documento.setJerarquiaOrganizacional(usuarioFirmado.getAreaActual().getAreaId());
			    }
			    		
                           
			    documentoId = documentoDelegate.guardarOficioEnajenacion(documento,ids);   
                            documento.setDocumentoId(documentoId);
                            String docXml=converter.toXML(documento);
			    escribirRespuesta(response,docXml );
                        
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}

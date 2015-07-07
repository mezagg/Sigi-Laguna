/**
 * Nombre del Programa : GeneradorDocumentoAction.java
 * Autor                            : vaguirre
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 19 May 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Describir el objetivo de la clase de manera breve
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.web.documento.action;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.segob.nsjp.comun.constants.ConstantesGenerales;
import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.actividad.ActividadesRS;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusMandamiento;
import mx.gob.segob.nsjp.comun.enums.documento.OperacionDocumento;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.forma.Formas;
import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.pdf.PDFPropiedad;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.actividad.ActividadDelegate;
import mx.gob.segob.nsjp.delegate.audiencia.AudienciaDelegate;
import mx.gob.segob.nsjp.delegate.catalogo.CatUIEspecializadaDelegate;
import mx.gob.segob.nsjp.delegate.detencion.DetencionDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.delegate.mandamiento.AdministrarMandamientoJudicialDelegate;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.delegate.usuario.UsuarioDelegate;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatUIEspecializadaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.detencion.PertenenciaDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.GrupoObjetosExpedienteDTO;
import mx.gob.segob.nsjp.dto.documento.GuardadoDefinitivoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.ObjetoResumenDTO;
import mx.gob.segob.nsjp.dto.domicilio.DomicilioDTO;
import mx.gob.segob.nsjp.dto.elemento.CalidadDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoDTO;
import mx.gob.segob.nsjp.dto.expediente.DelitoPersonaDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ParametrosDocumentoDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.involucrado.AliasInvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.NombreDemograficoDTO;
import mx.gob.segob.nsjp.dto.persona.TelefonoDTO;
import mx.gob.segob.nsjp.dto.programas.ActoBuenaConductaDTO;
import mx.gob.segob.nsjp.dto.programas.AsignacionProgramaDTO;
import mx.gob.segob.nsjp.dto.programas.PeriodoAcumulacionPuntosDTO;
import mx.gob.segob.nsjp.dto.reinsercion.InventarioPertenenciaDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.ReporteBaseAction;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Action encargado de recibir y procesar las solicitudes para la emisi?n de un documento
 * basado en una Forma (plantilla) de la base de datos y los datos de un expediente
 * 
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
public class GeneradorDocumentoAction extends ReporteBaseAction {
    
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	private SolicitudDelegate solicituDelegate;
	@Autowired
	private ActividadDelegate actividadDelegate;
	@Autowired
	private NotificacionDelegate notificacionDelegate;
	
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	@Autowired
	private UsuarioDelegate usuarioDelegate;
	@Autowired
	private AudienciaDelegate audienciaDelegate;
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	@Autowired
	private CatUIEspecializadaDelegate catUIEspecializadaDelegate;
	@Autowired
	private ReinsercionDelegate reinsercionDelegate;
	@Autowired
	private AsignacionProgramaDelegate asignacionProgramaDelegate;
	@Autowired
	private DetencionDelegate detencionDelegate;
	@Autowired
	private AdministrarMandamientoJudicialDelegate administrarMandamientoJudicialDelegate;
	
	public static final String PARAM_TEXTO_DOCUMENTO = "texto";
	public static final String PARAM_GUARDAR_PARCIAL = "parcial";
	public static final String PARAM_FORMA_DOCUMENTO = "formaId";
	public static final String PARAM_IDENT_DOCUMENTO = "documentoId";
	public static final String PARAMT_TIPO_DOCUMENTO = "tipoDocumento";
	public static final String PARAMT_TIPO_OPERACION = "tipoOperacion";
	private static final String PARAM_ID_SENTENCIA = "sentenciaId";
	public static final Long   TIPO_DOCUMENTO_SOLICITUD = TipoDocumento.SOLICITUD.getValorId();
	public static final String TAMANIO_PAPEL = "seleccionTamanioPapel";
	
	private static final Logger logger = Logger
            .getLogger(GeneradorDocumentoAction.class);
    /**
     * Crea o actualiza un documento para el expediente que se est? trabajando actualmente
     * 
     * Parametros:
     * 
     * formaId (obligatorio) -opcional si se manda el tipo de documento- El identificador del formato a emitir (acta, denuncia, acuse de recibo, etc)
     * 
     * documentoId (opcional) - Indica un cierto ID de documento con el que se debe de trabajar en lugar de
     * crear uno nuevo. En este caso el documento se actualizar?a en lugar de crearse 
     * 
     * tipoOperacion - Identificador del tipo de operaci?n a realizar una vez que sea impreso el documento
     * <code>OperacionDocumento.ACTUALIZAR_ESTADO_SOLICITUD</code> Actualizar el estado de la solicitud
     * <code>OperacionDocumento.REGISTRAR_ORDEN_DETENCION</code> Registra un solicitud de Orden de Detencion
     * <code>OperacionDocumento.ASOCIAR_DOCUMENTO_A_RESOLUTIVO</code> Asocia el Documento a un resolutivo
     * posterior al emitir un documento 
     * Al utilizar este tipo de operaci?n se debe enviar tambi?n el par?metro de estatusSolicitud indicando
     * el estado de solicitud destino
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarDocumento(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	Long nuevaActividad = NumberUtils.toLong(request.getParameter("nuevaActividad"), 0L);
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	String parametro = request.getParameter(PARAM_IDENT_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long documentoId = 0L;
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);

		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	logger.debug(PARAM_IDENT_DOCUMENTO + " :: " + parametro);
		    	
		    	String audienciaId = request.getParameter("audienciaId");
		    	String solicitudPeritoId=request.getParameter("solicitudPeritoId");
		    	
		    	UsuarioDTO usuarioFirmado = getUsuarioFirmado(request); 
				FuncionarioDTO responsableDocumento = usuarioFirmado.getFuncionario();
				
				ConfInstitucionDTO confInstitucionDTO = null;
				if(usuarioFirmado.getInstitucion() != null) {
					confInstitucionDTO = new ConfInstitucionDTO();
					Long confInstId = usuarioFirmado.getInstitucion().getConfInstitucionId();
					confInstitucionDTO.setConfInstitucionId(confInstId);	
				}
				
		    	if(StringUtils.isNotBlank(parametro) && NumberUtils.toLong(parametro,0L) > 0L){
//		    		Se est? editando un documento en espec?fico
		    		documentoId = NumberUtils.toLong(parametro);
		    		documento = documentoDelegate.cargarDocumentoPorId(documentoId);
		    		forma = documento.getFormaDTO();
		    	}else{
//		    		Si es un documento nuevo se obtiene el tipo de forma que se est? editando
		            documento = new DocumentoDTO();
			    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
		            forma = documentoDelegate.buscarForma(formaId);
		    	}
		    	
		    	if(forma == null){
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		    	}
		    	documento.setConfInstitucion(confInstitucionDTO);
		    	
		    	//Se asocia El idientificador de la solicitudPerito en el documento
		    	if (solicitudPeritoId != null){
		    		documento.setIdSolicitudPericial(Long.parseLong(solicitudPeritoId));		    		
		    	}
		    	
		    	ArchivoDigitalDTO archivo = null;
		    	//FIXME La entidad Forma no contiene el campo TipoDocumento. 
//			    Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), forma.getTipoDocumentoDTO().getIdCampo());
		    	Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMENTO_SOLICITUD);
			    boolean parcial = StringUtils.isNotBlank(request.getParameter(PARAM_GUARDAR_PARCIAL));
			    String numeroExpediente = request.getParameter("numeroUnicoExpediente").trim();
			    ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, numeroExpediente);
			    if(expTrabajo == null && numeroExpediente != null)
			    	expTrabajo = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
		    	String numeroFolio = request.getParameter("iNumeroOficio");
		    	ByteArrayOutputStream archivoPDF = null;
		    	logger.debug("Numero Folio :: " + request.getParameter("iNumeroOficio"));
		    	logger.debug("parcial :: " + parcial);
			    if(!parcial){
			    	archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
				    archivo = new ArchivoDigitalDTO();
				    archivo.setContenido(archivoPDF.toByteArray());
				    archivo.setNombreArchivo(forma.getNombre());
				    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
		    	}else{
		    	    documento.setTextoParcial(textoPdf);
		    	}
			    
			    documento.setFormaDTO(forma);
			    documento.setArchivoDigital(archivo);
			    documento.setFechaCreacion(new Date());
			    documento.setNombreDocumento(forma.getNombre());
			    documento.setFolioDocumento(numeroFolio);
			    documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
			    documento.setEsGuardadoParcial(parcial);
			    documento.setResponsableDocumento(responsableDocumento);
			    //Se setea el area del rol activo.
			    if(usuarioFirmado != null && usuarioFirmado.getAreaActual() != null && usuarioFirmado.getAreaActual().getAreaId() != null){
			    	documento.setJerarquiaOrganizacional(usuarioFirmado.getAreaActual().getAreaId());
			    }
			    			    
			    if(nuevaActividad!=null && nuevaActividad!=0L){
			    	documentoId = documentoDelegate.guardarDocumento(documento,expTrabajo,nuevaActividad,null);
			    } else {
			    	documentoId = documentoDelegate.guardarDocumento(documento,expTrabajo,null,null);	
			    }

			    
			    if ((nuevaActividad == null || nuevaActividad <= 0L)
			    		&& (documentoId != null && documentoId > 0L)) {
			    	
			    		nuevaActividad = actividadDelegate.consultarActividadePorDocumentoId(documentoId);
			    }
			    if(nuevaActividad!=null && nuevaActividad!=0L){
			    	if (nuevaActividad.equals(Actividades.GENERAR_ACUSE_RECIBO_PERTENENCIAS.getValorId())
			    			||nuevaActividad.equals(Actividades.ENTREGAR_VALORES_Y_EFECTOS_DEPOSITADOS.getValorId())){
			    		actualizarInventarioPertenencias(request, 
			    				parcial, 
			    				documentoId, 
			    				Actividades.getByValor(nuevaActividad),
			    				expTrabajo);
			    	}else if (nuevaActividad.equals(Actividades.GENERAR_RESUMEN_PUNTOS_RECOMPENSAS.getValorId())){
			    		actualizarResumenPeriodosAcumulacion(request, parcial);
			    	}
			    }
			    
			    
			    
			    
				try {
					
					Long audienciaIdRel = NumberUtils.toLong(audienciaId, 0L);
					
					if(audienciaIdRel > 0L){
						AudienciaDTO audienciaRel = new AudienciaDTO();
						audienciaRel.setId(audienciaIdRel);
						
						DocumentoDTO documentoRel = new DocumentoDTO();
						documentoRel.setDocumentoId(documentoId);
						
						audienciaDelegate.asociarDocumentoAAudiencia(audienciaRel, documentoRel);
					}
				} catch (NSJPNegocioException ne) {
					if (ne.getCodigo().equals(CodigoError.DOCUMENTO_YA_ASOCIADO)) {
						logger.debug("DOCUMENTO YA ASOCIADO EN AUDIENCIA");
					}
				}

		    	if(!parcial){
		    		 request.getSession().setAttribute("documentoId",documentoId);
			    	 escribirReporte(response, archivoPDF, forma.getNombre());
			    	 logger.info("/**** ID Retornado :: "+documentoId);			    	 
			    	 if(StringUtils.isNotBlank(request.getParameter(PARAMT_TIPO_OPERACION))){
			    		 documento.setDocumentoId(documentoId);
			    		 ejecutarAccion(request, documento);
			    	 }
			    }else{
		    		escribirRespuesta(response, converter.toXML(documentoId+","+numeroFolio));
			    }
		    }catch (NSJPNegocioException e) {
				logger.error(e.getMessage(), e);
			}
		    
        return null;
    }
    
    /**
     * Genera un PDF, a partir del contenido del Editor de Texto, y de acuerdo a la forma de documento.
     * Requiere de los siguientes parametros del request:
     * -texto: texto del editor Parcial.
     * -formaId: identificador de la forma
     * -documentoId: identificador del documento (Opcional)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarVistaPreliminar(ActionMapping mapping,            
    		ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	logger.debug("*********EJECUTANDO ACTION generarVistaPrevia************");
    	logger.debug("VERIFICANDO PARAMETROS.....");

    	FormaDTO forma =null;
    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
    	String formaIdString = request.getParameter(PARAM_FORMA_DOCUMENTO);
    	String documentoIdString = request.getParameter(PARAM_IDENT_DOCUMENTO);
    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
    	
    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
    	logger.debug(PARAM_IDENT_DOCUMENTO + " :: " + documentoIdString);
    	logger.debug(PARAM_FORMA_DOCUMENTO + " :: " + formaIdString);
    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
    	
    	try{
	    	//Valores para la forma de acuerdo al tipo de documento
	    	
	    	if(StringUtils.isNotBlank(documentoIdString)){
	    		Long documentoId = NumberUtils.toLong(documentoIdString);
	    		DocumentoDTO documento = documentoDelegate.cargarDocumentoPorId(documentoId);
	    		forma = documento.getFormaDTO();
	    	}else{
		    	Long formaId = NumberUtils.toLong(formaIdString, 1L);
	            forma = documentoDelegate.buscarForma(formaId);
	    	}
	    	if(forma == null){
	    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
	    	}
	    	ByteArrayOutputStream archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
	    	escribirReporte(response, archivoPDF, forma.getNombre());
    	} catch (NSJPNegocioException e) {
    		logger.error(e.getMessage(), e);
		}
    	return null;
    }
    
    @SuppressWarnings("unchecked")
	public ActionForward generarDocumentoNotificacion(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	logger.debug("*********EJECUTANDO ACTION generarDocumentoNotificacion************");
		    	logger.debug("VERIFICANDO PARAMETROS.....");
		    	    	
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	logger.debug("textoPdf:: " + textoPdf);
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	String parametro = request.getParameter(PARAM_IDENT_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);

		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	logger.debug(PARAM_IDENT_DOCUMENTO+" :: "+parametro);
		    	//para verificar parametros
		    	logger.debug(PARAM_FORMA_DOCUMENTO+"::" +request.getParameter(PARAM_FORMA_DOCUMENTO));
		    	logger.debug(PARAMT_TIPO_DOCUMENTO+"::" +request.getParameter(PARAMT_TIPO_DOCUMENTO));
		    	logger.debug("NUMERO UNICO EXPEDIENTE::" +request.getParameter("numeroUnicoExpediente"));
		    	logger.debug("NUMEROS EXPEDIENTES::" +request.getParameter("numerosVariosExpedientes"));
		    	logger.debug("VARIOS NUMEROS?::" +request.getParameter("variosExpedientes"));
		    	
		    	if(StringUtils.isNotBlank(parametro)){
//		    		Se est? editando un documento en espec?fico
		    		Long documentoId = NumberUtils.toLong(parametro);
		    		documento = documentoDelegate.cargarDocumentoPorId(documentoId);
		    		forma = documento.getFormaDTO();
		    	}else{
//		    		Si es un documento nuevo se obtiene el tipo de forma que se est? editando
		            documento = new DocumentoDTO();
			    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
		            forma = documentoDelegate.buscarForma(formaId);
		    	}
		    	
		    	if(forma == null){
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		    	}
		    	
		    	ArchivoDigitalDTO archivo = null;
			    Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMENTO_SOLICITUD);
			    logger.debug("tipoDocumento:: " + tipoDocumento);
			    boolean parcial = StringUtils.isNotBlank(request.getParameter(PARAM_GUARDAR_PARCIAL));
			    
			    ExpedienteDTO expTrabajo = null;
			    List<ExpedienteDTO> listExpedientes = null;
			    
			    
			    boolean variosExpedientes = StringUtils.isNotBlank(request.getParameter("variosExpedientes"));
			    if(variosExpedientes){
			    	
			    	UsuarioDTO usuarioFirmado =  getUsuarioFirmado(request);
			    	
			    	List<Long> listaIdsNumsExpVis = new ArrayList<Long>();
			    	listaIdsNumsExpVis.addAll( (List<Long>) request.getSession().getAttribute("KEY_SESSION_IDS_NUM_EXP_NEW_VIS"));
			    	request.getSession().removeAttribute("KEY_SESSION_IDS_NUM_EXP_NEW_VIS");
			    	
			    	if(listaIdsNumsExpVis == null ||( listaIdsNumsExpVis!= null && listaIdsNumsExpVis.isEmpty())){
			    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			    	}
			    	
			    	listExpedientes=new ArrayList<ExpedienteDTO>();
			    	for (Long numExpId:listaIdsNumsExpVis) {			    		
			    			ExpedienteDTO expedienteDTO=new ExpedienteDTO();
			    			expedienteDTO.setNumeroExpedienteId( numExpId);
			    			expedienteDTO.setUsuario(usuarioFirmado);
			    			listExpedientes.add(expedienteDTO);
			    	}
			    }else{
			    	expTrabajo = super.getExpedienteTrabajo(request, request.getParameter("numeroUnicoExpediente"));
			    	
			    	if(expTrabajo != null){
		            	expTrabajo.setUsuario(getUsuarioFirmado(request));
		        	}
			    }
			    
		    	
		    	ByteArrayOutputStream archivoPDF = null;
		    	logger.debug("parcial :: " + parcial);
			    if(!parcial){
		    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
				    archivo = new ArchivoDigitalDTO();
				    archivo.setContenido(archivoPDF.toByteArray());
				    archivo.setNombreArchivo(forma.getNombre());
				    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
				    documento.setEsGuardadoParcial(false);//Dado que es definitivo
		    	}else{
		    	    documento.setTextoParcial(textoPdf);
		    	}
			    
			    documento.setFormaDTO(forma);
			    documento.setArchivoDigital(archivo);
			    documento.setFechaCreacion(new Date());
			    documento.setNombreDocumento(forma.getNombre());
			    documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
			    Long documentoId = null;
			    if(variosExpedientes){
			    	for(ExpedienteDTO exp:listExpedientes){
			    		documentoId = notificacionDelegate.guardarYEnviarNotificacionAMismaInstitucion(exp,documento);			    		
			    	}
			    }else{
			    	documentoId = notificacionDelegate.guardarYEnviarNotificacionAMismaInstitucion(expTrabajo,documento);			    	
			    }
			    
		    	if(!parcial){
			    	 escribirReporte(response, archivoPDF, forma.getNombre());
			    	 if(StringUtils.isNotBlank(request.getParameter(PARAMT_TIPO_OPERACION))){
			    		 documento.setDocumentoId(documentoId);
			    		 ejecutarAccion(request, documento);
			    	 }
			    }else{
			    	escribirRespuesta(response, converter.toXML(documentoId));
			    }
		    	return mapping.findForward("success");
		    }catch (NSJPNegocioException e) {
				logger.error(e.getMessage(), e);
				return mapping.findForward("fail");
			}
    }
    
    
    /**
     * 
     * @param request
     * @param documento
     * @throws NSJPNegocioException
     */
    private void ejecutarAccion( HttpServletRequest request, DocumentoDTO documento) throws NSJPNegocioException{
		OperacionDocumento op = OperacionDocumento.getByValor(NumberUtils.toLong(request.getParameter(PARAMT_TIPO_OPERACION)));
		logger.debug(PARAMT_TIPO_OPERACION + " :: " + op);
		logger.info("Inicia - ejecutarAccion(...)");
		switch(op){
			case ACTUALIZAR_ESTADO_SOLICITUD:
				 if(request.getParameter("estatusSolicitud") != null){
					 SolicitudDTO solicitud = new SolicitudDTO(documento.getDocumentoId());
					 EstatusSolicitud estatus = EstatusSolicitud.getByValor(NumberUtils.toLong( request.getParameter("estatusSolicitud")));
					 solicituDelegate.actualizaEstatusSolicitud(solicitud, estatus);
				 }else{
			    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				 }
				 break;
			case ASOCIAR_DOCUMENTO_A_RESOLUTIVO:
			    Long resolutivoId = null;
			    ResolutivoDTO resolutivo = new ResolutivoDTO();
			    if(request.getParameter("idResolutivo") != null){
					resolutivoId = NumberUtils.toLong(request.getParameter("idResolutivo"), 2L);
					resolutivo.setResolutivoId(resolutivoId);
			    	documentoDelegate.asociarDocuementoA(resolutivo, documento);

			    }else{
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
			    }
			    break;
			case REGISTRAR_ORDEN_DETENCION:
				SolicitudDTO ordenDetencion = (SolicitudDTO) request.getSession().getAttribute("ordenDetencion");
				if(ordenDetencion != null){
					solicituDelegate.registrarSolicitudOrdenDeDetencion(ordenDetencion  , documento.getDocumentoId());
					request.getSession().removeAttribute("ordenDetencion");
				}else{
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
				}
				break;
				
		}
    }
    
    /**
     * Realiza la pre-carga de un documento para el expediente de trabajo.
     * Si no existe ning?n documento del tipo de forma seleccionado entonces prepara
     * un documento nuevo (sin guardarlo en BD), obtiene la plantilla de la forma
     * y la llena con los datos del expediente para presentarla
     * en el CKEditor. Si ya existe alg?n documento con texto parcial para el expediente
     * de trabajo carga la informaci?n que el usuario hab?a guardado parcialmente
     * y la presenta en el CKEditor
     * 
     * Parametros:
     * 
     * formaId (obligatorio-opcional si se env?a el tipo de documento)- El identificador del formato a emitir (acta, denuncia, acuse de recibo, etc)
     * 
     * documentoId (opcional) - Indica un cierto ID de documento con el que se debe de trabajar en lugar de
     * crear uno nuevo. En este caso el documento se actualizar?a en lugar de crearse 
     * 
     * tipoOperacion - Identificador del tipo de operaci?n a realizar una vez que sea impreso el documento
     * <code>OperacionDocumento.ACTUALIZAR_ESTADO_SOLICITUD</code> Actualizar el estado de la solicitud
     * posterior al emitir un documento 
     * Al utilizar este tipo de operaci?n se debe enviar tambi?n el par?metro de estatusSolicitud indicando
     * el estado de solicitud destino
     * 
     * esconderArbol - Enviar este par?metro con uno para no mostrar el ?rbol de datos de expediente, no mandarlo
     * para mostrar el ?rbol
     * 
     * numeroUnicoExpediente (obligatorio) - Identificador del n?mero ?nico de expediente con el que se est? trabajando
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward cargarDocumento(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    	
    	try {
    		UsuarioDTO usuario = getUsuarioFirmado(request);
    		String numeroExpediente = (request.getParameter("numeroUnicoExpediente") != null ? request.getParameter("numeroUnicoExpediente").trim(): null);
    		String mandaFormaEnConsulta = request.getParameter("mandaFormaEnConsulta");
    		logger.debug("/**** NumeroUnico :::: "+numeroExpediente+"**");
		    ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, numeroExpediente);
		    if(expTrabajo == null && numeroExpediente != null){
		    	expTrabajo = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
		    }
	    	if(expTrabajo != null){
        		expTrabajo.setArea(getUsuarioFirmado(request).getAreaActual());
            	expTrabajo.setUsuario(getUsuarioFirmado(request));
            	expTrabajo.setObjetosSolicitados(true);
        	}
	    	
    		Map<String,Object> parametrosExtra = new HashMap<String,Object> ();
    		
    		for(Object llave:request.getParameterMap().keySet()){
    			parametrosExtra.put(llave.toString(), request.getParameter(llave.toString()));
    		}
    		
    		Long formaId = NumberUtils.toLong(request.getParameter("formaId"), 1L);
    		Long documentoId =  StringUtils.isNotBlank(request.getParameter("documentoId"))?NumberUtils.toLong(request.getParameter("documentoId")):null;

    		// si se recibe la 
    		
    		Long sentenciaId = NumberUtils.toLong(request.getParameter(PARAM_ID_SENTENCIA), 0L);
    		if(sentenciaId > 0L) {
    			Map<String,Object> parametrosSentencia = new HashMap<String,Object>();
    			
    			SentenciaDTO sentenciaDTO = new SentenciaDTO();
    			sentenciaDTO.setSentenciaId(sentenciaId);
    			Long asignacionProgramaId = NumberUtils.toLong(request.getParameter("asignacionProgramaId"), 0L);	
    			if(asignacionProgramaId.compareTo(0L) != 0){
    				List<AsignacionProgramaDTO> lstAsignacionProgramaDTO = new ArrayList<AsignacionProgramaDTO>();
    				AsignacionProgramaDTO asignacionProgramaDTO = new AsignacionProgramaDTO();
    				asignacionProgramaDTO.setAsignacionProgramaId(asignacionProgramaId);
    				lstAsignacionProgramaDTO.add(asignacionProgramaDTO);
    				sentenciaDTO.setAsignacionProgramas(lstAsignacionProgramaDTO);
    			}
    			
    			parametrosExtra.putAll( documentoDelegate.getParametrosExtraSentecia(sentenciaDTO, parametrosSentencia));
    			
    			if (documentoId == null){
    				// se recibe la actividad para obtener la ultima y asi el ultima documentoId
    				Long tipoActividadId = NumberUtils.toLong(request.getParameter("actividadId"));
    				if (tipoActividadId > 0L 
    						&& ActividadesRS.getByValor(tipoActividadId) 
    									== ActividadesRS.ELABORAR_INFORME_FINAL_DE_REINSERCION_SOCIAL_DEL_SENTENCIADO) {
    					
    					ActividadDTO actividadDTO = new ActividadDTO();
    					actividadDTO.setTipoActividad(Actividades.getByValor(tipoActividadId));
    					
    					DocumentoDTO documentoDTO = new DocumentoDTO();
    					documentoDTO.setActividadDTO(actividadDTO);
    					documentoDTO.setExpedienteDTO(expTrabajo);

    					documentoDTO = documentoDelegate.consultarDocumentoFiltro(documentoDTO);
    					
    					if(documentoDTO != null 
    							&& documentoDTO.getDocumentoId() != null
    							&& documentoDTO.getDocumentoId() > 0L) {
    						documentoId = documentoDTO.getDocumentoId();
    					}
    				}
    			}
    		}
    		
    		DocumentoDTO documentoCargado = null;
    		logger.debug("Intentando carga de documento: forma: " + formaId );
    		logger.debug("Intentando carga de documento: documento: " + documentoId );
    		logger.debug("Intentando carga de documento: numeroExpediente: " + (request.getParameter("numeroUnicoExpediente") != null ? request.getParameter("numeroUnicoExpediente").trim(): null));
    		logger.debug("Expediente de trabajo: " + expTrabajo);
    		//si documentioId es nulo se trabaja con el expediente actual de sesion
    		if(documentoId == null){
				logger.debug("entra formas");
				// Buscar la forma en las definidas por el enum
				Formas forma = Formas.getByValor(formaId);
    			
				logger.debug("forma_antes_del_switch:: " + forma);
				if (forma == null) {
					// Si no esta definida, se busca en BD mediante el parametro
					// de 'mandaFormaEnConsulta'
					logger.info("forma_no_en_enum.... formaID:: " + formaId);
					documentoCargado = documentoDelegate
							.cargarDocumentoPorExpedienteYForma(expTrabajo,
									new FormaDTO(formaId), parametrosExtra,
									mandaFormaEnConsulta);
				}
    			else
    			{
	    			switch(forma){
	    				case AUDIENCIA:
	    					logger.debug("entra switch audiencia");
	    					Long auidenciaId = NumberUtils.toLong(request.getParameter("idAudiencia"));
	    					logger.debug("LLega Audiencia id: " + auidenciaId );
	    					
	    					AudienciaDTO audiencia = new AudienciaDTO();
	    					audiencia.setId(auidenciaId);
	    					documentoCargado = documentoDelegate.cargarDocumentoPorAudienciaYForma(audiencia, new FormaDTO(formaId));
	    					break;
	    				case DUPLICIDAD_TERMINO_CONSTITUCIONAL:
	    					logger.debug("entra switch duplicidad termino");
	    					auidenciaId = NumberUtils.toLong(request.getParameter("idAudiencia"));
	    					audiencia = new AudienciaDTO();
	    					audiencia.setId(auidenciaId);
	    					Long resolutivoId = NumberUtils.toLong(request.getParameter("idResolutivo"));
	    					logger.debug("LLega Resolutivo: " + resolutivoId );
	    					logger.debug("LLega Audiencia: " + auidenciaId );
	    					ResolutivoDTO resolutivo = new ResolutivoDTO();
	    					resolutivo.setResolutivoId(resolutivoId);
	    					resolutivo.setAudiencia(audiencia);
	    					documentoCargado = documentoDelegate.cargarDocumentoPorResolutivoYForma(resolutivo, new FormaDTO(formaId));
	    					break;
	    				default:
	    					expTrabajo.setDatosPlatillaSolicitados(true);
//	    					expTrabajo.setImagenesAsociadasAElementos(true);
	    					documentoCargado = documentoDelegate.cargarDocumentoPorExpedienteYForma(expTrabajo, new FormaDTO(formaId),parametrosExtra,mandaFormaEnConsulta);
	    					break;
	    			}
    			}
    		}else{
    			documentoCargado = documentoDelegate.cargarDocumentoPorId(documentoId,expTrabajo);
    		}
			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML);
			PrintWriter pw = response.getWriter();
			logger.info(ConstantesGenerales.BODY_TAG_APERTURA+ConstantesGenerales.C_DATA_OPEN+
					(documentoCargado.getTextoParcial()!=null?documentoCargado.getTextoParcial():StringUtils.EMPTY)+
					ConstantesGenerales.C_DATA_CLOSE+ConstantesGenerales.BODY_TAG_CIERRE);
			pw.print(
					ConstantesGenerales.BODY_TAG_APERTURA+
						// Recuperacio_n del cuerpo del documento
						ConstantesGenerales.CUERPO_DOCUMENTO_TAG_APERTURA+
							ConstantesGenerales.C_DATA_OPEN+
								(documentoCargado.getTextoParcial()!=null?documentoCargado.getTextoParcial():StringUtils.EMPTY)+
							ConstantesGenerales.C_DATA_CLOSE+
						ConstantesGenerales.CUERPO_DOCUMENTO_TAG_CIERRE+
						// Recuperacio_n del nu_mero de folio
						ConstantesGenerales.NUMERO_FOLIO_TAG_APERTURA+
							(documentoCargado.getFolioDocumento()!=null?documentoCargado.getFolioDocumento():StringUtils.EMPTY)+
						ConstantesGenerales.NUMERO_FOLIO_TAG_CIERRE+
						"<documentoId>" +(documentoCargado.getDocumentoId()!=null?documentoCargado.getDocumentoId():StringUtils.EMPTY)+"</documentoId>" +  
					ConstantesGenerales.BODY_TAG_CIERRE
					);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			logger.error("Exception forma :"+e.getMessage(), e);
		}
    	return null;
    	
    }
    
    /**
     * Carga un resumen de expediente para llenar un ?rbol de contenido
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward cargarArbolExpediente(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {   
    	logger.debug("cargarArbolExpediente");
    	String loNumeroExpediente = (request.getParameter("numeroUnicoExpediente") != null ? 
    			request.getParameter("numeroUnicoExpediente").trim() : null);
    	ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, loNumeroExpediente);	
    	if(expTrabajo != null){
    		expTrabajo.setArea(new AreaDTO(Areas.ATENCION_TEMPRANA_PG_PENAL));
    		expTrabajo.setInvolucradosSolicitados(true);
    		expTrabajo.setObjetosSolicitados(true);
    		expTrabajo.setUsuario(getUsuarioFirmado(request));
    	}
    	String idnumero=(request.getParameter("idNumeroUnicoExpediente") != null ?
    			request.getParameter("idNumeroUnicoExpediente").trim(): null);
    	if(idnumero!=null &&!idnumero.equals("")){
    		expTrabajo.setNumeroExpedienteId(Long.parseLong(idnumero));
    	}
    	try{
    		ParametrosDocumentoDTO resumen = documentoDelegate.cargarResumenExpedienteParaDocumento(expTrabajo);
    		
    		//Consultar el Catalogo de Unidades de Investigacion 
    		List<CatUIEspecializadaDTO> listaCatalogoUIE = catUIEspecializadaDelegate.consultarUnidadesIEspecializadas();
			
    		if(resumen != null){
    			logger.debug("cargarArbolExpediente - resumen :::: "+resumen);
    			logger.debug("cargarArbolExpediente - listaCatalogoUIE :::: "+listaCatalogoUIE);
    			
    			//Para el catalogo de unidades de investigacion
    			converter.alias("listaCatalogo", java.util.List.class);
    			converter.alias("CatUIEspecializadaDTO", CatUIEspecializadaDTO.class);
    			
    			String xml ="<DatosArbol>"+ escribirXMLArbolExpediente(resumen); 
    			xml += converter.toXML(listaCatalogoUIE) + "</DatosArbol>";  
    			
    			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML);
    			PrintWriter pw = response.getWriter();
    			pw.print( xml );
    			pw.flush();
    			pw.close();
    		}
    		
    	}catch (Exception e) {
    		logger.error(e.getMessage(), e);
		}
    	
    	
    	return null;
    }
    
    public ActionForward cargarUIE(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {   
    	logger.debug("cargarUIE");
    		try{
    		
    		//Consultar el Catalogo de Unidades de Investigacion
    		List<CatUIEspecializadaDTO> listaCatalogoUIE = catUIEspecializadaDelegate.consultarUnidadesIEspecializadas();
			
    			//Para el catalogo de unidades de investigacion
    			converter.alias("listaCatalogo", java.util.List.class);
    			converter.alias("CatUIEspecializadaDTO", CatUIEspecializadaDTO.class);
    			
    			String xml =converter.toXML(listaCatalogoUIE); 
    			response.setContentType(ConstantesGenerales.CONTENT_TYPE_XML);
    			PrintWriter pw = response.getWriter();
    			pw.print( xml );
    			pw.flush();
    			pw.close();
    		
    	}catch (Exception e) {
    		logger.error(e.getMessage(), e);
		}
    	
    	
    	return null;
    }
    
    /**
     * Convierte la informaci?n necesaria de un expediente para poder trabajarlo en el documento
     * en forma de ?rbol de informaci?n
     * 
     * 
     * @param resumen
     * @return
     */
	private String escribirXMLArbolExpediente(ParametrosDocumentoDTO resumen) {
		converter.alias("expedienteResumenDTO", ParametrosDocumentoDTO.class);
		converter.alias("involucradoDTO", InvolucradoDTO.class);
		converter.alias("calidadDTO", CalidadDTO.class);
		converter.alias("domicilioDTO", DomicilioDTO.class);
		converter.alias("nombreDemograficoDTO", NombreDemograficoDTO.class);
		converter.alias("telefonoDTO", TelefonoDTO.class);
		converter.alias("correoElectronicoDTO", CorreoElectronicoDTO.class);
		converter.alias("aliasInvolucradoDTO", AliasInvolucradoDTO.class);
		converter.alias("grupoObjetosExpedienteDTO", GrupoObjetosExpedienteDTO.class);
		converter.alias("objetoResumenDTO", ObjetoResumenDTO.class);
		
		return converter.toXML(resumen);
	}
	
	/**
     * Crea o actualiza un documento producto de una transcripci?n
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarDocumentoTranscripcion(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
		    	
		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	documento = new DocumentoDTO();
		    	//se obtiene el tipo de forma que se est? editando
			    Long formaId = NumberUtils.toLong(request.getParameter("formaId"), 1L);
			    Long solicitudId = NumberUtils.toLong(request.getParameter("solicitudId"), 1L);
		    	
		        forma = documentoDelegate.buscarForma(formaId);
		    	 if(forma != null){
		            	//se copian valores del tipo de forma al documento
		            	String nombreArchivo = forma.getNombre();
					    String nombreDocumento = forma.getNombre();
					    ValorDTO tipoDocumento = new ValorDTO(TipoDocumento.SOLICITUD.getValorId());
					    Double version = 1.2;
					    boolean parcial = StringUtils.isNotBlank(request.getParameter("parcial"));
				    	ArchivoDigitalDTO archivo = null;
				    	//ExpedienteDTO expTrabajo =   super.getExpedienteTrabajo(); //cambiar a expediente de la audiencia
				    	ByteArrayOutputStream archivoPDF = null;
				    	if(!parcial){
				    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
						    //Crear el archivo digital
						    archivo = new ArchivoDigitalDTO();
						    archivo.setContenido(archivoPDF.toByteArray());
						    archivo.setNombreArchivo(nombreArchivo);
						    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
				    	}
					    documento.setFechaCreacion(new Date());
					    documento.setFormaDTO(forma);
					    documento.setTipoDocumentoDTO(tipoDocumento);
					    documento.setNombreDocumento(nombreDocumento);
					    documento.setVersion(version);
					    documento.setTextoParcial(parcial?textoPdf:null);
					    //documentoDelegate.guardarDocumentoTranscripcion(documento,expTrabajo);
					    documentoDelegate.guardarDocumentoTranscripcion(documento, solicitudId);
					    if(!parcial){
					    	 escribirReporte(response, archivoPDF, nombreArchivo);
					    }
		            }
		    }catch (NSJPNegocioException e) {
		        logger.error(e.getMessage(), e);
			}
		    
        return null;
    }
    
	/**
     * Crea o actualiza un documento producto de una transcripci?n
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarActaAudiencia(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	// se obtiene el texto del editor
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
		    	
		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	documento = new DocumentoDTO();
		    	//se obtiene el tipo de forma que se est? editando
			    Long formaId = NumberUtils.toLong(request.getParameter("formaId"), 1L);
		        forma = documentoDelegate.buscarForma(formaId);
		    	 if(forma != null){
		            	//se copian valores del tipo de forma al documento
		            	String nombreArchivo = forma.getNombre();
					    String nombreDocumento = forma.getNombre();
					    ValorDTO tipoDocumento = new ValorDTO(TipoDocumento.SOLICITUD.getValorId());
					    Double version = 1.2;
					    boolean parcial = StringUtils.isNotBlank(request.getParameter("parcial"));
				    	ArchivoDigitalDTO archivo = null;
				    	ExpedienteDTO expTrabajo =   super.getExpedienteTrabajo(request, request.getParameter("numeroUnicoExpediente"));
				    	ByteArrayOutputStream archivoPDF = null;
				    	if(!parcial){
				    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
						    //Crear el archivo digital
						    archivo = new ArchivoDigitalDTO();
						    archivo.setContenido(archivoPDF.toByteArray());
						    archivo.setNombreArchivo(nombreArchivo);
						    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
				    	}
					    documento.setFechaCreacion(new Date());
					    documento.setFormaDTO(forma);
					    documento.setTipoDocumentoDTO(tipoDocumento);
					    documento.setNombreDocumento(nombreDocumento);
					    documento.setVersion(version);
					    documento.setTextoParcial(parcial?textoPdf:null);
					    documentoDelegate.guardarActaAudiencia(documento,expTrabajo);
					    if(!parcial){
					    	 escribirReporte(response, archivoPDF, nombreArchivo);
					    }
		            }
		    }catch (NSJPNegocioException e) {
		        logger.error(e.getMessage(), e);
			}
		    
        return null;
    }
    
    /**
     * Realiza la carga y guardado del documento sin pasar por la pantalla de edici?n 
     * del documento.
     * 
     * Se trabaja con los datos del expediente de trabajo
     * 
     * Parametros:
     * 
     * formaId (obligatorio)- El identificador del formato a emitir (acta, denuncia, acuse de recibo, etc)
     * 
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward generarDocumentoDirecto(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {    	
    	try {
    		
    		String numero=request.getParameter("numeroUnicoExpediente");
    		logger.info("GenerarDocumento numeroUnicoExpediente:: "+request.getParameter("numeroUnicoExpediente"));
			
    		ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request,request.getParameter("numeroUnicoExpediente")!=null?
    				request.getParameter("numeroUnicoExpediente").trim():null);
    		if(expTrabajo==null){
    			HttpSession ses = request.getSession();
    			expTrabajo=(ExpedienteDTO)ses.getAttribute("ExpedienteJor");
    		}
    		UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);
        	expTrabajo.setArea(super.getUsuarioFirmado(request).getAreaActual());
        	expTrabajo.setUsuario(usuarioFirmado);
    		Long formaId = NumberUtils.toLong(request.getParameter("formaId"), 1L);
    		Long documentoId = NumberUtils.toLong(request.getParameter("documentoId"), -1L);
    		Long tipoDocumt = NumberUtils.toLong(request.getParameter("tipoDocumento"), -1L);
    		String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
        	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
        	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
        	
        	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
        	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
        	
    		boolean returnDocument = false;
    		if(request.getParameter("returnDocument") == null || 
    		  (request.getParameter("returnDocument") != null && request.getParameter("returnDocument").equals("1"))){
    			returnDocument = true;
    		} 
    		
    		if (logger.isDebugEnabled()) {
    		    logger.debug("formaId :: " + formaId);
    		    logger.debug("documentoId :: " + documentoId);
    		    logger.debug("tipoDocumt :: " + tipoDocumt);    		        		    
    		}
    		converter.alias("expedienteDTO", ExpedienteDTO.class);
		    logger.info(converter.toXML("MMM::"+expTrabajo));

    		FormaDTO forma = documentoDelegate.buscarForma(formaId);
    		 logger.debug("SE ENCONTRO LA FORMA:: " + forma);
    		DocumentoDTO documentoCargado = null;
    		if(documentoId.longValue() != -1L){
    			documentoCargado = documentoDelegate.cargarDocumentoPorId(documentoId, expTrabajo);
    			forma = documentoCargado.getFormaDTO();
    			logger.debug("FORMA DEL DOCUMENTO CARGADO:: " + documentoCargado.getFormaDTO());
    		}else{
	    		documentoCargado = documentoDelegate.cargarDocumentoPorExpedienteYForma(expTrabajo, new FormaDTO(formaId));
    		}
	    	if(forma != null){
            	//se copian valores del tipo de forma al documento
            	String nombreArchivo = forma.getNombre();
			    String nombreDocumento = forma.getNombre();
			    ValorDTO tipoDocumento = null;
			    if(tipoDocumt.longValue() != -1){
			    	tipoDocumento = new ValorDTO(tipoDocumt);
			    }else{
			    	tipoDocumento = new ValorDTO(TipoDocumento.ACTA.getValorId());
			    }
			    Double version = 1.2;
			    
		    	ArchivoDigitalDTO archivo = null;
		    	
		    	ByteArrayOutputStream archivoPDF = null;
		    	
	    		archivoPDF = generarReportePDFDeHTML(documentoCargado.getTextoParcial(), confPapel);
			    //Crear el archivo digital
			    archivo = new ArchivoDigitalDTO();
			    archivo.setContenido(archivoPDF.toByteArray());
			    archivo.setNombreArchivo(nombreArchivo);
			    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
			    documentoCargado.setArchivoDigital(archivo);
			    documentoCargado.setFechaCreacion(new Date());
			    documentoCargado.setFormaDTO(forma);
			    documentoCargado.setTipoDocumentoDTO(tipoDocumento);
			    documentoCargado.setNombreDocumento(nombreDocumento);
			    documentoCargado.setVersion(version);
			    documentoCargado.setTextoParcial(null);
			    //Se setea el area del rol activo.
			    if(usuarioFirmado != null && usuarioFirmado.getAreaActual() != null && usuarioFirmado.getAreaActual().getAreaId() != null 
			    		&& usuarioFirmado.getAreaActual().getAreaId() > 0L ){
			    	documentoCargado.setJerarquiaOrganizacional(usuarioFirmado.getAreaActual().getAreaId());
			    }

			    documentoDelegate.guardarDocumento(documentoCargado,expTrabajo, null,null);	
			    if(returnDocument){
			    	escribirReporte(response, archivoPDF, nombreArchivo);
			    }
			 }
	    }catch (NSJPNegocioException e) {
	        logger.error(e.getMessage(), e);
		}
        return null;
    }
    /**
     * Genera un archivo digital que corresponde al documento a generar/crear
     * sin embargo no escribe el archivo generado al response del cliente, en vez de esto
     * escribe como XML ?nicamente el ID del documento que se acaba de generar, dejando
     * a la pantalla que invoc? a este m?todo el comportamiento posterior que desea realizar, ya sea
     * para actualizar alg?n estatus o para enviar el documento reci?n creado 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward guardarDocumentoSincrono(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)  {
    	
    	try{
	    	// se obtiene el texto del editor
	    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
	    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
	    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
	    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
	    	
	    	UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);
	    	
	    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
	    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
	    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
	    	DocumentoDTO documento = null;
	    	FormaDTO forma =null;
	    	String parametro = request.getParameter(PARAM_IDENT_DOCUMENTO);
	    	if(StringUtils.isNotBlank(parametro)){
	    		//Se est? editando un documento en espec?fico
	    		Long documentoId = NumberUtils.toLong(parametro);
	    		documento = documentoDelegate.cargarDocumentoPorId(documentoId);
	    		forma = documento.getFormaDTO();
	    	}else{
	    		//Si es un documento nuevo se obtiene el tipo de forma que se est? editando
	            documento = new DocumentoDTO();
		    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
	            forma = documentoDelegate.buscarForma(formaId);
	    	}
	    	
	    	if(forma == null){
	    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
	    	}
	    	
	    	ArchivoDigitalDTO archivo = null;
		    Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMENTO_SOLICITUD);
		    boolean parcial = StringUtils.isNotBlank(request.getParameter(PARAM_GUARDAR_PARCIAL));
	    	ExpedienteDTO expTrabajo = super.getExpedienteTrabajo(request, request.getParameter("numeroUnicoExpediente"));
	    	ByteArrayOutputStream archivoPDF = null;
	    	
		    if(!parcial){
	    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
			    archivo = new ArchivoDigitalDTO();
			    archivo.setContenido(archivoPDF.toByteArray());
			    archivo.setNombreArchivo(forma.getNombre());
			    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
	    	}else{
	    	    documento.setTextoParcial(textoPdf);
	    	}
		    
		    documento.setFormaDTO(forma);
		    documento.setArchivoDigital(archivo);
		    if(documento.getFechaCreacion() == null)
		    	documento.setFechaCreacion(new Date());	
		    documento.setNombreDocumento(forma.getNombre());
		    if(!(documento.getTipoDocumentoDTO() != null && documento.getTipoDocumentoDTO().getIdCampo() != null))
		    	documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
		    //Se setea el area del rol activo.
		    if(usuarioFirmado != null && usuarioFirmado.getAreaActual() != null && usuarioFirmado.getAreaActual().getAreaId() != null){
		    	documento.setJerarquiaOrganizacional(usuarioFirmado.getAreaActual().getAreaId());
		    }

	    	Long documentoId = documentoDelegate.guardarDocumento(documento,expTrabajo, null,null);
	    	
		    escribirRespuesta(response, converter.toXML(documentoId));
		    
	    }catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
    	
    	
    	return null;
    }
    
    /***
     * funcion para generar la notificacion de la solicitud de ayuda psicologica UAVD
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward generarDocumentoNotificacionAyudaPsicologicaUAVD(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		    try{
		    	//Obtener datos del editor de texto
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
		    	String parametro = request.getParameter(PARAM_IDENT_DOCUMENTO);
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;
		    	
		    	UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);

		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	logger.debug(PARAM_IDENT_DOCUMENTO + " :: " + parametro);
		    	
		    	//Edici&oacute;n del documento
		    	if(StringUtils.isNotBlank(parametro)){
		    		Long documentoId = NumberUtils.toLong(parametro);
		    		documento = documentoDelegate.cargarDocumentoPorId(documentoId);
		    		forma = documento.getFormaDTO();
		    	}else{//Si es un documento nuevo se obtiene el tipo de forma que se est&aacute; editando
		            documento = new DocumentoDTO();
			    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
		            forma = documentoDelegate.buscarForma(formaId);
		    	}
		    	if(forma == null){
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		    	}
		    	
		    	ArchivoDigitalDTO archivo = null;
			    Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMENTO_SOLICITUD);
			    boolean parcial = StringUtils.isNotBlank(request.getParameter(PARAM_GUARDAR_PARCIAL));
		    	ByteArrayOutputStream archivoPDF = null;
		    	logger.debug("Es Parcial? :: " + parcial);
			    if(!parcial){
		    		archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
				    archivo = new ArchivoDigitalDTO();
				    archivo.setContenido(archivoPDF.toByteArray());
				    archivo.setNombreArchivo(forma.getNombre());
				    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
		    	}else{
		    	    documento.setTextoParcial(textoPdf);
		    	}
			    documento.setFormaDTO(forma);
			    documento.setArchivoDigital(archivo);
			    documento.setFechaCreacion(new Date());
			    documento.setNombreDocumento(forma.getNombre());
			    documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
			    documento.setEsGuardadoParcial(false);
			    
			    //Numero Expediente Actual 
			    logger.debug("Se trabaja con el expediente actual");
		    	Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"),0L);
		    	ExpedienteDTO expedienteActual = new ExpedienteDTO(); 	
				if(idNumeroExpediente != 0){
					expedienteActual.setNumeroExpedienteId(idNumeroExpediente);
					expedienteActual.setExpedienteId(this.expedienteDelegate.obtenerExpedienteIdPorNumExpId(expedienteActual));
				}else{
					String numeroExpediente = request.getParameter("numeroUnicoExpediente").trim();
					expedienteActual = super.getExpedienteTrabajo(request, numeroExpediente);
				    if(numeroExpediente != null)
				    	expedienteActual = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);
				}
			    //Generar nueva actividad y asociar el nuevo documento
				GuardadoDefinitivoDTO aoGuardadoDefinitivo = new GuardadoDefinitivoDTO();
				aoGuardadoDefinitivo.setExpedienteDTO(expedienteActual);
				aoGuardadoDefinitivo
						.setIdActividad(Actividades.SOLICITAR_ATENCION_PSICOLOGICA_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS
								.getValorId());
				
				aoGuardadoDefinitivo.setFuncionarioDTO(usuarioFirmado.getFuncionario());
				aoGuardadoDefinitivo.setUsuario(usuarioFirmado);
				aoGuardadoDefinitivo.setDocumentoDTO(documento);
				aoGuardadoDefinitivo.setEsGuardadoParcial(false);
				
			    //Se setea el area del rol activo.
			    if(usuarioFirmado != null && usuarioFirmado.getAreaActual() != null && usuarioFirmado.getAreaActual().getAreaId() != null){
			    	if(aoGuardadoDefinitivo.getDocumentoDTO() != null){
			    		aoGuardadoDefinitivo.getDocumentoDTO().setJerarquiaOrganizacional(usuarioFirmado.getAreaActual().getAreaId());
			    	}
			    }
				
				Long documentoId = documentoDelegate
						.ejecutaGuardadoDefinitivo(aoGuardadoDefinitivo);

			    
			    //Datos para el Nuevo Expediente
			    logger.debug("idRelacionDelito" + " :: " + request.getParameter("idRelDelito"));
			    logger.debug("idProbableResponsable" + " :: " + request.getParameter("idPR"));
			    logger.debug("idVictima" + " :: " + request.getParameter("idVictima"));
			    logger.debug("idDelito" + " :: " + request.getParameter("idDelito"));
			    logger.debug("idSolicitud" + " :: " + request.getParameter("idSolicitudVa"));
			    String idFunDestinatario = request.getParameter("idFunDestinatario");
			    logger.debug("idFunDestinatario" + " :: " + idFunDestinatario);
			    Long idSolici=Long.parseLong(request.getParameter("idSolicitudVa"));
			    DelitoPersonaDTO delitoPersonaDTO=new DelitoPersonaDTO();
			    delitoPersonaDTO.setDelitoPersonaId(Long.parseLong(request.getParameter("idRelDelito")));
			    delitoPersonaDTO.setProbableResponsable(new InvolucradoDTO(Long.parseLong(request.getParameter("idPR"))));
			    Long idVictima = NumberUtils.toLong(request.getParameter("idVictima"),0L);
			    if(!idVictima.equals(0L)){
			    	delitoPersonaDTO.setVictima(new InvolucradoDTO(Long.parseLong(request.getParameter("idVictima"))));	
			    }
			    delitoPersonaDTO.setDelito(new DelitoDTO(Long.parseLong(request.getParameter("idDelito"))));
			    
			    UsuarioDTO usuarioTempDTO = null;
			    if(idFunDestinatario.isEmpty()){
			    	List<FuncionarioDTO> listaFuncionario=funcionarioDelegate.consultarFuncionariosPorAreayPuesto(Areas.COORDINACION_ATENCION_VICTIMAS.parseLong(), Puestos.COORDINADOR_ATENCION_VICTIMAS.getValorId());
				    if(listaFuncionario!= null && listaFuncionario.size()!=0){
				    	usuarioTempDTO=usuarioDelegate.consultarUsuarioPorClaveFuncionario(listaFuncionario.get(0).getClaveFuncionario());
				    	//Se agrega el area al funcionario destinatario, para que genere el expediente con el acronimo VictiDel
				    	usuarioTempDTO.setAreaActual(new AreaDTO(Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()));
				    	delitoPersonaDTO.setUsuario(usuarioTempDTO);
				    }else{
				    	delitoPersonaDTO.setUsuario(super.getUsuarioFirmado(request)); 
				    }
			    }else{
			    	usuarioTempDTO=usuarioDelegate.consultarUsuarioPorClaveFuncionario(Long.parseLong(idFunDestinatario));
			    	//Se agrega el area al funcionario destinatario, para que genere el expediente con el acronimo VictiDel
			    	usuarioTempDTO.setAreaActual(new AreaDTO(Areas.COORDINACION_ATENCION_VICTIMAS.parseLong()));
			    	delitoPersonaDTO.setUsuario(usuarioTempDTO);
			    }
			    
			    
			    //Generar el nuevo Numero Expediente 
			    logger.debug("Se genera nuevo expediente de UAVD con la actuacion SOLICITAR_ATENCION_PSICOLOGICA_A_LA_DIRECCION_DE_ATENCION_A_VICTIMAS");
			    delitoPersonaDTO.setCasoDTO(expedienteActual.getCasoDTO());
			    logger.debug("El numero de caso asociado es: " +(expedienteActual.getCasoDTO() != null  ?
			    		expedienteActual.getCasoDTO() : "-"));
			    ExpedienteDTO expedienteNuevoUAVD = expedienteDelegate.generarNuevoExpedienteUAVD(delitoPersonaDTO);
			    logger.debug("Expediente Generado:"+expedienteNuevoUAVD!=null? expedienteNuevoUAVD.getNumeroExpediente():"");
			    logger.debug("Expediente Generado - Actividad:"+expedienteNuevoUAVD!=null? expedienteNuevoUAVD.getActividadActual():"");
			    
			    //Se guarda el documento en el nuevo expediente asociado a la actividad generada con el numero exp
				Long idActividadExistente = expedienteNuevoUAVD
						.getActividadActual().getActividadId();
				
				
				   //Se setea el area del rol activo.
			    if(usuarioFirmado != null && usuarioFirmado.getAreaActual() != null && usuarioFirmado.getAreaActual().getAreaId() != null){
			    	documento.setJerarquiaOrganizacional(usuarioFirmado.getAreaActual().getAreaId());
			    }

				documentoDelegate.guardarDocumento(documento, expedienteNuevoUAVD,
						null, idActividadExistente);
			    logger.debug("Se coloca la solicitud el expediente de UAVD");
			    expedienteDelegate.asociarNumCarpetaASolicitud(expedienteNuevoUAVD, idSolici);
			    
			    if(!parcial){
			    	 escribirReporte(response, archivoPDF, forma.getNombre());
			    	 logger.debug("expediente_ayuda_psicoliga:: "+converter.toXML(expedienteNuevoUAVD)); 
			    	 if(StringUtils.isNotBlank(request.getParameter(PARAMT_TIPO_OPERACION))){
			    		 documento.setDocumentoId(documentoId);
			    		 ejecutarAccion(request, documento);
			    	 }
			    }else{
			    	logger.debug("expediente_ayuda_psicoliga:: "+converter.toXML(expedienteNuevoUAVD));
			    	escribirRespuesta(response, converter.toXML(expedienteNuevoUAVD));
			    }
		    }catch (NSJPNegocioException e) {
				logger.error(e.getMessage(), e);
			}
		    
        return null;
    }
    
    /**
     * M&eacute;todo que lleva a cabo la asociación de un documento a un inventario de pertenencias.
     * @param request - petici&oacute;n de HTML de la cual se obtiene el objeto del inventario de 
     * 					pertenencias que se encuentra en la sesión del usuario.
     * @param parcial - bandera que indica si se trata de un guardado definitivo o parcial.
     * @param documentoId - Identificador del documento con el cual se va a asociar el inventario de 
     * 						pertenencias.
     * @param actividad - el tipo de actividad.
     * @param numeroExpedienteDTO el n&uacute;mero de expediente para consultar la sentencia. 
     */
    private void actualizarInventarioPertenencias(HttpServletRequest request, 
    		Boolean parcial, 
    		Long documentoId, 
    		Actividades actividad, 
    		ExpedienteDTO numeroExpedienteDTO){
    	
    	try {
    		// para cuando sea guardado definitivo
    		if (!parcial) {
		    	InventarioPertenenciaDTO invPer = null;
		    	
		    	switch(actividad) {    	
			    	case GENERAR_ACUSE_RECIBO_PERTENENCIAS:
						invPer = consultarInventarioPertenencias(request);
    					invPer.setDefinitivo(!parcial);
    					if (documentoId != null && documentoId > 0L){
    						DocumentoDTO docDTO = new DocumentoDTO();
    						docDTO.setDocumentoId(documentoId);
    						invPer.setDocumentoDTO(docDTO);
    					}
							
							List<SolicitudDTO> lstSolicitudDTOs = solicituDelegate.consultarSolicitudesPorNumeroExpedienteYTipo(
									numeroExpedienteDTO.getNumeroExpedienteId(),
									TiposSolicitudes.CERTIFICADO_MEDICO
							);
							
							if(lstSolicitudDTOs != null && !lstSolicitudDTOs.isEmpty()){
								SolicitudDTO solicitudDTO =  lstSolicitudDTOs.get(0);
								if(	solicitudDTO.getEstatus() != null
										&& EstatusSolicitud.CERRADA ==  EstatusSolicitud.getByValor(solicitudDTO.getEstatus().getIdCampo())){
									ExpedienteDTO expedienteDTO = new ExpedienteDTO();
									expedienteDTO.setNumeroExpedienteId(numeroExpedienteDTO.getNumeroExpedienteId());
									expedienteDTO.setEstatus(new ValorDTO(EstatusExpediente.PENDIENTE_DE_INGRESO.getValorId()));
									expedienteDelegate.actualizarEstatusExpediente(expedienteDTO);
								}
								
							}
						break;
					case ENTREGAR_VALORES_Y_EFECTOS_DEPOSITADOS :
						// para que todos los registros tengan la misma fecha
						Date fechaEntrega = new Date();					
						SentenciaDTO sentenciaDTO = new SentenciaDTO();
						sentenciaDTO.setNumeroExpedienteDTO(numeroExpedienteDTO);
						sentenciaDTO = asignacionProgramaDelegate.consultarSentencia(sentenciaDTO);
						if(sentenciaDTO != null){
							invPer = sentenciaDTO.getInventarioPertenenciaDTO();
							if(invPer != null){
								invPer.setEntregado(Boolean.TRUE);
								invPer.setFechaEntrega(fechaEntrega);
								for (PertenenciaDTO pertenenciaDTO : invPer.getPertenenciasDTO()) {
									pertenenciaDTO.setEsDevuelto(Boolean.TRUE);
									pertenenciaDTO.setFechaDevolucion(fechaEntrega);
									
									InventarioPertenenciaDTO idInventarioPertenencia = new InventarioPertenenciaDTO();
									idInventarioPertenencia.setInventarioPertenenciaId(invPer.getInventarioPertenenciaId());
									pertenenciaDTO.setInventarioPertenenciaDTO(idInventarioPertenencia);
									
									detencionDelegate.actualizarPertenencia(pertenenciaDTO);
								}							
							}
						}
						break;
		    	}    	
		    	
		    	if(invPer != null) {
		    		reinsercionDelegate.actualizarInventarioPertenencias(invPer);
		    	}
    		}
    	} catch (NSJPNegocioException e) {
    		logger.error(e.getMessage(), e);
    	}
    }   
    
	public ActionForward enviarDocumentosAInstitucion (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		logger.debug("ejecutando Action AsignarProgramaReinsercionSocialAction método llenarGrid");

		StringBuilder html = new StringBuilder(); 

		response.setContentType("text/javascript; charset=ISO-8859-1");
		
		try {
				JSONParser parser = new JSONParser();		
				
				StringBuilder sb = new StringBuilder();
			    BufferedReader br = request.getReader();
			    String str;
			    while( (str = br.readLine()) != null ){
			        sb.append(str);
			    }    
			    Object obj = parser.parse(sb.toString());
				
			    JSONObject jsonObject = (JSONObject) obj;
			    
			    List<DocumentoDTO> lstDocumentoDTO = new ArrayList<DocumentoDTO>();

			    if (jsonObject != null && !jsonObject.isEmpty()) {
			    	
			    	Long sentenciaId = NumberUtils.toLong(jsonObject.get(PARAM_ID_SENTENCIA).toString());
			    	SentenciaDTO sentenciaDTO = null;
			    	if(sentenciaId >0L){
			    		sentenciaDTO = new SentenciaDTO();
			    		sentenciaDTO.setSentenciaId(sentenciaId);
			    		sentenciaDTO = asignacionProgramaDelegate.consultarSentencia(sentenciaDTO);
			    	}
			    	
			    	if(sentenciaDTO != null 
			    			&& sentenciaDTO.getNumeroExpedienteDTO()!= null
			    			&& sentenciaDTO.getNumeroExpedienteDTO().getCasoDTO()!= null
			    			&& sentenciaDTO.getNumeroExpedienteDTO().getCasoDTO().getNumeroGeneralCaso()!= null
			    			&& !sentenciaDTO.getNumeroExpedienteDTO().getCasoDTO().getNumeroGeneralCaso().isEmpty()) {
			    		
			    		CasoDTO casoDTO = sentenciaDTO.getNumeroExpedienteDTO().getCasoDTO();
			    		
				    	JSONArray jsonArray = (JSONArray)jsonObject.get("documentos");
					    
					    if (jsonArray != null && !jsonArray.isEmpty()){
							@SuppressWarnings("unchecked")
							Iterator<JSONObject> iterator = jsonArray.iterator();
							while (iterator.hasNext()) {
								JSONObject json = iterator.next();
								if (json.get("idDocumento") != null){
									Long documentoId = NumberUtils.toLong(json.get("idDocumento").toString());
									if(documentoId>0L){
										DocumentoDTO documentoDTO = documentoDelegate.consultarDocumentoXId(documentoId);
										lstDocumentoDTO.add(documentoDTO);
									}
								}
							}
						}
					    
					    if (!lstDocumentoDTO.isEmpty()){
					    	jsonArray = (JSONArray)jsonObject.get("destinatarios");				    	
						    if (jsonArray != null && !jsonArray.isEmpty()){
								@SuppressWarnings("unchecked")
								Iterator<JSONObject> iterator = jsonArray.iterator();
								while (iterator.hasNext()) {
									JSONObject json = iterator.next();
									if (json.get("idFuncionario") != null
											&& json.get("instId") != null){
										Long idFuncionario = NumberUtils.toLong(json.get("idFuncionario").toString());
										Long idInstitucion = NumberUtils.toLong(json.get("instId").toString());
										if(idFuncionario>0L && idInstitucion>0L){
											FuncionarioDTO funcionarioDTO = new FuncionarioDTO(idFuncionario);
											Instituciones destino = Instituciones.getByValor(idInstitucion);
											
											for (DocumentoDTO documentoDTO : lstDocumentoDTO) {
												documentoDTO.setResponsableDocumento(funcionarioDTO);
											}
											documentoDelegate.enviarDocumentoAInstitucion(lstDocumentoDTO, casoDTO.getNumeroGeneralCaso() , destino);
										}
									}
								}					
								html.append("{\"exito\":\"Los documentos se han enviado con exito.\"}");
							}
					    }
				    } else {
				    	html.append("{\"error\":\"Parametros insuficientes.\"}");	
				    }				    
				} else {
					html.append("{\"error\":\"Parametros insuficientes.\"}");
				}
		} catch (Exception e) {		
			logger.error(e.getCause(),e);
			html.append("{\"error\":\"Ha ocurrido un error, por favor intente mas tarde.\"}");
		} finally {
			try {			
				PrintWriter printWriter = response.getWriter();
				printWriter.print(html.toString());
				printWriter.flush();
				printWriter.close();
			} catch(Exception e) {
				logger.error(e.getCause(),e);	
			}
		}
		return null;
	}
	
	
	public ActionForward enviarUnDocumentoAInstitucion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String numeroGeneralCaso = request.getParameter("numeroGeneralCaso");
		String documentos = request.getParameter("idDocumento");
		Long idFuncionario = NumberUtils.toLong(request.getParameter("idFuncionario"));
		Long idInstitucion = NumberUtils.toLong(request.getParameter("idInstitucion"));
		
		if(!documentos.trim().isEmpty() && idFuncionario > 0L && idInstitucion > 0L) {
			String[] documentosIds = documentos.split(",");
			List<DocumentoDTO> lstDocumentoDTO = new ArrayList<DocumentoDTO>();
			for (int i = 0; i < documentosIds.length; i++) {
				Long documentoId = NumberUtils.toLong(documentosIds[i]);
				if(documentoId != 0L){					
					DocumentoDTO documentoDTO = documentoDelegate.consultarDocumentoXId(documentoId );
					FuncionarioDTO funcionarioDTO = new FuncionarioDTO(idFuncionario);
					documentoDTO.setResponsableDocumento(funcionarioDTO);
					ActividadDTO actividadDTO = new ActividadDTO();
					actividadDTO.setTipoActividad(Actividades.ANEXAR_DOCUMENTO);
					documentoDTO.setActividadDTO(actividadDTO);
					lstDocumentoDTO.add(documentoDTO);				
				}
			}			 
			Instituciones destino = Instituciones.getByValor(idInstitucion);
			try {
				documentoDelegate.enviarDocumentoAInstitucion(lstDocumentoDTO,
						numeroGeneralCaso, destino);
			} catch (NSJPNegocioException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta del inventario de Pertenencias desde la base de datos a 
	 * trav&eacute;s del identificador de la sentencia. Dicho identificador se obtiene como par&aacute;metro 
	 * anidado dentro del objeto HttpServletRequest.
	 * @param request - Objeto del cual se obtiene el par&aacute;metro para poder llevar a cabo la consulta
	 * 					de la sentencia.
	 * @return invPer - InventarioPertenenciaDTO con la informaci&oacute;n del inventario de pertenencias que 
	 * 					se encuentra persistida en la base de datos.
	 */
	private InventarioPertenenciaDTO consultarInventarioPertenencias(HttpServletRequest request){
		Long sentenciaId = NumberUtils.toLong(request.getParameter(PARAM_ID_SENTENCIA),0L);
		InventarioPertenenciaDTO invPer = null;
		if (sentenciaId > 0L){
			SentenciaDTO sentencia = new SentenciaDTO();
			sentencia.setSentenciaId(sentenciaId);
			try {
				sentencia = asignacionProgramaDelegate.consultarSentencia(sentencia);
			} catch (NSJPNegocioException e) {
				logger.error(e.getMessage(), e);
			}
			invPer = sentencia.getInventarioPertenenciaDTO();
		}
		return invPer;
	}
	

	public ActionForward guardarDocumentoDefinitivamente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
			try {
				Boolean esGuardadoParcial = BooleanUtils.toBoolean(request.getParameter(PARAM_GUARDAR_PARCIAL));
				Long idTipoSolicitud = NumberUtils.toLong(request.getParameter("idTipoSolicitud"),0);			

				
				logger.info("SECCION ENCARGADA DE CONFIGURAR LOS DATOS PARA REGISTRAR UNA ACTIVIDAD AL EXPEDIENTE");
				Long documentoId = 0L;
				
				Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"),0L);
				Long idActividad = NumberUtils.toLong(request.getParameter("actuacion"),0L);
				Long idClaveFuncionarioAsignado = NumberUtils.toLong(request.getParameter("cveFuncionarioAsignado"),0L);
				Long solicitudPeritoId=	NumberUtils.toLong(request.getParameter("solicitudPeritoId"),0L);
				Long involcradoId = NumberUtils.toLong(request.getParameter("involcradoId"),0L);
				Long confActividadId = NumberUtils.toLong(request.getParameter("confActividadId"),0L);
				
				logger.info("ejecutando Action AtencionTempranaPenalAction en metodo registraActividadExpediente la actuacion es:"+idActividad);
				logger.info("ejecutando Action AtencionTempranaPenalAction en metodo registraActividadExpediente el idNumeroExpediente es:"+idNumeroExpediente);
				logger.info("ejecutando Action AtencionTempranaPenalAction en metodo registraActividadExpediente el NumeroExpediente es:"+idClaveFuncionarioAsignado);
				logger.info("ejecutando Action AtencionTempranaPenalAction en metodo registraActividadExpediente el involcradoId es:"+involcradoId);
				logger.info("ejecutando Action AtencionTempranaPenalAction en metodo registraActividadExpediente el confActividadId es:"+confActividadId);
				
				ExpedienteDTO expediente = new ExpedienteDTO(); 	
				
				if(!idNumeroExpediente.equals(0L)){
					expediente.setNumeroExpedienteId(idNumeroExpediente);
					expediente.setExpedienteId(this.expedienteDelegate.obtenerExpedienteIdPorNumExpId(expediente));
				}
				else if (request.getParameter("numeroUnicoExpediente") != null && !request.getParameter("numeroUnicoExpediente").trim().isEmpty()){				
					
					String numeroExpediente = request.getParameter("numeroUnicoExpediente").trim();
					
					expediente = super.getExpedienteTrabajo(request, numeroExpediente);
					if(expediente == null && numeroExpediente != null){
						expediente = expedienteDelegate.obtenerExpedientePorNumeroExpediente(numeroExpediente);						
					}
				}else if(NumberUtils.toLong(request.getParameter("expedienteId"),0L) > 0L){
					Long expedienteId = NumberUtils.toLong(request.getParameter("expedienteId"));
					expediente = expedienteDelegate.consultarExpedientePorExpedienteId(new ExpedienteDTO(expedienteId));
				}
				
				GuardadoDefinitivoDTO loGuardadoDefinitivoDTO = new GuardadoDefinitivoDTO();
				loGuardadoDefinitivoDTO.setIdActividad(idActividad);
				loGuardadoDefinitivoDTO.setIdClaveFuncionarioAsignado(idClaveFuncionarioAsignado);
				
				UsuarioDTO usuarioDTO=super.getUsuarioFirmado(request);				
				loGuardadoDefinitivoDTO.setFuncionarioDTO(usuarioDTO.getFuncionario());
				
				
				
				logger.info("SECCION ENCARGADA DE CONFIGURAR LOS DATOS PARA GUARDAR UN DOCUMENTO ASOCIADO AL EXPEDIENTE");
				Long nuevaActividad = NumberUtils.toLong(request.getParameter("nuevaActividad"), 0L);
				
		    	String textoPdf = request.getParameter(PARAM_TEXTO_DOCUMENTO);
		    	Long idDocumento = NumberUtils.toLong(request.getParameter(PARAM_IDENT_DOCUMENTO),0L);
		    	String tamanioPapelIdString = request.getParameter(TAMANIO_PAPEL);
		    	Long tamanioPapelId = NumberUtils.toLong(tamanioPapelIdString, 1L);
		    	PDFPropiedad confPapel = PDFPropiedad.getByValor(tamanioPapelId);
		    	Long tipoDocumento = NumberUtils.toLong(request.getParameter(PARAMT_TIPO_DOCUMENTO), TIPO_DOCUMENTO_SOLICITUD);
		    	Long solicitanteDeDefensorId = NumberUtils.toLong(request.getParameter("solicitanteId"), 0L);
		    	String arrayInvolucradosSolDefensor = request.getParameter("arrayInvolucradosSolDefensor");
		    	
		    	logger.debug(PARAM_TEXTO_DOCUMENTO + " :: " + textoPdf);
		    	logger.debug(TAMANIO_PAPEL + " :: " + tamanioPapelIdString);
		    	logger.debug("tamanioPapelId" + " :: " + tamanioPapelId);
		    	logger.debug(PARAM_IDENT_DOCUMENTO + " :: " + idDocumento);
		    	
		    	logger.info("SECCION ENCARGADA PARA RECUPERAR PARAMETROS PARA ASOCIAR DOCUMENTO A AUDIENCIA");
		    	String audienciaId = request.getParameter("audienciaId");
		    	
		    	logger.info("SECCION ENCARGADA PARA RECUPERAR PARAMETROS PARA ENVIAR ACUSE DE RECIBO DE SOL DE DEFENSOR");
		    	Long solicitudDefensorId = NumberUtils.toLong(request.getParameter("solicitudDefensorId"), 0L);
		    	
		    	DocumentoDTO documento = null;
		    	FormaDTO forma =null;

				ConfInstitucionDTO confInstitucionDTO = null;
				if(usuarioDTO.getInstitucion() != null) {
					confInstitucionDTO = new ConfInstitucionDTO();
					Long confInstId = usuarioDTO.getInstitucion().getConfInstitucionId();
					confInstitucionDTO.setConfInstitucionId(confInstId);	
				}
				
		    	if(idDocumento > 0){
		    		// Se esta editando un documento en especifico
		    		documento = documentoDelegate.cargarDocumentoPorId(idDocumento);
		    		forma = documento.getFormaDTO();
		    	}else{
		    		// Si es un documento nuevo se obtiene el tipo de forma que se esta editando
		            documento = new DocumentoDTO();
			    	Long formaId = NumberUtils.toLong(request.getParameter(PARAM_FORMA_DOCUMENTO), 1L);
		            forma = documentoDelegate.buscarForma(formaId);
		    	}
		    	
		    	if(forma == null){
		    		throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		    	}
		    	
		    	ArchivoDigitalDTO archivo = null;

		    	UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);
		    	String numeroFolio = request.getParameter("iNumeroOficio");
		    	ByteArrayOutputStream archivoPDF = null;
		    	logger.debug("Numero Folio :: " + request.getParameter("iNumeroOficio"));

		    	if(!esGuardadoParcial){
			    	archivoPDF = generarReportePDFDeHTML(textoPdf, confPapel);
				    archivo = new ArchivoDigitalDTO();
				    archivo.setContenido(archivoPDF.toByteArray());
				    archivo.setNombreArchivo(forma.getNombre());
				    archivo.setTipoArchivo(ConstantesGenerales.EXTENSION_PDF);
		    	}else{
		    	    documento.setTextoParcial(textoPdf);
		    	}
			    
			    documento.setConfInstitucion(confInstitucionDTO);
			    documento.setFormaDTO(forma);
			    documento.setArchivoDigital(archivo);
			    documento.setFechaCreacion(new Date());
			    documento.setNombreDocumento(forma.getNombre());
			    documento.setFolioDocumento(numeroFolio);
			    documento.setTipoDocumentoDTO(new ValorDTO(tipoDocumento));
			    documento.setEsGuardadoParcial(esGuardadoParcial);
			    documento.setResponsableDocumento(usuarioDTO.getFuncionario());
			    documento.setJerarquiaOrganizacional(usuarioFirmado.getFuncionario().getJerarquiaOrganizacional().getJerarquiaOrganizacionalId());
			    
			    loGuardadoDefinitivoDTO.setIdNuevaActividad(nuevaActividad);
			    loGuardadoDefinitivoDTO.setDocumentoDTO(documento);
			    if(idActividad.equals(Actividades.SOLICITAR_DEFENSOR_PUBLICO.getValorId())){
			    	expediente.setUsuario(usuarioFirmado);
			    }
			    loGuardadoDefinitivoDTO.setExpedienteDTO(expediente);
			    loGuardadoDefinitivoDTO.setEsGuardadoParcial(esGuardadoParcial);
			    loGuardadoDefinitivoDTO.setUsuario(usuarioFirmado);
			    
			    if(!esGuardadoParcial){
			    	logger.info("SECCION ENCARGADA DE CONFIGURAR EL CAMBIO DE CATDISCRIMIANTE DEL EXPEDIENTE (OPCIONAL)");
					Long catDiscriminanteId = NumberUtils.toLong(request.getParameter("catDiscriminanteId"),0L);
		        	Long catUIEId = NumberUtils.toLong(request.getParameter("catUIE"),0L);
		        	CatDiscriminanteDTO discriminante = new CatDiscriminanteDTO();
		        	discriminante.setCatDiscriminanteId(catDiscriminanteId);
		        	expediente.setDiscriminante(discriminante);
		        	expediente.setCatUIE(catUIEId);
		        	
				    
				    logger.info("SECCION ENCARGADA DE CONFIGURAR EL CAMBIO DE ESTATUS DE UN NUMERO DE EXPEDIENTE");
				    if(usuarioFirmado != null && usuarioFirmado.getAreaActual() != null){
				    	loGuardadoDefinitivoDTO.getExpedienteDTO().setArea(usuarioFirmado.getAreaActual());
				    }
		        	
				}
			
				logger.info("SECCION ENCARGADA DE CONFIGURAR LOS DATOS PARA REGISTRAR UNA SOLICITUD AL EXPEDIENTE");
				if(idTipoSolicitud > 0){
					configuraDatosDeSolicitud(loGuardadoDefinitivoDTO, request);
				}
				
				logger.info("SECCION ENVIAR ACUSE DE RECIBO DE SOLICITUD DE DEFENSOR");
				if(solicitudDefensorId > 0L){
					loGuardadoDefinitivoDTO.setSolicitudDTO(new SolicitudDTO(solicitudDefensorId));
				}
				
				logger.info("SECCION PARA REGISTRAR UNA SOLICITUD DE DEFENSOR DE DEFENSORIA A DEFENSORIA");
				if(solicitanteDeDefensorId > 0L){	
					SolicitudDTO solDefensorDefensoria = new SolicitudDTO();
					solDefensorDefensoria.setInvolucradoDTO(new InvolucradoDTO(solicitanteDeDefensorId));
					loGuardadoDefinitivoDTO.setSolicitudDTO(solDefensorDefensoria);
					loGuardadoDefinitivoDTO.setIdsInvolucrados(arrayInvolucradosSolDefensor);
				}
				if (involcradoId != null && involcradoId > 0) {
					loGuardadoDefinitivoDTO.setInvolcradoId(involcradoId);
				}
				if (confActividadId != null && confActividadId > 0) {
					loGuardadoDefinitivoDTO.setConfActividadId(confActividadId);
				}
				
				loGuardadoDefinitivoDTO.setSolicitudPeritoId(solicitudPeritoId);
				
				//SERVICIO TRANSACCIONAL
				documentoId = documentoDelegate.ejecutaGuardadoDefinitivo(loGuardadoDefinitivoDTO);
				
				//Permite relacionar la audiencia con el documento generado para el Rol de encargado Sala
				try {					
					Long audienciaIdRel = NumberUtils.toLong(audienciaId, 0L);
					
					if(audienciaIdRel > 0L){
						AudienciaDTO audienciaRel = new AudienciaDTO();
						audienciaRel.setId(audienciaIdRel);
						
						DocumentoDTO documentoRel = new DocumentoDTO();
						documentoRel.setDocumentoId(documentoId);
						
						audienciaDelegate.asociarDocumentoAAudiencia(audienciaRel, documentoRel);
					}
				} catch (NSJPNegocioException ne) {
					if (ne.getCodigo().equals(CodigoError.DOCUMENTO_YA_ASOCIADO)) {
						logger.debug("DOCUMENTO YA ASOCIADO EN AUDIENCIA");
					}
				}
				
				logger.info("SECCION PARA CAMBIAR EL ESTATUS AL MANDAMIENTO JUDICIAL, DESPUES DE UN GUARDADO DEFINITIVO");
				/**
				 * Se cambia el estatus del mandamiento judicial dependiendo si fue un guardado
				 * definitivo del propio mandamiento o un guardado definitivo de un documento de
				 * cambio de estatus
				 * 
				 * en el caso 1) se cambia el estatus a NO_ENVIADO,
				 * en el caso 2) se cambia el estatus a ACTUALIZACION_NO_ENVIADA
				 */
				if (!esGuardadoParcial
						&& (idActividad
								.equals(Actividades.GENERAR_MANDAMIENTO_JUDICIAL
										.getValorId()) || idActividad
								.equals(Actividades.GENERAR_DOCUMENTO_DE_CAMBIO_DE_ESTATUS_DE_MANDAMIENTO_JUDICIAL
										.getValorId()))) {
	
					MandamientoDTO mandamientoDTO = new MandamientoDTO();
					
					if (idActividad.equals(Actividades.GENERAR_MANDAMIENTO_JUDICIAL
							.getValorId())) {
						mandamientoDTO.setDocumentoId(documentoId);
						mandamientoDTO.setEstatus(new ValorDTO(
								EstatusMandamiento.NO_ENVIADO.getValorId()));
					} else {
	
						Long mandamientoId = NumberUtils.toLong(
								request.getParameter("mandamientoId"), 0L);
						if (mandamientoId < 0L) {
							throw new NSJPNegocioException(
									CodigoError.PARAMETROS_INSUFICIENTES);
						}
						mandamientoDTO.setDocumentoId(mandamientoId);
						mandamientoDTO.setEstatus(new ValorDTO(
								EstatusMandamiento.ACTUALIZACION_NO_ENVIADA.getValorId()));
					}
	
					administrarMandamientoJudicialDelegate
							.actualizarEstatusMandamiento(mandamientoDTO,
									false);
				}
				
				DocumentoDTO documentoDTO =new DocumentoDTO();
				documentoDTO.setDocumentoId(documentoId);
				converter.alias("documentoDTO", DocumentoDTO.class);
				String xml = converter.toXML(documentoDTO);
				escribirRespuesta(response, xml);
				
				
			} catch (Exception e) {
				DocumentoDTO documentoDTO =new DocumentoDTO();
				documentoDTO.setDocumentoId(0L);
				converter.alias("documentoDTO", DocumentoDTO.class);
				String xml = converter.toXML(documentoDTO);
				escribirRespuesta(response, xml);
				logger.error(e.getMessage(),e);
			}
			return null;
	}
	

	/**
	 * Permite configurar el detalle de una solicitud, previo a su registro en BD
	 * @param loGuardadoDefinitivoDTO
	 * @param request
	 * @throws NumberFormatException
	 * @throws NSJPNegocioException
	 */
	private void configuraDatosDeSolicitud(
			GuardadoDefinitivoDTO loGuardadoDefinitivoDTO, HttpServletRequest request) throws NumberFormatException, NSJPNegocioException {
		
		Long institucionSolicitante = NumberUtils.toLong(request.getParameter("institucionSolicitante"),0L);
		String solicitante = loGuardadoDefinitivoDTO.getFuncionarioDTO().getNombreCompleto();
		Long idSolicitud = NumberUtils.toLong(request.getParameter("idSolicitud"),0);
		Long idTipoSolicitud = NumberUtils.toLong(request.getParameter("idTipoSolicitud"),0);			
		String motivo =request.getParameter("motivo");
		Long solicitudIdOrigen = NumberUtils.toLong(request.getParameter("solicitudIdOrigen"),0);
		Long areaDestino = NumberUtils.toLong(request.getParameter("areaDestino"),0);
		
		String idFuncionariosSolicitantes = request.getParameter("idsFuncionariosSolicitantes");
		String[] iDsFuncionarios = null;
		if(!idFuncionariosSolicitantes.equals(""))
			iDsFuncionarios = idFuncionariosSolicitantes.split(",");
		
		
		Date fechaHora= new Date();

		logger.info("VERIFICANDO PARAMETROS::::::::::::::::::::::::::");
		logger.info("institucionSolicitante:" + institucionSolicitante);
		logger.info("solicitante           :" + solicitante);			
		logger.info("idsFuncionariosSolicitantes: " + idFuncionariosSolicitantes);			
		logger.info("idSolicitud           : " + idSolicitud);			
		logger.info("idTipoSolicitud       : " + idTipoSolicitud);
		logger.info("motivo                : " + motivo);
		logger.info("solicitudIdOrigen     : " + solicitudIdOrigen);
		logger.info("areaDestino     : " + areaDestino);
		
		ValorDTO tipoSolicitudDTO = new ValorDTO();
		SolicitudDTO solicitudDTO = new SolicitudDTO();
		ConfInstitucionDTO confInstitucionDTO = null;
		if(institucionSolicitante != null && institucionSolicitante>0){
			confInstitucionDTO = new ConfInstitucionDTO();
			confInstitucionDTO.setConfInstitucionId(institucionSolicitante);	
		}
							
		if(!idTipoSolicitud.equals(0L) && idTipoSolicitud.equals( TiposSolicitudes.POLICIA_MINISTERIAL.getValorId())){
			tipoSolicitudDTO.setIdCampo(TiposSolicitudes.POLICIA_MINISTERIAL.getValorId());
			solicitudDTO.setAreaDestino((long) Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal());
		}else{
			if(!idTipoSolicitud.equals(0L))
				tipoSolicitudDTO.setIdCampo(idTipoSolicitud);
			else
				tipoSolicitudDTO.setIdCampo(TiposSolicitudes.APOYO.getValorId());
		}
		
		solicitudDTO.setTipoSolicitudDTO(tipoSolicitudDTO);
		solicitudDTO.setEstatus(new ValorDTO(EstatusSolicitud.ABIERTA.getValorId()));
		
		solicitudDTO.setNombreDocumento("Solicitud");
		solicitudDTO.setFechaCreacion(new Date());
		
		solicitudDTO.setTipoDocumentoDTO(new ValorDTO(TipoDocumento.SOLICITUD.getValorId()));
		solicitudDTO.setFormaDTO(new FormaDTO(Formas.SOLICITUD.getValorId()));
		
		solicitudDTO.setExpedienteDTO(loGuardadoDefinitivoDTO.getExpedienteDTO());	
		solicitudDTO.setFechaCreacion(fechaHora);
		solicitudDTO.setInstitucion(confInstitucionDTO);
		solicitudDTO.setNombreSolicitante(solicitante);
		solicitudDTO.setUsuarioSolicitante(loGuardadoDefinitivoDTO.getUsuario().getFuncionario());
		if(motivo != null)
			solicitudDTO.setMotivo(motivo);
		solicitudDTO.setDocumentoId(idSolicitud);
		
		UsuarioDTO usuarioEnSesionDTO = loGuardadoDefinitivoDTO.getUsuario();
		Long areaId = usuarioEnSesionDTO.getAreaActual().getAreaId();
		for (int i=0; i< iDsFuncionarios.length; i++) {
			FuncionarioDTO loDestinatario = new FuncionarioDTO(Long.parseLong(iDsFuncionarios[i]));
			solicitudDTO.setDestinatario(loDestinatario);
			UsuarioDTO usuarioDTO=usuarioDelegate.consultarUsuarioPorClaveFuncionario(Long.parseLong(iDsFuncionarios[i]));
			//Tomamos el area del usuario destinatario
			if(areaDestino != null && areaDestino>0L){
				solicitudDTO.setAreaDestino(areaDestino);
			}
			else{
				solicitudDTO.setAreaDestino(usuarioDTO.getAreaActual().getAreaId());
			}
			solicitudDTO.setAreaOrigen(areaId);
			if(!idTipoSolicitud.equals(0L) && idTipoSolicitud.equals( TiposSolicitudes.POLICIA_MINISTERIAL.getValorId())){
				solicitudDTO.setAreaDestino(new Long(Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal()));
			}
			loGuardadoDefinitivoDTO.setSolicitudDTO(solicitudDTO);
		}
	}

	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n de los per&iacute;odos de 
	 * acumulaci&oacute;n de puntos de buena conducta una vez que se ha generado el resumen
	 * de acumulaci&oacute;n de puntos de buena conducta.
	 * @param request - objeto de Java que representa la petici&oacute;n de HTML.
	 * @param parcial - bandera que especifica si el guardado del documento es definitivo,
	 * 					s&oacute;lo en ese caso, se actualizan los per&iacute;odos de 
	 * 					acumulaci&oacute;n de puntos.
	 */
	private void actualizarResumenPeriodosAcumulacion(HttpServletRequest request, Boolean parcial){
		if (!parcial){
			Long sentenciaId = NumberUtils.toLong(request.getParameter(PARAM_ID_SENTENCIA),0L);
			if (sentenciaId > 0L){
				SentenciaDTO sentencia = new SentenciaDTO();
				sentencia.setSentenciaId(sentenciaId);
				
				PeriodoAcumulacionPuntosDTO pap = new PeriodoAcumulacionPuntosDTO();
				pap.setbResumenEmitido(Boolean.FALSE);
				
				ActoBuenaConductaDTO abc = new ActoBuenaConductaDTO();
				abc.setPeriodoAcumulacionPuntos(pap);
				
				List<ActoBuenaConductaDTO> lstAbc = new ArrayList<ActoBuenaConductaDTO>();
				lstAbc.add(abc);
				sentencia.setActoBuenaConductas(lstAbc);
				
				try {
					asignacionProgramaDelegate.actualizarResumenPeriodosAcumulacion(sentencia);
				} catch (NSJPNegocioException e) {
					logger.error(e.getMessage(),e);
				}
			}			
		}
	}
}	
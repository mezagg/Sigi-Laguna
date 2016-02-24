package mx.gob.segob.nsjp.web.documento.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.documento.TipoDocumento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.actividad.ActividadDelegate;
import mx.gob.segob.nsjp.delegate.documento.DocumentoDelegate;
import mx.gob.segob.nsjp.delegate.eslabon.EslabonDelegate;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.dto.ActividadDTO;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.evidencia.EslabonDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.documento.form.AdjuntarDocumentoAsociadoAExpedienteForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rgama
 */
public class DocumentoAction extends GenericAction {

    private static final Logger logger =
            Logger.getLogger(GenericAction.class);
        
	@Autowired
	private DocumentoDelegate documentoDelegate;
	@Autowired
	public ExpedienteDelegate expedienteDelegate;
	@Autowired
	public EslabonDelegate eslabonDelegate;
	@Autowired
	public ActividadDelegate actividadDelegate;

	/**
	 * Metodo utilizado para adjuntar un documento y asociarlo a un expediente 
	 * @param mapping
	 * @param form
	 * @param request 
	 * @param response
	 * @return null
	 * @throws IOException En caso de obtener una exception
	 */
	public ActionForward adjuntarDocumentoAExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			logger.info("EJECUTANDO ADJUNTAR DOCUMENTO Y ASOCIARLO A UN EXPEDIENTE");
            Long idExpediente = 0L;
            Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"),0);
            Long idEslabon = NumberUtils.toLong(request.getParameter("idEslabon"),0);


			ExpedienteDTO expedienteDTO = new ExpedienteDTO();
			ArchivoDigitalDTO adjunto = new ArchivoDigitalDTO();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
			
		    
            if(idEslabon > 0){
            	idExpediente = eslabonDelegate.obtenIdExpedienteDeUnEslabon(idEslabon);
				expedienteDTO.setExpedienteId(idExpediente);
            }
			
			AdjuntarDocumentoAsociadoAExpedienteForm adjuntarDocumentoAsociadoAExpedienteForm = (AdjuntarDocumentoAsociadoAExpedienteForm)form;			
			idExpediente = adjuntarDocumentoAsociadoAExpedienteForm.getExpedienteId();			
					
			if(idExpediente != null && idExpediente > 0){
				expedienteDTO.setExpedienteId(idExpediente);
			}else{
				if(idNumeroExpediente != null && idNumeroExpediente >0){
					ExpedienteDTO loExpedienteDTO = new ExpedienteDTO();
					loExpedienteDTO.setNumeroExpedienteId(idNumeroExpediente);
					idExpediente = expedienteDelegate.obtenerExpedienteIdPorNumExpId(loExpedienteDTO);
					expedienteDTO.setExpedienteId(idExpediente);
				}
			}			
				
			
			logger.info("expediente Id de la forma" + idExpediente);
			
			FormFile archivo = adjuntarDocumentoAsociadoAExpedienteForm.getArchivoAdjunto();
			String fileName    = archivo.getFileName();
			byte[] fileData    = archivo.getFileData();			        
			
			adjunto.setContenido(fileData);
			
			if(fileName != null){
				String[] extension = fileName.split("\\.");
				adjunto.setTipoArchivo("."+extension[extension.length-1]);
				adjunto.setNombreArchivo(extension[0]);
				logger.info("El nombre del archivo es: " + extension[0]);

			}
    		UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);
			
			adjunto.setUsuario(usuarioFirmado);
			
			funcionarioDTO = getUsuarioFirmado(request).getFuncionario();
			
			
			Actividades actividad = null;
			Long idTipoActividad = adjuntarDocumentoAsociadoAExpedienteForm.getTipoActividad();
			
			if (idTipoActividad > 0L){
				actividad = Actividades.getByValor(idTipoActividad);
			}else{
				actividad = Actividades.ANEXAR_DOCUMENTO;
			}
			
	
			DocumentoDTO loDocuemntoDTO = new DocumentoDTO();
			loDocuemntoDTO.setArchivoDigital(adjunto);			
       		/*Obligatorios de Documento*/
			loDocuemntoDTO.setNombreDocumento(adjunto.getNombreArchivo());
			loDocuemntoDTO.setFechaCreacion(new Date());
			//Se asigna el tipo de documento

			Long idTipoDocto = adjuntarDocumentoAsociadoAExpedienteForm.getTipoDocumento();
			TipoDocumento tipoDoc = null;
			if (idTipoDocto > 0L){
				loDocuemntoDTO.setTipoDocumentoDTO(new ValorDTO(idTipoDocto));
				tipoDoc = TipoDocumento.getByValor(idTipoDocto);
			}else{
				String extAudio = ((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarAudio();
				String extImagen = ((ConfiguracionDTO) request.getSession().getAttribute(GenericAction.KEY_SESSION_CONFIGURACION_GLOBAL)).getExtensionesPermitidasAlAdjuntarImagen();				

				if(extAudio.toLowerCase().contains(adjunto.getTipoArchivo().toLowerCase())){
					tipoDoc = TipoDocumento.ARCHIVO_AUDIO;
				}else if(extImagen.toLowerCase().contains(adjunto.getTipoArchivo().toLowerCase())){
					tipoDoc = TipoDocumento.ARCHIVO_IMAGEN;
				}else{
					tipoDoc = TipoDocumento.ARCHIVO_ADJUNTADO;
				}

				loDocuemntoDTO.setTipoDocumentoDTO(new ValorDTO(tipoDoc.getValorId()));
			}
			loDocuemntoDTO.setEsGuardadoParcial(false);
			
			 //Se setea el area del rol activo.
		    if(usuarioFirmado != null && usuarioFirmado.getAreaActual() != null && usuarioFirmado.getAreaActual().getAreaId() != null){
		    	loDocuemntoDTO.setJerarquiaOrganizacional(usuarioFirmado.getAreaActual().getAreaId());
		    }
			
			Long idDocumento = documentoDelegate.adjuntarDocumentoAExpediente(expedienteDTO, loDocuemntoDTO, funcionarioDTO, actividad, tipoDoc);
			
			
			 if(idEslabon > 0){//Se asocia el documento generado al eslabon
				 documentoDelegate.asociarDocumentoAEslabon(new EslabonDTO(idEslabon), new DocumentoDTO(idDocumento));
	         }
			 
			 Long regresarId = NumberUtils.toLong(request.getParameter("regresarId"));
			 if(regresarId.compareTo(0L) != 0){
				 request.setAttribute("idDocumento", idDocumento);
				 request.setAttribute("regresarIdAtt", 1);
			 }
			 
   			logger.info("regreso"+ idDocumento);
			return mapping.findForward("success");
					
		} catch (Exception e) {		
			logger.info(e.getCause(),e);
			return mapping.findForward("fail");
		}
		
	}
	
	public ActionForward validarTipoActividadEnExpediente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws NSJPNegocioException {
		
		String respuesta = "0";
		Long tipoActividad = NumberUtils.toLong(request.getParameter("tipoActividad"),0);
		Long idExpediente = NumberUtils.toLong(request.getParameter("idExpediente"),0);
		
		if(idExpediente == 0){
			Long idNumeroExpediente = NumberUtils.toLong(request.getParameter("idNumeroExpediente"),0);
			if(idNumeroExpediente > 0){
				ExpedienteDTO expedienteDTO = new ExpedienteDTO();
				expedienteDTO.setNumeroExpedienteId(idNumeroExpediente);
				idExpediente= expedienteDelegate.obtenerExpedienteIdPorNumExpId(expedienteDTO);
			}
		}
		
		if(idExpediente > 0 && tipoActividad > 0){

			if (documentoDelegate.existeDocumentoPorTipoActividadPorExpedienteId(
					idExpediente, tipoActividad) ){
				respuesta = "1";			
			}
		}
		XStream converter=new XStream();
		converter.alias("respuesta",String.class);
		escribirRespuesta(response,converter.toXML(respuesta.toString()));
		
		
		return null;
		
	}	
	
	
}

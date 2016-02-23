package mx.gob.segob.nsjp.web.notificacion.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.notificacion.NotificacionDelegate;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.documento.CatFormaNotificacionDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Jacob Lobaco
 */
public class GenerarNotificacionAction extends GenericAction {

    private static final Logger logger =
            Logger.getLogger(GenerarNotificacionAction.class);
    
    @Autowired
    private NotificacionDelegate notificacionDelegate;

    public ActionForward armaNotificacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("request = " + request);
            }
            FuncionarioDTO funcionarioDto = getUsuarioFirmado(request).getFuncionario();
            NotificacionDTO notificacionDto = notificacionDelegate.consultarUltimaNotificacionPorAnio(funcionarioDto);
            XStream converter=new XStream();
			converter.alias("Notificacion", NotificacionDTO.class);
            notificacionDto.setMotivo("Informe de notificaci�n");
            String notificacionXml = converter.toXML(notificacionDto);
            if (logger.isDebugEnabled()) {
                logger.debug("notificacionXml = " + notificacionXml);
            }

			converter.alias("Funcionario", FuncionarioDTO.class);
            String funcionarioXML = converter.toXML(funcionarioDto);
            if (logger.isDebugEnabled()) {
                logger.debug("funcionarioXML = " + funcionarioXML);
            }
            escribirRespuesta(response, notificacionXml + funcionarioXML);
        } catch (NSJPNegocioException ex) {
            java.util.logging.Logger.getLogger(GenerarNotificacionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ActionForward consultaDestinatarios(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<Destinatario> destinatarios = new LinkedList<Destinatario>();
        int total = 12;
        for(int i = 0; i < total; ++i){
            Destinatario destinatario = new Destinatario();
            destinatario.setClaveFuncionario((long)i);
            destinatario.setNombreFuncionario("Cosme" + i);
            DepartamentoDTO departamentoDTO = new DepartamentoDTO();
            AreaDTO areaDTO = new AreaDTO();
            areaDTO.setNombre("area" + i);
            departamentoDTO.setArea(areaDTO);
            destinatario.setDepartamento(departamentoDTO);
            destinatario.setEmail(i + "@ultra.com");
            destinatarios.add(destinatario);
        }
        Grid<Destinatario> paginaGrid = new Grid<Destinatario>
                //colNames:['idDestinatario','Nombre','Puesto', 'Correo Electr�nico', 'Principal', "Copia"],
                (1, 32, 10, destinatarios, "claveFuncionario", "nombreFuncionario", "departamento.area.nombre", "email", "principal", "copia");
        if (logger.isDebugEnabled()) {
            logger.debug("paginaGrid = " + paginaGrid);
        }
        escribirRespuesta(response, paginaGrid.toString());
        return null;
    }

    private class Destinatario extends FuncionarioDTO{
        private Boolean principal = false;
        private Boolean copia = false;

        public Boolean getCopia() {
            return copia;
        }

        public void setCopia(Boolean copia) {
            this.copia = copia;
        }

        public Boolean getPrincipal() {
            return principal;
        }

        public void setPrincipal(Boolean principal) {
            this.principal = principal;
        }

    }
    
    
	/**
	 * M&eacute;todo para generar una notificaci&oacute;n a un involucrado, a un
	 * funcionario o a un funcionario externo.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return XML con el ID de la notificaci&oacute;n generada.
	 * @throws IOException
	 */
    public ActionForward generarNotificacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	try {
			
			logger.info("EJECUTANDO ACTION GENERAR NOTIFICACION.....");
			
			Long documentoId = NumberUtils.toLong(
					request.getParameter("documentoId"), 0L);
			Long idEvento = NumberUtils.toLong(request.getParameter("id"), 0L);
			Long funcionarioId = NumberUtils.toLong(
					request.getParameter("funcionarioId"), 0L);
			Long funcionarioExternoId = NumberUtils.toLong(
					request.getParameter("funcionarioExternoId"), 0L);
			Long involucradoId = NumberUtils.toLong(
					request.getParameter("involucradoId"), 0L);
			
			Long formaNotificacion = NumberUtils.toLong(
					request.getParameter("formaNotificacion"), 0L);

			logger.info("Verificando parametros.....................");
			logger.info("documentoId............" + documentoId);
			logger.info("audienciaId............" + idEvento);
			logger.info("funcionarioId.........." + funcionarioId);
			logger.info("funcionarioExternoId..." + funcionarioExternoId);
			logger.info("involucradoId.........." + involucradoId);
			logger.info("forma notificacion....." + formaNotificacion);
			
			if (idEvento == null
					|| idEvento <= 0L
					|| (funcionarioId <= 0L && funcionarioExternoId <= 0L && involucradoId <= 0L)) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}
			
			
			NotificacionDTO notificacionDTO = new NotificacionDTO();
			notificacionDTO.setEstatus(new ValorDTO(
					EstatusNotificacion.EN_PROCESO.getValorId()));
			
			AudienciaDTO audienciaDTO = new AudienciaDTO(idEvento);
						
			
			//Verificamos la forma de notificacion
			if (formaNotificacion != null && formaNotificacion > 0L) {
				CatFormaNotificacionDTO catFormaNotificacionDTO = new CatFormaNotificacionDTO();
				catFormaNotificacionDTO
						.setCatFormaNotificacionId(formaNotificacion);
				notificacionDTO
						.setCatFormaNotificacionDTO(catFormaNotificacionDTO);
			}
			
			// valor id de la notificacion generada
			Long notificacion = 0L;
			
			if (funcionarioId > 0L) {
				notificacion = notificacionDelegate.guardarNotificacion(
						notificacionDTO, audienciaDTO,new FuncionarioDTO(funcionarioId));
			}
			if( funcionarioExternoId > 0L){
				notificacion = notificacionDelegate.guardarNotificacion(
						notificacionDTO, audienciaDTO,
						new FuncionarioExternoDTO(funcionarioExternoId));
			}
			if(involucradoId > 0L){
				notificacion = notificacionDelegate.guardarNotificacion(
						notificacionDTO, audienciaDTO, new InvolucradoDTO(involucradoId));
			}
	
			String xml = null;
			PrintWriter pw = null;
			XStream converter=new XStream(); 			converter.alias("NotificacionDTO", NotificacionDTO.class);
			xml = converter.toXML(notificacion);
			response.setContentType("text/xml");
			pw = response.getWriter();
			pw.print(xml);
			pw.flush();
			pw.close();
			logger.info("NotificacionID creada::::::::::::::::::::::::"
					+ notificacion);
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		}
		return null;	
    }
    
    /**
     * Metodo para actualizar los datos de una notificacion (generico)
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ActionForward actualizarNotificacion(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	try {
			
			logger.info("*******************ACTUALIZAR NOTIFICACION********************************");
			logger.info("VERIFICANDO PARAMETOS:::::::::::::::::::::::::::::::::::::::::::::::::::::");
			
			Long documentoId = NumberUtils.toLong(request.getParameter("idNotificacion"),0L);
			logger.info("id documentoId.... "+ documentoId);
			
			String fechaRecepcion = request.getParameter("fechaRecepcion");
			logger.info("id fechaRecepcion.... "+ fechaRecepcion);
			
			String horaRecepcion = request.getParameter("horaRecepcion");
			logger.info("id horaRecepcion.... "+ horaRecepcion);
			
			Long estatusNotifId = NumberUtils.toLong(request.getParameter("estatusNotifId"),0L);
			logger.info("id estatusNotifId.... "+ estatusNotifId);
			
			String motivoCancelacion = request.getParameter("motivoCancelacion");
			logger.info("id motivoCancelacion.... "+ motivoCancelacion);
			
			NotificacionDTO notificacionDTO = new NotificacionDTO();
			
			if(documentoId != null && documentoId > 0L){
				notificacionDTO.setDocumentoId(documentoId);
			}
			
			if (fechaRecepcion != null && horaRecepcion != null
					&& !fechaRecepcion.isEmpty() && !horaRecepcion.isEmpty()) {
				
				Date fecha = DateUtils.obtener(fechaRecepcion, horaRecepcion);
				notificacionDTO.setFechaCitado(fecha);
			}
			
			if(estatusNotifId != null && estatusNotifId > 0L){
				ValorDTO estatus = new ValorDTO();
				estatus.setIdCampo(estatusNotifId);
				notificacionDTO.setEstatus(estatus);
			}
			
			if(motivoCancelacion != null && !motivoCancelacion.isEmpty()){
				notificacionDTO.setMotivo(motivoCancelacion);
			}
			
			/**
			 * Se pueden agregar los otros campos de la notificacion,
			 * solo verificar que se encuentren en el transformer
			 */
			
			notificacionDelegate.actualizarNotificacion(notificacionDTO);
			
			String xml = null;
			XStream converter=new XStream(); 			converter.alias("NotificacionDTO", NotificacionDTO.class);
			xml = converter.toXML(notificacionDTO);
			escribirRespuesta(response, xml);
			
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		}
		return null;	
    }
    
	/**
	 * M&eacute;todo que env&iacute; la notificaci&oacute;n de que se program&oacute; una
	 * audiencia a la agenda del funcionario local o externo.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward enviarNotificacionByWebService(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			logger.info("*******************EJECUTANDO ACTION:enviarNotificacionByWebService*******");
			logger.info("VERIFICANDO PARAMETOS:::::::::::::::::::::::::::::::::");
			logger.info("audienciaId=" + request.getParameter("audienciaId"));
			logger.info("documentoId=" + request.getParameter("documentoId"));
			logger.info("institucionId="
					+ request.getParameter("institucionId"));
			logger.info("cveFuncionario="
					+ request.getParameter("cveFuncionario"));

			Long audienciaId = NumberUtils.toLong(
					request.getParameter("audienciaId"), 0);
			Long notificacionId = NumberUtils.toLong(
					request.getParameter("documentoId"), 0);
			Long institucionId = NumberUtils.toLong(
					request.getParameter("institucionId"), 0);
			Long cveFuncionario = NumberUtils.toLong(
					request.getParameter("cveFuncionario"), 0);

			if (notificacionId <= 0L || audienciaId <= 0L
					|| institucionId <= 0L || cveFuncionario <= 0L) {
				throw new NSJPNegocioException(
						CodigoError.PARAMETROS_INSUFICIENTES);
			}

			NotificacionDTO notificacionDTO = new NotificacionDTO();
			notificacionDTO.setDocumentoId(notificacionId);

			AudienciaDTO audienciaDTO = new AudienciaDTO(audienciaId);

			FuncionarioDTO funcionarioDTO = new FuncionarioDTO(cveFuncionario);
			funcionarioDTO
					.setInstitucion(new ConfInstitucionDTO(institucionId));

			notificacionDelegate.enviarNotificacion(notificacionDTO,
					audienciaDTO, funcionarioDTO);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
    
}
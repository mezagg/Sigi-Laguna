package mx.gob.segob.nsjp.web.defensor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.tarea.TipoEvento;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.delegate.funcionario.FuncionarioDelegate;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.tarea.EventoCitaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistroVacacionesIncapacidadesAction extends GenericAction {
	
	private static final Logger log = Logger.getLogger(RegistroVacacionesIncapacidadesAction.class);
	
	@Autowired
	private FuncionarioDelegate funcionarioDelegate;
	
	/**
	 * M&eacute;todo utilizado para realizar la consulta de defensores activos
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return succes - En caso de que el proceso sea correcto
	 * @throws IOException
	 *             En caso de obtener una exception
	 */
	public ActionForward consultarDefensoresActivos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {

			log.info("*****CONSULTAR DEFENSORES ACTIVOS ACTION***********");
			log.info(":::::::::VERIFICANDO PARAMETROS:::::::::::::::::::::");
			log.info("index..........." + request.getParameter("index"));

			UsuarioDTO usuarioFirmado = super.getUsuarioFirmado(request);

			List<FuncionarioDTO> funcionarioDTOs = funcionarioDelegate
					.consultarDefensoresActivosPorDistrito(usuarioFirmado
							.getFuncionario().getDiscriminante().getDistrito());

			if (log.isDebugEnabled()) {
				log.debug("Funcionarios" + funcionarioDTOs);
			}

			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();

			writer.print("<rows>");

			// Configuracion del Paginador
			String paginacion = PaginadorUtil.obtenerPaginacionAutomatica();
			writer.print(paginacion);

			if (!funcionarioDTOs.isEmpty()) {

				for (FuncionarioDTO funcionarioDTO : funcionarioDTOs) {

					if (request.getParameter("index") != null
							&& (Integer.parseInt(request.getParameter("index")) == 1)) {

						writer.print("<row id='"
								+ funcionarioDTO.getClaveFuncionario() + "'>");

						// Tipo de defensa
						if (funcionarioDTO.getTipoEspecialidad() != null) {
							writer.print("<cell>"
									+ funcionarioDTO.getTipoEspecialidad()
											.getValor() + "</cell>");
						} else {
							writer.print("<cell> --- </cell>");
						}
						// Nombre Funcionario
						writer.print("<cell>"
								+ funcionarioDTO.getNombreFuncionario()
								+ " "
								+ funcionarioDTO
										.getApellidoPaternoFuncionario()
								+ " "
								+ funcionarioDTO
										.getApellidoMaternoFuncionario()
								+ "</cell>");
						// Especialidad
						if (funcionarioDTO.getEspecialidad() != null) {
							writer.print("<cell>"
									+ funcionarioDTO.getEspecialidad()
											.getValor() + "</cell>");
						} else {
							writer.print("<cell> --- </cell>");
						}

						writer.print("</row>");
					} else {

						writer.print("<row id='"
								+ funcionarioDTO.getClaveFuncionario() + "'>");

						// Nombre Funcionario
						writer.print("<cell>"
								+ funcionarioDTO.getNombreFuncionario()
								+ "</cell>");
						writer.print("<cell>"
								+ funcionarioDTO
										.getApellidoPaternoFuncionario()
								+ "</cell>");
						writer.print("<cell>"
								+ funcionarioDTO
										.getApellidoMaternoFuncionario()
								+ "</cell>");
						writer.print("</row>");
					}
				}
			}
			writer.print("</rows>");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
		
		/**
		 * Metodo utilizado para realizar el registro de vacaciones e inactividades
		 * a la JSP de atencionSolicitudAudienciaNotificador
		 * @param mapping
		 * @param form
		 * @param request 
		 * @param response
		 * @return succes - En caso de que el proceso sea correcto
		 * @throws IOException En caso de obtener una exception
		 */
		public ActionForward registrarVacacionesIncapacidad(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				
				log.info("ENTRA A REGISTRAR VACACIONES O INCAPACIDAD");
				
				String idFuncionario = request.getParameter("claveFuncionario");
				log.info("llega id funcionario:" + idFuncionario);
					
				String nombreEvento = request.getParameter("nombreEvento");
				log.info("llega nombre evento:" + nombreEvento);
					
				Long tipoEvento = Long.parseLong(request.getParameter("tipoRegistro"));
				log.info("llega tipo:" + tipoEvento);
									
				String fechaInicio = request.getParameter("fechaInicio");
				log.info("llega:" + fechaInicio);
					
				String fechaFin = request.getParameter("fechaFin");
				log.info("llega:" + fechaFin);			
				
				DateFormat formato = new SimpleDateFormat("dd/MM/yy");
				Date fechaEventoInicio = null;
				Date fechaEventoFin = null;
				
				try {
					fechaEventoInicio = formato.parse(fechaInicio);
					fechaEventoFin = formato.parse(fechaFin);		
				
				} catch (ParseException e) {
				
					e.printStackTrace();
				}
				
				FuncionarioDTO funcionario = new FuncionarioDTO();
				funcionario.setClaveFuncionario(Long.parseLong(idFuncionario));
				
				ValorDTO valorDTO = new ValorDTO();
								
				if (tipoEvento == 1) {
					log.info("entra a vacaciones");
					valorDTO.setIdCampo(TipoEvento.VACACIONES.getValorId());
					log.info("valor de:" + TipoEvento.VACACIONES.getValorId());
		
				}else{
					log.info("entra a incapacidad");
					valorDTO.setIdCampo(TipoEvento.INCAPACIDAD.getValorId());
					log.info("valor de:" + TipoEvento.INCAPACIDAD.getValorId());					
					
				}		
				
				super.getUsuarioFirmado(request);
				
				EventoCitaDTO periodo = new EventoCitaDTO();
				periodo.setFechaInicioEvento(fechaEventoInicio);
				periodo.setFechaFinEvento(fechaEventoFin);
				periodo.setTipoEvento(valorDTO);
				periodo.setNombreEvento(nombreEvento);
				periodo.setEsAlertaAlarma(false);
				
				try{
					funcionarioDelegate.registrarVacacionesIncapacidad(funcionario, periodo, super.getUsuarioFirmado(request));
					
					XStream converter=new XStream();
					converter.alias("mensaje",String.class);
					String xml = converter.toXML("El registro se ha realizado con exito");
					escribirRespuesta(response, xml);
					
				}catch(NSJPNegocioException e){
					if(e.getCodigo() == CodigoError.EJCUCION_OPERACION_ESTADO_INCORRECTO){
						
						converter.alias("mensaje",String.class);
						String xml = converter.toXML("No se puede registrar el evento por que el periodo coincide con un periodo registrado");
						escribirRespuesta(response, xml);
						
					}
				}
								

			} catch (Exception e) {
			    log.error(e.getMessage(), e);
			}
			return null;
		}
				
}

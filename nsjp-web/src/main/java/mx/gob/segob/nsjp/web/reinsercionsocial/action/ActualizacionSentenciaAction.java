/**
* Nombre del Programa 		: ActualizacionSentenciaAction.java
* Autor 					: EdgarAT
* Compania 					: Ultrasist
* Proyecto 					: NSJP 								Fecha: 13/12/2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 								Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 								Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.web.reinsercionsocial.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.segob.nsjp.comun.enums.expediente.EstatusExpediente;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.remisiones.CatTipoRemision;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.delegate.expediente.ExpedienteDelegate;
import mx.gob.segob.nsjp.delegate.programa.AsignacionProgramaDelegate;
import mx.gob.segob.nsjp.delegate.reinsercion.ReinsercionDelegate;
import mx.gob.segob.nsjp.delegate.sentencia.SentenciaDelegate;
import mx.gob.segob.nsjp.delegate.solicitud.SolicitudDelegate;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.programas.CatTipoRemisionDTO;
import mx.gob.segob.nsjp.dto.programas.RemisionDTO;
import mx.gob.segob.nsjp.dto.sentencia.SentenciaDTO;
import mx.gob.segob.nsjp.dto.solicitud.SolicitudDTO;
import mx.gob.segob.nsjp.web.base.action.GenericAction;
import mx.gob.segob.nsjp.web.reinsercionsocial.form.DatosGeneralesReinsercionForm;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarAT
 *
 */
public class ActualizacionSentenciaAction extends GenericAction {

	private static final Logger LOGGER = Logger.getLogger(ActualizacionSentenciaAction.class);
	
	private static final String FWD_CONSULTAR_DATOS_GENERALES = "consultarDatosGenerales";
	private static final String PARAM_ID_SENTENCIA = "sentenciaId";
	private static final String PARAM_CAMBIO_EXITO = "cambioExitoso";
	private static final String PARAM_ENVIAR_A = "enviarA";
	private static final String PARAM_ID_INSTITUCION = "idInstitucion";
	private static final String PARAM_ID_NUMERO_EXPEDIENTE = "idNumeroExpediente";
	private static final String PARAM_ID_ESTATUS_EXPEDIENTE = "idEstatusExpediente";
	
	@Autowired
	private ReinsercionDelegate reinsercionDelegate;
	
	@Autowired
	private SolicitudDelegate solicitudDelegate;
	
	@Autowired
	private SentenciaDelegate sentenciaDelegate;
	
	@Autowired
	private AsignacionProgramaDelegate asignacionProgramaDelegate;
	
	@Autowired
	private ExpedienteDelegate expedienteDelegate;
	
	/**
	 * M&eacute;todo que lleva a cabo el registro de las remisiones que se van a acreditar 
	 * para una sentencia en espec&iacute;fico. Una vez que se lleva a cabo el registro de 
	 * la remisi&oacute;n, se actualiza el estatus de la solicitud asociada con la misma a
	 * cerrado, para de esta manera, asegurar que solamente se registra una remisi&oacute;n
	 * por cada solicitud de autorizaci&oacute;n
	 * 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return ActionForward - Nueva p&aacute;gina que desplegar&aacute; la informaci&oacute;n
	 * 						   del resultado de la persistencia.
	 * @throws IOException - En el caso de que se presente un error al momento de escribir la
	 * 						 respuesta.
	 */
	public ActionForward actualizarSentencia (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		Long institucionId = NumberUtils.toLong(request.getParameter(PARAM_ID_INSTITUCION));	
		
		DatosGeneralesReinsercionForm forma = (DatosGeneralesReinsercionForm) form;
		Long sentenciaId = NumberUtils.toLong(forma.getSentenciaId(), 0L);
		boolean aplicaRemisionParcial = forma.isRemisionParcial();
		boolean aplicaLibertadCondicional = forma.isLibertadCondicional();
		SentenciaDTO sentencia = new SentenciaDTO();
		sentencia.setSentenciaId(sentenciaId);
		List<RemisionDTO> remisiones = null;
		
		
		try {
			
			if (forma.getEstatusNumExpId() == EstatusExpediente.RECHAZADO.getValorId().longValue()){
				sentencia = asignacionProgramaDelegate.consultarSentencia(sentencia);
				sentencia.setDfechaInicioPena(DateUtils.obtener(forma.getFechaInicioPenaSTR()));
				sentencia.setDfechaFinPena(DateUtils.obtener(forma.getFechaFinPenaSTR()));
				sentencia.setDfechaCreacion(new Date());
				sentenciaDelegate.realizarAltasCambiosASentencia(sentencia);
				sentencia.getNumeroExpedienteDTO().setEstatus(new ValorDTO(EstatusExpediente.ENVIADO.getValorId()));
				expedienteDelegate.actualizarEstatusExpediente(sentencia.getNumeroExpedienteDTO());
			}
			
			if (aplicaRemisionParcial){
				remisiones = new ArrayList<RemisionDTO>();
				RemisionDTO remisionParcial = crearRemision(CatTipoRemision.REMISION_PARCIAL_DE_LA_PENACONDICIONAL, forma, sentencia);
				reinsercionDelegate.registrarRemision(remisionParcial);
				actualizaEstatusSolicitud(forma.getIdSolRPP());
				remisiones.add(remisionParcial);
			}

			if (aplicaLibertadCondicional){
				RemisionDTO remisionLC = crearRemision(CatTipoRemision.CONDICIONAL, forma, sentencia);
				reinsercionDelegate.registrarRemision(remisionLC);
				actualizaEstatusSolicitud(forma.getIdSolLC());
				remisiones = agregaRemisionALista(remisiones, remisionLC);
			}

			if (forma.getIdSolMulta() > 0L
					&& forma.isMultaPagada()){
				RemisionDTO multa = crearRemision(CatTipoRemision.MULTA, forma, sentencia);
				multa.setRemisionId(forma.getIdMulta());
				reinsercionDelegate.actualizarRemision(multa);
				actualizaEstatusSolicitud(forma.getIdSolMulta());
				remisiones = agregaRemisionALista(remisiones, multa);
			}else if (forma.getEstatusNumExpId() == EstatusExpediente.RECHAZADO.getValorId().longValue()
					&& forma.isAplicaMulta()){	
				RemisionDTO multa = crearRemision(CatTipoRemision.MULTA, forma, sentencia);
				multa.setImontoDanioReparado(Float.parseFloat(forma.getMontoMulta()));
				multa.setFechaAutorizacion(new Date());
				if (multa.getRemisionId() != null 
						&& multa.getRemisionId() > 0L){
					reinsercionDelegate.actualizarRemision(multa);
				}else{
					reinsercionDelegate.registrarRemision(multa);					
				}
				remisiones = agregaRemisionALista(remisiones, multa);
			}
			
			if (forma.getIdSolRD() > 0L
					&& forma.isReparacionPagada()){
				RemisionDTO reparacion = crearRemision(CatTipoRemision.REPARACION_DEL_DANIO, forma, sentencia);
				reparacion.setRemisionId(forma.getIdRD());
				reinsercionDelegate.actualizarRemision(reparacion);
				actualizaEstatusSolicitud(forma.getIdSolRD());
				remisiones = agregaRemisionALista(remisiones, reparacion);
			}else if (forma.getEstatusNumExpId() == EstatusExpediente.RECHAZADO.getValorId().longValue()
					&& forma.getReparacionDanio()){
				RemisionDTO reparacion = crearRemision(CatTipoRemision.REPARACION_DEL_DANIO, forma, sentencia);
				reparacion.setImontoDanioReparado(Float.parseFloat(forma.getMontoDanioReparado()));
				reparacion.setFechaAutorizacion(new Date());
				if (reparacion.getRemisionId() != null
						&& reparacion.getRemisionId() > 0L){
					reinsercionDelegate.actualizarRemision(reparacion);
				}else{
					reinsercionDelegate.registrarRemision(reparacion);					
				}
				remisiones = agregaRemisionALista(remisiones, reparacion);
			}
			
			reinsercionDelegate.eliminarRemisionesNoActualizadas(remisiones, sentencia);
			
			if (institucionId > 0L) {
				sentencia.setCnumeroCausa(forma.getCausa());
				sentencia.setCnus(forma.getNus());
				/**
				 * Debemos de consultar la lista de remisiones complementarias a la 
				 * que estamos guardando para, tener completud de datos en SSP
				 */
				List<RemisionDTO> remisionesComplementarias =  reinsercionDelegate
						.consultarComplementoRemisiones(remisiones, sentencia);
				if(remisionesComplementarias != null && !remisionesComplementarias.isEmpty()){
					remisiones.addAll(remisionesComplementarias);
				}
				sentencia.setRemisions(remisiones);
				
				sentenciaDelegate.actualizarSentenciaExterna(sentencia,
						Instituciones.getByValor(institucionId));
			}
			
		} catch (NSJPNegocioException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return new ActionForward(mapping.findForward(FWD_CONSULTAR_DATOS_GENERALES).getPath()+
				"?"+PARAM_ID_SENTENCIA+"="+ sentencia.getSentenciaId() +
				"&"+PARAM_CAMBIO_EXITO+"="+Boolean.TRUE+
				"&"+PARAM_ENVIAR_A+"="+DatosGeneralesReinsercionAction.SENTENCIAS_JUEZ_EJECUCION, false);
	}
	
	/**
	 * M&eacute;todo que lleva a cabo el registro de las remisiones que se van a acreditar 
	 * para una sentencia en espec&iacute;fico. Una vez que se lleva a cabo el registro de 
	 * la remisi&oacute;n, se actualiza el estatus de la solicitud asociada con la misma a
	 * cerrado, para de esta manera, asegurar que solamente se registra una remisi&oacute;n
	 * por cada solicitud de autorizaci&oacute;n
	 * 
	 * @param mapping - Mapeo de struts para asignar el jsp siguiente a ejecutar.
	 * @param form - Forma de struts con los datos que se muestran en la forma delJSP.
	 * @param request - Objeto de java que representa la petici&oacute;n en HTML enviada.
	 * @param response - Objeto de java que representa la respuesta de HTML enviada.
	 * @return ActionForward - Nueva p&aacute;gina que desplegar&aacute; la informaci&oacute;n
	 * 						   del resultado de la persistencia.
	 * @throws IOException - En el caso de que se presente un error al momento de escribir la
	 * 						 respuesta.
	 */
	public ActionForward actualizarEstatusSentencia (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		Long idInstitucion = NumberUtils.toLong(request.getParameter(PARAM_ID_INSTITUCION));
		Long idNumeroExpediente = NumberUtils.toLong(request.getParameter(PARAM_ID_NUMERO_EXPEDIENTE));
		Long idEstatusExpediente = NumberUtils.toLong(request.getParameter(PARAM_ID_ESTATUS_EXPEDIENTE));
		
		List<Long> idsNumExp = new ArrayList<Long>();
		idsNumExp.add(idNumeroExpediente);
		
		try {
			Map<Long, SentenciaDTO> mapaSentencias = sentenciaDelegate.consultarSentenciasPorIdsNumExp(idsNumExp);
			if (mapaSentencias != null){
				SentenciaDTO sentencia = mapaSentencias.get(idNumeroExpediente);
				if (sentencia != null){
					ValorDTO nuevoEstatus = new ValorDTO(idEstatusExpediente);
					sentencia.getNumeroExpedienteDTO().setEstatus(nuevoEstatus);
					if (idInstitucion > 0L){
						sentenciaDelegate.actualizarSentenciaExterna(sentencia, Instituciones.getByValor(idInstitucion));
					}
				}
			}
		} catch (NSJPNegocioException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * M&eacute;todo que lleva a cabo la actualizaci&oacute;n del estatus de una solicitud 
	 * registrada dentro de la base de datos, se cambia el estatus a cerrada, en base al 
	 * identificador pasado como par&aacute;metro.
	 * @param solicitudId - Identificador de la solicitud de la cual se cambiar&aacute; el 
	 * 						estatus a cerrada.
	 */
	private void actualizaEstatusSolicitud(Long solicitudId){
		SolicitudDTO solicitud = new SolicitudDTO();
		solicitud.setDocumentoId(solicitudId);
		try {
			solicitudDelegate.actualizaEstatusSolicitud(solicitud, EstatusSolicitud.CERRADA);
		} catch (NSJPNegocioException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	/**
	 * M&eacute;todo utilitario que agrega una remisi&oacute;n a una lista de remisiones 
	 * previamente existentes, o en el caso de que la lista no haya sido instanciada, 
	 * la misma se crea y se agrega la nueva remisi&oacute;n.
	 * @param remisiones - List<RemisionDTO> que contiene las remisiones asociadas a la 
	 * 					   sentencia o en su defecto <code>null</code>.
	 * @param nuevaRemision - La remisi&oacute;n que se agregar&aacute; a la lista ingresada
	 * 						  como par&aacute;metro.
	 * @return remisiones - List<RemisionDTO> con las remisiones que se van a asociar a la 
	 * 						sentencia.
	 */
	private List<RemisionDTO> agregaRemisionALista(List<RemisionDTO> remisiones, RemisionDTO nuevaRemision){
		if (remisiones == null){
			remisiones = new ArrayList<RemisionDTO>();
		}
		remisiones.add(nuevaRemision);
		return remisiones;
	}
	
	/**
	 * M&eacute;todo utilitario que lleva a cabo la creaci&oacute;n de las distintas remisiones 
	 * dependiendo del tipo al cual pertenece cada una de ellas, asimismo asigna 
	 * informaci&oacute;n adicional a cada una de las remisiones con datos que viajan a 
	 * trav&eacute;s de la forma desde la vista. 
	 * @param catTipoRemision - Tipo de remisi&oacute;n con el cual se crear&aacute; la nueva 
	 * 							remisi&oacute;n.
	 * @param forma - Forma de struts desde la cual se obtienen los datos a vaciar en cada una
	 * 				  de las nuevas remisiones.
	 * @param sentenciaDTO - Sentencia con la cual se van a asociar las remisiones una vez 
	 * 						 creadas dentro de la base de datos.
	 * @return remision - RemisionDTO creada con los datos del tipo de remisi&oacute;n 
	 * 					  correspondiente y datos obtenidos desde la forma de struts.
	 */
	private RemisionDTO crearRemision(CatTipoRemision catTipoRemision, 
			DatosGeneralesReinsercionForm forma, SentenciaDTO sentenciaDTO){
		RemisionDTO remision = null;
		if (catTipoRemision != null){
			remision = new RemisionDTO();
			CatTipoRemisionDTO tipoRemision = new CatTipoRemisionDTO();
			tipoRemision.setCatTipoRemisionId(catTipoRemision.getId());
			remision.setCatTipoRemisionDTO(tipoRemision);
			remision.setSentencia(sentenciaDTO);
			try {
				switch (catTipoRemision){
				case REMISION_PARCIAL_DE_LA_PENACONDICIONAL:
					remision.setCumplida(Boolean.TRUE);
					remision.setFechaAutorizacion(DateUtils.obtener(forma.getFechaRemisionSTR()));
					break;
				case CONDICIONAL:
					remision.setCumplida(Boolean.TRUE);
					remision.setFechaAutorizacion(new Date());
					break;
				case MULTA:
					remision.setCumplida(forma.isMultaPagada());
					if (forma.getIdMulta() > 0L){
						remision.setRemisionId(forma.getIdMulta());
					}
					break;
				case REPARACION_DEL_DANIO:
					remision.setCumplida(forma.isReparacionPagada());
					if (forma.getIdRD() > 0L){
						remision.setRemisionId(forma.getIdRD());
					}
					break;
				}
			} catch (NSJPNegocioException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return remision;
	}
}

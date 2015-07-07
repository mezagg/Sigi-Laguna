package mx.gob.segob.nsjp.service.solicitud.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.ConsecutivosUtil;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dao.audiencia.AudienciaDAO;
import mx.gob.segob.nsjp.dao.catalogo.ValorDAO;
import mx.gob.segob.nsjp.dao.documento.DocumentoDAO;
import mx.gob.segob.nsjp.dao.documento.MandamientoDAO;
import mx.gob.segob.nsjp.dao.documento.NotificacionDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.mandamiento.MandamientoPersonaDAO;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDAO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GenerarFolioSolicitudServiceImpl implements
		GenerarFolioSolicitudService {
	
	//Logger de la clase
	Logger log = Logger.getLogger(GenerarFolioSolicitudServiceImpl.class);

	@Autowired
	private SolicitudDAO solicitudDAO;

	@Autowired
	private NotificacionDAO notificacionDAO;

	@Autowired
	private DocumentoDAO documentoDAO;

	@Autowired
	private ConfInstitucionDAO confInstitucionDAO;

	@Autowired
	AudienciaDAO audienciaDAO;

	@Autowired
	MandamientoDAO mandamientoDAO;	

	@Autowired
	ValorDAO valorDAO;
	
	@Autowired
	MandamientoPersonaDAO mandamientoPersonaDAO;

	private static Long folioSol = 0L;
	private static Long folioNot = 0L;
	private static Long folioDoc = 0L;
	private static Long folioMand = 0L;
	private static Long folioMandPer = 0L;
	
	
	private static ConfInstitucion institucion = null;

	public synchronized String generarFolioSolicitud()
			throws NSJPNegocioException {
		if (folioSol == 0) {
			String ultimo = solicitudDAO.obtenerUltimoFolioSolicitud();
			if (ultimo != null && !ultimo.isEmpty()) {
				folioSol = Long.parseLong(ultimo.split("/")[1].substring(4));
			}
		}

		if (institucion == null) {
			institucion = confInstitucionDAO.consultarInsitucionActual();
		}

		String format1 = "00000";
		DecimalFormat fm1 = new DecimalFormat(format1,
				new DecimalFormatSymbols(Locale.US));

		return institucion.getMonograma() + "/"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ fm1.format(++folioSol);
	}

	@Override
	public synchronized String generarFolioNotificacion()
			throws NSJPNegocioException {
		if (folioNot == 0) {
			String ultimo = notificacionDAO.obtenerUltimoFolioNotificacion();
			if (ultimo != null && !ultimo.isEmpty()) {
				folioNot = Long.parseLong(ultimo.split("/")[1].substring(4));
			}
		}

		if (institucion == null) {
			institucion = confInstitucionDAO.consultarInsitucionActual();
		}

		String format1 = "00000";
		DecimalFormat fm1 = new DecimalFormat(format1,
				new DecimalFormatSymbols(Locale.US));

		return institucion.getMonograma() + "/"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ fm1.format(++folioNot);
	}

	@Override
	public synchronized String generarFoliodDocumento()
			throws NSJPNegocioException {

		if (institucion == null) {
			institucion = confInstitucionDAO.consultarInsitucionActual();
		}

		if (folioDoc == 0 && institucion != null) {
			String ultimo = documentoDAO
					.obtenerUltimoFolioDocumento(institucion);
			if (ultimo != null && !ultimo.isEmpty()) {
				folioDoc = Long.parseLong(ultimo);
			}
		}

		String format1 = "00000";
		DecimalFormat fm1 = new DecimalFormat(format1,
				new DecimalFormatSymbols(Locale.US));

		return institucion.getMonograma() + "/"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ fm1.format(++folioDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.segob.nsjp.service.solicitud.GenerarFolioSolicitudService#
	 * generarFolioAudiencia()
	 */
	@Override
	public synchronized String generarFolioAudiencia()
			throws NSJPNegocioException {
		if (folioDoc == 0) {
			String ultimo = audienciaDAO.obtenerUltimoFolioAudiencia();
			if (ultimo != null && !ultimo.isEmpty()) {
				folioDoc = Long.parseLong(ultimo.split("/")[1].substring(4));
			}
		}

		if (institucion == null) {
			institucion = confInstitucionDAO.consultarInsitucionActual();
		}

		String format1 = "00000";
		DecimalFormat fm1 = new DecimalFormat(format1,
				new DecimalFormatSymbols(Locale.US));

		return institucion.getMonograma() + "/"
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ fm1.format(++folioDoc);
	}

	@Override
	public synchronized String generarFolioMandamientoJudicial(
			Long tipoMandamiento, Date fechaResolutivo, String numeroDeCausa)
			throws NSJPNegocioException {

		// validacion de parametros
		if (tipoMandamiento == null || tipoMandamiento <= 0L
				|| fechaResolutivo == null || numeroDeCausa == null
				|| numeroDeCausa.isEmpty()) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Valor tipoMandamientoValor = valorDAO.read(tipoMandamiento);

		if (tipoMandamientoValor == null
				|| tipoMandamientoValor.getRegistro() == null
				|| tipoMandamientoValor.getRegistro().getRegistroId() == null) {
			throw new NSJPNegocioException(
					CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		// RECUPERAR EL VALOR DE LA ABREVIATURA
		
		Valor abreviaturaMandamiento = null;

		try {
			abreviaturaMandamiento = valorDAO
					.consultarAbreviaturaMandamiento(tipoMandamientoValor);
		} catch (Exception e) {
			log.error("NO SE HA PODIDO CONSULTAR LA ABREVIACION DEL TIPO MANDAMIENTO***");
			throw new NSJPNegocioException(CodigoError.FALLA_OPERACION_BD);
		}
		    

		if (abreviaturaMandamiento == null
				|| abreviaturaMandamiento.getValor() == null
				|| abreviaturaMandamiento.getValor().isEmpty()) {
			log.error("NO EXISTE LA ABREVIACION DEL TIPO MANDAMIENTO***");
			throw new NSJPNegocioException(

			CodigoError.INFORMACION_PARAMETROS_ERRONEA);
		}

		if (folioMand == 0) {
			Long ultimo = mandamientoDAO
					.obtenerUltimoFolioMandamientoPorCausa(numeroDeCausa);
			if (ultimo != null) {
				folioMand = ultimo;
			}
		}

		DecimalFormat fmtConsec = new DecimalFormat(
				ConsecutivosUtil.FORMATO_CONSECUTIVO_MANDAMIENTO,
				new DecimalFormatSymbols(Locale.US));

		return numeroDeCausa + ConsecutivosUtil.SEPARADOR_FOLIO_MANDAMIENTO
				+ DateUtils.formatearParaFolio(fechaResolutivo)
				+ ConsecutivosUtil.SEPARADOR_FOLIO_MANDAMIENTO
				+ abreviaturaMandamiento.getValor()
				+ ConsecutivosUtil.SEPARADOR_FOLIO_MANDAMIENTO
				+ fmtConsec.format(++folioMand);
	}

	@Override
	public String generarFolioInterInstitucionalMandamientoPersona()
			throws NSJPNegocioException {

		Long ultimoFolio = null;
		
		if (folioMandPer == 0) {
			
			try {
				ultimoFolio = mandamientoPersonaDAO
						.obtenerUltimoFolioMandamientoPersona();
			} catch (Exception e) {
				log.error("NO SE HA PODIDO EL ULTIMO CONSECUTIVO DEL MANDAMIENTO PERSONA***");
				throw new NSJPNegocioException(CodigoError.FALLA_OPERACION_BD);
			}

			if (ultimoFolio != null) {
				folioMandPer = ultimoFolio;
			}
		}

		DecimalFormat fmtConsec = new DecimalFormat(
				ConsecutivosUtil.FORMATO_CONSECUTIVO_MANDAMIENTO_PERSONA,
				new DecimalFormatSymbols(Locale.US));

		return Instituciones.PJ.toString()
				+ ConsecutivosUtil.SEPARADOR_PREFIJOS
				+ ConsecutivosUtil.PREFIJO_MANDAMIENTO_JUDICIAL
				+ Calendar.getInstance().get(Calendar.YEAR)
				+ ConsecutivosUtil.SEPARADOR_CONSECUTIVO
				+ fmtConsec.format(++folioMandPer);
	}
}
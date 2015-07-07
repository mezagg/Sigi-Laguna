/**
 * Nombre del Programa	: EnviarReplicaCasoServiceImpl.java
 * Autor                : vaguirre
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 21 Aug 2011
 * Marca de cambio      : N/A
 * Descripcion General  : Implementacion para enviar la replica del caso a las dem&aacute;s intituciones
 * Programa Dependiente : N/A
 * Programa Subsecuente : N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                                 Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.caso.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.relacion.Relaciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.URLUtils;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.institucion.ConfInstitucionDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.organizacion.OrganizacionDAO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.Organizacion;
import mx.gob.segob.nsjp.service.caso.EnviarReplicaCasoService;
import mx.gob.segob.nsjp.service.expediente.impl.transform.ExpedienteTransformer;
import mx.gob.segob.nsjp.service.infra.ClienteGeneralService;
import mx.gob.segob.nsjp.service.involucrado.impl.transform.InvolucradoTransformer;
import mx.gob.segob.nsjp.service.organizacion.impl.transform.OrganizacionTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion para enviar la replica del caso a las dem&aacute;s
 * intituciones.
 * 
 * @version 1.0
 * @author vaguirre
 * 
 */
@Service
@Transactional
public class EnviarReplicaCasoServiceImpl implements EnviarReplicaCasoService {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(EnviarReplicaCasoServiceImpl.class);

	@Autowired
	private ConfInstitucionDAO institucionDao;

	@Autowired
	private ClienteGeneralService clienteWS;

	@Autowired
	private ExpedienteDAO expDao;

	@Autowired
	private InvolucradoDAO involucradoDao;

	@Autowired
	private OrganizacionDAO organizacionDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.segob.nsjp.service.caso.EnviarReplicaCasoService#enviarReplicaCaso
	 * (mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO)
	 */
	@Override
	public void enviarReplicaCaso(ExpedienteDTO expdienteConCaso)
			throws NSJPNegocioException {

		logger.debug("ENVIAR REPLICA DE CASO SERVICE");

		if (expdienteConCaso == null
				|| expdienteConCaso.getExpedienteId() == null
				|| expdienteConCaso.getExpedienteId() <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Expediente exp = this.expDao.read(expdienteConCaso.getExpedienteId());

		ExpedienteDTO expParam = ExpedienteTransformer
				.transformarExpedienteBasico(exp);
		expParam.getCasoDTO()
				.setFechaApertura(exp.getCaso().getFechaApertura());

		List<Involucrado> tempo = new ArrayList<Involucrado>();

		tempo.addAll(this.involucradoDao.obtenerInvByIdExpAndCalidad(
				expdienteConCaso.getExpedienteId(),
				Calidades.VICTIMA_PERSONA.getValorId(), null));
		tempo.addAll(this.involucradoDao.obtenerInvByIdExpAndCalidad(
				expdienteConCaso.getExpedienteId(),
				Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId(), null));
		tempo.addAll(this.involucradoDao.obtenerInvByIdExpAndCalidad(
				expdienteConCaso.getExpedienteId(),
				Calidades.PROBABLE_RESPONSABLE_ORGANIZACION.getValorId(), null));
		tempo.addAll(this.involucradoDao.obtenerInvByIdExpAndCalidad(
				expdienteConCaso.getExpedienteId(),
				Calidades.DENUNCIANTE.getValorId(), null));

		InvolucradoDTO invoDto = null;
		for (Involucrado invo : tempo) {
			invoDto = InvolucradoTransformer.transformarInvolucradoBasico(invo);
			if (invoDto.getCalidadDTO().getCalidades()
					.equals(Calidades.DENUNCIANTE)) {
				invoDto.getCalidadDTO().setCalidades(Calidades.VICTIMA_PERSONA);
			}
			// Agregamos la organizacion como dato basico para mostrar el nombre
			// de la organizacion
			if (invoDto.getTipoPersona() != null
					&& invoDto.getTipoPersona().equals(0L)) {
				Organizacion organizacion = organizacionDAO
						.obtenerOrganizacionByRelacion(
								invoDto.getElementoId(),
								new Long(Relaciones.ORGANIZACION_INVOLUCRADA
										.ordinal()));
				invoDto.setOrganizacionDTO(OrganizacionTransformer
						.transformarOrganizacion(organizacion));
			}
			expParam.addInvolucradoDTO(invoDto);
		}

		List<ConfInstitucion> instituciones = this.institucionDao
				.consultarDemasIntituciones();

		if (instituciones == null || instituciones.isEmpty()) {
			logger.error("NO EXISTEN INSTITUCIONES..........");
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		for (ConfInstitucion inst : instituciones) {
			try {
				if (URLUtils.validaURLWSDL(inst.getUrlInst())) {
					logger.debug("Enviando a :: " + inst.getNombreInst()
							+ " atraves de " + inst.getUrlInst());
					this.clienteWS.enviarReplicaCaso(expParam, inst);
					logger.debug("Envio a " + inst.getNombreInst() + " [OK]");
				}
			} catch (Exception e) {
				logger.error(
						"No se pudo replicar la informaci&oacute;n del caso en "
								+ inst.getNombreInst()
								+ ". Se obtuvo el siguiente error: "
								+ e.getMessage(), e);
			}
		}

	}

	public void actualizarExpedienteReplicado(Long idExpediente)
			throws NSJPNegocioException {

		// Habilitar el campo que indica que el Expediente es replicado.
		logger.info("SERVICIO PARA ACTUALIZAR EXPEDIENTE REPLICADO");

		if (idExpediente == null || idExpediente <= 0L) {
			throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
		}

		Expediente expCambiar = expDao.read(idExpediente);
		expCambiar.setEsReplicado(new Boolean(true));
		logger.info(" Expediente Replicado ID : "
				+ expCambiar.getExpedienteId());
		expDao.saveOrUpdate(expCambiar);
	}
}

/**
 * Nombre del Programa : SolicitudDefensorDAOImplTest.java
 * Autor                            : cesar
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 13 May 2011
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
package mx.gob.segob.nsjp.dao.test.solicitud.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.enums.solicitud.EstatusSolicitud;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.solicitud.SolicitudDefensorDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Forma;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.SolicitudDefensor;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author cesar
 * 
 */
public class SolicitudDefensorDAOImplTest extends
		BaseTestPersistencia<SolicitudDefensorDAO> {

	public void testObtenerUltimoNumTipo() {
		String respuesta = daoServcice.obtenerUltimoNumero().toString();
		assertNotNull("El string no debes ser nulo ", respuesta);
	}

	public void testEliminarSolicitudDefensor() {
		SolicitudDefensor sol = daoServcice.read(123L);
		daoServcice.delete(sol);
		sol = daoServcice.read(124L);
		daoServcice.delete(sol);
	}

	public void testCreateFromPJ() {

		final SolicitudDefensor pojo = new SolicitudDefensor();
		pojo.setFechaCreacion(new Date());
		pojo.setTipoDocumento(new Valor(1L));
		pojo.setFechaLimite(new Date());
		pojo.setForma(new Forma(1L));
		pojo.setNombreDocumento("Solicitud Defensor");
		pojo.setEstatus(new Valor(EstatusSolicitud.ABIERTA.getValorId()));
		pojo.setTipoSolicitud(new Valor(TiposSolicitudes.DEFENSOR.getValorId()));
		pojo.setNumeroExpediente(new NumeroExpediente(4L));
		pojo.setDetenido("ABDIEL ARCOS ALVARES ");
		pojo.setConfInstitucion(new ConfInstitucion(Long
				.valueOf(Instituciones.PJ.getValorId())));
		pojo.setFolioSolicitud("PJ/"
				+ Calendar.getInstance().get(Calendar.YEAR) + ""
				+ Calendar.getInstance().getTimeInMillis() % 100000);
		pojo.setAudiencia(new Audiencia(7L));
		daoServcice.create(pojo);

	}

	public void testConsultarSolicitudesDefensoriaByHistoricoYEstatus() {
		List<SolicitudDefensor> respuesta = daoServcice
				.consultarSolicitudesDefensoriaByHistoricoYEstatus(new Date(),
						EstatusSolicitud.ABIERTA.getValorId());

		assertTrue("La lista debe tener minimo un registro : ",
				respuesta.size() > 0);
		logger.info("La lista debe tener minimo un registro : "
				+ respuesta.size());
	}

	public void testObtenerSolDfensorByExpedienteYFuncionario() {
		SolicitudDefensor respuesta = daoServcice
				.obtenerSolDfensorByExpedienteYFuncionario(40L, 9L);
		assertNotNull("La solicirud no puede ser nula : ", respuesta);
		logger.info("La solicirud no puede ser nula : "
				+ respuesta.getDocumentoId());
	}

	public void testObtenerInvolucradosIdConSolicitudesDefensor() {
		Long[] imputadosIds= {3226L,3228L, 1L};
		List<Long> imputadosId= Arrays.asList(imputadosIds);
		
		Audiencia audiencia = new Audiencia();
		//audiencia.setAudienciaId(644L);

			List<Long> solicitudesEncontradas = daoServcice
					.obtenerInvolucradosIdConSolicitudDefensor(audiencia,
							imputadosId);
			logger.info("Solicitudes de defensor encontradas: "
					+ solicitudesEncontradas.size());
			for (Long involucradoId : solicitudesEncontradas) {
				logger.info("Id: "+involucradoId);
			}
	}
	
	public void testConsultarSolDeDefensorPorNumeroExpediente()
			throws NSJPNegocioException {

		String numeroCaso = "ZAC/FG/XX/PGU/2012/AA-00669";
		 String numeroExpediente = "NSJZACDE01000201233333";

		//Verificamos si existen otras solicitudes relacionadas al caso, sin
		//sin numero de expediente y sin aviso de designacion
		List<SolicitudDefensor> solicitudes = daoServcice
				.consultarSolDeDefensorPorNumeroExpediente(numeroCaso, numeroExpediente,
						true, false);
		assertFalse("La lista no puede ser vacia!!", solicitudes.isEmpty());
		
		if (solicitudes != null && !solicitudes.isEmpty()) {
			for (SolicitudDefensor sol : solicitudes) {
				logger.info("Solicitud - id:" + sol.getDocumentoId());
				logger.info("Solicitud - caso:" + sol.getNumeroCasoAsociado());
				logger.info("Solicitud - numExp:" + sol.getNumeroExpediente());
				logger.info("Solicitud - avisoDEsignacion:" + sol.getAvisoDesignacion());
				if(sol.getAvisoDesignacion()!=null){				
					logger.info("Solicitud - avisoDEsignacion:" + sol.getAvisoDesignacion().getDocumentoId());
				}
			}
		}
		logger.info("Numero de solicitudes: "+ solicitudes.size());
	}
}

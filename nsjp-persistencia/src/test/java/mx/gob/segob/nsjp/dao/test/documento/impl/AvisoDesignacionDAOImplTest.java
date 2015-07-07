package mx.gob.segob.nsjp.dao.test.documento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.documento.EstatusNotificacion;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.documento.AvisoDesignacionDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.AvisoDesignacion;

public class AvisoDesignacionDAOImplTest extends
		BaseTestPersistencia<AvisoDesignacionDAO> {

	public void testEliminarAvisoDesignacionDAO() {
		AvisoDesignacion aviso = daoServcice.read(60L);
		daoServcice.delete(aviso);

		// aviso = daoServcice.read(60L);
		// daoServcice.delete(aviso);
		// aviso = daoServcice.read(71L);
		// daoServcice.delete(aviso);
	}

	public void testConsultarAvisoDesignacionPorSolicitudDeDefensor()
			throws NSJPNegocioException {
		Long solicitudId = 1597L;
		AvisoDesignacion aviso = daoServcice
				.consultarAvisoDesignacionPorSolicitudDeDefensor(solicitudId);

		if (aviso != null) {
			logger.info("aviso.....Id" + aviso.getDocumentoId());
			logger.info("aviso....soldefId"
					+ aviso.getSolicitudDefensor().getDocumentoId());
			logger.info("aviso....expId"
					+ aviso.getExpediente().getExpedienteId());
		}
	}

	public void testConsultarAvisosDesignacion() {

		Long estadoNotificacionId = null;//EstatusNotificacion .NO_ATENDIDA.getValorId(); //2143l;
		Long claveFuncionario = null;// 76l;
		Long tipoSolicitudId = TiposSolicitudes.DEFENSOR.getValorId();
		Long distritoId = 1L;
		
		try {
			List<AvisoDesignacion> avisos = daoServcice
					.consultarAvisosDesignacion(estadoNotificacionId, claveFuncionario, tipoSolicitudId, distritoId);
			assertFalse("La lista no puede ser vacia", avisos.isEmpty());
			for (AvisoDesignacion avisoDesignacion : avisos) {
				logger.info("aviso.....Id:" + avisoDesignacion.getDocumentoId());
				logger.info("aviso....soldefId:"
						+ avisoDesignacion.getSolicitudDefensor()
								.getDocumentoId());
				logger.info("aviso....expId"
						+ avisoDesignacion.getExpediente().getExpedienteId());
			}
			logger.debug("#:"+ avisos.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}

	}
}

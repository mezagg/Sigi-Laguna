package mx.gob.segob.nsjp.dao.test.solicitud.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.solicitud.SolicitudAdjuntosDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.SolicitudAdjuntos;

public class SolicitudAdjuntosDAOImplTest extends
		BaseTestPersistencia<SolicitudAdjuntosDAO> {
	
	public void testConsultarAdjuntosXSolicitud(){
		Long IdSolicitud = 6294L;
		Boolean esPdf = false;
		List<SolicitudAdjuntos> archivos = daoServcice.consultarAdjuntosXSolicitud(IdSolicitud, esPdf);
		
		if(archivos.size()>0)
		{
			for(SolicitudAdjuntos row:archivos)
			{
		    	logger.info(row.getArchivoDigital().getArchivoDigitalId());
		    	logger.info(row.getArchivoDigital().getTipoArchivo());
			}
		}
	}

}

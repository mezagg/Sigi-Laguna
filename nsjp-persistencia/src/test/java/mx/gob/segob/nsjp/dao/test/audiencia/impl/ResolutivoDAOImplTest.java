package mx.gob.segob.nsjp.dao.test.audiencia.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.audiencia.ResolutivoDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.Resolutivo;

public class ResolutivoDAOImplTest extends BaseTestPersistencia<ResolutivoDAO> {

	
	public void testConsultarResolutivosByAudienciaId(){
		Long idAudiencia = 191L;
		List<Resolutivo> lista = daoServcice.consultarResolutivosByAudienciaId(idAudiencia);
		for(Resolutivo objeto : lista){
			logger.info(objeto);
		}

	}
	
	public void testConsultarResolutivosByAudienciaIdWithDocumento(){
		Long idAudiencia = 189L;
		List<Resolutivo> lista = daoServcice.consultarResolutivosByAudienciaIdWithDocumento(idAudiencia);
		if (lista.size()>0){
			logger.info("Lista no vac�a");
		}else{
			logger.info("Lista vac�a");
		}
		for(Resolutivo objeto : lista){
			logger.info("Resolutivo " + objeto.getResolutivoId() + ": " + objeto);
			logger.info("Lista de Mandamientos:"+objeto.getMandamientos());
			//logger.info("Archivo Digital: " + objeto.getDocumento().getArchivoDigital());
		}

	}
	
	public void testConsultarResolutivosByAudienciaIdWithDocumentoAndArchDigital(){
		Long idAudiencia = 189L;
		List<Resolutivo> lista = daoServcice.consultarResolutivosByAudienciaIdWithDocumentoAndArchDigital(idAudiencia);
		if (lista.size()>0){
			logger.info("*******************************Lista no vac�a************************************************");
		}else{
			logger.info("Lista vac�a");
		}
		for(Resolutivo objeto : lista){
			logger.info("Resolutivo " + objeto.getResolutivoId() + ": " + objeto);
			logger.info("Lista de Mandamientos:"+objeto.getMandamientos());
			//logger.info("Archivo Digital: " + objeto.getDocumento().getArchivoDigital().getArchivoDigitalId() + ": "+objeto.getDocumento().getArchivoDigital());
		}

	}
}

/**
 * 
 */
package mx.gob.segob.nsjp.dao.test.mandamientoAdjuntos.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.documento.MandamientoAdjuntosDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.Documento;

/**
 * @author AlejandroGA
 *
 */
public class MandamientoAdjuntosDAOImplTest extends BaseTestPersistencia<MandamientoAdjuntosDAO>{
	
	public void testConsultarDocumentoMandamientoAdjuntoPorMandamientoId(){
		
		PaginacionDTO pag = new PaginacionDTO();
		pag.setCampoOrd("fechaCreacion");
		pag.setDirOrd("asc");
		PaginacionThreadHolder.set(pag);
		
		Long mandamientoId = 927L; 
		
		List<Documento> documentosAdjuntos = daoServcice.consultarDocumentoMandamientoAdjuntoPorMandamientoId(mandamientoId);
		
		if(documentosAdjuntos != null){
			logger.info("Documentos obtenidos : " + documentosAdjuntos.size());
		}
		
		for(Documento docAdj:documentosAdjuntos){
			logger.info("DocumentoId="+docAdj.getDocumentoId());
			logger.info("nombre="+docAdj.getNombreDocumento());
			logger.info("forma="+docAdj.getForma().getNombre());
			logger.info("tipo="+docAdj.getTipoDocumento());
			logger.info("fecha="+docAdj.getFechaCreacion());
		}
	}
}

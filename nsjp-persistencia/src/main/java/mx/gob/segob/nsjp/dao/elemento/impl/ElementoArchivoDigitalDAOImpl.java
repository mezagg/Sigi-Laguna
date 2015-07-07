/**
 *
 * Nombre del Programa : ElementoArchivoDigitalDAOImpl.java                                    
 * Autor                            : Cesar Agustin                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 09/11/2012 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Implementación para el DAO de la entidad ElementoArchivoDigital                      
 * Programa Dependiente  :N/A                                                      
 * Programa Subsecuente :N/A                                                      
 * Cond. de ejecucion        :N/A                                                      
 * Dias de ejecucion          :N/A                             Horario: N/A       
 *                              MODIFICACIONES                                       
 *------------------------------------------------------------------------------           
 * Autor                       :N/A                                                           
 * Compania               :N/A                                                           
 * Proyecto                 :N/A                                   Fecha: N/A       
 * Modificacion           :N/A                                                           
 *------------------------------------------------------------------------------           
 */
package mx.gob.segob.nsjp.dao.elemento.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.elemento.ElementoArchivoDigitalDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.ElementoArchivoDigital;
import mx.gob.segob.nsjp.model.ElementoArchivoDigitalId;

import org.springframework.stereotype.Repository;

@Repository("elementoArchivoDigitalDAO")
public class ElementoArchivoDigitalDAOImpl extends GenericDaoHibernateImpl<ElementoArchivoDigital, ElementoArchivoDigitalId>
        implements ElementoArchivoDigitalDAO {
	

	@SuppressWarnings("unchecked")
	public List<ArchivoDigital> consultarArchivosDigitalesXElementoId(
			Long elementoId) {

		 final PaginacionDTO pag = PaginacionThreadHolder.get();
		 StringBuffer queryString = new StringBuffer();
		 
		 queryString.append("SELECT new ArchivoDigital( ad.archivoDigitalId, ad.nombreArchivo, ad.tipoArchivo)")
		 .append(" FROM ArchivoDigital ad WHERE ad.archivoDigitalId IN ( SELECT ead.archivoDigital.archivoDigitalId FROM ElementoArchivoDigital ead")
		 .append(" WHERE ead.elemento.elementoId = ").append(elementoId);
		 queryString.append(")");
				 
		 logger.debug("pag :: " + pag);
		 
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("ad.archivoDigitalId");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("ad.nombreArchivo");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("3")) {
				queryString.append(" order by ");
				queryString.append("ad.tipoArchivo");
				queryString.append(" ").append(pag.getDirOrd());
			}
		} 
		
		 return super.ejecutarQueryPaginado(queryString, pag);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ArchivoDigital> consultarArchivosDigitalesSinEvniarXElementoId(Long elementoId) {

		 final PaginacionDTO pag = PaginacionThreadHolder.get();
		 StringBuffer queryString = new StringBuffer();
		 
		 queryString.append("SELECT ad")
		 .append(" FROM ArchivoDigital ad WHERE ad.archivoDigitalId IN ( SELECT ead.archivoDigital.archivoDigitalId FROM ElementoArchivoDigital ead")
		 .append(" WHERE ead.elemento.elementoId = ").append(elementoId)
		 .append(" AND ad.esEnviadoAOtraInstitucion IS NULL");
		 queryString.append(")");
				 
		 logger.debug("pag :: " + pag);
		 
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("ad.archivoDigitalId");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("ad.nombreArchivo");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("3")) {
				queryString.append(" order by ");
				queryString.append("ad.tipoArchivo");
				queryString.append(" ").append(pag.getDirOrd());
			}
		} 
		return super.ejecutarQueryPaginado(queryString, pag);
	}
	
}

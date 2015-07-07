/**
 * Nombre del Programa : NumeroExpedienteDAOImpl.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 13 Ene 2013
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci&oacute;n para el DAO de BitacoraEstatusNumExpediente
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
package mx.gob.segob.nsjp.dao.expediente.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.BitacoraEstatusNumExpedienteDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.BitacoraEstatusNumExpediente;

import org.springframework.stereotype.Repository;

/**
 * Implementaci&eacute;n para el DAO de BitacoraEstatusNumExpedienteDAO
 * 
 * @version 1.0
 * @author rgama
 * 
 */
@Repository("BitacoraEstatusNumExpedienteDAO")
public class BitacoraEstatusNumExpedienteDAOImpl extends GenericDaoHibernateImpl<BitacoraEstatusNumExpediente, Long>
        implements BitacoraEstatusNumExpedienteDAO {

    @SuppressWarnings("unchecked")
	public List<BitacoraEstatusNumExpediente> consultarBitacoraEstatusNumExpedientePorIdExpediente(Long expedienteId) {
        final StringBuffer queryString = new StringBuffer();
    	       
        queryString.append(" from BitacoraEstatusNumExpediente bene ");
        
        if(expedienteId != null && expedienteId > 0){
        	queryString.append(" where bene.numeroExpediente.expediente.expedienteId = ");
        	queryString.append(expedienteId);
        }
              
        logger.info("Query:"+ queryString);

        final PaginacionDTO pag = PaginacionThreadHolder.get();

		logger.debug("pag :: " + pag);
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("1")) {
				queryString.append(" order by ");
				queryString.append("bene.fechaCreacion");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("2")) {
				queryString.append(" order by ");
				queryString.append("bene.jerarquiaOrganizacional.nombre");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("3")) {
				queryString.append(" order by ");
				queryString.append("bene.numeroExpediente");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("4")) {
				queryString.append(" order by ");
				queryString.append("bene.funcionario.nombreFuncionario");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("5")) {
				queryString.append(" order by ");
				queryString.append("bene.estatus.valor");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("6")) {
				queryString.append(" order by ");
				queryString.append("bene.tipoActividad.valor");
				queryString.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("7")) {
				queryString.append(" order by ");
				queryString.append("bene.tipoDocumento.valor");
				queryString.append(" ").append(pag.getDirOrd());
			}
		}
		return super.ejecutarQueryPaginado(queryString, pag);   
    }
}

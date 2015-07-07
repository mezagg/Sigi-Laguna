package mx.gob.segob.nsjp.dao.expediente.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.AcumulacionNumeroExpedienteDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.AcumulacionNumeroExpediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.NumeroExpediente;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AcumulacionNumeroExpedienteDAOImpl extends GenericDaoHibernateImpl<AcumulacionNumeroExpediente, Long> implements AcumulacionNumeroExpedienteDAO {
	


	@Override
	public List<AcumulacionNumeroExpediente> consultaAcumulacion(Long numeroExpedienteId)throws NSJPNegocioException {
		StringBuffer queryString = new StringBuffer();
		
		final StringBuffer queryStr = new StringBuffer();
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		if (numeroExpedienteId != null){					
		
			queryStr.append(" FROM AcumulacionNumeroExpediente ane ");
			queryStr.append(" WHERE (ane.numeroExpedienteId.numeroExpedienteId =  ").append(numeroExpedienteId);
			queryStr.append(" OR ane.numeroExpedienteId.numeroExpedienteId IN (");
			queryStr.append(" SELECT ane1.numeroExpedienteId FROM AcumulacionNumeroExpediente ane1 ");
			queryStr.append(" WHERE ane1.numeroExpedienteAcumuladoId.numeroExpedienteId =  ").append(numeroExpedienteId);
			queryStr.append(" )) AND ane.numeroExpedienteAcumuladoId.numeroExpedienteId !=  ").append(numeroExpedienteId);
		}
		
		if (pag != null && pag.getCampoOrd() != null) {
			if (pag.getCampoOrd().equals("numeroExpediente")) {
				queryStr.append(" ORDER BY ");
				queryStr.append(" ane.numeroExpedienteId.numeroExpediente ");
				queryStr.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("fecha")) {
				queryStr.append(" ORDER BY ");
				queryStr.append(" he.fecha");
				queryStr.append(" ").append(pag.getDirOrd());
			}
			if (pag.getCampoOrd().equals("funcionario")) {
				queryStr.append(" ORDER BY ");
				queryStr.append(" he.claveFuncionario");
				queryStr.append(" ").append(pag.getDirOrd());
			}			
		}
				
		return ejecutarQueryPaginado(queryStr, pag);
	}

}

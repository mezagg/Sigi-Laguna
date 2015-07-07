package mx.gob.segob.nsjp.dao.expediente.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.BitacoraPermisoExpedienteDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.BitacoraPermisoExpediente;

import org.springframework.stereotype.Repository;

@Repository
public class BitacoraPermisoExpedienteDAOImpl extends GenericDaoHibernateImpl<BitacoraPermisoExpediente, Long>
		implements BitacoraPermisoExpedienteDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<BitacoraPermisoExpediente> obtenerPermisosDeNumeroExpediente(Long idExpediente) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM BitacoraPermisoExpediente bpe WHERE ")
					.append(" bpe.numeroExpediente.expediente.expedienteId=").append(idExpediente);
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("numeroExpediente")) {
            	queryString.append(" order by ");
            	queryString.append("bpe.numeroExpediente.numeroExpediente");
            	queryString.append(" ").append(pag.getDirOrd());
            }
            else  if (pag.getCampoOrd().equals("funcionario")) {
            	queryString.append(" order by ");
            	queryString.append("bpe.funcionario.nombreFuncionario");
            	queryString.append(" ").append(pag.getDirOrd());
            }
            
            else  if (pag.getCampoOrd().equals("area")) {
            	queryString.append(" order by ");
            	queryString.append("bpe.jerarquiaOrganizacional.nombre");
            	queryString.append(" ").append(pag.getDirOrd());
            }
            else  if (pag.getCampoOrd().equals("fechaMovimiento")) {
            	queryString.append(" order by ");
            	queryString.append("bpe.fechaMovimiento");
            	queryString.append(" ").append(pag.getDirOrd());
            }
            else  if (pag.getCampoOrd().equals("fechaLimite")) {
            	queryString.append(" order by ");
            	queryString.append("bpe.fechaLimite");
            	queryString.append(" ").append(pag.getDirOrd());
            }
            else  if (pag.getCampoOrd().equals("esActivo")) {
            	queryString.append(" order by ");
            	queryString.append("bpe.esActivo");
            	queryString.append(" ").append(pag.getDirOrd());
            }
        }
        return super.ejecutarQueryPaginado(queryString, pag);
	}
	
	public boolean actualizarRegistrosActivosEnBitacora(Long claveFuncionario,Long numExpId) throws NSJPNegocioException {
		StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append(" UPDATE BitacoraPermisoExpediente be SET be.esActivo = 0 WHERE ")
			.append(" be.funcionario=").append(claveFuncionario)
			.append(" AND be.numeroExpediente=").append(numExpId);
        
        int  res = super.getSession().createQuery(hqlQuery.toString()).
        							  executeUpdate();
        if(res == 0){
        	return Boolean.FALSE;
        }
        return Boolean.TRUE;
	}

}

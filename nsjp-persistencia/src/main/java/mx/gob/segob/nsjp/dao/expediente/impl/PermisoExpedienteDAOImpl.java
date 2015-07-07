package mx.gob.segob.nsjp.dao.expediente.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.PermisoExpedienteDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.PermisoExpediente;
import mx.gob.segob.nsjp.model.PermisoExpedienteId;

@Repository
public class PermisoExpedienteDAOImpl extends GenericDaoHibernateImpl<PermisoExpediente, PermisoExpedienteId>
		implements PermisoExpedienteDAO {

	@Override
	public PermisoExpediente obtnerPermisoFuncionario(Long claveFuncionario,
			Long numExpId) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("FROM PermisoExpediente pe WHERE ")
					.append("pe.funcionario=").append(claveFuncionario)
					.append(" AND pe.numeroExpediente=").append(numExpId);
		Query query = super.getSession().createQuery(queryString.toString());
		return (PermisoExpediente)query.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarExpedientescConPermisoFuncionario(
			Long claveFuncionario, Long discriminanteId) {
		Calendar diaActual = Calendar.getInstance();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT pe.numeroExpediente FROM PermisoExpediente pe WHERE ")
					.append("pe.funcionario=").append(claveFuncionario)
					.append(" AND CONVERT (VARCHAR, pe.fechaLimite, 112)>=").append(DateUtils.formatearBD(diaActual.getTime()));
		//Se corrige la consulta a la nueva versi�n
		//queryString.append(" AND pe.numeroExpediente.expediente.discriminante.valorId=").append(discriminanteId);
		queryString.append(" AND pe.numeroExpediente.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		return ejecutarQueryPaginado(queryString, pag);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<NumeroExpediente> consultarExpedientesPermisoFuncionarioJerarquiaRol(
			Long claveFuncionario, Long jerarquiarRolId) {
		Calendar diaActual = Calendar.getInstance();
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT pe.numeroExpediente FROM PermisoExpediente pe WHERE ")
				.append("pe.funcionario=")
				.append(claveFuncionario)
				.append(" AND CONVERT (VARCHAR, pe.fechaLimite, 112)>=")
				.append(DateUtils.formatearBD(diaActual.getTime()))
				.append(" AND pe.numeroExpediente.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ")
				.append(jerarquiarRolId);
		
		final PaginacionDTO pag = PaginacionThreadHolder.get();
		
		if(pag != null && pag.getSearchField() != null ){
			if(pag.getSearchField().equals("NumeroExpediente")){
				pag.setSearchField("numeroExpediente");
			    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"pe.numeroExpediente",false,false));
			}else if(pag.getSearchField().equals("Fecha")){
			    pag.setSearchField("fechaCreacion");
			    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"pe.numeroExpediente.expediente",false,true));
			}else if(pag.getSearchField().equals("Origen")){
			    pag.setSearchField("valor");
			    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag,"pe.numeroExpediente.expediente.origen",false,false));
			}
		}
		
		return ejecutarQueryPaginado(queryString, pag);
	}
}

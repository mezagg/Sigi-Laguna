package mx.gob.segob.nsjp.dao.exhorto.impl;

import java.util.List;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.exhorto.ExhortoDAO;
import mx.gob.segob.nsjp.model.Exhorto;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ExhortoDAOImpl extends GenericDaoHibernateImpl<Exhorto, Long> implements ExhortoDAO {

	@Override
	public Long registrarExhorto(Exhorto o) {
		Long id = null;
		try{
			id = super.create(o);
		}catch (Exception e) {
			e.printStackTrace();
		}
		 
		 
		 return id;
		
	}

	@Override
	public Exhorto consultarExhorto(Long exhortoId) {
		StringBuilder queryStr = new StringBuilder();
		Exhorto exhorto = new Exhorto();
		
		try{
			
			queryStr.append("SELECT ex ");
			queryStr.append("FROM Exhorto ex ");
			queryStr.append(" WHERE ex.exhortoId =:exhortoId");
			
			Query query = super.getSession().createQuery(queryStr.toString());
			query.setParameter("exhortoId", exhortoId);
			exhorto = (Exhorto)query.uniqueResult();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		

		return exhorto;
	}


	public List<Exhorto> consultarExhortos_Bk(String idExpediente) {

		final StringBuffer queryStr = new StringBuffer();
		List<Exhorto> exhortoLista = null;
		
		try{
			
			queryStr.append("SELECT ex.exhortoId, ex.expediente, ex.funcionario, ex.valorEstatus, ex.documento, ex.folio, ex.fechaVencida, ex.fechaDiligencia, ex.fechaEnvio, ex.diligencia, d.esGuardadoParcial ");
			queryStr.append(" FROM Exhorto ex inner join Documento d on ex.Documento_id = d.Documento_id ");
			queryStr.append(" WHERE ex.expediente =:expediente");
			
			Query query = super.getSession().createQuery(queryStr.toString());
			query.setParameter("expediente", idExpediente);
			exhortoLista =  query.list();


//	        List<Object> resp = query.list();
//	        logger.debug("resp.size() :: " + resp.size());
//
//	        
//	        Iterator iterator = resp.iterator();
//	        
//	        while(iterator.hasNext()){ 
//		        Object []obj = (Object[])iterator.next();
//		     	Documento loDocumento = new Documento();
//		     	loDocumento.setDocumentoId(((BigDecimal)obj[0]).longValue());
//		     	Date fechaCreacionAct = (Date) obj[1];
//		     	Actividad loActividad = new Actividad();
//		     	loActividad.setFechaCreacion(fechaCreacionAct);
//		     	loActividad.setTipoActividad(new Valor(((BigDecimal)obj[11]).longValue(), (String)obj[2]));
//		     	Funcionario loFuncionario = new Funcionario();
//		     	CatAreasNegocio loCatAreasNegocio = new  CatAreasNegocio();
//		     	loCatAreasNegocio.setNombreCatAN(  (String)obj[7] == null ? "-" : (String)obj[7]);
//		     	loFuncionario.setCatAreaNegocio(loCatAreasNegocio);
//		     	loActividad.setFuncionario(loFuncionario);
//		     	loDocumento.setActividad(loActividad);
//		     	loDocumento.setTipoDocumento(new Valor(((BigDecimal)obj[8]).longValue(), (String)obj[3]));
//	        	loDocumento.setNombreDocumento((String)obj[4]);
//	        	Date fechaCreacionDoc = (Date) obj[5];
//	        	loDocumento.setFechaCreacion(fechaCreacionDoc);
//	        	BigDecimal lo = (BigDecimal)obj[6]; 
//		     	loDocumento.setEsGuardadoParcial((lo.toString().equals("1") ? true: false));
//				loDocumento.setFolioDocumento(obj[9] != null ? (String) obj[9]: null);
//				loDocumento.setConfInstitucion(obj[10] != null ? new ConfInstitucion(
//								((BigDecimal) obj[10]).longValue()) : null);
//				loDocumento.setArchivoDigital(new ArchivoDigital(null, null, (String) obj[12]));
//	        	loDocumentos.add(loDocumento);
//	        }
//	        
//	        return loDocumentos;
	        
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return exhortoLista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Exhorto> consultarExhortos(String idExpediente) {

		final StringBuffer queryStr = new StringBuffer();
		List<Exhorto> exhorto = null;
		
		try{
			
			queryStr.append("SELECT ex ");
			queryStr.append("FROM Exhorto ex ");
			queryStr.append(" WHERE ex.expediente =:expediente");
			
			Query query = super.getSession().createQuery(queryStr.toString());
			query.setParameter("expediente", idExpediente);
			exhorto =  query.list();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return exhorto;
	}



}

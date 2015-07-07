package mx.gob.segob.nsjp.dao.hecho.impl;

import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.hecho.ConclusionHechoDAO;
import mx.gob.segob.nsjp.model.ConclusionHecho;

@Repository("conclusionHechoDAO")
public class ConclusionHechoDAOImpl extends GenericDaoHibernateImpl<ConclusionHecho, Long> 
	implements ConclusionHechoDAO{

}

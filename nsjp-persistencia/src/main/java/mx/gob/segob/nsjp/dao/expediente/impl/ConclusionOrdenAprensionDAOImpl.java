package mx.gob.segob.nsjp.dao.expediente.impl;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.ConclusionOrdenAprensionDAO;
import mx.gob.segob.nsjp.model.ConclusionOrdenAprension;

import org.springframework.stereotype.Repository;

@Repository
public class ConclusionOrdenAprensionDAOImpl extends
		GenericDaoHibernateImpl<ConclusionOrdenAprension, Long> implements
		ConclusionOrdenAprensionDAO {
}

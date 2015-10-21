/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.segob.nsjp.dao.actividad.impl;

import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoRolDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.model.ConfActividadDocumentoRol;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pamela
 */
@Repository
public class ConfActividadDocumentoRolDAOImpl extends GenericDaoHibernateImpl<ConfActividadDocumentoRol, Long>
        implements ConfActividadDocumentoRolDAO {

    @Override
    public ConfActividadDocumentoRol consultaConfActividadDocumentoRolPorIdActividad(Long idTipoActividad) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT conf FROM ConfActividadDocumentoRol conf ").
                append("WHERE conf.tipoActividad.valorId = :idTipoActividad");
        Query q = getSession().createQuery(sb.toString());
        q.setParameter("idTipoActividad", idTipoActividad);
        return (ConfActividadDocumentoRol) q.uniqueResult();
    }

}
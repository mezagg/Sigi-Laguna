/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.segob.nsjp.dao.actividad;

import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.ConfActividadDocumentoRol;

/**
 *
 * @author pamela
 */
public interface ConfActividadDocumentoRolDAO extends
        GenericDao<ConfActividadDocumentoRol, Long> {
    ConfActividadDocumentoRol consultaConfActividadDocumentoRolPorIdActividad(Long idTipoActividad);
}

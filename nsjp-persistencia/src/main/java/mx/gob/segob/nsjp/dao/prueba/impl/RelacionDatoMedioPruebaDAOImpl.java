/**
* Nombre del Programa : RelacionDatoMedioPruebaDAOImpl.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 29/09/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.dao.prueba.impl;

import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.prueba.RelacionDatoMedioPruebaDAO;
import mx.gob.segob.nsjp.model.RelacionDatoMedioPrueba;

/**
 * Implementaci�n de los m�todos de acceso a los datos de la relacion de DatoPrueba y MedioPrueba.
 * @version 1.0
 * @author GustavoBP
 *
 */
@Repository
public class RelacionDatoMedioPruebaDAOImpl extends GenericDaoHibernateImpl<RelacionDatoMedioPrueba, Long> implements
		RelacionDatoMedioPruebaDAO {

}

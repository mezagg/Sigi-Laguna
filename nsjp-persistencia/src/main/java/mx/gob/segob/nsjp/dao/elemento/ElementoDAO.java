/**
*
* Nombre del Programa : DomicilioDAOImpl.java                                    
* Autor                            : Cesar Agustin                                               
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 30/03/2011 
* Marca de cambio        : N/A                                                     
* Descripcion General    : Interface para el DAO de la entidad Elemento                      
* Programa Dependiente  :N/A                                                      
* Programa Subsecuente :N/A                                                      
* Cond. de ejecucion        :N/A                                                      
* Dias de ejecucion          :N/A                             Horario: N/A       
*                              MODIFICACIONES                                       
*------------------------------------------------------------------------------           
* Autor                       :N/A                                                           
* Compania               :N/A                                                           
* Proyecto                 :N/A                                   Fecha: N/A       
* Modificacion           :N/A                                                           
*------------------------------------------------------------------------------           
*/
package mx.gob.segob.nsjp.dao.elemento;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.Elemento;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * @author Cesar Agustin
 * @version 1.0
 */
public interface ElementoDAO extends GenericDao<Elemento, Long> {
    
    Session getHBSession();
    
    HibernateTemplate getHibernateTemplate();
    
    /**
     * Operaci�n que realiza la funcionalidad de consultar los elementos
     * y las relaciones impl�citas de los elementos relacionados al expediente.
     * Recibe el expediente del cual va a buscar la informaci�n 
     * @return Devuelve un listado de objetos de tipo Elemento
     */
    List<Elemento> consultarElementosPorCalidad(String numeroExpediente, Calidades calidadId);

    /**
     * Consultar los elementos por el Id del Expediente
     * 
     * @param expedienteId
     * @return
     */
    List<Elemento> findElementosByExpedienteId(Long expedienteId);

    /**
     * Consulta los Elementos asociados al expediente de la actividad con id
     * {@code idActividad}
     * @param idActividad El id de la activad de la que se obtendra su
     * expediente y a su vez de este se obtendran los elementos asociados.
     * @return Una lista que contiene los elementos del expediente asociado
     * a la actividad. Regresa la lista vacia en caso que no existan elementos
     * asociados al expediente.
     */
    List<Elemento> consultarElementoXActividad(Long idActividad);
    
    /**
     * Consulta los elementos asociados al expediente y de acuerdo al tipo de 
     * elemento que esta registrado en Valor con el par�metro valorDescripci�n.
     * El tipo de Valor que puede tener la persona es de acuerdo 
     * al Campo =22 de BD: Persona,Organizaci�n, Lugar y Objeto  
     * 
     * @param idExpediente
     * @param valorDescripcion  Filtro de busqueda, Valor de tipo
     * @param esOrganizacion 
     * @return Lista de elementos que cumplan con el filtro de busqueda.
     */
    List<Elemento> consultarElementoXIdExpedienteTipoValor(Long idExpediente, String valorDescripcion, Boolean esOrganizacion);

    /**
     * Consulta elemento por folio, internamente obtiene una lista y obtiene el primer elemento.
     * Esto con el proposito de que la aplicaci�n no truene en caso de encontrar mas de un 
     * elemento con el mismo folio. Este escenario se puede dar dado que en la base de datos 
     * existen datos de prueba.
     * @param folioElemento
     * @return
     */
	Elemento consultarElementoPorFolio(String folioElemento);

    
}

/**
* Nombre del Programa : DatoPruebaDAO.java
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
package mx.gob.segob.nsjp.dao.prueba;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.dao.base.GenericDao;
import mx.gob.segob.nsjp.model.DatoPrueba;
import mx.gob.segob.nsjp.model.Involucrado;

/**
 * Interfaz para los metodos de acceso a los datos de la entidad DatoPrueba.
 * @version 1.0
 * @author GustavoBP
 */
public interface DatoPruebaDAO 
	extends GenericDao<DatoPrueba, Long>  {

	/**
     * Recupera los expedientes de acuerdo con los parametros de busqueda
     * 
     * @param numeroExpediente
     * @return Lista de expedientes obetenidos
     */
    public List<DatoPrueba> buscarDatosDePrueba(Long expedienteId);

    /**
     * Consulta los datos de prueba asociados a los involucrados que, su identificador, es 
     * proporcionado en una lista.
     * La busqueda se realizas en la RelacionPruebaInvolucrado mediante el idInvolucrado.
     * En caso de que no sea proporcionado al menos un id, se regresa todos los DP.
     * @param involucradosId
     * @return
     */
    List<DatoPrueba> buscarDatosDePruebaPorInvolucrados(List<Long> involucradosId);

    /**
     * Consulta los involucrados que esten relacionados a una Prueba.
     * i.e. No se verifica que el dato prueba sea aceptado, ni que tenga
     * una relaci�n con Medio Prueba aceptada. Esta validaci�n se hace desde 
     * la interfaz gr�fica.
     * En caso de que el datoPruebaId sea nulo, se buscan sobre todos los datoPruebas.
     * @param expedienteId
     * @param datoPruebaId
     * @return
     */
    List<Involucrado> consultarInvolucradoConRelacionADatoPrueba(Long expedienteId, Long datoPruebaId);
    
    /**
     * Utilizado para obtener los imputados de un expediente sin relacion al dato de prueba 
     * @param expedienteId
     * @param datoPruebaId
     * @return Lista de imputados del expediente sin relacion al dato de prueba
     */
	List<Involucrado> consultarInvolucradosDeExpedienteSinRelacionConDatoPrueba(
			Long expedienteId, Long datoPruebaId, Calidades[] calidades,
			Boolean esActivo);

    /**
     * ervicio que consulta las pruebas asociadas a un expediente.
	 * La condici�n para que sea prueba es:
	 * 1.-Que el Dato Prueba sea aceptado -> para poder ser relacionado a Medio Prueba.
	 * 2.-Que exista una relaci�n con almenos un Medio Prueba.
	 * 3.-Que la relaci�n Medio Prueba sea aceptada.
	 * 
     * @param expedienteId
     * @return
     */
    List<DatoPrueba> buscarPruebasPorExpediente(Long expedienteId) ;
    

    /**
     * Permite consultar la difencia de los datos de prueba
     * @param expedienteId
     * @param medioPruebaId
     * @return
     */
	List<DatoPrueba> buscarDatosDePrueba(Long expedienteId,Long medioPruebaId);
	
}

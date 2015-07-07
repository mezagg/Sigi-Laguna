/**
 * Nombre del Programa : FormaDelegate.java
 * Autor                            : Escorza
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21 Jun 2011
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato de metodos para operar los servicios relativos a un Documento
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
package mx.gob.segob.nsjp.delegate.forma;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.forma.CamposFormaDTO;
import mx.gob.segob.nsjp.dto.forma.FormaDTO;

/**
 * 
 * Contrato de metodos para operar los servicios relativos a una Forma
 * @version 1.0
 * @author adrian
 *
 */
public interface FormaDelegate {
	
	/**
	 * Operaci�n que realiza la funcionalidad de consultar las plantillas registradas 
	 * en el sistema de acuerdo a  un criterio de tipo de documento.
	 * 
	 * @param tipoDocumento
	 * @return Devuelve un listado de plantillas, en caso contrario, regresa null.
	 * @throws NSJPNegocioException
	 */
	List<FormaDTO> consultarFormaPlantilla(String tipoDocumento)throws NSJPNegocioException;
	
	List<FormaDTO> consultarPlantillaPorTipo(Long tipoForma)throws NSJPNegocioException;
	
	
	/**
	 * Consulta el inventario de formas disponibles en el sistema filtradas
	 * por cierto tipo de forma, si el tipo de forma enviado es 0 entonces
	 * no filtra los elementos
	 * @param tipoFormaId el tipo de forma a filtrar
	 * @return Listado de formas
	 */
	List<FormaDTO> consultarFormasPorTipoForma(Long tipoFormaId) throws NSJPNegocioException;
	
	/**
	 * Inserta o actualiza una forma, si ya se tiene un ID de forma se actualiza, si no
	 * entonces se crea una nueva forma
	 * @param forma
	 * @return
	 */
	Long guardarForma(FormaDTO forma)  throws NSJPNegocioException;
	/**
	 * Operaci�n que realiza la funcionalidad de consultar el Detalle de la Forma seleccionada
	 * 
	 * @param Recibe el identificador de la forma
	 * @return Devuelve la Forma.
	 * @throws NSJPNegocioException
	 */
	FormaDTO consultarDetalleForma(Long idForma) throws NSJPNegocioException;
	
	/**
	 * Operaci�n que realiza la funcionalidad de eliminar la forma seleccionada.
	 * 
	 * @param idForma
	 * @return Devuelve un valor boleano, verdadero en caso de que haya eliminado la forma, falso en caso contrario.
	 * @throws NSJPNegocioException
	 */
	void eliminarForma(Long idForma)throws NSJPNegocioException;
	/**
	 * Consulta los campos que pueden ser usados para llenar un expediente
	 * @return Lista de campos
	 * @throws NSJPNegocioException
	 */
	List<CamposFormaDTO> consultarCamposForma() throws NSJPNegocioException;

}

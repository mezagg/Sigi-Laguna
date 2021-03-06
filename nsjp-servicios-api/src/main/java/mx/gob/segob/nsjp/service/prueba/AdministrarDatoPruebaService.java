/**
* Nombre del Programa : AdministrarDatoPruebaService.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 30/09/2011
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
package mx.gob.segob.nsjp.service.prueba;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.prueba.DatoPruebaDTO;

/**
 * Contrato de los servicios para registrar, modificar, consultar y relacionar los
 * Datos de Prueba
 *  
 * @version 1.0
 * @author GustavoBP
 *
 */
public interface AdministrarDatoPruebaService {

	/**
	 * Servicio que permite registrar o actualizar un objeto del tipo DatoPrueba.
	 * El numeroExpediente(CAUSA), es obligatorio para crear la relaci�n del Dato Prueba
	 * con el Expediente.
	 * En caso de que se desee actualizar, es necesario ingresar el idDatoPruab del objeto, 
	 * caso contrario, sea nulo o un valor invalido, ser� registrado.
	 * En la actualizaci�n se hace validaci�n del objeto este registrado en BD, caso contrario
	 * se manda una excepci�n.
	 * 
	 * @param datoPruebaDTO
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	DatoPruebaDTO registrarActualizarDatoPrueba(DatoPruebaDTO datoPruebaDTO, String numeroExpediente) throws NSJPNegocioException;
	
	/**
	 * Servicio que permite consultar los Datos de Pruebas asociados a un 
	 * expediente, a trav�s de su CAUSA (NumeroExpediente)
	 * 
	 * @param filtroDTO
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DatoPruebaDTO> consultarDatosPruebaPorFiltro(DatoPruebaDTO filtroDTO, String numeroExpediente) throws NSJPNegocioException;
	
	
	/**
	 * Servicio que permite actualizar el estado del dato de prueba (aceptado/rechazado)
	
	 * 
	 * @param filtroDTO
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	DatoPruebaDTO aceptarCancelarDatoPrueba(DatoPruebaDTO datoPruebaDTO) throws NSJPNegocioException;

	/**
	 * Servicio que relaciona una prueba con uno o varios Involucrados
	 * @param datoPruebaDTO
	 * @param listaMediosPruebaDTO
	 * @return
	 * @throws NSJPNegocioException
	 */
	Long relacionarPruebaAInvolucrado(DatoPruebaDTO datoPruebaDTO,
			List<InvolucradoDTO> listaResponsables)throws NSJPNegocioException;

	/**
	 * Servicio para consultar los datos de prueba de una audiencia, 
	 * i.e.: Los datos de prueba del expediente sin asignaci�n y (opcionalmente) los asignados a un grupo de probables responsables
	 * @param listaResponsables
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DatoPruebaDTO> consultarDatosPruebaXAudiencia(
			List<InvolucradoDTO> listaResponsables, String numeroExpediente)throws NSJPNegocioException;

	/**
	 * Servicio que consulta el detalle de un DatoPrueba
	 * @param datoPruebaId
	 * @return
	 * @throws NSJPNegocioException
	 */
	DatoPruebaDTO consultarDatoPrueba(Long datoPruebaId)throws NSJPNegocioException;
	

	/**
	 * Servicio utilizado para mostrar los imputados de la audiencia que no tenga asociada
	 * la prueba pasada como parametro. 
	 * Se consulta primero todos los involucrados de la audiencia, se consulta los involucrados
	 * relacionados a la Prueba (Dato prueba), posteriormente, se descriminan los involucardos
	 * de las segunda lista con la lista de los imputados.
	 *  
	 * Se requiere:
	 * 		-AudienciaId
	 * 		-datoPruebaId
	 * 		-numeroExpediente
	 * @param audienciaDTO
	 * @param datoPruebaDTO
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<InvolucradoViewDTO> obtenerImputadosAudienciaSinRelacionConPrueba(
			AudienciaDTO audienciaDTO, DatoPruebaDTO datoPruebaDTO, String numeroExpediente) throws NSJPNegocioException ;
	
	/**
	 * Utilizado para obtener los imputados de un expediente sin relacion al dato de prueba 
	 * @param datoPruebaDTO
	 * @param datoPruebaDTO
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<InvolucradoViewDTO> consultarImputadosDeExpedienteSinRelacionConDatoPrueba(
			DatoPruebaDTO datoPruebaDTO, String numeroExpediente)
			throws NSJPNegocioException;
	
	/**
	 * Servicio que consulta las pruebas asociadas a un expediente.
	 * La condici�n para que sea prueba es:
	 * 1.-Que el Dato Prueba sea aceptado -> para poder ser relacionado a Medio Prueba.
	 * 2.-Que exista una relaci�n con almenos un Medio Prueba.
	 * 3.-Que la relaci�n Medio Prueba sea aceptada.
	 *  
	 * @param numeroExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<DatoPruebaDTO> consultarPruebasPorNumeroExpediente(String numeroExpediente) throws NSJPNegocioException ;
	
}

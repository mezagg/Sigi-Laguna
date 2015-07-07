/**
 * Nombre del Programa : ElementoDelegate.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 12-jul-2011
 * Marca de cambio        : N/A
 * Descripcion General    : N/A
 * Programa Dependient    :N/A
 * Programa Subsecuente   :N/A
 * Cond. de ejecucion     :N/A
 * Dias de ejecucion      :N/A                                Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                            :N/A
 * Compania                         :N/A
 * Proyecto                         :N/A                      Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.delegate.elemento;


import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.archivo.ArchivoDigitalDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.elemento.BitacoraElementoDTO;
import mx.gob.segob.nsjp.dto.elemento.ElementoDTO;
import mx.gob.segob.nsjp.dto.elemento.RelacionReincidenciaDTO;
import mx.gob.segob.nsjp.dto.relacion.RelacionDTO;

/**
 * Contrato del delegate para los metodos de comunicacion de Elemento
 * entre la vista y los servicios.
 * @version 1.0
 * @author Jacob Lobaco
 */
public interface ElementoDelegate {

    /**
     * Consulta los Elementos asociados al expediente de la actividad con id
     * {@code idActividad}
     * @param idActividad El id de la activad de la que se obtendra su
     * expediente y a su vez de este se obtendran los elementos asociados.
     * @return Una lista que contiene los elementos del expediente asociado
     * a la actividad. Regresa la lista vacia en caso que no existan elementos
     * asociados al expediente.
     * @throws NSJPNegocioException En caso que {@code idActividad} sea nulo.
     */
    List<ElementoDTO> consultarElementosXActividad(Long idActividad)
            throws NSJPNegocioException;

    /**
     * Consulta el tipo de elemento de un elemento.
     * @param idElemento El id del elemento a consultar.
     * @return Una cadena que representa el tipo de elemento.
     */
    String consultarCatElemento(Long idElemento) throws NSJPNegocioException;

    /**
     * Consultar los elementos asociados al expediente y de acuerdo
	 * a la categoria de relacion que es solicitada.
	 * El parametro esSujeto permite determinar cual de los dos valores
	 * del CatCategoriaRelacion (Persona - Objeto, Persona - Organización)
	 * se debe de efectuar la consulta:
	 *  esSujeto: true --> Primer valor
	 *  esSujeto: false --> Segundo valor
	 * 
     * @param idExpediente
     * @param idCatCategoriaRelacion
     * @param esSujeto
     * @return
     * @throws NSJPNegocioException
     */
    List<ElementoDTO> consultarElementosXIdExpedienteCatRelacion(Long idExpediente, Long idCatCategoriaRelacion, Boolean esSujeto)
    throws NSJPNegocioException ;
    
    public void adjuntarArchivoAElemento(ElementoDTO elemento,
			ArchivoDigitalDTO adjunto) throws NSJPNegocioException;
    
	/**
	 * Servicio utilizado para consultar los elementos asociados a un expediente, regresando 
	 * la relacion con otros elementos, el tipo de relación y la Categoría de la Relación
	 * 
	 * @param idExpediente
	 * @return
	 * @throws NSJPNegocioException
	 */
	List<RelacionDTO> consultarElementosXIdExpediente(String numeroExpediente)
	   		throws NSJPNegocioException;
	
    /**
     * Permite buscar las reincidencias de un elemnto:
     * Si es un Automovil, permitira filtrar por el numero de serie y por el numero de placas
     * Si es un Arma, permitira filtrar por el numero de serie
     * Se es una persona, permitira filtrar por el nombre y apellidos
     * @param elementoDTO
     * @return List<CasoDTO> 
     * @throws NSJPNegocioException
     */
    public List<CasoDTO> buscarReincidenciaDeElementos(ElementoDTO elementoDTO) throws NSJPNegocioException;
    
    /**
     * Permite registrar reincidencias de un elemento
     * @param idElemento
     * @param idCasos
     * @param idFuncionario
     * @return List<RelacionReincidenciaDTO>: Devuelve el id de las relaciones registradas
     * @throws NSJPNegocioException
     */
    public List<RelacionReincidenciaDTO> registrarReinicidencias(Long idElemento,
			List<Long> idCasos, Long idFuncionario) throws NSJPNegocioException;
    
	/**
	 * Permite consultar las reincidencias de un Elemento(Vehiculo o persona)
	 * @param idElemento
	 * @return List<RelacionReincidenciaDTO> 
	 */
	public List<RelacionReincidenciaDTO> consultarReincidenciasXElemento(Long idElemento) throws NSJPNegocioException;
	
	/**
	 * Metodo para para Anular el Elemento de acuerdo al idElemento,
	 * considerando las relaciones asociadas a él, de acuerdo a:
	 * -Si el elemento es un Objeto se elimina:
	 * 		-Evidencia
	 * -Si el elemento es un Involucrado se elimina:
	 * 		-Relaciones con otros involucrados
	 * 		-Relaciones con Delito Persona.  
	 * 
	 * @param idElemento
	 * @throws NSJPNegocioException
	 */
	public Boolean anularElemento(Long idElemento) throws NSJPNegocioException;

	/**
	 * Servicio que se encarga de consultar las  
	 * relaciones asociadas al elemento ya sea para:
	 * -Si el elemento es un Objeto se consulta:
	 * 		-Evidencia
	 * -Si el elemento es un Involucrado se consulta:
	 * 		-Relaciones con otros involucrados
	 * 		-Relaciones con Delito Persona. 
	 * 
	 * Regresa una lista de cadenas, indicando :
	 * -Si el elemento es un Objeto indica:
	 * 		-Evidencia
	 * -Si el elemento es un Involucrado indica:
	 * 		-La calidad del elemento relacionado
	 * 		-El número de delitos relacionados
	 * tipo de calidad de los elementos  
	 * @param idElemento
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<String> consultarRelacionesElemento(Long idElemento) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la bit&aacute;cora de los 
	 * Elementos que se pasan como par&aacute;metros.
	 * @param elementos - List<BitacoraElemento> con los identificadores de los
	 * 					  elementos de los cu&aacute;les se va a consultar la
	 * 					  bit&aacute;cora asociada.
	 * @return Map<Long,BitacoraElemento> - Mapa con la informaci&oacute;n de la
	 * 									bit&aacute;cora asociada a cada uno de 
	 * 									los elementos.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en los 
	 * 								  par&aacute;metros suficientes para llevar 
	 * 								  a cabo la consulta.
	 */
	public Map<Long,BitacoraElementoDTO> consultarBitacoraElementos(List <BitacoraElementoDTO> elementos) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo la consulta de la bit&aacute;cora asociada a 
	 * un elemento.
	 * @param elemento - El elemento del cual se va a consultar la bit&aacute;cora 
	 * 					 asociada.
	 * @return BitacoraElemento - La bit&aacute;cora asociada con el elemento pasado
	 * 							  como par&aacute;metro.
	 * @throws NSJPNegocioException - En el caso de que no se env&iacute;en los 
	 * 								  par&aacute;metros suficientes para llevar 
	 * 								  a cabo la consulta.
	 */
	public BitacoraElementoDTO consultarBitacoraXElemento(BitacoraElementoDTO elemento) throws NSJPNegocioException;
	
	/**
	 * M&eacute;todo que lleva a cabo el guardado o actualizaci&oacute;n de la 
	 * bit&aacute;cora asociada con un elemento.
	 * @param bitacoraElemento - La bit&aacute;cora de la cual se va a actualizar la
	 * 							 informaci&oacute;n
	 * @return BitacoraElementoDTO - la bit&aacute;cora con la informaci&oacute;n 
	 * 								 actualizada. 
	 */
	public BitacoraElementoDTO actualizaBitacoraElemento (BitacoraElementoDTO bitacoraElemento) throws NSJPNegocioException;
	
	
	/**
	 * Consulta archivos digitales asociados a un elemento
	 * @param elementoId
	 * @return
	 * @throws NSJPNegocioException
	 */
	public List<ArchivoDigitalDTO> consultarArchivosDigitalesXElementoId(Long elementoId)throws NSJPNegocioException;
	
	/**
	 * Permite adjuntar un archivo digital a una tabla un elmento, dicha informacion se guardara en la tabla de cruce
	 * ElementoArchivoDigital
	 * @param elemento
	 * @param adjunto
	 * @throws NSJPNegocioException
	 */
	public void adjuntarArchivoAElementoTablaCruce(ElementoDTO elemento,ArchivoDigitalDTO adjunto) throws NSJPNegocioException;
    
	public ArchivoDigitalDTO leeIdIdentificadorMinMax(int operadorMinMax)throws NSJPNegocioException;
	
	public List<ArchivoDigitalDTO> leeRangosArchivosDigitales(Long inicio)throws NSJPNegocioException;
	
	public void modificaArchivosDigitales(List<ValorDTO> identificadorRutaArchivoDigital)throws NSJPNegocioException;
}

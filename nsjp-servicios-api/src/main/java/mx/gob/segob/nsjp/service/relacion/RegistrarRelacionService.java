/**
 * Nombre del Programa : RegistrarRelacionService.java
 * Autor                            : Jacob Lobaco
 * Compania                         : Ultrasist
 * Proyecto                         : NSJP                    Fecha: 13-jul-2011
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
package mx.gob.segob.nsjp.service.relacion;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;

/**
 * 
 * @version 1.0
 * @author Jacob Lobaco
 */
public interface RegistrarRelacionService {

    /**
     * Crea un nuevo registro en la tabla relacion con los parametros que se le
     * pasan.
     * @param idCatRelacion El id del tipo de relacion que estamos creando.
     * @param idElementoSujeto El id del elemento sujeto.
     * @param idElementoComplemento El id del elemento complemento.
     * @throws NSJPNegocioException En caso que alguno de los parametros sea
     * null.
     */
    void registrarRelacion(Long idCatRelacion,
            Long idElementoSujeto, Long idElementoComplemento)
            throws NSJPNegocioException;
    
    /**
     * M&eacute;todo que lleva a cabo la creaci&oacute;n de un nuevo registro
     * dentro de la tabla RelacionDocumentoElemento que sirve para mantener 
     * ligados ciertos documentos con un elemento en espec&iacute;fico.
     * 
     * @param idCatRelacion - El id de la categor&iacute;a de relaci&oacute;n 
     * 						  que se est&aacute; creando.
     * 
     * @param idElemento - El identificador del elemento que se est&aacute;
     * 					   asociando con el documento.
     * 
     * @param idDocumento - El identificador del documento que se est&aacute;
     * 						asociando con el elemento.
     * 
     * @throws NSJPNegocioException - En caso que alguno de los par&aacute;metros 
     * 								  sea <code>null</code> o no v&aacute;lido.
     */
    public void registrarRelacionDocumentoElemento(Long idCatRelacion,
            Long idElemento, Long idDocumento) throws NSJPNegocioException;
}

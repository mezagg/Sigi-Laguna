/**
 * Nombre del Programa : AmparoDelegate.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 15 Feb 2012
 * Marca de cambio        : N/A
 * Descripcion General    : Contrato de metodos para operar los servicios relativos a un Acuse de recibo
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
package mx.gob.segob.nsjp.delegate.documento;

import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.documento.DocumentoDTO;
import mx.gob.segob.nsjp.dto.documento.DocumentoValorDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoDTO;


/**
 * Contrato de metodos para operar los servicios relativos a un Amparo
 * @author rgama
 *
 */
public interface AmparoDelegate {
	
	
    /**
     * Permite consultar los involucrados relacionados a un amparo
     * @param idAmparo
     * @return 
     * @throws NSJPNegocioException
     */
    public List<InvolucradoDTO> consultarInvolucradosXAmparo(Long idAmparo) throws NSJPNegocioException;
    
    public void insertarEstatusAmparo(DocumentoValorDTO documentoValorDTO) throws NSJPNegocioException;
    
    public Map<Long, DocumentoValorDTO> consultarEstatusPorIdsDocumentos(List<Long> idsDocumentos) throws NSJPNegocioException;
    
    public DocumentoValorDTO consultarEstatusPorIdDocumento(Long idDocumento) throws NSJPNegocioException;
    
    public void actualizaAmparo(DocumentoDTO amparo,DocumentoValorDTO estatus,List<Long> idsInvolucrados) throws NSJPNegocioException;
	
}

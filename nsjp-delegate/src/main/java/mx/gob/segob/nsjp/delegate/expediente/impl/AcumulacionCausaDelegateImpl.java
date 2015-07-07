/**
* Nombre del Programa : AcumulacionCausaDelegateImpl.java
* Autor                            : JIFO
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 02/04/2015
* Marca de cambio        : N/A
* Descripcion General    : 
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
package mx.gob.segob.nsjp.delegate.expediente.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.delegate.expediente.AcumulacionCausaDelegate;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.AcumulacionNumeroExpedienteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.expediente.AcumulacionCausaService;



/**
 * Implementación del delegate de acumulacion de causas
 * @version 1.0
 * @author JIFO
 *
 */
@Service
public class AcumulacionCausaDelegateImpl implements AcumulacionCausaDelegate {

	@Autowired
	AcumulacionCausaService acumulacionCausaService;

	@Override
	 public List<AcumulacionNumeroExpedienteDTO> consultarAcumulacionCausa(String numeroExpediente)
	            throws NSJPNegocioException{
		return acumulacionCausaService.consultarAcumulacionCausa(numeroExpediente);
	 }
	@Override
	 public String crearAcumulacion(AcumulacionNumeroExpedienteDTO acumulacion) throws NSJPNegocioException {
		 return acumulacionCausaService.crearAcumulacion(acumulacion);
	 }
	
}

/**
* Nombre del Programa : CamposFormaTransformer.java
* Autor                            : Emigdio Hern�ndez
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 06/06/2011
* Marca de cambio        : N/A
* Descripcion General    : Clase de transoformaci�n para el objeto del modelo de CamposForma
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
package mx.gob.segob.nsjp.service.forma.impl.transform;

import mx.gob.segob.nsjp.dto.forma.CamposFormaDTO;
import mx.gob.segob.nsjp.model.CamposForma;

/**
 * Clase de transformaci�n para el objeto del modelo de CamposForma
 * @version 1.0
 * @author Emigdio Hern�ndez
 *
 */
public class CamposFormaTransformer {

	/**
	 * Transforma un objeto <code>CamposForma</code> en un DTO del tipo
	 * <code>CamposFormaDTO</code>
	 * @param src Datos de origen
	 * @return DTO con los datos de origen
	 */
	public static CamposFormaDTO transformarCamposForma(CamposForma src){
		CamposFormaDTO destino = null;
		if(src != null){
			destino = new CamposFormaDTO();
			destino.setCamposFormaId(src.getCamposFormaId());
			destino.setDescripcion(src.getDescripcion());
			destino.setNombreNegocio(src.getNombreNegocio());
			destino.setRutaDato(src.getRutaDato());		
		}
		return destino;
	}

	/**
	 * Transforma un objeto <code>CamposFormaDTO</code> en un Entity del tipo
	 * <code>CamposForma</code>
	 * @param  Datos de origen
	 * @return DTO con los datos de origen
	 */
	public static CamposForma transformar(CamposFormaDTO src){
		CamposForma destino = null;
		if(src != null){
			destino = new CamposForma();
			destino.setCamposFormaId(src.getCamposFormaId());
			destino.setDescripcion(src.getDescripcion());
			destino.setNombreNegocio(src.getNombreNegocio());
			destino.setRutaDato(src.getRutaDato());		
		}
		return destino;
	}	
	
}

/**
* Nombre del Programa : LugarTransfromer.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 15 Jun 2011
* Marca de cambio        : N/A
* Descripcion General    : Clase para realizar la conversion de Lugar a LugarDTO y viceversa
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
package mx.gob.segob.nsjp.service.lugar.impl.transform;

import mx.gob.segob.nsjp.dto.domicilio.LugarDTO;
import mx.gob.segob.nsjp.model.CoordenadaGeografica;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Lugar;
import mx.gob.segob.nsjp.model.Valor;

/**
 * Clase para realizar la conversion de Lugar a LugarDTO y viceversa.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public class LugarTransformer {
	
	/**
	 * 
	 * @param lugarDTO
	 * @return
	 */
	public static Lugar transformarLugar(LugarDTO lugarDTO) {
		Lugar lugar = new Lugar();
		//Lugar
		lugar.setDescripcion(lugarDTO.getDescripcion());
		lugar.setElementoId(lugarDTO.getElementoId());
		
		//Elemento
		lugar.setFechaCreacionElemento(lugarDTO.getFechaCreacionElemento());
		if (lugarDTO.getValorIdElemento()!=null)
			lugar.setTipoElemento(new Valor(lugarDTO.getValorIdElemento().getIdCampo()));
		if (lugarDTO.getExpedienteDTO()!=null)
			lugar.setExpediente(new Expediente(lugarDTO.getExpedienteDTO().getExpedienteId()));
		
		return lugar;
	}

	public static LugarDTO transformarLugar(Lugar lugar) {
		if (lugar==null){
			return null;
		}
		LugarDTO lugarDTO = new LugarDTO();
		lugarDTO.setElementoId(lugar.getElementoId());
		//Lugar
		lugarDTO.setDescripcion(lugar.getDescripcion());		
		//Elemento
		lugarDTO.setFechaCreacionElemento(lugar.getFechaCreacionElemento());
		
		if(lugar.getCoordenadaGeograficas()!= null && !lugar.getCoordenadaGeograficas().isEmpty()){
			for (CoordenadaGeografica coorGeo : lugar.getCoordenadaGeograficas()) {
				lugarDTO.setLatitud(coorGeo.getLatitud());
				lugarDTO.setLongitud(coorGeo.getLongitud());
			}		
		}
		return lugarDTO;
	}
	
}

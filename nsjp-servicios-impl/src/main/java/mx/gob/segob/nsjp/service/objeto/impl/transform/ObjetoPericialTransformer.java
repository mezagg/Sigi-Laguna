/**
* Nombre del Programa : ObjetoPericialTransformer.java                                    
* Autor                            : GustavoBP                                              
* Compania                    : Ultrasist                                                
* Proyecto                      : NSJP                    Fecha: 12/09/2012 
* Marca de cambio        : N/A                                                     
* Descripcion General    : tranformer de los objetos del Tipo Pericial                      
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
package mx.gob.segob.nsjp.service.objeto.impl.transform;

import java.util.Date;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.objeto.ObjetoPericialDTO;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.ObjetoPericial;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.archivo.impl.transform.ArchivoDigitalTransformer;

/**
 * tranformer de los objetos del Tipo Pericial
 * 
 * @version 1.0
 * @author GustavoBP
 *
 */
public class ObjetoPericialTransformer {

	public static ObjetoPericial transformarObjetoPericial(ObjetoPericialDTO objetoPericialDTO){
		
		ObjetoPericial objetoPericial = new ObjetoPericial();
		
		objetoPericial = (ObjetoPericial) ObjetoTransformer.transformarObjeto(objetoPericialDTO,objetoPericial);
		
		if(objetoPericialDTO.getFolioElemento() != null){
			objetoPericial.setFolioElemento(objetoPericialDTO.getFolioElemento());
		}
		
		if(objetoPericialDTO.getCategoriaIndicioVal()!=null && objetoPericialDTO.getCategoriaIndicioVal().getIdCampo()!=null){
			objetoPericial.setCategoriaIndicioVal(new Valor(objetoPericialDTO.getCategoriaIndicioVal().getIdCampo()));
		}

		if(objetoPericialDTO.getIndicioVal()!=null && objetoPericialDTO.getIndicioVal().getIdCampo()!=null){
			objetoPericial.setIndicioVal(new Valor(objetoPericialDTO.getIndicioVal().getIdCampo()));
		}
		
		if(objetoPericialDTO.getValorByCondicionFisicaVal()!=null && objetoPericialDTO.getValorByCondicionFisicaVal().getIdCampo()!=null)
			objetoPericial.setValorByCondicionFisicaVal(new Valor(objetoPericialDTO.getValorByCondicionFisicaVal().getIdCampo()));
		
		if(objetoPericialDTO.getExpedienteDTO()!=null){
			objetoPericial.setExpediente(new Expediente(objetoPericialDTO.getExpedienteDTO().getExpedienteId()));
			objetoPericial.setFechaCreacionElemento(new Date());
		}	
		
		objetoPericial.setDescripcion(objetoPericialDTO.getDescripcion());
		
		//Permite guardar la foto de un elemento
		objetoPericial.setArchivoDigital(ArchivoDigitalTransformer.transformarArchivoDigitalDTO(objetoPericialDTO.getFotoDelElemento()));		
		objetoPericial.setEsActivo(objetoPericialDTO.getEsActivo());
		
		return objetoPericial;
	}
	
	public static ObjetoPericialDTO transformarObjetoPericialDTO(ObjetoPericial objetoPericial){
		ObjetoPericialDTO objetoPericialDTO = new ObjetoPericialDTO();
		
		if(objetoPericial.getCategoriaIndicioVal()!=null ){
			objetoPericialDTO.setCategoriaIndicioVal(new ValorDTO(
					objetoPericial.getCategoriaIndicioVal().getValorId(),
					objetoPericial.getCategoriaIndicioVal().getValor()));
		}

		if(objetoPericial.getIndicioVal()!=null ){
			objetoPericialDTO.setIndicioVal(new ValorDTO(objetoPericial
					.getIndicioVal().getValorId(), objetoPericial
					.getIndicioVal().getValor()));
		}
		
		objetoPericialDTO.setDescripcion(objetoPericial.getDescripcion());
		
		return objetoPericialDTO;
	}
}

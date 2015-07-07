/**
 *
 * Nombre del Programa : CatEtapaTransformer.java                                    
 * Autor                            : GustavoBP                                               
 * Compania                    : Ultrasist                                                
 * Proyecto                      : NSJP                    Fecha: 21/01/2013 
 * Marca de cambio        : N/A                                                     
 * Descripcion General    : Clase que transforma una entidad de BD a un DTO, y viceversa.                      
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
package mx.gob.segob.nsjp.service.catalogo.impl.transform;

import mx.gob.segob.nsjp.dto.catalogo.CatEtapaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.model.CatEtapa;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.ValorTransformer;

/**
 * Clase que transforma una entidad de BD a un DTO, y viceversa.
 * 
 * @author GustavoBP
 */
public class CatEtapaTransformer {

	public static CatEtapaDTO transformar(CatEtapa catEtapa) {
		
		if (catEtapa == null) {
			return null;
		}
		
		CatEtapaDTO catEtapaDTO = CatEtapaTransformer.transformarBasico(catEtapa);

		if (catEtapa.getCatEtapaPadre() != null) {
			catEtapaDTO.setCatEtapaPadre(CatEtapaTransformer
					.transformar(catEtapa.getCatEtapaPadre()));
		}
		return catEtapaDTO;
	}
	
	public static CatEtapaDTO transformarBasico(CatEtapa catEtapa) {
		if (catEtapa == null) {
			return null;
		}
		CatEtapaDTO catEtapaDTO = new CatEtapaDTO();

		catEtapaDTO.setCatEtapaId(catEtapa.getCatEtapaId());
		if (catEtapa.getCatEtapaValor() != null) {
			ValorDTO catEtapaValor = ValorTransformer.transformar(catEtapa
					.getCatEtapaValor());
			catEtapaDTO.setCatEtapaValor(catEtapaValor);
		}
		catEtapaDTO.setEsEtapaExpediente(catEtapa.getEsEtapaExpediente());

		return catEtapaDTO;
	}

	
	public static CatEtapa transformar(CatEtapaDTO catEtapaDTO) {
		
		if (catEtapaDTO == null) {
			return null;
		}
		
		CatEtapa catEtapa = CatEtapaTransformer.transformarBasico(catEtapaDTO);

		if (catEtapaDTO.getCatEtapaPadre() != null) {
			catEtapa.setCatEtapaPadre(CatEtapaTransformer
					.transformar(catEtapaDTO.getCatEtapaPadre()));
		}
		return catEtapa;
	}

	public static CatEtapa transformarBasico(CatEtapaDTO catEtapaDTO) {
		if (catEtapaDTO == null) {
			return null;
		}
		CatEtapa catEtapa = new CatEtapa();

		catEtapa.setCatEtapaId(catEtapaDTO.getCatEtapaId());
		if (catEtapaDTO.getCatEtapaValor() != null) {
			Valor catEtapaValor = ValorTransformer.transformar(catEtapaDTO
					.getCatEtapaValor());
			catEtapa.setCatEtapaValor(catEtapaValor);
		}
		catEtapa.setEsEtapaExpediente(catEtapaDTO.getEsEtapaExpediente());

		return catEtapa;
	}
}

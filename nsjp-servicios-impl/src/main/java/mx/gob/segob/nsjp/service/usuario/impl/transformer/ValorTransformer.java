/**
 * 
 */
package mx.gob.segob.nsjp.service.usuario.impl.transformer;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.model.Valor;

/**
 * @author LuisMG
 *
 */
public class ValorTransformer {

	public static ValorDTO transformar (Valor valor){
		ValorDTO resp = null;
		if (valor!=null){
			resp= new ValorDTO();
			resp.setValor(valor.getValor());
			resp.setIdCampo(valor.getValorId());
		}
		return resp;
	}
	
	public static Valor transformar (ValorDTO valorDTO){
		Valor resp=null;
		if (valorDTO!=null){
			resp= new Valor();
			resp.setValor(valorDTO.getValor());
			resp.setValorId(valorDTO.getIdCampo());
		}
		return resp;
	}
	
	public static List<Valor> transformar (List<ValorDTO> valoresDTO){
		List<Valor> entities = null;
		if (valoresDTO != null
				&& !valoresDTO.isEmpty()){
			entities = new ArrayList<Valor>();
			for (ValorDTO dto : valoresDTO){
				entities.add(transformar(dto));
			}
		}
		return entities;
	}
	
}

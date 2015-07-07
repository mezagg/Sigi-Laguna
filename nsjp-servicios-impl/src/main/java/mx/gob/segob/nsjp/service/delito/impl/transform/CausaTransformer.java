package mx.gob.segob.nsjp.service.delito.impl.transform;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.delito.CausaDTO;
import mx.gob.segob.nsjp.model.Causa;

public class CausaTransformer {

	/**
	 * Metodo que convierte una lista de causas a lista de causasDTO
	 * @param causas
	 * @return
	 */
	public static List<CausaDTO> transformarCausas(List<Causa> causas) {
		List<CausaDTO> causasDTO = new ArrayList<CausaDTO>();
		for(Causa causa : causas){
			causasDTO.add(transformar(causa));
		}
		return causasDTO;
	}
	
	/**
	 * Método que realiza la transformación de TODO el entity de Causa a DTO. 
	 * @param Causa
	 * @return	CausaDTO
	 */
	public static CausaDTO transformar(Causa causa) {
		CausaDTO causaDTO = null;
		if (causa != null) {
			
			causaDTO = new CausaDTO();
			causaDTO.setCausaId(causa.getCausaId());
			causaDTO.setDescripcionCausa(causa.getDescripcionCausa());
			causaDTO.setNombreCausa(causa.getNombreCausa());
			
			causaDTO.setCausaPadre(transformar(causa.getCausaPadre()));
			
		}
		return causaDTO;
	}
	


	/**
	 * Método que realiza la transformación de TODO el dto de RolDTO a Rol. 
	 * @param rolDTO
	 * @return	Rol
	 */
	public static Causa transformar(CausaDTO causaDTO) {
		Causa causa = null;
		if (causaDTO != null) {
			
			causa = new Causa();
			causa.setCausaId(causaDTO.getCausaId());
			causa.setDescripcionCausa(causaDTO.getDescripcionCausa());
			causa.setNombreCausa(causaDTO.getNombreCausa());
			
			causa.setCausaPadre(transformar(causaDTO.getCausaPadre()));
			
		}
		return causa;
	}



}

package mx.gob.segob.nsjp.service.hecho.impl.transform;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.hecho.ConclusionHechoDTO;
import mx.gob.segob.nsjp.model.ConclusionHecho;
import mx.gob.segob.nsjp.model.Valor;

public class ConclusionHechoTransformer {
	
	public static ConclusionHecho transformarConclusion(ConclusionHechoDTO dto){
		ConclusionHecho conclusion = new ConclusionHecho();
		
		conclusion.setHecho(HechoTransformer.transformarHecho(dto.getHecho()));
		conclusion.setFechaConclusion(dto.getFechaConclusion() != null ? dto.getFechaConclusion() : null);
		conclusion.setTipoConclusion(dto.getTipoConclusion() != null ? new Valor(dto.getTipoConclusion().getIdCampo()) : null);
		conclusion.setTipoSubConclusion(dto.getTipoSubConclusion() != null ? new Valor(dto.getTipoSubConclusion().getIdCampo()) : null);
		
		return conclusion;
	} 
	
	public static ConclusionHechoDTO transformarConclusion(ConclusionHecho conclusion){
		ConclusionHechoDTO dto = new ConclusionHechoDTO();
		
		dto.setHecho(HechoTransformer.transformarHecho(conclusion.getHecho()));
		dto.setFechaConclusion(conclusion.getFechaConclusion() != null ? conclusion.getFechaConclusion() : null);
		dto.setTipoConclusion(conclusion.getTipoConclusion() != null && conclusion.getTipoConclusion().getValorId() != null ? 
				new ValorDTO(conclusion.getTipoConclusion().getValorId()) : null);
		dto.setTipoSubConclusion(conclusion.getTipoSubConclusion() != null && conclusion.getTipoSubConclusion().getValorId() != null ?
				new ValorDTO(conclusion.getTipoSubConclusion().getValorId()) : null);
		
		return dto;
	}

}

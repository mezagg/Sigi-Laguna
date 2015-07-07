package mx.gob.segob.nsjp.service.audiencia.impl.transform;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoDTO;
import mx.gob.segob.nsjp.dto.resolutivo.ResolutivoViewDTO;
import mx.gob.segob.nsjp.model.Audiencia;
import mx.gob.segob.nsjp.model.Mandamiento;
import mx.gob.segob.nsjp.model.Resolutivo;
import mx.gob.segob.nsjp.service.documento.impl.tranform.MandamientoJudicialTransformer;

public class ResolutivoTransformer {

	public static Resolutivo transformarResolutivo(ResolutivoDTO resolutivo) {

		if (resolutivo == null) {
			return null;
		}

		Resolutivo resol = new Resolutivo();

		if (resolutivo.getAudiencia() != null) {
			Audiencia aud = new Audiencia();
			aud.setAudienciaId(resolutivo.getAudiencia().getId());
			resol.setAudiencia(aud);
		}
		resol.setDetalle(resolutivo.getDetalle());
		resol.setTemporizador(resolutivo.getTemporizador());
		return resol;
	}

	public static ResolutivoDTO transformarResolutivo(Resolutivo resolutivo) {

		if (resolutivo == null) {
			return null;
		}

		ResolutivoDTO resolutivoDTO = new ResolutivoDTO();

		resolutivoDTO.setResolutivoId(resolutivo.getResolutivoId());

		if (resolutivo.getAudiencia() != null) {
			AudienciaDTO aud = EventoTransformer
					.transformarAudienciaBasico(resolutivo.getAudiencia());
			resolutivoDTO.setAudiencia(aud);
		}
		resolutivoDTO.setDetalle(resolutivo.getDetalle());
		resolutivoDTO.setTemporizador(resolutivo.getTemporizador());

		return resolutivoDTO;
	}
	
	public static ResolutivoDTO transformarResolutivoCompleto(
			Resolutivo resolutivo) {

		if (resolutivo == null) {
			return null;
		}

		ResolutivoDTO resolutivoDTO = transformarResolutivo(resolutivo);

		List<MandamientoDTO> mandamientosDTO = new ArrayList<MandamientoDTO>();

		if (resolutivo.getMandamientos() != null
				&& !resolutivo.getMandamientos().isEmpty()) {
			for (Mandamiento mandamiento : resolutivo.getMandamientos()) {
				mandamientosDTO.add(MandamientoJudicialTransformer
						.transformarMandamientoBasico(mandamiento));
			}
			resolutivoDTO.setMandamientos(mandamientosDTO);
		}
		return resolutivoDTO;
	}
	
	public static ResolutivoViewDTO transformarResolutivoView(
			Resolutivo resolutivo) {

		if (resolutivo == null) {
			return null;
		}

		ResolutivoViewDTO dto = new ResolutivoViewDTO();

		dto.setResolutivoId(resolutivo.getResolutivoId());
		dto.setTemporizador(DateUtils.formatearHoraSegs(resolutivo
				.getTemporizador()));
		dto.setDetalle(resolutivo.getDetalle());
		return dto;
	}
}



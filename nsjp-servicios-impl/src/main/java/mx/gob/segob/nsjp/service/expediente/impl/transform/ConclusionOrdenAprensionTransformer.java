package mx.gob.segob.nsjp.service.expediente.impl.transform;

import mx.gob.segob.nsjp.dto.conclusion.ConclusionOrdenAprensionDTO;
import mx.gob.segob.nsjp.model.ConclusionOrdenAprension;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Valor;

public class ConclusionOrdenAprensionTransformer {

	public static ConclusionOrdenAprension transformarConclusionOrdenAprensionDTO(
			ConclusionOrdenAprensionDTO dto) {
		ConclusionOrdenAprension conclusionOrdenAprension = new ConclusionOrdenAprension();

		if (dto.getConclusionOrdenAprensionId() != null) {
			conclusionOrdenAprension.setConclusionOrdenAprensionId(dto
					.getConclusionOrdenAprensionId());
		}
		if (dto.getNumeroExpediente() != null) {
			conclusionOrdenAprension.setNumeroExpediente(new NumeroExpediente(
					dto.getNumeroExpediente().getNumeroExpedienteId()));
		}
		conclusionOrdenAprension.setCorporacionPoliciaca(dto.getCorporacionPoliciaca());
		conclusionOrdenAprension.setNoFichaPago(dto.getNoFichaPago());
		conclusionOrdenAprension.setFechaPago(dto.getFechaPago());
		conclusionOrdenAprension.setMonto(dto.getMonto());
		conclusionOrdenAprension.setEsConReparacion(dto.getEsConReparacion());
		return conclusionOrdenAprension;
	}

}

/**
 * 
 */
package mx.gob.segob.nsjp.service.alarma.impl.transform;

import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.dto.alarmas.AlarmaDTO;
import mx.gob.segob.nsjp.dto.alarmas.AlertaDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.model.Alarma;
import mx.gob.segob.nsjp.model.Alerta;
import mx.gob.segob.nsjp.model.Notificacion;
import mx.gob.segob.nsjp.model.Valor;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;

/**
 * @author adrian
 * 
 */
public class AlertaTransformer {

	public static Alerta transformarAlertaDTO(AlertaDTO dto) {
		Alerta aler = new Alerta();

		if (dto.getAlertaId() != null)
			aler.setAlertaId(dto.getAlertaId());

		aler.setFechaAlerta(dto.getFechaAlerta());

		if (dto.getTipoAlerta() != null)
			aler.setTipoAlerta(new Valor(dto.getTipoAlerta().getIdCampo()));
		if (dto.getAlarma() != null)
			aler.setAlarma(new Alarma(dto.getAlarma().getAlarmaId()));
		if (dto.getEstatusAlarmaAlerta() != null)
			aler.setEstatusAlarmaAlerta(new Valor(dto.getEstatusAlarmaAlerta()
					.getIdCampo(), dto.getEstatusAlarmaAlerta().getValor()));

		aler.setNombre(dto.getNombre());
		aler.setEsAplaza(dto.getEsAplaza());
		aler.setTiempo(dto.getTiempo());
		aler.setUnidadDeTiempo(new Valor(dto.getUnidadDeTiempo().getIdCampo(),
				dto.getUnidadDeTiempo().getValor()));
		aler.setUsuario(UsuarioTransformer.transformarDTO(dto.getUsuario()));

		return aler;
	}

	public static AlertaDTO transformarAlerta(Alerta ale) {
		AlertaDTO dto = new AlertaDTO();
		AlarmaDTO alarmaDto = new AlarmaDTO();

		dto.setAlertaId(ale.getAlertaId());
		dto.setFechaAlerta(ale.getFechaAlerta());
		if (ale.getEstatusAlarmaAlerta() != null)
			dto.setEstatusAlarmaAlerta(new ValorDTO(ale
					.getEstatusAlarmaAlerta().getValorId(), ale
					.getEstatusAlarmaAlerta().getValor()));
		if (ale.getTipoAlerta() != null)
			dto.setTipoAlerta(new ValorDTO(ale.getTipoAlerta().getValorId(),
					ale.getTipoAlerta().getValor()));

		dto.setNombre(ale.getNombre());
		dto.setEsAplaza(ale.getEsAplaza()); // habilita el bot�n "posponer"
		dto.setTiempo(ale.getTiempo());
		if (ale.getUnidadDeTiempo() != null)
			dto.setUnidadDeTiempo(new ValorDTO(ale.getUnidadDeTiempo()
					.getValorId(), ale.getUnidadDeTiempo().getValor()));
		if(ale.getUsuario()!=null )
			dto.setUsuario(UsuarioTransformer.transformarUsuarioMinimoSinRoles(ale
				.getUsuario()));
		if(ale.getAlarma() != null){
			alarmaDto.setAlarmaId(ale.getAlarma().getAlarmaId());
			dto.setAlarma(alarmaDto);
		}

		return dto;
	}

	public static AlertaDTO transformarNotificacion(Notificacion ale) {
		AlertaDTO dto = new AlertaDTO();

		dto.setAlertaId(ale.getDocumentoId());
		dto.setFechaAlerta(ale.getFechaCreacion());
		if (ale.getEstatus() != null)
			dto.setEstatusAlarmaAlerta(new ValorDTO(ale.getEstatus()
					.getValorId(), ale.getEstatus().getValor()));

		if (ale.getAudiencia() != null) {
			dto.setNombre("Notificaci�n de audiencia "
					+ ale.getAudiencia().getFolioAudiencia()
					+ " para el d�a "
					+ DateUtils.formatear(ale.getAudiencia()
							.getFechaAudiencia())
					+ " a las "
					+ DateUtils.formatearHora(ale.getAudiencia()
							.getFechaAudiencia()) + " Hrs.");
		}

		dto.setEsAplaza(Boolean.FALSE);

		return dto;
	}
}

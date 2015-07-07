/**
 * Nombre del Programa		: MandamientoWSDTOTransformer.java
 * Autor                    : AlejandroGA
 * Compania                 : Ultrasist
 * Proyecto                 : NSJP							Fecha: 26/06/2013
 * Marca de cambio			: N/A
 * Descripcion General		: MandamientoWSDTOTransformer
 * Programa Dependiente		: N/A
 * Programa Subsecuente		: N/A
 * Cond. de ejecucion       : N/A
 * Dias de ejecucion        : N/A                           Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                    : N/A
 * Compania               	: N/A
 * Proyecto                 : N/A                           Fecha: N/A
 * Modificacion           	: N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.infra.impl.transform.registrarmandamiento;

import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoDTO;
import mx.gob.segob.nsjp.dto.documento.MandamientoWSDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class MandamientoWSDTOTransformer {

	public static MandamientoDTO Transformar(MandamientoWSDTO mandamientoWSDTO) {

		MandamientoDTO mandamientoDTO = null;
		
		if (mandamientoWSDTO == null) {
			return mandamientoDTO;
		}

		// Transforma atributos del documento
		mandamientoDTO = (MandamientoDTO) DocumentoWSDTOTransformer
				.transformar(mandamientoWSDTO);

		// Estatus del mandamiento
		if (mandamientoWSDTO.getEstatus() != null) {
			mandamientoDTO.setEstatus(new ValorDTO(mandamientoWSDTO
					.getEstatus()));
		}

		// Tipo de mandamiento
		if (mandamientoWSDTO.getIdTipoMandamiento() != null) {
			mandamientoDTO.setTipoMandamiento(new ValorDTO(mandamientoWSDTO
					.getIdTipoMandamiento()));
		}

		// Numero de causa
		mandamientoDTO.setNumeroCausa(mandamientoWSDTO.getNumeroCausa());

		// Relaciones Mandamiento DelitoPersona
		if (mandamientoWSDTO.getDelitosPersona() != null
				&& !mandamientoWSDTO.getDelitosPersona().isEmpty()) {
			mandamientoDTO.setDelitosPersona(DelitoPersonaWSDTOTransformer
					.transformarListaWSDTOaDTO(mandamientoWSDTO
							.getDelitosPersona()));

		}

		// Relaciones mandamiento persona
		if(mandamientoWSDTO
				.getMandamientosPersona() != null && !mandamientoWSDTO
				.getMandamientosPersona().isEmpty()){
			mandamientoDTO
			.setMandamientosPersona(MandamientoPersonaWSDTOTransformer
					.transformarListaWSDTOaDTO(mandamientoWSDTO
							.getMandamientosPersona()));			
		}

		return mandamientoDTO;
	}

	public static mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoWSDTO Transformar(
			MandamientoDTO mandamientoDTO) {

		mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoWSDTO mandamientoWSDTO = null;
		
		if (mandamientoDTO == null) {
			return null;
		}

		// Atributos del documento
		mandamientoWSDTO = (mx.gob.segob.nsjp.ws.cliente.mandamiento.MandamientoWSDTO) DocumentoWSDTOTransformer
				.transformar(mandamientoDTO);

		// Estatus del mandamiento
		if (mandamientoDTO.getEstatus() != null) {
			mandamientoWSDTO.setEstatus(mandamientoDTO.getEstatus()
					.getIdCampo());
		}

		// Tipo de mandamiento
		if (mandamientoDTO.getTipoMandamiento() != null) {
			mandamientoWSDTO.setIdTipoMandamiento(mandamientoDTO
					.getTipoMandamiento().getIdCampo());
		}

		// Numero de causa
		mandamientoWSDTO.setNumeroCausa(mandamientoDTO.getNumeroCausa());

		// Relaciones Mandamiento DelitoPersona
		if (mandamientoDTO.getDelitosPersona() != null
				&& !mandamientoDTO.getDelitosPersona().isEmpty()) {
			mandamientoWSDTO.getDelitosPersona().addAll(
					DelitoPersonaWSDTOTransformer
							.transformarListaDTOaWSDTO(mandamientoDTO
									.getDelitosPersona()));
		}

		// Relaciones mandamiento persona
		if (mandamientoDTO.getMandamientosPersona() != null
				&& !mandamientoDTO.getMandamientosPersona().isEmpty()) {
			mandamientoWSDTO.getMandamientosPersona().addAll(
					MandamientoPersonaWSDTOTransformer
							.transformarListaDTOaWSDTO(mandamientoDTO
									.getMandamientosPersona()));

		}

		return mandamientoWSDTO;
	}

}

/**
 * Nombre del Programa	: FuncionarioExternoAudienciaTransformer.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 28 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : permite tranformar una entidad FuncionarioExternoAudiencia a DTO 
 * 						  y viceversa;
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                             Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform;

import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
import mx.gob.segob.nsjp.model.FuncionarioExternoAudiencia;
import mx.gob.segob.nsjp.service.audiencia.impl.transform.AudienciaTransformer;

/**
 * Permite tranformar una entidad FuncionarioExternoAudiencia a DTO y viceversa;
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class FuncionarioExternoAudienciaTransformer {

	public static FuncionarioExternoAudienciaDTO transformar(
			FuncionarioExternoAudiencia funcionarioExternoAudiencia) {

		if (funcionarioExternoAudiencia == null) {
			return null;
		}

		FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO = new FuncionarioExternoAudienciaDTO();

		funcionarioExternoAudienciaDTO.setAudienciaDTO(AudienciaTransformer
				.transformarBasico(funcionarioExternoAudiencia.getAudiencia()));		

		if (funcionarioExternoAudiencia.getFuncionarioExternoAudienciaId() != null) {

			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO = new FuncionarioExternoAudienciaIdDTO(
					funcionarioExternoAudiencia
							.getFuncionarioExternoAudienciaId()
							.getAudienciaId(), funcionarioExternoAudiencia
							.getFuncionarioExternoAudienciaId()
							.getFuncionarioExternoId());

			funcionarioExternoAudienciaDTO
					.setFuncionarioExternoAudienciaIdDTO(funcionarioExternoAudienciaIdDTO);
		}

		funcionarioExternoAudienciaDTO
				.setFuncionarioExternoDTO(FuncionarioExternoTransformer
						.transformar(funcionarioExternoAudiencia
								.getFuncionarioExterno()));

		funcionarioExternoAudienciaDTO
				.setEsPresente(funcionarioExternoAudiencia.getEsPresente());
		funcionarioExternoAudienciaDTO.setEsTitular(funcionarioExternoAudiencia
				.getEsTitular());

		return funcionarioExternoAudienciaDTO;

	}

}

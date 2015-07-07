/**
 * Nombre del Programa	: IngresarFuncionarioExternoAudienciaServiceImplTest.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 01 Marzo 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Service Test de ingersarFuncionarioAudienciaServiceImpl
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
package mx.gob.segob.nsjp.service.test.audiencia.impl;

import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.service.audiencia.IngresarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Service Test de ingersarFuncionarioAudienciaServiceImpl
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class IngresarFuncionarioExternoAudienciaServiceImplTest extends
		BaseTestServicios<IngresarFuncionarioExternoAudienciaService> {

	public void testIngresarFuncionarioExternoAudienciaService()
			throws NSJPNegocioException {

		FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();
		AudienciaDTO audienciaDTO = new AudienciaDTO();

		funcionarioExternoDTO.setCveFuncionarioInstExt(new Long("90"));
		funcionarioExternoDTO.setNombre("Oscar");
		funcionarioExternoDTO.setApellidoPaterno("Chavez");
		funcionarioExternoDTO.setApellidoMaterno("Salas");
		funcionarioExternoDTO.setEmail("os@hotmail.com");
		funcionarioExternoDTO.setPuesto(Puestos.FISCAL.toString());
		funcionarioExternoDTO.setConfInstitucionDTO(new ConfInstitucionDTO(
				Instituciones.PGJ.getValorId()));
		funcionarioExternoDTO.setArea(Areas.AGENCIA_DEL_MINISTERIO_PUBLICO
				.toString());
		funcionarioExternoDTO.setFuncionarioExternoId(23L);

		audienciaDTO.setId(new Long("793"));

		try {
			FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO = service
					.ingresarFuncionarioExternoAudiencia(funcionarioExternoDTO,
							audienciaDTO, true);

			if (funcionarioExternoAudienciaDTO != null) {

				if (funcionarioExternoAudienciaDTO
						.getFuncionarioExternoAudienciaIdDTO() != null) {
					logger.info("LLave de la asociacion");
					logger.info("Funcionario Externo id:"
							+ funcionarioExternoAudienciaDTO
									.getFuncionarioExternoAudienciaIdDTO()
									.getFuncionarioExternoId());

					logger.info("Audiencia id:"
							+ funcionarioExternoAudienciaDTO
									.getFuncionarioExternoAudienciaIdDTO()
									.getAudienciaId());
				}
				logger.info("Es presente"
						+ funcionarioExternoAudienciaDTO.getEsPresente());
				logger.info("Es titular"
						+ funcionarioExternoAudienciaDTO.getEsTitular());
			} else {
				logger.info("NO SE ASOCIO EL FUNCIONARIO A LA AUDIENCIA");
			}

		} catch (Exception e) {
			logger.info("Exception e"+e);
		}
	}

}

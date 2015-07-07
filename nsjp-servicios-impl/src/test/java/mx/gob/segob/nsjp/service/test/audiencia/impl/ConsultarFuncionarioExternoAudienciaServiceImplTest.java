/**
 * Nombre del Programa	: ConsultarFuncionarioExternoAudienciaServiceImplTest.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 05 Marzo 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Prueba Unitaria del servicio ConsultarFuncionarioExternoAudienciaService
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

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.documento.NotificacionDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoAudienciaIdDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.service.audiencia.ConsultarFuncionarioExternoAudienciaService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria del servicio ConsultarFuncionarioExternoAudienciaService
 * 
 * @author AlejandroGA
 * @version 1.0
 */
public class ConsultarFuncionarioExternoAudienciaServiceImplTest extends
		BaseTestServicios<ConsultarFuncionarioExternoAudienciaService> {

	public void testConsultarFuncionarioExternoAudienciaPorId() {
		try {

			FuncionarioExternoAudienciaIdDTO funcionarioExternoAudienciaIdDTO = new FuncionarioExternoAudienciaIdDTO(
					789L, 15L);

			FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO = service
					.ConsultarFuncionarioExternoAudienciaPorId(funcionarioExternoAudienciaIdDTO);
			if (funcionarioExternoAudienciaDTO != null) {
				
				logger.info("Funcionario Externo Audiencia...........");
				logger.info("Funcionario Externo Id:"
						+ funcionarioExternoAudienciaDTO
								.getFuncionarioExternoDTO()
								.getFuncionarioExternoId());
				logger.info("Audiencia Id:"
						+ funcionarioExternoAudienciaDTO.getAudienciaDTO()
								.getId());
				logger.info("Es presente:"
						+ funcionarioExternoAudienciaDTO.getEsPresente());

				logger.info("Es titular:"
						+ funcionarioExternoAudienciaDTO.getEsTitular());
				
				logger.info("Nombre:"
						+ funcionarioExternoAudienciaDTO
								.getFuncionarioExternoDTO().getNombre());
				logger.info("ApPaterno:"
						+ funcionarioExternoAudienciaDTO
								.getFuncionarioExternoDTO()
								.getApellidoPaterno());
				logger.info("ApMaterno:"
						+ funcionarioExternoAudienciaDTO
								.getFuncionarioExternoDTO()
								.getApellidoMaterno());
				logger.info("Clave Inst Externa:"
						+ funcionarioExternoAudienciaDTO
								.getFuncionarioExternoDTO()
								.getCveFuncionarioInstExt());
				if (funcionarioExternoAudienciaDTO.getFuncionarioExternoDTO()
						.getRolDTO() != null) {
					logger.info("Nombre Rol:"
							+ funcionarioExternoAudienciaDTO
									.getFuncionarioExternoDTO().getRolDTO()
									.getNombreRol());
				}

			} else {
				logger.info("NO SE ENCONTRO EL REGISTRO:");
			}

		} catch (Exception e) {
			
		}
	}

	public void testConsultarFuncionarioExternoAudienciaPorRol() {
		try {
			FuncionarioExternoAudienciaDTO funcionarioExternoAudienciaDTO = new FuncionarioExternoAudienciaDTO();

			funcionarioExternoAudienciaDTO.setAudienciaDTO(new AudienciaDTO(
					742L));
			FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();
			funcionarioExternoDTO.setRolDTO(new RolDTO(Roles.DEFENSOR
					.getValorId()));
			funcionarioExternoAudienciaDTO
					.setFuncionarioExternoDTO(funcionarioExternoDTO);

			List<FuncionarioExternoDTO> funcionarioExternoDTOList = service
					.consultarFuncionarioExternoAudienciaPorRol(funcionarioExternoAudienciaDTO);

			if (funcionarioExternoDTOList != null
					&& !funcionarioExternoDTOList.isEmpty()) {
				logger.info("TAMANIO DE LA LISTA:"
						+ funcionarioExternoDTOList.size());
				for (FuncionarioExternoDTO funcionarioExternoDto : funcionarioExternoDTOList) {
					logger.info("Funcionario Externo...........");
					logger.info("Nombre:" + funcionarioExternoDto.getNombre());
					logger.info("ApPaterno:"
							+ funcionarioExternoDto.getApellidoPaterno());
					logger.info("ApMaterno:"
							+ funcionarioExternoDto.getApellidoMaterno());
					logger.info("Clave Inst Externa:"
							+ funcionarioExternoDto.getCveFuncionarioInstExt());
					if (funcionarioExternoDto.getRolDTO() != null) {
						logger.info("Nombre Rol:"
								+ funcionarioExternoDto.getRolDTO()
										.getNombreRol());
					}
					if (funcionarioExternoDto.getConfInstitucionDTO() != null) {
						logger.info("Nombre Institucion:"
								+ funcionarioExternoDto.getConfInstitucionDTO()
										.getNombreInst());
					}
				}
			} else {
				logger.info("NO SE ENCONTRARON FUNCIONARIOS:");
			}

		} catch (Exception e) {

		}

	}
	
	public void testConsultarFuncionarioExternoAudienciaNotificaciones() {
		try {
			

			List<FuncionarioExternoDTO> funcionarioExternoDTOList = service
					.consultarFuncionarioExternoAudienciaNotificaciones(new AudienciaDTO(827L));
			 assertNotNull("La lista no puede ser nulo", funcionarioExternoDTOList);

			if (funcionarioExternoDTOList != null
					&& !funcionarioExternoDTOList.isEmpty()) {
				logger.info("TAMANIO DE LA LISTA:"
						+ funcionarioExternoDTOList.size());
				for (FuncionarioExternoDTO funcionarioExternoDto : funcionarioExternoDTOList) {
					logger.info("Funcionario Externo...........");
					logger.info("Nombre:" + funcionarioExternoDto.getNombre());
					logger.info("ApPaterno:"
							+ funcionarioExternoDto.getApellidoPaterno());
					logger.info("ApMaterno:"
							+ funcionarioExternoDto.getApellidoMaterno());
					logger.info("Clave Inst Externa:"
							+ funcionarioExternoDto.getCveFuncionarioInstExt());
					if (funcionarioExternoDto.getRolDTO() != null) {
						logger.info("Nombre Rol:"
								+ funcionarioExternoDto.getRolDTO()
										.getNombreRol());
					}
					if (funcionarioExternoDto.getConfInstitucionDTO() != null) {
						logger.info("Nombre Institucion:"
								+ funcionarioExternoDto.getConfInstitucionDTO()
										.getNombreInst());
					}
					if (funcionarioExternoDto.getNotificaciones() != null && !funcionarioExternoDto.getNotificaciones().isEmpty()){
						logger.info("NOTIFICACIONES DEL FUNCIONARIO EXTERNO:");
						for(NotificacionDTO notificacionDto:funcionarioExternoDto.getNotificaciones()){
							logger.info("Notificacion Id:"
									+ notificacionDto.getDocumentoId());
							logger.info("Notificacion consecutivo:"
									+ notificacionDto.getConsecutivoNotificacion());
							logger.info("Notificacion fecha creacion:"
									+ notificacionDto.getFechaCreacion());
							logger.info("Notificacion estatus:"
									+ notificacionDto.getEstatus().getNombreCampo());
						}
					}
					
				}
			} else {
				logger.info("NO SE ENCONTRARON FUNCIONARIOS:");
			}

		} catch (Exception e) {

		}

	}

}

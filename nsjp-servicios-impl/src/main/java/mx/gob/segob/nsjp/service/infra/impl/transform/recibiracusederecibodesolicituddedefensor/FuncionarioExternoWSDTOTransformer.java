/**
 * Nombre del Programa	: FuncionarioExternoWSDTOTransformer.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 27 Feb 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Transformer FuncionarioExternoWSDTO para el paquete de recibiracusederecibodesolicituddedefensor
 * Programa Dependiente	: N/A
 * Programa Subsecuente	: N/A
 * Cond. de ejecucion   : N/A
 * Dias de ejecucion    : N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                : N/A
 * Compania             : N/A
 * Proyecto             : N/A                                 Fecha: N/A
 * Modificacion         : N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.infra.impl.transform.recibiracusederecibodesolicituddedefensor;

import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoWSDTO;
import mx.gob.segob.nsjp.dto.persona.CorreoElectronicoDTO;
import mx.gob.segob.nsjp.dto.persona.MedioDeContactoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;

/**
 * @author AlejandroGA
 * @version 1.0
 */
public class FuncionarioExternoWSDTOTransformer {

	public static mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.FuncionarioExternoWSDTO transformarWSDTO(
			FuncionarioDTO funcionarioDTO) {
		
		if (funcionarioDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.FuncionarioExternoWSDTO funcionarioExternoWSDTO = new mx.gob.segob.nsjp.ws.cliente.recibiracusederecibodesolicituddedefensor.FuncionarioExternoWSDTO();

		funcionarioExternoWSDTO
				.setNombre(funcionarioDTO.getNombreFuncionario());
		funcionarioExternoWSDTO.setApellidoPaterno(funcionarioDTO
				.getApellidoPaternoFuncionario());
		funcionarioExternoWSDTO.setApellidoMaterno(funcionarioDTO
				.getApellidoMaternoFuncionario());
		funcionarioExternoWSDTO.setCveFuncionarioInstExt(funcionarioDTO
				.getClaveFuncionario());
		
		// Agregamos solo un correo electronico
		if (funcionarioDTO.getMediosContacto() != null
				&& !funcionarioDTO.getMediosContacto().isEmpty()) {
			for (MedioDeContactoDTO medioContactoDto : funcionarioDTO
					.getMediosContacto()) {
				if (medioContactoDto instanceof CorreoElectronicoDTO) {
					funcionarioExternoWSDTO
							.setEmail(((CorreoElectronicoDTO) medioContactoDto)
									.getDireccionElectronica());
					break;
				}
			}
		}

		if (funcionarioDTO.getInstitucion() != null
				&& funcionarioDTO.getInstitucion().getConfInstitucionId() != null
				&& funcionarioDTO.getInstitucion().getConfInstitucionId() > 0L) {

			funcionarioExternoWSDTO.setConfInstId(funcionarioDTO
					.getInstitucion().getConfInstitucionId());

		}
		if (funcionarioDTO.getCatAreaNegocio() != null
				&& funcionarioDTO.getCatAreaNegocio().getNombreCatAreaNegocio() != null) {
			funcionarioExternoWSDTO.setArea(funcionarioDTO.getCatAreaNegocio()
					.getNombreCatAreaNegocio());
		}
		if (funcionarioDTO.getPuesto() != null
				&& funcionarioDTO.getPuesto().getValor() != null) {
			funcionarioExternoWSDTO.setPuesto(funcionarioDTO.getPuesto()
					.getValor());
		}
	
		return funcionarioExternoWSDTO;
	}
	
	public static FuncionarioExternoDTO transformarDTO(
			FuncionarioExternoWSDTO FuncionarioExternoWSDTO) {

		if (FuncionarioExternoWSDTO == null) {
			return null;
		}

		FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();

		funcionarioExternoDTO.setNombre(FuncionarioExternoWSDTO.getNombre());
		funcionarioExternoDTO.setApellidoPaterno(FuncionarioExternoWSDTO
				.getApellidoPaterno());
		funcionarioExternoDTO.setApellidoMaterno(FuncionarioExternoWSDTO
				.getApellidoMaterno());
		funcionarioExternoDTO.setCveFuncionarioInstExt(FuncionarioExternoWSDTO
				.getCveFuncionarioInstExt());
		funcionarioExternoDTO.setEmail(FuncionarioExternoWSDTO.getEmail());

		if (FuncionarioExternoWSDTO.getConfInstId() != null
				&& FuncionarioExternoWSDTO.getConfInstId() >= 0L) {
			funcionarioExternoDTO.setConfInstitucionDTO(new ConfInstitucionDTO(
					FuncionarioExternoWSDTO.getConfInstId()));
		}

		funcionarioExternoDTO.setArea(FuncionarioExternoWSDTO.getArea());
		funcionarioExternoDTO.setPuesto(FuncionarioExternoWSDTO.getPuesto());
		if (FuncionarioExternoWSDTO.getRolId() != null
				&& FuncionarioExternoWSDTO.getRolId() > 0L) {
			funcionarioExternoDTO.setRolDTO(new RolDTO(FuncionarioExternoWSDTO
					.getRolId()));
		}

		return funcionarioExternoDTO;
	}

}

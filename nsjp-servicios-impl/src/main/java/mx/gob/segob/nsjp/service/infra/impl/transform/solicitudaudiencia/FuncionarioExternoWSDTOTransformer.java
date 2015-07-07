/**
 * Nombre del Programa	: FuncionarioExternoWSDTOTransformer.java
 * Autor				: AlejandroGA
 * Compania             : Ultrasist
 * Proyecto             : NSJP                    Fecha: 12 Marzo 2013
 * Marca de cambio      : N/A
 * Descripcion General  : Transformer FuncionarioExternoWSDTO para el paquete de solicitudaudiencia
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
package mx.gob.segob.nsjp.service.infra.impl.transform.solicitudaudiencia;

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

	public static mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.FuncionarioExternoWSDTO transformarWSDTO(
			FuncionarioDTO funcionarioDTO) {
		
		if (funcionarioDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.FuncionarioExternoWSDTO funcionarioExternoWSDTO = new mx.gob.segob.nsjp.ws.cliente.solicitudaudiencia.FuncionarioExternoWSDTO();

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
			funcionarioExternoWSDTO.setConfInstitucionId(funcionarioDTO
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
			FuncionarioExternoWSDTO funcionarioExternoWSDTO) {

		if (funcionarioExternoWSDTO == null) {
			return null;
		}

		FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();

		funcionarioExternoDTO.setNombre(funcionarioExternoWSDTO.getNombre());
		funcionarioExternoDTO.setApellidoPaterno(funcionarioExternoWSDTO
				.getApellidoPaterno());
		funcionarioExternoDTO.setApellidoMaterno(funcionarioExternoWSDTO
				.getApellidoMaterno());
		funcionarioExternoDTO.setCveFuncionarioInstExt(funcionarioExternoWSDTO
				.getCveFuncionarioInstExt());
		funcionarioExternoDTO.setEmail(funcionarioExternoWSDTO.getEmail());

		if (funcionarioExternoWSDTO.getConfInstId() != null
				&& funcionarioExternoWSDTO.getConfInstId() >= 0L) {
			funcionarioExternoDTO.setConfInstitucionDTO(new ConfInstitucionDTO(
					funcionarioExternoWSDTO.getConfInstId()));
		}

		funcionarioExternoDTO.setArea(funcionarioExternoWSDTO.getArea());
		funcionarioExternoDTO.setPuesto(funcionarioExternoWSDTO.getPuesto());
		if (funcionarioExternoWSDTO.getRolId() != null
				&& funcionarioExternoWSDTO.getRolId() > 0L) {
			funcionarioExternoDTO.setRolDTO(new RolDTO(funcionarioExternoWSDTO
					.getRolId()));
		}

		return funcionarioExternoDTO;
	}

	/**
	 * Transformar un FuncionarioExterno de DTO a WSDTO
	 * 
	 * @param funcionarioExternoDTO
	 * @return
	 */
	public static FuncionarioExternoWSDTO transformar(
			FuncionarioExternoDTO funcionarioExternoDTO) {

		if (funcionarioExternoDTO == null) {
			return null;
		}

		FuncionarioExternoWSDTO funcionarioExternoWSDTO = new FuncionarioExternoWSDTO();

		funcionarioExternoWSDTO.setNombre(funcionarioExternoDTO.getNombre());
		funcionarioExternoWSDTO.setApellidoPaterno(funcionarioExternoDTO
				.getApellidoPaterno());
		funcionarioExternoWSDTO.setApellidoMaterno(funcionarioExternoDTO
				.getApellidoMaterno());
		funcionarioExternoWSDTO.setCveFuncionarioInstExt(funcionarioExternoDTO
				.getCveFuncionarioInstExt());
		funcionarioExternoWSDTO.setEmail(funcionarioExternoDTO.getEmail());

		if (funcionarioExternoDTO.getConfInstitucionDTO() != null
				&& funcionarioExternoDTO.getConfInstitucionDTO()
						.getConfInstitucionId() != null
				&& funcionarioExternoDTO.getConfInstitucionDTO()
						.getConfInstitucionId() >= 0L) {
			funcionarioExternoWSDTO.setConfInstId(funcionarioExternoDTO
					.getConfInstitucionDTO().getConfInstitucionId());
		}

		if (funcionarioExternoDTO.getArea() != null) {
			funcionarioExternoWSDTO.setArea(funcionarioExternoDTO.getArea());
		}

		if (funcionarioExternoDTO.getPuesto() != null) {
			funcionarioExternoWSDTO
					.setPuesto(funcionarioExternoDTO.getPuesto());
		}

		if (funcionarioExternoDTO.getRolDTO() != null
				&& funcionarioExternoDTO.getRolDTO().getRolId() != null
				& funcionarioExternoDTO.getRolDTO().getRolId() > 0L) {
			funcionarioExternoWSDTO.setRolId(funcionarioExternoDTO.getRolDTO()
					.getRolId());
		}

		if (funcionarioExternoWSDTO.getRolId() == null
				|| funcionarioExternoWSDTO.getRolId() <= 0L) {
			if (funcionarioExternoDTO.getUsuario() != null
					&& funcionarioExternoDTO.getUsuario().getRolACtivo() != null
					&& funcionarioExternoDTO.getUsuario().getRolACtivo()
							.getRol() != null
					&& funcionarioExternoDTO.getUsuario().getRolACtivo()
							.getRol().getRolId() > 0L) {
				funcionarioExternoWSDTO.setRolId(funcionarioExternoDTO
						.getUsuario().getRolACtivo().getRol().getRolId());
			}
		}

		return funcionarioExternoWSDTO;
	}
	
	
	/**
	 * Transformar un FuncionarioExterno de WSDTO a DTO
	 * 
	 * @param funcionarioExternoWSDTO
	 * @return
	 */
	public static FuncionarioExternoDTO transformar(
			mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta.FuncionarioExternoWSDTO funcionarioExternoWSDTO) {

		if (funcionarioExternoWSDTO == null) {
			return null;
		}

		FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();

		funcionarioExternoDTO.setNombre(funcionarioExternoWSDTO.getNombre());
		funcionarioExternoDTO.setApellidoPaterno(funcionarioExternoWSDTO
				.getApellidoPaterno());
		funcionarioExternoDTO.setApellidoMaterno(funcionarioExternoWSDTO
				.getApellidoMaterno());
		funcionarioExternoDTO.setCveFuncionarioInstExt(funcionarioExternoWSDTO
				.getCveFuncionarioInstExt());
		funcionarioExternoDTO.setEmail(funcionarioExternoWSDTO.getEmail());

		if (funcionarioExternoWSDTO.getConfInstId() != null
				&& funcionarioExternoWSDTO.getConfInstId() >= 0L) {
			funcionarioExternoDTO.setConfInstitucionDTO(new ConfInstitucionDTO(
					funcionarioExternoWSDTO.getConfInstId()));
		}

		if (funcionarioExternoWSDTO.getArea() != null) {
			funcionarioExternoDTO.setArea(funcionarioExternoWSDTO.getArea());
		}

		if (funcionarioExternoWSDTO.getPuesto() != null) {
			funcionarioExternoDTO
					.setPuesto(funcionarioExternoWSDTO.getPuesto());
		}

		if (funcionarioExternoWSDTO.getRolId() != null
				&& funcionarioExternoWSDTO.getRolId() > 0L) {
			funcionarioExternoDTO.setRolDTO(new RolDTO(funcionarioExternoWSDTO
					.getRolId()));
		}
		return funcionarioExternoDTO;
	}
	
	/**
	 * Transformar un FuncionarioExterno de DTO a WSDTO
	 * 
	 * @param funcionarioExternoDTO
	 * @return
	 */
	public static mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta.FuncionarioExternoWSDTO transformarWSDTO(
			FuncionarioExternoDTO funcionarioExternoDTO) {

		if (funcionarioExternoDTO == null) {
			return null;
		}

		mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta.FuncionarioExternoWSDTO funcionarioExternoWSDTO = new mx.gob.segob.nsjp.ws.cliente.solicitudcarpeta.FuncionarioExternoWSDTO();

		funcionarioExternoWSDTO
				.setNombre(funcionarioExternoDTO.getNombre());
		funcionarioExternoWSDTO.setApellidoPaterno(funcionarioExternoDTO
				.getApellidoPaterno());
		funcionarioExternoWSDTO.setApellidoMaterno(funcionarioExternoDTO
				.getApellidoMaterno());
		funcionarioExternoWSDTO
				.setCveFuncionarioInstExt(funcionarioExternoDTO
						.getCveFuncionarioInstExt());
		funcionarioExternoWSDTO.setEmail(funcionarioExternoDTO.getEmail());

		if (funcionarioExternoDTO.getConfInstitucionDTO() != null
				&& funcionarioExternoDTO.getConfInstitucionDTO()
						.getConfInstitucionId() != null
				&& funcionarioExternoDTO.getConfInstitucionDTO()
						.getConfInstitucionId() >= 0L) {
			funcionarioExternoWSDTO.setConfInstId(funcionarioExternoDTO
					.getConfInstitucionDTO().getConfInstitucionId());
		}

		if (funcionarioExternoDTO.getArea() != null) {
			funcionarioExternoWSDTO
					.setArea(funcionarioExternoDTO.getArea());
		}

		if (funcionarioExternoDTO.getPuesto() != null) {
			funcionarioExternoWSDTO.setPuesto(funcionarioExternoDTO
					.getPuesto());
		}

		if (funcionarioExternoDTO.getRolDTO() != null
				&& funcionarioExternoDTO.getRolDTO().getRolId() != null
				& funcionarioExternoDTO.getRolDTO().getRolId() > 0L) {
			funcionarioExternoWSDTO.setRolId(funcionarioExternoDTO
					.getRolDTO().getRolId());
		}

		if (funcionarioExternoWSDTO.getRolId() == null
				|| funcionarioExternoWSDTO.getRolId() <= 0L) {
			if (funcionarioExternoDTO.getUsuario() != null
					&& funcionarioExternoDTO.getUsuario().getRolACtivo() != null
					&& funcionarioExternoDTO.getUsuario().getRolACtivo()
							.getRol() != null
					&& funcionarioExternoDTO.getUsuario().getRolACtivo()
							.getRol().getRolId() > 0L) {
				funcionarioExternoWSDTO.setRolId(funcionarioExternoDTO
						.getUsuario().getRolACtivo().getRol().getRolId());
			}
		}
		return funcionarioExternoWSDTO;
	}
}

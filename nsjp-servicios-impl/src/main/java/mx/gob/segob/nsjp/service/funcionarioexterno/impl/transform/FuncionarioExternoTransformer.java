/**
* Nombre del Programa 		: FuncionarioExternoTransformer.java
* Autor 					: EdgarTE
* Compania 					: Ultrasist
* Proyecto 					: NSJP 							Fecha: 13 Apr 2012
* Marca de cambio 			: N/A
* Descripcion General 		: Describir el objetivo de la clase de manera breve
* Programa Dependiente 		: N/A
* Programa Subsecuente 		: N/A
* Cond. de ejecucion 		: N/A
* Dias de ejecucion 		: N/A 							Horario: N/A
*                              MODIFICACIONES
*------------------------------------------------------------------------------
* Autor 					: N/A
* Compania 					: N/A
* Proyecto 					: N/A 							Fecha: N/A
* Modificacion 				: N/A
*------------------------------------------------------------------------------
*/
package mx.gob.segob.nsjp.service.funcionarioexterno.impl.transform;

import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.FuncionarioExterno;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.RolTransformer;

/**
 * Transformer que lleva a cabo las conversiones de objetos entre entities y DTO's 
 * para los funcionarios Externos.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class FuncionarioExternoTransformer {

	/**
	 * M&eacute;todo que lleva a cabo la conversi&oacute;n de un objeto de tipo entidad 
	 * a un objeto de transferencia de datos para el funcionario externo.
	 * @param funcionarioExterno - El entity a transformar.
	 * @return funcionario - El funcionarioExternoDTO transformado.
	 */
	public static FuncionarioExternoDTO transformar (FuncionarioExterno funcionarioExterno){
		FuncionarioExternoDTO funcionario = null;
		if (funcionarioExterno != null){
			funcionario = new FuncionarioExternoDTO();
			funcionario.setApellidoMaterno(funcionarioExterno.getApellidoMaterno());
			funcionario.setApellidoPaterno(funcionarioExterno.getApellidoPaterno());
			funcionario.setArea(funcionarioExterno.getArea());
			funcionario.setConfInstitucionDTO(ConfInstitucionTransformer.transformarInstitucion(funcionarioExterno.getConfInstitucion()));
			funcionario.setCveFuncionarioInstExt(funcionarioExterno.getCveFuncionarioInstExt());
			funcionario.setEmail(funcionarioExterno.getEmail());
			funcionario.setFuncionarioExternoId(funcionarioExterno.getFuncionarioExternoId());
			funcionario.setNombre(funcionarioExterno.getNombre());
			funcionario.setPuesto(funcionarioExterno.getPuesto());
			funcionario.setRolDTO(RolTransformer.transformar(funcionarioExterno.getRol()));
		}
		return funcionario;
	}
	
	/**
	 * M&eacute; que lleva a cabo la transformaci&oacute;n de un objeto de transferencia de
	 * datos a un objeto de tipo entidad.
	 * @param funcionarioExternoDTO - El DTO a transformar
	 * @return funcionario - La entidad FuncionarioExterno transformada.
	 */
	public static FuncionarioExterno transformar (FuncionarioExternoDTO funcionarioExternoDTO){
		FuncionarioExterno funcionario = null;
		if (funcionarioExternoDTO != null){
			funcionario = new FuncionarioExterno();
			funcionario.setApellidoMaterno(funcionarioExternoDTO.getApellidoMaterno());
			funcionario.setApellidoPaterno(funcionarioExternoDTO.getApellidoPaterno());
			funcionario.setArea(funcionarioExternoDTO.getArea());
			
			if (funcionarioExternoDTO.getConfInstitucionDTO() != null && funcionarioExternoDTO.getConfInstitucionDTO().getConfInstitucionId() != null){
				ConfInstitucion confInstitucion = new ConfInstitucion();
				confInstitucion.setConfInstitucionId(funcionarioExternoDTO.getConfInstitucionDTO().getConfInstitucionId());
				funcionario.setConfInstitucion(confInstitucion);
			}
			
			funcionario.setCveFuncionarioInstExt(funcionarioExternoDTO.getCveFuncionarioInstExt());
			funcionario.setEmail(funcionarioExternoDTO.getEmail());
			funcionario.setFuncionarioExternoId(funcionarioExternoDTO.getFuncionarioExternoId());
			funcionario.setNombre(funcionarioExternoDTO.getNombre());
			funcionario.setPuesto(funcionarioExternoDTO.getPuesto());
			funcionario.setRol(RolTransformer.transformar(funcionarioExternoDTO.getRolDTO()));
		}
		return funcionario;
	}
	
	public static FuncionarioExterno transformarUpdate(FuncionarioExterno funOri, FuncionarioExternoDTO funDes){
		
		funOri.setApellidoMaterno(funDes.getApellidoMaterno());
		funOri.setApellidoPaterno(funDes.getApellidoPaterno());
		funOri.setArea(funDes.getArea());
		funOri.setConfInstitucion(ConfInstitucionTransformer.transformarInstitucion(funDes.getConfInstitucionDTO()));
		funOri.setCveFuncionarioInstExt(funDes.getCveFuncionarioInstExt());
		funOri.setEmail(funDes.getEmail());;		
		funOri.setNombre(funDes.getNombre());
		funOri.setPuesto(funDes.getPuesto());
		funOri.setRol(RolTransformer.transformar(funDes.getRolDTO()));
		
		return funOri;
	}
	
	public static FuncionarioExterno updateFuncionarioExterno(FuncionarioExterno funOri, FuncionarioExterno funDes){

		funOri.setApellidoMaterno(funDes.getApellidoMaterno());
		funOri.setApellidoPaterno(funDes.getApellidoPaterno());
		funOri.setArea(funDes.getArea());
		ConfInstitucion confInstitucion = new ConfInstitucion();
		confInstitucion.setConfInstitucionId(funDes.getConfInstitucion()
				.getConfInstitucionId());
		funOri.setConfInstitucion(confInstitucion);
		funOri.setCveFuncionarioInstExt(funDes.getCveFuncionarioInstExt());
		funOri.setEmail(funDes.getEmail());;		
		funOri.setNombre(funDes.getNombre());
		funOri.setPuesto(funDes.getPuesto());
		funOri.setRol(funDes.getRol());

		return funOri;
	}
	
	
	/**
	 * M&eacute;todo que lleva a cabo la transformaci&oacute;n de un objeto de tipo FuncionarioDTO
	 * a otro objeto de tipo FuncionarioExternoDTO para que dicho objeto se utilice como par&aacute;metro
	 * para enviar la informaci&oacute;n del funcionario via web service.
	 * @param funcionarioDTO - Objeto de tipo FuncionarioDTO a transformar.
	 * @return funcionarioExterno - Objeto de tipo FuncionarioExternoDTO una vez que se ha llevado a cabo
	 * 								la transformaci&oacute;n.
	 */
	public static FuncionarioExternoDTO transformarFuncionarioLocal(
			FuncionarioDTO funcionarioDTO) {
		FuncionarioExternoDTO funcionarioExterno = null;

		if (funcionarioDTO == null) {
			return funcionarioExterno;
		}

		funcionarioExterno = new FuncionarioExternoDTO();
		funcionarioExterno.setNombre(funcionarioDTO.getNombreFuncionario());
		funcionarioExterno.setApellidoPaterno(funcionarioDTO
				.getApellidoPaternoFuncionario());
		funcionarioExterno.setApellidoMaterno(funcionarioDTO
				.getApellidoMaternoFuncionario());
		funcionarioExterno.setCveFuncionarioInstExt(funcionarioDTO
				.getClaveFuncionario());
		funcionarioExterno.setEmail(funcionarioDTO.getEmail());

		if (funcionarioDTO.getInstitucion() != null
				&& funcionarioDTO.getInstitucion().getConfInstitucionId() != null
				&& funcionarioDTO.getInstitucion().getConfInstitucionId() > 0L) {
			ConfInstitucionDTO conf = new ConfInstitucionDTO();
			conf.setConfInstitucionId(funcionarioDTO.getInstitucion()
					.getConfInstitucionId());
			funcionarioExterno.setConfInstitucionDTO(conf);
		}
		if (funcionarioDTO.getCatAreaNegocio() != null
				&& funcionarioDTO.getCatAreaNegocio()
						.getNombreCatAreaNegocio() != null) {
			funcionarioExterno.setArea(funcionarioDTO.getCatAreaNegocio()
					.getNombreCatAreaNegocio());
		}
		if (funcionarioDTO.getPuesto() != null
				&& funcionarioDTO.getPuesto().getValor() != null) {
			funcionarioExterno.setPuesto(funcionarioDTO.getPuesto()
					.getValor());
		}

		return funcionarioExterno;
	}
}

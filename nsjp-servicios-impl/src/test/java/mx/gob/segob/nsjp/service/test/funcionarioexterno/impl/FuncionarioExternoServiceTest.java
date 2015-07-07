/**
* Nombre del Programa 		: FuncionarioExternoServiceTest.java
* Autor        				: EdgarTE
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
package mx.gob.segob.nsjp.service.test.funcionarioexterno.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.audiencia.AudienciaDTO;
import mx.gob.segob.nsjp.dto.configuracion.ConfInstitucionDTO;
import mx.gob.segob.nsjp.dto.funcionarioexterno.FuncionarioExternoDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.service.funcionarioexterno.FuncionarioExternoService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;


/**
 * Describir el objetivo de la clase con punto al final.
 * @version 1.0
 * @author EdgarTE
 *
 */
public class FuncionarioExternoServiceTest extends BaseTestServicios<FuncionarioExternoService> {
	 
	
	public void testCrearFuncionarioExterno(){
		FuncionarioExternoDTO funcionarioExt = new FuncionarioExternoDTO();
		funcionarioExt.setNombre("Juan");
		funcionarioExt.setApellidoPaterno("Perez");
		ConfInstitucionDTO inst = new ConfInstitucionDTO();
		inst.setConfInstitucionId(5L);
		funcionarioExt.setConfInstitucionDTO(inst);
		funcionarioExt.setCveFuncionarioInstExt(1L);
		funcionarioExt = service.crearFuncionarioExterno(funcionarioExt);
		logger.info("Funcionario externo creado exitosamente con el id: " + funcionarioExt.getFuncionarioExternoId());
	}
	
	public void testActualizarFuncionarioExterno(){
		FuncionarioExternoDTO funcionarioExt = new FuncionarioExternoDTO();
		funcionarioExt.setNombre("Pedro");
		funcionarioExt.setApellidoPaterno("Sanchez");
		funcionarioExt.setApellidoMaterno("Lopez");
		ConfInstitucionDTO inst = new ConfInstitucionDTO();
		inst.setConfInstitucionId(1L);
		funcionarioExt.setConfInstitucionDTO(inst);
		funcionarioExt.setCveFuncionarioInstExt(1L);
		funcionarioExt.setFuncionarioExternoId(1L);
		funcionarioExt.setPuesto("Programador");
		funcionarioExt.setArea("Desarrollo");
		funcionarioExt.setEmail("psanchez@nsjp.gob.mx");
		service.actualizarFuncionarioExterno(funcionarioExt);
		logger.info("Funcionario externo actualizado exitosamente.");
	}
	
	public void testConsultarFuncionarioExterno(){
		FuncionarioExternoDTO funcionarioExt = new FuncionarioExternoDTO();
		funcionarioExt.setFuncionarioExternoId(5L);
		funcionarioExt = service.consultarFuncionarioExternoPorId(funcionarioExt);
		if (funcionarioExt != null){
			logger.info("Id: "+ funcionarioExt.getFuncionarioExternoId());
			logger.info("Nombre: "+ funcionarioExt.getNombre());
			logger.info("Apellido Paterno: "+ funcionarioExt.getApellidoPaterno());
			logger.info("Apellido Materno: "+ funcionarioExt.getApellidoMaterno());
			logger.info("Area: "+ funcionarioExt.getArea());
			logger.info("Puesto: "+ funcionarioExt.getPuesto());
			logger.info("E-mail: "+ funcionarioExt.getEmail());
			logger.info("Institucion: "+ funcionarioExt.getConfInstitucionDTO().getNombreInst());
			logger.info("Clave funcionario institucion externa: "+funcionarioExt.getCveFuncionarioInstExt());			
		}else{
			logger.info("No se encontraron resultados de la busqueda");
		}
	}
	
	public void testEliminarFuncionarioExterno(){
		FuncionarioExternoDTO funcionarioExt = new FuncionarioExternoDTO();
		funcionarioExt.setFuncionarioExternoId(1L);
		funcionarioExt = service.consultarFuncionarioExternoPorId(funcionarioExt);
		service.eliminarFuncionarioExterno(funcionarioExt);
		logger.info("El Funcionario externo se elimino correctamente");
	}
	
	public void testGuardarActualizarFuncionarioExternoPorIClaveFuncionarioInsExt(){
		
		FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();
		
		funcionarioExternoDTO.setCveFuncionarioInstExt(new Long("45"));
		funcionarioExternoDTO.setNombre("Raul");
		funcionarioExternoDTO.setApellidoPaterno("Fernandez");
		funcionarioExternoDTO.setApellidoMaterno("Yunes");
		funcionarioExternoDTO.setEmail("raulPru@yahoo.com");
		funcionarioExternoDTO.setPuesto(Puestos.COORDINADOR_DE_DEFENSORES.toString());
		funcionarioExternoDTO.setConfInstitucionDTO(new ConfInstitucionDTO(
				Instituciones.DEF.getValorId()));
		funcionarioExternoDTO.setArea(Areas.COORDINACION_DEFENSORIA.toString());
		funcionarioExternoDTO.setFuncionarioExternoId(22L);
		
		try {
			funcionarioExternoDTO = service.guardarActualizarFuncionarioExternoPorIClaveFuncionarioInsExt(funcionarioExternoDTO);
			
			if (funcionarioExternoDTO != null){
				logger.info("Id: "+ funcionarioExternoDTO.getFuncionarioExternoId());
				logger.info("Nombre: "+ funcionarioExternoDTO.getNombre());
				logger.info("Apellido Paterno: "+ funcionarioExternoDTO.getApellidoPaterno());
				logger.info("Apellido Materno: "+ funcionarioExternoDTO.getApellidoMaterno());
				logger.info("Area: "+ funcionarioExternoDTO.getArea());
				logger.info("Puesto: "+ funcionarioExternoDTO.getPuesto());
				logger.info("E-mail: "+ funcionarioExternoDTO.getEmail());
				logger.info("Institucion id: "+ funcionarioExternoDTO.getConfInstitucionDTO().getConfInstitucionId());
				logger.info("Clave funcionario institucion externa: "+funcionarioExternoDTO.getCveFuncionarioInstExt());			
			}else{
				logger.info("NO SE HA GUARDADO NADA");
			}
		} catch (NSJPNegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testConsultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(){
		
		FuncionarioExternoDTO funcionarioExternoDTO = new FuncionarioExternoDTO();
		
		funcionarioExternoDTO.setRolDTO(new RolDTO(14L));
		
		AudienciaDTO audienciaDTO = new AudienciaDTO(793L);
		
		try {
			List<FuncionarioExternoDTO> funcionarioExternoDTOList = service.consultarFuncionarioExternoPorFiltroNoAsociadoALaAudiencia(funcionarioExternoDTO, audienciaDTO);
			
			if (funcionarioExternoDTOList != null && !funcionarioExternoDTOList.isEmpty()) {
				logger.info("TAMANIO DE LA LISTA::::::::::::::::::::"
						+ funcionarioExternoDTOList.size());
				for (FuncionarioExternoDTO funcExtDTO : funcionarioExternoDTOList) {
					logger.info("__________________________________________");
					logger.info("funcExt.getFuncionarioExternoId():::"
							+ funcExtDTO.getFuncionarioExternoId());
					logger.info("funcExt.getCveFuncionarioInstExt()::"
							+ funcExtDTO.getCveFuncionarioInstExt());
					if (funcExtDTO.getConfInstitucionDTO() != null) {
						logger.info("funcExt.getConfInstitucion()::::"
								+ funcExtDTO.getConfInstitucionDTO()
										.getConfInstitucionId());
					}
					logger.info("funcExt.getNombre()::::::::::::::::"
							+ funcExtDTO.getNombre());
					logger.info("funcExt.getApellidoPaterno():::::::"
							+ funcExtDTO.getApellidoPaterno());
					logger.info("funcExt.getApellidoMaterno():::::::"
							+ funcExtDTO.getApellidoMaterno());
					if (funcExtDTO.getRolDTO() != null) {
						logger.info("funcExt.getRol().getRolId()::::"
								+ funcExtDTO.getRolDTO().getRolId());
					}
					logger.info("funcExt.getArea()::::::::::::::::::"
							+ funcExtDTO.getArea());
					logger.info("funcExt.getPuesto()::::::::::::::::"
							+ funcExtDTO.getPuesto());
					logger.info("funcExt.getEmail():::::::::::::::::"
							+ funcExtDTO.getEmail());
				}
			} else {
				logger.info("NO HAY DATOS");
			}
		} catch (NSJPNegocioException e) {
			logger.error("Excepcion"+e);
			e.printStackTrace();
		}
	}
}


/**
 * Nombre del Programa : IngresarPeritoServiceImplTest.java
 * Autor                            : rgama
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    
 * Marca de cambio        : N/A
 * Descripcion General    : Prueba unitaria para el servicio de Registrar Perito
 * Programa Dependiente  :N/A
 * Programa Subsecuente :N/A
 * Cond. de ejecucion        :N/A
 * Dias de ejecucion          :N/A                             Horario: N/A
 *                              MODIFICACIONES
 *------------------------------------------------------------------------------
 * Autor                       :N/A
 * Compania               :N/A
 * Proyecto                 :N/A                                 Fecha: N/A
 * Modificacion           :N/A
 *------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.service.test.funcionario.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.funcionario.Puestos;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.involucrado.InvolucradoViewDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.UsuarioRol;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionariosPorRolService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria para consultar una lista de funcionarios por su puesto
	 * Este metodo sera usado para obtener la informacion de:
	 * -Abogado defensor.
	 * -Coordinador de defensores.
	 * -Coordinador de fiscales.
	 * -Coordinador de servicios periciales.
	 * -Fiscal general.
	 * -Fiscal.
	 * -Juez.
	 * -Magistrado.
 * @version 1.0
 * @author rgama
 */
public class ConsultarFuncionariosPorRolServiceImplTest extends
		BaseTestServicios<ConsultarFuncionariosPorRolService> {
	/**
	 * Metodo que permite consultar por filtro
	 */
	public void testConsultarCoordinadorServiciosPericial() {
		List<FuncionarioDTO> loFuncionariosDTO = null;
		try {
			loFuncionariosDTO = service.consultarFuncionariosPorRol(Puestos.COORDINADOR_DE_SERVICIOS_PERICIALES.getValorId());			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
		
		assertFalse("Se regresa una lista vacia de funcionarios", loFuncionariosDTO.isEmpty());
		
		for (FuncionarioDTO funcionarioDTO : loFuncionariosDTO) {
			logger.info("Nombre funcionario: " + funcionarioDTO.getNombreFuncionario() + " " 
					+ funcionarioDTO.getApellidoPaternoFuncionario()+ " "
					+ funcionarioDTO.getApellidoMaternoFuncionario());
			logger.info("Puesto del funcionario: " + funcionarioDTO.getPuesto().getValor());	
		}
	}	

	public void testConsultarCoordinadorDefensores() {
		List<FuncionarioDTO> loFuncionariosDTO = null;
		try {
			loFuncionariosDTO = service.consultarFuncionariosPorRol(Puestos.COORDINADOR_DE_DEFENSORES.getValorId());
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

		assertFalse("Se regresa una lista vacia de funcionarios",
				loFuncionariosDTO.isEmpty());

		for (FuncionarioDTO funcionarioDTO : loFuncionariosDTO) {
			logger.info("Nombre funcionario: "
					+ funcionarioDTO.getNombreFuncionario() + " "
					+ funcionarioDTO.getApellidoPaternoFuncionario() + " "
					+ funcionarioDTO.getApellidoMaternoFuncionario());
			logger.info("Puesto del funcionario: "
					+ funcionarioDTO.getPuesto().getValor());
		}
	}
	
	public void testConsultarCoordinadorFiscales() {
		List<FuncionarioDTO> loFuncionariosDTO = null;
		try {
			loFuncionariosDTO = service.consultarFuncionariosPorRol(Puestos.COORDINADOR_DE_FISCALES.getValorId());
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

		assertFalse("Se regresa una lista vacia de funcionarios",
				loFuncionariosDTO.isEmpty());

		for (FuncionarioDTO funcionarioDTO : loFuncionariosDTO) {
			logger.info("Nombre funcionario: "
					+ funcionarioDTO.getNombreFuncionario() + " "
					+ funcionarioDTO.getApellidoPaternoFuncionario() + " "
					+ funcionarioDTO.getApellidoMaternoFuncionario());
			logger.info("Puesto del funcionario: "
					+ funcionarioDTO.getPuesto().getValor());
		}
	}
	
	public void testConsultarFuncionariosSubordinados () {
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();		
		funcionarioDTO.setClaveFuncionario(254L);
		funcionarioDTO.setDiscriminante(new CatDiscriminanteDTO(1L));
		
		UsuarioDTO usuario = new UsuarioDTO();
		UsuarioRolDTO usuarioRol = new UsuarioRolDTO();
		Set<UsuarioRolDTO> usuarioRoles = new HashSet<UsuarioRolDTO>();	
		FuncionarioDTO funcionario = new FuncionarioDTO();
		funcionario.setDiscriminante(new CatDiscriminanteDTO(1L));
		
		RolDTO rol = new RolDTO(Roles.COORDINADORAMP.getValorId());
		rol.setNombreRol(Roles.COORDINADORAMP.toString());
		
		usuarioRol.setRol(rol);
		usuarioRoles.add(usuarioRol);
		
		usuario.setUsuarioRoles(usuarioRoles);
		usuario.setRolActivo(rol.getNombreRol());
		usuario.setFuncionario(funcionario);
		
		try {
			List<FuncionarioDTO> respuesta = service.consultarFuncionariosSubordinados(usuario);
			assertTrue("La lista debe tener minimo un Funcionario :: ",respuesta.size()>0);
			for (FuncionarioDTO funcionarioDTO2 : respuesta) {
				logger.info("-------------------------");
				logger.info("Clave Funcionario "+funcionarioDTO2.getClaveFuncionario());
				logger.info("Nombre Funcionario "+funcionarioDTO2.getNombreCompleto());
				logger.info("-------------------------");
			}
			logger.info("Total " + respuesta.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testObtenerDefensorAsignadoAExpediente() {
		
		try {
			FuncionarioDTO respuesta = service.obtenerDefensorAsignadoAExpediente( new ExpedienteDTO(33L));
			assertNotNull("El funcionario no puede ser nulo : ", respuesta);
			logger.info("El funcionario no puede ser nulo : "+ respuesta);			
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void testObtenerFuncionarioSuperior () {
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setClaveFuncionario(39L);
		
		try {
			FuncionarioDTO respuesta = service.obtenerFuncionarioSuperior(funcionarioDTO);
			assertNotNull("No puede ser nulo el funcionario superior", respuesta);
			logger.info("Funcionario superior :: "+respuesta);
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void testObtenerFuncionarioPorNombreCompleto() {
		
		FuncionarioDTO respuesta = service.obtenerFuncionarioPorNombreCompleto("Alberto Pastrana Huicochea");
		
		assertNotNull(respuesta);
		logger.info("---------------------");
		logger.info("Funcionario ID :: "+respuesta.getClaveFuncionario());
		logger.info("Funcionario Nombre :: "+respuesta.getNombreCompleto());
		logger.info("---------------------");
	}

	public void testConsultarFuncionariosPorAreaYPuesto(){
		Long area=9L;
		Long puesto=Puestos.FISCAL.getValorId();
		try {
			List<FuncionarioDTO> funcionarios = service.consultarFuncionariosPorAreayPuesto(area, puesto);
			assertNotNull(funcionarios);
			logger.info("Existen "+funcionarios.size()+" funcionarios");
			for (FuncionarioDTO dto : funcionarios) {
				logger.info("---------------------");
				logger.info("Clave: "+dto.getClaveFuncionario());
				logger.info("NombreCompleto: "+dto.getNombreCompleto());
				logger.info("Depto: "+dto.getDepartamento().getNombreDepto());
				logger.info("Area: "+dto.getDepartamento().getArea().getNombre());
				logger.info("DiscriminanteID: "+dto.getDiscriminante().getClave());
			}
			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testConsultarFuncionariosPorRolMultiRol(){
		try {
			List<FuncionarioDTO> funcionarios = service.consultarFuncionariosPorRolMultiRol(Roles.COORDINADOR_CONSIGNACION.getValorId());
			assertNotNull(funcionarios);
			logger.info("Existen "+funcionarios.size()+" funcionarios");
			for (FuncionarioDTO dto : funcionarios) {
				logger.info("---------------------");
				logger.info("Clave: "+dto.getClaveFuncionario());
				logger.info("NombreCompleto: "+dto.getNombreCompleto());
				logger.info("Area: "+dto.getDepartamento().getArea().getNombre());
				logger.info("DiscriminanteID: "+dto.getDiscriminante().getClave());
			}
			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	public void testConsultarFuncionariosPorRolyPorDistrito(){
		try {
			Long idDistrito = 1L;
			List<FuncionarioDTO> funcionarios = service.consultarFuncionariosPorRolyPorDistrito(Roles.COORDPERFIS.getValorId(), idDistrito);
			assertNotNull(funcionarios);
			logger.info("Existen "+funcionarios.size()+" funcionarios");
			for (FuncionarioDTO dto : funcionarios) {
				logger.info("---------------------");
				logger.info("Clave: "+dto.getClaveFuncionario());
				logger.info("NombreCompleto: "+dto.getNombreCompleto());
				logger.info("Area: "+dto.getDepartamento().getArea().getNombre());
				logger.info("DiscriminanteID: "+dto.getDiscriminante());
				logger.info("DiscriminanteID: "+dto.getDiscriminante().getDistrito().getCatDistritoId());
				
			}
			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testConsultarFuncionariosPorDicriminanteYRol(){
		try {
			Long catDiscriminanteId = 1L;
			Long idRol = Roles.COORDINADORAMP.getValorId();
			Long idUIE=1L;
			List<FuncionarioDTO> funcionarios = service.consultarFuncionariosPorDicriminanteYRol(catDiscriminanteId, idRol,idUIE);
			assertNotNull(funcionarios);
			
			logger.info("Existen "+funcionarios.size()+" funcionarios");
			for (FuncionarioDTO dto : funcionarios) {
				logger.info("---------------------");
				logger.info("Clave: "+dto.getClaveFuncionario());
				logger.info("NombreCompleto: "+dto.getNombreCompleto());
				logger.info("Depto: "+dto.getDepartamento().getNombreDepto());
				logger.info("Area: "+dto.getDepartamento().getArea().getNombre());
				logger.info("DiscriminanteID: "+dto.getDiscriminante().getClave());
				logger.info("UIE: "+dto.getUnidadIEspecializada().getNombreUIE());
			}
			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testValidarFuncionariosRol(){
		try {
			
			Long idRol = Roles.JUEZPJ.getValorId();
			List<FuncionarioDTO> funcionariosAntes = new ArrayList<FuncionarioDTO>();
			funcionariosAntes.add(new FuncionarioDTO(120L));
			funcionariosAntes.add(new FuncionarioDTO(24L));
			funcionariosAntes.add(new FuncionarioDTO(1L));
			
			List<InvolucradoViewDTO> funcionarios = service.validarFuncionariosRol(funcionariosAntes,idRol);
			
			
			logger.info("Existen "+funcionarios.size()+" funcionarios");
			for (InvolucradoViewDTO dto : funcionarios) {
				logger.info("---------------------");
				logger.info("Clave: "+dto.getInvolucradoId());
				logger.info("NombreCompleto: "+dto.getNombreCompleto());
				logger.info("NombreInst: "+dto.getNombreInstitucion());
				logger.info("esFuncionario: "+dto.isFuncionario());
				
			}
			
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}

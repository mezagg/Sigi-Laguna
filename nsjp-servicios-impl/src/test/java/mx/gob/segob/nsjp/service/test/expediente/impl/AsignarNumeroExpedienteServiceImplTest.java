/**
* Nombre del Programa : AsignarNumeroExpedienteServiceImplTest.java
* Autor                            : GustavoBP
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 21/10/2011
* Marca de cambio        : N/A
* Descripcion General    : Describir el objetivo de la clase de manera breve
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
package mx.gob.segob.nsjp.service.test.expediente.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.ValorDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Prueba unitaria para los servicios de Asignar numeroExpediente.
 * @version 1.0
 * @author GustavoBP
 *
 */
public class AsignarNumeroExpedienteServiceImplTest extends
	BaseTestServicios<AsignarNumeroExpedienteService> {
	
	

	public void testAsignarNumeroExpedienteCarpetaEjecucion(){
		
		try {
			Long expedienteId = 69L;
			ExpedienteDTO expDTO =service.asignarNumeroExpedienteCarpetaEjecucion(expedienteId);
			assertNotNull("El objeto no puede ser nulo", expDTO);
			logger.info(" expDTO:"+ expDTO.getExpedienteId());
			logger.info(" expDTO:"+ expDTO.getEstatus());
			logger.info(" expDTO:"+ expDTO.getTipoExpediente());
			logger.info(" expDTO:"+ expDTO.getNumeroExpediente());
			logger.info(" expDTO:"+ expDTO.getNumeroExpedienteId());
			
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(),e);
		}
		
		
	}
	
public void testObtenerNumeroExpedientePoderJudicial(){
		try {
			Boolean esCausa = true;
			Long claveFuncionario = 23L;
//			Long catDiscriminanteId = 8L;
//			String claveDiscriminante = "J02";
			
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO(claveFuncionario);
//			CatDiscriminanteDTO  dto = new CatDiscriminanteDTO( catDiscriminanteId, claveDiscriminante);
//			funcionarioDTO.setDiscriminante(dto);
			usuarioDTO.setFuncionario(funcionarioDTO);
			
			String numeroExpediente =service.obtenerNumeroExpedientePoderJudicial(esCausa, usuarioDTO);
			assertNotNull("El objeto no puede ser nulo", numeroExpediente);
			logger.info(" numeroExpediente:"+ numeroExpediente);
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(),e);
		}
	}

	public void testObtenerNumeroExpedienteAlterno(){
		UsuarioDTO usuario = new UsuarioDTO();
		
		CatDistritoDTO distrito = new CatDistritoDTO();
		distrito.setClaveRomanaDistrito("II");
		
		FuncionarioDTO funcionario = new FuncionarioDTO();
		
		CatDiscriminanteDTO discriminante = new CatDiscriminanteDTO();
		discriminante.setDistrito(distrito);
		funcionario.setDiscriminante(discriminante);
		usuario.setFuncionario(funcionario);
		
		//POLICIA MINISTERIAL
		Long areaId = new Long(Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal());

		ValorDTO especialidadDTO = new ValorDTO(4359L); //UNIDAD ESPECIALIZADA DE INVESTIGACIÓN CONTRA ROBOS
		usuario.getFuncionario().setEspecialidad(especialidadDTO);

		//UNIDAD_INVESTIGACION -  00025-UnidadIN/2012-ZAC-II
//		Long areaId = new Long(Areas.UNIDAD_INVESTIGACION.ordinal());
//		
//		CatUIEspecializadaDTO unidadIEspecializada = new CatUIEspecializadaDTO();
//		unidadIEspecializada.setClaveUIE("005"); //UNIDAD ESPECIALIZADA DE INVESTIGACIÓN CONTRA ROBOS
//		unidadIEspecializada.setAcronimo("UnidadIN");
//		usuario.getFuncionario().setUnidadIEspecializada(unidadIEspecializada);

		
		JerarquiaOrganizacionalDTO jerarquiaOrg = new JerarquiaOrganizacionalDTO();
		jerarquiaOrg.setJerarquiaOrganizacionalId(areaId);
		funcionario.setJerarquiaOrganizacional(jerarquiaOrg);
		usuario.setAreaActual(new AreaDTO(areaId));
		

		
		ExpedienteDTO expediente = new ExpedienteDTO();
		
		try {
			String noExpAlterno = service.obtenerNumeroExpedienteAlterno(usuario, expediente,null);
			logger.info("El numero de expediente alterno se genero correctamente: " + noExpAlterno);
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void testConsultarNumeroExpedienteAlterno(){
		
		try {
			Long numeroExpedienteId = 25562L;
			ExpedienteDTO expediente = new ExpedienteDTO();
			expediente.setNumeroExpedienteId(numeroExpedienteId);
			String numExpAlterno =service.consultarNumeroExpedienteAlterno(expediente);
			logger.info("NUMERO EXPEDIENTE ALTERNO:::"+ numExpAlterno);
			
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(),e);
		}
		
		
	}
	
	
	public void testReasignarNumerosDeExpedientePM(){
		
		try {
			FuncionarioDTO loFuncionarioDTO = new FuncionarioDTO(278L);
			UsuarioDTO loUsuarioDTO = new UsuarioDTO();
			loUsuarioDTO.setFuncionario(loFuncionarioDTO);
			
			List<Long> idNumerosExpedientes = new ArrayList<Long>();
			idNumerosExpedientes.add(4464L);
			idNumerosExpedientes.add(4465L);
			
			service.reasignarNumerosDeExpedientePM(idNumerosExpedientes, 299L, loUsuarioDTO,299L);
			
		} catch (NSJPNegocioException e) {
			logger.info(e.getMessage(),e);
		}
	}
	
	/**
	 * Prueba unitaria para el m&eacute;todo privado: obtenerNumeroExpediente 
	 * Se requiere cambiar la visibilidad del metodo a public. 
	 * Y declarar en la interfaz la firma del metodo
	 * 
	 * En esta prueba comentar seccion 2 y descomentar seccion 1
	 */
	public void testObtenerNumeroExpediente() {

		// Valores para defensoria  
		//NOTA: todos los expedientes deben de ser generados en 
		// area de defensoria.
		Long idArea = new Long (Areas.DEFENSORIA.ordinal());
		String anioCreacionDelExpediente = null;

		// Referencia del defensorATE de la BD
		Long claveFuncionario = 15L;
		Long catDiscriminanteId = 3L;
		Long catDistritoId = 1L;
		String claveDistrito = "01";

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		CatDiscriminanteDTO discriminanteDTO = new CatDiscriminanteDTO(
				catDiscriminanteId);
		CatDistritoDTO catDistrito = new CatDistritoDTO(catDistritoId);
		catDistrito.setClaveDistrito(claveDistrito);
		discriminanteDTO.setDistrito(catDistrito);

		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setClaveFuncionario(claveFuncionario);
		funcionarioDTO.setDiscriminante(discriminanteDTO);

		usuarioDTO.setFuncionario(funcionarioDTO);

//		try {
//			String numeroExpediente = null;
//			//Seccion 1 
//			numeroExpediente =service.obtenerNumeroExpediente(idArea,
//					usuarioDTO, anioCreacionDelExpediente);
//			assertFalse(" No se logro geenrar el numero de Expediente: ",
//					numeroExpediente == null
//					|| numeroExpediente.trim().isEmpty());
//			
//			//Seccion 2 
//			logger.debug("Numero de Expediente Generado:" + numeroExpediente);
//			
//		} catch (NSJPNegocioException e) {
//			logger.error(e.getMessage(), e);
//		}
	}
}














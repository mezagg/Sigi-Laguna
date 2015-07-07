/**
 * Nombre del Programa : ConsultarFuncionarioPorFiltroServiceImplTest.java
 * Autor                            : adrian
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 20/07/2011
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
package mx.gob.segob.nsjp.service.test.funcionario.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.seguridad.Roles;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDiscriminanteDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatDistritoDTO;
import mx.gob.segob.nsjp.dto.catalogo.CatUIEspecializadaDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.institucion.DepartamentoDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.service.funcionario.ConsultarFuncionarioPorFiltroService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;

/**
 * Describir el objetivo de la clase con punto al final.
 * 
 * @version 1.0
 * @author adrian
 * 
 */
public class ConsultarFuncionarioPorFiltroServiceImplTest extends
		BaseTestServicios<ConsultarFuncionarioPorFiltroService> {

	/**
	 * Test method for
	 * {@link mx.gob.segob.nsjp.service.funcionario.impl.ConsultarFuncionarioPorFiltroServiceImpl#consultarFuncionarioPorFiltro(mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO)}
	 * .
	 */
	public void testConsultarFuncionarioPorFiltro() {
		FuncionarioDTO filtro = new FuncionarioDTO();
		filtro.setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(Areas.COORDINACION_UEI_GENERAL.parseLong()));
		filtro.setBuscarPorJerarquiasHijas(true);
		try {
			List<FuncionarioDTO> funcionarios = service
					.consultarFuncionarioPorFiltro(filtro,null, true);
			for (FuncionarioDTO dto : funcionarios) {
				logger.info("---------------------");
				logger.info("Clave: "+dto.getClaveFuncionario());
//				logger.info("NombreCompleto: "+dto.getNombreCompleto());
//				logger.info("Area: "+dto.getJerarquiaOrganizacional().getNombre());
//				logger.info("Depto: "+dto.getDepartamento().getNombreDepto());
//				logger.info("Area: "+dto.getDepartamento().getArea().getNombre());
//				logger.info("DiscriminanteID: "+dto.getDiscriminante().getClave());
//				logger.info("UIE: "+dto.getUnidadIEspecializada().getNombreUIE());
				logger.info("Modulos: "+dto.getUsuario().getUsuarioRoles().iterator().next().getRol().getModulos());
			}
			logger.info("Existen " + funcionarios.size() + "funcionarios");


		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

	}

	public void consultarFuncionarioPorDepartamentoYArea() {
		FuncionarioDTO filtro = new FuncionarioDTO();
		DepartamentoDTO depa = new DepartamentoDTO();
		depa.setArea(new AreaDTO(Areas.FISCAL_FACILITADOR));  //Se pasa un departamento, para que se consulte el área.
		filtro.setDepartamento(depa);

		Long idDepartamento = Areas.FISCAL_FACILITADOR.parseLong();
		try {
			List<FuncionarioDTO> funcionarios = service
					.consultarFuncionarioPorDepartamentoYArea(idDepartamento);
			logger.info("Existen " + funcionarios.size() + "funcionarios");
			for (FuncionarioDTO func : funcionarios) {
				logger.info("------------");
				logger.info("Id: " + func.getClaveFuncionario());
				logger.info("NOmbre:" + func.getNombreCompleto());
			}

		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void testConsultarFuncionariosXAgencia(){
		try {
			List<FuncionarioDTO> funcionarios = service.consultarFuncionariosXAgencia(1L);
			assertNotNull(funcionarios);
			logger.info("Existen "+funcionarios.size()+ " funcionarios de la agencia");
			for (FuncionarioDTO dto : funcionarios) {
				logger.info("----------------------");
				logger.info("Clave: "+dto.getClaveFuncionario());
				logger.info("Nombre: "+dto.getNombreCompleto());
				logger.info("DiscriminanteID: "+dto.getDiscriminante().getClave());
				
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	public void testConsultarFuncionarioXFiltroYAreas(){
		try {
			
			PaginacionDTO loPaginacion = new PaginacionDTO();
			loPaginacion.setCampoOrd("3");
			loPaginacion.setRows(10);
			loPaginacion.setDirOrd("asc");
			PaginacionThreadHolder.set(loPaginacion);
			
			Long idArea = Areas.UNIDAD_INVESTIGACION.parseLong();
			Long idCatUIE = 0L;
			Long idCatDiscriminante = 0L;
			Long idDistrito = 0L;

			FuncionarioDTO filtro = new FuncionarioDTO();
			
			Areas area = Areas.getByValor(idArea);
			
			List<Long> idsJerarquiaOrganizacional = new ArrayList<Long>();
			
			if(area != null){
				filtro.setJerarquiaOrganizacional(new JerarquiaOrganizacionalDTO(idArea));
				idsJerarquiaOrganizacional.add(idArea);
				switch (area){
					case UNIDAD_INVESTIGACION:
						idsJerarquiaOrganizacional.add(Areas.COORDINACION_UNIDAD_INVESTIGACION.parseLong());					
					break;
					case FISCAL_FACILITADOR:
						idsJerarquiaOrganizacional.add(Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.parseLong());
					break;
				}
			}
			
			
			CatDiscriminanteDTO loCatDiscriminanteDTO = new CatDiscriminanteDTO(idCatDiscriminante);
			CatDistritoDTO loCatDistritoDTO= new CatDistritoDTO(idDistrito);
			loCatDiscriminanteDTO.setDistrito(loCatDistritoDTO);
			
			// Se agrega el filtro de agencia y distrito 
			filtro.setDiscriminante(loCatDiscriminanteDTO);
			
			CatUIEspecializadaDTO loCatUIEspecializadaDTO = new CatUIEspecializadaDTO(idCatUIE); 
			// Se agrega el filtro de UIE
			filtro.setUnidadIEspecializada(loCatUIEspecializadaDTO);
			
			
			
			List<FuncionarioDTO> funcionarios = service.consultarFuncionarioXFiltroYAreas(filtro,idsJerarquiaOrganizacional);
			assertNotNull(funcionarios);
			logger.info("Existen "+funcionarios.size()+ " funcionarios de la agencia");
			for (FuncionarioDTO dto : funcionarios) {
//				logger.info("----------------------");
//				logger.info("Clave: "+dto.getClaveFuncionario());
				logger.info("Nombre: "+dto.getNombreCompleto());
//				logger.info("Edificio ID: "+(dto.getDiscriminante() != null &&
//						dto.getDiscriminante().getCatDiscriminanteId() != null ?
//								dto.getDiscriminante().getCatDiscriminanteId(): "-"));
//				logger.info("Distrito ID: "+(dto.getDiscriminante() != null &&
//						dto.getDiscriminante().getDistrito() != null &&
//						dto.getDiscriminante().getDistrito().getCatDistritoId() != null ?
//								dto.getDiscriminante().getDistrito().getCatDistritoId(): "-"));
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
}


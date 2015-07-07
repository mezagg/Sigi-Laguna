/**
* Nombre del Programa : ExpedienteServiceImplTest.java
* Autor                            : cesarAgustin
* Compania                    : Ultrasist
* Proyecto                      : NSJP                    Fecha: 15 Apr 2011
* Marca de cambio        : N/A
* Descripcion General    : Clase para generar las pruebas unitarias de los metodos del servicio de Expediente
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

import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteDTO;
import mx.gob.segob.nsjp.dto.funcionario.FuncionarioDTO;
import mx.gob.segob.nsjp.dto.institucion.AreaDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.service.expediente.AsignarNumeroExpedienteService;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;
import mx.gob.segob.nsjp.service.usuario.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase para generar las pruebas unitarias de los metodos del servicio de Expediente.
 * @version 1.0
 * @author cesarAgustin
 *
 */
public class ExpedienteServiceImplTest extends BaseTestServicios<AsignarNumeroExpedienteService> {
	@Autowired
    private UsuarioService usrService;
    
	public void testAsignarNumeroExpediente () {
		ExpedienteDTO expedienteDTO = new ExpedienteDTO();
				
//		CasoDTO casoDTO = new CasoDTO(new Long(18));
		
//		expedienteDTO.setCasoDTO(casoDTO);
		expedienteDTO.setExpedienteId(1621L);
		//expedienteDTO.setFechaApertura(new Date());
		expedienteDTO.setArea(new AreaDTO(Areas.AGENCIA_DEL_MINISTERIO_PUBLICO));
//		expedienteDTO.setEtapa(new ValorDTO(EtapasExpediente.RESTAURATIVA.getValorId()));
//		expedienteDTO.setTipoExpediente(new ValorDTO(TipoExpediente.CARPETA_EJECUCION.getValorId()));
		
		try {
		    UsuarioDTO usrParam = new UsuarioDTO();
//		        usrParam.setClaveUsuario("coordinadorConsig");
//		    usrParam.setPassword("Pa55w0rd");
		    usrParam.setFuncionario(new FuncionarioDTO(30L));
		    expedienteDTO.setUsuario(usrParam);
		    
		    ExpedienteDTO respuesta = service.asignarNumeroExpediente(expedienteDTO);
			logger.info("Respuesta : " + respuesta);
			assertNotNull("El expediente no puede estar vacio ", respuesta);
			assertNotNull("El numero de expediente no puede estar vacio ", respuesta.getNumeroExpediente());
		} catch (NSJPNegocioException e) {			
			logger.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}
}

/**
 * 
 */
package mx.gob.segob.nsjp.dao.test.usuario.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.dao.usuario.RolDAO;
import mx.gob.segob.nsjp.dto.rol.FiltroRolesDTO;
import mx.gob.segob.nsjp.model.Rol;

/**
 * @author GustavoBP
 *
 */
public class RolDAOImplTest extends BaseTestPersistencia<RolDAO> {

	public void testConsultarRoles(){
		
		try {
			Long institucionActualId;
			institucionActualId = daoServcice.consultarInsitucionActual().getConfInstitucionId();
//			institucionActualId = 2L;
			FiltroRolesDTO filtroRolesDTO=new FiltroRolesDTO();
			filtroRolesDTO.setConfInstitucionId(institucionActualId);
			List<Rol> roles = daoServcice.consultarRoles(filtroRolesDTO);
			assertFalse("La lista no debe de ser vacia", roles.isEmpty());
			logger.info(" Lista de roles" +  roles);
			for (Rol rol : roles) {
				logger.info(" Rol:" + rol.getRolId());
				logger.info(" Rol:" + rol.getNombreRol());
				logger.info(" Rol:" + rol.getDescripcionRol());
				logger.info(" Rol:" + rol.getEsActivo());
				logger.info(" Rol:" + rol.getInstitucionPertenece());
				if(!rol.getModulos().isEmpty()){
					for (int i=0;i<rol.getModulos().size();i++){
						logger.info("M�dulo: "+i+": "+ rol.getModulos().get(i).getNombreModulo());
					}
					
				}
			}
			logger.info(" Lista de roles" +  roles.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
		
	}
	
public void testConsultarRolesPadre(){
		
		try {
			Long institucionActualId;
			institucionActualId = daoServcice.consultarInsitucionActual().getConfInstitucionId();
//			institucionActualId = 2L;
			FiltroRolesDTO filtroRolesDTO=new FiltroRolesDTO();
			filtroRolesDTO.setConfInstitucionId(institucionActualId);
			List<Rol> roles = daoServcice.consultarRoles(filtroRolesDTO);
			assertFalse("La lista no debe de ser vacia", roles.isEmpty());
			logger.info(" Lista de roles" +  roles);
			for (Rol rol : roles) {
				logger.info(" Rol:" + rol.getRolId());
				logger.info(" Rol:" + rol.getNombreRol());
				logger.info(" Rol:" + rol.getDescripcionRol());
				logger.info(" Rol:" + rol.getEsActivo());
				logger.info(" Rol:" + rol.getInstitucionPertenece());
				if(!rol.getModulos().isEmpty()){
					for (int i=0;i<rol.getModulos().size();i++){
						logger.info("M�dulo: "+i+": "+ rol.getModulos().get(i).getNombreModulo());
					}
					
				}
			}
			logger.info(" Lista de roles" +  roles.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
		
	}
public void testConsultarSubRoles(){
		
		try {
			List<Rol> subRoles = daoServcice.consultarSubRoles(new Rol(7L));
			assertFalse("La lista no debe de ser vacia", subRoles.isEmpty());
			logger.info(" Lista de roles" +  subRoles);
			for (Rol rol : subRoles) {
				logger.info(" subRol:" + rol.getRolId());
				logger.info(" subRol:" + rol.getNombreRol());
				logger.info(" subRol:" + rol.getDescripcionRol());
				logger.info(" subRol:" + rol.getEsActivo());
				logger.info(" subRol:" + rol.getInstitucionPertenece());
				if(!rol.getModulos().isEmpty()){
					for (int i=0;i<rol.getModulos().size();i++){
						logger.info("M�dulo: "+i+": "+ rol.getModulos().get(i).getNombreModulo());
					}
					
				}
			}
			logger.info(" Lista de roles" +  subRoles.size());
		} catch (NSJPNegocioException e) {
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void testConsultaRol(){
		try{
			Rol rol= new Rol("luis");
			rol = daoServcice.consultarRol(rol);
			if (rol!=null){
				logger.info("Rol Id: "+rol.getRolId());
				logger.info("Nombre: "+rol.getNombreRol());
				logger.info("No Conf:"+rol.getConfActividadDocumentos().size());
				if (rol.getModulos()!=null){
					logger.info("No. Modulos: "+rol.getModulos().size());
					
				}
				if (rol.getElementosMenu()!=null){
					logger.info("No. ElementosMenu: "+rol.getElementosMenu().size());
					
				}
			}else{
				logger.info("Rol inexistante, favor de verificar");
			}
			
			
		}catch(NSJPNegocioException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	
	public void consultarRolPadre(){
			Rol rol= new Rol();
			rol = daoServcice.consultarRolPadre(201L);
			if (rol!=null){
				logger.info("Rol Id: "+rol.getRolId());
				logger.info("Nombre: "+rol.getNombreRol());				
			}else{
				logger.info("Rol inexistante, favor de verificar");
			}
	}
	
}

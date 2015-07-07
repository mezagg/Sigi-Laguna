/**
 * 
 */
package mx.gob.segob.nsjp.service.test.usuario.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.usuario.ModuloDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.service.test.base.BaseTestServicios;
import mx.gob.segob.nsjp.service.usuario.RolService;


/**
 * @author LuisMG
 * 
 */
public class RolServiceImplTest extends BaseTestServicios<RolService> {

	public void testModificarRol(){
		RolDTO rolDTO = new RolDTO(118L);
		try{
			rolDTO=service.consultarRolMinimo(rolDTO);
			rolDTO.setNombreRol("hola2");
			rolDTO.setDescripcionRol("hola2");
			service.modificarRol(rolDTO);
		}catch (NSJPNegocioException e){
			e.printStackTrace();
		}
	}
	
	public void testActualizarConfiguracionRol(){
		RolDTO rolDTO = new RolDTO(109L);
		try {
			rolDTO = service.consultarRol(rolDTO);
			List<ElementoMenuDTO> lstEMs = new ArrayList<ElementoMenuDTO>();
			List<ConfActividadDocumentoDTO> lstCADs = new ArrayList<ConfActividadDocumentoDTO>();
			lstEMs.add(new ElementoMenuDTO(41L));
			//lstCADs.add(new ConfActividadDocumentoDTO(41L));
			//lstCADs.add(new ConfActividadDocumentoDTO(42L));
			//lstEMs.add(new ElementoMenuDTO(3L));
			
			
		
			rolDTO.setElementosMenu(lstEMs);
			rolDTO.setConfActividadDocumentoDTO(lstCADs);
			if (service.actualizarConfiguracionRol(rolDTO)) {
				System.out.println("Exito al actualizar");
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}	
	}
	
	public void testActualizarModulosRol() {
		RolDTO rolDTO = new RolDTO(51L);
		try {
			rolDTO = service.consultarRol(rolDTO);
			List<ModuloDTO> lstModulos = new ArrayList<ModuloDTO>();
			// lstModulos.add(new ModuloDTO(1L));
			// lstModulos.add(new ModuloDTO(2L));
			rolDTO.setModulos(lstModulos);
			if (service.actualizaModulosRol(rolDTO)) {
				System.out.println("Exito al actualizar");
			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}
	}

	public void testConsultarSubRoles(){
		RolDTO rolDTO = new RolDTO(1L);
		try{
			List<RolDTO> subRolesDTO = service.consultarSubRoles(rolDTO);
			if (subRolesDTO!=null){
				
				for (int i=0; i<subRolesDTO.size();i++){
					System.out.println("==== Informaci�n de RolDTO ====");
					System.out.println("Id Rol: " + subRolesDTO.get(i).getRolId());
					System.out.println("Nombre: " + subRolesDTO.get(i).getNombreRol());
					System.out.println("Decripci�n: " + subRolesDTO.get(i).getDescripcionRol());
				}
			}
		}catch (NSJPNegocioException e){
			e.printStackTrace();
		}
	}
	
	public void testConsultaRolCompleto() {
		RolDTO rolDTO = new RolDTO(56L);
		try {
			rolDTO = service.consultarRolCompleto(rolDTO);
			if (rolDTO != null) {
				System.out.println("==== Informaci�n de RolDTO ====");
				System.out.println("Id Rol: " + rolDTO.getRolId());
				System.out.println("Nombre: " + rolDTO.getNombreRol());
				System.out.println("Decripci�n: " + rolDTO.getDescripcionRol());
				if (rolDTO.getEsPrincipal() != null) {
					if ((rolDTO.getEsPrincipal())) {
						System.out.println("Es el rol principal");
					}
				}
				if (rolDTO.getModulos() != null) {
					System.out.println("==== Informaci�n de ModuloDTO ====");
					if (rolDTO.getModulos().size() > 0) {
						for (int i = 0; i < rolDTO.getModulos().size(); i++) {
							System.out.println("== M�dulo Id: "
									+ rolDTO.getModulos().get(i).getModuloId());
							System.out.println("== Nombre: "
									+ rolDTO.getModulos().get(i)
											.getNombreModulo());
							System.out.println("== Descripci�n: "
									+ rolDTO.getModulos().get(i)
											.getDescripcionModulo());
							if (rolDTO.getModulos().get(i).getFunciones() != null) {
								System.out
										.println("==== Informaci�n de FuncionDTO ====");
								if (rolDTO.getModulos().get(i).getFunciones()
										.size() > 0) {
									for (int j = 0; i < rolDTO.getModulos()
											.get(i).getFunciones().size(); j++) {
										System.out.println("== == Funci�n Id: "
												+ rolDTO.getModulos().get(i)
														.getFunciones().get(j)
														.getFuncionId());
										System.out.println("== == Nombre: "
												+ rolDTO.getModulos().get(i)
														.getFunciones().get(j)
														.getNombreFuncion());
										System.out
												.println("== == Descripci�n: "
														+ rolDTO.getModulos()
																.get(i)
																.getFunciones()
																.get(j)
																.getDescripcionFuncion());
									}
								} else {
									System.out.println("== == No Funciones: 0");
								}
							}else{
								System.out.println("== == SIN FUNCIONES");
							}
						}
					} else {
						System.out.println("== == No Modulos: 0");
					}
				}else {
					System.out.println("== == SIN MODULOS");
				}

			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

	}
	public void testConsultaRolMedio() {
		RolDTO rolDTO = new RolDTO(7L);
		try {
			rolDTO = service.consultarRol(rolDTO);
			if (rolDTO != null) {
				System.out.println("==== Informaci�n de RolDTO ====");
				System.out.println("Id Rol: " + rolDTO.getRolId());
				System.out.println("Nombre: " + rolDTO.getNombreRol());
				System.out.println("Decripci�n: " + rolDTO.getDescripcionRol());
				if (rolDTO.getEsPrincipal() != null) {
					if ((rolDTO.getEsPrincipal())) {
						System.out.println("Es el rol principal");
					}
				}
				if (rolDTO.getModulos() != null) {
					System.out.println("==== Informaci�n de ModuloDTO ====");
					if (rolDTO.getModulos().size() > 0) {
						for (int i = 0; i < rolDTO.getModulos().size(); i++) {
							System.out.println("== M�dulo Id: "
									+ rolDTO.getModulos().get(i).getModuloId());
							System.out.println("== Nombre: "
									+ rolDTO.getModulos().get(i)
											.getNombreModulo());
							System.out.println("== Descripci�n: "
									+ rolDTO.getModulos().get(i)
											.getDescripcionModulo());
							if (rolDTO.getModulos().get(i).getFunciones() != null) {
								System.out
										.println("==== Informaci�n de FuncionDTO ====");
								if (rolDTO.getModulos().get(i).getFunciones()
										.size() > 0) {
									for (int j = 0; i < rolDTO.getModulos()
											.get(i).getFunciones().size(); j++) {
										System.out.println("== == Funci�n Id: "
												+ rolDTO.getModulos().get(i)
														.getFunciones().get(j)
														.getFuncionId());
										System.out.println("== == Nombre: "
												+ rolDTO.getModulos().get(i)
														.getFunciones().get(j)
														.getNombreFuncion());
										System.out
												.println("== == Descripci�n: "
														+ rolDTO.getModulos()
																.get(i)
																.getFunciones()
																.get(j)
																.getDescripcionFuncion());
									}
								} else {
									System.out.println("== == No Funciones: 0");
								}
							}else{
								System.out.println("== == SIN FUNCIONES");
							}
						}
					} else {
						System.out.println("== == No Modulos: 0");
					}
				}else {
					System.out.println("== == SIN MODULOS");
				}

			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

	}
	public void testConsultaRolMinimo() {
		RolDTO rolDTO = new RolDTO(7L);
		try {
			rolDTO = service.consultarRolMinimo(rolDTO);
			if (rolDTO != null) {
				System.out.println("==== Informaci�n de RolDTO ====");
				System.out.println("Id Rol: " + rolDTO.getRolId());
				System.out.println("Nombre: " + rolDTO.getNombreRol());
				System.out.println("Decripci�n: " + rolDTO.getDescripcionRol());
				if (rolDTO.getEsPrincipal() != null) {
					if ((rolDTO.getEsPrincipal())) {
						System.out.println("Es el rol principal");
					}
				}
				if (rolDTO.getModulos() != null) {
					System.out.println("==== Informaci�n de ModuloDTO ====");
					if (rolDTO.getModulos().size() > 0) {
						for (int i = 0; i < rolDTO.getModulos().size(); i++) {
							System.out.println("== M�dulo Id: "
									+ rolDTO.getModulos().get(i).getModuloId());
							System.out.println("== Nombre: "
									+ rolDTO.getModulos().get(i)
											.getNombreModulo());
							System.out.println("== Descripci�n: "
									+ rolDTO.getModulos().get(i)
											.getDescripcionModulo());
							if (rolDTO.getModulos().get(i).getFunciones() != null) {
								System.out
										.println("==== Informaci�n de FuncionDTO ====");
								if (rolDTO.getModulos().get(i).getFunciones()
										.size() > 0) {
									for (int j = 0; i < rolDTO.getModulos()
											.get(i).getFunciones().size(); j++) {
										System.out.println("== == Funci�n Id: "
												+ rolDTO.getModulos().get(i)
														.getFunciones().get(j)
														.getFuncionId());
										System.out.println("== == Nombre: "
												+ rolDTO.getModulos().get(i)
														.getFunciones().get(j)
														.getNombreFuncion());
										System.out
												.println("== == Descripci�n: "
														+ rolDTO.getModulos()
																.get(i)
																.getFunciones()
																.get(j)
																.getDescripcionFuncion());
									}
								} else {
									System.out.println("== == No Funciones: 0");
								}
							}else{
								System.out.println("== == SIN FUNCIONES");
							}
						}
					} else {
						System.out.println("== == No Modulos: 0");
					}
				}else {
					System.out.println("== == SIN MODULOS");
				}

			}
		} catch (NSJPNegocioException e) {
			e.printStackTrace();
		}

	}



}

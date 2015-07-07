package mx.gob.segob.nsjp.service.usuario.impl.transformer;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.dto.ConfActividadDocumentoDTO;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.usuario.ModuloDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioRolDTO;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ElementoMenu;
import mx.gob.segob.nsjp.model.Modulo;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.UsuarioRol;
import mx.gob.segob.nsjp.service.actividad.impl.ConfActividadDocumentoTransformer;
import mx.gob.segob.nsjp.service.elementomenu.impl.transform.ElementoMenuTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.JerarquiaOrganizacionalTransformer;
import mx.gob.segob.nsjp.service.expediente.impl.transform.UsuarioTransformer;
import mx.gob.segob.nsjp.service.solicitud.impl.transform.ConfInstitucionTransformer;

public class RolTransformer {

	/**
	 * M�todo que realiza la transformaci�n de TODO el entity de Rol a DTO. Es
	 * decir, tambi�n transforma los m�dulos y funciones. Esto significa, que
	 * har� la consulta a la BD hasta el �ltimo de los registros relacionados al
	 * rol.
	 * LOMG
	 * @param rol
	 * @return
	 */
	public static RolDTO transformar(Rol rol) {
		RolDTO rolDTO = null;
		if (rol != null) {
			rolDTO = new RolDTO();
			rolDTO.setDescripcionRol(rol.getDescripcionRol());
			rolDTO.setNombreRol(rol.getNombreRol());
			rolDTO.setRolId(rol.getRolId());
			rolDTO.setInstitucionPertenece(ConfInstitucionTransformer
					.transformarInstitucion(rol.getInstitucionPertenece()));
			rolDTO.setRolPadre(transformar(rol.getRolPadre()));
			if (rol.getModulos() != null) {
				rolDTO.setModulos(new ArrayList<ModuloDTO>());
				for (int i = 0; i < rol.getModulos().size(); i++) {
					rolDTO.getModulos().add(
							ModuloTransformer.transformar(rol.getModulos().get(
									i)));
				}
			}
			
			rolDTO.setJerarquiaOrganizacionalDTO(JerarquiaOrganizacionalTransformer
					.transformarJerarquiaOrganizacional(rol
							.getJerarquiaOrganizacional()));
			rolDTO.setConfActividadDocumentoDTO(new ArrayList<ConfActividadDocumentoDTO>());
			List<ConfActividadDocumento> cAD = new ArrayList<ConfActividadDocumento>();
			cAD.addAll(rol.getConfActividadDocumentos());
			for (int i=0; i<rol.getConfActividadDocumentos().size();i++){
				rolDTO.getConfActividadDocumentoDTO().add(ConfActividadDocumentoTransformer.transformarConfActividadDocumento(cAD.get(i)));
			}
			
		}
		return rolDTO;
	}
	
	
	
	
	public static RolDTO transformarBasico(Rol rol) {
		RolDTO rolDTO = null;
		if (rol != null) {
			rolDTO = new RolDTO();
			rolDTO.setDescripcionRol(rol.getDescripcionRol());
			rolDTO.setNombreRol(rol.getNombreRol());
			rolDTO.setRolId(rol.getRolId());
			rolDTO.setInstitucionPertenece(ConfInstitucionTransformer
					.transformarInstitucion(rol.getInstitucionPertenece()));
			rolDTO.setJerarquiaOrganizacionalDTO(JerarquiaOrganizacionalTransformer
					.transformarJerarquiaOrganizacional(rol
							.getJerarquiaOrganizacional()));
		}
		return rolDTO;
	}


	/**
	 * M�todo que realiza la transformaci�n de TODO el entity de RolDTO a Rol. Es
	 * decir, tambi�n transforma los m�dulos y funciones. Esto significa, que
	 * har� la consulta a la BD hasta el �ltimo de los registros relacionados al
	 * rol.
	 * LOMG
	 * @param rolDTO
	 * @return
	 */
	public static Rol transformar(RolDTO rolDTO) {
		Rol rol = null;
		if (rolDTO != null) {
			rol = new Rol();
			rol.setDescripcionRol(rolDTO.getDescripcionRol());
			rol.setNombreRol(rolDTO.getNombreRol());
			rol.setRolId(rolDTO.getRolId());
			rol.setInstitucionPertenece(ConfInstitucionTransformer
					.transformarInstitucion(rolDTO.getInstitucionPertenece()));
			rol.setRolPadre(transformar(rolDTO.getRolPadre()));
			if (rolDTO.getModulos() != null) {
				rol.setModulos(new ArrayList<Modulo>());
				for (int i = 0; i < rolDTO.getModulos().size(); i++) {
					rol.getModulos().add(
							ModuloTransformer.transformar(rolDTO.getModulos()
									.get(i)));
				}
			}
			
			rol.setJerarquiaOrganizacional(JerarquiaOrganizacionalTransformer
					.transformarJerarquiaOrganizacional(rolDTO
							.getJerarquiaOrganizacionalDTO()));
		}
		return rol;
	}



	/**
	 * M�todo que realiza la transformaci�n �NICAMENTE del entity de Rol a RolDTO.
	 * LOMG
	 * @param rol
	 * @return
	 */
	public static RolDTO transformarMinimo(Rol rol) {
		RolDTO rolDTO = null;
		if (rol != null) {
			rolDTO = new RolDTO();
			rolDTO.setDescripcionRol(rol.getDescripcionRol());
			rolDTO.setNombreRol(rol.getNombreRol());
			rolDTO.setRolId(rol.getRolId());
			rolDTO.setInstitucionPertenece(ConfInstitucionTransformer
					.transformarInstitucion(rol.getInstitucionPertenece()));
			rolDTO.setRolPadre(transformarMinimo(rol.getRolPadre()));
			rolDTO.setJerarquiaOrganizacionalDTO(JerarquiaOrganizacionalTransformer
					.transformarJerarquiaOrganizacional(rol
							.getJerarquiaOrganizacional()));
		
			
		}
		return rolDTO;
	}
/**
 *  M�todo que realiza la transformaci�n �NICAMENTE del entity de RolDTO a Rol.
 *  LOMG
 * @param rolDTO
 * @return
 */
	public static Rol transformarMinimo(RolDTO rolDTO) {
		Rol rol = null;
		if (rolDTO != null) {
			rol = new Rol();
			rol.setDescripcionRol(rolDTO.getDescripcionRol());
			rol.setNombreRol(rolDTO.getNombreRol());
			rol.setRolId(rolDTO.getRolId());
			rol.setRolPadre(transformar(rolDTO.getRolPadre()));			
			rol.setJerarquiaOrganizacional(JerarquiaOrganizacionalTransformer
					.transformarJerarquiaOrganizacional(rolDTO
							.getJerarquiaOrganizacionalDTO()));
		}
		return rol;
	}
	
	/**
	 * M�todo que realiza la transformaci�n del entity de Rol a RolDTO
	 * hasta el nivel de ModulosDTO.
	 * LOMG
	 * @param rol
	 * @return
	 */
	public static RolDTO transformarMedio(Rol rol) {
		RolDTO rolDTO = null;
		if (rol != null) {
			rolDTO = new RolDTO();
			rolDTO.setDescripcionRol(rol.getDescripcionRol());
			rolDTO.setNombreRol(rol.getNombreRol());
			rolDTO.setRolId(rol.getRolId());
			rolDTO.setInstitucionPertenece(ConfInstitucionTransformer
					.transformarInstitucion(rol.getInstitucionPertenece()));
			rolDTO.setRolPadre(transformar(rol.getRolPadre()));
			if (rol.getModulos() != null) {
				rolDTO.setModulos(new ArrayList<ModuloDTO>());
				for (int i = 0; i < rol.getModulos().size(); i++) {
					rolDTO.getModulos().add(
							ModuloTransformer.transformarMinimo(rol.getModulos().get(
									i)));
				}
			}
			if (rol.getElementosMenu()!=null){
				rolDTO.setElementosMenu(new ArrayList<ElementoMenuDTO>());
				for (int i=0;i<rol.getElementosMenu().size();i++){
					rolDTO.getElementosMenu().add(ElementoMenuTransformer.transformarMinimo(rol.getElementosMenu().get(i)));
				}
			}
			rolDTO.setJerarquiaOrganizacionalDTO(JerarquiaOrganizacionalTransformer
					.transformarJerarquiaOrganizacional(rol
							.getJerarquiaOrganizacional()));
			if(rol.getConfActividadDocumentos()!=null && rol.getConfActividadDocumentos().size()>0){
				List<ConfActividadDocumentoDTO> list=new ArrayList<ConfActividadDocumentoDTO>();
				for (ConfActividadDocumento confActividadDocumento : rol.getConfActividadDocumentos()){
					list.add(ConfActividadDocumentoTransformer.transformarConfActividadDocumento(confActividadDocumento));
				}
				rolDTO.setConfActividadDocumentoDTO(list);
			}
		}
		return rolDTO;
	}

	/**
	 * M�todo que realiza la transformaci�n del entity de RolDTO a Rol
	 * hasta el nivel de m�dulo
	 * LOMG
	 * @param rolDTO
	 * @return
	 */
	public static Rol transformarMedio(RolDTO rolDTO) {
		Rol rol = null;
		if (rolDTO != null) {
			rol = new Rol();
			rol.setDescripcionRol(rolDTO.getDescripcionRol());
			rol.setNombreRol(rolDTO.getNombreRol());
			rol.setRolId(rolDTO.getRolId());
			rol.setInstitucionPertenece(ConfInstitucionTransformer
					.transformarInstitucion(rolDTO.getInstitucionPertenece()));
			rol.setRolPadre(transformar(rolDTO.getRolPadre()));
			if (rolDTO.getModulos() != null) {
				rol.setModulos(new ArrayList<Modulo>());
				for (int i = 0; i < rolDTO.getModulos().size(); i++) {
					rol.getModulos().add(
							ModuloTransformer.transformarMinimo(rolDTO.getModulos()
									.get(i)));
				}
			}
			if (rolDTO.getElementosMenu()!=null){
				rol.setElementosMenu(new ArrayList<ElementoMenu>());
				for (int i=0;i<rolDTO.getElementosMenu().size();i++){
					rol.getElementosMenu().add(ElementoMenuTransformer.transformarMinimo(rolDTO.getElementosMenu().get(i)));
				}
				
			}
			
			if (rolDTO.getConfActividadDocumentoDTO()!=null){
				rol.setConfActividadDocumentos(ConfActividadDocumentoTransformer.listTransformer(rolDTO.getConfActividadDocumentoDTO()));
				
			}
		
			rol.setJerarquiaOrganizacional(JerarquiaOrganizacionalTransformer
					.transformarJerarquiaOrganizacional(rolDTO
							.getJerarquiaOrganizacionalDTO()));
			
		}
		return rol;
	}

	/**
	 * Transforma un objeto del tipo <code>UsuarioRol</code> a su
	 * correspondiente DTO <code>UsuarioRolDTO</code>
	 * 
	 * @param src
	 * @return
	 */
	public static UsuarioRolDTO transformar(UsuarioRol src) {
		UsuarioRolDTO ur = null;
		if (src != null) {
			ur = new UsuarioRolDTO();
			ur.setFechaFin(src.getFechaFin());
			ur.setFechaInicio(src.getFechaInicio());
			ur.setRol(transformar(src.getRol()));
			ur.setUsuario(UsuarioTransformer.transformarUsuario(src
					.getUsuario()));
		}
		return ur;
	}

}

/**
 * 
 */
package mx.gob.segob.nsjp.service.elementomenu.impl;

import java.util.*;

import mx.gob.segob.nsjp.comun.enums.menu.TipoMenu;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.dao.elementomenu.ElementoMenuDAO;
import mx.gob.segob.nsjp.dto.elementomenu.ElementoMenuDTO;
import mx.gob.segob.nsjp.dto.usuario.RolDTO;
import mx.gob.segob.nsjp.model.ElementoMenu;
import mx.gob.segob.nsjp.service.elementomenu.ElementoMenuService;
import mx.gob.segob.nsjp.service.elementomenu.impl.transform.ElementoMenuTransformer;
import mx.gob.segob.nsjp.service.usuario.impl.transformer.RolTransformer;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LuisMG
 * 
 */
@Service
@Transactional
public class ElementoMenuServiceImpl implements ElementoMenuService {
	@Autowired
	private ElementoMenuDAO elementoMenuDAO;

	public static HashMap<String, List> menuDTOHashtable;

	public ElementoMenuServiceImpl(){
		menuDTOHashtable = new HashMap<String, List>();
		/*
		Iterator i = lista.iterator();
		ElementoMenuDTO menu;
		while (i.hasNext())
		{
			menu= (ElementoMenuDTO) i.next();
			System.out.println(menu.getcNombre());
		}*/
	}

	@Override
	public List<ElementoMenuDTO> consultarElementosMenuXRol(RolDTO rolDTO, TipoMenu tm)
			throws NSJPNegocioException {
		List<ElementoMenuDTO> resp = null;

		//resp = menuDTOHashtable.get(rolDTO.getNombreRol()+"_"+tm.name());

		//if(resp==null){
			List<ElementoMenu> elementosMenu = elementoMenuDAO
					.consultarElementosMenuXRol(RolTransformer.transformarMinimo(rolDTO), tm, null);

			resp = construyeMenuDTO(elementosMenu );
		//	menuDTOHashtable.put(rolDTO.getNombreRol()+"_"+tm.name(), resp);
		//}

		return resp;
	}



	@Override
	public List<ElementoMenuDTO> consultarElementosMenuObligatorios () throws NSJPNegocioException{
		List<ElementoMenuDTO> resp = null;
		List<ElementoMenu> elementosMenu = elementoMenuDAO
				.consultarElementosMenuObligatorios();
		if (elementosMenu != null && !elementosMenu.isEmpty()) {
			resp = new ArrayList<ElementoMenuDTO>();
			for (int i = 0; i < elementosMenu.size(); i++) {
				if (elementosMenu.get(i).getElementoMenuPadre() == null) {
					resp.add(construyeArbolDTO(elementosMenu,
							elementosMenu.get(i), 1));
				}
			}
		}
		return resp;
	}

	@Override
	public ElementoMenuDTO consultarElementoMenu(RolDTO rolDTO, ElementoMenuDTO eMDTO)
			throws NSJPNegocioException {
		ElementoMenuDTO resp = null;
		ElementoMenu eM = null;
		List<ElementoMenu> eMFacultados = elementoMenuDAO.consultarElementosMenuXRol(RolTransformer.transformar(rolDTO), null, eMDTO.getElementoMenuId());
		List<ElementoMenuDTO> emDtos = construyeMenuDTO( eMFacultados );
		eMDTO.getElementoMenuHijosDTO().addAll(emDtos);
		//Collections.sort(hijos, new ElementoMenuIOrden());
		return eMDTO;
	}


	private List<ElementoMenuDTO> construyeMenuDTO( List<ElementoMenu> elementosMenu) {
		List<ElementoMenuDTO> resp =  new ArrayList<ElementoMenuDTO>();
		ElementoMenu em;
		ElementoMenuDTO emDto;
		ElementoMenuDTO emDtoPadre;

		HashMap<Long, ElementoMenuDTO> hm = new HashMap();
		Iterator<ElementoMenu>it=elementosMenu.iterator();

		while (it.hasNext()){
			em =it.next();
			emDto = ElementoMenuTransformer.transformar(em);
			hm.put(em.getElementoMenuId(), emDto);
			if(em.getElementoMenuPadre()!=null) {
				System.out.println(">"+em.getElementoMenuId()+" "+em.getElementoMenuPadre().getElementoMenuId());
				emDtoPadre = hm.get(em.getElementoMenuPadre().getElementoMenuId());
				if(emDtoPadre!=null)
					emDtoPadre.getElementoMenuHijosDTO().add(emDto);
				else{
					resp.add(emDto);
				}
			}else{
				resp.add(emDto);
			}

		}
		return resp;
	}

	/** 
	 * Dado un Elemento Menu y un nivel de profundiad se regresa el �rbol pertinente
	 * Para la profundicad los valores indican lo siguiente
	 * 0 - Padres
	 * 1 - Padres e hijos
	 * 2 - Padres, hijos y nietos
	 * y as� sucesivamente
	 */
	ElementoMenuDTO construyeArbolDTO(List<ElementoMenu> facultados,
			ElementoMenu eM, int profundidad) {
		ElementoMenuDTO eMDTO = null;
		ElementoMenuDTO eMTemp = null;
		int j = 0;
		if (eM != null) {
			while (j < facultados.size()
					&& facultados.get(j).getElementoMenuId() != eM
							.getElementoMenuId()) {
				j++;
			}
			if (j < facultados.size()) {
				eMDTO = ElementoMenuTransformer.transformar(eM);
				if (profundidad > 0) {
					if (eM.getElementoMenuHijos() != null) {
						eMDTO.setElementoMenuHijosDTO(new ArrayList<ElementoMenuDTO>());
						for (int i = 0; i < eM.getElementoMenuHijos().size(); i++) {
							eMTemp = construyeArbolDTO(facultados, eM
									.getElementoMenuHijos().get(i),
									profundidad - 1);
							if (eMTemp != null) {
								eMDTO.getElementoMenuHijosDTO().add(eMTemp);
							}
						}
					}
				} else {
					eMDTO.setElementoMenuHijosDTO(null);
				}
			} else {
				eMDTO = null;
			}
		}
		return eMDTO;
	}

	
	class ElementoMenuIOrden implements Comparator<ElementoMenuDTO>{

		public int compare(ElementoMenuDTO o1, ElementoMenuDTO o2) {
			
			if ((o1 == null || o1.getiOrden() == null) && (o2 == null || o2.getiOrden()== null)) {
				return 0;
			}
			//null mayor que un numero, se ordenan los null despu�s de los n�meros
			if (o1 == null || o1.getiOrden() == null){
				return 1;
			}
			// numero menor que null, se ordenan los n�meros antes de los null
			if (o2 == null || o2.getiOrden()== null){
				return -1;
			}
			return o1.getiOrden().compareTo(o2.getiOrden());
		}
	}	
	
}




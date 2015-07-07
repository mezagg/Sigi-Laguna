/**
 * 
 */
package mx.gob.segob.nsjp.dao.test.elementomenu.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.segob.nsjp.comun.enums.menu.TipoMenu;
import mx.gob.segob.nsjp.dao.elementomenu.ElementoMenuDAO;
import mx.gob.segob.nsjp.dao.test.base.BaseTestPersistencia;
import mx.gob.segob.nsjp.model.ElementoMenu;
import mx.gob.segob.nsjp.model.Funcion;
import mx.gob.segob.nsjp.model.Rol;

/**
 * @author LuisMG
 * 
 */
public class ElementoMenuDAOImplTest extends
		BaseTestPersistencia<ElementoMenuDAO> {


	
	public void testConsultarElementoMenu() {
		ElementoMenu elementoMenu = new ElementoMenu(1L);
		elementoMenu = daoServcice.consultarElementoMenu(elementoMenu);
		assertNotNull(elementoMenu);
		imprimeArbol(elementoMenu, 0);
	}
	
	public void testConsultarElementosMenuXRol(){
		List<ElementoMenu> elementosMenu = new ArrayList<ElementoMenu>();
		Rol rol = new Rol(3L);
		TipoMenu tm = TipoMenu.ARRIBA;
		elementosMenu=daoServcice.consultarElementosMenuXRol(rol, tm);
		assertNotNull(elementosMenu);
		for (int i=0;i<elementosMenu.size();i++)
		{
		imprimeArbol(elementosMenu.get(i), 0);
		}
	}
	
	public void testConsultarElementosMenuObligatorios(){
		List<ElementoMenu> elementosMenu = new ArrayList<ElementoMenu>();
		
		elementosMenu=daoServcice.consultarElementosMenuObligatorios();
		assertNotNull(elementosMenu);
		for (int i=0;i<elementosMenu.size();i++)
		{
		imprimeArbol(elementosMenu.get(i), 0);
		}
	}

	public void testEliminarElementoMenu() {
		ElementoMenu elementoMenu = new ElementoMenu(5L);
		if (daoServcice.eliminarElementoMenu(elementoMenu))
			System.out.println("Elemento elminado CORRECTAMENTE");
		else
			System.out.println("Elemento no encontrado, favor de verificar");
	}

	public void testInsertarSimpleElementoMenu() {
		ElementoMenu elementoMenuPadre = new ElementoMenu();
		Funcion funcion = new Funcion(1L, "Funci�n 1", "La Funci�n 1");
		ElementoMenu elementoMenu = new ElementoMenu(elementoMenuPadre,
				funcion, "El menu de luis", 0, "El comando de luis");
		Long result = daoServcice.insertarSimpleElementoMenu(elementoMenu);
		if (result != null)
			System.out.print("Se insert� correctamente el Elemento del Men�");
		else
			System.out.print("Error al insertar el Men�");
	}

	void imprimeArbol(ElementoMenu elementoMenu, int nivel) {
		if (elementoMenu != null) {
			if (nivel==0)
				System.out.println("-----------------------------------------------------------------");
			for (int i = 0; i < nivel * 5; i++)
				System.out.print("*");
			System.out.println("Clave: " + elementoMenu.getElementoMenuId()
					+ " Nombre: " + elementoMenu.getcNombre()+ " EsObligatorio: " + elementoMenu.isEsObligatorio());
			if (elementoMenu.getElementoMenuHijos() != null
					&& !elementoMenu.getElementoMenuHijos().isEmpty())
				for (int i = 0; i < elementoMenu.getElementoMenuHijos().size(); i++) {
					imprimeArbol(elementoMenu.getElementoMenuHijos().get(i),
							nivel + 1);
				}
		}
	}

}

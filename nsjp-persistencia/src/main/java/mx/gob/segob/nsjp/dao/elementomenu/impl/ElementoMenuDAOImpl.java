/**
 * 
 */
package mx.gob.segob.nsjp.dao.elementomenu.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.enums.menu.TipoMenu;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.elementomenu.ElementoMenuDAO;
import mx.gob.segob.nsjp.model.ElementoMenu;
import mx.gob.segob.nsjp.model.Rol;

/**
 * @author LuisMG
 * 
 */
@Repository
public class ElementoMenuDAOImpl extends
		GenericDaoHibernateImpl<ElementoMenu, Long> implements ElementoMenuDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public ElementoMenu consultarElementoMenu(ElementoMenu elementoMenu) {
		ElementoMenu resp = new ElementoMenu();
		final StringBuffer query = new StringBuffer();
		query.append(" select s ");
		query.append(" from ElementoMenu s");
		query.append(" where s.elementoMenuId= :elementoMenuId");
		query.append(" order by s.iOrden ");
		logger.debug(" query :: " + query);
		Query hbq = super.getSession().createQuery(query.toString());
		hbq.setLong("elementoMenuId", elementoMenu.getElementoMenuId());
		resp = (ElementoMenu) hbq.uniqueResult();
		return resp;
	}

	@Override
	public boolean eliminarElementoMenu(ElementoMenu elementoMenu) {
		boolean resp = false;
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("DELETE FROM ElementoMenu WHERE ElementoMenu_id= "
				+ elementoMenu.getElementoMenuId());
		super.getSession().createSQLQuery(queryStr.toString()).executeUpdate();
		resp = true;
		return resp;
	}

	@Override
	public Long insertarSimpleElementoMenu(ElementoMenu elementoMenu) {
		Long resp = null;
		final StringBuffer queryStr = new StringBuffer();
		queryStr.append("INSERT INTO ElementoMenu(ElementoMenuPadre_id,Funcion_id,cNombre,iOrden,cComando)");
		queryStr.append(" VALUES(");
		queryStr.append(elementoMenu.getElementoMenuPadre().getElementoMenuId()
				+ "," + elementoMenu.getFuncion().getFuncionId() + ",'"
				+ elementoMenu.getcNombre() + "'," + elementoMenu.getiOrden()
				+ ",'" + elementoMenu.getcComando() + "')");
		resp = (long) super.getSession().createSQLQuery(queryStr.toString())
				.executeUpdate();

		return resp;
	}
//Long elementoMenuId,String cNombre, String cComando, String cIdHTML, String cClassHTML, Integer iOrden, Integer iPosicion, Long idElementoMenuPadre
	@Override
	@SuppressWarnings("unchecked")
	public List<ElementoMenu> consultarElementosMenuXRol(Rol rol, TipoMenu tm, Long elementoMenuId) {
		StringBuffer queryString = new StringBuffer();

			queryString
					.append("select new ElementoMenu (em.elementoMenuId, em.cNombre, em.cComando, em.cIdHTML, em.cClassHTML, em.cForward, em.iOrden, em.iPosicion, emp.elementoMenuId)" )
					.append(" FROM ElementoMenu em left join em.elementoMenuPadre emp join em.roles r WHERE " );
		if (tm != null) {

			queryString.append("em.iPosicion = "+tm.getId() +" and" );
		}
		if(elementoMenuId!=null){
			queryString.append("emp.elementoMenuId = "+ elementoMenuId +" and" );
		}
			queryString.append(" r.rolId = "+rol.getRolId())
					   .append(" ORDER BY em.elementoMenuPadre, em.iPosicion, em.iOrden");


		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ElementoMenu> consultarElementosMenuObligatorios (){
		StringBuffer queryString = new StringBuffer();
		queryString
		.append("FROM ElementoMenu em WHERE em.elementoMenuId IN ( ")
		.append(" SELECT em.elementoMenuId FROM ElementoMenu WHERE em.esObligatorio = ")
		.append(true).append(" ) ")
		.append(" ORDER BY em.iOrden");
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

}

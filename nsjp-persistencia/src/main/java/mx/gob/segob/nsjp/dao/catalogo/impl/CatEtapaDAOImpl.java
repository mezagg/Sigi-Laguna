/**
 * Nombre del Programa : catEtapaDAOImpl.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 21/01/2013
 * Marca de cambio        : N/A
 * Descripcion General    : Implementaci&oacute; del objeto de acceso a datos para el catalogo CatEtapa.
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
package mx.gob.segob.nsjp.dao.catalogo.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.catalogo.CatEtapaDAO;
import mx.gob.segob.nsjp.model.CatEtapa;

/**
 * Implementaci&oacute;n del objeto de acceso a datos para el catalogo CatEtapa.
 * 
 * @author GustavoBP
 */
@Repository
public class CatEtapaDAOImpl extends GenericDaoHibernateImpl<CatEtapa, Long>
		implements CatEtapaDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<CatEtapa> consultarEtapasExpedienteInvolucrado(
			Boolean esEtapaExpediente) {

		StringBuffer queryString = new StringBuffer();
		queryString.append(" FROM CatEtapa WHERE 1=1 ");

		if (esEtapaExpediente != null) {
			queryString.append(" AND esEtapaExpediente = ");
			queryString.append(esEtapaExpediente);
		}

		logger.debug("Query:" + queryString);
		Query query = super.getSession().createQuery(queryString.toString());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatEtapa> consultarEtapasPadresExpedienteInvolucrado(
			Boolean esEtapaExpediente) {

		StringBuffer queryString = new StringBuffer();
		queryString.append(" FROM CatEtapa WHERE 1=1 ");

		if (esEtapaExpediente != null) {
			queryString.append(" AND esEtapaExpediente = ");
			queryString.append(esEtapaExpediente);
		}
		queryString.append(" AND catEtapaPadre is null ");

		logger.debug("Query:" + queryString);
		Query query = super.getSession().createQuery(queryString.toString());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatEtapa> consultarEtapasHijaExpedienteInvolucradoPorPadre(
			Boolean esEtapaExpediente, Long catEtapaPadreId) {

		StringBuffer queryString = new StringBuffer();
		queryString.append(" FROM CatEtapa WHERE 1=1 ");

		if (esEtapaExpediente != null) {
			queryString.append(" AND esEtapaExpediente = ");
			queryString.append(esEtapaExpediente);
		}
		if (catEtapaPadreId != null) {
			queryString.append(" AND catEtapaPadre = ");
			queryString.append(catEtapaPadreId);
		}

		logger.debug("Query:" + queryString);
		Query query = super.getSession().createQuery(queryString.toString());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatEtapa> consultarEtapaPorFiltro(CatEtapa filtro) {
		StringBuffer queryString = new StringBuffer();
		queryString.append(" FROM CatEtapa WHERE 1=1 ");

		if (filtro.getCatEtapaId() != null && filtro.getCatEtapaId() > 0) {
			queryString.append(" AND catEtapaId = ");
			queryString.append(filtro.getCatEtapaId());
		}
		if (filtro.getCatEtapaValor() != null
				&& filtro.getCatEtapaValor().getValorId() != null
				&& filtro.getCatEtapaValor().getValorId() > 0) {
			queryString.append(" AND catEtapaValor.valorId = ");
			queryString.append(filtro.getCatEtapaValor().getValorId());
		}

		if (filtro.getCatEtapaPadre() != null
				&& filtro.getCatEtapaPadre().getCatEtapaId() != null
				&& filtro.getCatEtapaPadre().getCatEtapaId() > 0) {
			queryString.append(" AND catEtapaPadre.catEtapaId = ");
			queryString.append(filtro.getCatEtapaPadre().getCatEtapaId());
		}

		if (filtro.getEsEtapaExpediente() != null) {
			queryString.append(" AND esEtapaExpediente = ");
			queryString.append(filtro.getEsEtapaExpediente());
		}

		if (filtro.getOrdenSecuencia() != null) {
			queryString.append(" AND ordenSecuencia = ");
			queryString.append(filtro.getOrdenSecuencia());
		}

		if (filtro.getInstitucionOrigen() != null
				&& filtro.getInstitucionOrigen().getConfInstitucionId() != null
				&& filtro.getInstitucionOrigen().getConfInstitucionId() > 0) {
			queryString.append(" AND institucionOrigen.confInstitucionId = ");
			queryString.append(filtro.getInstitucionOrigen()
					.getConfInstitucionId());
		}

		logger.debug("Query:" + queryString);
		Query query = super.getSession().createQuery(queryString.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public CatEtapa consultarEtapaInicialPorInstitucion(Long confInstitucionId) {
		StringBuffer queryString = new StringBuffer();

		queryString.append(" FROM CatEtapa ce WHERE 1=1 ");
		if (confInstitucionId != null && confInstitucionId > 0) {
			queryString
					.append(" AND ce.institucionOrigen.confInstitucionId = ")
					.append(confInstitucionId);
		}
		logger.debug("Query:" + queryString);
		Query query = super.getSession().createQuery(queryString.toString());
		List<CatEtapa> resultados = query.list();
		if (resultados != null && !resultados.isEmpty()) {
			return resultados.get(0);
		} else {
			//Tomar por default la etapa incial de PG
			queryString = new StringBuffer();
			queryString.append(" FROM CatEtapa ce WHERE 1=1 ");
			queryString
					.append(" AND ce.institucionOrigen.confInstitucionId = ")
					.append(Instituciones.PGJ.getValorId());
			logger.debug("Query:" + queryString);
			query = super.getSession()
					.createQuery(queryString.toString());
			resultados = query.list();
			if (resultados != null && !resultados.isEmpty()) {
				return resultados.get(0);
			} else {
				return null;
			}
		}
	}
}

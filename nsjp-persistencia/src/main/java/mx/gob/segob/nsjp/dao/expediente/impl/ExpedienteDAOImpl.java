/**
 *
 * Nombre del Programa : ExpedienteDAOImpl.java Autor : Cesar Agustin Compania :
 * Ultrasist Proyecto : NSJP Fecha: 30/03/2011 Marca de cambio : N/A Descripcion
 * General : Implementaci�n para el DAO de la entidad Expediente Programa
 * Dependiente :N/A Programa Subsecuente :N/A Cond. de ejecucion :N/A Dias de
 * ejecucion :N/A Horario: N/A MODIFICACIONES
 * ------------------------------------------------------------------------------
 * Autor :N/A Compania :N/A Proyecto :N/A Fecha: N/A Modificacion :N/A
 * ------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.expediente.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import mx.gob.segob.nsjp.comun.enums.actividad.Actividades;
import mx.gob.segob.nsjp.comun.enums.calidad.Calidades;
import mx.gob.segob.nsjp.comun.enums.excepciones.CodigoError;
import mx.gob.segob.nsjp.comun.enums.expediente.EtapasExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoBusquedaCoordinadorAT;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoDeBusquedaDeExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoExpediente;
import mx.gob.segob.nsjp.comun.enums.expediente.TipoTurno;
import mx.gob.segob.nsjp.comun.enums.institucion.Areas;
import mx.gob.segob.nsjp.comun.enums.menu.EstatusMenuJAR;
import mx.gob.segob.nsjp.comun.enums.solicitud.TiposSolicitudes;
import mx.gob.segob.nsjp.comun.excepcion.NSJPNegocioException;
import mx.gob.segob.nsjp.comun.indicador.Indicadores;
import mx.gob.segob.nsjp.comun.util.DateUtils;
import mx.gob.segob.nsjp.comun.util.PaginadorUtil;
import mx.gob.segob.nsjp.comun.util.QueryUtils;
import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO;
import mx.gob.segob.nsjp.dao.expediente.NumeroExpedienteDAO;
import mx.gob.segob.nsjp.dao.funcionario.FuncionarioDAO;
import mx.gob.segob.nsjp.dao.involucrado.InvolucradoDAO;
import mx.gob.segob.nsjp.dao.persona.DelitoPersonaDAO;
import mx.gob.segob.nsjp.dao.usuario.UsuarioDAO;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.dto.caso.CasoDTO;
import mx.gob.segob.nsjp.dto.expediente.ExpedienteViewDTO;
import mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO;
import mx.gob.segob.nsjp.dto.institucion.JerarquiaOrganizacionalDTO;
import mx.gob.segob.nsjp.dto.usuario.UsuarioDTO;
import mx.gob.segob.nsjp.model.CatDiscriminante;
import mx.gob.segob.nsjp.model.ArchivoDigital;
import mx.gob.segob.nsjp.model.CatAreasNegocio;
import mx.gob.segob.nsjp.model.ConfInstitucion;
import mx.gob.segob.nsjp.model.Delito;
import mx.gob.segob.nsjp.model.DelitoPersona;
import mx.gob.segob.nsjp.model.Documento;
import mx.gob.segob.nsjp.model.Expediente;
import mx.gob.segob.nsjp.model.Funcionario;
import mx.gob.segob.nsjp.model.Involucrado;
import mx.gob.segob.nsjp.model.JerarquiaOrganizacional;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Usuario;
import mx.gob.segob.nsjp.model.Valor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CesarAgustin
 * @version 1.0
 *
 */
@Repository
public class ExpedienteDAOImpl extends
        GenericDaoHibernateImpl<Expediente, Long> implements ExpedienteDAO {

    @Autowired
    private InvolucradoDAO involucradoDao;

    @Autowired
    private DelitoPersonaDAO delitoPersonaDao;

    @Autowired
    private NumeroExpedienteDAO numeroExpedienteDao;

    @Autowired
    private UsuarioDAO usuarioDao;

    @Autowired
    private FuncionarioDAO funcionarioDAO;

    public List<Expediente> buscarExpedientes(String numeroExpediente,
            Long areaId, Long discriminanteId) {
        if (logger.isDebugEnabled()) {
            logger.debug("numeroExpediente :: [" + numeroExpediente
                    + "] y areaId :: " + areaId);
        }
        logger.info("numeroExpediente :: [" + numeroExpediente
                + "] y areaId :: " + areaId);

        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select e FROM Expediente e left join e.numeroExpedientes n");
        queryStr.append(" WHERE n.numeroExpediente like '");
        queryStr.append(numeroExpediente);
        if (areaId != null && areaId > 0) {
            queryStr.append("' and n.jerarquiaOrganizacional.jerarquiaOrganizacionalId = " + areaId);
        } else {
            queryStr.append("'");
        }
        if (discriminanteId != null && !discriminanteId.equals(0L)) {
            queryStr.append(" and n.expediente.discriminante.catDiscriminanteId=").append(discriminanteId);
        }

        Query query = super.getSession().createQuery(queryStr.toString());
        logger.info("QUERY:" + query.toString());
        List<?> lista = query.list();
        List<Expediente> listaRegreso = new ArrayList<Expediente>();
        for (Object object : lista) {
            if (object instanceof Expediente) {
                listaRegreso.add((Expediente) object);
            }
        }
        return listaRegreso;
    }

    @SuppressWarnings("unchecked")
    public List<NumeroExpediente> buscarNumeroExpedientes(Long expedienteID,
            Long areaId) {
        if (logger.isDebugEnabled()) {
            logger.debug("expedienteID :: [" + expedienteID
                    + "] y areaId :: " + areaId);
        }
        logger.info("expedienteID :: [" + expedienteID
                + "] y areaId :: " + areaId);

        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select n FROM NumeroExpediente n ");
        queryStr.append(" WHERE n.expediente.expedienteId in ( ");
        queryStr.append(expedienteID);
        queryStr.append(" ) and n.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
        queryStr.append(areaId);
        Query query = super.getSession().createQuery(queryStr.toString());
        logger.info("QUERY:" + query.toString());
        return query.list();
    }

    @Override
    public String obtenerUltimoNumero(Long area) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select e.numeroExpediente ");
        queryStr.append(" from NumeroExpediente e where e.numeroExpedienteId =");
        queryStr.append(" (select MAX(obj.numeroExpedienteId) from NumeroExpediente obj where obj.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
        queryStr.append(area);
        queryStr.append(" )");
        queryStr.append(" and e.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
        queryStr.append(area);
        logger.debug("queryStr :: " + queryStr);
        Query qry = super.getSession().createQuery(queryStr.toString());
        return (String) qry.uniqueResult();
    }

    @Override
    public Expediente buscarUltimoNumeroPorExpedienteIdAreaId(Long expedienteId,
            Long areaId) {
        logger.debug("expedienteId :: [" + expedienteId
                + "] y areaId :: " + areaId);

        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new Expediente(e.expedienteId, n.numeroExpediente, n.numeroExpedienteId) ");
        queryStr.append(" FROM Expediente e left join e.numeroExpedientes n");
        queryStr.append(" WHERE e.expedienteId = ").append(expedienteId);

        queryStr.append(" AND n.numeroExpedienteId = ");
        queryStr.append(" (select MAX(obj.numeroExpedienteId) from NumeroExpediente obj where 1=1");
        if (areaId != null) {
            queryStr.append(" AND obj.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
            queryStr.append(areaId);
        }
        queryStr.append(" AND obj.expediente.expedienteId =  ");
        queryStr.append(expedienteId);
        queryStr.append(" )");
        if (areaId != null) {
            queryStr.append(" AND n.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").append(areaId);
        }
        logger.info("QUERY PARA ULTIMO NUM EXPEDIENTE "+ queryStr.toString());
        Query query = super.getSession().createQuery(queryStr.toString());
        return (Expediente) query.uniqueResult();
    }

    @Override
    public List<Expediente> consultarExpedientesPorIdCaso(Long idCaso,
            Long areaId) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new Expediente(v.expedienteId, n.numeroExpediente, n.numeroExpedienteId)");
        queryStr.append(" from Expediente v ");
        queryStr.append(" left join v.numeroExpedientes n");
        queryStr.append(" where v.caso.casoId = ");
        queryStr.append(idCaso);
        if (areaId != null) {
            queryStr.append(" and n.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
            queryStr.append(areaId);
        }
        queryStr.append(" order by n.numeroExpediente");
        @SuppressWarnings("unchecked")
        List<Expediente> resp = super.getHibernateTemplate().find(
                queryStr.toString());
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        return resp;
    }

    @Override
    public List<Expediente> consultarExpedientesPorId(Long idCaso,
            Long areaId) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select v from Expediente v LEFT JOIN v.numeroExpedientes ne");
        queryStr.append(" where v.caso.casoId = ");
        queryStr.append(idCaso);
        if (areaId != null) {
            queryStr.append(" and ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
            queryStr.append(areaId);
        }
        @SuppressWarnings("unchecked")
        List<Expediente> resp = super.getHibernateTemplate().find(
                queryStr.toString());
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        return resp;
    }

    @SuppressWarnings("unchecked")
    public List<Long> consultarExpedientesPorEvidencia(FiltroExpedienteDTO filtroExpedienteDTO) {

        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT DISTINCT ").append(filtroExpedienteDTO.getNombreEvidencia())
                .append(".expediente.expedienteId FROM ")
                .append(filtroExpedienteDTO.getNombreEvidencia()).append(" ").append(filtroExpedienteDTO.getNombreEvidencia());

        List<Field> atrString = QueryUtils.obtenerParametrosQuery(
                filtroExpedienteDTO.getNombreEvidencia(), String.class);
        List<Field> atrValor = QueryUtils.obtenerParametrosQuery(
                filtroExpedienteDTO.getNombreEvidencia(), Valor.class);

        for (Field field : atrValor) {

            queryString.append(" left outer join ").append(filtroExpedienteDTO.getNombreEvidencia())
                    .append(".");
            queryString.append(field.getName()).append(" as ");
            queryString.append(field.getName());
        }

        queryString.append(" where ");

        boolean isFirstCondition = true;
        for (String palabra : filtroExpedienteDTO.getPalabrasClave()) {
            if (StringUtils.isNotBlank(palabra)) {
                if (isFirstCondition) {
                    isFirstCondition = false;
                } else {
                    queryString.append(" OR ");
                }
                queryString.append(filtroExpedienteDTO.getNombreEvidencia() + ".descripcion like '%");
                queryString.append(palabra).append("%'");
            }
        }

        for (String palabra : filtroExpedienteDTO.getPalabrasClave()) {
            if (StringUtils.isNotBlank(palabra)) {
                for (Field field : atrValor) {
                    queryString.append(" OR ");
                    queryString.append(field.getName());
                    queryString.append(".valor like '%").append(palabra)
                            .append("%'");
                }
            }
        }

        for (String palabra : filtroExpedienteDTO.getPalabrasClave()) {
            if (StringUtils.isNotBlank(palabra)) {
                for (Field field : atrString) {
                    queryString.append(" OR ");
                    queryString.append(filtroExpedienteDTO.getNombreEvidencia() + "." + field.getName()
                            + " like '%");
                    queryString.append(palabra).append("%'");
                }
            }
        }

        logger.debug("queryString :: " + queryString);

        Query query = super.getSession().createQuery(queryString.toString());

        return query.list();
    }

    @Override
    public String obtenerUltimoNumTipoExp(String nomExpediente) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT e.numeroExpediente ").append("FROM ")
                .append(nomExpediente).append(" e ")
                .append("WHERE e.expedienteId = (SELECT MAX(ex.expedienteId) ")
                .append("FROM ").append(nomExpediente).append(" ex)");
        Query query = super.getSession().createQuery(queryString.toString());
        return (String) query.uniqueResult();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<Expediente> consultarExpedientesPorActividadActual(
            Long actividad, Long estatusExp) {
        final StringBuffer queryString = new StringBuffer();

        queryString.append("FROM Expediente e LEFT JOIN e.actividads da ")
                .append("WHERE  da.actividadId like :actividad");
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("actividad", actividad);
        List resp = query.list();

        if (logger.isDebugEnabled()) {
//			logger.debug("resp.size() :: " + resp.size());
        }
        return resp;

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NumeroExpediente> consultarExpedientesActividadAreaAnio(
            FiltroExpedienteDTO filtroExpedienteDTO) {

        final StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT ne FROM NumeroExpediente ne ")
                .append("LEFT JOIN ne.expediente e ")
                .append("LEFT JOIN e.origen o ")
                .append(" WHERE 1 = 1");
        queryString.append(" AND ne.numeroExpedienteId IN(");

        queryString
                .append("SELECT ne.numeroExpedienteId FROM Expediente e ")
                .append("LEFT JOIN e.actividads ac LEFT JOIN e.numeroExpedientes ne ")
                .append("LEFT JOIN e.origen o WHERE ");

        //FILTRO DE LA JERARQUIA ORGANIZACIONAL
        if (filtroExpedienteDTO.getIdActividad().equals(Actividades.RECIBIR_CANALIZACION_JAR.getValorId())) {
            queryString.append("ne.jerarquiaOrganizacional = ");
            if (filtroExpedienteDTO.getestatusMenuCoorJAR() != null
                    && (filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.ASIGNADOS.getValorId())
                    || filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.PROPIOS.getValorId()))) {
                queryString.append(Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal() + " ");
            } else {
                queryString.append(" ac.documento.jerarquiaOrganizacional.jerarquiaOrganizacionalId ");
            }
        } else if (filtroExpedienteDTO.getIdActividad().equals(Actividades.ATENDER_CANALIZACION_UI.getValorId())) {
            queryString.append("ne.jerarquiaOrganizacional IN (")
                    //.append(filtroExpedienteDTO.getIdArea()).append(", ").append(Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal())
                    .append(filtroExpedienteDTO.getIdArea())
                    .append(")");
        } else if (filtroExpedienteDTO.getIdActividad().equals(Actividades.RECIBIR_CANALIZACION_UI.getValorId()) && filtroExpedienteDTO.getestatusMenuCoorJAR() != null && filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.PROPIOS_UI.getValorId())) {
            queryString.append("ne.jerarquiaOrganizacional IN (")
                    .append(Areas.UNIDAD_INVESTIGACION.ordinal())
                    .append(")");
        } else if (filtroExpedienteDTO.getIdActividad().equals(Actividades.RECIBIR_CANALIZACION_UI.getValorId())) {
            queryString.append("ne.jerarquiaOrganizacional IN (")
                    .append(filtroExpedienteDTO.getIdArea()).append(", ").append(Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal())
                    .append(")");
        } else {
            queryString.append("ne.jerarquiaOrganizacional=")
                    .append(filtroExpedienteDTO.getIdArea());
        }

						//FILTRO DEL TIPO DE ACTIVIDAD
        // Coordinador AMP
        if (filtroExpedienteDTO.getIdActividad().equals(Actividades.RECIBIR_CANALIZACION_UI.getValorId())
                && filtroExpedienteDTO.getIdArea().equals(new Long(Areas.ATENCION_TEMPRANA_PG_PENAL.ordinal()))) {
            // Filtrado de expedientes sin asignar
            if (filtroExpedienteDTO.getExpedientesAsignados() != null && filtroExpedienteDTO.getExpedientesAsignados() == true) {
                queryString.append(" AND ").append("ac.tipoActividad=").append(Actividades.ATENDER_CANALIZACION_UI.getValorId());
            } // Filtrado de expedientes asignados
            else {
                queryString.append(" AND ").append("ac.tipoActividad=").append(Actividades.RECIBIR_CANALIZACION_UI.getValorId());

                queryString.append(" AND ").append("e.expedienteId not in (")
                        .append("SELECT e FROM Expediente e ")
                        .append("LEFT JOIN e.actividads ac WHERE ")
                        .append(" ac.tipoActividad=").append(Actividades.ATENDER_CANALIZACION_UI.getValorId());
                if (filtroExpedienteDTO.getUsuario() != null
                        && filtroExpedienteDTO.getUsuario().getFuncionario() != null
                        && filtroExpedienteDTO.getUsuario().getFuncionario().getUnidadIEspecializada() != null
                        && filtroExpedienteDTO.getUsuario().getFuncionario().getUnidadIEspecializada().getCatUIEId() != null) {
                    queryString.append(" and e.catUIEspecializada.catUIEId=").append(filtroExpedienteDTO.getUsuario().getFuncionario().getUnidadIEspecializada().getCatUIEId());
                    queryString.append(" and  ac.funcionario.claveFuncionario =").append(filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario());
                }
                queryString.append(")");
            }
        } else {
            queryString.append(" AND ").append("ac.tipoActividad=")
                    .append(filtroExpedienteDTO.getIdActividad());
        }

        //FILTRO POR EL FUNCIONARIO RESPONSABLE 
        if (filtroExpedienteDTO.getestatusMenuCoorJAR() != null && filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.PROPIOS.getValorId())) {
            filtroExpedienteDTO.setIdFuncionario(filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario());
        }

        if (filtroExpedienteDTO.getestatusMenuCoorJAR() != null
                && filtroExpedienteDTO.getIdFuncionario() != null
                && filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.PROPIOS.getValorId())) {
            queryString.append(" AND ").append("ne.funcionario.claveFuncionario=").append(filtroExpedienteDTO.getIdFuncionario());
        } else if (filtroExpedienteDTO.getIdFuncionario() != null) {
            if (filtroExpedienteDTO.getIdArea() != null
                    && filtroExpedienteDTO.getIdArea() == Areas.COORDINACION_POLICIA_MINISTERIAL.ordinal()) {
                queryString.append(" AND ").append("ac.funcionario.claveFuncionario=").append(filtroExpedienteDTO.getIdFuncionario());
            } else {
                queryString.append(" AND ").append("ne.funcionario.claveFuncionario=").append(filtroExpedienteDTO.getIdFuncionario());
            }
        }

        //FILTRO POR EL DISCRIMINANTE
        if (filtroExpedienteDTO.getIdDiscriminante() != null && filtroExpedienteDTO.getIdDiscriminante() > 0) {
            queryString.append(" AND e.discriminante.catDiscriminanteId IN ( ").append(filtroExpedienteDTO.getIdDiscriminante());
            try {
                Funcionario f= funcionarioDAO.consultarFuncionarioXIdUsuario(filtroExpedienteDTO.getUsuario().getIdUsuario());
                for (CatDiscriminante cd:f.getFuncionarioAgencias()) {
                    queryString.append(" , " + cd.getCatDiscriminanteId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            queryString.append(" )");
        }

        //FILTRO POR EL ESTATUS
        if (filtroExpedienteDTO.getEstatus() != null && filtroExpedienteDTO.getEstatus() > 0) {
            queryString.append(" AND ne.estatus.valorId=").append(filtroExpedienteDTO.getEstatus());
        }
        if (filtroExpedienteDTO.getIdJerarquiaRemitos() != null && filtroExpedienteDTO.getIdJerarquiaRemitos() > 0) {
            queryString.append(" AND e.expedienteId in ("
                    + "SELECT e.expedienteId FROM Expediente e "
                    + "LEFT JOIN e.actividads ac "
                    + "LEFT JOIN ac.documento doc "
                    + "WHERE ac.tipoActividad=").append(Actividades.RECIBIR_CANALIZACION_JAR.getValorId())
                    .append(" and doc.jerarquiaOrganizacional in (").append(filtroExpedienteDTO.getIdJerarquiaRemitos()).append(" )) ");
        }
        queryString.append(" GROUP BY ne.numeroExpedienteId, ac.tipoActividad ");
        queryString.append(")");

        final PaginacionDTO pag = PaginacionThreadHolder.get();
        if (pag != null && pag.getSearchField() != null) {
            if (pag.getSearchField().equals("NumeroExpediente")) {
                pag.setSearchField("numeroExpediente");
                queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "ne", false, false));
            } else if (pag.getSearchField().equals("Fecha")) {
                pag.setSearchField("fechaCreacion");
                queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "e", false, true));
            } else if (pag.getSearchField().equals("Origen")) {
                pag.setSearchField("valor");
                queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "o", false, false));
            } else if (pag.getSearchField().equals("Estatus")) {
                if (pag.getSearchOper().equals("nu")) {
                    pag.setSearchField("estatus");
                    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "ne", false, false));
                } else {
                    pag.setSearchField("valor");
                    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "ne.estatus", false, false));
                }

            }
        }

        PaginacionDTO paguinaantigua = pag;
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("NumeroExpediente")) {
                queryString.append(" order by ");
                queryString.append("ne.numeroExpediente");
                queryString.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("Fecha")) {
                queryString.append(" order by ");
                queryString.append("e.fechaCreacion");
                queryString.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("Origen")) {
                queryString.append(" order by ");
                queryString.append("o.valor ");
                queryString.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("Estatus")) {
                queryString.append(" order by ");
                queryString.append("ne.estatus.valor");
                queryString.append(" ").append(pag.getDirOrd());
            }
        }
        logger.info("QUERY DEBUG----> "+ queryString.toString());

        if (filtroExpedienteDTO.getestatusMenuCoorJAR() != null
                && (filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.ASIGNADOS.getValorId())
                || filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.PORASIGNAR.getValorId())
                || filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.PROPIOS.getValorId()))) {

            List<NumeroExpediente> lista = super.ejecutarQueryPaginado(queryString, null);
            PaginacionThreadHolder.set(paguinaantigua);
            logger.info("LISTA1 "+lista.size());
            return lista;

        } else {
            logger.info("LISTA 2"+ super.ejecutarQueryPaginado(queryString, pag).size());
            return super.ejecutarQueryPaginado(queryString, pag);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NumeroExpediente> consultarExpedientesCanalizados(
            FiltroExpedienteDTO filtroExpedienteDTO) {

        final StringBuffer queryString = new StringBuffer();
        queryString
                .append("SELECT DISTINCT ne FROM Expediente e ")
                .append("LEFT JOIN e.actividads ac LEFT JOIN e.numeroExpedientes ne ")
                .append("LEFT JOIN e.origen o WHERE 1=1 ");

        if (filtroExpedienteDTO.getIdArea() != null) {
            queryString.append(" AND ne.jerarquiaOrganizacional=")
                    .append(filtroExpedienteDTO.getIdArea());
        }

        if (filtroExpedienteDTO.getIdActividad() != null) {
            queryString.append(" AND ac.tipoActividad=")
                    .append(filtroExpedienteDTO.getIdActividad());
        }

        if (filtroExpedienteDTO.getFechaCreacionInicio() != null
                && filtroExpedienteDTO.getFechaCreacionFin() != null) {

            queryString.append(" AND CONVERT (nvarchar, ac.fechaCreacion, 112) between ")
                    .append(DateUtils.formatearBD(filtroExpedienteDTO.getFechaCreacionInicio())).append(" AND ")
                    .append(DateUtils.formatearBD(filtroExpedienteDTO.getFechaCreacionFin()));

        }

        if (filtroExpedienteDTO.getNumeroExpediente() != null && !filtroExpedienteDTO.getNumeroExpediente().equals("")) {
            queryString.append(" AND ne.numeroExpediente='" + filtroExpedienteDTO.getNumeroExpediente() + "'");
        }

        final PaginacionDTO pag = PaginacionThreadHolder.get();

        if (pag != null && pag.getSearchField() != null) {
            if (pag.getSearchField().equals("NumeroExpediente")) {
                pag.setSearchField("numeroExpediente");
                queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "ne", false, false));
            } else if (pag.getSearchField().equals("Fecha")) {
                pag.setSearchField("fechaCreacion");
                queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "e", false, true));
            } else if (pag.getSearchField().equals("Origen")) {
                pag.setSearchField("valor");
                queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "o", false, false));
            } else if (pag.getSearchField().equals("Estatus")) {
                if (pag.getSearchOper().equals("nu")) {
                    pag.setSearchField("estatus");
                    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "ne", false, false));
                } else {
                    pag.setSearchField("valor");
                    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "ne.estatus", false, false));
                }
            } else if (pag.getSearchField().equals("UIE")) {
                if (pag.getSearchOper().equals("nu")) {
                    pag.setSearchField("catUIEspecializada");
                    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "e", false, false));
                } else {
                    pag.setSearchField("nombreUIE");
                    queryString.append(PaginadorUtil.obtenerBusquedaPaginacionEnDAO(pag, "e.catUIEspecializada", false, false));
                }

            }
        }

        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("NumeroExpediente")) {
                queryString.append(" order by ");
                queryString.append("ne.numeroExpediente");
                queryString.append(" ").append(pag.getDirOrd());
            }
        }
        return super.ejecutarQueryPaginado(queryString, pag);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NumeroExpediente> consultarExpedientesPorEtapa(
            EtapasExpediente etapa, Long cveFuncionario, Long areaId) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("FROM NumeroExpediente ne WHERE ")
                .append("ne.funcionario=").append(cveFuncionario)
                .append(" AND ne.etapa=").append(etapa.getValorId());
        if (areaId != null) {
            queryStr.append(" AND ne.jerarquiaOrganizacional=")
                    .append(areaId);
        }
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            queryStr.append(" ORDER BY ");
            int orden = NumberUtils.toInt(pag.getCampoOrd(), 0);
            switch (orden) {
                case 2002: //Numero General de Caso
                    queryStr.append(" ne.expediente.caso.numeroGeneralCaso");
                    break;
                case 2003: // Expediente
                    queryStr.append(" ne.numeroExpediente");
                    break;
                // FIXME Imputado poner la ruta de el imputado case 2009:
                case 2040:
                    queryStr.append(" ne.etapa");
                    break;
                case 2011:
                    queryStr.append(" ne.estatus");
                    break;

		    	// Detenido poner la ruta de el detenido case 2005:
                // fecha hora limite de atencion Detenido poner la ruta de fecha limite atencion case 2038:
                // fecha hora de designacion Detenido poner la ruta de fecha de designacion case 2039:
                // TipoAudiencia case 2017:
                // Sala case 2029:
                // Fecha audiencia case 2018:
                default:
                    queryStr.append(" ne");
                    break;
            }
            queryStr.append(" " + pag.getDirOrd());
        }

        return super.ejecutarQueryPaginado(queryStr, pag);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NumeroExpediente> consultarExpedientesPorEtapaDefensoria(
            Long etapaValorId, Long cveFuncionario, Long areaId) {

        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("FROM NumeroExpediente ne WHERE ")
                .append("ne.funcionario=").append(cveFuncionario)
                .append(" AND ne.catEtapa.catEtapaValor=").append(etapaValorId);
        if (areaId != null) {
            queryStr.append(" AND ne.jerarquiaOrganizacional=")
                    .append(areaId);
        }

        //TODO GBP PAGINACION DE GRID
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            queryStr.append(" ORDER BY ");
            int orden = NumberUtils.toInt(pag.getCampoOrd(), 0);
            switch (orden) {
                case 2002: //Numero General de Caso
                    queryStr.append(" ne.expediente.caso.numeroGeneralCaso");
                    break;
                case 2003: // Expediente
                    queryStr.append(" ne.numeroExpediente");
                    break;
                // FIXME Imputado poner la ruta de el imputado case 2009:
                case 2040:
                    queryStr.append(" ne.etapa");
                    break;
                case 2011:
                    queryStr.append(" ne.estatus");
                    break;

		    	// Detenido poner la ruta de el detenido case 2005:
                // fecha hora limite de atencion Detenido poner la ruta de fecha limite atencion case 2038:
                // fecha hora de designacion Detenido poner la ruta de fecha de designacion case 2039:
                // TipoAudiencia case 2017:
                // Sala case 2029:
                // Fecha audiencia case 2018:
                default:
                    queryStr.append(" ne");
                    break;
            }
            queryStr.append(" " + pag.getDirOrd());
        }

        return super.ejecutarQueryPaginado(queryStr, pag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expediente consultarDetalleExpediente(Expediente expediente,
            Usuario usuario) {
        Expediente detalleExpediente = new Expediente();
        Expediente consultado = read(expediente.getExpedienteId());
        detalleExpediente.setExpedienteId(consultado.getExpedienteId());
        detalleExpediente.setCaso(consultado.getCaso());
        Usuario usuarioCompleto = usuarioDao.read(usuario.getUsuarioId());
        NumeroExpediente numeroExpediente = numeroExpedienteDao
                .obtenerNumeroExpediente(consultado.getExpedienteId(),
                        usuarioCompleto.getFuncionario().getArea()
                        .getJerarquiaOrganizacionalId());
        detalleExpediente.setNumeroExpediente(numeroExpediente
                .getNumeroExpediente());
        // consultamos los involucrados de este expediente
        List<Involucrado> involucrados = involucradoDao
                .consultarInvolucradosByExpediente(expediente.getExpedienteId());
        Involucrado imputado = null;
        for (Involucrado involucrado : involucrados) {
            long valorId = involucrado.getCalidad().getTipoCalidad()
                    .getValorId();
            /*
             * verificamos si alguno de los involucrados es un probable
             * responsable persona u organizacion.
             */
            if (valorId == Calidades.PROBABLE_RESPONSABLE_PERSONA.getValorId()
                    || valorId == Calidades.PROBABLE_RESPONSABLE_ORGANIZACION
                    .getValorId()) {
                imputado = involucrado;
                break;
            }
        }
        if (imputado == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No pudimos encontrar el imputado para el "
                        + "expediente = " + expediente);
                logger.debug("Los involucrados del expediente son: "
                        + involucrados.size());
                for (Involucrado involucrado : involucrados) {
                    logger.debug("involucrado.getElementoId(): "
                            + involucrado.getElementoId());
                }
            }
        }
        detalleExpediente.getElementos().add(imputado);
		// Consultamos los delitos del imputado
        //FIXME  Porque se regresa solo los delitos del primer imputado
        if (imputado != null) {
            List<DelitoPersona> delitosImputado = delitoPersonaDao
                    .consultarDelitosPorImputado(imputado.getElementoId(),
                            expediente.getExpedienteId());
            Set<Delito> delitosExpediente = detalleExpediente.getDelitos();
            for (DelitoPersona delitoPersona : delitosImputado) {
                delitosExpediente.add(delitoPersona.getDelito());
            }
        }
        return detalleExpediente;
    }

    @Override
    public Long consultarExpedientePorNumeroExpediente(String numExp) {

        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT e.numeroExpedienteId ")
                .append(" FROM NumeroExpediente e  ")
                .append(" WHERE e.numeroExpediente like '%").append(numExp)
                .append("%'");
        Query query = super.getSession().createQuery(queryString.toString());
        if (query.list().isEmpty()) {
            return null;
        }
        return (Long) query.list().get(0);
    }

    @Override
    public Long obtenerIdNumExpedientePorNumeroExpedienteYNumeroCaso(String numExp, String numCaso) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT e.numeroExpedienteId ")
                .append(" FROM NumeroExpediente e  ")
                .append(" WHERE e.numeroExpediente like '%").append(numExp)
                .append("%'");
        if (numCaso != null && !numCaso.equals("")) {
            queryString.append(" AND e.expediente.caso.numeroGeneralCaso like '%").append(numCaso).append("%'");
        }

        Query query = super.getSession().createQuery(queryString.toString());
        if (query.list().isEmpty()) {
            return null;
        }
        return (Long) query.list().get(0);
    }

    @Override
    public Long consultarExpedienteIdPorNumeroExpediente(String numExp) {

        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT e.expediente.expedienteId ")
                .append(" FROM NumeroExpediente e  ")
                .append(" WHERE e.numeroExpediente like '%").append(numExp)
                .append("%'");
        Query query = super.getSession().createQuery(queryString.toString());
        return (Long) query.uniqueResult();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Expediente> consultarExpedientesPorIdCasoConfInstitucion(Long idCaso,
            Long idConfInstitucion) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new Expediente(v.expedienteId, n.numeroExpediente, n.numeroExpedienteId)");
        queryStr.append(" from Expediente v ");
        queryStr.append(" left join v.numeroExpedientes n");
        queryStr.append(" where v.caso.casoId = ");
        queryStr.append(idCaso);
        if (idConfInstitucion != null) {
            queryStr.append(" and v.pertenceConfInst.confInstitucionId = ");
            queryStr.append(idConfInstitucion);
        }
        Query query = super.getSession().createQuery(queryStr.toString());
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public List<Expediente> consultarCausasPorIdCaso(Long idCaso) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append("select new Expediente(v.expedienteId, n.numeroExpediente, n.numeroExpedienteId) ");
        queryStr.append(" from Expediente v left join v.numeroExpedientes n");
        queryStr.append(" where n.tipoExpediente.valorId = ");
        queryStr.append(TipoExpediente.CAUSA.getValorId());
        queryStr.append(" AND v.caso.casoId = ");
        queryStr.append(idCaso);
        Query query = super.getSession().createQuery(queryStr.toString());
        return query.list();
    }

    @Override
    public Expediente buscarExpedientePorCasoCalidadNombreImputado(String numeroGeneralCaso,
            String nombre, String aPaterno, String aMaterno, Long idCalidad) throws NSJPNegocioException {
        StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append("SELECT i.expediente");
        hqlQuery.append(" FROM Involucrado i LEFT JOIN i.nombreDemograficos n");
        hqlQuery.append(" WHERE i.calidad.tipoCalidad.valorId = " + idCalidad);
        hqlQuery.append(" AND i.expediente.caso.numeroGeneralCaso= '" + numeroGeneralCaso + "'");
        hqlQuery.append(" AND n.nombre = '" + nombre + "'");
        hqlQuery.append(" AND n.apellidoPaterno = '" + aPaterno + "'");
        hqlQuery.append(" AND n.apellidoMaterno = '" + aMaterno + "'");
        Query query = super.getSession().createQuery(hqlQuery.toString());
        return (Expediente) query.uniqueResult();
    }

    @Override
    public Expediente buscarExpedientePorCasoFolioInvolucrado(String numeroGeneralCaso, String folioInvolucrado) throws NSJPNegocioException {
        StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append("SELECT i.expediente");
        hqlQuery.append(" FROM Involucrado i ");
        hqlQuery.append(" WHERE i.folioElemento = '" + folioInvolucrado + "'");
        hqlQuery.append(" AND i.expediente.caso.numeroGeneralCaso= '" + numeroGeneralCaso + "'");
        Query query = super.getSession().createQuery(hqlQuery.toString());
        return (Expediente) query.uniqueResult();
    }

    @Override
    public Expediente buscarExpedientePorCasoImputado(String numeroCaso,
            String imputado, Long valorId) {
        StringBuffer hqlQuery = new StringBuffer();
        hqlQuery.append("SELECT i.expediente");
        hqlQuery.append(" FROM Involucrado i LEFT JOIN i.nombreDemograficos n");
        hqlQuery.append(" AND i.calidad.tipoCalidad.valorId = " + valorId);
        hqlQuery.append(" AND i.expediente.caso.numeroGeneralCaso= '" + numeroCaso + "'");
        hqlQuery.append(" AND n.nombre+' '+ n.apellidoPaterno");
        hqlQuery.append("+' '+n.apellidoMaterno like '" + imputado + "'");
        Query query = super.getSession().createQuery(hqlQuery.toString());
        return (Expediente) query.uniqueResult();
    }

    @Override
    public Expediente consultarExpedientePorIdNumerExpediente(
            Long idNumeroExpediente) throws NSJPNegocioException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT numero.expediente ")
                .append(" FROM NumeroExpediente numero  ")
                .append(" WHERE numero.numeroExpedienteId = " + idNumeroExpediente);
        logger.debug("queryString :: " + queryString);
        Query query = super.getSession().createQuery(queryString.toString());
        return (Expediente) query.uniqueResult();
    }

    @Override
    public Long obtenerExpedienteIdPorIdNumerExpediente(
            Long idNumeroExpediente) throws NSJPNegocioException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT numero.expediente.expedienteId ")
                .append(" FROM NumeroExpediente numero  ")
                .append(" WHERE numero.numeroExpedienteId = " + idNumeroExpediente);
        Query query = super.getSession().createQuery(queryString.toString());
        return (Long) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> obtenerExpedientesPorMes(Date fechaIni, Date fechaFin) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT MONTH (e.fechaCreacion), COUNT(*) FROM Expediente e ")
                .append(" WHERE CONVERT (varchar, e.fechaCreacion, 112) BETWEEN ").append(DateUtils.formatearBD(fechaIni))
                .append(" AND ").append(DateUtils.formatearBD(fechaFin))
                .append(" GROUP BY MONTH (e.fechaCreacion)");
        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Expediente> consultarExpedientesPorNumeroCaso(String numeroExpediente) {
        final StringBuffer queryStr = new StringBuffer();
        queryStr.append(" from Expediente e");
        queryStr.append(" where e.caso.numeroGeneralCaso like '%");
        queryStr.append(numeroExpediente);
        queryStr.append("%'");
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("caso")) {
                queryStr.append(" order by ");
                queryStr.append("e.caso.numeroGeneralCaso");
                queryStr.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("fecha")) {
                queryStr.append(" order by ");
                queryStr.append("e.caso.fechaApertura");
                queryStr.append(" ").append(pag.getDirOrd());
            }
        }
        return super.ejecutarQueryPaginado(queryStr, pag);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String obtenerUltimoNumeroDeExpediente(Long area) {
        final StringBuffer queryStr = new StringBuffer();
        //Se obtienen los ultimos cinco digitos del maximo en la cadena de los numero des expediente
        queryStr.append(" select MAX(substring(obj.numeroExpediente,len(obj.numeroExpediente)-4,5)) from NumeroExpediente obj where 1=1 ");
        if (area != null) {
            queryStr.append(" AND obj.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
            queryStr.append(area);
        }
        logger.debug("queryStr :: " + queryStr);
        Query qry = super.getSession().createQuery(queryStr.toString());
        String maximoDeLosConsecutivos = (String) qry.uniqueResult();

        //Se prepara la segunda consulta la cual permite obtener el numero de Expdiente id
        queryStr.delete(0, queryStr.length());

        queryStr.append(" select obj.numeroExpedienteId from NumeroExpediente obj where 1= 1 ");
        if (area != null) {
            queryStr.append(" AND obj.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
            queryStr.append(area);
        }
        queryStr.append(" and obj.numeroExpediente like '%" + maximoDeLosConsecutivos + "%'");
        logger.debug("queryStr :: " + queryStr);
        qry = super.getSession().createQuery(queryStr.toString());

        List<Long> idsNumerosExpediente = (List<Long>) qry.list();
        Long idNumeroExpediente = null;
        if (idsNumerosExpediente != null && idsNumerosExpediente.size() > 0) {
            idNumeroExpediente = idsNumerosExpediente.get(0);
        }

        //Se prepara la segunda consulta la cual permite obtener el numero de Expdiente id	
        queryStr.delete(0, queryStr.length());

        queryStr.append("select e.numeroExpediente ");
        queryStr.append(" from NumeroExpediente e where e.numeroExpedienteId =" + idNumeroExpediente);
        if (area != null) {
            queryStr.append(" and e.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ");
            queryStr.append(area);
        }

        logger.debug("queryStr :: " + queryStr);
        qry = super.getSession().createQuery(queryStr.toString());
        return (String) qry.uniqueResult();
    }

    @Override
    public String obtenerUltimoNumeroDeExpedientePoderJudicial(
            String cadenaBusqueda, Character separador) {

        if (cadenaBusqueda == null || cadenaBusqueda.trim().isEmpty()
                || separador == null) {
            return null;
        }

        final StringBuffer queryStr = new StringBuffer();

		// Se obtienen los primeros digitos de la cadena de los numero de
        // expediente considerando el separador Proporcionado
        queryStr.append(
                " SELECT MAX (SUBSTRING(ne.cNumeroExpediente,0, CHARINDEX('")
                .append(separador)
                .append("', ne.cNumeroExpediente)) )  FROM NumeroExpediente ne");

		// Al final se considera la cadena delimitadora, como filtro en la
        // busqueda de numero de expediente similar
        queryStr.append(" WHERE SUBSTRING(ne.cNumeroExpediente, CHARINDEX('")
                .append(separador)
                .append("', ne.cNumeroExpediente)+1, LEN(ne.cNumeroExpediente)) = '")
                .append(cadenaBusqueda).append("'");

        Query qry = super.getSession().createSQLQuery(queryStr.toString());
        logger.debug("queryStr :: " + queryStr);
        return (String) qry.uniqueResult();
    }

    @Override
    public JerarquiaOrganizacional consultarOrigendeExpediente(Long expedienteId) {
        final StringBuffer queryStr = new StringBuffer();

        queryStr.append(" SELECT ne.jerarquiaOrganizacional");
        queryStr.append(" FROM NumeroExpediente ne");
        queryStr.append(" WHERE ne.expediente = " + expedienteId);
        queryStr.append(" ORDER BY ne.fechaApertura");

        Query qry = super.getSession().createQuery(queryStr.toString());
        return (JerarquiaOrganizacional) qry.list().get(0);
    }

    @Override
    public List<Expediente> consultarExpedientesPorIdCaso(CasoDTO caso) {

        StringBuffer queryStr = new StringBuffer();
        queryStr.append("SELECT DISTINCT new Expediente( e.expedienteId, n.numeroExpediente, n.numeroExpedienteId, e.caso)");
        queryStr.append("from Expediente e ,NumeroExpediente n");
        if (caso.getFiltroExpedienteDTO() != null
                && caso.getFiltroExpedienteDTO().getUsuario() != null
                && caso.getFiltroExpedienteDTO().getUsuario().getFuncionario() != null
                && caso.getFiltroExpedienteDTO().getUsuario().getFuncionario().getDiscriminante() != null) {
            FiltroExpedienteDTO filtroExpedienteDTO = caso.getFiltroExpedienteDTO();
            queryStr.append(" where e.caso.casoId = ");
            queryStr.append(caso.getCasoId());
            queryStr.append(" AND e.discriminante.catDiscriminanteId = ");
            queryStr.append(filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getCatDiscriminanteId());
            if (filtroExpedienteDTO.getIdFuncionario() != null) {
                queryStr.append(" AND e.expedienteId = n.expediente.expedienteId");
                queryStr.append(" AND n.funcionario.claveFuncionario = ");
                queryStr.append(filtroExpedienteDTO.getIdFuncionario());
            }
        } else {
            queryStr.append(" where e.caso.casoId = ");
            queryStr.append(caso.getCasoId());
            if (caso.getFiltroExpedienteDTO().getIdFuncionario() != null) {
                queryStr.append(" AND e.expedienteId = n.expediente.expedienteId");
                queryStr.append(" AND n.funcionario.claveFuncionario = ");
                queryStr.append(caso.getFiltroExpedienteDTO().getIdFuncionario());
            }
        }
        @SuppressWarnings("unchecked")
        List<Expediente> resp = super.getHibernateTemplate().find(
                queryStr.toString());
        if (logger.isDebugEnabled()) {
            logger.debug("resp.size() :: " + resp.size());
        }
        return resp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Expediente> buscadorDeExpedientes(FiltroExpedienteDTO filtroExpedienteDTO) {
        String numeroExpediente = filtroExpedienteDTO.getNumeroExpediente();
        Long areaId = 0L;
        if (filtroExpedienteDTO != null && filtroExpedienteDTO.getUsuario() != null && filtroExpedienteDTO.getUsuario().getAreaActual() != null
                && filtroExpedienteDTO.getUsuario().getAreaActual().getAreaId() != null
                && filtroExpedienteDTO.getUsuario().getAreaActual().getAreaId() > 0L) {
            areaId = filtroExpedienteDTO.getUsuario().getAreaActual().getAreaId();
        }
        if (areaId.equals(Areas.COORDINACION_UEI_GENERAL.parseLong())) {
            areaId = Areas.UNIDAD_INVESTIGACION.parseLong();
        }
        Long discriminanteId = 0L;
        if (filtroExpedienteDTO.getEsCordinadorGeneralAMP() != null && !filtroExpedienteDTO.getEsCordinadorGeneralAMP().equals(1L)) {
            discriminanteId = filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getCatDiscriminanteId();
        }
        Long idFuncionario = filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario();
        Long idActividad = filtroExpedienteDTO.getIdActividad();
        boolean existeActividad = true;
        if (logger.isDebugEnabled()) {
            logger.debug("numeroExpediente :: [" + numeroExpediente
                    + "] y areaId :: " + areaId);
        }
        logger.info("numeroExpediente :: [" + numeroExpediente
                + "] y areaId :: " + areaId);
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        final StringBuffer queryStr = new StringBuffer();

        queryStr.append(" SELECT DISTINCT ");
        queryStr.append(" new Expediente( ");
        queryStr.append("e.expedienteId, ");
        queryStr.append("n.numeroExpediente, ");
        queryStr.append("n.numeroExpedienteId, ");
        queryStr.append("e.discriminante, ");
        queryStr.append("c");
        if (filtroExpedienteDTO.getEstatusNumeroExpediente() != null
                && !filtroExpedienteDTO.getEstatusNumeroExpediente().isEmpty()) {
            queryStr.append(", n.estatus");
        }
        queryStr.append(")");

        queryStr.append(" FROM Expediente e ");
        queryStr.append(" LEFT JOIN e.numeroExpedientes n ");
        queryStr.append(" LEFT JOIN e.caso c ");
        queryStr.append(" LEFT JOIN e.actividads ac ");

        queryStr.append(" WHERE n.numeroExpediente like '%");
        queryStr.append(numeroExpediente.trim());
        queryStr.append("%'");
        if (filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual() != null
                && filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual().equals(Areas.UNIDAD_INVESTIGACION.parseLong())) {
            queryStr.append(" and ac.tipoActividad.valorId = " + Actividades.RECIBIR_CANALIZACION_UI.getValorId());
        } else {
            if (idActividad != null && !idActividad.equals(0L)) {
                queryStr.append(" and ac.tipoActividad.valorId = " + idActividad);
                existeActividad = false;
            }
        }

        if (filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual() != null && filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual().equals(Areas.UNIDAD_INVESTIGACION.parseLong())) {
            queryStr.append(" and n.jerarquiaOrganizacional.jerarquiaOrganizacionalId IN ( ");
            queryStr.append(Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong() + " , ");
            queryStr.append(Areas.UNIDAD_INVESTIGACION.parseLong() + " , ");
            queryStr.append(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong() + "  ");
            queryStr.append(" ) ");
        } else {
            if (areaId != null && !areaId.equals(0L) && existeActividad) {
                queryStr.append(" and n.jerarquiaOrganizacional.jerarquiaOrganizacionalId IN ( ");
                if (filtroExpedienteDTO.getJerarquiaOrgSubordinadas() != null
                        && !filtroExpedienteDTO.getJerarquiaOrgSubordinadas().isEmpty()) {

                    for (JerarquiaOrganizacionalDTO jerarquiaOrganizacional : filtroExpedienteDTO.getJerarquiaOrgSubordinadas()) {
                        queryStr.append(jerarquiaOrganizacional.getJerarquiaOrganizacionalId() + ", ");
                    }
                }
                queryStr.append(areaId);
                queryStr.append(" ) ");
            }
        }
        if (discriminanteId != null && !discriminanteId.equals(0L) && filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual() == null) {
            queryStr.append(" AND n.expediente.discriminante.catDiscriminanteId = ").append(discriminanteId);
			// si se busca por jerarqu�as hijas, se considera que el discriminante del funcionario debe
            // se el mismo que el discriminante del expediente
            if (filtroExpedienteDTO.getUsuario().getAreaActual() != null
                    && filtroExpedienteDTO.getUsuario().getAreaActual()
                    .getBuscarEnJerarquia() != null
                    && filtroExpedienteDTO.getUsuario().getAreaActual()
                    .getBuscarEnJerarquia()) {
                queryStr.append(" AND n.funcionario.discriminante.catDiscriminanteId = ").append(discriminanteId);
            }
        }
        if (idFuncionario != null && !idFuncionario.equals(0L) && existeActividad && filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual() == null) {
            queryStr.append(" and n.numeroExpediente.funcionario.claveFuncionario = ").append(idFuncionario);
        }

        if (filtroExpedienteDTO.getEstatusNumeroExpediente() != null
                && !filtroExpedienteDTO.getEstatusNumeroExpediente().isEmpty()
                && filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual() == null) {
            queryStr.append(" and n.numeroExpediente.estatus IN ( ");
            for (int i = 0; i < filtroExpedienteDTO.getEstatusNumeroExpediente().size(); i++) {
                Long estatus = filtroExpedienteDTO.getEstatusNumeroExpediente().get(i);
                queryStr.append(estatus);
                if (i < filtroExpedienteDTO.getEstatusNumeroExpediente().size() - 1) {
                    queryStr.append(", ");
                }
            }
            queryStr.append(" ) ");
        }
        if (filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual() != null && filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual().equals(Areas.UNIDAD_INVESTIGACION.parseLong())) {
            queryStr.append(" ORDER BY e.expedienteId ASC ");
        } else {
            queryStr.append(" ORDER BY n.numeroExpediente ASC ");
        }

        if (filtroExpedienteDTO.getEstatusNumeroExpediente() != null
                && !filtroExpedienteDTO.getEstatusNumeroExpediente().isEmpty() && filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual() == null) {
            queryStr.append(" , n.estatus ASC ");
        }

        if (filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual() != null && filtroExpedienteDTO.getFiltroEspecificoDeAreaRolActual().equals(Areas.UNIDAD_INVESTIGACION.parseLong())) {
            List<Expediente> lista = super.ejecutarQueryPaginado(queryStr, null);
            PaginacionThreadHolder.set(pag);
            return lista;

        } else {
            return super.ejecutarQueryPaginado(queryStr, pag);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> consultarIndicador(Indicadores indicador, HashMap<String, String> valores) {
        logger.info(" consultarIndicador: " + indicador);
        logger.info(" consultarIndicador - Parametros: " + indicador.getParametros());
        logger.info(" consultarIndicador - NombreColumnas: " + indicador.getNombreColumnas());
        logger.info(" consultarIndicador - Valores: " + valores);

        final StringBuffer queryStr = new StringBuffer(indicador.getConsulta());
        Query query = super.getSession().createSQLQuery(queryStr.toString());

        Iterator<Entry<String, String>> iterador = valores.entrySet().iterator();
        while (iterador.hasNext()) {
            Map.Entry<String, String> dato = (Entry<String, String>) iterador.next();
            query.setParameter(dato.getKey(), dato.getValue());
        }
        logger.info(" QUERYS:" + query.getQueryString());

        return query.list();
    }

    @Override
    public List<Expediente> consultarExpedientesPorActividadActualyExpedienteID(
            Long actividad, Long expedienteId) {
        final StringBuffer queryString = new StringBuffer();

        queryString.append("FROM Expediente e LEFT JOIN e.actividads da ")
                .append("WHERE  da.tipoActividad.valorId = :actividad")
                .append(" AND  e.expedienteId = :expedienteId");
        logger.info("QUERY CONSULTAR ACTIVIDADaCTUALExpediente---->" + queryString.toString());
        Query query = super.getSession().createQuery(queryString.toString());
        query.setParameter("actividad", actividad);
        query.setParameter("expedienteId", expedienteId);
        List resp = query.list();

        if (logger.isDebugEnabled()) {
//			logger.debug("resp.size() :: " + resp.size());
        }
        return resp;

    }

    @Override
    public List<Expediente> consultaExpedientesDoorAT(
            FiltroExpedienteDTO filtroExpedienteDTO) {
        List<Expediente> listExpedientes = new ArrayList<Expediente>();
        try {
            final PaginacionDTO pag = PaginacionThreadHolder.get();
            pag.setCampoOrd("fecha");
            final StringBuffer queryStr = new StringBuffer();

            queryStr.append(" SELECT new Expediente(e.expedienteId, n.numeroExpediente, n.numeroExpedienteId, c,e.fechaCreacion,v");
            queryStr.append(") ");
            queryStr.append(" FROM Expediente e LEFT JOIN e.origen v, NumeroExpediente n, Caso c");
            queryStr.append(" ,Turno t");

            queryStr.append(" WHERE e.expedienteId=n.expediente.expedienteId");
            queryStr.append(" AND e.caso.casoId=c.casoId");

            if (filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_DIA.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_TODOS.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_AGENCIA.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_USUARIO.getId())) {
                queryStr.append(" AND t.tipoTurno='" + TipoTurno.PENAL.name() + "'");
                queryStr.append(" AND n.jerarquiaOrganizacional.jerarquiaOrganizacionalId=" + Areas.ATENCION_TEMPRANA_PG_PENAL.parseLong());
            }

            if (filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_DIA.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_TODOS.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_AGENCIA.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_USUARIO.getId())) {
                queryStr.append(" AND t.tipoTurno='" + TipoTurno.ADMINISTRATIVO.name() + "'");
                queryStr.append(" AND n.jerarquiaOrganizacional.jerarquiaOrganizacionalId=" + Areas.ATENCION_TEMPRANA_PG_NO_PENAL.parseLong());
            }

            if (filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_DIA.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_DIA.getId())) {
                queryStr.append(" AND e.expedienteId=t.expediente.expedienteId");
                if (filtroExpedienteDTO.getFechaBusqueda() != null) {
                    final SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
                    String fecha = formato.format(filtroExpedienteDTO.getFechaBusqueda());
                    queryStr.append(" AND CONVERT (nvarchar, t.fechaAtencion, 112)=" + fecha);
                    queryStr.append(" AND t.expediente.expedienteId is not null");
                }

            }
            if (filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_TODOS.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_AGENCIA.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_USUARIO.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_TODOS.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_AGENCIA.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_USUARIO.getId())) {
                queryStr.append(" AND e.expedienteId=t.expediente.expedienteId");
                queryStr.append(" AND t.expediente.expedienteId is not null");
            }

            if (filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_AGENCIA.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_AGENCIA.getId())) {
                queryStr.append(" AND e.discriminante.catDiscriminanteId=" + filtroExpedienteDTO.getIdAgencia());
            }

            if (filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_ATP_USUARIO.getId())
                    || filtroExpedienteDTO.getTipoBusquedaCoorAT().equals(TipoBusquedaCoordinadorAT.EXPEDIENTES_AT_USUARIO.getId())) {
                queryStr.append(" AND n.funcionario.claveFuncionario=" + filtroExpedienteDTO.getIdFuncionario());
            }

            if (pag != null && pag.getCampoOrd() != null) {
                if (pag.getCampoOrd().equals("fecha")) {
                    queryStr.append(" order by ");
                    queryStr.append("e.fechaCreacion");
                    queryStr.append(" ").append(pag.getDirOrd());
                }
            }
            listExpedientes = super.ejecutarQueryPaginado(queryStr, pag);
        } catch (Exception e) {
            logger.info(e);
        }
        return listExpedientes;
    }

    @SuppressWarnings("unchecked")
    public List<NumeroExpediente> consultarExpedientesCanalizadosNoAtendidos(
            FiltroExpedienteDTO filtroExpedienteDTO) {

        final StringBuffer queryString = new StringBuffer();
        queryString
                .append("SELECT DISTINCT ne FROM Expediente e ")
                .append("LEFT JOIN e.actividads ac LEFT JOIN e.numeroExpedientes ne ")
                .append("LEFT JOIN e.origen o WHERE 1=1 ");

        if (filtroExpedienteDTO.getJerarquiaOrgSubordinadas() != null && !filtroExpedienteDTO.getJerarquiaOrgSubordinadas().isEmpty()) {
            queryString.append(" and ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId IN ( ");

            for (JerarquiaOrganizacionalDTO jerarquiaOrganizacional : filtroExpedienteDTO.getJerarquiaOrgSubordinadas()) {
                queryString.append(jerarquiaOrganizacional.getJerarquiaOrganizacionalId() + ", ");
            }

            queryString.append(0L);
            queryString.append(" ) ");
        }

        if (filtroExpedienteDTO.getIdActividad() != null) {
            queryString.append(" AND ac.tipoActividad=")
                    .append(filtroExpedienteDTO.getIdActividad());
        }

        queryString.append(" AND ne.numeroExpedienteId NOT IN(");
        queryString
                .append("SELECT DISTINCT ne.numeroExpedienteId FROM Expediente e ")
                .append("LEFT JOIN e.actividads ac LEFT JOIN e.numeroExpedientes ne ")
                .append("LEFT JOIN e.origen o WHERE 1=1 ");

        if (filtroExpedienteDTO.getIdActividad() != null) {
            queryString.append(" AND ac.tipoActividad IN(")
                    .append(filtroExpedienteDTO.getIdTipoActividadComplemento());
            queryString.append(")");
        };
        queryString.append(")");

        final PaginacionDTO pag = PaginacionThreadHolder.get();

        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("NumeroExpediente")) {
                queryString.append(" order by ");
                queryString.append("ne.numeroExpediente");
                queryString.append(" ").append(pag.getDirOrd());
            }
        }

        return super.ejecutarQueryPaginado(queryString, pag);

    }

    /* (non-Javadoc)
     * @see mx.gob.segob.nsjp.dao.expediente.ExpedienteDAO#consultarExpedientesRSPorNumeroExpediente(mx.gob.segob.nsjp.dto.expediente.FiltroExpedienteDTO)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Expediente> consultarExpedientesRSPorNumeroExpediente(
            FiltroExpedienteDTO filtro) throws NSJPNegocioException {
        final StringBuffer queryStr = new StringBuffer();
        final PaginacionDTO pag = PaginacionThreadHolder.get();

        queryStr.append(" SELECT DISTINCT ");
        queryStr.append(" new Expediente( ");
        queryStr.append("e.expedienteId, ");
        queryStr.append("n.numeroExpediente, ");
        queryStr.append("n.numeroExpedienteId, ");
        queryStr.append("d, ");
        queryStr.append("c, n.estatus) ");

        queryStr.append(" FROM Expediente e ");
        queryStr.append(" LEFT JOIN e.numeroExpedientes n ");
        queryStr.append(" LEFT JOIN e.caso c ");
        queryStr.append(" LEFT JOIN e.discriminante d ");
        queryStr.append(" WHERE n.numeroExpediente like '%");
        queryStr.append(filtro.getNumeroExpediente().trim());
        queryStr.append("%'");
        queryStr.append(" AND n.jerarquiaOrganizacional.jerarquiaOrganizacionalId IN(");
        if (filtro.getJerarquiaOrgSubordinadas() != null
                && !filtro.getJerarquiaOrgSubordinadas().isEmpty()) {
            for (JerarquiaOrganizacionalDTO jerarquiaOrganizacional : filtro.getJerarquiaOrgSubordinadas()) {
                queryStr.append(jerarquiaOrganizacional.getJerarquiaOrganizacionalId() + ", ");
            }
        }
        queryStr.append(filtro.getUsuario().getAreaActual().getAreaId());
        queryStr.append(" ) ");
        queryStr.append(" AND n.estatus.valorId IN ( ");
        for (int i = 0; i < filtro.getEstatusNumeroExpediente().size(); i++) {
            Long estatus = filtro.getEstatusNumeroExpediente().get(i);
            queryStr.append(estatus);
            if (i < filtro.getEstatusNumeroExpediente().size() - 1) {
                queryStr.append(", ");
            }
        }
        queryStr.append(" ) ");
        queryStr.append(" ORDER BY n.numeroExpediente ASC, ");
        queryStr.append(" n.estatus.valorId ASC ");

        return (List<Expediente>) super.ejecutarQueryPaginado(queryStr, pag);
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> consultarExpedientesConSP(TipoDeBusquedaDeExpediente tipoBusqueda, HashMap<String, String> valores) {
        logger.info(" consultarExpedientesConSP: " + tipoBusqueda);
        logger.info(" consultarExpedientesConSP - Parametros: " + tipoBusqueda.getParametros());
        logger.info(" consultarExpedientesConSP - Valores: " + valores);

        final StringBuffer queryStr = new StringBuffer(tipoBusqueda.getConsulta());
        Query query = super.getSession().createSQLQuery(queryStr.toString());

        Iterator<Entry<String, String>> iterador = valores.entrySet().iterator();
        while (iterador.hasNext()) {
            Map.Entry<String, String> dato = (Entry<String, String>) iterador.next();
            query.setParameter(dato.getKey(), dato.getValue());
        }
        logger.info(" QUERYS:" + query.getQueryString());

        return query.list();
    }

    @Override
    public Expediente consultarExpedientePorNumeroDeCaso(
            CasoDTO casoDto, UsuarioDTO usuarioDto) throws NSJPNegocioException {

        if (casoDto == null
                || casoDto.getNumeroGeneralCaso() == null
                || casoDto.getNumeroGeneralCaso().trim().isEmpty()
                || usuarioDto == null
                || usuarioDto.getFuncionario() == null
                || usuarioDto.getFuncionario().getDiscriminante() == null
                || usuarioDto.getFuncionario().getDiscriminante().getCatDiscriminanteId() == null
                || usuarioDto.getFuncionario().getDiscriminante().getCatDiscriminanteId() <= 0L) {
            throw new NSJPNegocioException(CodigoError.PARAMETROS_INSUFICIENTES);
        }

        final StringBuffer queryStr = new StringBuffer();

        queryStr.append(" FROM Expediente e");
        queryStr.append(" WHERE e.caso.numeroGeneralCaso LIKE '");
        queryStr.append(casoDto.getNumeroGeneralCaso().trim());
        queryStr.append("'");
//		queryStr.append(" AND e.discriminante.catDiscriminanteId = ");
//		queryStr.append(usuarioDto.getFuncionario().getDiscriminante().getCatDiscriminanteId());

        logger.debug("queryStr :: " + queryStr);
        Query qry = super.getSession().createQuery(queryStr.toString());
        return (Expediente) qry.uniqueResult();
    }

    public int obtenDetalleDeCanalizacionDeNumeroExpediente(Long idNumeroExpediente) {
        final StringBuffer queryString = new StringBuffer();

        int respuesta;

        //Se pregunta si en el expediente se genero alguna canalizacion
        queryString
                .append("SELECT ne FROM Expediente e ")
                .append("LEFT JOIN e.actividads ac LEFT JOIN e.numeroExpedientes ne ")
                .append("LEFT JOIN e.origen o ")
                .append("WHERE ac.tipoActividad IN(").append(Actividades.RECIBIR_CANALIZACION_UI.getValorId()).append(",")
                .append(Actividades.RECIBIR_CANALIZACION_JAR.getValorId()).append(") ");
        queryString.append(" AND ne.numeroExpedienteId = ").append(idNumeroExpediente);

        Query qry = super.getSession().createQuery(queryString.toString());
        NumeroExpediente loExpediente = (NumeroExpediente) qry.uniqueResult();
        if (loExpediente != null) {
            //Se calcuala el numero de canalizaciones recibidas en el expediente
            queryString.delete(0, queryString.length());
            queryString
                    .append("SELECT count(ac) FROM Expediente e ")
                    .append("LEFT JOIN e.actividads ac LEFT JOIN e.numeroExpedientes ne ")
                    .append("LEFT JOIN e.origen o ")
                    .append("WHERE ac.tipoActividad IN(").append(Actividades.RECIBIR_CANALIZACION_UI.getValorId()).append(",")
                    .append(Actividades.RECIBIR_CANALIZACION_JAR.getValorId()).append(") ");
            queryString.append(" AND ne.numeroExpedienteId = ").append(idNumeroExpediente);
            qry = super.getSession().createQuery(queryString.toString());
            Long noDeCanalizacionesRecibidas = (Long) qry.uniqueResult();
            logger.info("El total de canalizaciones RECIBIDAS en el expediente es: " + noDeCanalizacionesRecibidas);

            //Se calcuala el numero de canalizaciones recibidas en el expediente
            queryString.delete(0, queryString.length());
            queryString
                    .append("SELECT count(ac) FROM Expediente e ")
                    .append("LEFT JOIN e.actividads ac LEFT JOIN e.numeroExpedientes ne ")
                    .append("LEFT JOIN e.origen o ")
                    .append("WHERE ac.tipoActividad IN(").append(Actividades.ATENDER_CANALIZACION_UI.getValorId()).append(",")
                    .append(Actividades.ATENDER_CANALIZACION_JAR.getValorId()).append(") ");
            queryString.append(" AND ne.numeroExpedienteId = ").append(idNumeroExpediente);
            qry = super.getSession().createQuery(queryString.toString());
            Long noDeCanalizacionesAtendidas = (Long) qry.uniqueResult();
            logger.info("El total de canalizaciones ATENDIDAS en el expediente es: " + noDeCanalizacionesAtendidas);
            if (noDeCanalizacionesRecibidas == noDeCanalizacionesAtendidas) {
                respuesta = 2; // Las canalizaciones del expediente fueron atendidas
            } else {
                respuesta = 1; // Las canalizaciones del expediente no han sido atendidas
            }

        } else {
            respuesta = 0; // El expediente no ha sido canalizado
        }
        return respuesta;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Valor> consultarEstatusDeExpedientesDiferentes() {
        final StringBuffer queryString = new StringBuffer();

        queryString.append("SELECT v FROM Valor v ")
                .append(" WHERE v.valorId IN (")
                .append("	SELECT DISTINCT e.estatus.valorId FROM Expediente e ")
                .append(" )")
                .append(" ORDER BY v.valor");
        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NumeroExpediente> buscadorDeExpedientesAReasignarPM(FiltroExpedienteDTO filtroExpedienteDTO) {
        String numeroExpediente = filtroExpedienteDTO.getNumeroExpediente();

        Long distritoId = 0L;

        if (filtroExpedienteDTO.getUsuario() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getDistrito() != null) {
            distritoId = filtroExpedienteDTO.getUsuario().getFuncionario().getDiscriminante().getDistrito().getCatDistritoId();
        }

        Long idFuncionario = 0L;
        if (filtroExpedienteDTO.getUsuario() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario() != null) {
            idFuncionario = filtroExpedienteDTO.getUsuario().getFuncionario().getClaveFuncionario();
        }

        final PaginacionDTO pag = PaginacionThreadHolder.get();
        final StringBuffer queryStr = new StringBuffer();

        queryStr.append(" SELECT DISTINCT n");
        queryStr.append(" FROM Expediente e ");
        queryStr.append(" LEFT JOIN e.numeroExpedientes n ");
        queryStr.append(" WHERE 1=1");

        //Filtra por el numero de expediente
        if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
            queryStr.append(" AND n.numeroExpediente like '%");
            queryStr.append(numeroExpediente.trim());
            queryStr.append("%'");
        }

        //Filtra por area
        queryStr.append(" and n.jerarquiaOrganizacional.jerarquiaOrganizacionalId IN ( ");
        queryStr.append(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong());
        queryStr.append(" ) ");

        //Filtra por funcionario
        if (idFuncionario != null && !idFuncionario.equals(0L)) {
            queryStr.append(" and n.numeroExpediente.funcionario.claveFuncionario = ").append(idFuncionario);
        }

        //Filtra por distrito
        if (distritoId != null && !distritoId.equals(0L)) {
            queryStr.append(" and n.expediente.discriminante.distrito.catDistritoId = ").append(distritoId);
        }

		//UNION PARA CONSULTAR LOS EXPEDIENTES DE UNIDAD DE INVESTIGACION QUE AUN NO TIENEN EXPEDIENTE DE POLICIA MINISTERIAL
        queryStr.append(" UNION");
        queryStr.append(" SELECT DISTINCT n");
        queryStr.append(" FROM Expediente e ");
        queryStr.append(" LEFT JOIN e.numeroExpedientes n ");

        queryStr.append(" WHERE 1=1");

        //Filtra por area		
        queryStr.append(" AND n.jerarquiaOrganizacional.jerarquiaOrganizacionalId IN ( ").append(Areas.UNIDAD_INVESTIGACION.parseLong()).append(")");

        queryStr.append(" AND n.numeroExpedienteId IN(");
        queryStr.append(" 		SELECT s.numeroExpediente.numeroExpedienteId from Solicitud s");
        queryStr.append(" 		WHERE  s.tipoSolicitud = ").append(TiposSolicitudes.POLICIA_MINISTERIAL.getValorId());

        //Filtra por el numero de expediente
        if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
            queryStr.append("   AND s.numeroExpediente.numeroExpediente like '%");
            queryStr.append(numeroExpediente.trim());
            queryStr.append("%'");
        }

        //Filtra por funcionario
        if (idFuncionario != null && !idFuncionario.equals(0L)) {
            queryStr.append(" 	AND s.destinatario.claveFuncionario = ").append(idFuncionario);
        }
        queryStr.append(")");

        //Filtra por distrito
        if (distritoId != null && !distritoId.equals(0L)) {
            queryStr.append(" and n.expediente.discriminante.distrito.catDistritoId = ").append(distritoId);
        }
        queryStr.append(" AND e.expedienteId NOT IN(");
        queryStr.append(" 	select ne.expediente.expedienteId from NumeroExpediente ne");
        queryStr.append(" 	where ne.jerarquiaOrganizacional.jerarquiaOrganizacionalId IN ( ").append(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong()).append(")");
        queryStr.append(" )");

        queryStr.append(" ORDER BY n.numeroExpediente ASC ");
        return super.ejecutarQueryPaginado(queryStr, pag);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Long> buscadorDeExpedientesAReasignarPMQueryNativo(
            FiltroExpedienteDTO filtroExpedienteDTO, Boolean consultarSolicituedesDeInvestigacion) {
        String numeroExpediente = filtroExpedienteDTO.getNumeroExpediente();

        Long distritoId = 0L;

        if (filtroExpedienteDTO.getUsuario() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario()
                .getDiscriminante() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario()
                .getDiscriminante().getDistrito() != null) {
            distritoId = filtroExpedienteDTO.getUsuario().getFuncionario()
                    .getDiscriminante().getDistrito().getCatDistritoId();
        }

        Long idFuncionario = 0L;
        if (filtroExpedienteDTO.getUsuario() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario() != null
                && filtroExpedienteDTO.getUsuario().getFuncionario()
                .getClaveFuncionario() != null) {
            idFuncionario = filtroExpedienteDTO.getUsuario().getFuncionario()
                    .getClaveFuncionario();
        }

        final StringBuffer queryStr = new StringBuffer();

        queryStr.append(" select ");
        queryStr.append(" NumeroExpediente.NumeroExpediente_id ");
        queryStr.append(" from ");
        queryStr.append(" Funcionario Funcionario  ");
        queryStr.append(" left join NumeroExpediente NumeroExpediente  ");
        queryStr.append(" on Funcionario.iClaveFuncionario = NumeroExpediente. ");
        queryStr.append(" iClaveFuncionario  ");
        queryStr.append(" left join Expediente Expediente  ");
        queryStr.append(" on NumeroExpediente.Expediente_id = Expediente.Expediente_id  ");
        queryStr.append(" left join CatDiscriminante CatDiscriminante  ");
        queryStr.append(" on Expediente.catDiscriminante_id = CatDiscriminante. ");
        queryStr.append(" catDiscriminante_id  ");
        queryStr.append(" left join CatDistrito CatDistrito  ");
        queryStr.append(" on CatDistrito.catDistrito_id = CatDiscriminante. ");
        queryStr.append(" catDistrito_id  ");
        queryStr.append(" left join JerarquiaOrganizacional  ");
        queryStr.append(" JerarquiaOrganizacional  ");
        queryStr.append(" on NumeroExpediente.JerarquiaOrganizacional_id =  ");
        queryStr.append(" JerarquiaOrganizacional.JerarquiaOrganizacional_id  ");
        queryStr.append(" where 1=1 ");

        // Filtra por el numero de expediente
        if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
            queryStr.append("   AND (NumeroExpediente.cNumeroExpediente like '%");
            queryStr.append(numeroExpediente.trim());
            queryStr.append("%') ");
        }

        // Filtra por area
        queryStr.append(
                " AND (JerarquiaOrganizacional.JerarquiaOrganizacional_id = ")
                .append(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong())
                .append(")");

        // Filtra por funcionario
        if (idFuncionario != null && !idFuncionario.equals(0L)) {
            queryStr.append(" AND (NumeroExpediente.iClaveFuncionario = ")
                    .append(idFuncionario).append(")");
        }

        // Filtra por distrito
        if (distritoId != null && !distritoId.equals(0L)) {
            queryStr.append(" AND (CatDiscriminante.catDistrito_id = ")
                    .append(distritoId).append(")");
        }

        if (consultarSolicituedesDeInvestigacion) {
            queryStr.append(" UNION ");
            queryStr.append(" select ");
            queryStr.append(" NumeroExpediente.NumeroExpediente_id ");
            queryStr.append(" from ");
            queryStr.append(" Funcionario Funcionario  ");
            queryStr.append(" left join NumeroExpediente NumeroExpediente  ");
            queryStr.append(" on Funcionario.iClaveFuncionario = NumeroExpediente. ");
            queryStr.append(" iClaveFuncionario  ");
            queryStr.append(" left join Expediente Expediente  ");
            queryStr.append(" on NumeroExpediente.Expediente_id = Expediente.Expediente_id  ");
            queryStr.append(" left join CatDiscriminante CatDiscriminante  ");
            queryStr.append(" on Expediente.catDiscriminante_id = CatDiscriminante. ");
            queryStr.append(" catDiscriminante_id  ");
            queryStr.append(" left join CatDistrito CatDistrito  ");
            queryStr.append(" on CatDistrito.catDistrito_id = CatDiscriminante. ");
            queryStr.append(" catDistrito_id  ");
            queryStr.append(" left join JerarquiaOrganizacional  ");
            queryStr.append(" JerarquiaOrganizacional  ");
            queryStr.append(" on NumeroExpediente.JerarquiaOrganizacional_id =  ");
            queryStr.append(" JerarquiaOrganizacional.JerarquiaOrganizacional_id  ");
            queryStr.append(" where	 1=1 ");
            queryStr.append(
                    " AND (JerarquiaOrganizacional.JerarquiaOrganizacional_id = ")
                    .append(Areas.UNIDAD_INVESTIGACION.parseLong()).append(")");
            queryStr.append(" AND (NumeroExpediente.NumeroExpediente_id IN( ");
            queryStr.append(" select ");
            queryStr.append(" DISTINCT NumeroExpediente.NumeroExpediente_id ");
            queryStr.append(" from ");
            queryStr.append(" Solicitud Solicitud  ");
            queryStr.append(" left join NumeroExpediente NumeroExpediente  ");
            queryStr.append(" on Solicitud.NumeroExpediente_id = NumeroExpediente. ");
            queryStr.append(" NumeroExpediente_id  ");
            queryStr.append(" left join Funcionario Funcionario  ");
            queryStr.append(" on Solicitud.iFuncionarioDestinatario = Funcionario. ");
            queryStr.append(" iClaveFuncionario  ");
            queryStr.append(" left join Valor Valor  ");
            queryStr.append(" on Solicitud.TipoSolicitud_val = Valor.Valor_id  ");
            queryStr.append(" where 1=1 ");
            queryStr.append(" AND (Valor.Valor_id = ")
                    .append(TiposSolicitudes.POLICIA_MINISTERIAL.getValorId())
                    .append(")");
            // Filtra por el numero de expediente
            if (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) {
                queryStr.append("  AND  (NumeroExpediente.cNumeroExpediente like '%");
                queryStr.append(numeroExpediente.trim());
                queryStr.append("%')");
            }
            // Filtra por funcionario
            if (idFuncionario != null && !idFuncionario.equals(0L)) {
                queryStr.append(" AND (Solicitud.iFuncionarioDestinatario = ")
                        .append(idFuncionario).append(")");
            }

            queryStr.append(" )) ");

            // Filtra por distrito
            if (distritoId != null && !distritoId.equals(0L)) {
                queryStr.append(" AND (CatDiscriminante.catDistrito_id = ")
                        .append(distritoId).append(") ");
            }
            queryStr.append(" AND (Expediente.Expediente_id NOT IN ( ");
            queryStr.append(" select ");
            queryStr.append(" Expediente.Expediente_id  ");
            queryStr.append(" from ");
            queryStr.append(" NumeroExpediente NumeroExpediente  ");
            queryStr.append(" left join Expediente Expediente  ");
            queryStr.append(" on NumeroExpediente.Expediente_id = Expediente.Expediente_id  ");
            queryStr.append(" left join JerarquiaOrganizacional JerarquiaOrganizacional  ");
            queryStr.append(" on JerarquiaOrganizacional.JerarquiaOrganizacional_id =  ");
            queryStr.append(" NumeroExpediente.JerarquiaOrganizacional_id  ");
            queryStr.append(" where ");
            queryStr.append(
                    " (JerarquiaOrganizacional.JerarquiaOrganizacional_id = ")
                    .append(Areas.COORDINACION_POLICIA_MINISTERIAL.parseLong())
                    .append(")) ");
            queryStr.append(" )  ");
        }

        final PaginacionDTO pag = PaginacionThreadHolder.get();

        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("1")) {
                queryStr.append(" order by ");
                queryStr.append(" NumeroExpediente.NumeroExpediente_id ");
                queryStr.append(" ").append(pag.getDirOrd());
            }
        }

        Query query = super.getSession().createSQLQuery(queryStr.toString());

        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(pag.getFirstResult());
            if (pag.getRows() != null & pag.getRows() > 0) {
                query.setMaxResults(pag.getRows());
            } else {
                query.setMaxResults(PaginacionDTO.DEFAULT_MAX_RESULT); // default
            }
        }

        List<BigDecimal> resp = query.list();
        logger.debug("resp.size() :: " + resp.size());

        if (pag != null && pag.getPage() != null) {
            query.setFirstResult(0);
            query.setMaxResults(-1);
            final List<BigDecimal> temp = query.list();
            logger.debug("temp.size() :: " + temp.size());
            pag.setTotalRegistros(new Long(temp.size()));
            PaginacionThreadHolder.set(pag);
        }
        List<Long> respueta = new ArrayList<Long>();

        Iterator iterator = resp.iterator();

        while (iterator.hasNext()) {
            respueta.add(((BigDecimal) iterator.next()).longValue());
        }
        return respueta;
    }

    public void reasignaExpedientesDePolMinConSP(HashMap<String, String> valores) {

        logger.info(" reasignaExpedientesDePolMinConSP - Valores: " + valores);

        String consuta = " EXEC usp_cambiarResponsablePolicia :as_cNumeroExpediente, :ai_FuncionarioIni, :ai_FuncionarioFin ";

        final StringBuffer queryStr = new StringBuffer(consuta);
        Query query = super.getSession().createSQLQuery(queryStr.toString());

        Iterator<Entry<String, String>> iterador = valores.entrySet().iterator();
        while (iterador.hasNext()) {
            Map.Entry<String, String> dato = (Entry<String, String>) iterador.next();
            query.setParameter(dato.getKey(), dato.getValue());
        }
        logger.info(" QUERYS:" + query.getQueryString());

        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NumeroExpediente> consultarExpedientesActividadAreaJarAsignados(
            FiltroExpedienteDTO filtroExpedienteDTO) {

        final StringBuffer queryString = new StringBuffer();
        queryString
                .append("SELECT ne FROM NumeroExpediente ne ")
                .append("LEFT JOIN ne.expediente e ")
                .append("LEFT JOIN e.origen o ")
                .append(" WHERE 1 = 1");
        queryString.append(" AND ne.numeroExpedienteId IN(")
                .append("SELECT ne.numeroExpedienteId FROM Expediente e ")
                .append("LEFT JOIN e.actividads ac LEFT JOIN e.numeroExpedientes ne ")
                .append("LEFT JOIN e.origen o WHERE ");
        queryString.append("ne.jerarquiaOrganizacional = ");
        queryString.append(Areas.JUSTICIA_ALTERNATIVA_RESTAURATIVA.ordinal() + " ");
        queryString.append(" AND ").append("ac.tipoActividad=").append(Actividades.RECIBIR_CANALIZACION_JAR.getValorId());
        if(filtroExpedienteDTO.getEsAsignado()){
         queryString.append(" AND ne.numeroExpediente != ''  ");}
        else{
            queryString.append(" AND ne.numeroExpediente = ''  ");
        }
        if (filtroExpedienteDTO.getIdDiscriminante() != null && filtroExpedienteDTO.getIdDiscriminante() > 0) {
            if (filtroExpedienteDTO.getestatusMenuCoorJAR() != null) {
                queryString.append(" AND e.discriminante.catDiscriminanteId in (");
                queryString.append("select cf.discriminante.catDiscriminanteId from Funcionario cf where cf.claveFuncionario = ");
                queryString.append(filtroExpedienteDTO.getUsuario().getIdUsuario()).append(") ");
                if(filtroExpedienteDTO.getEsPropio())
                queryString.append(" AND  ne.funcionario.claveFuncionario = ").append(filtroExpedienteDTO.getUsuario().getIdUsuario());
            } else {
                queryString.append(" AND e.discriminante.catDiscriminanteId=").append(filtroExpedienteDTO.getIdDiscriminante());
            }
        }
        queryString.append(" GROUP BY ne.numeroExpedienteId, ac.tipoActividad ");
        queryString.append(")");
        logger.info("QUERY ASIGNADOS "+ queryString.toString());
        final PaginacionDTO pag = PaginacionThreadHolder.get();

        PaginacionDTO paguinaantigua = pag;
        logger.debug("pag :: " + pag);
        if (pag != null && pag.getCampoOrd() != null) {
            if (pag.getCampoOrd().equals("NumeroExpediente")) {
                queryString.append(" order by ");
                queryString.append("ne.numeroExpediente");
                queryString.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("Fecha")) {
                queryString.append(" order by ");
                queryString.append("e.fechaCreacion");
                queryString.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("Origen")) {
                queryString.append(" order by ");
                queryString.append("o.valor ");
                queryString.append(" ").append(pag.getDirOrd());
            }
            if (pag.getCampoOrd().equals("Estatus")) {
                queryString.append(" order by ");
                queryString.append("ne.estatus.valor");
                queryString.append(" ").append(pag.getDirOrd());
            }
        }

        if (filtroExpedienteDTO.getestatusMenuCoorJAR() != null
                && (filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.ASIGNADOS.getValorId())
                || filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.PORASIGNAR.getValorId())
                || filtroExpedienteDTO.getestatusMenuCoorJAR().equals(EstatusMenuJAR.PROPIOS.getValorId()))) {

            List<NumeroExpediente> lista = super.ejecutarQueryPaginado(queryString, null);
            PaginacionThreadHolder.set(paguinaantigua);
            return lista;

        } else {
            return super.ejecutarQueryPaginado(queryString, pag);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<ExpedienteViewDTO> consultaCiudadana(String apaterno, String amaterno, String nombre, String expediente) {

        logger.info("nombres: " + nombre);
        logger.info("apaterno: " + apaterno);
        logger.info("amaterno" + amaterno);

        List<ExpedienteViewDTO> loExpedientes = new ArrayList<ExpedienteViewDTO>();
        StringBuffer queryString = new StringBuffer();
        queryString.append(" declare @Nombre  varchar(150)")
                .append(" declare @Apaterno varchar(150)")
                .append(" declare @Amaterno varchar(150)")
                .append(" declare @expediente varchar(35)")
                .append(" set @Nombre = '").append(nombre).append("'")
                .append(" set @Apaterno = '").append(apaterno).append("'")
                .append(" set @Amaterno = '").append(amaterno).append("'")
                .append(" set @expediente = '").append(expediente).append("'")
                .append(" EXEC busquedaKiosco @Nombre, @Apaterno, @Amaterno ,@expediente");
        Query query = super.getSession().createSQLQuery(queryString.toString());

        List<Object> resp = query.list();

        if (!resp.isEmpty()) {
            Iterator iterator = resp.iterator();
            while (iterator.hasNext()) {
                Object[] obj = (Object[]) iterator.next();
                ExpedienteViewDTO loExpediente = new ExpedienteViewDTO();
                loExpediente.setNumeroGeneralCaso((String) obj[1]);
                loExpediente.setNumeroExpediente((String) obj[4]);
                loExpediente.setVictima((String) obj[5]);
                loExpediente.setDelito((String) obj[6]);
                loExpediente.setNombreFuncionario((String) obj[9]);
                loExpediente.setArea((String) obj[12]);
                loExpediente.setEstatus((String) obj[14]);
                loExpedientes.add(loExpediente);
            }
        }
        return loExpedientes;
    }

}

/**
 * Nombre del Programa : ConfActividadDocumentoDAOImpl.java Autor : Jacob Lobaco
 * Compania : Ultrasist Proyecto : NSJP Fecha: 06-jul-2011 Marca de cambio : N/A
 * Descripcion General : N/A Programa Dependient :N/A Programa Subsecuente :N/A
 * Cond. de ejecucion :N/A Dias de ejecucion :N/A Horario: N/A MODIFICACIONES
 * ------------------------------------------------------------------------------
 * Autor :N/A Compania :N/A Proyecto :N/A Fecha: N/A Modificacion :N/A
 * ------------------------------------------------------------------------------
 */
package mx.gob.segob.nsjp.dao.actividad.impl;

import java.util.List;

import mx.gob.segob.nsjp.comun.util.tl.PaginacionThreadHolder;
import mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO;
import mx.gob.segob.nsjp.dao.base.impl.GenericDaoHibernateImpl;
import mx.gob.segob.nsjp.dto.base.PaginacionDTO;
import mx.gob.segob.nsjp.model.ConfActividadDocumento;
import mx.gob.segob.nsjp.model.ConfActividadDocumentoRol;
import mx.gob.segob.nsjp.model.NumeroExpediente;
import mx.gob.segob.nsjp.model.Rol;
import mx.gob.segob.nsjp.model.Valor;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @version 1.0
 * @author Jacob Lobaco
 */
@Repository
public class ConfActividadDocumentoDAOImpl
        extends GenericDaoHibernateImpl<ConfActividadDocumento, Long>
        implements ConfActividadDocumentoDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<ConfActividadDocumento> consultarConfActividadDocumento(
            Long jerarquiaOrganizacionalId, NumeroExpediente numeroExpediente, Long idCategoriaActidad) {
        // Debemos consultar el estado del expediente.
        Valor estatus = numeroExpediente.getEstatus();
        if (estatus == null) {
            throw new IllegalStateException("No es posible consulta conActivid"
                    + "adDocumento para el expediente = " + numeroExpediente.getNumeroExpediente()
                    + " Se requiere que el expediente tenga un estatus asociado");
        }
        // Buscamos los registros que tengan grupoActividad = estatus
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT conf FROM ConfActividadDocumento conf ").
                append("WHERE conf.grupoActividad = ").append(estatus.getValorId()).append(" ").
                append("AND conf.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").append(jerarquiaOrganizacionalId).
                append(" AND conf.muestraEnCombo = 1");
        if (idCategoriaActidad != null) {
            sb.append(" AND conf.categoriaActividad = ").append(idCategoriaActidad);
        }

        sb.append(" order by ");
        sb.append("conf.tipoActividad.valor");

        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
    }

    @Override
    public List<ConfActividadDocumentoRol> consultarActividadRol(Long idRol) {

        StringBuilder sb = new StringBuilder();
        /*  private Long confActividadDocumentoRolId;
         private String descripcion;
         private Valor tipoActividad;
         private Valor tipoDocumento;
         private Boolean muestraEnCombo;
         private Forma forma;
         private Boolean usaEditor;
         private Rol rol;
         private Boolean activo;
    
         sb.append("SELECT cRol.*, cRol.tipoActividad.valor, cRol.tipoActividad.valorId "
         + ", cRol.tipoDocumento.valor, cRol.tipoDocumento.valorId "
         + "FROM ConfActividadDocumentoRol cRol").
         append(" WHERE cRol.rol.rolId = ").append(idRol).
         append(" AND cRol.activo = 1").
         append(" order by cRol.tipoActividad.valor");
         logger.info("QUERY PARA consultarActividadRol: " + sb);
         Query query = super.getSession().createQuery(sb.toString());
        
         return query.list();*/
        return consultarActividadCatUie(idRol, null);
    }

    @Override
    public List<ConfActividadDocumentoRol> consultarActividadCatUie(Long idRol, Long catUIE) {

        StringBuilder sb = new StringBuilder();

       // sb.append("SELECT cRol.*, cRol.tipoDocumento.valorId, cRol.tipoDocumento.valor , ").
        //    append(" cRol.tipoActividad.valorId, cRol.tipoActividad.valor  FROM").
        sb.append("SELECT cRol from ConfActividadDocumentoRol cRol").
                append(catUIE != null ? " , ConfActividadUIE cUie " : "").
                append(" WHERE cRol.rol.rolId = ").append(idRol).
                append(" AND cRol.activo = 1");
        if (catUIE != null) {
            //:TODO agregar atributo de activo
            //sb.append(" AND cUie.activo = 1 and cUie.catUIEspecializada.catUIEId = ").append(catUIE).
            sb.append(" AND cUie.catUIEspecializada.catUIEId = ").append(catUIE).
                    append(" AND cRol.tipoActividad.valorId = cUie.tipoActividad.valorId");
        }

        sb.append(" order by cRol.tipoActividad.valor ");

        logger.info("QUERY PARA consultarActividadCatUie: " + sb);
        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
    }

    @Override
    public ConfActividadDocumento consultaConfActividadDocumentoPorIdActividad(Long idTipoActividad) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT conf FROM ConfActividadDocumento conf ").
                append("WHERE conf.tipoActividad.valorId = :idTipoActividad");
        Query q = getSession().createQuery(sb.toString());
        q.setParameter("idTipoActividad", idTipoActividad);
        return (ConfActividadDocumento) q.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Valor> consultarEstadosDistintosPorJerarquiaOrganizacional(Long idJerarquiaOrganizacional) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT(estadoCambioExpediente)  FROM ConfActividadDocumento conf ").
                append("WHERE conf.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").
                append(idJerarquiaOrganizacional).
                append(" AND conf.estadoCambioExpediente.registro.esActivo = true");
        Query q = getSession().createQuery(sb.toString());

        logger.info("MenuRepetidos: " + sb.toString());

        return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ConfActividadDocumento> consultarActuacionesPorRol(Rol rol) {
        StringBuffer sb = new StringBuffer();
        sb.append(" FROM ConfActividadDocumento cad ").
                append(" WHERE cad.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").
                append(rol.getJerarquiaOrganizacional().getJerarquiaOrganizacionalId()).
                append(" and cad.muestraEnCombo = ").
                append(true);
        final PaginacionDTO pag = PaginacionThreadHolder.get();
        return ejecutarQueryPaginado(sb, pag);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ConfActividadDocumento> consultarPorTipoActFormaAndJerarquia(
            Long idTipoActividad, Long idTipoForma, Long jeraquia) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("FROM ConfActividadDocumento cad")
                .append(" WHERE cad.tipoActividad=").append(idTipoActividad)
                .append(" AND cad.forma=").append(idTipoForma)
                .append(" AND cad.jerarquiaOrganizacional=").append(jeraquia);
        Query query = super.getSession().createQuery(queryString.toString());
        return query.list();
    }

    /* (non-Javadoc)
     * @see mx.gob.segob.nsjp.dao.actividad.ConfActividadDocumentoDAO#consultarConfActividadDocumentoFiltro(mx.gob.segob.nsjp.model.ConfActividadDocumento)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConfActividadDocumento> consultarConfActividadDocumentoFiltro(
            ConfActividadDocumento filtroConfActividadDocumento) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT conf FROM ConfActividadDocumento conf ").append(
                " WHERE 1=1 ");
        if (filtroConfActividadDocumento.getConfActividadDocumentoId() != null
                && filtroConfActividadDocumento.getConfActividadDocumentoId() > 0) {
            sb.append(" AND conf.confActividadDocumentoId = ").append(
                    filtroConfActividadDocumento.getConfActividadDocumentoId());
        }
        if (filtroConfActividadDocumento.getTipoActividad() != null
                && filtroConfActividadDocumento.getTipoActividad().getValorId() != null
                && filtroConfActividadDocumento.getTipoActividad().getValorId() > 0) {
            sb.append(" AND conf.tipoActividad.valorId = ").append(
                    filtroConfActividadDocumento.getTipoActividad()
                    .getValorId());
        }
        if (filtroConfActividadDocumento.getJerarquiaOrganizacional() != null
                && filtroConfActividadDocumento.getJerarquiaOrganizacional()
                .getJerarquiaOrganizacionalId() != null
                && filtroConfActividadDocumento.getJerarquiaOrganizacional()
                .getJerarquiaOrganizacionalId() > 0) {
            sb.append(
                    " AND conf.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ")
                    .append(filtroConfActividadDocumento
                            .getJerarquiaOrganizacional()
                            .getJerarquiaOrganizacionalId());
        }
        if (filtroConfActividadDocumento.getMuestraEnCombo() != null) {
            sb.append(" AND conf.muestraEnCombo =").append(
                    filtroConfActividadDocumento.getMuestraEnCombo());
        }
        if (filtroConfActividadDocumento.getAccion() != null) {
            sb.append(" AND conf.accion like '")
                    .append(filtroConfActividadDocumento.getAccion())
                    .append("' ");
        }
        if (filtroConfActividadDocumento.getTipoDocumento() != null
                && filtroConfActividadDocumento.getTipoDocumento().getValorId() != null
                && filtroConfActividadDocumento.getTipoDocumento().getValorId() > 0) {
            sb.append(" AND conf.tipoDocumento.valorId =").append(
                    filtroConfActividadDocumento.getTipoDocumento()
                    .getValorId());
        }
        if (filtroConfActividadDocumento.getEstadoCambioExpediente() != null
                && filtroConfActividadDocumento.getEstadoCambioExpediente()
                .getValorId() != null
                && filtroConfActividadDocumento.getEstadoCambioExpediente()
                .getValorId() > 0) {
            sb.append(" AND conf.estadoCambioExpediente.valorId =").append(
                    filtroConfActividadDocumento.getEstadoCambioExpediente()
                    .getValorId());
        }
        if (filtroConfActividadDocumento.getUsaEditor() != null) {
            sb.append(" AND conf.usaEditor =").append(
                    filtroConfActividadDocumento.getUsaEditor());
        }
        if (filtroConfActividadDocumento.getGrupoActividad() != null
                && filtroConfActividadDocumento.getGrupoActividad() > 0) {
            sb.append(" AND conf.grupoActividad =").append(
                    filtroConfActividadDocumento.getGrupoActividad());
        }
        if (filtroConfActividadDocumento.getForma() != null
                && filtroConfActividadDocumento.getForma().getFormaId() != null
                && filtroConfActividadDocumento.getForma().getFormaId() > 0) {
            sb.append(" AND conf.forma.formaId =").append(
                    filtroConfActividadDocumento.getForma().getFormaId());
        }

        if (filtroConfActividadDocumento.getCategoriaActividad() != null
                && filtroConfActividadDocumento.getCategoriaActividad()
                .getValorId() != null
                && filtroConfActividadDocumento.getCategoriaActividad()
                .getValorId() > 0) {
            sb.append(" AND conf.categoriaActividad.valorId = ").append(
                    filtroConfActividadDocumento.getCategoriaActividad()
                    .getValorId());
        }

        sb.append(" order by ");
        sb.append("conf.tipoActividad.valor");

        Query query = super.getSession().createQuery(sb.toString());
        return (List<ConfActividadDocumento>) query.list();
    }

    public ConfActividadDocumento consultarProximoEstatusDeNumExp(
            Long idTipoActividad, Long idEstatusActualNumExp,
            Long jeraquiaOrganizacional) {
        ConfActividadDocumento loConfActividadDocumento = null;

        StringBuffer queryString = new StringBuffer();
        queryString.append("FROM ConfActividadDocumento cad")
                .append(" WHERE cad.tipoActividad=").append(idTipoActividad)
                .append(" AND cad.grupoActividad=")
                .append(idEstatusActualNumExp)
                .append(" AND cad.jerarquiaOrganizacional=")
                .append(jeraquiaOrganizacional)
                .append(" order by cad.muestraEnCombo desc");
        Query query = super.getSession().createQuery(queryString.toString());
        @SuppressWarnings("unchecked")
        List<ConfActividadDocumento> loListabD = query.list();
        if (loListabD != null && loListabD.size() > 0) {
            loConfActividadDocumento = loListabD.get(0);
        }

        return loConfActividadDocumento;
    }

    @Override
    public Long consultarCatUieIdFuncionario(Long catDiscriminanteId) {
        StringBuffer queryString = new StringBuffer();
        queryString.append("SELECT duie.catDiscriminante.catDiscriminanteId FROM DiscriminanteUIEspecializada duie").
                append(" WHERE duie.catUIEspecializada.catUIEId = ").append(catDiscriminanteId);
        logger.info("CONSULTA PARA consultarCatUieIdFuncionario: " + queryString);
        Query qry = super.getSession().createQuery(queryString.toString());

        return (Long) qry.uniqueResult();
    }

    @Override
    public List<ConfActividadDocumento> consultarConfActividadDocumentoCatUie(Long jerarquiaOrganizacionalId, NumeroExpediente numeroExpediente, Long idCategoriaActidad, Long catUie) {
        // Debemos consultar el estado del expediente.
        Valor estatus = numeroExpediente.getEstatus();
        if (estatus == null) {
            throw new IllegalStateException("No es posible consulta conActivid"
                    + "adDocumento para el expediente = " + numeroExpediente.getNumeroExpediente()
                    + " Se requiere que el expediente tenga un estatus asociado");
        }
        // Buscamos los registros que tengan grupoActividad = estatus
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT conf FROM ConfActividadDocumento conf, CatUIEConfActDocumeto cad, CatUIEspecializada cuie ").
                append("WHERE conf.grupoActividad = ").append(estatus.getValorId()).append(" ").
                append("AND conf.jerarquiaOrganizacional.jerarquiaOrganizacionalId = ").append(jerarquiaOrganizacionalId).
                append(" AND conf.muestraEnCombo = 1");
        if (idCategoriaActidad != null) {
            sb.append(" AND conf.categoriaActividad = ").append(idCategoriaActidad);
        }
        sb.append(" AND cad.catUIEspecializada.catUIEId = ").append(catUie).
                append(" AND cuie.catUIEId = cad.catUIEspecializada.catUIEId").
                append(" AND conf.confActividadDocumentoId = cad.confActividadDocumento.confActividadDocumentoId");

        sb.append(" order by ");
        sb.append("conf.tipoActividad.valor");

        logger.info("LA CONSULTA PARA consultarConfActividadDocumentoCatUie " + sb);
        Query query = super.getSession().createQuery(sb.toString());
        return query.list();
    }
}

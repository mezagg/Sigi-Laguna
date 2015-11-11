/**
 * Nombre del Programa : Indicadores.java
 * Autor                            : GustavoBP
 * Compania                    : Ultrasist
 * Proyecto                      : NSJP                    Fecha: 18/06/2012
 * Marca de cambio        : N/A
 * Descripcion General    : Enumeracion que lista los distintas consultas para generar los indicadores 
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
package mx.gob.segob.nsjp.comun.indicador;

import static mx.gob.segob.nsjp.comun.enums.institucion.Instituciones.DEF;
import static mx.gob.segob.nsjp.comun.enums.institucion.Instituciones.PGJ;
import static mx.gob.segob.nsjp.comun.enums.institucion.Instituciones.PJ;
import static mx.gob.segob.nsjp.comun.enums.institucion.Instituciones.SSP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.segob.nsjp.comun.enums.institucion.Instituciones;

/**
 * Enumeracion que lista los distintas consultas para generar los indicadores
 * 
 * @author GustavoBP
 */
public enum Indicadores {
	INDICADOR_1(1L,
			"Incidencia de delitos estatal",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("ClaveDelito","Delito", "Total"),
			"DECLARE @cClaveDelito [cadena 10]"+
			"DECLARE @query nvarchar(max)"+
			"SELECT @cClaveDelito =''"+
			"Select @query ='"+
			" SELECT CD.cClaveDelito, CD.cNombre AS Delito, COUNT (*) as Total " +
			" FROM Expediente E, Delito D, CatDelito CD" +
			" WHERE E.Expediente_id =D.Expediente_id  " +
			" AND D.CatDelito_id = CD.CatDelito_id' " +
			"  " +
			" IF (@cClaveDelito>0) BEGIN " +
			" SELECT  @query = @query + ' AND CD.cClaveDelito = '''+@cClaveDelito+'''' END"+
			" SELECT  @query = @query +' AND convert(date, E.dFechaCreacion, 103) between convert(date,'''+:fechaIncio+''',103) AND  convert(date,'''+:fechaFin+''',103)' " +
			" SELECT  @query = @query +' group by  CD.cClaveDelito,CD.cNombre' exec (@query)",
			1, 2, -1, //X,Y,Serie (-1 no requiere la serie) 
			"Delito", "Número de Delitos",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, true
			),
	INDICADOR_2(2L,
			"Incidencia de delitos distrital",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("ClaveDelito","Delito","Distrito","Total"),
			"DECLARE @cClaveDelito [cadena 10]"+
			"DECLARE @query nvarchar(max)"+
			"SELECT @cClaveDelito =''"+
			"Select @query ='"+
			" SELECT CD.cClaveDelito,CD.cNombre as Delito,Dist.cNombre as Distrito, COUNT(*) Total " +
			" FROM Expediente E,Delito D, CatDelito CD, CatDiscriminante Dis, CatDistrito Dist" +
			" WHERE E.Expediente_id =D.Expediente_id  " +
			" AND D.CatDelito_id = CD.CatDelito_id " +
			" AND E.catDiscriminante_id = Dis.catDiscriminante_id" +
			" AND Dis.catDistrito_id = Dist.catDistrito_id ' " +
			" IF (@cClaveDelito>0) BEGIN " +
			" SELECT  @query = @query + ' AND CD.cClaveDelito = '''+@cClaveDelito+'''' " +
			" END"+
			" SELECT  @query = @query +' AND convert(date, E.dFechaCreacion,103) between convert(date,'''+:fechaIncio+''',103) AND convert(date,'''+:fechaFin+''',103)' " +
			" SELECT  @query = @query +' group by CD.cClaveDelito,CD.cNombre, Dist.cNombre' exec (@query)",
			1, 3, 2,
			"Delito", "Número de Delitos",
			OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_4(4L,
			"Avance de investigaciones",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Funcionario","Estatus", "Total"),
			" SELECT F.cNombreFuncionario+' '+F.cApellidoPaternoFuncionario+' '+F.cApellidoMaternoFuncionario AS 'Funcionario',V.cValor AS 'Estatus',COUNT (*) as 'Total' " +
			" FROM Expediente E, NumeroExpediente NE, Funcionario F, Valor V " +
			" WHERE E.Expediente_id =NE.Expediente_id  " +
			" AND NE.JerarquiaOrganizacional_id = 10 " +
			" AND NE.iClaveFuncionario = F.iClaveFuncionario " +
			" AND NE.Estatus_val = V.Valor_id "+
			" AND convert(date, E.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) " +
			" GROUP BY F.cNombreFuncionario,F.cApellidoPaternoFuncionario,F.cApellidoMaternoFuncionario,V.cValor ",
			0, 2, 1,
			"Funcionario", "Número de Expedientes", 
			OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_6(6L,
			"Aumento de denuncias",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito", "Agencia/Tribunal", "#DeDenuncias"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#DeDenuncias' " +
			" FROM Expediente E, CatDiscriminante cD, CatDistrito D " +
			" WHERE origen_val = 2078  " +
			" AND E.catDiscriminante_id = cD.catDiscriminante_id " +
			" AND cD.catDiscriminante_id = D.catDistrito_id " +
			" AND convert(date, E.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) " +
			" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
			1, 2, 0,
			"Distrito", "Número de Denuncias",
			OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_7(7L,
			"Cantidad de Delitos cometidos en cada uno de los distritos, ordenado de mayor a menor incidencia.",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Clave Delito","Delito", "Distrito", "Total"),
			" SELECT CD.cClaveDelito,CD.cNombre,Dist.cNombre as Distrito, COUNT (NDM.cSexo)  as Total" +
			" FROM Delito D, CatDelito CD, DelitoPersona DPM, Involucrado IM,NombreDemografico NDM,Expediente E, " +
			" CatDiscriminante Dis, CatDistrito DisT" +
			" WHERE D.CatDelito_id = CD.CatDelito_id" +
			" AND D.Delito_id =DPM.Delito_id" +
			" AND DPM.ProbableResponsable_id=IM.Involucrado_id" +
			" AND IM.Involucrado_id = NDM.Persona_id" +
			" AND E.Expediente_id = D.Expediente_id" +
			" AND E.catDiscriminante_id = Dis.catDiscriminante_id" +
			" AND Dis.catDistrito_id = DisT.catDistrito_id" +
			" AND convert (date, E.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) " +
			" GROUP BY  Dist.cNombre,CD.cClaveDelito,CD.cNombre" +
			" ORDER BY  Dist.cNombre ,Total Desc",
			1, 3, 2,
			"Delito", "Número de Delitos",
			OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_8(8L,
			"Mayor número de sentencias condenatorias",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia\\Tribunal", "#NumeroDeSentencias"),
			" SELECT D.cClaveDistrito +'-'+D.cNombre As 'Distrito', cD.cClaveDiscriminante+'-'+ cD.cNombre AS 'Agencia\\Tribunal', COUNT (*) AS '#NumeroDeSentencias'  "+
					" FROM  Sentencia S,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE TipoSentencia_val in (323,324) "+
					" AND S.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND cD.catDiscriminante_id = E.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert(date, S.dFechaInicioPena, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Distrito", "Número de Sentencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_9(9L,
			"Movimientos en Actas de Atención Temprana",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Edificio/Agecia","Total Actas"),
			" SELECT Dis.cNombre as 'Edificio/Agecia', COUNT (*) As 'totalActasAtencionTemprana'" +
			" FROM Expediente E, NumeroExpediente NE, CatDiscriminante cD, CatDistrito D, CatDiscriminante Dis" +
			" WHERE E.Expediente_id= NE.Expediente_id" +
			" AND E.catDiscriminante_id = cD.catDiscriminante_id" +
			" AND cD.catDistrito_id = D.catDistrito_id" +
			" AND NE.JerarquiaOrganizacional_id = 45" +
			" AND Dis.catDiscriminante_id = E.catDiscriminante_id" +
			" AND convert(date, E.dFechaCreacion, 103) between convert(date, :fechaIncio, 103) AND convert(date, :fechaFin, 103)" +
			" GROUP BY Dis.cNombre",
			0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
			"Edificio", "Número de Actas",
			OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_10(10L,
			"Percepción de justicia",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#DeConvenios"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#DeConvenios' "+
					" FROM Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND E.Expediente_id = A.Expediente_id "+
					" AND A.TipoActividad_val = 6274 "+
					" AND A.Documento_id is not null"+
					" AND convert(date, A.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Distrito", "Número de Convenios",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, false, false		
			),
			
	INDICADOR_11(11L,
			"Aplicación correcta de Mecanismos Alternativos de Solución de Controversias",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#DeConvenios"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#DeConvenios' "+
					" FROM Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D  "+
					" WHERE E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND E.Expediente_id = A.Expediente_id "+
					" AND A.TipoActividad_val = 6274 "+
					" AND A.Documento_id is not null"+
					" AND convert(date, A.dFechaCreacion, 103) between convert(date, :fechaIncio, 103) AND convert(date, :fechaFin, 103)  "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Distrito", "Número de Convenios",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, false, false
			),
	INDICADOR_12(12L,
			"Cantidad de casos atendidos en el modulo de Justicia Restaurativa",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Modulo","Total"),
			" SELECT 'Atención en CJR' as Modulo, COUNT (*) as Total" +
			" FROM  NumeroExpediente NE ,Expediente E" +
			" WHERE NE.Expediente_id = E.Expediente_id" +
			" AND NE.JerarquiaOrganizacional_id = 7" +
			" AND convert(date, E.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103)",
			0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
			"Modulo", "Número de Casos Atendidos",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, true
			),
	INDICADOR_13(13L,
			"Cantidad de personas que acudieron al modulo de atencion temprana en general",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Modulo","IndiceDeAtencion"),
			" SELECT  'Atención  Temprana' as Modulo,COUNT (*) As 'IndiceDeAtencion'" +
			" FROM Expediente E, NumeroExpediente NE, CatDiscriminante cD, CatDistrito D, CatDiscriminante Dis" +
			" WHERE E.Expediente_id= NE.Expediente_id" +
			" AND E.catDiscriminante_id = cD.catDiscriminante_id" +
			" AND cD.catDistrito_id = D.catDistrito_id" +
			" AND NE.JerarquiaOrganizacional_id = 45" +
			" AND Dis.catDiscriminante_id = E.catDiscriminante_id" +
			" AND convert(date, E.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) ",
			0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
			"Modulo", "Número de Personas",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, true
			),
	INDICADOR_14(14L,
			"Difusión del principio de publicidad del NSJP",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "AdienciasPublicas"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal', COUNT (*) AS '#AdienciasPublicas' "+
					" FROM  Audiencia A, NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE A.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND convert (date, A.dFechaAudiencia, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND A.TipoAudiencia_val not in (1718,1717,292) "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Distrito", "Número de Audiencias Publicas",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_17(17L,
			"Atender puntualmente todas las solicitudes de audiencia",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "Iden. de Audiencia", "FechaDeSolicitud", "FechaDeAtencion"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal', "+
					" a.Audiencia_id as AudienciaId,Doc.dFechaCreacion as 'FechaDeSolicitud',A.dFechaAudiencia AS 'FechaDeAtencion' "+
					" FROM SolicitudAudiencia SA, Solicitud S, Audiencia A,Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE SA.SolicitudAudiencia_id = S.Solicitud_id "+
					" AND A.Audiencia_id = SA.Audiencia_id "+
					" AND Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND A.dFechaAudiencia  is not null "+
					" AND convert(date, Doc.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND S.TipoSolicitud_val = 1771 "+
					" AND cD.catDistrito_id = D.catDistrito_id ",
					0, 2, 1,
					"Distrito", "Número de Audiencias Publicas",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_18(18L,
			"Dictar autos a la totalidad de las solicitudes y promociones formuladas al Poder Judicial",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "TipoSolicitud", "Estado", "#EstatusCerrado"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',CONVERT (varchar,S.TipoSolicitud_val)+'-'+VT.cValor AS 'TipoSolicitud',Convert (varchar,(S.Estatus_val)) +' - '+ V.cValor AS 'Estado', COUNT (*) AS '#EstatusCerrado' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D,Valor V,Valor VT "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND convert(date,Doc.dFechaCreacion,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND S.TipoSolicitud_val <> 1771 "+
					" AND S.Estatus_val = 1776  "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND S.Estatus_val = V.Valor_id "+
					" AND VT.Valor_id =  S.TipoSolicitud_val "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre,S.TipoSolicitud_val,VT.cValor, S.Estatus_val,V.cValor ",
					1, 4, 2,
					"Agencia/Tribunal", "Número de Solictudes",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_24(24L,
			"Atención a Víctimas",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "TipoSolicitud", "Estado", "#EstatusEnProceso"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',CONVERT (varchar,S.TipoSolicitud_val)+' '+VT.cValor AS 'TipoSolicitud',Convert (varchar,(S.Estatus_val)) +' - '+ V.cValor AS 'Estado', COUNT (*) AS '#EstatusEnProceso' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D,Valor V,Valor VT "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND convert(date, Doc.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND S.TipoSolicitud_val in (2809,2810,2811) "+
					" AND S.Estatus_val = 2096  "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND S.Estatus_val = V.Valor_id "+
					" AND VT.Valor_id =  S.TipoSolicitud_val "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre,S.TipoSolicitud_val,VT.cValor, S.Estatus_val,V.cValor ",
					2, 4, 3,
					"Tipo de Solicitud", "Número de Solicitudes",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_25(25L,
			"Ejecución del módulo de Atención a Víctimas",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "TipoSolicitud", "Estado", "#EstatusCerrado"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',CONVERT (varchar,S.TipoSolicitud_val)+'-'+VT.cValor AS 'TipoSolicitud',Convert (varchar,(S.Estatus_val)) +' - '+ V.cValor AS 'Estado', COUNT (*) AS '#EstatusCerrado' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D,Valor V,Valor VT "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND convert(date, Doc.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND S.TipoSolicitud_val in (2809,2810,2811)  "+
					" AND S.Estatus_val = 1776 "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND S.Estatus_val = V.Valor_id "+
					" AND VT.Valor_id =  S.TipoSolicitud_val "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre,S.TipoSolicitud_val,VT.cValor, S.Estatus_val,V.cValor ",
					2, 4, 3,
					"Tipo de Solicitud", "Número de Solicitudes",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, false, false		
			),
	INDICADOR_26(26L,
			"Ejecución del módulo de acción penal privada",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#DeDenuncias"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#DeDenuncias' "+
					" FROM Expediente E, CatDiscriminante cD, CatDistrito D, Turno T "+
					" WHERE E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDiscriminante_id = D.catDistrito_id "+
					" AND convert(date, E.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND T.Expediente_id = E.Expediente_id "+
					" AND T.cTipoTurno ='JUDICIAL' "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Denuncias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_29(29L,
			"Justicia Restaurativa",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#ExpedientesCanalizadosJAR"),
			" SELECT  cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ExpedientesCanalizadosJAR' "+
					" FROM NumeroExpediente NE ,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id  "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND NE.JerarquiaOrganizacional_id = 7 "+
					" AND convert(date, NE.dFechaApertura, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Expedientes Canalizados JAR",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_30(30L,
			"Ejecución del Módulo de Justicia Alternativa Restaurativa (mediación, conciliación y justicia restaurativa)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#ExpedientesCanalizadosJAR"),
			" SELECT  cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ExpedientesCanalizadosJAR'"+
					" FROM NumeroExpediente NE ,Expediente E, CatDiscriminante cD, CatDistrito D, Actividad A"+
					" WHERE NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id  "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND E.Expediente_id = A.Expediente_id"+
					" AND A.TipoActividad_val = 2290"+
					" AND convert (date, NE.dFechaApertura, 103)  between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre  ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Expedientes Canalizados JAR",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_32(32L,
			"Protección al inocente",
			DEF,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#SolicitudDefensor"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) AS '#SolicitudDefensor' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND convert(date,Doc.dFechaCreacion,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND S.TipoSolicitud_val <> 1772 "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Distrito", "Número de Solicitudes de Defensor",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_33(33L,
			"Dar vista oportuna al Defensor Público en caso de detención del imputado",
			DEF,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#SolicitudDefensor"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito', "+
					" cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) AS '#SolicitudDefensor' "+
					" FROM  Solicitud S, Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND convert(date,Doc.dFechaCreacion,103) between convert(date,:fechaIncio,103) AND  convert(date,:fechaFin,103) "+
					" AND S.TipoSolicitud_val <> 1772 "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" GROUP BY cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Distrito", "Número de Solicitudes de Defensor",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_34(34L,
			"Notificación de fecha, hora, lugar y juez de audiencia al defensor público",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#Notificaciones"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Tribunal',COUNT (*) as '#Notificaciones' "+
					" FROM Notificacion N, Audiencia A, NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE N.Audiencia_id = A.Audiencia_id "+
					" AND A.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert(date, dFechaCitado, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Notificaciones",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_36(36L,
			"Evitar impunidad",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "Tipo", "#Total"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal','Convenio' AS 'Tipo',COUNT (*) as '#Total' "+
					" FROM Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND E.Expediente_id = A.Expediente_id"+
					" AND A.TipoActividad_val = 6274"+
					" AND A.Documento_id is not null"+
					" AND convert(date, A.dFechaCreacion,103) between convert(date,:fechaIncio,103) AND  convert(date,:fechaFin,103)"+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre"+
					" UNION  "+
					" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Tribunal','Medida Cautelar' AS 'Tipo',COUNT (*) as '#Total'"+
					" FROM Medida M , MedidaCautelar MC, NumeroExpediente NE , Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE M.Medida_id = MC.MedidaCautelar_id"+
					" AND M.NumeroExpediente_id = NE.NumeroExpediente_id"+
					" AND NE.Expediente_id = E.Expediente_id"+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id"+
					" AND cD.catDistrito_id = D.catDistrito_id"+
					" AND convert (date, M.dFechaInicio, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103)"+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre",
					1, 3, 2,
					"Agencia/Tribunal", "Tipo de Medida",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_37(37L,
			"Ejecutar módulo de acuerdos reparatorios",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "ConveniosCumplidos"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ConveniosCumplidos'   "+
					" FROM Convenio C,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE C.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert (date, C.dFechaInicio, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Convenios Cumplidos",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, false, false		
			),
	INDICADOR_38(38L,
			"Ejecutar módulo de medidas cautelares",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Tribunal", "#MedidasCautelares"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Tribunal',COUNT (*) as '#MedidasCautelares' "+
					" FROM Medida M , MedidaCautelar MC, NumeroExpediente NE , Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE M.Medida_id = MC.MedidaCautelar_id "+
					" AND M.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert(date,M.dFechaInicio,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103)" +
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Medias Cautelares",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_39(39L,
			"Sustentación del Fiscal Investigador de la Teoría del Caso",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#TeoriaDelCaso"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#TeoriaDelCaso'  "+
					" FROM OficioEstructurado OE,Documento Doc, Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE OE.TipoOficio_val = 2382 "+
					" AND OE.Documento_id = Doc.Documento_id "+
					" AND Doc.Documento_id = A.Documento_id "+
					" AND A.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert(date,Doc.dFechaCreacion,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Teorias del Caso",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_42(42L,
			"Esclarecimiento de los hechos",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#TeoriaDelCaso"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#TeoriaDelCaso'  "+
					" FROM OficioEstructurado OE,Documento Doc, Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE OE.TipoOficio_val = 2382 "+
					" AND OE.Documento_id = Doc.Documento_id "+
					" AND Doc.Documento_id = A.Documento_id "+
					" AND A.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert(date, Doc.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Teoria del Caso",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_47(47L,
			"Reparación del daño",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#ConvenioCumplidos"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ConvenioCumplidos' "+
					" FROM Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D  "+
					" WHERE E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND E.Expediente_id = A.Expediente_id"+
					" AND A.TipoActividad_val = 4004"+
					" AND A.Documento_id is not null"+
					" AND convert (date, A.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103)"+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Convenios Cumplidos",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_48(48L,
			"Reclamación de reparación del daño del fiscal investigador al imputado, en la formulación de imputación y acusación",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#ConveniosCumplidos"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',COUNT (*) as '#ConvenioCumplidos' "+
					" FROM Actividad A, Expediente E, CatDiscriminante cD, CatDistrito D  "+
					" WHERE E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND E.Expediente_id = A.Expediente_id"+
					" AND A.TipoActividad_val = 4004"+
					" AND A.Documento_id is not null"+
					" AND convert(date, A.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					1, 2, 0,
					"Agencia/Tribunal", "Número de Convenios Cumplidos",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_51(51L,
			"Ejecución de audiencia pública",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "Tipos de Audiencia", "#Audiencias"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal',V.cValor as 'TiposAudiencia',COUNT (*) as '#Audiencias' "+
					" FROM Audiencia A, NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D, Valor V "+
					" WHERE A.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert(date,A.dFechaAudiencia,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND V.Valor_id = A.TipoAudiencia_val "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre,cValor ",
					1, 3, 2,
					"Agencia/Tribunal", "Número de Audiencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_52(52L,
			"Solicitud al Poder Judicial, de señalamiento de fecha,  hora y lugar de audiencias para dar impulso al proceso penal",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito", "Agencia/Tribunal", "#SolicitudesAuidiencia"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Agencia/Tribunal', COUNT (*) as '#SolicitudesAuidiencia' "+
					" FROM SolicitudAudiencia SA, Solicitud S, Audiencia A,Documento Doc, NumeroExpediente NE,Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE SA.SolicitudAudiencia_id = S.Solicitud_id "+
					" AND A.Audiencia_id = SA.Audiencia_id "+
					" AND Doc.Documento_id = S.Solicitud_id "+
					" AND S.NumeroExpediente_id= NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND A.dFechaAudiencia  is not null "+
					" AND convert(date,Doc.dFechaCreacion,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND S.TipoSolicitud_val = 1771 "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Agencia/Tribunal", "Número de Solicitudes de Audiencia",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_53(53L,
			"Ejecución del principio de igualdad entre las partes",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Tribunal", "#Notificaciones"),
			" SELECT cClaveDistrito+'-'+D.cNombre AS 'Distrito',cD.cClaveDiscriminante +'-'+cD.cNombre AS 'Tribunal',COUNT (*) as '#Notificaciones' "+
					" FROM Notificacion N, Audiencia A, NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE N.Audiencia_id = A.Audiencia_id "+
					" AND A.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert(date,dFechaCitado,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Agencia/Tribunal", "Número de Notificaciones",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_55(5L,
			"Ejecución de sanciones",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#NumeroDeSentencias"),
			" SELECT D.cClaveDistrito +'-'+D.cNombre As 'Distrito', cD.cClaveDiscriminante+'-'+ cD.cNombre AS 'Agencia/Tribunal', COUNT (*) AS '#NumeroDeSentencias'  "+
					" FROM  Sentencia S,NumeroExpediente NE, Expediente E, CatDiscriminante cD, CatDistrito D "+
					" WHERE TipoSentencia_val in (323,324) "+
					" AND S.NumeroExpediente_id = NE.NumeroExpediente_id "+
					" AND NE.Expediente_id = E.Expediente_id "+
					" AND cD.catDiscriminante_id = E.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND convert(date,S.dFechaInicioPena,103)between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Agencia/Tribunal", "Número de Sentencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_56(56L,
			"Restaurar la armonía social",
			SSP,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia/Tribunal", "#EjecucionRS"),
			" SELECT D.cClaveDistrito +'-'+D.cNombre As 'Distrito', cD.cClaveDiscriminante+'-'+ cD.cNombre AS 'Agencia/Tribunal', COUNT (*) AS '#EjecucionRS'   "+
					" FROM NumeroExpediente NE, Expediente E,CatDiscriminante, CatDiscriminante cD,CatDistrito D "+
					" WHERE NE.Expediente_id = E.Expediente_id "+
					" AND E.catDiscriminante_id = cD.catDiscriminante_id "+
					" AND cD.catDistrito_id = D.catDistrito_id "+
					" AND NE.JerarquiaOrganizacional_id = 60 "+
					" AND convert(date,NE.dFechaApertura,103) between convert(date,:fechaIncio,103) AND  convert(date,:fechaFin,103) "+
					" GROUP BY D.cClaveDistrito,D.cNombre,cD.cClaveDiscriminante,cD.cNombre ",
					0, 2, 1,
					"Agencia/Tribunal", "Número de Ejecuciones de RS",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_57(57L,
			"Número de audiencias programadas, reprogramadas y canceladas",
			PJ,
			Arrays.asList("fechaIncio","fechaFin","estadoProgramada","estadoReprogramada","estadoCancelada"),
			Arrays.asList("Estado audiencia","#Audiencias"),
			"SELECT v.cValor as estadoAudiencia, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN Valor v "+
					" ON a.Estatus_val = v.Valor_id "+
					" WHERE v.Valor_id in (:estadoProgramada, :estadoReprogramada, :estadoCancelada) " +
					" AND convert(date,a.dFechaAudiencia,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY v.cValor "+
					" ORDER BY v.cValor ",
					0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
					"Estado de Audiencia", "Número de Audiencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_58(58L,
			"Número de audiencias por causa",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Número de causa","#Audiencias"),
			"SELECT ne.cNumeroExpediente, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN NumeroExpediente ne "+
					" ON a.NumeroExpediente_id = ne.NumeroExpediente_id "+
					" WHERE convert(date,a.dFechaAudiencia,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY ne.cNumeroExpediente "+
					" ORDER BY ne.cNumeroExpediente ",
					0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
					"Número Expediente", "Número de Audiencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_59(59L,
			"Número de audiencias programadas vs realizadas",
			PJ,
			Arrays.asList("fechaIncio","fechaFin","estadoProgramada","estadoFinalizada"),
			Arrays.asList("Estado audiencia","#Audiencias"),
			"SELECT v.cValor as EstadoAudiencia, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN Valor v "+
					" ON a.Estatus_val = v.Valor_id "+
					" WHERE v.Valor_id in (:estadoProgramada, :estadoFinalizada) " +
					" AND convert(date, a.dFechaAudiencia,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY v.cValor "+
					" ORDER BY v.cValor ",
					0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
					"Estado Audiencia", "Número de Audiencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_60(60L,
			"Tiempo programado vs tiempo real de audiencia",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Folio audiencia", "Fecha inicio de audiencia", "Fecha fin de audiencia", "Duración estimada", "Duración real"),
			"SELECT ISNULL(cFolioAudiencia,'Sin folio') as folioAudiencia, dFechaAudiencia, dFechaHoraFin, " +
					" ISNULL(iDuracionEstimada,0) as duracionEstimada, DATEDIFF(minute,dFechaAudiencia,dFechaHoraFin) as duracionReal "+
					" FROM Audiencia a " +
					" WHERE convert(date,a.dFechaAudiencia,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" ORDER BY ISNULL(cFolioAudiencia,'Sin folio')" , 
					0, 4, -1, //X,Y,Serie (-1 no requiere la serie)
					"Folio de Audiencia", "Duración en Minutos",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_61(61L,
			"Número de audiencias por juez",
			PJ,
			Arrays.asList("fechaIncio","fechaFin","rolIdJuez"),
			Arrays.asList("Juez","#Audiencias"),
			"SELECT f.cNombreFuncionario+' '+f.cApellidoPaternoFuncionario+' '+" +
					"f.cApellidoMaternoFuncionario as nombreCompleto, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN FuncionarioAudiencia fa "+
					" ON a.Audiencia_id = fa.Audiencia_id "+
					" INNER JOIN Funcionario f "+
					" ON f.iClaveFuncionario = fa.iClaveFuncionario "+
					" INNER JOIN Usuario u "+
					" ON f.iClaveFuncionario = u.iClaveFuncionario "+
					" INNER JOIN UsuarioRol ur "+
					" ON u.Usuario_id = ur.Usuario_id "+
					" WHERE ur.Rol_id = :rolIdJuez " +
					" AND convert(date,a.dFechaAudiencia,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY f.cNombreFuncionario+' '+f.cApellidoPaternoFuncionario+' '+f.cApellidoMaternoFuncionario",
					0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
					"Juez", "Número de Audiencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_62(62L,
			"Número de audiencias por tipo",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Tipo de audiencia","#Audiencias"),
			"SELECT v.cValor, count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" INNER JOIN Valor v "+
					" ON a.TipoAudiencia_val = v.Valor_id "+
					" WHERE convert(date,a.dFechaAudiencia,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY v.cValor",
					0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
					"Tipo de Audiencia", "Número de Audiencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	INDICADOR_63(63L,
			"Número de audiencias por sala",
			PJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Nombre de sala","#Audiencias"),
			"SELECT ISNULL(sa.cNombreSala,ISNULL(s.cDomicilioSala,ISNULL(st.cDomicilioSala,'Sin sala asignada'))) as nombreSala, "+
					" count(*) as numeroAudiencias "+
					" FROM Audiencia a "+
					" LEFT JOIN SalaAudiencia sa "+
					" ON a.SalaAudiencia_id = sa.SalaAudiencia_id "+
					" LEFT JOIN Sala s "+
					" ON s.Sala_id = s.Sala_id "+
					" LEFT JOIN SalaTemporal st "+
					" ON a.SalaTemporal_id = st.SalaTemporal_id "+
					" WHERE convert(date,a.dFechaAudiencia,103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" GROUP BY ISNULL(sa.cNombreSala,ISNULL(s.cDomicilioSala,ISNULL(st.cDomicilioSala,'Sin sala asignada'))) "+
					" ORDER BY ISNULL(sa.cNombreSala,ISNULL(s.cDomicilioSala,ISNULL(st.cDomicilioSala,'Sin sala asignada'))) ",
					0, 1, -1, //X,Y,Serie (-1 no requiere la serie)
					"Nombre de Sala", "Número de Audiencias",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true		
			),
	//10.ModuloDeAtencionTempranaMovimientoEnElModuloDeAtencionTemprana
	INDICADOR_64(64L,
			"Movimientos en el modulo de atencion temprana",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("MovimientoAtencionTemprana","TipoDeExpediente","Total"),
			" EXEC usp_CanalizacionesDesdeAtencionTemprana :fechaIncio, :fechaFin ",
			0, 2, 1,
			"Movimientos Atención Temprana", "Número de Movimientos",
			OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
			//corresponde a 18.4Indicador de AtencionCiudadana
	INDICADOR_65(65L,
			"Eficiencia para el nuevo sistema de justicia penal",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("ID","Descripcion","Totales"),
			" EXEC usp_IndicadorDeAtencionCiudadana :fechaIncio, :fechaFin ",
			1, 2, -1, //X,Y,Serie (-1 no requiere la serie)
			"Descripción", "Número de Total",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, true
			),
			//corresponde a 	5. IncidenciaDelictivaPorGenero
	INDICADOR_66(66L,
			"Incidencia delictiva por genero",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Clave Delito", "Delito", "Total Mujeres", "Total Hombres"),
			" EXEC usp_IncidenciasDelictivasPorGenero :fechaIncio, :fechaFin ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"Delito", "Número de Incidencias Delictivas",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, true
			),
			//corresponde a 	6. IncidenciaDelictivaPorGeneroINEGI
	INDICADOR_67(
			67L,
			"Incidencia delictiva por genero INEGI",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Clave Delito", "Delito", "Total Mujeres", "Total Hombres"),
			" EXEC usp_IncidenciasDelictivasPorGeneroINEGI :fechaIncio, :fechaFin ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"Delito", "Número de Incidencias Delictivas",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, true
			),
			//corresponde a 1  Incidencia de delitos estatal
	INDICADOR_68(68L,
			"Incidencia Sistema Nacional de Seguridad Pública",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Catalogo Delito","Clave Delito", "Delito", "Total","Con violencia","Sin violencia",
					"Arma Blanca","Arma de Fuego","Otras","Sin Datos"),
			" EXEC usp_IncidenciaSistemaNacional :fechaIncio, :fechaFin ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"Delito", "Número de Incidencias Delictivas",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
			//corresponde a 	1. Policía Federal_v1.2
	INDICADOR_69(
			69L,
			"Policia Federal",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito", "Número Carpeta de Investigación", "Unidad Especializada", "Fecha y Hora Hechos Inicio",
					"Fecha y Hora Hechos Fin","Delito Principal","Violencia","Hechos municipio","Hechos tramo y lugar",
					"Estatus de la carpeta de investigación"),
			" EXEC usp_PoliciaFederal :fechaIncio, :fechaFin ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"Delito", "Número de Incidencias Delictivas",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_70(
			70L,
			"Cantidad de carpetas de investigación por estatus, por delito en cada modalidad",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Entidad Federativa","Estatus carpeta","Delito","Modalidad","Cantidad "),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select [Entidad Federativa], [Estatus carpeta], [Delito], [Modalidad], [Cantidad] from( " +
			"select " +
			        "'Entidad Federativa'= CASE " +
						"WHEN GROUPING([EntidadFederativa].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([EntidadFederativa].[cNombre], 'N/D') " +
					"END, " +
			        "'Estatus carpeta'= CASE " +
						"WHEN GROUPING([Valor].[cValor])=1 THEN ' Total' " +
						"ELSE ISNULL([Valor].[cValor], 'N/D') " +
					"END, " +
			        "'Delito'= CASE " +
						"WHEN GROUPING([CatDelito].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([CatDelito].[cNombre], 'N/D') " +
					"END, "+
			        "'Modalidad'= CASE " +
						"WHEN GROUPING([catDelitoModalidad].[cDescripcion] )=1 THEN ' Total' " +
						"ELSE ISNULL([catDelitoModalidad].[cDescripcion] , 'N/D') " +
					"END, " +
			        "count(distinct [Expediente].[Expediente_id]) 'Cantidad' " +
			"from " +
				"[dbo].[Expediente] [Expediente] " +
					"inner join [dbo].[Delito] [Delito] " +
					"on [Expediente].[Expediente_id] = [Delito].[Expediente_id] " +
						"left outer join [dbo].[DelitoPersona] [DelitoPersona] " +
						"on [Delito].[Delito_id] = [DelitoPersona].[Delito_id] " +
							"left outer join [dbo].[catDelitoModalidad] [catDelitoModalidad] " +
							"on [DelitoPersona].[catDelitoModalidad_id] = [catDelitoModalidad].[catDelitoModalidad_id] " +
								"inner join [dbo].[CatDelito] [CatDelito] " +
								"on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id] " +
									"left outer join [dbo].[Hecho] [Hecho] " +
									"on [Expediente].[Expediente_id] = [Hecho].[Expediente_id] " +
										"left outer join [dbo].[Domicilio] [Domicilio] " +
										"on [Hecho].[Lugar_id] = [Domicilio].[Domicilio_id] " +
											"left outer join [dbo].[EntidadFederativa] [EntidadFederativa] " +
											"on [Domicilio].[EntidadFederativa_id] = [EntidadFederativa].[EntidadFederativa_id] " +
												"inner join [dbo].[NumeroExpediente] [NumeroExpediente] " +
												"on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id] " +
													"inner join [dbo].[Valor] [Valor] " +
													"on [NumeroExpediente].[Estatus_val] = [Valor].[Valor_id] " +
			"where " +
				"(convert(date, [Expediente].[dFechaCreacion], 103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) and " +
				"([NumeroExpediente].[JerarquiaOrganizacional_id] = 10) " +
			    
			"group by [EntidadFederativa].[cNombre], " +
			    "[Valor].[cValor], " +
				"[CatDelito].[cNombre], " +
			    "[catDelitoModalidad].[cDescripcion] " +
			"WITH CUBE " +
			") as a " +
			"where a.[Entidad Federativa] not like ' Total' and " +
			"[Estatus carpeta] not like ' Total' " +
			"order by [Entidad Federativa], [Estatus carpeta], [Delito], [Modalidad] ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_71(
			71L,
			"Cantidad de carpetas de investigación por etapas",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Estatus Carpeta Investigación","Cantidad"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date" +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select v.cValor 'Estatus Carpeta Investigación', count(*) 'Cantidad' " +
			"from NumeroExpediente ne, Valor v " +
			"where ne.Estatus_val = v.Valor_id " +
			"and convert(date, ne.dFechaApertura, 103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin), 103) " +
			"and ne.JerarquiaOrganizacional_id = 10 " +
			"group by v.cValor ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_72(
			72L,
			"Incidencia de delitos estatal en carpetas iniciadas (delito principal)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Entidad Federativa","Delito","Cantidad"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select [Entidad Federativa], [Delito], [Cantidad] from( " +
			"select " +
			        "'Entidad Federativa'= CASE  " +
						"WHEN GROUPING([EntidadFederativa].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([EntidadFederativa].[cNombre], 'N/D') " +
					"END, " +
			        "'Delito'= CASE  " +
						"WHEN GROUPING([CatDelito].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([CatDelito].[cNombre], 'N/D') " +
					"END, " +
			        "count(distinct [Expediente].[Expediente_id]) 'Cantidad' 	  " +
			"from " +
				"[dbo].[Expediente] [Expediente]  " +
					"inner join [dbo].[Delito] [Delito]  " +
					"on [Expediente].[Expediente_id] = [Delito].[Expediente_id]  " +
						"inner join [dbo].[CatDelito] [CatDelito]  " +
						"on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id]  " +
							"left outer join [dbo].[Hecho] [Hecho]  " +
							"on [Expediente].[Expediente_id] = [Hecho].[Expediente_id]  " +
								"left outer join [dbo].[Domicilio] [Domicilio]  " +
								"on [Hecho].[Lugar_id] = [Domicilio].[Domicilio_id]  " +
									"left outer join [dbo].[EntidadFederativa] [EntidadFederativa]  " +
									"on [Domicilio].[EntidadFederativa_id] = [EntidadFederativa].[EntidadFederativa_id]  " +
											"inner join [dbo].[NumeroExpediente] [NumeroExpediente]  " +
											"on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id]  " +
			"where " +
				"(convert (date, [Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) and " +
				"([NumeroExpediente].[JerarquiaOrganizacional_id] = 10) and " +
				"([Delito].[bEsPrincipal] =1)	    " +
			"group by [EntidadFederativa].[cNombre], " +
				"[CatDelito].[cNombre] " +
			"WITH CUBE " +
			") as a " +
			"where a.[Entidad Federativa] not like ' Total' " +
			"order by [Entidad Federativa], [Delito] ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_73(
			73L,
			"Incidencia de delitos estatal en carpetas iniciadas (todos los delitos)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Entidad Federativa","Delito","Cantidad"),
			"declare @ld_fechaIni datetime " +
			"declare @ld_fechaFin datetime " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select [Entidad Federativa], [Delito], [Cantidad] from( " +
			"select " +
			        "'Entidad Federativa'= CASE  " +
						"WHEN GROUPING([EntidadFederativa].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([EntidadFederativa].[cNombre], 'N/D') " +
					"END, " +
			        "'Delito'= CASE  " +
						"WHEN GROUPING([CatDelito].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([CatDelito].[cNombre], 'N/D') " +
					"END, " +
			        "count([Expediente].[Expediente_id]) 'Cantidad' 	  " +
			"from " +
				"[dbo].[Expediente] [Expediente]  " +
					"inner join [dbo].[Delito] [Delito]  " +
					"on [Expediente].[Expediente_id] = [Delito].[Expediente_id]  " +
						"inner join [dbo].[CatDelito] [CatDelito]  " +
						"on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id]  " +
							"left outer join [dbo].[Hecho] [Hecho]  " +
							"on [Expediente].[Expediente_id] = [Hecho].[Expediente_id]  " +
								"left outer join [dbo].[Domicilio] [Domicilio]  " +
								"on [Hecho].[Lugar_id] = [Domicilio].[Domicilio_id]  " +
									"left outer join [dbo].[EntidadFederativa] [EntidadFederativa]  " +
									"on [Domicilio].[EntidadFederativa_id] = [EntidadFederativa].[EntidadFederativa_id]  " +
											"inner join [dbo].[NumeroExpediente] [NumeroExpediente]  " +
											"on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id]  " +
			"where " +
				"(convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) AND convert(date, @ld_fechaFin, 103)) and " +
				"([NumeroExpediente].[JerarquiaOrganizacional_id] = 10)    " +
			"group by [EntidadFederativa].[cNombre], " +
				"[CatDelito].[cNombre] " +
			"WITH CUBE " +
			") as a " +
			"where a.[Entidad Federativa] not like ' Total' " +
			"order by [Entidad Federativa], [Delito] ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_74(
			74L,
			"Incidencia de delitos estatal en carpetas iniciadas por modalidad (delito principal)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Entidad Federativa","Delito","Modalidad","Cantidad"),
			"declare @ld_fechaIni date" +
			"declare @ld_fechaFin date" +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select [Entidad Federativa], [Delito], [Modalidad], [Cantidad] from( " +
			"select " +
			        "'Entidad Federativa'= CASE  " +
						"WHEN GROUPING([EntidadFederativa].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([EntidadFederativa].[cNombre], 'N/D') " +
					"END, " +
			        "'Delito'= CASE  " +
						"WHEN GROUPING([CatDelito].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([CatDelito].[cNombre], 'N/D') " +
					"END, " +
			        "'Modalidad'= CASE  " +
						"WHEN GROUPING([catDelitoModalidad].[cDescripcion] )=1 THEN ' Total' " +
						"ELSE ISNULL([catDelitoModalidad].[cDescripcion] , 'N/D') " +
					"END, " +
			        "count(distinct [Expediente].[Expediente_id]) 'Cantidad' 	 	 " +
			"from " +
				"[dbo].[Expediente] [Expediente]  " +
					"inner join [dbo].[Delito] [Delito]  " +
					"on [Expediente].[Expediente_id] = [Delito].[Expediente_id]  " +
						"left outer join [dbo].[DelitoPersona] [DelitoPersona]  " +
						"on [Delito].[Delito_id] = [DelitoPersona].[Delito_id]  " +
							"left outer join [dbo].[catDelitoModalidad] [catDelitoModalidad]  " +
							"on [DelitoPersona].[catDelitoModalidad_id] = [catDelitoModalidad].[catDelitoModalidad_id]  " +
								"inner join [dbo].[CatDelito] [CatDelito]  " +
								"on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id]  " +
									"left outer join [dbo].[Hecho] [Hecho]  " +
									"on [Expediente].[Expediente_id] = [Hecho].[Expediente_id]  " +
										"left outer join [dbo].[Domicilio] [Domicilio]  " +
										"on [Hecho].[Lugar_id] = [Domicilio].[Domicilio_id]  " +
											"left outer join [dbo].[EntidadFederativa] [EntidadFederativa]  " +
											"on [Domicilio].[EntidadFederativa_id] = [EntidadFederativa].[EntidadFederativa_id]  " +
												"inner join [dbo].[NumeroExpediente] [NumeroExpediente]  " +
												"on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id] " +
			"where " +
				"(convert (date, [Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) and " +
				"([NumeroExpediente].[JerarquiaOrganizacional_id] = 10)  and " +
				"([Delito].[bEsPrincipal] =1)	 " +
			"group by [EntidadFederativa].[cNombre], " +
				"[CatDelito].[cNombre], " +
			    "[catDelitoModalidad].[cDescripcion]  " +
			"WITH CUBE " +
			") as a " +
			"where a.[Entidad Federativa] not like ' Total' " +
			"order by [Entidad Federativa], [Delito], [Modalidad] " ,
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_75(
			75L,
			"Incidencia de delitos estatal en carpetas iniciadas por modalidad (todos los delitos)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Entidad Federativa","Estatus carpeta","Delito","Modalidad","Cantidad "),
			"declare @ld_fechaIni datetime " +
			"declare @ld_fechaFin datetime " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select [Entidad Federativa], [Delito], [Modalidad], [Cantidad] from( " +
			"select " +
			        "'Entidad Federativa'= CASE  " +
						"WHEN GROUPING([EntidadFederativa].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([EntidadFederativa].[cNombre], 'N/D') " +
					"END, " +
			        "'Delito'= CASE  " +
						"WHEN GROUPING([CatDelito].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([CatDelito].[cNombre], 'N/D') " +
					"END, " +
			        "'Modalidad'= CASE  " +
						"WHEN GROUPING([catDelitoModalidad].[cDescripcion] )=1 THEN ' Total' " +
						"ELSE ISNULL([catDelitoModalidad].[cDescripcion] , 'N/D') " +
					"END, " +
			        "count(distinct [Expediente].[Expediente_id]) 'Cantidad' 	 	 " +
			"from " +
				"[dbo].[Expediente] [Expediente]  " +
					"inner join [dbo].[Delito] [Delito]  " +
					"on [Expediente].[Expediente_id] = [Delito].[Expediente_id]  " +
						"left outer join [dbo].[DelitoPersona] [DelitoPersona]  " +
						"on [Delito].[Delito_id] = [DelitoPersona].[Delito_id]  " +
							"left outer join [dbo].[catDelitoModalidad] [catDelitoModalidad]  " +
							"on [DelitoPersona].[catDelitoModalidad_id] = [catDelitoModalidad].[catDelitoModalidad_id]  " +
								"inner join [dbo].[CatDelito] [CatDelito]  " +
								"on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id]  " +
									"left outer join [dbo].[Hecho] [Hecho]  " +
									"on [Expediente].[Expediente_id] = [Hecho].[Expediente_id]  " +
										"left outer join [dbo].[Domicilio] [Domicilio]  " +
										"on [Hecho].[Lugar_id] = [Domicilio].[Domicilio_id]  " +
											"left outer join [dbo].[EntidadFederativa] [EntidadFederativa]  " +
											"on [Domicilio].[EntidadFederativa_id] = [EntidadFederativa].[EntidadFederativa_id]  " +
												"inner join [dbo].[NumeroExpediente] [NumeroExpediente]  " +
												"on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id] " +
			"where " +
				"(convert (date, [Expediente].[dFechaCreacion], 103) between convert(date, @ld_fechaIni, 103) and convert(date,@ld_fechaFin, 103)) and " +
				"([NumeroExpediente].[JerarquiaOrganizacional_id] = 10)    " +
			"group by [EntidadFederativa].[cNombre], " +
				"[CatDelito].[cNombre], " +
			    "[catDelitoModalidad].[cDescripcion]  " +
			"WITH CUBE " +
			") as a " +
			"where a.[Entidad Federativa] not like ' Total' " +
			"order by [Entidad Federativa], [Delito], [Modalidad] " ,
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_76(
			76L,
			"Incidencia de delitos municipal en carpetas iniciadas (delito principal)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList(" Entidad Federativa","Municipio","Delito","Cantidad"),
			"declare @ld_fechaIni datetime " +
			"declare @ld_fechaFin datetime " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select [Entidad Federativa], [Municipio], [Delito], [Cantidad] from( " +
			"select " +
			        "'Entidad Federativa'= CASE  " +
						"WHEN GROUPING([EntidadFederativa].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([EntidadFederativa].[cNombre], 'N/D') " +
					"END, " +
			        "'Municipio'= CASE  " +
						"WHEN GROUPING([Municipio].[cNombreMunicipio])=1 THEN ' Total' " +
						"ELSE ISNULL([Municipio].[cNombreMunicipio], 'N/D') " +
					"END, " +
			        "'Delito'= CASE  " +
						"WHEN GROUPING([CatDelito].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([CatDelito].[cNombre], 'N/D') " +
					"END, " +
			        "count(distinct [Expediente].[Expediente_id]) 'Cantidad' 	  " +
			"from " +
				"[dbo].[Expediente] [Expediente]  " +
					"inner join [dbo].[Delito] [Delito]  " +
					"on [Expediente].[Expediente_id] = [Delito].[Expediente_id]  " +
						"inner join [dbo].[CatDelito] [CatDelito]  " +
						"on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id]  " +
							"left outer join [dbo].[Hecho] [Hecho]  " +
							"on [Expediente].[Expediente_id] = [Hecho].[Expediente_id]  " +
								"left outer join [dbo].[Domicilio] [Domicilio]  " +
								"on [Hecho].[Lugar_id] = [Domicilio].[Domicilio_id]  " +
									"left outer join [dbo].[EntidadFederativa] [EntidadFederativa]  " +
									"on [Domicilio].[EntidadFederativa_id] = [EntidadFederativa].[EntidadFederativa_id]  " +
										"left outer join [dbo].[Municipio] [Municipio]  " +
										"on [Domicilio].[Municipio_id] = [Municipio].[Municipio_id]  " +
											"inner join [dbo].[NumeroExpediente] [NumeroExpediente]  " +
											"on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id]  " +
			"where " +
				"(convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) AND convert(date, @ld_fechaFin, 103)) and " +
				"([NumeroExpediente].[JerarquiaOrganizacional_id] = 10)  and " +
				"([Delito].[bEsPrincipal] =1)	 " +
			"group by [EntidadFederativa].[cNombre], " +
			    "[Municipio].[cNombreMunicipio], " +
				"[CatDelito].[cNombre] " +
			"WITH CUBE " +
			") as a " +
			"where a.[Entidad Federativa] not like ' Total' " +
			"order by [Entidad Federativa], [Municipio], [Delito] " ,
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_77(
			77L,
			"Incidencia de delitos municipal en carpetas iniciadas (todos los delitos)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList(" Entidad Federativa","Municipio","Delito","Cantidad"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select [Entidad Federativa], [Municipio], [Delito], [Cantidad] from( " +
			"select " +
			        "'Entidad Federativa'= CASE  " +
						"WHEN GROUPING([EntidadFederativa].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([EntidadFederativa].[cNombre], 'N/D') " +
					"END, " +
			        "'Municipio'= CASE  " +
						"WHEN GROUPING([Municipio].[cNombreMunicipio])=1 THEN ' Total' " +
						"ELSE ISNULL([Municipio].[cNombreMunicipio], 'N/D') " +
					"END, " +
			        "'Delito'= CASE  " +
						"WHEN GROUPING([CatDelito].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([CatDelito].[cNombre], 'N/D') " +
					"END, " +
			        "count(distinct [Expediente].[Expediente_id]) 'Cantidad' 	  " +
			"from " +
				"[dbo].[Expediente] [Expediente]  " +
					"inner join [dbo].[Delito] [Delito]  " +
					"on [Expediente].[Expediente_id] = [Delito].[Expediente_id]  " +
						"inner join [dbo].[CatDelito] [CatDelito]  " +
						"on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id]  " +
							"left outer join [dbo].[Hecho] [Hecho]  " +
							"on [Expediente].[Expediente_id] = [Hecho].[Expediente_id]  " +
								"left outer join [dbo].[Domicilio] [Domicilio]  " +
								"on [Hecho].[Lugar_id] = [Domicilio].[Domicilio_id]  " +
									"left outer join [dbo].[EntidadFederativa] [EntidadFederativa]  " +
									"on [Domicilio].[EntidadFederativa_id] = [EntidadFederativa].[EntidadFederativa_id]  " +
										"left outer join [dbo].[Municipio] [Municipio]  " +
										"on [Domicilio].[Municipio_id] = [Municipio].[Municipio_id]  " +
											"inner join [dbo].[NumeroExpediente] [NumeroExpediente]  " +
											"on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id]  " +
			"where " +
				"(convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) and " +
				"([NumeroExpediente].[JerarquiaOrganizacional_id] = 10)    " +
			"group by [EntidadFederativa].[cNombre], " +
			    "[Municipio].[cNombreMunicipio], " +
				"[CatDelito].[cNombre] " +
			"WITH CUBE " +
			") as a " +
			"where a.[Entidad Federativa] not like ' Total' " +
			"order by [Entidad Federativa], [Municipio], [Delito] " ,
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_78(
			78L,
			"Indicador de delitos de mayor impacto",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Delito","Principal","Cantidad"),
			"declare @ld_fechaIni date" +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select [Delito], [Principal], [Cantidad] from( " +
			"select " +
			    "'Delito'= CASE  " +
						"WHEN GROUPING([CatDelito].[cNombre])=1 THEN ' Total' " +
						"ELSE ISNULL([CatDelito].[cNombre], 'N/D') " +
					"END, " +
				"'Principal'= CASE  " +
						"WHEN GROUPING([Delito].[bEsPrincipal])=1 THEN ' Total' " +
						"ELSE case [Delito].[bEsPrincipal] when 1 then 'Si' " +
			            "else 'No' end " +
					"END,  " +
			    "count(distinct [Expediente].[Expediente_id]) as 'Cantidad' " +
			"from " +
				"[dbo].[Expediente] [Expediente]  " +
					"inner join [dbo].[Delito] [Delito]  " +
					"on [Expediente].[Expediente_id] = [Delito].[Expediente_id]  " +
						"inner join [dbo].[CatDelito] [CatDelito]  " +
						"on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id]  " +
							"inner join [dbo].[NumeroExpediente] [NumeroExpediente]  " +
							"on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id]  " +						
			"where " +
				"(convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) " +
			    " and catdelito.bimpactoSocial = 1 " +
			    " and ([NumeroExpediente].[JerarquiaOrganizacional_id] = 10) " +
			"group by [CatDelito].[cNombre], [Delito].[bEsPrincipal] " +
			"WITH CUBE " +
			") as a " +
			"where a.[Delito] not like ' Total' " +
			"order by [Delito], [Principal] " ,
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_79(
			79L,
			"Reporte general del centro de justicia restaurativa",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Estatus Carpeta Investigación","Cantidad"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select 'Distrito'= CASE  " +
					"WHEN GROUPING(d.cNombre)=1 THEN ' Total' " +
					"ELSE ISNULL(d.cNombre, 'N/D') " +
				"END, " +
				"'Estatus Carpeta Investigación'= CASE  " +
					"WHEN GROUPING(v.cValor)=1 THEN ' Total' " +
					"ELSE ISNULL(v.cValor, 'N/D') " +
				"END, " +
				"count(distinct e.Expediente_id) 'Cantidad' " +
			"from NumeroExpediente ne, Valor v, Expediente e, CatDiscriminante c, CatDistrito d " +
			"where ne.Estatus_val = v.Valor_id " +
			"and convert(date,ne.dFechaApertura,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) " +
			"and ne.Expediente_id = e.Expediente_id " +
			"and e.catDiscriminante_id = c.catDiscriminante_id " +
			"and c.catDistrito_id = d.catDistrito_id " +
			"and ne.JerarquiaOrganizacional_id = 7 " +
			"group by d.cNombre, v.cValor " +
			"WITH CUBE " +
			"order by d.cNombre, v.Cvalor " ,
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_80(
			80L,
			"Sistema nacional categorizado",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Categoría","Delito","Delitos Principales", "Delitos no principales", "Total"),
			"declare @ld_fechaIni date" +
			"declare @ld_fechaFin date" +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select Categoría, Delito, [Delitos Principales], [Delitos no principales], [Delitos Principales]+[Delitos no principales] as Total from( " +
			"select 'Categoría'= CASE" +
			"			WHEN GROUPING(cCategoria)=1 THEN ' Total' " +
			"			ELSE ISNULL(cCategoria, 'N/D')" +
			"		END," +
			"       'Delito'= CASE" +
			"			WHEN GROUPING(cDelito)=1 THEN ' Total'" +
			"			ELSE ISNULL(cDelito, 'N/D')" +
			"		END," +
			"    sum([Delitos Principales]) as [Delitos Principales], sum([Delitos no principales]) as [Delitos no principales] from (" +
			" select [CategoriaDelito].[cNombre] as cCategoria," +
			"       [CatDelito].[cNombre] as cDelito, " +
			"	count(distinct [Expediente].[Expediente_id]) as 'Delitos Principales', 0 as 'Delitos no principales' " +
			" from [dbo].[CatDelito] [CatDelito] " +
			"		inner join [dbo].[CategoriaDelito] [CategoriaDelito] " +
			"		on [CatDelito].[CategoriaDelito_id] = [CategoriaDelito].[CategoriaDelito_id]  " +
			"			inner join [dbo].[Delito] [Delito] " +
			" on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id] " +
			"				inner join [dbo].[Expediente] [Expediente] " +
			"				on [Expediente].[Expediente_id] = [Delito].[Expediente_id] " +
			"                    inner join [dbo].[NumeroExpediente] [NumeroExpediente] " +
			"                    on [NumeroExpediente].[Expediente_id] = [Expediente].[Expediente_id] " +
			" where" +
			"	(convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date,@ld_fechaFin, 103)) " +
			"	and [Delito].besPrincipal = 1 " +
			"    and [NumeroExpediente].[JerarquiaOrganizacional_id] = 10 " +
			" group by [CategoriaDelito].[cNombre],	[CatDelito].[cNombre] " +
			" union " +
			" select [CategoriaDelito].[cNombre],[CatDelito].[cNombre], 0, count(distinct [Expediente].[Expediente_id]) 'Delitos Principales' " +
			" from [dbo].[CatDelito] [CatDelito] " +
			"		inner join [dbo].[CategoriaDelito] [CategoriaDelito]" +
			"		on [CatDelito].[CategoriaDelito_id] = [CategoriaDelito].[CategoriaDelito_id] " +
			"			inner join [dbo].[Delito] [Delito] " +
			"			on [Delito].[CatDelito_id] = [CatDelito].[CatDelito_id] " +
			"				inner join [dbo].[Expediente] [Expediente] " +
			"				on [Expediente].[Expediente_id] = [Delito].[Expediente_id] " +
			"                    inner join [dbo].[NumeroExpediente] [NumeroExpediente] " +
			"                    on [NumeroExpediente].[Expediente_id] = [Expediente].[Expediente_id] " +
			" where" +
			"	(convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date,@ld_fechaFin, 103)) " +
			"	and [Delito].besPrincipal = 0 " +
			"   and [NumeroExpediente].[JerarquiaOrganizacional_id] = 10 " +
			" group by [CategoriaDelito].[cNombre],[CatDelito].[cNombre]) as a " +
			" group by cCategoria, cDelito " +
			" WITH CUBE" +
			" ) as a " +
			" where a.Categoría not like ' Total' " +
			" order by Categoría, Delito" ,
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_81(
			81L,
			"Expedientes por etapa",
			DEF,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Etapa","Total de Expedientes"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select "+
			"	isnull([CatDistrito].[cNombre],'N/A') as Distrito, "+
			"	[Valor].[cValor] as Etapa, "+
			"    count(*) as 'Total Expedientes' "+
			"from "+
			"	[dbo].[RegistroBitacora] [RegistroBitacora]  "+
			"		inner join [dbo].[NumeroExpediente] [NumeroExpediente]  "+
			"		on [RegistroBitacora].[NumeroExpediente_id] = [NumeroExpediente]. "+
			"		[NumeroExpediente_id]  "+
			"			inner join [dbo].[Valor] [Valor]  "+
			"			on [RegistroBitacora].[cNuevo] = [Valor].[Valor_id]  "+
			"				left outer join [dbo].[Expediente] [Expediente]  "+
			"				on [NumeroExpediente].[Expediente_id] = [Expediente]. "+
			"				[Expediente_id]  "+
			"					left outer join [dbo].[CatDiscriminante] [CatDiscriminante]  "+
			"					on [Expediente].[catDiscriminante_id] = [CatDiscriminante]. "+
			"					[catDiscriminante_id]  "+
			"						left outer join [dbo].[CatDistrito] [CatDistrito]  "+
			"						on [CatDiscriminante].[catDistrito_id] = [CatDistrito]. "+
			"						[catDistrito_id]  "+
			"where "+
			"	(convert(date,[RegistroBitacora].[dFechaInicio],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) and "+
			"	([RegistroBitacora].[TipoMovimiento_val] = 2931) "+
			"group by [CatDistrito].[cNombre], "+
			"	[Valor].[cValor] ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_82(
			82L,
			"Expedientes por defensor",
			DEF,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Funcionario","Total de Expedientes"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select "+
			"	isnull([CatDistrito].[cNombre],'N/A') as Distrito, "+
			"	[Funcionario].[cNombreFuncionario]+' '+ "+
			"	[Funcionario].[cApellidoPaternoFuncionario]+' '+ "+
			"	[Funcionario].[cApellidoMaternoFuncionario] as Funcionario, "+
			"    count(*) as 'Total de Expedientes' "+
			"from "+
			"	[dbo].[NumeroExpediente] [NumeroExpediente]  "+
			"		inner join [dbo].[Funcionario] [Funcionario]  "+
			"		on [NumeroExpediente].[iClaveFuncionario] = [Funcionario]. "+
			"		[iClaveFuncionario]  "+
			"			left outer join [dbo].[Expediente] [Expediente]  "+
			"			on [NumeroExpediente].[Expediente_id] = [Expediente].[Expediente_id]  "+
			"				left outer join [dbo].[CatDiscriminante] [CatDiscriminante]  "+
			"				on [Expediente].[catDiscriminante_id] = [CatDiscriminante]. "+
			"				[catDiscriminante_id]  "+
			"					left outer join [dbo].[CatDistrito] [CatDistrito]  "+
			"					on [CatDiscriminante].[catDistrito_id] = [CatDistrito]. "+
			"					[catDistrito_id]  "+
			"where "+
			"	(convert(date,[NumeroExpediente].[dFechaApertura],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) "+
			"group by  "+
			"	[CatDistrito].[cNombre], "+
			"	[Funcionario].[cNombreFuncionario], "+
			"	[Funcionario].[cApellidoPaternoFuncionario], "+
			"	[Funcionario].[cApellidoMaternoFuncionario] ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_83(
			83L,
			"Solicitudes de defensor, por Institución origen y por estatus",
			DEF,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Institución","Estatus","# Solicitudes de defensor"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select c.cNombreInst as 'Institución', v.cValor as 'Estatus', count(*) '# Solicitudes de defensor' "+
			"from documento d, solicitud s, ConfInstitucion c, Valor v "+
			"where d.Documento_id = s.Solicitud_id  "+
			"and d.ConfInstitucion_id = c.ConfInstitucion_id "+
			"and s.Estatus_val = v.Valor_id "+
			"and convert(date,d.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"and s.TipoSolicitud_val = 1772 "+
			"group by c.cNombreInst, v.cValor "+
			"order by c.cNombreInst, v.cValor ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_84(
			84L,
			"Justicia Alternativa",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Casos que pueden resolverse por justicia alterna"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select count(*) 'Casos que pueden resolverse por justicia alterna' "+
			"from Expediente e, NumeroExpediente n, Actividad a "+
			"where e.Expediente_id = n.Expediente_id "+
			"and e.Expediente_id = a.Expediente_id "+
			"and n.JerarquiaOrganizacional_id = 7 "+
			"and convert(date,n.dFechaApertura,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"and a.TipoActividad_val = 3718 ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_85(
			85L,
			"INEGI - Victimas Persona Moral",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito", "Cantidad"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select "+
			"	'Distrito' = case WHEN GROUPING([CatDistrito].[cNombre])=1 THEN ' Total' "+
			"			ELSE ISNULL([CatDistrito].[cNombre], 'N/D') "+
			"            END, "+
			"	count(*) as Cantidad  "+
			"from "+
			"	[dbo].[Elemento] [Elemento]  "+
			"		inner join [dbo].[Calidad] [Calidad]  "+
			"		on [Elemento].[Calidad_id] = [Calidad].[Calidad_id]  "+
			"			inner join [dbo].[Valor] [Valor]  "+
			"			on [Calidad].[TipoCalidad_val] = [Valor].[Valor_id]  "+
			"				inner join [dbo].[NumeroExpediente] [NumeroExpediente]  "+
			"				on [NumeroExpediente].[Expediente_id] = [Elemento]. "+
			"				[Expediente_id]  "+
			"					inner join [dbo].[Expediente] [Expediente]  "+
			"					on [NumeroExpediente].[Expediente_id] = [Expediente]. "+
			"					[Expediente_id]  "+
			"						inner join [dbo].[CatDiscriminante] [CatDiscriminante]  "+
			"						on [Expediente].[catDiscriminante_id] =  "+
			"						[CatDiscriminante].[catDiscriminante_id]  "+
			"							inner join [dbo].[CatDistrito] [CatDistrito]  "+
			"							on [CatDiscriminante].[catDistrito_id] =  "+
			"							[CatDistrito].[catDistrito_id]  "+
			"								inner join [dbo].[Organizacion] [Organizacion]  "+
			"								on [Elemento].[Elemento_id] = [Organizacion]. "+
			"								[Organizacion_id]  "+
			"where "+
			"    (convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) and "+
			"	([Valor].[Valor_id] =214) "+
			"group by "+
			"    [CatDistrito].[cNombre] "+
			"with cube ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_86(
			86L,
			"Eficiencia del CJR",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Concepto", "Total"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +

			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +

			"select 'En convenio de Conciliación' as 'Concepto', count(*) as Total "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12318, 12320, 12330) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'En convenio de Justicia Restaurativa' as 'tipo', count(*) "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12322, 12324, 12332) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'En convenio de Mediación' as 'tipo', count(*) "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12326, 12328, 12334) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'Convenio Cumplido Conciliación' as 'tipo', count(*) "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12258, 12260, 12270) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'Convenio Cumplido Justicia Restaurativa' as 'tipo', count(*) "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12262, 12264, 12272) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'Convenio Cumplido Mediación' as 'tipo', count(*) "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12266, 12268, 12274) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'Convenio Incumplido Conciliación' as 'tipo', count(*) "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12276, 12278, 12288) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'Convenio Incumplido Justicia Restaurativa' as 'tipo', count(*) "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12280, 12282, 12290) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'Convenio Incumplido Mediación' as 'tipo', count(*) "+
			"from Expediente e, Actividad a "+
			"where e.Expediente_id = a.Expediente_id "+
			"and a.TipoActividad_val in (12284, 12286, 12292) "+
			"and convert(date,a.dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'No llegaron a convenio de Conciliación' as 'tipo', count(*) "+
			"from Expediente e, NumeroExpediente n "+
			"where e.Expediente_id = n.Expediente_id "+
			"and n.JerarquiaOrganizacional_id = 7 "+
			"and n.Estatus_val in (12055, 12007, 12043, 12037, 12025, 12181, 12112, 12160, 12091, 12154, 12085, 12142, 12073) "+
			"and convert(date,n.dFechaApertura,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'No llegaron a convenio de Justicia Restaurativa' as 'tipo', count(*) "+
			"from Expediente e, NumeroExpediente n "+
			"where e.Expediente_id = n.Expediente_id "+
			"and n.JerarquiaOrganizacional_id = 7 "+
			"and n.Estatus_val in (12059, 12011, 12047, 12038, 12026, 12185, 12116, 12164, 12095, 12155, 12086, 12143, 12074) "+
			"and convert(date,n.dFechaApertura,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 'No llegaron a convenio de Mediación' as 'tipo', count(*) "+
			"from Expediente e, NumeroExpediente n "+
			"where e.Expediente_id = n.Expediente_id "+
			"and n.JerarquiaOrganizacional_id = 7 "+
			"and n.Estatus_val in (12063, 12015, 12051, 12039, 12027, 12189, 12120, 12168, 12099, 12156, 12087, 12144, 12075) "+
			"and convert(date,n.dFechaApertura,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_87(
			87L,
			"Indicador de Atención Ciudadana",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Id", "Área", "Expedientes"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +
			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +
			"select 1 as Id, 'MAT' as 'Área', count(*) as 'Expedientes' "+
			"from NumeroExpediente where JerarquiaOrganizacional_id in (44, 45) "+
			"and convert(date,dFechaApertura,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 2, 'Centro de Justicia restaurativa' as 'Área', count(*) as 'Expedientes' "+
			"from NumeroExpediente where JerarquiaOrganizacional_id = 7 "+
			"and convert(date,dFechaApertura,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 3, 'Unidades de Investigación' as 'Área', count(*) as 'Expedientes' "+
			"from NumeroExpediente where JerarquiaOrganizacional_id = 10 "+
			"and convert(date,dFechaApertura,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) "+
			"union "+
			"select 4, 'Total Personas Atendidas', count(*) as 'Expedientes' "+
			"from Expediente where convert(date,dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103) ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),			
	INDICADOR_88(
			88L,
			"Denuncias, Querellas y Reportes",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Tipo", "Total"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +
			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +
			"select v.cValor Tipo, count(*) Total "+
			"from Expediente e, Valor v "+
			"where e.origen_val = v.Valor_id "+
			"and convert(date,dFechaCreacion,103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)  "+
			"and v.Valor_id in (2078, 2079, 3975) "+
			"group by v.cValor ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),
	INDICADOR_89(89L,
			"Expedientes sin datos",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Caso", "Número de Expediente", "Funcionario", "Estatus", "Fecha de creación",
					"Delito Principal", "Denunciante", "Víctima(s)", "Probable(s) Partícipe(s)"),
			" EXEC usp_expedientesSinDatos :fechaIncio, :fechaFin ",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"Delito", "Número de Incidencias Delictivas",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, true
			),	
	INDICADOR_90(
			90L,
			"Víctimas por sexo, edad, escolaridad, ocupación, nacionalidad (delito principal)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Sexo", "Edad Aproximada", "Escolaridad", "Ocupacion", "Nacionalidad", "Total"),
			"declare @ld_fechaIni date " +
			"declare @ld_fechaFin date " +
			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +
			" select [NombreDemografico].[cSexo] as Sexo, " +
			"	[NombreDemografico].[iEdadAproximada] as [Edad Aproximada], " +
			"	isnull([Escolaridad].[cValor], 'ND') as [Escolaridad], " +
			"	isnull([Ocupacion].[cValor], 'ND')   as [Ocupacion], " +
			"    isnull([Nacionalidad].[cValor], 'ND')   as [Nacionalidad], " +
			"    count(distinct [DelitoPersona].[ProbableResponsable_id]) as Total " +
			"from [dbo].[Involucrado] [Involucrado] " +
			"		left outer join [dbo].[Valor] [Escolaridad]" +
			"		on [Involucrado].[Escolaridad_val] = [Escolaridad].[Valor_id]" +
			"			left outer join [dbo].[InvolucradoNacionalidad] [InvolucradoNacionalidad]" +
			"			on [Involucrado].[Involucrado_id] = [InvolucradoNacionalidad].[Involucrado_id]" +
			"				inner join [dbo].[Valor] [Nacionalidad]" +
			"				on [InvolucradoNacionalidad].[Valor_id] = [Nacionalidad].[Valor_id]" +
			"					left outer join [dbo].[InvolucradoOcupacion] [InvolucradoOcupacion]" +
			"					on [Involucrado].[Involucrado_id] = [InvolucradoOcupacion].[Involucrado_id]" +
			"						left outer join [dbo].[Valor] [Ocupacion]" +
			"						on [InvolucradoOcupacion].[Valor_id] = [Ocupacion].[Valor_id]" +
			"							inner join [dbo].[DelitoPersona] [DelitoPersona]" +
			"							on [DelitoPersona].[Victima_id] = [Involucrado].[Involucrado_id]" +
			"								inner join [dbo].[Delito] [Delito]" +
			"								on [Delito].[Delito_id] = [DelitoPersona].[Delito_id]" +
			"									inner join [dbo].[Expediente] [Expediente]" +
			"									on [Expediente].[Expediente_id] = [Delito].[Expediente_id]" +
			"										inner join [dbo].[NumeroExpediente] [NumeroExpediente]" +
			"										on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id]" +
			"											inner join [dbo].[Persona] [Persona]" +
			"											on [DelitoPersona].[Victima_id] = [Persona].[Persona_id]" +
			"												left outer join [dbo].[NombreDemografico] [NombreDemografico]" +
			"												on [Persona].[Persona_id] = [NombreDemografico].[Persona_id]" +
			" where (convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) and " +
			"	([NumeroExpediente].[JerarquiaOrganizacional_id] = 10) and " +
			"   ([Delito].[bEsPrincipal] = 1) " +
			"group by [NombreDemografico].[cSexo], [NombreDemografico].[iEdadAproximada]," +
			"	[Escolaridad].[cValor],	[Ocupacion].[cValor], [Nacionalidad].[cValor]",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),			
	INDICADOR_91(
			91L,
			"Probables responsables por sexo, edad, escolaridad, ocupación, nacionalidad (delito principal)",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Sexo", "Edad Aproximada", "Escolaridad", "Ocupacion", "Nacionalidad", "Total"),
			"declare @ld_fechaIni date" +
			"declare @ld_fechaFin date" +
			"set @ld_fechaIni = ''+:fechaIncio+'' " + 
			"set @ld_fechaFin = ''+:fechaFin+'' " +
			" select [NombreDemografico].[cSexo] as Sexo, " +
			"	[NombreDemografico].[iEdadAproximada] as [Edad Aproximada], " +
			"	isnull([Escolaridad].[cValor], 'ND') as [Escolaridad], " +
			"	isnull([Ocupacion].[cValor], 'ND')   as [Ocupacion], " +
			"    isnull([Nacionalidad].[cValor], 'ND')   as [Nacionalidad], " +
			"    count(distinct [DelitoPersona].[ProbableResponsable_id]) as Total " +
			"from [dbo].[Involucrado] [Involucrado] " +
			"		left outer join [dbo].[Valor] [Escolaridad]" +
			"		on [Involucrado].[Escolaridad_val] = [Escolaridad].[Valor_id]" +
			"			left outer join [dbo].[InvolucradoNacionalidad] [InvolucradoNacionalidad]" +
			"			on [Involucrado].[Involucrado_id] = [InvolucradoNacionalidad].[Involucrado_id]" +
			"				inner join [dbo].[Valor] [Nacionalidad]" +
			"				on [InvolucradoNacionalidad].[Valor_id] = [Nacionalidad].[Valor_id]" +
			"					left outer join [dbo].[InvolucradoOcupacion] [InvolucradoOcupacion]" +
			"					on [Involucrado].[Involucrado_id] = [InvolucradoOcupacion].[Involucrado_id]" +
			"						left outer join [dbo].[Valor] [Ocupacion]" +
			"						on [InvolucradoOcupacion].[Valor_id] = [Ocupacion].[Valor_id]" +
			"							inner join [dbo].[DelitoPersona] [DelitoPersona]" +
			"							on [DelitoPersona].[ProbableResponsable_id] = [Involucrado].[Involucrado_id]" +
			"								inner join [dbo].[Delito] [Delito]" +
			"								on [Delito].[Delito_id] = [DelitoPersona].[Delito_id]" +
			"									inner join [dbo].[Expediente] [Expediente]" +
			"									on [Expediente].[Expediente_id] = [Delito].[Expediente_id]" +
			"										inner join [dbo].[NumeroExpediente] [NumeroExpediente]" +
			"										on [Expediente].[Expediente_id] = [NumeroExpediente].[Expediente_id]" +
			"											inner join [dbo].[Persona] [Persona]" +
			"											on [DelitoPersona].[Victima_id] = [Persona].[Persona_id]" +
			"												left outer join [dbo].[NombreDemografico] [NombreDemografico]" +
			"												on [Persona].[Persona_id] = [NombreDemografico].[Persona_id]" +
			" where (convert(date,[Expediente].[dFechaCreacion],103) between convert(date, @ld_fechaIni, 103) and convert(date, @ld_fechaFin, 103)) and " +
			"	([NumeroExpediente].[JerarquiaOrganizacional_id] = 10) and " +
			"   ([Delito].[bEsPrincipal] = 1) " +
			"group by [NombreDemografico].[cSexo], [NombreDemografico].[iEdadAproximada]," +
			"	[Escolaridad].[cValor],	[Ocupacion].[cValor], [Nacionalidad].[cValor]",
			1, -1, -1, //X,Y (-1 Datos compartidos),Serie (-1 Se define una nueva serie)
			"", "",
			OrientacionGrafica.Vertical, TipoGrafica.Linea, true, false
			),	
			
	INDICADOR_92(92L,
			"Investigaciones judicializadas",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Distrito","Agencia\\Tribunal", "Estatus", "# Investigaciones Judicializadas"),
			" SELECT  distrito.cNombre As 'Distrito', cd.cNombre AS 'Agencia\\Tribunal', estatus.cValor as 'Estatus', count(*) AS '#Investigaciones Judicializadas' " +
					" FROM audiencia a, NumeroExpediente ne, Expediente e, CatDiscriminante cd, CatDistrito distrito, Valor estatus "+
					" WHERE a.Audiencia_id in( "+
					"  SELECT Audiencia_id FROM SolicitudAudiencia"+
					"  WHERE SolicitudAudiencia_id in("+
					"        SELECT Solicitud_id from Solicitud"+
					"        where TipoSolicitud_val = 1771"+
					"        and cFolioSolicitud like 'FG%' "+
					"    )"+
					")"+
					" AND convert(date, a.dFechaAudiencia, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" AND a.NumeroExpediente_id = ne.NumeroExpediente_id"+
					" AND ne.Expediente_id = e.Expediente_id"+
					" AND e.catDiscriminante_id = cd.catDiscriminante_id"+
					" AND cd.catDistrito_id = distrito.catDistrito_id"+
					" AND a.Estatus_val = estatus.Valor_id"+
					" GROUP BY distrito.cNombre, cd.cNombre, estatus.cValor ",
					0, 2, 1,
					"", "",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, false, true
			),

			
	INDICADOR_93(93L,
					"Medidas cautelares, Audiencias iniciales y complementarias por Carpeta",
					PGJ,
					Arrays.asList("fechaIncio","fechaFin"),
					Arrays.asList("Carpeta","Funcionario", "Actuación", "Cantidad"),
							" select (select cNumeroExpediente from NumeroExpediente where Expediente_id = a.Expediente_id and JerarquiaOrganizacional_id = 10 ) as 'Carpeta', " +
							" f.cNombreFuncionario + ' ' + f.cApellidoPaternoFuncionario + ' ' + f.cApellidoMaternoFuncionario as 'Funcionario', "+
							" tipoActividad.cValor as 'Actuación', count(*) as 'Cantidad' "+
							" from Actividad a, Valor tipoActividad, Funcionario f "+
							" where a.TipoActividad_val = tipoActividad.Valor_id "+
							" and TipoActividad_val IN(9844,9846,9848,9850,6863,6879,9856,9858,9860,9862,9864,9866,9868,9870,9872,9874,9876,9878,9880) "+
							" and a.iClaveFuncionario = f.iClaveFuncionario "+
							" and convert(date, a.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
							" group by a.Expediente_id, tipoActividad.cValor, f.cNombreFuncionario + ' ' + f.cApellidoPaternoFuncionario + ' ' + f.cApellidoMaternoFuncionario ",							
							0, 2, 1,
							"", "",
							OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
					),
	INDICADOR_94(94L,
			"Medidas cautelares, Audiencias iniciales y complementarias por Funcionario",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Funcionario", "Actuación", "Cantidad"),
					" select f.cNombreFuncionario + ' ' + f.cApellidoPaternoFuncionario + ' ' + f.cApellidoMaternoFuncionario as 'Funcionario', " +
					" tipoActividad.cValor as 'Actuación', count(*) as 'Cantidad' "+
					" from Actividad a, Valor tipoActividad, Funcionario f "+
					" where a.TipoActividad_val = tipoActividad.Valor_id "+
					" and TipoActividad_val IN(9844,9846,9848,9850,6863,6879,9856,9858,9860,9862,9864,9866,9868,9870,9872,9874,9876,9878,9880) "+
					" and a.iClaveFuncionario = f.iClaveFuncionario "+
					" and convert(date, a.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" group by tipoActividad.cValor, f.cNombreFuncionario + ' ' + f.cApellidoPaternoFuncionario + ' ' + f.cApellidoMaternoFuncionario ",							
					0, 2, 1,
					"", "",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
	INDICADOR_95(95L,
			"Total General de Medidas cautelares, Audiencias iniciales y complementarias",
			PGJ,
			Arrays.asList("fechaIncio","fechaFin"),
			Arrays.asList("Actuación", "Cantidad"),
						" select tipoActividad.cValor as 'Actuación', count(*) as 'Cantidad' " +
						" from Actividad a, Valor tipoActividad "+
						" where a.TipoActividad_val = tipoActividad.Valor_id "+
						" and TipoActividad_val IN(9844,9846,9848,9850,6863,6879,9856,9858,9860,9862,9864,9866,9868,9870,9872,9874,9876,9878,9880) "+
						" and convert(date, a.dFechaCreacion, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
						" group by tipoActividad.cValor ",											
					0, 2, 1,
					"", "",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
		INDICADOR_96(96L,
					"Estadística de colonias más afectadas",
					SSP,
					Arrays.asList("fechaIncio","fechaFin"),
					Arrays.asList("Colonia", "Cantidad de IPH"),
								" select Asentamiento.cNombreAsentamiento as Asentamiento, count(*) as 'Cantidad de IPH' " +
								" from	Domicilio Domicilio inner join Asentamiento Asentamiento " +
								" on Domicilio.Asentamiento_id = Asentamiento.Asentamiento_id " +
								" ,Elemento e WHERE e.Elemento_id=Domicilio.Domicilio_id " +
								" and convert(date, e.dFechaCreacionElemento, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
								" group by Asentamiento.cNombreAsentamiento " +
								" order by count(*) desc ",											
							0, 1, -1,
							"Colonia", "Cantidad",
							OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
					),
		INDICADOR_97(97L,
							"Estadística de delitos más registrados",
							SSP,
							Arrays.asList("fechaIncio","fechaFin"),
							Arrays.asList("Delito", "Cantidad de Delitos"),
										" select CatDelito.cNombre as Delito, count(*) as 'Cantidad de Delitos' " +
										" from DelitoIph DelitoIph	inner join CatDelito CatDelito 	on " +
										" DelitoIph.CatDelito_id = CatDelito.CatDelito_id,InformePolicialHomologado iph " +
										" where iph.InformePolicialHomologado_id=DelitoIph.InformePolicialHomologado_id " +
										" and convert(date, iph.dFechaCaptura, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
										" group by [CatDelito].[cNombre] " +
										" order by count(*) desc",
									0, 1, -1,
									"Delito", "Cantidad",
									OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
							),
			INDICADOR_98(98L,
							"Cuadro estadístico de captura de IPH por Corporación",
							SSP,
							Arrays.asList("fechaIncio","fechaFin"),
							Arrays.asList("Corporación", "Cantidad de IPH"),
							" select corporacion.cValor, count(*) as 'Cantidad de IPH' " +
							" from InformePolicialHomologado iph,Valor corporacion	" +
							" where iph.corporacion_val=corporacion.Valor_id " +
							" and convert(date, iph.dFechaCaptura, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
							" group by corporacion.cValor order by count(*) desc ", 
							0, 1, -1,
							"Corporación", "Cantidad",
							OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
			),
			INDICADOR_99(99L,
					"Total de IPH Enviado a Procuraduría",
					SSP,
					Arrays.asList("fechaIncio","fechaFin"),
					Arrays.asList("Cantidad", "Corporación","Enviado a Procuraduría"),
					" select count(*) as 'cantidad', corporacion.cValor,'NO ' as 'Enviado a Procuraduría' "+
					" from InformePolicialHomologado iph, Actividad a,Valor corporacion "+
					" where iph.Expediente_id=a.Expediente_id "+
					" and iph.corporacion_val=corporacion.Valor_id "+
					" and a.Documento_id is null "+
					" and convert(date, iph.dFechaCaptura, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" group by corporacion.cValor "+
					" union "+
					" select count(*) as 'cantidad', corporacion.cValor,'SI ' as 'Enviado a Procuraduría' "+
					" from InformePolicialHomologado iph, Actividad a,Valor corporacion "+
					" where iph.Expediente_id=a.Expediente_id "+
					" and iph.corporacion_val=corporacion.Valor_id "+
					" and a.Documento_id is not null "+
					" and convert(date, iph.dFechaCaptura, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
					" group by corporacion.cValor ",
					1, 0, 2,
					 "Corporación","Cantidad",
					OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
					),
		INDICADOR_100(100L,
							"Total de detenidos por Corporación",
							SSP,
							Arrays.asList("fechaIncio","fechaFin"),
							Arrays.asList("Corporación","Cantidad"),
							" select corporacion.cValor, count(e.Expediente_id) as 'Cantidad de Detenidos ' " +
							" from InformePolicialHomologado iph,Valor corporacion,Elemento e,Calidad c " +
							" where iph.corporacion_val=corporacion.Valor_id " +
							" and e.Expediente_id=iph.Expediente_id " +
							" and e.Calidad_id=c.Calidad_id " +
							" and e.TipoElemento_val=427 " +
							" and c.TipoCalidad_val=213 " +
							" and convert(date, iph.dFechaCaptura, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
							" group by corporacion.cValor " +
							" order by count(e.Expediente_id) desc",
							0, 1, -1,
							 "Cantidad","Corporación",
							OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
							),
		INDICADOR_101(101L,
							"Audiencias de Técnicas de investigación",
							PJ,
							Arrays.asList("fechaIncio","fechaFin"),
							Arrays.asList("Causa","Fecha Programacion","Estatus"),
							" select ne.cNumeroExpediente as Causa,a.dFechaAudiencia as 'Fecha Programacion',estatus.cValor as Estatus "+
							" from Audiencia a,NumeroExpediente ne,SolicitudAudiencia sa,Solicitud s,Valor estatus "+
							" where a.NumeroExpediente_id=ne.NumeroExpediente_id "+ 
							" and sa.Audiencia_id=a.Audiencia_id "+
							" and s.Solicitud_id=sa.SolicitudAudiencia_id "+
							" and a.Estatus_val=estatus.Valor_id "+
							" and a.TipoAudiencia_val=6779 "+
							" and convert(date, a.dFechaAudiencia, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
							" order by Causa,a.dFechaAudiencia desc " ,
							1, 0, 2,
							"Fecha Programacion","Causa",
							OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
							),
		INDICADOR_102(102L,
				"Audiencias Privadas",
				PJ,
				Arrays.asList("fechaIncio","fechaFin"),
				Arrays.asList("Causa","Fecha Programacion","Estatus"),
				" select ne.cNumeroExpediente as Causa,a.dFechaAudiencia as 'Fecha Programacion',estatus.cValor as Estatus "+
				" from Audiencia a,NumeroExpediente ne,SolicitudAudiencia sa,Solicitud s,Valor estatus "+
				" where a.NumeroExpediente_id=ne.NumeroExpediente_id "+ 
				" and sa.Audiencia_id=a.Audiencia_id "+
				" and s.Solicitud_id=sa.SolicitudAudiencia_id "+
				" and a.Estatus_val=estatus.Valor_id "+
				" and a.TipoAudiencia_val in (1718,6477,6779) "+
				" and convert(date, a.dFechaAudiencia, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
				" order by Causa,a.dFechaAudiencia desc ",
				1, 0, 2,
				"Fecha Programacion","Causa",
				OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
				),
		INDICADOR_103(103L,
				"Audiencias no celebradas",
				PJ,
				Arrays.asList("fechaIncio","fechaFin"),
				Arrays.asList("Causa","Fecha Programacion","Estatus"),
				" select ne.cNumeroExpediente as Causa,a.dFechaAudiencia as 'Fecha Programacion',estatus.cValor as Estatus "+
				" from Audiencia a,NumeroExpediente ne,SolicitudAudiencia sa,Solicitud s,Valor estatus "+
				" where a.NumeroExpediente_id=ne.NumeroExpediente_id "+ 
				" and sa.Audiencia_id=a.Audiencia_id "+
				" and s.Solicitud_id=sa.SolicitudAudiencia_id "+
				" and a.Estatus_val=estatus.Valor_id "+
				" and estatus.Valor_id in (3552) "+
				" and convert(date, a.dFechaAudiencia, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
				" order by Causa,a.dFechaAudiencia desc ",
				1, 0, 2,
				"Fecha Programacion","Causa",
				OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
				),
		INDICADOR_104(104L,
				"Número de audiencias intermedia  Totales",
				PJ,
				Arrays.asList("fechaIncio","fechaFin"),
				Arrays.asList("Causa","Fecha Programacion","Estatus"),
				" select ne.cNumeroExpediente as Causa,a.dFechaAudiencia as 'Fecha Programacion',estatus.cValor as Estatus "+
				" from Audiencia a,NumeroExpediente ne,SolicitudAudiencia sa,Solicitud s,Valor estatus "+
				" where a.NumeroExpediente_id=ne.NumeroExpediente_id "+ 
				" and sa.Audiencia_id=a.Audiencia_id "+
				" and s.Solicitud_id=sa.SolicitudAudiencia_id "+
				" and a.Estatus_val=estatus.Valor_id "+
				" and a.TipoAudiencia_val=2774 "+
				" and convert(date, a.dFechaAudiencia, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
				" order by Causa,a.dFechaAudiencia desc ",
				1, 0, 2,
				"Fecha Programacion","Causa",
				OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
				),
		INDICADOR_105(105L,
				"Número de audiencias de juicio oral Totales",
				PJ,
				Arrays.asList("fechaIncio","fechaFin"),
				Arrays.asList("Causa","Fecha Programacion","Estatus"),
				" select ne.cNumeroExpediente as Causa,a.dFechaAudiencia as 'Fecha Programacion',estatus.cValor as Estatus "+
				" from Audiencia a,NumeroExpediente ne,SolicitudAudiencia sa,Solicitud s,Valor estatus "+
				" where a.NumeroExpediente_id=ne.NumeroExpediente_id "+ 
				" and sa.Audiencia_id=a.Audiencia_id "+
				" and s.Solicitud_id=sa.SolicitudAudiencia_id "+
				" and a.Estatus_val=estatus.Valor_id "+
				" and a.TipoAudiencia_val in (10015,2021) "+
				" and convert(date, a.dFechaAudiencia, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
				" order by Causa,a.dFechaAudiencia desc ",
				1, 0, 2,
				"Fecha Programacion","Causa",
				OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
				),
		INDICADOR_106(106L,
				"Medios de impugnación",
				PJ,
				Arrays.asList("fechaIncio","fechaFin"),
				Arrays.asList("Causa","Fecha Programacion","Estatus"),
				" select ne.cNumeroExpediente as Causa,a.dFechaAudiencia as 'Fecha Programacion',estatus.cValor as Estatus "+
				" from Audiencia a,NumeroExpediente ne,SolicitudAudiencia sa,Solicitud s,Valor estatus "+
				" where a.NumeroExpediente_id=ne.NumeroExpediente_id "+ 
				" and sa.Audiencia_id=a.Audiencia_id "+
				" and s.Solicitud_id=sa.SolicitudAudiencia_id "+
				" and a.Estatus_val=estatus.Valor_id "+
				" and s.TipoSolicitud_val=2091 "+
				" and convert(date, a.dFechaAudiencia, 103) between convert(date,:fechaIncio,103) AND convert(date,:fechaFin,103) "+
				" order by Causa,a.dFechaAudiencia desc ",
				1, 0, 2,
				"Fecha Programacion","Causa",
				OrientacionGrafica.Vertical, TipoGrafica.BarChart3D, true, true
				),
				
			;
	
	
	private static final String ETIQUETA_AGENCIA = "Agencia";
	
	private Long indicadorId;
	private String titulo;
	private Instituciones institucion;
	private List<String>  parametros;
	private List<String>  nombreColumnas;
	private String consulta;
	
	//VALORES UTILIZADOS PARA GRAFICAS
	//Valor X, del resultado de la consulta
	private int idValorDescripcionX;	
	//Valor y, del resultado de la consulta
	private int idValorNumericoY; 	
	//Clasificacion de Agrupacion, del resultado de la consulta
	private int idClasificacion; 
	
	private String tituloCategoriaEjeX;
	private String tituloCategoriaEjeY;
	private OrientacionGrafica orientacionGrafica;
	private TipoGrafica tipoGrafica;
	private Boolean esActivoIndicador = true;
	private Boolean esParaGrafica = true;
	
	private final static Map<Long, Indicadores> hash = new HashMap<Long, Indicadores>();
    static {
        Indicadores[] objs = Indicadores.values();
        int pos = 0;
        while (pos < objs.length) {
            hash.put(objs[pos].getIndicadorId(), objs[pos]);
            pos++;
        }
    }
	
    
	/**
	 * @param indicadorId
	 * @param titulo
	 * @param institucion
	 * @param parametros
	 * @param nombreColumnas
	 * @param consulta
	 * @param idValorDescripcionX
	 * @param idValorNumericoY
	 * @param idClasificacion
	 * @param tituloCategoriaEjeX
	 * @param tituloCategoriaEjeY
	 * @param orientacionGrafica
	 * @param tipoGrafica
	 * @param esActivoIndicador
	 * @param esParaGrafica
	 */
	private Indicadores(Long indicadorId, String titulo,
			Instituciones institucion, List<String> parametros,
			List<String> nombreColumnas, String consulta,
			int idValorDescripcionX, int idValorNumericoY, int idClasificacion,
			String tituloCategoriaEjeX, String tituloCategoriaEjeY,
			OrientacionGrafica orientacionGrafica, TipoGrafica tipoGrafica,
			Boolean esActivoIndicador, Boolean esParaGrafica) {
		this.indicadorId = indicadorId;
		this.titulo = titulo;
		this.institucion = institucion;
		this.parametros = parametros;
		this.nombreColumnas = nombreColumnas;
		this.consulta = consulta;
		this.idValorDescripcionX = idValorDescripcionX;
		this.idValorNumericoY = idValorNumericoY;
		this.idClasificacion = idClasificacion;
		this.tituloCategoriaEjeX = tituloCategoriaEjeX;
		this.tituloCategoriaEjeY = tituloCategoriaEjeY;
		this.orientacionGrafica = orientacionGrafica;
		this.tipoGrafica = tipoGrafica;
		this.esActivoIndicador = esActivoIndicador;
		this.esParaGrafica = esParaGrafica;
	}

	 /**
     * 
     * @param valorIdPredefinido
     * @return
     */
    public static Indicadores getByValor(Long valorIdPredefinido) {
        return hash.get(valorIdPredefinido);
    }
    
	/**
	 * @return the indicadorId
	 */
	public Long getIndicadorId() {
		return indicadorId;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @return the institucion
	 */
	public Instituciones getInstitucion() {
		return institucion;
	}
	/**
	 * @return the nombreColumnas
	 */
	public List<String> getNombreColumnas() {
		return nombreColumnas;
	}
	/**
	 * @return the parametros
	 */
	public List<String> getParametros() {
		return parametros;
	}
	/**
	 * @return the consulta
	 */
	public String getConsulta() {
		return consulta;
	}

	/**
	 * @return the idValorDescripcionX
	 */
	public int getIdValorDescripcionX() {
		return idValorDescripcionX;
	}

	/**
	 * @return the idValorNumericoY
	 */
	public int getIdValorNumericoY() {
		return idValorNumericoY;
	}

	/**
	 * @return the idClasificacion
	 */
	public int getIdClasificacion() {
		return idClasificacion;
	}

	/**
	 * @return the tituloCategoriaEjeX
	 */
	public String getTituloCategoriaEjeX() {
		return tituloCategoriaEjeX;
	}

	/**
	 * @return the tituloCategoriaEjeY
	 */
	public String getTituloCategoriaEjeY() {
		return tituloCategoriaEjeY;
	}

	/**
	 * @return the orientacionGrafica
	 */
	public OrientacionGrafica getOrientacionGrafica() {
		return orientacionGrafica;
	}

	/**
	 * @return the tipoGrafica
	 */
	public TipoGrafica getTipoGrafica() {
		return tipoGrafica;
	}

	/**
	 * @param tituloAgencia the tituloAgencia to set
	 */
	public void cambiarEtiquetaAgencia(String tituloAgencia) {
		
		//Reemplazar en las columnas
		String nuevaLista[] = new String[this.getNombreColumnas().size()];
		int cont=0;
		for (String nombreColumna : this.getNombreColumnas()) {
			String nuevonombre = nombreColumna;
			if(nombreColumna.contains(ETIQUETA_AGENCIA) ){
				nuevonombre = nuevonombre.replace(ETIQUETA_AGENCIA, tituloAgencia);
			}
			nuevaLista[cont] = nuevonombre;
			cont++;
		}
		this.setNombreColumnas( Arrays.asList( nuevaLista));
		
		//Reemplazar en el Query
		String nuevaConsulta = this.getConsulta().replace(ETIQUETA_AGENCIA,
				tituloAgencia); 
		this.setConsulta(nuevaConsulta);
		
		//Reemplazar Titulo
		String nuevoTitulo = this.getTitulo().replace(ETIQUETA_AGENCIA,
				tituloAgencia); 
		this.setTitulo(nuevoTitulo);
		
		//Reemplazar Titulo X
		String nuevoTituloX = this.getTituloCategoriaEjeX().replace(ETIQUETA_AGENCIA,
				tituloAgencia); 
		this.setTituloCategoriaEjeX(nuevoTituloX);

		//Reemplazar Titulo Y
		String nuevoTituloY = this.getTituloCategoriaEjeY().replace(ETIQUETA_AGENCIA,
				tituloAgencia); 
		this.setTituloCategoriaEjeY(nuevoTituloY);
	}

	/**
	 * @return the esActivoIndicador
	 */
	public Boolean getEsActivoIndicador() {
		return esActivoIndicador;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @param nombreColumnas the nombreColumnas to set
	 */
	public void setNombreColumnas(List<String> nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}

	/**
	 * @param consulta the consulta to set
	 */
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	/**
	 * @param tituloCategoriaEjeX the tituloCategoriaEjeX to set
	 */
	public void setTituloCategoriaEjeX(String tituloCategoriaEjeX) {
		this.tituloCategoriaEjeX = tituloCategoriaEjeX;
	}

	/**
	 * @param tituloCategoriaEjeY the tituloCategoriaEjeY to set
	 */
	public void setTituloCategoriaEjeY(String tituloCategoriaEjeY) {
		this.tituloCategoriaEjeY = tituloCategoriaEjeY;
	}
	
	/**
	 * @return the hash
	 */
	public static Map<Long, Indicadores> getHash() {
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.indicadorId + " - " + this.titulo + " - X:"
				+ this.tituloCategoriaEjeX + " - Y:" + this.tituloCategoriaEjeY
				+ " - Orientacion:" + this.orientacionGrafica
				+ " - TipoGrafica:" + this.tipoGrafica 
				+ " "
				;
	}

	public void setEsParaGrafica(Boolean esParaGrafica) {
		this.esParaGrafica = esParaGrafica;
	}

	public Boolean getEsParaGrafica() {
		return esParaGrafica;
	}
	
	
}

enum OrientacionGrafica {
	Horizontal,
	Vertical;
}

enum TipoGrafica { 
	BarChart,
	BarChart3D,
	Linea;
}

<%@page
    import="mx.gob.segob.nsjp.comun.enums.funcionario.TipoDefensoria"%>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
        <head>
            <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
            <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css"
                  media="screen" />
             <link rel="stylesheet" type="text/css"
                  href="<%=request.getContextPath()%>/resources/css/jquery-ui.css" />
                  
             <link rel="stylesheet" type="text/css"
                  href="<%=request.getContextPath()%>/resources/css/estilos.css"
                  media="screen" />
            <link rel="stylesheet" type="text/css" media="screen"
                  href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
                  <link rel="stylesheet" type="text/css"
                  href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
            <script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
            <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		

            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

            <script type="text/javascript">

                var CONTEXT_ROOT = '<%= request.getContextPath()%>'; //nsjp-web
                var idExpediente ="";

                var reloadGrid=false;

                var reloadGrid2=false;

                $(document).ready(function() {
                    jQuery("#gridEvidencias").jqGrid({
                            url: CONTEXT_ROOT + '/consultarEvidenciasXCadenaCustodiaASDA.do',
                            datatype: "xml",
                            /**
                             * id
                             * n&uacute;mero de la evidencia,
                             * informaci&oacute;n de la evidencia, &iquest;descripcion?
                             * origen de la evidencia,
                             * &uacute;ltimo eslab&oacute;n asociado, &iquest;eslabonId?
                             * n&uacute;mero de eslab&oacute;n,
                             * tipo de eslab&oacute;n,
                             * almac&eacute;n donde se encuentra la evidencia,
                             * estado de la evidencia.
                             */
                            colNames:['id','N&uacute;mero','Informaci&oacute;n', 'Origen', '&Uacute;ltimo eslab&oacute;n asociado', 'N&uacute;mero de eslab&oacute;n', 'Tipo de eslab&oacute;n', 'Almac&eacute;n', 'Estado de la evidencia'],
                            colModel:[
                                    {name:'id',index:'id', width:75, viewable:false, key:true, hidden:true},
                                    {name:'numero',index:'numero', sorteable:false, align:"center"},
                                    {name:'informacion',index:'informacion', sorteable:false},
                                    {name:'origen',index:'origen', sortable:false},
                                    {name:'ultimoEslabonAsociado',index:'ultimoEslabonAsociado', sortable:false, align:"center"},
                                    {name:'numeroEslabon',index:'numeroEslabon', sortable:false, align:"center"},
                                    {name:'tipoEslabon',index:'tipoEslabon', align:"right", sorteable:false, align:"center"},
                                    {name:'almacen',index:'almacen', align:"right", sorteable:false, align:"center"},
                                    {name:'estadoEvidencia',index:'estadoEvidencia', align:"right", sorteable:false, align:"center"}
                            ],
                            multiselect:true,
                            multiboxonly:false,
                            rowNum:10,
                            autowidth: true,
                            rowList:[10,20,30],
                            pager: jQuery('#pagerGridEvidencias'),
                            sortname: 'numero',
                            viewrecords: true,
                            sortorder: "desc",
                            caption:"Evidencias"
                            }).navGrid('#pagerGridEvidencias',{edit:false,add:false,del:false});

                });

                function consultaEvidenciasPorFolio(){
                    ejecutaAction("/consultarCadenaCustodiaXFolio", function(respuesta){
                        var idCadenaDeCustodia = $(respuesta).find("id").text();
                        if (idCadenaDeCustodia == "-1") {
                            muestraAlert("mensajeSinCadenaDeCustodia", 300);
                        }else{
                            jQuery('#gridEvidencias').jqGrid('setGridParam',{postData:{idCadenaDeCustodia:idCadenaDeCustodia}})
                            $("#gridEvidencias").trigger('reloadGrid');
                            $("#wrapGriEvidencias").css("display", "inherit");
                            $("#gridEvidencias").show("fast");
                        }
                    }, "folioCadenaDeCustodia=" + $("#numCarpetaPJ").val());
                }
	
                function lanzaDarDeBajaIndicioOEvidencia() {
                    var evidencias = jQuery('#gridEvidencias').jqGrid('getGridParam','selarrrow');
                    if (evidencias && evidencias.length == 0) {
                        muestraAlert("mensajeSeleccionaUnaEvidencia", 280);
                    }else{
                        var evidenciasEnEstadoBaja = false;
                        for(var i = 0; i < evidencias.length; ++i){
                            var estado = $("#gridEvidencias").jqGrid('getCell', evidencias[i], 'estadoEvidencia');
                            if (estado == "Baja") {
                                evidenciasEnEstadoBaja = true;
                                break;
                            }
                        }
                        if (evidenciasEnEstadoBaja) {
                            muestraAlert("mensajeEvidenciasBaja");
                        }else{
                            var parametros = joinComoParametros(evidencias, "evidenciaId");
                            parametros += "&folioCadenaDeCustodia=" + $("#numCarpetaPJ").val();
                            abreNuevoFrame("iframewindowGenerarDocumento", "/darDeBajaIndicioOEvidencia.jsp?" + parametros, 0, 0, 800, 300, "Dar de baja indicio o evidencia");
                        }
                    }
                }

                function lanzaRegistrarSalidaAlmacen(){
                    abreNuevoFrame("iframeRegistrarSalidaAlmacen", "/registrarSalidaAlmacen.do?", 0, 0, 800, 300, "Registrar Salida de Almac&eacute;n");
                }
            </script>
        </head>
        <body>
            <div id="mensajeSeleccionaUnaEvidencia" style="display: none">
                Se debe seleccionar al menos una evidencia
            </div>
            <div id="mensajeEvidenciasBaja" style="display:none">
                Las evidencias no pueden tener el estado Baja
            </div>
            <div id="mensajeSinCadenaDeCustodia" style="display:none">
                No existe una cadena de custodia asociada al folio
            </div>
            <table>
                <tr class="fondoFuerteAP">
                    <td style="color: white" colspan="2" align="center">Busqueda
                    </td>
                </tr>
                <tr>
                    <td colspan="2">&nbsp;
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        N&uacute;mero de folio de la cadena de custodia:
                        <input type="text" id="numCarpetaPJ">
                    </td>
                    <td align="right">
                        <input type="button" value="Buscar"
                                             onclick="consultaEvidenciasPorFolio();" class="ui-button ui-corner-all ui-widget"/>
                    </td>
                </tr>
            </table>
            <div id="nabtabgrid" >
                <input type="button" value="Dar de baja indicio o evidencia"
                       onclick="lanzaDarDeBajaIndicioOEvidencia();" class="ui-button ui-corner-all ui-widget"/>
                <input type="button" value="Registrar salida de almac&eacute;n"
                       onclick="lanzaRegistrarSalidaAlmacen();" class="ui-button ui-corner-all ui-widget"/>
                <div id="wrapGriEvidencias" style="display:none">
                    <table id="gridEvidencias" align="center"></table>
                    <div id="pagerGridEvidencias"></div>
                </div>
            </div>
        </body>
    </html>
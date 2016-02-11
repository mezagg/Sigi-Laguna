<%-- 
    Document   : generarNotificacion
    Created on : 29-jul-2011, 11:55:37
    Author     : sawbona
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Notificaciones</title>
        <!--Hoja de estilos de Layout-->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/layout_complex.css" media="screen" />
        <!--link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" /-->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/estilos.css" media="screen" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/resources/css/jqgrid/ui.jqgrid.css" />
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.5.1.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/themes/1.8.10/jquery-ui.min.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.timeentry.css"/>
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.timeentry.js"></script>
        <!--script de windows engine (frames)-->
        <script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.windows-engine.js"></script>
        <!--Hoja de estilos windows engine (frames)-->
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/resources/css/jquery.windows-engine.css"/>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
        <script type="text/javascript" 	src="<%=request.getContextPath()%>/js/generarNotificacion.js"></script>
        
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
        
        <script type="text/javascript">
            // El codigo javascript de este jsp se encuentra en solicitarAudiencia.js
            var CONTEXT_ROOT = '<%= request.getContextPath()%>';
        </script>
    </head>
    <body>
        <div style="width: 80%;margin-left: 10% ">
            <div id="menuGenerarNotificacion">
                <ul>
                    <li>
                        <a style="cursor: pointer" onclick="seleccionarDestinatarios();">Seleccionar Destinatarios</a>
                        <a style="cursor: pointer">Guardado Parcial</a>
                        <a style="cursor: pointer">Enviar Notificaci&oacute;n</a>
                        <a style="cursor: pointer">Consultar Documentos</a>
                        <a style="cursor: pointer">Imprimir Notificaci&oacute;n</a>
                    </li>
                </ul>
                <div id="destinatariosSistemaWrap" style="">
                    <table id="destinatariosSistema"></table>
                    <div id="paginadorDestinatariosSistema"></div>
                </div>
                <div id="destinatariosFisicoWrap" style="">
                    <table id="destinatariosFisico"></table>
                    <div id="paginadorDestinatariosFisico"></div>
                </div>
                <div id="notificacion">
                    Notificacion:
                    <div>
                        <textarea id="notificacionTextArea" cols="" style="width: 100%;" rows="20" ></textarea>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

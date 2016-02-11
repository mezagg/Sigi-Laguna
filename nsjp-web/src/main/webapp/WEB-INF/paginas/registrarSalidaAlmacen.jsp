<%@page
    import="mx.gob.segob.nsjp.comun.enums.funcionario.TipoDefensoria"%>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link type="text/css" rel="stylesheet"
                  href="<%=request.getContextPath()%>/resources/css/jquery.windows-engine.css" />
            <link rel="stylesheet" type="text/css"
                  href="<%=request.getContextPath()%>/resources/css/layout_complex.css"
                  media="screen" />
            <link rel="stylesheet" type="text/css"
                  href="<%=request.getContextPath()%>/themes/1.8.10/south-street/jquery-ui.css" />
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
            src="<%=request.getContextPath()%>/resources/js/jquery.layout-1.3.0.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/layout_complex.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jqgrid/i18n/grid.locale-es.js"></script>
            <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jqgrid/jquery.jqGrid.min.js"></script>
            <script type="text/javascript" src="<%=request.getContextPath()%>/js/comun.js?n=1"></script>
            <script type="text/javascript" src="<%=request.getContextPath()%>/js/registrarSalidaAlmacen.js"></script>
            <script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		
            <style>
                .transpa {
                    background-color: transparent;
                    border: 0;
                }
            </style>
            <script type="text/javascript">
                var CONTEXT_ROOT = '<%= request.getContextPath()%>'; //nsjp-web
            </script>
        </head>
        <body>
        </body>
    </html>
<html>
	<head>
		<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery-1.5.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>
		<script>
		var ventanaPrincipal;
		function loadVentanaPrincipal() {
			ventanaPrincipal=window.open('<%= request.getContextPath() %>/Login.do','ventanaPrincipal','toolbar=no,location=no,directories=no,status=no, menubar=no,scrollbars=no,resizable=no,width=' + window.screen.width + ',height=' + window.screen.height);
		}
		</script>
	</head>
<body onLoad="javascript:loadVentanaPrincipal();">
Cargando Aplicacion .....
</body>
</html>
 
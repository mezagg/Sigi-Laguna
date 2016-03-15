<%@page import="mx.gob.segob.nsjp.dto.usuario.RolDTO"%>
<%@page import="mx.gob.segob.nsjp.dto.usuario.UsuarioDTO"%>
<%@ page import="mx.gob.segob.nsjp.dto.configuracion.ConfiguracionDTO"%>
<%@ page import="mx.gob.segob.nsjp.web.login.action.LoginAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<% 
	UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute("KEY_SESSION_USUARIO_FIRMADO");
	RolDTO rolDTO = usuario.getRolACtivo().getRol();
 %>
<div class="ui-layout-north">
	<div class="content">
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" background="<%=request.getContextPath()%>/resources/images/top_gral_sin_version.jpg" height="100%">
			<TBODY>
				<TR>
					<TD width=100 align=left valign="middle">
						<img height="80px" src="<%=request.getContextPath()%>/resources/images/logo_login.png">
					</TD>
                   	<TD width=301 align=left valign="middle">
                   		<div class='nombreInstitucion'><%=rolDTO.getDescripcionRol()%></div>
                   		<div class='versionCodigo'>${nsjp.version.codigo}</div>
                   	</TD>
				    <TD width=126 align=left valign="top"><SPAN></SPAN></TD>
				    <TD width=272 align=center valign="top">&nbsp;</TD>
				    <TD width=28 align=middle>&nbsp;</TD><!--<td width="150" align="center"></td>-->
				    <TD width=380 align="right" valign="middle">
				    	<table width="362" border="0" cellspacing="0" cellpadding="0">
				        	<tr>
				          		<td width="165">
				          			<table width="141" border="0" cellspacing="0" cellpadding="0">
				            			<tr>
				              				<td width="53" align="right" class="txt_menu_top">&nbsp;</td>
				              				<td width="157">&nbsp;</td>
				            			</tr>
				          			</table>
				          		</td>
				          		<td width="103">
				          			<table width="89" border="0" cellspacing="0" cellpadding="0">
				            			<tr>
				            				
				            				
				              			</tr>
				          			</table>
				            		<label for="textfield"></label>
				            	</td>
				          		<td width="204">
				          			<table width="89" border="0" cellspacing="0" cellpadding="0">
				            			<tr>
				              				<td width="107" class="txt_menu_top">Cerrar sesi&oacute;n</td>
				              				<td width="42" class="txt_menu_top"><a href="javascript:void(0);" onclick='$("#dialog-logout").dialog( "open" );'><img src="<%=request.getContextPath()%>/resources/images/btn_cerrar.png" width="29" height="26" border="0"></a></td>
				            			</tr>
				          			</table>
				          		</td>
				        	</tr>
				      	</table>
				      	<table width="363" border="0" cellspacing="0" cellpadding="0">
				        	<tr>
				          		<td width="363" align="right" valign="middle">
				          			<TABLE border=0 cellSpacing=0 cellPadding=0 width="300" height="100%">
				            			<TBODY>
				              				<TR vAlign=top>
				                				<TD width=128 align=right valign="middle">&nbsp;</TD>
				                				<TD width=150 align="right" valign="middle">
				                					<DIV id=liveclock></DIV>
				                				</TD>
				                				<TD width=10 align="right" valign="middle">
				                					<IMG alt="Icono reloj" src="<%=request.getContextPath()%>/resources/images/icn_reloj.png" width=26 height=25>
				                				</TD>
				              				</TR>
				            			</TBODY>
				          			</TABLE>
				          		</td>
				        	</tr>
						</table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</div>
	
	<ul class="toolbar ui-widget-header ui-state-hover">
		<logic:notEmpty name="KEY_SESSION_MENU_DINAMICO_SUPERIOR">
			<logic:iterate name="KEY_SESSION_MENU_DINAMICO_SUPERIOR" id="elementoMenuSuperior" >
				<div id="<bean:write name="elementoMenuSuperior" property="cIdHTML" />">
					<logic:notEmpty name="elementoMenuSuperior" property="elementoMenuHijosDTO">
						<logic:iterate name="elementoMenuSuperior" property="elementoMenuHijosDTO" id="elementoMenuBarra" >
							<li 
								id="<bean:write name="elementoMenuBarra" property="cIdHTML" />" 
								class="<bean:write name="elementoMenuBarra" property="cClassHTML" />" 
								onclick="<bean:write name="elementoMenuBarra" property="cComando" />" 
							>									
								<span></span>
								<bean:write name="elementoMenuBarra" property="cNombre" />
							</li>
						</logic:iterate>
					</logic:notEmpty>
				</div>
			</logic:iterate>					
		</logic:notEmpty>
		<logic:empty name="KEY_SESSION_MENU_DINAMICO_SUPERIOR">
			<div id="menu_head">	
				<li id="tbarBtnHeaderZise" class="first"><span></span></li>
			</div>
			<div id="menu_config" >
			</div>
		</logic:empty>			
	</ul>
</div>
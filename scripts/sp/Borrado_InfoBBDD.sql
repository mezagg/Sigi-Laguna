--select * from AvisoDetencion
DELETE FROM FechaCompromiso
GO
DELETE FROM Convenio
go
DELETE FROM CompromisoPeriodico
GO
DELETE FROM Alerta
GO  
DELETE FROM Alarma
go
Update Funcionario set ArchivoDigital_id = null
GO
DELETE FROM AvisoDetencionDelito
GO
DELETE FROM AvisoDesignacion
GO
DELETE FROM AvisoDetencion
GO
Delete from AvisoDesignacion
go
DELETE FROM Notificacion
GO
DELETE FROM QuejaCiudadana
GO
DELETE FROM Actividad
GO
DELETE FROM Documento
GO
DELETE FROM RelNumExpedienteAuditoria
GO
--DELETE FROM Dictamen
GO
DELETE FROM SolicitudPericial
GO
DELETE FROM SolicitudAdjuntos
GO
delete from SolicitudTranscripcionAudiencia
GO
DELETE FROM SolicitudAudiencia
GO
DELETE FROM SolicitudDefensorDelito
GO
DELETE FROM InvolucradoSolicitudDefensor
GO
DELETE FROM SolicitudDefensor
GO
DELETE FROM MedidaCautelar
GO
DELETE FROM MedidaAlterna
GO
DELETE FROM MedidaAdjuntos
GO
DELETE FROM Medida
GO
DELETE FROM AcuseRecibo
GO
--DELETE SolicitudTrasladoImputado
GO
DELETE FROM SolicitudMandamiento
GO
DELETE FROM Solicitud
GO
DELETE FROM Convenio
GO
DELETE FROM EslabonDocumento
GO
DELETE FROM RelacionDocumentoElemento
GO
DELETE FROM MandamientoAdjuntos
GO
DELETE FROM OrdenDeAprehension
GO
DELETE FROM MandamientoDelitoPersona
GO
DELETE FROM Mandamiento
GO
DELETE FROM RelacionDatoMedioPrueba
GO
DELETE FROM MedioPrueba
GO
DELETE FROM AudienciaDocumento
GO
DELETE FROM RelacionDocumento
GO
DELETE FROM Documento
GO
DELETE FROM PermisoExpediente
GO
DELETE FROM HistoricoExpediente
GO
DELETE FROM Resolutivo
GO
DELETE FROM FuncionarioAudiencia
GO
DELETE FROM InvolucradoAudiencia
GO
DELETE FROM EntrevistaInicial
GO
DELETE FROM Familiar
GO
DELETE FROM EntrevistaComplementaria
GO
DELETE FROM NotaEvolucion
GO
DELETE FROM Sesion
GO
DELETE FROM DefensaInvolucrado
GO
DELETE FROM RegistroBitacora
GO
DELETE FROM AudienciaDocumento
GO
DELETE FROM FuncionarioExternoAudiencia
GO
DELETE FROM Audiencia
GO
--DELETE FROM BitacoraMovimiento
GO
--DELETE FROM BitacoraConsulta
GO
DELETE FROM AsignacionPrograma
GO
DELETE FROM AsignacionCentroDetencion
GO
DELETE FROM ActoBuenaConducta
GO
DELETE FROM InventarioPertenencia
GO
DELETE FROM Remision
GO
DELETE FROM Sentencia
GO
DELETE FROM MultaSancion
GO
DELETE FROM Inspeccion
GO
--DELETE FROM Arancel
GO
--DELETE FROM Interviniente
GO
DELETE FROM BitacoraDescarga
GO
DELETE FROM PermisoAudiencia
GO
DELETE FROM DescriptorAudiencia
GO
DELETE FROM OrganizacionAudiencia
GO
DELETE FROM Audiencia 
GO
DELETE FROM BitacoraEstatusNumExpediente
GO
DELETE FROM BitacoraPermisoExpediente
GO
DELETE FROM NumeroExpediente
GO
DELETE FROM AvisoHechoDelictivo
GO
DELETE FROM TurnoLaboralIPH
GO
DELETE FROM DelitoIPH
GO
DELETE FROM Operativo
GO
DELETE FROM ObjetoAsegurado
GO
DELETE FROM FaltaAdministrativaIPH
GO
DELETE FROM InformePolicialHomologado
GO
DELETE FROM Hecho
GO
DELETE FROM DomicilioExtranjero
GO
UPDATE Objeto SET iIdentificadorAlmacen = NULL
GO
DELETE FROM EncargadoAlmacen
GO
--Se queda un alamacen
DELETE FROM Almacen where iIdentificadorAlmacen >1
GO
UPDATE Almacen set domicilio_id = null
--SELECT * FROM Domicilio 
--SELECT * FROM Almacen
DELETE FROM Domicilio --where Domicilio_id not in (SELECT Domicilio_id FROM Almacen where iIdentificadorAlmacen =1)
GO
DELETE FROM CoordenadaGeografica
GO
DELETE FROM Pertenencia
GO
DELETE FROM Detencion
GO
DELETE FROM Lugar
GO
DELETE FROM NombreDemografico
GO
DELETE FROM InvolucradoNacionalidad
GO
DELETE FROM SeniaParticular
GO
DELETE FROM AvisoDetencion
GO
DELETE FROM Pertenencia
GO
DELETE FROM Detencion
GO
DELETE FROM MediaFiliacion
GO
DELETE FROM AliasInvolucrado
GO
DELETE FROM DelitoPersona
GO
DELETE FROM InvolucradoOcupacion
GO
DELETE FROM RelacionPruebaInvolucrado
GO
DELETE FROM Sentencia
GO
DELETE FROM DatosDefuncion
GO
DELETE FROM Involucrado
GO
DELETE FROM Telefono where Telefono_id in (select MedioDeContacto_id from MedioDeContacto where Persona_id>0)
GO
DELETE FROM CorreoElectronico where CorreoElectronico_id  in (select MedioDeContacto_id from MedioDeContacto where Persona_id>0)
GO
DELETE FROM MedioDeContacto where Persona_id>0
GO
DELETE FROM Persona
GO
DELETE FROM Relacion
GO
DELETE FROM EquipoComputo
GO
DELETE FROM Eslabon
GO
DELETE FROM Evidencia
GO
DELETE FROM Arma
GO
DELETE FROM Vehiculo
GO
DELETE FROM Explosivo
GO
DELETE FROM DocumentoOficial
GO
DELETE FROM Telefonia
GO
DELETE FROM Sustancia
GO
DELETE FROM Aeronave
GO
DELETE FROM Vegetal
GO
DELETE FROM Animal
GO
DELETE FROM Joya
GO
DELETE FROM Embarcacion
GO
DELETE FROM Numerario
GO
DELETE FROM ObjetoPericial
GO
DELETE FROM Objeto
GO
DELETE FROM ObraArte
GO
DELETE FROM Organizacion
GO
DELETE FROM RelacionReincidencia
GO
DELETE FROM BitacoraElemento
GO
DELETE FROM ElementoArchivoDigital
GO
DELETE FROM  Elemento
GO
DELETE FROM CadenaDeCustodia
GO
DELETE FROM Delito
GO
DELETE FROM NotaExpediente
GO
DELETE FROM InformePolicialHomologado
GO
DELETE FROM Turno
GO
DELETE FROM Comentario
GO
DELETE FROM LineaInvestigacion
GO
DELETE FROM SeguimientoLI
GO
DELETE FROM DatoPrueba
GO
DELETE FROM Expediente
GO
DELETE FROM Caso
GO
DELETE FROM Tarea
GO
DELETE FROM EventoCita
GO
DELETE FROM	AgendaFuncionario
GO
DELETE FROM	ArchivoDigital
GO
DELETE FROM	Calidad
GO
DELETE FROM Telefono where Telefono_id in (select MedioDeContacto_id from MedioDeContacto where Implicado_id>0)
GO
DELETE FROM CorreoElectronico where CorreoElectronico_id  in (select MedioDeContacto_id from MedioDeContacto where Implicado_id>0)
GO
DELETE FROM  MedioDeContacto where Implicado_id>0
GO
DELETE FROM	Implicado
GO
DELETE FROM	NumeroExpediente
GO
DELETE FROM	Visitante
GO
--select * from CatDelitoActuacion where CatDelito_id <133
--SELECT * from CatDelito where CatDelito_id <133
--Se mantienen los delitos
--delete from CatDelito where CatDelito_id <133
--GO
update Usuario set idSesionServer = null
update Usuario set iSesion = 0, iIntentos = 0,cIP= '0.0.0.0'
--delete from UsuarioRol where Usuario_id not in ( select Usuario_id from Usuario where iClaveFuncionario <=47)
--delete from Usuario where iClaveFuncionario>47
delete from Telefono 
delete from CorreoElectronico
delete from MedioDeContacto
--delete from Funcionario where iClaveFuncionario >47
--update Usuario set password = 0x5D4F20F64233BB0A8F902693F5294D22 
--go
--update Usuario set cllave = 0xFDE8F0AA3479D0A10E2F01EC2D5BD668 
--go
--delete from Valor where Valor_id  >5000 and CampoCatalogo_id in (118,119)
--delete from Valor where Valor_id  >5000 and Valor_id <6000 and Valor_id not in ( 5049)
--delete from CatUIEspecializada where catUIE_id >1
DELETE FROM Documento
GO

--------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------
/*Tablas de PROCURADURIA, Checar si existen en Defensoria, Tribuna y Seguridad*/
--------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------
delete from ConclusionNumeroExpediente
delete from DatosDefuncion
delete from ConclusionOrdenAprension


delete MandamientoPersona



--------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------

--DESHABILITA

alter table ConclusionNumeroExpediente CHECK CONSTRAINT ALL;
alter table ConclusionOrdenAprension CHECK CONSTRAINT ALL;
alter table FaltaAdministrativaIPH CHECK CONSTRAINT ALL;
--alter table SolicitudTrasladoImputado CHECK CONSTRAINT ALL;
alter table ElementoMenu CHECK CONSTRAINT ALL;
--alter table RolFuncion CHECK CONSTRAINT ALL;
alter table ModuloFuncion CHECK CONSTRAINT ALL;
alter table Notificacion CHECK CONSTRAINT ALL;
alter table SolicitudAdjuntos CHECK CONSTRAINT ALL;
alter table SolicitudAudiencia CHECK CONSTRAINT ALL;
alter table SolicitudDefensor CHECK CONSTRAINT ALL;
--alter table SolicitudDiligencia CHECK CONSTRAINT ALL;
alter table SolicitudMandamiento CHECK CONSTRAINT ALL;
alter table SolicitudPericial CHECK CONSTRAINT ALL;
alter table SolicitudTranscripcionAudiencia CHECK CONSTRAINT ALL;
--alter table PermisoSolicitud CHECK CONSTRAINT ALL;
alter table AcuseRecibo CHECK CONSTRAINT ALL;
alter table Detencion CHECK CONSTRAINT ALL;
alter table NotaExpediente CHECK CONSTRAINT ALL;
alter table Inspeccion CHECK CONSTRAINT ALL;
alter table Almacen CHECK CONSTRAINT ALL;
--alter table Interviniente CHECK CONSTRAINT ALL;
--alter table Arancel CHECK CONSTRAINT ALL;
alter table Documento CHECK CONSTRAINT ALL;
alter table NumeroExpediente CHECK CONSTRAINT ALL;
alter table Involucrado CHECK CONSTRAINT ALL;
alter table EncargadoAlmacen CHECK CONSTRAINT ALL;
alter table AvisoDesignacion CHECK CONSTRAINT ALL;
alter table Usuario CHECK CONSTRAINT ALL;
alter table Actividad CHECK CONSTRAINT ALL;
alter table Eslabon CHECK CONSTRAINT ALL;
alter table PermisoExpediente CHECK CONSTRAINT ALL;
--alter table BitacoraDefensor CHECK CONSTRAINT ALL;
alter table Evidencia CHECK CONSTRAINT ALL;
alter table CartaCumplimiento CHECK CONSTRAINT ALL;
--alter table QuejaConfirmada CHECK CONSTRAINT ALL;
alter table Comentario CHECK CONSTRAINT ALL;
alter table RelacionReincidencia CHECK CONSTRAINT ALL;
alter table Funcionario CHECK CONSTRAINT ALL;
alter table Medida CHECK CONSTRAINT ALL;
alter table Convenio CHECK CONSTRAINT ALL;
alter table FuncionarioAudiencia CHECK CONSTRAINT ALL;
alter table MedioDeContacto CHECK CONSTRAINT ALL;
alter table HistoricoExpediente CHECK CONSTRAINT ALL;
alter table MultaSancion CHECK CONSTRAINT ALL;
alter table AgendaFuncionario CHECK CONSTRAINT ALL;
alter table InformePolicialHomologado CHECK CONSTRAINT ALL;
alter table Alarma CHECK CONSTRAINT ALL;
alter table Solicitud CHECK CONSTRAINT ALL;
alter table Programa CHECK CONSTRAINT ALL;
alter table CatCursoCatPrograma CHECK CONSTRAINT ALL;
alter table CatTrabajoCatPrograma CHECK CONSTRAINT ALL;
alter table CentroDetencionPrograma CHECK CONSTRAINT ALL;
alter table Relacion CHECK CONSTRAINT ALL;
alter table Familiar CHECK CONSTRAINT ALL;
alter table RelacionDocumentoElemento CHECK CONSTRAINT ALL;
alter table PermisoAudiencia CHECK CONSTRAINT ALL;
alter table Asentamiento CHECK CONSTRAINT ALL;
alter table SolicitudDefensorDelito CHECK CONSTRAINT ALL;
alter table AvisoHechoDelictivo CHECK CONSTRAINT ALL;
alter table CatCurso CHECK CONSTRAINT ALL;
alter table NivelAcademico CHECK CONSTRAINT ALL;
alter table QuejaCiudadana CHECK CONSTRAINT ALL;
alter table CatPrograma CHECK CONSTRAINT ALL;
alter table Remision CHECK CONSTRAINT ALL;
--alter table Dictamen CHECK CONSTRAINT ALL;
alter table CuerpoOficioEstructurado CHECK CONSTRAINT ALL;
alter table IndiceEstructurado CHECK CONSTRAINT ALL;
alter table CatTrabajo CHECK CONSTRAINT ALL;
alter table ArmaExplosivoInvolucrado CHECK CONSTRAINT ALL;
alter table ObjetoAsegurado CHECK CONSTRAINT ALL;
alter table InvolucradoIPH CHECK CONSTRAINT ALL;
alter table Operativo CHECK CONSTRAINT ALL;
alter table TurnoLaboralIPH CHECK CONSTRAINT ALL;
alter table MedioTransporte CHECK CONSTRAINT ALL;
alter table DelitoIPH CHECK CONSTRAINT ALL;
--alter table Multimedia CHECK CONSTRAINT ALL;
alter table Trabajo CHECK CONSTRAINT ALL;
alter table Expediente CHECK CONSTRAINT ALL;
alter table CatDelito CHECK CONSTRAINT ALL;
alter table ofRosterGroups CHECK CONSTRAINT ALL;
alter table InventarioPertenencia CHECK CONSTRAINT ALL;
alter table AsignacionCentroDetencion CHECK CONSTRAINT ALL;
--alter table Sitio CHECK CONSTRAINT ALL;
alter table EventoCita CHECK CONSTRAINT ALL;
alter table Alerta CHECK CONSTRAINT ALL;
alter table Domicilio CHECK CONSTRAINT ALL;
alter table Pertenencia CHECK CONSTRAINT ALL;
alter table ArchivoDigital CHECK CONSTRAINT ALL;
alter table Hecho CHECK CONSTRAINT ALL;
alter table InvolucradoAudiencia CHECK CONSTRAINT ALL;
alter table InvolucradoNacionalidad CHECK CONSTRAINT ALL;
alter table InvolucradoOcupacion CHECK CONSTRAINT ALL;
alter table OrdenDeAprehension CHECK CONSTRAINT ALL;
--alter table Perfil CHECK CONSTRAINT ALL;
alter table Mandamiento CHECK CONSTRAINT ALL;
--alter table Biometrico CHECK CONSTRAINT ALL;
alter table MediaFiliacion CHECK CONSTRAINT ALL;
alter table RelacionPruebaInvolucrado CHECK CONSTRAINT ALL;
alter table Sentencia CHECK CONSTRAINT ALL;
alter table DefensaInvolucrado CHECK CONSTRAINT ALL;
--alter table ServidorPublico CHECK CONSTRAINT ALL;
alter table DelitoPersona CHECK CONSTRAINT ALL;
alter table AliasInvolucrado CHECK CONSTRAINT ALL;
alter table NotaEvolucion CHECK CONSTRAINT ALL;
alter table FechaCompromiso CHECK CONSTRAINT ALL;
alter table Objeto CHECK CONSTRAINT ALL;
alter table RolConfActividadDocumento CHECK CONSTRAINT ALL;
alter table CatAreasNegocio CHECK CONSTRAINT ALL;
alter table Rol CHECK CONSTRAINT ALL;
alter table FuncionarioExterno CHECK CONSTRAINT ALL;
alter table Elemento CHECK CONSTRAINT ALL;
alter table MandamientoAdjuntos CHECK CONSTRAINT ALL;
--alter table FechaCompArchDig CHECK CONSTRAINT ALL;
alter table MedidaAdjuntos CHECK CONSTRAINT ALL;
alter table Turno CHECK CONSTRAINT ALL;
alter table UsuarioRol CHECK CONSTRAINT ALL;
--alter table BitacoraConsulta CHECK CONSTRAINT ALL;
--alter table BitacoraMovimiento CHECK CONSTRAINT ALL;
alter table JerarquiaOrganizacional CHECK CONSTRAINT ALL;
alter table JerarquiaOrgTipoSolicitud CHECK CONSTRAINT ALL;
--alter table EtiquetaJerarquia CHECK CONSTRAINT ALL;
alter table CompromisoPeriodico CHECK CONSTRAINT ALL;
alter table ConfActividadDocumento CHECK CONSTRAINT ALL;
alter table Animal CHECK CONSTRAINT ALL;
alter table Numerario CHECK CONSTRAINT ALL;
alter table DocumentoOficial CHECK CONSTRAINT ALL;
alter table Arma CHECK CONSTRAINT ALL;
alter table DomicilioExtranjero CHECK CONSTRAINT ALL;
alter table Sustancia CHECK CONSTRAINT ALL;
alter table ObraArte CHECK CONSTRAINT ALL;
alter table OficioEstructurado CHECK CONSTRAINT ALL;
alter table Audiencia CHECK CONSTRAINT ALL;
alter table Embarcacion CHECK CONSTRAINT ALL;
alter table Tarea CHECK CONSTRAINT ALL;
alter table Telefonia CHECK CONSTRAINT ALL;
alter table Telefono CHECK CONSTRAINT ALL;
alter table Tiempo CHECK CONSTRAINT ALL;
alter table Organizacion CHECK CONSTRAINT ALL;
alter table EntidadFederativa CHECK CONSTRAINT ALL;
alter table Joya CHECK CONSTRAINT ALL;
alter table LeyCodigo CHECK CONSTRAINT ALL;
alter table EquipoComputo CHECK CONSTRAINT ALL;
alter table LineaInvestigacion CHECK CONSTRAINT ALL;
--alter table EquipoTactico CHECK CONSTRAINT ALL;
alter table Vegetal CHECK CONSTRAINT ALL;
alter table Vehiculo CHECK CONSTRAINT ALL;
alter table Persona CHECK CONSTRAINT ALL;
--alter table PuntoCarretero CHECK CONSTRAINT ALL;
alter table Calidad CHECK CONSTRAINT ALL;
alter table Caso CHECK CONSTRAINT ALL;
--alter table CatComponente CHECK CONSTRAINT ALL;
alter table Explosivo CHECK CONSTRAINT ALL;
alter table CatDelitoActuacion CHECK CONSTRAINT ALL;
alter table RegistroBitacora CHECK CONSTRAINT ALL;
alter table FacultadDocumento CHECK CONSTRAINT ALL;
alter table RelacionDatoMedioPrueba CHECK CONSTRAINT ALL;
alter table CentroDetencion CHECK CONSTRAINT ALL;
alter table Forma CHECK CONSTRAINT ALL;
alter table DatoPrueba CHECK CONSTRAINT ALL;
alter table Implicado CHECK CONSTRAINT ALL;
alter table Aeronave CHECK CONSTRAINT ALL;
alter table Sesion CHECK CONSTRAINT ALL;
alter table NombreDemografico CHECK CONSTRAINT ALL;
--alter table Inmediacion CHECK CONSTRAINT ALL;
--alter table Inmueble CHECK CONSTRAINT ALL;
alter table OrganizacionAudiencia CHECK CONSTRAINT ALL;
alter table Direccion CHECK CONSTRAINT ALL;
--alter table AreaGeografica CHECK CONSTRAINT ALL;
--alter table EspacioAereo CHECK CONSTRAINT ALL;
--alter table EspacioMaritimo CHECK CONSTRAINT ALL;
alter table CoordenadaGeografica CHECK CONSTRAINT ALL;
alter table AvisoDetencionDelito CHECK CONSTRAINT ALL;
alter table DescriptorAudiencia CHECK CONSTRAINT ALL;
alter table AudienciaDocumento CHECK CONSTRAINT ALL;
alter table AudienciaEvidencia CHECK CONSTRAINT ALL;
alter table Resolutivo CHECK CONSTRAINT ALL;
alter table SeniaParticular CHECK CONSTRAINT ALL;
alter table ActoBuenaConducta CHECK CONSTRAINT ALL;
alter table MedidaAlterna CHECK CONSTRAINT ALL;
alter table MedidaCautelar CHECK CONSTRAINT ALL;
--alter table Incidencia CHECK CONSTRAINT ALL;
alter table BitacoraDescarga CHECK CONSTRAINT ALL;
alter table AvisoDetencion CHECK CONSTRAINT ALL;
alter table AsignacionMedidaAlterna CHECK CONSTRAINT ALL;
alter table CorreoElectronico CHECK CONSTRAINT ALL;
alter table EslabonDocumento CHECK CONSTRAINT ALL;
alter table RelacionDocumento CHECK CONSTRAINT ALL;
alter table MedioPrueba CHECK CONSTRAINT ALL;
--alter table Nota CHECK CONSTRAINT ALL;
alter table Subproceso CHECK CONSTRAINT ALL;
alter table ProcesoRol CHECK CONSTRAINT ALL;
alter table RolModulo CHECK CONSTRAINT ALL;
alter table AsignacionPrograma CHECK CONSTRAINT ALL;
alter table Curso CHECK CONSTRAINT ALL;
alter table Lugar CHECK CONSTRAINT ALL;
alter table RolElementoMenu CHECK CONSTRAINT ALL;
alter table Valor CHECK CONSTRAINT ALL;
alter table Ciudad CHECK CONSTRAINT ALL;
alter table Municipio CHECK CONSTRAINT ALL;
alter table CampoCatalogo CHECK CONSTRAINT ALL;
--alter table Intento CHECK CONSTRAINT ALL;
alter table RelNumExpedienteAuditoria CHECK CONSTRAINT ALL;
alter table CatRelacion CHECK CONSTRAINT ALL;
--alter table ArmaBlanca CHECK CONSTRAINT ALL;
--alter table Cadaver CHECK CONSTRAINT ALL;
--alter table FluidoCorporal CHECK CONSTRAINT ALL;
--alter table MutilacionCorporal CHECK CONSTRAINT ALL;
alter table AgendaAlarma CHECK CONSTRAINT ALL;
alter table Delito CHECK CONSTRAINT ALL;
alter table CadenaDeCustodia CHECK CONSTRAINT ALL;
--alter table ExpedienteAdscrito CHECK CONSTRAINT ALL;
--alter table ExpedienteRestDefensoria CHECK CONSTRAINT ALL;
--alter table ExpedienteTecnico CHECK CONSTRAINT ALL;
alter table SeguimientoLI CHECK CONSTRAINT ALL;
alter table SalaJAVS CHECK CONSTRAINT ALL;
alter table SalaAudiencia CHECK CONSTRAINT ALL;
alter table CatDiscriminante CHECK CONSTRAINT ALL;
alter table EntrevistaComplementaria CHECK CONSTRAINT ALL;
alter table EntrevistaInicial CHECK CONSTRAINT ALL;
alter table usuario CHECK CONSTRAINT ALL;
alter table catuiespecializada CHECK CONSTRAINT ALL;
alter table cattipoasentamiento CHECK CONSTRAINT ALL;
alter table registro CHECK CONSTRAINT ALL;
alter table catDistrito CHECK CONSTRAINT ALL;
alter table confinstitucion CHECK CONSTRAINT ALL;
--alter table BitacoraAudiencia CHECK CONSTRAINT ALL;
alter table Audiencia CHECK CONSTRAINT ALL;
--alter table BitacoraEstatusNumExpediente CHECK CONSTRAINT ALL;
--alter table RelacionSolicitudDocumentoFuncionario CHECK CONSTRAINT ALL;
--alter table BitacoraPermisoExpediente CHECK CONSTRAINT ALL;


delete SolicitudAudiencia
delete FuncionarioExternoAudiencia
delete FuncionarioAudiencia
delete Audiencia


--select * from Expediente
--select * from NumeroExpediente
--select * from caso
--select * from Persona
--select * from Elemento
--select * from ArchivoDigital
--select * from documento


----exec i_Funcionario_sp 'ALAN','HERNANDEZ','PRESAS','M',2068,4035,42,1730,'1220',1,NULL,9,'alanhernandez',36

--SET IDENTITY_INSERT [dbo].[Funcionario] ON 
--INSERT [dbo].[Funcionario] ([iClaveFuncionario], [cNombreFuncionario], [cApellidoPaternoFuncionario], [cApellidoMaternoFuncionario], [cSexo], [cRFC], [cCURP], [dFechaNacimiento], [cEmail], [cCedula], [iClaveFuncionarioJefe], [Especialidad_val], [Puesto_val], [JerarquiaOrganizacional_id], [dcCargaTrabajo], [TipoEspecialidad_val], [bEsPar], [cNumeroEmpleado], [catDiscriminante_id], [dFechaIngreso], [ArchivoDigital_id], [catUIE_id], [CatAreasNegocio_id]) VALUES (CAST(1 AS Decimal(18, 0)), N'Administrador', N'SI', N'GI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, CAST(2004 AS Decimal(18, 0)), CAST(2110 AS Decimal(18, 0)), CAST(42 AS Decimal(18, 0)), CAST(1.00 AS Decimal(5, 2)), CAST(1730 AS Decimal(18, 0)), CAST(1 AS Decimal(1, 0)), N'1234', CAST(1 AS Decimal(18, 0)), NULL, NULL, NULL, CAST(1 AS Decimal(18, 0)))
--SET IDENTITY_INSERT [dbo].[Funcionario] OFF

--SET IDENTITY_INSERT [dbo].[Usuario] ON 
--INSERT [dbo].[Usuario] ([Usuario_id], [cClaveUsuario], [cPalabraSecreta], [iClaveFuncionario], [password], [cllave], [iSesion], [iIntentos], [cIP], [bEsActivo], [idSesionServer]) VALUES (CAST(1 AS Decimal(18, 0)), N'Administrador', N'password', CAST(1 AS Decimal(18, 0)), 0xFCCB40705276BFC4515B5252C7FC5978, 0x057320909A9A53AC814A21CF9361C254, CAST(0 AS Numeric(2, 0)), CAST(0 AS Numeric(2, 0)), N'172.25.23.31', CAST(1 AS Decimal(1, 0)), N'')
--SET IDENTITY_INSERT [dbo].[Usuario] OFF

--SET IDENTITY_INSERT [dbo].[UsuarioRol] ON 
--INSERT [dbo].[UsuarioRol] ([Usuario_id], [Rol_id], [dFechaInicio], [dFechaFin], [esPrincipal]) VALUES (CAST(1 AS Decimal(18, 0)), CAST(36 AS Decimal(18, 0)), CAST(getdate() AS DateTime), CAST(getdate() AS DateTime), CAST(1 AS Decimal(1, 0)))
--SET IDENTITY_INSERT [dbo].[UsuarioRol] OFF


--insert into CatDiscriminante(catDistrito_id, cClaveDiscriminante,cnombre, iTipo, bOpUIE) values(4, '044','COORDINACION DE UNIDAD DE INVESTIGACION DE SABINAS I',1,0)
--insert into CatUIEspecializada values(1,'UNIDAD DE INVESTIGACION DE SABINAS I','UISABI')
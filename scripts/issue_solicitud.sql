 alter table PermisoSolicitud
 add creadoPor varchar(30);

GO
 
 alter table PermisoSolicitud
 add modificadoPor varchar(30);

GO
 
 alter table PermisoSolicitud
 add fechacreacion datetime;

GO

 alter table PermisoSolicitud
 add fechaModificacion datetime;

GO

 ALTER TABLE [dbo].[PermisoSolicitud] ADD  CONSTRAINT [DF_PermisoSolicitud_fechaCreacion]  DEFAULT (getdate()) FOR [fechaCreacion]
GO


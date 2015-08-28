

/****** Object:  Table [dbo].[PermisoSolicitud]    Script Date: 25/08/2015 17:54:26 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PermisoSolicitud](
	[Solicitud_id] [dbo].[campo llave] NOT NULL,
	[iClaveFuncionario] [dbo].[campo llave] NOT NULL,
	[dFechaLimite] [datetime] NOT NULL,
	[bEsEscritura] [dbo].[boleano] NOT NULL,
 CONSTRAINT [PK_PermisoSolicitud] PRIMARY KEY CLUSTERED 
(
	[Solicitud_id] ASC,
	[iClaveFuncionario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[PermisoSolicitud]  WITH CHECK ADD  CONSTRAINT [FK_PermisoSolicitud_Funcionario1] FOREIGN KEY([iClaveFuncionario])
REFERENCES [dbo].[Funcionario] ([iClaveFuncionario])
GO

ALTER TABLE [dbo].[PermisoSolicitud] CHECK CONSTRAINT [FK_PermisoSolicitud_Funcionario1]
GO

ALTER TABLE [dbo].[PermisoSolicitud]  WITH CHECK ADD  CONSTRAINT [FK_PermisoSolicitud_Solicitud1] FOREIGN KEY([Solicitud_id])
REFERENCES [dbo].[Solicitud] ([Solicitud_id])
GO

ALTER TABLE [dbo].[PermisoSolicitud] CHECK CONSTRAINT [FK_PermisoSolicitud_Solicitud1]
GO



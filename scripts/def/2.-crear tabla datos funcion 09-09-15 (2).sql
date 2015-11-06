
GO

/****** Object:  Table [dbo].[DatosDefuncion]    Script Date: 09/09/2015 01:22:21 p.m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[DatosDefuncion](
	[DatosDefuncion_id] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Involucrado_id] [decimal](18, 0) NULL,
	[cFolioDefuncion] [varchar](10) NULL,
	[dFechaAveriguacion] [date] NULL,
	[dFechaDefuncion] [date] NULL,
	[TipoDefuncion_val] [decimal](18, 0) NULL,
	[CertificadaPor_val] [decimal](18, 0) NULL,
	[SitioDefuncion_val] [decimal](18, 0) NULL,
	[SitioLesion_val] [decimal](18, 0) NULL,
	[FueEnTrabajo_val] [decimal](18, 0) NULL,
	[cAgenteExterno] [varchar](200) NULL,
	[TipoEventoDefuncion_val] [decimal](18, 0) NULL,
	[TipoVictima_val] [decimal](18, 0) NULL,
	[TipoArma_val] [decimal](18, 0) NULL,
	[cCausaA] [varchar](225) NULL,
	[cDuracionA] [varchar](15) NULL,
	[cCausaB] [varchar](225) NULL,
	[cDuracionB] [varchar](15) NULL,
	[cCausaC] [varchar](225) NULL,
	[cDuracionC] [varchar](15) NULL,
	[cCausaD] [varchar](225) NULL,
	[cDuracionD] [varchar](15) NULL,
	[cEdoPatologico] [varchar](40) NULL,
	[cDuracionPatologico] [varchar](15) NULL,
	[CondicionEmbarazo_val] [decimal](18, 0) NULL,
	[PeriodoPosparto_val] [decimal](18, 0) NULL,
	[iEdadDefuncion] [decimal](3, 0) NULL,
	[EdadUnidadDefuncion_val] [decimal](18, 0) NULL,
	[CondicionActividad_val] [decimal](18, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[DatosDefuncion_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([CertificadaPor_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([CondicionEmbarazo_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([CondicionActividad_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([EdadUnidadDefuncion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([FueEnTrabajo_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([Involucrado_id])
REFERENCES [dbo].[Involucrado] ([Involucrado_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([PeriodoPosparto_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([SitioDefuncion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([SitioLesion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([TipoArma_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([TipoDefuncion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([TipoEventoDefuncion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[DatosDefuncion]  WITH CHECK ADD FOREIGN KEY([TipoVictima_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO


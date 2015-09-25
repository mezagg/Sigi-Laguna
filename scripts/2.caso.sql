ALTER TABLE [dbo].[Caso]
ALTER COLUMN [cNumeroGeneralCaso] [varchar](32);
GO

ALTER TABLE [dbo].[CatDiscriminante]
ADD [region_id] [numeric](18, 0) NULL;
GO

ALTER TABLE [dbo].[CatDistrito]
ADD [region_id] [numeric](18, 0) NULL;
GO


CREATE TABLE [dbo].[Region](
	[region_id] [numeric](18, 0) NOT NULL,
	[cClaveRegion] [nvarchar](3) NOT NULL,
	[cNombre] [nvarchar](50) NOT NULL,
	[entidadFederativa_id] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_Region] PRIMARY KEY CLUSTERED
(
	[region_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


ALTER TABLE [dbo].[CatDiscriminante]  WITH CHECK ADD  CONSTRAINT [FK_CatDiscriminante_Region] FOREIGN KEY([region_id])
REFERENCES [dbo].[Region] ([region_id])
GO

ALTER TABLE [dbo].[CatDiscriminante] CHECK CONSTRAINT [FK_CatDiscriminante_Region]
GO

ALTER TABLE [dbo].[CatDiscriminante]  WITH NOCHECK ADD  CONSTRAINT [fk01_CatDiscriminante_CatDistrito] FOREIGN KEY([catDistrito_id])
REFERENCES [dbo].[CatDistrito] ([catDistrito_id])
GO

ALTER TABLE [dbo].[CatDiscriminante] CHECK CONSTRAINT [fk01_CatDiscriminante_CatDistrito]
GO

ALTER TABLE [dbo].[CatDistrito]  WITH CHECK ADD  CONSTRAINT [FK_CatDistrito_Region] FOREIGN KEY([region_id])
REFERENCES [dbo].[Region] ([region_id])
GO

ALTER TABLE [dbo].[CatDistrito] CHECK CONSTRAINT [FK_CatDistrito_Region]
GO


/*

ALTER TABLE [dbo].[Region]
ADD [entidadFederativa_id] [decimal](18, 0) NULL;
GO

 */

ALTER TABLE [dbo].[Region]  WITH CHECK ADD  CONSTRAINT [FK_Region_EntidadFederativa] FOREIGN KEY([entidadFederativa_id])
REFERENCES [dbo].[EntidadFederativa] ([entidadFederativa_id])
GO

ALTER TABLE [dbo].[Region] CHECK CONSTRAINT [FK_Region_EntidadFederativa]
GO

ALTER TABLE [dbo].[Funcionario]
ADD [entidadFederativa_id] [decimal](18, 0) NULL;
GO

ALTER TABLE [dbo].[Funcionario]
ADD [region_id] [numeric](18, 0) NULL;
GO


ALTER TABLE [dbo].[Funcionario]  WITH CHECK ADD  CONSTRAINT [FK_Funcionario_EntidadFederativa] FOREIGN KEY([entidadFederativa_id])
REFERENCES [dbo].[EntidadFederativa] ([EntidadFederativa_id])
GO

ALTER TABLE [dbo].[Funcionario] CHECK CONSTRAINT [FK_Funcionario_EntidadFederativa]
GO

ALTER TABLE [dbo].[Funcionario]  WITH CHECK ADD  CONSTRAINT [FK_Funcionario_Region] FOREIGN KEY([region_id])
REFERENCES [dbo].[Region] ([region_id])
GO

ALTER TABLE [dbo].[Funcionario] CHECK CONSTRAINT [FK_Funcionario_Region]
GO



select * from ConclusionHecho


USE [coa-pgSaltilloCurso]
GO

/****** Object:  Table [dbo].[ConclusionNumeroExpediente]    Script Date: 30/10/2015 16:02:34 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ConclusionHecho](
	[Hecho_id] [decimal](18, 0) NOT NULL,
	[dFechaConclusion] [date] NULL,
	[TipoConclusion_val] [decimal](18, 0) NULL,
	[TipoSubConclusion_val] [decimal](18, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[Hecho_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[ConclusionHecho]  WITH CHECK ADD FOREIGN KEY([Hecho_id])
REFERENCES [dbo].[Hecho] ([Hecho_id])
GO

ALTER TABLE [dbo].[ConclusionHecho]  WITH CHECK ADD FOREIGN KEY([TipoConclusion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO

ALTER TABLE [dbo].[ConclusionHecho]  WITH CHECK ADD FOREIGN KEY([TipoSubConclusion_val])
REFERENCES [dbo].[Valor] ([Valor_id])
GO


USE [reservation]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[room](
  [roomName] [varchar](50) NOT NULL,
  [roomsn] [bigint] IDENTITY(1,1) NOT NULL
  ) ON [PRIMARY]
  GO


CREATE NONCLUSTERED INDEX [IX_roomName] ON [dbo].[room]
(
[roomName] ASC
)
INCLUDE ( 	[roomsn]) ON [PRIMARY]
GO
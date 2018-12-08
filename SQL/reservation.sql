USE [reservation]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		rudtyz
-- Create date: 2018-12-07
-- Description:	예약상황 저장을 위한 테이블
-- =============================================

CREATE TABLE [dbo].[reservation](
  [idx] [bigint] IDENTITY(1,1) NOT NULL,
  [roomsn] [bigint] NOT NULL,
  [beginTime] [datetime] NOT NULL,
  [endTime] [datetime] NOT NULL,
  [repeat] [int] NOT NULL,
  [usersn] [bigint] NOT NULL,
  ) ON [PRIMARY]
  GO


ALTER TABLE [dbo].[reservation] ADD  CONSTRAINT [PK_reservation] PRIMARY KEY CLUSTERED
  (
  [idx] ASC
  ) ON [PRIMARY]
  GO


-- 방 이름으로 조회할 수 있도록 방이름, 시작시간, 끝시간 인덱스 추가
CREATE NONCLUSTERED INDEX [IX_reservation] ON [dbo].[reservation]
(
[roomsn] ASC,
[beginTime] DESC,
[endTime] DESC
)
INCLUDE ([idx], [usersn], [repeat]) ON [PRIMARY]
GO



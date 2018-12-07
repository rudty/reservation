USE [reservation]
GO


SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		rudtyz
-- Create date: 2018-12-07
-- Description: 예약 사용자 확인을 위한 테이블.
--              각 사용자 이름, 사용자 아이디로 구성
-- =============================================
CREATE TABLE [dbo].[reservation_user](
  [userName] [varchar](80) NOT NULL,
  [usersn] [bigint] IDENTITY(1,1) NOT NULL,
  CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED
(
  [usersn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
  ) ON [PRIMARY]
  GO

-- 이름으로 검색 시 sn 추가
CREATE NONCLUSTERED INDEX [IX_userName] ON [dbo].[reservation_user]
(
[userName] ASC
)
INCLUDE ( 	[usersn])  ON [PRIMARY]
GO

-- 기본적인 join 에서 이름 보기 
CREATE NONCLUSTERED INDEX [IX_usersn] ON [dbo].[reservation_user]
(
[usersn] ASC
)
INCLUDE ( [userName]) ON [PRIMARY]
GO





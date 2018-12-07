USE [reservation]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		rudtyz
-- Create date: 2018-12-07
-- Description:	해당 방번호가 있으면 SN 반환
-- =============================================
CREATE PROCEDURE [dbo].[get_room_sn]
@roomName varchar(80)
AS
BEGIN
SET NOCOUNT ON;
select
  top 1 roomsn
from dbo.room with(nolock)
where roomName=@roomName
END

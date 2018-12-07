USE [reservation]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		rudtyz
-- Create date: 2018-12-07
-- Description:	현재 예약 상황을 확인
-- =============================================
CREATE PROCEDURE [dbo].[get_reservation_status]
@beginTime datetime,
@endTime datetime,
@roomName varchar(50)
AS
BEGIN
SET NOCOUNT ON;

select
  idx,		-- 몇번째 예약
  beginTime,	-- 회의 시작 시간
  endTime,	-- 회의 종료 시간
  userName,	-- 예약한 사람
  [repeat]	-- 반복
from [dbo].[reservation] as r
     join [dbo].[reservation_user] as u  with(nolock)
on r.usersn = u.usersn
   join [dbo].[room] with(nolock)
on r.[roomsn] = [room].[roomsn]
where 1=1
  and beginTime >= @beginTime
  and endTime < @endTime
  and roomName = @roomName
order by beginTime
END


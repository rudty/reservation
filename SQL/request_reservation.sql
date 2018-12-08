
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		rudtyz
-- Create date: 2018-12-07
-- Description:	실제로 예약을 진행합니다
-- =============================================
CREATE PROCEDURE request_reservation
@beginTime datetime,
@endTime datetime,
@roomsn bigint,
@usersn bigint,
@repeat int
AS
BEGIN

SET NOCOUNT ON;

/******************************************************************
 *
 *
 * while (true) {
 *		db에 넣음
 *		if(repeat == 0) {
 *			break;
 *		}
 *		repeat -= 1
 *      날짜 7일 증가
 * }
 *
 ******************************************************************/
  while 1=1
  begin
    insert into dbo.reservation (beginTime, endTime, [roomsn], [usersn], [repeat])
    values (@beginTime, @endTime, @roomsn, @usersn, @repeat)

     if @repeat <= 0
         break

    set @repeat -= 1
    set @beginTime = DATEADD(week, 1, @beginTime)
    set @endTime = DATEADD(week, 1, @endTime)
  end
END
GO

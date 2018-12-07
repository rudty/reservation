
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		rudtyz
-- Create date: 2018-12-07
-- Description:	현재 예약이 가능한지 확인
-- =============================================
CREATE PROCEDURE confirm_availability_reservation
@beginTime datetime,
@endTime datetime,
@roomsn bigint,
@repeatint
AS
BEGIN
  SET NOCOUNT ON;

  /******************************************************************
   *
   * int i = 0;
   * for(i = 0; i < repeat; i++) {
   *		if ( 예약 중복) {
   *			break;
   *		}
   *      날짜 7일 증가
   * }
   *
   * if(i == repeat) {
   *		return 1;
   * }
   * return 0;
   ******************************************************************/
  declare @i int = 0
  while @i < @repeat
  begin
    if exists (
      select top 1
        *
      from reservation
      where 1=1
      and roomsn = @roomsn
      and (
        (beginTime = @beginTime)
        or
        (endTime = @endTime)
        or
        (beginTime < @beginTime and @beginTime < endTime)
        or
        (beginTime < @endTime and @endTime < endTime)
        or
        (@beginTime < beginTime and beginTime < @endTime)
        or
        (@beginTime < endTime and endTime < @endTime)
      )
    )
    begin
      break
    end

    set @i +=1
    set @beginTime = DATEADD(week, 1, @beginTime)
    set @endTime = DATEADD(week, 1, @endTime)
  end

  if @i = @repeat
    select 1
  else
    select 0

  END
GO

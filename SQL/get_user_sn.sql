USE [reservation]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		rudtyz
-- Create date: 2018-12-07
-- Description:	해당 유저가 있으면 SN 반환
-- =============================================
CREATE PROCEDURE [dbo].[get_user_sn]
	@userName varchar(80)
AS
BEGIN
	SET NOCOUNT ON;
	select
		top 1 usersn
		from dbo.reservation_user with(nolock)
		where userName=@userName
END

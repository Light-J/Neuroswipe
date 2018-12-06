use brainschema;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeUserRatings`(userId Int)
BEGIN
    
DECLARE success boolean;

SET success = FALSE;

START TRANSACTION;
	DELETE FROM userratings WHERE userprofileid = userId;
	IF(SELECT COUNT(*) FROM userratings WHERE userprofileid = userId) = 0 THEN
        SET success = TRUE;
	END IF;
    
	IF Success = FALSE THEN
		ROLLBACK;
		SELECT "Transaction has been rolled back because the delete failed." as Message;
	ELSE
		COMMIT;
		SELECT CONCAT("Transaction has been commited.") as Message;
	END IF;
END//

DELIMITER ;
use brainschema;
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeUserFromRatings`(userId Int)
BEGIN
    
DECLARE success boolean;

SET success = FALSE;

START TRANSACTION;
	IF(SELECT COUNT(*) FROM userresponse WHERE userprofileid <>0) THEN
		UPDATE userresponse SET userprofileid = null WHERE userprofileid = userId;
        SET success = TRUE;
	END IF;
    
	IF Success = FALSE THEN
		ROLLBACK;
		SELECT "Transaction has been rolled back because the updates failed." as Message;
	ELSE
		COMMIT;
		SELECT CONCAT("Transaction has been commited.") as Message;
	END IF;

END//

DELIMITER ;
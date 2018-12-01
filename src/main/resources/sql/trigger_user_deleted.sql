-- Trigger on DELETE
-- When a user account is removed due to GDPR we need to set all responses and raitings to null
DELIMITER //
DROP TRIGGER IF EXISTS brainschema.TRIGGER_user_removed//
USE brainschema//
CREATE DEFINER = CURRENT_USER 
TRIGGER brainschema.TRIGGER_user_removed 
AFTER DELETE ON useraccount FOR EACH ROW
BEGIN

	UPDATE userresponse SET userprofileid = null WHERE userprofileid = (old.id);
    UPDATE userrating SET userprofileid = null WHERE userprofileid = (old.id);

END//
DELIMITER ;


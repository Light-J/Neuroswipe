-- Trigger on DELETE
-- When a user account is removed due to GDPR we need to set all responses and raitings to null
DELIMITER //
DROP TRIGGER IF EXISTS brainschema.TRIGGER_user_deleted//
USE brainschema//
CREATE DEFINER = CURRENT_USER 
TRIGGER brainschema.TRIGGER_user_deleted 
BEFORE DELETE ON useraccounts FOR EACH ROW
BEGIN
	DECLARE user_profile_id integer;
    
    SET user_profile_id := (SELECT userprofileid FROM userprofiles WHERE useraccountid = old.id);
    
    UPDATE userratings SET userprofileid = null WHERE userprofileid = user_profile_id;
    DELETE FROM userfeedback WHERE userprofileid = user_profile_id;
	DELETE FROM userprofiles WHERE id = old.id;
    
END//
DELIMITER ;


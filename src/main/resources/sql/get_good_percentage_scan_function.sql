-- Function to return the percentage of responses that were good for a scan
DELIMITER //
CREATE FUNCTION get_good_percentage_for_scan(scanId int) 
RETURNS FLOAT 
BEGIN

RETURN (SELECT sum(response)/count(scanid)*100 
		FROM userrating WHERE scanid = scanId group by scanid);

END//
DELIMITER ;
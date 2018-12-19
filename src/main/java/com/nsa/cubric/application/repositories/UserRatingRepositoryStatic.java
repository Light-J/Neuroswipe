package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRatingRepositoryStatic implements UserRatingRepository {

	private JdbcTemplate jdbcTemplate;

	private RowMapper<UserRating> responsesMapper;

	@Autowired
	public UserRatingRepositoryStatic(JdbcTemplate aTemplate) {
		jdbcTemplate = aTemplate;

		responsesMapper = (rs, i) -> new UserRating(
				rs.getLong("rating_id"),
				rs.getLong("profile_id"),
				rs.getInt("scan_id"),
				rs.getBoolean("response"));
	}

	@Override
	public void storeUserRatings(UserRating rating) {
		jdbcTemplate.update("INSERT INTO rating (profile_id, scan_id, response) values (?,?,?)",
				rating.getUserProfileId(), rating.getScanId(), rating.getResponse());
	}

	@Override
	public List<UserRating> getAll() {
		return jdbcTemplate.query("SELECT rating_id, profile_id, scan_id, response FROM rating", new Object[] {},
				responsesMapper);
	}

	@Override
	public List<UserRating> getUserRatings(String userEmail) {
		return jdbcTemplate.query("SELECT rating_id, profile_id, scan_id, response FROM rating WHERE profile_id = \n" +
						"	(SELECT profile_id FROM profile WHERE account_id = \n" +
						"		(SELECT account_id FROM account WHERE email = ?));",
				new Object[] {userEmail}, responsesMapper);
	}
}

package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRatingRepository implements UserRatingRepositoryStatic {

	private JdbcTemplate jdbcTemplate;

	private RowMapper<UserRating> responsesMapper;

	@Autowired
	public UserRatingRepository(JdbcTemplate aTemplate) {
		jdbcTemplate = aTemplate;

		responsesMapper = (rs, i) -> new UserRating(rs.getLong("id"), rs.getLong("userprofileid"), rs.getInt("scanid"),
				rs.getBoolean("response"));
	}

	@Override
	public void storeUserRatings(UserRating rating) {
		jdbcTemplate.update("INSERT INTO userratings (userprofileid, scanid, response) values (?,?,?)",
				rating.getUserProfileId(), rating.getScanId(), rating.getResponse());
	}

	@Override
	public List<UserRating> getAll() {
		return jdbcTemplate.query("SELECT id, userprofileid, scanid, response FROM userratings", new Object[] {},
				responsesMapper);
	}

	@Override
	public List<UserRating> getUserRatings(String userEmail) {
		return jdbcTemplate.query("SELECT id, userprofileid, scanid, response FROM userratings WHERE userprofileid = \n" +
						"	(SELECT id FROM userprofiles WHERE useraccountid = \n" +
						"		(SELECT id FROM useraccounts WHERE email = ?));",
				new Object[] {userEmail}, responsesMapper);
	}
}

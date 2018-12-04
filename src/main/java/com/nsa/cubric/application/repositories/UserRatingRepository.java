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

		responsesMapper = (rs, i) -> new UserRating(rs.getLong("id"), rs.getLong("userprofileid"), rs.getInt("imageid"),
				rs.getBoolean("response"));
	}

	@Override
	public void storeUserRatings(UserRating rating) {
		jdbcTemplate.update("INSERT INTO userratings (userprofileid, imageid, response) values (?,?,?)",
				rating.getUserProfileId(), rating.getImageId(), rating.getResponse());
	}

	@Override
	public List<UserRating> getAll() {
		return jdbcTemplate.query("SELECT id, userprofileid, imageid, response FROM userratings", new Object[] {},
				responsesMapper);
	}

	@Override
	public List<UserRating> getUserRatings(String userProfileId) {
		return jdbcTemplate.query("SELECT id, userprofileid, imageid, response FROM userratings WHERE userprofileid = ?",
				new Object[] { Integer.parseInt(userProfileId) }, responsesMapper);
	}
}

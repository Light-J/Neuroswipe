package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserResponseRepository implements UserResponseRepositoryStatic {

	private JdbcTemplate jdbcTemplate;

	private RowMapper<UserResponse> responsesMapper;

	@Autowired
	public UserResponseRepository(JdbcTemplate aTemplate) {
		jdbcTemplate = aTemplate;

		responsesMapper = (rs, i) -> new UserResponse(rs.getLong("id"), rs.getLong("userprofileid"),
				rs.getInt("scanid"), rs.getBoolean("response"));
	}

	@Override
	public void storeUserResponse(UserResponse response) {
		jdbcTemplate.update("INSERT INTO userresponses (userprofileid, scanid, response) values (?,?,?)",
				response.getUserProfileId(), response.getScanId(), response.getResponse());
	}

	@Override
	public List<UserResponse> getAll() {
		return jdbcTemplate.query("SELECT id, userprofileid, scanid, response FROM userresponses", new Object[] {},
				responsesMapper);
	}

	@Override
	public List<UserResponse> getUserResponses(String userProfileId) {
		return jdbcTemplate.query(
				"SELECT id, userprofileid, scanid, response FROM userresponses WHERE userprofileid = ?",
				new Object[] { Integer.parseInt(userProfileId) }, responsesMapper);
	}
}

package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScanRepository implements ScanRepositoryStatic {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Scan> imageMapper;

    @Autowired
    public ScanRepository(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        imageMapper = (rs, i) -> new Scan(
                rs.getInt("id"),
                rs.getString("path1"),
                rs.getString("path2"),
                rs.getString("path3"),
                (Boolean) rs.getObject("known_good")
        );
    }

    @Override
    public Scan findById(Long id){
        try{
            return jdbcTemplate.queryForObject(
                    "select id, path1, path2, path3, known_good from images WHERE id = ?",
                    new Object[]{id},imageMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insert(Scan scan){
        jdbcTemplate.update(
                "INSERT into images (path1, path2, path3, known_good) values (?, ?, ?, ?)",
                scan.getPath1(), scan.getPath2(), scan.getPath3(), scan.getKnownGood());
    }

    @Override
    public void updateKnownGood(Long id, Boolean knownGood) {
        jdbcTemplate.update(
                "UPDATE images SET known_good = ? WHERE id = ?",
                knownGood, id);
    }

    @Override
    public List<Scan> getAll(){
        return jdbcTemplate.query(
                "SELECT id, path1, path2, path3, known_good FROM images",
                new Object[]{},imageMapper
        );
    }
}

package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ScanRepository implements ScanRepositoryStatic {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Scan> scanMapper;

    @Autowired
    public ScanRepository(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        scanMapper = (rs, i) -> new Scan(
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
                    "SELECT id, path1, path2, path3, known_good FROM scans WHERE id = ?",
                    new Object[]{id},scanMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insert(Scan scan){
        jdbcTemplate.update(
                "INSERT into scans (path1, path2, path3, known_good) values (?, ?, ?, ?)",
                scan.getPath1(), scan.getPath2(), scan.getPath3(), scan.getKnownGood());
    }

    @Override
    public void updateKnownGood(Long id, Boolean knownGood) {
        jdbcTemplate.update(
                "UPDATE scans SET known_good = ? WHERE id = ?",
                knownGood, id);
    }

    @Override
    public List<Scan> getAll(int offset){
        return jdbcTemplate.query(
                "SELECT id, path1, path2, path3, known_good FROM scans LIMIT ?, 10",
                new Object[]{offset}, scanMapper
        );
    }

    @Override
    public Optional<Scan> getNext(){
        return jdbcTemplate.query(
                "SELECT * FROM scans ORDER BY RAND() LIMIT 0,1", scanMapper
        ).stream().findFirst();
    }

    @Override
    public Optional<Scan> getNextPractice(){
        return jdbcTemplate.query(
                "SELECT * FROM scans WHERE known_good in (1,0) ORDER BY RAND() LIMIT 0,1", scanMapper
        ).stream().findFirst();
    }

    @Override
    public List<Scan> getScansFiltered(int minResponses, int percentageGood){
        return jdbcTemplate.query(
                "SELECT * \n" +
                        "FROM scans WHERE known_good is null AND id in \n" +
                        "   (SELECT scanid FROM userratings\n" +
                        "       GROUP BY scanid \n" +
                        "       HAVING count(scanid) >= ? AND sum(response)/count(scanid)*100 >= ?);",
                new Object[]{minResponses, percentageGood}, scanMapper
        );
    }

    @Override
    public List<Scan> getScansFilteredPaginated(int minResponses, int percentageGood, int offset){
        return jdbcTemplate.query(
                "SELECT * \n" +
                        "FROM scans WHERE known_good is null AND id in \n" +
                        "   (SELECT scanid FROM userratings\n" +
                        "       GROUP BY scanid \n" +
                        "       HAVING count(scanid) >= ? AND sum(response)/count(scanid)*100 >= ?)" +
                        "LIMIT ?, 10;",
                new Object[]{minResponses, percentageGood, offset}, scanMapper
        );
    }
}

package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScanRepositoryStatic implements ScanRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Scan> scanMapper;

    @Autowired
    public ScanRepositoryStatic(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        scanMapper = (rs, i) -> new Scan(
                rs.getInt("scan_id"),
                rs.getString("top_image"),
                rs.getString("front_image"),
                rs.getString("side_image"),
                (Boolean) rs.getObject("known_good"),
                rs.getString("bad_reason")
        );
    }

    @Override
    public Scan findById(Long id){
        try{
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM scan WHERE scan_id = ?",
                    new Object[]{id},scanMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insert(Scan scan){
        jdbcTemplate.update(
                "INSERT into scan (top_image, front_image, side_image, known_good, bad_reason) values (?, ?, ?, ?)",
                scan.getTopImage(), scan.getSideImage(), scan.getFrontImage(), scan.getKnownGood());
    }

    @Override
    public void updateKnownGood(Long id, Boolean knownGood) {
        jdbcTemplate.update(
                "UPDATE scan SET known_good = ? WHERE scan_id = ?",
                knownGood, id);
    }

    @Override
    public void updateBadReason(Long id, String badReason) {
        jdbcTemplate.update(
                "UPDATE scan SET bad_reason = ? WHERE scan_id = ?",
                badReason, id);
    }

    @Override
    public List<Scan> getAll(int offset){
        return jdbcTemplate.query(
                "SELECT * FROM scan LIMIT ?, 10",
                new Object[]{offset}, scanMapper
        );
    }

    @Override
    public Optional<Scan> getNext(){
        return jdbcTemplate.query(
                "SELECT * FROM scan ORDER BY RAND() LIMIT 0,1", scanMapper
        ).stream().findFirst();
    }

    @Override
    public Optional<Scan> getNextPractice(){
        return jdbcTemplate.query(
                "SELECT * FROM scan WHERE known_good in (1,0) ORDER BY RAND() LIMIT 0,1", scanMapper
        ).stream().findFirst();
    }

    @Override
    public List<Scan> getScansFiltered(int minResponses, int percentageGood){
        return jdbcTemplate.query(
                "SELECT * \n" +
                        "FROM scan WHERE known_good is null AND scan_id in \n" +
                        "   (SELECT scan_id FROM rating\n" +
                        "       GROUP BY scan_id \n" +
                        "       HAVING count(scan_id) >= ? AND sum(response)/count(scan_id)*100 >= ?);",
                new Object[]{minResponses, percentageGood}, scanMapper
        );
    }

    @Override
    public List<Scan> getScansFilteredPaginated(int minResponses, int percentageGood, int offset){
        return jdbcTemplate.query(
                "SELECT * \n" +
                        "FROM scan WHERE known_good is null AND scan_id in \n" +
                        "   (SELECT scan_id FROM rating\n" +
                        "       GROUP BY scan_id \n" +
                        "       HAVING count(scan_id) >= ? AND sum(response)/count(scan_id)*100 >= ?)" +
                        "LIMIT ?, 10;",
                new Object[]{minResponses, percentageGood, offset}, scanMapper
        );
    }
}

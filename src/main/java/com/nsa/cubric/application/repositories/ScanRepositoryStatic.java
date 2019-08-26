package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Scan;
import com.nsa.cubric.application.domain.ScanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ScanRepositoryStatic implements ScanRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Scan> scanMapper;
    private RowMapper<ScanResult> scanResultMapper;

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

        scanResultMapper = (rs, i) -> new ScanResult(
                rs.getString("name").replaceAll("top/", ""),
                rs.getInt("good"),
                rs.getInt("bad")
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
    public void updateReason(Long id, String badReason) {
        jdbcTemplate.update(
                "UPDATE scan SET bad_reason = ? WHERE scan_id = ?",
                badReason, id);
    }

    @Override
    public List<Scan> getAll(int offset, Boolean onlyPractice){
        String filterSQL = "";
        if(onlyPractice){
            filterSQL = " WHERE known_good is not null ";
        }
        return jdbcTemplate.query(
                "SELECT * FROM scan " + filterSQL + "LIMIT ?, 10",
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

    @Override
    public List<ScanResult> getScanResults(Integer[] scanIds){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        NamedParameterJdbcTemplate template =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        List ids = Arrays.asList(scanIds);
        Map<String, List> paramMap = Collections.singletonMap("ids", ids);


        List<ScanResult> results =  template.query("select sum(response) as 'good', count(*)-sum(response) as 'bad', s.top_image as 'name' from rating JOIN scan s on rating.scan_id = s.scan_id WHERE rating.scan_id IN (:ids) GROUP BY rating.scan_id", paramMap, scanResultMapper);
        System.out.println(results.toString());
        return results;
    }
}

package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageRepository implements ImageRepositoryStatic {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Image> imageMapper;

    @Autowired
    public ImageRepository(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        imageMapper = (rs, i) -> new Image(
                rs.getInt("id"),
                rs.getString("path1"),
                rs.getString("path2"),
                rs.getString("path3"),
                (Boolean) rs.getObject("known_good")
        );
    }

    @Override
    public Image findById(Long id){
        try{
            return jdbcTemplate.queryForObject(
                    "select id, path1, path2, path3, known_good from images WHERE id = ?",
                    new Object[]{id},imageMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insert(Image image){
        jdbcTemplate.update(
                "INSERT into images (path1, path2, path3, known_good) values (?, ?, ?, ?)",
                image.getPath1(), image.getPath2(), image.getPath3(), image.getKnownGood());
    }

    @Override
    public void updateKnownGood(Long id, Boolean knownGood) {
        jdbcTemplate.update(
                "UPDATE images SET known_good = ? WHERE id = ?",
                knownGood, id);
    }

    @Override
    public List<Image> getAll(){
        return jdbcTemplate.query(
                "SELECT id, path1, path2, path3, known_good FROM images",
                new Object[]{},imageMapper
        );
    }
}

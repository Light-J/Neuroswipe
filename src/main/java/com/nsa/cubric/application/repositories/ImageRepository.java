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
                rs.getString("path")
        );
    }

    @Override
    public Image findById(Long id){
        try{
            return jdbcTemplate.queryForObject(
                    "select id, path from images WHERE id = ?",
                    new Object[]{id},imageMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void insert(Image image){
        jdbcTemplate.update(
                "INSERT into images (path) values (?)",
                image.getPath());
    }

    @Override
    public List<Image> getAll(){
        return jdbcTemplate.query(
                "SELECT id, path FROM images",
                new Object[]{},imageMapper
        );
    }
}

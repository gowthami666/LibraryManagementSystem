package com.utd.libmgmt.DaoRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.Author;

public class AuthorRowMapper implements RowMapper<Author> {

	@Override
	public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
		Author author = new Author();
		author.setAuthorName(rs.getString("name"));
		author.setIsbn(rs.getString("isbn"));
		return author;
	}

}

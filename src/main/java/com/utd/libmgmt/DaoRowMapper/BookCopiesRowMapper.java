package com.utd.libmgmt.DaoRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.libmgmt.model.Book;

public class BookCopiesRowMapper implements RowMapper<Book> {

	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();
		book.setIsbn(rs.getString("isbn"));
		book.setCopies(rs.getInt("copies"));
		return book;
	}

}

package com.farm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;


import com.farm.model.Category;
import com.farm.model.Product;


@Service
public class ProductService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	public Integer createCategory(Category category) {
		final String sql = "INSERT INTO category_master (cat_name,cat_desc) values(?,?)";

		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = null;
				try {
					ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, category.getName());
					ps.setString(2, category.getDescription());

					return ps;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					
				}
				return ps;
			}
		}, holder);

		int categoryId = holder.getKey().intValue();
		return categoryId;
	}


	public void createProduct(Product product) {

		try {
			final File image = new File(product.getImage());
			final InputStream imageIs = new FileInputStream(image);
			LobHandler lobHandler = new DefaultLobHandler();
			jdbcTemplate.update("INSERT INTO product_inv (category,product,product_img, product_desc) values(?,?,?,?)",
					new Object[] { product.getCategoryId(), product.getName(),
							new SqlLobValue(imageIs, (int) image.length(), lobHandler), product.getDescription() },
					new int[] { Types.INTEGER, Types.VARCHAR, Types.BLOB, Types.VARCHAR });
		} catch (DataAccessException e) {
			System.out.println("DataAccessException " + e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("DataAccessException " + e.getMessage());
		}
	}
}

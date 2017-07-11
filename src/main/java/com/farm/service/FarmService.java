package com.farm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.farm.entity.Product;
import com.farm.model.Farm;
import com.farm.model.Login;
import com.farm.model.User;

@Service
public class FarmService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
    public User create(final User user) 
    {
        final String sql = "INSERT INTO user (name,address,contact,lat,lon,uAliasName,uPass)values(?,?,?,?,?,?,?)";
 
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = null;
				try{
				    ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	                ps.setString(1, user.getuName());
	                ps.setString(2, user.getuAddress());
	                ps.setString(3, user.getuPhoneNo());
	                ps.setBigDecimal(4, user.getuLat());
	                ps.setBigDecimal(5, user.getuLong());
	                ps.setString(6, user.getuAliasName());
	                ps.setString(7, user.getuPass());
				return ps;
				}
				catch(Exception e){
					e.printStackTrace();
				}
				finally{
				}
				return ps;
			}
        }, holder);
 
        int newUserId = holder.getKey().intValue();
        user.setuId(newUserId);
        return user;
    }


    public boolean login(Login login) {

    	String sql = "SELECT count(*) FROM user WHERE uAliasName = ? and uPass = ?";
    	boolean result = false;

    	int count = jdbcTemplate.queryForObject(
    			sql, new Object[] { login.getUserName() ,login.getPassword()}, Integer.class);

    	if (count > 0) {
    		result = true;
    	}

    	return result;
    }
    public void addSellerProducts(Farm farm) throws ParseException {
    	try {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		final File image = new File(farm.getImgFilePath()); 
    		final InputStream imageIs = new FileInputStream(image); 
    		LobHandler lobHandler = new DefaultLobHandler(); 
    		jdbcTemplate.update( "INSERT INTO seller_products (prod_id,prod_name,prod_desc,prod_img,prod_delivery_mode,seller_id,prod_quantity,product_expiry,active) VALUES (?,?, ?,?,?,?,?,?,?)", new Object[] { 
    				farm.getProdId(),
    				farm.getProdName(),
    				farm.getProdDesc(),
    				new SqlLobValue(imageIs, (int)image.length(), lobHandler),
    				farm.getProdDeliveryMode(),
    				auth.getName(),
    				farm.getProdQuantity(),
    				new Timestamp(new java.text.SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(farm.getProductExpiry().toString()).getTime()),
    				true
    				}, new int[] {
    						Types.INTEGER,
    						Types.VARCHAR, 
    						Types.VARCHAR, 
    						Types.BLOB,
    						Types.VARCHAR, 
    						Types.VARCHAR,
    						Types.INTEGER,
    						Types.TIMESTAMP,
    						Types.TINYINT}); 
    		} 
    	catch (DataAccessException e) { 
    			System.out.println("DataAccessException " + e.getMessage()); 
    			} 
    	catch (FileNotFoundException e){ 
    		System.out.println("DataAccessException " + e.getMessage()); 
    		}
    	}
    
	public List<Farm> getImage() {
		 return jdbcTemplate.query("SELECT product_img FROM product_inv", new RowMapper<Farm>() {
            @Override
            public Farm mapRow(ResultSet rs, int rowNum)throws SQLException {
            	Farm farm = new Farm();
           	 	farm.setProdImg(rs.getBlob("product_img"));
                return farm;
            }
        });
	}


	public List<Product> populateProduct(Farm farm) {
		 return jdbcTemplate.query("SELECT * FROM product_inv where category=?",new Object[] { farm.getCategory() },
				 new RowMapper<Product>() {
	            @Override
	            public Product mapRow(ResultSet rs, int rowNum)throws SQLException {
	            	Product product = new Product();
	            	product.setProdId(rs.getInt("product_id"));
	            	product.setProdName(rs.getString("product"));
	                return product;
	            }
	        });
	}
}

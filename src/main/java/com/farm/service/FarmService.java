package com.farm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.farm.entity.Category;
import com.farm.entity.Product;
import com.farm.entity.SellerProduct;
import com.farm.entity.User;
import com.farm.model.Farm;
import com.farm.model.Login;

@Service
public class FarmService {

	@Autowired
    private JdbcTemplate jdbcTemplate;


    public void addSellerProducts(Farm farm) throws ParseException {
    	try {
    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		final File image = new File(farm.getImgFilePath()); 
    		final InputStream imageIs = new FileInputStream(image); 
    		LobHandler lobHandler = new DefaultLobHandler(); 
    		jdbcTemplate.update( "INSERT INTO seller_products (prod_id,prod_name,prod_desc,prod_img,prod_delivery_mode,seller_id,"
    				+ "prod_quantity,product_expiry,active,product_units,product_price) VALUES (?,?, ?,?,?,?,?,?,?,?,?)", new Object[] { 
    				farm.getProdId(),
    				farm.getProdName(),
    				farm.getProdDesc(),
    				new SqlLobValue(imageIs, (int)image.length(), lobHandler),
    				farm.getProdDeliveryMode(),
    				auth.getName(),
    				farm.getProdQuantity(),
    				new Timestamp(new java.text.SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(farm.getProdExpiry().toString()).getTime()),
    				true,
    				farm.getProdUnits(),
    				farm.getProdPrice()
    				}, new int[] {
    						Types.INTEGER,
    						Types.VARCHAR, 
    						Types.VARCHAR, 
    						Types.BLOB,
    						Types.VARCHAR, 
    						Types.VARCHAR,
    						Types.INTEGER,
    						Types.TIMESTAMP,
    						Types.TINYINT,
    						Types.VARCHAR,
    						Types.DECIMAL
    						}); 
    		} 
    	catch (DataAccessException e) { 
    			System.out.println("DataAccessException " + e.getMessage()); 
    			} 
    	catch (FileNotFoundException e){ 
    		System.out.println("DataAccessException " + e.getMessage()); 
    		}
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

	
	public List<Category> getCategory() {
		 return jdbcTemplate.query("SELECT * FROM category_master",new Object[] {},
				 new RowMapper<Category>() {
	            @Override
	            public Category mapRow(ResultSet rs, int rowNum)throws SQLException {
	            	Category cat = new Category();
	            	cat.setCatId(rs.getInt("id"));
	            	cat.setCatName(rs.getString("cat_name"));
	                return cat;
	            }
	        });
	}
	
	
	public List<SellerProduct> getSellerProducts(Farm farm) {
		 return jdbcTemplate.query("SELECT * FROM seller_products where prod_name =?",new Object[] {farm.getProdName()},
				 new RowMapper<SellerProduct>() {
	            @Override
	            public SellerProduct mapRow(ResultSet rs, int rowNum)throws SQLException {
	            	SellerProduct sellerProduct = new SellerProduct();
	            	sellerProduct.setProdImg(rs.getBlob("prod_img"));
	            	sellerProduct.setProdDeliveryMode(rs.getString("prod_delivery_mode"));
	            	sellerProduct.setProductExpiry(rs.getTimestamp("product_expiry"));
	            	sellerProduct.setProductPrice(rs.getBigDecimal("product_price"));
	            	sellerProduct.setProdName(rs.getString("prod_name"));
	            	sellerProduct.setId(rs.getInt("id"));
	            	System.err.println("sellerName:"+rs.getString("seller_id"));
	            	sellerProduct.setSellerId(rs.getString("seller_id"));
	                return sellerProduct;
	            }
	        });
	}
	
	public User getUser(String userName){
		
		 return jdbcTemplate.queryForObject("SELECT * FROM user where username =?",new Object[]  {userName},new RowMapper<User>(){
	            @Override
	            public User mapRow(ResultSet rs, int rowNum)throws SQLException {
	            	User user = new User();
	            	user.setLat(rs.getBigDecimal("lat"));
	            	user.setLon(rs.getBigDecimal("lon"));
	            	user.setAddress(rs.getString("address"));
	            	user.setName(rs.getString("name"));
	            	user.setuPhoneNo(rs.getString("contact"));
	            	return user;
	            }
	        });
	}
	
	public User getUserById(String userId){
		
		 return jdbcTemplate.queryForObject("SELECT * FROM user where uid =?",new Object[]  {userId},new RowMapper<User>(){
	            @Override
	            public User mapRow(ResultSet rs, int rowNum)throws SQLException {
	            	User user = new User();
	            	user.setLat(rs.getBigDecimal("lat"));
	            	user.setLon(rs.getBigDecimal("lon"));
	            	user.setAddress(rs.getString("address"));
	            	user.setName(rs.getString("name"));
	            	user.setuPhoneNo(rs.getString("contact"));
	            	return user;
	            }
	        });
	}
}

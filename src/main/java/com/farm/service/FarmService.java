package com.farm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

import com.farm.entity.Category;
import com.farm.entity.Order;
import com.farm.entity.OrderSummary;
import com.farm.entity.Product;
import com.farm.entity.SellerProduct;
import com.farm.entity.User;
import com.farm.model.BasketObject;
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
	            	sellerProduct.setProdQuantity(rs.getInt("prod_quantity"));
	            	sellerProduct.setUnits(rs.getString("product_units"));
	            	sellerProduct.setProductPrice(rs.getBigDecimal("product_price"));
	            	sellerProduct.setSellerId(rs.getString("seller_id"));
	            	sellerProduct.setId(rs.getInt("id"));
	                return sellerProduct;
	            }
	        });
	}
	
		public Integer createOrder(String buyerName, BigDecimal amount){
			final String sql = "INSERT INTO efarm_orders (buyer_id,amount) values(?,?)";
			 
	        KeyHolder holder = new GeneratedKeyHolder();
	        jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = null;
					try{
					    ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		                ps.setString(1, buyerName);
		                ps.setBigDecimal(2, amount);
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
	 
	        int orderId = holder.getKey().intValue();
	        return orderId;
	}
		
	
	public void createOrderSummary(Farm farm){
		
		
		 String sql = "INSERT INTO "
			        + "order_summary "
			        + "(order_id,seller_id,amount,product_id, product_quantity, product_units) "
			        + "VALUES " + "(?,?,?,?,?,?)";

			    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						 BasketObject orderSummary =farm.getBasket().get(i);
				            ps.setInt(1, orderSummary.getOrderId());
				            ps.setString(2, orderSummary.getSellerId());
				            ps.setBigDecimal(3, orderSummary.getItemPrice());
				            ps.setInt(4, orderSummary.getSellerProdId());
				            ps.setDouble(5, orderSummary.getQuantity());
				            ps.setString(6, orderSummary.getProdUnits());
					}

					@Override
					public int getBatchSize() {
						 return farm.getBasket().size();
					}
				});
	}
	
	
	
	public List<OrderSummary> getOrderSummary(Integer orderId) {
		 return jdbcTemplate.query("select sp.prod_name, u.address from seller_products sp, user u where sp.id in "
		 		+ " (select product_id from order_summary where order_id = ?)  and u.username in (select seller_id from order_summary where order_id = ?)  "
		 		+ " and sp.seller_id = u.username;",new Object[] {orderId,orderId},
				 new RowMapper<OrderSummary>() {
	            @Override
	            public OrderSummary mapRow(ResultSet rs, int rowNum)throws SQLException {
	            	OrderSummary summary = new OrderSummary();
	            	summary.setName(rs.getString("prod_name"));
	            	summary.setAddress(rs.getString("address"));
	                return summary;
	            }
	        });
	}



	public String getUserContact(String name) {
	    String sql = "SELECT contact FROM user WHERE username=?";
	    String contact = (String) jdbcTemplate.queryForObject(
	            sql, new Object[] { name }, String.class);
	    return contact;
	}
}

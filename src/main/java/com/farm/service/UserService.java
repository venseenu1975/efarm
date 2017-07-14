package com.farm.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.farm.entity.User;



@Service
public class UserService {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
    public void create(final com.farm.model.User user) 
    {
        final String sql = "INSERT INTO user (name,address,contact,lat,lon,username,password)values(?,?,?,?,?,?,?)";
 
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
 
      /*  int newUserId = holder.getKey().intValue();
        user.setuId(newUserId);
        return user;*/
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

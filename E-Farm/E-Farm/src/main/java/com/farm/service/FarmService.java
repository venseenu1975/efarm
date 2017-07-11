package com.farm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
import org.springframework.stereotype.Service;

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
				    ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
    
    public void insertImage(Farm farm) {
    	try {
    		final File image = new File(farm.getImgFilePath()); 
    		final InputStream imageIs = new FileInputStream(image); 
    		LobHandler lobHandler = new DefaultLobHandler(); 
    		jdbcTemplate.update( "INSERT INTO product_inv (category, product_img) VALUES (?, ?)", new Object[] { 
    				farm.getCategory(), new SqlLobValue(imageIs, (int)image.length(), lobHandler), 
    				}, new int[] {Types.VARCHAR, Types.BLOB}); 
    		} 
    	catch (DataAccessException e) { 
    			System.out.println("DataAccessException " + e.getMessage()); 
    			} 
    	catch (FileNotFoundException e){ 
    		System.out.println("DataAccessException " + e.getMessage()); 
    		}
    	}
    

/*    public byte[] getImage() {
        Blob img = null;
        byte[] imgData = null;
        String sql = "SELECT img_data FROM trn_imgs";

        try {
            img = jdbcTemplate.queryForObject(sql, new Object[]{}, new RowMapper<Blob>() {

                @Override
                public Blob mapRow(ResultSet rs, int arg1) throws SQLException {
                    Blob blob = rs.getBlob("img_data");
                    return blob;
                }
            });
            imgData = img.getBytes(1, (int) img.length());
            return imgData;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/
    
    
	public List<Farm> getImage() {
		 return jdbcTemplate.query("SELECT img_data FROM trn_imgs", new RowMapper<Farm>() {
            @Override
            public Farm mapRow(ResultSet rs, int rowNum)throws SQLException {
            	Farm farm = new Farm();
           	 	farm.setProdImg(rs.getBlob("img_data"));
                return farm;
            }
        });
	}
}

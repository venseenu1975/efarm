package com.farm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.model.Farm;
import com.farm.model.Login;
import com.farm.model.User;
import com.farm.service.FarmService;


@Controller
public class FarmController {
	
	@Autowired
	FarmService farmService;

	@ModelAttribute(name="user")
	public User createUserModel() {
		return new User();
	}
	
	@ModelAttribute(name="login")
	public Login createLoginModel() {
		return new Login();
	}
	
	@ModelAttribute(name="farm")
	public Farm createFarmModel() {
		return new Farm();
	}
	
	@RequestMapping("/userReg")
	public String locate(Map<String, Object> model) {
		return "farm_user_reg";
	}
	
	@RequestMapping("/login")
	public String login(Map<String, Object> model,@ModelAttribute Login login) {
		return "login";
	}
	
	@RequestMapping("/authenticate")
	public ModelAndView authenticate(Map<String, Object> model,@ModelAttribute Login login) {
		if(farmService.login(login)){
			String base64Encoded;
			ModelAndView mav = new ModelAndView("farm_shop");
			List<Farm> farmList=farmService.getImage();
			for(Farm farm:farmList){
				byte[] encodeBase64;
				try {
					encodeBase64 = Base64.encodeBase64(farm.getProdImg().getBytes(1, (int) farm.getProdImg().length()));
					base64Encoded = new String(encodeBase64, "UTF-8");
					farm.setProductAltImg(base64Encoded);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			mav.addObject("galleria", farmList);
			return mav;
		}
		return new ModelAndView("login");
	}
	
	
	@RequestMapping("/sell")
	public String sell(Map<String, Object> model,@ModelAttribute Farm farm) {
		
		return "farm_sell";
	}
	private static String UPLOADED_FOLDER = "C://pic//";
	@RequestMapping("/addProductToSell") // //new annotation since 4.3
    public String singleFileUpload(@ModelAttribute Farm farm) {

        try {

            // Get the file and save it somewhere
            byte[] bytes = farm.getImgFile().getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + farm.getImgFile().getOriginalFilename());
            Files.write(path, bytes);
            System.out.println(farm.getCategory());
            farm.setImgFilePath(path.toString());
            System.out.println(farm.getImgFilePath());
            farmService.insertImage(farm);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "farm_sell";
    }
	
	@RequestMapping("/addUser")
	public String populateState(ModelMap model,@ModelAttribute User user) {
		System.out.println("user   "+user.getuAddress());
		System.out.println("user   "+user.getuLat());
		//farmService.create(user);
		System.out.println("user   "+farmService.create(user));
		model.put("msg",  "Added");
		return "farm_user_reg";
	}
}
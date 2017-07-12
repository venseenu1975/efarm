package com.farm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.entity.SellerProduct;
import com.farm.model.Farm;
import com.farm.model.User;
import com.farm.service.FarmService;
import com.mysql.fabric.xmlrpc.base.Array;


@Controller
public class FarmController {
	
	
	@Autowired
	FarmService farmService;

	@ModelAttribute(name="user")
	public User createUserModel() {
		return new User();
	}
	
	@ModelAttribute(name="farm")
	public Farm createFarmModel() {
		return new Farm();
	}
	

	
    @GetMapping("/")
    public ModelAndView home1() {
		String base64Encoded;
		ModelAndView mav = new ModelAndView("farm_shop");
/*		List<Farm> farmList=farmService.getImage();
		if(farmList!=null && !farmList.isEmpty()){
			for(Farm farm:farmList){
				if(farm.getProdImg() !=null){
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
			}
		}
		mav.addObject("galleria", farmList);*/
		return mav;
    }
    

    
    @RequestMapping("/buy")
	public String buy(Map<String, Object> model,@ModelAttribute Farm farm) {
    	
		return "farm_buy";
	}
    
	@RequestMapping("/sell")
	public String sell(Map<String, Object> model,@ModelAttribute Farm farm) {
		model.put("categories",  farmService.getCategory());
		return "farm_sell";
	}
	private static String UPLOADED_FOLDER = "C://pic//";
	@RequestMapping("/addProductToSell") // //new annotation since 4.3
    public String singleFileUpload(@ModelAttribute Farm farm,Map<String, Object> model) {

        try {
            // Get the file and save it somewhere
            byte[] bytes = farm.getImgFile().getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + farm.getImgFile().getOriginalFilename());
            Files.write(path, bytes);
            farm.setImgFilePath(path.toString());
            System.out.println(farm.getImgFilePath());
            System.out.println(farm.getProdExpiry());
            model.put("categories",  farmService.getCategory());
            farmService.addSellerProducts(farm);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			e.printStackTrace();
		}

        return "farm_sell";
    }
	

	
	@RequestMapping("/populateProduct")
	public String populateProduct(ModelMap model,@ModelAttribute Farm farm) {
		System.out.println("user   "+farm.getCategory());
		model.put("categories",  farmService.getCategory());
		model.put("products",farmService.populateProduct(farm));
		return "farm_sell";
	}
	
	
	
	@RequestMapping("/search")
	public ModelAndView populateSearchResults(ModelMap model,@ModelAttribute Farm farm) {
		ModelAndView mav = new ModelAndView("farm_search");
		String base64Encoded;
		List<Farm> farmList = new ArrayList<>();
		List<SellerProduct> sellerProducts = farmService.getSellerProducts(farm);
		
			if(sellerProducts!=null && !sellerProducts.isEmpty()){
				for(SellerProduct sellerProduct:sellerProducts){
					Farm farmObj  = new Farm();
					farmObj.setProdImg(sellerProduct.getProdImg());
					farmObj.setProdName(sellerProduct.getProdName());
					farmObj.setProdQuantity(sellerProduct.getProdQuantity());
					farmObj.setProdExpiry(sellerProduct.getProductExpiry());
					
				
					if(farmObj.getProdImg() !=null){
					byte[] encodeBase64;
					try {
						encodeBase64 = Base64.encodeBase64(farmObj.getProdImg().getBytes(1, (int) farmObj.getProdImg().length()));
						base64Encoded = new String(encodeBase64, "UTF-8");
						farmObj.setProductAltImg(base64Encoded);
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					farmList.add(farmObj);
				}
				}
			}
		
		mav.addObject("farmList",  farmList);
		return mav;
	}
}
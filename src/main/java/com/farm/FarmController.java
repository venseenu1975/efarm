package com.farm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.entity.SellerProduct;
import com.farm.model.BasketObject;
import com.farm.model.Farm;
import com.farm.model.User;
import com.farm.service.FarmService;
import com.farm.util.FarmUtil;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
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
    public String home1() {
		return "home";
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
	public ModelAndView populateSearchResults(ModelMap model, @ModelAttribute Farm farm) throws ParseException {
		ModelAndView mav = new ModelAndView("farm_search");
		String base64Encoded;
		List<Farm> farmList = new ArrayList<>();
		List<SellerProduct> sellerProducts = farmService.getSellerProducts(farm);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUser = auth.getName();
		System.out.println("loggedUser   "+loggedUser);
		com.farm.entity.User buyer = farmService.getUser(loggedUser);
		Date d = new Date();
		if (sellerProducts != null && !sellerProducts.isEmpty()) {
			for (SellerProduct sellerProduct : sellerProducts) {
				Farm farmObj = new Farm();
				Date expiryDate = sellerProduct.getProductExpiry();
				if (expiryDate.compareTo(d)>0) {
					System.out.println("expiryDate:"+expiryDate);
					System.out.println("Seller ID:"+sellerProduct.getSellerId());
					com.farm.entity.User sellerDetails = farmService.getUser(sellerProduct.getSellerId());
					double distanceInKms = FarmUtil.distance(sellerDetails.getLat().doubleValue(), buyer.getLat().doubleValue(), sellerDetails.getLon().doubleValue(), buyer.getLon().doubleValue());
					if (distanceInKms<2) {
						farmObj.setProdImg(sellerProduct.getProdImg());
						farmObj.setProdName(sellerProduct.getProdName());
						farmObj.setProdQuantity(sellerProduct.getProdQuantity());
						farmObj.setProdExpiry(sellerProduct.getProductExpiry());
						farmObj.setProdUnits(sellerProduct.getUnits());
						farmObj.setProdPrice(sellerProduct.getProductPrice());
						if (farmObj.getProdImg() != null) {
							byte[] encodeBase64;
							try {
								encodeBase64 = Base64.encodeBase64(
										farmObj.getProdImg().getBytes(1, (int) farmObj.getProdImg().length()));
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
			}
		}

		mav.addObject("farmList", farmList);
		return mav;
	}
	
    @RequestMapping("/addToBasket")
	public String addToBasket(Map<String, Object> model,@ModelAttribute Farm farm) {
    	System.out.println("farm basket >> "+farm.getBasket());
    	if(farm.getBasket() !=null && !farm.getBasket().isEmpty()){
    		for(BasketObject basketObject:farm.getBasket()){
    			System.out.println("basket name >> "+basketObject.getName());
    			System.out.println("basket quantity >> "+basketObject.getQuantity());
    			System.out.println("basket Price >> "+basketObject.getPrice());
    			System.out.println("basket cart added >> "+basketObject.getAddToCart());
    			System.out.println("------------------------------------------------------");
    		}
    	}
		return "farm_buy";
	}

}
package com.farm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.farm.entity.SellerProduct;
import com.farm.model.BasketObject;
import com.farm.model.Category;
import com.farm.model.Farm;
import com.farm.model.Product;
import com.farm.model.User;
import com.farm.service.FarmService;
import com.farm.service.ProductService;
import com.farm.service.UserService;
import com.farm.util.FarmUtil;

@Controller
@SessionAttributes({ "cart", "farmProductList" })
public class FarmController {

	@Autowired
	FarmService farmService;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@ModelAttribute(name = "user")
	public User createUserModel() {
		return new User();
	}

	@ModelAttribute(name = "farm")
	public Farm createFarmModel() {
		return new Farm();
	}

	@GetMapping("/")
	public String home1() {
		return "home";
	}

	@RequestMapping("/buy")
	public String buy(Map<String, Object> model, @ModelAttribute Farm farm) {
		return "farm_buy";
	}

	public static final String CATEGORIES = "categories";
	public static final String FARMSELL = "farm_sell";
	public static final String FARMADDCATEGORY = "farm_add_category";
	public static final String FARMADDPRODUCT = "farm_add_product";
	private static Logger log = Logger.getLogger(FarmController.class);

	@RequestMapping("/sell")
	public String sell(Map<String, Object> model, @ModelAttribute Farm farm) {
		model.put(CATEGORIES, farmService.getCategory());
		return FARMSELL;
	}

	private static String uploadFolder = "C://pic//";

	@RequestMapping("/addProductToSell") // //new annotation since 4.3
	public String singleFileUpload(@Valid Farm farm, BindingResult bindingResult, Map<String, Object> model) {

		if (bindingResult.hasErrors()) {
			return FARMSELL;
		}

		try {
			// Get the file and save it somewhere
			byte[] bytes = farm.getImgFile().getBytes();
			Path path = Paths.get(uploadFolder + farm.getImgFile().getOriginalFilename());
			Files.write(path, bytes);
			farm.setImgFilePath(path.toString());
			log.info(farm.getImgFilePath());
			log.info(farm.getProdExpiry());
			model.put(CATEGORIES, farmService.getCategory());
			farmService.addSellerProducts(farm);
			model.put("farm", new Farm());
			model.put("msg", "Your Products added successfully");
		} catch (Exception e) {
			log.error(e);
		} 
		return FARMSELL;
	}

	@RequestMapping("/populateProduct")
	public String populateProduct(ModelMap model, @ModelAttribute Farm farm) {
		log.info("user   " + farm.getCategory());
		model.put(CATEGORIES, farmService.getCategory());
		model.put("products", farmService.populateProduct(farm));
		return FARMSELL;
	}

	@RequestMapping("/search")
	public ModelAndView populateSearchResults(ModelMap model, @ModelAttribute Farm farm)
			throws ParseException, IOException {
		Map<Integer, Farm> farmMap = null;
		farmMap = (Map<Integer, Farm>) model.get("farmProductList");
		if (!(farmMap != null && !farmMap.isEmpty())) {
			farmMap = new HashMap<>();
		}

		ModelAndView mav = new ModelAndView("farm_search");
		String base64Encoded;
		List<Farm> farmList = new ArrayList<>();
		List<SellerProduct> sellerProducts = farmService.getSellerProducts(farm);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUser = auth.getName();
		log.info("loggedUser   " + loggedUser);
		com.farm.entity.User buyer = userService.getUser(loggedUser);
		Date d = new Date();
		if (sellerProducts != null && !sellerProducts.isEmpty()) {
			for (SellerProduct sellerProduct : sellerProducts) {
				Farm farmObj = new Farm();
				Date expiryDate = sellerProduct.getProductExpiry();
				if (expiryDate.compareTo(d) > 0) {
					log.info("expiryDate:" + expiryDate);
					log.info("Seller ID:" + sellerProduct.getSellerId());
					com.farm.entity.User sellerDetails = userService.getUser(sellerProduct.getSellerId());
					String configuredDistance = FarmUtil.getProperties().get("distanceInKms").toString();
					double confDistance = 0;
					if (null != configuredDistance)
						confDistance = Double.valueOf(configuredDistance);
					log.info("Configured Distance:" + configuredDistance);
					double distanceInKms = FarmUtil.distance(sellerDetails.getLat().doubleValue(),
							buyer.getLat().doubleValue(), sellerDetails.getLon().doubleValue(),
							buyer.getLon().doubleValue());
					log.info("distance in m >>" + distanceInKms);
					if ((distanceInKms / 1000) < confDistance) {
						farmObj.setProdImg(sellerProduct.getProdImg());
						farmObj.setProdName(sellerProduct.getProdName());
						farmObj.setProdQuantity(sellerProduct.getProdQuantity());
						farmObj.setProdExpiry(sellerProduct.getProductExpiry());
						farmObj.setProdUnits(sellerProduct.getUnits());
						farmObj.setProdPrice(sellerProduct.getProductPrice());
						farmObj.setProdId(sellerProduct.getId());
						farmObj.setSellerId(sellerProduct.getSellerId());
						farmObj.setProdDesc(sellerProduct.getProdDesc());
						if (farmObj.getProdImg() != null) {
							byte[] encodeBase64;
							try {
								encodeBase64 = Base64.encodeBase64(
										farmObj.getProdImg().getBytes(1, (int) farmObj.getProdImg().length()));
								base64Encoded = new String(encodeBase64, "UTF-8");
								farmObj.setProductAltImg(base64Encoded);
							} catch (SQLException e1) {
								log.error(e1);
							} catch (UnsupportedEncodingException e) {
								log.error(e);
							}
						}
						farmMap.put(sellerProduct.getId(), farmObj);
						farmList.add(farmObj);
					}
				}
			}
		}
		model.addAttribute("farmProductList", farmMap);
		mav.addObject("farmList", farmList);
		return mav;
	}

	@RequestMapping("/addToBasket")
	public String addToBasket(ModelMap model, @ModelAttribute Farm farmObj) {
		Map<Integer, Farm> farmMap = (Map<Integer, Farm>) model.get("farmProductList");
		log.info("basket map keys ----" + farmMap);
		if (null != model.get("cart")) {
			farmObj.getBasket().addAll((List<BasketObject>) model.get("cart"));
		}
		log.info("farm basket >> " + farmObj.getBasket());
		BigDecimal total = BigDecimal.ZERO;
		int count = 0;
		if (farmObj.getBasket() != null && !farmObj.getBasket().isEmpty()) {
			for (Iterator<BasketObject> iterator = farmObj.getBasket().iterator(); iterator.hasNext();) {
				BasketObject basketObject = iterator.next();
				if (basketObject.getAddToCart() != null && (basketObject.getAddToCart())) {
					log.info("------------------------------------------------------");
					log.info("basket seller prod id  >> " + basketObject.getSellerProdId());
					log.info("basket quantity >> " + basketObject.getQuantity());
					log.info("basket cart added >> " + basketObject.getAddToCart());
					log.info("-------------------------from MAp-----------------------------");

					basketObject.setName(farmMap.get(basketObject.getSellerProdId()).getProdName());
					basketObject.setPrice(farmMap.get(basketObject.getSellerProdId()).getProdPrice());
					basketObject.setProdUnits(farmMap.get(basketObject.getSellerProdId()).getProdUnits());
					basketObject.setSellerId(farmMap.get(basketObject.getSellerProdId()).getSellerId());

					log.info("basket name >> " + farmMap.get(basketObject.getSellerProdId()).getProdName());
					log.info("basket Price >> " + farmMap.get(basketObject.getSellerProdId()).getProdPrice());
					log.info(
							"basket prod units >> " + farmMap.get(basketObject.getSellerProdId()).getProdUnits());
					log.info("seller id  >> " + farmMap.get(basketObject.getSellerProdId()).getSellerId());

					basketObject.setItemPrice(FarmUtil.calculateCost(basketObject.getQuantity(),
							farmMap.get(basketObject.getSellerProdId()).getProdPrice()));
					total = total.add(basketObject.getItemPrice());
					basketObject.setTotalPrice(total);
					log.info("basket tot Price >> " + basketObject.getTotalPrice());
					basketObject.setAddToCartprodId(basketObject.getSellerProdId() + "_" + count++);
					log.info("prod new  id  >> " + basketObject.getAddToCartprodId());
				} else {
					iterator.remove();
				}

			}
			model.addAttribute("cart", farmObj.getBasket());
		}
		return "farm_checkout";
	}

	@RequestMapping("/removeItem/{id}")
	public String removeItem(ModelMap model, @ModelAttribute Farm farm, @PathVariable("id") String sellerProdId) {
		BigDecimal newTotal;
		BigDecimal currTotal;
		BigDecimal currItemPrice;
		Map<String, BasketObject> farmMap = null;
		List<BasketObject> modifiedList = null;
		if (null != model.get("cart")) {
			List<BasketObject> list = (List<BasketObject>) model.get("cart");
			if (list != null && !list.isEmpty()) {

				// converted list to map
				farmMap = list.stream().collect(Collectors.toMap(BasketObject::getAddToCartprodId, item -> item));
				farmMap.forEach((k, v) -> log.info(k + " => " + v));

				// get current removal element cost
				log.info("basket Item Price >> " + farmMap.get(sellerProdId).getItemPrice());
				currItemPrice = farmMap.get(sellerProdId).getItemPrice();

				// get total cost of basket
				log.info("Farm Basket Total price >> " + list.get(list.size() - 1).getTotalPrice());
				currTotal = list.get(list.size() - 1).getTotalPrice();

				// remove curr item from map
				farmMap.remove(sellerProdId);
				farmMap.forEach((k, v) -> log.info(k + " => " + v));

				// compute new total
				newTotal = currTotal.subtract(currItemPrice);

				// map to list
				modifiedList = new ArrayList<>(farmMap.values());

				// set new total
				log.info("farm basket new total price >> " + newTotal);
				if (!modifiedList.isEmpty()) {
					modifiedList.get(modifiedList.size() - 1).setTotalPrice(newTotal);
				}
				log.info("farm basket list size >> " + modifiedList.size());
			}
		}
		model.addAttribute("cart", modifiedList);
		return "farm_checkout";
	}

	@RequestMapping("/payBasket")
	public String payBasket(ModelMap model, @ModelAttribute Farm farm, SessionStatus status) {
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null != model.get("cart")) {
			List<BasketObject> list = (List<BasketObject>) model.get("cart");
			if (list != null && !list.isEmpty()) {
				log.info("farm basket total price >> " + list.get(list.size() - 1).getTotalPrice());
				model.addAttribute("total", list.get(list.size() - 1).getTotalPrice());
				model.addAttribute("cart", list);
			}
		}
		return "farm_pay";
	}

	@RequestMapping("/buyBasket")
	public String buyBasket(Map<String, Object> model, @ModelAttribute Farm farm, SessionStatus status) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<String, BasketObject> farmMap = null;
		if (null != model.get("cart")) {
			List<BasketObject> list = (List<BasketObject>) model.get("cart");
			if (list != null && !list.isEmpty()) {
				farmMap = list.stream().collect(Collectors.toMap(BasketObject::getAddToCartprodId, item -> item));
				farmMap.forEach((k, v) -> log.info(k + " => " + v));

				log.info("farm basket total price >> " + list.get(list.size() - 1).getTotalPrice());
				int orderId = farmService.createOrder(auth.getName(), list.get(list.size() - 1).getTotalPrice());
				log.info("Ur order id is >>" + orderId);

				for (BasketObject basketObject : list) {
					int prodId = Integer.parseInt(basketObject.getAddToCartprodId().substring(0,
							basketObject.getAddToCartprodId().indexOf('_')));
					/*
					 * log.info("basket seller prod id  >> "
					 * +Integer.valueOf(basketObject.getAddToCartprodId().
					 * substring(0,
					 * basketObject.getAddToCartprodId().indexOf("_"))));
					 * log.info(
					 * "---------------------From map---------------------------------"
					 * ); log.info("basket quantity >> "
					 * +farmMap.get(basketObject.getAddToCartprodId()).
					 * getQuantity()); log.info(
					 * "basket Item Price >> "
					 * +farmMap.get(basketObject.getAddToCartprodId()).
					 * getItemPrice()); log.info("basket Price >> "
					 * +farmMap.get(basketObject.getAddToCartprodId()).getPrice(
					 * )); log.info("basket prod units >> "
					 * +farmMap.get(basketObject.getAddToCartprodId()).
					 * getProdUnits()); log.info(
					 * "basket seller  id  >> "
					 * +farmMap.get(basketObject.getAddToCartprodId()).
					 * getSellerId()); log.info(
					 * "basket total  price  >> "+basketObject.getTotalPrice());
					 * log.info(
					 * "------------------------------------------------------")
					 * ;
					 * 
					 * basketObject.setQuantity(farmMap.get(basketObject.
					 * getAddToCartprodId()).getQuantity());
					 * basketObject.setItemPrice(farmMap.get(basketObject.
					 * getAddToCartprodId()).getItemPrice());
					 * basketObject.setProdUnits(farmMap.get(basketObject.
					 * getAddToCartprodId()).getProdUnits());
					 * basketObject.setSellerId(farmMap.get(basketObject.
					 * getAddToCartprodId()).getSellerId());
					 */
					basketObject.setOrderId(orderId);
					basketObject.setSellerProdId(prodId);
				}
				farm.setBasket(list);
				farmService.createOrderSummary(farm);
				model.put("order_id", orderId);
				model.put("order", farmService.getOrderSummary(orderId));		
				LocalDate localDate = LocalDate.now();		       
				FarmUtil.sendSMS(FarmUtil.getMsgProperties().getProperty("order.confirmation").replace("$", Integer.toString(orderId)).replace("#",  " "+localDate.now().plus(1, ChronoUnit.DAYS)), farmService.getUserContact(auth.getName()));
				status.setComplete();
			}
		}

		return "farm_order";
	}

	@RequestMapping("/admin/addCategory")
	public String addCategory(@Valid Category category, BindingResult bindingResult, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return FARMADDCATEGORY;
		}

		productService.createCategory(category);
		model.put("category", new Category());
		model.put("msg", "Product Category added successfully");

		return FARMADDCATEGORY;
	}

	@RequestMapping("/admin/addProduct")
	public String addProduct(@Valid Product product, BindingResult bindingResult, ModelMap model) {
		try {
			if (bindingResult.hasErrors()) {
				return FARMADDPRODUCT;
			}

			log.info("in ---");
			// Get the file and save it somewhere
			byte[] bytes = product.getImgFile().getBytes();
			Path path = Paths.get(uploadFolder + product.getImgFile().getOriginalFilename());
			Files.write(path, bytes);
			product.setImage(path.toString());

			productService.createProduct(product);
			model.put(CATEGORIES, farmService.getCategory());
			model.put("product", new Product());
			model.put("msg", "Product added successfully");
		} catch (IOException e) {
			log.error(e);
		}
		return FARMADDPRODUCT;
	}

	@RequestMapping("/admin/category")
	public String sell(Map<String, Object> model, @ModelAttribute Category category) {
		return FARMADDCATEGORY;
	}

	@RequestMapping("/admin/product")
	public String product(Map<String, Object> model, @ModelAttribute Product product) {
		model.put(CATEGORIES, farmService.getCategory());
		return FARMADDPRODUCT;
	}
}

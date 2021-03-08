package com.example.BorsaUygulamasi.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.BorsaUygulamasi.implementation.UserImplementation;
import com.example.BorsaUygulamasi.implementation.HisseImplementation;
import com.example.BorsaUygulamasi.model.AlimSatim;
import com.example.BorsaUygulamasi.model.User;
import com.example.BorsaUygulamasi.model.Hisse;

@Controller
public class BorsaUygulamasiController {

	public static ArrayList<Hisse>  userlistesi=new ArrayList<Hisse>();
	
	public static ArrayList<AlimSatim> alimsatimlistesi =new ArrayList<AlimSatim>();
	
	@Autowired
	private UserImplementation productImplementation;
	
	
	@Autowired
	private HisseImplementation userImplementation;

	
	@GetMapping("/home")
	public String getHome(Model model) {
		List<User> listProducts=productImplementation.getProducts();
		System.out.println(listProducts.get(0));
		model.addAttribute("listProducts", listProducts);
		return "products";
	}
	
	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		User p=new User();
		model.addAttribute("product", p);
		return "new_product";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveNewProduct(@ModelAttribute("product") User product) {
		productImplementation.saveProducts(product);
		
		return "redirect:/home";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView EditProduct(@PathVariable(name="id") int id) {
		ModelAndView mav=new ModelAndView("edit_product");
		User product= productImplementation.getProduct(id);
		mav.addObject("product",product);
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String DeleteProduct(@PathVariable(name="id") int id) {
		productImplementation.deleteProductById(id);
		return "redirect:/home";
	}
	
	@GetMapping("/login")
	public ModelAndView Login() {
		ModelAndView mav=new ModelAndView("login");
		mav.addObject("user",new User());
		return mav;
	}
	
	@PostMapping("/login")
	public String Login(@ModelAttribute("user") User user) {
		User auhuser=productImplementation.login(user.getUsername(), user.getPassword());
		System.err.println(auhuser+user.getUsername()+user.getPassword());
		if(Objects.nonNull(auhuser)) {
			return "redirect:/hisselisteleme";
		}else {
			return "redirect:/login";
		}
		//return null;
		
	}
	@GetMapping("/hisselisteleme")
	public String getHisseListeleme(Model model) {
		List<Hisse> listUsers=userImplementation.getUsers();
		System.out.println(listUsers.get(0));
		model.addAttribute("listUser",BorsaUygulamasiController.userlistesi);
	
		return "hisselisteleme";
	}
	@RequestMapping("/hisseekle")
	public String showNewHisseForm(Model model) {
		Hisse u=new Hisse();
		model.addAttribute("user", u);
		return "hisse_ekleme";
	}
	
	@RequestMapping(value="/save1",method=RequestMethod.POST)
	public String saveNewHisse(@ModelAttribute("user") Hisse user) {
		userImplementation.saveUser(user);
		user.setTarih(new Date());
		user.setUcret(randomSkaicius());
		BorsaUygulamasiController.userlistesi.add(user);
		
		
		return "redirect:/hisselisteleme";
	}
	
	
	@RequestMapping("/edit1/{id}")
	public ModelAndView EditUser(@PathVariable(name="id") int id) {
		ModelAndView mav=new ModelAndView("edit_user");
		Hisse user= userImplementation.getUser(id);
		mav.addObject("user",user);
		return mav;
	}
	@RequestMapping("/delete1/{id}")
	public String DeleteUser(@PathVariable(name="id") int id) {
		userImplementation.deleteUserById(id);
		return "redirect:/hisselisteleme";
	}
	
	public int randomSkaicius() {
        Random rand = new Random();
        int skaicius = (int) (Math.random() * 1000 + 1);
        return skaicius;
    }
	@GetMapping("/hissealimlisteleme")
	public String getHissealimListeleme(Model model) {
		List<Hisse> listUsers=userImplementation.getUsers();
		System.out.println(listUsers.get(0));
		model.addAttribute("tests", listUsers);
		model.addAttribute("alimsatimx",BorsaUygulamasiController.alimsatimlistesi);
		return "hissealimlisteleme";
	}
	


	@RequestMapping(value = { "/hissealimsatim" }, method = RequestMethod.GET)
	public String selectOptionExample1Page(Model model ) {
		Hisse u=new Hisse();
		model.addAttribute("user", u);
		
	    List<Hisse> tests = userImplementation.getUsers();
	    model.addAttribute("tests", tests);
		return "hisse_alim_satim";
	   
	}
	
	
	@RequestMapping(value="/save2",method=RequestMethod.POST)
	public String saveNewUser(@ModelAttribute("alimsatim") AlimSatim alimSatim,@RequestParam(value="action", required=true) String action) {
		
		 if (action.equals("ekle")) {
		        // do something here 
			 	for (int i=0;i<BorsaUygulamasiController.userlistesi.size();i++) {
					if(alimSatim.getTestOrder().equals(BorsaUygulamasiController.userlistesi.get(i).getKod())) {
						alimSatim.toplam=Integer.parseInt(alimSatim.getMiktar())*BorsaUygulamasiController.userlistesi.get(i).getUcret();
					}
				}
			 	
				BorsaUygulamasiController.alimsatimlistesi.add(alimSatim);
				
		    }

		    if (action.equals("sil")) {
		    	for (int i=0;i<BorsaUygulamasiController.userlistesi.size();i++) {
					if(alimSatim.getTestOrder().equals(BorsaUygulamasiController.userlistesi.get(i).getKod())) {
						   for (int j=0;j< BorsaUygulamasiController.alimsatimlistesi.size();j++) {
							if(alimSatim.getTestOrder().equals(BorsaUygulamasiController.alimsatimlistesi.get(j).getTestOrder())){
								BorsaUygulamasiController.alimsatimlistesi.get(j).toplam=BorsaUygulamasiController.alimsatimlistesi.get(j).toplam-Integer.parseInt(alimSatim.getMiktar())*BorsaUygulamasiController.userlistesi.get(i).getUcret();
								Integer miktar=Integer.parseInt(BorsaUygulamasiController.alimsatimlistesi.get(j).getMiktar())-Integer.parseInt(alimSatim.getMiktar());
								BorsaUygulamasiController.alimsatimlistesi.get(j).setMiktar(miktar.toString());
							}
					}
					}
				}
				
		    }
		    
		

		return "redirect:/hissealimlisteleme";
	}
	
	
	

	
}

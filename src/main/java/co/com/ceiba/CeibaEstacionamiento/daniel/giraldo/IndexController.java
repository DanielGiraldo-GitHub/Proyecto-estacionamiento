package co.com.ceiba.CeibaEstacionamiento.daniel.giraldo;

import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
public class IndexController {
  
	
	@RequestMapping("/")
	public String getIndexPage(){
		return "index";
	}

}

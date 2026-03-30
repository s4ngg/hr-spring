package kr.co.studyProject;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Homecontroller {

	
	@RequestMapping("/")
	public String goHome() {

		return "pages/member/home";
	}
	
}

package com.project.counsellor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.counsellor.dto.CounsellorDto;
import com.project.counsellor.dto.DashboardResponseDto;
import com.project.counsellor.service.CounsellorSevice;
import com.project.counsellor.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorControlor {

	@Autowired
	private CounsellorSevice counsellorService;

	@Autowired
	private EnquiryService enquiryService;

//	URL : http://localhost:8080/

	@GetMapping("/")
	public String index(Model model) {

		CounsellorDto counsellorDto = new CounsellorDto();
		model.addAttribute("counsellor", counsellorDto);
		return "index";
	}

	@PostMapping("/login")
	public String handleLogin(HttpServletRequest request, @ModelAttribute("counsellor") CounsellorDto counsellorDto,
			Model model) {

		CounsellorDto counsellor = counsellorService.login(counsellorDto);
		if (counsellor == null) {
			model.addAttribute("errorMessage", "Invalid Credentials.");
			model.addAttribute("counsellor", new CounsellorDto());
			return "login"; // Return to login page with an error 
		} else {
			Integer counsellorId = counsellor.getCounsellorId();
			// store counsellorId into HttpServletRequest session obj
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", counsellorId);

			DashboardResponseDto dashboardDto = enquiryService.getDashboardInfo(counsellorId);
			model.addAttribute("dashboardDto", dashboardDto);
			return "dashboard"; // Redirect to dashboard page
		}
	}

	@GetMapping("/dashboard")
	public String displyDashboard(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		DashboardResponseDto dashboardDto = enquiryService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardDto", dashboardDto);

		return "dashboard"; // Redirect to dashboard page
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);
		session.invalidate();
		model.addAttribute("counsellor", new CounsellorDto());
		return "index";

	}

	@GetMapping("/register-form")
	public String loadRegisterPage(Model model) {
		model.addAttribute("counsellor", new CounsellorDto());
		return "register";

	}

	@PostMapping("/register")
	public String registerPage(@ModelAttribute("counsellor") CounsellorDto counsellorDTO, Model model) {

		boolean unique = counsellorService.uniqueEmailCheck(counsellorDTO.getEmail());
		if (unique) {
			boolean registered = counsellorService.register(counsellorDTO);
			if (registered) {
				model.addAttribute("smsg", "Registration Success");
				model.addAttribute("counsellor", new CounsellorDto());

			} else {
				model.addAttribute("emsg", "Registration Failed");
			}
		} else {
			model.addAttribute("emsg", "Email already exists !");

		}
		return "register";

	}
}

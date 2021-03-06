package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class SchoolClassesController {

	@RequestMapping(value = "/SchoolClasses")
	public String listSchoolsClasses(Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
		model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());

		return "schoolClassesList";
	}

	@RequestMapping(value = "/AddSchoolClass")
	public String displayAddSchoolForm(Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		return "schoolClassForm";
	}

	@RequestMapping(value = "/CreateSchoolClass", method = RequestMethod.POST)
	public String createSchoolClass(@RequestParam(value = "schoolClassStartYear", required = false) String startYear,
			@RequestParam(value = "schoolClassCurrentYear", required = false) String currentYear,
			@RequestParam(value = "schoolClassProfile", required = false) String profile, Model model,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		SchoolClass schoolClass = new SchoolClass();
		schoolClass.setStartYear(Integer.valueOf(startYear));
		schoolClass.setCurrentYear(Integer.valueOf(currentYear));
		schoolClass.setProfile(profile);

		DatabaseConnector.getInstance().addSchoolClass(schoolClass);
		model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
		model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
		model.addAttribute("message", "Nowa klasa zostala dodana");

		return "schoolClassesList";
	}

	@RequestMapping(value = "/DeleteSchoolClass", method = RequestMethod.POST)
	public String deleteSchoolClass(@RequestParam(value = "schoolClassId", required = false) String schoolClassId,
			Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		DatabaseConnector.getInstance().deleteSchoolClass(schoolClassId);
		model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
		model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
		model.addAttribute("message", "Klasa zostala usunieta");

		return "schoolClassesList";
	}

	@RequestMapping(value = "/ModifySchoolClass")
	public String modifySchoolClass(@RequestParam(value = "schoolClassId", required = true) String schoolClassId,
			Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("schoolClass", DatabaseConnector.getInstance().getSchoolClass(schoolClassId));
		model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());

		return "schoolClassModifyForm";
	}

	@RequestMapping(value = "/UpdateSchoolClass", method = RequestMethod.POST)
	public String updateSchool(@RequestParam(value = "schoolClassProfile", required = false) String profile,
			@RequestParam(value = "schoolClassStartYear", required = false) int startYear,
			@RequestParam(value = "schoolClassCurrentYear", required = false) int currentYear,
			@RequestParam(value = "schoolClassId", required = false) String schoolClassId,
			@RequestParam(value = "schoolClassSchool", required = false) String schoolId, Model model,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		DatabaseConnector.getInstance().updateSchoolClass(schoolClassId, profile, startYear, currentYear, schoolId);
		model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
		model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
		model.addAttribute("message", "Dane klasy zostaly zaktualizowane");

		return "schoolClassesList";
	}

}
package org.tothought.spring.controllers.resume;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tothought.entities.Experience;
import org.tothought.entities.ExperienceDetail;
import org.tothought.json.JsonUtil;
import org.tothought.repositories.ExperienceDetailRepository;
import org.tothought.repositories.ExperienceRepository;
import org.tothought.spring.propertyeditors.ExperienceDetailTypeEditor;
import org.tothought.spring.utilities.TagCreatorUtil;

@Controller
@RequestMapping("/resume/manager/experience")
public class ExperienceController {
	
	@Autowired
	ExperienceRepository experienceRepository;
	
	@Autowired
	ExperienceDetailRepository detailRepository;
	
	@Autowired
	TagCreatorUtil tagCreatorUtil;
	
	@RequestMapping("/")
	public String getExperiences(){		
		return "resume/experience";
	}
	
	@RequestMapping("/new")
	public String newExperience(Model model){
		model.addAttribute("experience", new Experience());
		return "resume/manager/manageExperience";
	}
	
	@RequestMapping("/edit/{experienceId}")
	public String editExperience(@PathVariable Integer experienceId, Model model){
		model.addAttribute("experience", experienceRepository.findOne(experienceId));
		return "resume/manager/manageExperience";		
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute Experience experience, @RequestParam("tags") String tags){
		experience.setTags(tagCreatorUtil.createTags(tags));
		experienceRepository.save(experience);
		return "redirect:/resume/experience";
	}
	
	@RequestMapping("/{experienceId}/tags")
	@ResponseBody
	public String getTags(@PathVariable Integer experienceId) {
		Experience experience = experienceRepository.findOne(experienceId);
		return JsonUtil.getJson(experience.getTags());
	}

	/**
	 * Sets a binder to handle the conversion of the file.
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinderAll(WebDataBinder binder) {
		   SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		   dateFormat.setLenient(false);

		// true passed to CustomDateEditor constructor means convert empty String to null
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(ExperienceDetail.class, new ExperienceDetailTypeEditor(detailRepository));
	}

}
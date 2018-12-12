package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,  int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job jobs = jobData.findById(id);

        model.addAttribute("jobs",jobs);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title","Add Job");
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        System.out.println("jobForm object" + jobForm);

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()){
            model.addAttribute("title","Add Job");

            return "new-job";
        }
        Job job = new Job();
        job.setName(jobForm.getName());
        job.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
        job.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
        job.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));
        job.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionTypeId()));

//        job.setEmployer(jobForm.getEmployers().get(jobForm.getEmployerId()));/?
//
//
          System.out.println("job= " + job);

        jobData.add(job);
        int id = job.getId();
        model.addAttribute("id",id);
        return "redirect:?id=" + id;

    }
}

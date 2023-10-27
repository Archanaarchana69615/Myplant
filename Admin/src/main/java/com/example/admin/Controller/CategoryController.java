package com.example.admin.Controller;

import com.example.library.dto.CategoryDto;
import com.example.library.model.Category;
import com.example.library.service.Categoryservice;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    private Categoryservice categoryservice;

    public CategoryController(Categoryservice categoryservice) {
        this.categoryservice = categoryservice;
    }

    @GetMapping("/category")
    public String categories(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "manage category");
        List<Category> categories = categoryservice.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size",categories.size());
        return "category";
    }

    @PostMapping("save/category")
    public String save(@Valid @ModelAttribute("categoryNew") CategoryDto categoryDto, Model model, RedirectAttributes redirectAttributes) {
        try {
            categoryservice.save(categoryDto);
            redirectAttributes.addFlashAttribute("error", " duplicate name of category");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("success", "added sucessfully ");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "server error");
        }
        return "redirect:/category";
    }

    @GetMapping("/category/update/{id}")
    @ResponseBody
    public Category findById(@PathVariable Long id) {
        return categoryservice.findById(id);
    }

    @PostMapping("/update-category")
    public String update(Category category, RedirectAttributes redirectAttributes) {
        try {
            categoryservice.update(category);
            redirectAttributes.addFlashAttribute("success", "update successfully");

        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "duplicate name of category");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "error from server");
        }
        return "redirect:/category";
    }

    @GetMapping("/enable-category/{id}")
    public String enable(@PathVariable Long id,RedirectAttributes redirectAttributes)
    {
        try
        {
            categoryservice.enableById(id);
            redirectAttributes.addFlashAttribute("sucess","enabled successfully");
        }catch (DataIntegrityViolationException e1){
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error","duplicate name of category");
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error","error server");
        }
        return "redirect:/category";
    }
    @GetMapping("disable-category/{id}")
    public  String delete(@PathVariable Long id,RedirectAttributes redirectAttributes)
    {
        try
        {
            categoryservice.disableById(id);
            redirectAttributes.addFlashAttribute("sucess","disabled successfully");
        }catch (DataIntegrityViolationException e1)
        {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error","delete successfully");
        }catch (Exception e2)
        {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error","server error");
        }
        return "redirect:/category";
    }

    @GetMapping("delete/categories{id}")
    public String deletecategory(@PathVariable Long id,RedirectAttributes redirectAttributes)
    {
        try
        {
            categoryservice.deleteById(id);
            redirectAttributes.addFlashAttribute("success","deleted s");

        }
        catch(DataIntegrityViolationException e1)
        {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error","delete");
        }catch (Exception e2)
        {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error","d er");
        }
        return "redirect:/category";
    }
}


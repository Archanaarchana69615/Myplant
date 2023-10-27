package com.example.admin.Controller;

import com.example.library.dto.CategoryDto;
import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.model.Image;
import com.example.library.model.Product;
import com.example.library.service.Categoryservice;
import com.example.library.service.ImageService;
import com.example.library.service.Productservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {
   private Productservice productservice;

    private Categoryservice categoryservice;
    private ImageService imageService;

    public ProductController(Productservice productservice, Categoryservice categoryservice, ImageService imageService) {
        this.productservice = productservice;
        this.categoryservice = categoryservice;
        this.imageService = imageService;
    }

    @GetMapping("/product")
    public String productlist(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        List<ProductDto> product = productservice.findAll();
        model.addAttribute("products", product);
        model.addAttribute("size", product.size());
        return "product";
    }

    @GetMapping("/add-product")
    public String addproducts(Model model) {
        model.addAttribute("title", "add products");
        List<Category> categories = categoryservice.findAllActivatedTrue();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categoryNew", new CategoryDto());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "add-product";

    }


@PostMapping("save-product")
    public String saveproduct(@ModelAttribute("ProductDto")ProductDto product,@RequestParam("imageProduct") List<MultipartFile>imageProduct,
                             RedirectAttributes redirectAttributes)

    {
       try
       {
         productservice.save(imageProduct,product);
         redirectAttributes.addFlashAttribute("success","added new product");

       } catch (Exception e)
       {
           e.printStackTrace();
           redirectAttributes.addFlashAttribute("error","failed to add product");
       }
       return "redirect:/product";

    }
    @GetMapping("/update-product/{id}")
    public String updateProduct(@PathVariable("id")long
                                id,Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null|| authentication instanceof AnonymousAuthenticationToken)
        {
           return "redirect:/login";
        }
        List<Category>categories = categoryservice.findAllActivatedTrue();
        ProductDto productDto= productservice.findById(id);
        List<Image>images= imageService.findProductImages(id);
        model.addAttribute("title","update ");
        model.addAttribute("categories",categories);
        model.addAttribute("images",images);

        model.addAttribute("productDto",productDto);
        return "update-product";
    }
    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") List<MultipartFile> imageProduct,
                                RedirectAttributes redirectAttributes) {
        try {
            productservice.update(imageProduct,productDto);
            redirectAttributes.addFlashAttribute("success","update successfully");
        }catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error","error server");
        }
       return "redirect:/product";

    }
    @GetMapping("disable-product/{id}")
    public String disableproduct(@PathVariable("id")long id,RedirectAttributes redirectAttributes)
    {
      redirectAttributes.addFlashAttribute("success","product disabled");

      productservice.disable(id);
      return "redirect:/product";
    }

    @GetMapping("enable-product/{id}")
    public String enableproduct(@PathVariable("id")long id,RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("success","product enabled");

        productservice.enable(id);
        return "redirect:/product";
    }

    @GetMapping("delete/product/{id}")
    public String delete(@PathVariable("id")long id,RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("success","product deleted");

        productservice.deleted(id);
        return "redirect:/product";
    }

}





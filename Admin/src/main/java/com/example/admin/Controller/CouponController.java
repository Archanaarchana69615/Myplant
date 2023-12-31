package com.example.admin.Controller;

import com.example.library.dto.CouponDto;
import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.model.Coupon;
import com.example.library.service.Categoryservice;
import com.example.library.service.CouponService;
import com.example.library.service.Productservice;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CouponController {

  private Categoryservice categoryservice;

  private CouponService couponService;


    private Productservice productservice;

    public CouponController(Categoryservice categoryservice, CouponService couponService, Productservice productservice) {
        this.categoryservice = categoryservice;
        this.couponService = couponService;
        this.productservice = productservice;
    }

    @GetMapping("/coupons")
    public String coupon(Principal principal, Model model)
    {
        if(principal==null)
        {
            return "redirect:/login";
        }
        List<Coupon>coupon=couponService.getAllCoupons();
        model.addAttribute("coupons",coupon);
        model.addAttribute("size",coupon.size());
     return "coupons";
    }
    @GetMapping("/coupons/add-coupon")
    public String addCoupon(Principal principal,Model model)
    {
        if(principal==null)
        {
            return "redirect:/login";

        }
        List<ProductDto>productList=productservice.findAllProducts();
        List<Category>categoryList=categoryservice.findAllActivatedTrue();

        LocalDate minimumDate=LocalDate.now().plusDays(1);
        model.addAttribute("minimumDate",minimumDate);
        model.addAttribute("products",productList);
        model.addAttribute("categories",categoryList);
        model.addAttribute("coupon",new CouponDto());
        return "add-coupon";
    }
  @PostMapping("/coupons/save")
    public String saveCoupon(@Valid@ModelAttribute("coupon")CouponDto coupon, RedirectAttributes redirectAttributes, BindingResult result,Model model)
  {
 if(result.hasErrors())
 {
  model.addAttribute("coupon",coupon);
  return "add-coupon";
 }
 try {
     System.out.println(coupon);
    couponService.save(coupon);
    redirectAttributes.addFlashAttribute("success","added new coupon successfully");
 }catch (Exception e)
 {
e.printStackTrace();
redirectAttributes.addFlashAttribute("error","failed to add new coupon");
 }
 return "redirect:/coupons";
  }

  @GetMapping("/coupons/update-coupon/{id}")
    public String updateCoupon(@PathVariable("id")long id,Model model,Principal principal)
  {
if(principal==null)
{
    return "redirect:/login";
}
CouponDto couponDto=couponService.findById(id);
model.addAttribute("couponDto",couponDto);
LocalDate minimumDate=LocalDate.now().plusDays(1);
model.addAttribute("minimumDate",minimumDate);

return "update-coupon";
  }
  @PostMapping("/coupons/update-coupon/{id}")
    public String updateCoupons(@Valid@ModelAttribute("coupon")CouponDto couponDto, RedirectAttributes redirectAttributes,
                                Model model,BindingResult result)


  {
      System.out.println(couponDto);
   if(result.hasErrors())
   {
   model.addAttribute("coupon",couponDto);

   return "update-coupon";
   }
   try {
      couponService.update(couponDto);
      redirectAttributes.addFlashAttribute("success","updated successfully");
   }catch (Exception e)
   {
       e.printStackTrace();
       redirectAttributes.addFlashAttribute("error","error server,please try again");
   }
   return "redirect:/coupons";
  }

@GetMapping("/disable-coupon/{id}")
    public String disableCoupon(@PathVariable("id")long id,RedirectAttributes redirectAttributes)
{
 couponService.disable(id);
 redirectAttributes.addFlashAttribute("success","coupon disabled");
return "redirect:/coupons";
}
@GetMapping("/enable-coupon/{id}")
    public String enableCoupon(@PathVariable("id")long id,RedirectAttributes redirectAttributes)
{
    couponService.enable(id);
    redirectAttributes.addFlashAttribute("success","coupon enabled");
    return "redirect:/coupons";
}
@GetMapping("/delete-coupon/{id}")
    public String deleteCoupon(@PathVariable("id")long id,RedirectAttributes redirectAttributes)
{
    couponService.delete(id);
    redirectAttributes.addFlashAttribute("success","deleted successfully");
    return "redirect:/coupons";
}

}

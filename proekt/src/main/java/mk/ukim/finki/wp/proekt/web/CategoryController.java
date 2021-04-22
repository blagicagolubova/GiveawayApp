package mk.ukim.finki.wp.proekt.web;

import mk.ukim.finki.wp.proekt.sevice.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add-category")
    public String addCategoryPage(Model model,HttpServletRequest req){
        String username=req.getRemoteUser();
        model.addAttribute("username", username);
        model.addAttribute("bodyContent","add-category");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveCategory(@RequestParam String name,
                                   @RequestParam(required = false) String description,
                                   HttpServletRequest request)
    {
        this.categoryService.save(name, description);
        return "redirect:/giveaway/add-giveaway";

    }
}

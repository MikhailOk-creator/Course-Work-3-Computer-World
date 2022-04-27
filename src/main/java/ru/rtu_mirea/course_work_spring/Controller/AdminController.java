package ru.rtu_mirea.course_work_spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import ru.rtu_mirea.course_work_spring.Model.Product;
import ru.rtu_mirea.course_work_spring.Model.ProductType;
import ru.rtu_mirea.course_work_spring.Model.User;
import ru.rtu_mirea.course_work_spring.Service.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping("")
    public String adminPage(Model model, HttpServletRequest request) {
        ProductType[] productType = ProductType.values();
        model.addAttribute("prods", productType);
        model.addAttribute("prodss", productType);

        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if(map != null) {
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }
        }

        return "adminPage";
    }

    @PostMapping("/addNewPosition")
    public String addNewPosition(@ModelAttribute @Valid Product product, Errors errors, RedirectAttributes redirectAttributes, @RequestParam(name = "image") MultipartFile file){
        return service.addNewPos(product, file, errors, redirectAttributes);
    }

    @GetMapping("/userList")
    public String getUserList(Model model){
        return service.userList(model);
    }

    @GetMapping("/checkClickedButton/{prod}")
    public String checkButton(Model model, @PathVariable(name = "prod") ProductType type, RedirectAttributes redirectAttributes){
        return service.getCheckedListForm(model, type, redirectAttributes);
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id){
        return service.deleteProd(id);
    }

    @GetMapping("/changeProduct/{id}")
    public String changeProduct(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request){
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        return service.changeProductForm(id, model, map);
    }

    @PostMapping("/changeProduct/{id}")
    public String changeProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes, @PathVariable(name = "id") Long id){
        return service.changeProduct(product, redirectAttributes, id);
    }

    @GetMapping("/changeImage/{id}")
    public String changeImageForm(@PathVariable(name = "id") Long id, Model model, HttpServletRequest request){
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        return service.changeImageForm(id, model, map);
    }

    @PostMapping("/changeImage/{id}")
    public String changeImage(@PathVariable(name = "id") Long id, @RequestParam(name = "newImage") MultipartFile img, RedirectAttributes attributes){
        System.out.println(img.getOriginalFilename());
        return service.changeImage(id, img, attributes);
    }

    @PostMapping("/changeUserActivity/{id}/")
    public String changeActivity(@PathVariable(name = "id") Long id){
        return service.changeUserActivity(id);
    }

    @PostMapping("/addNewWorker")
    public String registrNewWorker(@ModelAttribute @Valid User user, Errors errors, RedirectAttributes redirectAttributes){
        return service.addNewWorker(user, errors, redirectAttributes);
    }
}

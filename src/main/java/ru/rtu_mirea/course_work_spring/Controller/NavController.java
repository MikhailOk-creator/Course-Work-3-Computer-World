package ru.rtu_mirea.course_work_spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {
    @GetMapping("/brands")
    public String getBrands() {
        return "TheSecondVersion_PastCourseWork/brands";
    }
    @GetMapping("/selectionOfComponents")
    public String getSelectionOfComponents() {
        return "TheSecondVersion_PastCourseWork/selectionOfComponents";
    }
    @GetMapping("/about")
    public String getAbout() {
        return "TheSecondVersion_PastCourseWork/about_site";
    }

    @GetMapping("/CPU")
    public String getCPU() {
        return "TheSecondVersion_PastCourseWork/ComponentsPages/CPU_page";
    }
    @GetMapping("/Motherboard")
    public String getMotherboard() {
        return "TheSecondVersion_PastCourseWork/ComponentsPages/Motherboard_page";
    }
    @GetMapping("/GPU")
    public String getGPU() {
        return "TheSecondVersion_PastCourseWork/ComponentsPages/GPU_page";
    }
    @GetMapping("/RAM")
    public String getRAM() {
        return "TheSecondVersion_PastCourseWork/ComponentsPages/RAM_page";
    }
    @GetMapping("/SSD")
    public String getSSD() {
        return "TheSecondVersion_PastCourseWork/ComponentsPages/SSD_page";
    }
    @GetMapping("/HDD")
    public String getHDD() {
        return "TheSecondVersion_PastCourseWork/ComponentsPages/HDD_page";
    }
    @GetMapping("/FAN")
    public String getFAN() {
       return "TheSecondVersion_PastCourseWork/ComponentsPages/FAN_page";
    }
    @GetMapping("/PSU")
    public String getPSU() {
        return "TheSecondVersion_PastCourseWork/ComponentsPages/PSU_page";
    }
}

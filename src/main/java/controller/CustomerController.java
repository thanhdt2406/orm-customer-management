package controller;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ModelAndView listCustomer() {
        List<Customer> customers = customerService.findAll();
        return new ModelAndView("list", "customers", customers);
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        return new ModelAndView("create", "customer", new Customer());
    }

    @PostMapping("/create")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "Success!");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            return new ModelAndView("edit", "customer", customer);
        }
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/edit")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Customer updated successfully!");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            return new ModelAndView("delete", "customer", customer);
        }
        return new ModelAndView("redirect:/customer");

    }

    @PostMapping("/delete")
    public ModelAndView deleteCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.remove(customer.getId());
        return new ModelAndView("redirect:/customer") ;
    }
}

package by.tvoyshevich.base.controllers;

import by.tvoyshevich.base.models.Orders;
import by.tvoyshevich.base.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/all-orders")
    public String allOrders(Model model) {
        Iterable<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);
        return "all-orders";
    }

    @GetMapping("/order/add")
    public String newOrder(Model model) {
        return "add-order";
    }

    @PostMapping("/order/add")
    public String orderAdd(@RequestParam String title, @RequestParam String description, @RequestParam String price, @RequestParam String name, @RequestParam String phone,
                           @RequestParam String adress, Model model) {
        Orders order = new Orders(title, description, name, adress, phone, price);
        ordersRepository.save(order);
        return "redirect:/all-orders";
    }

    @GetMapping("/order/{id}")
    public String orderDetails(@PathVariable(value = "id") int id, Model model) {
        if (!ordersRepository.existsById(id)) {
            return "redirect:/all-orders";
        }
        Optional<Orders> order = ordersRepository.findById(id);
        ArrayList<Orders> result = new ArrayList<>();
        order.ifPresent(result::add);
        model.addAttribute("order", result);
        return "order-details";
    }

    @GetMapping("/order/{id}/edit")
    public String edit(@PathVariable(value = "id") int id, Model model) {
        if (!ordersRepository.existsById(id)) {
            return "redirect:/all-orders";
        }
        Optional<Orders> order = ordersRepository.findById(id);
        ArrayList<Orders> result = new ArrayList<>();
        order.ifPresent(result::add);
        model.addAttribute("order", result);
        return "order-edit";

    }

    @PostMapping("/order/{id}/edit")
    public String orderEdit(@PathVariable(value = "id") int id, @RequestParam String title, @RequestParam String description, @RequestParam String price, @RequestParam String name, @RequestParam String phone,
                            @RequestParam String adress, Model model) {
        Orders order = ordersRepository.findById(id).orElseThrow();
        order.setTitle(title);
        order.setDescription(description);
        order.setPrice(price);
        order.setName(name);
        order.setAdress(adress);
        order.setPhone(phone);
        ordersRepository.save(order);
        return "redirect:/all-orders";
    }

    @GetMapping("/order/{id}/delete")
    public String delete(@PathVariable(value = "id") int id, Model model) {
        if (!ordersRepository.existsById(id)) {
            return "redirect:/all-orders";
        }
        Optional<Orders> order = ordersRepository.findById(id);
        ArrayList<Orders> result = new ArrayList<>();
        order.ifPresent(result::add);
        model.addAttribute("order", result);
        return "order-delete";
    }

    @PostMapping("/order/{id}/delete")
    public String orderDelete(@PathVariable(value = "id") int id, Model model) {
        Orders order = ordersRepository.findById(id).orElseThrow();
        ordersRepository.delete(order);
        return "redirect:/all-orders";
    }

    @GetMapping("/search")
    public String search (Model model) {
        Iterable<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);
        return "search";
    }

    @PostMapping("filter")
    public String filter (@RequestParam String text, Model model) {
        List<Orders> orders = ordersRepository.findByPhone(text);
        if(text!=null){
        model.addAttribute("orders", orders);
        return "search";
        } else return "all-orders";
    }
}
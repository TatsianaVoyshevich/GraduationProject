package by.tvoyshevich.base.repo;

import by.tvoyshevich.base.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<Orders, Integer> {

    List<Orders> findByTitle (String title);
    List<Orders> findByDescription (String description);
    List<Orders> findByName (String name);
    List<Orders> findByAdress (String adress);
    List<Orders> findByPhone (String phone);
}

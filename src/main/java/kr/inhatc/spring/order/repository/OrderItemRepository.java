package kr.inhatc.spring.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.inhatc.spring.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}

package kr.inhatc.spring.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.inhatc.spring.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}

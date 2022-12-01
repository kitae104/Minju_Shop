package kr.inhatc.spring.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.inhatc.spring.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    

}

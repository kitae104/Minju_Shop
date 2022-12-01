package kr.inhatc.spring.utils.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

//변경 되는지 안 되는지 계속 감지하는 어노테이션
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {
   
//    생성된 날을 자동으로 가져온다 
    @CreatedDate
//    한 번만 세팅을 할 거임 (그니까 날짜가 건들 때마다 바뀌면 안되잖아요) -> 수정 불가로 만든다. 
    @Column(updatable = false)
    private LocalDateTime regTime;
    
//    수정 될 때마다 그 날자로 수정해준다. 
    @LastModifiedDate
    private LocalDateTime updateTime;

}

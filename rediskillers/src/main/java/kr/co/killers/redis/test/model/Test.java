package kr.co.killers.redis.test.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Data
public class Test {
    @NotNull(message="파라미터 num 인자가 존재하지 않습니다.")
    @NotEmpty(message="파라미터 num 의 값이 없습니다.")
    private String num;
    @NotEmpty(message="파라미터에 zipCode 의 값이 없습니다.")
    private String zipCode;
    private String sido;
    private String gugun; 
    

}

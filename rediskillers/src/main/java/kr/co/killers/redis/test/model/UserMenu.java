package kr.co.killers.redis.test.model;

import java.util.List;


import lombok.Data;

@Data
public class UserMenu {
    private String menuId;
    private String menuNm;
    private List<UserScreen> userScreenList;

}

package org.example.objectpool.pojo;

import lombok.Data;

import javax.persistence.Id;

/**
 * @author lch
 * @date 2023年03月15日 22:43
 */
@Data
public class User {
    @Id
    private Long userId;
    private String username;
    private String phone;
}

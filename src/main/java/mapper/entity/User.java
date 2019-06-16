package mapper.entity;

import lombok.Data;

/**
 * @author primerxiao
 * @date 2019/6/9
 */
@Data
public class User {
    private int id;
    private String name;
    private int age;
    private String pwd;
}

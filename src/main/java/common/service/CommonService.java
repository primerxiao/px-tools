package common.service;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author primerxiao
 * @date 2019/6/16
 */
@Log4j2
@Data
public class CommonService<T> {
    public T controller;
}

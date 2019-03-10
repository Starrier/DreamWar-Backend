package org.starrier.dreamwar.util.common;

import jdk.internal.dynalink.ChainedCallSite;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Controller 参数校验，错误返回封装
 *
 * @author Starrier
 * @date 2019/1/31.
 */
@Data
@NoArgsConstructor
public class ParameterInvalidItem implements Serializable {

    private String fieldName;
    private String message;

}

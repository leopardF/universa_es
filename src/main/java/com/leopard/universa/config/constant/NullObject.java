package com.leopard.universa.config.constant;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NullObject {
    private Object option = true;
}

package com.udacity.orderservice.domain.car;

import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class Manufacturer {

        private Integer code;
        private String name;

        public Manufacturer() { }

        public Manufacturer(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

}

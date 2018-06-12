package com.epam.training.sportsbetting.domain.user;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
@XmlType(name = "currency")
public enum Currency {
    HUF, USD, EUR
}

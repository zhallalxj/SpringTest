package org.zh.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"values"})
public interface IgnoreValues {

}

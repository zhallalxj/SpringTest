package org.zh.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @Description:TODO(数据返回格式封装)   
 * @author: level.meng 
 * @date:   2017年2月16日 下午4:47:58   
 *     
 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
@SuppressWarnings("all")
@Component
public class CustomResponse implements Serializable {

	private static final long serialVersionUID = -5040291346567702945L;

	public static final Integer STATUS_SUCCESS = 10000;

	public static final Integer STATUS_ERROR = -1;

	public static final String SUCCESS = "SUCCESS";

	public static final String ERROR = "ERROR";
	
	public static final String JSON_CONVERT_ERROR = "JSON_CONVERT_ERROR";

	private Integer status;

	private String message;

	private Map<String, Object> values;

	public CustomResponse() {
		this(STATUS_SUCCESS, SUCCESS, null);
	}

	public CustomResponse(Integer status, String message) {
		this(status, message, null);
	}

	public CustomResponse(Integer status, String message, Map<String, Object> values) {
		// TODO Auto-generated constructor stub
		this.status = status;
		this.message = message;
		if (values != null) {
			this.values = new TreeMap<String, Object>(values);
		}
		
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		if (values != null) {
			if (this.values != null) {
				this.values.clear();
				this.values.putAll(values);
			} else {
				this.values = new TreeMap<String, Object>(values);
			}
		}
	}

	public void addValues(Map<String, Object> values) {
		if (values != null && !values.isEmpty()) {
			if (this.values == null) {
				this.values = new TreeMap<String, Object>(values);
			} else {
				this.values.putAll(values);
			}
		}
	}

	public void removeValue(String key) {
		if (!StringUtils.hasText(key))
			return;
		values.remove(key);
	}

	@SuppressWarnings("all")
	public void addValue(String key, Object value) {
		if (key == null) {
			throw new IllegalArgumentException("The key must not be null or empty.");
		}
		if (values == null && value != null) {
			values = new TreeMap<String, Object>();
		}
		if (values != null) {
			if (value == null) {
				values.remove(key);
				return;
			}
			values.put(key, value);
		}
	}

	/*
	 * getErrorJson  
	 */
	public  String getErrorJson(String message){
		return getErrorJson(STATUS_ERROR,message ,null);
	}
	
	public String getErrorJson(String message, int status) {
		return getErrorJson(status, message, null);
	}
	
	public  String getErrorJson(String message, Map<String, Object> values) {
		return getErrorJson(STATUS_ERROR, message, values);
	}
	
	
	public String getErrorJson(int status,String message,Map<String, Object> values) {
		this.setMessage(message);
		this.setStatus(status);
		this.setValues(values);
		try {
			if(this.getValues() == null){
				Map<Class<?>, Class<?>> sourceMixins = new HashMap<>();
				sourceMixins.put(CustomResponse.class, IgnoreValues.class);
				return new JsonObjectMapper().setMixIns(sourceMixins).writeValueAsString(this);
			}
			return new JsonObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON_CONVERT_ERROR;
	}

 
    public  String getSuccessJson(String message) {
    	return getSuccessJson(message,null);
    }
    public  String getSuccessJson(Map<String, Object> values) {
    	return getSuccessJson(SUCCESS,values);
    }
    
    public String getSuccessJson(String message, Map<String, Object> values) {
    	this.message = message;
    	if(values!=null && this.values!=null){
    		this.values.putAll(values);
    	}
        try {
        	if(this.getValues() == null){
				Map<Class<?>, Class<?>> sourceMixins = new HashMap<>();
				sourceMixins.put(CustomResponse.class, IgnoreValues.class);
				return new JsonObjectMapper().setMixIns(sourceMixins).writeValueAsString(this);
			}
			return new JsonObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON_CONVERT_ERROR;
    }
    
    public String toJSONString() {
		// TODO Auto-generated method stub
		try {
			if(getValues() == null){
				Map<Class<?>, Class<?>> sourceMixins = new HashMap<>();
				sourceMixins.put(CustomResponse.class, IgnoreValues.class);
				return new JsonObjectMapper().setMixIns(sourceMixins).writeValueAsString(this);
			}
			return new JsonObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON_CONVERT_ERROR;
	}
		
	public String toJSONString(Map<Class<?>, Class<?>> sourceMixins) {
		// TODO Auto-generated method stub
		try {
			if(getValues() == null){
				Map<Class<?>, Class<?>> sourceMixin = new HashMap<>();
				sourceMixins.put(CustomResponse.class, IgnoreValues.class);
				return new JsonObjectMapper().setMixIns(sourceMixin).writeValueAsString(this);
			}
			return new JsonObjectMapper().setMixIns(sourceMixins).writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON_CONVERT_ERROR;
	}
	

}
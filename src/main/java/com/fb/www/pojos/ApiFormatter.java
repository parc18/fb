package com.fb.www.pojos;

import java.io.Serializable;

public class ApiFormatter<T> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
    private String result;
    private T response;
    private String error;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String resultVal) {
        this.result = resultVal;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}

package com.xbxxz.demo.result;


import com.xbxxz.demo.enums.ResponseEnum;
import com.xbxxz.demo.enums.i.IResponseEnum;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result(){}

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /** 成功 */
    public static final Integer SUCCESS = ResponseEnum.SUCCESS.getCode();

    /** 失败 */
    public static final Integer FAIL = ResponseEnum.FAILD.getCode();

    private static <T> Result<T> restResult(T data, int code, String message)
    {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(message);
        return apiResult;
    }

    /**
     * 返回 失败结果
     */
    public static <T> Result<T> ok(){
        return restResult(null, SUCCESS, ResponseEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> ok(T data){
        return restResult(data, SUCCESS, ResponseEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> Result<T> ok(IResponseEnum responseEnum) {
        return restResult(null, (Integer) responseEnum.code(), (String) responseEnum.message());
    }


    /**
     * 返回 失败结果
     */
    public static <T> Result<T> error() {
        return restResult(null, FAIL, ResponseEnum.FAILD.getMessage());
    }

    public static <T> Result<T> error(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> Result<T> error(T data) {
        return restResult(data, FAIL, ResponseEnum.FAILD.getMessage());
    }

    public static <T> Result<T> error(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> Result<T> error(IResponseEnum responseEnum) {
        return restResult(null, (Integer) responseEnum.code(), (String) responseEnum.message());
    }

    /**
     * 返回 指定结果
     */
    public  static<T>  Result<T> setResult(IResponseEnum responseEnum){
        return restResult(null, (Integer) responseEnum.code(), (String) responseEnum.message());
    }
    public  static<T>  Result<T> setResult(IResponseEnum responseEnum, T data){
        return restResult(data, (Integer) responseEnum.code(), (String) responseEnum.message());
    }
}

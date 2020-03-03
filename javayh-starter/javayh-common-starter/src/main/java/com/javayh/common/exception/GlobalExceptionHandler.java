package com.javayh.common.exception;

import com.javayh.common.result.ResultData;
import com.javayh.common.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 全局异常
 * </p>
 *
 * @author Dylan-haiji
 * @version 1.0.0
 * @since 2020-03-01 21:36
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * <p>
     *       全局Base异常处理
     * </p>
     * @version 1.0.0
     * @author Dylan
     * @since 2020/2/27
     * @param e
     */
    @ExceptionHandler({BaseException.class})
    public ResultData customExceptionHandler(BaseException e) {
        sysLog();
        log.error("自定义异常 ---> {}",e);
        return ResultData.fail(e.getMessage());
    }

    /**
     * <p>
     *       其他类型的异常处理
     * </p>
     * @version 1.0.0
     * @author Dylan
     * @since 2020/2/27
     * @param e
     */
    @ExceptionHandler({Exception.class})
    public ResultData customExceptionHandler(Exception e) {
        sysLog();
        log.error("未知的运行异常 ---> ",e);
        return ResultData.fail();
    }

    /**
     * <p>
     *       参数异常处理
     * </p>
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2020/2/28
     * @param exception
     */
    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public ResultData methodNotValidHandler(MethodArgumentNotValidException exception) {
        sysLog();
        log.error("参数异常 ---> ",exception);
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        return  ResultData.fail(fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * <p>
     *       统一日志输出
     * </p>
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2020/2/28
     * @param
     * @return void
     */
    private void sysLog(){
        HttpServletRequest request = RequestUtils.getRequest();
        String requestUri = request.getRequestURI();
        log.error("异常  method ---> {}",request.getMethod());
        log.error("异常 requestURI ---> {}",requestUri);
    }

}

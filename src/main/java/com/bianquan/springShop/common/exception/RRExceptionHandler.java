package com.bianquan.springShop.common.exception;



import com.bianquan.springShop.common.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 */
@RestControllerAdvice
public class RRExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public Response handleRRException(RRException e){
        Response response = new Response();
        response.put("code", e.getCode());
        response.put("msg", e.getMessage());

        return response;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Response handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return Response.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Response handleDuplicateKeyException(DuplicateKeyException e){
        logger.error(e.getMessage(), e);
        return Response.error("数据库中已存在该记录");
    }


    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e){
        logger.error(e.getMessage(), e);
        return Response.error();
    }
}


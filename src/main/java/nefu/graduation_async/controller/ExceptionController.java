package nefu.graduation_async.controller;

import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.dto.Code;
import nefu.graduation_async.exception.XException;
import nefu.graduation_async.vo.ResultVO;
import org.springframework.r2dbc.UncategorizedR2dbcException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    // filter内无效，单独处理。
    @ExceptionHandler(XException.class)
    public Mono<ResultVO> handleXException(XException exception) {
        if (exception.getCode() != null) {
            return Mono.just(ResultVO.error(exception.getCode()));
        }
        return Mono.just(ResultVO.error(exception.getCodeN(), exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResultVO> handleException(Exception exception) {
        return Mono.just(ResultVO.error(Code.BAD_REQUEST.getCode(), exception.getMessage()));
    }

    @ExceptionHandler(UncategorizedR2dbcException.class)
    public Mono<ResultVO> handelUncategorizedR2dbcException(UncategorizedR2dbcException exception) {
        return Mono.just(ResultVO.error(Code.BAD_REQUEST.getCode(), "唯一约束冲突！" + exception.getMessage()));
    }
}
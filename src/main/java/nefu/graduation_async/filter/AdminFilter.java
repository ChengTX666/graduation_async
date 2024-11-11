package nefu.graduation_async.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nefu.graduation_async.dox.User;
import nefu.graduation_async.dto.Code;
import nefu.graduation_async.vo.RequestAttributeConstant;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;
@Component
@Slf4j
@Order(2)
@RequiredArgsConstructor
public class AdminFilter implements WebFilter {
    private final ResponseHelper responseHelper;
    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange,@NonNull WebFilterChain chain) {
        PathPattern includes = new PathPatternParser().parse("/api/admin/**");
        ServerHttpRequest request = exchange.getRequest();

        if(includes.matches(request.getPath().pathWithinApplication())){
            String role=(String) exchange.getAttribute(RequestAttributeConstant.ROLE);
            if(User.ROLE_ADMIN.equals(role))return chain.filter(exchange);
            return responseHelper.response(Code.FORBIDDEN,exchange);
        }
        return chain.filter(exchange);

    }
}

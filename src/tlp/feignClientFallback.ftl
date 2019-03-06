package ${feignClientPackage};

import com.fow.core.rest.view.SuccessView;
import ${domainPackage}.${className};
import ${requestPackage}.${className}SearchForm;
import ${requestPackage}.${className}CreateForm;
import ${requestPackage}.${className}UpdateForm;
import ${viewPackage}.${className}PageView;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ${className}HystrixClientFallback implements FallbackFactory<${className}FeignClient> {

    private static final Logger logger = LoggerFactory.getLogger(${className}HystrixClientFallback.class);

    @Override
    public ${className}FeignClient create(Throwable throwable) {
        return new ${className}FeignClient() {
            @Override
            public ${className}PageView search(${className}SearchForm form) {
                logger.error(throwable.getMessage());
                return null;
            }

            @Override
            public ${className} searchOne(${className}SearchForm form) {
                logger.error(throwable.getMessage());
                return null;
            }

            @Override
            public Integer count(${className}SearchForm form) {
                logger.error(throwable.getMessage());
                return null;
            }

            @Override
            public ${className} selectById(Integer id) {
                logger.error(throwable.getMessage());
                return null;
            }

            @Override
            public SuccessView insert(${className}CreateForm form) {
                logger.error(throwable.getMessage());
                return null;
            }

            @Override
            public SuccessView update(${className}UpdateForm form) {
                logger.error(throwable.getMessage());
                return null;
            }
        };
    }


}

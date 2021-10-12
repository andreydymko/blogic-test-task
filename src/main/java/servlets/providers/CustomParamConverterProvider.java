package servlets.providers;

import servlets.providers.helpers.OrderParamConverter;
import servlets.providers.helpers.RangeHeaderConverter;
import servlets.providers.helpers.types.OrdersListParam;
import servlets.providers.helpers.types.RangeHeader;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class CustomParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType == RangeHeader.class) {
            return (ParamConverter<T>) new RangeHeaderConverter();
        }
        if (rawType == OrdersListParam.class) {
            return (ParamConverter<T>) new OrderParamConverter();
        }
        return null;
    }
}

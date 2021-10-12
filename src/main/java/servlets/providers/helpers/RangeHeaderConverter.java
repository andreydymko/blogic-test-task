package servlets.providers.helpers;

import servlets.providers.helpers.types.RangeHeader;

import javax.ws.rs.ext.ParamConverter;

public class RangeHeaderConverter implements ParamConverter<RangeHeader> {
    @Override
    public RangeHeader fromString(String s) {
        return RangeHeader.fromValue(s);
    }

    @Override
    public String toString(RangeHeader rangeHeader) {
        return rangeHeader.toString();
    }
}

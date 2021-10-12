package servlets.providers.helpers;

import servlets.providers.helpers.types.ContentRangeHeader;

import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.RuntimeDelegate;

@Provider
public class RangeHeaderDelegate implements RuntimeDelegate.HeaderDelegate<ContentRangeHeader> {
    @Override
    public ContentRangeHeader fromString(String value) {
        return ContentRangeHeader.fromValue(value);
    }

    @Override
    public String toString(ContentRangeHeader value) {
        return value.toString();
    }
}

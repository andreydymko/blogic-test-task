package servlets.providers.helpers.types;

public class ContentRangeHeader {
    private String units;
    private Long from;
    private Long to;
    private Long limit;



    public ContentRangeHeader(String units, Long from, Long to, Long limit) {
        this.units = units;
        this.from = from;
        this.to = to;
        this.limit = limit;
    }

    public ContentRangeHeader(String units, Long from, Long to) {
        this.units = units;
        this.from = from;
        this.to = to;
    }

    public static ContentRangeHeader fromLength(String units, Long from, Long length, Long limit) {
        return new ContentRangeHeader(units, from, from + length - 1, limit);
    }

    public static ContentRangeHeader fromValue(String value) {
        if (value == null) {
            return null;
        }

        String[] tokens = value.replace("Content-Range: ", "").split(" ");
        String unit = tokens[0];

        String[] fromToLimit = tokens[1].split("-");
        Long from, to, limit;
        String[] toLimit;

        if (fromToLimit.length < 2) {
            from = 0L;
            to = Long.MAX_VALUE - 1;
            toLimit = fromToLimit[0].split("/");
        } else {
            from = Long.valueOf(fromToLimit[0]);
            toLimit = fromToLimit[1].split("/");
            to = Long.valueOf(toLimit[0]);
        }

        try {
            limit = Long.valueOf(toLimit[1]);
        } catch (Exception e) {
            limit = Long.MAX_VALUE;
        }
        return new ContentRangeHeader(unit, from, to, limit);
    }

    @Override
    public String toString() {
        if (limit != null) {
            return String.format("%s %d-%d/%s", units, from, to, limit);
        } else {
            return String.format("%s %d-%d/*", units, from, to);
        }
    }
}

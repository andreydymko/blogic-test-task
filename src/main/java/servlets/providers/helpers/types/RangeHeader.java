package servlets.providers.helpers.types;

public class RangeHeader {
    private String units;
    private Long from;
    private Long to;

    public RangeHeader(String units, Long from, Long to) {
        this.units = units;
        this.from = from;
        this.to = to;
    }

    public String getUnits() {
        return units;
    }

    public Long getFrom() {
        return from;
    }

    public Long getTo() {
        return to;
    }

    public Long getLength() {
        return to - from;
    }

    public static RangeHeader fromValue(String range) {
        if (range == null) {
            return null;
        }
        String[] tokens = range.replace("Range: ", "").split("=");
        String unit = tokens[0];

        String[] fromTo = tokens[1].split("-");
        Long from = Long.valueOf(fromTo[0]);
        Long to = Long.valueOf(fromTo[1]);

        return new RangeHeader(unit, from, to);
    }

    @Override
    public String toString() {
        return String.format("Range: %s=%d-%d", units, from, to);
    }
}

package com.aaronr92.korben_chan_bot;

public enum Flag {

    USSR("<:earth_asia:1049352687197630464>"),
    CHINA("<:flag_cn:1046791081594982460>"),
    FRANCE("<:flag_cp:1046791081594982460>"),
    ITALY("<:flag_it:1046791081594982460>"),
    GERMANY("<:flag_de:1046791081594982460>"),
    USA("<:flag_us:1046791081594982460>"),
    BRITAIN("<:flag_gb:1046791081594982460>"),
    SWEDEN("<:flag_se:1046791081594982460>"),
    CZECH("<:flag_cz:1046791081594982460>");

    final String code;

    Flag(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

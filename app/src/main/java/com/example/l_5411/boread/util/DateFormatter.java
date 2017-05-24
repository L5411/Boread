package com.example.l_5411.boread.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by L_5411 on 2017/3/11.
 */

public class DateFormatter {

    public String dateFormat(Long date) {
        String sDate;
        Date d = new Date(date + 24 * 60 * 60 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        sDate = format.format(d);

        return sDate;
    }

}

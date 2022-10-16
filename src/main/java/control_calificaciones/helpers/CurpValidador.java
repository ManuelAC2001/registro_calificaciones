package control_calificaciones.helpers;

import java.time.LocalDate;

public class CurpValidador {

    public static boolean coincideFecha(String curp, LocalDate fecha) {

        String curpDate = curp.substring(4, 10);

        Integer curpYear = Integer.parseInt(curpDate.substring(0, 2));
        Integer curpMonth = Integer.parseInt(curpDate.substring(2, 4));
        Integer curpDay = Integer.parseInt(curpDate.substring(4, curpDate.length()));

        return LocalDate.of(fecha.getYear(), curpMonth, curpDay).equals(fecha) &&
                matchesYearCurp(fecha.getYear(), curpYear);
    }

    private static boolean matchesYearCurp(Integer year, Integer curpYear) {

        year = Integer.parseInt(String.valueOf(year).substring(2));

        return year == curpYear;
    }

}

package ee.svekko.eventmgr.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EstIdService {
    public boolean validate(String idCode) {
        if (idCode == null || idCode.length() != 11) {
            return false;
        }

        Integer century = switch (idCode.charAt(0)) {
            case '3', '4' -> 19;
            case '5', '6' -> 20;
            default -> null;
        };
 
        if (century == null) {
            return false;
        }

        try {
            if (getControlNumber(idCode) != Integer.parseInt(String.valueOf(idCode.charAt(10)))) {
                return false;
            }

            int year = Integer.parseInt(century + idCode.substring(1, 3));
            int month = Integer.parseInt(idCode.substring(3, 5));
            int day = Integer.parseInt(idCode.substring(5, 7));

            LocalDate.parse(String.format("%04d-%02d-%02d", year, month, day));
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    private int getControlNumber(String idCode) {
        List<Integer> mul1 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 1);
        List<Integer> mul2 = List.of(3, 4, 5, 6, 7, 8, 9, 1, 2, 3);

        int controlNum;
        int total = 0;

        for (int i = 0; i < 10; ++i) {
            total += Integer.parseInt(String.valueOf(idCode.charAt(i))) * mul1.get(i);
        }

        controlNum = total % 11;
        total = 0;

        if (controlNum == 10) {
            for (int i = 0; i < 10; ++i) {
                total += Integer.parseInt(String.valueOf(idCode.charAt(i))) * mul2.get(i);
            }

            controlNum = total % 11;
            if (10 == controlNum) {
                controlNum = 0;
            }
        }

        return controlNum;
    }
}

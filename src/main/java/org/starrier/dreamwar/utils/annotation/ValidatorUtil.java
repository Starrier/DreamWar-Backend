package org.starrier.dreamwar.util.annotation;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Starrier
 * @date 2019/1/7.
 */
public class ValidatorUtil {

    public static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String value) {
        return !StringUtils.isEmpty(value) && MOBILE_PATTERN.matcher(value).matches();
    }
}

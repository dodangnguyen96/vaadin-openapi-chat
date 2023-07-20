Pattern pattern = Pattern.compile("\"(.*?)\":\\s*\"(.*?)\"");
Matcher matcher = pattern.matcher(input);
StringBuilder sb = new StringBuilder();

package com.example.teslocal;

public class ReplaceString {
public static void main(String[] args) {
String json = "{\n  \"service\": \"dental\",\n  \"plan\": \"basic\"\n,\n  \"npi\": 12321321\n}";
System.out.println(json.trim());
// Remove double quotes around keys
String modifiedJson = json.replaceAll("\"(\\w+)\":", "$1:");

        System.out.println(modifiedJson);
    }
}

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

public class HeaderUtil {
public static String getAuthorizationHeader() {
RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
return request.getHeader("Authorization");
}
}
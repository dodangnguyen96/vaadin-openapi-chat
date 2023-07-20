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
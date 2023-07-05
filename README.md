Pattern pattern = Pattern.compile("\"(.*?)\":\\s*\"(.*?)\"");
Matcher matcher = pattern.matcher(input);
StringBuilder sb = new StringBuilder();

            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                sb.append(key).append(":\"").append(value).append("\",");
            }

            String result = sb.toString().substring(0, sb.length() - 1).trim();


            return result;
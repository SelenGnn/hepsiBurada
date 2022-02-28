package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    private static String pageKey;

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getPageObject(String pageObject) throws IOException, ParseException {
        JSONObject returnObject = getJsonObject();
        return returnObject.get(pageObject).toString();
    }

    public String getElementKey(String elementKey) throws IOException, ParseException {
        JSONObject returnObject = getJsonObject();
        JSONObject elementObject = (JSONObject) returnObject.get("elements");
        return elementObject.get(elementKey).toString();
    }

    private JSONObject getJsonObject() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("src/test/resources/configs/pages.json"));
        JSONObject jsonObject = (JSONObject) object;
        return (JSONObject) jsonObject.get(pageKey);
    }

}

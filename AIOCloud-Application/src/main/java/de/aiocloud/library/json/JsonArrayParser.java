package de.aiocloud.library.json;

import com.google.gson.JsonArray;

public class JsonArrayParser {

    private String[] array;
    private JsonArray jsonarray;

    /**
     * This is the initial method of the 'JsonArrayParser' object.
     * @param jsonarray
     */
    public JsonArrayParser(JsonArray jsonarray) {
        this.jsonarray = jsonarray;
        int arraySize = this.jsonarray.size();
        String[] stringArray = new String[arraySize];
        for(int i=0; i<arraySize; i++) {
            stringArray[i] = this.jsonarray.get(i).getAsString();
        }
        this.array = stringArray;
    }
    /**
     * GET method.
     * @return the string array from jsonarray
     */
    public String[] getStringArray() {
        return array;
    }
}

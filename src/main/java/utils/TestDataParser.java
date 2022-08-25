package utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import helpers.FileFormat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

public class TestDataParser {
    private JsonFactory jsonFactory;

    public TestDataParser(FileFormat fileType) {
        if (fileType == FileFormat.YAML) {
            jsonFactory = new YAMLFactory();
        } else {
            jsonFactory = new JsonFactory();
        }
    }

    public Object[][] readLinearStructure(String pathToFile) {
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        Object[][] output = new Object[][]{};

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(pathToFile);

        if (resource == null) {
            throw new IllegalArgumentException("File not found! " + pathToFile);
        }

        try {
            JsonNode rootArray = mapper.readTree(new File(resource.toURI()));
            int n = rootArray.size();
            int m = rootArray.get(0).size();
            Object[][] data = new Object[n][m];

            for (int i = 0; i < n; i++) {
                JsonNode row = rootArray.get(i);

                int j = 0;
                for (Iterator<String> it = row.fieldNames(); it.hasNext(); ) {
                    JsonNode fieldValue = row.get(it.next());
                    Object object;

                    if (fieldValue.isInt()) {
                        object = fieldValue.asInt();
                    } else if (fieldValue.isDouble()) {
                        object = fieldValue.asDouble();
                    } else if (fieldValue.isBoolean()) {
                        object = fieldValue.asBoolean();
                    } else {
                        object = fieldValue.asText();
                    }

                    data[i][j] = object;

                    j++;
                }
            }

            output = data;

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return output;
    }
}

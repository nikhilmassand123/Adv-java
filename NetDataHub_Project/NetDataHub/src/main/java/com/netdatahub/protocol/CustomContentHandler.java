package com.netdatahub.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

class ProductCatalogPayload {
    private final List<String> lines;

    public ProductCatalogPayload(List<String> lines) {
        this.lines = lines;
    }

    public List<String> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return String.join("\n", lines);
    }
}

public class CustomContentHandler extends ContentHandler {
    @Override
    public Object getContent(URLConnection urlc) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlc.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return new ProductCatalogPayload(lines);
    }
}

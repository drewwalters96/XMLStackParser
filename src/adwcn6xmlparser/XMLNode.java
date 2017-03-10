package adwcn6xmlparser;

import java.util.ArrayList;
import java.util.HashMap;

public class XMLNode {
    private String name;
    private String content;
    private HashMap<String, String> attributes = null;
    private ArrayList<XMLNode> elements = null;
    
    public String getName() {
        return name;
    }
    
    public String getContent() {
        return content;
    }
        
    public HashMap<String, String> getAttributes() {
        return attributes;
    }
    
    public ArrayList<XMLNode> getElements() {
        return elements;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public void addAttribute(String name, String content) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(name, content);
    }
    
    public void addElement(XMLNode node) {
        if (elements == null) {
            elements = new ArrayList<>();
        }
        elements.add(node);
    }
}

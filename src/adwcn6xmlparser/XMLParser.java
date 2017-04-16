// Created by Drew Walters
//
// An event-driven XML parser developed for CS4330
// that creates a DOM representation of an XML 
// document using the Java SAX parser.
package adwcn6xmlparser;

import java.io.File;
import java.util.Stack;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class XMLParser {
    public static XMLNode parse(File file) {
        XMLNode documentRoot = null;
        Stack<XMLNode> stack = new Stack<>();
        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            
            DefaultHandler dh = new DefaultHandler() {               
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    XMLNode node = new XMLNode();
                    node.setName(qName);
                    
                    // Add attributes if any exist
                    for (int i = 0; i < attributes.getLength(); i++) {
                        node.addAttribute(attributes.getLocalName(i), attributes.getValue(i));
                    }
                    
                    // Add element to stack
                    stack.add(node);
                }
               
                @Override
                public void endElement(String uri, String localName, String qName) {
                   // Get current element if not root
                   if (stack.size() >= 2) {
                        XMLNode currentElement = stack.pop();
                   
                        // Get previous element and append current element
                        XMLNode prevElement = stack.peek();
                        prevElement.addElement(currentElement);
                   }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    // Get current element and set content
                    XMLNode currentElement = stack.peek();
                    currentElement.setContent(new String(ch, start, length));
                }
            };
            parser.parse(file, dh);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        
        // Root element is last remaining element on stack
        documentRoot = stack.pop();
        return documentRoot;
    }
}
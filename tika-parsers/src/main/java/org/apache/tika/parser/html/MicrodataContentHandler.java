/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.parser.html;

import static org.apache.tika.sax.XHTMLContentHandler.XHTML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.parser.html.microdata.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

/**
 * SAX based Microdata content handler.
 *
 * @see <a href="https://issues.apache.org/jira/browse/TIKA-980">TIKA-980</a>
 * @author markus@apache.org
 */
public class MicrodataContentHandler extends DefaultHandler {
    private static final Log logger =
      LogFactory.getLog(MicrodataContentHandler.class);

    /** List of item scopes */
    protected List<ItemScope> items = new ArrayList<ItemScope>();

    /** Which depth is this itemScope? */
    protected Stack<Integer> scopeDepthStack = new Stack<Integer>();

    /** Temp reference to the itemScope we're currently working on */
    protected Stack<ItemScope> currentItemScopeRef = new Stack<ItemScope>();

    /** We need to keep track of our depth */
    protected int depth = 0;

    /** Whether to read contents for the itemprop from the sax characters */
    protected Stack<Boolean> readContentStack = new Stack<Boolean>();

    /** Name of the itemProp we're currently processing */
    protected Stack<String> propNameStack = new Stack<String>();

    /** Depth of the itemProp we're currently processing */
    protected Stack<Integer> propDepthStack = new Stack<Integer>();

    /** Buffer for storing nested characters as value for an itemProp */
    protected StringBuilder contents = new StringBuilder();

    /** Contains the contents we read from an attribute */
    protected String contentFromAttribute = null;

    /**
     * List of tags providing the <code>src</code> property.
     */
    public static final Set<String> SRC_TAGS = Collections.unmodifiableSet(
        new HashSet<String>( Arrays.asList("audio", "embed", "iframe", "img",
          "source", "track", "video") )
    );

    /**
     * List of tags providing the <code>href</code> property.
     */
    public static final Set<String> HREF_TAGS = Collections.unmodifiableSet(
        new HashSet<String>( Arrays.asList("a", "area", "link") )
    );

    /**
     * List of tags providing the <code>content</code> property.
     */
    public static final Set<String> CONTENT_TAGS = Collections.unmodifiableSet(
        new HashSet<String>( Arrays.asList("meta") )
    );

    /**
     * List of tags providing the <code>datetime</code> property.
     */
    public static final Set<String> DATETIME_TAGS =
      Collections.unmodifiableSet(new HashSet<String>( Arrays.asList("time") )
    );

    /**
     * List of tags providing the <code>value</code> property.
     */
    public static final Set<String> VALUE_TAGS = Collections.unmodifiableSet(
        new HashSet<String>( Arrays.asList("data") )
    );

    /**
     * Default constructor.
     */
    public MicrodataContentHandler() {
        super();
    }

    /**
     * Returns the list of collected items.
     *
     * @return collected items
     */
    public List<ItemScope> getItems() {
        return items;
    }

    @Override
    public void startElement(String uri, String local, String name,
      Attributes attributes) throws SAXException {
        // XHTML?
        if (XHTML.equals(uri)) {
            // Increase element depth
            depth++;

            // Do we have attributes?
            if (attributes == null || attributes.getLength() == 0) {
                // Nothing to do here
                return;
            }

            // Get the itemProp and itemType for this element
            String type = getAttr(attributes, "itemtype");
            String prop = getAttr(attributes, "itemprop");

            // The content reading state produced by this element
            boolean readContents = false;

            // Continue the previous reading state
            if (readContentStack.size() > 0) {
              readContents = readContentStack.peek();
            }

            // Do we actually have one?
            if (prop != null) {
                // We must be in an itemScope
                if (currentItemScopeRef.size() == 0) {
                  logger.warn("ItemProp " + local + "." + prop +
                    " not in itemscope");
                  return;
                }

                // Keep track of the current depth
                propDepthStack.push(depth);

                // Remember the current item prop name
                propNameStack.push(prop);

                // We'll attempt to read the contents for this itemprop
                // from its attributes
                // http://www.w3.org/TR/microdata/#values
                String content = null;

                if (SRC_TAGS.contains(local)) {
                    // Attempt to read the src attribute for this itemprop
                    content = getAttr(attributes, "src");

                } else if (HREF_TAGS.contains(local)) {
                    // Attempt to read the href attribute for this itemprop
                    content = getAttr(attributes, "href");

                } else if (CONTENT_TAGS.contains(local)) {
                    // Attempt to read the content attribute for this itemprop
                    content = getAttr(attributes, "content");

                } else if (DATETIME_TAGS.contains(local)) {
                    // Attempt to read the datetime attribute for this itemprop
                    content = getAttr(attributes, "datetime");

                } else if (VALUE_TAGS.contains(local)) {
                    // Attempt to read the datetime attribute for this itemprop
                    content = getAttr(attributes, "value");

                } else {
                    // Attempt to read the contents attribute for this itemprop
                    // (this is non-standard)
                    content = getAttr(attributes, "content");
                }

                // Did we get contents from an attribute?
                if (content != null) {
                    // Store the content read from the attribute
                    contentFromAttribute = content;
                }

                // We're reading contents from the following textElement(s)
                readContents = true;
            }

            // Are we in an item scope?
            if (isItemScope(attributes)) {
                // Create a new ItemScope
                ItemScope itemScope = new ItemScope();

                // Are we nested and in an item property and do we have a
                // property at all?
                if (scopeDepthStack.size() != 0 && prop != null) {
                    // Create a new ItemPropValue
                    ItemPropValue itemPropValue = new ItemPropValue(itemScope,
                      ItemPropValue.Type.Nested);

                    // Create a new ItemProp
                    ItemProp itemProp = new ItemProp(prop, itemPropValue);

                    // Add this itemScope as value of this property
                    currentItemScopeRef.peek().acquireProperty(itemProp);
                } else {
                    // Add scope to lowest level
                    items.add(itemScope);
                }

                // Add current depth to the stack
                scopeDepthStack.push(depth);

                // Add this item scope to the current on the stack
                currentItemScopeRef.push(itemScope);

                // Disable reading of contents
                readContents = false;
            }

            // Do we have a type here?
            if (type != null) {
                // Set the current itemScope's type
                currentItemScopeRef.peek().setType(type);

                // Disable reading of contents
                readContents = false;
            }

            // Push the final reading state from this element if it changed
            if (readContentStack.size() == 0 || readContentStack.peek() !=
              readContents || contentFromAttribute != null) {
                readContentStack.push(readContents);
            }
        }
    }

    @Override
    public void characters(char[] chars, int offset, int length)
      throws SAXException {
        // Do we have to read contents?
        if (readContentStack.size() > 0 && readContentStack.peek() == true) {
            // Copy the proper character range
            char[] characters = new char[length];
            System.arraycopy(chars, offset, characters, 0, length);

            // Add the contents
            contents.append(characters);
        }
    }

    public void characters(String characters) throws SAXException {
        if (characters != null && characters.length() > 0) {
            characters(characters.toCharArray(), 0, characters.length());
        }
    }

    @Override
    public void ignorableWhitespace(char[] chars, int offset, int length)
      throws SAXException {
        characters(chars, offset, length);
    }

    @Override
    public void endElement(String uri, String local, String name)
      throws SAXException {
        // XHTML?
        if (XHTML.equals(uri)) {
            // Do we have item scopes at all and are we leaving an item scope?
            if (scopeDepthStack.size() > 0 &&
              scopeDepthStack.peek() == depth) {
                // Yes! Get rid of it's depth info
                scopeDepthStack.pop();

                // And remove it from the temp stack
                currentItemScopeRef.pop();
            }

            // Are we back at the depth of the current itemprop?
            if (propDepthStack.size() > 0 && depth == propDepthStack.peek()) {
                // We're leaving this propery
                propDepthStack.pop();

                // This is going to be our property's value
                String contentStr;

                // Prefer content read from the attribute
                if (contentFromAttribute != null) {
                  contentStr = contentFromAttribute;
                } else {
                  contentStr = contents.toString();
                }

                // Clean it up!
                contentStr = contentStr.replaceAll("\\s+", " ").trim();

                // Get the current prop name and leave it
                String currentPropName = propNameStack.pop();

                // Create an item with attribute's value or content read from
                // text elements
                if (contentFromAttribute != null || readContentStack.size() > 0
                  && readContentStack.peek() == true) {
                    // Do we have any contents?
                    if (contentStr.length() > 0) {
                        try {
                            // Create a new ItemPropValue
                            ItemPropValue itemPropValue =
                              getItemPropValue(contentStr, local);

                            // Create a new ItemProp
                            ItemProp itemProp =
                              new ItemProp(currentPropName, itemPropValue);

                            // Add this itemScope as value of this property
                            currentItemScopeRef.peek().acquireProperty(itemProp);
                        } catch (Exception e) {
                            logger.warn("Cannot read value for ItemProp " +
                              local + "." + currentPropName + " " +
                              e.toString());
                        }
                    } else {
                        // Should we send a warning for this?
                        logger.warn("ItemProp " + currentPropName +
                          " has no contents");
                    }
                }

                // Leave the current content reading state
                if (readContentStack.size() > 0) {
                  readContentStack.pop();
                }

                // Clear the buffer only if we've not read from an attribute
                if (contentFromAttribute != null) {
                  contentFromAttribute = null;
                } else {
                  contents.setLength(0);
                }
            }

            // Clear the buffer in case we're not reading anyway
            if (readContentStack.size() == 0 ||
              readContentStack.peek() == false) {
                contents.setLength(0);
            }

            // Decrease element depth
            depth--;
        }
    }

    /**
     * Return an ItemPropValue for the specified content and element
     *
     * @param String
     * @param String
     * @return ItemPropValue
     */
    protected ItemPropValue getItemPropValue(String content, String element) {
        // Get the itemProp type for this element
        ItemPropValue.Type itemPropType = getItemPropType(element);

        // Is this a date?
        if (itemPropType == ItemPropValue.Type.Date) {
            // Return with content as Date
            try {
                return new ItemPropValue(ItemPropValue.parseDateTime(content),
                  itemPropType);
            } catch (Exception e) { }
        }

        // Return with content as String
        return new ItemPropValue(content, itemPropType);
    }

    /**
     * Returns the ItemPropValue type for the specified element.
     *
     * @param String element
     * @return ItemPropValue.Type
     */
    protected ItemPropValue.Type getItemPropType(String element) {
        if ("meta".equals(element)) {
            return ItemPropValue.Type.Plain;
        }

        if (SRC_TAGS.contains(element)) {
            return ItemPropValue.Type.Link;
        }

        if (HREF_TAGS.contains(element)) {
            return ItemPropValue.Type.Link;
        }

        if ("object".equals(element) ) {
            return ItemPropValue.Type.Link;
        }

        if ("time".equals(element) ) {
            return ItemPropValue.Type.Date;
        }

        return ItemPropValue.Type.Plain;
    }

    /**
     * Return the value for the itemscope attribute.
     *
     * @param Attributes attributes
     * @return boolean
     */
    protected boolean isItemScope(Attributes attributes) {
        return getAttr(attributes, "itemscope") != null ? true : false;
    }

    /**
     * Return the value for the specified attribute name.
     *
     * @param Attributes attributes
     * @param String name
     * @return String
     */
    protected String getAttr(Attributes attributes, String name) {
        for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.getLocalName(i).equals(name)) {
                return attributes.getValue(i);
            }
        }

        return null;
    }

}

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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import java.lang.*;
import java.lang.reflect.*;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.microdata.*;
import org.apache.tika.parser.html.microdata.*;
import org.apache.tika.parser.ParseContext;

/**
 * Test cases for the {@link MicrodataContentHandler} class.
 */
public class MicrodataContentHandlerTest extends TestCase {

    public void testMicrodataParser() throws SAXException, IOException, TikaException  {
        //String path = "/test-documents/microdata.html";
        String path = "/test-documents/recipe.html";

        // Set up a parse context
        ParseContext context = new ParseContext();

        // ..and tell the parser not to remap HTML elements or we'll loose important other mark up
        context.set(HtmlMapper.class, IdentityHtmlMapper.INSTANCE);

        Metadata metadata = new Metadata();
        MicrodataContentHandler handler = new MicrodataContentHandler();
        new HtmlParser().parse(
                MicrodataContentHandlerTest.class.getResourceAsStream(path),
                handler,  metadata, context);

        // Get the microdata items
        List<ItemScope> items = handler.getItems();
System.out.println("Number of items: " + Integer.toString(items.size()));
System.out.println(items.toString());

        // The first itemscope should be webpage
//         assertEquals("http://schema.org/WebPage", items.get(0).getType());

        // Check for the bread crumb, it's a nested item and must not contain the item value from the attribute
        //assertEquals("Apache Tika Microdata", items.get(0).getProperties().get("breadcrumb").get(0).getValue().getContent());

System.out.println(items.get(0).getType());
//System.out.println(items.get(1).getProperties().get("description").get(0).getValue().getContent());
//System.out.println(items.get(1).getProperties().get("price").get(0).getValue().getContent());

for (ItemScope item: handler.getItems())
{
    metadata.add(mdRecipe.NAMESPACE_URI_MD , item.getType().toString());
    Map<String, List<ItemProp>> props = item.getProperties();

    class MdMetadata 
        extends Metadata 
        implements mdRecipe {
    }

    MdMetadata mdMd = new MdMetadata();
    try
    {
        for (Field f : mdMd.getClass().getFields())
        {
            if (f.getType().getName() == "org.apache.tika.metadata.Property")
            {
                Property p = (Property)f.get(mdMd);
                if (props.containsKey(p.getName().substring(3)))
                {
                    List<ItemProp> itemprops = props.get(p.getName().substring(3));
                    for (ItemProp i : itemprops)
                    {
                        metadata.add(p.getName().substring(3), i.getValue().getContent().toString());
                    }
                }
            }
        }
    }
    catch(Exception e)
    {
        System.out.println(e.getLocalizedMessage());
    }    
}

//        // Do we have a description field
//        assertNotNull(items.get(1).getProperties().get("description"));
//
//        // Check a price
//        assertEquals("17.50", items.get(1).getProperties().get("price").get(0).getValue().getContent());
//
//        // Check a date
//        assertNotNull(items.get(1).getProperties().get("startDate"));
//
//        // Check a property in in-body meta tag (bad practice)
//        assertEquals("EUR", items.get(1).getProperties().get("priceCurrency").get(0).getValue().getContent());
//
//        // Check if the content attribute is read
//        assertEquals("http://apachecon.eu/", items.get(1).getProperties().get("url").get(0).getValue().getContent());
//
//        // Check a nested field
//        assertEquals("Portland, Oregon", items.get(2).getProperties().get("location").get(0).getValue().getAsNested().getProperties().get("name").get(0).getValue().getContent());
    }
}
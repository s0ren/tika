/*
 * Copyright 2015 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.parser.html;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author sm
 */
public class HtmlRecipeHandler extends HtmlHandler{
       
    private final HtmlMapper mapper;

    private final XHTMLContentHandler xhtml;

    private final Metadata metadata;

    private int bodyLevel = 0;

    private int discardLevel = 0;

    private int titleLevel = 0;
    
    private int itemscopeLevel = 0;
    
    private boolean isTitleSetToMetadata = false; 
    
    private HtmlRecipeHandler(
            HtmlMapper mapper, XHTMLContentHandler xhtml, Metadata metadata) {
        super(mapper, xhtml, metadata);
        this.mapper = mapper;
        this.xhtml = xhtml;
        this.metadata = metadata;

       
    }

    public HtmlRecipeHandler(
            HtmlMapper mapper, ContentHandler handler, Metadata metadata) {
        this(mapper, new XHTMLContentHandler(handler, metadata), metadata);
    }

    @Override
    public void startElement(
            String uri, String local, String name, Attributes atts)
            throws SAXException 
    {
        if (atts.getIndex("itemscope") >= 0 && atts.getIndex("itemtype") >= 0
                && atts.getValue("itemtype").equalsIgnoreCase("http://schema.org/Recipe")  )
        {
            itemscopeLevel++;
        }
        
        if(atts.getIndex("itemprops") >= 0)
        {
            switch(atts.getValue("itemprops"))
            {
                case "image": break;
                case "recipeCategory": break;
                /*
                    name
                    recipeYield
                    prepTime
                    cookTime
                    totalTime
                    recipeInstructions
                    ingredients
                    thumbnailUrl //<meta se fakta
                    author
                    nutrition
                        proteinContent
                        carbohydrateContent
                        fatContent
                        calories
                */        
                    
            }
        }
        //TODO finish startElement code
        
        //TODO Add shit to metadata
           
        //FIXME Hvad mangler?
        
        
        
        super.startElement(uri, local, name, atts);
    }
    
    @Override
    public void endElement(
            String uri, String local, String name) throws SAXException 
    {
        if (bodyLevel > 0 && discardLevel == 0) 
        {
           //TODO finish endElement code 
            
        }
    }
}

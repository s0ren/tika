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
package org.apache.tika.metadata.microdata;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;


/**
 *
 * @author sm
 * @see http://schema.org/Thing
 */
public interface mdThing {
    
    public static final String NAMESPACE_URI_MD = "http://schema.org/Thing";
    public static final String PREFIX_MD = "md";
    
    /**
     * An additional type for the item, typically used for adding more specific 
     * types from external vocabularies in microdata syntax. 
     * This is a relationship between something and a class that the thing is in. 
     * In RDFa syntax, it is better to use the native RDFa syntax - the 'typeof' 
     * attribute - for multiple types. Schema.org tools may have only weaker
     * understanding of extra types, in particular those defined externally.
     * 
     */
    Property ADDITIONAL_TYPE = Property.internalText(
            PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "additionalType");
   
    /**
     * An alias for the item.
     */
    Property ALTERNATE_NAME = Property.internalText(
            PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "alternateName");
    /**
     * A short description of the item.
     */
    Property DESCRIPTION = Property.internalText(
            PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "description");
    
    /**
     * An image of the item. This can be a URL or a fully described ImageObject.
     */
    Property IMAGE = Property.internalText(
            PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "image");
   
    /**
     * Indicates a page (or other CreativeWork) for which this thing is the main
     * entity being described.      *
     * Many (but not all) pages have a fairly clear primary topic, some entity
     * or thing that the page describes. For example a restaurant's home page
     * might be primarily about that Restaurant, or an event listing page might
     * represent a single event. The mainEntity and mainEntityOfPage properties
     * allow you to explicitly express the relationship between the page and the
     * primary entity.      *
     * Related properties include sameAs, about, and url.      *
     * The sameAs and url properties are both similar to mainEntityOfPage. The
     * url property should be reserved to refer to more official or
     * authoritative web pages, such as the item’s official website. The sameAs
     * property also relates a thing to a page that indirectly identifies it.
     * Whereas sameAs emphasises well known pages, the mainEntityOfPage property
     * serves more to clarify which of several entities is the main one for that
     * page.      *
     * mainEntityOfPage can be used for any page, including those not recognized
     * as authoritative for that entity. For example, for a product, sameAs
     * might refer to a page on the manufacturer’s official site with specs for
     * the product, while mainEntityOfPage might be used on pages within various
     * retailers’ sites giving details for the same product.      *
     * about is similar to mainEntity, with two key differences. First, about
     * can refer to multiple entities/topics, while mainEntity should be used
     * for only the primary one. Second, some pages have a primary entity that
     * itself describes some other entity. For example, one web page may display
     * a news article about a particular person. Another page may display a
     * product review for a particular product. In these cases, mainEntity for
     * the pages should refer to the news article or review, respectively, while
     * about would more properly refer to the person or product. 
     * Inverse property: mainEntity.
     */ 
    Property MAIN_ENTITY_OF_PAGE = Property.internalTextBag(
             PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "mainEntityOfPage");
    
    /**
     * The name of the item.
     */
    Property NAME = Property.internalTextBag(
             PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "name");
    
    /**
     * Indicates a potential Action, which describes an idealized action in 
     * which this thing would play an 'object' role.
     */
    Property POTENTIAL_ACTION = Property.internalTextBag(
             PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "potentialAction");
    
    /**
     * URL of a reference Web page that unambiguously indicates the item's identity. 
     * E.g. the URL of the item's Wikipedia page, Freebase page, or official website.
     */
    Property SAME_AS = Property.internalTextBag(
             PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "sameAs");
    /**
     * URL of the item.
     */
    Property URL = Property.internalURI(
             PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "url");
}

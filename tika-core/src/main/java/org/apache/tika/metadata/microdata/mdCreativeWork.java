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
import static org.apache.tika.metadata.microdata.mdThing.PREFIX_MD;

/**
 *
 * @author sm
 * 
 * @see http://schema.org/CreativeWork
 */
public interface mdCreativeWork extends mdThing {
   
    public static final String NAMESPACE_URI_MD = "http://schema.org/CreativeWork";
    
    /**
     * The subject matter of the content.
     */
    Property ABOUT = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "about");

    /**
     * Indicates that the resource is compatible with the referenced 
     * accessibility API (WebSchemas wiki lists possible values).
     */
    Property ACCESSIBILITY_API = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "accessibilityAPI");
    
    /**
     * Identifies input methods that are sufficient to fully control the 
     * described resource (WebSchemas wiki lists possible values).
     */
    Property ACCESSIBILITY_CONTROL = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "accessibilityControl");
    
    /**
     * Content features of the resource, such as accessible media, alternatives 
     * and supported enhancements for accessibility 
     * (WebSchemas wiki lists possible values).
     */
    Property ACCESSIBILITY_FEATURE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "accessibilityFeature");
    
    /**
     * A characteristic of the described resource that is physiologically
     * dangerous to some users. Related to WCAG 2.0 guideline 2.3 (WebSchemas
     * wiki lists possible values).
     */
    Property ACCESSIBILITY_HAZARD = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "accessibilityHazard");
    
    /**
     * Specifies the Person that is legally accountable for the CreativeWork.
     */
    Property ACCOUNTABLE_PERSON = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "accountablePerson");
    
    /**
     * The overall rating, based on a collection of reviews or ratings, of the
     * item.
     */
    Property AGGREGATE_RATING = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "aggregateRating");
    
    /**
     * A secondary title of the CreativeWork.
     */
    Property ALTERNATIVE_HEADLINE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "alternativeHeadline");
   
    /**
     * A media object that encodes this CreativeWork. This property is a synonym
     * for encoding.
     */
    Property ASSOCIATED_MEDIA = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "associatedMedia");
    
    /**
     * An intended audience, i.e. a group for whom something was created.
     * Supersedes serviceAudience.
     */
    Property AUDIENCE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "audience");
    
    /**
     * An embedded audio object.
     */
    Property AUDIO = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "audio");
    
    /**
     * The author of this content. Please note that author is special in that
     * HTML 5 provides a special mechanism for indicating authorship via the rel
     * tag. That is equivalent to this and may be used interchangeably.
     */
    Property AUTHOR = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "author");
    
    /**
     * An award won by or for this item. Supersedes awards.
     */
    Property AWARD = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "award");
    
    /**
     * Fictional person connected with a creative work.
     */
    Property CHARACTER = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "character");
    
    /**
     * A citation or reference to another creative work, such as another
     * publication, web page, scholarly article, etc.
     */
    Property CITATION = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "citation");
   
    /**
     * Comments, typically from users.
     */
    Property COMMENT = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "comment");
   
    /**
     * The number of comments this CreativeWork (e.g. Article, Question or
     * Answer) has received. This is most applicable to works published in Web
     * sites with commenting system; additional comments may exist elsewhere.
     */
    Property COMMENT_COUNT = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "commentCount");
   
    /**
     * The location depicted or described in the content. For example, the
     * location in a photograph or painting.
     */
    Property CONTENT_LOCATION = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "contentLocation");
   
    /**
     * Official rating of a piece of content—for example,'MPAA PG-13'.
     */
    Property CONTENT_RATING = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "contentRating");
   
    /**
     * A secondary contributor to the CreativeWork.
     */
    Property CONTRIBUTOR = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "contributor");
   
    /**
     * The party holding the legal copyright to the CreativeWork.
     */
    Property COPYRIGHT_HOLDER = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "copyrightHolder");
   
    /**
     * The year during which the claimed copyright for the CreativeWork was
     * first asserted.
     */
    Property COPYRIGHT_YEAR = Property.internalInteger(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "copyrightYear");
   
    /**
     * The creator/author of this CreativeWork. 
     * This is the same as the Author property for CreativeWork.
     */
    Property CREATOR = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "creator");
   
    /**
     * The date on which the CreativeWork was created.
     */
    Property DATE_CREATED = Property.internalDate(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "dateCreated");
   
    /**
     * The date on which the CreativeWork was most recently modified.
     */
    Property DATE_MODIFIED = Property.internalDate(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "dateModified");
   
    /**
     * Date of first broadcast/publication.
     */
    Property DATE_PUBLISHED = Property.internalDate(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "datePublished");
   
    /**
     * A link to the page containing the comments of the CreativeWork
     */
    Property DISCUSSION_URL = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "discussionUrl");
   
    /**
     * Specifies the Person who edited the CreativeWork.
     */
    Property EDITOR = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "editor");
   
    /**
     * An alignment to an established educational framework.
     */
    Property EDUCATIONAL_ALIGNMENT = Property.internalText(                         //TODO AlignmentObject ?????
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "educationalAlignment");
   
    /**
     * The purpose of a work in the context of education; for example,
     * 'assignment', 'group work'.
     */
    Property EDUCATIONAL_USE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "educationalUse");
   
    /**
     * A media object that encodes this CreativeWork. This property is a synonym
     * for associatedMedia. Supersedes encodings.
     */
    Property ENCODING = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "encoding");
   
    /**
     * A creative work that this work is an
     * example/instance/realization/derivation of. Inverse property:
     * workExample.
     */
    Property EXAMPLE_OF_WORK = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "exampleOfWork");
   
    /**
     * Genre of the creative work or group.
     */
    Property GENRE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "genre");
   
    /**
     * Indicates a CreativeWork that is (in some sense) a part of this
     * CreativeWork. Inverse property: isPartOf.
     */
    Property HAS_PART = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "hasPart");
   
    /**
     * Headline of the article.
     */
    Property HEADLINE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "headline");
   
    /**
     * The language of the content or performance or used in an action. Please
     * use one of the language codes from the IETF BCP 47 standard. Supersedes
     * language.
     */
    Property IN_LANGUAGE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "inLanguage");
   
    /**
     * The predominant mode of learning supported by the learning resource.
     * Acceptable values are 'active', 'expositive', or 'mixed'.
     */
    Property INTERACTIVITY_TYPE = Property.internalText(                            //TODO Property.internalClosedChoise(PREFIX_MD, choices)
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "interactivityType");
   
    /**
     * A resource that was used in the creation of this resource. This term can
     * be repeated for multiple sources. For example,
     * http://example.com/great-multiplication-intro.html.
     */
    Property IS_BASED_ON_URL = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "isBasedOnUrl");
   
    /**
     * Indicates whether this content is family friendly.
     */
    Property IS_FAMILY_FRIENDLY = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "isFamilyFriendly");
   
    /**
     * Indicates a CreativeWork that this CreativeWork is (in some sense) part
     * of. Inverse property: hasPart.
     */
    Property IS_PART_OF = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "isPartOf");
   
    /**
     * Keywords or tags used to describe this content. Multiple entries in a
     * keywords list are typically delimited by commas.
     */
    Property KEYWORDS = Property.internalTextBag(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "keywords");
   
    /**
     * The predominant type or kind characterizing the learning resource. For
     * example, 'presentation', 'handout'.
     */
    Property LEARNING_RESOURCE_TYPE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "learningResourceType");
   
    /**
     * A license document that applies to this content, typically indicated by
     * URL.
     */
    Property LICENSE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "license");
   
    /**
     * Indicates the primary entity described in some page or other
     * CreativeWork. Inverse property: mainEntityOfPage.
     */
    Property MAIN_ENTITY = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "mainEntity");
   
    /**
     * Indicates that the CreativeWork contains a reference to, but is not
     * necessarily about a concept.
     */
    Property MENTIONS = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "mentions");
   
    /**
     * An offer to provide this item—for example, an offer to sell a product,
     * rent the DVD of a movie, or give away tickets to an event.
     */
    Property OFFERS = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "offers");
   
    Property POSITION = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "position");
   
    /**
     * The person or organization who produced the work (e.g. music album,
     * movie, tv/radio series etc.).
     */
    Property PRODUCER = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "producer");
   
    /**
     * The service provider, service operator, or service performer; the goods
     * producer. Another party (a seller) may offer those services or goods on
     * behalf of the provider. A provider may also serve as the seller.
     * Supersedes carrier.
     */
    Property PROVIDER = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "provider");
   
    /**
     * A publication event associated with the item.
     */
    Property PUBLICATION = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "publication");
   
    /**
     * The publisher of the creative work.
     */
    Property PUBLISHER = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "publisher");
   
    /**
     * Link to page describing the editorial principles of the organization
     * primarily responsible for the creation of the CreativeWork.
     */
    Property PUBLISHING_PRINCIPLES = Property.internalText(                         //TODO Url?
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "publishingPrinciples");
   
    /**
     * The Event where the CreativeWork was recorded. The CreativeWork may
     * capture all or part of the event. Inverse property: recordedIn.
     */
    Property RECORDED_AT = Property.internalText(                                   // TODO Event??
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "recordedAt");
   
    /**
     * The place and time the release was issued, expressed as a
     * PublicationEvent.
     */
    Property RELEASED_EVENT = Property.internalText(                                //TODO PublicationEvent
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "releasedEvent");
   
    /**
     * A review of the item. Supersedes reviews.
     */
    Property REVIEW = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "review");
   
    /**
     * Indicates (by URL or string) a particular version of a schema used in
     * some CreativeWork. For example, a document could declare a schemaVersion
     * using an URL such as http://schema.org/version/2.0/ if precise indication
     * of schema version was required by some application.
     */
    Property ADDITIONAL_TYPE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "schemaVersion");
   
    /**
     * The Organization on whose behalf the creator was working.
     */
    Property SOURCE_ORGANIZATION = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "sourceOrganization");
   
    /**
     * The textual content of this CreativeWork.
     */
    Property TEXT = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "text");
   
    /**
     * A thumbnail image relevant to the Thing.
     */
    Property THUMBNAIL_URL = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "thumbnailUrl");
   
    /**
     * Approximate or typical time it takes to work with or through this
     * learning resource for the typical intended target audience, e.g. 'P30M',
     * 'P1H25M'.
     */
    Property TIME_REQUIRED = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "timeRequired");
   
    /**
     * Organization or person who adapts a creative work to different languages,
     * regional differences and technical requirements of a target market.
     */
    Property TRANSLATOR = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "translator");
   
    Property TYPICAL_AGE_RANGE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "typicalAgeRange");
   
    /**
     * The version of the CreativeWork embodied by a specified resource.
     */
    Property VERSION = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "version");
   
    /**
     * An embedded video object.
     */
    Property VIDEO = Property.internalText(                                             //TODO VideoObject
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "video");
   
    /**
     * Example/instance/realization/derivation of the concept of this creative
     * work. eg. The paperback edition, first edition, or eBook. Inverse
     * property: exampleOfWork.
     */
    Property WORK_EXAMPLE = Property.internalText(
        PREFIX_MD + Metadata.NAMESPACE_PREFIX_DELIMITER + "workExample");
    
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tika.parser.html.microdata;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.time.DateUtils;

/**
 * Describes a possible value for a <b>Microdata item property</b>.
 *
 * @author Michele Mostarda (mostarda@fbk.eu)
 */
public class ItemPropValue {

    /**
     * ISO-8601 datetime format as specified by schema.org
     * @see http://schema.org/Date
     */
    private static final SimpleDateFormat iso8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

    /**
     * ISO-8601 datetime formats
     */
    private static final String[] iso8601Formats = new String [] {
        "yyyy-MM-dd",
        "yyyy-MM-dd'T'HH:mm:ss'Z'"
    };

    /**
     * Supported types.
     */
    public enum Type {
        Plain,
        Link,
        Date,
        Nested
    }

    /**
     * Attempt to parse the specified date string
     *
     * @param String
     * @return Date
     */
    public static Date parseDateTime(String dateStr) throws Exception {
        // Is this a Duration?
        if (dateStr.charAt(0) == 'p' || dateStr.charAt(0) == 'P') {
          // Cannot deal with durations...
          throw new Exception("Unable to handle ISO-8601 durations");
        }

        // Attempt to parse the date string as ISO-8601 as specified by schema.org
        return DateUtils.parseDate(dateStr, iso8601Formats);
    }

    /**
     * Format the given date object as ISO-8601 date string
     *
     * @param Date
     * @return String
     */
    public static String formatDateTime(Date in) {
        return iso8601format.format(in);
    }

    /**
     * Internal content value.
     */
    private final Object content;

    /**
     * Content type.
     */
    private final Type type;

    /**
     * Constructor.
     *
     * @param content content object.
     * @param type content type.
     */
    public ItemPropValue(Object content, Type type) {
        if(content == null) {
            throw new NullPointerException("content cannot be null.");
        }
        if(type == null) {
            throw new NullPointerException("type cannot be null.");
        }
        if(type == Type.Nested && ! (content instanceof ItemScope) ) {
            throw new IllegalArgumentException(
                    "content must be an " + ItemScope.class + " when type is " + Type.Nested
            );
        }
        if(type == Type.Date && !(content instanceof Date) ) {
            throw new IllegalArgumentException(
                    "content '" + content + "' must be a " + Date.class.getName() + " when type is " + Type.Date
            );
        }
        if(content instanceof String && ((String) content).trim().length() == 0) {
            throw new IllegalArgumentException("Invalid content '" + content + "'");
        }

        this.content = content;
        this.type = type;
    }

    /**
     * @return the content object.
     */
    public Object getContent() {
        return content;
    }

    /**
     * @return the content type.
     */
    public Type getType() {
        return type;
    }

   /**
     * @return <code>true</code> if type is plain text.
     */
    public boolean isPlain() {
        return type == Type.Plain;
    }

    /**
     * @return <code>true</code> if type is a link.
     */
    public boolean isLink() {
        return type == Type.Link;
    }

    /**
     * @return <code>true</code> if type is a date.
     */
    public boolean isDate() {
        return type == Type.Date;
    }

    /**
     * @return <code>true</code> if type is a nested {@link ItemScope}.
     */
    public boolean isNested() {
        return type == Type.Nested;
    }

    /**
     * @return <code>true</code> if type is an integer.
     */
    public boolean isInteger() {
        if(type != Type.Plain) return false;
         try {
             Integer.parseInt((String) content);
             return true;
         } catch (Exception e) {
             return false;
         }
     }

    /**
     * @return <code>true</code> if type is a float.
     */
     public boolean isFloat() {
         if(type != Type.Plain) return false;
         try {
             Float.parseFloat((String) content);
             return true;
         } catch (Exception e) {
             return false;
         }
     }

    /**
     * @return <code>true</code> if type is a number.
     */
     public boolean isNumber() {
         return isInteger() || isFloat();
     }

    /**
     * @return the content value as integer, or raises an exception.
     * @throws NumberFormatException if the content is not an integer.
     * @throws ClassCastException if content is not plain.
     */
     public int getAsInteger() {
         return Integer.parseInt((String) content);
     }

    /**
     * @return the content value as float, or raises an exception.
     * @throws NumberFormatException if the content is not an float.
     * @throws ClassCastException if content is not plain.
     */
     public float getAsFloat() {
         return Float.parseFloat((String) content);
     }


    /**
     * @return the content as {@link Date}
     *         if <code>type == Type.DateTime</code>,
     * @throws ClassCastException if content is not a valid date.
     */
    public Date getAsDate() {
        return (Date) content;
    }

    /**
     * @return the content value as URL, or raises an exception.
     * @throws MalformedURLException if the content is not a valid URL.
     * @throws ClassCastException if content is not a link.
     */
    public URL getAsLink() {
        try {
            return new URL((String) content);
        } catch (MalformedURLException murle) {
            throw new IllegalStateException("Error while parsing URI.", murle);
        }
    }

    /**
     * @return the content value as {@link ItemScope}.
     * @throws ClassCastException if the content is not a valid nested item.
     */
    public ItemScope getAsNested() {
        return (ItemScope) content;
    }

    public String toJSON() {
        String contentStr;
        if(content instanceof String) {
            contentStr = (String)content;
            contentStr = contentStr.replaceAll("\\\\", "\\\\\\\\\"");
            contentStr = "\"" + escapeAsJSONString(contentStr) + "\"";
        } else if(content instanceof Date) {
            contentStr = "\"" + iso8601format.format((Date) content) + "\"";
        } else {
            contentStr = content.toString();
        }

        return String.format(Locale.ENGLISH, "{ \"content\" : %s, \"type\" : \"%s\" }", contentStr, type );
    }

    @Override
    public String toString() {
        return toJSON();
    }

    @Override
    public int hashCode() {
        return content.hashCode() * type.hashCode() * 2;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(obj instanceof ItemPropValue) {
            final ItemPropValue other = (ItemPropValue) obj;
            return content.equals(other.content) && type.equals(other.type);
        }
        return false;
    }

    /**
     * Escapes all the unescaped double quotes when needed.
     *
     * @param in input string.
     * @return unescaped output.
     */
    public static String escapeDoubleQuotes(String in) {
        final StringBuilder out = new StringBuilder();
        boolean escaped = false;
        char current;
        for(int i = 0; i < in.length(); i++) {
            current = in.charAt(i);
            if(current == '\\') {
              escaped = !escaped;
            } else if(current == '"' && !escaped) {
              out.append('\\');
            }
            out.append(current);
        }
        return out.toString();
    }

    /**
     * Escapes the <code>in</code> string as <b>JSON</b> string
     * to let it being embeddable within a string field.
     *
     * @param in string to be escaped.
     * @return escaped string.
     */
    public static String escapeAsJSONString(String in) {
        return escapeDoubleQuotes( in.replaceAll("\n", "\\\\n") );
    }
}


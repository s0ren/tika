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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class describes a <b>Microdata <i>itemscope</i></b>.
 *
 * @author Michele Mostarda (mostarda@fbk.eu)
 */
public class ItemScope {

    /**
     * Map of properties and multi values.
     */
    private Map<String, List<ItemProp>> properties = new HashMap<String, List<ItemProp>>();

    /**
     * <i>itemscope</i> references.
     */
    private String[] refs;

    /**
     * <i>itemscope</i> type.
     */
    private URL type;
    
    public ItemScope() {}

    /**
     * Constructor.
     *
     * @param itemProps list of properties bound to this <i>itemscope</i>.
     * @param refs      list of item prop references connected to this <i>itemscope</i>. Can be <code>null<code>.
     * @param type      <i>itemscope</i> type. Can be <code>null<code>.
     */
    public ItemScope(ItemProp[] itemProps, String[] refs, String type) {
        if (itemProps == null) {
            throw new NullPointerException("itemProps list cannot be null.");
        }
        if (type != null) {
            try {
                this.type = new URL(type);
            } catch (MalformedURLException murle) {
                throw new IllegalArgumentException("Invalid type '" + type + "', must be a valid URL.");
            }
        } else {
            this.type = null;
        }

        this.refs = refs;

        final Map<String, List<ItemProp>> tmpProperties = new HashMap<String, List<ItemProp>>();
        for (ItemProp itemProp : itemProps) {
            final String propName = itemProp.getName();
            List<ItemProp> propList = tmpProperties.get(propName);
            if (propList == null) {
                propList = new ArrayList<ItemProp>();
                tmpProperties.put(propName, propList);
            }
            propList.add(itemProp);
        }
        final Map<String, List<ItemProp>> properties = new HashMap<String, List<ItemProp>>();
        for (Map.Entry<String, List<ItemProp>> propertiesEntry : tmpProperties.entrySet()) {
            properties.put(
                    propertiesEntry.getKey(),
                    //Collections.unmodifiableList( propertiesEntry.getValue() )
                    propertiesEntry.getValue()
            );
        }
        // this.properties = Collections.unmodifiableMap(properties);
        this.properties = properties;
    }

    /**
     * @return map of declared properties, every property can have more than a value.
     */
    public Map<String, List<ItemProp>> getProperties() {
        return properties;
    }

    /**
     * @return <i>itemscope</i> list of references to <i>itemprop</i>s.
     */
    public String[] getRefs() {
        return refs;
    }

    /**
     * @return <i>itemscope</i> type.
     */
    public URL getType() {
        return type;
    }
    
    /**
     *
     */
    public void setType(URL type) {
        this.type = type;
    }
    
    /**
     *
     */
    public void setType(String type) {
        try {
            this.type = new URL(type);
        } catch (MalformedURLException e) {}         
    }
    
    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        int i, j;
        final Collection<List<ItemProp>> itemPropsList = properties.values();
        j = 0;
        for (List<ItemProp> itemProps : itemPropsList) {
            i = 0;
            for (ItemProp itemProp : itemProps) {
                sb.append(itemProp);
                if (i < itemProps.size() - 1) {
                    sb.append(", ");
                }
                i++;
            }
            if (j < itemPropsList.size() - 1) {
                sb.append(", ");
            }
            j++;
        }
        return String.format(
                Locale.ENGLISH,
                "{ " +
                        "\"refs\" : %s, \"type\" : %s, \"properties\" : [ %s ]" +
                        " }",
                refs == null ? null : toJSON(refs),
                type == null ? null : "\"" + type + "\"",
                sb.toString()
        );
    }

    @Override
    public String toString() {
        return toJSON();
    }

    @Override
    public int hashCode() {
            return
                (properties == null ? 1 : properties.hashCode()) *
                (refs == null       ? 1 : refs.hashCode()) * 3 *
                (type == null       ? 1 : type.hashCode()) * 5;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof ItemScope) {
            final ItemScope other = (ItemScope) obj;
                return
                        (properties == null ? other.properties == null : properties.equals(other.properties))
                            &&
                        (refs == null ? other.refs == null : Arrays.equals(refs, other.refs))
                            &&
                        (type == null ? other.type == null : type.equals(other.type));
        }
        return false;
    }

    public void acquireProperty(ItemProp itemProp) {
        List<ItemProp> itemProps = properties.get(itemProp.getName());
        if (itemProps == null) {
            itemProps = new ArrayList<ItemProp>();
            properties.put(itemProp.getName(), itemProps);
        }
        if (!itemProps.contains(itemProp)) itemProps.add(itemProp);
    }

    public void disownProperty(ItemProp itemProp) {
        List<ItemProp> propList = properties.get(itemProp.getName());
        if (propList != null) propList.remove(itemProp);
    }

    private String toJSON(String[] in) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < in.length; i++) {
            sb.append("\"");
            sb.append(in[i]);
            sb.append("\"");
            if (i < in.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

}
 

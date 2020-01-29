/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.AttributeTypeDef;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.AttributeTypeDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeMappingStore {

    private static final Logger log = LoggerFactory.getLogger(AttributeMappingStore.class);

    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;

    private Map<String, AttributeMapping> omrsGuidToMapping;
    private Map<String, AttributeTypeDef> omrsGuidToAttributeTypeDef;
    private Map<String, String> omrsNameToGuid;
    private Map<String, List<String>> omrsTypeDefCategoryToGuids;

    private Map<String, AttributeTypeDef> unimplementedAttributeTypeDefs;

    public AttributeMappingStore(IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        omrsGuidToMapping = new HashMap<>();
        omrsGuidToAttributeTypeDef = new HashMap<>();
        omrsNameToGuid = new HashMap<>();
        omrsTypeDefCategoryToGuids = new HashMap<>();
        unimplementedAttributeTypeDefs = new HashMap<>();
        this.igcomrsRepositoryConnector = igcomrsRepositoryConnector;
    }

    /**
     * Adds an attribute mapping for the provided AttributeTypeDef, when it is a simple-mapped type (ie. either a
     * collection or a primitive).
     *
     * @param omrsTypeDef the OMRS AttributeTypeDef
     * @return boolean false when unable to add the mapping (ie. it is not a collection or primitive AttributeTypeDef)
     */
    public boolean addMapping(AttributeTypeDef omrsTypeDef) {

        AttributeTypeDefCategory category = omrsTypeDef.getCategory();
        if (category.equals(AttributeTypeDefCategory.COLLECTION) || category.equals(AttributeTypeDefCategory.PRIMITIVE)) {
            String guid = omrsTypeDef.getGUID();
            omrsGuidToAttributeTypeDef.put(guid, omrsTypeDef);
            omrsNameToGuid.put(omrsTypeDef.getName(), guid);
            addGuidToCategory(category.getName(), guid);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Adds an attribute mapping for the provided AttributeTypeDef, using the provided Java class for the mapping.
     *
     * @param omrsTypeDef the OMRS AttributeTypeDef
     * @param mappingClass the AttributeMapping Java class
     * @return boolean false when unable to retrieve AttributeMapping from provided class
     */
    public boolean addMapping(AttributeTypeDef omrsTypeDef, Class<?> mappingClass) {

        AttributeMapping mapping = getAttributeMapper(mappingClass);

        if (mapping != null) {
            String guid = omrsTypeDef.getGUID();
            omrsGuidToAttributeTypeDef.put(guid, omrsTypeDef);
            omrsGuidToMapping.put(guid, mapping);
            omrsNameToGuid.put(omrsTypeDef.getName(), guid);
            addGuidToCategory(omrsTypeDef.getCategory().getName(), guid);
        }

        return (mapping != null);

    }

    /**
     * Adds the provided AttributeTypeDef to the list of those that are not implemented in the repository.
     *
     * @param attributeTypeDef an unimplemented attribute type definition
     */
    public void addUnimplementedAttributeTypeDef(AttributeTypeDef attributeTypeDef) {
        String guid = attributeTypeDef.getGUID();
        unimplementedAttributeTypeDefs.put(guid, attributeTypeDef);
    }

    /**
     * Adds a mapping between AttributeTypeDef category and GUID of the OMRS AttributeTypeDef.
     *
     * @param categoryName the name of the OMRS AttributeTypeDefCategory
     * @param guid of the OMRS TypeDef
     */
    private void addGuidToCategory(String categoryName, String guid) {
        if (!omrsTypeDefCategoryToGuids.containsKey(categoryName)) {
            omrsTypeDefCategoryToGuids.put(categoryName, new ArrayList<>());
        }
        omrsTypeDefCategoryToGuids.get(categoryName).add(guid);
    }

    /**
     * Retrieves an unimplemented AttributeTypeDef by its GUID.
     *
     * @param guid of the type definition
     * @return AttributeTypeDef
     */
    public AttributeTypeDef getUnimplementedAttributeTypeDefByGUID(String guid) {
        return getUnimplementedAttributeTypeDefByGUID(guid, true);
    }

    /**
     * Retrieves an unimplemented AttributeTypeDef by its GUID.
     *
     * @param guid of the attribute type definition
     * @param logOnNotFound whether to log or not if not found
     * @return AttributeTypeDef
     */
    public AttributeTypeDef getUnimplementedAttributeTypeDefByGUID(String guid, boolean logOnNotFound) {
        if (unimplementedAttributeTypeDefs.containsKey(guid)) {
            return unimplementedAttributeTypeDefs.get(guid);
        } else {
            if (logOnNotFound) { log.warn("Unable to find unimplemented OMRS AttributeTypeDef: {}", guid); }
            return null;
        }
    }

    /**
     * Retrieves an implemented AttributeTypeDef by its GUID.
     *
     * @param guid of the attribute type definition
     * @return TypeDef
     */
    public AttributeTypeDef getAttributeTypeDefByGUID(String guid) {
        return getAttributeTypeDefByGUID(guid, true);
    }

    /**
     * Retrieves an implemented AttributeTypeDef by its GUID.
     *
     * @param guid of the attribute type definition
     * @param logOnNotFound whether to log or not if not found
     * @return TypeDef
     */
    public AttributeTypeDef getAttributeTypeDefByGUID(String guid, boolean logOnNotFound) {
        if (omrsGuidToAttributeTypeDef.containsKey(guid)) {
            return omrsGuidToAttributeTypeDef.get(guid);
        } else {
            if (logOnNotFound) { log.warn("Unable to find OMRS AttributeTypeDef: {}", guid); }
            return null;
        }
    }

    /**
     * Retrieves an implemented AttributeTypeDef by its name.
     *
     * @param name of the attribute type definition
     * @return AttributeTypeDef
     */
    public AttributeTypeDef getAttributeTypeDefByName(String name) {
        if (omrsNameToGuid.containsKey(name)) {
            String guid = omrsNameToGuid.get(name);
            return getAttributeTypeDefByGUID(guid);
        } else {
            log.warn("Unable to find OMRS AttributeTypeDef: {}", name);
            return null;
        }
    }

    /**
     * Retrieves a listing of all AttributeTypeDefs of the provided category.
     *
     * @param category of the AttributeTypeDef
     * @return {@code List<AttributeTypeDef>}
     */
    public List<AttributeTypeDef> getAttributeTypeDefsByCategory(AttributeTypeDefCategory category) {
        String categoryName = category.getName();
        List<AttributeTypeDef> results = new ArrayList<>();
        if (omrsTypeDefCategoryToGuids.containsKey(categoryName)) {
            for (String guid : omrsTypeDefCategoryToGuids.get(categoryName)) {
                results.add(getAttributeTypeDefByGUID(guid));
            }
        } else {
            log.warn("Unable to find OMRS AttributeTypeDefCategory: {}", categoryName);
        }
        return results;
    }

    /**
     * Retrieves a listing of all AttributeTypeDefs implemented for this repository.
     *
     * @return {@code List<AttributeTypeDef>}
     */
    public List<AttributeTypeDef> getAllAttributeTypeDefs() {
        return new ArrayList<>(omrsGuidToAttributeTypeDef.values());
    }

    /**
     * Introspect a mapping class to retrieve an AttributeMapping.
     *
     * @param mappingClass the mapping class to retrieve an instance of
     * @return RelationshipMapping
     */
    private AttributeMapping getAttributeMapper(Class<?> mappingClass) {
        AttributeMapping attributeMapper = null;
        try {
            Method getInstance = mappingClass.getMethod("getInstance", IGCVersionEnum.class);
            attributeMapper = (AttributeMapping) getInstance.invoke(null, igcomrsRepositoryConnector.getIGCVersion());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("Unable to find or instantiate AttributeMapping class: {}", mappingClass, e);
        }
        return attributeMapper;
    }

}

/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.base;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ItemList;

/**
 * POJO for the {@code inferred_foreign_key} asset type in IGC, displayed as '{@literal Inferred Foreign Key}' in the IGC UI.
 * <br><br>
 * (this code has been created based on out-of-the-box IGC metadata types.
 *  If modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="_type", visible=true, defaultImpl=InferredForeignKey.class)
@JsonAutoDetect(getterVisibility=PUBLIC_ONLY, setterVisibility=PUBLIC_ONLY, fieldVisibility=NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("inferred_foreign_key")
public class InferredForeignKey extends Reference {

    @JsonProperty("long_description")
    protected String longDescription;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("native_id")
    protected String nativeId;

    @JsonProperty("references_inferred_keys")
    protected InferredKey referencesInferredKeys;

    @JsonProperty("short_description")
    protected String shortDescription;

    @JsonProperty("table_analysis")
    protected MainObject tableAnalysis;

    @JsonProperty("total_records")
    protected Number totalRecords;

    @JsonProperty("uses_database_fields")
    protected ItemList<DatabaseColumn> usesDatabaseFields;

    /**
     * Retrieve the {@code long_description} property (displayed as '{@literal Long Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("long_description")
    public String getLongDescription() { return this.longDescription; }

    /**
     * Set the {@code long_description} property (displayed as {@code Long Description}) of the object.
     * @param longDescription the value to set
     */
    @JsonProperty("long_description")
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    /**
     * Retrieve the {@code name} property (displayed as '{@literal Name}') of the object.
     * @return {@code String}
     */
    @JsonProperty("name")
    public String getTheName() { return this.name; }

    /**
     * Set the {@code name} property (displayed as {@code Name}) of the object.
     * @param name the value to set
     */
    @JsonProperty("name")
    public void setTheName(String name) { this.name = name; }

    /**
     * Retrieve the {@code native_id} property (displayed as '{@literal Native ID}') of the object.
     * @return {@code String}
     */
    @JsonProperty("native_id")
    public String getNativeId() { return this.nativeId; }

    /**
     * Set the {@code native_id} property (displayed as {@code Native ID}) of the object.
     * @param nativeId the value to set
     */
    @JsonProperty("native_id")
    public void setNativeId(String nativeId) { this.nativeId = nativeId; }

    /**
     * Retrieve the {@code references_inferred_keys} property (displayed as '{@literal References Inferred Keys}') of the object.
     * @return {@code InferredKey}
     */
    @JsonProperty("references_inferred_keys")
    public InferredKey getReferencesInferredKeys() { return this.referencesInferredKeys; }

    /**
     * Set the {@code references_inferred_keys} property (displayed as {@code References Inferred Keys}) of the object.
     * @param referencesInferredKeys the value to set
     */
    @JsonProperty("references_inferred_keys")
    public void setReferencesInferredKeys(InferredKey referencesInferredKeys) { this.referencesInferredKeys = referencesInferredKeys; }

    /**
     * Retrieve the {@code short_description} property (displayed as '{@literal Short Description}') of the object.
     * @return {@code String}
     */
    @JsonProperty("short_description")
    public String getShortDescription() { return this.shortDescription; }

    /**
     * Set the {@code short_description} property (displayed as {@code Short Description}) of the object.
     * @param shortDescription the value to set
     */
    @JsonProperty("short_description")
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    /**
     * Retrieve the {@code table_analysis} property (displayed as '{@literal Table Analysis}') of the object.
     * @return {@code MainObject}
     */
    @JsonProperty("table_analysis")
    public MainObject getTableAnalysis() { return this.tableAnalysis; }

    /**
     * Set the {@code table_analysis} property (displayed as {@code Table Analysis}) of the object.
     * @param tableAnalysis the value to set
     */
    @JsonProperty("table_analysis")
    public void setTableAnalysis(MainObject tableAnalysis) { this.tableAnalysis = tableAnalysis; }

    /**
     * Retrieve the {@code total_records} property (displayed as '{@literal Total Records}') of the object.
     * @return {@code Number}
     */
    @JsonProperty("total_records")
    public Number getTotalRecords() { return this.totalRecords; }

    /**
     * Set the {@code total_records} property (displayed as {@code Total Records}) of the object.
     * @param totalRecords the value to set
     */
    @JsonProperty("total_records")
    public void setTotalRecords(Number totalRecords) { this.totalRecords = totalRecords; }

    /**
     * Retrieve the {@code uses_database_fields} property (displayed as '{@literal Uses Database Fields}') of the object.
     * @return {@code ItemList<DatabaseColumn>}
     */
    @JsonProperty("uses_database_fields")
    public ItemList<DatabaseColumn> getUsesDatabaseFields() { return this.usesDatabaseFields; }

    /**
     * Set the {@code uses_database_fields} property (displayed as {@code Uses Database Fields}) of the object.
     * @param usesDatabaseFields the value to set
     */
    @JsonProperty("uses_database_fields")
    public void setUsesDatabaseFields(ItemList<DatabaseColumn> usesDatabaseFields) { this.usesDatabaseFields = usesDatabaseFields; }

}

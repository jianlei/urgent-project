/*
 * Copyright 2012 Igor Vaynberg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.daren.core.web.component.select2;

import com.daren.core.web.component.json.Json;
import org.json.JSONException;
import org.json.JSONWriter;

import java.io.Serializable;



/**
 * Select2 Ajax settings. Refer to the Select2 documentation for what these options mean.
 * 
 * @author igor
 */
public final class AjaxSettings implements Serializable {
    private CharSequence url;
    private String dataType = "json";
    private int quietMillis = 100;
    private String data;
    private String results;
    /** whether or not to use traditional parameter encoding. */
    private Boolean traditional;

    void toJson(JSONWriter writer) throws JSONException {
	writer.object();
	Json.writeFunction(writer, "data", data);
	Json.writeObject(writer, "dataType", dataType);
	Json.writeObject(writer, "quietMillis", quietMillis);
	Json.writeFunction(writer, "results", results);
	Json.writeObject(writer, "url", url);
	Json.writeObject(writer, "traditional", traditional);
	writer.endObject();
    }

    public CharSequence getUrl() {
	return url;
    }

    public void setUrl(CharSequence url) {
	this.url = url;
    }

    public String getDataType() {
	return dataType;
    }

    public void setDataType(String dataType) {
	this.dataType = dataType;
    }

    public int getQuietMillis() {
	return quietMillis;
    }

    public void setQuietMillis(int quietMillis) {
	this.quietMillis = quietMillis;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    public String getResults() {
	return results;
    }

    public void setResults(String results) {
	this.results = results;
    }

    public boolean isTraditional() {
        return traditional;
    }

    public void setTraditional(boolean traditional) {
        this.traditional = traditional;
    }
    
}

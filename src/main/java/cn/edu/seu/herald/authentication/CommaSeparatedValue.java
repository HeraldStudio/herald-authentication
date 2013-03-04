/*
 * Copyright 2013 Herald Studio, Southeast University.
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
package cn.edu.seu.herald.authentication;

/**
 *
 * @author rAy <predator.ray@gmail.com>
 */
public class CommaSeparatedValue {

    private static final String COMMA = ",";
    private static final String ESCAPED_COMMA = "\\,";
    private StringBuilder builder;

    public CommaSeparatedValue append(String value) {
        if (builder.length() > 0) {
            builder.append(COMMA);
        }
        String escapeStr = value.replace(COMMA, ESCAPED_COMMA);
        builder.append(escapeStr);
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
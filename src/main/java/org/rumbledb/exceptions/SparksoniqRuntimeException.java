/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Stefan Irimescu, Can Berker Cikis
 *
 */

package org.rumbledb.exceptions;

import org.rumbledb.errorcodes.ErrorCode;

import java.util.Arrays;

public class SparksoniqRuntimeException extends RuntimeException {


    private static final long serialVersionUID = 1L;
    private final ErrorCode errorCode;
    private final String errorMessage;
    private ExceptionMetadata metadata;

    public SparksoniqRuntimeException(String message) {
        super("Error [err: " + ErrorCode.RuntimeExceptionErrorCode + " ] " + message);
        this.errorCode = ErrorCode.RuntimeExceptionErrorCode;
        this.errorMessage = message;
    }

    public SparksoniqRuntimeException(String message, ErrorCode errorCode) {
        super("Error [err: " + errorCode + " ] " + message);
        if (!Arrays.asList(ErrorCode.class.getFields()).stream().anyMatch(f -> {
            try {
                return f.get(null).equals(errorCode);
            } catch (IllegalAccessException e) {
                return true;
            }
        })) {
            this.errorCode = ErrorCode.RuntimeExceptionErrorCode;
        } else {
            this.errorCode = errorCode;
        }
        this.errorMessage = message;
    }


    public SparksoniqRuntimeException(String message, ErrorCode errorCode, ExceptionMetadata metadata) {
        super(
            "Error [err: "
                + errorCode
                + "]"
                + (metadata != null
                    ? "LINE:"
                        + metadata.getTokenLineNumber()
                        +
                        ":COLUMN:"
                        + metadata.getTokenColumnNumber()
                        + ":"
                    : "")
                + message
        );
        if (!Arrays.asList(ErrorCode.class.getFields()).stream().anyMatch(f -> {
            try {
                return f.get(null).equals(errorCode);
            } catch (IllegalAccessException e) {
                return true;
            }
        })) {
            this.errorCode = ErrorCode.RuntimeExceptionErrorCode;
        } else {
            this.errorCode = errorCode;
        }
        this.metadata = metadata;
        this.errorMessage = message;
    }

    public SparksoniqRuntimeException(String message, ExceptionMetadata metadata) {
        super(
            "Error [err: "
                + ErrorCode.RuntimeExceptionErrorCode
                + "]"
                + (metadata != null
                    ? "LINE:"
                        + metadata.getTokenLineNumber()
                        +
                        ";COLUMN:"
                        + metadata.getTokenColumnNumber()
                        + ";"
                    : "")
                + message
        );
        this.errorCode = ErrorCode.RuntimeExceptionErrorCode;;
        this.metadata = metadata;
        this.errorMessage = message;
    }

    public String getErrorCode() {
        return this.errorCode.toString();
    }

    public ExceptionMetadata getMetadata() {
        return this.metadata;
    }

    public String getJSONiqErrorMessage() {
        return this.errorMessage;
    }
}

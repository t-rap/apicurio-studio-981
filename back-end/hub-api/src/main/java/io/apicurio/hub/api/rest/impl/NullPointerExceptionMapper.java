/*
 * Copyright 2020 Red Hat
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

package io.apicurio.hub.api.rest.impl;

import java.io.PrintWriter;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.output.StringBuilderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.apicurio.hub.api.beans.ApiError;

/**
 * @author eric.wittmann@gmail.com
 */
@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {
    
    private static Logger logger = LoggerFactory.getLogger(NullPointerExceptionMapper.class);
    
    /**
     * Constructor.
     */
    public NullPointerExceptionMapper() {
    }
    
    /**
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(NullPointerException data) {
        logger.error("Unexpected NPE detected.", data);
        ApiError error = new ApiError();
        error.setErrorType(data.getClass().getSimpleName());
        error.setMessage(data.getMessage());
        error.setTrace(getStackTrace(data));
        ResponseBuilder builder = Response.status(500).header("X-API-Error", "true"); //$NON-NLS-1$ //$NON-NLS-2$
        builder.type(MediaType.APPLICATION_JSON_TYPE);
        return builder.entity(error).build();
    }

    /**
     * Gets the full stack trace for the given exception and returns it as a
     * string.
     * @param data
     */
    private String getStackTrace(NullPointerException data) {
        try (StringBuilderWriter writer = new StringBuilderWriter()) {
            data.printStackTrace(new PrintWriter(writer));
            return writer.getBuilder().toString();
        }
    }
}
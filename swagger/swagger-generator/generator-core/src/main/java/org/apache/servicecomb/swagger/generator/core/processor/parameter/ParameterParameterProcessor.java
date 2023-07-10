/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicecomb.swagger.generator.core.processor.parameter;

import java.lang.reflect.Type;

import org.apache.servicecomb.swagger.SwaggerUtils;
import org.apache.servicecomb.swagger.generator.ParameterProcessor;
import org.apache.servicecomb.swagger.generator.core.model.HttpParameterType;

import com.fasterxml.jackson.databind.JavaType;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;

public class ParameterParameterProcessor implements ParameterProcessor<io.swagger.v3.oas.annotations.Parameter> {
  @Override
  public Class<?> getProcessType() {
    return io.swagger.v3.oas.annotations.Parameter.class;
  }

  @Override
  public Type getGenericType(io.swagger.v3.oas.annotations.Parameter parameterAnnotation) {
    if (parameterAnnotation.schema() == null) {
      return null;
    }
    return parameterAnnotation.schema().implementation();
  }

  @Override
  public String getParameterName(io.swagger.v3.oas.annotations.Parameter parameterAnnotation) {
    return parameterAnnotation.name();
  }

  @Override
  public HttpParameterType getHttpParameterType(io.swagger.v3.oas.annotations.Parameter annotation) {
    return HttpParameterType.from(annotation.in());
  }

  @Override
  public void fillParameter(OpenAPI swagger, Operation operation, Parameter parameter, JavaType type,
      io.swagger.v3.oas.annotations.Parameter annotation) {
    Schema schema = SwaggerUtils.resolveTypeSchemas(swagger, type);
    parameter.setSchema(schema);
    parameter.setRequired(annotation.required());
  }

  @Override
  public void fillRequestBody(OpenAPI swagger, Operation operation, RequestBody parameter,
      String parameterName, JavaType type, io.swagger.v3.oas.annotations.Parameter parameter2) {

  }
}
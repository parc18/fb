/*
 * Copyright 2016-2017 Axioma srl.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.fb.www.api;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fb.www.pojos.ApiFormatter;
import com.fb.www.services.ServiceUtil;

/**
 * JAX-RS API endpoint.
 */
@SuppressWarnings("restriction")
@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class ApiEndpoint {	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiEndpoint.class);

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity<?> ping() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
    	LOGGER.debug("Server is running Fine Check DB credentials");
      	ApiFormatter<String>  events= ServiceUtil.convertToSuccessResponse("ok");
        return ResponseEntity.ok(events);
    }

}

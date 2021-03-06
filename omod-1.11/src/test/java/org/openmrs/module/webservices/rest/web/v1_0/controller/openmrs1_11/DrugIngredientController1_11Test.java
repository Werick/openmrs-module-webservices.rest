/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.webservices.rest.web.v1_0.controller.openmrs1_11;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.test.Util;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceControllerTest;

public class DrugIngredientController1_11Test extends MainResourceControllerTest {

	String drugUuid = "3cfcf118-931c-46f7-8ff6-7b876f0d4202";
	
	String drugUuid2 = "05ec820a-d297-44e3-be6e-698531d9dd3f";
	
	String ingredientUuid = "6519d653-393d-4118-9c83-a3715b82d4dc";
	
	private ConceptService service;
	
	@Override
	public String getURI() {
		return "drug/" + drugUuid + "/ingredient";
	}

	@Override
	public String getUuid() {
		return ingredientUuid;
	}

	@Override
	public long getAllCount() {
		return service.getDrugByUuid(drugUuid).getIngredients().size();
	}
	
	@Before
	public void before() throws Exception {
		this.service = Context.getConceptService();
	}
	
	@Test
	public void shouldAddIngredientToDrug() throws Exception {
		int before = service.getDrugByUuid(drugUuid).getIngredients().size();
		String json = "{ \"ingredient\":\"0abca361-f6bf-49cc-97de-b2f37f099dde\", \"strength\":4.0, \"units\":\"0955b484-b364-43dd-909b-1fa3655eaad2\"}";
		
		handle(newPostRequest(getURI(), json));
		
		int after = service.getDrugByUuid(drugUuid).getIngredients().size();
		Assert.assertEquals(before + 1, after);
	}
	
	@Test
	public void shouldListIngredientsForADrug() throws Exception {
		SimpleObject response = deserialize(handle(newGetRequest("drug/" + drugUuid2 + "/ingredient")));
		
		List<Object> resultsList = Util.getResultsList(response);
		
		Assert.assertEquals(1, resultsList.size());
		List<Object> ingredients = Arrays.asList(PropertyUtils.getProperty(resultsList.get(0), "ingredient"));
		
		Assert.assertEquals(((Map)ingredients.get(0)).get("display"), "ASPIRIN");
		Assert.assertEquals(((Map)ingredients.get(0)).get("uuid"), "15f83cd6-64e9-4e06-a5f9-364d3b14a43d");
	}
}
package com.onboarding.payu.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.client.request.ClientRequest;
import com.onboarding.payu.model.client.response.ClientResponse;
import com.onboarding.payu.service.IClientService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Credit Card's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1.0/clients")
public class ClientController {

	private IClientService iClientService;

	@Autowired
	public ClientController(final IClientService iClientService) {

		this.iClientService = iClientService;
	}

	@PostMapping
	public ResponseEntity<ClientResponse> createClient(@Valid @NotNull @RequestBody final ClientRequest clientRequest){
		return new ResponseEntity(iClientService.create(clientRequest), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientResponse> findProductById(@NotNull @PathVariable Integer id) {

		Validate.notNull(id, "Client id cannot not be empty");
		return ResponseEntity.ok(iClientService.findClientById(id));
	}

	@PutMapping
	public ResponseEntity<ClientResponse> updateProduct(@Valid @NotNull @RequestBody ClientRequest clientRequest) {

		Validate.notNull(clientRequest.getIdClient(), "Client id cannot not be empty");
		return ResponseEntity.ok(iClientService.update(clientRequest));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ClientResponse> deleteProduct(@NotNull @PathVariable Integer id) {

		Validate.notNull(id, "Product identification cannot not be empty to remove");
		iClientService.delete(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}

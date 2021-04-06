package com.onboarding.payu.service.impl.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.ProductPoDto;
import com.onboarding.payu.model.purchase.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.PurchaseOrderResponse;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderMapper {

	/**
	 * Get PurchaseOrder to register them in database
	 *
	 * @param client           {@link Client}
	 * @param productList      {@link List < Product >}
	 * @param purchaseOrderRequest {@link PurchaseOrder}
	 * @return {@link PurchaseOrder}
	 */
	public PurchaseOrder toPurchaseOrder(final Client client, final List<Product> productList,
												final PurchaseOrderRequest purchaseOrderRequest) {

		return PurchaseOrder.builder().client(client)
							.status(StatusType.SAVED.name())
							.date(LocalDate.now())
							.value(getTotalValue(productList, purchaseOrderRequest.getProductList()))
							.referenceCode(UUID.randomUUID().toString())
							.languaje(LanguageType.ES.getLanguage())
							.street1(purchaseOrderRequest.getClient().getStreet1())
							.street2(purchaseOrderRequest.getClient().getStreet2())
							.city(purchaseOrderRequest.getClient().getCity())
							.state(purchaseOrderRequest.getClient().getState())
							.country(purchaseOrderRequest.getClient().getCountry())
							.postalCode(purchaseOrderRequest.getClient().getPostalCode())
							.build();
	}

	/**
	 * Get the total value of the purchase order
	 *
	 * @param productList    {@link List<Product>}
	 * @param productPoDTOList {@link List< ProductPoDto >}
	 * @return {@link BigDecimal}
	 */
	private BigDecimal getTotalValue(final List<Product> productList, final List<ProductPoDto> productPoDTOList) {

		return productPoDTOList.stream().map(productPoDTO -> BigDecimal.valueOf(productPoDTO.getQuantity()).multiply(
				productList.stream().filter(product -> product.getIdProduct().equals(productPoDTO.getIdProduct())).findFirst().get()
						   .getPrice())).reduce(BigDecimal.valueOf(0.0), (a, b) -> a.add(b));
	}

	/**
	 *
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @return {@link PurchaseOrderResponse}
	 */
	public PurchaseOrderResponse toPurchaseOrderResponse(final PurchaseOrder purchaseOrder) {

		return PurchaseOrderResponse.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
									.status(purchaseOrder.getStatus())
									.referenceCode(purchaseOrder.getReferenceCode())
									.date(purchaseOrder.getDate())
									.value(purchaseOrder.getValue()).build();
	}
}

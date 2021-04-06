package com.onboarding.payu.service.impl.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.ProductDto;
import com.onboarding.payu.model.purchase.PurchaseOrderDto;
import com.onboarding.payu.model.purchase.PurchaseOrderResponse;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.repository.entity.PurchaseOrder;

public class PurchaseOrderMapper {

	/**
	 * Get PurchaseOrder to register them in database
	 *
	 * @param client           {@link Client}
	 * @param productList      {@link List < Product >}
	 * @param purchaseOrderDTO {@link PurchaseOrder}
	 * @return {@link PurchaseOrder}
	 */
	public static PurchaseOrder toPurchaseOrder(final Client client, final List<Product> productList,
												final PurchaseOrderDto purchaseOrderDTO) {

		return PurchaseOrder.builder().client(client)
							.status(StatusType.SAVED.name())
							.date(LocalDate.now())
							.value(getTotalValue(productList, purchaseOrderDTO.getProductList()))
							.referenceCode(UUID.randomUUID().toString())
							.languaje(LanguageType.ES.getLanguage())
							.street1(purchaseOrderDTO.getClientDto().getStreet1())
							.street2(purchaseOrderDTO.getClientDto().getStreet2())
							.city(purchaseOrderDTO.getClientDto().getCity())
							.state(purchaseOrderDTO.getClientDto().getState())
							.country(purchaseOrderDTO.getClientDto().getCountry())
							.postalCode(purchaseOrderDTO.getClientDto().getPostalCode())
							.build();
	}

	/**
	 * Get the total value of the purchase order
	 *
	 * @param productList    {@link List<Product>}
	 * @param productDTOList {@link List<ProductDto>}
	 * @return {@link BigDecimal}
	 */
	private static BigDecimal getTotalValue(final List<Product> productList, final List<ProductDto> productDTOList) {

		return productDTOList.stream().map(productDTO -> BigDecimal.valueOf(productDTO.getQuantity()).multiply(
				productList.stream().filter(product -> product.getIdProduct().equals(productDTO.getIdProduct())).findFirst().get()
						   .getPrice())).reduce(BigDecimal.valueOf(0.0), (a, b) -> a.add(b));
	}

	/**
	 *
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @return {@link PurchaseOrderResponse}
	 */
	public static PurchaseOrderResponse toPurchaseOrderResponse(final PurchaseOrder purchaseOrder) {

		return PurchaseOrderResponse.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
									.status(purchaseOrder.getStatus())
									.referenceCode(purchaseOrder.getReferenceCode())
									.date(purchaseOrder.getDate())
									.value(purchaseOrder.getValue()).build();
	}
}

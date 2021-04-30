export interface PurchasOrderResponse{
    id: number,
	status: string,
	referenceCode: string,
	date: string,
	value: number,
	products: any[]
}
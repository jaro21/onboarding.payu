export interface PurchasOrderResponse{
    id: number,
	status: string,
	referenceCode: string,
	date: string,
	value: number,
	products: any[],
	fullName: string,
	email: string,
	dniNumber: string
}
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseOrderAdminComponent } from './purchase-order-admin.component';

describe('PurchaseOrderAdminComponent', () => {
  let component: PurchaseOrderAdminComponent;
  let fixture: ComponentFixture<PurchaseOrderAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchaseOrderAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseOrderAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

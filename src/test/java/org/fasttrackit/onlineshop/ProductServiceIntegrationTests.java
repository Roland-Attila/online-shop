package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exeption.ResourceNotFoundExeption;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.transfer.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

	@Autowired
	private ProductService productService;

	@Test
	public void testCreateProduct_whenValidRequest_thenProductIsSaved() {
		createProduct();
	}

	@Test(expected = TransactionSystemException.class)
	public void testCreateProduct_whenInvalidRequest_thenThrowException(){
		SaveProductRequest request = new SaveProductRequest();
//		leaving request properties with default null values
//		to validate the negative flow
		productService.createProduct(request);
	}

	@Test
	public void testGetProduct_whenExistingProduct_thenReturnProduct() {
		Product createdProduct = createProduct();
		Product retrieve = productService.getProduct(createdProduct.getId());

		assertThat(retrieve, notNullValue());
		assertThat(retrieve.getId(), is(createdProduct.getId()));
		assertThat(retrieve.getName(), is(createdProduct.getName()));
		assertThat(retrieve.getPrice(), is(createdProduct.getPrice()));
		assertThat(retrieve.getQuantity(), is(createdProduct.getQuantity()));
		assertThat(retrieve.getDescription(), is(createdProduct.getDescription()));
	}

	@Test(expected = ResourceNotFoundExeption.class)
	public void testGetProduct_whenNonExistingProduct_thenThrowResourcesNotFoundException() {
		productService.getProduct(999999999);
	}

	@Test
	public void testUpdateProduct_whenValidRequest_thenReturnUpdatedProduct() {
		Product createdProduct = createProduct();

		SaveProductRequest request = new SaveProductRequest();
		request.setName(createdProduct.getName()+ " updated ");
		request.setDescription(createdProduct.getDescription()+ " updated ");
		request.setPrice(createdProduct.getPrice()+ 10 );
		request.setQuantity(createdProduct.getQuantity()+ 10);

		Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);

		assertThat(updatedProduct, notNullValue());
		assertThat(updatedProduct.getId(), is(createdProduct.getId()));
		assertThat(updatedProduct.getName(), is(request.getName()));
		assertThat(updatedProduct.getDescription(), is(request.getDescription()));
		assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
		assertThat(updatedProduct.getPrice(), is(request.getPrice()));
	}

	@Test (expected = ResourceNotFoundExeption.class)
	public void testDeleteProduct_whenExistingProduct_thenProductIsDeleted(){
		Product product = createProduct();
		productService.deleteProduct(product.getId());
		productService.getProduct(product.getId());
	}

	private Product createProduct() {
		SaveProductRequest request = new SaveProductRequest();
		request.setName("Banana" + System.currentTimeMillis());
		request.setPrice(5.0);
		request.setQuantity(100);
		request.setDescription("Healthy food");

		Product createdProduct = productService.createProduct(request);
		assertThat(createdProduct, notNullValue());
		assertThat(createdProduct.getId(), notNullValue());
		assertThat(createdProduct.getId(), greaterThan(0L));
		assertThat(createdProduct.getName(), is(request.getName()));
		assertThat(createdProduct.getPrice(), is(request.getPrice()));
		assertThat(createdProduct.getQuantity(), is(request.getQuantity()));
		assertThat(createdProduct.getDescription(), is(request.getDescription()));
		return createdProduct;
	}
}
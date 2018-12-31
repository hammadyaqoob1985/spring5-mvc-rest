package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    public static final String JOHN = "John";
    public static final long ID = 1L;
    private CategoryService categoryService;
    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);


    }

    @Test
    public void getAllCategories() {

        List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categoryList);

        assertThat(categoryService.getAllCategories().size(), is(3));
    }

    @Test
    public void getCategoryByName() {
        Category category = new Category();
        category.setName(JOHN);
        category.setId(ID);

        when(categoryRepository.findByName(JOHN)).thenReturn(category);

        CategoryDTO returnedCategoryDTO = categoryService.getCategoryByName(JOHN);

        assertThat(returnedCategoryDTO.getName(), is(JOHN));
        assertThat(returnedCategoryDTO.getId(), is(ID));

    }
}
package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class CategoryMapperTest {

    public static final String JOHN = "John";
    public static final long ID = 1L;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void testMapping() {

        Category category = new Category();
        category.setName(JOHN);
        category.setId(ID);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertThat(categoryDTO, not(nullValue()));
        assertThat(categoryDTO.getId(), is(ID));
        assertThat(categoryDTO.getName(), is(JOHN));
    }
}
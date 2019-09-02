package core.di.factory;

import core.di.factory.config.AnnontatedBeanDefinition;
import core.di.factory.example.IntegrationConfig;
import core.di.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotatedBeanDefinitionReaderTest {

    @Test
    void read() throws NoSuchMethodException {
        Class<?> targetClass = IntegrationConfig.class;
        DefaultListableBeanFactory registry = new DefaultListableBeanFactory();
        Method dataSource = targetClass.getMethod("dataSource");
        Method jdbcTemplate = targetClass.getMethod("jdbcTemplate", DataSource.class);

        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(registry);
        reader.register(targetClass);

        assertThat(registry.getDefinitions()).hasSize(2);
        assertThat(registry.getDefinitions())
                .contains(
                        new AnnontatedBeanDefinition(targetClass, dataSource)
                        , new AnnontatedBeanDefinition(targetClass, jdbcTemplate));
    }
}
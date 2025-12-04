package com.mycompany.alumno.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.sql.DataSource;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * CAPA DE UTILIDADES - Gestión de conexión JPA SIN persistence.xml
 * Configuración programática de Hibernate
 */
public class Conexion {
    
    private static EntityManagerFactory emf = null;
    
    // Obtener el EntityManagerFactory (Singleton)
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                Properties props = new Properties();
                
                // Configuración de la base de datos
                props.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
                props.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5435/sistema_alumnos");
                props.put("jakarta.persistence.jdbc.user", "postgres");
                props.put("jakarta.persistence.jdbc.password", "123456");
                
                // Configuración de Hibernate
                props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                props.put("hibernate.hbm2ddl.auto", "update"); // Crea/actualiza las tablas automáticamente
                props.put("hibernate.show_sql", "true"); // Muestra las queries SQL en consola
                props.put("hibernate.format_sql", "true"); // Formatea las queries
                
                PersistenceUnitInfo persistenceUnitInfo = new PersistenceUnitInfo() {
                    @Override
                    public String getPersistenceUnitName() {
                        return "AlumnosPU";
                    }

                    @Override
                    public String getPersistenceProviderClassName() {
                        return "org.hibernate.jpa.HibernatePersistenceProvider";
                    }

                    @Override
                    public PersistenceUnitTransactionType getTransactionType() {
                        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
                    }

                    @Override
                    public DataSource getJtaDataSource() {
                        return null;
                    }

                    @Override
                    public DataSource getNonJtaDataSource() {
                        return null;
                    }

                    @Override
                    public List<String> getMappingFileNames() {
                        return Collections.emptyList();
                    }

                    @Override
                    public List<URL> getJarFileUrls() {
                        return Collections.emptyList();
                    }

                    @Override
                    public URL getPersistenceUnitRootUrl() {
                        return null;
                    }

                    @Override
                    public List<String> getManagedClassNames() {
                        // Aquí especificas tus entidades
                        return List.of("com.mycompany.alumno.model.Alumno");
                    }

                    @Override
                    public boolean excludeUnlistedClasses() {
                        return false;
                    }

                    @Override
                    public Properties getProperties() {
                        return props;
                    }

                    @Override
                    public String getPersistenceXMLSchemaVersion() {
                        return "3.0";
                    }

                    @Override
                    public ClassLoader getClassLoader() {
                        return Thread.currentThread().getContextClassLoader();
                    }

                    @Override
                    public void addTransformer(jakarta.persistence.spi.ClassTransformer transformer) {
                    }

                    @Override
                    public ClassLoader getNewTempClassLoader() {
                        return null;
                    }
                };
                
                HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
                emf = provider.createContainerEntityManagerFactory(persistenceUnitInfo, props);
                
                System.out.println("✅ Conexión a la base de datos establecida correctamente");
            } catch (Exception e) {
                System.err.println("❌ Error al crear EntityManagerFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return emf;
    }
    
    // Obtener un nuevo EntityManager
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
    
    // Cerrar el EntityManagerFactory
    public static void cerrarConexion() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("🔒 Conexión cerrada correctamente");
        }
    }
}
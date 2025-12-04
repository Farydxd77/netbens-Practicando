/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.alumno.dao;

import com.mycompany.alumno.model.Alumno;
import com.mycompany.alumno.util.Conexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * CAPA DAO (Data Access Object)
 * Maneja todas las operaciones CRUD con la base de datos
 */
public class AlumnoDAO {
    
    // Crear un nuevo alumno
    public void crear(Alumno alumno) {
        EntityManager em = Conexion.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(alumno);
            transaction.commit();
            System.out.println("✅ Alumno creado: " + alumno.getNombre());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("❌ Error al crear alumno: " + e.getMessage());
            throw new RuntimeException("Error al crear alumno", e);
        } finally {
            em.close();
        }
    }
    
    // Obtener un alumno por ID
    public Alumno obtenerPorId(Long id) {
        EntityManager em = Conexion.getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }
    
    // Obtener todos los alumnos
    public List<Alumno> obtenerTodos() {
        EntityManager em = Conexion.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Alumno a", Alumno.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    // Actualizar un alumno
    public void actualizar(Alumno alumno) {
        EntityManager em = Conexion.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.merge(alumno);
            transaction.commit();
            System.out.println("✅ Alumno actualizado: " + alumno.getNombre());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("❌ Error al actualizar alumno: " + e.getMessage());
            throw new RuntimeException("Error al actualizar alumno", e);
        } finally {
            em.close();
        }
    }
    
    // Eliminar un alumno
    public void eliminar(Long id) {
        EntityManager em = Conexion.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            Alumno alumno = em.find(Alumno.class, id);
            if (alumno != null) {
                em.remove(alumno);
                System.out.println("✅ Alumno eliminado con ID: " + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("❌ Error al eliminar alumno: " + e.getMessage());
            throw new RuntimeException("Error al eliminar alumno", e);
        } finally {
            em.close();
        }
    }
    
    // Buscar alumnos por nombre
    public List<Alumno> buscarPorNombre(String nombre) {
        EntityManager em = Conexion.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM Alumno a WHERE LOWER(a.nombre) LIKE LOWER(:nombre)", 
                    Alumno.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}